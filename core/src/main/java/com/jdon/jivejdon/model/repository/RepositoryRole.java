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
package com.jdon.jivejdon.model.repository;

import org.apache.log4j.Logger;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.message.DigDataBag;
import com.jdon.jivejdon.model.message.MessageDigVo;
import com.jdon.jivejdon.model.message.MessageVO;

@Introduce("message")
public class RepositoryRole implements RepositoryRoleIF {
	private final static Logger logger = Logger.getLogger(RepositoryRole.class);

	/*
	 * MessageTransactionPersistence OnEvent
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#addTopicMessage(com
	 * .jdon.jivejdon.model.ForumMessage)
	 */
	@Override
	@Send("addTopicMessage")
	public DomainMessage addTopicMessage(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	@Send("deleteMessage")
	public DomainMessage deleteMessage(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#addReplyMessage(com
	 * .jdon.jivejdon.model.ForumMessageReply)
	 */
	@Send("addReplyMessage")
	public DomainMessage addReplyMessage(ForumMessageReply forumMessageReply) {
		ForumMessageReply newMessage = null;
		try {
			newMessage = (ForumMessageReply) forumMessageReply.clone();
			MessageVO messageVO = forumMessageReply.getMessageVOClone();
			newMessage.setMessageVO(messageVO);
		} catch (CloneNotSupportedException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return new DomainMessage(newMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#saveMessage(com.jdon
	 * .jivejdon.model.ForumMessage)
	 */
	@Send("saveMessage")
	public DomainMessage saveMessage(ForumMessage forumMessage) {
		ForumMessage newMessage = null;
		try {
			newMessage = (ForumMessage) forumMessage.clone();
			MessageVO messageVO = forumMessage.getMessageVOClone();
			newMessage.setMessageVO(messageVO);
		} catch (CloneNotSupportedException e) {
			logger.error(e);
		} catch (Exception e) {
			logger.error(e);
		}
		return new DomainMessage(newMessage);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#moveMessage(com.jdon
	 * .jivejdon.model.ForumMessage)
	 */
	@Send("moveMessage")
	public DomainMessage moveMessage(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#updateMessageProperties
	 * (com.jdon.jivejdon.model.ForumMessage)
	 */
	@Send("updateMessageProperties")
	public DomainMessage updateMessageProperties(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#saveUploadFiles(com
	 * .jdon.jivejdon.model.ForumMessage)
	 */
	@Send("saveUploadFiles")
	public DomainMessage saveUploadFiles(ForumMessage forumMessage) {
		return new DomainMessage(forumMessage);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#changeTags(com.jdon
	 * .jivejdon.model.ForumThread)
	 */
	@Send("changeTags")
	public DomainMessage changeTags(ForumThread forumThread) {
		return new DomainMessage(forumThread);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.model.repository.RepositoryRoleIF#addMessageDigCount
	 * (com.jdon.jivejdon.model.message.MessageDigVo)
	 */
	@Send("addMessageDigCount")
	public DomainMessage addMessageDigCount(MessageDigVo messageDigVo) {
		DigDataBag digDataBag = new DigDataBag(messageDigVo.getMessage().getMessageId(), messageDigVo.getNumber());
		return new DomainMessage(digDataBag);
	}
}
