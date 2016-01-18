<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>

<%@ page contentType="text/html; charset=UTF-8" %>


<%
String title = "搜索主题贴 ";
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
    <td class="smallgray">
<div class="tres" >         
    
     符合查询的主题贴共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 
   
<MultiPages:pager actionFormName="messageListForm" page="/query/searchThreadAction.shtml" paramId="query" paramName="query">
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
		 onClick="goToAnotherPage('<html:rewrite page="/query/searchThreadAction.shtml"  paramId="query" paramName="query"/>',
		 <bean:write name="messageListForm" property="count" />);"
		 />				
	</div>
  </div>
</div>  
</div>
    </td>
</tr>
</table>


<table bgcolor="#cccccc"
 cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr><td>
    <table bgcolor="#cccccc" id="ejiaA1"
     cellpadding="6" cellspacing="1" border="0" width="100%">
    <tr bgcolor="#868602" background="<%=request.getContextPath()%>/images/tableheadbg.gif" height="30">
        <td width="1%" nowrap="nowrap" align="center"><font color="#ffffff">标签</font></td>
        <td width="96%" nowrap="nowrap" align="center">
          <b><font color="#ffffff">&nbsp; 主题名</font></b>
        </td>
        <td width="1%" nowrap="nowrap" align="center"><b><font color="#ffffff">&nbsp; 回复 &nbsp;</font></b></td>
        <td width="1%" nowrap="nowrap" align="center"><b><font color="#ffffff">&nbsp; 作者 &nbsp;</font></b></td>
        <td width="1%" nowrap="nowrap" align="center"><b><font color="#ffffff">最后回复</font></b></td>
    </tr>

<logic:iterate indexId="i"   id="messageSearchSpec" name="messageListForm" property="list" >

<bean:define id="forumMessage" name="messageSearchSpec" property="message"/>
<bean:define id="forumThread" name="forumMessage" property="forumThread"/>

<script>

var initTagsW = function (e){          
 TooltipManager.init('Tags', 
  {url: getContextPath() +'/query/tt.shtml?tablewidth=300&count=20', 
   options: {method: 'get'}},
   {className:"mac_os_x", width:300});   
   TooltipManager.showNow(e);
}

</script>
    <tr bgcolor="#FFFFEC">
        <td nowrap="nowrap">
        
          <logic:iterate id="threadTag" name="forumThread" property="tags" >
            <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
              <span  onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" />
             </span>
             </a>
             <br>             
          </logic:iterate>
        
        </td>
        <td>
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
              <span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
                <b><bean:write name="forumThread" property="name" /></b>
               </span>
             </a>
             <p>            
             <span class="tpc_content">
                <bean:write name="messageSearchSpec" property="messageVO.body" filter="false"/>
             </span>         
             </p>
        </td>
        <td align="center">
            <bean:write name="forumThread" property="state.messageCount" />            
        </td>
        <td nowrap>
            &nbsp;
            <html:link page="/profile.jsp" paramId="user" paramName="forumThread" paramProperty="rootMessage.account.username"
            target="_blank" ><b><bean:write name="forumThread" property="rootMessage.account.username" /></b
            ></html:link>

            &nbsp;
        </td>
        <td nowrap>
         
         <logic:notEmpty name="forumThread" property="state.lastPost">
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
            <bean:write name="lastPost" property="modifiedDate" />
            <br/> by: 
                    <logic:equal name="lastPost" property="root" value="true">
                         <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
                    </logic:equal>
                    <logic:equal name="lastPost" property="root" value="false">
                        <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />' target="_blank" > 
                    </logic:equal>
                   <bean:write name="lastPost" property="account.username" /></a>
        </logic:notEmpty>
        </td>
    </tr>
</logic:iterate>


    </table>
    </td>
</tr>
</table>



<script language="javascript"><!--
//ejiaA1("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");

function ejiaA1(o,a,b,c,d){
  var t=document.getElementById(o).getElementsByTagName("tr");
  for(var i=1;i<t.length;i++){
    t[i].style.backgroundColor=(t[i].sectionRowIndex%2==0)?a:b;
    t[i].onclick=function(){
           if(this.x!="1"){
                 this.x="1";//本来打算直接用背景色判断，FF获取到的背景是RGB值，不好判断
                 this.style.backgroundColor=d;
           }else{
                 this.x="0";
                 this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
           }
    }
    t[i].onmouseover=function(){
          if(this.x!="1")this.style.backgroundColor=c;
    }
    t[i].onmouseout=function(){
          if(this.x!="1")this.style.backgroundColor=(this.sectionRowIndex%2==0)?a:b;
    }
  }
}

ejiaA1("ejiaA1","#fff","#F5F5F5","#FFFFCC","#FFFF84");
--></script>

<table cellpadding="3" cellspacing="0" border="0" width="971" align="center">
<tr>
    <td class="smallgray" align="right">
<div class="tres">        
     符合查询的主题共有<b><bean:write name="messageListForm" property="allCount"/></b>贴
<a href="JavaScript:void(0);"  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_go">Go</a>       
<MultiPages:pager actionFormName="messageListForm" page="/query/searchThreadAction.shtml" paramId="query" paramName="query" >
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

