<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", request);
if (errorBlocker.checkCount(request.getRemoteAddr(), 5)){
	response.sendError(404);
    return;
}
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body>
<% if (request.getParameter("oid") != null) { %>
 <img src="<%=request.getContextPath() %>/img/<%=request.getParameter("id") %>-<%=request.getParameter("oid") %>"  border='0' />
<% }else if (request.getParameter("type") != null) {   %> 
 <img src="<%=request.getContextPath() %>/img/uploadShowAction.shtml?id=<%=request.getParameter("id") %>&type=<%=request.getParameter("type") %>"  border='0' />
<%} %>
</body>
</html>