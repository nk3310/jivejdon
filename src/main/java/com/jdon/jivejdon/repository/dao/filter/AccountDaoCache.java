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
package com.jdon.jivejdon.repository.dao.filter;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.pointcut.Around;
import com.jdon.cache.LRUCache;
import com.jdon.controller.cache.Cache;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.repository.builder.AccountInitFactory;
import com.jdon.jivejdon.repository.dao.sql.AccountDaoSql;
import com.jdon.jivejdon.repository.dao.sql.AccountSSOSql;
import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;

@Introduce("modelCache")
public class AccountDaoCache extends AccountDaoSql {
	// same as second level cache of hibernate
	// not modelCache
	private Cache secondCache;

	public AccountDaoCache(JdbcTempSource jdbcTempSource, AccountSSOSql accountSSOSql, ContainerUtil containerUtil,
			AccountInitFactory accountInitFactory, Constants constants) {
		super(jdbcTempSource, accountSSOSql, containerUtil, accountInitFactory, constants);
		this.secondCache = new LRUCache("cache.xml");
	}

	@Around()
	public Account getAccount(String userId) {
		Account account = (Account) secondCache.get(userId);
		if (account == null) {
			account = super.getAccount(userId);
			secondCache.put(userId, account);
		}
		return account;
	}

	public Account getAccountByName(String username) {
		Account account = super.getAccountByName(username);
		if (account == null)
			return null;
		return getAccount(account.getUserId());
	}

	public Account getAccountByEmail(String email) {
		Account account = super.getAccountByEmail(email);
		if (account == null)
			return null;
		return getAccount(account.getUserId());
	}

}
