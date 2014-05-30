<%@ page contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="struts-logic" prefix="logic" %>
 <%@ taglib uri="struts-bean" prefix="bean" %>
 <%
	int adddays =  4 * 60 * 60 * 1000;  
    com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(adddays, request, response);
%>

<%@ include file="../common/security.jsp" %> 
<html>
<head>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty>
<logic:empty  name="title">
  jdon.com
</logic:empty>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/mobile/mobile.css"/>" type="text/css"> 
<script language="javascript" src="<html:rewrite page="/mobile/js/mb.js"/>" ></script>
</head>

<body leftmargin="0" rightmargin="0" topmargin="0">
<%@ include file="adsense.jsp" %>

<%@ include file="isNewMessage.jsp" %>
