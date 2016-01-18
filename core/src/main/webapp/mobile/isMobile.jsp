<%@page import="java.util.regex.Matcher"%>
<%@page import="java.util.regex.Pattern"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
String ua=request.getHeader("User-Agent").toLowerCase();
Pattern p = Pattern.compile(".*(iPhone|iPod|blackberry|DoCoMo|cldc|android|htc|lg|midp|mmp|mobile|nokia|opera mini|palm|pocket|psp|sgh|smartphone|symbian|treo mini|Playstation Portable|SonyEricsson|Samsung|MobileExplorer|PalmSource|Benq|Windows Phone|Windows Mobile|IEMobile|Windows CE|Nintendo Wii).*",Pattern.CASE_INSENSITIVE);
Matcher matcher = p.matcher(ua);
if(!matcher.matches()){
   response.sendRedirect(request.getContextPath() +"/thread/" + request.getParameter("thread"));
   return;
}
%>