<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>

<%@ page contentType="text/html; charset=UTF-8" %>

  
<%          
com.jdon.jivejdon.util.ToolsUtil.setHeaderCache(25 * 24 * 60 * 60, request, response);
%>

<logic:iterate indexId="i"   id="forumThread" name="announceList_all">

    <tr bgcolor="#FFFFEC">
        <td nowrap="nowrap">
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" /></span>
             </a>
             <br>             
        </logic:iterate>
        </td>
        <td>        
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <html:img page="/images/icon.posting_sticky.gif" border="0" width="15" height="16"/><font color="red">【公告】<bean:write name="forumThread" property="name" /></font>                       
             </span></b></a>          
             
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
              

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
            <span  class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />' target="_blank"  rel="nofollow"> 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
        </logic:notEmpty>
  
           
        </td>
    </tr>
</logic:iterate>
    
<logic:iterate indexId="i"   id="forumThread" name="announceList">

    <tr bgcolor="#FFFFEC">
        <td nowrap="nowrap">
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" /></span>
             </a>
             <br>             
        </logic:iterate>
        </td>
        <td>        
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <html:img page="/images/icon.posting_sticky.gif" border="0" width="15" height="16"/><font color="red">【公告】<bean:write name="forumThread" property="name" /></font>                       
             </span></b></a>
             
             <%--  stick no need js 
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="30">
             <script>
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
              --%>
              
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
                       
             <div id="tooltip_content_<bean:write name="forumThread" property="threadId"/>" style="display:none">
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
            <span  class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />' target="_blank" > 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
        </logic:notEmpty>
 
        </td>
    </tr>
</logic:iterate>

<logic:iterate indexId="i"   id="forumThread" name="stickyList_all">

    <tr bgcolor="#FFFFEC">
        <td nowrap="nowrap">
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" /></span>
             </a>
             <br>             
        </logic:iterate>
        </td>
        <td>        
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <html:img page="/images/icon.posting_sticky.gif" border="0" width="15" height="16"/><font color="blue">【置顶】<bean:write name="forumThread" property="name" /></font>                        
             </span></b></a>
             
              <%--  stick no need js  
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="30">
             <script>
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
             --%>
             
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
 

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
            <span  class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />' target="_blank" > 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
        </logic:notEmpty>
    
           
        </td>
    </tr>
</logic:iterate>

    
<logic:iterate indexId="i"   id="forumThread" name="stickyList">

    <tr bgcolor="#FFFFEC">
        <td nowrap="nowrap">
        <logic:iterate id="threadTag" name="forumThread" property="tags" >
           <a href='<%=request.getContextPath() %>/tags/<bean:write name="threadTag" property="tagID"/>' target="_blank" class="post-tag">
             <span  class='Tags ajax_tagID=<bean:write name="threadTag" property="tagID"/>' >
             <bean:write name="threadTag" property="title" /></span>
             </a>
             <br>             
        </logic:iterate>
        </td>
        <td>        
             <a href="<%=request.getContextPath()%>/thread/<bean:write name="forumThread" property="threadId"/>" 
              target="_blank">
             <b><span class="tooltip html_tooltip_content_<bean:write name="forumThread" property="threadId"/>">
             <html:img page="/images/icon.posting_sticky.gif" border="0" width="15" height="16"/><font color="blue">【置顶】<bean:write name="forumThread" property="name" /></font>                        
             </span></b></a>
             
              <%--  stick no need js  
             <logic:greaterEqual  name="forumThread" property="state.messageCount" value="30">
             <script>
               document.write(generateLinkString("<%=request.getContextPath()%>", '<bean:write name="forumThread" property="state.messageCount" />', "<bean:write name="forumThread" property="threadId"/>", "15"));
             </script>
             </logic:greaterEqual>
              --%>
              
              <logic:greaterThan name="forumThread" property="state.subscriptionCount" value="0">
                 (<bean:write name="forumThread" property="state.subscriptionCount"/>人关注)
              </logic:greaterThan>
                

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
            <span  class='ThreadLastPost ajax_threadId=<bean:write name="forumThread" property="threadId"/>' >
            <bean:write name="lastPost" property="modifiedDate" />
            </span>
            <br> by:
            <logic:equal name="lastPost" property="root" value="true">
                   <a href='<%=request.getContextPath()%>/thread/<bean:write name="lastPost" property="forumThread.threadId" />' target="_blank" >                    
            </logic:equal>
            <logic:equal name="lastPost" property="root" value="false">
                   <a href='<%=request.getContextPath()%>/thread/nav/<bean:write name="lastPost" property="forumThread.threadId" />/<bean:write name="lastPost" property="messageId" />#<bean:write name="lastPost" property="messageId" />' target="_blank" > 
            </logic:equal>
                    <span class='Users ajax_userId=<bean:write name="lastPost" property="account.userId"/>' >
                    <bean:write name="lastPost" property="account.username" /></span></a>
        </logic:notEmpty>
  
           
        </td>
    </tr>
</logic:iterate>


