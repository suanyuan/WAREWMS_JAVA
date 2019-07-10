<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	/*#allmap {overflow: hidden;margin:0;background:url('<c:url value="/images/main.png"/>')no-repeat;}*/
</style>
<c:import url="/WEB-INF/jsp/include/meta.jsp" /> 
<c:import url="/WEB-INF/jsp/include/easyui.jsp" /> 
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<div id="allmap" data-options="region:'center',border:false"></div>
	<div id="show" style="display: none;">
		<table id="tb"></table>
	</div>

	<script>
		$(function () {
            $.ajax({
                url : sy.bp()+"/gspEnterpriseInfoController.do?getBusinessLicenseOutTime",
                type : 'POST', dataType : 'JSON',async  :true,
                success : function(result){
                    if(result.obj){
                        $('#tb').datagrid({
                            data:result.obj,
                            columns:[[
                                {field:'enterpriseNo',title:'企业信息代码',width:100},
                                {field:'shorthandName',title:'简称',width:100},
                                {field:'enterpriseName',title:'企业名称',width:100},
                                {field:'outTime',title:'是否过期',width:100,formatter:outTimeFormatter}
                            ]]
                        });

                        $('#show').dialog({
                            modal : true,
                            title : '主体证照过期提醒',
                            width:450,
							height:350,
                            cache: false,
                            onClose : function() {

                            }
                        })
					}
                }
            });
        })
	</script>
</body>
</html>