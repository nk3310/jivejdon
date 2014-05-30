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

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.messsage.AccountNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.ForumNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.NotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.TagNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.TagSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;

@Component
public class NotifyMessageFactory {

	private ForumNotifyMessage forumNotifyMessageTemp;
	private ThreadNotifyMessage threadNotifyMessageTemp;
	private TagNotifyMessage tagNotifyMessageTemp;
	private AccountNotifyMessage accountNotifyMessageTemp;

	public NotifyMessageFactory(ForumNotifyMessage forumNotifyMessageTemp, ThreadNotifyMessage threadNotifyMessageTemp,
			TagNotifyMessage tagNotifyMessageTemp, AccountNotifyMessage accountNotifyMessageTemp) {
		super();
		this.forumNotifyMessageTemp = forumNotifyMessageTemp;
		this.threadNotifyMessageTemp = threadNotifyMessageTemp;
		this.tagNotifyMessageTemp = tagNotifyMessageTemp;
		this.accountNotifyMessageTemp = accountNotifyMessageTemp;
	}

	public NotifyMessage create(Subscription subscription) {
		int subscribeType = subscription.getSubscribeType();
		if (subscribeType == ForumSubscribed.TYPE) {
			ForumNotifyMessage sm = forumNotifyMessageTemp.clone();
			sm.setSubscription(subscription);
			return sm;
		} else if (subscribeType == ThreadSubscribed.TYPE) {
			ThreadNotifyMessage sm = threadNotifyMessageTemp.clone();
			sm.setSubscription(subscription);
			return sm;
		} else if (subscribeType == TagSubscribed.TYPE) {
			TagNotifyMessage tnm = tagNotifyMessageTemp.clone();
			tnm.setSubscription(subscription);
			return tnm;
		} else if (subscribeType == AccountSubscribed.TYPE) {
			AccountNotifyMessage anm = accountNotifyMessageTemp.clone();
			anm.setSubscription(subscription);
			return anm;
		}
		return null;
	}

}
