<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<form method="POST" action="<%=request.getContextPath()%>/jasslogin"  onsubmit="return Juge(this);"> 

<table border="0" cellpadding="4" cellspacing="0" width="100%">
<tr>
<td align="center">

           <table border="0" cellpadding="0" cellspacing="2">
  <tr>
    <td> 用户 </td>
    <td width="10">&nbsp;</td>
    <td><input type="text" name="j_username" size="20" tabindex="1">
    </td>
    <td width="10">&nbsp;</td>
    <td><table border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td>自动登陆 </td>
        <td align="right"><input type="checkbox" name="rememberMe"  checked="checked" >
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td>密码 </td>
    <td width="10">&nbsp;</td>
    <td><input type="password" name="j_password" size="20" tabindex="2">
    </td>
    <td width="10">&nbsp;</td>
    <td><input type="submit" value=" 登陆 " tabindex="3" >
    </td>
  </tr>
  <tr>
    <td align="center" colspan="5">
	<a href="<%=request.getContextPath()%>/account/newAccountForm.shtml"  target="_blank" >
                          新用户注册
                    </a>
                    <a href="<%=request.getContextPath()%>/account/forgetPasswd.jsp" target="_blank">
                          忘记密码?
                    </a>
	</td>
  </tr>
</table>
</form>