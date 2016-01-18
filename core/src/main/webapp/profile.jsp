<%@ page session="false" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>

<%@ include file="./common/503warn.jsp"%> <%-- for search spammer bot  --%>
<meta name="robots" content="nofollow"> 
<%
response.sendRedirect(request.getContextPath() + "/blog/" + request.getParameter("user"));
		return;
%>
