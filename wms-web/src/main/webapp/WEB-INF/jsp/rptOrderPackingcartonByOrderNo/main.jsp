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
		url : '<c:url value="/statisticalAnalysisController.do?showDatagridRptOrderPackingcartonByOrderNo"/>',
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
			{field: 'orderno', title: '出库单号', width: 124},
			{field: 'traceid',		title: '箱号',	width: 150 },
			{field: 'traceid1',		title: '冷链虚拟箱号',	width: 100 },
			{field: 'packingflag', title: '是否装箱完成', width: 90,formatter:yesOrNoFormatter},
			{field: 'lotatt10', title: '质量状态', width: 80, formatter: ZL_TYPstatusFormatter},
			{field: 'customerid', title: '货主代码', width: 134},
			{field: 'shippershortname', title: '货主简称', width: 134},
			{field: 'lotatt08Name', title: '供应商', width: 134},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'lotatt12', title: '产品名称', width: 200},
			{field: 'lotatt06', title: '注册证号', width: 200},
			{field: 'skudescrc', title: '规格/型号', width: 150},
			{field: 'lotatt04', title: '生产批号', width: 110},
			{field: 'lotatt05', title: '序列号', width: 110},
			{field: 'lotatt07',title: '灭菌批号',	width: 110 },
			{field: 'lotatt03', title: '入库日期', width: 100},
			{field: 'lotatt01', title: '生产日期', width: 100},
			{field: 'lotatt02', title: '有效期/失效期', width: 100},
			{field: 'lotatt11', title: '存储条件', width: 100},
			{field: 'lotatt15', title: '生产企业', width: 200},
			{field: 'reservedfield06', title: '生产许可证号/备案号', width: 150},
			{field: 'qty', title: '装箱件数', width: 100},
			{field: 'qtyEach', title: '装箱数量', width: 100},
			{field: 'uom', title: '单位', width: 70},
			{field: 'description', title: '复核说明', width: 160},
			{field: 'conclusion', title: '复核结论', width: 100},
			{field: 'editwho', title: '复核人', width: 100},
			{field: 'edittime', title: '复核时间', width: 134},
			{field: 'addtime', title: '创建时间', width: 134},
			{field: 'addwho', title: '创建人', width: 100},
			{field: 'lotatt14', title: '入库单号', width: 150},
			{field: 'lotatt09', title: '产品属性', width: 100,formatter:YP_TYPstatusFormatter},
			{field: 'lotatt13', title: '双证', width: 100,formatter:Asn_DoublecstatusFormatter},
			// {field: 'qty1', title: '换算率', width: 100}
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




    //货主代码下拉框
    $('#customerid').combobox({
        // panelHeight: 'auto',
        url:sy.bp()+'/gspCustomerController.do?getCustomerNoCombobox',
        valueField:'id',
        textField:'value'
    });
});


/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		// lotatt03StartDate:$('#lotatt03StartDate').datebox('getValue'),
		// lotatt03EndDate:$('#lotatt03EndDate').datebox('getValue'),
		orderno:$('#orderno').val(),
		sku:$('#sku').val(),
		packingflag:$('#packingflag').combobox('getValue'),
		lotatt04:$('#lotatt04').val(),
		traceid:$('#traceid').val(),
		customerid : $('#customerid').combobox('getValue'),
		lotatt12:$('#lotatt12').val(),
		skudescrc:$('#descrc').val(),
		lotatt05 : $('#lotatt05').val(),
		traceid1 : $('#traceid1').val()
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
		param.put("orderno",$('#orderno').val());
		param.put("sku",$('#sku').val());
		param.put("packingflag",$('#packingflag').combobox('getValue'));
		param.put("lotatt04",$('#lotatt04').val());
		param.put("traceid",$('#traceid').val());
		param.put("customerid",$('#customerid').combobox('getValue'));
		param.put("lotatt12",$('#lotatt12').val());
		param.put("skudescrc",$('#descrc').val());
		param.put("lotatt05",$('#lotatt05').val());
		param.put("traceid1",$('#traceid1').val());
        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/statisticalAnalysisController.do?exportOrderPackingcartonByOrderNoDataToExcel", param);
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
						<tr>
							<th>出库单号</th><td><input type='text' id='orderno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否装箱完成</th><td><input type='text' id='packingflag' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getYesOrNoCombobox"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
							<th>批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>箱号</th><td><input type='text' id='traceid' class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr >
<%--							<th>入库日期(开始)</th><td><input type='text' id='lotatt03StartDate' class='easyui-datebox' size='16' data-options=''/></td>--%>
<%--							<th>入库日期(结束)</th><td><input type='text' id='lotatt03EndDate' class='easyui-datebox' size='16' data-options=''/></td>--%>
	                        <th>货主代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
	                        <th>产品名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
							<th>规格</th><td><input type='text' id='descrc' class='easyui-textbox' size='16' data-options=''/></td>
	                        <th>序列号</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
	                        <th>冷链虚拟箱号</th><td><input type='text' id='traceid1' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<td colspan="2">
								<a onclick='doSearch();'  id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
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
