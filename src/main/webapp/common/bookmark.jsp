<%@ page contentType="text/html; charset=UTF-8" %>

	

<!-- Feedsky FEED发布代码开始 -->
<!-- FEED自动发现标记开始 -->
<link title="RSS 2.0" type="application/rss+xml" href="http://feed.feedsky.com/jdon" rel="alternate" />
<!-- FEED自动发现标记结束 -->
<a href="http://feed.feedsky.com/jdon" target="_blank">
<img src="<%=request.getContextPath()%>/images/cache/icon_sub_c1s16_d.gif" width="50" height="16" alt="RSS" border="0"   /></a>
<!-- Feedsky FEED发布代码结束 -->


<a title="Google网摘" href='javascript:u=window.top.location.href;t=window.top.document.title;c%20=%20""%20+%20(window.getSelection%20? window.getSelection() : document.getSelection ? document.getSelection() : document.selection.createRange().text);var url="http://www.google.com/bookmarks/mark?op=add&bkmk="+encodeURIComponent(u) +"&title="+encodeURIComponent(t);window.open(url,"_blank","scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes"); void 0'>
<img src="<%=request.getContextPath()%>/images/google.gif" width="16" height="16" border="0" alt="google"/></a>


<a title="中文Yahoo网摘" href='javascript:u=window.top.location.href;t=window.top.document.title;c%20=%20""%20+%20(window.getSelection%20? window.getSelection() : document.getSelection ? document.getSelection() : document.selection.createRange().text);var url="http://myweb.cn.yahoo.com/popadd.html?url="+encodeURIComponent(u)+ "&title="+encodeURIComponent(t);window.open(url,"_blank","scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes"); void 0'>
<img src="<%=request.getContextPath()%>/images/yahoo.gif" width="16" height="16" border="0" alt="yahoo"/></a>

<a title="百度搜藏" href='javascript:u=window.top.location.href;t=window.top.document.title;c%20=%20""%20+%20(window.getSelection%20? window.getSelection() : document.getSelection ? document.getSelection() : document.selection.createRange().text);var url="http://cang.baidu.com/do/add?it="+encodeURIComponent(t)+"&amp;iu="+encodeURIComponent(u)+"&amp;dc="+encodeURIComponent(c)+"&amp;fr=ien#nw=1";window.open(url,"_blank","scrollbars=no,width=600,height=450,left=75,top=20,status=no,resizable=yes"); void 0'>
<img src="<%=request.getContextPath()%>/images/baidu.gif" width="16" height="16" border="0" alt="添加到百度搜藏"/></a>

<a title="POCO网摘" href="javascript:d=window.top.document;t=d.selection?(d.selection.type!='None'?d.selection.createRange().text:''):(d.getSelection?d.getSelection():'');void(keyit=window.open('http://my.poco.cn/fav/storeIt.php?t='+escape(d.title)+'&amp;u='+escape(d.location.href)+'&amp;c='+escape(t)+'&amp;img=http://www.h-strong.com/blog/logo.gif','keyit','scrollbars=no,width=475,height=575,status=no,resizable=yes'));keyit.focus();">
<img src="<%=request.getContextPath()%>/images/poco.gif" width="16" height="16" border="0" alt="POCO网摘"/></a>

<a title="新浪ViVi网摘" href="javascript:d=window.top.document;t=d.selection?(d.selection.type!='None'?d.selection.createRange().text:''):(d.getSelection?d.getSelection():'');void(vivi=window.open('http://vivi.sina.com.cn/collect/icollect.php?pid=28&title='+escape(d.title)+'&url='+escape(d.location.href)+'&desc='+escape(t),'vivi','scrollbars=no,width=480,height=480,left=75,top=20,status=no,resizable=yes'));vivi.focus();">
<img src="<%=request.getContextPath()%>/images/vivi.gif" width="16" height="16" border="0" alt="新浪ViVi"/></a>

<a title="QQ网摘" href="javascript:window.open('http://shuqian.qq.com/post?from=3&title='+encodeURIComponent(window.top.document.title)+'&uri='+encodeURIComponent(window.top.document.location.href)+'&jumpback=2&noui=1','favit','width=930,height=470,left=50,top=50,toolbar=no,menubar=no,location=no,scrollbars=yes,status=yes,resizable=yes');void(0)"">
<img src="<%=request.getContextPath()%>/images/qq.gif" width="16" height="16"  border="0" alt="QQ网摘" /></a>

<a title="CSDN网摘" href="JavaScript:d=window.top.document;t=d.selection?(d.selection.type!='None'?d.selection.createRange().text:''):(d.getSelection?d.getSelection():'');void(saveit=window.open('http://wz.csdn.net/storeit.aspx?t='+escape(d.title)+'&u='+escape(d.location.href)+'&c='+escape(t),'saveit','scrollbars=no,width=590,height=300,left=75,top=20,status=no,resizable=yes'));saveit.focus();"  >
<img src="<%=request.getContextPath()%>/images/cn3.gif" width="16" height="16" border="0" alt="CSDN网摘"/></a>

<p>新浪微博:<a href="http://weibo.com/ijdon">weibo.com/ijdon</a>
<p >
<a href="http://www.jdon.com/followus.html" target="_blank" ><b style="font-size: 12">更多关注方式 </b></a>
