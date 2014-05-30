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
package com.jdon.jivejdon.manager.subscription;

import java.util.ArrayList;
import java.util.Collection;

import com.jdon.jivejdon.model.subscription.messsage.NotifyMessage;

public class SubscriptionActionHolder {

	private Collection<SubscriptionAction> subscriptionActions;

	public SubscriptionActionHolder() {
		this.subscriptionActions = new ArrayList();
	}

	public boolean hasActionType(Class actionClass) {
		if (actionClass == null)
			return false;
		for (SubscriptionAction subaction : subscriptionActions) {
			if (subaction.getClass().isAssignableFrom(actionClass)) {
				return true;
			}
		}
		return false;
	}

	public SubscriptionAction getActionType(Class actionClass) {
		if (actionClass == null)
			return null;
		for (SubscriptionAction subaction : subscriptionActions) {
			if (subaction.getClass().isAssignableFrom(actionClass)) {
				return subaction;
			}
		}
		return null;
	}

	public boolean removeActionType(Class actionClass) {
		if (actionClass == null)
			return false;
		for (SubscriptionAction subaction : subscriptionActions) {
			if (subaction.getClass().isAssignableFrom(actionClass)) {
				subscriptionActions.remove(subaction);
			}
		}
		return false;
	}

	public void addAction(SubscriptionAction subscriptionAction) {
		subscriptionActions.add(subscriptionAction);
	}

	public void doAction(NotifyMessage notifyMessage, SubscriptionNotify subscriptionNotify) {
		for (SubscriptionAction subaction : subscriptionActions) {
			subaction.exec(notifyMessage, subscriptionNotify);
		}
	}

}
