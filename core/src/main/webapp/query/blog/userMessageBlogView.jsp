<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<bean:parameter  id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">


<bean:define id="title"  value=" 用户主题博文查询" />
<%@ include file="../../common/IncludeTop.jsp" %>


<center>
<jsp:include page="../../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>
<center><table ><tr><td>
</logic:empty>

<!-- second query result -->
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" >

<logic:notEmpty name="forumMessage" property="forumThread">
  <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
  <bean:define id="forum" name="forumMessage" property="forum" />

  
       <div class="b_content_title2">
       <logic:equal name="forumMessage" property="root" value="true">
              <a href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />?' target="_blank">                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />' target="_blank"> 
       </logic:equal>       
         <bean:write name="forumMessage" property="messageVO.subject"/>
        </a>
        <span>(<bean:write name="forumMessage" property="creationDate" />)</span>
      <logic:equal name="forumMessage" property="root" value="true">
              <a href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />'      target="_blank">                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
              <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'      target="_blank"> 
       </logic:equal>       
        <html:img page="/images/arrow_down.gif" width="18" height="18" alt="点击此处看原文" border="0" />
        </a>      
        </div>
        
       

      <div class="b_content_body">
      <bean:write name="forumMessage" property="messageVO.shortBody[100]" filter="false"/>
     </div>

      <div class="b_content_other">
        <div class="b_content_other_left">                  
        </div>
      </div>
      <div class="b_content_line"> </div>
      	
</logic:notEmpty>
</logic:iterate>

<div class="title_right">
<div class="tres" >    
     共有<b><bean:write name="messageListForm" property="allCount"/></b>贴       
<MultiPages:pager actionFormName="messageListForm" page="/query/blog/userMessageReplyBlog.shtml" name="paramMaps">
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>     
</div>
</div>

</logic:greaterThan>
</logic:present>


<logic:empty name="noheaderfooter">
</td></tr></table></center>

<%@ include file="../searchInputView.jsp" %>


<%@ include file="../queryInputView.jsp" %>

<%@include file="../../common/IncludeBottom.jsp"%>

</logic:empty>
