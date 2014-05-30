<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<bean:parameter id="parentMessageId" name="parentMessageId" value="" />

<iframe id='target_reply' name='target_reply' src='' style='display: none'></iframe>

<div id="replyDiv">
<div class="tooltip_content">
<html:form action="/message/messageReplySaveAction.sthml" target="target_reply" styleId="messageReply"  method="post"  onsubmit="return checkPost(this);" >

<html:hidden property="action" value="create"/>
<input type="hidden" name="parentMessage.messageId" id="parentMessageId" value="<bean:write name="parentMessageId"  />" />
<html:hidden property="messageId" />

<!-- create another name "messageForm", so in messageFormBody.jsp it can be used -->
<bean:define id="messageForm" name="messageReplyForm" scope="request" />

 <jsp:include page="../message/messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="true"/>   
 </jsp:include>  

<script>
function loadPostjs(){
  if (typeof(openReplyWindow) == 'undefined') {
     $LAB
     .script('<html:rewrite page="/forum/js/postreply.js"/>').wait()
     .wait(function(){
          setObserve();
     })     
  }else
     setObserve();
}

function setObserve(){
 if(typeof(Ajax) != "undefined"){
      Event.observe('messageReply', 'submit', callbackSubmit);
  }   
}

</script>

</html:form>
</div>
</div>

