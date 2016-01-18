<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<bean:define id="title"  value=" 论坛查询" />
<%@ include file="../common/IncludeTop.jsp" %>


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>

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

  <%String bgcolor = "#FFFFFF";%>

  <%@include file="./messageListBody.jsp"%>

  <br>

</logic:notEmpty>
</logic:iterate>


<table cellpadding="3" cellspacing="0" border="0" width="971" align="center">
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




<%@ include file="searchInputView.jsp" %>


<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp"%>


