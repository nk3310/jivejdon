<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>论坛管理工具</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon.css"/>" type="text/css">
</head>

<body background="images/sidebar_back.gif" text="#000000" link="#0000ff" vlink="#0000ff" alink="#6699cc"
onload="parent.frames['header'].location.href='tabs.jsp?method=users';">

<html:img page="/images/blank.gif" width="50" height="5" border="0" alt=""/><br>

 <table cellpadding="2" cellspacing="0" border="0" width="100%">
    <tr><td rowspan="99" width="1%">&nbsp;</td>
        <td colspan="3" width="99%"><font size="-1"><b>用户</b></font></td>
    </tr>
    <tr><td width="1%">&nbsp;</td>
        <td width="1%">&#149;</td>
        <td width="97%"><font size="-1">
        <html:link page="/admin/user/userListAction.shtml"  target="main">用户概览</html:link>
        </font></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1">
        <html:link page="/admin/user/userMessageList.shtml"  target="main">管理用户帖子</html:link>
        </font></td>
    </tr>
    <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1">
        <html:link page="/admin/user/banIPAction.shtml"  target="main">IP查封</html:link>
        </font></td>
    </tr>
    
     <tr><td>&nbsp;</td>
        <td>&#149;</td>
        <td><font size="-1">
        用户授权
        </font></td>
    </tr>
    </table>

</body>
</html>