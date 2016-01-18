<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5  * 60 * 60, request, response);%>
<html>
<head>
<title>home</title>
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<script>
function digMessage(id)
{            
	var pars = 'messageId='+id;   
    new Ajax.Updater('digNumber_'+id, getContextPath() +'/updateDigCount.shtml', { method: 'get', parameters: pars });
    $('textArea_'+id).update("顶一下");
    
}
</script>
</head>

<body>
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

<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres" >             
<MultiPages:pager actionFormName="threadListForm" page="/query/approvedThreadListOther.shtml"  >
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>     
      </div>
    </td>
</tr>
</table>
</body>
</html>
