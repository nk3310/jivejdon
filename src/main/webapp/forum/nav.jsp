<%--
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
--%>
<%@ page contentType="text/html; charset=UTF-8" %>

<table cellpadding="0" cellspacing="0" border="0" width="971" align="center">
<tr>
<html:form action="/query/searchAction.shtml"  method="post" styleClass="search">
    <td valign="top" width="50%">
     <b>
    <a href="http://www.jdon.com/"
    >首页</a>
    &raquo;
    <html:link page="/" title="返回论坛列表"
    >论坛</html:link>
    &raquo;
    <logic:notEmpty name="forum" >
    <a href="<%=request.getContextPath()%>/<bean:write name="forum" property="forumId" />">  
            <bean:write name="forum" property="name" />
    </a>
    </logic:notEmpty>
    </b>
    
    </td>    
     <td valign="top"  align="right">   
        <input type="text"  name="query" size="25" id="queryId" onfocus="javascript:loadAcJS(this.id,'<%=request.getContextPath()%>')"/>
         <html:submit value="论坛搜索"/>
    </td>
    </html:form>
 </tr>
</table>
   
<script>

function loadAcJS(thisId){
  if (typeof(ac) == 'undefined') {
     $LAB
     .script('<%=request.getContextPath()%>/common/js/autocomplete.js')
     .wait(function(){
          ac(thisId,'<%=request.getContextPath()%>');
     })     
  }else
      ac(thisId,'<%=request.getContextPath()%>');
}
</script>