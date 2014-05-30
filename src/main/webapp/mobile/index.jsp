<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
String ua=request.getHeader("User-Agent").toLowerCase();
Pattern p = Pattern.compile(".*(iPhone|mobile|iPod|blackberry|DoCoMo|cldc|android|htc|lg|midp|mmp|mobile|nokia|opera mini|palm|pocket|psp|sgh|smartphone|symbian|treo mini|Playstation Portable|SonyEricsson|Samsung|MobileExplorer|PalmSource|Benq|Windows Phone|Windows Mobile|IEMobile|Windows CE|Nintendo Wii).*",Pattern.CASE_INSENSITIVE);
Matcher matcher = p.matcher(ua);
if(matcher.matches()){
	response.sendRedirect(request.getContextPath() +"/mobile/new");
	return;
}
%>
欢迎光临Jdon.com

您当前使用的不是手机浏览器，或无法辨认您的手机型号。

