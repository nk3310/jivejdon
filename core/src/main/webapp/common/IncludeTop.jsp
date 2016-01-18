<%@ page contentType="text/html; charset=UTF-8" %>
<%  
   com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5*60, request, response);	   
%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<head>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> - Thinking In Jdon
</title>

<%@ include file="./headerBody.jsp" %>

<%@ include file="security.jsp" %>
<%@ include file="loginAccount.jsp" %>

</head>
<%@ include file="./body_header.jsp" %>
<%@ include file="../account/loginAJAX.jsp" %>
 <%@ include file="./header_errors.jsp" %>
 