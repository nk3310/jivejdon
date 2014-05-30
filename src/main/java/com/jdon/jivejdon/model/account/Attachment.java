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
package com.jdon.jivejdon.model.account;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.attachment.UploadFile;

public class Attachment {

	private DomainMessage eventMessage;

	private Account account;

	public Attachment(Account account) {
		super();
		this.account = account;
	}

	/**
	 * * <logic:notEmpty name="forumMessage" property="account.uploadFile"> <img
	 * src=
	 * "<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="
	 * forumMessage
	 * " property="account.userId"/>&id=<bean:write name="forumMessage
	 * " property="account.uploadFile.id"/>" border='0' /> </logic:notEmpty>
	 * 
	 * 
	 * @return
	 */
	public UploadFile getUploadFile() {
		UploadFile uploadFile = null;
		try {
			if (eventMessage == null && account.uploadLazyLoader != null) {
				// logic:notEmpty name="forumMessage"
				// property="account.uploadFile"
				eventMessage = account.uploadLazyLoader.loadUploadFiles(account.getUserId());
			} else if (eventMessage != null)
				// id=<bean:write
				// name="forumMessage property="account.uploadFile.id"
				uploadFile = (UploadFile) eventMessage.getEventResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadFile;
	}

	public void updateUploadFile() {
		eventMessage = account.uploadLazyLoader.loadUploadFiles(account.getUserId());
	}

}
