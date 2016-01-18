package com.jdon.jivejdon.repository.listener;

import com.jdon.annotation.Consumer;
import com.jdon.async.disruptor.EventDisruptor;
import com.jdon.domain.message.DomainEventHandler;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.model.message.DigDataBag;
import com.jdon.jivejdon.model.proptery.MessagePropertys;
import com.jdon.jivejdon.repository.dao.PropertyDao;

@Consumer("addMessageDigCount")
public class AddMessageDigCount implements DomainEventHandler {
	private PropertyDao propertyDao;

	public AddMessageDigCount(PropertyDao propertyDao) {
		super();
		this.propertyDao = propertyDao;
	}

	public void onEvent(EventDisruptor event, boolean endOfBatch) throws Exception {

		DigDataBag digData = (DigDataBag) event.getDomainMessage().getEventSource();
		Long messageId = digData.messageId;
		Property Numberproperty = new Property(MessagePropertys.DIG_NUMBER, String.valueOf(digData.number));

		propertyDao.updateProperty(Constants.MESSAGE, messageId, Numberproperty);

	}
}
