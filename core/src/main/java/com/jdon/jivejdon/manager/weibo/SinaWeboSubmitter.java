/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.manager.weibo;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;
import weibo4j.http.AccessToken;
import weibo4j.http.HttpClient;
import weibo4j.http.PostParameter;
import weibo4j.http.RequestToken;
import weibo4j.http.Response;

import com.jdon.annotation.Component;

@Component
public class SinaWeboSubmitter {
	private final static Logger logger = Logger.getLogger(SinaWeboSubmitter.class);

	public final WeiboOAuthParamVO weiboOAuthParamVO;

	public SinaWeiboUserPwd sinaWeiboUserPwd;

	public SinaWeboSubmitter(WeiboOAuthParamVO weiboOAuthParamVO) {
		super();
		this.weiboOAuthParamVO = weiboOAuthParamVO;
	}

	public void submitWeibo(String content) {
		try {
			RequestToken requestToken = request("xml");
			HttpClient http = new HttpClient();
			PostParameter[] postParameters = new PostParameter[] { new PostParameter("oauth_callback", "xml"),
					new PostParameter("userId", sinaWeiboUserPwd.getUserId()), new PostParameter("passwd", sinaWeiboUserPwd.getPasswd()) };
			Response response = http.get(requestToken.getAuthorizationURL());
			response = http.post(requestToken.getAuthorizationURL(), postParameters);

			StringReader reader = new StringReader(response.asString());
			InputSource inputSource = new InputSource(reader);
			Map map = readXML(inputSource);

			AccessToken accessToken = requstAccessToken(requestToken, (String) map.get("oauth_verifier"));
			update(accessToken, content);
		} catch (Exception e) {
			logger.error("submitWeibo error:" + e);
		}

	}

	public boolean verfiyUser(String userId, String password) {
		boolean valide = false;
		try {
			RequestToken requestToken = request("xml");
			HttpClient http = new HttpClient();
			PostParameter[] postParameters = new PostParameter[] { new PostParameter("oauth_callback", "xml"), new PostParameter("userId", userId),
					new PostParameter("passwd", password) };
			Response response = http.get(requestToken.getAuthorizationURL());
			response = http.post(requestToken.getAuthorizationURL(), postParameters);

			StringReader reader = new StringReader(response.asString());
			InputSource inputSource = new InputSource(reader);
			Map map = readXML(inputSource);

			AccessToken accessToken = requstAccessToken(requestToken, (String) map.get("oauth_verifier"));
			Weibo weibo = new Weibo();
			weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());

			User user = weibo.verifyCredentials();
			if (user != null)
				valide = true;
		} catch (Exception e) {
			logger.error("submitWeibo error:" + e);
		}
		return valide;

	}

	public RequestToken request(String backUrl) {
		try {
			System.setProperty("weibo4j.oauth.consumerKey", weiboOAuthParamVO.CONSUMER_KEY);
			System.setProperty("weibo4j.oauth.consumerSecret", weiboOAuthParamVO.CONSUMER_SECRET);

			Weibo weibo = new Weibo();
			RequestToken requestToken = weibo.getOAuthRequestToken(backUrl);

			System.out.println("Got request token.");
			System.out.println("Request token: " + requestToken.getToken());
			System.out.println("Request token secret: " + requestToken.getTokenSecret());
			return requestToken;
		} catch (Exception e) {
			logger.error("request error:" + e);
			return null;
		}
	}

	public AccessToken requstAccessToken(RequestToken requestToken, String verifier) {
		try {
			System.setProperty("weibo4j.oauth.consumerKey", weiboOAuthParamVO.CONSUMER_KEY);
			System.setProperty("weibo4j.oauth.consumerSecret", weiboOAuthParamVO.CONSUMER_SECRET);

			Weibo weibo = new Weibo();
			AccessToken accessToken = weibo.getOAuthAccessToken(requestToken.getToken(), requestToken.getTokenSecret(), verifier);

			return accessToken;
		} catch (Exception e) {
			logger.error("requstAccessToken error:" + e);
			return null;
		}
	}

	public void update(AccessToken access, String content) throws Exception {
		try {
			Weibo weibo = new Weibo();
			weibo.setToken(access.getToken(), access.getTokenSecret());
			Status status = weibo.updateStatus(content);
			logger.debug("Successfully updated the status to [" + status.getText() + "].");
		} catch (WeiboException e) {
			throw new WeiboException(e);
		}
	}

	public static Map readXML(InputSource inputSource) {
		Map map = new HashMap();
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();

			Document doc = docBuilder.parse(inputSource);

			// normalize text representation
			doc.getDocumentElement().normalize();
			System.out.println("Root element of the doc is " + doc.getDocumentElement().getNodeName());

			map.put("oauth_token", getValue(doc, "oauth_token"));
			map.put("oauth_verifier", getValue(doc, "oauth_verifier"));
			System.out.println(" oauth_token=" + getValue(doc, "oauth_token"));
			System.out.println(" oauth_token=" + getValue(doc, "oauth_verifier"));

		} catch (Exception err) {

			System.out.println(" " + err.getMessage());

		}
		return map;

	}

	public static String getValue(Document doc, String name) {
		String ret = "";
		NodeList listOfPersons = doc.getElementsByTagName(name);
		for (int s = 0; s < listOfPersons.getLength(); s++) {
			Node firstPersonNode = listOfPersons.item(s);
			Element firstPersonElement = (Element) firstPersonNode;
			ret = firstPersonElement.getTextContent();

		}// end of for loop with s var
		return ret;

	}

	public static void main(String[] args) throws Exception {

		WeiboOAuthParamVO weiboOAuthParamVO = new WeiboOAuthParamVO("2879276008", "8af8248a4cd6bf389685e6d3907349f0");
		SinaWeboSubmitter sinaWeboSubmitter = new SinaWeboSubmitter(weiboOAuthParamVO);
		if (sinaWeboSubmitter.verfiyUser("wback@sina.cn", "weibobackup"))
			System.out.print("ok");

	}

	public SinaWeiboUserPwd getSinaWeiboUserPwd() {
		return sinaWeiboUserPwd;
	}

	public void setSinaWeiboUserPwd(SinaWeiboUserPwd sinaWeiboUserPwd) {
		this.sinaWeiboUserPwd = sinaWeiboUserPwd;
	}

}
