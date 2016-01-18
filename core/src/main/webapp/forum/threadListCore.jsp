<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

<table bgcolor="#cccccc" 
 cellpadding="0" cellspacing="0" border="0" width="100%"> 
<tr><td>
    <table bgcolor="#cccccc" id="ejiaA1"
     cellpadding="6" cellspacing="1" border="0" width="100%">
     <tbody >
      <tr bgcolor="#CFCFA0"  height="30" id="ejiaA1_title_tr">     
        <td width="5%"   align="center" nowrap><font color="#ffffff">标签</font></td>
        <td width="80%"  align="center" nowrap>
          <b><font color="#ffffff">&nbsp; 主题名</font></b>
        </td>
        <td width="5%"  align="center" nowrap><b><font color="#ffffff">&nbsp; 阅读 &nbsp;</font></b></td>
        <td width="5%"  align="center" nowrap><b><font color="#ffffff">&nbsp; 回复 &nbsp;</font></b></td>
        <td width="5%" align="center" nowrap><b><font color="#ffffff">&nbsp; 作者 &nbsp;</font></b></td>
        <td width="5%" align="center" nowrap>
     
        <logic:present name="ASC">
           <a href="<%=request.getContextPath()%>/<bean:write name="forum" property="forumId" />">
           <b><font color="#ffffff">最近更新</font></b>   
           <html:img page="/images/desc_order.png" border="0" width="11" height="11" alt="最新在前排列" title="最新在前排列"/>
           </a>   
        </logic:present>
        <logic:notPresent name="ASC">
           <html:link page="/forum.jsp?ASC" paramId="forum" paramName="forum" paramProperty="forumId"
           ><b><font color="#ffffff">最近更新</font></b>
           <html:img page="/images/asc_order.png" border="0" width="11" height="11" alt="最早在前排列" title="最早在前排列"/>
           </html:link>        
        </logic:notPresent>
         </td>          
    </tr>


<logic:iterate indexId="i"   id="forumThread" name="threadListForm" property="list" >
    <tr bgcolor="#FFFFEC" id="tr_<bean:write name="forumThread" property="threadId" />">
        <td nowrap="nowrap">
        
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span onmouseover="loadWLJSWithP(this, initTagsW)" class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" /></span>
             </a>
             <br>             
        </logic:iterate>

        </td>
        <td>
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              >
             <b><span  onmouseover="loadWLJSWithP(this, initTooltipWL)" class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <bean:write name="forumThread" property="name" />                          
             </span></b></a>
             
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="5">
             <script>             
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "5"));
             </script>
             </logic:greaterEqual>
             
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 <span class="tooltip html_tooltip_sub" onmouseover="loadWLJSWithP(this, initTooltipWL)">
                   <a title="关注本主题" href="<%=request.getContextPath()%>/account/protected/sub/subAction.shtml?subscribeType=1&subscribeId=<bean:write name="forumThread" property="threadId" />"  rel="nofollow">
                      <html:img page="/images/user_add.gif" width="18" height="18" alt="关注本主题" border="0" /></a>                    
                    </span>
                    <div id="tooltip_sub" style="display:none">
                         <h3>已有<bean:write name="forumThread" property="state.subscriptionCount"/>人关注本主题</h3>
                        <br>点按进入注册后，当本主题有新帖回复，在下次访问时自动立即通知您。                    
                    </div>
              </logic:greaterThan>
                             
            <!-- for prototype window  -->
            
             <div onmouseover="loadWLJSWithP(this, initTooltipWL)" id="tooltip_content_<bean:write name="forumThread" property="threadId"/>" style="display:none">
               <div class="tooltip_content">
                <span class="tpc_content">
                 <bean:write name="forumThread" property="rootMessage.messageVO.shortBody[100]" />
                 </span>
               </div>
             </div>
            
        </td>
        <td align="center">
            <bean:write name="forumThread" property="viewCount" />            
        </td>
        <td align="center">
            <bean:write name="forumThread" property="state.messageCount" />            
        </td>
        <td nowrap="nowrap">
            &nbsp;
            <bean:define id="rootMessage" name="forumThread" property="rootMessage"></bean:define>
            <logic:notEmpty name="rootMessage"  property="account">
            
            <html:link page="/profile.jsp" paramId="user" paramName="rootMessage" paramProperty="account.username"
            target="_blank" >
            <span class='Users ajax_userId=<bean:write name="rootMessage" property="account.userId"/>' >
                <b><bean:write name="rootMessage" property="account.username" /></b>            
            </span>
            </html:link>

             </logic:notEmpty>

            &nbsp;
        </td>
        <td nowrap="nowrap">
           <logic:notEmpty name="forumThread" property="state.lastPost">
            
            <bean:define id="lastPost" name="forumThread" property="state.lastPost"/>
            <span onmouseover="loadWLJSWithP(this, initLastPost)" class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>            
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />'  rel="nofollow"> 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
                               
        </logic:notEmpty>
        <%-- 
           <span id="<bean:write name="forumThread" property="threadId" />"></span>
            <script>lastPost('<bean:write name="forumThread" property="threadId" />', '<bean:write name="forumThread" property="state.lastPost.messageId" />')</script>
            --%>
           
        </td>
    </tr>
</logic:iterate>

      </tbody>
    </table>
    </td></tr>
</table>

<script language="javascript">
addStickyList('ejiaA1_title_tr','<bean:write name="forum" property="forumId"/>');
ejiaA1("ejiaA1","#fff","#F5F5F5","#FFFFCC","#FFFF84");
</script>

