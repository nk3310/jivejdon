<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@page import="com.jdon.jivejdon.presentation.listener.UserCounterListener,java.util.List,java.util.Iterator"%>

<%
List userList = (List)application.getAttribute(UserCounterListener.OnLineUser_KEY);
String renderText = "";
if(userList.contains(request.getParameter("username")))
	renderText = "<font color=blue>我在线上</font>";
else
	renderText = "当前离线";
%>
<%=renderText%>

