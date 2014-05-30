package com.jdon.jivejdon.service.imp.upload;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.jdon.annotation.intercept.Poolable;
import com.jdon.annotation.intercept.SessionContextAcceptable;
import com.jdon.container.visitor.data.SessionContext;
import com.jdon.controller.events.EventModel;
import com.jdon.jivejdon.Constants;
import com.jdon.jivejdon.model.attachment.UploadFile;
import com.jdon.jivejdon.repository.UploadRepository;
import com.jdon.jivejdon.service.UploadService;
import com.jdon.util.UtilValidate;

@Poolable
public class UploadServiceShell implements UploadService {
	private final static Logger logger = Logger.getLogger(UploadServiceShell.class);

	private final static String UPLOAD_NAME = "UPLOAD";

	private final static int UPLOAD_QTY = 3;

	protected UploadRepository uploadRepository;

	protected SessionContext sc;

	public UploadServiceShell(UploadRepository uploadRepository) {
		this.uploadRepository = uploadRepository;

	}

	/*
	 * save the uploadfile to session becuase the messageId has not produce,
	 * when the message create operation is active, will call saveUploadFileToDB
	 * method
	 */
	public void saveUploadFile(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		logger.debug("UploadFile size =" + uploadFile.getSize());

		Long parentId = null;
		if (!UtilValidate.isEmpty(uploadFile.getParentId()))
			parentId = Long.parseLong(uploadFile.getParentId());

		addUploadFileSession(parentId, uploadFile, UPLOAD_QTY);
	}

	protected void addUploadFileSession(Long parentId, UploadFile uploadFile, int maxCount) {
		List<UploadFile> uploads = getUploadFilesFromSession(this.sc);
		if (uploads == null)
			uploads = initSession(parentId, this.sc);

		if (uploads.size() != 0 && uploads.size() >= maxCount) {
			uploads.remove(uploads.size() - 1);
		}
		uploads.add(uploadFile);
	}

	public void saveUploadFileNow(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		String pid = uploadFile.getParentId();
		Long parentId = Long.parseLong(pid);
		addUploadFileSession(parentId, uploadFile, 1);
		Collection uploads = getAllUploadFiles(parentId);
		try {
			uploadRepository.saveAllUploadFiles(pid, uploads);
			clearSession();
		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
		em.setModelIF(uploadFile);
	}

	public void updateUploadFileNow(EventModel em) {
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		String pid = uploadFile.getParentId();
		Long parentId = Long.parseLong(pid);
		addUploadFileSession(parentId, uploadFile, 1);
		Collection uploads = getAllUploadFiles(parentId);
		try {
			uploadRepository.updateAllUploadFiles(pid, uploads);
			clearSession();
		} catch (Exception e) {
			em.setErrors(Constants.ERRORS);
		}
		em.setModelIF(uploadFile);
	}

	public Collection getAllUploadFiles(Long messageId) {
		List<UploadFile> uploads = getUploadFilesFromSession(this.sc);
		if (uploads == null)
			uploads = initSession(messageId, this.sc);
		return uploads;
	}

	/**
	 * get all UploadFiles include session but not exclude the old
	 */
	public Collection getAllUploadFiles(SessionContext sessionContext) {
		logger.debug(" loadUploadFiles ");
		Collection re = getUploadFilesFromSession(sessionContext);
		return re;
	}

	public void removeUploadFile(EventModel em) {
		logger.debug(" uploadService.removeUploadFile ");
		UploadFile uploadFile = (UploadFile) em.getModelIF();
		List<UploadFile> uploads = getUploadFilesFromSession(this.sc);

		int IdIndex = Integer.parseInt(uploadFile.getId());
		if ((uploads.size() < IdIndex))
			return;
		uploads.remove(IdIndex);

	}

	public UploadFile getUploadFile(String objectId) {
		logger.debug("getUploadFile for id=" + objectId);
		UploadFile uf = getUploadFileFromSession(objectId);
		if (uf == null)
			uf = uploadRepository.getUploadFile(objectId);
		return uf;
	}

	private UploadFile getUploadFileFromSession(String objectId) {
		List<UploadFile> uploads = getUploadFilesFromSession(this.sc);
		if (uploads == null)
			return null;

		int IdIndex = Integer.parseInt(objectId);
		if ((uploads.size() < IdIndex))
			return null;

		return uploads.get(IdIndex);
	}

	private List<UploadFile> getUploadFilesFromSession(SessionContext sessionContext) {
		return (List) sessionContext.getArrtibute(UPLOAD_NAME);
	}

	private List<UploadFile> initSession(Long parentId, SessionContext sessionContext) {
		List<UploadFile> uploads = new ArrayList<UploadFile>();
		sessionContext.setArrtibute(UPLOAD_NAME, uploads);
		logger.debug("first time init ");
		if ((parentId != null) && (parentId.longValue() != 0)) {
			Collection dbList = uploadRepository.getUploadFiles(parentId.toString());
			Iterator iter = dbList.iterator();
			while (iter.hasNext()) {
				UploadFile uploadFile = (UploadFile) iter.next();
				uploads.add(uploadFile);
			}
		}
		return uploads;
	}

	public void clearSession(SessionContext sessionContext) {
		if (sessionContext != null)
			sessionContext.remove(UPLOAD_NAME);
	}

	public void clearSession() {
		if (this.sc != null)
			this.sc.remove(UPLOAD_NAME);
	}

	/**
	 * @return Returns the sessionContext.
	 */
	public SessionContext getSessionContext() {
		return sc;
	}

	/**
	 * @param sessionContext
	 *            The sessionContext to set.
	 */
	@SessionContextAcceptable
	public void setSessionContext(SessionContext sessionContext) {
		if (sessionContext != null)
			this.sc = sessionContext;
	}

}
