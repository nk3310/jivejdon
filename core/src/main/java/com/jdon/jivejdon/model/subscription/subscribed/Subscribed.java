package com.jdon.jivejdon.model.subscription.subscribed;

public interface Subscribed {

	Long getSubscribeId();

	String getName();

	int getSubscribeType();

	Object[] getSubscribed();

	void addSubscribed(Object o);

	void updateSubscriptionCount(int count);

}
