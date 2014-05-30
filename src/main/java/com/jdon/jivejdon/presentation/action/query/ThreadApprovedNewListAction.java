package com.jdon.jivejdon.presentation.action.query;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.strutsutil.ModelListAction;

public class ThreadApprovedNewListAction extends ModelListAction {

	private final ThreadApprovedNewList threadApprovedNewList = new ThreadApprovedNewList();

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {
		if (start >= 150 || start % count != 0)
			return new PageIterator();
		Collection<Long> list = threadApprovedNewList.getApprovedThreads(start, request);
		if (list != null)
			return new PageIterator(150, list.toArray(new Long[0]));
		else
			return new PageIterator();
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		try {
			return forumMessageService.getThread((Long) key);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
