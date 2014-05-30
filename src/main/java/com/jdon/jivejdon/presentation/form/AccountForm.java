/*
 * Copyright 2003-2005 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.presentation.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.EmailValidator;
import org.apache.struts.action.ActionMapping;

import com.jdon.model.ModelForm;
import com.jdon.util.UtilValidate;

/**
 * @author <a href="mailto:banq@163.com">banq </a>
 * 
 */
public class AccountForm extends BaseForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String email;

	private String password;

	private String password2;

	private String username;

	private String registerCode;

	private String passwdanswer;

	private String passwdtype;

	private boolean emailVisible;

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRegisterCode() {
		return registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public String getPasswdtype() {
		return passwdtype;
	}

	public String getPasswdanswer() {
		return passwdanswer;
	}

	public void setPasswdanswer(String passwdanswer) {
		this.passwdanswer = passwdanswer;
	}

	public void setPasswdtype(String passwdtype) {
		this.passwdtype = passwdtype;
	}

	/**
	 * @return Returns the emailVisible.
	 */
	public boolean isEmailVisible() {
		return emailVisible;
	}

	/**
	 * @param emailVisible
	 *            The emailVisible to set.
	 */
	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if ((getAction() == null) || ModelForm.EDIT_STR.equals(getAction()) || ModelForm.CREATE_STR.equals(getAction())) {

			addErrorIfStringEmpty(errors, "username.required", getUsername());

			if (getPassword() != null && getPassword().length() > 0) {
				if (!getPassword().equals(getPassword2())) {
					errors.add("password.check");
				}
			}
			addErrorIfStringEmpty(errors, "email.emailcheck", getEmail());

			if (!UtilValidate.isAlphanumeric(this.getUsername())) {
				errors.add("username.alphanumeric");
			}

			if (this.getUsername() != null && this.getUsername().length() > 20) {
				errors.add("username.toolong");
			}

			if (!EmailValidator.getInstance().isValid(this.getEmail())) {
				errors.add("email.emailcheck");
			}

			if (!SkinUtils.verifyRegisterCode(registerCode, request)) {
				errors.add("registerCode.dismatch");
			}

		}

	}
}
