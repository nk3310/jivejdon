<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<bean:define id="threadList" name="threadListForm" property="list" />

<logic:empty name="threadListForm" property="oneModel">
  <% 
  response.sendError(404);  
  %>
</logic:empty>
<bean:define id="forum" name="threadListForm" property="oneModel"/>
<bean:define id="title" name="forum" property="name" />
<bean:define id="pagestart" name="threadListForm" property="start" />
<bean:define id="pagecount" name="threadListForm" property="count" />

<bean:define id="lastModifiedDate" name="forum" property="modifiedDate2"/>
<%

int pagestartInt = ((Integer)pageContext.getAttribute("pagestart")).intValue();
int pagecountInt = ((Integer)pageContext.getAttribute("pagecount")).intValue();
int currentPageNo = 1;
if (pagecountInt > 0) {
	currentPageNo = (pagestartInt / pagecountInt) + 1;
}
String titleStr = (String)pageContext.getAttribute("title");
if (currentPageNo > 1){
	titleStr = titleStr + "  - 第"+ currentPageNo + "页";
}
pageContext.setAttribute("title", titleStr);
%>
<%@ include file="../common/IncludeTop.jsp" %>
<script>
 if(top !== self) top.location = self.location;
  contextpath = "<%=request.getContextPath()%>";
 </script>
<script language="javascript" src="<html:rewrite page="/forum/js/threadList.js"/>"></script>



<!-- 导航区  -->
<%@include file="nav.jsp"%>

<center>
<jsp:include page="../common/advert.jsp" flush="true">   
   <jsp:param name="fmt" value="728x90"/>   
</jsp:include>
</center>


<table cellpadding="3" cellspacing="0" border="0" width="971" align="center">
<tr>
    <td>
<div class="tres" >    
              
<MultiPages:pager actionFormName="threadListForm" page="/forum.jsp" paramId="forum" paramName="forum" paramProperty="forumId">

<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>
<a href="JavaScript:void(0);" onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
总共有<b><bean:write name="threadListForm" property="allCount"/></b>贴   

<div id="tooltip_content_go"  style="display:none">
  <div class="tooltip_content">
			<div class="title">前往下页:</div>
			<div class="form">

				<input type="text" style="width: 50px;" id="pageToGo">
				<input type="button" value=" Go " onClick="goToAnotherPage('<html:rewrite page="/forum.jsp" paramId="forum" paramName="forum" paramProperty="forumId"/>',<bean:write name="threadListForm" property="count" />);" />
				
			</div>
  </div>			
 </div>
</div>  
      
    </td>
    <td align="right">
        &nbsp;<a href="#post" ><html:img page="/images/newtopic.gif" width="113" height="20" border="0"/></a>
    </td>
</tr>
</table>

<table  width="971" border="0" cellpadding="2" cellspacing="1"  align="center" bgcolor="#cccccc">
<tr><td bgcolor="#ffffff">

<%@ include file="threadListCore.jsp" %>


<table bgcolor="#cccccc" cellpadding="1" cellspacing="0" border="0" width="100%">
<tr>
    <td>
<table bgcolor="#FFFFCC" cellpadding="3" cellspacing="0" border="0" width="100%">
<tr>
 <td>
   
    </td>
    <td align="right" >
<div class="tres">    
     总共有<b><bean:write name="threadListForm" property="allCount"/></b>贴         
<MultiPages:pager actionFormName="threadListForm" page="/forum.jsp" paramId="forum" paramName="forum" paramProperty="forumId">
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>
<MultiPages:prev name="&#9668;" />
<MultiPages:index displayCount="3" />
<MultiPages:next  name="&#9658;" />
</MultiPages:pager>   
</div>  
</td>
   
</tr>
</table>
    </td>
</tr>
</table>

</td></tr></table>


<center>
热点TAG: <div id="hotkeys"></div>
</center>



<table align="center"><tr><td valign="top">
<div id=hotList>正在读取，请等待...</div>

</td><td align="center" valign="top" >
<div id="vgad336x280">
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="336x280_2_b"/>   
</jsp:include> 
</div>

</td></tr></table>

<script>
if (typeof(Ajax) != "undefined"){
        new Ajax.Updater('hotkeys', '<%=request.getContextPath()%>/query/hotKeys.shtml?method=hotkeys', { method: 'get' });
        var pars = "";
        new Ajax.Updater('hotList', '<%=request.getContextPath()%>/hot/180_400_10_190.html', { method: 'get', parameters: pars });
}
</script>

<html:form action="/query/threadViewQuery.shtml" method="post">
<html:hidden  name="queryForm" property="queryType" value="HOT1"/>
<input type="hidden"  name="forumId"  value="<bean:write name="forum" property="forumId"/>"/> 
  <table cellspacing="1" cellpadding="0" width="971" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="center" >              
              查询本论坛内
                        <html:select name="queryForm" property="dateRange" >
                <html:optionsCollection name="queryForm" property="dateRanges" value="value" label="name"/>
           </html:select>
           回复超过<input type="text" name="messageReplyCountWindow"  size="4" value="10"/>的热门帖子                           
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>                        
            </td>
          </tr>
         
        </table>
      </td>
    </tr>
  </table>
</html:form> 

<center>
<a name="post"></a>
<jsp:include page="threadPost.jsp" flush="true">   
   <jsp:param name="forumId">
      <jsp:attribute name="value" >
        <bean:write name="forum" property="forumId"/>
     </jsp:attribute>
   </jsp:param>   
</jsp:include>
</center>


<%@ include file="./messageNotfier.jsp" %>


<%@include file="../common/IncludeBottom.jsp"%>


