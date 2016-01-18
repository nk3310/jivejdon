<%@ taglib uri="struts-logic" prefix="logic"%>
<%@ taglib uri="struts-bean" prefix="bean"%>
<%@ taglib uri="struts-html" prefix="html"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>
<%@ include file="../common/security.jsp" %>

<logic:present name="NEWMESSAGES">
	<logic:notEmpty name="NEWMESSAGES">
		<logic:greaterThan name="NEWMESSAGES" value="0">
		   <br><br>
		   <div align="center">
			<html:link page="/shortmessage.jsp" target="_blank">
				<html:img page="/images/message.gif" width="16" height="12"
					styleId="myblank" alt="新消息" title="新消息" border="0" />
				您有<bean:write name="NEWMESSAGES" />条新消息
			</html:link>
			</div>
			
<script> 	 
function loadWLJS(myfunc){
  if (typeof(window.top.TooltipManager) == 'undefined') {     
     window.top.$LAB
     .script('<%=request.getContextPath()%>/common/js/window_def.js').wait()   
     .wait(function(){
          myfunc();          
     })    
  }else
     myfunc();
}
   
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
            if (typeof(window.top.popUpNewMessage) != "undefined")
                window.top.popUpNewMessage();
            else  if (typeof(popUpNewMessage) != "undefined")
                popUpNewMessage();
}

loadWLJS(loadNMJS());

            </script>
		</logic:greaterThan>
		<logic:equal name="NEWMESSAGES" value="0">
            <script> 	 
            if (typeof(window.top.clearPopUP) != "undefined")
                window.top.clearPopUP();
            else  if (typeof(clearPopUP) != "undefined")
                clearPopUP();
            </script>
		   
		</logic:equal>
	</logic:notEmpty>
</logic:present>

