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
package com.jdon.jivejdon.model.shortmessage;

import java.util.HashMap;
import java.util.Map;

import com.jdon.annotation.Introduce;
import com.jdon.annotation.model.Send;
import com.jdon.domain.message.DomainMessage;
import com.jdon.jivejdon.model.ForumMessage;

@Introduce("message")
public class ShortMPublisherRole {

	@Send("shortMessageService")
	public DomainMessage sendShortMessage(ForumMessage message, String toUsername) {
		Map commandReqs = new HashMap();
		commandReqs.put("name", "sendShortMessage");
		commandReqs.put("message", message);
		commandReqs.put("to", toUsername);
		return new DomainMessage(commandReqs);
	}
}
