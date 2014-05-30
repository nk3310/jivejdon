/*
 * Copyright 2003-2009 the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.jdon.jivejdon.manager.account;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.jdon.annotation.Component;
import com.jdon.container.pico.Startable;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.email.ForgotPasswdEmail;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.AccountRepository;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.StringUtil;

@Component
public class AccountManager implements Startable {

	private List cachedOneTimes = new ArrayList();

	private final ScheduledExecutorUtil scheduledExecutorUtil;

	private ForgotPasswdEmail forgotPasswdEmail;

	private final AccountRepository accountRepository;

	protected final AccountFactory accountFactory;

	public AccountManager(AccountFactory accountFactory, AccountRepository accountRepository, ForgotPasswdEmail forgotPasswdEmail,
			ScheduledExecutorUtil scheduledExecutorUtil) {
		super();
		this.cachedOneTimes = new ArrayList();
		this.forgotPasswdEmail = forgotPasswdEmail;
		this.accountRepository = accountRepository;
		this.accountFactory = accountFactory;
		this.scheduledExecutorUtil = scheduledExecutorUtil;
	}

	public void start() {
		Runnable task = new Runnable() {
			public void run() {
				cachedOneTimes.clear();
			}
		};
		// per ten mintue
		scheduledExecutorUtil.getScheduExec().scheduleAtFixedRate(task, 60, 60 * 60 * 2, TimeUnit.SECONDS);
	}

	public void stop() {
		cachedOneTimes.clear();
	}

	public boolean contains(String chkKey) {
		return this.cachedOneTimes.contains(chkKey);
	}

	public void addChkKey(String chkKey) {
		this.cachedOneTimes.add(chkKey);
	}

	public void forgetPasswdAction(EventModel em) {

		Account accountParam = (Account) em.getModelIF();
		Account account = null;
		try {
			account = accountFactory.getFullAccount(accountParam);
			if (account == null) {
				em.setErrors(Constants.NOT_FOUND);
				return;
			}
			if ((account.getPasswdanswer().equalsIgnoreCase(accountParam.getPasswdanswer()))
					&& (account.getPasswdtype().equalsIgnoreCase(accountParam.getPasswdtype()))) {
				String newpasswd = StringUtil.getPassword(8);
				account.setPassword(ToolsUtil.hash(newpasswd));
				accountRepository.updateAccount(account);
				forgotPasswdEmail.send(account, newpasswd);
			} else {
				em.setErrors(Constants.NOT_FOUND);
				return;
			}

		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
	}

}
