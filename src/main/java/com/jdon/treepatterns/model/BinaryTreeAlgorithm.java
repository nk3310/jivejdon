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
package com.jdon.treepatterns.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class BinaryTreeAlgorithm {
	private final Map leftChildren;
	private final Map rightSiblings;

	// Pointer to next available slot.
	private volatile int nextIndex = 2;

	public BinaryTreeAlgorithm(int size) {
		this();
	}

	public BinaryTreeAlgorithm() {
		leftChildren = new HashMap();
		rightSiblings = new HashMap();

		// New tree, so set the fields to null at root.
		setNull(1);
	}

	public void setNull(int i) {
		leftChildren.put(i, (int)0);
		rightSiblings.put(i, (int)0);
	}

	/**
	 * @return Returns the leftChildren.
	 */
	public Map getLeftChildren() {
		return leftChildren;
	}

	public int getLeftChild(int i) {
		Integer ret = (Integer) leftChildren.get(i);
		if (ret == null)
			return 0;
		else
			return ret.intValue();
	}

	public void setLeftChild(int i, int value) {
		leftChildren.put(i, value);
	}

	/**
	 * @return Returns the nextIndex.
	 */
	public int getNextIndex() {
		return nextIndex;
	}

	/**
	 * @return Returns the rightSiblings.
	 */
	public Map getRightSiblings() {
		return rightSiblings;
	}

	public int getRightSibling(int i) {
		Integer ret = (Integer) rightSiblings.get(i);
		if (ret == null)
			return 0;
		else
			return ret.intValue();
	}

	public void setRightSibling(int i, int value) {
		rightSiblings.put(i, value);
	}

	/**
	 * @param nextIndex The nextIndex to set.
	 */
	public void setNextIndex(int nextIndex) {
		this.nextIndex = nextIndex;
	}
}
