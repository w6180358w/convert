<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.wordwriter.*"
	pageEncoding="utf-8"%>
<%
	//******************************卓正PageOffice组件的使用*******************************
	FileMakerCtrl fmCtrl = new FileMakerCtrl(request);
	fmCtrl.setServerPage(request.getContextPath()+"/poserver.zz"); //此行必须

	WordDocument worddoc = new WordDocument();
	//先在要插入word文件的位置手动插入书签,书签必须以“PO_”为前缀
	//给DataRegion赋值,值的形式为："[word]word文件路径[/word]、[excel]excel文件路径[/excel]、[image]图片路径[/image]"
	DataRegion data1 = worddoc.openDataRegion("PO_p1");
	data1.setValue("[word]F:\\1.doc[/word]");
	DataRegion data2 = worddoc.openDataRegion("PO_p2");
	data2.setValue("[word]F:\\2.doc[/word]");
	DataRegion data3 = worddoc.openDataRegion("PO_p3");
	data3.setValue("[word]F:\\3.doc[/word]");

	fmCtrl.setWriter(worddoc);
	fmCtrl.setSaveFilePage("mergeSave.do");
	fmCtrl.setFileTitle("newfilename.doc");
	fmCtrl.fillDocument("F:\\test.doc", DocumentOpenType.Word);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<script type="text/javascript" src="/convert/jquery.min.js"></script>
    <script type="text/javascript" src="/convert/Samples4/js/pageoffice.js" id="po_js_main"></script>
	</head>
	<body>
			<div style="width: auto; height: 700px;">
				<!--**************   PageOffice 客户端代码开始    ************************-->
					        <%=fmCtrl.getHtmlCode("PageOfficeCtrl1")%>
				<!--**************   PageOffice 客户端代码结束    ************************-->
			</div>
		<script>
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
			//console.log(POBrowser.sendUserdata);
		}
		</script>
	</body>
</html>