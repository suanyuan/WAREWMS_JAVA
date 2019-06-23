<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	#allmap {overflow: hidden;margin:0;background:url('<c:url value="/images/main.png"/>')no-repeat;}
</style>
<c:import url="/WEB-INF/jsp/include/meta.jsp" /> 
<c:import url="/WEB-INF/jsp/include/easyui.jsp" /> 
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="allmap" data-options="region:'center',border:false"></div>
</body>
</html>