package com.jdon.jivejdon.repository.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.jdon.controller.model.PageIterator;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.jivejdon.repository.dao.SequenceDao;
import com.jdon.jivejdon.repository.dao.UploadFileDao;

public class UploadRepositoryDao implements UploadRepository {
	private final static Logger logger = Logger.getLogger(UploadRepositoryDao.class);

	private SequenceDao sequenceDao;
	private UploadFileDao uploadFileDao;

	public UploadRepositoryDao(SequenceDao sequenceDao, UploadFileDao uploadFileDao) {
		this.sequenceDao = sequenceDao;
		this.uploadFileDao = uploadFileDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#getUploadFiles(java.lang
	 * .String)
	 */
	public Collection getUploadFiles(String parentId) {
		Collection uploads = new ArrayList();
		Collection ids = uploadFileDao.getAdjunctIDs(parentId);
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			UploadFile uf = getUploadFile(id);
			uploads.add(uf);
		}
		return uploads;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#getSingleUploadFile(java
	 * .lang.String)
	 */
	public UploadFile getSingleUploadFile(String parentId) {
		Collection ids = uploadFileDao.getAdjunctIDs(parentId);
		Iterator iter = ids.iterator();
		UploadFile uf = null;
		if (iter.hasNext()) {
			String id = (String) iter.next();
			uf = getUploadFile(id);
		}
		return uf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jdon.jivejdon.repository.UploadRepository#getUploadFiles(int,
	 * int)
	 */
	public PageIterator getUploadFiles(int start, int count) {
		return uploadFileDao.getUploadFiles(start, count);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#getUploadFile(java.lang
	 * .String)
	 */
	public UploadFile getUploadFile(String objectId) {
		return uploadFileDao.getUploadFile(objectId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#saveAllUploadFiles(java
	 * .lang.String, java.util.Collection)
	 */
	public void saveAllUploadFiles(String parentId, Collection uploads) throws Exception {
		logger.debug(" prepareForSaveAllUploadFiles parentId=" + parentId);
		if (uploads == null)
			return;
		logger.debug(" uploads size=" + uploads.size());
		Iterator iter = uploads.iterator();
		while (iter.hasNext()) {
			UploadFile uploadFile = (UploadFile) iter.next();
			uploadFile.setParentId(parentId);
			saveUpload(uploadFile);
		}
	}

	private void saveUpload(UploadFile uploadFile) throws Exception {
		Long mIDInt = sequenceDao.getNextId(Constants.OTHERS);
		uploadFile.setId(mIDInt.toString());
		uploadFileDao.createUploadFile(uploadFile);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#updateAllUploadFiles(java
	 * .lang.String, java.util.Collection)
	 */
	public void updateAllUploadFiles(String parentId, Collection uploads) throws Exception {
		if (uploads == null)
			return;
		logger.debug(" mergeAllUploadFiles parentId=" + parentId);
		deleteAllUploadFiles(parentId);
		saveAllUploadFiles(parentId, uploads);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#deleteAllUploadFiles(java
	 * .lang.String)
	 */
	public void deleteAllUploadFiles(String parentId) {
		Collection ids = uploadFileDao.getAdjunctIDs(parentId);
		Iterator iter = ids.iterator();
		while (iter.hasNext()) {
			String id = (String) iter.next();
			deleteUploadFile(id);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jdon.jivejdon.repository.UploadRepository#deleteUploadFile(java.lang
	 * .String)
	 */
	public void deleteUploadFile(String objectId) {
		uploadFileDao.deleteUploadFile(objectId);
	}
}
