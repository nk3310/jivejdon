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
package com.jdon.jivejdon.model.realtime;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.jdon.jivejdon.model.realtime.notification.ContentFormatConverter;
import com.jdon.jivejdon.model.util.AutoDiscardingDeque;

public class NotificationLocator {

	private AutoDiscardingDeque<Notification> notifications;
	// key: userId value Date LastNotificationAcceptTimestamp
	private Map<String, Notification> userLastNotifications;

	private ContentFormatConverter contentFormatConverter;

	public NotificationLocator(ContentFormatConverter contentFormatConverter) {
		this.notifications = new AutoDiscardingDeque(100);
		this.userLastNotifications = new HashMap();
		this.contentFormatConverter = contentFormatConverter;
	}

	public void addNotification(Notification notification) {
		notifications.offerFirst(notification);
	}

	public Notification checkNotification(String username) {
		Notification userLastNotification = userLastNotifications.get(username);
		Notification notification = notifications.peek();
		if (userLastNotification == null && notification == null)
			return null;
		Date nowD = new Date();
		if (userLastNotification != null && notification == null) {
			if (nowD.after(userLastNotification.getDeadDate())) {
				userLastNotifications.remove(username);
				return null;
			} else {
				return userLastNotification;
			}
		}

		// userLastNotification != null && notification != null or
		// userLastNotification == null && notification != null
		if (nowD.after(notification.getDeadDate())) {
			notifications.remove(notification);
			return null;
		}

		notification = contentFormatConverter.convertContent(notification);
		userLastNotifications.put(username, notification);
		return notification;

	}

	// remove by com.jdon.jivejdon.presentation.listener.UserCounterListener
	public void removeUser(String username) {
		userLastNotifications.remove(username);

	}

	public void clear() {
		notifications.clear();
	}

}
