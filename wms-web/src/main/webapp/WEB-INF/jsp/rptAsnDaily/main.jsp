<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;

var ezuiCustDataDialog;
var ezuiCustDataDialogId;

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/rptAsnDailyController.do?showDatagrid"/>',
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
		idField : 'asnno',
		queryParams:{
			inbounddate : $('#inbounddate').datetimebox('getValue'),
			inbounddatetext : $('#inbounddatetext').datetimebox('getValue'),
		},
		columns : [[
            {field: 'customerid',		title: '客户编码 ',	width: 60 },
            {field: 'asnno',		title: 'ASN编号 ',	width: 110 },
            {field: 'asntype',		title: 'ASN类型 ',	width: 80 },
            {field: 'asnlineno',		title: 'ASN行号 ',	width: 60 },
            {field: 'inbounddate',		title: '入库时间 ',	width: 130 },
			{field: 'asncreatetime',		title: '创建时间 ',	width: 130 },
			{field: 'sku',		title: '商品编码  ',	width: 80 },
			{field: 'skuDescrC',		title: '商品名称 ',	width: 180 },
			{field: 'skuDescrE',		title: '英文描述 ',	width: 180 },
			//{field: 'reservedfield01',		title: '单位  ',	width: 13 },
			{field: 'packid',		title: '包装  ',	width: 80 },
			{field: 'qty',		title: '数量 ',	width: 45 },
			{field: 'cubic',		title: '体积 ',	width: 45 },
			{field: 'grossweight',		title: '重量 ',	width: 45 },
			{field: 'lotatt01',		title: '生产日期 ',	width: 100 },
			{field: 'lotatt02',		title: '失效日期 ',	width: 100 },
			{field: 'lotatt03',		title: '入库日期',	width: 100 }
			//{field: 'lotatt04',		title: '批次属性04',	width: 19 },
			//{field: 'lotatt05',		title: '批次属性05',	width: 19 },
			//{field: 'lotatt06',		title: '批次属性06',	width: 19 }	
		]],
		onDblClickCell: function(index,field,value){
			//edit();
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
			ajaxBtn($('#menuId').val(), '<c:url value="/rptAsnDailyController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	
	$('#inbounddate').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });

	$('#inbounddatetext').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	
	//查询条件货主字段初始化
	$("#customerid").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	//货主查询弹框初始化
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});

/* 查询条件清空按钮 */
var ezuiToolbarClear = function(){
	$('#customerid').textbox('clear');
	$('#sku').textbox('clear');
	$('#skutext').textbox('clear');
	$("#inbounddate").datetimebox('clear'
		// value:inbounddate(new Date())
	);
	$("#inbounddatetext").datetimebox('clear'
		// value:inbounddatetext(new Date())
	);
};

/* 获取起始日期 */
var inbounddate = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 00:00:00';
};
/* 获取结束日期 */
var inbounddatetext = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 23:59:59';
};
/*增删改已屏蔽*/
var add = function(){
	processType = 'add';
	$('#rptAsnDailyId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			asncreatetime : row.asncreatetime,
			asnlineno : row.asnlineno,
			asnno : row.asnno,
			asnreference1inreport : row.asnreference1inreport,
			asnreference2inreport : row.asnreference2inreport,
			asnreference3inreport : row.asnreference3inreport,
			asnreference4inreport : row.asnreference4inreport,
			asnreference5inreport : row.asnreference5inreport,
			asntype : row.asntype,
			carrierid : row.carrierid,
			carriername : row.carriername,
			commodity : row.commodity,
			cubic : row.cubic,
			customerid : row.customerid,
			grossweight : row.grossweight,
			inbounddate : row.inbounddate,
			inbounddatetext : row.inbounddatetext,
			lotatt01 : row.lotatt01,
			lotatt02 : row.lotatt02,
			lotatt03 : row.lotatt03,
			lotatt04 : row.lotatt04,
			lotatt05 : row.lotatt05,
			lotatt06 : row.lotatt06,
			notes : row.notes,
			packid : row.packid,
			packuom1 : row.packuom1,
			qty : row.qty,
			sku : row.sku,
			skuDescrC : row.skuDescrC,
			skuDescrE : row.skuDescrE,
			skugroup : row.skugroup,
			skutext : row.skutext,
			supplierid : row.supplierid,
			suppliername : row.suppliername,
			udf1 : row.udf1,
			udf2 : row.udf2,
			udf3 : row.udf3,
			udf4 : row.udf4,
			udf5 : row.udf5,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'rptAsnDailyController.do?delete',
					data : {id : row.id},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '<spring:message code="common.message.data.delete.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							ezuiDatagrid.datagrid('reload');
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/rptAsnDailyController.do?edit"/>';
	}else{
		url = '<c:url value="/rptAsnDailyController.do?add"/>';
	}
	ezuiForm.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiForm.form('validate')){
				$.messager.progress({
					text : '<spring:message code="common.message.data.processing"/>', interval : 100
				});
				return true;
			}else{
				return false;
			}
		},
		success : function(data) {
			var msg='';
			try {
				var result = $.parseJSON(data);
				if(result.success){
					msg = result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialog.dialog('close');
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			}
		}
	});
};
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		asncreatetime : $('#asncreatetime').val(),
		asnno : $('#asnno').val(),
		asntype : $('#asntype').val(),
		customerid : $('#customerid').val(),
		inbounddate : $('#inbounddate').datetimebox("getValue"),
		inbounddatetext : $('#inbounddatetext').datetimebox("getValue"),
		sku : $('#sku').val(),
		skutext : $('#skutext').val()
	});
};

//货主查询弹框弹出start=========================
var ezuiCustDataClick = function(){
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
	url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=Y&customerType=OW"/>',
	method:'POST',
	toolbar : '#ezuiCustToolbar',
	title: '客户档案',
	pageSize : 50,
	pageList : [50, 100, 200],
	fit: true,
	border: false,
	fitColumns : true,
	nowrap: false,
	striped: true,
	collapsible:false,
	pagination:true,
	rownumbers:true,
	singleSelect:true,
	idField : 'id',
	columns : [[
				{field: 'customerid',	title: '客户代码',	width: 15},
				{field: 'descrC',		title: '中文名称',	width: 50},
				{field: 'descrE',		title: '英文名称',	width: 50},
				{field: 'customerTypeName',	title: '类型',	width: 15},
				{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
					return rowData.activeFlag == 'Y' ? '是' : '否';
	            }}
			]],
	onDblClickCell: function(index,field,value){
		selectCust();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
// 			ajaxBtn($('#menuId').val(), '<c:url value="/rptAsnDailyController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','Y').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		/* customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue') */
	});
};
//货主查询弹框选择按钮
var selectCust = function(){
	processType = 'selectCust';
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================
	
/* 导出start */
var doExport = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		param.put("customerid", $('#customerid').val());
		param.put("sku", $('#sku').val());
		param.put("skutext", $('#skutext').val());
		param.put("inbounddate", $('#inbounddate').datetimebox('getValue'));
		param.put("inbounddatetext", $('#inbounddatetext').datetimebox('getValue'));
		//--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/rptAsnDailyController.do?exportAsndailyDataToExcel", param);
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
	<%--主页上部查询条件--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
						    <th>客户编码：</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>   
						    <th>商品编码：</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<%--<th>&nbsp;&nbsp;&nbsp;至：</th><td><input type='text' id='skutext' class='easyui-textbox' size='16' data-options=''/></td>--%>
						</tr>    
						    <th>收货时间：</th><td><input type='text' id='inbounddate' class='easyui-datetimebox' size='16' data-options='editable:false,
																																		required:true,
																																		showSeconds:true,
																																		value:inbounddate(new Date())'/></td>
							<th>&nbsp;&nbsp;&nbsp;至：</th><td><input type='text' id='inbounddatetext' class='easyui-datetimebox' size='16' data-options='editable:false,
																																		required:true,
																																		showSeconds:true,
																																		value:inbounddatetext(new Date())'/></td>
							
							<td>
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
							</td>
						</tr>
					</table>
				</fieldset>
				<!--  
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
				-->
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<%--双击弹窗dialog 已经屏蔽 不编辑弹窗--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='rptAsnDailyId' name='rptAsnDailyId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='asncreatetime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='asnlineno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='asnno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='asnreference1inreport' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='asnreference2inreport' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='asnreference3inreport' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='asnreference4inreport' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='asnreference5inreport' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='asntype' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='carrierid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='carriername' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='commodity' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='cubic' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='grossweight' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='inbounddate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入16</th>
					<td><input type='text' name='inbounddatetext' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入17</th>
					<td><input type='text' name='lotatt01' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入18</th>
					<td><input type='text' name='lotatt02' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入19</th>
					<td><input type='text' name='lotatt03' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入20</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入21</th>
					<td><input type='text' name='lotatt05' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入22</th>
					<td><input type='text' name='lotatt06' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入23</th>
					<td><input type='text' name='notes' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入24</th>
					<td><input type='text' name='packid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入25</th>
					<td><input type='text' name='packuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入26</th>
					<td><input type='text' name='qty' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入27</th>
					<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入28</th>
					<td><input type='text' name='skuDescrC' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入29</th>
					<td><input type='text' name='skuDescrE' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入30</th>
					<td><input type='text' name='skugroup' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入31</th>
					<td><input type='text' name='skutext' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入32</th>
					<td><input type='text' name='supplierid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入33</th>
					<td><input type='text' name='suppliername' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入34</th>
					<td><input type='text' name='udf1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入35</th>
					<td><input type='text' name='udf2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入36</th>
					<td><input type='text' name='udf3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入37</th>
					<td><input type='text' name='udf4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入38</th>
					<td><input type='text' name='udf5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入39</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<!-- 
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
	 -->
	 
	  <!-- 客户编码选择弹框 -->
	<div id='ezuiCustDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
	<div class='easyui-layout' data-options='fit:true,border:false'>
	<div data-options="region:'center'">
		<div id='ezuiCustToolbar' class='datagrid-toolbar'   style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th><td>
								<input type='text' id='customerid' name="customerid" class='easyui-textbox'  size='12' data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th><td>
								<input type='text' id='customerType' name="customerType" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
								<th>激活：</th><td>
								<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'}, 
																																{id: 'N', value: '否'}
																															]"/></td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
	<div id='ezuiCustDialogBtn'> </div>
	</div>
		<table id='ezuiCustDataDialogId' ></table> 
	</div>
	</div>
	</div>
	<div id='ezuiCustDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	
</body>
</html>
