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
package com.jdon.jivejdon.model.subscription.messsage;

import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.util.StringUtil;

public class ForumNotifyMessage implements NotifyMessage {
	private Subscription subscription;
	private String notifyUrlTemp;
	private ShortMessage shortMessage;

	public ForumNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
		super();
		shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(notifyTitle);
		shortMessage.setMessageFrom(notifier);
		this.notifyUrlTemp = notifyUrlTemp;

	}

	public Subscription getSubscription() {
		return subscription;
	}

	public void setSubscription(Subscription subscription) {
		this.subscription = subscription;
		//
		this.shortMessage.setAccount(subscription.getAccount());
		this.shortMessage.setMessageTo(subscription.getAccount().getUsername());
	}

	public String getNotifyUrlTemp() {
		return notifyUrlTemp;
	}

	public ForumNotifyMessage clone() {
		return new ForumNotifyMessage(shortMessage.getMessageTitle(), this.notifyUrlTemp, shortMessage.getMessageFrom());
	}

	public void fullfillNotifyMessage(Subscribed subscribed) {
		Forum forum = (Forum) subscribed.getSubscribed()[0];
		// http://www.jdon.com/jivejdon/thread/threadId#messageId
		String newSubscribedUrl = StringUtil.replace(getNotifyUrlTemp(), "threadId", forum.getForumState().getLastPost().getForumThread()
				.getThreadId().toString());
		newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "messageId", forum.getForumState().getLastPost().getMessageId().toString());
		String body = forum.getForumState().getLastPost().getAccount().getUsername() + "【"
				+ forum.getForumState().getLastPost().getMessageVO().getSubject() + "】: "
				+ forum.getForumState().getLastPost().getMessageVO().getShortBody(60) + "..." + newSubscribedUrl;
		shortMessage.setMessageBody(body);
		shortMessage.setMessageTitle(subscribed.getName() + ":" + shortMessage.getMessageTitle());

	}

	public ShortMessage getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		this.shortMessage = shortMessage;
	}
}
