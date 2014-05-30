<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

  
<% 
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(5 * 24 * 60 * 60, request, response);
%>

<html>
<head>

    <title>热点贴列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />

</head>

<%--  
two caller:
no parameter: /query/hotlist.shtml

parameters:
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="400" HEIGHT="200"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/hotlist.shtml?dateRange=180&tablewidth=400"></iframe>


--%>
<body leftmargin="0" rightmargin="0" topmargin="0">

<bean:parameter id="tablewidth" name="tablewidth" value="160"/>


<table cellpadding="0" cellspacing="0" border="0" width='<bean:write name="tablewidth"/>'>
  <tr>
    <td valign="top">  
   
       <table bgcolor="#cccccc"
     cellpadding="0" cellspacing="0" border="0" width="100%">
        <tr>
          <td> <table bgcolor="#FFFFCC"
         cellpadding="2" cellspacing="0" border="0" width="100%">
              <tr>
              <td align="center" bgcolor="#cccccc" width="80%"><font color="#000000" ><span class="small">热点讨论</span></font></td>
                <td align="right" bgcolor="#cccccc">
                <html:link page="/query/threadViewQuery.shtml?queryType=HOT1&dateRange=180" target="_blank">
                <span class="smallgray">more</span></html:link>
                </td>
                         </tr>
            </table></td>
        </tr>
        <tr>
          <td>
           <table  width="100%"  cellpadding="2" bgcolor="#ffffff"><tr><td>

<bean:parameter id="length" name="length" value="8"/>
<bean:size id="hotcount" name="threadListForm" property="list"/>

<%
String coutlength = (String)pageContext.getAttribute("length");
String randomoffset = "0";

String noRandom = request.getParameter("noRandom");
if (noRandom == null){
	int hotcounti = ((Integer)pageContext.getAttribute("hotcount")).intValue(); 
	int coutlengthi = Integer.parseInt(coutlength);
	if (hotcounti > coutlengthi){
		hotcounti = hotcounti - coutlengthi;
	}
	int randomoffsetI = (int) (Math.random() * hotcounti);
	randomoffset = Integer.toString(randomoffsetI);	
}


%>
<%-- 
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%= coutlength%>' offset='<%= randomoffset %>'>
 --%>
<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" length='<%= coutlength%>' > 
<table bgcolor="#ffffff"
         cellpadding="1" cellspacing="1" border="0" width="100%" style='TABLE-LAYOUT: fixed'>
            <tr>
                <td width="100%" style='word-WRAP: break-word'>
                <html:img page="/images/dot.gif" width="18" height="18"  border="0" align="absmiddle" />
                 
                  <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
             title="<bean:write name="forumThread" property="name" />" target="_blank">
             <bean:write name="forumThread" property="name" /></a>
              <logic:notEmpty name="forumThread" property="state">
                  <span class="blackgray">(<bean:write name="forumThread" property="state.messageCount" />)</span> 
             </logic:notEmpty>
             <span class="blackgray">
             [<logic:iterate id="threadTag" name="forumThread" property="tags" >
                <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" >
                    <span class="blackgray">
                    <bean:write name="threadTag" property="title" />
                    </span>
                 </a>                    
             </logic:iterate>]
                </span>  
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



</body>
</html>
