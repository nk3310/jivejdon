/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.presentation.servlet;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jdon.controller.WebAppUtil;
import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.query.MultiCriteria;
import com.jdon.jivejdon.service.ForumMessageQueryService;
import com.jdon.jivejdon.service.ForumMessageService;
import com.jdon.util.UtilValidate;
import com.sun.syndication.feed.synd.SyndCategory;
import com.sun.syndication.feed.synd.SyndCategoryImpl;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.SyndFeedOutput;

public class RSSGenServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String channel_title = "";
	private String channel_des = "";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String title = config.getInitParameter("title");
		if (!UtilValidate.isEmpty(title)) {
			channel_title = title;
		}

		String description = config.getInitParameter("description");
		if (!UtilValidate.isEmpty(description)) {
			channel_des = description;
		}

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			SyndFeed feed = new SyndFeedImpl();
			// rss_0.90, rss_0.91, rss_0.92, rss_0.93, rss_0.94, rss_1.0 rss_2.0
			// or atom_0.3
			String rssType = request.getParameter("rssType");
			if (UtilValidate.isEmpty(rssType)) {
				rssType = "rss_2.0";
			}
			feed.setFeedType(rssType);

			String startS = request.getParameter("start");
			if (UtilValidate.isEmpty(startS)) {
				startS = "0";
			}
			int start = Integer.parseInt(startS);

			String countS = request.getParameter("count");
			if (UtilValidate.isEmpty(countS)) {
				countS = "30";
			}
			int count = Integer.parseInt(countS);

			java.net.URL reconstructedURL = new java.net.URL(request.getScheme(), request.getServerName(), request.getServerPort(), request
					.getContextPath());
			String url = reconstructedURL.toString().replace(":80/", "/");

			feed.setTitle(channel_title);
			feed.setLink(url + "/");
			feed.setDescription(channel_des);
			feed.setEncoding("UTF-8");

			List entries = new ArrayList();

			MultiCriteria queryCriteria = new MultiCriteria();
			queryCriteria.setUserID(request.getParameter("userId"));
			queryCriteria.setFromDate("2000-01-01");// from begin
			ForumMessageQueryService forumMessageQueryService = (ForumMessageQueryService) WebAppUtil.getService("forumMessageQueryService", request);
			PageIterator pi = forumMessageQueryService.getMessages(queryCriteria, start, count);

			while (pi.hasNext()) {
				Long messageId = (Long) pi.next();
				ForumMessage message = getForumMessage(request, messageId);
				addMessage(url, entries, message, request);
			}

			feed.setEntries(entries);

			response.setCharacterEncoding("UTF-8");
			Writer writer = response.getWriter();
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ForumMessage getForumMessage(HttpServletRequest request, Long key) {
		ForumMessageService forumMessageService = (ForumMessageService) WebAppUtil.getService("forumMessageService", request);
		return forumMessageService.getMessage(key);
	}

	private void addMessage(String url, List entries, ForumMessage message, HttpServletRequest request) {
		try {
			SyndEntry entry = new SyndEntryImpl();
			entry.setTitle(message.getMessageVO().getSubject());
			entry.setLink(getItemLink(url, message));

			Date publishedDate = Constants.parseDateTime(message.getCreationDate());
			entry.setPublishedDate(publishedDate);
			entry.setAuthor(message.getAccount().getUsername());
			Date updateDate = Constants.parseDateTime(message.getModifiedDate());
			entry.setUpdatedDate(updateDate);

			SyndContent description = new SyndContentImpl();
			description.setType("text/html");
			description.setValue(message.getMessageVO().getShortBody(100));
			entry.setDescription(description);

			if (message.isRoot()) {
				List cats = new ArrayList();
				for (Object o : message.getForumThread().getTags()) {
					ThreadTag tag = (ThreadTag) o;
					SyndCategory cat = new SyndCategoryImpl();

					cat.setTaxonomyUri(url + "/tags/" + tag.getTagID());
					cat.setName(tag.getTitle());
					cats.add(cat);
				}
				entry.setCategories(cats);
			}
			entries.add(entry);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getItemLink(String url, ForumMessage message) {
		if (message.isRoot()) {
			String relativeLink = "/thread/" + message.getForumThread().getThreadId().toString();
			return url + relativeLink;

		} else {
			String relativeLink = "/thread/nav/" + message.getForumThread().getThreadId().toString() + "/" + message.getMessageId().toString();
			return url + relativeLink + "#" + message.getMessageId();
		}
	}

	public void destroy() {
		super.destroy();
	}
}