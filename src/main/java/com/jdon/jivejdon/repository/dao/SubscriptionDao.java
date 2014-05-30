package com.jdon.jivejdon.repository.dao;

import java.util.Collection;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.subscription.Subscription;

public interface SubscriptionDao {

	boolean isSubscription(Long subscribedID, String userId);

	void createSubscription(Subscription subscription);

	Subscription getSubscription(Long id);

	void deleteSubscription(Subscription subscription);

	Collection<Subscription> getSubscriptionsForsubscribed(Long subscribedID);

	int getSubscriptionsForsubscribedCount(Long subscribedID);

	PageIterator getSubscriptions(String userId, int subscribedtype, int start, int count);

}
