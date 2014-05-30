<%@ taglib uri="struts-logic" prefix="logic" %>
<%@ taglib uri="struts-bean" prefix="bean" %>
<%@ taglib uri="struts-html" prefix="html" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<% 
response.setHeader("Pragma","No-cache"); 
response.setHeader("Cache-Control","no-cache"); 
response.setDateHeader("Expires", 0); 
%>
<html>
  <head>
    <title>上传文件</title>
    <META HTTP-EQUIV="Pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
    <META HTTP-EQUIV="Expires" CONTENT="0">   
    <%@ include file="../../common/headerBody.jsp" %>
    
  </head>
<body>
<html:errors />

<logic:present name="errors">
  <logic:iterate id="error" name="errors">
    <B><FONT color=RED>
      <BR><bean:write name="error" />
    </FONT></B>
  </logic:iterate>
</logic:present>


<logic:equal name="upLoadFileForm" property="authenticated" value="false">
 <center>  <h2><font color="red" >对不起，现在没有权限操作本帖。</font> </h2></center>
</logic:equal>   

<logic:equal name="upLoadFileForm" property="authenticated" value="true">


<% int tempId = 0; 
   int allSize=0; 
%>

<html:form action="/message/upload/saveUploadAction.shtml" method="post" >
        <input type="hidden" name="action"  value="delete">
        <input type="hidden" name="forward" id="forward" value="read">
        <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#dddddd">
          <tr class="list" height="24">
            <td width="20">&nbsp;</td>
            <td width="350">文件</td>
            <td width="50">大小</td>
            <td width="52">&nbsp;</td>
          </tr>
    
     <logic:iterate id="upLoadFile" name="upLoadFileListForm" property="list" indexId="tempIdIndex">
          
                <tr><td height="1" colspan="4" bgcolor="#cccccc"></td></tr>
                    <tr class="list">
            <td width="20" bgcolor="#EFECEC">
            <logic:equal  name="upLoadFile"  property="image"  value="true">
               <html:link page="/message/upload/show.jsp" paramId="id" paramName="tempIdIndex" target="_blank">
                <img width="40" height="40" src="<%=request.getContextPath() %>/message/uploadShowAction.shtml?id=<bean:write name="tempIdIndex" />"  border='0' />
                </html:link>
            </logic:equal>
            </td>
            <td width="350" bgcolor="#EFECEC"><bean:write name="upLoadFile"  property="name"/></td>
            <td width="50"  bgcolor="#EFECEC"><bean:write name="upLoadFile"  property="size"/>K</td>
            <td width="52" bgcolor="#EFECEC"> 
            <a href="<%=request.getContextPath()%>/message/upload/saveUploadAction.shtml?action=delete&id=<bean:write name="tempIdIndex" />"
            target="target_upload">
            <html:img page="/images/button_delete.gif" alt="删除"/> 
            </a> </td>
          </tr>
          <bean:define id="count" name="upLoadFile"  property="size" type="java.lang.Integer"/>
          
          <%
          allSize = allSize+ count.intValue();
          tempId ++;
          
          %>
     </logic:iterate>
          
          <tr><td height="1" colspan="4" bgcolor="#dddddd"></td></tr>
                    <tr class="list" height="25">
            <td width="20">&nbsp;</td>
            <td width="350">总　　计</td>
            <td width="50"><%=allSize%> k</td>
            <SCRIPT language = "Javascript">
            var attcount = <%=tempId%>;
            if(window.top.attcount != null){
                window.top.attcount =  <%=tempId%>;
             }
            if(window.parent != null){
                if(window.parent.document.all.attachsize!=null){	
                    window.parent.document.all.attachsize.value = "有<%=tempId%>个附件 共<%=allSize%>k";
                 }
              }
            </script>
            <td width="52">&nbsp;</td>
          </tr>
        </table>
        
</html:form>

<!-- This iframe is used as a place for the post to load -->
<iframe id='target_upload' name='target_upload' src='' style='display: none'></iframe>


<html:form action="/message/upload/saveUploadAction.shtml" enctype="multipart/form-data"
 onsubmit="return uploadValid($F('uploadFile'))"
 target="target_upload">

<input type="hidden" name="action" value="create" />
<input type="hidden" name="tempId" value="<%=tempId%>" />
<input type="hidden" name="id" value="<%=tempId%>" />

<html:hidden property="parentId" />
<html:hidden property="parentName" />
<center><input id="submitButton" type="button" value="全部上传完成按此关闭本窗口" class="Button" onClick="closeThisWindow()"></center>
<br>
本地附件:<html:file property="theFile" size="30" styleId="uploadFile"/> 
<html:submit property="submit" value="上传"/>
<br>
附件描述:<html:text property="description" size="30" />
<br>
网上附件:<html:text property="theFileUrl" size="30" value="http://"  styleId="theFileUrl"/>



<SCRIPT language = "Javascript">
<!--
function closeThisWindow(){ 
  var surl = urlAction();
  window.top.killUploadWindow(surl, attcount);
}

function urlAction(){
    var saveS = "";
    var theFile = $F('theFileUrl');
      
    if ( (theFile != null) && (theFile.toLowerCase().indexOf("http") > -1 )){
	     if (isImage(theFile)){
    	   saveS="[img]"+theFile+"[/img]";
	     }else{
	      if (theFile != "http://")
             saveS="[url="+theFile+"]"+theFile+"[/url]";
       }
     } 
     return saveS;
}

function uploadValid(field){
	if (field.toLowerCase().indexOf("http://") > -1){
        myalert("必须提供你硬盘上文件上传");
         return false;
	}
    if (!isAuth(field)){
       myalert("对不起，上传附件文件的类型不在允许的类型之中");
       return false;
   }
}
-->
//-->
</script>

</table>
<br>
<SCRIPT language = "Javascript">

function isAuth(field){
  <logic:iterate id="fileType" name="upLoadFileForm" property="fileTypes" >
     if (field.toLowerCase().indexOf(".<bean:write name="fileType"/>") > -1){
         return true;
     }
     
   </logic:iterate> 
   return false;
}

function isImage(field){
    if ((field == null) || (field == "")) {
        return false;  
     }
     <logic:iterate id="imageType" name="upLoadFileForm" property="imagesTypes" >
     if (field.toLowerCase().indexOf(".<bean:write name="imageType"/>") > -1){
         return true;
     }
     
   </logic:iterate> 
     return false;  
}
</SCRIPT>

<div style="font-size:12px; padding:5px;padding-left:20px;line-height:20px">
1.附件在本地硬盘上,请用"浏览"选中硬盘上的附件后,按"上传"按钮,最多传三个附件，
每个最大限制100K以内 <!-- 见struts-config-upload.xml中配置  --> 
上传附件有效类型:
 <logic:iterate id="fileType" name="upLoadFileForm" property="fileTypes" >
  <bean:write name="fileType"/>,
</logic:iterate> 
<br>
2.文件图片在互联网上,在网上附件直接输入url网址。
<br>
3.以上操作完成后，必须按"关闭窗口"。
 
</div>
</html:form>

<script type="text/javascript" language="JavaScript">

function myalert(errorM)
    {
        if (errorM == null) return;
         Dialog.alert(errorM, 
                {windowParameters: {className: "mac_os_x", width:200, height:150}, okLabel: "   确定  "});
    }

</script>

</logic:equal>   


</body>
</html>