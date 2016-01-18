<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="isMobile.jsp" %>

<bean:size id="messageCount" name="messageListForm" property="list" />
<logic:equal name="messageCount" value="0">
   无此贴 或已经删除
</logic:equal>

<logic:greaterThan name="messageCount" value="0">

   
<bean:define id="forumThread" name="messageListForm" property="oneModel" />
<bean:define id="forum" name="forumThread" property="forum" />
<bean:define id="title" name="forumThread" property="name" />
<%@ include file="./header.jsp" %>

<bean:parameter name="start" id="start" value="0"/>

<logic:iterate id="forumMessage" name="messageListForm" property="list" length="1" indexId="i">

<table width='100%' border="1" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
  <tr>
    <td bgcolor="#c3d9e7" align="left">
<bean:write name="forumMessage" property="messageVO.subject"/>
</td></tr></table>

 作者：<bean:write name="forumMessage" property="account.username"/>
<br>
 时间：<bean:write name="forumMessage" property="creationDate"/>

<br><br>
<bean:write name="forumMessage" property="messageVO.body" filter="false"/>
<br><br>

<br>

<bean:define id="allCount" name="messageListForm" property="numReplies" />
<%
Integer allCountIntO = (Integer)pageContext.getAttribute("allCount");
int allCountInt = allCountIntO.intValue();
int startInt = Integer.parseInt((String)pageContext.getAttribute("start"));
%>


<logic:notEqual name="start" value="0">
<br>
<a href="<%=request.getContextPath()%>/mobile/thread/<bean:write name="forumThread" property="threadId"/>/<%=startInt-1%>">上一个</a>
</logic:notEqual>

<%if (allCountInt-startInt > 0){ %>
  <br>
  <a href="<%=request.getContextPath()%>/mobile/thread/<bean:write name="forumThread" property="threadId"/>/<%=startInt+1%>" tabindex="1">下一个</a>
   后有<%=allCountInt-startInt %>个回复
<%}else if (allCountInt-startInt == 0){ %>
    
  <br>
  <a href="<%=request.getContextPath()%>/mobile/thread/<bean:write name="forumThread" property="threadId"/>">回主题</a>
  
<%} %>   
<br></br>
<div class="tres"> 
<MultiPagesREST:pager actionFormName="messageListForm" page="/mobile/thread" paramId="thread" paramName="forumThread" paramProperty="threadId">
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
</div>
<br>

<a href="<html:rewrite page="/mobile/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
             />&forum.forumId=<bean:write name="forumThread" property="forum.forumId"
             />" rel="nofollow">发表回复</a>

<a href='<html:rewrite page="/mobile/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId" />' rel="nofollow">编辑</a>


<br>
<br>
<a href="<%=request.getContextPath()%>/mobile/new"><strong>返回主题列表</strong></a>



</logic:iterate>

<script>
   var pageURL = '<%=request.getContextPath() %>/mobile/thread/<bean:write name="forumThread" property="threadId"/>';
   var count = <bean:write name="messageListForm" property="count" />;
   var allCount = <bean:write name="messageListForm" property="allCount" />
   var start = <bean:write name="messageListForm" property="start" />;
   document.onkeydown=leftRightgoPageREST;
</script>


<%@include file="footer.jsp"%> 

</logic:greaterThan>
