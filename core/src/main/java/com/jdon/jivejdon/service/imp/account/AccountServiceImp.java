/*
 * Copyright 2003-2005 the original author or authors.
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
package com.jdon.jivejdon.service.imp.account;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.auth.Role;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.AccountRepository;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.repository.dao.util.OldUpdateNewUtil;
import com.jdon.jivejdon.service.AccountService;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;
import com.jdon.jivejdon.util.ToolsUtil;
import com.jdon.util.Debug;
import com.jdon.util.task.TaskEngine;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public abstract class AccountServiceImp implements AccountService {
	private final static String module = AccountServiceImp.class.getName();

	protected AccountFactory accountFactory;

	protected AccountRepository accountRepository;

	private SequenceDao sequenceDao;

	protected JtaTransactionUtil jtaTransactionUtil;

	private OldUpdateNewUtil oldUpdateNewUtil;

	public AccountServiceImp(AccountFactory accountFactory, AccountRepository accountRepository, SequenceDao sequenceDao,
			JtaTransactionUtil jtaTransactionUtil) {
		this.accountFactory = accountFactory;
		this.accountRepository = accountRepository;
		this.sequenceDao = sequenceDao;
		this.jtaTransactionUtil = jtaTransactionUtil;
		// this.oldUpdateNewUtil = oldUpdateNewUtil;

	}

	public Account getAccountByName(String username) {
		Account accountIn = new Account();
		accountIn.setUsername(username);
		return accountFactory.getFullAccount(accountIn);
	}

	public Account getAccountByEmail(String email) {
		Account accountIn = new Account();
		accountIn.setEmail(email);
		return accountFactory.getFullAccount(accountIn);
	}

	public PageIterator getAccountByNameLike(String username, int start, int count) {
		return accountRepository.getAccountByNameLike(username, start, count);
	}

	public Account getAccount(Long userId) {
		return accountFactory.getFullAccount(userId.toString());
	}

	/**
	 * init the account
	 */
	public Account initAccount(EventModel em) {
		return new Account();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.framework.samples.jpetstore.service.AccountService#insertAccount
	 * (com.jdon.framework.samples.jpetstore.domain.Account)
	 */

	public void createAccount(EventModel em) {
		Account account = (Account) em.getModelIF();
		Debug.logVerbose("createAccount username=" + account.getUsername(), module);
		try {
			if (accountFactory.getFullAccountForEmail(account.getEmail()) != null) {
				em.setErrors(Constants.EMAIL_EXISTED);
				return;
			}
			if (accountFactory.getFullAccountForUsername(account.getUsername()) != null) {
				em.setErrors(Constants.USERNAME_EXISTED);
				return;
			}
			jtaTransactionUtil.beginTransaction();
			Long userIDInt = sequenceDao.getNextId(Constants.USER);
			Debug.logVerbose("new userIDInt =" + userIDInt, module);
			account.setUserId(userIDInt.toString().trim());

			// setup the role is User
			account.setRoleName(Role.USER);

			account.setPassword(ToolsUtil.hash(account.getPassword()));

			accountRepository.createAccount(account);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			Debug.logError(" createAccount error : " + e, module);
			em.setErrors(Constants.ERRORS);
			jtaTransactionUtil.rollback();
		}
	}

	public boolean checkUser(String username) {
		if (accountFactory.getFullAccountForUsername(username) != null) {
			return true;
		}
		return false;
	}

	public void updateAccount(Account accountInput) throws Exception {
		Debug.logVerbose("enter updateAccount", module);

		try {
			Account checkAccount = getAccount(accountInput.getUserIdLong());
			if (checkAccount == null)
				return;
			if (checkAccount.isEmailValidate()) {
				accountInput.setEmail(checkAccount.getEmail());
			}

			jtaTransactionUtil.beginTransaction();
			accountInput.setPassword(ToolsUtil.hash(accountInput.getPassword()));
			accountRepository.updateAccount(accountInput);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			Debug.logError(" updateAccount error : " + e, module);
			jtaTransactionUtil.rollback();
			throw new Exception(e);
		}
	}

	public void deleteAccount(Account account) throws Exception {
		try {
			jtaTransactionUtil.beginTransaction();
			accountRepository.deleteAccount(account);
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			Debug.logError(" deleteAccount error : " + e, module);
			jtaTransactionUtil.rollback();
			throw new Exception(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.service.AccountService#getAccounts(int, int)
	 */
	public PageIterator getAccounts(int start, int count) {
		return accountRepository.getAccounts(start, count);
	}

	public void update() {
		TaskEngine.addTask(oldUpdateNewUtil);
		Debug.logVerbose("work is over", module);
		Debug.logVerbose("work is over", module);
	}

}
