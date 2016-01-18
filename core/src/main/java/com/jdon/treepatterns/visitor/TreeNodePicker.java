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
package com.jdon.treepatterns.visitor;

import java.util.ArrayList;
import java.util.List;

import com.jdon.treepatterns.composite.Branch;
import com.jdon.treepatterns.composite.Leaf;
import com.jdon.util.Debug;

/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 * 
 */
public class TreeNodePicker extends RecursiveNodeWalker {
	private final static String module = TreeNodePicker.class.getName();
	private List result;

	/**
	 * @param result
	 */
	public TreeNodePicker() {
		this.result = new ArrayList();
	}

	public void visitforLeaf(Leaf leaf) {
		Debug.logVerbose(" leaf=" + leaf.getKey(), module);
		addToList(leaf.getKey());
	}

	public void visitforOneBranch(Branch branch) {
		Debug.logVerbose("  Branch=" + branch.getKey(), module);
		addToList(branch.getKey());
	}

	private void addToList(Long messageId) {
		result.add(messageId);
	}

	/**
	 * @return Returns the result.
	 */
	public List getResult() {
		return result;
	}
}
