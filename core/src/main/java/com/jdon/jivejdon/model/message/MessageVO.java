package com.jdon.jivejdon.model.message;

import java.io.Serializable;

import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableProperty;

import com.jdon.util.StringUtil;

/**
 * ForumMessage's value object
 * 
 * @author gateway
 * 
 */
@Searchable(root = false)
public class MessageVO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@SearchableProperty
	protected String subject;

	@SearchableProperty
	protected String body;

	@SearchableProperty
	private String[] tagTitle;

	private int rewardPoints;

	private boolean isFiltered;

	public MessageVO() {
	}

	/**
	 * @return Returns the subject.
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return Returns the body.
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 *            The body to set.
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * @param subject
	 *            The subject to set.
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return Returns the rewardPoints.
	 */
	public int getRewardPoints() {
		return rewardPoints;
	}

	/**
	 * @param rewardPoints
	 *            The rewardPoints to set.
	 */
	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}

	public String[] getTagTitle() {
		return tagTitle;
	}

	public void setTagTitle(String[] tagTitle) {
		this.tagTitle = tagTitle;
	}

	public String getShortBody(int length) {
		if (getBody() != null)
			return shortenNoHTML(getBody(), length);
		else
			return "";
	}

	public String shortenNoHTML(String in, int length) {
		String s = in.replaceAll("\\<.*?\\>", " ");
		return StringUtil.shorten(s, length);

	}

	public boolean isFiltered() {
		return isFiltered;
	}

	public void setFiltered(boolean isFiltered) {
		this.isFiltered = isFiltered;
	}

	/**
	 * add property to propertys;
	 * 
	 * @param propName
	 * @param propValue
	 */

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
