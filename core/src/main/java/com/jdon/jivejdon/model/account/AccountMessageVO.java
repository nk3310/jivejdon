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
package com.jdon.jivejdon.model.account;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.repository.LazyLoaderRole;

public class AccountMessageVO {

	private int messageCount = -1;

	private DomainMessage messageCountAsyncResult;

	private Account account;

	public AccountMessageVO(Account account) {
		super();
		this.account = account;
	}

	public int getMessageCount(LazyLoaderRole domainEvents) {
		try {
			if (messageCount == -1 && domainEvents != null) {
				if (messageCountAsyncResult == null) {
					messageCountAsyncResult = domainEvents.loadAccountMessageCount(account.getUserIdLong());
				} else {
					messageCount = (Integer) messageCountAsyncResult.getEventResult();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageCount;
	}

	public int getMessageCountNow(LazyLoaderRole domainEvents) {
		try {
			messageCountAsyncResult = domainEvents.loadAccountMessageCount(account.getUserIdLong());
			messageCount = (Integer) messageCountAsyncResult.getEventResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return messageCount;
	}

	public void update(int count) {
		if (messageCount != -1) {
			messageCount = messageCount + count;
		}

	}
}
