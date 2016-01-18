package com.jdon.jivejdon.presentation.action.query;

import javax.servlet.http.HttpServletRequest;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.presentation.action.util.ForumEtagFilterList;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.Debug;

/**
 * difference with ThreadHotAction ThreadPopularAction is simple, only for one
 * page , no multi pages. ThreadPopularAction is no messageReplyCountWindow,
 * donot need sorted by message replies call from /query/popularlist.shtml only
 * in fourmList.jsp
 * 
 * ThreadPopularAction is like ThreadHotAction, but another way in SQL query.
 * 
 * @author banq
 * 
 */
public class ThreadPopularAction extends ForumEtagFilterList {

	private final static String module = ThreadPopularAction.class.getName();

	public PageIterator getPageIterator(HttpServletRequest request, int start, int count) {

		String dateRange = "1";
		if (request.getParameter("dateRange") != null)
			dateRange = request.getParameter("dateRange");
		ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
		Debug.logVerbose("ThreadPopularAction dateRange=" + dateRange + " count=" + count, module);
		return forumMessageQueryService.popularThreads(Integer.parseInt(dateRange), count);
	}

	public Object findModelIFByKey(HttpServletRequest request, Object key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		ForumThread thread = null;
		try {
			thread = forumMessageService.getThread((Long) key);
		} catch (Exception e) {
			Debug.logError("getThread error:" + e, module);
		}
		return thread;
	}

}
