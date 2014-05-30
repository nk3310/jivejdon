<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>

<bean:parameter  id="noheaderfooter" name="noheaderfooter" value=""/>
<logic:empty name="noheaderfooter">

<bean:define id="forumThread" name="messageListForm" property="oneModel" />
<bean:define id="forum" name="forumThread" property="forum" />
<bean:define id="title" name="forumThread" property="name" />
<bean:define id="pagestart" name="messageListForm" property="start" />
<bean:define id="pagecount" name="messageListForm" property="count" />
<%
int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
int currentPageNo = 1;
if (pagecountInt > 0) 
	currentPageNo = (pagestartInt / pagecountInt) + 1;
String titleStr = (String)pageContext.getAttribute("title");
if (titleStr == null)
	response.sendError(404); 	  
if (currentPageNo > 1)
	titleStr = titleStr + "  - 第"+ currentPageNo + "页";
pageContext.setAttribute("title", titleStr);
%>
<%@ include file="header.jsp" %>
<meta name="keywords" content='
<logic:iterate id="threadTag" name="forumThread" property="tags" >
  <bean:write name="threadTag" property="title" />,
</logic:iterate>'/>


<!-- 导航区  -->
<%@include file="nav.jsp"%>
 
<table cellpadding="0" cellspacing="0" width="971" align="center"><tr><td>

<!--  上下主题 start -->
<%@include file="threadsPrevNext1.jsp"%>
<!--  上下主题 结束  -->

</td></tr></table>
</logic:empty>

<logic:notEmpty name="noheaderfooter">
<%
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(0, request, response);
%>
<script>
autoCompleteLogin();
</script>
</logic:notEmpty>
<div id="messageListBody" >

<logic:empty name="forumThread">
   <bean:define id="forumThread" name="messageListForm" property="oneModel" />
   <bean:define id="forum" name="forumThread" property="forum" />
</logic:empty>


<div class="table-entice">
<div class="table-button">   

<div class="tres">         
<MultiPagesREST:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId">
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
<logic:greaterThan name="messageListForm" property="numPages" value="1">
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go"><span class="pageGo">Go</span></a>
有<b><bean:write name="messageListForm" property="numPages" /></b>页
</logic:greaterThan>
阅读<span id="viewcount"><bean:write name="forumThread" property="viewCount" /></span>次 
<logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
     <bean:write name="forumThread" property="state.subscriptionCount"/>人关注
</logic:greaterThan>

<div id="tooltip_content_go"  style="display:none">
<div class="tooltip_content">
  <div class="title">前往下页:</div>
	<div class="form">
		<input type="text" style="width: 50px;" id="pageToGo">
		<input type="button" value=" Go "
		 onClick="goToAnotherPageREST('/thread/<bean:write name="forumThread" property="threadId"/>',
		 <bean:write name="messageListForm" property="count" />);" />				
	</div>
  </div>  
</div>  

</div>
    
    
       &nbsp;<html:link page="/message/messageAction.shtml" paramId="forum.forumId" paramName="forum" paramProperty="forumId">
       <html:img page="/images/newtopic.gif" width="113" height="20" border="0" alt="发表新帖子"/>
       </html:link>
        &nbsp;<a href="javascript:void(0);" onclick="loadWLJSWithP('<bean:write name="forumThread" property="rootMessage.messageId" />',loadWPostjs)">
        <html:img page="/images/replytopic.gif" width="113" height="20" border="0" alt="回复该主题贴"/></a>
    
 </div>  
</div>
    

 
<logic:iterate id="forumMessage" name="messageListForm" property="list" indexId="i">
 
<%@include file="messageListBody.jsp"%>

</logic:iterate>

  
<table bgcolor="#cccccc"
 cellpadding="1" cellspacing="0" border="0" width="971" align="center">
 <tr bgcolor="#FFFFCC">
    <td >
    
      <table cellpadding="3" cellspacing="0" border="0" width="100%" >
           <tr>
             <td>
<!-- JiaThis Button BEGIN -->
<div id="ckepop">
<a href="http://www.jiathis.com/share/" class="jiathis jiathis_txt jtico jtico_jiathis" target="_blank">分享到：</a>
<a class="jiathis_button_tools_1"></a>
<a class="jiathis_button_tools_2"></a>
<a class="jiathis_button_tools_3"></a>
<a class="jiathis_button_tools_4"></a>
</div>
<!-- JiaThis Button END -->
             </td><td  align="right">
             <div class="tres">
<logic:greaterThan name="messageListForm" property="numPages" value="1">
有<b><bean:write name="messageListForm" property="numPages" /></b>页
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
</logic:greaterThan>    
<MultiPagesREST:pager actionFormName="messageListForm" page="/thread" paramId="thread" paramName="forumThread" paramProperty="threadId">
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
</div>     

               </td></tr>
        </table>

</td></tr><tr ><td >
    <table cellpadding="3" cellspacing="0" border="0" width="100%" height="30"  >
     <tr><td  align="center">
        <html:link page="/query/tagsList.shtml?count=150" target="_blank" title="标签"><html:img page="/images/tag_yellow.png" width="16" height="16" alt="标签" border="0"/></html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
         <span onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />(<bean:write name="threadTag" property="assonum" />)
             </a></span>
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
       </td></tr></table>
    
</td></tr></table>

</div>

<logic:empty name="noheaderfooter">
<!--  上下主题 start -->
<%@include file="threadsPrevNext2.jsp"%>
<!--  上下主题 结束  -->

<center>
<div id="hotkeys"></div>
</center>

<table align="center"><tr><td valign="top" >
<div id=newPosts>正在读取，请等待...</div>
</td><td  valign="top">
<div id="vgad336x280">
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="336x280_2_b"/>   
</jsp:include>  
</div>
</td></tr></table>

<div id="approved" class="approved" ></div>

<p>
<br><br>

<div id="reply" style="display:none">
<input type="hidden" id="replySubject" name="replySubject"  value="<bean:write name="forumThread" property="rootMessage.messageVO.subject"/>"/>  
</div>

<%--   loaded in $LAB.js
 <%@ include file="./messageNotfier.jsp" %>
--%>
<div id="isNewMessage" style="display:none"></div>

<script>
$LAB
.script("<html:rewrite page="/common/js/prototype.js"/>").wait()
.script("<html:rewrite page="/forum/js/messageList.js"/>")
.wait(function(){
	var pageURL = '<%=request.getContextPath() %>/thread/<bean:write name="forumThread" property="threadId"/>';
	var start = <bean:write name="messageListForm" property="start" />;
	var count = <bean:write name="messageListForm" property="count" />;
	var allCount = <bean:write name="messageListForm" property="allCount" />
	document.onkeydown=leftRightgoPageREST;		
		
	//below need prototype.js	
 viewcount(<bean:write name="forumThread" property="threadId"/>);
 stickyList();
 if (isDisplayNeedLoad('approved')){
    approveList();
 }else{ 	 
   Event.observe(window, 'scroll', function() {
		setTimeout(function(){
		 if (isDisplayNeedLoad('approved')){	
					    approveList();
         }			
		},1500);
   });
 }
 
 //checkmessage from messageNotfier.jsp
 <logic:present name="principal" >
 var messageChkURL = "<%=request.getContextPath() %>/shortmessage/checknewmessage.shtml";     
 new Ajax.PeriodicalUpdater('isNewMessage', messageChkURL,
  { method: 'get',
    frequency: 300, 
    decay: 2,
    evalScripts: true});
</logic:present>

<logic:notPresent name="principal" >
 var messageChkURL = "<%=request.getContextPath() %>/forum/checknewmessage.shtml";
 new Ajax.Updater('isNewMessage', messageChkURL, { method: 'get', evalScripts: true});
</logic:notPresent>
 
})
.script("http://v2.jiathis.com/code/jia.js");          
</script>



<%@include file="footer.jsp"%> 


   
</logic:empty> 