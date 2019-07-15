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
		url : '<c:url value="/docPoDetailsController.do?showDatagrid"/>',
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
			{field: 'pono',		title: '待输入栏位0',	width: 15 },
			{field: 'polineno',		title: '待输入栏位1',	width: 15 },
			{field: 'customerid',		title: '待输入栏位2',	width: 15 },
			{field: 'sku',		title: '待输入栏位3',	width: 15 },
			{field: 'skudescrc',		title: '待输入栏位4',	width: 15 },
			{field: 'skudescre',		title: '待输入栏位5',	width: 15 },
			{field: 'orderedqty',		title: '待输入栏位6',	width: 15 },
			{field: 'orderedqtyEach',		title: '待输入栏位7',	width: 15 },
			{field: 'receivedqty',		title: '待输入栏位8',	width: 15 },
			{field: 'receivedqtyEach',		title: '待输入栏位9',	width: 15 },
			{field: 'receivedtime',		title: '待输入栏位10',	width: 15 },
			{field: 'uom',		title: '待输入栏位11',	width: 15 },
			{field: 'packid',		title: '待输入栏位12',	width: 15 },
			{field: 'totalcubic',		title: '待输入栏位13',	width: 15 },
			{field: 'totalgrossweight',		title: '待输入栏位14',	width: 15 },
			{field: 'totalnetweight',		title: '待输入栏位15',	width: 15 },
			{field: 'totalprice',		title: '待输入栏位16',	width: 15 },
			{field: 'userdefine1',		title: '待输入栏位17',	width: 15 },
			{field: 'userdefine2',		title: '待输入栏位18',	width: 15 },
			{field: 'userdefine3',		title: '待输入栏位19',	width: 15 },
			{field: 'userdefine4',		title: '待输入栏位20',	width: 15 },
			{field: 'userdefine5',		title: '待输入栏位21',	width: 15 },
			{field: 'addtime',		title: '待输入栏位22',	width: 15 },
			{field: 'addwho',		title: '待输入栏位23',	width: 15 },
			{field: 'edittime',		title: '待输入栏位24',	width: 15 },
			{field: 'editwho',		title: '待输入栏位25',	width: 15 },
			{field: 'lotatt01',		title: '待输入栏位26',	width: 15 },
			{field: 'lotatt02',		title: '待输入栏位27',	width: 15 },
			{field: 'lotatt03',		title: '待输入栏位28',	width: 15 },
			{field: 'lotatt04',		title: '待输入栏位29',	width: 15 },
			{field: 'lotatt05',		title: '待输入栏位30',	width: 15 },
			{field: 'lotatt06',		title: '待输入栏位31',	width: 15 },
			{field: 'lotatt07',		title: '待输入栏位32',	width: 15 },
			{field: 'lotatt08',		title: '待输入栏位33',	width: 15 },
			{field: 'lotatt09',		title: '待输入栏位34',	width: 15 },
			{field: 'lotatt10',		title: '待输入栏位35',	width: 15 },
			{field: 'lotatt11',		title: '待输入栏位36',	width: 15 },
			{field: 'notes',		title: '待输入栏位37',	width: 15 },
			{field: 'lotatt12',		title: '待输入栏位38',	width: 15 },
			{field: 'polinestatus',		title: '待输入栏位39',	width: 15 },
			{field: 'dEdi01',		title: '待输入栏位40',	width: 15 },
			{field: 'dEdi02',		title: '待输入栏位41',	width: 15 },
			{field: 'dEdi03',		title: '待输入栏位42',	width: 15 },
			{field: 'dEdi04',		title: '待输入栏位43',	width: 15 },
			{field: 'dEdi05',		title: '待输入栏位44',	width: 15 },
			{field: 'dEdi06',		title: '待输入栏位45',	width: 15 },
			{field: 'dEdi07',		title: '待输入栏位46',	width: 15 },
			{field: 'dEdi08',		title: '待输入栏位47',	width: 15 },
			{field: 'dEdi09',		title: '待输入栏位48',	width: 15 },
			{field: 'dEdi10',		title: '待输入栏位49',	width: 15 },
			{field: 'qtyreleased',		title: '待输入栏位50',	width: 15 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/docPoDetailsController.do?getBtn"/>', ezuiMenu);
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
	$('#docPoDetailsId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			pono : row.pono,
			polineno : row.polineno,
			customerid : row.customerid,
			sku : row.sku,
			skudescrc : row.skudescrc,
			skudescre : row.skudescre,
			orderedqty : row.orderedqty,
			orderedqtyEach : row.orderedqtyEach,
			receivedqty : row.receivedqty,
			receivedqtyEach : row.receivedqtyEach,
			receivedtime : row.receivedtime,
			uom : row.uom,
			packid : row.packid,
			totalcubic : row.totalcubic,
			totalgrossweight : row.totalgrossweight,
			totalnetweight : row.totalnetweight,
			totalprice : row.totalprice,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			lotatt01 : row.lotatt01,
			lotatt02 : row.lotatt02,
			lotatt03 : row.lotatt03,
			lotatt04 : row.lotatt04,
			lotatt05 : row.lotatt05,
			lotatt06 : row.lotatt06,
			lotatt07 : row.lotatt07,
			lotatt08 : row.lotatt08,
			lotatt09 : row.lotatt09,
			lotatt10 : row.lotatt10,
			lotatt11 : row.lotatt11,
			notes : row.notes,
			lotatt12 : row.lotatt12,
			polinestatus : row.polinestatus,
			dEdi01 : row.dEdi01,
			dEdi02 : row.dEdi02,
			dEdi03 : row.dEdi03,
			dEdi04 : row.dEdi04,
			dEdi05 : row.dEdi05,
			dEdi06 : row.dEdi06,
			dEdi07 : row.dEdi07,
			dEdi08 : row.dEdi08,
			dEdi09 : row.dEdi09,
			dEdi10 : row.dEdi10,
			qtyreleased : row.qtyreleased
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
					url : 'docPoDetailsController.do?delete',
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
		url = '<c:url value="/docPoDetailsController.do?edit"/>';
	}else{
		url = '<c:url value="/docPoDetailsController.do?add"/>';
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
		pono : $('#pono').val(),
		polineno : $('#polineno').val(),
		customerid : $('#customerid').val(),
		sku : $('#sku').val(),
		skudescrc : $('#skudescrc').val(),
		skudescre : $('#skudescre').val(),
		orderedqty : $('#orderedqty').val(),
		orderedqtyEach : $('#orderedqtyEach').val(),
		receivedqty : $('#receivedqty').val(),
		receivedqtyEach : $('#receivedqtyEach').val(),
		receivedtime : $('#receivedtime').val(),
		uom : $('#uom').val(),
		packid : $('#packid').val(),
		totalcubic : $('#totalcubic').val(),
		totalgrossweight : $('#totalgrossweight').val(),
		totalnetweight : $('#totalnetweight').val(),
		totalprice : $('#totalprice').val(),
		userdefine1 : $('#userdefine1').val(),
		userdefine2 : $('#userdefine2').val(),
		userdefine3 : $('#userdefine3').val(),
		userdefine4 : $('#userdefine4').val(),
		userdefine5 : $('#userdefine5').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val(),
		lotatt01 : $('#lotatt01').val(),
		lotatt02 : $('#lotatt02').val(),
		lotatt03 : $('#lotatt03').val(),
		lotatt04 : $('#lotatt04').val(),
		lotatt05 : $('#lotatt05').val(),
		lotatt06 : $('#lotatt06').val(),
		lotatt07 : $('#lotatt07').val(),
		lotatt08 : $('#lotatt08').val(),
		lotatt09 : $('#lotatt09').val(),
		lotatt10 : $('#lotatt10').val(),
		lotatt11 : $('#lotatt11').val(),
		notes : $('#notes').val(),
		lotatt12 : $('#lotatt12').val(),
		polinestatus : $('#polinestatus').val(),
		dEdi01 : $('#dEdi01').val(),
		dEdi02 : $('#dEdi02').val(),
		dEdi03 : $('#dEdi03').val(),
		dEdi04 : $('#dEdi04').val(),
		dEdi05 : $('#dEdi05').val(),
		dEdi06 : $('#dEdi06').val(),
		dEdi07 : $('#dEdi07').val(),
		dEdi08 : $('#dEdi08').val(),
		dEdi09 : $('#dEdi09').val(),
		dEdi10 : $('#dEdi10').val(),
		qtyreleased : $('#qtyreleased').val()
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
							<th>待输入名称0：</th><td><input type='text' id='pono' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称1：</th><td><input type='text' id='polineno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称2：</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称3：</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称4：</th><td><input type='text' id='skudescrc' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称5：</th><td><input type='text' id='skudescre' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称6：</th><td><input type='text' id='orderedqty' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称7：</th><td><input type='text' id='orderedqtyEach' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称8：</th><td><input type='text' id='receivedqty' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称9：</th><td><input type='text' id='receivedqtyEach' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称10：</th><td><input type='text' id='receivedtime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称11：</th><td><input type='text' id='uom' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称12：</th><td><input type='text' id='packid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称13：</th><td><input type='text' id='totalcubic' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称14：</th><td><input type='text' id='totalgrossweight' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称15：</th><td><input type='text' id='totalnetweight' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称16：</th><td><input type='text' id='totalprice' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称17：</th><td><input type='text' id='userdefine1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称18：</th><td><input type='text' id='userdefine2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称19：</th><td><input type='text' id='userdefine3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称20：</th><td><input type='text' id='userdefine4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称21：</th><td><input type='text' id='userdefine5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称22：</th><td><input type='text' id='addtime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称23：</th><td><input type='text' id='addwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称24：</th><td><input type='text' id='edittime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称25：</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称26：</th><td><input type='text' id='lotatt01' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称27：</th><td><input type='text' id='lotatt02' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称28：</th><td><input type='text' id='lotatt03' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称29：</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称30：</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称31：</th><td><input type='text' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称32：</th><td><input type='text' id='lotatt07' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称33：</th><td><input type='text' id='lotatt08' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称34：</th><td><input type='text' id='lotatt09' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称35：</th><td><input type='text' id='lotatt10' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称36：</th><td><input type='text' id='lotatt11' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称37：</th><td><input type='text' id='notes' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称38：</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称39：</th><td><input type='text' id='polinestatus' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称40：</th><td><input type='text' id='dEdi01' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称41：</th><td><input type='text' id='dEdi02' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称42：</th><td><input type='text' id='dEdi03' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称43：</th><td><input type='text' id='dEdi04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称44：</th><td><input type='text' id='dEdi05' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称45：</th><td><input type='text' id='dEdi06' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称46：</th><td><input type='text' id='dEdi07' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称47：</th><td><input type='text' id='dEdi08' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称48：</th><td><input type='text' id='dEdi09' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称49：</th><td><input type='text' id='dEdi10' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称50：</th><td><input type='text' id='qtyreleased' class='easyui-textbox' size='16' data-options=''/></td>
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
			<input type='hidden' id='docPoDetailsId' name='docPoDetailsId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='pono' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='polineno' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='skudescre' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='orderedqty' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='orderedqtyEach' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='receivedqty' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='receivedqtyEach' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='receivedtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='uom' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='packid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='totalcubic' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='totalgrossweight' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='totalnetweight' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入16</th>
					<td><input type='text' name='totalprice' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入17</th>
					<td><input type='text' name='userdefine1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入18</th>
					<td><input type='text' name='userdefine2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入19</th>
					<td><input type='text' name='userdefine3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入20</th>
					<td><input type='text' name='userdefine4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入21</th>
					<td><input type='text' name='userdefine5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入22</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入23</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入24</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入25</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入26</th>
					<td><input type='text' name='lotatt01' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入27</th>
					<td><input type='text' name='lotatt02' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入28</th>
					<td><input type='text' name='lotatt03' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入29</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入30</th>
					<td><input type='text' name='lotatt05' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入31</th>
					<td><input type='text' name='lotatt06' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入32</th>
					<td><input type='text' name='lotatt07' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入33</th>
					<td><input type='text' name='lotatt08' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入34</th>
					<td><input type='text' name='lotatt09' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入35</th>
					<td><input type='text' name='lotatt10' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入36</th>
					<td><input type='text' name='lotatt11' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入37</th>
					<td><input type='text' name='notes' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入38</th>
					<td><input type='text' name='lotatt12' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入39</th>
					<td><input type='text' name='polinestatus' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入40</th>
					<td><input type='text' name='dEdi01' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入41</th>
					<td><input type='text' name='dEdi02' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入42</th>
					<td><input type='text' name='dEdi03' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入43</th>
					<td><input type='text' name='dEdi04' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入44</th>
					<td><input type='text' name='dEdi05' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入45</th>
					<td><input type='text' name='dEdi06' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入46</th>
					<td><input type='text' name='dEdi07' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入47</th>
					<td><input type='text' name='dEdi08' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入48</th>
					<td><input type='text' name='dEdi09' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入49</th>
					<td><input type='text' name='dEdi10' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入50</th>
					<td><input type='text' name='qtyreleased' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
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
