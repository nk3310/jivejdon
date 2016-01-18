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

import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.annotation.Service;
import com.jdon.annotation.intercept.Poolable;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.auth.NoPermissionException;
import com.jdon.jivejdon.auth.ResourceAuthorization;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.message.output.RenderingFilterManager;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.UploadService;
import com.jdon.jivejdon.service.util.SessionContextUtil;
import com.jdon.util.UtilValidate;

/**
 * ForumMessageShell is the shell of ForumMessage core implementions.
 * 
 * invoking order: ForumMessageShell --> MessageContentFilter --->
 * ForumMessageServiceImp
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Poolable
@Service("forumMessageService")
public class ForumMessageShell implements ForumMessageService {
	private final static Logger logger = Logger.getLogger(ForumMessageShell.class);

	protected SessionContext sessionContext;

	protected SessionContextUtil sessionContextUtil;

	protected ResourceAuthorization resourceAuthorization;

	protected MessageInputeFilter messageInputeFilter;

	private RenderingFilterManager renderingFilterManager;

	protected MessageKernel messageKernel;

	protected UploadService uploadService;

	protected ForumFactory forumBuilder;

	public ForumMessageShell(SessionContextUtil sessionContextUtil, ResourceAuthorization messageServiceAuth,
			MessageInputeFilter messageInputeFilter, MessageKernel messageKernel, UploadService uploadService, ForumFactory forumBuilder,
			RenderingFilterManager renderingFilterManager) {
		this.sessionContextUtil = sessionContextUtil;
		this.resourceAuthorization = messageServiceAuth;
		this.messageInputeFilter = messageInputeFilter;
		this.renderingFilterManager = renderingFilterManager;
		this.messageKernel = messageKernel;
		this.uploadService = uploadService;
		this.forumBuilder = forumBuilder;
	}

	/**
	 * must be often checked, every request, if use login.
	 * 
	 * @param forumMessage
	 */
	public boolean checkIsAuthenticated(ForumMessage forumMessage) {
		boolean allowEdit = false;
		if (!forumMessage.isEmbedded())
			return allowEdit;// forumMessage is null or only has mesageId
		com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
		allowEdit = resourceAuthorization.isAuthenticated(forumMessage, account);
		return allowEdit;
	}

	/**
	 * create Topic Message
	 */
	public void createTopicMessage(EventModel em) throws Exception {
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		if (!prepareCreate(forumMessage))
			return;
		try {

			Long mIDInt = forumBuilder.getNextId(Constants.MESSAGE);
			forumMessage.setMessageId(mIDInt);

			// upload
			Collection uploads = uploadService.getAllUploadFiles(sessionContext);
			forumMessage.getAttachment().importUploadFiles(uploads);

			messageInputeFilter.createTopicMessage(em);
			if (!UtilValidate.isEmpty(forumMessage.getMessageVO().getBody())) {
				messageKernel.addTopicMessage(em);
			}

		} catch (Exception e) {
			logger.error(e);
			em.setErrors(Constants.ERRORS);
		} finally {
			uploadService.clearSession(sessionContext);
		}
	}

	/**
	 * set the login account into the domain model
	 */
	public void createReplyMessage(EventModel em) throws Exception {
		ForumMessageReply forumMessageReply = (ForumMessageReply) em.getModelIF();
		if (UtilValidate.isEmpty(forumMessageReply.getMessageVO().getBody()))
			return;
		if (!prepareCreate(forumMessageReply))
			return;
		try {
			Long mIDInt = this.forumBuilder.getNextId(Constants.MESSAGE);
			forumMessageReply.setMessageId(mIDInt);

			Collection uploads = uploadService.getAllUploadFiles(sessionContext);
			forumMessageReply.getAttachment().importUploadFiles(uploads);

			messageInputeFilter.createReplyMessage(em);

			messageKernel.addReplyMessage(em);

		} catch (Exception e) {
			logger.error(e);
			em.setErrors(Constants.ERRORS);
		} finally {
			uploadService.clearSession(sessionContext);
		}
	}

	private boolean prepareCreate(ForumMessage forumMessage) throws Exception {
		// the poster
		com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
		if (account == null)
			return false;
		forumMessage.setAccount(account);
		return true;
	}

	private boolean isAuthenticated(ForumMessage forumMessage) {
		try {
			com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
			resourceAuthorization.verifyAuthenticated(forumMessage, account);
			return true;
		} catch (NoPermissionException e) {
			logger.error("No Permission to operate it mesageId=" + forumMessage.getMessageId());
		}
		return false;
	}

	/**
	 * 1. auth check: amdin and the owner can modify this nessage. 2. if the
	 * message has childern, only admin can update it. before business logic, we
	 * must get a true message from persistence layer, now the ForumMessage
	 * packed in EventModel object is not full, it is a DTO from prensentation
	 * layer.
	 * 
	 * 
	 */
	public void updateMessage(EventModel em) throws Exception {
		ForumMessage newForumMessageInputparamter = (ForumMessage) em.getModelIF();
		try {
			ForumMessage oldforumMessage = messageKernel.getMessage(newForumMessageInputparamter.getMessageId());
			if (oldforumMessage == null)
				return;
			if (!isAuthenticated(oldforumMessage)) {
				em.setErrors(Constants.NOPERMISSIONS);
				return;
			}
			newForumMessageInputparamter.setOperator(sessionContextUtil.getLoginAccount(sessionContext));

			Collection uploads = uploadService.getAllUploadFiles(this.sessionContext);
			newForumMessageInputparamter.getAttachment().importUploadFiles(uploads);

			// applyFilters is called by createMessage
			// and updateMessage, set the flg g is modified, so inFilterManager
			// can know,
			messageInputeFilter.updateMessage(em);// newForumMessageInputparamter
			// in em, not forumMessage
			// update message in persistence such as database
			messageKernel.updateMessage(em);

		} catch (Exception e) {
			logger.error(e);
			em.setErrors(Constants.ERRORS);
		} finally {
			uploadService.clearSession(sessionContext);
		}
	}

	/**
	 * delete a message the auth is same as to the updateMessage
	 * 
	 * 
	 */
	public void deleteMessage(EventModel em) throws Exception {
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		forumMessage = messageKernel.getMessage(forumMessage.getMessageId());
		if (forumMessage == null)
			return;
		if (!isAuthenticated(forumMessage)) {
			em.setErrors(Constants.NOPERMISSIONS);
			return;
		}
		em.setModelIF(forumMessage);
		try {
			messageInputeFilter.deleteMessage(em);
			messageKernel.deleteMessage(forumMessage);

		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
			logger.error(e);
		}
	}

	public void deleteUserMessages(String username) throws Exception {
		try {
			com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
			if (resourceAuthorization.isAdmin(account))
				messageKernel.deleteUserMessages(username);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);

		}
	}

	/**
	 * only admin or moderator can mask a message
	 */
	public void maskMessage(EventModel em) throws Exception {
		logger.debug("enter maskMessage");
		com.jdon.jivejdon.model.Account account = sessionContextUtil.getLoginAccount(sessionContext);
		if (!resourceAuthorization.isAdmin(account))
			return;

		ForumMessage forumMessageParamter = (ForumMessage) em.getModelIF();
		// masked value is from messageForm and from
		// /message/messageMaskAction.shtml?method=maskMessage&masked=false
		boolean masked;
		try {
			masked = forumMessageParamter.isMasked();
			ForumMessage forumMessage = messageKernel.getMessage(forumMessageParamter.getMessageId());
			if (forumMessage == null) {
				logger.error("the message don't existed!");
				return;
			}
			this.messageKernel.updateMask(forumMessage, masked);
		} catch (Exception e) {
			logger.error(e);
		}

	}

	/**
	 * get the full forum in forumMessage, and return it.
	 */
	public ForumMessage initMessage(EventModel em) {
		return messageKernel.initMessage(em);
	}

	public ForumMessage initReplyMessage(EventModel em) {
		return messageKernel.initReplyMessage(em);
	}

	/*
	 * return message with body filter to client; return a full ForumMessage
	 * need solve the relations with Forum ForumThread
	 */
	public ForumMessage getMessage(Long messageId) {
		if (messageId == null)
			return null;
		return messageKernel.getMessage(messageId);
	}

	/**
	 * find a Message for modify
	 */
	public ForumMessage findMessage(Long messageId) {
		if (messageId == null)
			return null;

		logger.debug("enter ForumMessageShell's findMessage");
		ForumMessage forumMessage = messageKernel.getMessage(messageId);
		if (forumMessage == null)
			return null;
		try {
			ForumMessage newMessage = (ForumMessage) forumMessage.clone();
			MessageVO messageVO = forumMessage.getMessageVOClone();
			newMessage.setMessageVO(messageVO);
			newMessage.reloadMessageVOOrignal();
			// newMessage.setCacheable(false);
			// after update must enable it see updateMessage;
			return newMessage;
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	}

	public ForumMessage findMessageWithPropterty(Long messageId) {
		return messageKernel.getMessageWithPropterty(messageId);
	}

	/**
	 * return a full ForumThread one ForumThread has one rootMessage need solve
	 * the realtion with Forum rootForumMessage lastPost
	 * 
	 * @param threadId
	 * @return
	 */
	public ForumThread getThread(Long threadId) {
		try {
			return messageKernel.getThread(threadId);
		} catch (Exception e) {
			return null;
		}
	}

	public RenderingFilterManager getFilterManager() {
		return this.renderingFilterManager;
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
