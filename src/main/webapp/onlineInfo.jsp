<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<%@page import="com.jdon.jivejdon.presentation.listener.UserCounterListener,java.util.List,java.util.Iterator"%>

<html>
<head>
<title>
在线登录用户状态
</title>
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<body>
<table border="0" cellpadding="0" cellspacing="0" width="300" align='center'>
<tr>
<td>
<%
int count = 1;
List userList = (List)application.getAttribute(UserCounterListener.OnLineUser_KEY);
StringBuffer sb = new StringBuffer();
Iterator iter = userList.iterator();
while(iter.hasNext()){
	String username = (String)iter.next();
	sb.append("<a href=\""+request.getContextPath()+"/blog/"+username+"\" target=\"_blank\">"+username+"</a>&nbsp;");
	if ((count = count % 6) == 0)
		sb.append("<br>");
	count = count + 1;
}
out.print(sb.toString());
%>
</td>
</tr>
</table>
</body></html>
