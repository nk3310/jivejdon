package com.jdon.jivejdon.model.subscription.subscribed;

import com.jdon.jivejdon.model.Account;

public class AccountSubscribed implements Subscribed {

	public final static int TYPE = 3;

	protected Account account;
	protected Object o;

	public AccountSubscribed(Account account) {
		this.account = account;
	}

	public Long getSubscribeId() {
		return account.getUserIdLong();
	}

	public void setSubscribeId(Long subscribeId) {
		account.setUserIdLong(subscribeId);
	}

	public String getName() {
		return account.getUsername();
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getSubscribeType() {
		return AccountSubscribed.TYPE;
	}

	public void updateSubscriptionCount(int count) {
		account.updateSubscriptionCount(count);
	}

	public void addSubscribed(Object o) {
		this.o = o;
	}

	public Object[] getSubscribed() {
		return new Object[] { account, o };
	}

}
