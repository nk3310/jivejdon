<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>
<title>
编辑注册资料
</title> 
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<body>
<html:errors/>

<div align="center">
<center>注册资料修改 (密码只能重新设定)</center>
<html:form method="post" action="/account/protected/editSaveAccount.shtml" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="edit" />
<html:hidden name="accountForm" property="userId" />

<%@include file="../IncludeAccountFields.jsp"%>

</html:form>

<p>
</p>
</div>
</body></html>

