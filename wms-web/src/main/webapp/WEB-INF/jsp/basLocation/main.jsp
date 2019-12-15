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

var ezuiImportDataDialog;
var ezuiImportDataForm;

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/basLocationController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: false,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'id',
		columns : [[
			{field: 'locationid',		title: '库位 ',	width: 120 },
			{field: 'picklogicalsequence',		title: '拣货顺序',	width: 100 },
			{field: 'locationusage',		title: '库位使用',	width: 100, formatter:function(value,rowData,rowIndex){
				return rowData.locationusageName;
            }},
			{field: 'locationcategory',		title: '库位类型',	width: 100, formatter:function(value,rowData,rowIndex){
				return rowData.locationcategoryName;
            }},
			{field: 'logicalsequence',		title: '上架顺序 ',	width: 100 },
			{field: 'locationattribute',		title: '库位属性',	width: 100, formatter:function(value,rowData,rowIndex){
				return rowData.locationattributeName;
            }},
			{field: 'locationhandling',		title: '库位处理',	width: 100 , formatter:function(value,rowData,rowIndex){
				return rowData.locationhandlingName;
            }},
			{field: 'demand',		title: '周转需求',	width: 100 , formatter:function(value,rowData,rowIndex){
				return rowData.demandName;
            }},
			{field: 'putawayzone',		title: '上架区',	width: 100 , formatter:function(value,rowData,rowIndex){
				return rowData.putawayzoneName;
            }},
			{field: 'pickzone',		title: '拣货区',	width: 100 , formatter:function(value,rowData,rowIndex){
				return rowData.pickzoneName;
            }},
			{field: 'cubiccapacity',		title: '体积限制',	width: 60 },
			{field: 'weightcapacity',		title: '重量限制',	width: 60 },
			{field: 'cscount',		title: '箱数限制',	width: 60 },
			{field: 'countcapacity',		title: '数量限制',	width: 60 },
			{field: 'plcount',		title: '托盘限制',	width: 60 },
			{field: 'mixFlag',		title: '允许混放产品',	width: 80, formatter:function(value,rowData,rowIndex){
				return rowData.mixFlag == 'Y' ? '是' : '否';
            }},
			{field: 'mixLotflag',		title: '允许混放批次',	width: 80, formatter:function(value,rowData,rowIndex){
				return rowData.mixLotflag == 'Y' ? '是' : '否';
            }},
			{field: 'loseidFlag',		title: '忽略ID',	width: 60 , formatter:function(value,rowData,rowIndex){
				return rowData.loseidFlag == 'Y' ? '是' : '否';
            }},
			{field: 'length',		title: '长度',	width: 45 },
			{field: 'width',		title: '宽度',	width: 45 },
			{field: 'height',		title: '高度',	width: 45 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/basLocationController.do?getBtn"/>', ezuiMenu);
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
	
	//导入
	ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
		modal : true,
		title : '导入',
		buttons : '#ezuiImportDataDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiImportDataForm);
		}
	}).dialog('close');
});

var add = function(){
	processType = 'add';
	$('#basLocationId').val(0);
	$("#ezuiForm #locationusage").combobox('setValue','EA'); //库位使用
	$("#ezuiForm #locationcategory").combobox('setValue','RK'); //库位类型
	$("#ezuiForm #locationattribute").combobox('setValue','OK'); //库位属性
	$("#ezuiForm #locationhandling").combobox('setValue','OT'); //库位处理
	$("#ezuiForm #demand").combobox('setValue','A'); //周转需求
	$("#ezuiForm #mixFlag").combobox('setValue','Y');//允许混放产品
	$("#ezuiForm #mixLotflag").combobox('setValue','Y');//允许混放批次
	$("#ezuiForm #loseidFlag").combobox('setValue','Y');//忽略ID
	$("#ezuiForm #cubiccapacity").textbox("setValue",0);
	$("#ezuiForm #weightcapacity").textbox("setValue",0);
	$("#ezuiForm #cscount").textbox("setValue",0);
	$("#ezuiForm #countcapacity").textbox("setValue",0);
	$("#ezuiForm #plcount").textbox("setValue",0);
	$("#ezuiForm #length").textbox("setValue",0);
	$("#ezuiForm #width").textbox("setValue",0);
	$("#ezuiForm #height").textbox("setValue",0);
	$("#ezuiForm #locationid").textbox({
		editable:true
	});
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		
		$("#ezuiForm #locationid").textbox({
			editable:false
		});
		
		ezuiForm.form('load',{
			locationid : row.locationid,
			addtime : row.addtime,
			addwho : row.addwho,
			countcapacity : row.countcapacity,
			cscount : row.cscount,
			cubiccapacity : row.cubiccapacity,
			demand : row.demand,
			edittime : row.edittime,
			editwho : row.editwho,
			environment : row.environment,
			facilityId : row.facilityId,
			height : row.height,
			length : row.length,
			locationattribute : row.locationattribute,
			locationcategory : row.locationcategory,
			locationhandling : row.locationhandling,
			locationusage : row.locationusage,
			locgroup1 : row.locgroup1,
			locgroup2 : row.locgroup2,
			loclevel : row.loclevel,
			logicalsequence : row.logicalsequence,
			loseidFlag : row.loseidFlag,
			mixFlag : row.mixFlag,
			mixLotflag : row.mixLotflag,
			picklogicalsequence : row.picklogicalsequence,
			pickzone : row.pickzone,
			plcount : row.plcount,
			putawayzone : row.putawayzone,
			status : row.status,
			weightcapacity : row.weightcapacity,
			width : row.width,
			xcoord : row.xcoord,
			xdistance : row.xdistance,
			ycoord : row.ycoord,
			ydistance : row.ydistance,
			zcoord : row.zcoord
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
					url : 'basLocationController.do?delete',
					data : {locationid : row.locationid},
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
		url = '<c:url value="/basLocationController.do?edit"/>';
	}else{
		url = '<c:url value="/basLocationController.do?add"/>';
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
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		locationid : $('#locationid').val(),
		locationusage : $('#locationusage').combobox('getValue'),
		locationcategory : $('#locationcategory').combobox('getValue'),
		locationattribute : $('#locationattribute').combobox('getValue'),
		locationhandling : $('#locationhandling').combobox('getValue'),
		demand : $('#demand').combobox('getValue'),
		putawayzone : $('#putawayzone').combobox('getValue'),
		pickzone : $('#pickzone').combobox('getValue')
	});
};

//导出start
var doExport = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		param.put("locationid", $('#locationid').val());
		param.put("locationusage", $('#locationusage').combobox('getValue'));
		param.put("locationcategory", $('#locationcategory').combobox('getValue'));
		param.put("locationattribute", $('#locationattribute').combobox('getValue'));
		param.put("locationhandling", $('#locationhandling').combobox('getValue'));
		param.put("demand", $('#demand').combobox('getValue'));
		param.put("putawayzone", $('#putawayzone').combobox('getValue'));
		param.put("pickzone", $('#pickzone').combobox('getValue'));
		var formId = ajaxDownloadFile(sy.bp()+"/basLocationController.do?exportLocationDataToExcel", param);
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
		}, 1000);
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};
//导出end
	
//导入start
var commitImportData = function(obj){
	//alert('cs1');
	ezuiImportDataForm.form('submit', {
		url : '<c:url value="/basLocationController.do?importExcelData"/>',
		onSubmit : function(){
			//alert('cs2');
			if(ezuiImportDataForm.form('validate')){
				//alert('cs3');
				$.messager.progress({
					text : '<spring:message code="common.message.data.processing"/>', interval : 100
				});
				return true;
			}else{
				//alert('cs4');
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

//下载导入模板
var downloadTemplate = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_downloadTemplate').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		var formId = ajaxDownloadFile(sy.bp()+"/basLocationController.do?exportTemplate", param);
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
//导入end

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
							<th>库位：</th>
							<td><input type='text' id='locationid' name='locationid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>库位使用：</th>
							<td><input type='text' id='locationusage' name='locationusage' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getUsgTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
							<th>库位类型：</th>
							<td><input type='text' id='locationcategory' name='locationcategory' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getCatTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
							<th>库位属性：</th>
							<td><input type='text' id='locationattribute' name='locationattribute' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getAttTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
						</tr>
						<tr>
							<th>库位处理：</th>
							<td><input type='text' id='locationhandling' name='locationhandling' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getHdlTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
                            <th>周转需求：</th>
                            <td><input type='text' id='demand' name='demand' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getDmdTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
							<th>上架区：</th>
							<td><input type='text' id='putawayzone' name='putawayzone' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getPtzoneTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
							<th>拣货区：</th>
							<td><input type='text' id='pickzone' name='pickzone' class='easyui-combobox' size='16' data-options="required:false,
																												                          panelHeight:'auto',
																													                      editable:false,
																														                  url:'<c:url value="/basLocationController.do?getPizoneTypeCombobox"/>',
																														                  valueField: 'id',
																														                  textField: 'value'"/></td>
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
<%--					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='basLocationId' name='basLocationId'/>
			<table>
				<tr>
					<th>库位编码</th>
					<td><input type='text' id='locationid' name='locationid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>上架顺序</th>
					<td><input type='text' id='logicalsequence' name='logicalsequence' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>拣货顺序</th>
					<td><input type='text' id='picklogicalsequence' name='picklogicalsequence' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
				    <th>库位使用</th>
					<td><input type='text' id='locationusage' name='locationusage' class='easyui-combobox' size='16' data-options="required:true,
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getUsgTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
					<th>库位类型</th>
					<td><input type='text' id='locationcategory' name='locationcategory' class='easyui-combobox' size='16' data-options="required:true,
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getCatTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
					<th>库位属性</th>
					<td><input type='text' id='locationattribute' name='locationattribute' class='easyui-combobox' size='16' data-options="required:true,
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getAttTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
				</tr>
				<tr>
				    <th>库位处理</th>
					<td><input type='text' id='locationhandling' name='locationhandling' class='easyui-combobox' size='16' data-options="required:true,
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getHdlTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
					<th>周转需求</th>
					<td><input type='text' id='demand' name='demand' class='easyui-combobox' size='16' data-options="required:true,
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getDmdTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
					<th>上架区</th>
					<td><input type='text' id='putawayzone' name='putawayzone' class='easyui-combobox' size='16' data-options="
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getPtzoneTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
				</tr>
				<tr>
				    <th>拣货区</th>
					<td><input type='text' id='pickzone' name='pickzone' class='easyui-combobox' size='16' data-options="
																												panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getPizoneTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
					<th>体积限制</th>
					<td><input type='text' id='cubiccapacity' name='cubiccapacity' class='easyui-textbox' size='16' data-options='required:false'/></td>
				    <th>重量限制</th>
					<td><input type='text' id='weightcapacity' name='weightcapacity' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
				    <th>箱数限制</th>
					<td><input type='text' id='cscount' name='cscount' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>数量限制</th>
					<td><input type='text' id='countcapacity' name='countcapacity' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>托盘限制</th>
					<td><input type='text' id='plcount' name='plcount' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>长度</th>
					<td><input type='text' id='length' name='length' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>宽度</th>
					<td><input type='text' id='width' name='width' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>高度</th>
					<td><input type='text' id='height' name='height' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
				    <th>允许混放产品</th>
					<td><input type='text' id='mixFlag' name='mixFlag' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																										 editable:false,
																										 valueField: 'id',
																										 textField: 'value',
																										data: [
																											    {id: 'Y', value: '是'}, 
																											    {id: 'N', value: '否'}
																										      ]"/></td>
				    <th>允许混放批次</th>
					<td><input type='text' id='mixLotflag' name='mixLotflag' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																										 editable:false,
																										 valueField: 'id',
																										 textField: 'value',
																										data: [
																											    {id: 'Y', value: '是'}, 
																											    {id: 'N', value: '否'}
																										      ]"/></td>
					<th>忽略ID</th>
					<td><input type='text' id='loseidFlag' name='loseidFlag' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																										 editable:false,
																										 valueField: 'id',
																										 textField: 'value',
																										data: [
																											    {id: 'Y', value: '是'}, 
																											    {id: 'N', value: '否'}
																										      ]"/></td>
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
