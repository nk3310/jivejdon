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
package com.jdon.jivejdon.presentation.form.weibo;

import com.jdon.jivejdon.manager.subscription.SubscriptionActionHolder;
import com.jdon.jivejdon.manager.subscription.action.SinaWeiboAction;
import com.jdon.jivejdon.manager.weibo.SinaWeiboUserPwd;

public class SinaweiboForm {

	private boolean sinaweibo;

	private String userId = "";

	private String passwd = "";

	public boolean isSinaweibo() {
		return sinaweibo;
	}

	public void setSinaweibo(boolean sinaweibo) {
		this.sinaweibo = sinaweibo;
	}

	public SubscriptionActionHolder pack(SubscriptionActionHolder subscriptionActionHolder) {
		if (isSinaweibo()) {
			subscriptionActionHolder.addAction(new SinaWeiboAction(new SinaWeiboUserPwd(getUserId(), getPasswd())));
		} else
			subscriptionActionHolder.removeActionType(SinaWeiboAction.class);
		return subscriptionActionHolder;

	}

	public void unpack(SubscriptionActionHolder subscriptionActionHolder) {
		if (subscriptionActionHolder.hasActionType(SinaWeiboAction.class)) {
			SinaWeiboAction sinaWeiboAction = (SinaWeiboAction) subscriptionActionHolder.getActionType(SinaWeiboAction.class);
			this.passwd = sinaWeiboAction.getSinaWeiboUserPwd().getPasswd();
			this.userId = sinaWeiboAction.getSinaWeiboUserPwd().getUserId();
			this.sinaweibo = true;
		}

	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

}
