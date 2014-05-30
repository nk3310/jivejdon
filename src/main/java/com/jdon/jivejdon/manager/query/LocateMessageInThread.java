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
package com.jdon.jivejdon.manager.query;

import com.jdon.annotation.Component;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.repository.dao.MessageQueryDao;
import com.jdon.jivejdon.util.ContainerUtil;

@Component
public class LocateMessageInThread {

	protected ContainerUtil containerUtil;

	protected MessageQueryDao messageQueryDao;

	public LocateMessageInThread(MessageQueryDao messageQueryDao, ContainerUtil containerUtil) {
		super();
		this.containerUtil = containerUtil;
		this.messageQueryDao = messageQueryDao;
	}

	public int locateTheMessage(Long threadId, Long messageId, int count) {
		StringBuffer sb = new StringBuffer(threadId.toString());
		sb.append(messageId.toString()).append(count);
		Integer start = (Integer) containerUtil.getCacheManager().getCache().get(sb.toString());
		if (start == null) {
			start = new Integer(locateTheMessageAction(threadId, messageId, count));
			containerUtil.getCacheManager().getCache().put(sb.toString(), start);
		}
		return start.intValue();

	}

	public int locateTheMessageAction(Long threadId, Long messageId, int count) {
		int start = 0;
		PageIterator pi = messageQueryDao.getMessages(threadId, start, count);

		int allCount = pi.getAllCount();
		while (start < allCount) {// loop all
			while (pi.hasNext()) {
				Long messageIdT = (Long) pi.next();
				if (messageIdT.longValue() == messageId.longValue()) {
					return start;
				}
			}
			start = start + count;
			pi = messageQueryDao.getMessages(threadId, start, count);
		}
		return -1;
	}

}
