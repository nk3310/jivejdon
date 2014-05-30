package com.jdon.jivejdon.model.auth;

import java.util.ArrayList;
import java.util.List;

/**
 * contain all operations and them method that must be Authenticated accessed.
 * its scope is application.
 * @author banq
 *
 */
public class PermissionRule {

	/**
	 * contain all operations and them method, this operations
	 * are all  Authenticated, but not contain they are authenticated
	 * by who what role.
	 * 
	 * this method can be used for anonymous.
	 * 
	 * key is the service + method 
	 */
	private List operationAuthenticated;

	/**
	 * contain all  operations are authenticated by who?
	 * a list of all maps that key is service + method + role
	 */
	private List operationAuthenticatedByRoles;
	
	public PermissionRule(){
		operationAuthenticated = new ArrayList();
		operationAuthenticatedByRoles = new ArrayList();
	}

	public  boolean isOperationAuthenticated(String serviceName, String methodName) {
		StringBuffer bf = new StringBuffer(serviceName);
		bf.append(methodName);
		return operationAuthenticated.contains(bf.toString());
	}

	public boolean isOperationAuthenticatedByRole(String serviceName, String methodName,
			String roleName) {
		StringBuffer bf = new StringBuffer(serviceName);
		bf.append(methodName);
		bf.append(roleName);
		return operationAuthenticatedByRoles.contains(bf.toString());
	}
	
	public void putRule(String serviceName, String methodName, String roleName) {
		StringBuffer bf = new StringBuffer(serviceName);
		bf.append(methodName);
		operationAuthenticated.add(bf.toString());
		bf.append(roleName);
		operationAuthenticatedByRoles.add(bf.toString());
	}

}
