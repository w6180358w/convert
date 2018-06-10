<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="utf-8"
%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
// 获取文件磁盘路径
String path = request.getParameter("path");
String show = request.getAttribute("show")+""; 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<c:url value="/" var="rootUrl" scope="application"></c:url>
<c:if test="${fn:contains(rootUrl,';jsession')}">
	<c:set value="${fn:split(rootUrl,';')[0] }" var="rootUrl"
		scope="application" />
</c:if>
<html>
<body>
<%=path %>
     <script type="text/javascript" src="/convert/jquery.min.js"></script>
     <script type="text/javascript" src="/convert/pageoffice.js" id="po_js_main"></script>
     <script type="text/javascript">
         $(document).ready(function() {
        	 setTimeout(open,500);//延时3秒 
         });
         
         function open(){
        	 var path = encodeURIComponent('<%=path%>');
        	 console.log(path);
        	 var url = "${rootUrl}word/<%=show%>.do?path="+path;
        	 console.log(encodeURI(url));
        	 POBrowser.openWindowModeless(encodeURI(url), 'width=1050px;height =900px;',path);
         }
     </script>
</body>
</html>

