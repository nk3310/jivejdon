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

import java.util.Iterator;
import java.util.List;

import com.jdon.treepatterns.model.TreeModel;
import com.jdon.treepatterns.visitor.RecursiveNodeWalker;
import com.jdon.treepatterns.visitor.TreeNodePicker;

/**
 * * 1000
 |-- 3000
 |-- |--4000
 |-- |--6000 
 |-- |-- |-- 6100
 |-- |-- |-- 6200 
 |-- |--6700
 |-- |--7000
 |-- |-- |--8000
 |-- |-- |-- |--9000
 |-- |-- |-- |--10000   
 |-- 5000 
 * 
 * @author <a href="mailto:banqiao@jdon.com">banq</a>
 *
 */
public class TreeTestMain {
    private final static String module = TreeTestMain.class.getName();


    public static void main(String[] args) {
        TreeModel treeBuilder = new TreeModel(1000, 12);

        treeBuilder.addChild(1000, 3000);
        treeBuilder.addChild(1000, 5000);
        treeBuilder.addChild(3000, 4000);
        treeBuilder.addChild(3000, 6000);
        treeBuilder.addChild(6000, 6100);
        treeBuilder.addChild(6000, 6200);
        treeBuilder.addChild(3000, 6700);
        treeBuilder.addChild(3000, 7000);
        treeBuilder.addChild(7000, 8000);
        treeBuilder.addChild(8000, 9000);
        treeBuilder.addChild(8000, 10000);


        TreeNodeFactory TreeNodeFactory = new TreeNodeFactory(treeBuilder);
        TreeNodeVisitable treeNode = TreeNodeFactory.createNode(new Long(1000));

        TreeVisitor visitor = new RecursiveNodeWalker();
        treeNode.accept(visitor);
        //test need create? add  Debug.useLog4J = false; to TreeNodeFactory create methods
        System.out.print("===================test cache=====");
        
        long parent = 3000;
        treeNode = TreeNodeFactory.createNode(new Long(parent));

        visitor = new TreeNodePicker();
        treeNode.accept(visitor);
        List list = ((TreeNodePicker)visitor).getResult();
        list.remove(new Long(parent));
        System.out.print(parent +" childern is below===== \n" );
        Iterator iter = list.iterator();
        while(iter.hasNext()){
            Long id = (Long)iter.next();
            System.out.print(" id=" + id);
        }
    }

}
