<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<div id="isNewMessage" style="display:none"></div>

<script>
 <logic:present name="principal" >
     var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";     
     new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
      { method: 'get',
        frequency: 120, 
        decay: 2,
        evalScripts: true});
  </logic:present>
 
<logic:notPresent name="principal" >
  var messageChkURL = "<%=request.getContextPath() %>/forum/checknewmessage.shtml";
  username = readCookie("username");
  if (username != null){//active auto login
     messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";   
  }
   
     new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
      { method: 'get',
        frequency: 300, 
        decay: 2,
        evalScripts: true});

</logic:notPresent>

</script>