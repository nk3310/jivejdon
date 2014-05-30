<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<bean:parameter  id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">


<bean:define id="title"  value=" 用户回帖查询" />
<%@ include file="../../common/IncludeTop.jsp" %>


<center>
<jsp:include page="../../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>
<center><table ><tr><td>
</logic:empty>

<!-- second query result -->
<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >

  <bean:define id="forumMessage" name="forumThread" property="rootMessage"/>
  <bean:define id="forum" name="forumMessage" property="forum" />

  
       <div class="b_content_title2">
         <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>"
          target="_blank"><bean:write name="forumMessage" property="messageVO.subject"/>
        </a>
        <span>(<bean:write name="forumMessage" property="creationDate" />)</span>
      
        </div>
        
       

      <div class="b_content_body">
      <bean:write name="forumMessage" property="messageVO.shortBody[100]" />
     </div>

       <div class="b_content_other">
        <div class="b_content_other_left">         
         评论(<bean:write name="forumThread" property="state.messageCount" />)
        </div>
      </div>
      <div class="b_content_line"> </div>
      	
</logic:iterate>

<div class="title_right">
<div class="tres" >    
     共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
    
<MultiPages:pager actionFormName="threadListForm" page="/query/blog/userThreadBlog.shtml" name="paramMaps">
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>     
</div>
</div>

</logic:greaterThan>
</logic:present>



</td></tr></table></center>
