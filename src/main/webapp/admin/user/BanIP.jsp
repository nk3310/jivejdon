<%@page import="com.jdon.jivejdon.util.BanIPUtils;"%>


<%
String ip = request.getParameter("ip");
if (ip == null || ip.length() == 0) return;
BanIPUtils.addIPTables(ip);
%>
