package com.jdon.jivejdon.model.subscription.subscribed;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;

public class TagSubscribed implements Subscribed {

	public final static int TYPE = 2;

	protected ThreadTag tag;
	protected ForumThread thread;

	public TagSubscribed(ThreadTag tag, ForumThread thread) {
		this.tag = tag;
		this.thread = thread;
	}

	public TagSubscribed(ThreadTag tag) {
		this.tag = tag;
	}

	public Long getSubscribeId() {
		return tag.getTagID();
	}

	public void setSubscribeId(Long subscribeId) {
		tag.setTagID(subscribeId);
	}

	public ThreadTag getTag() {
		return tag;
	}

	public void setTag(ThreadTag tag) {
		this.tag = tag;
	}

	public String getName() {
		return tag.getTitle();
	}

	public int getSubscribeType() {
		return TagSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		tag.updateSubscriptionCount(count);
	}

	public void addSubscribed(Object o) {
		if (o != null && o instanceof ForumThread)
			this.thread = (ForumThread) o;
	}

	public Object[] getSubscribed() {
		return new Object[] { tag, thread };
	}

}
