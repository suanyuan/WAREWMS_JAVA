<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
	<style>
		table th{
			text-align: right;
		}
	</style>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
var dialogUrl = "/gspProductRegisterController.do?toDetail";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspProductRegisterController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
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
		idField : 'id',
		columns : [[
			{field: 'productRegisterId',		title: '主键',	width: 100 ,hidden:true},
			{field: 'productRegisterNo',		title: '注册证编号',	width: 150 },
            {field: 'classifyId',		title: '管理分类',	width: 60 },
            //{field: 'classifyCatalog',		title: '分类目录',	width: 28 },
			{field: 'productNameMain',		title: '产品名称',	width: 170 },
			{field: 'approveDate',		title: '批准日期',	width: 150 },
			{field: 'productRegisterExpiryDate',		title: '有效期至',	width: 150 },
			{field: 'productRegisterVersion',		title: '注册证版本',	width: 100 },
			{field: 'checkerId',		title: '审核人',	width: 100 },
			{field: 'checkDate',		title: '审核时间',	width: 150 },
			{field: 'createId',		title: '创建人',	width: 100 },
			{field: 'createDate',		title: '创建时间',	width: 150 },
			{field: 'isUse',		title: '是否有效',	width: 100 ,formatter:isUseFormatter}

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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspProductRegisterController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
});
var add = function(){
	processType = 'add';
	$('#gspProductRegisterId').val(0);
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        href:dialogUrl,
        fit:true,
        cache: false,
        onClose : function() {
            ezuiFormClear(ezuiForm);
        },
        onLoad:function () {
        }
    })
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
        //ezuiDialog.dialog("refresh",dialogUrl+"&id="+row.productRegisterId).dialog('open');
        ezuiDialog = $('#ezuiDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:dialogUrl+"&id="+row.productRegisterId,
            fit:true,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            },
            onLoad:function () {

            }
        })
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
					url : 'gspProductRegisterController.do?delete',
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

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		productRegisterNo : $('#productRegisterNo').val(),
		productNameMain : $('#productNameMain').val(),
        classifyId : $('#classifyId').combobox("getValue"),
        classifyCatalog : $('#classifyCatalog').combobox("getValue"),
        productRegisterVersion : $('#productRegisterVersion').combobox("getValue"),
        createDateBegin : $("#createDateBegin").datebox('getValue'),
        createDateEnd : $("#createDateEnd").datebox('getValue'),
		isUse : $('#isUse').combobox("getValue")
	});
};
//下拉框
$(function () {
    $('#productRegisterVersion').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogVersion',
        valueField:'id',
        textField:'value',
        onChange:function (newValue,oldValue) {
            $('#classifyCatalog').combobox({
                url:sy.bp()+'/gspInstrumentCatalogController.do?getCombobox&version='+newValue,
                valueField:'id',
                textField:'value'
            });
        }
    });

    $('#classifyId').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogClassify',
        valueField:'id',
        textField:'value'
    });

    $('#classifyCatalog').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/gspInstrumentCatalogController.do?getCombobox&version=',
        valueField:'id',
        textField:'value'
    });

    $('#isUse').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getIsUseCombobox',
        valueField:'id',
        textField:'value'
    });
})

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
							<th>注册证编号</th><td><input type='text' id='productRegisterNo' class='easyui-textbox'  size='16' data-options=''/></td>
							<th>产品名称</th><td><input type='text' id='productNameMain' class='easyui-textbox'  size='16' data-options=''/></td>
							<th>管理分类</th><td><input type='text' id='classifyId' class='easyui-combobox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>版本</th><td><input type='text' id='productRegisterVersion' class='easyui-combobox' size='16' data-options=''/></td>
							<th>分类目录</th><td><input type='text' id='classifyCatalog' class='easyui-combobox' size='16' data-options=''/></td>
							<th>是否有效</th><td><input type='text' id='isUse' class='easyui-combobox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>创建时间</th><td><input type='text' id='createDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox'  size='16' data-options=''/></td>
							<td colspan="2">
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

	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
