<%--  called bu ThreadList http://127.0.0.1:8080/jivejdon/forum.jsp --%>

<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:parameter id="forumId" name="forumId" value="" />

<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>

<html:form action="/message/messageSaveAction.sthml" method="post" target="target_new" styleId="messageNew"  onsubmit="return checkPost(this);" >

<logic:notEmpty name="forumId">
  <input type="hidden" name="forum.forumId" value="<bean:write name="forumId"/>" id="forumId_select"/>
</logic:notEmpty>


<logic:empty name="forumId">
<table cellpadding="4" cellspacing="0" border="0"  width="971" align="center">

<tr>
	<td  width="50" align="right">在 </td>
	<td align="left"> 
	
     <select name="forum.forumId"  id="forumId_select" ></select> 中发帖.	    
<script>
var pars = "";
new Ajax.Request('<html:rewrite page="/forum/forumListJSON.shtml"/>', 
  	    {method: 'post', parameters: pars, onSuccess: shoForumResponse}); 
  	     	    	  
function shoForumResponse(transport){
	if (!transport.responseText.isJSON())
	 	 return;
     var dataArray = (transport.responseText).evalJSON();	 	 
     dataArray.each(function(forum){   
    	 var optn = document.createElement('option');
	     optn.text = forum.name;
	     optn.value = forum.forumId;		  
          $('forumId_select').options.add(optn);
     });
 }
</script>    


    </td>
</tr>
</table>
</logic:empty>
<html:hidden property="messageId" />
<html:hidden property="action" value="create"/>

<jsp:include page="../message/messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="false"/>   
</jsp:include> 

</html:form>


<script>
function loadPostjs(){
  if (typeof(openInfoDiag) == 'undefined') {
     $LAB
     .script('<html:rewrite page="/forum/js/threadPost.js"/>').wait()
     .wait(function(){
         setObserve();
     })              
  }else
     setObserve();
}

function setObserve(){
 if(typeof(Ajax) != "undefined"){
      $('messageNew').observe("submit", callbackSubmit);
  }   
}

</script>
