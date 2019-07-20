<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css">
	/*#allmap {overflow: hidden;margin:0;background:url('<c:url value="/images/main1.png"/>')no-repeat;}*/
</style>
<c:import url="/WEB-INF/jsp/include/meta.jsp" /> 
<c:import url="/WEB-INF/jsp/include/easyui.jsp" /> 
</head>
<body class="easyui-layout" data-options="fit:true,border:false">
	<!--<div id="allmap" data-options="region:'center',border:false"></div>-->
	<div style="overflow:hidden;margin:0;text-align:center;background:url('<c:url value="/images/main.png"/>')no-repeat;background-size: cover;" data-options="region:'center',border:false">
		<div style='color:silver;font-family:"微软雅黑";font-weight:300;margin-top:8%'>
			<p style='font-size:30px;color:#fff;'>欢迎使用上海嘉和诚康医疗器械有限公司WMS系统</p>
			<p style='font-size:14px;'>如有疑问请拨打电话：400-000-0000</p>
		</div>
		<!--<img style='position:absolute;top:65%;margin-left:-8%'src='<c:url value="/images/menuImg/logo.png"/>'/>-->
	</div>
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