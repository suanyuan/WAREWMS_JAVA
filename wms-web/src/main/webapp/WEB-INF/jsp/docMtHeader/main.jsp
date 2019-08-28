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
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
//主页datagird
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
			// {field: 'userdefine1',		title: '待输入栏位5',	width: 50 },
			// {field: 'userdefine2',		title: '待输入栏位6',	width: 50 },
			// {field: 'userdefine3',		title: '待输入栏位7',	width: 50 },
			// {field: 'userdefine4',		title: '待输入栏位8',	width: 50 },
			// {field: 'userdefine5',		title: '待输入栏位9',	width: 50 },
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
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		rownumbers: true,
		singleSelect: true,
		columns: [[
			{field: 'lotnum', title: '批次', width: 100},
			{field: 'locationid', title: '库位', width: 100},
			{field: 'customerid', title: '货主', width: 71},
			{field: 'sku', title: '产品代码', width: 100},
			{field: 'qty', title: '养护件数', width: 100},
			{field: 'lotatt03', title: '入库时间', width: 100},
			{field: 'lotatt03test', title: '预期养护时间', width: 100},
			{field: 'reservedfield10', title: '养护周期', width: 100},
			//	用于回传后台时间
			{field: 'fromDate', title: '开始时间', width: 100,hidden:true},
			{field: 'toDate', title: '结束时间', width: 100,hidden:true},
			// {field: 'qty1', title: '转化率', width: 100},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		},onClose:function(){
		}
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
	if(row.mtstatus!="00"){
		$.messager.show({
			msg : "只有养护状态为任务创建的状态才能删除!", title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
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
var closegenerationPlan = function(){
	url = '<c:url value="/docMtHeaderController.do?closegenerationPlan"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
    if(row) {
		var mtno=row.mtno;
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '您要关闭所选项目?', function(confirm) {
			if(confirm){
			$.messager.progress({
				text: '<spring:message code="common.message.data.processing"/>', interval: 100
			});
			$.ajax({
				url: url,
				data:{mtno:mtno},
				dataType: 'json',
				error: function (a, b, c) {
					alert(a + b + c);
				},
				success: function (result) {
					try {
						if (result.errorCode == "200") {
							msg = result.msg;
							ezuiDatagrid.datagrid('reload');
							$.messager.show({
								msg: msg, title: '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
						} else {
							msg = result.msg;
							ezuiDatagrid.datagrid('reload');
							$.messager.show({
								msg: msg, title: '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
						}
					} catch (e) {
						$.messager.show({
							msg: '数据错误!', title: '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
					}
				}
			});
			}
		});
	}else{
		$.messager.show({
			msg: '请选择一笔资料', title: '<spring:message code="common.message.prompt"/>'
		});
	}
};
//生成 时间段查询完养护计划之后
var generationPlanT = function(){
	url = '<c:url value="/docMtHeaderController.do?ToGenerationInfo"/>';
	var rows=ezuiDetailsDatagrid.datagrid('getRows')
	var data=new Object();
	var fromDate=rows[0].fromDate;
	var toDate=rows[0].toDate;
	data.fromdate=fromDate;
	data.todate=toDate;

	var msg='';
	$.messager.progress({
		text: '<spring:message code="common.message.data.processing"/>', interval: 100
	});
	$.ajax({
		url: url,
		data:data,
		dataType: 'json',
		error: function (a,b,c) {
			alert(a+b+c);
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
	});
};
var ezuiDialogzToolbarClear= function(){
	$("#ezuiToolbar #fromdate").datebox('clear');
	$("#ezuiToolbar #todate").datebox('clear');
};
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
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='generationPlan();' id='ezuiBtn_plan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成计划</a>
					<a onclick='closegenerationPlan();' id='ezuiBtn_closeplan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>关闭计划单</a>

				<%--					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
<%--					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

	<c:import url='/WEB-INF/jsp/docMtHeader/dialog.jsp'/>
</body>
</html>
