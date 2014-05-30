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

import java.util.Collection;
import java.util.Iterator;

import com.jdon.treepatterns.TreeNodeVisitable;
import com.jdon.treepatterns.TreeVisitor;
import com.jdon.treepatterns.composite.Branch;
import com.jdon.treepatterns.composite.Leaf;

/**
 * a walker aross the whole tree.
 * we can extends this class, and implements visitforOneBranch and visitforLeaf
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class RecursiveNodeWalker implements TreeVisitor{
    
    public void visitCollection(Collection list){
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            TreeNodeVisitable node = (TreeNodeVisitable)iter.next();
            node.accept(this);
        }
    }

    /* (non-Javadoc)
     * @see com.jdon.treepatterns.TreeVisitor#deleteforBranch(com.jdon.treepatterns.composite.Branch)
     */
    public void visitforBranch(Branch branch) {           
        visitforOneBranch(branch);
        recursiveDeep(branch);
    }
    
    private void recursiveDeep(Branch branch){
        TreeNodeVisitable child = branch.getLeft();
        // 2.  the Id's childern
        child.accept(this);
        
        // 3. the Id's childern 's siblings        
        while ((child = child.getRightSibling()) != null){
            child.accept(this);
        }             
    }

    /* 
     * need sub class to override it 
     */
    public void visitforLeaf(Leaf leaf) {
        System.out.print(" leaf=" + leaf.getKey());
    }
    
    /**
     *need sub class to override it 
     */
    public void visitforOneBranch(Branch branch){
    	System.out.print("  Branch=" + branch.getKey());
    }
    
    
    

}
