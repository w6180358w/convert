<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"%>
<%
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//打开Word文档
poCtrl.webOpen("doc/test.doc",OpenModeType.docNormalEdit,"张佚名");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <title>用js控制工具栏的显示和隐藏</title>

</head>
<body>
    <script type="text/javascript">
        function mySave() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
        
        // 隐藏/显示 标题栏
        function Button1_onclick() {
            var bVisible = document.getElementById("PageOfficeCtrl1").Titlebar;
            document.getElementById("PageOfficeCtrl1").Titlebar = !bVisible;
        }
        
        // 隐藏/显示 菜单栏
        function Button2_onclick() {
            var bVisible = document.getElementById("PageOfficeCtrl1").Menubar;
            document.getElementById("PageOfficeCtrl1").Menubar = !bVisible;
        }


        // 隐藏/显示 自定义工具栏
        function Button3_onclick() {
            var bVisible = document.getElementById("PageOfficeCtrl1").CustomToolbar;
            document.getElementById("PageOfficeCtrl1").CustomToolbar = !bVisible;
        }
        // 隐藏/显示 Office工具栏
        function Button4_onclick() {
            var bVisible = document.getElementById("PageOfficeCtrl1").OfficeToolbars;
            document.getElementById("PageOfficeCtrl1").OfficeToolbars = !bVisible;
        }

    </script>
    <form id="form1" >
    <input id="Button1" type="button" value="隐藏/显示 标题栏"  onclick="return Button1_onclick()" />
    <input id="Button2" type="button" value="隐藏/显示 菜单栏" onclick="return Button2_onclick()" />
    <input id="Button3" type="button" value="隐藏/显示 自定义工具栏"  onclick="return Button3_onclick()" />
    <input id="Button4" type="button" value="隐藏/显示 Office工具栏"  onclick="return Button4_onclick()" />
    <br /><br />
    <div style=" width:auto; height:700px;">
        <%=poCtrl.getHtmlCode("PageOfficeCtrl1")%>
    </div>
    </form>
</body>
</html>
