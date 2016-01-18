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
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.proptery.ThreadPropertys;
import com.jdon.jivejdon.presentation.form.ThreadListForm;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.PropertyService;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.jivejdon.util.ToolsUtil;

public class StickyThreadList extends Action {

	private Collection<ForumThread> stickyThreadList = new ArrayList();

	public StickyThreadList() {
		Runnable task = new Runnable() {
			public void run() {
				stickyThreadList.clear();
			}
		};
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(task, 0, 60 * 60 * 6, TimeUnit.SECONDS);
	}

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ThreadListForm threadListForm = (ThreadListForm) form;

		Collection list = getStickyThreadList(request);
		threadListForm.setList(list);

		ForumThread newThread = (ForumThread) list.toArray()[0];
		int expire = 10 * 60;
		long modelLastModifiedDate = newThread.getState().getLastPost().getModifiedDate2();
		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;// no modified;
		}
		return mapping.findForward("success");

	}

	public Collection getStickyThreadList(HttpServletRequest request) {
		if (!stickyThreadList.isEmpty()) {
			return stickyThreadList;
		}

		Collection<ForumThread> results = new ArrayList();
		PropertyService propertyService = (PropertyService) WebAppUtil.getService("propertyService", request);
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		try {
			PageIterator stickyids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.STICKY_ALL);

			while (stickyids.hasNext()) {
				Long id = (Long) stickyids.next();
				ForumThread thread = forumMessageService.getThread(id);
				results.add(thread);
			}
			stickyThreadList = results;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return results;
	}

}
