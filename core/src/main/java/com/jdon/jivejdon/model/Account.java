package com.jdon.jivejdon.model;

import java.util.Date;
import java.util.Observable;

import com.jdon.annotation.Model;
import com.jdon.annotation.model.Inject;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.account.AccountMessageVO;
import com.jdon.jivejdon.model.account.Attachment;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.model.message.upload.UploadLazyLoader;
import com.jdon.jivejdon.model.repository.LazyLoaderRole;
import com.jdon.jivejdon.model.shortmessage.AccountSMState;
import com.jdon.jivejdon.model.subscription.SubscribedState;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;

/**
 * we have a SSO server, all auth information will be save to the sso server,
 * and in this system, we keep some additional fields.
 * 
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Model
public class Account {

	private String userId;

	private String password;

	private String username;

	private String email;

	private String roleName;

	private boolean emailVisible;

	private boolean emailValidate;

	private String creationDate;

	private String modifiedDate;

	private String postIP;

	private final AccountMessageVO accountMessageVO;

	private final SubscribedState subscribedState;

	private final AccountSMState accountSMState;

	private Reward reward;

	private String passwdanswer;

	private String passwdtype;

	private boolean anonymous;

	private Attachment attachment;

	@Inject
	public LazyLoaderRole lazyLoaderRole;

	@Inject
	public UploadLazyLoader uploadLazyLoader;

	public Account() {
		subscribedState = new SubscribedState(new AccountSubscribed(this));
		accountMessageVO = new AccountMessageVO(this);
		attachment = new Attachment(this);
		accountSMState = new AccountSMState(this);
	}

	/**
	 * @return Returns the creationDate.
	 */
	public String getCreationDate() {
		try {
			if (creationDate != null && creationDate.length() != 0)
				return creationDate.substring(0, 11);
		} catch (Exception e) {
		}
		return "";
	}

	public Date getCreationDate2() {
		return Constants.parseDateTime(creationDate);
	}

	/**
	 * @param creationDate
	 *            The creationDate to set.
	 */
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return Returns the emailVisible.
	 */
	public boolean isEmailVisible() {
		return emailVisible;
	}

	/**
	 * @param emailVisible
	 *            The emailVisible to set.
	 */
	public void setEmailVisible(boolean emailVisible) {
		this.emailVisible = emailVisible;
	}

	/**
	 * @return Returns the modifiedDate.
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate
	 *            The modifiedDate to set.
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return Returns the reward.
	 */
	public Reward getReward() {
		return reward;
	}

	/**
	 * @param reward
	 *            The reward to set.
	 */
	public void setReward(Reward reward) {
		this.reward = reward;
	}

	/**
	 * @return Returns the postIP.
	 */
	public String getPostIP() {
		return postIP;
	}

	/**
	 * @param postIP
	 *            The postIP to set.
	 */
	public void setPostIP(String postIP) {
		this.postIP = postIP;
	}

	/**
	 * @return Returns the userId.
	 */
	public Long getUserIdLong() {
		if (this.getUserId() != null)
			return new Long(this.getUserId());
		else
			return null;
	}

	/**
	 * @param userId
	 *            The userId to set.
	 */
	public void setUserIdLong(Long userId) {
		if (userId != null)
			this.setUserId(userId.toString().trim());

	}

	/**
	 * @return Returns the messageCount.
	 */
	public int getMessageCount() {
		if (isAnonymous())
			return 0;
		if (lazyLoaderRole != null)
			return accountMessageVO.getMessageCount(lazyLoaderRole);
		else
			return 0;
	}

	public int getMessageCountNow() {
		if (isAnonymous())
			return 0;
		if (lazyLoaderRole != null)
			return accountMessageVO.getMessageCountNow(lazyLoaderRole);
		else
			return 0;
	}

	/**
	 * @return Returns the passwdanswer.
	 */
	public String getPasswdanswer() {
		return passwdanswer;
	}

	/**
	 * @param passwdanswer
	 *            The passwdanswer to set.
	 */
	public void setPasswdanswer(String passwdanswer) {
		this.passwdanswer = passwdanswer;
	}

	/**
	 * @return Returns the passwdtype.
	 */
	public String getPasswdtype() {
		return passwdtype;
	}

	/**
	 * @param passwdtype
	 *            The passwdtype to set.
	 */
	public void setPasswdtype(String passwdtype) {
		this.passwdtype = passwdtype;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAnonymous() {
		return anonymous;
	}

	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}

	public boolean isEmailValidate() {
		return emailValidate;
	}

	public void setEmailValidate(boolean emailValidate) {
		this.emailValidate = emailValidate;
	}

	public int getSubscriptionCount() {
		if (this.lazyLoaderRole != null)
			return this.subscribedState.getSubscriptionCount(this.lazyLoaderRole);
		else
			return -1;
	}

	public void updateSubscriptionCount(int count) {
		subscribedState.update(count);
	}

	public void updateMessageCount(int count) {
		this.accountMessageVO.update(count);
	}

	/**
	 * <logic:notEmpty name="forumMessage" property="account.uploadFile"> <img
	 * src=
	 * "<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="
	 * forumMessage
	 * " property="account.userId"/>&id=<bean:write name="forumMessage
	 * " property="account.uploadFile.id"/>" border='0' /> </logic:notEmpty>
	 * 
	 * @return
	 */
	public UploadFile getUploadFile() {
		if (attachment != null)
			return this.attachment.getUploadFile();
		else
			return null;
	}

	public void setUploadFile(boolean update) {
		if (update)
			this.attachment.updateUploadFile();
	}

	public void reloadAccountSMState() {
		accountSMState.reload();
	}

	public void addOneNewMessage(int count) {
		accountSMState.addOneNewMessage(count);
	}

	public void addMessageObservable(Observable observable) {
		observable.addObserver(accountSMState);
	}

	public int getNewShortMessageCount() {
		return accountSMState.getNewShortMessageCount();
	}

	public boolean isAdmin() {
		if (this.getRoleName().equals(Role.ADMIN))
			return true;
		else
			return false;
	}

}