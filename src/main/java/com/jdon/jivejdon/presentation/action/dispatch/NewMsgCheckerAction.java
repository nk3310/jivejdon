package com.jdon.jivejdon.presentation.action.dispatch;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.model.realtime.Lobby;
import com.jdon.jivejdon.model.realtime.Notification;
import com.jdon.jivejdon.service.AccountService;

public class NewMsgCheckerAction extends Action {
	private final static Logger logger = Logger.getLogger(NewMsgCheckerAction.class);

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.debug("enter checkReceiveShortMessages");
		AccountService accountService = (AccountService) WebAppUtil.getService("accountService", request);
		com.jdon.jivejdon.model.Account account = accountService.getloginAccount();
		String username = "anonymous";
		int count = 0;
		if (account != null) {
			count = account.getNewShortMessageCount();
			username = account.getUsername();
		}
		if (count != 0) {
			request.setAttribute("NEWMESSAGES", count);
			return mapping.findForward("checkshortmsg");
		}
		Lobby lobby = (Lobby) WebAppUtil.getComponentInstance("lobby", request);
		Notification news = lobby.checkNotification(username);
		request.setAttribute("Notification", news);
		return mapping.findForward("checklobbymsg");

	}

}
