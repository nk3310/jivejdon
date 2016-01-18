package com.jdon.jivejdon.model.subscription.subscribed;

import com.jdon.jivejdon.model.ForumThread;

public class ThreadSubscribed implements Subscribed {

	public final static int TYPE = 1;

	protected ForumThread forumThread;

	public ThreadSubscribed(ForumThread thread) {
		this.forumThread = thread;
	}

	public Long getSubscribeId() {
		return forumThread.getThreadId();
	}

	public void setSubscribeId(Long subscribeId) {
		forumThread.setThreadId(subscribeId);
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public void setForumThread(ForumThread thread) {
		this.forumThread = thread;
	}

	public String getName() {
		return forumThread.getName();
	}

	public int getSubscribeType() {
		return ThreadSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		forumThread.getState().updateSubscriptionCount(count);
	}

	public void addSubscribed(Object o) {
		if (o != null && o instanceof ForumThread)
			this.forumThread = (ForumThread) o;
	}

	public Object[] getSubscribed() {
		return new Object[] { forumThread };
	}

}
