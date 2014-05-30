<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>

<%
String forward = "/article/articleList.shtml";
String threadId = request.getParameter("thread");
String redirectUrl = request.getContextPath() + forward + "?thread=" + threadId;
response.sendRedirect(redirectUrl); %>
