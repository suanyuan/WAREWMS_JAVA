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
	<div id="show">
		<table id="tb"></table>
	</div>
	<div id="showMt">
		<table id="tbMt"></table>
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
                            left:0,
                            top:0,
                            width:450,
                            height:350,
                            cache: false,
                            onClose : function() {

                            }
                        })
					}
                }
            });

            $('#tbMt').datagrid({
                url : sy.bp()+"/commonController.do?queryMtList",
                method:'POST',
                title: '养护计划',
                pageSize : 100,
                pageList : [50, 100, 200],
                fit: true,
                border: false,
                fitColumns : false,
                nowrap: true,
                striped: true,
                collapsible:false,
                pagination:true,
                rownumbers:true,
                singleSelect:false,
                columns : [[
                    {field: 'sku',		title: '客户编码',	width: 71 },
                    {field: 'locationid',		title: '入库单编号',	width: 101 },
                    {field: 'customerid',		title: '入库类型',	width: 71 },
                    {field: 'qty',		title: '入库状态',	width: 71 }
                ]],
                onLoadSuccess:function(data){
                    if(data.total>0){
                        $('#showMt').dialog({
                            modal : true,
                            title : '养护提醒',
							left:450,
							right:0,
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