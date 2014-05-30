package com.jdon.jivejdon.repository.dao.sql;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.subscription.SubscribedFactory;
import com.jdon.jivejdon.manager.subscription.action.EmailAction;
import com.jdon.jivejdon.manager.subscription.action.ShortMsgAction;
import com.jdon.jivejdon.manager.subscription.action.SinaWeiboAction;
import com.jdon.jivejdon.manager.weibo.SinaWeiboUserPwd;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.repository.Userconnector;
import com.jdon.jivejdon.repository.dao.SubscriptionDao;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.model.query.PageIteratorSolver;

public class SubscriptionDaoSql implements SubscriptionDao {
	private final static Logger logger = Logger.getLogger(SubscriptionDaoSql.class);

	protected JdbcTempSource jdbcTempSource;

	protected Constants constants;

	protected PageIteratorSolver pageIteratorSolver;

	protected Userconnector userconnectorSql;

	public SubscriptionDaoSql(JdbcTempSource jdbcTempSource, Constants constants, ContainerUtil containerUtil, Userconnector userconnectorSql) {
		this.jdbcTempSource = jdbcTempSource;
		this.constants = constants;
		this.userconnectorSql = userconnectorSql;
		this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());

	}

	private Subscription createSub(Map map) {
		Subscription ret = new Subscription();
		ret.setSubscriptionId((Long) map.get("subscriptionID"));

		String userId = (String) map.get("userId");
		Account accountin = new Account();
		accountin.setUserId(userId);
		ret.setAccount(accountin);

		Integer subscribeType = (Integer) map.get("subscribedtype");
		Long subscribeID = (Long) map.get("subscribedID");

		ret.setSubscribed(SubscribedFactory.createTransient(subscribeType.intValue(), subscribeID));

		String saveDateTime = ((String) map.get("creationDate")).trim();
		ret.setCreationDate(constants.getDateTimeDisp(saveDateTime));

		Boolean sendmsg = (Boolean) map.get("sendmsg");
		if (sendmsg)
			ret.addAction(new ShortMsgAction());

		Boolean sendemail = (Boolean) map.get("sendemail");
		if (sendemail) {
			ret.addAction(new EmailAction(ret));
		}

		SinaWeiboUserPwd sinaWeiboUserPwd = userconnectorSql.loadSinaWeiboUserconn(userId, Long.toString(ret.getSubscriptionId()));
		if (sinaWeiboUserPwd != null) {
			SinaWeiboAction sinaWeiboAction = new SinaWeiboAction(sinaWeiboUserPwd);
			sinaWeiboAction.setSubscription(ret);
			ret.addAction(sinaWeiboAction);
		}

		return ret;
	}

	public void createSubscription(Subscription subscription) {
		try {
			String ADD_SUB = "INSERT INTO subscription(subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail)"
					+ " VALUES (?,?,?,?,?,?,?)";
			List queryParams = new ArrayList();
			queryParams.add(subscription.getSubscriptionId());
			queryParams.add(subscription.getAccount().getUserId());

			Subscribed subscribed = subscription.getSubscribed();
			queryParams.add(subscribed.getSubscribeType());
			queryParams.add(subscribed.getSubscribeId());

			long now = System.currentTimeMillis();
			String saveDateTime = ToolsUtil.dateToMillis(now);
			String displayDateTime = constants.getDateTimeDisp(saveDateTime);
			queryParams.add(saveDateTime);
			subscription.setCreationDate(displayDateTime);

			queryParams.add(subscription.getSubscriptionActionHolder().hasActionType(ShortMsgAction.class));
			queryParams.add(subscription.getSubscriptionActionHolder().hasActionType(EmailAction.class));

			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SUB);

		} catch (Exception e) {
			logger.error(e);
		}
	}

	public Subscription getSubscription(Long id) {
		logger.debug("enter getSubscription for id:" + id);
		String LOAD_SUB = "SELECT subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail FROM subscription WHERE subscriptionID=?";
		List queryParams = new ArrayList();
		queryParams.add(id);

		Subscription ret = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				ret = createSub(map);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public boolean isSubscription(Long subscribedID, String userId) {
		String LOAD_SUB = "SELECT subscriptionID FROM subscription WHERE subscribedID=? and userId = ?";
		List queryParams = new ArrayList();
		queryParams.add(subscribedID);
		queryParams.add(userId);

		boolean ret = false;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				ret = true;
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return ret;
	}

	public void deleteSubscription(Subscription subscription) {
		try {
			String sql = "DELETE FROM subscription WHERE subscriptionID=?";
			List queryParams = new ArrayList();
			queryParams.add(subscription.getSubscriptionId());
			jdbcTempSource.getJdbcTemp().operate(queryParams, sql);
		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Override
	public PageIterator getSubscriptions(String userId, int subscribedtype, int start, int count) {
		logger.debug("enter getSubscriptions ..");

		String GET_ALL_ITEMS_ALLCOUNT = "select count(1) FROM subscription WHERE userId=? and subscribedtype =?";

		String GET_ALL_ITEMS = "select subscriptionID FROM subscription WHERE userId=? and subscribedtype =? ";

		Collection params = new ArrayList(2);
		params.add(userId);
		params.add(new Integer(subscribedtype));
		return pageIteratorSolver.getPageIterator(GET_ALL_ITEMS_ALLCOUNT, GET_ALL_ITEMS, params, start, count);

	}

	@Override
	public Collection<Subscription> getSubscriptionsForsubscribed(Long subscribedID) {
		logger.debug("enter getSubscriptions for id:" + subscribedID);
		String LOAD_SUB = "SELECT subscriptionID, userId, subscribedtype, subscribedID, creationDate, sendmsg, sendemail FROM subscription WHERE subscribedID=?";
		List queryParams = new ArrayList();
		queryParams.add(subscribedID);

		Collection<Subscription> rets = new ArrayList();
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			Iterator iter = list.iterator();
			while (iter.hasNext()) {
				Map map = (Map) iter.next();
				Subscription ret = createSub(map);
				rets.add(ret);
			}
		} catch (Exception se) {
			logger.error(se);
		}
		return rets;
	}

	public int getSubscriptionsForsubscribedCount(Long subscribedID) {
		logger.debug("enter getSubscriptions for id:" + subscribedID);
		String LOAD_SUB = "SELECT subscriptionID FROM subscription WHERE subscribedID=?";
		List queryParams = new ArrayList();
		queryParams.add(subscribedID);
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SUB);
			return list.size();
		} catch (Exception se) {
			logger.error(se);
		}
		return 0;
	}

}
