<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@page import="com.jdon.jivejdon.util.ToolsUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="com.jdon.jivejdon.presentation.form.MessageForm"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
<head>
<title>享道</title>
<%@ include file="./common/headerBody.jsp" %>
<script type="text/javascript" src="<html:rewrite page="/common/js/prototype.js"/>"></script>
</head>
<%@ include file="./account/loginAJAX.jsp" %>
<body>
<%
String subject = "";
if (request.getParameter("subject") != null){
	subject = request.getParameter("subject");
    subject = ToolsUtil.replaceBlank(subject, "\t|\r|\n");
    if (subject.length() >= MessageForm.subjectMaxLength)
       subject = subject.substring(0, MessageForm.subjectMaxLength);
}

String url = "";
if (request.getParameter("url") != null)
	url = request.getParameter("url");

%>

<center>
<a name="post"></a>
<jsp:include page="./forum/threadPost.jsp" flush="true">   
   <jsp:param name="forumId">
      <jsp:attribute name="value" >        
     </jsp:attribute>
   </jsp:param>   
</jsp:include>
</center>
<script>

$('replySubject').value='<%=subject%>';
$('formBody').setAttribute("cols", "60");
$('formBody').setAttribute("rows", "12"); 
$('formBody').value='[url=<%=url%>]<%=subject%>[/url]'
</script>

</body>
</html>