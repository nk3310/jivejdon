package com.jdon.jivejdon.service.imp.account;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.annotation.intercept.Poolable;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.manager.email.ValidateCodeEmail;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.AccountProfile;
import com.jdon.jivejdon.model.Property;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.AccountRepository;
import com.jdon.jivejdon.repository.dao.PropertyDao;
import com.jdon.jivejdon.service.AccountProfileService;
import com.jdon.util.UtilValidate;

@Poolable
public class AccountProfileServiceImp implements AccountProfileService {
	private final static Logger logger = Logger.getLogger(AccountProfileServiceImp.class);

	private PropertyDao propertyDao;
	private ValidateCodeEmail validateCodeEmail;
	private AccountFactory accountFactory;
	protected AccountRepository accountRepository;

	public AccountProfileServiceImp(AccountFactory accountFactory, AccountRepository accountRepository, PropertyDao propertyDao,
			ValidateCodeEmail validateCodeEmail) {
		this.propertyDao = propertyDao;
		this.accountRepository = accountRepository;
		this.validateCodeEmail = validateCodeEmail;
		this.accountFactory = accountFactory;
	}

	public AccountProfile getAccountProfile(String user) {
		Account accountIn = new Account();
		if (UtilValidate.isInteger(user))
			accountIn.setUserId(user);
		else
			accountIn.setUsername(user);
		Account account = accountFactory.getFullAccount(accountIn);
		if (account == null) {
			return null;
		}

		AccountProfile accountProfile = new AccountProfile();
		accountProfile.setAccount(account);
		accountProfile.setUserId(account.getUserId());

		Collection oldpropertys = propertyDao.getProperties(Constants.USER, account.getUserIdLong());
		accountProfile.setPropertys(oldpropertys);
		return accountProfile;
	}

	public void updateAccountProfile(EventModel em) {
		AccountProfile accountProfiler = (AccountProfile) em.getModelIF();
		Long userIdL = Long.parseLong(accountProfiler.getAccount().getUserId());
		Collection propss = new ArrayList();
		Iterator iter = accountProfiler.getPropertys().iterator();
		while (iter.hasNext()) {
			Property prop = (Property) iter.next();
			if (!UtilValidate.isEmpty(prop.getName()) && !UtilValidate.isEmpty(prop.getValue()))
				propss.add(prop);
		}
		propertyDao.deleteProperties(Constants.USER, userIdL);
		propertyDao.updateProperties(Constants.USER, userIdL, propss);
	}

	public void emailValidate(EventModel em) {
		AccountProfile accountProfiler = (AccountProfile) em.getModelIF();
		Account account = accountProfiler.getAccount();
		try {
			if (validateCodeEmail.emailValidate(account.getUserId(), accountProfiler.getValidateCode())) {
				logger.debug("emailValidate passed =" + account.getUserId());
				account.setEmailValidate(true);
				accountRepository.updateAccountEmailValidate(account);
			} else
				em.setErrors(Constants.ERRORS);
		} catch (Exception e) {
			logger.error(e);
		}

	}
}
