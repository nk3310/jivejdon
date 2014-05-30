<%@ page contentType="text/html; charset=UTF-8"%>

<%
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
%>

<%@page import="com.jdon.controller.WebAppUtil,
com.jdon.jivejdon.manager.block.ErrorBlockerIF"%>
<%
ErrorBlockerIF errorBlocker = (ErrorBlockerIF) WebAppUtil.getComponentInstance("errorBlocker", request);
if (errorBlocker.checkCount(request.getRemoteAddr(), 10)){
	response.sendError(404);
    return;
}
%>

<html>
<head>
<title>503 错误</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />

</head>
<body>
哦，您看累了吧，该休息一会儿了，不知您是不是机器爬虫呢？验证成功后，请活动一下再来：
<form action="<%=request.getContextPath() %>/account/registerCodeAction" name="vform">
<br>输入验证码：<input type="text" name="registerCode" size="10"
				maxlength="10" >
				 <img id="theImg" src="<%=request.getContextPath()%>/account/registerCodeAction" border="0"  width="60" height="20"/>
        <input type="submit" value="验证" >			
        <br>如果无法验证，请转<a href="<%=request.getContextPath()%>/forum/">论坛首页</a>再次验证。	 
<p>
<br>需要人工帮助：<a href='<%=request.getContextPath()%>/forum/feed/feedback.jsp?subject=fromjivejdon3_503&body=<%=request.getRemoteAddr()%>_<%=request.getHeader("Referer")%>'>按这里报告管理员</a>
</form>

   </body>
</html>