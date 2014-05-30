/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.repository.dao.filter;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.domain.model.cache.ModelKey;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.builder.MessageInitFactory;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.repository.dao.UploadFileDao;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.repository.dao.sql.MessageDaoSql;
import com.jdon.jivejdon.repository.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.repository.search.MessageSearchRepository;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * Cache decorator
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Introduce("modelCache")
public class MessageDaoDecorator extends MessageDaoSql {

	protected PageIteratorSolver pageIteratorSolver;
	protected MessageSearchRepository messageSearchProxy;

	public MessageDaoDecorator(MessagePageIteratorSolver messagePageIteratorSolver, JdbcTempSource jdbcTempSource, ContainerUtil containerUtil,
			AccountDao accountDao, PropertyDao propertyDao, UploadFileDao uploadFileDao, MessageSearchRepository messageSearchProxy,
			MessageInitFactory messageFactory, Constants constants) {
		super(jdbcTempSource, messageFactory, constants);
		this.pageIteratorSolver = messagePageIteratorSolver.getPageIteratorSolver();
		this.messageSearchProxy = messageSearchProxy;
	}

	/**
	 * active the cache
	 */
	@Around()
	public ForumMessage getMessageCore(ModelKey modelKey) {
		ForumMessage message = super.getMessageCore((Long) modelKey.getDataKey());
		return message;
	}

	@Around()
	public ForumThread getThreadCore(Long threadId) {
		return super.getThreadCore(threadId);
	}

	public void createMessage(ForumMessage forumMessage) throws Exception {
		super.createMessage(forumMessage);// db
		messageSearchProxy.createMessage(forumMessage);//

		// refresh the batch inquiry cache
		pageIteratorSolver.clearCache();
	}

	public void createMessageReply(ForumMessageReply forumMessageReply) throws Exception {
		super.createMessageReply(forumMessageReply);
		// AddReplyMessageSearchFile do it
		// messageSearchProxy.createMessageReply(forumMessageReply);

		// refresh the batch inquiry cache
		pageIteratorSolver.clearCache();
	}

	public void updateMessage(ForumMessage forumMessage) throws Exception {
		super.updateMessage(forumMessage);
		// SaveMessageSearch do it
		// messageSearchProxy.updateMessage(forumMessage);

		// refresh the batch inquiry cache
		pageIteratorSolver.clearCache();
	}

	/**
	 * if this deleted message is the last message, we must refresh the forum
	 * state.
	 */
	public void deleteMessage(Long forumMessageId) throws Exception {
		// second delete the message from database
		super.deleteMessage(forumMessageId);
		messageSearchProxy.deleteMessage(forumMessageId);

		pageIteratorSolver.clearCache();
	}

	public void updateThread(ForumThread forumThread) throws Exception {
		super.updateThread(forumThread);
		// refresh the batch inquiry cache
		pageIteratorSolver.clearCache();
	}

	/**
	 * deleteThread always combined with deleteMessage.
	 */
	public void deleteThread(Long forumThreadId) throws Exception {
		super.deleteThread(forumThreadId);
		// refresh the batch inquiry cache
		pageIteratorSolver.clearCache();
	}

}
