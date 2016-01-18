<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ taglib uri="/WEB-INF/MultiPages.tld" prefix="MultiPages" %>
<%@ page contentType="text/html; charset=UTF-8" %>


<html><head>
<title>
论坛使用指南
</title>
<link rel="stylesheet" href="<html:rewrite page="/jivejdon_css.jsp"/>" type="text/css" />
<body>


<table border="0" cellpadding="0" cellspacing="0" width="500" align='center'>
<tr>
<td>


本论坛除正常论坛功能以外，有以下扩展功能：
<p>1.手机访问：www.jdon.com首页或该网址: <a href="http://www.jdon.com/m/" >http://www.jdon.com/m/</a>
<p>2.RSS订阅：RSS地址: <a href="http://www.jdon.com/jivejdon/rss">http://www.jdon.com/jivejdon/rss</a>
      <!-- Feedsky FEED发布代码开始 -->
<!-- FEED自动发现标记开始 -->
<link title="RSS 2.0" type="application/rss+xml" href="http://feed.feedsky.com/jdon" rel="alternate" />
<!-- FEED自动发现标记结束 -->
<a href="http://feed.feedsky.com/jdon" target="_blank">
<html:img page="/images/cache/icon_sub_c1s16_d.gif" width="50" height="16" alt="RSS" border="0"  vspace="2"  style="margin-bottom:3px" /></a>
<!-- Feedsky FEED发布代码结束 -->              
       或
      <a href="http://feeds.feedburner.com/jdon">http://feeds.feedburner.com/jdon</a> 
       
<p>3.关注功能：<a href="http://www.jdon.com/followus.html" target="_blank" ><b>按此进入关注详细介绍 </b></a>，你对某个主题讨论或标签话题有兴趣时，可以加入你的收藏关注，并可以设置：当它们有新内容，会自动发站内消息或邮件新浪微博通知你。       
<p>4.收藏分享功能： 每个贴右上方都有收藏图标<img alt="" src="http://www.jdon.com/jivejdon/images/user_up.gif">，将当前文章加入自己网摘；帖子尾部有分享图标，可分享到微博上。
<p>5.推荐功能：将按钮<a  href="javascript:(function(){window.open('http://www.jdon.com/jivejdon/importUrl.jsp?subject='+encodeURIComponent(document.title)+'&url='+encodeURIComponent(location.href),'_blank','width=580,height=310');})()" alt="享道！"　>
<img style="cursor:move" src="images/share.gif" alt="享道" border="0" width="50" height="16"/></a> 拖到你浏览器(Chrome Firefox)的书签栏，
      通过此按钮将你看到的好文章分享转贴到本论坛。 
<p>6.个人博客：当你注册以后，就拥有以你用户名为开头的博客域名：http://你的用户名.jdon.com
     在个人博客中发表文章也同时出现在相应的论坛中，约束作者发表与论坛内容性质一致的专业博客。
<p>7.回复提醒功能：在帖子内容中输入"@用户名(空格)"，将给该用户发一个私信，通知他你在这个帖子提到了他。 
<p>9.站内消息：使用AJAX异步更新技术，可以实现两个用户在线实时聊天交流。
<p>10.标签功能：发表新文章时，会出现标签输入，输入本文章的关键字TAG，如果已经存在，会自动弹出可选标签，最好选择已经存在的标签，否则就不要输入标签，由管理员来补充。
<p>11.备份功能：发贴时，系统定时将输入内容复制到你电脑的粘贴板clipboard中(IE会自动弹出提示选择Yes)，如果提交出错或出现意外输入内容丢失了，可以通过Ctrl和V键从粘贴板中恢复出来。

 
</td></tr></table>

</body></html>
