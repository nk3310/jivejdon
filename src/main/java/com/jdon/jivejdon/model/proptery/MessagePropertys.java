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
package com.jdon.jivejdon.model.proptery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.Property;

public class MessagePropertys {
	public final static String PROPERTY_MASKED = "MASKED";

	public final static String PROPERTY_IP = "IP";

	public final static String DIG_NUMBER = "digNumber";

	private ForumMessage forumMessage;

	private Collection propertys;

	private boolean reload;

	public MessagePropertys(ForumMessage message) {
		this.forumMessage = message;
		this.propertys = new ArrayList();
	}

	public MessagePropertys() {
		this.propertys = new ArrayList();
	}

	public void importPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	public void updatePropertys(Collection propertys) {
		if (propertys != null && forumMessage != null) {
			this.propertys = propertys;
			this.forumMessage.repositoryRole.updateMessageProperties(forumMessage);
		}
	}

	public void notifyFilter() {
		this.reload = true;
	}

	public Collection exportPropertys() {
		return this.propertys;
	}

	public Collection getPropertys() {
		if (reload && forumMessage != null) {
			DomainMessage message = this.forumMessage.lazyLoaderRole.loadMessageProperties(forumMessage);
			this.propertys = (Collection) message.getEventResult();
			reload = false;
		}
		return propertys;
	}

	public void addProperty(String propName, String propValue) {
		if (getPropertyValue(propName) != null)
			return;
		Property property = new Property();
		property.setName(propName);
		property.setValue(propValue);
		getPropertys().add(property);
	}

	public String getPropertyValue(String propName) {
		Iterator iter = getPropertys().iterator();
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				return property.getValue();
			}
		}
		return null;
	}

	public void removeProperty(String propName) {
		Iterator iter = getPropertys().iterator();
		Property removePorp = null;
		while (iter.hasNext()) {
			Property property = (Property) iter.next();
			if (property.getName().equalsIgnoreCase(propName)) {
				removePorp = property;
				break;
			}
		}
		getPropertys().remove(removePorp);
	}

	public ForumMessage getForumMessage() {
		return forumMessage;
	}

	public void setForumMessage(ForumMessage forumMessage) {
		this.forumMessage = forumMessage;
	}

}
