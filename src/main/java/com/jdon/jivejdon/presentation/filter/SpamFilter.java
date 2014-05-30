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
package com.jdon.jivejdon.presentation.filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jdon.controller.WebAppUtil;
import com.jdon.jivejdon.manager.block.IPBanListManagerIF;
import com.jdon.util.UtilValidate;

/**
 * if referrerUrl inlucde if referrerUrl is empty, it is a spam if referrerUrl
 * is not starting with "http://" , it is spam
 * 
 * see configuration in web.xml
 * 
 * diable, only check ip in this filter
 * 
 */
public class SpamFilter implements Filter {
	private final static Logger log = Logger.getLogger(SpamFilter.class);

	protected IPBanListManagerIF iPBanListManagerIF;

	protected Pattern domainPattern;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (isSpam(httpRequest) && (!isPermittedReferer(httpRequest))) {
			log.debug("spammer, giving 'em a 503");
			disableSessionOnlines(httpRequest);
			if (!response.isCommitted())
				response.reset();
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(503);
			return;
		}

		chain.doFilter(request, response);
		return;
	}

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	/**
	 * Process the incoming request to extract referrer info and pass it on to
	 * the referrer processing queue for tracking.
	 * 
	 * @returns true if referrer was spam, false otherwise
	 */
	protected boolean isSpam(HttpServletRequest request) {
		return isSpamForThrottle2(request);
	}

	private boolean isPermittedReferer(HttpServletRequest request) {
		String referrerUrl = request.getHeader("Referer");
		if (domainPattern != null) {
			if (referrerUrl != null && referrerUrl.length() > 0 && domainPattern.matcher(referrerUrl.toLowerCase()).matches()) {
				return true;
			}
		}
		return false;
	}

	protected boolean isSpamForThrottle2(HttpServletRequest request) {
		if (iPBanListManagerIF == null)
			iPBanListManagerIF = (IPBanListManagerIF) WebAppUtil.getComponentInstance("iPBanListManager", request);
		if (iPBanListManagerIF.isBanned(request.getRemoteAddr())) {
			String userAgent = request.getHeader("User-Agent");
			String referrerUrl = request.getHeader("Referer");
			log.error("it is spam : processing referrer for " + request.getRequestURI() + " referrerUrl=" + referrerUrl + " userAgent=" + userAgent
					+ " ip=" + request.getRemoteAddr());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Unused.
	 */
	public void destroy() {
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		String domainPatternStr = config.getInitParameter("referrer.domain.namePattern");
		if (!UtilValidate.isEmpty(domainPatternStr)) {
			try {
				domainPattern = Pattern.compile(domainPatternStr);
				if (domainPattern != null)
					config.getServletContext().setAttribute(SpamFilter2.DP, domainPattern);
			} catch (Exception e) {
				log.error("Error parsingreferrer.domain.namePattern value '" + domainPattern, e);
			}
		}

	}
}
