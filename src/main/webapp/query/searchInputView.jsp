<%@ page contentType="text/html; charset=UTF-8" %>
<center>

<script language="javascript" src="<html:rewrite page="/common/js/autocomplete.js"/>"></script>

<script type="text/JavaScript">
function changeAction(theForm){
   
      if (theForm.view[0].checked){
    	  theForm.action = '<%=request.getContextPath()%>/query/searchThreadAction.shtml'
	  }else if (theForm.view[1].checked){
		  theForm.action = '<%=request.getContextPath()%>/query/searchAction.shtml'
    	 
	  }
}
 
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


<logic:notPresent name="query">
  <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<table><tr><td align="middle">
  <html:form action="/query/searchThreadAction.shtml"  method="post" styleClass="search" onsubmit="changeAction(this);">
        <input type="text"  name="query"  value="<bean:write name="query"/>" id="queryId" onfocus="javascript:loadAcJS(this.id)" size="40" />
         <html:submit value="论坛搜索"/>                  
         <input type="radio" name="view" checked="checked" />查询主题贴(不包括回贴)
         <input type="radio" name="view" />查询每个帖子
         
         <a href="searchhelp.html" target="_blank">help</a>
         <br><center>热点关键词:<div id="hotkeys"></div></center>         
    </html:form>
    <script type="text/javascript">
    function hotkeys(){
        new Ajax.Updater('hotkeys', '<%=request.getContextPath()%>/query/hotKeys.shtml?method=hotkeys', { method: 'get' });
      }
   
     hotkeys();
    </script>
</td></tr> </table>    
 

</center>