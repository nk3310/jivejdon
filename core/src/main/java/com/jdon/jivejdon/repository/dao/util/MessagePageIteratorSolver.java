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
package com.jdon.jivejdon.repository.dao.util;

import com.jdon.jivejdon.repository.dao.sql.JdbcTempSource;
import com.jdon.jivejdon.util.ContainerUtil;
import com.jdon.model.query.PageIteratorSolver;

/**
 * MessageDaoSql and MessageQueryDaoSql share this class,
 * so share one PageIteratorSolver.
 * when create/update happened in MessageDaoSql, it can clear
 * the cache in PageIteratorSolver that shared with MessageQueryDaoSql,
 * there are many batch inquiry operations in MessageQueryDaoSql.
 * so, that will refresh the result of batch inquiry
 * @author <a href="mailto:banqJdon<AT>jdon.com">banq</a>
 *
 */
public class MessagePageIteratorSolver {
    
    private PageIteratorSolver pageIteratorSolver;
    
    private ContainerUtil containerUtil;
    
    


    /**
     * @param pageIteratorSolver
     * @param containerUtil
     */
    public MessagePageIteratorSolver(JdbcTempSource jdbcTempSource, ContainerUtil containerUtil) {
        this.pageIteratorSolver = new PageIteratorSolver(jdbcTempSource.getDataSource(), containerUtil.getCacheManager());
        this.containerUtil = containerUtil;
    }
       
    
    /**
     * @return Returns the containerUtil.
     */
    public ContainerUtil getContainerUtil() {
        return containerUtil;
    }
    /**
     * @param containerUtil The containerUtil to set.
     */
    public void setContainerUtil(ContainerUtil containerUtil) {
        this.containerUtil = containerUtil;
    }
    /**
     * @return Returns the pageIteratorSolver.
     */
    public PageIteratorSolver getPageIteratorSolver() {
        return pageIteratorSolver;
    }
    /**
     * @param pageIteratorSolver The pageIteratorSolver to set.
     */
    public void setPageIteratorSolver(PageIteratorSolver pageIteratorSolver) {
        this.pageIteratorSolver = pageIteratorSolver;
    }
}
