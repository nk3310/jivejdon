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
package com.jdon.jivejdon.service;

import com.jdon.controller.events.EventModel;
import com.jdon.controller.model.PageIterator;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface AccountService {

	com.jdon.jivejdon.model.Account initAccount(EventModel em);

	com.jdon.jivejdon.model.Account getAccountByName(String username);

	PageIterator getAccountByNameLike(String username, int start, int count);

	void createAccount(EventModel em);

	void updateAccount(EventModel em);

	void deleteAccount(EventModel em);

	PageIterator getAccounts(int start, int count);

	com.jdon.jivejdon.model.Account getAccount(Long userId);

	com.jdon.jivejdon.model.Account getloginAccount();

	// /account/forgetPasswd.shtml will directly activate this method
	void forgetPasswd(EventModel em);

	void updateAccountAttachment(EventModel em);

	boolean checkUser(String username);

	// only for admin update jive2.5
	void update();

}
