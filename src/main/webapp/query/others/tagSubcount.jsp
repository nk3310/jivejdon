<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

  
<%  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>

<%@ page contentType="text/html; charset=UTF-8" %>
<logic:greaterThan name="tagForm" property="subscriptionCount" value="0">
<bean:write name="tagForm" property="subscriptionCount"/>人关注
</logic:greaterThan>