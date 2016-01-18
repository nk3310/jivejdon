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
public class TreeModel {

	private final long rootKey;
	private final NodeLongKeys keys;
	private final BinaryTreeAlgorithm bta;
	private final Map nodesCache;

	/**
	 * @param branchLongKeys
	 * @param branchAlgorithm
	 */
	public TreeModel(long rootKey, int size) {
		this.rootKey = rootKey;
		this.keys = new NodeLongKeys(rootKey, size);
		this.bta = new BinaryTreeAlgorithm(size);
		this.nodesCache = new HashMap(size);
	}

	public TreeModel(long rootKey) {
		this.rootKey = rootKey;
		this.keys = new NodeLongKeys(rootKey);
		this.bta = new BinaryTreeAlgorithm();
		this.nodesCache = new HashMap();
	}

	/**
	 * Adds a child to the tree.
	 * 
	 * @param parentKey
	 *            the parent to add the new value to.
	 * @param newKey
	 *            new value to add to the tree.
	 */
	public synchronized void addChild(long parentKey, long newKey) {
		try {
			// Find record for parent
			int parentIndex = findKey(parentKey, (char) 1);
			if (parentIndex == 0) {
				throw new IllegalArgumentException("Parent key " + parentKey + " not found when adding child " + newKey + ".");
			}

			// Create record for new key.
			int nextIndex = bta.getNextIndex();
			keys.setKey(nextIndex, newKey);
			bta.setNull(nextIndex);

			// Adjust references. Check to see if the parent has any children.
			if (bta.getLeftChild(parentIndex) == 0) {
				// No children, therefore make the new key the first child.
				bta.setLeftChild(parentIndex, nextIndex);
				;
			} else {
				// The parent has children, so find the right-most child.
				long siblingIndex = bta.getLeftChild(parentIndex);
				while (bta.getRightSibling(new Long(siblingIndex).intValue()) != 0) {
					siblingIndex = bta.getRightSibling(new Long(siblingIndex).intValue());
				}
				// Add the new entry as a sibling of that last child.
				bta.setRightSibling(new Long(siblingIndex).intValue(), nextIndex);
			}

			// Finally, increment nextIndex so it's ready for next add.
			nextIndex++;
			bta.setNextIndex(nextIndex);
		} catch (Exception e) {
			System.err.print("error in TreeModel:" + e);
		} finally {
		}
	}

	/**
	 * Returns the index of the specified value, or 0 if the key could not be
	 * found. Tail recursion was removed, but not the other recursive step.
	 * Using a stack instead often isn't even faster under Java.
	 * 
	 * @param value
	 *            the key to search for.
	 * @param startIndex
	 *            the index in the tree to start searching at. Pass in the root
	 *            index to search the entire tree.
	 */
	public int findKey(long value, int startIndex) {
		try {
			if (startIndex == 0) {
				return 0;
			}

			if (keys.getKey(startIndex) == value) {
				return startIndex;
			}

			int siblingIndex = bta.getLeftChild(startIndex);
			while (siblingIndex != 0) {
				int recursiveIndex = findKey(value, siblingIndex);
				if (recursiveIndex != 0) {
					return recursiveIndex;
				} else {
					siblingIndex = bta.getRightSibling(siblingIndex);
				}
			}
			return 0;
		} finally {
		}
	}

	/**
	 * Returns a parent of <code>childKey</code>.
	 */
	public long getParent(long childKey) {
		// If the root key was passed in, return -1;
		if (keys.getKey(1) == childKey) {
			return -1;
		}

		// Otherwise, perform a search to find the parent.
		int childIndex = findKey(childKey, (int) 1);
		if (childIndex == 0) {
			return -1;
		}

		// Adjust the childIndex pointer until we find the left most sibling of
		// childKey.
		int leftSiblingIndex = getLeftSiblingIndex(childIndex);
		while (leftSiblingIndex != 0) {
			childIndex = leftSiblingIndex;
			leftSiblingIndex = getLeftSiblingIndex(childIndex);
		}

		// Now, search the leftChildren array until we find the parent of
		// childIndex. First, search backwards from childIndex.
		for (int i = childIndex - 1; i >= 0; i--) {
			if (bta.getLeftChild(i) == childIndex) {
				return keys.getKey(i);
			}
		}

		// Now, search forward from childIndex.
		for (int i = childIndex + 1; i <= bta.getLeftChildren().size(); i++) {
			if (bta.getLeftChild(i) == childIndex) {
				return keys.getKey(i);
			}
		}

		// We didn't find the parent, so giving up. This shouldn't happen.
		return -1;
	}

	/**
	 * Returns a child of <code>parentKey</code> at index <code>index</code>.
	 */
	public long getChild(long parentKey, int index) {
		int parentIndex = findKey(parentKey, (char) 1);
		if (parentIndex == 0) {
			return -1;
		}

		int siblingIndex = bta.getLeftChild(parentIndex);
		if (siblingIndex == -1) {
			return -1;
		}
		int i = index;
		while (i > 0) {
			siblingIndex = bta.getRightSibling(siblingIndex);
			if (siblingIndex == 0) {
				return -1;
			}
			i--;
		}
		return keys.getKey(siblingIndex);
	}

	/**
	 * Returns the number of children of <code>parentKey</code>.
	 */
	public int getChildCount(long parentKey) {
		int count = 0;
		int parentIndex = findKey(parentKey, (char) 1);
		if (parentIndex == 0) {
			return 0;
		}
		int siblingIndex = bta.getLeftChild(parentIndex);
		while (siblingIndex != 0) {
			count++;
			siblingIndex = bta.getRightSibling(siblingIndex);
		}
		return count;
	}

	/**
	 * Returns an array of the children of the parentKey, or an empty array if
	 * there are no children or the parent key is not in the tree.
	 * 
	 * @param parentKey
	 *            the parent to get the children of.
	 * @return the children of parentKey
	 */
	public long[] getChildren(long parentKey) {
		int childCount = getChildCount(parentKey);
		if (childCount == 0) {
			return new long[0];
		}

		long[] children = new long[childCount];

		int i = 0;
		int parentIndex = findKey(parentKey, (char) 1);
		int siblingIndex = bta.getLeftChild(parentIndex);
		while (siblingIndex != 0) {
			children[i] = keys.getKey(siblingIndex);
			i++;
			siblingIndex = bta.getRightSibling(siblingIndex);
		}
		return children;
	}

	/**
	 * Returns the index of <code>childKey</code> in <code>parentKey</code>
	 * or -1 if <code>childKey</code> is not a child of <code>parentKey</code>.
	 */
	public int getIndexOfChild(long parentKey, long childKey) {
		int parentIndex = findKey(parentKey, (int) 1);

		int index = 0;

		int siblingIndex = bta.getLeftChild(new Long(parentIndex).intValue());
		if (siblingIndex == 0) {
			return -1;
		}
		while (keys.getKey(siblingIndex) != childKey) {
			index++;
			siblingIndex = bta.getRightSibling(siblingIndex);
			if (siblingIndex == 0) {
				return -1;
			}
		}
		return index;
	}

	/**
	 * Returns the depth in the tree that the element can be found at or -1 if
	 * the element is not in the tree. For example, the root element is depth 0,
	 * direct children of the root element are depth 1, etc.
	 * 
	 * @param key
	 *            the key to find the depth for.
	 * @return the depth of <tt>key</tt> in the tree hiearchy.
	 */
	public int getDepth(long key) {
		int[] depth = { 0 };
		if (findDepth(key, (char) 1, depth) == 0) {
			return -1;
		}
		return depth[0];
	}

	/**
	 * Returns true if the tree node is a leaf.
	 * 
	 * @return true if <code>key</code> has no children.
	 */
	public boolean isLeaf(long key) {
		int keyIndex = findKey(key, (char) 1);
		if (keyIndex == 0) {
			return false;
		}
		return (bta.getLeftChild(keyIndex) == 0);
	}

	/**
	 * Returns the keys in the tree.
	 */
	public long[] keys() {
		long[] k = new long[bta.getNextIndex() - 1];
		for (int i = 0; i < k.length; i++) {
			k[i] = keys.getKey(i);
		}
		return k;
	}

	/**
	 * Identical to the findKey method, but it also keeps track of the depth.
	 */
	private int findDepth(long value, int startIndex, int[] depth) {
		if (startIndex == 0) {
			return 0;
		}

		if (keys.getKey(startIndex) == value) {
			return startIndex;
		}

		int siblingIndex = bta.getLeftChild(startIndex);
		while (siblingIndex != 0) {
			depth[0]++;
			int recursiveIndex = findDepth(value, siblingIndex, depth);
			if (recursiveIndex != 0) {
				return recursiveIndex;
			} else {
				depth[0]--;
				siblingIndex = bta.getRightSibling(siblingIndex);
			}
		}
		return 0;
	}

	/**
	 * Returs the left sibling index of index. There is no easy way to find a
	 * left sibling. Therefore, we are forced to linearly scan the rightSiblings
	 * array until we encounter a reference to index. We'll make the assumption
	 * that entries are added in order since that assumption can yield big
	 * performance gain if it's true (and no real performance hit otherwise).
	 */
	private int getLeftSiblingIndex(int index) {
		// First, search backwards throw rightSiblings array
		for (int i = index - 1; i >= 0; i--) {
			if (bta.getRightSibling(i) == index) {
				return (char) i;
			}
		}

		// Now, search forwards
		for (int i = index + 1; i < bta.getRightSiblings().size(); i++) {
			if (bta.getRightSibling(i) == index) {
				return (char) i;
			}
		}

		// No sibling found, give up.
		return (char) 0;
	}

	/**
	 * @return Returns the branch.
	 */
	public BinaryTreeAlgorithm getBinaryTreeAlgorithm() {
		return bta;
	}

	/**
	 * @return Returns the keys.
	 */
	public NodeLongKeys getNodeLongKeys() {
		return keys;
	}

	/**
	 * @return Returns the rootKey.
	 */
	public long getRootKey() {
		return rootKey;
	}

	/**
	 * @return Returns the nodesCache.
	 */
	public Map getNodesCache() {
		return nodesCache;
	}
}
