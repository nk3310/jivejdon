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
package com.jdon.jivejdon.model.dci.action;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.repository.ForumFactory;

@Consumer("afterdelAction")
public class AfterDelAction implements DomainEventHandler {

	protected final ForumFactory forumAbstractFactory;

	public AfterDelAction(ForumFactory forumAbstractFactory) {
		super();
		this.forumAbstractFactory = forumAbstractFactory;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		DomainMessage lastStepMessage = (DomainMessage) event.getDomainMessage().getEventSource();
		Object lastStepOk = lastStepMessage.getBlockEventResult();
		if (lastStepOk != null) {
			ForumMessage delforumMessage = (ForumMessage) lastStepOk;
			reload(delforumMessage);
		}

	}

	public void reload(ForumMessage delforumMessage) {
		try {
			// update memory
			if (!delforumMessage.getForumThread().isRoot(delforumMessage)) {// this
				// thread is be deleted only
				ForumThread forumThread = delforumMessage.getForumThread();
				this.forumAbstractFactory.reloadThreadState(forumThread);// refresh
			} else
				this.forumAbstractFactory.reloadhForumState(delforumMessage.getForum().getForumId());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
