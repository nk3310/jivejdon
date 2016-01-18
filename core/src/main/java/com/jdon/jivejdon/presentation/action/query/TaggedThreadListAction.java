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
package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.query.specification.TaggedThreadListSpec;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.TagService;
import com.jdon.strutsutil.ModelListAction;
import com.jdon.util.Debug;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public class TaggedThreadListAction extends ModelListAction {
	private final static String module = TaggedThreadListAction.class.getName();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.strutsutil.ModelListAction#getPageIterator(javax.servlet.http
	 *      .HttpServletRequest, int, int)
	 */
	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
		String tagID = request.getParameter("tagID");
		if (tagID == null || !UtilValidate.isInteger(tagID)) {
			return new PageIterator();
		}
		ThreadTag tag = othersService.getThreadTag(new Long(tagID));
		if (tag == null)
			return new PageIterator();
		request.setAttribute("TITLE", tag.getTitle());
		TaggedThreadListSpec taggedThreadListSpec = new TaggedThreadListSpec();
		taggedThreadListSpec.setTagID(new Long(tagID));
		return othersService.getTaggedThread(taggedThreadListSpec, start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.strutsutil.ModelListAction#findModelByKey(javax.servlet.http
	 *      .HttpServletRequest, java.lang.Object)
	 */
	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		Debug.logVerbose(" key calss type = " + key.getClass().getName(), module);
		ForumThread thread = null;
		try {
			thread = forumMessageService.getThread((Long) key);
		} catch (Exception e) {
			Debug.logError("getThread error:" + e, module);
		}
		return thread;
	}

}
