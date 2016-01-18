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
package com.jdon.jivejdon.repository.dao.filter;

import java.util.Collection;

import com.jdon.jivejdon.model.util.CachedCollection;
import com.jdon.jivejdon.repository.dao.AccountDao;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.repository.dao.sql.MessageQueryDaoSql;
import com.jdon.jivejdon.repository.dao.util.MessagePageIteratorSolver;
import com.jdon.jivejdon.repository.search.MessageSearchRepository;
import com.jdon.jivejdon.util.ContainerUtil;

/**
 * @author banq(http://www.jdon.com)
 * 
 */

public class MessageQueryDaoProxy extends MessageQueryDaoSql {

	public static final String Message_Name = "Message_Name";
	public static final String Thread_Name = "Thread_Name";

	private MessageSearchRepository messageSearchProxy;
	private ContainerUtil containerUtil;

	public MessageQueryDaoProxy(ContainerUtil containerUtil, JdbcTempSource jdbcTempSource, MessagePageIteratorSolver messagePageIteratorSolver,
			AccountDao accountDao, MessageSearchRepository messageSearchProxy) {
		super(jdbcTempSource, messagePageIteratorSolver);
		this.messageSearchProxy = messageSearchProxy;
		this.containerUtil = containerUtil;
	}

	/**
	 * 
	 * public ForumThreadState getForumThreadState(ForumThread forumThread){
	 * ForumThreadState forumThreadState = (ForumThreadState)
	 * containerUtil.getModelFromCache(forumThread.getThreadId(),
	 * ForumThreadState.class); if ((forumThreadState == null) ||
	 * (forumThreadState.isModified())){ forumThreadState =
	 * super.getForumThreadState(forumThread);
	 * containerUtil.addModeltoCache(forumThread.getThreadId(),
	 * forumThreadState); } return forumThreadState; }
	 */

	public Collection find(String query, int start, int count) {
		return messageSearchProxy.find(query, start, count);
	}

	private String getSearchKey(String name, String query, int start, int count) {
		StringBuffer sb = new StringBuffer(name);
		sb.append(query).append(start).append(count);
		return sb.toString();
	}

	public Collection findThread(String query, int start, int count) {
		String searchKey = getSearchKey(Thread_Name, query, start, count);
		CachedCollection cc = (CachedCollection) containerUtil.getModelFromCache(searchKey, CachedCollection.class);
		if (cc == null) {
			Collection list = messageSearchProxy.findThread(query, start, count);
			cc = new CachedCollection(Thread_Name, list);
			containerUtil.addModeltoCache(searchKey, cc);
		}
		return cc.getList();
	}

	public int findThreadsAllCount(String query) {
		String searchKey = getSearchKey("Allcount", query, 0, 0);
		Integer allCount = (Integer) containerUtil.getCacheManager().getCache().get(searchKey);
		if ((allCount == null) || (allCount.intValue() == 0)) {
			allCount = new Integer(messageSearchProxy.findThreadsAllCount(query));
			if (allCount.intValue() != 0) {
				containerUtil.getCacheManager().getCache().put(searchKey, allCount);
			}
		}
		return allCount.intValue();
	}

}
