/*
 * Copyright 2003-2006 the original author or authors.
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

import com.jdon.annotation.model.Inject;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.message.upload.UploadHelper;
import com.jdon.jivejdon.model.message.upload.UploadLazyLoader;

/**
 * @author banq(http://www.jdon.com)
 * 
 */

public class UploadFile extends com.jdon.strutsutil.file.UploadFile {
	private static final long serialVersionUID = 1L;

	private UploadHelper uploadHelper;

	@Inject
	private UploadLazyLoader domainEvents;

	private DomainMessage imgDataAsyncResult;

	// private UploadCacheMonitored ucm;

	public UploadFile() {
		this.uploadHelper = new UploadHelper();
		// this.ucm = new UploadCacheMonitored();
	}

	public void addMonitor(ForumMessage message) {
		// this.ucm.addObserver(message);
	}

	public boolean isImage() {
		return uploadHelper.isImage(this.getName());

	}

	public byte[] getData(String oid) {
		if (!isValid(Integer.parseInt(oid)))
			return null;
		return getData();
	}

	public byte[] getData() {
		byte[] bytes = super.getData();
		if (bytes != null)
			return bytes;
		preloadData();
		bytes = (byte[]) imgDataAsyncResult.getEventResult();
		this.setData(bytes);
		return bytes;
	}

	public void preloadData() {
		if (imgDataAsyncResult == null && domainEvents != null)
			imgDataAsyncResult = this.domainEvents.loadUploadEntity(this.getId());
	}

	public boolean isValid(int oid) {
		if (oid == Integer.parseInt(this.getParentId())) {
			return true;
		}
		return false;
	}

	// // parent id
	public int getOid() {
		try {
			return Integer.parseInt(this.getParentId());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
