<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
/* 初始化 */
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();


	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/statisticalAnalysisController.do?showDatagridRptSalesSoList"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: false,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		columns : [[
			{field: 'warehouseid',		title: '仓库编码',	width: 150 },
			{field: 'sourceOrder',		title: '来源订单号',	width: 100 },
			{field: 'soOrderNum',		title: '发货单号码',	width: 100 },
			{field: 'soOrderNo',		title: '出库单号',	width: 150 },
			{field: 'soDate',		    title: '出库日期',	width: 150 },
			{field: 'soType',		    title: '出库类别',	width: 150 },
			{field: 'warehouse',		title: '仓库',	width: 150 },
			{field: 'customerid',		title: '客户名称',	width: 100 },
			{field: 'sku',		        title: '存货编码',	width: 100 },
			{field: 'lotatt12',	        title: '存货名称',	width: 100 },
			{field: 'descrc',           title: '规格型号 ',	width: 100 },
			{field: 'qtyeach',		    title: '数量',	width: 100 },
			{field: 'qty',              title: '件数',	width: 100 },
			{field: 'uom',		        title: '主计量单位',	width: 130 },
			{field: 'lotatt04',         title: '批号',	width: 130 },
			{field: 'lotatt02',	        title: '失效日期',	width: 100},
			{field: 'addwho',		    title: '制单人',	width: 200},
			{field: 'reviewWho',		title: '审核人',	width: 200},
			{field: 'trackingNumber',   title: '快递单号码',	width: 200},
			{field: 'qty1',		        title: '换算率 ',	width: 70},


		]],
		onDblClickCell: function(index,field,value){

		},
		onRowContextMenu : function(event, rowIndex, rowData) {
			event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			});
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});

});


/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		// lotatt03StartDate:$('#lotatt03StartDate').datebox('getValue'),
		// lotatt03EndDate:$('#lotatt03EndDate').datebox('getValue'),
		customerid : $('#customerid').val(),
		lotatt12:$('#lotatt12').val(),
		descrc:$('#descrc').val(),
		lotatt04 : $('#lotatt04').val(),
	});
};

/* 导出start */
var doExport = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
		param.put("token", token);
		// param.put("lotatt03StartDate",$('#lotatt03StartDate').datebox('getValue'));
		// param.put("lotatt03EndDate",$('#lotatt03EndDate').datebox('getValue'));
		param.put("customerid",$('#customerid').val());
		param.put("lotatt12",$('#lotatt12').val());
		param.put("descrc",$('#descrc').val());
		param.put("lotatt04",$('#lotatt04').val());
        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/statisticalAnalysisController.do?exportSalesSoListDataToExcel", param);
        downloadCheckTimer = window.setInterval(function () {
            window.clearInterval(downloadCheckTimer);
            $('#'+formId).remove();
            $('#ezuiBtn_export').linkbutton('enable');
            $.messager.progress('close');
            $.messager.show({
                msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
            });
        }, 1000);
    }else{
        $.messager.show({
            msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
        });
    }
};
/* 导出end */


</script>
</head>
<body>


	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table style="text-align: right">
						<tr >
<%--							<th>入库日期(开始)</th><td><input type='text' id='lotatt03StartDate' class='easyui-datebox' size='16' data-options=''/></td>--%>
<%--							<th>入库日期(结束)</th><td><input type='text' id='lotatt03EndDate' class='easyui-datebox' size='16' data-options=''/></td>--%>
	                        <th>客户名称</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
	                        <th>存货名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
							<th>规格</th><td><input type='text' id='descrc' class='easyui-textbox' size='16' data-options=''/></td>
	                        <th>批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
	                       <td >
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
<%--					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.update'/></a>--%>
<%--					<a onclick='goon();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.goon'/></a>--%>
<%--					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>--%>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>

</body>
</html>