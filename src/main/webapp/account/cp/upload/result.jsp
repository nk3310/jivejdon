<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html:errors/>

<html><head>
<title>
</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />

<script type='text/javascript'>
 var errorM = null;
<logic:messagesPresent>
 <html:messages id="error">
     errorM = "<bean:write name="error" />";
 </html:messages>
</logic:messagesPresent>

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
      errorM = "<bean:write name="error" />";
  </logic:iterate>
</logic:present>
        
  function killUpdate()
        {       
        if (errorM != null){
           window.parent.myalert(errorM);
        }else{           
           window.parent.location.href="<%=request.getContextPath() %>/account/protected/upload/uploadUserFaceAction.shtml?parentId=0&userId=<bean:write name="accountFaceFileForm" property="userId"/>"; }
        }
        
</script>        
</head>
<body onload='killUpdate();'>

</body>
</html>
