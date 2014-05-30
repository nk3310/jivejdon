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
package com.jdon.treepatterns.composite;

import com.jdon.treepatterns.TreeNodeVisitable;
import com.jdon.treepatterns.TreeVisitor;


/**
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class Branch implements TreeNodeVisitable{

    private Long key;
    private TreeNodeVisitable left;
    private TreeNodeVisitable right;
    
    /**
     * @param value
     * @param left
     * @param right
     */
    public Branch(Long key, TreeNodeVisitable left, TreeNodeVisitable right) {

        this.key = key;
        this.left = left;
        this.right = right;
    }
    


    /* (non-Javadoc)
     * @see com.jdon.jivejdon.service.tree.TreeComponent#Accept(com.jdon.jivejdon.service.tree.TreeVisitor)
     */
    public void accept(TreeVisitor visitor) {        
        visitor.visitforBranch(this);
    }
    
    public TreeNodeVisitable getRightSibling(){
        return right;
    }    
    
    

    /**
     * @return Returns the key.
     */
    public Long getKey() {
        return key;
    }
    /**
     * @param key The key to set.
     */
    public void setKey(Long key) {
        this.key = key;
    }
    /**
     * @return Returns the left.
     */
    public TreeNodeVisitable getLeft() {
        return left;
    }
    /**
     * @param left The left to set.
     */
    public void setLeft(TreeNodeVisitable left) {
        this.left = left;
    }
    /**
     * @return Returns the right.
     */
    public TreeNodeVisitable getRight() {
        return right;
    }
    /**
     * @param right The right to set.
     */
    public void setRight(TreeNodeVisitable right) {
        this.right = right;
    }
}
