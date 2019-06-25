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
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspEnterpriseInfoController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
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
			{field: 'enterpriseId',		title: '主键',	width: 61 ,hidden:true},
			{field: 'enterpriseNo',		title: '企业信息代码',	width: 61 },
			{field: 'shorthandName',		title: '简称',	width: 61 },
			{field: 'enterpriseName',		title: '企业名称',	width: 61 },
			{field: 'enterpriseType',		title: '企业类型',	width: 61 },
			{field: 'contacts',		title: '联系人',	width: 61 },
			{field: 'contactsPhone',		title: '联系人电话',	width: 61 },
			{field: 'remark',		title: '备注',	width: 61 },
            {field: 'createId',		title: '录入人',	width: 61 },
            {field: 'createDate',		title: '录入时间',	width: 61 },
            {field: 'editId',		title: '修改人',	width: 61 },
            {field: 'editDate',		title: '修改时间',	width: 61 },
            {field: 'isUse',		title: '是否有效',	width: 61 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspEnterpriseInfoController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		href:'/gspEnterpriseInfoController.do?toDetail',
		fit:true,
        cache: false,
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});
var add = function(){
	processType = 'add';
	$('#gspEnterpriseInfoId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			enterpriseId : row.enterpriseId,
			enterpriseNo : row.enterpriseNo,
			shorthandName : row.shorthandName,
			enterpriseName : row.enterpriseName,
			enterpriseType : row.enterpriseType,
			contacts : row.contacts,
			contactsPhone : row.contactsPhone,
			remark : row.remark,
			createId : row.createId,
			createDate : row.createDate,
			editId : row.editId,
			editDate : row.editDate,
			isUse : row.isUse
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
					url : 'gspEnterpriseInfoController.do?delete',
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
    var infoObj = new Object();
    var businessObj = new Object();
    //var infoObj = new Object();
    //var infoObj = new Object();
    $("#ezuiFormInfo input[class='textbox-value']").each(function (index) {
        infoObj[""+$(this).attr("name")+""] = $(this).val();
    })
    $("#ezuiFormBusiness input[class='textbox-value'][type!='file']").each(function (index) {
        businessObj[""+$(this).attr("name")+""] = $(this).val();
    })

	console.log(businessObj);
	/*var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/gspEnterpriseInfoController.do?edit"/>';
	}else{
		url = '<c:url value="/gspEnterpriseInfoController.do?add"/>';
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
	});*/
};
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		enterpriseNo : $('#enterpriseNo').val(),
		shorthandName : $('#shorthandName').val(),
		enterpriseName : $('#enterpriseName').val(),
		enterpriseType : $('#enterpriseType').val(),
		createDate : $('#createDate').val(),
		isUse : $('#isUse').val()
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
							<th>企业信用代码：</th><td><input type='text' id='enterpriseNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>简称：</th><td><input type='text' id='shorthandName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业名称：</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业类型：</th><td><input type='text' id='enterpriseType' class='easyui-combobox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
						<tr>
							<th>创建时间：</th><td><input type='text' id='createDate' class='easyui-datebox' size='16' data-options=''/></td>
							<th>是否启用：</th>
							<td>
								<select id="isUse" class="easyui-combobox"  style="width:100px;">
									<option value=""></option>
									<option value="10">是</option>
									<option value="0">否</option>
								</select>
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
