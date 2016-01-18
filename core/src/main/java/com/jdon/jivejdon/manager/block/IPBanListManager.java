/*
 * Copyright 2007 the original author or jdon.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.jdon.jivejdon.manager.block;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Collection;

import org.apache.log4j.Logger;

import com.jdon.jivejdon.repository.dao.SetupDao;
import com.jdon.util.task.TaskEngine;

/**
 * From Apache Roller
 * 
 * Represents a list of banned ip addresses.
 * 
 * ip can be 202.1.2.3 or can be 202.1.*.* or 202.1.2.* or 202.*.*.*
 * 
 * modified by banQ
 * 
 * This base implementation gets its list from a file on the filesystem. We are
 * also aware of when the file changes via some outside source and we will
 * automatically re-read the file and update the list when that happens.
 * 
 * 
 */
public class IPBanListManager implements Runnable, IPBanListManagerIF {
	private final static Logger log = Logger.getLogger(IPBanListManager.class);

	private final String PERSISTENCE_NAME = "IPBANLIST";

	private final IPHolder ipHolder;

	private boolean myLastModified = false;

	// file listing the ips that are banned
	private SetupDao setupDao;

	public IPBanListManager(SetupDao setupDao) {
		this.setupDao = setupDao;
		log.debug("INIT");
		ipHolder = new IPHolder();
		this.loadBannedIps();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.block.IPBanListManagerIF#isBanned(java.lang
	 * .String)
	 */
	public boolean isBanned(String remoteIp) {
		// update the banned ips list if needed
		this.loadBannedIpsIfNeeded(false);
		if (remoteIp != null) {
			return this.ipHolder.isBanned(remoteIp);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.block.IPBanListManagerIF#addBannedIp(java.lang
	 * .String)
	 */
	public void addBannedIp(String ip) {
		if (ip == null || ip.equals("localhost") || ip.equals("127.0.0.1")) {
			return;
		}
		// update the banned ips list if needed
		this.loadBannedIpsIfNeeded(false);
		try {
			ipHolder.addressAdd(ip);

			TaskEngine.addTask(this);
			log.error("ADDED " + ip);
		} catch (Exception e) {
			log.error("Error adding banned ip ", e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.manager.block.IPBanListManagerIF#deleteBannedIp(java
	 * .lang.String)
	 */
	public void deleteBannedIp(String ip) {
		if (ip == null) {
			return;
		}
		// update the banned ips list if needed
		this.loadBannedIpsIfNeeded(false);
		try {
			// remove
			ipHolder.addressRemove(ip);

			TaskEngine.addTask(this);
			log.debug("DEL " + ip);
		} catch (Exception e) {
			log.error("Error delete banned ip ", e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.manager.block.IPBanListManagerIF#getAllBanIpList()
	 */
	public Collection getAllBanIpList() {
		this.loadBannedIpsIfNeeded(false);
		return ipHolder.getAllBanIpList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.manager.block.IPBanListManagerIF#clear()
	 */
	public void clear() {
		myLastModified = true;
		ipHolder.clear();
	}

	public void run() { // TaskEngine.addTask call
		saveBanIpList();
		log.info("save ip finished!");
	}

	private void saveBanIpList() {
		try {
			String saveS = ipHolder.addressLoad();
			setupDao.saveSetupValue(PERSISTENCE_NAME, saveS);
			myLastModified = true;
		} finally {
		}
	}

	/**
	 * Load the list of banned ips from a file. This clears the old list and
	 * loads exactly what is in the file.
	 */
	private synchronized void loadBannedIps() {

		try {
			// sort is low performance
			// Set newBannedIpList = new TreeSet(new StringSortComparator());

			String ipListText = setupDao.getSetupValue(PERSISTENCE_NAME);
			BufferedReader in = new BufferedReader(new StringReader(ipListText));

			String ip = null;
			while ((ip = in.readLine()) != null) {
				ipHolder.addressAdd(ip);
				log.debug("READED " + ip);
			}

			in.close();

			// list updated, reset modified file
			// this.bannedIps = newBannedIpList;
		} catch (Exception ex) {
			log.error("Error loading banned ips from file", ex);
		}

	}

	/**
	 * Check if the banned ips file has changed and needs to be reloaded.
	 */
	private void loadBannedIpsIfNeeded(boolean forceLoad) {

		if (myLastModified || forceLoad) {
			// need to reload
			this.loadBannedIps();
			myLastModified = false;
		}
	}

}
