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
import com.jdon.jivejdon.model.AccountProfile;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
public interface AccountProfileService {

	/**
	 * get Account Profile of any user.
	 * 
	 * @param user
	 *            is userId or user name
	 * @return
	 */
	AccountProfile getAccountProfile(String user);

	void updateAccountProfile(EventModel em);

	void emailValidate(EventModel em);

}
