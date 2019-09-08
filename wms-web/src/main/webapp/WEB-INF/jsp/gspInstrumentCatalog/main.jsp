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
		url : '<c:url value="/gspInstrumentCatalogController.do?showDatagrid"/>',
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
		idField : 'instrumentCatalogId',
		columns : [[
			{field: 'instrumentCatalogId',		title: '主键',	width: 50,hidden:true },
            {field: 'isUse',		title: '是否有效',	width: 72 ,formatter:isUseFormatter},
			{field: 'instrumentCatalogNo',		title: '编号',	width: 72 },
			{field: 'instrumentCatalogName',		title: '名称',	width: 72 },
			{field: 'instrumentCatalogRemark',		title: '描述',	width: 72 },
			{field: 'classifyId',		title: '分类',	width: 72 },
			{field: 'version',		title: '版本',	width: 72 },
			{field: 'createId',		title: '创建人',	width: 72 },
			{field: 'cretaeDate',		title: '创建时间',	width: 72 ,formatter: dateFormat},
			{field: 'editId',		title: '修改人',	width: 72 },
			{field: 'editDate',		title: '修改时间',	width: 72 ,formatter: dateFormat},

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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspInstrumentCatalogController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		width:300,
		height:400,
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

    $('#classifyIdQuery').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogClassify',
        valueField:'id',
        textField:'value',
        editable:false
    });

    $('#versionQuery').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogVersion',
        valueField:'id',
        textField:'value',
        editable:false
    });

    $('#classifyId').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogClassify',
        valueField:'id',
        textField:'value',
        editable:false
    });

    $('#version').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogVersion',
        valueField:'id',
        textField:'value',
        editable:false
    });

    $('#isUseQuery').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value',
        editable:false
    });
});
var add = function(){
	processType = 'add';
	$('#gspInstrumentCatalogId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
	    $("#instrumentCatalogId").val(row.instrumentCatalogId);
		ezuiForm.form('load',{
			instrumentCatalogId : row.instrumentCatalogId,
			instrumentCatalogNo : row.instrumentCatalogNo,
			instrumentCatalogName : row.instrumentCatalogName,
			instrumentCatalogRemark : row.instrumentCatalogRemark
            //classifyId:row.classifyId,
            //version:row.version
		});

        $('#fromClassify').combobox("setValue",row.classifyId);

        $('#fromVersion').combobox("setValue",row.version);

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
					url : 'gspInstrumentCatalogController.do?delete',
					data : {id : row.instrumentCatalogId},
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
		url = '<c:url value="/gspInstrumentCatalogController.do?edit"/>';
	}else{
		url = '<c:url value="/gspInstrumentCatalogController.do?add"/>';
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
		instrumentCatalogNo : $('#instrumentCatalogNoQuery').val(),
		instrumentCatalogName : $('#instrumentCatalogNameQuery').val(),
		classifyId : $('#classifyIdQuery').combobox("getValue"),
		version : $('#versionQuery').combobox("getValue"),
		isUse : $('#isUseQuery').combobox("getValue")
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
							<!--<th style="display: none;">待输入名称0：</th><td><input type='text'  id='instrumentCatalogId' class='easyui-textbox' size='16' data-options=''/></td>-->
							<th>编号</th><td><input type='text' id='instrumentCatalogNoQuery' class='easyui-textbox' size='16' data-options=''/></td>
							<th>名称</th><td><input type='text' id='instrumentCatalogNameQuery' class='easyui-textbox' size='16' data-options=''/></td>
							<!--<th style="display: none;">待输入名称3：</th><td><input type='text' id='instrumentCatalogRemark' class='easyui-textbox' size='16' data-options=''/></td>-->
							<th>分类</th><td><input type='text' id='classifyIdQuery' class='easyui-combobox' size='16' data-options=''/></td>
							<th>版本</th><td><input type='text' id='versionQuery'  class='easyui-combobox' size='16' data-options=''/></td>
							<!--<th >待输入名称6：</th><td><input type='text' id='createId' class='easyui-textbox' size='16' data-options=''/></td>-->
							<!--<th >待输入名称7：</th><td><input type='text' id='cretaeDate' class='easyui-textbox' size='16' data-options=''/></td>-->
							<!--<th >待输入名称8：</th><td><input type='text' id='editId' class='easyui-textbox' size='16' data-options=''/></td>-->
							<!--<th >待输入名称9：</th><td><input type='text' id='editDate' class='easyui-textbox' size='16' data-options=''/></td>-->
							<th>是否有效</th><td><input type='text' id='isUseQuery' class='easyui-combobox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<!--<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>-->
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<%--二级dialog--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='instrumentCatalogId' name='instrumentCatalogId' />
			<table>
				<tr>
					<th>编号</th>
					<td><input type='text' name='instrumentCatalogNo' class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
				</tr>
				<tr>
					<th>名称</th>
					<td><input type='text' name='instrumentCatalogName' class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
				</tr>
				<tr>
					<th>分类</th>
					<td><input type='text' id="classifyId" name='classifyId'  size='16' data-options='required:true,width:200'/></td>
				</tr>
				<tr>
					<th>版本</th>
					<td><input type='text' id="version" name='version'  size='16' data-options='required:true,width:200'/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td><input type='text' name='instrumentCatalogRemark' class='easyui-textbox' size='16' style="height: 150px;" data-options='multiline:true,width:200'/></td>
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
