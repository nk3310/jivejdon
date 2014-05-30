/*
 * Copyright 2003-2005 the original author or authors. Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *  
 */
package com.jdon.jivejdon.presentation.form;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jdon.util.StringUtil;

public class SkinUtils {

	private static String RegisterCODE = "CODE";

	public static String getRegisterCode(HttpServletRequest request, HttpServletResponse response) {
		String registerCode = "1234";
		try {
			HttpSession session = request.getSession();
			registerCode = StringUtil.getPassword(4, "0123456789");
			session.setAttribute(RegisterCODE, registerCode);
		} catch (Exception ex) {
			System.err.println(" getRegisterCode error : " + registerCode + ": " + ex);
		}
		return registerCode;
	}

	public static boolean verifyRegisterCode(String registerCodeIn, HttpServletRequest request) {
		boolean isTrue = false;
		String registerCode = "1234";
		try {
			HttpSession session = request.getSession();
			registerCode = (String) session.getAttribute(RegisterCODE);
			if ((registerCode != null) && (registerCodeIn != null) && (registerCodeIn.equalsIgnoreCase(registerCode)))
				isTrue = true;
		} catch (Exception ex) {
			System.err.println(" verifyRegisterCode : " + ex);
		}

		return isTrue;
	}

}
