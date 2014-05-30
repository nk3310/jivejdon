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
package com.jdon.jivejdon.model;

import com.jdon.annotation.Model;
import com.jdon.domain.message.DomainMessage;
import com.jdon.treepatterns.TreeNodeFactory;
import com.jdon.treepatterns.TreeNodeVisitable;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.model.TreeModel;

/**
 * ForumThreadTreeModel lifecycle is not same as ForumThreadState
 * 
 * @author banq
 * 
 */
@Model
public class ForumThreadTreeModel {
	private ForumThread forumThread;

	private DomainMessage treeModelMessage;

	public ForumThreadTreeModel(ForumThread forumThread) {
		super();
		this.forumThread = forumThread;
	}

	/**
	 * blocking waiting TreeModel loaded
	 * 
	 * @return Returns the treeModel.
	 */
	public TreeModel getTreeModel() {
		preload();
		return (TreeModel) treeModelMessage.getBlockEventResult();
	}

	public void preload() {
		if (treeModelMessage == null)
			treeModelMessage = forumThread.lazyLoaderRole.loadTreeModel(forumThread);
	}

	public void addChildAction(ForumMessageReply forumMessageReply) {
		getTreeModel().addChild(forumMessageReply.getParentMessage().getMessageId(), forumMessageReply.getMessageId());
	}

	public ForumThread getForumThread() {
		return forumThread;
	}

	public void acceptVisitor(Long startNode, TreeVisitor treeVisitor) {
		TreeNodeFactory TreeNodeFactory = new TreeNodeFactory(getTreeModel());
		TreeNodeVisitable treeNode = TreeNodeFactory.createNode(startNode);
		treeNode.accept(treeVisitor);
	}

}
