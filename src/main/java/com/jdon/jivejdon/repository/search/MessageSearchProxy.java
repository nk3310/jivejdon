package com.jdon.jivejdon.repository.search;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.compass.annotations.config.CompassAnnotationsConfiguration;
import org.compass.core.Compass;
import org.compass.core.CompassException;
import org.compass.core.CompassHits;
import org.compass.core.CompassSession;
import org.compass.core.CompassTransaction;
import org.compass.core.config.CompassConfiguration;
import org.compass.core.config.CompassEnvironment;
import org.compass.core.config.ConfigurationException;
import org.compass.core.engine.SearchEngineException;

import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.model.message.MessageVO;
import com.jdon.jivejdon.model.query.MessageSearchSpec;
import com.jdon.jivejdon.repository.dao.sql.MessageUtilSQL;
import com.jdon.jivejdon.util.ContainerUtil;

public class MessageSearchProxy implements Startable, MessageSearchRepository {
	private final static Logger logger = Logger.getLogger(MessageSearchProxy.class);
	private Compass compass;

	private ContainerUtil containerUtil;

	private MessageUtilSQL messageUtilSQL;

	public MessageSearchProxy(ContainerUtil containerUtil, MessageUtilSQL messageUtilSQL) {
		this.containerUtil = containerUtil;
		this.messageUtilSQL = messageUtilSQL;
	}

	public MessageSearchProxy(boolean rebuild) {
		if (rebuild) {
			reInit();
		} else {
			init();
		}

	}

	public void start() {
		init();
	}

	public void stop() {
		this.compass.close();
		this.compass = null;

	}

	public void init() {
		try {
			logger.debug("compass init");
			CompassConfiguration config = new CompassAnnotationsConfiguration();
			// .configure("/com/jdon/jivejdon/search/compass/compass.cfg.xml");
			// in j2ee env, the configure seem can not be found
			// error :org.compass.core.config.ConfigurationException: Failed to
			// open config resource
			// com/jdon/jivejdon/search/compass/compass.cfg.xml
			config.setSetting(CompassEnvironment.CONNECTION, "/target/testindex");
			config.setSetting("compass.engine.highlighter.default.formatter.simple.pre", "<font color=CC0033>");
			config.setSetting("compass.engine.highlighter.default.formatter.simple.post", "</font>");
			// config.setSetting("compass.transaction.factory",
			// "org.compass.core.transaction.JTASyncTransactionFactory");
			// config.setSetting("compass.transaction.isolation",
			// "read_committed");
			// config.setSetting("compass.transaction.lockTimeout", "15");

			/**
			 * config.setSetting("compass.engine.connection",
			 * "jdbc://java:/JiveJdonDS");
			 * config.setSetting("compass.jndi.enable", "true");
			 * config.setSetting
			 * ("compass.engine.store.jdbc.connection.provider.class",
			 * "org.compass.core.lucene.engine.store.jdbc.ExternalDataSourceProvider"
			 * ); ExternalDataSourceProvider.setDataSource(ds);
			 * 
			 * 
			 * TableToResourceMapping folderMapping = new
			 * TableToResourceMapping("frmfolders", "frmfolders");
			 * TableToResourceMapping templateMapping = new
			 * TableToResourceMapping("frmtemplates", "frmtemplates");
			 * TableToResourceMapping variantMapping = new
			 * TableToResourceMapping("frmvariants", "frmvariants");
			 * 
			 * config.addMappingResover(new
			 * ResultSetResourceMappingResolver(folderMapping, ds));
			 * config.addMappingResover(new
			 * ResultSetResourceMappingResolver(templateMapping, ds));
			 * config.addMappingResover(new
			 * ResultSetResourceMappingResolver(variantMapping, ds));
			 */
			config.addClass(com.jdon.jivejdon.model.ForumMessage.class);
			config.addClass(com.jdon.jivejdon.model.Forum.class);
			config.addClass(com.jdon.jivejdon.model.message.MessageVO.class);
			compass = config.buildCompass();
			// compassTemplate = new CompassTemplate(compass);
		} catch (ConfigurationException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (SearchEngineException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (CompassException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	public void reInit() {
		try {
			logger.debug("compass init");
			CompassConfiguration config = new CompassAnnotationsConfiguration();
			config.setSetting(CompassEnvironment.CONNECTION, "target/testindex");

			config.addClass(com.jdon.jivejdon.model.ForumMessage.class);
			config.addClass(com.jdon.jivejdon.model.Forum.class);
			config.addClass(com.jdon.jivejdon.model.message.MessageVO.class);
			compass = config.buildCompass();
			// compass.getSearchEngineIndexManager().deleteIndex();
			// compass.getSearchEngineIndexManager().createIndex();
			// compassTemplate = new CompassTemplate(compass);
		} catch (ConfigurationException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (SearchEngineException e) {
			logger.error(e);
			e.printStackTrace();
		} catch (CompassException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#createMessage
	 * (com.jdon.jivejdon.model.ForumMessage)
	 */
	@Override
	public void createMessage(ForumMessage forumMessage) {
		logger.debug("MessageSearchProxy.createMessage");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(forumMessage);
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.search.MessageSearchRepository#
	 * createMessageReply(com.jdon.jivejdon.model.ForumMessageReply)
	 */
	@Override
	public void createMessageReply(ForumMessageReply forumMessageReply) {
		logger.debug("MessageSearchProxy.createMessageReply");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			session.save(forumMessageReply);
			tx.commit();
		} catch (SearchEngineException ex) {

		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#updateMessage
	 * (com.jdon.jivejdon.model.ForumMessage)
	 */
	@Override
	public void updateMessage(ForumMessage forumMessage) {
		logger.debug("MessageSearchProxy.updateMessage");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, forumMessage.getMessageId());
			MessageVO messageVO = new MessageVO();
			MessageVO messageVOClone = forumMessage.getMessageVOClone();
			messageVO.setSubject(messageVOClone.getSubject());
			messageVO.setBody(messageVOClone.getBody());
			messageVO.setTagTitle(messageVOClone.getTagTitle());
			messageS.setMessageVO(messageVO);
			tx = session.beginTransaction();
			session.save(messageS);
			tx.commit();
		} catch (SearchEngineException ex) {

		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#deleteMessage
	 * (java.lang.Long)
	 */
	@Override
	public void deleteMessage(Long forumMessageId) {
		logger.debug("MessageSearchProxy.deleteMessage");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		try {
			tx = session.beginTransaction();
			ForumMessage messageS = (ForumMessage) session.load(ForumMessage.class, forumMessageId);
			session.delete(messageS);
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#find(java
	 * .lang.String, int, int)
	 */
	public Collection find(String query, int start, int count) {
		logger.debug("MessageSearchProxy.find");
		Collection result = new ArrayList();
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		MessageSearchSpec messageSearchSpec = null;
		CompassHits hits = null;
		try {
			tx = session.beginTransaction();
			hits = session.find(query);
			logger.debug("Found [" + hits.getLength() + "] hits for [" + query + "] query");
			int end = start + count;
			if (end >= hits.getLength())
				end = hits.getLength();

			for (int i = start; i < end; i++) {
				logger.debug("create  messageSearchSpec collection");
				ForumMessage smessage = (ForumMessage) hits.data(i);
				messageSearchSpec = new MessageSearchSpec();
				messageSearchSpec.setMessageId(smessage.getMessageId());

				MessageVO mVO = new MessageVO();
				String body = hits.highlighter(i).fragment("body");
				mVO.setBody(body);
				String subject = hits.highlighter(i).fragment("subject");
				mVO.setSubject(subject);
				messageSearchSpec.setMessageVO(mVO);

				// String tagTitle[] =
				// hits.highlighter(i).fragments("tagTitle");
				// messageSearchSpec.setTagTitle(tagTitle);
				messageSearchSpec.setResultAllCount(hits.getLength());
				result.add(messageSearchSpec);
			}
			hits.close();
			tx.commit();
		} catch (Exception ce) {
			if (hits != null)
				hits.close();
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.search.MessageSearchRepository#
	 * findThreadsAllCount(java.lang.String)
	 */
	public int findThreadsAllCount(String query) {
		logger.debug("findThreadsAllCount.find");
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		int allCount = 0;
		try {
			tx = session.beginTransaction();
			CompassHits hits = session.find(query);
			allCount = hits.getLength();
			hits.close();
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
		return allCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.search.MessageSearchRepository#findThread
	 * (java.lang.String, int, int)
	 */
	public Collection findThread(String query, int start, int count) {
		logger.debug("MessageSearchProxy.find");
		Collection result = new ArrayList();
		CompassSession session = compass.openSession();
		CompassTransaction tx = null;
		MessageSearchSpec messageSearchSpec = null;
		try {
			tx = session.beginTransaction();
			CompassHits hits = session.find(query);
			logger.debug("Found [" + hits.getLength() + "] hits for [" + query + "] query");
			start = getNewStart(hits, start);
			int j = start;
			for (int i = start; j < start + count; i++) {
				if (i >= hits.getLength())
					break;
				logger.debug("create  messageSearchSpec collection");
				ForumMessage smessage = (ForumMessage) hits.data(i);
				messageSearchSpec = getMessageSearchSpec(smessage.getMessageId());
				if (messageSearchSpec.isRoot()) {
					messageSearchSpec.setMessageId(smessage.getMessageId());

					MessageVO mVO = new MessageVO();
					String body = hits.highlighter(i).fragment("body");
					mVO.setBody(body);
					String subject = hits.highlighter(i).fragment("subject");
					mVO.setSubject(subject);
					messageSearchSpec.setMessageVO(mVO);

					messageSearchSpec.setResultAllCount(hits.getLength());
					result.add(messageSearchSpec);
					j++;
				}
			}
			hits.close();
			tx.commit();
		} catch (Exception ce) {
			if (tx != null)
				tx.rollback();
			logger.error(ce);
		} finally {
			session.close();
		}
		return result;
	}

	private int getNewStart(CompassHits hits, int end) {
		int newStart = 0;
		int j = 0;
		MessageSearchSpec messageSearchSpec;
		for (int i = 0; j < end; i++) {
			newStart = i;
			if (i >= hits.getLength())
				break;
			logger.debug("create  messageSearchSpec collection");
			ForumMessage smessage = (ForumMessage) hits.data(i);
			messageSearchSpec = getMessageSearchSpec(smessage.getMessageId());
			if (messageSearchSpec.isRoot()) {
				j++;
			}

		}
		return newStart;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.search.MessageSearchRepository#
	 * getMessageSearchSpec(java.lang.Long)
	 */
	public MessageSearchSpec getMessageSearchSpec(Long messageId) {
		MessageSearchSpec mss = (MessageSearchSpec) containerUtil.getModelFromCache(messageId, MessageSearchSpec.class);
		if ((mss == null)) {
			mss = new MessageSearchSpec();
			boolean isRoot = messageUtilSQL.isRoot(messageId);
			mss.setRoot(isRoot);
			containerUtil.addModeltoCache(messageId, mss);
		}
		return mss;
	}

}
