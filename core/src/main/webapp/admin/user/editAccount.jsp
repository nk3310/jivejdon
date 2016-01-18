<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 用户管理" />
<%@ include file="../header.jsp" %>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="99%">
    <%@include file="../../forum/nav.jsp"%>
    <br>
    </td>
    <td valign="top"  align="center">
    </td>
</tr>
</table>


<h3 align="center">用户注册资料修改</h3>
<center>*密码只能重新设定</center>
<div align="center">

<html:form method="post" action="/admin/user/editSaveAccount.shtml" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="edit" />
<html:hidden name="accountForm" property="userId" />

<%@include file="../../account/IncludeAccountFields.jsp"%>

</html:form>

<%@include file="../footer.jsp"%>


