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
		url : '<c:url value="gspEnterpriseInfoController.do?showDatagrid"/>',
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

            {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 30 },
            {field: 'shorthandName',		title: '简称 ',	width: 30 },
            {field: 'enterpriseName',		title: '企业名称 ',	width: 30 },
            {field: 'contacts',		title: '联系人 ',	width: 30 },
            {field: 'contactsPhone',		title: '联系人电话 ',	width: 30 },
            {field: 'remark',		title: '备注 ',	width: 30 },
           /* {field: 'operateType',		title: '类型 ',	width: 12, formatter:function(value,rowData,rowIndex){
                    return rowData.operateType;
                }},*/

			/*{field: 'overreceiving',		title: '允许超收',	width: 12, formatter:function(value,rowData,rowIndex){
				return rowData.overreceiving == 'Y' ? '是' : '否';
            }},*/
            {field: 'createId',		title: '录入人',	width: 50 },
            {field: 'createDate',		title: '录入时间',	width: 50 },
            {field: 'editId',		title: '修改人',	width: 50 },
            {field: 'editDate',		title: '修改时间',	width: 50 },
            {field: 'isUse',		title: '激活 ',	width: 12}
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
			ajaxBtn($('#menuId').val(), '<c:url value="gspEnterpriseInfoController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	
	/*$("#customerid").textbox('textbox').css('text-transform','uppercase');*/

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
	$('#enterpriseId').val(0);
	/*$("#ezuiForm #customerid").textbox({
		editable:true
	}).textbox('textbox').css('text-transform','uppercase');*/
	/*$("#ezuiForm #customerType").combo('readonly', false);*/
	/*$("#ezuiForm #activeFlag").combobox('setValue','Y').combobox('setText','是');*/
	/*$("#ezuiForm #operateType").combobox('setValue','N').combobox('setText','否');*/
	/*$("#ezuiForm #overrcvpercentage").numberbox('setValue','0');*/
	ezuiDialog.dialog('open');
};

/* 编辑 */
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		/*$("#ezuiForm #customerid").textbox({
			editable:false
		});*/
	/*	$("#ezuiForm #customerType").combo('readonly', true);
		$("#ezuiForm #operateType").combo('readonly', true);*/
		ezuiForm.form('load',{



            enterpriseNo:row.enterpriseNo,
            shorthandName:row.shorthandName,
            enterpriseName:row.enterpriseName,

            contacts:row.contacts,
            contactsPhone:row.contactsPhone,
            remark:row.remark,



			/*address1 : row.address1,
			contact1 : row.contact1,
			contact1Tel1 : row.contact1Tel1,
			overreceiving : row.overreceiving,
			overrcvpercentage : row.overrcvpercentage*/
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
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.update"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'gspEnterpriseInfoController.do?deleteEnter',
					data : {enterpriseId : row.enterpriseId},
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
		url = '<c:url value="/gspEnterpriseInfoController.do?edit"/>';
	}else{
		url = '<c:url value="/gspEnterpriseInfoController.do?addEnterprise"/>';
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

        enterpriseNo : $('#enterpriseNo').val(),
        shorthandName : $('#shorthandName').val(),
        enterpriseName : $('#enterpriseName').val(),
        isUse : $('#isUse').val(),



        /* isChineseLabel : $('#isChineseLabel').combobox('getValue'),
         customerType : $('#customerType').combobox('getValue'),
         operateType : $('#operateType').combobox('getValue'),*/


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

							<th>企业信息代码：</th><td><input type='text' id='enterpriseNo' class='easyui-textbox' size='4' data-options=''/></td>
							<th>简称：</th><td><input type='text' id='shorthandName' class='easyui-textbox' size='4' data-options=''/></td>
							<th>企业名称：</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='4' data-options=''/></td>



							<%--<th>是否贴中文标签：</th><td><input type='text' id="isChineseLabel"  name="activeFlag"  class="easyui-combobox" size='4' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'}, 
																																	{id: 'N', value: '否'}
																																	]"/></td>--%>
							<%--<th>是否激活：</th><td><input type='text' id="activeFlag"  name="activeFlag"  class="easyui-combobox" size='4' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'},
																																	{id: 'N', value: '否'}
																																	]"/></td>--%>
						<th>是否激活：</th>
						<td>
							<select id="isUse" class="easyui-combobox"  style="width:100px;">
								<option value=""></option>
								<option value="Y">是</option>
								<option value="N">否</option>
							</select>
						</td>
						<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.update'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='enterpriseId' name='enterpriseId'/>
			<table>
                <tr>
                    <th>企业信息代码</th>
                    <td colspan="3"><input type='text' name='enterpriseNo'  class='easyui-textbox' size='23' data-options='required:true'/></td>
                </tr>
                <tr>
					<th>企业名称</th>
					<td><input type='text' name='enterpriseName'  class='easyui-textbox' size='8' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>简称</th>
					<td colspan="3"><input type='text' name='shorthandName'  class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>


				<tr>
					<th>联系人</th>
					<td colspan="3"><input type='text' name='contacts' id='contacts' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td colspan="3"><input type='text' name='contactsPhone' id='contactsPhone' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><input type='text' name='remark' id='remark' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>


			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<%--<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
	</div>--%>
</body>
</html>
