<%@ page contentType="text/html; charset=UTF-8" %>
<link rel="stylesheet" type="text/css" media="all" href="<%=request.getContextPath()%>/common/js/calendar-win2k-cold-1.css" title="win2k-cold-1" />
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/calendar.js"></script>

<logic:notPresent name="query">
  <bean:parameter name="query" id="query" value=""/>
</logic:notPresent>
<br>
<center>
 <jsp:include page="../common/advert.jsp" flush="true">   
                <jsp:param name="fmt" value="search"/>   
 </jsp:include>  


<html:form action="/query/threadViewQuery.shtml" method="post" onsubmit="return loadWLJSWithP(this, checkPost);">
<html:hidden  name="queryForm" property="queryType" value="HOT2"/>
  <table cellspacing="1" cellpadding="0" width="971" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="middle" >      
              <table width="350"><tr><td align="left">        
              在
     <html:select name="queryForm" property="forumId">
       <html:option value="">所有论坛</html:option>
       <html:optionsCollection name="queryForm" property="forums" value="forumId" label="name"/>
     </html:select>查询发布时间：
      <br/>
              从<html:text styleId="begin_date_b" name="queryForm" property='fromDate' size="20" maxlength="20" 
              /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('begin_date_b', 'y-m-d');">
            <br>到<html:text styleId="end_date_b" name="queryForm" property='toDate' size="20" maxlength="20" 
               /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('end_date_b', 'y-m-d');">               
           <br>
              回复数不少于<input type="text" name="messageReplyCountWindow"  size="4"  value="10"/>
              <html:submit value=" 查询热门主题" property="btnsearch" />
              </td></tr></table>
            </td>
          </tr>

        </table>
      </td>
    </tr>
  </table>
</html:form> 
<br>
<html:form action="/query/threadViewQuery.shtml"  method="post" onsubmit="return checkPost(this);">
<html:hidden  name="queryForm" property="queryType" value="messageQueryAction"/>
  <table cellspacing="1" cellpadding="0" width="971" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>
              <td  align="middle" >
              
            <table width="350"><tr><td align="left">
              在 <html:select name="queryForm" property="forumId">
                     <option value="">所有论坛</option>
                     <html:optionsCollection name="queryForm" property="forums" value="forumId" label="name"/>
              </html:select>中 查询用户名          
          <br>为<html:text name="queryForm" property="username" />发布所有帖子
 
          <br>发布时间 <br>从<html:text styleId="begin_date_b2" name="queryForm" property='fromDate' size="20" maxlength="20" 
              /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('begin_date_b2', 'y-m-d');">
              <br>到<html:text styleId="end_date_b2" name="queryForm" property='toDate' size="20" maxlength="20" 
               /><img width="22" height="21" src="<%=request.getContextPath()%>/images/show-calendar.gif" onclick="return showCalendar('end_date_b2', 'y-m-d');">               
            <br>
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>
                
                </td></tr></table>
            </td>
          </tr>

        </table>
      </td>
    </tr>
  </table>
</html:form>
<script language="JavaScript" type="text/javascript">
//loadWLJSWithP(theForm, checkPost)
var checkPost = function(theForm) {      
      if ((theForm.fromDate.value  == "") ||(theForm.toDate.value  == "") ){          
         Dialog.alert(" 请完整输入条件 起始日期 终止日期 和 用户名", 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
         return false;
      }     
      return true;      
   }
 </script>
</center>



