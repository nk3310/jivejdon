<%@ page contentType="text/html; charset=UTF-8" %>
 <%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<body id="mainbody">

<table width="971" border="0" cellpadding="0" cellspacing="0" align="center">
  <tr bgcolor="#FFFFFF">
    <td width="120"  rowspan="2"    >
    	<a href="http://www.jdon.com" class="site-logo"><img src='<html:rewrite page="/images/jdon.gif" />' width="120" height="60" alt="JiveJdon Community Forums" border="0" /></a>
    <td  align="right">
    <div class="userbox">                

<font color="#555555" id="loggedInfo">
<logic:present name="principal" >
   欢迎<bean:write name="principal" />   
</logic:present>
 </font>

  <span class="smallgray">
  <a href="javascript:void(0);" target="_blank" onmouseover="loadWLJS(onlinesInf)" >在线</a> 
  </span>

  &nbsp;&nbsp;<html:link page="/thread">主题表</html:link>
  &nbsp;&nbsp;<html:link page="/tags">标签</html:link>
  &nbsp;&nbsp;<html:link page="/query/threadViewQuery.shtml" >查搜</html:link>

<logic:notPresent name="principal" >
  &nbsp;&nbsp;<html:link page="/account/newAccountForm.shtml">注册</html:link>
</logic:notPresent>

<logic:notPresent name="principal" >  
  &nbsp;&nbsp;<a href="javascript:void(0);" onclick='loadWLJS(loginW)'>登陆 </a>    
</logic:notPresent>
              
<logic:present name="principal" >
  &nbsp;&nbsp;<a href="<%=request.getContextPath()%>/blog/<bean:write name="principal"/>" >个人博客</a>
</logic:present>
                  
<logic:present name="principal" >
   &nbsp;&nbsp;<html:link page="/jasslogin?logout">退出 </html:link>   
</logic:present>       

&nbsp;&nbsp;<a href="javascript:void(0);"  onclick="window.location.reload()">刷新</a>

<a href="http://feed.feedsky.com/jdon" target="_blank"><html:img page="/images/feed-icon-12x12-orange.gif" width="12" height="12" alt="RSS" border="0" align="absmiddle"/></a>

  <a href="http://weibo.com/ijdon" target="_blank"><html:img page="/images/sina.png" width="12" height="12" border="0"/></a>     


    </div>


</td>
  </tr>
  <tr>
    <td>
   <table cellpadding="0" cellspacing="1" bgcolor="#ffffff" width="100%">
        <tr >
          <td height="26" bgcolor="#707070">&nbsp;</td>
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/designpatterns/" target="_blank" class="a03">设计模式</a></td>
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/ddd.html" target="_blank" class="a03">领域驱动设计</a></td>
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/design.htm" target="_blank" class="a03">云架构</a></td>
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/jdonframework/jivejdon3/index.html" target="_blank" class="a03">JiveJdon</a></td>                    
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/jdonframework/" target="_blank" class="a03">Jdon框架</a></td>
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/jivejdon/" target="_blank"  class="a03">软件社区</a></td>
          <td height="26" align="center" bgcolor="#888888"  width="100" ><a href="http://www.jdon.com/consultant.html" target="_blank" class="a03">企业咨询</a></td>
          
        </tr>
      </table>
      </td>
  </tr>
</table>

<table width="971" border="0" cellspacing="0" cellpadding="0"  align="center" height="2"  bgcolor="#F97D0D">
  <tr>
    <td></td>
  </tr>
</table>