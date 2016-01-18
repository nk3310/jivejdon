<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html>
<head>
<title>
<logic:notEmpty  name="title">
  <bean:write name="title" />
</logic:notEmpty> - Thinking In Jdon
</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="shortcut icon" href="<html:rewrite page="/images/favicon.ico"/>" />
<link href="<html:rewrite page="/forum/css/mList_css.jsp"/>" rel="stylesheet" type="text/css" />
<script>
 if(top !== self) top.location = self.location;
 window.google_analytics_uacct = "UA-257352-2";
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-257352-2']);
  _gaq.push(['_trackPageview']);

</script>

<%@ include file="../common/security.jsp" %>
<%@ include file="../common/loginAccount.jsp" %>

</head>
<%@ include file="../common/body_header.jsp" %>

<%-- include LAB.js --%>   
<%@ include file="../account/loginAJAX.jsp" %>

 <%@ include file="../common/header_errors.jsp" %>
 