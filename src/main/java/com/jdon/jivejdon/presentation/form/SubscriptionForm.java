package com.jdon.jivejdon.presentation.form;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.jdon.jivejdon.manager.subscription.SubscribedFactory;
import com.jdon.jivejdon.manager.subscription.SubscriptionActionHolder;
import com.jdon.jivejdon.manager.subscription.action.EmailAction;
import com.jdon.jivejdon.manager.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.presentation.form.weibo.SinaweiboForm;

public class SubscriptionForm extends BaseForm {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long subscriptionId;

	private int subscribeType;

	private Long subscribeId;

	private Subscribed subscribed;

	private String creationDate;

	private SubscriptionActionHolder subscriptionActionHolder;

	private SinaweiboForm sinaweiboForm;

	public SubscriptionForm() {
		this.subscriptionActionHolder = new SubscriptionActionHolder();
		this.sinaweiboForm = new SinaweiboForm();
	}

	public Long getSubscribeId() {
		return subscribeId;
	}

	public void setSubscribeId(Long subscribeId) {
		this.subscribeId = subscribeId;
	}

	/**
	 * transfer Form to Model join fields into Subscribed;
	 * 
	 * @return
	 */

	public Subscribed getSubscribed() {
		try {
			if (subscribed != null)
				return subscribed;
			else
				return SubscribedFactory.createTransient(subscribeType, subscribeId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * transfer Model to Form separate Subscribed into fields;
	 * 
	 * @return
	 */

	public void setSubscribed(Subscribed subscribed) {
		if (subscribed == null)
			return;
		try {
			this.subscribeType = subscribed.getSubscribeType();
			this.subscribeId = subscribed.getSubscribeId();
			this.subscribed = subscribed;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Long getSubscriptionId() {
		return subscriptionId;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isSendmsg() {
		return subscriptionActionHolder.hasActionType(ShortMsgAction.class);
	}

	public void setSendmsg(boolean sendmsg) {
		if (sendmsg)
			subscriptionActionHolder.addAction(new ShortMsgAction());
		else
			subscriptionActionHolder.removeActionType(ShortMsgAction.class);
	}

	public boolean isSendemail() {
		return subscriptionActionHolder.hasActionType(EmailAction.class);
	}

	public void setSendemail(boolean sendemail) {
		if (sendemail)
			subscriptionActionHolder.addAction(new EmailAction());
		else
			subscriptionActionHolder.removeActionType(EmailAction.class);
	}

	public int getSubscribeType() {
		return subscribeType;
	}

	public void setSubscribeType(int subscribeType) {
		this.subscribeType = subscribeType;
	}

	public SubscriptionActionHolder getSubscriptionActionHolder() {
		return sinaweiboForm.pack(subscriptionActionHolder);
	}

	public void setSubscriptionActionHolder(SubscriptionActionHolder subscriptionActionHolder) {
		sinaweiboForm.unpack(subscriptionActionHolder);
		this.subscriptionActionHolder = subscriptionActionHolder;
	}

	public SinaweiboForm getSinaweiboForm() {
		return sinaweiboForm;
	}

	public void setSinaweiboForm(SinaweiboForm sinaweiboForm) {
		this.sinaweiboForm = sinaweiboForm;
	}

	public void doValidate(ActionMapping mapping, HttpServletRequest request, List errors) {
		if (!this.getAction().equals("delete")) {
			// if (subscribeType == 0) {
			// errors.add("subscribeType is required.");
			// }

			if (subscribeId == null) {
				errors.add("subscribeId is required.");
			}
		}

	}

}
