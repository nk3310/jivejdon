package com.jdon.jivejdon.model.message;

import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;

/**
 * 
 * @author oojdon
 * 
 */
public class MessageDigVo {

	private ForumMessage message;

	private int number = -1;

	private DomainMessage digCountAsyncResult;

	public MessageDigVo(ForumMessage message) {
		super();
		this.message = message;
	}

	public int getDigCount() {
		if (number == -1) {
			preloadDigCount();
		}
		return number;
	}

	public void preloadDigCount() {
		try {
			if (digCountAsyncResult == null) {
				digCountAsyncResult = message.lazyLoaderRole.loadMessageDigCount(message.getMessageId());
				Object o = digCountAsyncResult.getEventResult();
				if (o != null)
					number = (Integer) o;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void increment() {
		if (number == -1) {
			preloadDigCount();
		}
		number++;
		message.repositoryRole.addMessageDigCount(this);
	}

	public ForumMessage getMessage() {
		return message;
	}

	public void setMessage(ForumMessage message) {
		this.message = message;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

}
