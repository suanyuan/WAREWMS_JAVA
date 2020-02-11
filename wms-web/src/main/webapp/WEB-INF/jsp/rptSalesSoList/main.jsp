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
			{field: 'edittime', title: '出库日期', width: 145},
			{field: 'customerIdRef', title: '货主', width: 145},
			{field: 'orderTypeName', title: '订单类型', width: 80},
			{field: 'orderno', title: 'SO编号', width: 120},
			{field: 'soOrderNum',		title: '发货单号码',	width: 150 },
			{field: 'pname',		title: '产品线',		width: 101 },
			{field: 'qtyshipped',              title: '出库件数',	width: 100 },
			{field: 'qtyshippedEach',		    title: '出库数量',	width: 100 },
			{field: 'consigneeid',		title: '收货单位',	width: 150 },
			{field: 'sourceOrder',		title: '来源订单号',	width: 150 },
			{field: 'cAddress4', title: '快递单号', width: 120},
			{field: 'notes', title: '备注', width: 150},
			{field: 'editwho', title: '编辑人', width: 70}
			/*{field: 'warehouseid',		title: '仓库编码',	width: 100 },
			{field: 'soOrderNum',		title: '发货单号码',	width: 150 },

			{field: 'soOrderNo',		title: '出库单号',	width: 150 },
			{field: 'soDate',		    title: '出库日期',	width: 100 },
			{field: 'soType',		    title: '出库类别',	width: 100 },
			{field: 'warehouse',		title: '仓库',	width: 150 },
			{field: 'customerid',		title: '客户名称',	width: 200 },
			{field: 'sku',		        title: '存货编码',	width: 150 },
			{field: 'lotatt12',	        title: '存货名称',	width: 200 },
			{field: 'descrc',           title: '规格型号 ',	width: 150 },

			{field: 'uom',		        title: '主计量单位',	width: 100 },
			{field: 'lotatt04',         title: '批号',	width: 150 },
			{field: 'lotatt05',         title: '序列号',	width: 150 },
			{field: 'lotatt02',	        title: '失效日期',	width: 100},
			{field: 'addwho',		    title: '制单人',	width: 100},
			{field: 'reviewWho',		title: '审核人',	width: 100},
			{field: 'trackingNumber',   title: '快递单号码',	width: 200},*/
			// {field: 'qty1',		        title: '换算率 ',	width: 70},


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
			ajaxBtn($('#menuId').val(), '<c:url value="/statisticalAnalysisController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});

});


/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		orderno: $('#orderno').val(),
		customerid: $('#customerid').val(),
		soreference1: $('#soreference1').val(),
		soreference2: $('#soreference2').val(),
		consigneeid: $('#consigneeid').val(),
		psName: $("#toolbar #productLineOrder").combobox('getText'),
		edisendflag: $("#toolbar #edisendflag").combobox('getValue'), //回传标识
		orderStatusName: $('#sostatus').combobox('getValue'),
		sostatusTo: $('#sostatusTo').combobox('getValue'),
		ordertime: $('#ordertime').datetimebox('getValue'),
		ordertimeTo: $('#ordertimeTo').datetimebox('getValue'),
		edittime: $('#edittime').datetimebox('getValue'),
		edittimeTo: $('#edittimeTo').datetimebox('getValue'),
		orderTypeName: $('#ordertype').combobox('getValue'),
		releasestatus: $('#releasestatus').combobox('getValue'),
		cAddress4: $('#cAddress4Q').val(),
	});
};

/* 导出start */
var doExport = function(){
	var token = new Date().getTime();
	var param = new HashMap();
	param.put("orderno",$('#orderno').val());
	param.put("customerid",$('#customerid').val());
	param.put("soreference1",$('#soreference1').val());
	param.put("soreference2",$('#soreference2').val());
	param.put("consigneeid",$('#consigneeid').val());
	param.put("releasestatus",$('#releasestatus').combobox('getValue'));
	param.put("sostatusTo",$('#sostatusTo').combobox('getValue'));
	param.put("ordertime",$('#ordertime').datebox('getValue'));//时间查询
	param.put("ordertimeTo",$('#ordertimeTo').datebox('getValue'));//入库单号时间查询
	param.put("edittime",$('#edittime').datebox('getValue'));//入库单号时间查询
	param.put("edittimeTo",$('#edittimeTo').combobox('getValue'));//质量状态
	param.put("token", token);
	ajaxDownloadFile(sy.bp()+ "/statisticalAnalysisController.do?exportSalesSoListDataToExcel",param);
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
							<th>SO编号</th>
							<td><input type='text' id='orderno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>货主</th>
							<td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>客户单号</th>
							<td><input type='text' id='soreference1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>定向入库单号</th>
							<td><input type='text' id='soreference2' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>公司抬头</th>
							<td><input type='text' id='consigneeid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>快递单号</th>
							<td><input type='text' id='cAddress4Q' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品线</th>
							<td>
								<input id="productLineOrder" name="productLineOrder" size='16' type="text"
									   class='easyui-combobox' data-options="
																										url:'<c:url value="/productLineController.do?getCombobox"/>',
																										valueField: 'id',
																										textField: 'value'"/>
							</td>
							<th>回传标识</th>
							<td>
								<input type='text' id='edisendflag' name="edisendflag" class='easyui-combobox' size='16'
									   data-options="panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '已回传'},
																																{id: '0', value: '未回传'}
																															]"/>
							</td>

						</tr>
						<tr>
							<th>订单状态</th>
							<td><input type='text' id='sostatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
							</td>

							<th>至</th>
							<td><input type='text' id='sostatusTo' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
							</td>
							<th>订单类型</th>
							<td><input type='text' id='ordertype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																editable: false,
																																url:'<c:url value="/docOrderHeaderController.do?getOrderTypeCombobox"/>',
																																valueField: 'id',
																																textField: 'value'
																																"/>
							</td>
							<th>释放状态</th>
							<td><input type='text' id='releasestatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docOrderHeaderController.do?getReleasestatusCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/>
							</td>
						</tr>
						<tr>
							<th>订单创建时间</th>
							<td><input type='text' id='ordertime' class='easyui-datetimebox' size='16' data-options=""/>
							</td>
							<th>至</th>
							<td><input type='text' id='ordertimeTo' class='easyui-datetimebox' size='16' data-options=""/>
							</td>
							<th>订单发运时间</th>
							<td><input type='text' id='edittime' class='easyui-datetimebox' size='16' data-options=""/></td>
							<th>至</th>
							<td><input type='text' id='edittimeTo' class='easyui-datetimebox' size='16' data-options=""/>
							</td>

						</tr>
						<tr>
							<td colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
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
