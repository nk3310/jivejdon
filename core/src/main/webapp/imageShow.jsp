<%@ page contentType="text/javascript; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<logic:forward name="imageShow" />
<%
response.sendRedirect(request.getContextPath() + "/img/uploadShowAction.shtml?id=" + request.getParameter("id") + "&type=" + request.getParameter("type"));
return;
%>
