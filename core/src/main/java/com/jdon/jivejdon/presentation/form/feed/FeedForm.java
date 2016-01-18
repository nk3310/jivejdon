package com.jdon.jivejdon.presentation.form.feed;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.presentation.form.BaseForm;
import com.jdon.jivejdon.presentation.form.SkinUtils;

public class FeedForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String email;
	private String subject;
	private String body;
	private String registerCode;

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {

		if ((email == null) || (email.length() < 1)) {
			errors.add("need email");
			return;
		}

		if ((subject == null) || (subject.length() < 1)) {
			errors.add("need subject");
			return;
		}

		if ((body == null) || (body.length() < 1)) {
			errors.add("need body");
			return;

		}

		if (!SkinUtils.verifyRegisterCode(registerCode, request)) {
			errors.add("registerCode  dismatch");
			return;
		}

	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest) {
	}

	public String getBody() {
		return body;
	}

	public String getSubject() {
		return subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}
}
