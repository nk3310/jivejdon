<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>

<%@ include file="../common/503warn.jsp"%>

<bean:define id="title" value=" 错误" />
<%@ include file="../common/IncludeTop.jsp"%>
<bean:parameter id="error" name="error" value="" />
<center>
<H3>服务器内部错误...</H3>
发生系统错误<bean:write name="error" />，<a href='javascript:contactAdmin()'>请反馈 帮助完善开源JiveJdon</a>
<br>
<br>
<br>
<p>返回<html:link page="/index.jsp"> 首页</html:link>
</center>
<script>
    try{
      if (window.top.setDiagInfo)
         window.top.setDiagInfo('<center><br><h2> ERROR: 发生系统错误 <bean:write name="error" /> ');
    }catch(ex){}

    function contactAdmin(){
    	location.href = '<%=request.getContextPath()%>/forum/feed/feedback.jsp?subject=fromjivejdon3&body=<%=request.getHeader("Referer")%>_<bean:write name="error" />'
        	    + location.href;
    }
   </script>

<%@include file="../common/IncludeBottom.jsp"%>


<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>