package com.jdon.jivejdon.presentation.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.jivejdon.service.PropertyService;

/**
 * displays the sticky thread list
 * 
 * @author oojdon
 * 
 */
public class StickyListAction extends Action {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		PropertyService propertyService = (PropertyService) WebAppUtil.getService("propertyService", request);

		String forumId = request.getParameter("forumId");
		setStickyThreadForAllForums(propertyService, forumMessageService, request);
		setStickyThreadForThisForum(propertyService, forumMessageService, request, forumId);

		return actionMapping.findForward("success");

	}

	private void setStickyThreadForThisForum(PropertyService propertyService, ForumMessageService forumMessageService, HttpServletRequest request,
			String forumId) throws Exception {
		PageIterator stickyids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.STICKY);
		PageIterator announceids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.ANNOUNCE);

		List<ForumThread> stickyList = getThreadList(forumMessageService, stickyids);
		List<ForumThread> announceList = getThreadList(forumMessageService, announceids);

		if (forumId != null && !forumId.equals("")) {
			filterForumThread(stickyList, new Long(forumId));
			filterForumThread(announceList, new Long(forumId));
		}

		request.setAttribute("stickyList", stickyList);
		request.setAttribute("announceList", announceList);

	}

	private void setStickyThreadForAllForums(PropertyService propertyService, ForumMessageService forumMessageService, HttpServletRequest request)
			throws Exception {
		PageIterator stickyids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.STICKY_ALL);
		PageIterator announceids = propertyService.getThreadIdsByNameAndValue(ThreadPropertys.UISTATE, ThreadPropertys.ANNOUNCE_ALL);

		request.setAttribute("stickyList_all", getThreadList(forumMessageService, stickyids));
		request.setAttribute("announceList_all", getThreadList(forumMessageService, announceids));
	}

	private void filterForumThread(List<ForumThread> threads, Long forumId) {
		for (Iterator<ForumThread> iterator = threads.iterator(); iterator.hasNext();) {
			ForumThread t = iterator.next();
			long fid = t.getForum().getForumId();
			if (fid != forumId)
				iterator.remove();
		}
	}

	private List<ForumThread> getThreadList(ForumMessageService forumMessageService, PageIterator ids) throws Exception {
		List<ForumThread> list = new ArrayList<ForumThread>();
		while (ids.hasNext()) {
			Long id = (Long) ids.next();
			ForumThread thread = forumMessageService.getThread(id);
			list.add(thread);
		}
		return list;

	}

}
