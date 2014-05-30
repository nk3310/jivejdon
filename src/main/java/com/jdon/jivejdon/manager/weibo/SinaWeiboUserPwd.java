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

import com.jdon.util.StringUtil;

public class SinaWeiboUserPwd {

	public static final String TYPE = "sinaweibo";

	private String type;
	private String userId;
	private String passwd;

	public SinaWeiboUserPwd(String userId, String passwd) {
		super();
		this.userId = userId;
		this.passwd = passwd;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public void setPasswdDecode(String passwd) {
		this.passwd = StringUtil.decodeString(passwd);
	}

	public String getUserId() {
		return userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getPasswdEncode() {
		return StringUtil.encodeString(passwd);

	}

	public boolean isEmpty() {
		if (userId == null || passwd == null)
			return true;
		if (userId.isEmpty() || passwd.isEmpty())
			return true;
		return false;
	}

}
