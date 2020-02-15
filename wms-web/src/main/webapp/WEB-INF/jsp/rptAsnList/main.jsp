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
		url : '<c:url value="/statisticalAnalysisController.do?showDatagridRptAsnList"/>',
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
			{field: 'loatt03',		        title: '入库日期',	    width: 131 },
			{field: 'customerIdRef',		title: '货主',	    width: 131 },
			{field: 'asnno',		        title: '单据号',	    width: 131 },
			{field: 'asntypeName',		    title: '单据类型',	    width: 71 },
			{field: 'asnreference1',		title: '客户单号1',	    width: 131 },
			{field: 'asnreference2',		title: '客户单号2',		width: 101 },
			{field: 'pname',		title: '产品线',		width: 101 },
			{field: 'expectedqty',			title: '入库件数',		width: 101 },
			{field: 'expectedqtyNum',		title: '入库数量',		width: 101 },
			{field: 'notes',		        title: '备注',	        width: 250 },
			{field: 'editwho',		        title: '编辑人',	        width: 71 }
/*			{field: 'documentNo',		title: '单据号',	width: 150 },
			{field: 'asndate',		    title: '日期',	width: 100 },
			{field: 'documentType',		title: '单据类型',	width: 100 },
			{field: 'warehouse',		title: '仓库',	width: 150 },
			{field: 'customerid',		title: '供货单位',	width: 150 },
			{field: 'sku',		        title: '存货编码',	width: 150 },
			{field: 'lotatt12',		    title: '存货名称',	width: 150 },
			{field: 'descrc',		    title: '规格型号',	width: 100 },
			{field: 'reservedfield02',	title: '产品描述',	width: 200 },
			{field: 'asnqty',	        title: '入库件数',	width: 100 },
			{field: 'asnqtyeach',		title: '入库数量',	width: 100 },
			{field: 'uom',              title: '主计量单位',	width: 100 },
			{field: 'lotatt04',		    title: '批号',	width: 130 },
			{field: 'lotatt05',		    title: '序列号',	width: 130 },
			{field: 'qty1',		        title: '换算率 ',	width: 70},
			{field: 'purchaseOrderNumber',title: '采购订单号',	width: 130 },
			{field: 'planPrice',		title: '计划价',	width: 200},
			{field: 'notes',	        title: '备注',	width: 100},*/


		]],
		onDblClickCell: function(index,field,value){
			ajaxBtn($('#menuId').val(), '<c:url value="/statisticalAnalysisController.do?getBtn"/>', ezuiMenu);
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
	$("#warehouseId").combobox({
		panelHeight: 'auto',
		editable: false,
		url:'<c:url value="/docPoHeaderController.do?getWarehouseCombobox"/>',
		valueField: 'id',
		textField: 'value'
	});
});


/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		customerid : $('#customerid').val(),
		asnno : $('#asnno').val(),
		asntype : $('#asntype').combobox('getValue'),
		asnreference1 : $('#asnreference1').val(),
		asnreference2 : $('#asnreference2').val(),
		editwho : $('#editwho').val(),
		notes:$('#notes').val(),
		warehouseid:$('#warehouseId').combobox('getValue'),
		skuGroup1:$('#skugroup1').combobox('getValue'),
		loatt03star : $('#loatt03star').datebox('getValue'),
		loatt03end : $('#loatt03end').datebox('getValue'),
		userdefine2:$('#pano').val()//上架单号

	});
};

/* 导出start */
var doExport = function(){
	var token = new Date().getTime();
	var param = new HashMap();
	param.put("token", token);
	param.put("customerid",$('#customerid').val());
	param.put("asnno",$('#asnno').val());
	param.put("userdefine2",$('#pano').val());
	param.put("asntype",$('#asntype').combobox('getValue'));
	param.put("asnreference1",$('#asnreference1').val());
	param.put("asnreference2",$('#asnreference2').val());
	param.put("warehouseId",$('#warehouseId').combobox('getValue'));
	param.put("notes",$('#notes').val());
	param.put("editwho",$('#editwho').val());
	param.put("loatt03star",$('#loatt03star').datebox('getValue'));
	param.put("loatt03end",$('#loatt03end').datebox('getValue'));
	param.put("skugroup1",$('#skugroup1').combobox('getValue'));
	//--导出Excel
	ajaxDownloadFile(sy.bp()+ "/statisticalAnalysisController.do?exportAsnListDataToExcel",param);
	$.messager.show({
		msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
	});
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
					<table>
						<tr>
							<th>货主代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>入库单编号</th><td><input type='text' id='asnno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>上架单号</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>
							<th>入库类型</th><td>
							<input type='text' id='asntype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docAsnHeaderController.do?getAsnTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>


						</tr>
						<tr>
							<th>客户单号1</th><td><input type='text' id='asnreference1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>客户单号2</th><td><input type='text' id='asnreference2' class='easyui-textbox' size='16' data-options=''/></td>
							<th style="text-align: right">仓库</th><td><input type="text" class='easyui-combobox' size='16' id="warehouseId"/></td>
							<th>备注</th><td><input type='text' id='notes' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>入库日期</th><td><input type='text' id='loatt03star' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='loatt03end' class='easyui-datebox' size='16' data-options=''/></td>
							<th>编辑人</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品线</th>
							<td>
								<input type='text' id='skugroup1' name="skugroup1" class='easyui-combobox' size='16' data-options="panelHeight: '300px',
																															editable: false,
																															url:'<c:url value="/productLineController.do?getCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
							</td>

							<th colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导出</a>
							</th>
						</tr>
					</table>
<%--					<table style="text-align: right">--%>
<%--						<tr >--%>
<%--							<th>单据号</th><td><input type='text' id='documentNo' class='easyui-textbox' size='16' data-options=''/></td>--%>
<%--							<th>供货单位</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>--%>
<%--							<th>存货编码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>--%>
<%--							<th>存货名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>--%>

<%--						</tr>--%>
<%--						<tr >--%>
<%--							<th>入库日期(开始)</th><td><input type='text' id='lotatt03StartDate' class='easyui-datebox' size='16' data-options=''/></td>--%>
<%--							<th>入库日期(结束)</th><td><input type='text' id='lotatt03EndDate' class='easyui-datebox' size='16' data-options=''/></td>--%>
<%--							<th>批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>--%>
<%--							<th>序列号</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>--%>
<%--							<td colspan="2">--%>
<%--								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>--%>
<%--								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>--%>
<%--								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>--%>

<%--							</td>--%>
<%--						</tr>--%>
<%--					</table>--%>
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
