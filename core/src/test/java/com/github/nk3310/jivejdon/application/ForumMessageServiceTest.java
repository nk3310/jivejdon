package com.github.nk3310.jivejdon.application;

import com.jdon.controller.AppUtil;

import junit.framework.TestCase;

import com.github.nk3310.jivejdon.application.ForumMessageService;
import com.github.nk3310.jivejdon.domain.model.ForumMessage;


public class ForumMessageServiceTest  extends TestCase{
	
	 protected void setUp() throws Exception {
		 
	 }
	 
	 protected void tearDown() throws Exception {
		 
	 }
	 
	 
	 public void testcreateTopicMessage() throws Exception {
		 
		 AppUtil appUtil = new AppUtil();
		 
		 ForumMessageService  forumMessageService =  (ForumMessageService) appUtil.getComponentInstance("ForumMessageService");
		 
		 ForumMessage forumMessage = new ForumMessage();
		 
		 forumMessageService.createTopicMessage(forumMessage);
		 
		 
	 }
	 
	 
	 

}
