package com.jdon.jivejdon.repository.listener;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.proptery.MessagePropertys;
import com.jdon.jivejdon.repository.dao.PropertyDao;

@Consumer("loadMessageDigCount")
public class LoadMessageDigCount implements DomainEventHandler {
	private PropertyDao propertyDao;

	public LoadMessageDigCount(PropertyDao propertyDao) {
		super();
		this.propertyDao = propertyDao;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {
		Long messageId = (Long) event.getDomainMessage().getEventSource();
		Property p = propertyDao.getMessageProperty(messageId, MessagePropertys.DIG_NUMBER);

		if (p == null)
			event.getDomainMessage().setEventResult(0);
		else {
			String number = p.getValue();
			event.getDomainMessage().setEventResult(Integer.valueOf(number));
		}

	}

}
