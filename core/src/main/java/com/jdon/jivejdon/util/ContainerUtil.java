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
package com.jdon.jivejdon.util;

import org.apache.log4j.Logger;

import com.jdon.annotation.Component;
import com.jdon.container.visitor.data.SessionContextSetup;
import com.jdon.controller.cache.CacheManager;
import com.jdon.domain.model.cache.ModelKey;
import com.jdon.domain.model.cache.ModelManager;

/**
 * @author <a href="mailto:banq@163.com">banq</a>
 * 
 */
@Component
public class ContainerUtil {
	private final static Logger logger = Logger.getLogger(ContainerUtil.class);

	private SessionContextSetup sessionContextSetup;
	private ModelManager modelManager;
	private CacheManager cacheManager;

	/**
	 * @param containerCallback
	 */
	public ContainerUtil(SessionContextSetup sessionContextSetup, ModelManager modelManager, CacheManager cacheManager) {
		this.sessionContextSetup = sessionContextSetup;
		this.modelManager = modelManager;
		this.cacheManager = cacheManager;
	}

	public Object getModelFromCache(Object key, Class modelClass) {
		ModelKey modelKey = new ModelKey(key, modelClass);
		logger.debug("try to get model from cache, cacheKey=" + modelKey.toString());
		return modelManager.getCache(modelKey);
	}

	public void addModeltoCache(Object key, Object model) {
		if (model == null)
			return;
		ModelKey modelKey = new ModelKey(key, model.getClass());
		modelManager.addCache(modelKey, model);
	}

	public void clearCache(Object key) {
		try {
			modelManager.removeCache(key);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void clearAllModelCache() {
		modelManager.clearCache();
		cacheManager.clear();
	}

	public SessionContextSetup getSessionContextSetup() {
		return sessionContextSetup;
	}

	/**
	 * @return Returns the cacheManager.
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}
}
