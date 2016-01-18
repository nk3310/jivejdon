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
package com.jdon.jivejdon.presentation.filter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
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
import com.jdon.jivejdon.manager.throttle.hitkey.CustomizedThrottle;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeyDiff;
import com.jdon.jivejdon.manager.throttle.hitkey.HitKeyIF;
import com.jdon.jivejdon.util.ScheduledExecutorUtil;
import com.jdon.util.UtilValidate;

public class SpamFilter2 implements Filter {
	private final static Logger log = Logger.getLogger(SpamFilter2.class);

	protected Pattern robotPattern;

	protected Pattern domainPattern;

	public static String DP = "domainPattern";

	private CustomizedThrottle customizedThrottle;

	private boolean isFilter = false;

	public void init(FilterConfig config) throws ServletException {
		// check for possible robot pattern
		String robotPatternStr = config.getInitParameter("referrer.robotCheck.userAgentPattern");
		if (!UtilValidate.isEmpty(robotPatternStr)) {
			// Parse the pattern, and store the compiled form.
			try {
				robotPattern = Pattern.compile(robotPatternStr);
			} catch (Exception e) {
				// Most likely a PatternSyntaxException; log and continue as if
				// it is not set.
				log.error("Error parsing referrer.robotCheck.userAgentPattern value '" + robotPatternStr + "'.  Robots will not be filtered. ", e);
			}
		}

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

		String thresholdStr = config.getInitParameter("throttle.conf.threshold");
		String intervalStr = config.getInitParameter("throttle.conf.interval");
		if (!UtilValidate.isEmpty(thresholdStr) && !UtilValidate.isEmpty(intervalStr)) {
			// throttleConf = new ThrottleConf(thresholdStr, intervalStr);
		}

		Runnable startFiltertask = new Runnable() {
			public void run() {
				isFilter = true;
			}
		};
		// half hour
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(startFiltertask, 60, 60 * 30, TimeUnit.SECONDS);

		Runnable stopFiltertask = new Runnable() {
			public void run() {
				isFilter = false;
			}
		};
		// after 10 Mintues stop it
		ScheduledExecutorUtil.scheduExecStatic.scheduleAtFixedRate(stopFiltertask, 60 * 10, 60 * 40, TimeUnit.SECONDS);

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!isFilter) {
			chain.doFilter(request, response);
			return;
		}
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (isPermittedReferer(httpRequest) || isPermittedRobot(httpRequest)) {
			chain.doFilter(request, response);
			disableSessionOnlines(httpRequest);
			return;
		}

		String path = httpRequest.getServletPath();
		int slash = path.lastIndexOf("/");
		String id = path.substring(slash + 1, path.length());
		if (checkSpamHit(id, httpRequest))
			chain.doFilter(request, response);
		else
			((HttpServletResponse) response).sendError(503);
		return;

	}

	private boolean checkSpamHit(String id, HttpServletRequest request) {
		if (customizedThrottle == null) {
			customizedThrottle = (CustomizedThrottle) WebAppUtil.getComponentInstance("customizedThrottle", request);
		}
		HitKeyIF hitKey = new HitKeyDiff(request.getRemoteAddr(), id);
		return customizedThrottle.processHitFilter(hitKey);
	}

	private boolean isPermittedRobot(HttpServletRequest request) {
		// if refer is null, 1. browser 2. google 3. otherspam
		String userAgent = request.getHeader("User-Agent");
		if (robotPattern != null) {
			if (userAgent != null && userAgent.length() > 0 && robotPattern.matcher(userAgent.toLowerCase()).matches()) {
				disableSessionOnlines(request);// although permitted, but
				// disable session.
				return true;
			}
		}
		return false;
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

	private void disableSessionOnlines(HttpServletRequest httpRequest) {
		HttpSession session = httpRequest.getSession(false);
		if (session != null)
			session.invalidate();
	}

	public void destroy() {

	}

}
