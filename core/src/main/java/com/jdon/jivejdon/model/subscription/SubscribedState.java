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
package com.jdon.jivejdon.model.subscription;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.repository.LazyLoaderRole;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;

/**
 * // the field is not the core field of ThreadTag, it's value is fetched by //
 * query, not by aggregation
 * 
 * @author banq
 * 
 */
public class SubscribedState {

	private int subscriptionCount = -1;

	private DomainMessage subCount;

	private Subscribed subscribed;

	public SubscribedState(Subscribed subscribed) {
		super();
		this.subscribed = subscribed;
	}

	/**
	 * ajax async invoke. call this method for two times.
	 * 
	 * getLazyCount at first is called. and later getSubscriptionCount will be
	 * called, and then messageSubCount will have value.
	 * 
	 * @return
	 */
	public int getSubscriptionCount(LazyLoaderRole domainEvents) {
		try {
			if (subscriptionCount == -1) {
				if (subCount == null) {
					subCount = domainEvents.loadSubscriptionNumbers(subscribed.getSubscribeId());
				} else {
					subscriptionCount = (Integer) subCount.getEventResult();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return subscriptionCount;
	}

	public void update(int count) {
		if (subscriptionCount != -1) {
			subscriptionCount = subscriptionCount + count;
		}

	}

}
