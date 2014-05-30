package com.jdon.jivejdon.service.imp.subscription;

import com.jdon.annotation.Service;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.annotation.intercept.Stateful;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.email.ValidateCodeEmail;
import com.jdon.jivejdon.manager.subscription.action.EmailAction;
import com.jdon.jivejdon.manager.subscription.action.SinaWeiboAction;
import com.jdon.jivejdon.manager.weibo.SinaWeboSubmitter;
import com.jdon.jivejdon.manager.weibo.SinaWeiboUserPwd;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.TagSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.repository.SubscriptionRepository;
import com.jdon.jivejdon.repository.Userconnector;
import com.jdon.jivejdon.service.SubscriptionService;
import com.jdon.jivejdon.service.util.SessionContextUtil;

@Stateful
@Service("subscriptionService")
public class SubscriptionServiceImp implements SubscriptionService {

	private SubscriptionRepository subscriptionRepository;

	protected SessionContext sessionContext;

	protected SessionContextUtil sessionContextUtil;

	protected Account account;

	protected ValidateCodeEmail validateCodeEmail;

	protected SinaWeboSubmitter sinaWeboSubmitter;

	protected Userconnector userconnector;

	public SubscriptionServiceImp(SessionContextUtil sessionContextUtil, SubscriptionRepository subscriptionRepository,
			ValidateCodeEmail validateCodeEmail, SinaWeboSubmitter sinaWeboSubmitter, Userconnector userconnector) {
		super();
		this.subscriptionRepository = subscriptionRepository;
		this.sessionContextUtil = sessionContextUtil;
		this.validateCodeEmail = validateCodeEmail;
		this.sinaWeboSubmitter = sinaWeboSubmitter;
		this.userconnector = userconnector;
	}

	public Account getloginAccount() {
		if (account == null)
			account = sessionContextUtil.getLoginAccount(sessionContext);
		return account;
	}

	public Subscription initSubscription(EventModel em) {
		Subscription subscription = (Subscription) em.getModelIF();
		return subscriptionRepository.getFullSub(subscription);

	}

	@Override
	public void createSubscription(EventModel em) {
		try {
			Subscription subscription = (Subscription) em.getModelIF();
			subscription.setAccount(getloginAccount());
			boolean hasAdded = subscriptionRepository.getSubscriptionDao().isSubscription(subscription.getSubscribed().getSubscribeId(),
					subscription.getAccount().getUserId());
			if (hasAdded) {
				em.setErrors(Constants.EXISTED_ERROR);
				return;
			}
			if (subscription.getSubscriptionActionHolder().hasActionType(EmailAction.class) && !subscription.getAccount().isEmailValidate()) {
				validateCodeEmail.send(subscription.getAccount());
			}
			subscriptionRepository.createSubscription(subscription);
			if (subscription.getSubscriptionActionHolder().hasActionType(SinaWeiboAction.class)) {

				SinaWeiboAction sinaWeiboAction = (SinaWeiboAction) subscription.getSubscriptionActionHolder().getActionType(SinaWeiboAction.class);
				SinaWeiboUserPwd sinaWeiboUserPwd = sinaWeiboAction.getSinaWeiboUserPwd();
				sinaWeiboUserPwd.setType(Long.toString(subscription.getSubscriptionId()));
				if (sinaWeboSubmitter.verfiyUser(sinaWeiboUserPwd.getUserId(), sinaWeiboUserPwd.getPasswd()))
					userconnector.saveSinaWeiboUserconn(subscription.getAccount().getUserId(), sinaWeiboAction.getSinaWeiboUserPwd());
			}
			subscription = getSubscription(subscription.getSubscriptionId());
			subscription.updateSubscriptionCount(1);
		} catch (Exception e) {
			e.printStackTrace();
			em.setErrors(Constants.ERRORS);
		}

	}

	public void updateSubscription(EventModel em) {
		deleteSubscription(em);
		createSubscription(em);
	}

	@Override
	public void deleteSubscription(EventModel em) {
		Subscription subscriptionp = (Subscription) em.getModelIF();
		Subscription subscription = getSubscription(subscriptionp.getSubscriptionId());
		if (subscription != null) {
			subscriptionRepository.deleteSubscription(subscription);
			if (subscription.getSubscriptionActionHolder().hasActionType(SinaWeiboAction.class)) {
				userconnector.removeSinaWeiboUserconn(subscription.getAccount().getUserId(), Long.toString(subscription.getSubscriptionId()));
			}
			subscription.updateSubscriptionCount(-1);
		}

	}

	@Override
	public Subscription getSubscription(Long id) {
		return subscriptionRepository.getSubscription(id);
	}

	public PageIterator getSubscriptionsForTag(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), TagSubscribed.TYPE, start, count);
	}

	public PageIterator getSubscriptionsForAccount(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), AccountSubscribed.TYPE, start, count);
	}

	public PageIterator getSubscriptionsForThread(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), ThreadSubscribed.TYPE, start, count);
	}

	public PageIterator getSubscriptionsForForum(String userId, int start, int count) {
		return subscriptionRepository.getSubscriptionDao().getSubscriptions(getloginAccount().getUserId(), ForumSubscribed.TYPE, start, count);
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sessionContext;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		this.sessionContext = sessionContext;
	}

}
