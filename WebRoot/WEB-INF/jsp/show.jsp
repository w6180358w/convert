<%@page import="java.net.URLEncoder"%>
<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%
PageOfficeCtrl poCtrl=(PageOfficeCtrl)request.getAttribute("ctrl");
String window = request.getParameter("window");
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

function BeforeBrowserClosed(){    
    if (document.getElementById("PageOfficeCtrl1").IsDirty){
        if(confirm("提示：文档已被修改，是否继续关闭放弃保存 ？")){
            return  true;
        }else{
            return  false;
        }
     	
    }
}
var idWindow = <%=window==null?false:true%>;
$(document).ready(function() {
	if(IEVersion()==-1 && document.getElementById("PageOfficeCtrl1")==null){
		$("p").hide();
		idWindow = true;
		setTimeout(openDoc,500);//延时3秒 
	}
	
});
function openDoc(){
	POBrowser.openWindowModeless(window.location.href+"&window=true", 'width=1050px;height =900px;');
}
function closeWindow(){
	if(idWindow){
		POBrowser.closeWindow();
	}else{
		window.close();
	}
}
function IEVersion() {
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串  
    var isIE = userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1; //判断是否IE<11浏览器  
    var isEdge = userAgent.indexOf("Edge") > -1 && !isIE; //判断是否IE的Edge浏览器  
    var isIE11 = userAgent.indexOf('Trident') > -1 && userAgent.indexOf("rv:11.0") > -1;
    if(isIE) {
        var reIE = new RegExp("MSIE (\\d+\\.\\d+);");
        reIE.test(userAgent);
        var fIEVersion = parseFloat(RegExp["$1"]);
        if(fIEVersion == 7) {
            return 7;
        } else if(fIEVersion == 8) {
            return 8;
        } else if(fIEVersion == 9) {
            return 9;
        } else if(fIEVersion == 10) {
            return 10;
        } else {
            return 6;//IE版本<=7
        }   
    } else if(isEdge) {
        return -1;//edge
    } else if(isIE11) {
        return 11; //IE11  
    }else{
        return -1;//不是ie浏览器
    }
}
</script>
</body>
</html>

