<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<html>
<head>
<title>
<bean:write name="title" />
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<script type="text/javascript" src="<html:rewrite page="/common/js/prototype.js"/>"></script>
<script>
var contextPath = "<%=request.getContextPath()%>";
</script>
<script language="javascript" src="<html:rewrite page="/common/js/autocomplete.js"/>"></script>

</head>
<body bgcolor='#ffffff'>



<!-- Support for non-traditional but simple message -->
<logic:present name="message">
  <b><font color="BLUE"><bean:write name="message" /></font></b>
</logic:present>

<!-- Support for non-traditional but simpler use of errors... -->
<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>

<html:errors/>