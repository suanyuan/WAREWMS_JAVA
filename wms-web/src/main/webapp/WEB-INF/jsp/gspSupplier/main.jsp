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
var dialogUrl = "/gspSupplierController.do?toAdd";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspSupplierController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '待输入标题',
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
			{field: 'supplierId',		title: '主键',	width: 88,hidden:true },
			{field: 'enterpriseId',		title: '企业流水号',	width: 88 },

			{field: 'operateType',		title: '类型（经营/生产）',	width: 88 },
			{field: 'createId',		title: '创建人',	width: 88 },
			{field: 'createDate',		title: '创建时间',	width: 88 },
			{field: 'editId',		title: '编辑人',	width: 88 },
			{field: 'editDate',		title: '编辑时间',	width: 88 },
            {field: 'isCheck',		title: '是否审查',	width: 88 ,formatter:function(value,rowData,rowIndex){
                    return rowData.isCheck == '1' ? '是' : '否';
                }},
			{field: 'isUse',		title: '是否有效',	width: 88 ,formatter:function(value,rowData,rowIndex){
                    return rowData.isUse == '1' ? '是' : '否';
                }}
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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspSupplierController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href:dialogUrl,
        fit:true,
        cache: false,
        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    }).dialog('close');
});
var add = function(){
	processType = 'add';
	$('#gspSupplierId').val(0);
	ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	//alert(row.supplierId);
	if(row){
		// ezuiForm.form('load',{
        //     // supplierId : row.supplierId,
		// 	// enterpriseId : row.enterpriseId,
		// 	// isCheck : row.isCheck,
		// 	// operateType : row.operateType,
		// 	// createId : row.createId,
		// 	// createDate : row.createDate,
		// 	// editId : row.editId,
		// 	// editDate : row.editDate,
		// 	// isUse : row.isUse
		// });
		ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
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
					url : 'gspSupplierController.do?delete',
					data : {id : row.supplierId},
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
    $("#ezuiFormInfo input[class='textbox-value']").each(function (index) {
        infoObj[""+$(this).attr("name")+""] = $(this).val();
    })

	var url = '';
	if (processType == 'edit') {
        var row = ezuiDatagrid.datagrid('getSelected');
        infoObj["supplierId"] = row.supplierId;
        url = sy.bp()+'/gspSupplierController.do?edit';
	}else{
        url = sy.bp()+'/gspSupplierController.do?add';
	}

    $.ajax({
        url : url,
        data : {"gspSupplierForm":JSON.stringify(infoObj)},type : 'POST', dataType : 'JSON',async  :true,
        success : function(result){
            console.log(result);
            var msg='';
            try{
                if(result.success){
                    msg = result.msg;
                    ezuiDatagrid.datagrid('reload');
                    ezuiDialog.dialog('close');
                }else{
                    msg = '<font color="red">' + result.msg + '</font>';
                }
            }catch (e) {
                //msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
            } finally {
                $.messager.show({
                    msg : msg, title : '<spring:message code="common.message.prompt"/>'
                });
                $.messager.progress('close');
            }
        }
    });
	<%--ezuiForm.form('submit', {--%>
		<%--url : url,--%>
		<%--onSubmit : function(){--%>
			<%--if(ezuiForm.form('validate')){--%>
				<%--$.messager.progress({--%>
					<%--text : '<spring:message code="common.message.data.processing"/>', interval : 100--%>
				<%--});--%>
				<%--return true;--%>
			<%--}else{--%>
				<%--return false;--%>
			<%--}--%>
		<%--},--%>
		<%--success : function(data) {--%>
			<%--var msg='';--%>
			<%--try {--%>
				<%--var result = $.parseJSON(data);--%>
				<%--if(result.success){--%>
					<%--msg = result.msg;--%>
					<%--ezuiDatagrid.datagrid('reload');--%>
					<%--ezuiDialog.dialog('close');--%>
				<%--}else{--%>
					<%--msg = '<font color="red">' + result.msg + '</font>';--%>
				<%--}--%>
			<%--} catch (e) {--%>
				<%--msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';--%>
				<%--msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;--%>
			<%--} finally {--%>
				<%--$.messager.show({--%>
					<%--msg : msg, title : '<spring:message code="common.message.prompt"/>'--%>
				<%--});--%>
				<%--$.messager.progress('close');--%>
			<%--}--%>
		<%--}--%>
	<%--});--%>
};
var doSearch = function(){
   // alert( $('#isCheck').val());
	ezuiDatagrid.datagrid('load', {
		// supplierId : $('#supplierId').val(),
		enterpriseId : $('#enterpriseId').val(),

		operateType : $('#operateType').val(),
		// createId : $('#createId').val(),
        createDateStart : $('#createDateStart').val(),
        createDateEnd : $('#createDateEnd').val(),
        editDateStart : $('#editDateStart').val(),
        //isCheck : $('#isCheck').combobox('getValue'),
        isUse : $('#isUse').combobox('getValue'),
        editDateEnd : $('#editDateEnd').val()
		// editId : $('#editId').val(),
		//editDate : $('#editDate').val(),

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
							<th>企业流水号</th><td><input type='text' id='enterpriseId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建时间起始</th><td><input type='text' id='createDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>创建时间结束</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<th>类型</th><td><input type="text" id="productionAddress"  name="productionAddress"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '经营', value: '经营'},
																																	{id: '生产', value: '生产'}
																																]"/></td>
							<%--<td><input type='text' id='operateType' class='easyui-textbox' size='16' data-options=''/></td>--%>
						</tr>
						<tr>
							<th>是否审查</th><td><input type="text" id="ischeck"  name="ischeck"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}]"/></td>
								<%--<th>是否审查：</th><td>--%>
								<%--<select id="ischeck" name="ischeck" class="easyui-combobox"  style="width:150px;">--%>
									<%--<option value=""></option>--%>
									<%--<option value="1">是</option>--%>
									<%--<option value="0">否</option>--%>
								<%--</select></td>--%>
							<th>编辑时间起始</th><td><input type='text' id='editDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>编辑时间结束</th><td><input type='text' id='editDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<th>是否有效</th><td><input type="text" id="isUse"  name="isUse"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
								<%--<th>是否有效：</th><td>--%>
								<%--<select id="isUse" name="isUse" class="easyui-combobox"  style="width:150px;">--%>
									<%--<option value=""></option>--%>
									<%--<option value="1">是</option>--%>
									<%--<option value="0">否</option>--%>
								<%--</select></td>--%>

						</tr>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>

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
