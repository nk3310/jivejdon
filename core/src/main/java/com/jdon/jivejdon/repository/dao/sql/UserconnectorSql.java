/*
 * Copyright 2003-2009 the original author or authors.
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

import com.jdon.annotation.Component;
import com.jdon.jivejdon.manager.weibo.SinaWeiboUserPwd;
import com.jdon.jivejdon.repository.Userconnector;

@Component
public class UserconnectorSql implements Userconnector {
	private final static Logger logger = Logger.getLogger(UserconnectorSql.class);

	private JdbcTempSource jdbcTempSource;

	public UserconnectorSql(JdbcTempSource jdbcTempSource) {
		super();
		this.jdbcTempSource = jdbcTempSource;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.dao.sql.Userconnector#saveSinaWeiboUserconn
	 * (java.lang.String, com.jdon.jivejdon.manager.weibo.SinaWeiboUserPwd)
	 */
	@Override
	public void saveSinaWeiboUserconn(String userId, SinaWeiboUserPwd sinaWeiboUserPwd) {
		List queryParams = new ArrayList();
		if (sinaWeiboUserPwd.isEmpty())
			return;
		try {
			String ADD_SQL = "REPLACE INTO userconnector(userId, conntype, connuser, connpasswd)" + " VALUES (?,?,?,?)";
			queryParams.add(userId);
			queryParams.add(sinaWeiboUserPwd.getType());
			queryParams.add(sinaWeiboUserPwd.getUserId());
			queryParams.add(sinaWeiboUserPwd.getPasswdEncode());
			jdbcTempSource.getJdbcTemp().operate(queryParams, ADD_SQL);
		} catch (Exception e) {
			if (e.getMessage().indexOf("SQLException") != -1)
				creatTable();
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.dao.sql.Userconnector#removeSinaWeiboUserconn
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public void removeSinaWeiboUserconn(String userId, String type) {
		List queryParams = new ArrayList();
		try {
			String DEL_SQL = "DELETE FROM userconnector WHERE userId=? and conntype=?";
			queryParams.add(userId);
			queryParams.add(type);
			jdbcTempSource.getJdbcTemp().operate(queryParams, DEL_SQL);
		} catch (Exception e) {
			if (e.getMessage().indexOf("SQLException") != -1)
				creatTable();
			logger.error(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.dao.sql.Userconnector#loadSinaWeiboUserconn
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public SinaWeiboUserPwd loadSinaWeiboUserconn(String userId, String type) {
		logger.debug("enter loadForum for id:" + userId);
		String LOAD_SQL = "SELECT userId, conntype, connuser, connpasswd FROM userconnector WHERE userId=? and conntype=?";
		List queryParams = new ArrayList();
		queryParams.add(userId);
		queryParams.add(type);

		SinaWeiboUserPwd sinaWeiboUserPwd = null;
		try {
			List list = jdbcTempSource.getJdbcTemp().queryMultiObject(queryParams, LOAD_SQL);
			Iterator iter = list.iterator();
			if (iter.hasNext()) {
				Map map = (Map) iter.next();
				sinaWeiboUserPwd = new SinaWeiboUserPwd((String) map.get("connuser"), (String) map.get("connpasswd"));
				sinaWeiboUserPwd.setPasswdDecode((String) map.get("connpasswd"));
				sinaWeiboUserPwd.setType((String) map.get("conntype"));
			}
		} catch (Exception e) {
			if (e.getMessage().indexOf("SQLException") != -1)
				creatTable();
			logger.error(e);
		}
		return sinaWeiboUserPwd;
	}

	private void creatTable() {

		try {
			String createTable = "create table userconnector (userId varchar (100), conntype varchar (100),connuser  varchar (100),connpasswd varchar (100)); ";
			jdbcTempSource.getJdbcTemp().operate(new ArrayList(), createTable);
			String createIndex = "CREATE INDEX userId ON userconnector (userId, conntype)";
			jdbcTempSource.getJdbcTemp().operate(new ArrayList(), createIndex);
		} catch (Exception ex) {
			logger.error("create table userconnector schema error, manual create it" + ex);
		}

	}

}
