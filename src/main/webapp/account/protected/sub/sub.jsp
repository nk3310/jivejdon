<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../../cp/header.jsp" %>

<%--  /account/protected/sub/subAction.shtml?subscribeType=1&subscribeId=XXXX --%>
<%--  /account/protected/sub/subAction.shtml?subscribeType=2&subscribeId=XXXX --%>
<bean:parameter name="subscribeType" id="subscribeType"/>


   <div class="mainarea_right"> 
      <div class="box_mode_2"> 


<html:form action="/account/protected/sub/subSaveAction.shtml" method="post">
<html:hidden name="subscriptionForm" property="subscriptionId"/>
<html:hidden name="subscriptionForm" property="action"/>
<html:hidden name="subscriptionForm" property="subscribeType"/>
<html:hidden name="subscriptionForm" property="subscribeId"/>
	   
<logic:equal name="subscribeType" value="1">
         <div class="title"> 
		    <div class="title_left">加入下面为您关注的主题？</div> 
		    <div class="title_right"></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content><a href="<%=request.getContextPath()%>/thread/<bean:write name="subscriptionForm" property="subscribeId"/>" 
              target="_blank"><bean:write name="subscriptionForm" property="subscribed.name"/></a>
                </div>
           </div>
          </div>	 
</logic:equal>

<logic:equal name="subscribeType" value="2">
         <div class="title"> 
		    <div class="title_left">加入该标签为您关注的？</div> 
		    <div class="title_right"></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content><a href="<%=request.getContextPath()%>/tags/<bean:write name="subscriptionForm" property="subscribeId"/>" 
              target="_blank"><bean:write name="subscriptionForm" property="subscribed.name"/></a>
                </div>
           </div>
          </div>	 
</logic:equal>
	

<div class="content"> 	
<br>
 <html:checkbox name="subscriptionForm" property="sendmsg"/>您关注的有新内容时，是否以站内信息通知？当你登录本系统时，会自动弹出新信息
<br>
<html:checkbox name="subscriptionForm" property="sendemail"/>您关注的有新内容时，是否以发送邮件通知？选中本项，需要验证注册Email。                      
<br>
<html:checkbox name="subscriptionForm" property="sinaweiboForm.sinaweibo"/>您关注的有新内容时，给您发条新浪微博，需要输入微博的用户名和密码，保存时将验证有效性。                      
 <ul><li>微博用户: <html:text name="subscriptionForm" property="sinaweiboForm.userId"/>    </li>
     <li>微博密码: <html:password name="subscriptionForm" property="sinaweiboForm.passwd"/>  </li>
 </ul>

<html:submit value="确定"></html:submit> 
	
</html:form>
</div>

</div> 
</div> 
	  


<%@ include file="../../cp/footer.jsp" %>