<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<html:errors/>

<bean:parameter name="subject" id="subject" value=""/>
<bean:parameter name="body" id="body" value=""/>
<bean:parameter name="email" id="email" value=""/>
<html>
<head></head>
<%@ include file="../../common/headerBody.jsp" %>
<body>
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

<p><h3 align="center">联系表单提交</h3>
<div align="center"><html:form action="/forum/feed/feedbackAction.shtml" method="POST" onsubmit="return Juge(this);">
  <html:hidden property="action" value="send"/>
  <table>
    <tr><td>您的信箱:</td><td><input type="text" name="email" size="20" value="<bean:write name="email"/>">(以便回复您结果)</td></tr>
  
  <tr><td>表单标题:</td><td><input type="text" name="subject" size="50" value="<bean:write name="subject"/>"></td></tr>
    <tr><td>表单内容:</td><td><textarea name="body" cols="50" rows="6"><bean:write name="body"/></textarea></td></tr>

    <tr><td>验证码:</td><td>
               <input type="text" name="registerCode" size="10"
				maxlength="50" tabindex="102"> <html:img
				page="/account/registerCodeAction" border="0" /> 
                </td></tr>
    <tr><td colspan="2" align="center"><html:submit property="submit" value="提交"/></td></tr>
  
</table>
  </html:form>
  

</div>
</body>
</html