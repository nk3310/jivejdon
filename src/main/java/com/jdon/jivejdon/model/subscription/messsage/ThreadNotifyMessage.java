package com.jdon.jivejdon.model.subscription.messsage;

import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.util.StringUtil;

public class ThreadNotifyMessage implements NotifyMessage {

	private Subscription subscription;
	private String notifyUrlTemp;
	private ShortMessage shortMessage;

	public ThreadNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
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

	public ThreadNotifyMessage clone() {
		return new ThreadNotifyMessage(shortMessage.getMessageTitle(), this.notifyUrlTemp, shortMessage.getMessageFrom());
	}

	public void fullfillNotifyMessage(Subscribed subscribed) {
		ForumThread forumThread = (ForumThread) subscribed.getSubscribed()[0];
		// http://www.jdon.com/jivejdon/thread/threadId#messageId
		String newSubscribedUrl = StringUtil.replace(getNotifyUrlTemp(), "threadId", forumThread.getThreadId().toString());
		newSubscribedUrl = StringUtil.replace(newSubscribedUrl, "messageId", forumThread.getState().getLastPost().getMessageId().toString());
		String body = forumThread.getState().getLastPost().getAccount().getUsername() + "【"
				+ forumThread.getState().getLastPost().getMessageVO().getSubject() + "】:"
				+ forumThread.getState().getLastPost().getMessageVO().getShortBody(60) + "..." + newSubscribedUrl;
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
