package com.jdon.jivejdon.presentation.action.tag;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.service.TagService;

public class MethodDispatchAction extends DispatchAction {
	private final static Logger logger = Logger.getLogger(MethodDispatchAction.class);

	public ActionForward tags(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter tags");
		String q = request.getParameter("q");
		TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
		Collection c = othersService.tags(q);
		request.setAttribute("TAGS", c);
		return mapping.findForward("tags");
	}

	public ActionForward savetags(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter savetags");
		String[] tagTitle = request.getParameterValues("tagTitle");
		String threadId = request.getParameter("threadId");
		TagService othersService = (TagService) WebAppUtil.getService("othersService", request);
		othersService.saveTag(new Long(threadId), tagTitle);
		return mapping.findForward("savetags");
	}

}
