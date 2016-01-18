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
package com.jdon.jivejdon.repository.listener;

import org.apache.log4j.Logger;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.MessageTransactionPersistence;

/**
 * topic addReplyMessage has three DomainEventHandlers: AddReplyMessage;
 * AddReplyMessageRefresher AddReplyMessageSearchFile
 * 
 * these DomainEventHandlers run by the alphabetical(字母排列先后运行) of their class
 * name
 * 
 * @author banq
 * 
 */
@Consumer("addReplyMessage")
public class AddReplyMessageRefresher implements DomainEventHandler {
	private final static Logger logger = Logger.getLogger(SaveMessage.class);

	protected MessageTransactionPersistence messageTransactionPersistence;
	protected ForumFactory forumAbstractFactory;

	public AddReplyMessageRefresher(MessageTransactionPersistence messageTransactionPersistence, ForumFactory forumAbstractFactory) {
		super();
		this.messageTransactionPersistence = messageTransactionPersistence;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		ForumMessageReply forumMessageReply = (ForumMessageReply) event.getDomainMessage().getEventSource();
		try {
			// load the new message into cache, prepare for next GET request
			ForumMessage forumMessage = forumAbstractFactory.getMessage(forumMessageReply.getMessageId());
			event.getDomainMessage().setEventResult(forumMessage);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
