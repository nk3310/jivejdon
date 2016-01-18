package com.jdon.treepatterns;

import com.jdon.treepatterns.model.TreeModel;
import com.jdon.treepatterns.visitor.RecursiveNodeWalker;

import junit.framework.TestCase;

public class TreeModelTest extends TestCase {

	TreeModel treeModel = new TreeModel(1000);

	protected void setUp() throws Exception {

		treeModel.addChild(1000, 3000);
		treeModel.addChild(1000, 5000);
		treeModel.addChild(3000, 4000);
		treeModel.addChild(3000, 6000);
		treeModel.addChild(6000, 6100);
		treeModel.addChild(6000, 6200);
		treeModel.addChild(3000, 6700);
		treeModel.addChild(3000, 7000);
		treeModel.addChild(7000, 8000);
		treeModel.addChild(8000, 9000);
		treeModel.addChild(8000, 10000);
		treeModel.addChild(8000, 20000);

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testWalker() {
		TreeNodeFactory TreeNodeFactory = new TreeNodeFactory(treeModel);
		TreeNodeVisitable treeNode = TreeNodeFactory.createNode(new Long(1000));

		TreeVisitor visitor = new RecursiveNodeWalker();
		treeNode.accept(visitor);
	}

}
