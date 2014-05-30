<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<logic:notEmpty name="messageForm">
<bean:define id="forum" name="messageForm" property="forum"  />
<logic:notEmpty name="forum">
<bean:define id="title" name="forum" property="name" />
<%@ include file="messageHeader.jsp" %>

<table cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr><td><html:img page="/images/blank.gif" width="1" height="10" border="0" alt=""/></td></tr>
</table>

<table cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr>
    <td valign="top" width="98%">

      <%@include file="nav.jsp"%>    
    
    </td>
    <td valign="top" width="1%" align="center">
    
    </td>
</tr>
</table>


<div align="center">

<iframe id='target_new' name='target_new' src='' style='display: none'></iframe>

<html:form action="/message/messageSaveAction.sthml" method="post" target="target_new" styleId="messageNew" onsubmit="return checkPost(this);" >
<html:hidden property="action" />
<html:hidden property="messageId" />
<html:hidden property="forumThread.threadId" />


<table cellpadding="4" cellspacing="0" border="0" width="971" align="center">

<tr>
	<td  width="50" align="right">在 </td>
	<td align="left"> 
     <html:select name="messageForm" property="forum.forumId" styleId="forumId_select">
       <html:option value="">请选择</html:option>
       <html:optionsCollection name="forums" value="forumId" label="name"/>       
     </html:select>
    中操作帖.</td>
</tr>
</table>

<logic:equal name="messageForm" property="authenticated"  value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="messageForm" property="authenticated" value="true">
  
 <jsp:include page="messageFormBody.jsp" flush="true">   
     <jsp:param name="reply" value="false"/>   
 </jsp:include> 
 

<script>
function loadPostjs(){
  if (typeof(openInfoDiag) == 'undefined') {
    $LAB
     .script('<html:rewrite page="/message/js/messageEdit.js"/>').wait()
     .wait(function(){
         setObserve();
     
     })      
  }else
     setObserve();
       
}

function setObserve(){
 if(typeof(Ajax) != "undefined"){
      $('messageNew').observe("submit", callbackSubmit);
  }   
}


function hackAction(myform){
	//hack <input type="hidden" name="action" value="edit"> for IE not work
	var a=[];
	for (var i = 0, p=myform.elements; i < p.length; i++)
         if(p[i].name&&p[i].name.toLowerCase()=='action'){
             a.push([p[i], p[i].name]);
             p[i].removeAttribute('name', 0);
          }
	return a;
}

function recoverAction(a){
	//recover hack
	for (var i = 0; i < a.length; i++)
         a[i][0].setAttribute('name', a[i][1], 0);	 
}

function changeAction(myform, newAction){
	var a = hackAction(myform);
	var oldformAction = myform.action;
	myform.action = newAction;
	recoverAction(a);
	return oldformAction;
}



function notify(){	
	var oldformAction = changeAction(document.messageForm, "<%=request.getContextPath()%>/message/messageSaveAction2.shtml");
	document.messageForm.submit();
		
	changeAction(document.messageForm, oldformAction);
}
notify();
</script>



<table cellpadding="4" cellspacing="0" border="0" width="400">
<tr>
	<td width="20">&nbsp;</td>
  <logic:equal name="messageForm" property="action" value="edit">
	<td>
       <html:link page="/message/messageDeleAction.shtml" paramId="messageId" paramName="messageForm" paramProperty="messageId">
       删除本贴(只有无跟贴才可删除)
       </html:link>	
	</td>
	 <logic:equal name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=false" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
        取消屏蔽
        </html:link></td>   
     </logic:equal>           
        
    <logic:notEqual name="messageForm" property="masked" value="true">
      <td > <html:link page="/message/messageMaskAction.shtml?method=maskMessage&masked=true" paramId="messageId" paramName="messageForm" paramProperty="messageId" >
       屏蔽该贴
        </html:link></td>    
     </logic:notEqual>        
     
   </logic:equal>  
</tr>
</table>

</logic:equal>   

</html:form>
</div>


</logic:notEmpty>
</logic:notEmpty>

<%@ include file="../forum/messageNotfier.jsp" %>

<p><%@include file="../common/IncludeBottom.jsp"%></p>


