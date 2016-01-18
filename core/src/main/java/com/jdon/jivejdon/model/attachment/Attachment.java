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
package com.jdon.jivejdon.model.attachment;

import java.util.ArrayList;
import java.util.Collection;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;

public class Attachment {
	private ForumMessage forumMessage;

	// for upload files lazyload
	private Collection uploadFiles;

	private boolean reload;

	private DomainMessage eventMessage;

	public Attachment(ForumMessage forumMessage) {
		super();
		this.forumMessage = forumMessage;
		this.uploadFiles = new ArrayList();
	}

	public void importUploadFiles(Collection uploadFiles) {
		this.uploadFiles = uploadFiles;
	}

	public void updateUploadFiles(Collection uploadFiles) {
		this.uploadFiles = uploadFiles;
		eventMessage = this.forumMessage.repositoryRole.saveUploadFiles(forumMessage);
		this.reload = true;
	}

	public Collection exportUploadFiles() {
		return this.uploadFiles;
	}

	public Collection getUploadFiles() {
		if (reload) {
			this.uploadFiles = (Collection) eventMessage.getEventResult();
			reload = false;
		}
		return uploadFiles;
	}

	public void preloadUploadFileDatas() {
		if (uploadFiles == null || uploadFiles.size() == 0)
			return;
		for (Object o : uploadFiles) {
			UploadFile uploadFile = (UploadFile) o;
			uploadFile.preloadData();
		}
	}

	public ForumMessage getForumMessage() {
		return forumMessage;
	}

	public void setForumMessage(ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
	}

}
