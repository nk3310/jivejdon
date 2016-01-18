<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="method" name="method" value="system" />
<html>
<head>
	<title>管理标头区</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
        
</head>


<body topmargin="0" rightmargin="0" leftmargin="0" bottommargin="0" marginheight="0" marginwidth="0"
text="#000000" link="#0000ff" vlink="#0000ff" alink="#6699cc">

<table background="images/header_grad.gif" cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
	<td><a href="main.jsp" target="main"><img src="images/header.gif" width="184" height="51" alt="论坛管理" border="0"></a></td>
</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr><td bgcolor="#666666"><html:img page="/images/blank.gif" width="1" height="1" border="0" alt=""/></td></tr>
<tr><td bgcolor="#999999"><html:img page="/images/blank.gif" width="1" height="1" border="0" alt=""/></td></tr>
<tr><td bgcolor="#cccccc"><html:img page="/images/blank.gif" width="1" height="1" border="0" alt=""/></td></tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr bgcolor="#ffffff"> 
    <td width="40%" nowrap 
         >
         <html:link page="/admin/sidebar.shtml?method=system" target="sidebar" ><img src="images/tab_global_<%= (method.equals("system"))?"on":"off" %>.gif" width="113" height="25" border="0"></html:link
         ><html:link page="/admin/sidebar.shtml?method=forum" target="sidebar" ><img src="images/tab_forums_<%= (method.equals("forum"))?"on":"off" %>.gif" width="113" height="25" border="0"></html:link
         ><html:link page="/admin/sidebar.shtml?method=users" target="sidebar" ><img src="images/tab_users_<%= (method.equals("users"))?"on":"off" %>.gif" width="113" height="25" border="0"></html:link
         ></td>
    <td background="images/tab_stretch.gif">&nbsp;</td>
    <td  width="10%" background="images/tab_stretch.gif">
    <html:link page="/jasslogin?logout" target="_parent">退出</html:link></td>
</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr bgcolor="#FFFFCC">
    <td width="1%"> <html:img page="/images/blank.gif" width="130" height="9" border="0" alt=""/></td>
    <td width="99%"><img src="images/toolbar_stretch.gif" width="100%" height="9" border="0"></td>
</tr>
</table>
<table cellpadding="0" cellspacing="0" border="0" width="130">
<tr>
    <td><img src="images/sidebar_stretch.gif" width="130" height="20" border="0"></td>
</tr>
</table>
<script>
function change(url) {
    document.location.href=url;
    alert("dd");
    document.location.reload();
}
 
</script>
</body>
</html>