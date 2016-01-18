<%@ page contentType="text/html;charset=utf-8" %>
<%@ page language="java" import="weibo4j.*" %>
<%@ page language="java" import="weibo4j.http.*" %>

<jsp:useBean id="weboauth" scope="session" class="com.jdon.jivejdon.manager.weibo.SinaWeboSubmitter" />

<%
RequestToken resToken=weboauth.request(request.getContextPath() + "/account/protected/weibo/sinacallback.jsp");
if(resToken!=null){
	out.println(resToken.getToken());
	out.println(resToken.getTokenSecret());
	session.setAttribute("resToken",resToken);
	response.sendRedirect(resToken.getAuthorizationURL());
}
%>