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
public class NodeLongKeys {

    private Map keys;
    
    public NodeLongKeys(long rootKey, int size) {
        keys = new HashMap(); 
        keys.put(1, rootKey);
    }
    
    public NodeLongKeys(long rootKey) {
        keys = new HashMap(); 
        keys.put(1, rootKey);
    }
    
    /**
     * @return Returns the keys.
     */
    public Map getKeys() {
        return keys;
    }
    
    public long getKey(int i) {
        return (Long)keys.get(i);
    }    
    
    public void setKey(int i, long value){
    	keys.put(i, value);
        
    }
    
}
