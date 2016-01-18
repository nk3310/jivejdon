<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<bean:define id="title"  value=" 标签列表" />
<%@ include file="../common/IncludeTop.jsp" %>

<bean:parameter name="queryType" id="queryType" value=""/>


<logic:present name="tagsListForm">
<logic:greaterThan name="tagsListForm" property="allCount" value="0">


<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>


<center>
<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres" >        
    <%-- request.setAttribute("paramMaps", qForm.getParamMaps());  in ThreadQueryAction --%>    
    共有<b><bean:write name="tagsListForm" property="allCount"/></b>标签 
<MultiPages:pager actionFormName="tagsListForm" page="/query/tagsList.shtml"  >
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
				<input type="button" value=" Go " onClick="goToAnotherPage('<html:rewrite page="/query/tagsList.shtml"  />',
				<bean:write name="tagsListForm" property="count" />);" />
				
			</div>
 </div>
</div> 
</div>      
    </td>
</tr>
</table>


<script>

var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
TooltipManager.showNow(e);   
}

</script>
<table width="600">
<tr><td></td><td>包含主题数</td><td>关注</td></tr>
<logic:iterate id="threadTag" name="tagsListForm" property="list" >
<tr><td>
  <span onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
    <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span class="big18"><bean:write name="threadTag" property="title" /></span>      
    </a>
   </span>
     
   &nbsp;&nbsp;&nbsp;&nbsp;
   </td>
<td><bean:write name="threadTag" property="assonum" /></td>
<td>

<a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=<bean:write name="threadTag" property="tagID"/>"
 target="_blank" title="当本标签有新内容加入 自动通知我"  rel="nofollow">
 <html:img page="/images/user_add.gif" width="18" height="18" alt="关注本标签 有新回复自动通知我" border="0" />
 </a>
<span id='count_<bean:write name="threadTag" property="tagID"/>'></span>
 
 
 </td>
</tr>
</logic:iterate>

</table>

<script type="text/javascript" src="<html:rewrite page="/common/js/tags.js"/>"></script>
<script>
  getTagSubCount('<%=request.getContextPath()%>', '<bean:write name="threadTag" property="subscriptionCount"/>', '<bean:write name="threadTag" property="tagID"/>');
</script>



<table cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray" align="center">
<div class="tres">        
    共有<b><bean:write name="tagsListForm" property="allCount"/></b>标签  
<MultiPages:pager actionFormName="tagsListForm" page="/query/tagsList.shtml"  >
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>     
      </div>
    </td>
</tr>
</table>
</center>
</logic:greaterThan>
</logic:present>

<br><br><br><br><br><br><br><br>

<%@ include file="searchInputView.jsp" %>

<%@ include file="queryInputView.jsp" %>

<%@include file="../common/IncludeBottom.jsp"%>