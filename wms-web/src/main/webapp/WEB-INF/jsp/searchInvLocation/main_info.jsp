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
		url : '<c:url value="/drugInspectionController.do?showSearchInvLocationDatagridSum"/>',
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
			{field: 'enterpriseName',		title: '委托方企业名称',	width: 150 },
			{field: 'supplierName',		title: '供应商',	width: 150 },
			{field: 'lotatt14',		        title: '入库单号',	width: 140 },
			{field: 'lotatt03',		        title: '入库日期',	width: 100 },
			{field: 'inventoryage',		        title: '库龄(天)',	width: 60 },
			{field: 'sku',		            title: '产品代码',	width: 120 },
			{field: 'lotatt12',		        title: '产品名称',	width: 150 },
			{field: 'descrc',		        title: '规格/型号',	width: 100 },
			{field: 'lotatt15',		        title: '生产企业',	width: 150 },
			{field: 'reservedfield06',		title: '生产企业许可证号/备案凭证号',	width: 170 },
			{field: 'lotatt06',		        title: '产品注册证号/备案凭证号 ',	width: 150 },
			{field: 'lotatt04',		        title: '生产批号',	width: 100 },
			{field: 'lotatt05',		        title: '序列号',	width: 100 },
			{field: 'lotatt01',		        title: '生产日期',	width: 100 },
			{field: 'lotatt02',		        title: '有效期/失效期',	width: 100 },
			{field: 'qty',                  title: '库存件数 ',	width: 100 },
			{field: 'qtyeach',		        title: '库存数量 ',	width: 100 },
			{field: 'uom',                  title: '单位 ',	width: 70 },
			{field: 'locationid',		    title: '库存地点(货架号)',	width: 130 },
			{field: 'lotatt11',		        title: '储存条件',	width: 130 },
			{field: 'lotatt10',	            title: '质量状态',	width: 100,formatter:ZL_TYPstatusFormatter},
			{field: 'productLineName',     title: '产品线', width: 100},
			{field: 'notes',		        title: '备注 ',	width: 200},
			{field: 'qty1',		           title: '换算率 ',	width: 70},


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
			ajaxBtn($('#menuId').val(), '<c:url value="/drugInspectionController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
//委托方下拉框
	$('#toolbar #enterpriseName').combobox({
		// panelHeight: 'auto',
		url:sy.bp()+'/basCustomerController.do?getCustomerNameComboboxAA&type=client',
		valueField:'id',
		textField:'value'
	});
//供应商下拉框
	$('#toolbar #supplierName').combobox({
		// panelHeight: 'auto',
		url:sy.bp()+'/basCustomerController.do?getCustomerNameCombobox&type=supplier',
		valueField:'id',
		textField:'value'
	});


	//货主带出产品线
	$("#toolbar #enterpriseName").combobox({
		onChange:function(){
			var customerid = $("#toolbar #enterpriseName").combobox('getValue');
			if(customerid !=null && ($.trim(customerid).length>0)){
				$("#toolbar #productLineName").combobox({
					panelHeight: 'auto',
					url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid,
					valueField:'id',
					textField:'value',
					onLoadSuccess:function () {
					}
				});
			}else{
				$("#toolbar #productLineName").combobox({
					panelHeight: 'auto',
					url:"/productLineController.do?getCombobox",
					valueField:'id',
					textField:'value',
					onLoadSuccess:function () {
					}
				});
			}
		}
	});
});


/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		enterpriseName:$('#enterpriseName').combobox('getText'),
		sku:$('#sku').val(),
		lotatt12:$('#lotatt12').val(),
		lotatt01Start:$('#lotatt01Start').datebox('getValue'),
		lotatt01End:$('#lotatt01End').datebox('getValue'),
		lotatt02Start:$('#lotatt02Start').datebox('getValue'),
		lotatt02End:$('#lotatt02End').datebox('getValue'),
		lotatt15:$('#lotatt15').val(),
		lotatt04 : $('#lotatt04').val(),
		lotatt06 : $('#lotatt06').val(),
		supplierName:$('#supplierName').combobox('getValue'),
		lotatt05 : $('#lotatt05').val(),
		reservedfield09 : $('#reservedfield09').combobox('getValue'),
		lotatt14:$('#lotatt14').val(),
		descrc:$('#descrc').val(),
		productLineName:$('#productLineName').combobox('getValue'),
		locationid : $('#locationid').val(),
		lotatt10 : $('#lotatt10').combobox('getValue'),
		inventoryage : $('#inventoryage').numberbox('getValue')

	});
};

/* 导出start */
var doExport = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
		param.put("token", token);
		param.put("enterpriseName",$('#enterpriseName').combobox('getText'));
		param.put("sku",$('#sku').val());
		param.put("lotatt12",$('#lotatt12').val());
		param.put("lotatt01Start",$('#lotatt01Start').datebox('getValue'));
		param.put("lotatt01End",$('#lotatt01End').datebox('getValue'));
		param.put("lotatt02Start",$('#lotatt02Start').datebox('getValue'));
		param.put("lotatt02End",$('#lotatt02End').datebox('getValue'));
		param.put("lotatt15",$('#lotatt15').val());
		param.put("lotatt04",$('#lotatt04').val());
		param.put("lotatt06",$('#lotatt06').val());
		param.put("supplierName",$('#supplierName').combobox('getValue'));
		param.put("lotatt05",$('#lotatt05').val());
		param.put("reservedfield09",$('#reservedfield09').combobox('getValue'));
		param.put("lotatt14",$('#lotatt14').val());
		param.put("descrc",$('#descrc').val());
		param.put("productLineName",$('#productLineName').combobox('getValue'));
		param.put("locationid",$('#locationid').val());
		param.put("lotatt10",$('#lotatt10').combobox('getValue'));
		param.put("inventoryage",$('#inventoryage').numberbox('getValue'));

		//--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/drugInspectionController.do?exportSearchInvLocationDataToExcelSum", param);
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
						<th>委托方企业名称</th><td><input type='text' id='enterpriseName' class='easyui-combobox' size='16' data-options=''/></td>
						<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
						<th>产品名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产日期</th>
							<td><input type='text' id='lotatt01Start' class='easyui-datebox' size='16' data-options=''/>
							</td>
							<th>至
							</th>
							<td><input type='text' id='lotatt01End' class='easyui-datebox' size='16' data-options=''/></td>
						</tr>
						<tr >
							<th>生产企业</th><td><input type='text' id='lotatt15' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品注册证号</th><td><input type='text' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>
							<th>效期</th>
							<td><input type='text' id='lotatt02Start' class='easyui-datebox' size='16' data-options=''/>
							</td>
							<th>至
							</th>
							<td><input type='text' id='lotatt02End' class='easyui-datebox' size='16' data-options=''/></td>
						</tr>
						<tr >
							<th>供应商</th><td><input type='text' id='supplierName' class='easyui-combobox' size='16' data-options=''/></td>
							<th>序列号</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>

							<th >是否医疗器械</th>
							<td>
								<select id="reservedfield09" class="easyui-combobox"  style="width:135px;" data-options="panelHeight:'auto',">
									<option value=""></option>
									<option value="1">医疗器械</option>
									<option value="0">非医疗器械</option>
								</select>
							</td>
							<th>入库单号</th><td><input type='text' id='lotatt14' class='easyui-textbox' size='16' data-options=''/></td>
							<th>规格</th><td><input type='text' id='descrc' class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr>
							<th>产品线</th>
							<td><input type='text' id='productLineName' class='easyui-combobox' size='16' data-options="editable:false,
																										url:'<c:url value="/productLineController.do?getCombobox"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
							<th>库存地点(货架号)</th><td><input type='text' id='locationid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>质量状态</th><td><input type='text' id='lotatt10' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?qcState"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
							<th>库龄</th><td><input type='text' id='inventoryage' class='easyui-numberbox' size='16' data-options=''/></td>

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
