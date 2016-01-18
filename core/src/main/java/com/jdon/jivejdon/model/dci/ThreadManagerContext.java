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
package com.jdon.jivejdon.model.dci;

import java.util.HashMap;
import java.util.Map;

import com.jdon.annotation.Component;
import com.jdon.domain.dci.RoleAssigner;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.repository.RepositoryRole;
import com.jdon.jivejdon.model.repository.RepositoryRoleIF;

@Component
public class ThreadManagerContext {
	private final RoleAssigner roleAssinger;

	private Map<Long, DomainMessage> transactions;

	public ThreadManagerContext(RoleAssigner roleAssinger) {
		super();
		this.roleAssinger = roleAssinger;
		this.transactions = new HashMap();
	}

	public boolean isTransactionOk(Long messageId) {
		if (transactions.size() == 0)
			return true;
		if (!transactions.containsKey(messageId)) {
			return true;
		}
		DomainMessage message = transactions.get(messageId);
		if (message.getBlockEventResult() != null) {
			transactions.remove(messageId);
			return true;
		}
		return false;
	}

	public void create(ForumMessage forumMessage) {
		RepositoryRoleIF repositoryRole = (RepositoryRoleIF) roleAssinger.assign(forumMessage, new RepositoryRole());
		DomainMessage domainMessage = repositoryRole.addTopicMessage(forumMessage);
		transactions.put(forumMessage.getMessageId(), domainMessage);

		ThreadRoleIF threadRole = (ThreadRoleIF) roleAssinger.assign(forumMessage, new ThreadRole());
		threadRole.aftercreateAction(domainMessage);

	}

	public void delete(ForumMessage delforumMessage) {
		RepositoryRoleIF repositoryRole = (RepositoryRoleIF) roleAssinger.assign(delforumMessage, new RepositoryRole());
		DomainMessage domainMessage = repositoryRole.deleteMessage(delforumMessage);
		transactions.put(delforumMessage.getMessageId(), domainMessage);

		ThreadRoleIF threadRole = (ThreadRoleIF) roleAssinger.assign(delforumMessage, new ThreadRole());
		threadRole.afterdelAction(domainMessage);

	}

}
