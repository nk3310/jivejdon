<!-- todo -->
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!--  显示一个主题下所有帖子 -->
<bean:define id="messageList" name="messageListForm" property="list" />

<logic:empty name="messageListForm"  property="oneModel" >
   无此贴
</logic:empty>

<logic:notEmpty name="messageListForm"  property="oneModel" >
<bean:define id="forumThread" name="messageListForm" property="oneModel" />

<bean:define id="forum" name="forumThread" property="forum" />

<bean:define id="title" name="forumThread" property="name" />
<%@ include file="header.jsp" %>



<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i" offset="1">

 <div class="forum-list-tree" style="position: relative; left: 0px; margin-right: 0px;">  
        <b><bean:write name="forumMessage" property="messageVO.subject"/></b>
        by:<bean:write name="forumMessage" property="account.username"/>
 </div>        
</logic:iterate>


</logic:notEmpty>
<%@include file="footer.jsp"%> 


