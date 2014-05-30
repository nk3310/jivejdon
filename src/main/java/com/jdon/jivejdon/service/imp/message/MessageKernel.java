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
package com.jdon.jivejdon.service.imp.message;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.dci.ThreadManagerContext;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.service.ForumMessageQueryService;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Component
public class MessageKernel {
	private final static Logger logger = Logger.getLogger(MessageKernel.class);

	protected ForumMessageQueryService forumMessageQueryService;

	protected ForumFactory forumAbstractFactory;

	private final ThreadManagerContext threadManagerContext;

	public MessageKernel(ForumMessageQueryService forumMessageQueryService, ForumFactory forumAbstractFactory,
			ThreadManagerContext threadManagerContext) {
		this.forumMessageQueryService = forumMessageQueryService;
		this.forumAbstractFactory = forumAbstractFactory;
		this.threadManagerContext = threadManagerContext;
	}

	/**
	 * get the full forum in forumMessage, and return it.
	 */
	public ForumMessage initMessage(EventModel em) {
		logger.debug(" enter service: initMessage ");
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		try {
			if (forumMessage.getForum() == null) {
				logger.error(" no Forum in this ForumMessage");
				return forumMessage;
			}
			Long forumId = forumMessage.getForum().getForumId();
			logger.debug(" paremter forumId =" + forumId);
			Forum forum = forumAbstractFactory.getForum(forumId);
			forumMessage.setForum(forum);
		} catch (Exception e) {
			logger.error(e);
		}
		return forumMessage;
	}

	public ForumMessage initReplyMessage(EventModel em) {
		logger.debug(" enter service: initReplyMessage ");
		ForumMessageReply forumMessageReply = (ForumMessageReply) initMessage(em);
		try {
			Long pmessageId = forumMessageReply.getParentMessage().getMessageId();
			if (pmessageId == null) {
				logger.error(" no the parentMessage.messageId parameter");
				return null;
			}
			ForumMessage pMessage = forumAbstractFactory.getMessage(pmessageId);
			forumMessageReply.setParentMessage(pMessage);
			forumMessageReply.getMessageVO().setSubject(pMessage.getMessageVO().getSubject());
		} catch (Exception e) {
			logger.error(e);
		}
		return forumMessageReply;
	}

	/*
	 * return a full ForumMessage need solve the relations with Forum
	 * ForumThread parentMessage
	 */
	public ForumMessage getMessage(Long messageId) {
		logger.debug("enter MessageServiceImp's getMessage");
		if (threadManagerContext.isTransactionOk(messageId))
			return forumAbstractFactory.getMessage(messageId);
		else
			return null;
	}

	public ForumMessage getMessageWithPropterty(Long messageId) {
		if (threadManagerContext.isTransactionOk(messageId))
			return forumAbstractFactory.getMessageWithPropterty(messageId);
		else
			return null;
	}

	/**
	 * return a full ForumThread one ForumThread has one rootMessage need solve
	 * the realtion with Forum rootForumMessage lastPost
	 * 
	 * @param threadId
	 * @return
	 */
	public ForumThread getThread(Long threadId) throws Exception {
		logger.debug("enter getThread");
		return forumAbstractFactory.getThread(threadId);

	}

	/*
	 * create the topic message
	 */
	public void addTopicMessage(EventModel em) throws Exception {
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		threadManagerContext.create(forumMessage);
	}

	/**
	 * the relation about creating reply forumMessage only need a parameter :
	 * parent message. we can get the Forum or ForumThread from the parent
	 * message. the hypelink parameter in jsp must be a paremeter: the Id of
	 * parent message.
	 * 
	 */
	public void addReplyMessage(EventModel em) throws Exception {
		try {
			ForumMessageReply forumMessageReply = (ForumMessageReply) em.getModelIF();
			ForumMessage parentMessage = getMessage(forumMessageReply.getParentMessage().getMessageId());
			if (parentMessage == null) {
				logger.error("not this parent Message: " + forumMessageReply.getParentMessage().getMessageId());
				return;
			}
			parentMessage.addReplyMessage(forumMessageReply);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	/*
	 * update the message, update the message's subject and body we must mark
	 * the message that has been updated. there are two kinds of parameters: the
	 * primary key /new entity data in DTO ForumMessage of the method patameter
	 */
	public void updateMessage(EventModel em) throws Exception {
		logger.debug("enter updateMessage");

		ForumMessage newForumMessageInputparamter = (ForumMessage) em.getModelIF();
		try {

			ForumMessage forumMessage = this.getMessage(newForumMessageInputparamter.getMessageId());
			if (forumMessage == null)
				return;
			forumMessage.update(newForumMessageInputparamter);
			em.setModelIF(forumMessage);

			Forum newForum = this.forumAbstractFactory.getForum(newForumMessageInputparamter.getForum().getForumId());
			if (newForum == null)
				return;
			newForumMessageInputparamter.setForum(newForum);
			forumMessage.moveForum(newForumMessageInputparamter);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
		logger.debug("updateMessage ok!");
	}

	public void updateMask(ForumMessage forumMessage, boolean masked) throws Exception {
		try {
			forumMessage.updateMasked(masked);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	/*
	 * delete a message and not inlcude its childern
	 */
	public void deleteMessage(ForumMessage delforumMessage) throws Exception {
		delforumMessage = this.getMessage(delforumMessage.getMessageId());
		if (delforumMessage != null)
			this.threadManagerContext.delete(delforumMessage);

	}

	public void deleteUserMessages(String username) throws Exception {
		logger.debug("enter userMessageListDelete username=" + username);
		MultiCriteria mqc = new MultiCriteria("1970/01/01");
		mqc.setUsername(username);

		// iterate all messages
		int oneMaxSize = 100;
		PageIterator pi = forumMessageQueryService.getMessages(mqc, 0, oneMaxSize);
		int allCount = pi.getAllCount();

		int wheelCount = allCount / oneMaxSize;
		int start = 0;
		int end = 0;
		for (int i = 0; i <= wheelCount; i++) {
			end = oneMaxSize + oneMaxSize * i;
			logger.debug("start = " + start + " end = " + end);
			if (pi == null)
				pi = forumMessageQueryService.getMessages(mqc, start, end);
			messagesDelete(pi, username);
			pi = null;
			start = end;
		}
	}

	private void messagesDelete(PageIterator pi, String username) throws Exception {
		Object[] keys = pi.getKeys();
		for (int i = 0; i < keys.length; i++) {
			Long messageId = (Long) keys[i];
			logger.debug("delete messageId =" + messageId);
			ForumMessage message = getMessage(messageId);
			if (message.getAccount().getUsername().equals(username)) {
				deleteMessage(message);
			}
		}
	}

	public ForumMessageQueryService getForumMessageQueryService() {
		return forumMessageQueryService;
	}

	public void setForumMessageQueryService(ForumMessageQueryService forumMessageQueryService) {
		this.forumMessageQueryService = forumMessageQueryService;
	}

}
