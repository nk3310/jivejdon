<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPagesREST.tld" prefix="MultiPagesREST" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="header.jsp" %>

<%@page import="com.jdon.jivejdon.model.ForumThread"%><bean:parameter id="tablewidth" name="tablewidth" value="155"/>

<table width='100%' border="1" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
  <tr>
    <td bgcolor="#c3d9e7"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="25" align="center"  bordercolor="#eeeeee">       
        <b>J道最新内容</b> &nbsp;&nbsp;<a href="<%=request.getContextPath()%>/rss">RSS订阅</a>
        &nbsp;&nbsp;<font color="#ffffff" ></font>        
        </td>
        
      </tr>
      <tr>
        <td >
        
<table  width="100%"  cellpadding="2" bgcolor="#ffffff">
<tr><td>        
<bean:parameter id="count" name="count" value="8"/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%=coutlength%>' >
 
<table bgcolor="#ffffff"
         cellpadding="1" cellspacing="1" border="0" width="100%" style='TABLE-LAYOUT: fixed'>
            <tr>
                <td width="100%" style='word-WRAP: break-word'>
                <html:img page="/images/dot.gif" width="18" height="18" alt="JavaEE" border="0" align="absmiddle" /> 
                             
               <a href="<%=request.getContextPath()%>/mobile/thread/<bean:write name="forumThread" property="threadId"/>" 
               tabindex="<bean:write name="i"  />">
             <bean:write name="forumThread" property="name" /></a>
             
                          
             <logic:notEmpty name="forumThread" property="state">
               <span class="blackgray" >(<bean:write name="forumThread" property="state.messageCount" />)</span>
             </logic:notEmpty>     
             <%
             com.jdon.jivejdon.model.ForumThread thread = (ForumThread)pageContext.getAttribute("forumThread");
             int bodylength = thread.getRootMessage().getMessageVO().getBody().length();
             java.text.DecimalFormat df  = new java.text.DecimalFormat("#.000");
             
             %>
             <span class="blackgray" >[<%=bodylength %>字节]  </span>
                </td>
              </tr>
              
            </table>
            
</logic:iterate>
</td></tr></table>
</td>
      </tr>
    
      
      
      
    </table></td>
  </tr>
</table>

<div class="tres">
 有<b><bean:write name="threadListForm" property="allCount"/></b>主题    
<MultiPagesREST:pager actionFormName="threadListForm" page="/mobile/new" >
<MultiPagesREST:prev name="&#9668;" />
<MultiPagesREST:index displayCount="3" />
<MultiPagesREST:next  name="&#9658;" />
</MultiPagesREST:pager>
</div>  


<script>
   var pageURL = '<%=request.getContextPath() %>/mobile/new';
   var count = <bean:write name="threadListForm" property="count" />;
   var allCount = <bean:write name="threadListForm" property="allCount" />
   var start = <bean:write name="threadListForm" property="start" />;
   document.onkeydown=leftRightgoPageREST;
</script>



<%@include file="footer.jsp"%> 


