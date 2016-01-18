/*
 * Copyright 2003-2009 the original author or authors.
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
package com.jdon.jivejdon.model.message.weibo;

import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.util.UtilValidate;

public class AuthorNameFilter {
	public final static String PRE_AUTHOR = "@";

	public String getUsernameFromBody(ForumMessage message) {
		String toUsername = null;
		try {
			MessageVO messageVO = message.getMessageVO();
			if (messageVO.getBody().indexOf(PRE_AUTHOR) == -1)
				return null;

			if (messageVO.getBody().indexOf("[author]") != -1) {
				return null;
			}

			String body = messageVO.getBody();
			String buff = body.substring(body.indexOf(PRE_AUTHOR));
			String[] as = buff.split("(?i)[^a-zA-Z0-9\u4E00-\u9FA5]");
			if (as.length < 2)
				return null;
			toUsername = as[1];
			if (UtilValidate.isEmpty(toUsername))
				return null;

			messageVO.setBody(body.replaceAll(toUsername, getAuthorURL(toUsername)));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return toUsername;
	}

	protected String getAuthorURL(String toUsername) {
		return "[author]" + toUsername + "[/author]";

	}
}
