<%@ page contentType="text/html; charset=UTF-8" %>


<a name="<bean:write name="forumMessage" property="messageId"/>"></a>
<div class="post_warp">
  <div class="post_sidebar">
	<div class="post_author">
          <logic:notEmpty name="forumMessage" property="account">

          <html:link page="/profile.jsp" paramId="user" paramName="forumMessage" paramProperty="account.username">
            <span onmouseover="loadWLJSWithP(this, initUsersW)"  class='Users ajax_userId=<bean:write name="forumMessage" property="account.userId"/>' id="users" >                      
              <b> <span  id='author_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="account.username" /></span></b>                       
          <br><br/>
           <logic:notEmpty name="forumMessage" property="account.uploadFile">
            <img  src="<%=request.getContextPath() %>/img/uploadShowAction.shtml?oid=<bean:write name="forumMessage" property="account.userId"/>&id=<bean:write name="forumMessage" property="account.uploadFile.id"/>"  border='0' />
           </logic:notEmpty>
           </span>           
		  <br/>个人详介按这里<br/>
          </html:link>	
          	  
           <html:link page="/query/threadViewQuery.shtml?queryType=userMessageQueryAction" paramId="user" paramName="forumMessage" paramProperty="account.userId" target="_blank">
           <span class="smallgray"> 发表文章:
              <logic:greaterThan name="forumMessage" property="account.messageCount" value="0">
                 <bean:write name="forumMessage" property="account.messageCount"/>
              </logic:greaterThan>
           </html:link>
           </span>
           <%-- </html:link> --%>          
            <br>
           <span class="smallgray"> 注册时间: <bean:write name="forumMessage" property="account.creationDate"/></span>
           <br>
         <logic:notEmpty name="forumMessage" property="account.username">
         
         <div id="divname">
         <ul class="link">
            <li class="box21">
            <span  class="smallgray">            
            <span id="messageOwnerOnline_<bean:write name="forumMessage" property="messageId"/>"></span>                        
            <bean:define id="username" name="forumMessage" property="account.username" />                      
             <jsp:include page="./onlineCheck.jsp" flush="true">   
                  <jsp:param name="username" value="<%=username%>"/>   
             </jsp:include>         
             </span>     
            </li>
            
            <li class="box22"">
             <a href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=3&subscribeId=<bean:write name="forumMessage" property="account.userId"/>"
                target="_blank" title="我要关注该作者发言"  rel="nofollow">    
              <span class="smallgray">          
              <logic:greaterThan name="forumMessage" property="account.subscriptionCount" value="0">
                 <bean:write name="forumMessage" property="account.subscriptionCount"/>人关注
              </logic:greaterThan>
              <logic:lessEqual name="forumMessage" property="account.subscriptionCount" value="0">
                            关注他
              </logic:lessEqual>
              </span>
              </a></li>
                                      
             <li class="box23">         
             <bean:define id="messageTo" name="forumMessage" property="account.username" />
             <a href="javascript:void(0);" onclick="loadWLJSWithP('<html:rewrite page="/account/protected/shortmessageAction.shtml" paramId="messageTo"  paramName="messageTo" />', openShortmessageWindow);" rel="nofollow">          
             <span class="smallgray">悄悄话</span>
              </a>
             </li>     

            <li class="box24">            
            <a href="<%=request.getContextPath()%>/profile.jsp?user=<bean:write name="forumMessage" property="account.username"/>"  rel="nofollow">
            <span class="smallgray">个人博客</span>
            </a>
            </li>   
         </ul>
        </div>
                        
            </logic:notEmpty> 
         </logic:notEmpty>
             
       </div>
     </div>

 <div class="post_header">
	 <div class="post_title">
	  <div class="post_titlename">	           
        <span id='subject_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="messageVO.subject"/></span>
      </div>
      <div nowrap="" class="post_titledate">
        <span class="smallgray" id='creationDate_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="creationDate" /></span>
      </div>
	  <div nowrap="" align="center" class="post_titleright">
         <logic:equal name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true">
              <html:link page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId"
              >编辑 </html:link>       
          </logic:equal>
         <logic:notEqual name="messageListForm" property='<%= "authenticated[" + i + "]" %>' value="true">
            <bean:size id="listSize" name="messageListForm" property="list"/>            
            <logic:equal name="listSize" value="<%= Integer.toString(i+1) %>">
              <span id='<%= "authenticated[" + i + "]" %>'></span>
              <script>                                
              if (document.getElementById("j_username").value == document.getElementById('author_<bean:write name="forumMessage" property="messageId"/>').innerHTML)
            	   document.getElementById('<%= "authenticated[" + i + "]" %>').innerHTML = '<html:link page="/message/messageAction.shtml?action=edit" paramId="messageId" paramName="forumMessage" paramProperty="messageId">编辑 </html:link>';
                         			   
              </script>    
           </logic:equal>                 
          </logic:notEqual>
          
          
       <logic:equal name="i" value="0">
       <logic:notEmpty name="principal">  
       <logic:equal name="loginAccount" property="roleName" value="Admin">
                
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce_all','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">全公告</a>
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','sticky_all','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">全置顶</a>
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','sticky','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">置顶</a>
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce','change','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">公告</a>  
	      
	            <a href="javascript:stickyThread('<bean:write name="forumMessage" property="forumThread.threadId"/>','announce_all','delete','<bean:write name="forumMessage" property="forumThread.forum.forumId"/>')">取消</a>                
	      
	     </logic:equal>  
	     </logic:notEmpty>
       </logic:equal>
       
       <span class="box11">                         
       <logic:equal name="forumMessage" property="root" value="true">
             <a title="到本帖网址" href='<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId" />'>                    
       </logic:equal>
       <logic:equal name="forumMessage" property="root" value="false">
             <a title="到本帖网址" href='<%=request.getContextPath()%>/thread/nav/<bean:write name="forumThread" property="threadId" />/<bean:write name="forumMessage" property="messageId" />#<bean:write name="forumMessage" property="messageId" />'  rel="nofollow"> 
       </logic:equal>                                 
       &nbsp;&nbsp;</a>
       </span>
       <span class="box12""><a  href="javascript:void(0);" onclick="loadWLJSWithP('<bean:write name="forumMessage" property="messageId"/>',loadWPostjs)">&nbsp;&nbsp;</a>
        </span>
       <span class="box13"><a  href="javascript:void(0);" onclick="loadWLJSWithP('<bean:write name="forumMessage" property="messageId"/>',loadQPostjs)">&nbsp;&nbsp;</a>
        </span>
               
     </div>
     
   <div class="post_titletag">    
    <logic:equal name="i" value="0"> 

        <html:link page="/query/tagsList.shtml?count=150" target="_blank" title="标签"><html:img page="/images/tag_yellow.png" width="16" height="16" alt="标签" border="0"/></html:link>
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <span  onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <bean:write name="threadTag" property="title" />
           </a>
            </span>
             &nbsp;&nbsp;&nbsp;&nbsp;
        </logic:iterate>
        
     </logic:equal>
    </div>
   <div class="gen-2"></div>
  </div>
  </div>
  <br>
  <div class="post_body">
	<div class="post_bodyin">
<DIV class=digg-row>
<SPAN class=diggArea>	
  <DIV class=diggNum id="digNumber_<bean:write name="forumMessage" property="messageId"/>">
  <logic:notEqual name="forumMessage" property="digCount" value="0">
    <bean:write name="forumMessage" property="digCount"/>
  </logic:notEqual>
  </DIV>
  <DIV class="diggLink top8" id="textArea_<bean:write name="forumMessage" property="messageId"/>"><a href="javascript:digMessage('<bean:write name="forumMessage" property="messageId"/>')">顶一下</a></DIV> 	
</SPAN>
</DIV>           
<h1 class="tpc_content" id='body_<bean:write name="forumMessage" property="messageId"/>'><bean:write name="forumMessage" property="messageVO.body" filter="false"/></h1>
<logic:equal name="i" value="0"> 
  <div id="advert_468x60" style="text-align:left">	
     <!-- advert -->   
      <jsp:include page="../common/advert.jsp" flush="true">   
       <jsp:param name="fmt" value="728x90"/>   
      </jsp:include>                               
   </div>           
</logic:equal>

   </div>
 <div class="gen-2"></div>
 </div>							
 <div class="gen-2"></div>
 </div>
  
             
