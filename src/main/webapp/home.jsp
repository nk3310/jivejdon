<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/js/slide/style_css.jsp"/>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/post.css"/>
</head>

<body>
<div  style="width: 250px; float: left; overflow: hidden;">
<div id="slideimg" class="block" style="height: 255px; overflow: hidden;">
</div></div>
<script src="<%=request.getContextPath()%>/common/js/prototype.js" type="text/javascript"></script> 
<script src="<%=request.getContextPath()%>/forum/imagesSlide.shtml?count=5&width=240&height=230"></script>
<script src="<%=request.getContextPath()%>/common/js/slide.js"></script>
<script>
slideout();
init();
setTimeout("stop();",60000); 
</script>

<center>
<table  border="0" cellpadding="5" cellspacing="5" bgcolor="#F6F6F4" width="600">
                          <tr>
                            <td align="left">

<div id="approved" ></div>

<script type="text/javascript">
	 function approved(){
        var pars =  "";
        new Ajax.Updater("approved", '<%=request.getContextPath()%>/query/threadApprovedNewList.shtml?start=0&count=15', { method: 'get', parameters: pars });
   }
  approved();
 
  
</script> </td></tr></table>
                        </center>
</body>
</html>
