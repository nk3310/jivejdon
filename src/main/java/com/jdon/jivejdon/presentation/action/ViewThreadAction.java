package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeySame;
import com.jdon.jivejdon.manager.viewcount.ThreadViewCounterJob;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.UtilValidate;

public class ViewThreadAction extends Action {

	private CustomizedThrottle customizedThrottle;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String threadId = request.getParameter("thread");
		if ((threadId == null) || (!UtilValidate.isInteger(threadId))) {
			((HttpServletResponse) response).sendError(404);
			return actionMapping.findForward("error");
		}
		if (!checkSpamHit(threadId, request))
			((HttpServletResponse) response).sendError(503);

		try {
			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);

			ForumThread thread = forumMessageService.getThread(new Long(threadId));

			if (thread == null) {
				((HttpServletResponse) response).sendError(404);
				return actionMapping.findForward("error");
			}

			addViewCount(thread, request);

			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().print(thread.getViewCount());
			response.getWriter().close();
		} catch (Exception e) {
			return actionMapping.findForward("error");
		}
		return null;
	}

	private void addViewCount(ForumThread forumThread, HttpServletRequest request) {
		String ip = request.getRemoteAddr();
		forumThread.addViewCount(ip);
		ThreadViewCounterJob threadViewCounterJob = (ThreadViewCounterJob) WebAppUtil.getComponentInstance("threadViewCounterJob", request);
		threadViewCounterJob.checkViewCounter(forumThread);
	}

	private boolean checkSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", request);
		}
		HitKeyIF hitKey = new HitKeySame(request.getRemoteAddr(), id);
		return customizedThrottle.processHitFilter(hitKey);
	}
}
