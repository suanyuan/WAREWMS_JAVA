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

/* 初始化 */
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
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
			{field: 'customerid',		title: '客户',	width: 16 },
			{field: 'activeFlag',		title: '激活 ',	width: 12, formatter:function(value,rowData,rowIndex){
				return rowData.activeFlag == 'Y' ? '是' : '否';
            }},
			{field: 'customerType',		title: '客户类型 ',	width: 12, formatter:function(value,rowData,rowIndex){
				return rowData.customerTypeName;
            }},
			{field: 'descrC',		title: '中文名称 ',	width: 30 },
			{field: 'descrE',		title: '英文名称 ',	width: 20 },
			{field: 'address1',		title: '地址 ',	width: 40 },
			{field: 'contact1',		title: '联系人 ',	width: 16 },
			{field: 'contact1Tel1',		title: '电话 ',	width: 20 },
			{field: 'overreceiving',		title: '允许超收',	width: 12, formatter:function(value,rowData,rowIndex){
				return rowData.overreceiving == 'Y' ? '是' : '否';
            }},
			{field: 'overrcvpercentage',		title: '超收百分比',	width: 12 }
		]],
		onDblClickCell: function(index,field,value){
			edit();
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
			ajaxBtn($('#menuId').val(), '<c:url value="/basCustomerController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	
	$("#customerid").textbox('textbox').css('text-transform','uppercase');
	
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});

/* 新增 */
var add = function(){
	processType = 'add';
	$('#basCustomerId').val(0);
	$("#ezuiForm #customerid").textbox({
		editable:true
	}).textbox('textbox').css('text-transform','uppercase');
	$("#ezuiForm #customerType").combo('readonly', false);
	$("#ezuiForm #activeFlag").combobox('setValue','Y').combobox('setText','是');
	$("#ezuiForm #overreceiving").combobox('setValue','N').combobox('setText','否');
	$("#ezuiForm #overrcvpercentage").numberbox('setValue','0');
	ezuiDialog.dialog('open');
};

/* 编辑 */
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$("#ezuiForm #customerid").textbox({
			editable:false
		});
		$("#ezuiForm #customerType").combo('readonly', true);
		ezuiForm.form('load',{
			customerid : row.customerid,
			customerType : row.customerType,
			descrC : row.descrC,
			descrE : row.descrE,
			activeFlag : row.activeFlag,
			address1 : row.address1,
			contact1 : row.contact1,
			contact1Tel1 : row.contact1Tel1,
			overreceiving : row.overreceiving,
			overrcvpercentage : row.overrcvpercentage
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 删除 */
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'basCustomerController.do?delete',
					data : {customerid : row.customerid, customertype : row.customerType},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							if(result.success){
								msg = result.msg;
							}else{
								$.messager.alert('操作提示', result.msg);
								msg = '<font color="red">' + result.msg + '</font>';
							}
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

/* 提交 */
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/basCustomerController.do?edit"/>';
	}else{
		url = '<c:url value="/basCustomerController.do?add"/>';
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
					$.messager.alert('操作提示', result.msg);
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

/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		customerid : $('#customerid').val(),
		customerType : $('#customerType').combobox('getValue'),
		activeFlag : $('#activeFlag').combobox('getValue')
	});
};

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
							<th>客户：</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>客户类型：</th><td><input type='text' id='customerType' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/></td>
							<th>是否激活：</th><td><input type='text' id="activeFlag"  name="activeFlag"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'}, 
																																	{id: 'N', value: '否'}
																																	]"/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='basCustomerId' name='basCustomerId'/>
			<table>
				<tr>
					<th>客户</th>
					<td><input type='text' name='customerid' id='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>是否激活</th>
					<td><input type='text' name='activeFlag' id='activeFlag' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'Y', value: '是'}, 
																											{id: 'N', value: '否'}
																											]"/></td>
				</tr>
				<tr>
					<th>中文名称</th>
					<td colspan="3"><input type='text' name='descrC' id='descrC' class='easyui-textbox' size='46' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>英文名称</th>
					<td><input type='text' name='descrE' id='descrE' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>客户类型</th>
					<td><input type='text' name='customerType' id='customerType' class='easyui-combobox' size='16' data-options="required:true,
																																panelHeight:'auto',
																																editable:false,
																																url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																																valueField: 'id',
																																textField: 'value'"/></td>
				</tr>
				<tr>
					<th>地址</th>
					<td colspan="3"><input type='text' name='address1' id='address1' class='easyui-textbox' size='46' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input type='text' name='contact1' id='contact1' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>电话</th>
					<td><input type='text' name='contact1Tel1' id='contact1Tel1' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>财务联系人（D）</th>
					<td><input type='text' name='contact1' id='contact1' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>电话（D）</th>
					<td><input type='text' name='contact1Tel1' id='contact1Tel1' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>允许超收</th>
					<td><input type='text' name='overreceiving' id='overreceiving' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'}, 
																																	{id: 'N', value: '否'}
																																	]"/></td>
					<th>超收百分比</th>
					<td><input type='text' name='overrcvpercentage' id='overrcvpercentage' class='easyui-numberbox' size='16' data-options='required:false,min:0'/></td>
					<th>是否整托收货（D）</th>
					<td><input type='text' name='overrcvpercentage' id='overrcvpercentage' class='easyui-numberbox' size='16' data-options='required:false,min:0'/></td>
				</tr>			
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
