package com.jdon.jivejdon.manager.email;

import com.jdon.async.task.Task;
import com.jdon.jivejdon.util.EmailTask;

public class EmailTaskListerner extends Task {
	private EmailTask emailTask;

	public EmailTaskListerner(EmailTask emailTask) {
		super();
		this.emailTask = emailTask;
	}

	@Override
	public void action() {
		Thread thread = new Thread(emailTask);
		thread.start();

	}

	public EmailTask getEmailTask() {
		return emailTask;
	}

	public void setEmailTask(EmailTask emailTask) {
		this.emailTask = emailTask;
	}

}
