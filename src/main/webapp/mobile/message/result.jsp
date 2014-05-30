<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ include file="../header.jsp" %>

<logic:notEmpty name="messageReplyForm">
<bean:define id="messageId" name="messageReplyForm" property="messageId"  />
<bean:define id="forumThread" name="messageReplyForm" property="forumThread"  />
<bean:define id="action" name="messageReplyForm" property="action"  />
</logic:notEmpty>


<logic:notEmpty name="messageForm">
<bean:define id="messageId" name="messageForm" property="messageId"  />
<bean:define id="forumThread" name="messageForm" property="forumThread"  />
<bean:define id="action" name="messageForm" property="action"  />
</logic:notEmpty>

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
  <table cellpadding="0" cellspacing="0" border="0"  align="center"> 
<tr> 
    <td valign="top" > 
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
    
    </td></tr></table>
  </logic:iterate>
</logic:present>

 <logic:messagesNotPresent>
    <logic:empty name="errors">
     <br><br>
      帖子发布成功！<a href="<%=request.getContextPath()%>/mobile/new">回主题</a>
  </logic:empty>
  </logic:messagesNotPresent>      

<%@include file="../footer.jsp"%> 