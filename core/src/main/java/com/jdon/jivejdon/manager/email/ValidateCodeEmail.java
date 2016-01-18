package com.jdon.jivejdon.manager.email;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.util.EmailTask;
import com.jdon.util.Debug;

@Component("validateCodeEmail")
public class ValidateCodeEmail {
	private final static String module = ValidateCodeEmail.class.getName();

	private Map<String, Integer> validatecodes;

	private ValidateCodeEmailParams validateCodeEmailParams;

	private EmailHelper emailHelper;

	public ValidateCodeEmail(ValidateCodeEmailParams validateCodeEmailParams, EmailHelper emailHelper) {
		super();

		this.validateCodeEmailParams = validateCodeEmailParams;
		this.emailHelper = emailHelper;
		this.validatecodes = new HashMap(20);
	}

	public void send(Account account) {
		Debug.logVerbose("sendValidateCodeEmail  ", module);

		Random r = new Random();
		int validateCode = r.nextInt();
		if (validatecodes.size() > 100) {// no out of memeory
			validatecodes.clear();
		}
		validatecodes.put(account.getUserId(), validateCode);

		String body = createValidateEmailBody(account, validateCode);

		String subject = validateCodeEmailParams.getTitle();
		String toEmail = account.getEmail();
		String toName = account.getUsername();
		String fromName = validateCodeEmailParams.getFromName();
		String fromEmail = validateCodeEmailParams.getFromEmail();
		EmailTask emailTask = new EmailTask(validateCodeEmailParams.getJndiname());
		emailTask.addMessage(toName, toEmail, fromName, fromEmail, subject, body, EmailTask.HTML_FORMAT);
		emailHelper.sendComposer(emailTask);
		Debug.logVerbose("email is over", module);

	}

	private String createValidateEmailBody(Account account, int validateCode) {
		StringBuffer buffer = new StringBuffer(validateCodeEmailParams.getBody());
		buffer.append("\n\n");
		String url = validateCodeEmailParams.getUrl() + "&validateCode=" + validateCode + "&account.userId=" + account.getUserId();
		buffer.append("<a href='").append(url).append("' target=_blank>").append(url).append("</a>");
		buffer.append("\n\n");
		return buffer.toString();
	}

	public boolean emailValidate(String userId, int validateCode) {
		if (validatecodes.size() > 20) // make the map immutable, prevent for
			// memory leak.
			validatecodes.clear();
		if (validatecodes.containsKey(userId)) {
			if (validatecodes.get(userId).intValue() == validateCode) {
				validatecodes.remove(userId);
				return true;
			}
		}
		return false;
	}

}
