<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>


  
<%  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 60 * 60, request, response);
%>

<%@ page contentType="application/json; charset=UTF-8" %>
{ results: [<logic:iterate id="threadTag" name="TAGS" indexId="i">
	{ value: "<bean:write name="threadTag" property="title" />", info: ""},
</logic:iterate>
    {value:"", info: ""}]
}