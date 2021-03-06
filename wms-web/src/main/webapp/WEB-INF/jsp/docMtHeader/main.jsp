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
var ezuiMenu;        //右键菜单
var ezuiForm;        //二级dialog form
var ezuiDialog;      //二级dialog
var ezuiDetailsDatagrid;//二级datagrid
var ezuiDatagrid;    //主页datagird
var closegenerationPlanForm;       //关闭计划dialog form
var closegenerationPlanDialog;     //关闭计划dialog

var ezuiCustDataDialog;        //货主编码
var ezuiCustDataDialogId;      //货主编码
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
//主页datagird
	closegenerationPlanForm = $('#closegenerationPlanForm').form();   //关闭计划dialog form

	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docMtHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '养护计划',
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
			{field: 'mtno',		title: '养护单号',	width: 150 },
			{field: 'mtstatus',		title: '养护状态',	width: 150,formatter:MT_STSstatusFormatter},
			{field: 'mttype',		title: '养护类型',	width: 150,formatter:MT_TYPstatusFormatter },
			{field: 'fromdate',		title: '开始时间',	width: 150 },
			{field: 'todate',		title: '结束时间',	width: 150 },
			{field: 'storageFlag',		title: '贮存条件',	width: 100,formatter:AccordOrNoAccord},
			{field: 'flowFlag',		title: '作业流程',	width: 100,formatter:AccordOrNoAccord },
			{field: 'signFlag',		title: '标志清晰',	width: 100,formatter:AccordOrNoAccord },
			{field: 'fenceFlag',		title: '防护措施',	width: 100,formatter:AccordOrNoAccord },
			{field: 'sanitationFlag',		title: '卫生环境',	width: 100,formatter:AccordOrNoAccord },
			{field: 'remark',		title: '备注',	width: 150 },
			{field: 'addtime',		title: '创建时间',	width: 150 },
			{field: 'addwho',		title: '创建人',	width: 150 },
			{field: 'edittime',		title: '编辑时间',	width: 150 },
			{field: 'editwho',		title: '编辑人',	width: 150 },
			{field: 'warehouseid',		title: '仓库编码',	width: 150 }
		]],
		onDblClickCell: function(index,field,value){

		},
		// onRowContextMenu : function(event, rowIndex, rowData) {
		// 	event.preventDefault();
		// 	$(this).datagrid('unselectAll');
		// 	$(this).datagrid('selectRow', rowIndex);
		// 	ezuiMenu.menu('show', {
		// 		left : event.pageX,
		// 		top : event.pageY
		// 	});
		// },
		 onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/docMtHeaderController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
//一级dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		top:0,
		left:200,
		title : '生成计划',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiToolbar);
		}
	}).dialog('close');
//二级datagird初始化
	ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
		url: '<c:url value="/docMtHeaderController.do?getGenerationInfo"/>',
		method: 'POST',
		toolbar: '#ezuiToolbar',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination:true,
		rownumbers: true,
		singleSelect: true,
		columns: [[
			{field: 'locationid', title: '库位', width: 100},
			{field: 'customerName', title: '货主', width: 150},
			{field: 'sku', title: '产品代码', width: 100},
			{field: 'lotatt10', title: '质量状态', width: 100, formatter: ZL_TYPstatusFormatter},
			{field: 'qty', title: '养护件数', width: 100},
			{field: 'lotatt03', title: '入库时间', width: 100},
			{field: 'lotatt03test', title: '预期养护时间', width: 100},
			{field: 'reservedfield10', title: '养护周期', width: 100},
			{field: 'fromDate', title: '开始时间', width: 100,hidden:true},
			//	用于回传后台时间
			{field: 'toDate', title: '结束时间', width: 100,hidden:true},
			{field: 'lotnum', title: '批次', width: 100},
			// {field: 'qty1', title: '转化率', width: 100},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		},onClose:function(){
		}
	});
//关闭计划dialog
	closegenerationPlanDialog = $('#closegenerationPlanDialog').dialog({
		modal : true,
		width:270,
		height:250,
		title : '关闭计划',
		buttons : '#closegenerationPlanDialogBtn',
		onClose : function() {
			ezuiFormClear(closegenerationPlanForm);
		}
	}).dialog('close');
	//货主查询弹框初始化
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
	//查询条件货主字段初始化
	$("#ezuiDialog #customerid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
});
//增加
var add = function(){
	processType = 'add';
	$('#docMtHeaderId').val(0);
	ezuiDialog.dialog('open');
};
//编辑
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			mtno : row.mtno,
			mtstatus : row.mtstatus,
			mttype : row.mttype,
			fromdate : row.fromdate,
			todate : row.todate,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			remark : row.remark,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//删除
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docMtHeaderController.do?delete',
					data : {id : row.mtno},
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
//生成计划
var generationPlan = function(){
	doDialogSearch();//清空datagrid
	ezuiDialog.dialog('open');
};
//关闭计划单
var closegenerationPlan = function() {
	var row = ezuiDatagrid.datagrid('getSelected');
	if (row) {
		closegenerationPlanDialog.dialog('open');
	}else{
		$.messager.show({
			msg: '请选择一笔资料', title: '<spring:message code="common.message.prompt"/>'
		});
	}

}
//取消任务单
var cancel = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		var mtno=row.mtno;
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确定取消当前计划单!', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docMtHeaderController.do?cancel',
					data : {mtno : mtno},
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
//提交关闭计划单
var commitClosegenerationPlan = function(){
	url = '<c:url value="/docMtHeaderController.do?closegenerationPlan"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
	if(closegenerationPlanForm.form('validate')){
		var data=new Object();
		data.mtno=row.mtno;
		data.storageFlag=$("#closegenerationPlanForm #storageFlag").combobox('getValue');
		data.flowFlag=$("#closegenerationPlanForm #flowFlag").combobox('getValue');
		data.signFlag=$("#closegenerationPlanForm #signFlag").combobox('getValue');
		data.fenceFlag=$("#closegenerationPlanForm #fenceFlag").combobox('getValue');
		data.sanitationFlag=$("#closegenerationPlanForm #sanitationFlag").textbox('getValue');

			$.messager.progress({
				text: '<spring:message code="common.message.data.processing"/>', interval: 100
			});
			$.ajax({
				url: url,
				data:data,
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

					}finally {
						$.messager.show({
							msg:msg, title: '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
						closegenerationPlanDialog.dialog('close');
						ezuiDatagrid.datagrid('reload');
					}
				}
			});
	}else{
		$.messager.show({
			msg: '请填写完整!', title: '<spring:message code="common.message.prompt"/>'
		});
	}
};
//生成 时间段查询完养护计划之后
var generationPlanT = function(){
	url = '<c:url value="/docMtHeaderController.do?ToGenerationInfo"/>';
	var data=new Object();
	var fromDate=$("#ezuiDialog #fromdate").textbox('getValue');
	var toDate=$("#ezuiDialog #todate").textbox('getValue');
	var lotatt10=$("#ezuiDialog #lotatt10").combobox('getValue');
	var customerid=$("#ezuiDialog #customerid").textbox('getValue');
	var locationid=$("#ezuiDialog #locationid").textbox('getValue');
	data.fromdate=fromDate;
	data.todate=toDate;
	data.lotatt10=lotatt10;
	data.customerid=customerid;
	data.locationid=locationid;

	var msg='';
	$.messager.progress({
		text: '<spring:message code="common.message.data.processing"/>', interval: 100
	});
	$.ajax({
		url: url,
		data:data,
		dataType: 'json',
		error: function (a,b,c) {
			//alert(a+b+c);
		},
		success: function (result) {
			try{
				if(result.success){
					msg=result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialog.dialog('close');
					$.messager.show({
						msg:msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
				}else{
					msg=result.msg;
					ezuiDatagrid.datagrid('reload');
					ezuiDialog.dialog('close');
					$.messager.show({
						msg :msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
				}
			}catch (e) {
				$.messager.show({
					msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			}
		}
	});
};
//一级dialog提交
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docMtHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docMtHeaderController.do?add"/>';
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
//主页datagrid查询
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		mtno : $('#mtno').val(),
		mtstatus : $('#mtstatus').combobox('getValue'),
		mttype : $('#mttype').val(),
		fromdate : $('#fromdate').val(),
		todate : $('#todate').val(),
		userdefine1 : $('#userdefine1').val(),
		userdefine2 : $('#userdefine2').val(),
		userdefine3 : $('#userdefine3').val(),
		userdefine4 : $('#userdefine4').val(),
		userdefine5 : $('#userdefine5').val(),
		remark : $('#remark').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val(),
		warehouseid : $('#warehouseid').val()
	});
};
//二级datagrid查询
var doDialogSearch = function(){
	ezuiDetailsDatagrid.datagrid('load', {
		fromdate : $('#fromdate').datebox('getValue'),
		todate : $('#todate').datebox('getValue'),
		customerid:$('#ezuiDialog #customerid').textbox('getValue'),
		lotatt10:$('#ezuiDialog #lotatt10').combobox('getValue'),
		locationid:$('#ezuiDialog #locationid').textbox('getValue')
	});
};
// var ezuiDialogzToolbarClear= function(){
// 	$("#ezuiToolbar #fromdate").datebox('clear');
// 	$("#ezuiToolbar #todate").datebox('clear');
// };


//货主查询弹框弹出start=========================
var ezuiCustDataClick = function () {
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url: '<c:url value="/basCustomerController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiCustToolbar',
		title: '货主档案',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		queryParams: {
			activeFlag: '1',
			customerType: 'OW'
		},
		idField: 'id',
		columns: [[
			{field: 'customerid', title: '客户代码', width: 15},
			{field: 'descrC', title: '中文名称', width: 50},
			{field: 'customerTypeName', title: '类型', width: 15},
			{
				field: 'activeFlag', title: '激活', width: 15, formatter: function (value, rowData, rowIndex) {
					return rowData.activeFlag == '1' ? '是' : '否';
				}
			}
		]],
		onDblClickCell: function (index, field, value) {
			selectCust();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue', 'OW').combobox('setText', '货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue', '1').combobox('setText', '是');
	ezuiCustDataDialog.dialog('open');
};

//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function () {
	ezuiCustDataDialogId.datagrid('load', {
		customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	});
};
//货主查询弹框选择按钮
var selectCust = function () {
	processType = 'selectCust';
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if (row) {
		$("#ezuiDialog #customerid").textbox('setValue', row.descrC);
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function () {
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页datagrid工具栏--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>养护单号</th><td><input type='text' id='mtno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>养护状态</th><td><input type='text' id='mtstatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getMtStatus"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
							<td>
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='generationPlan();' id='ezuiBtn_plan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成计划</a>
					<a onclick='closegenerationPlan();' id='ezuiBtn_closeplan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>关闭计划单</a>
					<a onclick='cancel();' id='ezuiBtn_cancelplan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>取消计划单</a>

				<%--					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
<%--					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--关闭计划单点击弹窗--%>
	<div id='closegenerationPlanDialog' style='padding: 10px;'>
		<form id='closegenerationPlanForm' method='post'>
			<table>

				<tr>
					<th>贮存条件</th><td><input type='text' id='storageFlag' name="storageFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getAccordOrNoAccordCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>

				</tr>
				<tr>
					<th>作业流程</th><td><input type='text' id='flowFlag' name="flowFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getAccordOrNoAccordCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>

				</tr>
				<tr>
					<th>标志清晰</th>
					<td><input type='text' id='signFlag' name="signFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getAccordOrNoAccordCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>防护措施</th>
					<td><input type='text' id='fenceFlag' name="fenceFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getAccordOrNoAccordCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>卫生环境</th>
					<td><input type='text' id='sanitationFlag' name="sanitationFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getAccordOrNoAccordCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td><input type='text' id='remark' name="remark" class='easyui-textbox' size='20' data-options=""/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='closegenerationPlanDialogBtn'>
		<a onclick='commitClosegenerationPlan();' class='easyui-linkbutton' href='javascript:void(0);'>确认</a>
	</div>
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>


	<!-- 货主选择弹框 -->
	<div id='ezuiCustDataDialog' style="width:700px;height:480px;padding:10px 20px">
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiCustToolbar' class='datagrid-toolbar' style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th>
								<td>
									<input type='text' id='customerid' name="customerid" class='easyui-textbox' size='12'
										   data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th>
								<td>
									<input type='text' id='customerType' name="customerType" class='easyui-combobox'
										   size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
								</td>
								<th>激活：</th>
								<td>
									<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox' size='8'
										   data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'},
																																{id: 'N', value: '否'}
																															]"/>
								</td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
<%--									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton'--%>
<%--									   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>--%>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-remove"'
									   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
					<div id='ezuiCustDialogBtn'></div>
				</div>
				<table id='ezuiCustDataDialogId'></table>
			</div>
		</div>
	</div>
	<div id='ezuiCustDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.close'/></a>
	</div>

	<c:import url='/WEB-INF/jsp/docMtHeader/dialog.jsp'/>
</body>
</html>
