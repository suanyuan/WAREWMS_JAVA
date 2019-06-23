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

var ezuiCustDataDialog;
var ezuiCustDataDialogId;

var ezuiImportDataDialog;
var ezuiImportDataForm;

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/basSkuController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '产品档案',
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
			/* {field: 'instockDetail',		title: '打印操作',	width: 7,formatter:function(value,rowData,rowIndex){
				return "<a id='ezuiBtn_instockDetailTable'"+rowIndex+" onclick=\"instockDetail('"+rowData.basSkuPK.customerid+"','"+rowData.basSkuPK.sku+"')\" href='javascript:void(0);'>打印</a>";
			}}, */
			{field: 'customerid',		title: '客户代码',	width: 12/* ,formatter:function(value,rowData,rowIndex){
				return rowData.basSkuPK.customerid;
            } */},
			{field: 'sku',		title: '产品',	width: 24},
			{field: 'activeFlag',		title: '激活',	width: 12, formatter:function(value,rowData,rowIndex){
				return rowData.activeFlag == 'Y' ? '是' : '否';
            }},
			{field: 'descrC',		title: '中文名称',	width: 42 },
			{field: 'descrE',		title: '英文名称',	width: 30 },
			{field: 'alternateSku1',		title: '条形码',	width: 24 },
			{field: 'packid',		title: '包装代码',	width: 18 },
			{field: 'reservedfield01',		title: '单位',	width: 12 },
			{field: 'firstinbounddate',		title: '首次入库',	width: 27 },
			{field: 'cube',		title: '体积',	width: 12 },
			{field: 'grossweight',		title: '重量',	width: 12 },
			{field: 'price',		title: '单价',	width: 12 }
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
		},
		onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/basSkuController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});

	/* 控件初始化start */
	$("#customerid").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	/* easyUI textbox keyup事件用法 */
	/* $("input",$("#sku").next("span")).keyup(function(){
		$("#sku").textbox('setValue',this.value.toUpperCase());
	}); */
	/* 产品编码限定大写字母 */
	$("#sku").textbox('textbox').css('text-transform','uppercase');

	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onOpen : function() {
			
		},
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
	
	//资料弹框
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	
	//导入
	ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
		modal : true,
		title : '导入',
		buttons : '#ezuiImportDataDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiImportDataForm);
		}
	}).dialog('close');
	/* 控件初始化end */
});

/* 自动转大写 */
var toUpperFunction = function(){
	/* var x=document.getElementById("fname");
	x.value=x.value.toUpperCase(); */
	$("#sku").textbox('setValue',$("#sku").val().toUpperCase());
};

/* 新增 */
var add = function(){
	processType = 'add';
	$('#basSkuId').val(0);
	$("#ezuiForm #customerid").textbox({
		editable:false,
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataDialogClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	$("#ezuiForm #sku").textbox({
		editable:true
	}).textbox('textbox').css('text-transform','uppercase');
	$("#ezuiForm #activeFlag").combobox('setValue','Y').combobox('setText','是'); 
	$("#ezuiForm #packid").textbox({
		editable:false
	}).textbox('setValue','STANDARD');
	ezuiDialog.dialog('open');
};


/* 编辑 */
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$("#ezuiForm #customerid").textbox({
			editable:false,
			icons:[]
		});
		$("#ezuiForm #sku").textbox({
			editable:false
		});
		$("#ezuiForm #packid").textbox({
			editable:false
		});
		ezuiForm.form('load',{
			customerid : row.customerid/* row.basSkuPK.customerid */,
			sku : row.sku,
			descrC : row.descrC,
			descrE : row.descrE,
			packid : row.packid,
			activeFlag: row.activeFlag,
  			alternateSku1 : row.alternateSku1,
  			alternateSku2 : row.alternateSku2,
  			alternateSku3 : row.alternateSku3,
  			reservedfield01 : row.reservedfield01,
  			reservedfield02 : row.reservedfield02,
  			reservedfield03 : row.reservedfield03,
  			grossweight : row.grossweight,
  			cube : row.cube,
  			price : row.price
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
					url : 'basSkuController.do?delete',
					data : {customerid : row.customerid,sku : row.sku},
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
		url = '<c:url value="/basSkuController.do?edit"/>';
	}else{
		url = '<c:url value="/basSkuController.do?add"/>';
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
		activeFlag : $('#activeFlag').combobox('getValue'),
		customerid : $("#customerid").val(),
		sku : $("#sku").val()
	});
};

/* 打印 */
var instockDetail=function(customerid,sku){
	window.open(sy.bp()+"/basSkuController.do?exportPdf&customerid="+customerid+"&sku="+sku);
};

/* 货主弹框查询 */
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		/* customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue') */
	});
};

/* 货主弹框清空 */
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};

/* 货主弹框-1 */
var ezuiCustDataClick = function(){
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=Y&customerType=OW"/>',
		method:'POST',
		toolbar : '#ezuiCustToolbar',
		title: '客户档案',
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
		idField : 'customerid',
		columns : [[
					{field: 'customerid',	title: '客户代码',	width: 15},
					{field: 'descrC',		title: '中文名称',	width: 50},
					{field: 'descrE',		title: '英文名称',	width: 50},
					{field: 'customerTypeName',	title: '类型',	width: 15},
					{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
						return rowData.activeFlag == 'Y' ? '是' : '否';
		            }}
				]],
		onDblClickCell: function(index,field,value){
			selectCust();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','Y').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
/* 货主选择-1 */
var selectCust = function(){
	processType = 'selectCust';
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};

/* 货主弹框-2 */
var ezuiCustDataDialogClick = function(){
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=Y&customerType=OW"/>',
		method:'POST',
		toolbar : '#ezuiCustToolbar',
		title: '客户档案',
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
					{field: 'customerid',		title: '客户代码',	width: 15},
					{field: 'descrC',			title: '中文名称',	width: 50},
					{field: 'descrE',			title: '英文名称',	width: 50},
					{field: 'customerTypeName',	title: '类型',	width: 15},
					{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
						return rowData.activeFlag == 'Y' ? '是' : '否';
		            }}
				]],
		onDblClickCell: function(index,field,value){
			selectDialogCust();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','Y').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
/* 货主选择-2 */
var selectDialogCust = function(){
	/* processType = 'selectCust'; */
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDialog #customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};

/* 导出start */
var doExport = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		param.put("customerid", $('#customerid').val());
		param.put("sku", $('#sku').val());
		param.put("activeFlag", $('#activeFlag').combobox('getValue'));
		//--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/basSkuController.do?exportSkuDataToExcel", param);
		downloadCheckTimer = window.setInterval(function () {
			window.clearInterval(downloadCheckTimer);
			$('#'+formId).remove();
			$('#ezuiBtn_export').linkbutton('enable');
			$.messager.progress('close');
			$.messager.show({
				msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
			});
		}, 1000);
		//--导出csv
		/* var formId = ajaxDownloadFile(sy.bp()+"/basSkuController.do?exportSkuData", param);
		downloadCheckTimer = window.setInterval(function () {
			var list = new cookieList('exportToken');
			if (list.items() == token){
				window.clearInterval(downloadCheckTimer);
				list.clear();
				$('#'+formId).remove();
				$('#ezuiBtn_export').linkbutton('enable');
				$.messager.progress('close');
				$.messager.show({
					msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
				});
			}
		}, 1000); */
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};
/* 导出end */

/* 导入start */
var commitImportData = function(obj){
	ezuiImportDataForm.form('submit', {
		url : '<c:url value="/basSkuController.do?importExcelData"/>',
		onSubmit : function(){
			if(ezuiImportDataForm.form('validate')){
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
					msg = result.msg.replace(/ /g, '\n');
					ezuiDatagrid.datagrid('reload');
				}else{
					msg = result.msg.replace(/ /g, '\n');
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				ezuiFormClear(ezuiImportDataForm);
				$('#importResult').textbox('setValue',msg);
				$.messager.progress('close');
			}
		}
	});
};
var toImportData = function(){
	ezuiImportDataDialog.dialog('open');
};
/* 下载导入模板 */
var downloadTemplate = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_downloadTemplate').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		var formId = ajaxDownloadFile(sy.bp()+"/basSkuController.do?exportTemplate", param);
		downloadCheckTimer = window.setInterval(function () {
			var list = new cookieList('downloadToken');
			if (list.items() == token){
				window.clearInterval(downloadCheckTimer);
				list.clear();
				$('#'+formId).remove();
				$('#ezuiBtn_downloadTemplate').linkbutton('enable');
				$.messager.show({
					msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
				});
			}
		}, 1000);
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};
/* 导入end */
	
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
							<th>客户代码</th>
							<td><input type='text' id='customerid' name="customerid" class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品：</th>
							<td><input type='text' id='sku'  name="sku" class='easyui-textbox' size='16' /></td>
							<th>是否激活：</th><td>
							<input type="text" id="activeFlag"  name="activeFlag"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'}, 
																																	{id: 'N', value: '否'}
																																]"/> 
							
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
								<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>
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
	<div id='ezuiDialog' class='easyui-dialog'  style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='basSkuId' name='basSkuId'/>
			<table>
				<tr>
				<th>客户代码</th>
				<td><input type='text' name='customerid'  id='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				<th>产品</th>
				<td><input type='text' name='sku' id="sku" class='easyui-textbox' size='16' data-options='required:true'/></td>
				<th>是否激活</th>
				<td><input type='text' name='activeFlag' id="activeFlag" class='easyui-combobox' size='16' data-options="required:true,
																															panelHeight:'auto',
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
				<td colspan="3"><input type='text' name='descrC' class='easyui-textbox' size='44' data-options="required:true"/></td>
				</tr>
				<tr>
				<th>英文代码</th>
				<td colspan="3"><input type='text' name='descrE' class='easyui-textbox' size='44' /></td>
				</tr>
				<tr>
				<th>包装代码</th>
				<td><input type='text' name='packid'  id='packid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				<th>条形码</th>
				<td><input type='text' name='alternateSku1' class='easyui-textbox' size='16' /></td>
				<th>单位</th>
				<td><input type='text' name='reservedfield01' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
				<th>重量</th>
				<td><input type='text' name='grossweight' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
				<th>体积</th>
				<td><input type='text' name='cube' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
				<th>单价</th>
				<td><input type='text' name='price' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:2'/></td>
				</tr>
				<tr>
				<th>别名2</th>
				<td><input type='text' name='alternateSku2' class='easyui-textbox' size='16' /></td>
				<th>别名3</th>
				<td><input type='text' name='alternateSku3' class='easyui-textbox' size='16' /></td>
				</tr>
				<tr>
				<th>自定义2</th>
				<td><input type='text' name='reservedfield02' class='easyui-textbox' size='16' /></td>
				<th>自定义3</th>
				<td><input type='text' name='reservedfield03' class='easyui-textbox' size='16' /></td>
				<th>分配规则（D）</th>
				<td><input type='text' name='reservedfield03' class='easyui-textbox' size='16' /></td>
				</tr>
				<!-- <tr>
				<th>时间控件</th><td><input type='text' name='edittime' class='easyui-datebox' data-options='required:true,editable:false'/></td>
				<td><a href="#" id="ezuiBtn_Wms" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick='ezuiCustDataDialogClick();'>按钮控件</a></td>
				</tr> -->
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
	
	<!-- 客户选择弹框 -->
	<div id='ezuiCustDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
	<div class='easyui-layout' data-options='fit:true,border:false'>
	<div data-options="region:'center'">
		<div id='ezuiCustToolbar' class='datagrid-toolbar'   style=''>
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th><td>
								<input type='text' id='customerid' name="customerid" class='easyui-textbox'  size='12' data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th><td>
								<input type='text' id='customerType' name="customerType" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
								<th>激活：</th><td>
								<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'}, 
																																{id: 'N', value: '否'}
																															]"/></td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
	<div id='ezuiCustDialogBtn'> </div>
	</div>
		<table id='ezuiCustDataDialogId' ></table> 
	</div>
	</div>
	</div>
	<div id='ezuiCustDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	
	
	<!-- 导入start -->
	<div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>
		<form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>
			<table>	
				<tr>
					<th>档案</th>
					<td>
						<input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36" data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>
						<a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton' href='javascript:void(0);'>下载档案模版</a>
					</td>
				</tr>
				<tr>
					<th>执行结果</th>
					<td><input id='importResult' class="easyui-textbox" size='100' style="height:150px" data-options="editable:false,multiline:true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiImportDataDialogBtn'>
		<a onclick='commitImportData();' id='ezuiBtn_importDataCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiImportDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<!-- 导入end -->
	
</body>
</html>
