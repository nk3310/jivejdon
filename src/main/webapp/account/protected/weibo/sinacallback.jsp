<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="weibo4j.http.*" %>
<%@ page language="java" import="weibo4j.*" %>
<jsp:useBean id="weboauth" scope="session" class="com.jdon.jivejdon.manager.weibo.SinaWeboSubmitter" />

<%
	
	String verifier=request.getParameter("oauth_verifier");
	
	if(verifier!=null)
	{
	System.out.println("oauth:"+verifier);
		RequestToken resToken=(RequestToken) session.getAttribute("resToken");

		if(resToken!=null)
		{
			AccessToken accessToken=weboauth.requstAccessToken(resToken,verifier);
				if(accessToken!=null)
				{
				
					Weibo weibo = new Weibo();
					weibo.setToken(accessToken.getToken(), accessToken.getTokenSecret());
					weibo.verifyCredentials();
					
				}else
					{
					out.println("access token request error");
					}
		
		}
		else
			{
			out.println("request token session error");
			}
	}
	else
		{
		out.println("verifier String error");
		}

%>   