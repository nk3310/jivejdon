package com.jdon.jivejdon.repository;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.annotation.model.OnEvent;
import com.jdon.jivejdon.model.Forum;
import com.jdon.jivejdon.model.ForumMessage;
import com.jdon.jivejdon.model.ForumMessageReply;
import com.jdon.jivejdon.repository.builder.MessageRepositoryDao;
import com.jdon.jivejdon.service.util.JtaTransactionUtil;

@Component
public class MessageTransactionPersistence {
	private final static Logger logger = Logger.getLogger(MessageTransactionPersistence.class);

	protected JtaTransactionUtil jtaTransactionUtil;
	protected MessageRepositoryDao messageRepository;
	protected TagRepository tagRepository;
	protected ForumFactory forumAbstractFactory;

	public MessageTransactionPersistence(JtaTransactionUtil jtaTransactionUtil, MessageRepositoryDao messageRepository, TagRepository tagRepository,
			ForumFactory forumAbstractFactory) {
		super();
		this.jtaTransactionUtil = jtaTransactionUtil;
		this.messageRepository = messageRepository;
		this.tagRepository = tagRepository;
		this.forumAbstractFactory = forumAbstractFactory;
	}

	@OnEvent("addTopicMessage")
	public ForumMessage insertTopicMessage(ForumMessage forumMessage) throws Exception {
		logger.debug("enter createTopicMessage");
		try {
			jtaTransactionUtil.beginTransaction();
			messageRepository.createTopicMessage(forumMessage);
			logger.debug("createTopicMessage ok!");
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " createTopicMessage forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
		return forumMessage;
	}

	public void insertReplyMessage(ForumMessageReply forumMessageReply) throws Exception {
		logger.debug("enter createReplyMessage");
		if ((forumMessageReply.getParentMessage() == null) || (forumMessageReply.getParentMessage().getMessageId() == null)) {
			logger.error("parentMessage is null, this is not reply message!");
			return;
		}
		try {
			jtaTransactionUtil.beginTransaction();
			messageRepository.createReplyMessage(forumMessageReply);
			logger.debug("createReplyMessage ok!");
			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " createTopicMessage forumMessageId=" + forumMessageReply.getParentMessage().getMessageId();
			logger.error(error);
			throw new Exception(error);
		}

	}

	public void updateMessage(ForumMessage forumMessage) throws Exception {

		logger.debug("enter updateMessage");
		try {
			// merge
			jtaTransactionUtil.beginTransaction();
			messageRepository.updateMessage(forumMessage);

			// update the forumThread's updatetime
			messageRepository.updateThread(forumMessage.getForumThread());

			jtaTransactionUtil.commitTransaction();
		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " updateMessage forumMessageId=" + forumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}

	}

	public void moveMessage(ForumMessage forumMessage, Long forumId) throws Exception {
		Forum newForum = this.forumAbstractFactory.getForum(forumId);
		if (newForum != null) {
			Long messageId = forumMessage.getMessageId();
			Long threadId = forumMessage.getForumThread().getThreadId();
			messageRepository.getMessageDaoFacade().getMessageDao().updateMovingForum(messageId, threadId, forumId);
		}
	}

	@OnEvent("deleteMessage")
	public ForumMessage deleteMessage(ForumMessage delforumMessage) throws Exception {
		logger.debug("enter deleteMessage");
		try {
			jtaTransactionUtil.beginTransaction();
			this.messageRepository.deleteMessageComposite(delforumMessage);

			// if the root message was deleted, the thread that it be in
			// will all be deleted
			if (delforumMessage.getMessageId().longValue() == delforumMessage.getForumThread().getRootMessage().getMessageId().longValue()) {
				logger.debug("1. it is a root message, delete the forumThread");
				tagRepository.deleteTagTitle(delforumMessage.getForumThread().getThreadId());
				messageRepository.deleteThread(delforumMessage.getForumThread());
			}

			jtaTransactionUtil.commitTransaction();

		} catch (Exception e) {
			jtaTransactionUtil.rollback();
			String error = e + " deleteMessage forumMessageId=" + delforumMessage.getMessageId();
			logger.error(error);
			throw new Exception(error);
		}
		return delforumMessage;
	}

}
