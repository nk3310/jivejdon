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
package com.jdon.treepatterns;

import com.jdon.treepatterns.composite.Branch;
import com.jdon.treepatterns.composite.Leaf;
import com.jdon.treepatterns.model.TreeModel;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class TreeNodeFactory {
	private TreeModel treeModel;

	/**
	 * @param treeModel
	 */
	public TreeNodeFactory(TreeModel treeModel) {
		this.treeModel = treeModel;
	}

	public TreeNodeVisitable createNode(Long key) {
		TreeNodeVisitable treeNode = null;
		if (key == null)
			return treeNode;

		treeNode = (TreeNodeVisitable) treeModel.getNodesCache().get(key);
		if (treeNode != null) {
			return treeNode;
		}

		int index = treeModel.findKey(key.longValue(), (char) 1);
		if (index == 0) {
			return treeNode;
		}

		//no child , it is a leaf
		if (treeModel.getBinaryTreeAlgorithm().getLeftChild(index) == 0) {
			treeNode = createLeaf(key, index);
			treeModel.getNodesCache().put(key, treeNode);
			return treeNode;
		}

		treeNode = createBranch(key, index);
		treeModel.getNodesCache().put(key, treeNode);
		return treeNode;
	}

	private TreeNodeVisitable createLeaf(Long key, int index) {
		//Debug.useLog4J = false;

		int rightIndex = treeModel.getBinaryTreeAlgorithm().getRightSibling(index);
		Long rightChildKey = null;
		if (rightIndex != 0) {
			rightChildKey = new Long(treeModel.getNodeLongKeys().getKey(rightIndex));
		}
		return new Leaf(key, createNode(rightChildKey));
	}

	private TreeNodeVisitable createBranch(Long key, int index) {
		int leftChildIndex = treeModel.getBinaryTreeAlgorithm().getLeftChild(index);
		Long leftChildKey = new Long(treeModel.getNodeLongKeys().getKey(leftChildIndex));

		Long rightChildKey = null;
		int rightIndex = treeModel.getBinaryTreeAlgorithm().getRightSibling(index);
		if (rightIndex != 0) {
			rightChildKey = new Long(treeModel.getNodeLongKeys().getKey(rightIndex));
		}
		return new Branch(key, createNode(leftChildKey), createNode(rightChildKey));
	}
}
