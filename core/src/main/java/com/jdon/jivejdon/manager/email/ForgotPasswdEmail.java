package com.jdon.jivejdon.manager.email;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.util.EmailTask;
import com.jdon.util.Debug;

@Component("forgotPasswdEmail")
public class ForgotPasswdEmail {

	private final static String module = ForgotPasswdEmail.class.getName();

	private ForgotPasswdEmailParams forgotPasswdEmailParams;
	private EmailHelper emailHelper;

	public ForgotPasswdEmail(ForgotPasswdEmailParams forgotPasswdEmailParams, EmailHelper emailHelper) {
		super();
		this.forgotPasswdEmailParams = forgotPasswdEmailParams;
		this.emailHelper = emailHelper;
	}

	public void send(Account account, String newpasswd) {
		Debug.logVerbose("send email ", module);
		String subject = forgotPasswdEmailParams.getSubject();
		String body = createForgotPasswdEmailBody(account, newpasswd);
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = forgotPasswdEmailParams.getFromName();
		String fromEmail = forgotPasswdEmailParams.getFromEmail();

		EmailTask emailTask = new EmailTask(forgotPasswdEmailParams.getJndiname());
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.NOHTML_FORMAT);
		emailHelper.sendComposer(emailTask);
		Debug.logVerbose("email is over", module);

	}

	private String createForgotPasswdEmailBody(Account account, String newpasswd) {
		StringBuffer buffer = new StringBuffer(forgotPasswdEmailParams.getHeader());
		buffer.append("\n\n").append("username:").append(account.getUsername());
		buffer.append("\n");
		buffer.append("password:").append(newpasswd);
		buffer.append("\n\n");
		buffer.append(forgotPasswdEmailParams.getFooter());
		return buffer.toString();
	}

}
