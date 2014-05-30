package com.jdon.jivejdon.manager.email;

import com.jdon.async.EventProcessor;
import com.jdon.async.task.ObservableAdapter;
import com.jdon.jivejdon.util.EmailTask;

public class EmailHelper {

	private EventProcessor ep;

	public EmailHelper(EventProcessor ep) {
		this.ep = ep;
	}

	public void sendComposer(EmailTask emailTask) {
		EmailTaskListerner emailTaskListerner = new EmailTaskListerner(emailTask);
		ObservableAdapter subscriptionObservable = new ObservableAdapter(ep);
		subscriptionObservable.addObserver(emailTaskListerner);
		subscriptionObservable.notifyObservers(null);
	}

}
