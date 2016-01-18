package com.jdon.jivejdon.model.subscription.messsage;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ShortMessage;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.util.StringUtil;

/**
 * ShortMessage and NotifyMessage adpter
 * 
 * @author banq
 * 
 */
public class AccountNotifyMessage implements NotifyMessage {

	private Subscription subscription;

	private String notifyUrlTemp;

	private ShortMessage shortMessage;

	public AccountNotifyMessage(String notifyTitle, String notifyUrlTemp, String notifier) {
		super();
		shortMessage = new ShortMessage();
		shortMessage.setMessageTitle(notifyTitle);
		shortMessage.setMessageFrom(notifier);
		this.notifyUrlTemp = notifyUrlTemp;
	}

	public ShortMessage getShortMessage() {
		return shortMessage;
	}

	public void setShortMessage(ShortMessage shortMessage) {
		this.shortMessage = shortMessage;
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

	public AccountNotifyMessage clone() {
		return new AccountNotifyMessage(shortMessage.getMessageTitle(), this.notifyUrlTemp, shortMessage.getMessageFrom());
	}

	public String getNotifyUrlTemp() {
		return notifyUrlTemp;
	}

	public void setNotifyUrlTemp(String notifyUrlTemp) {
		this.notifyUrlTemp = notifyUrlTemp;
	}

	public void fullfillNotifyMessage(Subscribed subscribed) {
		ForumMessage forumMessage = (ForumMessage) subscribed.getSubscribed()[1];
		String newSubscribedUrl = StringUtil.replace(getNotifyUrlTemp(), "threadId", forumMessage.getForumThread().getThreadId().toString());
		shortMessage.setMessageBody(forumMessage.getAccount().getUsername() + "【" + forumMessage.getForumThread().getName() + "】: "
				+ forumMessage.getMessageVO().getShortBody(60) + "..." + newSubscribedUrl);
		shortMessage.setMessageTitle(subscribed.getName() + ":" + shortMessage.getMessageTitle());
	}

}
