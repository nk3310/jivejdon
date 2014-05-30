<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%--  显示一个主题下所有帖子
http://127.0.0.1:8080/jivejdon/article/articleList.shtml?thread=106
 --%>
<bean:size id="messageCount" name="messageListForm" property="list" />
<logic:notEqual name="messageCount" value="0">

<bean:define id="forumThread" name="messageListForm" property="oneModel" />

<bean:define id="forum" name="forumThread" property="forum" />

<bean:define id="title" name="forumThread" property="name" />
<%@ include file="header.jsp" %>
<meta name="keywords" content="<logic:iterate id="threadTag" name="forumThread" property="tags" ><bean:write name="threadTag" property="title" />,</logic:iterate>"/>

 <%@ include file="../common/urlUtil.jsp" %>

<!--  get the first message, regard it as article -->
<logic:iterate id="forumMessage" name="messageListForm" property="list" length="1">


<table  cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr><td>
  
     <h3 align="center"><bean:write name="forumMessage" property="messageVO.subject"/></h3>
     
      <p align="center">
      
 <html:link page="/query/tagsList.shtml?count=150" target="_blank" title="标签"><html:img page="/images/tag_yellow.png" width="16" height="16" alt="标签" border="0"/></html:link>
 <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
           </a>
         </span>
             &nbsp;&nbsp;&nbsp;&nbsp;
</logic:iterate>
              <br/>
            作者：<html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username"
            ><b><bean:write name="forumMessage" property="account.username"/></b>
             </html:link>(jdon.com)
            发表时间：<bean:write name="forumMessage" property="creationDate"/>
            <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
             />&forum.forumId=<bean:write name="forum" property="forumId"
             />"  rel="nofollow"><html:img page="/images/reply.gif" width="17" height="17" alt="回复此消息"  border="0"
              />回复</a>  
              
              <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>">原贴</a>
            <br/>

            
       </p>
         
  <!-- advert -->
         <table width="1%" border="0" cellpadding="0" cellspacing="5" align="right"> <tr> <td>
         <div style="margin-top:0px;margin-left:5px;" id="vgad300x250">
                <jsp:include page="../common/advert.jsp" flush="true">   
                  <jsp:param name="fmt" value="336x280"/>   
                </jsp:include>  
           </div>
          </td></tr>
            </table>
          <a name="<bean:write name="forumThread" property="threadId"/>"></a>                    
          <p class="article"><bean:write name="forumMessage" property="messageVO.body" filter="false"/></p>    
                
          <jsp:include page="../common/advert.jsp" flush="true">   
                  <jsp:param name="fmt" value="728x90x2"/>   
          </jsp:include>                  
</td></tr></table>
</logic:iterate>


<div class="tres">  
 这个主题共有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页 
<MultiPages:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId" target="_blank">
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>


</div>
<%
   int row = 1;
%>
<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i" offset="1">
 <%
 String bgcolor = "#FFFFEC";
 if (row++%2 != 1) {
   bgcolor = "#EAE9EA";
 }
 %>
<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
 
<table bgcolor="#cccccc" cellpadding="0" cellspacing="0" border="0" width="100%" align="center">
<tr>
	<td>
    <table bgcolor="#cccccc" cellpadding="4" cellspacing="1" border="0" width="100%">
    
    <tr bgcolor="<%=bgcolor%>">
        <td >
		<table width="100%"  cellpadding="1" cellspacing="1"><tr>
        <td width="97%">

        <b><bean:write name="forumMessage" property="messageVO.subject"/></b>

		</td>
        <td width="1%" nowrap>
        发表: <bean:write name="forumMessage" property="creationDate"/>
        </td>
         
       <td width="1%" nowrap="nowrap" align="center">
        <a href="<html:rewrite page="/message/messageReplyAction.shtml" paramId="parentMessage.messageId" paramName="forumMessage" paramProperty="messageId"
         />&forum.forumId=<bean:write name="forum" property="forumId"
         />"  rel="nofollow">回复</a>
          </td>
		 </tr>
		 </table>
         </td>
    </tr>
     <tr bgcolor="<%=bgcolor%>">
      <td>
      <table cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr><td>
             <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username"
            ><b><bean:write name="forumMessage" property="account.username"/></b>
             </html:link>
           <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="forumMessage" paramProperty="account.userId" target="_blank">
            发表文章: <bean:write name="forumMessage" property="account.messageCount"/>
            </html:link>/
            注册时间: <bean:write name="forumMessage" property="account.creationDate"/>
            </td>
        </tr>
       </table>
     </td>
    </tr>
    <tr bgcolor="<%=bgcolor%>">
        <td width="99%" colspan="4" valign="top">
        <table width="100%" border="0" cellspacing="8" cellpadding="5"  style='TABLE-LAYOUT: fixed'>
          <tr>
            <td  style='word-WRAP: break-word'>
        <span class="tpc_content">
     	<bean:write name="forumMessage" property="messageVO.body" filter="false"/>
    	</span>
		<p>
        </td> </tr>
        </table>
        </td>
    </tr>
    </table>

</td></tr>
</table>

</logic:iterate>


<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" align="center">
<tr>
    <td>
<table bgcolor="#FFFFCC"
 cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
<tr><td>
 
 <%-- 
<jsp:include page="../common/advert.jsp" flush="true">   
  <jsp:param name="fmt" value="article_end"/>   
</jsp:include>  
 --%>
    <td><td >
    </td>
</tr>
</table>
    </td>
</tr>
</table>       


<div id="tooltip_content_hotkeys" style="display:none">
       <div class="tooltip_content">
           <span class="tpc_content">
              点按搜索更多关于该标签的其他讨论...     
           </span>
     </div>
</div>
<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="100%" align="center">
 <tr bgcolor="#FFFFCC">
    <td >
    <table cellpadding="3" cellspacing="0" border="0" width="100%" height="30"  >
     <tr><td  align="center">
        <html:link page="/query/tagsList.shtml?count=150" target="_blank" >标签 </html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />(<bean:write name="threadTag" property="assonum" />)
             </a></span>
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
       </td></tr></table>
    </td>
</tr>

<tr>
    <td>
<table bgcolor="#FFFFCC"
 cellpadding="3" cellspacing="0" border="0" width="100%" align="center">
<tr><td align="right">
<div class="tres">  
共有 <b><bean:write name="messageListForm" property="numReplies" /></b> 回复 ／ <b><bean:write name="messageListForm" property="numPages" /></b> 页

<MultiPages:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId" target="_blank">
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>

</div>
    <td><td >

    </td>
</tr>
</table>
    </td>
</tr>
</table>


<%@include file="footer.jsp"%> 

</logic:notEqual>
