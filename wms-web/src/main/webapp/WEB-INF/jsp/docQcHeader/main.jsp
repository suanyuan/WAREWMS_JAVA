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
		url : '<c:url value="/docQcHeaderController.do?showDatagrid"/>',
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
			{field: 'qcno',	hidden:true,	title: '待输入栏位0',	width: 34 },
			{field: 'asnno',		hidden:true,	title: '待输入栏位1',	width: 34 },
			{field: 'customerid',		title: '货主sku产品代码',	width: 34 },
			{field: 'qcreference1',		title: '供应商',	width: 34 },
			{field: 'qcreference2',		title: '产品代码',	width: 34 },
			{field: 'qcreference3',		title: '产品名称',	width: 34 },
			{field: 'qcreference4',		title: '注册证号/备案凭证编号',	width: 34 },
			{field: 'qcreference5',		title: '规格/型号',	width: 34 },
			{field: 'qctype',		title: '生产批号',	width: 34 },
			{field: 'qcstatus',		title: '序列号',	width: 34 },
			{field: 'qccreationtime',		title: '灭菌批号',	width: 34 },
			{field: 'userdefine1',		title: '生产日期',	width: 34 },
			{field: 'userdefine2',		title: '存储条件',	width: 34 },
			{field: 'userdefine3',		title: '生产厂家',	width: 34 },
			{field: 'userdefine4',		title: '产地',	width: 34 },
			{field: 'userdefine5',		title: '到货数量',	width: 34 },
			{field: 'notes',		title: '验收合格数量',	width: 34 },
			{field: 'addtime',		title: '验收不合格数量',	width: 34 },
			{field: 'addwho',		title: '单位',	width: 34 },
			{field: 'edittime',		title: '验收说明',	width: 34 },
			{field: 'editwho',		title: '验收结论',	width: 34 },
			{field: 'qcPrintFlag',		title: '验收日期',	width: 34 },
			{field: 'warehouseid',		title: '验收人',	width: 34 },
			{field: 'warehouseid',		title: '合格证明文件上传',	width: 34 },
			{field: 'warehouseid',		title: '冷链产品随货温度记录上传',	width: 34 },
			{field: 'warehouseid',		title: '双证记录',	width: 34 },
			{field: 'warehouseid',		title: '产品线',	width: 34 },
			{field: 'warehouseid',		title: '备注',	width: 34 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/docQcHeaderController.do?getBtn"/>', ezuiMenu);
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
});
var add = function(){
	processType = 'add';
	$('#docQcHeaderId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			qcno : row.qcno,
			asnno : row.asnno,
			customerid : row.customerid,
			qcreference1 : row.qcreference1,
			qcreference2 : row.qcreference2,
			qcreference3 : row.qcreference3,
			qcreference4 : row.qcreference4,
			qcreference5 : row.qcreference5,
			qctype : row.qctype,
			qcstatus : row.qcstatus,
			qccreationtime : row.qccreationtime,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			notes : row.notes,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			qcPrintFlag : row.qcPrintFlag,
			warehouseid : row.warehouseid
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
					url : 'docQcHeaderController.do?delete',
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
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docQcHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docQcHeaderController.do?add"/>';
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
		qcno : $('#qcno').val(),
		asnno : $('#asnno').val(),
		customerid : $('#customerid').val(),
		qcreference1 : $('#qcreference1').val(),
		qcreference2 : $('#qcreference2').val(),
		qcreference3 : $('#qcreference3').val(),
		qcreference4 : $('#qcreference4').val(),
		qcreference5 : $('#qcreference5').val(),
		qctype : $('#qctype').val(),
		qcstatus : $('#qcstatus').val(),
		qccreationtime : $('#qccreationtime').val(),
		userdefine1 : $('#userdefine1').val(),
		userdefine2 : $('#userdefine2').val(),
		userdefine3 : $('#userdefine3').val(),
		userdefine4 : $('#userdefine4').val(),
		userdefine5 : $('#userdefine5').val(),
		notes : $('#notes').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val(),
		qcPrintFlag : $('#qcPrintFlag').val(),
		warehouseid : $('#warehouseid').val()
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
							<th>待输入名称0：</th><td><input type='text' id='qcno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称1：</th><td><input type='text' id='asnno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称2：</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称3：</th><td><input type='text' id='qcreference1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称4：</th><td><input type='text' id='qcreference2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称5：</th><td><input type='text' id='qcreference3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称6：</th><td><input type='text' id='qcreference4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称7：</th><td><input type='text' id='qcreference5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称8：</th><td><input type='text' id='qctype' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称9：</th><td><input type='text' id='qcstatus' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称10：</th><td><input type='text' id='qccreationtime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称11：</th><td><input type='text' id='userdefine1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称12：</th><td><input type='text' id='userdefine2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称13：</th><td><input type='text' id='userdefine3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称14：</th><td><input type='text' id='userdefine4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称15：</th><td><input type='text' id='userdefine5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称16：</th><td><input type='text' id='notes' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称17：</th><td><input type='text' id='addtime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称18：</th><td><input type='text' id='addwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称19：</th><td><input type='text' id='edittime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称20：</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称21：</th><td><input type='text' id='qcPrintFlag' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称22：</th><td><input type='text' id='warehouseid' class='easyui-textbox' size='16' data-options=''/></td>
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
			<input type='hidden' id='docQcHeaderId' name='docQcHeaderId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='qcno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='asnno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='qcreference1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='qcreference2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='qcreference3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='qcreference4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='qcreference5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='qctype' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='qcstatus' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='qccreationtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='userdefine1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='userdefine2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='userdefine3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='userdefine4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='userdefine5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入16</th>
					<td><input type='text' name='notes' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入17</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入18</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入19</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入20</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入21</th>
					<td><input type='text' name='qcPrintFlag' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入22</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>
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
