<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-html" prefix="html" %>


<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", request);
if (errorBlocker.checkRate(request.getRemoteAddr(), 8))
    return;
%>

<html>
<head>
<title>404 error</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />

</head>
<body>

Not found, return <a href="<%=request.getContextPath() %>" >homepage</a>

<%
    response.setStatus(200); // 200 = HttpServletResponse.SC_OK
%>
   </body>
</html>