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
package com.jdon.jivejdon.manager.throttle.post;

import java.util.Date;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.Account;

@Component
public class Throttler {

	protected final ThrottleManager throttleManager;
	private final ThrottleConf throttleConf;
	private final NewUserThrottleConf newUserThrottleConf;

	public Throttler(ThrottleManager throttleManager, ThrottleConf throttleConf, NewUserThrottleConf newUserThrottleConf) {
		super();
		this.throttleManager = throttleManager;
		this.throttleConf = throttleConf;
		this.newUserThrottleConf = newUserThrottleConf;

	}

	public boolean checkValidate(Account account) {
		Date nowD = new Date();
		long diffs = (nowD.getTime() - account.getCreationDate2().getTime()) / 1000;
		if (diffs < newUserThrottleConf.getNewUserThreshold()) {
			throttleManager.setThreshold(newUserThrottleConf.getThreshold());
			throttleManager.setInterval(newUserThrottleConf.getInterval());
		} else {
			throttleManager.setThreshold(throttleConf.getThreshold());
			throttleManager.setInterval(throttleConf.getInterval());
		}
		return throttleManager.checkValidate(account.getPostIP());

	}

	public boolean isAbusive(String ip) {
		return throttleManager.isAbusive(ip);
	}

}
