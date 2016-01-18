/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.action.query;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.UtilValidate;

public class SearchThreadAction extends ModelListAction {

	private final static Logger logger = Logger.getLogger(ThreadQueryAction.class);

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		String query = request.getParameter("query");
		if ((query == null) || (UtilValidate.isEmpty(query))) {
			logger.error(" getPageIterator error : query is null");
			return new PageIterator();
		}

		String useGBK = request.getParameter("useGBK");
		if (useGBK != null) {
			try {
				query = ToolsUtil.getParameterFromQueryString(request.getQueryString(), "query");
				query = new String((query).getBytes("ISO-8859-1"), "GBK");
				query = ToolsUtil.gbToUtf8(query);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("query", query);
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
		return forumMessageQueryService.searchThreads(query, start, count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		logger.debug(" key calss type = " + key.getClass().getName());
		return forumMessageService.getMessage((Long) key);
	}

}
