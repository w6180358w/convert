
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<title>盖章和签字专题</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link href="../css/style.css" rel="stylesheet" type="text/css" />		
        <script type="text/javascript" src="<%=request.getContextPath()%>/jquery.min.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/pageoffice.js" id="po_js_main"></script>
		<script type="text/javascript">
		
			function refreshDemo(){
				if(confirm("确认复位所有示例的文档吗？")){
					location.href="refresh.jsp";
				}
			}
		</script>
	</head>
	<body>
		<!--content-->
		<div class="zz-content mc clearfix pd-28" align="center">
			<div class="demo mc">
				<h2 class="fs-16">
					盖章和(手写)签字专题
				</h2>
			</div>
			<div style="margin: 10px" align="center">
				<p>
				点击
				<a href="../adminseal.zz" target="_blank">电子印章简易管理平台</a> 添加、删除印章。管理员:admin 密码:111111
				</p>
				<p>
				点击右侧的“全部复位”连接，即可重新演示盖章和签字效果：<a href="javascript:refreshDemo();" >全部复位</a>			
				</p>
			</div>
			<div style="margin: 10px" align="center">
			<h3>
				一、盖章
			</h3>
				<table style="border-collapse: separate; border-spacing: 20px;border: 1px solid #9CF " align="center" width="1200px">
					<tr >
						<th style="border-bottom: 1px solid #9CF ">功能演示</th>
						<th style="border-bottom: 1px solid #9CF ">文件目录</th>
					</tr>
					<tr>
						<td>
							1.常规盖章：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal1/Word1.jsp','width=1200px;height=800px;');">Word1.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal1)
						</td>
					</tr>
					<tr>
						<td>
							2.无需输入用户名、密码盖章：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal2/Word2.jsp?id=1','width=1200px;height=800px;');">Word2.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal2)
						</td>
					</tr>
					<tr>
						<td>
							3.无需输入用户名、密码，并且不弹出印章选择框盖章：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal3/Word3.jsp','width=1200px;height=800px;');">Word3.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal3)
						</td>
					</tr>		
					<tr>
						<td>
							4.编辑模版 - 在模版中添加盖章位置：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal4/Word4.jsp','width=1200px;height=800px;');">Word4.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal4)
						</td>
					</tr>
					<tr>
						<td>
							5.常规指定位置盖章，加盖印章到模板中的指定位置：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal5/Word5.jsp','width=1200px;height=800px;');">Word5.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal5)
						</td>
					</tr>
					<tr>
						<td>
							6.无需输入用户名、密码加盖印章到模板中的指定位置：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal6/Word6.jsp','width=1200px;height=800px;');">Word6.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal6)
						</td>
					</tr>
					<tr>
						<td>7.无需输入用户名、密码，并且不弹出印章选择框加盖印章到模板中的指定位置：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal7/Word7.jsp','width=1200px;height=800px;');">Word7.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal7)
						</td>
					</tr>
					
					<tr>
						<td>
							8.特殊盖章需求实现：盖章后印章不保护文档内容，用户仍可编辑修改，印章不会出现失效字样：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal8/Word8.jsp','width=1200px;height=800px;');">Word8.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal8)
						</td>
					</tr>
					
					<tr>
						<td>
							9.批量盖章：
							<a
								href="javascript:POBrowser.openWindowModeless('BatchAddSeal/Default.jsp','width=1200px;height=800px;');">Default.jsp</a>
						</td>
						<td>
                           (/InsertSeal/BatchAddSeal)
						</td>
					</tr>
					<tr>
						<td>
							10.删除印章：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSeal9/Word9.jsp','width=1200px;height=800px;');">Word9.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSeal9)
						</td>
					</tr>

				</table>
				<div  style="margin: 10px" align="center">
				<h3>二、(手写)签字</h3>
				<table style="border-collapse: separate; border-spacing: 20px;border: 1px solid #9CF" align="center" width="1200px">
					<tr >
						<th style="border-bottom: 1px solid #9CF ">功能演示</th>
						<th style="border-bottom: 1px solid #9CF ">文件目录</th>
					</tr>
					<tr>
						<td>
							1.常规(手写)签字：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSign1/Word1.jsp','width=1200px;height=800px;');">Word1.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSign1)
						</td>
					</tr>
					<tr>
						<td>
							2.无需输入用户名密码签字：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSign2/Word2.jsp','width=1200px;height=800px;');">Word2.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSign2)
						</td>
					</tr>
					<tr>
						<td>
							3.(手写)签字到文档指定位置：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSign3/Word3.jsp','width=1200px;height=800px;');">Word3.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSign3)
						</td>
					</tr>
					<tr>
						<td>
							4.无需输入用户名、密码，(手写)签字到模板中的指定位置（参考一、4示例在模板中添加签字位置）：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSign4/Word4.jsp','width=1200px;height=800px;');">Word4.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSign3)
						</td>
					</tr>
						<tr>
						<td>
							5.特殊(手写)签字需求实现：签批后(手写)签字不保护文档内容，用户仍可编辑修改，(手写)签字不会出现失效字样：
							<a
								href="javascript:POBrowser.openWindowModeless('AddSign5/Word5.jsp','width=1200px;height=800px;');">Word5.jsp</a>
						</td>
						<td>
                           (/InsertSeal/AddSign5)
						</td>
					</tr>
				</table>
			  </div>
			</div>
		</div>
	</body>
</html>
