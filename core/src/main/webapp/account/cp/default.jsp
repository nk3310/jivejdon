<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ include file="header.jsp" %>

   <div class="mainarea_right"> 
      <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left">博文主题</div> 
		    <div class="title_right"> <a href="<%=request.getContextPath()%>/forum/post.jsp">
            <html:img page="/images/newtopic.gif" width="113" height="20" border="0" alt="发表新帖子"/></a></div> 
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content>正在读取，请等待...</div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
	  
	  <div class="box_mode_2"> 
	     <div class="title"> 
		    <div class="title_left"><a href="javascript:loadcontent_reply();">更多论坛讨论贴 >></a></div> 		   
		 </div> 
		 <div class="content"> 	 
             <div class="b_content_title2">
                <div id=content_reply></div>
             </div>

            <div class="b_content_line"></div>			 
		 </div> 
	  </div> 
   </div> 
</div> 

<script>


  function loadContent(){
        var pars = "noheaderfooter=1&userID=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('content', '<%=request.getContextPath()%>/query/blog/userThreadBlog.shtml', { method: 'get', parameters: pars  });
   }
  
   function loadcontent_reply(){
        var pars = "noheaderfooter=1&userID=<bean:write name="accountProfileForm" property="account.userId"/>" ;
        new Ajax.Updater('content_reply', '<%=request.getContextPath()%>/query/blog/userMessageReplyBlog.shtml', { method: 'get', parameters: pars  });
   }
   
  loadContent();
  
</script>

<%@ include file="footer.jsp" %>