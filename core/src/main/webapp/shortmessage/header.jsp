<%@ page contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<html>
<head>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> 
</title>

</head>
<meta http-equiv="Pragma" content="no-cache">
<link rel="stylesheet" href="<html:rewrite page="/shortmessage/shortmsg_css.jsp"/>"	type="text/css">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="shortcut icon" href="<html:rewrite page="/images/favicon.ico"/>" />
<script language="javascript" src="<html:rewrite page="/common/js/prototype.js"/>"></script>

<body >