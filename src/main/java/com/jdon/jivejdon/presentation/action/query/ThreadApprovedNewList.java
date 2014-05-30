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
package com.jdon.jivejdon.presentation.action.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.query.ResultSort;
import com.jdon.jivejdon.model.query.specification.ApprovedListSpec;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

/**
 * CQRS complex query
 * 
 * @author banq
 * 
 */
public class ThreadApprovedNewList {
	private final static Logger logger = Logger.getLogger(ThreadApprovedNewList.class);

	public final Map<Integer, Collection<Long>> approvedThreadList;
	// private Cache approvedThreadList = new LRUCache("approvedCache.xml");
	private ApprovedListSpec approvedListSpec;

	private boolean refresh;

	public ThreadApprovedNewList() {
		approvedThreadList = new HashMap();
		approvedListSpec = new ApprovedListSpec();

		Runnable task = new Runnable() {
			public void run() {
				init();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 0, 60 * 60 * 6, TimeUnit.SECONDS);
	}

	public void init() {
		approvedThreadList.clear();
		approvedListSpec = new ApprovedListSpec();
		ResultSort resultSort = new ResultSort();
		resultSort.setOrder_DESCENDING();
		approvedListSpec.setResultSort(resultSort);
		refresh = true;
	}

	public Collection<Long> getApprovedThreads(int start, HttpServletRequest request) {
		if (approvedThreadList.containsKey(start)) {
			return approvedThreadList.get(start);
		}
		if (start < approvedListSpec.getCurrentStartPage()) {
			logger.error("start=" + start + " < approvedListSpec.getCurrentStartPage()" + approvedListSpec.getCurrentStartPage());
			return null;
		}

		return appendList(start, approvedListSpec, request);
	}

	protected synchronized Collection<Long> appendList(int start, ApprovedListSpec approvedListSpec, HttpServletRequest request) {
		if (approvedThreadList.containsKey(start)) {
			return approvedThreadList.get(start);
		}

		this.refresh = false;
		Collection<Long> resultSorteds = null;
		logger.debug("not found it in cache, create it");
		int count = approvedListSpec.getNeedCount();
		int i = approvedListSpec.getCurrentStartPage();
		while (i < start + count) {
			resultSorteds = loadApprovedThreads(approvedListSpec, request);
			approvedThreadList.put(i, resultSorteds);
			i = i + count;
		}
		if (i > approvedListSpec.getCurrentStartPage())
			approvedListSpec.setCurrentStartPage(i);
		return resultSorteds;

	}

	public synchronized List<Long> loadApprovedThreads(ApprovedListSpec approvedListSpec, HttpServletRequest request) {
		List<Long> resultSorteds = new ArrayList(approvedListSpec.getNeedCount());
		try {
			int i = 0;
			int start = approvedListSpec.getCurrentStartBlock();
			int count = 100;
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
			AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
			while (i < approvedListSpec.getNeedCount()) {
				PageIterator pi = forumMessageQueryService.getThreads(start, count, approvedListSpec);
				if (!pi.hasNext())
					break;

				while (pi.hasNext()) {
					Long threadId = (Long) pi.next();
					if (approvedListSpec.getCurrentIndicator() > threadId || approvedListSpec.getCurrentIndicator() == 0) {
						ForumThread thread = forumMessageQueryService.getThread(threadId);
						Long userId = thread.getRootMessage().getAccount().getUserIdLong();
						Account account = accountService.getAccount(userId);
						if (approvedListSpec.isApproved(thread, account) && i < approvedListSpec.getNeedCount()) {
							resultSorteds.add(thread.getThreadId());
							i++;
						}
						if (i >= approvedListSpec.getNeedCount()) {
							approvedListSpec.setCurrentIndicator(threadId);
							approvedListSpec.setCurrentStartBlock(start);
							break;
						}
					}
				}
				start = start + count;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultSorteds;
	}

	public boolean isRefresh() {
		return refresh;
	}

}
