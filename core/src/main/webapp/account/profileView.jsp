<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="accountProfileForm" property="account">
<logic:notEmpty name="accountProfileForm" property="account.username">
<bean:parameter id="winwidth" name="winwidth" value=""/>

<logic:empty name="winwidth" >
<bean:define id="title"  value=" 个人用户信息" />
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
</logic:empty>

<logic:empty name="winwidth" >
<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="500" align="center">
</logic:empty>

<logic:notEmpty name="winwidth" >
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="<bean:write name="winwidth" />" align="center">
 </logic:notEmpty>
 
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="3" border="0" width="100%">
<tr bgcolor="#FFFFCC">
 <td>
 <font class=p3 
     color="#000000">
 <b><bean:write name="accountProfileForm" property="account.username"/>个人用户信息</b>
 </font>
 </td>
</tr>
<tr bgcolor="#ffffff">
 <td align="center">

<table bgcolor="#cccccc" cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr><td>
    <table bgcolor="#cccccc" cellpadding="3" cellspacing="1" border="0" width="100%">

<tr  bgcolor="#FFFFFF">
  <td align="right">
   用户
   </td>
  <td>
   <bean:write name="accountProfileForm" property="account.username"/>
  </td>
</tr>

<tr  bgcolor="#FFFFFF">
  <td align="right">
   注册时间 
   </td>
  <td>
   <bean:write name="accountProfileForm" property="account.creationDate"/>
  </td>
</tr>

<tr  bgcolor="#FFFFFF">
  <td align="right">
   悄悄话 
   </td>
  <td>
          <bean:define id="messageTo" name="accountProfileForm" property="account.username" />
          <a href="<html:rewrite page="/account/protected/shortmessageAction.shtml" paramId="messageTo"  paramName="messageTo" />" target="_blank" rel="nofollow">给<bean:write name="accountProfileForm" property="account.username"/>发消息</a>

  </td>
</tr>




<logic:iterate id="property" name="accountProfileForm" property="propertys" indexId="i">
  <tr  bgcolor="#FFFFFF">
  <td align="right">
   <bean:write name="property"  property="name"  />
   </td>
  <td>
   <bean:write name="accountProfileForm"  property='<%= "property[" + i + "].value" %>'   filter="false" />
  </td>
</tr>
</logic:iterate>  
  
  
<tr  bgcolor="#FFFFFF">
  <td align="right">
   发帖情况 
   </td>
  <td>
  <logic:greaterThan name="accountProfileForm" property="account.messageCount" value="0">
     总数：<bean:write name="accountProfileForm" property="account.messageCount" />
  </logic:greaterThan>
   <a href='<html:rewrite page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="userID" paramName="accountProfileForm"
           paramProperty="account.userId"/>' target="_blank"  rel="nofollow">查询发表所有帖子</a>  
  </td>
</tr>

    </table>
  </td>
</tr></table>
 </td>
</tr></table>
</td></tr>
</table>
<center>
<logic:present name="principal" >  
     <html:link page="/account/protected/editAccountForm.shtml?action=edit" paramId="username" paramName="principal" target="_blank">
      编辑个人信息</html:link>
</logic:present>
</center>
<logic:empty name="winwidth" >

<%@include file="../common/IncludeBottom.jsp"%>
</logic:empty>

</logic:notEmpty>
</logic:notEmpty>
