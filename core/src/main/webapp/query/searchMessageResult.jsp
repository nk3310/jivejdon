<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<%
String title = "搜索所有贴 ";
if (request.getAttribute("query") != null){
	title += (String)request.getAttribute("query");
}
pageContext.setAttribute("title", title);
%>
<%@ include file="../common/IncludeTop.jsp" %>


<center>

<%@ include file="searchInputView.jsp" %>

</center>
<!-- second query result -->
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<table cellpadding="3" cellspacing="0" border="0" width="971" align="center">
<tr>
    <td >
<div class="tres" >      
 
     符合查询的所有帖子共有<b><bean:write name="messageListForm" property="allCount"/></b>贴      
<MultiPages:pager actionFormName="messageListForm" page="/query/searchAction.shtml" name="paramsMap">
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
<div id="tooltip_content_go"  style="display:none">
<div class="tooltip_content">
    <div class="title">前往下页:</div>
	<div class="form">
		<input type="text" style="width: 50px;" id="pageToGo">
		<input type="button" value=" Go "
		 onClick="goToAnotherPage('<html:rewrite page="/query/searchAction.shtml"  name="paramsMap"/>',
		 <bean:write name="messageListForm" property="count" />);"
		 />				
	</div>
</div>
</div>
</div>
    </td>
</tr>
</table>


<logic:iterate indexId="i" id="messageSearchSpec" name="messageListForm" property="list" >
 
<bean:define id="forumMessage" name="messageSearchSpec" property="message"/>
<logic:notEmpty name="forumMessage" property="forumThread">
<bean:define id="forumThread" name="forumMessage" property="forumThread"/>


<table bgcolor="#cccccc"
 cellspacing="0" cellpadding="0" border="0" width="971" align="center">
<tr><td>
<table bgcolor="#cccccc"
 cellspacing="1" cellpadding="6" border="0" width="100%">
 
<tr  bgcolor="#eeeeee" id="changeTR">
   <td >
   主题: <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
             title="<bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />"  target="_blank">
         <span class="tooltip html_tooltip_content_<bean:write name="forumMessage" property="messageId"/>"><bean:write name="forumMessage" property="forumThread.rootMessage.messageVO.subject" />
         </span></a>
         <div id="tooltip_content_<bean:write name="forumMessage" property="messageId"/>" style="display:none">
           <div class="tooltip_content">
                 <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />
           </div>
          </div>
     <br>
      帖子:
      <logic:equal name="forumMessage" property="root" value="true">
                     <a href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />' target="_blank" >                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
                     <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />' target="_blank" > 
       </logic:equal>       
      <bean:write name="forumMessage" property="messageVO.subject" /></a>
      <br>
   <html:link page="/query/tagsList.shtml?count=150" target="_blank" >标签列表</html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" indexId="i" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
             </a>(<bean:write name="threadTag" property="assonum" />)             
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
         
           
    </td>
    	   
     <td rowspan="2" width="150">
     
     <logic:notEmpty name="messageSearchSpec" property="message.account.uploadFile">
            <img  src="<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="messageSearchSpec" property="message.account.userId"/>&id=<bean:write name="messageSearchSpec" property="message.account.uploadFile.id"/>"  border='0' />
     </logic:notEmpty> <br />
          
     作者: <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username"
            ><bean:write name="forumMessage" property="account.username"/></html:link>
<br>         发表时间: <bean:write name="forumMessage" property="modifiedDate" />
<bean:parameter id="userId" name="userId" value="0"/>
<logic:notEqual name="userId" value="0">
<script>
   var postUserId = '<bean:write name="forumMessage" property="account.userId"/>';
   var queryUserId = '<bean:write name="userId" />';
   if (postUserId == queryUserId){
       document.getElementById('changeTR').style.backgroundColor = "#FFFF84";
   }
</script>

</logic:notEqual>
 </td>
</tr>



<tr bgcolor="#FFFFCC">
 <td >
 <span class="tpc_content">
      <bean:write name="messageSearchSpec" property="messageVO.body" filter="false" />
</span>
 </td>
</tr>

</table>
</td></tr>
</table>

</logic:notEmpty>
<br>
</logic:iterate>

<table cellpadding="3" cellspacing="0" border="0" width="971" align="center">
<tr>
    <td class="smallgray" align="right">
<div class="tres" >    
     符合查询的所有帖子共有<b><bean:write name="messageListForm" property="allCount"/></b>贴
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>      
<MultiPages:pager actionFormName="messageListForm" page="/query/searchAction.shtml" name="paramsMap" >
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

<%@ include file="queryInputView.jsp" %>


<%@include file="../common/IncludeBottom.jsp"%>

