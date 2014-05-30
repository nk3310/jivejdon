/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.model;

import java.util.concurrent.atomic.AtomicLong;

import com.jdon.jivejdon.model.subscription.SubscribedState;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;

/**
 * State is a Value Object, it is immutable. need a pattern to keep immutable.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class ForumThreadState {
	/**
	 * the number of messages in the thread. This includes the root message. So,
	 * to find the number of replies to the root message, subtract one from the
	 * answer of this method.
	 */
	private final AtomicLong messageCount;

	private final ForumMessage lastPost;

	private final ForumThread forumThread;

	private final SubscribedState subscribedState;

	public ForumThreadState(ForumThread forumThread, ForumMessage lastPost, long messageCount) {
		super();
		this.forumThread = forumThread;
		this.lastPost = lastPost;
		this.messageCount = new AtomicLong(messageCount);
		this.subscribedState = new SubscribedState(new ThreadSubscribed(forumThread));
	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		return messageCount.intValue();
	}

	/**
	 * @param messageCount
	 *            The messageCount to set.
	 */
	public void setMessageCount(int messageCount) {
		this.messageCount.set(messageCount);
	}

	public long addMessageCount() {
		return this.messageCount.incrementAndGet();
	}

	public ForumMessage getLastPost() {
		return lastPost;
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public String getModifiedDate() {
		if (lastPost != null)
			return lastPost.getModifiedDate();
		else
			return "";
	}

	public long getModifiedDate2() {
		if (lastPost != null)
			return lastPost.getModifiedDate2();
		else
			return 0;
	}

	public int getSubscriptionCount() {
		return subscribedState.getSubscriptionCount(this.forumThread.lazyLoaderRole);
	}

	public void updateSubscriptionCount(int count) {
		subscribedState.update(count);
	}

}
