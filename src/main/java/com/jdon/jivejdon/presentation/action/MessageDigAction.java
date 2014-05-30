package com.jdon.jivejdon.presentation.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.service.ForumMessageService;

/**
 * response the ajax request for getting dig count
 * @author oojdon
 *
 */
public class MessageDigAction extends Action {
	
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response)
	throws Exception {

			ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);			
			String messageId = request.getParameter("messageId");						
			Long key = Long.valueOf(messageId);			
			ForumMessage message = forumMessageService.getMessage(key);					
			message.messaegDigAction();				
			response.getWriter().print(message.getDigCount());			
			response.getWriter().close();
			return null;
	}
	


}
