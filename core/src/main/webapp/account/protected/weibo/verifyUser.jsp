<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="weibo4j.*" %>
<%@ page language="java" import="weibo4j.http.*" %>

<jsp:useBean id="weboauth" scope="session" class="com.jdon.jivejdon.manager.weibo.SinaWeboSubmitter" />


<%
String userId = request.getParameter("userId");
String passwd = request.getParameter("passwd");
if (weboauth.verfiyUser(userId, passwd)){
	
}
%>