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
package com.jdon.jivejdon.presentation.action.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.FormBeanUtil;
import com.jdon.util.UtilValidate;

public class ThreadEtagFilter extends Action {
	public final static String NEWLASMESSAGE = "NEWLASMESSAGE";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// browser cache expire time;
		int expire = 2 * 60;
		if (request.getParameter("nocache") != null) { // for just modified and
			// view it
			expire = 0;
			return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
		}

		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!UtilValidate.isInteger(threadId))) {
			response.sendError(404);
			return null;
		}

		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		ForumThread forumThread = forumMessageService.getThread(new Long(threadId));
		if (forumThread == null) {
			response.sendError(404);
			return null;
		}

		long modelLastModifiedDate = forumThread.getState().getModifiedDate2();

		if (!ToolsUtil.checkHeaderCache(expire, modelLastModifiedDate, request, response)) {
			return null;// response is 304
		}

		return actionMapping.findForward(FormBeanUtil.FORWARD_SUCCESS_NAME);
	}

}
