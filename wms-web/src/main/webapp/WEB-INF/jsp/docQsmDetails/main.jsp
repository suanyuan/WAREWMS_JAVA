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
var ezuiDatagrid;                 //主页datagird


var generatemanagementDialog;        //生成质量状态管理dialog
var generatemanagementDialogId;      //生成质量状态管理datagird


var qualityStatusForm;       //库存信息选择dialog form
var qualityStatusDialog;     //库存信息选择dialog

var qualityStatusWorkForm;       //生成质量状态选择dialog form
var qualityStatusWorkDialog;     //生成质量状态选择dialog
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docQsmDetailsController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '质量状态管理',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		columns : [[
			{field: 'qcudocno',		title: '管理任务号',	width: 150 },
			{field: 'qcustatus',		title: '管理状态',	width: 150,formatter:qcustatusFormatter },
			{field: 'lotatt14',		title: '入库单号',	width: 150 },
			{field: 'customerid',		title: '货主',	width: 150 },
			{field: 'lotatt03',		title: '入库日期',	width: 150 },
			{field: 'lotatt08',		title: '供应商',	width: 150 },
			{field: 'businesstype',		title: '业务类型',	width: 150 },
			{field: 'sku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12',		title: '产品名称',	width: 150 },
			{field: 'lotatt06',		title: '注册证号/备案凭证号',	width: 150 },
			{field: 'descrc',		title: '规格/型号',	width: 150 },
			{field: 'lotatt04',		title: '生产批号',	width: 150 },
			{field: 'lotatt05',		title: '序列号',	width: 150 },
			{field: 'lotatt07',		title: '灭菌批号',	width: 150 },
			{field: 'lotatt01',		title: '生产日期',	width: 150 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 150 },
			{field: 'locqty',		title: '原库存件数',	width: 70,hidden:true},
			{field: 'qty',		title: '件数',	width: 70 },
			// {field: 'qtyeach',		title: '库存数量',	width: 70},
			{field: 'lotatt15',		title: '生产企业',	width: 100 },
			{field: 'reservedfield06', title: '生产许可证号/备案号', width: 150},
			{field: 'lotatt10',		title: '质量状态',	width: 100,formatter:ZL_TYPstatusFormatter },
			{field: 'locationid',		title: '库位',	width: 150 },
			{field: 'finddate',		title: '发现日期',	width: 150 },
			{field: 'failedDescription',		title: '不合格说明',	width: 200 },
			{field: 'customeridTreatment',		title: '货主处置措施',	width: 150 },
			{field: 'treatmentDate',		title: '处置日期',	width: 150 },
			{field: 'remarks',		title: '备注',	width: 150 },
			{field: 'recordingDate',		title: '记录日期',	width: 150 },
			{field: 'recordingPeople',		title: '记录人',	width: 100 },
			{field: 'lotnum',		title: '批次',	width: 100 }
		]],
		onDblClickCell: function(index,field,value){
			qualityStatusWork();
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
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

//库存信息dialog
	generatemanagementDialog = $('#generatemanagementDialog').dialog({
		modal: true,
		height:600,
		width:900,
		title: '库存信息',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//库存信息选择dialog
	qualityStatusDialog = $('#qualityStatusDialog').dialog({
		modal : true,
		width:300,
		height:200,
		title : '生成',
		buttons : '#qualityStatusDialogBtn',
		onClose : function() {
			ezuiFormClear(qualityStatusForm);
		}
	}).dialog('close');
//生成质量状态选择dialog
	qualityStatusWorkDialog = $('#qualityStatusWorkDialog').dialog({
		modal:true,
		width:270,
		height:220,
		title : '提交',
		buttons : '#qualityStatusWorkDialogBtn',
		onClose : function() {
			ezuiFormClear(qualityStatusWorkForm);
		}
	}).dialog('close');
});
var add = function(){
	processType = 'add';
	$('#docQsmDetailsId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			qcudocno : row.qcudocno,
			qcustatus : row.qcustatus,
			lotatt14 : row.lotatt14,
			customerid : row.customerid,
			lotatt03 : row.lotatt03,
			lotatt08 : row.lotatt08,
			businesstype : row.businesstype,
			sku : row.sku,
			lotatt12 : row.lotatt12,
			lotatt06 : row.lotatt06,
			descrc : row.descrc,
			lotatt04 : row.lotatt04,
			lotatt05 : row.lotatt05,
			lotatt07 : row.lotatt07,
			lotatt01 : row.lotatt01,
			lotatt02 : row.lotatt02,
			qty : row.qty,
			qtyeach : row.qtyeach,
			lotatt15 : row.lotatt15,
			lotatt10 : row.lotatt10,
			locationid : row.locationid,
			finddate : row.finddate,
			failedDescription : row.failedDescription,
			customeridTreatment : row.customeridTreatment,
			treatmentDate : row.treatmentDate,
			remarks : row.remarks,
			recordingDate : row.recordingDate,
			recordingPeople : row.recordingPeople
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
					url : 'docQsmDetailsController.do?delete',
					data : {id : row.qcudocno},
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
		url = '<c:url value="/docQsmDetailsController.do?edit"/>';
	}else{
		url = '<c:url value="/docQsmDetailsController.do?add"/>';
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
//主页查询
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		qcudocno : $('#qcudocno').val(),
		qcustatus : $('#qcustatus').combobox('getValue'),

	});
};
//生成管理start=========================
var generatemanagement = function () {
	generatemanagementDialogId = $('#generatemanagementDialogId').datagrid({
		url: '<c:url value="/viewInvLotattController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#generatemanagementToolbar',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		queryParams:{
			lotatt10:'HG'
		},
		columns: [[
			{field: 'fmlocation', title: '库位', width: 100},
			{field: 'fmcustomerid',		title: '货主',	width: 100 },
			{field: 'fmsku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12', title: '产品名称', width: 200},
			{field: 'lotatt10', title: '质量状态', width: 100, formatter:ZL_TYPstatusFormatter},
			{field: 'fmqty',		title: '库存件数',	width: 71 },
			{field: 'fmqtyEach',		title: '库存数量',	width: 71 },
			{field: 'qtyallocated',		title: '分配件数',	width: 71 },
			{field: 'qtyallocatedEach',		title: '分配数量',	width: 71 },
			{field: 'qtyavailed',		title: '可用件数',	width: 71 },
			{field: 'qtyavailedEach',		title: '可用数量',	width: 71 },
			// {field: 'totalgrossweight',		title: '毛重',	width: 71 },
			// {field: 'totalcubic',		title: '体积',	width: 71 },
			//
			// {field: 'uom',		title: '单位',	width: 71 },
			{field: 'fmlotnum',		title: '批次',	width: 81 },

			{field: 'lotatt06', title: '注册证号', width: 150},//加个字段
			{field: 'skudescre', title: '规格型号', width: 103},
			{field: 'lotatt05', title: '序列号', width: 110},
			{field: 'lotatt04', title: '生产批号', width: 95},
			{field: 'lotatt07', title: '灭菌批号', width: 120},
			{field: 'lotatt03', title: '入库日期', width: 91},
			{field: 'lotatt01', title: '生产日期', width: 112},
			{field: 'lotatt02', title: '有效期/失效期', width: 113},
			{field: 'lotatt08', title: '供应商', width: 120},
			{field: 'lotatt09', title: '样品属性', width: 100,formatter:YP_TYPstatusFormatter},
			{field: 'lotatt11', title: '存储条件', width: 100},
			{field: 'onholdlocker', title: '冻结状态', width: 100, formatter:holdStatusFormatter},
			{field: 'warehouseid',		title: '仓库编码',	width: 71 }
		]],
		onDblClickCell: function (index, field, value) {
			generatemanagementT();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	generatemanagementDialog.dialog('open');
};

//生成质量状态管理查询
var generatemanagementDialogSearch = function () {
	generatemanagementDialogId.datagrid('load', {
		lotatt04: $("#generatemanagementDialog #lotatt04").textbox("getValue"),
		lotatt05: $("#generatemanagementDialog #lotatt05").textbox("getValue"),
		lotatt10:"HG",

	});
};
//生成质量状态管理清除
var generatemanagementToolbarClear = function () {
	$("#generatemanagementDialog #lotatt04").textbox('clear');
	$("#generatemanagementDialog #lotatt05").textbox('clear');
};
//生成管理end==========================

//双击或者选择弹出库存信息输入框
var generatemanagementT=function () {
	var row = generatemanagementDialogId.datagrid('getSelected');
	if(row){
		$("#qualityStatusForm #fmqty").textbox('setValue',row.fmqty);
		$("#qualityStatusForm #fmqtyF").textbox('setValue',"");
		$("#qualityStatusForm #remarks").textbox('setValue',"");
		qualityStatusDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}
//提交库存信息输入框
var commitQualityStatus=function () {
	var row = generatemanagementDialogId.datagrid('getSelected');
	if(row){
		var remarks=$("#qualityStatusForm #remarks").textbox('getValue');
		var fmqtyF=$("#qualityStatusForm #fmqtyF").textbox('getValue');
		if(fmqtyF>row.qtyavailed){
			$.messager.show({
				msg : '请输入小于可用件数的数字!', title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});
		var data=new Object();
		data.locationid=row.fmlocation;
		data.sku=row.fmsku;
		data.customerid=row.fmcustomerid;
		data.lotnum=row.fmlotnum;
		data.locationid=row.fmlocation;
		data.qty=fmqtyF;
		data.remarks=remarks;
		$.ajax({
			url : 'docQsmDetailsController.do?commitQualityStatus',
			data :data,
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
					$.messager.progress('close');
					qualityStatusDialog.dialog('close');
					ezuiDatagrid.datagrid('reload');
				}
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}


//双击或者选择弹出质量状态作业输入框
var qualityStatusWork=function () {
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		if(row.qcustatus!='00'){
			$.messager.show({
				msg : '只有管理状态为任务创建才可以操作!', title : '<spring:message code="common.message.prompt"/>'
			});
			return;
		}
		$("#qualityStatusWorkForm #locqty").textbox('setValue',row.locqty);
		$("#qualityStatusWorkForm #qty").textbox('setValue',row.qty);
		$("#qualityStatusWorkForm #failedDescription").textbox('setValue',"");
		qualityStatusWorkDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}
//提交质量状态作业输入框
var commitQualityStatusWork=function () {
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		var failedDescription=$("#qualityStatusWorkForm #failedDescription").textbox('getValue');
		var customeridTreatment=$("#qualityStatusWorkForm #customeridTreatment").textbox('getValue');
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});
		var data=new Object();
		data.qcudocno=row.qcudocno;
		data.locationid=row.location;
		data.sku=row.sku;
		data.customerid=row.customerid;
		data.lotnum=row.lotnum;
		data.locationid=row.locationid;
		data.qty=row.qty;
		data.locqty=row.locqty;
		data.failedDescription=failedDescription;
		data.customeridTreatment=customeridTreatment;
		$.ajax({
			url : 'docQsmDetailsController.do?commitQualityStatusWork',
			data :data,
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
					$.messager.progress('close');
					qualityStatusWorkDialog.dialog('close');
					ezuiDatagrid.datagrid('reload');
				}
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}

//关闭计划单
var closeP= function(){
	url = '<c:url value="/docQsmDetailsController.do?close"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
	if(row) {
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '确认关闭任务单!', function (confirm) {
			if (confirm) {
				var data = new Object();
				data.qcudocno = row.qcudocno;

				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				$.ajax({
					url: url,
					data: data,
					dataType: 'json',
					error: function (a, b, c) {
						//alert(a + b + c);
					},
					success: function (result) {
						try {
							if (result.success) {
								msg = result.msg;
							} else {
								msg = result.msg;

							}
						} catch (e) {

							msg: '数据错误!';

						} finally {
							$.messager.show({
								msg: msg, title: '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
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
//取消计划单
var cancel= function(){
	url = '<c:url value="/docQsmDetailsController.do?cancel"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
	if(row) {
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '确认取消任务单!', function (confirm) {
			if (confirm) {
				var data = new Object();
				data.qcudocno = row.qcudocno;

				$.messager.progress({
					text: '<spring:message code="common.message.data.processing"/>', interval: 100
				});
				$.ajax({
					url: url,
					data: data,
					dataType: 'json',
					error: function (a, b, c) {
						//alert(a + b + c);
					},
					success: function (result) {
						try {
							if (result.success) {
								msg = result.msg;
							} else {
								msg = result.msg;

							}
						} catch (e) {

							msg: '数据错误!';

						} finally {
							$.messager.show({
								msg: msg, title: '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
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

</script>
</head>
<body>
<%--主页datagrid查询--%>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>管理任务号</th><td><input type='text' id='qcudocno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>管理状态</th><td><input type='text' id='qcustatus' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '任务创建'},
																																       {label: '40',
																																         value: '任务完成'},
																																       {label: '90',
																																         value: '任务取消'},
																																        {label: '99',
																																         value: '任务关闭'}]"/></td>

							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='generatemanagement();' id='ezuiBtn_generatemanagement' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成质量状态管理</a>
					<a onclick='qualityStatusWork();' id='ezuiBtn_qualityStatusWork' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>质量状态管理作业</a>
					<a onclick='closeP();' id='ezuiBtn_close' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>关闭计划单</a>
					<a onclick='cancel();' id='ezuiBtn_cancel' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>取消计划单</a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>

<!-- 库存信息 合格-->
	<div id='generatemanagementDialog' style="width:700px;height:480px;padding:10px 20px">
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='generatemanagementToolbar' class='datagrid-toolbar' style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>生产批号</th>
								<td>
									<input type='text' id='lotatt04' name="lotatt04" class='easyui-textbox' size='12'
										   data-options=''/></td>
								<td>
								<th>序列号</th>
								<td>
									<input type='text' id='lotatt05' name="lotatt05" class='easyui-textbox' size='12'
										   data-options=''/></td>
								<td>
									<a onclick='generatemanagementDialogSearch();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='generatemanagementToolbarClear();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-remove"'
									   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
									<a onclick='generatemanagementT();' id='ezuiBtn_generatemanagementT' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成质量状态管理</a>

								</td>
							</tr>
						</table>
					</fieldset>
					<div id='generatemanagementDialogBtn'></div>
				</div>
				<table id='generatemanagementDialogId'></table>
			</div>
		</div>
	</div>
	<div id='generatemanagementDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#generatemanagementDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.close'/></a>
	</div>
<%--库存信息选择点击弹窗--%>
<div id='qualityStatusDialog' style='padding: 10px;'>
	<form id='qualityStatusForm' method='post'>
		<table>
			<input type='hidden' id='qty1' name="qty1" />
			<tr>
				<th>合格件数</th><td><input type='text' id='fmqty' name="fmqty" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

			</tr>
			<tr>
				<th>不合格件数</th><td><input type='text' id='fmqtyF' name="fmqtyF" class='easyui-textbox' size='20' data-options="required:true"/></td>

			</tr>
			<tr>
			  <th>备注</th><td><input type='text' id='remarks' name="remarks" class='easyui-textbox' size='20' data-options=""/></td>

			</tr>
		</table>
	</form>
</div>
<div id='qualityStatusDialogBtn'>
	<a onclick='commitQualityStatus();' class='easyui-linkbutton' href='javascript:void(0);'>生成</a>
</div>
<%--生成质量状态选择点击弹窗--%>
<div id='qualityStatusWorkDialog' style='padding: 10px;'>
	<form id='qualityStatusWorkForm' method='post'>
		<table>
			<tr>
				<th>合格件数</th><td><input type='text' id='locqty' name="locqty" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

			</tr>
			<tr>
				<th>不合格件数</th><td><input type='text' id='qty' name="qty" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

			</tr>
			<tr>
			  <th>不合格说明</th><td><input type='text' id='failedDescription' name="failedDescription" class='easyui-textbox' size='20' data-options=""/></td>

			</tr>
			<tr>
			  <th>货主处置措施</th><td><input type='text' id='customeridTreatment' name="customeridTreatment" class='easyui-textbox' size='20' data-options=""/></td>

			</tr>
		</table>
	</form>
</div>
<div id='qualityStatusWorkDialogBtn'>
	<a onclick='commitQualityStatusWork();' class='easyui-linkbutton' href='javascript:void(0);'>生成</a>
</div>




<%--dialog--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='docQsmDetailsId' name='docQsmDetailsId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='qcudocno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='qcustatus' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='lotatt14' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='lotatt03' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='lotatt08' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='businesstype' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='lotatt12' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='lotatt06' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='descrc' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='lotatt05' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='lotatt07' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='lotatt01' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='lotatt02' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入16</th>
					<td><input type='text' name='qty' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入17</th>
					<td><input type='text' name='qtyeach' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入18</th>
					<td><input type='text' name='lotatt15' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入19</th>
					<td><input type='text' name='lotatt10' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入20</th>
					<td><input type='text' name='locationid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入21</th>
					<td><input type='text' name='finddate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入22</th>
					<td><input type='text' name='failedDescription' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入23</th>
					<td><input type='text' name='customeridTreatment' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入24</th>
					<td><input type='text' name='treatmentDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入25</th>
					<td><input type='text' name='remarks' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入26</th>
					<td><input type='text' name='recordingDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入27</th>
					<td><input type='text' name='recordingPeople' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>

<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
