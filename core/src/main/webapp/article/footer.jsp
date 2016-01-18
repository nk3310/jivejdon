<%@ page contentType="text/html; charset=UTF-8" %>



   </td>
              </tr>
            </table>
          </td>
           <td width="1"  height="100%" background="../images/blackpoint.gif" >
<table height="100%" cellpadding="0" cellspacing="0" background="../images/blackpoint.gif" >
              <tr><td> <img name="" src="" width="1" height="1" alt="" ></td></tr></table>
		  </td > 
          <td width="200" valign="top"  bgcolor="#FFFFD7">
          	

   <table width="100%" border="0" cellspacing="0" cellpadding="3"  align="center">
            <tr>
              <td align="left" valign="top"></td>
            </tr>
            <tr>
              <td  valign="top">

<html:form action="/query/searchAction.shtml"  method="post" >
<input type="text"  name="query"  size="13" />
<html:submit value=" 搜索本站 "/>

</html:form>
              
<br>
<a href="http://www.jdon.com/jivejdon/query/tl.shtml?count=200" target=_blank >标签总列表</a>
<br> 
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="180" HEIGHT="300"  
scrolling="no" SRC="<%=request.getContextPath() %>/query/tl.shtml?count=20&tablewidth=240" ></iframe>

<br><br>              
 <p><a href="http://www.jdon.com/jdonframework/app.htm" target="_blank"><font color="#FF3333"><strong>Jdon框架演示</strong></font></a></p>
 <p><a href="http://www.jdon.com/jdonframework/jivejdon3/index.html"  target="_blank"><font color="#FF3333"><strong>JiveJdon3.0<br>
   源码下载</strong></font></a>
     </p>
 <p><a href="http://www.jdon.com/designpatterns/index.htm" target="_blank"><font color="#FF3333"><strong>GoF设计模式</strong></font></a></p>
 <p><a href="http://www.jdon.com/trainning/jiaocheng.htm" target="_blank"><font color="#FF3333"><strong>在线教程</strong></font></a></p>
 <p><a href="http://www.jdon.com/communication.htm" target="_blank"><strong><font color="#FF3333">社区精彩讨论</font></strong></a></p>
            


<p>
<br>
<br>
<br>

<br>
<br>
<br>

<br>

<center>
<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="190" HEIGHT="400"  scrolling="no" 
SRC="<%=request.getContextPath()%>/query/popularlist.shtml?count=15&length=15&tablewidth=190'"></iframe>

<br></br><br></br><br></br>

<IFRAME  MARGINHEIGHT=0 MARGINWIDTH=0 FRAMEBORDER=0 WIDTH="190" HEIGHT="400"  scrolling="no" 
SRC="<%=request.getContextPath()%>/hot/180_190_15_200.html"></iframe>
</center>

<html:form action="/query/threadViewQuery.shtml" method="post">
<html:hidden  name="queryForm" property="queryType" value="HOT1"/>
<input type="hidden"  name="forumId"  value="<bean:write name="forum" property="forumId"/>"/> 
  <table cellspacing="1" cellpadding="0" width="190" bgcolor="#999999" border="0" ID="Table2" align="center">
    <tr>
      <td bgcolor="#ffffff">
          <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" bgcolor="#E6E6E6" ID="table1">
            <tr>            
              <td  align="middle" >              
              查询本论坛
                        <html:select name="queryForm" property="dateRange" >
                <html:optionsCollection name="queryForm" property="dateRanges" value="value" label="name"/>
           </html:select>
           最热门帖子                           
              <html:submit value=" 查询 " property="btnsearch"  style="width:60"/>
            </td>
          </tr>         
        </table>
      </td>
    </tr>
  </table>
</html:form> 
   
				  

              </td>
            </tr>
            <tr><td valign="bottom">
            
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="160x600"/>   
 </jsp:include>              

            	</td></tr>
          </table>            
          </td>
		 
        </tr>
      </table>
    </td>
  </tr>
</table>



<table width="960" border="0" cellspacing="0" cellpadding="0" height="2" bgcolor="#000000"  align="center">
              <tr>
                <td></td>
              </tr>
</table>


<TABLE width="960" border=0 align="center" cellPadding=5 cellSpacing=0>
  <TBODY>
  <tr><td align="center">
       <table><tr><td>
        <html:link page="/rss" target="_blank">
              <html:img page="/images/rss_button.gif" width="36" height="14" border="0"/>
         </html:link>        
        </td>
        <td><a href="http://wap.feedsky.com/jdon" target="_blank" ><html:img page="/images/phone.gif" width="18" height="18" alt="手机阅读" border="0"/></a>
        </td>
        <td>
        <a href="http://www.google.com/ig/add?feedurl=http://www.jdon.com/jivejdon/rss"><html:img page="/images/add-to-google.gif" width="104" height="17" alt="add to google" border="0"/></a>
         </td>
          <td>
        <a href="http://add.my.yahoo.com/rss?url=http://www.jdon.com/jivejdon/rss"><html:img page="/images/add-to-myyahoo.gif" width="91" height="17" alt="add to yahoo" border="0"/></a>
         </td></tr></table>
  </td></tr>
  <TR>
    <TD align="center">
      <span class="white"> 
      <A href="http://www.jdon.com/aboutsite.htm" target=_blank  >关于我们 </A>| <A 
      href="http://www.jdon.com/jivejdon/rss" target=_blank  >RSS订阅</A> |
         <A 
      href="http://www.jdon.com/advert.htm" target=_blank  >广告联系</A> | <A 
      href="http://www.jdon.com/sitemap.html" target=_blank  >网站地图</A> 
      <br>
      <a href="http://www.jdon.com/" target="_blank"  >
        Copyright (C) 2002-2009 Jdon.com, All 
      Rights Reserved 版权所有 上海解道计算机技术有限公司</a><BR>
      <a href="http://www.miibeian.gov.cn/" target="_blank"  >沪ICP备05018152号</a> <A 
      href="http://www.jdon.com/my/feed/feedbackAction.do" target="_blank"  >如有意见请与我们联系</A> <A 
      href="http://www.jdon.com/jdonframework/index.htm"  >Powered by JdonFramework </A></SPAN></TD>
</TR>
  </TBODY></TABLE>
  
<jsp:include page="../common/advert.jsp" flush="true">   
     <jsp:param name="fmt" value="bottom"/>   
 </jsp:include>
     
 <jsp:include page="../common/advert.jsp" flush="true">   
       <jsp:param name="fmt" value="728x90x2_loader"/>   
</jsp:include> 
 

<center>
<jsp:include page="../common/advert.jsp" flush="true">   
       <jsp:param name="fmt" value="footer"/>   
</jsp:include>  
</center>

</body>
</html>
