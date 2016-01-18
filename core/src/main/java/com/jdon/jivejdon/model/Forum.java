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
package com.jdon.jivejdon.model;

import java.util.Collection;
import java.util.Date;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableProperty;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.repository.LazyLoaderRole;
import com.jdon.jivejdon.model.state.ForumStateFactory;
import com.jdon.jivejdon.model.subscription.SubPublisherRoleIF;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Searchable
@Model
public class Forum extends ForumModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SearchableId
	private Long forumId;

	@SearchableProperty
	private String name;
	private String description;
	private String creationDate;

	/**
	 * the forum modified date, not the message modified date in the forum,
	 */
	private String modifiedDate;
	private Collection propertys;

	/**
	 * @link aggregation
	 */

	private volatile ForumState forumState;

	private HotKeys hotKeys;

	@Inject
	public SubPublisherRoleIF publisherRole;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	private ForumStateFactory forumStateManager;

	public Forum() {
		// init state
		forumState = new ForumState(this, null, 0, 0);
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return Returns the forumId.
	 */
	public Long getForumId() {
		return forumId;
	}

	/**
	 * @param forumId
	 *            The forumId to set.
	 */
	public void setForumId(Long forumId) {
		this.forumId = forumId;
	}

	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	public long getModifiedDate2() {
		if (modifiedDate == null)
			return 0;
		Date mdate = Constants.parseDateTime(modifiedDate);
		return mdate.getTime();
	}

	/**
	 * @param modifiedDate
	 *            The modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return Returns the propertys.
	 */
	public Collection getPropertys() {
		return propertys;
	}

	/**
	 * @param propertys
	 *            The propertys to set.
	 */
	public void setPropertys(Collection propertys) {
		this.propertys = propertys;
	}

	/**
	 * @return Returns the forumState.
	 */
	public ForumState getForumState() {
		try {
			return forumState;
		} finally {
		}
	}

	/**
	 * @param forumState
	 *            The forumState to set.
	 */
	public void setForumState(ForumState forumState) {
		this.forumState = forumState;
	}

	public HotKeys getHotKeys() {
		return hotKeys;
	}

	public void setHotKeys(HotKeys hotKeys) {
		this.hotKeys = hotKeys;
	}

	public void addNewMessage(ForumMessageReply forumMessageReply) {
		forumStateManager.addNewMessage(this, forumMessageReply);
		this.publisherRole.subscriptionNotify(new ForumSubscribed(this));
	}

	public void updateNewMessage(ForumMessage forumMessage) {
		forumStateManager.updateMessage(this, forumMessage);
	}

	public void addNewThread(ForumMessage topicForumMessage) {
		forumStateManager.addNewThread(this, topicForumMessage);
		this.publisherRole.subscriptionNotify(new ForumSubscribed(this));
	}

}
