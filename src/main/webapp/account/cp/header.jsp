<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="../../common/security.jsp" %>
<%@ include file="../../common/loginAccount.jsp" %>

<logic:present name="loginAccount" >   
  <% 
//urlrewrite.xml:
//	  <rule>
//	<from>^/blog/(.*)$</from>
//	<!-- note: here must be userId, actually userId'value is username'value, need a model key  -->
//	<to>/account/cp/index.shtml?userId=$1&amp;username=$1</to>
//</rule>
  com.jdon.jivejdon.model.Account account = (com.jdon.jivejdon.model.Account)request.getAttribute("loginAccount");
  String userId = request.getParameter("userId");
  String username = request.getParameter("username");
  if (userId != null || username != null ){
	  //userId vaule maybe username or userId.
	  if ( account.getUsername().equals(username) ||  account.getUserId().equals(userId))
	      request.setAttribute("isOwner", "true"); 
  }else if (userId == null && username == null){ 
	  request.setAttribute("isOwner", "true"); 
	  com.jdon.jivejdon.presentation.form.AccountProfileForm accountProfileForm = new com.jdon.jivejdon.presentation.form.AccountProfileForm();
	  accountProfileForm.setAccount(account);
	  request.setAttribute("accountProfileForm", accountProfileForm);  
  
    } %>
</logic:present>
<% String constantsDomain = "jdon.com"; %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html> 
<head> 
<title><bean:write name="accountProfileForm" property="account.username"/>的博客</title> 
<meta content="text/html; charset=utf-8" http-equiv="Content-Type" />
<meta name="robots" content="nofollow"> 
<link href="<html:rewrite page="/account/cp/themes/default/style/blog.css"/>" rel=stylesheet type=text/css />

<%@ include file="../../common/headerBody.jsp" %>
<script >

var wtitlename;
var url;
function openPopUpWindow(wtitlename, url){
   this.wtitlename = wtitlename;
   this.url=url;
   loadWLJS(openPopUpBlogW);
}     

popupW=null; 
var openPopUpBlogW = function(){
    if (popupW == null) {             
       popupW = new Window({className: "mac_os_x", width:600, height:380, title: wtitlename}); 
       popupW.setURL(url);
       popupW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myW) {    	  
          if (myW == popupW){        	        	
            popupW = null;   
            Windows.removeObserver(this);
          }
        }
       }
     Windows.addObserver(myObserver);
     } 
 }     
 

</script>
</head> 

<body > 
<%@ include file="../loginAJAX.jsp" %>

<div class="topbar"> 
   <div class="topbar_inner"> 
      <div class="topbar_inner_left"><a href="<html:rewrite page="/"/>" target="_blank"><html:img page="/images/jdonsmall.gif" border="0" width="120" height="35" alt="jdon.com" /></a></div> 
      <div class="topbar_inner_right"> 
    <logic:present name="principal" >
         欢迎<bean:write name="principal" />    | 
         <a href="javascript:openPopUpWindow('注册资料修改','<%=request.getContextPath()%>/account/protected/editAccountForm.shtml?action=edit&username=<bean:write name="principal" />')"> 注册资料修改 </a> | 
         <logic:notPresent name="isOwner" >
           <a href="<%=request.getContextPath() %>/blog/<bean:write name="principal" />"><bean:write name="principal" />的博客</a> | 
         </logic:notPresent>
         <html:link page="/jasslogin?logout"> 退出 </html:link>
    </logic:present>
     <logic:notPresent name="principal"  >
     <span onmouseover="loadWLJS(loginW)">
        <a href="javascript:void(0);" onclick='loginW;'>登录</a> | <html:link page="/account/newAccountForm.shtml">注册</html:link>
        </span>  
     </logic:notPresent>
      
    </div> 
   </div> 
</div> 
<div class="headbar"> 
   <div class="headbar_list1"><bean:write name="accountProfileForm" property="account.username"/></div> 
   <div class="headbar_list2">永久域名 <a href="http://<bean:write name="accountProfileForm" property="account.username"/>.<%=constantsDomain%>">http://<bean:write name="accountProfileForm" property="account.username"/>.<%=constantsDomain%></a></div> 
</div> 
<div class="menubar"> 
   <div class="menubar_left"> 
      <ul class="menuitem"> 
	     <li>  </li> 
		 <li>博客空间</li> 
		 <li>  </li> 
	  </ul> 
   </div> 
<html:form action="/query/searchAction.shtml"  method="post" styleId="searchform" >
    <div class="menubar_right">
    <input name="count" type="hidden" value="1"/>
   <input name="userId" type="hidden" value="<bean:write name="accountProfileForm" property="account.userId"/>"/>
    <input name="query" value="请输入搜索关键字" type="text" style="width:140px;" class="inputstyle" onfocus="if (searchform.query.value=='请输入搜索关键字'){searchform.query.value=''}"/>
   <img src="<html:rewrite page="/account/cp/themes/default/images/search.gif" />" align="absmiddle" style="cursor:pointer" onClick="if((searchform.query.value=='') || (searchform.query.value=='请输入搜索关键字')){alert('请输入搜索关键字！');searchform.query.focus();}else{searchform.submit();}"></div> 
</html:form> 
</div> 
<div class="secondbar"></div> 
 <script language="JavaScript" type="text/javascript">
 var uploadW;
 ////loadWLJSWithP(url, openUploadWindow)
var openUploadWindow = function(url){                
    if (uploadW == null) {
       uploadW = new Window({className: "mac_os_x", width:450, height:300, title: " Upload "}); 
       uploadW.setURL(url);
       uploadW.showCenter();
	
	    
	   var myObserver = {
        onClose: function(eventName, myuploadW) {    	  
          if (myuploadW == uploadW){        	
            appendUploadUrl();
            uploadW = null;
            Windows.removeObserver(this);
          }
         }
        }
        Windows.addObserver(myObserver);
    } else
	  uploadW.showCenter();
   }     
   
   function killUploadWindow(){
      if (uploadW != null){  
           uploadW.close();                           
     }
     
     window.location.reload();
   }
   
     function myalert(errorM){
        if (errorM == null) return;
        infoDiagClose();
        Dialog.alert(errorM, 
                {windowParameters: {className: "mac_os_x", width:250, height:200}, okLabel: "   确定  "});
  }
 </script>
<div class="mainarea"> 
   <div class="mainarea_left">      
    <div class="box_mode_1"> 
	     <div class="title"> 
	        <div class="title_left"><a href="<%=request.getContextPath()%>/blog/<bean:write name="accountProfileForm" property="account.username"/>">博主首页</a></div> 
	        <div class="title_right"><a href="http://<%=request.getHeader("host")%>/jivejdon">论坛首页</a></div> 
	     </div> 
	     
		 <div class="content"> 
              <div class="b_photo">
                <logic:notEmpty name="accountProfileForm" property="account.uploadFile">
				  <img src="<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="accountProfileForm" property="account.userId"/>&id=<bean:write name="accountProfileForm" property="account.uploadFile.id"/>"  border='0' />
				</logic:notEmpty>
				
			    <logic:empty name="accountProfileForm" property="account.uploadFile">
				  <img src="<html:rewrite page="/account/cp/themes/default/images/nouserface_1.gif"/>" width="85" height="85" border="0" >
				</logic:empty>
				
				<logic:present name="isOwner" >
				    <br>
					<a href="javascript:loadWLJSWithP('<%=request.getContextPath()%>/account/protected/upload/uploadUserFaceAction.shtml?parentId=<bean:write name="accountProfileForm" property="account.userId"/>', openUploadWindow)"
               				tabindex="4">上传头像</a>	
                </logic:present>                    				
               </div>
              <div class="b_name">
              <span  class='Users ajax_userId=<bean:write name="accountProfileForm" property="account.userId"/>' ><bean:write name="accountProfileForm" property="account.username"/>
              <br>
                <logic:greaterThan name="accountProfileForm" property="account.subscriptionCount" value="0">
                 <bean:write name="accountProfileForm" property="account.subscriptionCount"/>人关注
                </logic:greaterThan>
                <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="accountProfileForm" property="account.userId"/>"
                  target="_blank" title="我要关注该作者"  rel="nofollow">            
                 <html:img page="/images/user_add.gif" width="18" height="18" alt="我要关注该作者" border="0" align="absmiddle"/>
                </a>
              
              
              </span></div>
              <div class="b_op">
              <span onmouseover="loadWLJS(nof)">
               <logic:notPresent name="isOwner" >
                    <a href="javascript:void(0);" onclick="openShortmessageWindow('发消息','<html:rewrite page="/account/protected/shortmessageAction.shtml" paramId="messageTo"  paramName="accountProfileForm"  paramProperty="account.username" />');"
                   >发消息</a>
               </logic:notPresent>
               <logic:present name="isOwner" >
                  
                   <a href="javascript:openPopUpWindow('写消息','<%=request.getContextPath()%>/account/protected/shortmessageAction.shtml')"
               				tabindex="1">写信息</a>
                   <a href="javascript:openPopUpWindow('收件箱','<%=request.getContextPath()%>/account/protected/receiveListAction.shtml?count=10')"
               				tabindex="2">收件箱</a>		
               	<a href="javascript:openPopUpWindow('发信箱','<%=request.getContextPath()%>/account/protected/sendListAction.shtml?count=10')"
               				tabindex="3">发送箱</a>
               	<a href="javascript:openPopUpWindow('草稿箱','<%=request.getContextPath()%>/account/protected/draftListAction.shtml?count=10')"
               				tabindex="4">草稿箱</a>	
              
               </logic:present>
                   </span>
                              
               </div>	
	  </div> 
	  </div> 
      <div class="box_mode_1"> 
	     <div class="title"> 
	       <div class="title_left">个人资料</div> 
	        <div class="title_right">
	        <logic:present name="isOwner" >
	          <a href="javascript:void(0);" onclick="openPopUpWindow('编辑个人公开资料','<html:rewrite page="/account/protected/accountProfileForm.shtml?action=edit" paramId="userId" paramName="accountProfileForm"  paramProperty="account.userId" />')">[编辑]</a>     	       
	        </logic:present>  
	        </div> 
	     </div> 
		 <div class="content"> 
            <div class="list_div">
<logic:iterate id="property" name="accountProfileForm" property="propertys" indexId="i">
<logic:notEmpty name="property"  property="name" >
<ul><li>
   <bean:write name="property"  property="name" />:
   <bean:write name="accountProfileForm"  property='<%= "property[" + i + "].value" %>'   filter="false" />
</li></ul>
</logic:notEmpty>
</logic:iterate>  
              </div>	
           </div> 
	  </div> 
      <div class="box_mode_1">
	     <div class="title">
	     <div class="title_left">我的关注</div>
	     <div class="title_right">
	     <logic:present name="isOwner" >
	          <html:link page="/account/protected/sub/default.jsp" >[编辑]</html:link>    	       
	        </logic:present>  
	     </div>
	     </div>
		 <div class="content">
		 </div>
	  </div>
      <div class="box_mode_1"> 
	     <div class="title"> 
	     <div class="title_left">友情链接</div> 
	     <div class="title_right"></div> 
	     </div> 
		 <div class="content"> 
		 </div> 
	  </div> 
</div> 