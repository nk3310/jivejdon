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
import java.util.Iterator;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableComponent;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableReference;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.attachment.Attachment;
import com.jdon.jivejdon.model.message.MessageDigVo;
import com.jdon.jivejdon.model.message.MessageRenderSpecification;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.proptery.MessagePropertys;
import com.jdon.jivejdon.model.repository.LazyLoaderRole;
import com.jdon.jivejdon.model.repository.RepositoryRole;
import com.jdon.jivejdon.model.shortmessage.ShortMPublisherRole;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.Debug;

/**
 * 
 * ForumMessage is a message in Forum.
 * 
 * ForumMessageDecorator is its child class.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
@Searchable
public class ForumMessage extends ForumModel implements Cloneable {
	private static final long serialVersionUID = 1L;

	private final static String module = ForumMessage.class.getName();

	@SearchableId
	private Long messageId;

	private String creationDate;

	private String modifiedDate;

	private Account account; // owner

	private Account operator; // operator this message,maybe Admin or others;

	private volatile ForumThread forumThread;

	@SearchableReference
	private Forum forum;

	@SearchableComponent
	private MessageVO messageVO;

	private Collection outFilters;

	// for upload files lazyload
	private Attachment attachment;

	private MessagePropertys messagePropertys;

	private MessageDigVo messageDigVo;

	private boolean replyNotify;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public RepositoryRole repositoryRole;

	@Inject
	public ShortMPublisherRole shortMPublisherRole;

	public ForumMessage() {
		this.messageVO = new MessageVO();
		this.attachment = new Attachment(this);
		this.messagePropertys = new MessagePropertys(this);
		this.messageDigVo = new MessageDigVo(this);

	}

	/**
	 * @return Returns the account.
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account
	 *            The account to set.
	 */
	public void setAccount(com.jdon.jivejdon.model.Account account) {
		this.account = account;
	}

	public Account getOperator() {
		return operator;
	}

	public void setOperator(Account operator) {
		this.operator = operator;
	}

	public boolean isLeaf() {
		return this.forumThread.isLeaf(this);
	}

	public boolean isRoot() {
		return this.forumThread.isRoot(this);
	}

	public void applyFilters() {
		try {
			if (outFilters == null)
				return;
			if (this.messageVO.isFiltered())
				return;
			Iterator iter = outFilters.iterator();
			while (iter.hasNext()) {
				MessageRenderSpecification mrs = ((MessageRenderSpecification) iter.next());
				mrs.render(this);
			}
			messageVO.setFiltered(true);
		} catch (Exception e) {
			Debug.logError(" applyFilters error:" + e + getMessageId(), module);
		}
	}

	public MessageVO getMessageVO() {
		return messageVO;
	}

	public MessageVO getMessageVOClone() throws Exception {
		return (MessageVO) this.messageVO.clone();
	}

	public void reloadMessageVOOrignal() {
		DomainMessage em = lazyLoaderRole.reloadMessageVO(this);
		messageVO = (MessageVO) em.getEventResult();
		setMessageVO(messageVO);
	}

	public void setMessageVO(MessageVO messageVO) {
		this.messageVO = messageVO;
	}

	public void addReplyMessage(ForumMessageReply forumMessageReply) {
		try {
			// basic construct
			forumMessageReply.setParentMessage(this);
			forumMessageReply.setForumThread(this.getForumThread());
			forumMessageReply.setForum(this.getForum());
			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = Constants.getDefaultDateTimeDisp(saveDateTime);
			forumMessageReply.setCreationDate(displayDateTime);
			forumMessageReply.setModifiedDate(displayDateTime);
			// basic construt over Message has setted in Account
			// this.lazyLoaderRole.loadAccount(forumMessageReply);
			repositoryRole.addReplyMessage(forumMessageReply);

			forumThread.addNewMessage(this, forumMessageReply);

			forumMessageReply.setOutFilters(this.getOutFilters());
			forumMessageReply.applyFilters();

			// messagecount + 1
			forumMessageReply.getAccount().updateMessageCount(1);

		} catch (Exception e) {
			Debug.logError(" addReplyMessage error:" + e + this.messageId, module);
		}
	}

	/**
	 * 
	 * 
	 * @param newForumMessageInputparamter
	 */
	public void update(ForumMessage newForumMessageInputparamter) {
		try {
			updateMessage(newForumMessageInputparamter);

			ForumThread forumThread = this.getForumThread();
			forumThread.put(this);

			repositoryRole.saveMessage(this);// save this message
			// to db
			this.applyFilters();
		} catch (Exception e) {
			Debug.logError(" updateMessage error:" + e + this.messageId, module);
		}
	}

	private void updateMessage(ForumMessage newForumMessageInputparamter) throws Exception {
		setMessageVO(newForumMessageInputparamter.getMessageVO());
		this.messagePropertys.updatePropertys(newForumMessageInputparamter.messagePropertys.exportPropertys());
		setOperator(newForumMessageInputparamter.getOperator());

		long now = System.currentTimeMillis();
		String saveDateTime = ToolsUtil.dateToMillis(now);
		String displayDateTime = Constants.getDefaultDateTimeDisp(saveDateTime);
		setModifiedDate(displayDateTime);

		Collection uploads = newForumMessageInputparamter.getAttachment().exportUploadFiles();
		if (uploads != null) {
			this.getAttachment().updateUploadFiles(uploads);
		}
	}

	public void moveForum(ForumMessage newForumMessageInputparamter) {
		Long newforumId = newForumMessageInputparamter.getForum().getForumId();
		if (newforumId != null && newforumId.intValue() != 0) {
			Long oldforumId = getForum().getForumId();
			if (oldforumId.longValue() != newforumId.longValue()) {
				Forum newForum = newForumMessageInputparamter.getForum();
				forumThread.moveForum(this, newForum);
			}
		}
	}

	public void updateMasked(boolean masked) {
		setMasked(masked);// modify full message

		this.getForumThread().put(this);

		repositoryRole.updateMessageProperties(this);

		this.reloadMessageVOOrignal();
		this.applyFilters();
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public void addPostip(String ip) {
		this.messagePropertys.addProperty(MessagePropertys.PROPERTY_IP, ip);

	}

	public String getPostip() {
		String ipaddress = "";

		String saveipaddress = this.messagePropertys.getPropertyValue(MessagePropertys.PROPERTY_IP);
		if (saveipaddress != null)
			ipaddress = saveipaddress;
		return ipaddress;
	}

	/**
	 * this mesages is masked by admin
	 * 
	 * @param forumMessage
	 *            TODO
	 * @return boolean
	 */
	public boolean isMasked() {
		if (this.messagePropertys.getPropertyValue(MessagePropertys.PROPERTY_MASKED) != null)
			return true;
		else
			return false;
	}

	public void setMasked(boolean masked) {
		if (masked) {
			this.messagePropertys.addProperty(MessagePropertys.PROPERTY_MASKED, MessagePropertys.PROPERTY_MASKED);
		} else {
			this.messagePropertys.removeProperty(MessagePropertys.PROPERTY_MASKED);
		}
	}

	public int getDigCount() {
		return messageDigVo.getDigCount();
	}

	public Collection exportPropertys() {
		return this.messagePropertys.exportPropertys();
	}

	public void importPropertys(Collection propertys) {
		this.messagePropertys.importPropertys(propertys);
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
	 * @return Returns the messageId.
	 */
	public Long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            The messageId to set.
	 */
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
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
	 * @return Returns the forumThread.
	 */
	public ForumThread getForumThread() {
		return forumThread;
	}

	/**
	 * @param forumThread
	 *            The forumThread to set.
	 */
	public void setForumThread(ForumThread forumThread) {
		this.forumThread = forumThread;
	}

	/**
	 * @return Returns the forum.
	 */
	public Forum getForum() {
		return forum;
	}

	/**
	 * @param forum
	 *            The forum to set.
	 */
	public void setForum(Forum forum) {
		this.forum = forum;
	}

	public void setOutFilters(Collection outFilters) {
		this.outFilters = outFilters;
	}

	public Collection getOutFilters() {
		return outFilters;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public MessageDigVo getMessageDigVo() {
		return messageDigVo;
	}

	public void setMessageDigVo(MessageDigVo messageDigVo) {
		this.messageDigVo = messageDigVo;
	}

	public void preloadAllLazyDatas() {
		getAttachment().preloadUploadFileDatas();// preload img
		getMessageDigVo().preloadDigCount();
	}

	public void messaegDigAction() {
		messageDigVo.increment();
	}

	public void setReplyNotify(boolean replyNotify) {
		this.replyNotify = replyNotify;
	}

	public boolean isReplyNotify() {
		return replyNotify;
	}

}
