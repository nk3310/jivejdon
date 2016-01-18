<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>


  
<%  com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>

<%@ page contentType="text/html; charset=UTF-8" %>

<html>
<head>

    <title>Jdon.com</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />

</head>
<bean:parameter id="tablewidth" name="tablewidth" value="155"/>
<bean:parameter id="count" name="count" value="8"/>
<bean:parameter id="query" name="query" value=""/>
<%
String coutlength = (String)pageContext.getAttribute("count");
%>        


<!-- second query result -->
<logic:present name="messageListForm">
<logic:greaterThan name="messageListForm" property="allCount" value="0">
<table width='<bean:write name="tablewidth"/>' border="1" cellpadding="0" cellspacing="0" bordercolor="#dddddd">
  <tr>
    <td bgcolor="#c3d9e7"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="25" align="center"  bordercolor="#eeeeee">       
        <font color="#ffffff" ><b> <bean:write name="query" /></b></font>        
        </td>
      </tr>
      <tr>
        <td >
        <table  width="100%"  cellpadding="2" bgcolor="#ffffff"><tr><td>


<logic:iterate indexId="i"   id="messageSearchSpec" name="messageListForm" property="list" length='<%=coutlength%>' >

<bean:define id="forumMessage" name="messageSearchSpec" property="message"/>
<bean:define id="forumThread" name="forumMessage" property="forumThread"/>

<table bgcolor="#ffffff"
         cellpadding="1" cellspacing="1" border="0" width="100%" style='TABLE-LAYOUT: fixed'>
            <tr>
                <td width="100%" style='word-WRAP: break-word'>
                <html:img page="/images/dot.gif" width="18" height="18" border="0" align="absmiddle" /> 
                             
                  <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
             title="<bean:write name="forumThread" property="name" />" target="_blank">
             <bean:write name="forumThread" property="name" /></a>
             
                          
             <logic:notEmpty name="forumThread" property="state">
               <span class="blackgray" >(<bean:write name="forumThread" property="state.messageCount" />)</span>
             </logic:notEmpty>     
             
            
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



<table cellpadding="2" cellspacing="0" border="0" width="100%">
<tr>
    <td class="smallgray">
<div class="tres">    
更多<html:link page="/query/searchThreadAction.shtml" paramId="query" paramName="query" target="_blank">按这里</html:link>
     符合主题贴共有<b><bean:write name="messageListForm" property="allCount"/></b>贴 
   
    </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>


</body>
</html>
