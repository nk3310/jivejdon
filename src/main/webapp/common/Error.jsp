<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<html>
<head>
<title>
出错
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
</head>
<body>

    <H3>Something happened...</H3>
    <B>可能发生错误，请将本页面地址拷贝告诉管理员！</B>
    <p>返回<html:link page="/index.jsp" > 首页</html:link>
    <script>
    try{
      if (window.top.setInfoConten)
         window.top.setInfoConten('ERROR: 可能发生错误，请将本页面地址拷贝告诉管理员 ');
      }catch(ex){}
   </script>       
    
 <!-- banner  -->   
<table  border="0" align="center">
  <tr>
    <td><table align='center'>
      <tr>
        <td align="center" >
		<a href="http://www.jdon.com/jdonframework/">Powered by Jdon Framework</a> </td>
        </tr>
    </table></td>
  </tr>
</table>
</p>

</body>
</html>