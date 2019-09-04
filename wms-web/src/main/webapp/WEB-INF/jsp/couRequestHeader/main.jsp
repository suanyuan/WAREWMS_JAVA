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

var ezuiForm;        //生成盘点任务dialog form
var ezuiDialog;      //生成盘点任务dialog
var ezuiDetailsDatagrid;//生成盘点任务datagrid

var ShowEzuiDialog;//双击查看盘点任务datagrid
var ShowEzuiDetailsDatagrid;//双击查看盘点任务datagrid

var ezuiDatagrid;    //主页datagird

var closeGenerateInventoryPlanForm;       //关闭计划dialog form
var closeGenerateInventoryPlanDialog;     //关闭计划dialog
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
//主页datagird
	closeGenerateInventoryPlanForm = $('#closeGenerateInventoryPlanForm').form();   //关闭计划dialog form
//主页Datagrid
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/couRequestHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '盘点任务',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'id',
		columns : [[
			{field: 'cycleCountno',		title: '盘点单号',	width: 61 },
			{field: 'status',		title: '状态',	width: 61 },
			{field: 'fuzzyc',		title: '条件',	width: 61 },
			{field: 'addtime',		title: '创建时间',	width: 61 },
			{field: 'addwho',		title: '创建人',	width: 61 },
			{field: 'starttime',		title: '开始时间',	width: 61 },
			{field: 'endtime',		title: '结束时间',	width: 61 },
			{field: 'notes',		title: '备注',	width: 61 },
			// {field: 'userdefine1',		title: '待输入栏位8',	width: 61 },
			// {field: 'userdefine2',		title: '待输入栏位9',	width: 61 },
			// {field: 'userdefine3',		title: '待输入栏位10',	width: 61 },
			// {field: 'userdefine4',		title: '待输入栏位11',	width: 61 },
			// {field: 'userdefine5',		title: '待输入栏位12',	width: 61 }
		]],
		onDblClickCell: function(index,field,value){
			ShowGenerateInventory();
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
//生成盘点任务dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		top:0,
		left:200,
		title : '生成任务',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiToolbar);
		}
	}).dialog('close');
//生成盘点任务datagrid初始化
	ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
		url: '<c:url value="/couRequestHeaderController.do?getcouRequestInfo"/>',
		method: 'POST',
		toolbar: '#ezuiToolbar',
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		rownumbers: true,
		singleSelect: false,
		columns: [[
			{field:'ck',checkbox:true},
			{field: 'lotnum', title: '批次', width: 100},
			{field: 'customerid', title: '货主', width: 71},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'locationid', title: '库位', width: 100},
			{field: 'qty', title: '系统数量', width: 100},
			{field: 'lotatt04', title: '生产批号', width: 100},
			{field: 'lotatt05', title: '序列号', width: 100},
			{field: 'productLineName', title: '产品线', width: 100},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		},onClose:function(){
			$(this).datagrid('unselectAll');
		}
	});
//双击查看盘点任务datagrid初始化
	ShowEzuiDetailsDatagrid = $('#ShowEzuiDetailsDatagrid').datagrid({
		url: '<c:url value="/couRequestDetailsController.do?getcouRequestInfoBycycleCountno"/>',
		method: 'POST',
		toolbar: '#ShowEzuiToolbar',
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		rownumbers: true,
		singleSelect:true,
		columns: [[
			{field: 'cycleCountlineno', title: '行号', width: 71},
			{field: 'customerid', title: '货主', width: 71},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'locationid', title: '库位', width: 100},
			{field: 'qtyInv', title: 'qtyInv', width: 70},
			{field: 'qtyAct', title: 'qtyAct', width: 70},
			{field: 'lotatt04', title: '生产批号', width: 100},
			{field: 'lotatt05', title: '序列号', width: 100},
			{field: 'addtime', title: '创建时间', width: 150},
			{field: 'addwho', title: '创建人', width: 70},
			{field: 'edittime', title: '编辑时间', width: 150},
			{field: 'editwho', title: '编辑人', width: 70},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
		},
		onClose:function(){
		}
	});
//双击查看盘点任务dialog初始化
	ShowEzuiDialog = $('#ShowEzuiDialog').dialog({
		modal : true,
		top:0,
		left:200,
		buttons : '#ShowEzuiDialogBtn',
		onClose : function() {
		}
	}).dialog('close');
//关闭计划dialog
	closeGenerateInventoryPlanDialog = $('#closeGenerateInventoryPlanDialog').dialog({
		modal : true,
		width:270,
		height:250,
		title : '关闭计划',
		buttons : '#closeGenerateInventoryPlanDialogBtn',
		onClose : function() {
			ezuiFormClear(closeGenerateInventoryPlanForm);
		}
	}).dialog('close');
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
	<%--if(row.mtstatus!="00"){--%>
	<%--	$.messager.show({--%>
	<%--		msg : "只有养护状态为任务创建的状态才能删除!", title : '<spring:message code="common.message.prompt"/>'--%>
	<%--	});--%>
	<%--	return;--%>
	<%--}--%>
	if(row){
		var cycleCountno=row.cycleCountno;
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'couRequestHeaderController.do?delete',
					data : {cycleCountno : cycleCountno},
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
//生成任务
var GenerateInventoryPlan = function(){
	doxDialogSearch();//清空datagrid
	ezuiDialog.dialog('open');
};
//生成任务 选择条数之后
var GenerateInventoryPlanT = function(){
	url = '<c:url value="/couRequestHeaderController.do?ToGenerateInventoryPlan"/>';
	var rows = ezuiDetailsDatagrid.datagrid('getChecked');
	var rowsL=rows.length;
	var forms=[];
	var data=null;
	var msg='';
	for (var i = 0; i < rows.length; i++) {
		data=new Object();
		data.customerid=rows[i].customerid;
		data.sku=rows[i].sku;
		data.lotnum=rows[i].lotnum;
		data.locationid=rows[i].locationid;
		forms.push(data);

	}
	if(rowsL>0) {
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});

		$.ajax({
			url: url,
			data:"forms="+JSON.stringify(forms),
			dataType: 'json',
			error: function (a,b,c) {
				//alert(a+b+c);
			},
			success: function (result) {
				try{
					if(result.success){
						msg=result.msg;
						ezuiDatagrid.datagrid('reload');
						$.messager.show({
							msg:msg, title : '<spring:message code="common.message.prompt"/>'
						});
						ezuiDialog.dialog('close');
						$.messager.progress('close');
					}else{
						msg=result.msg;
						ezuiDatagrid.datagrid('reload');
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

	}else{
		msg = '<font color="red">' +'请选择资料!'+ '</font>';
		$.messager.show({
			msg : msg, title : '<spring:message code="common.message.prompt"/>'
		});
		$.messager.progress('close');

	}
};
//查看盘点信息
var ShowGenerateInventory = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		 var cycleCountno=row.cycleCountno;
		 $("#ShowEzuiToolbar #cycleCountno").val(cycleCountno);
		ShowEzuiDetailsDatagrid.datagrid('load',{
			cycleCountno:cycleCountno
		});
		ShowEzuiDialog.dialog('setTitle',"盘点单号:"+cycleCountno);
		ShowEzuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//关闭任务单
var closeGenerateInventoryPlan = function() {
	var row = ezuiDatagrid.datagrid('getSelected');
	if (row) {
		closeGenerateInventoryPlanDialog.dialog('open');
	}else{
		$.messager.show({
			msg: '请选择一笔资料', title: '<spring:message code="common.message.prompt"/>'
		});
	}

}
//提交关闭任务单
var commitCloseGenerateInventoryPlan = function(){
	url = '<c:url value="/couRequestHeaderController.do?closegenerationPlan"/>';
	var row = ezuiDatagrid.datagrid('getSelected');
	var msg='';
	if(closeGenerateInventoryPlanForm.form('validate')){
		var data=new Object();
		data.mtno=row.mtno;
		data.storageFlag=$("#closeGenerateInventoryPlanForm #storageFlag").combobox('getValue');
		data.flowFlag=$("#closeGenerateInventoryPlanForm #flowFlag").combobox('getValue');
		data.signFlag=$("#closeGenerateInventoryPlanForm #signFlag").combobox('getValue');
		data.fenceFlag=$("#closeGenerateInventoryPlanForm #fenceFlag").combobox('getValue');
		data.sanitationFlag=$("#closeGenerateInventoryPlanForm #sanitationFlag").textbox('getValue');

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
					closeGenerateInventoryPlanDialog.dialog('close');
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
//一级dialog提交
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/couRequestHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/couRequestHeaderController.do?add"/>';
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
		cycleCountno : $('#cycleCountno').val(),
		status : $('#status').val(),
		fuzzyc : $('#fuzzyc').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		starttime : $('#starttime').val(),
		endtime : $('#endtime').val(),
		notes : $('#notes').val(),
		userdefine1 : $('#userdefine1').val(),
		userdefine2 : $('#userdefine2').val(),
		userdefine3 : $('#userdefine3').val(),
		userdefine4 : $('#userdefine4').val(),
		userdefine5 : $('#userdefine5').val()
	});
};
//生成盘点任务datagrid查询
var doxDialogSearch = function(){
	ezuiDetailsDatagrid.datagrid('load', {
		customerid : $('#ezuiToolbar #customerid').textbox('getValue'),
		sku : $('#ezuiToolbar #sku').textbox('getValue'),
		lotatt04 : $('#ezuiToolbar #lotatt04').textbox('getValue'),
		lotatt05 : $('#ezuiToolbar #lotatt05').textbox('getValue'),
		productLineName : $('#ezuiToolbar #productLineName').combobox('getText'),
	});
};
//双击盘点任务datagrid查询
var dosDialogSearch = function(){
	ShowEzuiDetailsDatagrid.datagrid('load', {
		cycleCountno : $("#ShowEzuiToolbar #cycleCountno").val(),
		customerid : $('#ShowEzuiToolbar #customerid').textbox('getValue'),
		sku : $('#ShowEzuiToolbar #sku').textbox('getValue'),
		lotatt04 : $('#ShowEzuiToolbar #lotatt04').textbox('getValue'),
		lotatt05 : $('#ShowEzuiToolbar #lotatt05').textbox('getValue'),
		productLineName : $('#ShowEzuiToolbar #productLineName').combobox('getText'),
		lotatt12 : $('#ShowEzuiToolbar #lotatt12').textbox('getText'),
		locationid : $('#ShowEzuiToolbar #locationid').textbox('getText'),
	});
};
//生成盘点任务datagrid清除
var ezuiDialogxToolbarClear= function(){
	$("#ezuiToolbar #customerid").textbox('clear');
	$("#ezuiToolbar #sku").textbox('clear');
	$("#ezuiToolbar #lotatt04").textbox('clear');
	$("#ezuiToolbar #lotatt05").textbox('clear');
	$("#ezuiToolbar #productLineName").combobox('clear');
};
//双击盘点任务datagrid清除
var ezuiDialogsToolbarClear= function(){
	$("#ShowEzuiToolbar #customerid").textbox('clear');
	$("#ShowEzuiToolbar #sku").textbox('clear');
	$("#ShowEzuiToolbar #lotatt04").textbox('clear');
	$("#ShowEzuiToolbar #lotatt05").textbox('clear');
	$("#ShowEzuiToolbar #productLineName").combobox('clear');
	$("#ShowEzuiToolbar #lotatt12").textbox('clear');
	$("#ShowEzuiToolbar #locationid").textbox('clear');
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
							<th>盘点单号</th><td><input type='text' id='cycleCountno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>盘点状态</th><td><input type='text' id='status' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
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
					<a onclick='GenerateInventoryPlan();' id='ezuiBtn_plan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成盘点任务</a>
					<a onclick='closeGenerateInventoryPlan();' id='ezuiBtn_closeplan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>关闭任务单</a>

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
	<div id='closeGenerateInventoryPlanDialog' style='padding: 10px;'>
		<form id='closeGenerateInventoryPlanForm' method='post'>
			<table>

				<tr>
					<th>贮存条件</th><td><input type='text' id='storageFlag' name="storageFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>

				</tr>
				<tr>
					<th>作业流程</th><td><input type='text' id='flowFlag' name="flowFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>

				</tr>
				<tr>
					<th>标志清晰</th>
					<td><input type='text' id='signFlag' name="signFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>防护措施</th>
					<td><input type='text' id='fenceFlag' name="fenceFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
				</tr>
				<tr>
					<th>卫生环境</th>
					<td><input type='text' id='sanitationFlag' name="sanitationFlag" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getQualifiedOrFailedCombobox"/>',
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
	<div id='closeGenerateInventoryPlanDialogBtn'>
		<a onclick='commitCloseGenerateInventoryPlan();' class='easyui-linkbutton' href='javascript:void(0);'>确认</a>
	</div>
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

	<c:import url='/WEB-INF/jsp/couRequestHeader/dialog.jsp'/>
	<c:import url='/WEB-INF/jsp/couRequestHeader/ShowDialog.jsp'/>
</body>
</html>
