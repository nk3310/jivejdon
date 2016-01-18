<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>
<bean:define id="title"  value=" 用户注册" />
<%@ include file="../common/IncludeTop.jsp" %>

<table cellpadding="0" cellspacing="0" border="0" width="100%">
<tr>
    <td valign="top" width="99%">
    <%@include file="../forum/nav.jsp"%>
    <br>
    </td>
    <td valign="top"  align="center">
    </td>
</tr>
</table>

<h3 align="center">用户注册</h3>
<div align="center">
  

<html:form action="/account/newAccount.shtml" method="post" onsubmit="return Juge(this);">

<html:hidden name="accountForm" property="action" value="create" />

<html:hidden property="userId" />

<%@include file="IncludeAccountFields.jsp"%>

</html:form>
</div>


<%--
<%@include file="IncludeAccountFields.jsp"%>
--%>



<%@include file="../common/IncludeBottom.jsp"%>