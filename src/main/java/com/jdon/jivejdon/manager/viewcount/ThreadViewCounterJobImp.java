package com.jdon.jivejdon.manager.viewcount;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.proptery.ThreadPropertys;
import com.jdon.jivejdon.model.thread.ViewCounter;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;

/**
 * a cache used for holding view count of ForumThread the data that in the cache
 * will be flushed to database per one hour
 * 
 * @author oojdon banq
 * 
 */

@Component("threadViewCounterJob")
public class ThreadViewCounterJobImp implements Startable, ThreadViewCounterJob {
	private final static Logger logger = Logger.getLogger(ThreadViewCounterJobImp.class);

	private ConcurrentMap<Long, ViewCounter> concurrentHashMap = new ConcurrentHashMap<Long, ViewCounter>();

	private final PropertyDao propertyDao;
	private final ThreadViewCountParameter threadViewCountParameter;
	private final ScheduledExecutorUtil scheduledExecutorUtil;

	public ThreadViewCounterJobImp(final PropertyDao propertyDao, final ThreadViewCountParameter threadViewCountParameter,
			ScheduledExecutorUtil scheduledExecutorUtil) {
		this.propertyDao = propertyDao;
		this.threadViewCountParameter = threadViewCountParameter;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				writeDB();
			}
		};
		// flush to db per one hour
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, threadViewCountParameter.getInitdelay(), threadViewCountParameter.getDelay(),
				TimeUnit.SECONDS);
	}

	// when container down or undeploy, active this method.
	public void stop() {
		writeDB();
	}

	public void writeDB() {
		// construct a immutable map, not effect old map.
		Map<Long, ViewCounter> viewCounters = Collections.unmodifiableMap(concurrentHashMap);
		for (long threadId : viewCounters.keySet()) {
			ViewCounter viewCounter = concurrentHashMap.get(threadId);
			if (viewCounter.getLastSavedCount() == viewCounter.getViewCount())
				concurrentHashMap.remove(threadId);
			else {
				saveItem(viewCounter);
				viewCounter.setLastSavedCount(viewCounter.getViewCount());
			}
		}
	}

	private void saveItem(ViewCounter viewCounter) {
		try {
			Property property = new Property();
			property.setName(ThreadPropertys.VIEW_COUNT);
			property.setValue(Long.toString(viewCounter.getViewCount()));
			propertyDao.updateProperty(Constants.THREAD, viewCounter.getThread().getThreadId(), property);
		} catch (Exception e) {
			logger.error(e);
		} finally {
		}
	}

	// called by thread create factory
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.viewcount.ThreadViewCounterJob#initViewCounter
	 * (com.jdon.jivejdon.model.ForumThread)
	 */
	@Override
	public void initViewCounter(ForumThread thread) {
		int count = -1;
		ViewCounter viewCounter = concurrentHashMap.get(thread.getThreadId());
		if (viewCounter == null) {
			count = getFromDB(thread.getThreadId());
			viewCounter = new ViewCounter(thread, count);
			concurrentHashMap.put(thread.getThreadId(), viewCounter);
		}
		thread.setViewCounter(viewCounter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.viewcount.ThreadViewCounterJob#checkViewCounter
	 * (com.jdon.jivejdon.model.ForumThread)
	 */
	@Override
	public void checkViewCounter(ForumThread thread) {
		if (!concurrentHashMap.containsValue(thread.getViewCounter())) {
			concurrentHashMap.put(thread.getThreadId(), thread.getViewCounter());
		}
	}

	private int getFromDB(Long threadId) {
		Integer number = null;
		Property p = propertyDao.getThreadProperty(threadId, ThreadPropertys.VIEW_COUNT);
		if (p != null)
			number = Integer.valueOf(p.getValue());
		else
			number = new Integer(0);

		return number;
	}

}
