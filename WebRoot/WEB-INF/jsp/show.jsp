<%@page import="java.net.URLEncoder"%>
<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%
PageOfficeCtrl poCtrl=(PageOfficeCtrl)request.getAttribute("ctrl");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head lang="en">
	<script type="text/javascript" src="/convert/jquery.min.js"></script>
    <script type="text/javascript" src="/convert/pageoffice.js" id="po_js_main"></script>
</head>
<body>
<div style=" width:auto; height:700px;">
    <%=poCtrl.getHtmlCode("PageOfficeCtrl1")%>
</div>
<script type="text/javascript">
function Save() {
    document.getElementById("PageOfficeCtrl1").WebSave();
}
function CloseFile() {
   window.external.close();
}

function BeforeBrowserClosed(){    
    if (document.getElementById("PageOfficeCtrl1").IsDirty){
        if(confirm("提示：文档已被修改，是否继续关闭放弃保存 ？")){
            return  true;
        }else{
            return  false;
        }
     	
    }
}
$(document).ready(function() {
	if(document.getElementById("PageOfficeCtrl1")==null){
		$("p").hide();
		setTimeout(open,500);//延时3秒 
	}
	//var path = POBrowser.getArgument();
	//document.getElementById("PageOfficeCtrl1").WebOpen("get.do?path="+encodeURI(path), "docNormalEdit", "" );
});
function open(){
	POBrowser.openWindowModeless(window.location.href, 'width=1050px;height =900px;');
}
</script>
</body>
</html>

