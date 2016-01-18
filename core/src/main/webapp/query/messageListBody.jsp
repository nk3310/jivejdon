<%@ page contentType="text/html; charset=UTF-8" %>


<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
 
<table bgcolor="#ffffff"
 cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr>
 <td>
    <table bgcolor="#cccccc" cellpadding="6" cellspacing="1" border="0" width="100%">

    <tr bgcolor="<%=bgcolor%>">
        <td width="1%" rowspan="3" valign="top">
        <table cellpadding="0" cellspacing="0" border="0" width="160">
        <tr><td>
            <logic:notEmpty name="forumMessage" property="account">
             <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username"
            ><b> <span  class='Users ajax_userId=<bean:write name="forumMessage" property="account.userId"/>' >
                 <bean:write name="forumMessage" property="account.username"/></span></b>
             </html:link>             
            </logic:notEmpty>
            <br><br>
             <bean:define id="messageTo" name="forumMessage" property="account.username" />
          <a href="<html:rewrite page="/account/protected/shortmessageAction.shtml" paramId="messageTo"  paramName="messageTo" />" target="_blank" rel="nofollow">          
             <html:img page="/images/user_comment.gif" width="18" height="18" border="0" /><span class="smallgray">悄悄话</span>
           </a>     
             <br>
            <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="forumMessage" paramProperty="account.userId" target="_blank">
           <span class="smallgray"> 发表文章: <bean:write name="forumMessage" property="account.messageCount"/></span>
            </html:link>
            <br>
           <span class="smallgray"> 注册时间: <bean:write name="forumMessage" property="account.creationDate"/></span>
          
             
            </td>
        </tr>
        </table>
        </td>
        <td >
 
  <table width="100%"  cellpadding="1" cellspacing="1">
    <tr>
        <td width="97%">
       <logic:equal name="forumMessage" property="root" value="true">
             <a href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />'>                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'  rel="nofollow"> 
       </logic:equal>       
        <bean:write name="forumMessage" property="messageVO.subject"/>
        </a>
        </td>
        <td width="1%" nowrap="nowrap">
        <span class="smallgray">
          <bean:write name="forumMessage" property="creationDate" />
         </span>
        </td>
       <td width="1%" nowrap="nowrap" align="center">

      <logic:equal name="forumMessage" property="root" value="true">
                     <a href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />'>                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
                     <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'  rel="nofollow"> 
       </logic:equal>           
       <html:img page="/images/arrow_down.gif" width="18" height="18" alt="到本帖网址" border="0" /></a>
             
        <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
         />&forum.forumId=<bean:write name="forum" property="forumId"
         />"  rel="nofollow"> <html:img page="/images/comment_reply.gif" width="18" height="18" border="0" alt="回复"/></a>
         
      <logic:notEmpty name="messageListForm"> 
       <logic:equal name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true">
           <html:link page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId"
            >编辑</html:link>       
       </logic:equal>       
      </logic:notEmpty>
          </td>
    </tr>
   </table>
   
         </td>
    </tr>
    <tr bgcolor="<%=bgcolor%>"> 
        <td >标签：
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" /></a>
        </logic:iterate> 
        
        </td> 
    </tr> 
    <tr bgcolor="<%=bgcolor%>">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td style='word-WRAP: break-word'>            
  
<span class="tpc_content">
      <bean:write name="forumMessage" property="messageVO.body" filter="false"/>
</span>

        
        </td></tr>
        </table>
        </td>
    </tr>
    </table>

</td></tr>
</table>

             
