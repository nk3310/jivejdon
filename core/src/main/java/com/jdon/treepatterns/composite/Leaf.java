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
public class Leaf implements TreeNodeVisitable {
    
    private Long key;
    private TreeNodeVisitable rightSibling;
   
    /**
     * @param fm
     */
    public Leaf(Long key, TreeNodeVisitable rightSibling) {
        this.key = key;
        this.rightSibling = rightSibling;
       
    }
  

    /* (non-Javadoc)
     * @see com.jdon.jivejdon.service.tree.TreeComponent#Accept(com.jdon.jivejdon.service.tree.TreeVisitor)
     */
    public void accept(TreeVisitor visitor) {
        visitor.visitforLeaf(this);
    }
    
    public TreeNodeVisitable getRightSibling(){
        return rightSibling;
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
     * @param rightSibling The rightSibling to set.
     */
    public void setRightSibling(TreeNodeVisitable rightSibling) {
        this.rightSibling = rightSibling;
    }
}
