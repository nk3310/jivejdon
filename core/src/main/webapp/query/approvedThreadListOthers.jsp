<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<logic:present name="threadListForm">
<logic:greaterThan name="threadListForm" property="allCount" value="0">

<bean:define id="title"  value=" 精华帖" />
<%@ include file="../common/IncludeTop.jsp" %>


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

<script>
function digMessage(id)
{            
	var pars = 'messageId='+id;   
    new Ajax.Updater('digNumber_'+id, getContextPath() +'/updateDigCount.shtml', { method: 'get', parameters: pars });
    $('textArea_'+id).update("顶一下");
    
}
</script>
<div  style="width:971px; margin:0 auto;">
<div  style="width:80%;background-color:#FFFFFF;margin-left: auto;margin-right: auto; float: left;">
 
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
<div class="linkblock">
	
<bean:define id="forumMessage" name="forumThread" property="rootMessage" />	
<DIV class=digg-row-left>
    <SPAN class=diggArea>	
      <DIV class=diggNum id="digNumber_<bean:write name="forumMessage" property="messageId"/>">
        <logic:notEqual name="forumMessage" property="digCount" value="0">
              <bean:write name="forumMessage" property="digCount"/>
        </logic:notEqual>
     </DIV>
  <DIV class="diggLink top8" id="textArea_<bean:write name="forumMessage" property="messageId"/>"><a href="javascript:digMessage('<bean:write name="forumMessage" property="messageId"/>')">顶一下</a></DIV> 	
</SPAN>
</DIV>      
	<div class="post-headline">
	<h3>
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><bean:write name="forumThread" property="name" /></b></a>
     </h3>             
   </div> 
   
   <div class="post-byline">   
        <html:link page="/profile.jsp" paramId="user" paramName="forumThread" paramProperty="rootMessage.account.username"
            target="_blank" ><b><bean:write name="forumThread" property="rootMessage.account.username" /></b
            ></html:link>
            &nbsp;
            <bean:define id="cdate" name="forumThread" property="creationDate" ></bean:define>            
            <%String cdateS = (String)pageContext.getAttribute("cdate"); %>
    <%=cdateS.substring(0, 11) %>   
    &nbsp;&nbsp;
    <html:img page="/images/comment_reply.gif" height="16" width="16"/>
    <bean:write name="forumThread" property="state.messageCount" />讨论
    &nbsp;&nbsp;
    <bean:write name="forumThread" property="viewCount" />浏览
   </div>
    			
     <p>
    <span class="tpc_content">
        <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />..
    </span>            
      </p>                
  

    <div class="post-footer">
    <html:img page="/images/tag_yellow.png" height="16" width="16"/>    
      <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
           </a>
         </span>
             &nbsp;&nbsp;
      </logic:iterate>        
        

    </div>
        
 </div>              	
</logic:iterate>

  
</div>
<div  style=" width:20%; float: right;">
tagged:<br>
<logic:iterate id="threadTag" name="tagsListForm" property="list" >

  <span onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
    <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span class="big18"><bean:write name="threadTag" property="title" /></span>      
    </a>
   </span>
     

<a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="threadTag" property="tagID"/>"
 target="_blank" title="当本标签有新内容加入 自动通知我"  rel="nofollow">
 <html:img page="/images/user_add.gif" width="18" height="18" alt="关注本标签 有新回复自动通知我" border="0" />
 </a>
<span id='count_<bean:write name="threadTag" property="tagID"/>'></span>
<br>
</logic:iterate>

</div>
</div>
<script type="text/javascript" src="<html:rewrite page="/common/js/tags.js"/>"></script>
<script>
  getTagSubCount('<%=request.getContextPath()%>', '<bean:write name="threadTag" property="subscriptionCount"/>', '<bean:write name="threadTag" property="tagID"/>');
</script>

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres" >        
     符合查询主题共有<b><bean:write name="threadListForm" property="allCount"/></b>贴  
<MultiPages:pager actionFormName="threadListForm" page="/query/approvedThreadListOther.shtml"   >
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>     
      </div>
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>


<script>

var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
TooltipManager.showNow(e);   
}

</script>

<%@include file="../common/IncludeBottom.jsp"%>


