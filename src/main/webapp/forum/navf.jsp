<%@page import="java.util.Enumeration"%>
<%@ include file="../common/503warn.jsp"%> <%-- for search spammer bot  --%>

<%-- (urlrewrite.xml)/thread/nav/([0-9]+)/([0-9]+) == > /forum/messageNavList.shtml  == > MessageListNavAction ==>navf.jsp ==> (urlrewrite.xml)/thread/([0-9]+)/([0-9]+) --%>
<meta name="robots" content="nofollow"> 
<% 
int start = ((Integer)request.getAttribute("start")).intValue();
long threadId = ((Long)request.getAttribute("threadId")).longValue();

String url = request.getContextPath() +"/thread";
if (start == 0)
    url = url + "/" + threadId ;
else
	url = url + "/" + threadId + "/" + start;

Enumeration e = request.getParameterNames();
while (e.hasMoreElements()) {            
      String paramName = (String) e.nextElement();
      if (paramName.equals(request.getParameter(paramName)))
               url = url + "/" + request.getParameter(paramName);
            
}
%>
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(0, request, response);
response.setHeader("Pragma", "No-cache");
response.setHeader("Cache-Control", "no-cache");
response.setDateHeader("Expires", 0);
response.setStatus(301); 
response.sendRedirect(url);
return;
%>
