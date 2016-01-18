/*
 * Copyright 2003-2006 the original author or authors.
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

import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.manager.filter.InFilterManager;
import com.jdon.jivejdon.model.ForumMessage;

/**
 * @author banq(http://www.jdon.com)
 * 
 */
public class MessageInputeFilter {
	private final static Logger logger = Logger.getLogger(MessageInputeFilter.class);

	protected final InFilterManager inFilterManager;

	public MessageInputeFilter(InFilterManager inFilterManager) {
		this.inFilterManager = inFilterManager;
	}

	public void createTopicMessage(EventModel em) throws Exception {
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		// throttling protection against spammers
		createMessageDecorator(forumMessage);
	}

	public void createReplyMessage(EventModel em) throws Exception {
		ForumMessage forumMessage = (ForumMessage) em.getModelIF();
		createMessageDecorator(forumMessage);
	}

	private void createMessageDecorator(ForumMessage forumMessage) throws Exception {
		try {
			inFilterManager.applyFilters(forumMessage);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}
	}

	public void updateMessage(EventModel em) throws Exception {
		try {
			ForumMessage newForumMessageInputparamter = (ForumMessage) em.getModelIF();

			// apply in filter
			inFilterManager.applyFilters(newForumMessageInputparamter);
		} catch (Exception e) {
			logger.error(e);
			throw new Exception(e);
		}

	}

	public void deleteMessage(EventModel em) throws Exception {

	}

}
