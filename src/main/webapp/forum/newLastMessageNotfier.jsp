<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ page contentType="text/html; charset=UTF-8" %>



<%--  com.jdon.jivejdon.presentation.action.ThreadEtagFilter  --%>
<%@ include file="./messageNotfier.jsp" %>

<logic:empty name="NEWLASMESSAGE" >
<script>
checkMSg();
</script>
</logic:empty>


 
<logic:notEmpty name="NEWLASMESSAGE" >
<div id="NEWLASMESSAGE"  style="display:none">
<center>
自从您上次访问以来有了更新
<br>
<a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="NEWLASMESSAGE" 
        property="forumThread.threadId" />/<bean:write name="NEWLASMESSAGE" property="messageId"/>#<bean:write name="NEWLASMESSAGE" property="messageId" />'>按这里</a>

<br><br>本窗口1分钟后消失        
        
</center>  
</div>      
</div>

<script>
var loadNMJS = function (){
  if (typeof(window.top.popUpNewMessage) == "undefined"){
     window.top.$LAB
     .script('<%=request.getContextPath()%>/forum/js/newMessage.js').wait()
     .wait(function(){
       popM();
     })    
  }else
       popM();
}

var popM = function(){  
      document.getElementById("isNewMessage").innerHTML = document.getElementById("NEWLASMESSAGE").innerHTML;      
      if (typeof(window.top.popUpNewMessage) != "undefined")
                setTimeout(window.top.popUpNewMessage,2000);
                //window.top.popUpNewMessage();
            else  if (typeof(popUpNewMessage) != "undefined")
                popUpNewMessage();
}

loadWLJS(loadNMJS());

setTimeout(checkMSg,60000);

</script>
</logic:notEmpty>
