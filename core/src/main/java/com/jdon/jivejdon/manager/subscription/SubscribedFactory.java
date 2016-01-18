package com.jdon.jivejdon.manager.subscription;

import com.jdon.annotation.Component;
import com.jdon.jivejdon.model.Account;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumThread;
import com.jdon.jivejdon.model.ThreadTag;
import com.jdon.jivejdon.model.subscription.Subscription;
import com.jdon.jivejdon.model.subscription.messsage.AccountNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.TagNotifyMessage;
import com.jdon.jivejdon.model.subscription.messsage.ThreadNotifyMessage;
import com.jdon.jivejdon.model.subscription.subscribed.AccountSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ForumSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.Subscribed;
import com.jdon.jivejdon.model.subscription.subscribed.TagSubscribed;
import com.jdon.jivejdon.model.subscription.subscribed.ThreadSubscribed;
import com.jdon.jivejdon.repository.AccountFactory;
import com.jdon.jivejdon.repository.ForumFactory;
import com.jdon.jivejdon.repository.TagRepository;

@Component
public class SubscribedFactory {

	private ForumFactory forumAbstractFactory;
	private TagRepository tagRepository;
	private AccountFactory accountFactory;

	public SubscribedFactory(ThreadNotifyMessage threadNotifyMessageTemp, TagNotifyMessage tagNotifyMessageTemp,
			AccountNotifyMessage accountNotifyMessageTemp, ForumFactory forumAbstractFactory, TagRepository tagRepository,
			AccountFactory accountFactory) {
		this.forumAbstractFactory = forumAbstractFactory;
		this.tagRepository = tagRepository;
		this.accountFactory = accountFactory;
	}

	public static Subscribed createTransient(int subscribeType, Long subscribeId) {
		if (subscribeType == ForumSubscribed.TYPE) {
			Forum forum = new Forum();
			forum.setForumId(subscribeId);
			return new ForumSubscribed(forum);
		} else if (subscribeType == ThreadSubscribed.TYPE) {
			ForumThread thread = new ForumThread();
			thread.setThreadId(subscribeId);
			return new ThreadSubscribed(thread);
		} else if (subscribeType == TagSubscribed.TYPE) {
			ThreadTag tag = new ThreadTag();
			tag.setTagID(subscribeId);
			return new TagSubscribed(tag);
		} else if (subscribeType == AccountSubscribed.TYPE) {
			Account account = new Account();
			account.setUserIdLong(subscribeId);
			return new AccountSubscribed(account);
		} else
			return null;

	}

	public void embedFull(Subscription subscription) {
		int subscribeType = subscription.getSubscribeType();
		Subscribed subscribed = subscription.getSubscribed();
		if (subscribeType == ForumSubscribed.TYPE) {
			((ForumSubscribed) subscribed).setForum(forumAbstractFactory.getForum(subscribed.getSubscribeId()));
		} else if (subscribeType == ThreadSubscribed.TYPE) {
			try {
				((ThreadSubscribed) subscribed).setForumThread(forumAbstractFactory.getThread(subscribed.getSubscribeId()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (subscribeType == TagSubscribed.TYPE) {
			((TagSubscribed) subscribed).setTag(tagRepository.getThreadTag(subscribed.getSubscribeId()));
		} else if (subscribeType == AccountSubscribed.TYPE) {
			((AccountSubscribed) subscribed).setAccount(accountFactory.getFullAccount(subscribed.getSubscribeId().toString()));
		}
	}

}
