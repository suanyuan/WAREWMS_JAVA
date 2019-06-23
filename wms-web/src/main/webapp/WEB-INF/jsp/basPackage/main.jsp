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
		url : '<c:url value="/basPackageController.do?showDatagrid"/>',
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
			{field: 'hashCode',		title: '待输入栏位0',	width: 9 },
			{field: 'packid',		title: '待输入栏位1',	width: 9 },
			{field: 'addtime',		title: '待输入栏位2',	width: 9 },
			{field: 'addwho',		title: '待输入栏位3',	width: 9 },
			{field: 'cartonizeuom1',		title: '待输入栏位4',	width: 9 },
			{field: 'cartonizeuom2',		title: '待输入栏位5',	width: 9 },
			{field: 'cartonizeuom3',		title: '待输入栏位6',	width: 9 },
			{field: 'cartonizeuom4',		title: '待输入栏位7',	width: 9 },
			{field: 'cartonizeuom5',		title: '待输入栏位8',	width: 9 },
			{field: 'cubeuom1',		title: '待输入栏位9',	width: 9 },
			{field: 'cubeuom2',		title: '待输入栏位10',	width: 9 },
			{field: 'cubeuom3',		title: '待输入栏位11',	width: 9 },
			{field: 'cubeuom4',		title: '待输入栏位12',	width: 9 },
			{field: 'cubeuom5',		title: '待输入栏位13',	width: 9 },
			{field: 'descr',		title: '待输入栏位14',	width: 9 },
			{field: 'descr1',		title: '待输入栏位15',	width: 9 },
			{field: 'descr2',		title: '待输入栏位16',	width: 9 },
			{field: 'descr3',		title: '待输入栏位17',	width: 9 },
			{field: 'descr4',		title: '待输入栏位18',	width: 9 },
			{field: 'descr5',		title: '待输入栏位19',	width: 9 },
			{field: 'edittime',		title: '待输入栏位20',	width: 9 },
			{field: 'editwho',		title: '待输入栏位21',	width: 9 },
			{field: 'heightuom1',		title: '待输入栏位22',	width: 9 },
			{field: 'heightuom2',		title: '待输入栏位23',	width: 9 },
			{field: 'heightuom3',		title: '待输入栏位24',	width: 9 },
			{field: 'heightuom4',		title: '待输入栏位25',	width: 9 },
			{field: 'heightuom5',		title: '待输入栏位26',	width: 9 },
			{field: 'inLabel1',		title: '待输入栏位27',	width: 9 },
			{field: 'inLabel2',		title: '待输入栏位28',	width: 9 },
			{field: 'inLabel3',		title: '待输入栏位29',	width: 9 },
			{field: 'inLabel4',		title: '待输入栏位30',	width: 9 },
			{field: 'inLabel5',		title: '待输入栏位31',	width: 9 },
			{field: 'lengthuom1',		title: '待输入栏位32',	width: 9 },
			{field: 'lengthuom2',		title: '待输入栏位33',	width: 9 },
			{field: 'lengthuom3',		title: '待输入栏位34',	width: 9 },
			{field: 'lengthuom4',		title: '待输入栏位35',	width: 9 },
			{field: 'lengthuom5',		title: '待输入栏位36',	width: 9 },
			{field: 'outLabel1',		title: '待输入栏位37',	width: 9 },
			{field: 'outLabel2',		title: '待输入栏位38',	width: 9 },
			{field: 'outLabel3',		title: '待输入栏位39',	width: 9 },
			{field: 'outLabel4',		title: '待输入栏位40',	width: 9 },
			{field: 'outLabel5',		title: '待输入栏位41',	width: 9 },
			{field: 'packMaterial2',		title: '待输入栏位42',	width: 9 },
			{field: 'packmaterial1',		title: '待输入栏位43',	width: 9 },
			{field: 'packmaterial2',		title: '待输入栏位44',	width: 9 },
			{field: 'packmaterial3',		title: '待输入栏位45',	width: 9 },
			{field: 'packmaterial4',		title: '待输入栏位46',	width: 9 },
			{field: 'packmaterial5',		title: '待输入栏位47',	width: 9 },
			{field: 'packuom1',		title: '待输入栏位48',	width: 9 },
			{field: 'packuom2',		title: '待输入栏位49',	width: 9 },
			{field: 'packuom3',		title: '待输入栏位50',	width: 9 },
			{field: 'packuom4',		title: '待输入栏位51',	width: 9 },
			{field: 'packuom5',		title: '待输入栏位52',	width: 9 },
			{field: 'pallethi',		title: '待输入栏位53',	width: 9 },
			{field: 'palletti',		title: '待输入栏位54',	width: 9 },
			{field: 'palletwoodheight',		title: '待输入栏位55',	width: 9 },
			{field: 'palletwoodlength',		title: '待输入栏位56',	width: 9 },
			{field: 'palletwoodwidth',		title: '待输入栏位57',	width: 9 },
			{field: 'qtyMaterial2',		title: '待输入栏位58',	width: 9 },
			{field: 'qty1',		title: '待输入栏位59',	width: 9 },
			{field: 'qty2',		title: '待输入栏位60',	width: 9 },
			{field: 'qty3',		title: '待输入栏位61',	width: 9 },
			{field: 'qty4',		title: '待输入栏位62',	width: 9 },
			{field: 'qty5',		title: '待输入栏位63',	width: 9 },
			{field: 'rplLabel1',		title: '待输入栏位64',	width: 9 },
			{field: 'rplLabel2',		title: '待输入栏位65',	width: 9 },
			{field: 'rplLabel3',		title: '待输入栏位66',	width: 9 },
			{field: 'rplLabel4',		title: '待输入栏位67',	width: 9 },
			{field: 'sn1',		title: '待输入栏位68',	width: 9 },
			{field: 'sn2',		title: '待输入栏位69',	width: 9 },
			{field: 'sn3',		title: '待输入栏位70',	width: 9 },
			{field: 'weightuom1',		title: '待输入栏位71',	width: 9 },
			{field: 'weightuom2',		title: '待输入栏位72',	width: 9 },
			{field: 'weightuom3',		title: '待输入栏位73',	width: 9 },
			{field: 'weightuom4',		title: '待输入栏位74',	width: 9 },
			{field: 'weightuom5',		title: '待输入栏位75',	width: 9 },
			{field: 'widthuom1',		title: '待输入栏位76',	width: 9 },
			{field: 'widthuom2',		title: '待输入栏位77',	width: 9 },
			{field: 'widthuom3',		title: '待输入栏位78',	width: 9 },
			{field: 'widthuom4',		title: '待输入栏位79',	width: 9 },
			{field: 'widthuom5',		title: '待输入栏位80',	width: 9 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/basPackageController.do?getBtn"/>', ezuiMenu);
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
	$('#basPackageId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			hashCode : row.hashCode,
			packid : row.packid,
			addtime : row.addtime,
			addwho : row.addwho,
			cartonizeuom1 : row.cartonizeuom1,
			cartonizeuom2 : row.cartonizeuom2,
			cartonizeuom3 : row.cartonizeuom3,
			cartonizeuom4 : row.cartonizeuom4,
			cartonizeuom5 : row.cartonizeuom5,
			cubeuom1 : row.cubeuom1,
			cubeuom2 : row.cubeuom2,
			cubeuom3 : row.cubeuom3,
			cubeuom4 : row.cubeuom4,
			cubeuom5 : row.cubeuom5,
			descr : row.descr,
			descr1 : row.descr1,
			descr2 : row.descr2,
			descr3 : row.descr3,
			descr4 : row.descr4,
			descr5 : row.descr5,
			edittime : row.edittime,
			editwho : row.editwho,
			heightuom1 : row.heightuom1,
			heightuom2 : row.heightuom2,
			heightuom3 : row.heightuom3,
			heightuom4 : row.heightuom4,
			heightuom5 : row.heightuom5,
			inLabel1 : row.inLabel1,
			inLabel2 : row.inLabel2,
			inLabel3 : row.inLabel3,
			inLabel4 : row.inLabel4,
			inLabel5 : row.inLabel5,
			lengthuom1 : row.lengthuom1,
			lengthuom2 : row.lengthuom2,
			lengthuom3 : row.lengthuom3,
			lengthuom4 : row.lengthuom4,
			lengthuom5 : row.lengthuom5,
			outLabel1 : row.outLabel1,
			outLabel2 : row.outLabel2,
			outLabel3 : row.outLabel3,
			outLabel4 : row.outLabel4,
			outLabel5 : row.outLabel5,
			packMaterial2 : row.packMaterial2,
			packmaterial1 : row.packmaterial1,
			packmaterial2 : row.packmaterial2,
			packmaterial3 : row.packmaterial3,
			packmaterial4 : row.packmaterial4,
			packmaterial5 : row.packmaterial5,
			packuom1 : row.packuom1,
			packuom2 : row.packuom2,
			packuom3 : row.packuom3,
			packuom4 : row.packuom4,
			packuom5 : row.packuom5,
			pallethi : row.pallethi,
			palletti : row.palletti,
			palletwoodheight : row.palletwoodheight,
			palletwoodlength : row.palletwoodlength,
			palletwoodwidth : row.palletwoodwidth,
			qtyMaterial2 : row.qtyMaterial2,
			qty1 : row.qty1,
			qty2 : row.qty2,
			qty3 : row.qty3,
			qty4 : row.qty4,
			qty5 : row.qty5,
			rplLabel1 : row.rplLabel1,
			rplLabel2 : row.rplLabel2,
			rplLabel3 : row.rplLabel3,
			rplLabel4 : row.rplLabel4,
			sn1 : row.sn1,
			sn2 : row.sn2,
			sn3 : row.sn3,
			weightuom1 : row.weightuom1,
			weightuom2 : row.weightuom2,
			weightuom3 : row.weightuom3,
			weightuom4 : row.weightuom4,
			weightuom5 : row.weightuom5,
			widthuom1 : row.widthuom1,
			widthuom2 : row.widthuom2,
			widthuom3 : row.widthuom3,
			widthuom4 : row.widthuom4,
			widthuom5 : row.widthuom5
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
					url : 'basPackageController.do?delete',
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
		url = '<c:url value="/basPackageController.do?edit"/>';
	}else{
		url = '<c:url value="/basPackageController.do?add"/>';
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
		hashCode : $('#hashCode').val(),
		packid : $('#packid').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		cartonizeuom1 : $('#cartonizeuom1').val(),
		cartonizeuom2 : $('#cartonizeuom2').val(),
		cartonizeuom3 : $('#cartonizeuom3').val(),
		cartonizeuom4 : $('#cartonizeuom4').val(),
		cartonizeuom5 : $('#cartonizeuom5').val(),
		cubeuom1 : $('#cubeuom1').val(),
		cubeuom2 : $('#cubeuom2').val(),
		cubeuom3 : $('#cubeuom3').val(),
		cubeuom4 : $('#cubeuom4').val(),
		cubeuom5 : $('#cubeuom5').val(),
		descr : $('#descr').val(),
		descr1 : $('#descr1').val(),
		descr2 : $('#descr2').val(),
		descr3 : $('#descr3').val(),
		descr4 : $('#descr4').val(),
		descr5 : $('#descr5').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val(),
		heightuom1 : $('#heightuom1').val(),
		heightuom2 : $('#heightuom2').val(),
		heightuom3 : $('#heightuom3').val(),
		heightuom4 : $('#heightuom4').val(),
		heightuom5 : $('#heightuom5').val(),
		inLabel1 : $('#inLabel1').val(),
		inLabel2 : $('#inLabel2').val(),
		inLabel3 : $('#inLabel3').val(),
		inLabel4 : $('#inLabel4').val(),
		inLabel5 : $('#inLabel5').val(),
		lengthuom1 : $('#lengthuom1').val(),
		lengthuom2 : $('#lengthuom2').val(),
		lengthuom3 : $('#lengthuom3').val(),
		lengthuom4 : $('#lengthuom4').val(),
		lengthuom5 : $('#lengthuom5').val(),
		outLabel1 : $('#outLabel1').val(),
		outLabel2 : $('#outLabel2').val(),
		outLabel3 : $('#outLabel3').val(),
		outLabel4 : $('#outLabel4').val(),
		outLabel5 : $('#outLabel5').val(),
		packMaterial2 : $('#packMaterial2').val(),
		packmaterial1 : $('#packmaterial1').val(),
		packmaterial2 : $('#packmaterial2').val(),
		packmaterial3 : $('#packmaterial3').val(),
		packmaterial4 : $('#packmaterial4').val(),
		packmaterial5 : $('#packmaterial5').val(),
		packuom1 : $('#packuom1').val(),
		packuom2 : $('#packuom2').val(),
		packuom3 : $('#packuom3').val(),
		packuom4 : $('#packuom4').val(),
		packuom5 : $('#packuom5').val(),
		pallethi : $('#pallethi').val(),
		palletti : $('#palletti').val(),
		palletwoodheight : $('#palletwoodheight').val(),
		palletwoodlength : $('#palletwoodlength').val(),
		palletwoodwidth : $('#palletwoodwidth').val(),
		qtyMaterial2 : $('#qtyMaterial2').val(),
		qty1 : $('#qty1').val(),
		qty2 : $('#qty2').val(),
		qty3 : $('#qty3').val(),
		qty4 : $('#qty4').val(),
		qty5 : $('#qty5').val(),
		rplLabel1 : $('#rplLabel1').val(),
		rplLabel2 : $('#rplLabel2').val(),
		rplLabel3 : $('#rplLabel3').val(),
		rplLabel4 : $('#rplLabel4').val(),
		sn1 : $('#sn1').val(),
		sn2 : $('#sn2').val(),
		sn3 : $('#sn3').val(),
		weightuom1 : $('#weightuom1').val(),
		weightuom2 : $('#weightuom2').val(),
		weightuom3 : $('#weightuom3').val(),
		weightuom4 : $('#weightuom4').val(),
		weightuom5 : $('#weightuom5').val(),
		widthuom1 : $('#widthuom1').val(),
		widthuom2 : $('#widthuom2').val(),
		widthuom3 : $('#widthuom3').val(),
		widthuom4 : $('#widthuom4').val(),
		widthuom5 : $('#widthuom5').val()
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
							<th>待输入名称1：</th><td><input type='text' id='hashCode' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称2：</th><td><input type='text' id='packid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称3：</th><td><input type='text' id='addtime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称4：</th><td><input type='text' id='addwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称5：</th><td><input type='text' id='cartonizeuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称6：</th><td><input type='text' id='cartonizeuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称7：</th><td><input type='text' id='cartonizeuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称8：</th><td><input type='text' id='cartonizeuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称9：</th><td><input type='text' id='cartonizeuom5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称10：</th><td><input type='text' id='cubeuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称11：</th><td><input type='text' id='cubeuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称12：</th><td><input type='text' id='cubeuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称13：</th><td><input type='text' id='cubeuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称14：</th><td><input type='text' id='cubeuom5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称15：</th><td><input type='text' id='descr' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称16：</th><td><input type='text' id='descr1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称17：</th><td><input type='text' id='descr2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称18：</th><td><input type='text' id='descr3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称19：</th><td><input type='text' id='descr4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称20：</th><td><input type='text' id='descr5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称21：</th><td><input type='text' id='edittime' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称22：</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称23：</th><td><input type='text' id='heightuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称24：</th><td><input type='text' id='heightuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称25：</th><td><input type='text' id='heightuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称26：</th><td><input type='text' id='heightuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称27：</th><td><input type='text' id='heightuom5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称28：</th><td><input type='text' id='inLabel1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称29：</th><td><input type='text' id='inLabel2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称30：</th><td><input type='text' id='inLabel3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称31：</th><td><input type='text' id='inLabel4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称32：</th><td><input type='text' id='inLabel5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称33：</th><td><input type='text' id='lengthuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称34：</th><td><input type='text' id='lengthuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称35：</th><td><input type='text' id='lengthuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称36：</th><td><input type='text' id='lengthuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称37：</th><td><input type='text' id='lengthuom5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称38：</th><td><input type='text' id='outLabel1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称39：</th><td><input type='text' id='outLabel2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称40：</th><td><input type='text' id='outLabel3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称41：</th><td><input type='text' id='outLabel4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称42：</th><td><input type='text' id='outLabel5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称43：</th><td><input type='text' id='packMaterial2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称44：</th><td><input type='text' id='packmaterial1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称45：</th><td><input type='text' id='packmaterial2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称46：</th><td><input type='text' id='packmaterial3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称47：</th><td><input type='text' id='packmaterial4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称48：</th><td><input type='text' id='packmaterial5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称49：</th><td><input type='text' id='packuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称50：</th><td><input type='text' id='packuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称51：</th><td><input type='text' id='packuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称52：</th><td><input type='text' id='packuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称53：</th><td><input type='text' id='packuom5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称54：</th><td><input type='text' id='pallethi' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称55：</th><td><input type='text' id='palletti' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称56：</th><td><input type='text' id='palletwoodheight' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称57：</th><td><input type='text' id='palletwoodlength' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称58：</th><td><input type='text' id='palletwoodwidth' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称59：</th><td><input type='text' id='qtyMaterial2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称60：</th><td><input type='text' id='qty1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称61：</th><td><input type='text' id='qty2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称62：</th><td><input type='text' id='qty3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称63：</th><td><input type='text' id='qty4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称64：</th><td><input type='text' id='qty5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称65：</th><td><input type='text' id='rplLabel1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称66：</th><td><input type='text' id='rplLabel2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称67：</th><td><input type='text' id='rplLabel3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称68：</th><td><input type='text' id='rplLabel4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称69：</th><td><input type='text' id='sn1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称70：</th><td><input type='text' id='sn2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称71：</th><td><input type='text' id='sn3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称72：</th><td><input type='text' id='weightuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称73：</th><td><input type='text' id='weightuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称74：</th><td><input type='text' id='weightuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称75：</th><td><input type='text' id='weightuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称76：</th><td><input type='text' id='weightuom5' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称77：</th><td><input type='text' id='widthuom1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称78：</th><td><input type='text' id='widthuom2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称79：</th><td><input type='text' id='widthuom3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称80：</th><td><input type='text' id='widthuom4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>待输入名称81：</th><td><input type='text' id='widthuom5' class='easyui-textbox' size='16' data-options=''/></td>
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
			<input type='hidden' id='basPackageId' name='basPackageId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='hashCode' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='packid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='cartonizeuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='cartonizeuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='cartonizeuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='cartonizeuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='cartonizeuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='cubeuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='cubeuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='cubeuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='cubeuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='cubeuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='descr' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='descr1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入16</th>
					<td><input type='text' name='descr2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入17</th>
					<td><input type='text' name='descr3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入18</th>
					<td><input type='text' name='descr4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入19</th>
					<td><input type='text' name='descr5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入20</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入21</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入22</th>
					<td><input type='text' name='heightuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入23</th>
					<td><input type='text' name='heightuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入24</th>
					<td><input type='text' name='heightuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入25</th>
					<td><input type='text' name='heightuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入26</th>
					<td><input type='text' name='heightuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入27</th>
					<td><input type='text' name='inLabel1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入28</th>
					<td><input type='text' name='inLabel2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入29</th>
					<td><input type='text' name='inLabel3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入30</th>
					<td><input type='text' name='inLabel4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入31</th>
					<td><input type='text' name='inLabel5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入32</th>
					<td><input type='text' name='lengthuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入33</th>
					<td><input type='text' name='lengthuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入34</th>
					<td><input type='text' name='lengthuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入35</th>
					<td><input type='text' name='lengthuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入36</th>
					<td><input type='text' name='lengthuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入37</th>
					<td><input type='text' name='outLabel1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入38</th>
					<td><input type='text' name='outLabel2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入39</th>
					<td><input type='text' name='outLabel3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入40</th>
					<td><input type='text' name='outLabel4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入41</th>
					<td><input type='text' name='outLabel5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入42</th>
					<td><input type='text' name='packMaterial2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入43</th>
					<td><input type='text' name='packmaterial1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入44</th>
					<td><input type='text' name='packmaterial2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入45</th>
					<td><input type='text' name='packmaterial3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入46</th>
					<td><input type='text' name='packmaterial4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入47</th>
					<td><input type='text' name='packmaterial5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入48</th>
					<td><input type='text' name='packuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入49</th>
					<td><input type='text' name='packuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入50</th>
					<td><input type='text' name='packuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入51</th>
					<td><input type='text' name='packuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入52</th>
					<td><input type='text' name='packuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入53</th>
					<td><input type='text' name='pallethi' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入54</th>
					<td><input type='text' name='palletti' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入55</th>
					<td><input type='text' name='palletwoodheight' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入56</th>
					<td><input type='text' name='palletwoodlength' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入57</th>
					<td><input type='text' name='palletwoodwidth' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入58</th>
					<td><input type='text' name='qtyMaterial2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入59</th>
					<td><input type='text' name='qty1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入60</th>
					<td><input type='text' name='qty2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入61</th>
					<td><input type='text' name='qty3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入62</th>
					<td><input type='text' name='qty4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入63</th>
					<td><input type='text' name='qty5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入64</th>
					<td><input type='text' name='rplLabel1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入65</th>
					<td><input type='text' name='rplLabel2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入66</th>
					<td><input type='text' name='rplLabel3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入67</th>
					<td><input type='text' name='rplLabel4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入68</th>
					<td><input type='text' name='sn1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入69</th>
					<td><input type='text' name='sn2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入70</th>
					<td><input type='text' name='sn3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入71</th>
					<td><input type='text' name='weightuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入72</th>
					<td><input type='text' name='weightuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入73</th>
					<td><input type='text' name='weightuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入74</th>
					<td><input type='text' name='weightuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入75</th>
					<td><input type='text' name='weightuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入76</th>
					<td><input type='text' name='widthuom1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入77</th>
					<td><input type='text' name='widthuom2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入78</th>
					<td><input type='text' name='widthuom3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入79</th>
					<td><input type='text' name='widthuom4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入80</th>
					<td><input type='text' name='widthuom5' class='easyui-textbox' size='16' data-options='required:true'/></td>
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
