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
package com.jdon.jivejdon.repository.dao.sql;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.repository.dao.SequenceDao;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 *
 */
public class AccountSSOSql {
    private final static Logger logger = Logger.getLogger(AccountSSOSql.class);
    
    private JdbcTempSSOSource jdbcTempSSOSource;
    private SequenceDao sequenceDao;
    /**
     * @param jdbcTempSSOSource
     */
    public AccountSSOSql(JdbcTempSSOSource jdbcTempSSOSource, SequenceDao sequenceDao) {
        this.jdbcTempSSOSource = jdbcTempSSOSource;
        this.sequenceDao = sequenceDao;
    }
    
    public String getRoleNameFByusername(String username) {
        logger.debug("enter getAccountByName for username=" + username);
        String SQL =
            "SELECT RL.name, 'Roles' FROM role as RL, user as U ,  users_roles as RU WHERE U.userid = RU.userid and RU.roleid = RL.roleid  and U.name = ?";
        List queryParams = new ArrayList();
        queryParams.add(username);    
        String roleName = null;
        try {
            List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                logger.debug("found the role");
                Map map = (Map) iter.next();
                roleName = ((String) map.get("name")).trim();
            }
        } catch (Exception se) {
            logger.error(se);
        }
        return roleName;
    }
        
    public void embedPasswordassit(com.jdon.jivejdon.model.Account account){
        String SQL =
            "SELECT * from passwordassit where userId = ?";
        List queryParams = new ArrayList();
        queryParams.add(account.getUserId());    
        try {
            List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                logger.debug("found the passwordassit");
                Map map = (Map) iter.next();
                String passwdtype = ((String) map.get("passwdtype")).trim();
                account.setPasswdtype(passwdtype);
                String passwdanswer = ((String) map.get("passwdanswer")).trim();
                account.setPasswdanswer(passwdanswer);
            }
        } catch (Exception se) {
            logger.error(se);
        }
    }

    public void insertSSOServer(com.jdon.jivejdon.model.Account account) throws Exception {
        logger.debug("enter insertSSOServer");
        try {
            //the user has `` ,because mysql user is special
            String INSERT_USER = "REPLACE INTO `user` (userId,password,name,email) VALUES(?,?,?,?)";
            List queryParams = new ArrayList();
            queryParams.add(account.getUserId());
            queryParams.add(account.getPassword());
            queryParams.add(account.getUsername());
            queryParams.add(account.getEmail());

            jdbcTempSSOSource.getJdbcTemp().operate(queryParams, INSERT_USER);

            String INSERT_USER_ROLE = "REPLACE INTO users_roles(userId,roleId) VALUES(?,?)";
            List queryParams2 = new ArrayList();
            queryParams2.add(account.getUserId());
            String roleId = getRoleId(account.getRoleName());
            logger.debug(" the roleName: " + account.getRoleName() + " roleId =" + roleId);
            queryParams2.add(roleId);
            jdbcTempSSOSource.getJdbcTemp().operate(queryParams2, INSERT_USER_ROLE);
            
            
            String INSERT_PASSWORDASSIT = "REPLACE INTO passwordassit(userId,passwdtype,passwdanswer) VALUES(?,?,?)";
            List queryParams3 = new ArrayList();
            queryParams3.add(account.getUserId());
            queryParams3.add(account.getPasswdtype());
            queryParams3.add(account.getPasswdanswer());
            jdbcTempSSOSource.getJdbcTemp().operate(queryParams3, INSERT_PASSWORDASSIT);
            
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }
    
    private String getRoleId(String roleName) throws Exception {
        try {
          String  roleId = getRoleName(roleName);
          if (roleId == null) {
              roleId = createRole(roleName);
          }
          return roleId;
        } catch (Exception ex) {
          logger.error(ex);
          throw new Exception(ex);
        }
    }
    
    public String getRoleName(String roleName){
        String SQL = "SELECT roleId FROM role WHERE name=? ";
        List queryParams = new ArrayList();
        queryParams.add(roleName);
        String roleId = null;
        try {
            List list = jdbcTempSSOSource.getJdbcTemp().queryMultiObject(queryParams, SQL);
            Iterator iter = list.iterator();
            if (iter.hasNext()) {
                Map map = (Map) iter.next();
                roleId = ((String) map.get("roleId")).trim();
            }
        } catch (Exception se) {
            logger.warn(se);
        }
        return roleId;
    }
    
    private String createRole(String roleName) throws Exception {
        logger.debug("enter insertSSOServer");

        String INSERT_ROLE = "INSERT INTO role(roleId, name) VALUES(?,?)";
        String roleId = null;
        try {
            Long roleIDInt = sequenceDao.getNextId(Constants.USER);
            
            List queryParams = new ArrayList();
            roleId = roleIDInt.toString().trim();
            queryParams.add(roleId);
            queryParams.add(roleName);

            jdbcTempSSOSource.getJdbcTemp().operate(queryParams, INSERT_ROLE);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
        return roleId;
    }
    
    
    public void updateSSOServer(com.jdon.jivejdon.model.Account account) throws Exception{
        logger.debug("enter updayeSSOServer");
 
   
        try {
            String SAVE_USER =
                "UPDATE user SET password=?,name=?,email=? " +
                " WHERE userId=?";
            List queryParams = new ArrayList();
            queryParams.add(account.getPassword());
            queryParams.add(account.getUsername());
            queryParams.add(account.getEmail());
            queryParams.add(account.getUserIdLong());     
            jdbcTempSSOSource.getJdbcTemp().operate(queryParams, SAVE_USER);
            
            
            String UPDATE_PASSWORDASSIT = "UPDATE  passwordassit SET passwdtype=?,passwdanswer=? " +
                " WHERE userId=?";
            List queryParams3 = new ArrayList();
            queryParams3.add(account.getPasswdtype());
            queryParams3.add(account.getPasswdanswer());
            queryParams3.add(account.getUserId());            
            jdbcTempSSOSource.getJdbcTemp().operate(queryParams3, UPDATE_PASSWORDASSIT);
            
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }    
    
    
    public void deleteSSOServer(com.jdon.jivejdon.model.Account account) throws Exception{
        logger.debug("enter deleteSSOServer");
        String SQL = "DELETE FROM user WHERE userId=?";
        List queryParams = new ArrayList();
        queryParams.add(account.getUserId());        
        try {
            jdbcTempSSOSource.getJdbcTemp().operate(queryParams, SQL);
        } catch (Exception e) {
            logger.error(e);
            throw new Exception(e);
        }
    }        
    
   
    
    
}
