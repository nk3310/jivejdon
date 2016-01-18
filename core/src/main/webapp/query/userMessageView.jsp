<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>
<bean:parameter  id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">


<bean:define id="title"  value=" 论坛查询" />
<%@ include file="../common/IncludeTop.jsp" %>


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

</logic:empty>


<!-- second query result -->
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">

<table cellpadding="3" cellspacing="0" border="0" width="971" align="center">
<tr>
    <td class="smallgray">
<div class="tres" >           
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴  
<MultiPages:pager actionFormName="messageListForm" page="/query/threadViewQuery.shtml" name="paramMaps" >
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
		 onClick="goToAnotherPage('<html:rewrite page="/query/threadViewQuery.shtml" name="paramMaps" />',
		 <bean:write name="messageListForm" property="count" />);" />				
	</div>
  </div>
</div>  
</div>      
    </td>
</tr>
</table>


<logic:iterate indexId="i"   id="forumMessage" name="messageListForm" property="list" >

<logic:notEmpty name="forumMessage" property="forumThread">
  <bean:define id="forumThread" name="forumMessage" property="forumThread"/>
  <bean:define id="forum" name="forumMessage" property="forum" />

  <%String bgcolor = "#eeeeee";%>

  
<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
 
<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr>
 <td>
    <table bgcolor="#cccccc" cellpadding="6" cellspacing="1" border="0" width="100%">

    <tr bgcolor="<%=bgcolor%>">
        <td width="1%" rowspan="2" valign="top">
      
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

  <br>

</logic:notEmpty>
</logic:iterate>


<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
<div class="tres" >    
     符合查询结果共有<b><bean:write name="messageListForm" property="allCount"/></b>贴  
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>     
<MultiPages:pager actionFormName="messageListForm" page="/query/threadViewQuery.shtml" name="paramMaps">
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
 

