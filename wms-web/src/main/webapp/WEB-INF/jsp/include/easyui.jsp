<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/jquery.cookie.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/jquery.easyui.min.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/datagrid-detailview.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/syUtils.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/swUtils.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<c:set var="themeValue">
	<c:out value="${cookie.easyuiThemeName.value}" default="default"/>
</c:set>
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/js/jquery-easyui/themes/bootstrap/easyui.css"/>' id="easyuiTheme"/>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/js/jquery-easyui/themes/icon.css"/>"/>
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/js/lightbox/css/lightbox.min.css"/>'>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/swCss.css"/>"/>
