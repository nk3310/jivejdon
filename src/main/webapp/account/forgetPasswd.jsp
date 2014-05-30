<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="title"  value=" 用户登录" />
<%@ include file="../common/IncludeTop.jsp" %>
<link rel="stylesheet" href="<html:rewrite page="/portlet_css.jsp"/>" type="text/css">

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


<table border="0" cellpadding="0" cellspacing="0" width="450" align='center'>
<tr>
<td>
<div class="portlet-container">
<div class="portlet-header-bar">
<div class="portlet-title">
<div style="position: relative; font-size: smaller; padding-top: 5px;"><b>&nbsp;忘记密码&nbsp;</b></div>
</div>
<div class="portlet-small-icon-bar">
</div>
</div><!-- end portlet-header-bar -->
<div class="portlet-top-decoration"><div><div></div></div></div>
<div class="portlet-box">
<div class="portlet-minimum-height">
<div id="p_p_body_2" >
<div id="p_p_content_2_" style="margin-top: 0; margin-bottom: 0;">


<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

<div align="center">
      <script>
<!--

function Juge(theForm)
{
   var myRegExp = /\w+\@[.\w]+/;
   if (!myRegExp.test(theForm.email.value)) {
  alert("请输入正确的Email");
  theForm.email.focus();
  return false;
 }
}
-->
    </script>
    
<logic:present name="accountForm">

<logic:messagesNotPresent>
<br><br><br>
    新密码已经发往您的信箱。请稍候收取。。  
<br><br><br>    
</logic:messagesNotPresent>
   
</logic:present>
    
  
<logic:notPresent name="accountForm">
    <table>
    <html:form action="/account/forgetPasswd.shtml" method="POST" onsubmit="return Juge(this);">
    <html:hidden property="action" value="forgetPasswd"/>
    <p>
    <br>
  <tr><td>
    密码问题：</td><td><html:text property="passwdtype"/>(如果没有可不填)
  </td></tr>
  <tr><td>
    密码答案：</td><td><html:text property="passwdanswer"/>(如果没有可不填)
  </td></tr>
  
<tr><td>
    Email信箱：</td><td><html:text property="email"/>(新密码将发往该信箱)
  </td></tr>
  
<tr><td>
    验证码：</td><td>
            <input type="text" name="registerCode" size="10" maxlength="50" >
            <html:img page="/account/registerCodeAction" border="0" />
  </td></tr>
  
<tr><td colspan="2" align="center">
    <html:submit property="submit" value="确定"/>
    </td></tr>
    </html:form>
      </table>
</div>

</logic:notPresent>  

<br>
<br><br><br><br><br><br><br><br><br><br>
</td>
</tr>
</table>
</div>
<br><br><br><br><br><br><br><br><br><br>
</div>
<br><br><br><br><br><br><br><br><br><br>
</div>
<br><br><br><br><br><br><br><br><br><br>
</div><!-- end portlet-box -->
<div class="portlet-bottom-decoration-2"><div><div></div></div></div>
</div><!-- End portlet-container -->

</td></tr></table>

<%@include file="../common/IncludeBottomBody.jsp"%>

