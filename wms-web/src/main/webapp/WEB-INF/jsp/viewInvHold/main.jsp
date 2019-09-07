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
		url : '<c:url value="/viewInvHoldController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '冻结管理',
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
			{field: 'warehouseid',		title: '仓库编码',	width: 71 },
			{field: 'inventoryholdid',		title: '冻结编号',	width: 91 },
			{field: 'customerid',		title: '客户编码',	width: 71 },
			{field: 'sku',		title: '商品编码',	width: 91 },
			{field: 'skudescrc',		title: '商品名称',	width: 151 },
			{field: 'locationid',		title: '库位',	width: 81 },
			{field: 'holdflag',		title: '是否冻结',	width: 71 },
			{field: 'fromtime',		title: '冻结时间',	width: 101 },
			{field: 'whoon',		title: '冻结操作人',	width: 71 },
			{field: 'offfromtime',		title: '释放时间',	width: 101 },
			{field: 'whooff',		title: '释放操作人',	width: 71 },
			{field: 'holdcodeName',		title: '冻结原因',	width: 71 },
			{field: 'reason',		title: '原因描述',	width: 101 },
			{field: 'lotatt01',		title: '生产日期',	width: 71 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 71 },
			{field: 'lotatt03',		title: '入库日期',	width: 71 },
			{field: 'lotatt04',		title: '库存状态',	width: 71 },
			{field: 'lotatt05',		title: '批属5',	width: 71 },
			{field: 'lotatt06',		title: '批属6',	width: 71 },
			{field: 'lotnum',		title: '批号',	width: 71 }
// 			{field: 'holdbyName',		title: '冻结方法',	width: 25 },
// 			{field: 'traceid',		title: '跟踪号',	width: 25 }
		]],
		onClickRow: function(index, data) {
			var row = $('#ezuiDatagrid').datagrid('getSelected');
			if (row){
				if (row.holdflag == 'Y') {
					$('#ezuiBtn_hold').linkbutton('disable');
					$('#ezuiBtn_release').linkbutton('enable');
				}else{
					$('#ezuiBtn_release').linkbutton('disable');
					$('#ezuiBtn_hold').linkbutton('enable');
				};
			};
		},
	});
	
	$('#fromtime').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$('#lotatt11').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$('#offfromtime').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$('#lotatt12').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	
	//查询条件货主字段初始化
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
	//货主查询弹框初始化
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});

//货主查询弹框弹出start=========================
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
	idField : 'id',
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
		},onLoadSuccess:function(data){
// 			ajaxBtn($('#menuId').val(), '<c:url value="/cosCommonAddressController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','Y').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		/* customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue') */
	});
};
//货主查询弹框选择按钮
var selectCust = function(){
	processType = 'selectCust';
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================

/* 查询条件清空按钮 */
var ezuiToolbarClear = function(){
	$("#fromtime").datetimebox({
		value:'1900-01-01 00:00'
	});
	$("#lotatt11").datetimebox({
		value:ordertimeDateTo(new Date())
	});
	$('#whoon').textbox('clear');
	$("#offfromtime").datetimebox('clear');
	$("#lotatt12").datetimebox('clear');
	$('#whooff').textbox('clear');
	$('#holdflag').combobox('clear');
	$('#holdcode').combobox('clear');
	$('#reason').textbox('clear');
	$('#customerid').textbox('clear');
	$('#sku').textbox('clear');
	$('#locationid').textbox('clear');
};

/* 获取起始日期 */
var ordertimeDate = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 00:00';
};
/* 获取结束日期 */
var ordertimeDateTo = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 23:59';
};

var hold = function(){
	processType = 'hold';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			customerid : row.customerid,
			customername : row.customername,
			fromtime : row.fromtime,
			holdby : row.holdby,
			holdbyName : row.holdbyName,
			holdcode : row.holdcode,
			holdcodeName : row.holdcodeName,
			holdflag : row.holdflag,
			inventoryholdid : row.inventoryholdid,
			locationid : row.locationid,
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
			lotatt12 : row.lotatt12,
			lotnum : row.lotnum,
			offfromtime : row.offfromtime,
			reason : row.reason,
			sku : row.sku,
			skudescrc : row.skudescrc,
			traceid : row.traceid,
			warehouseid : row.warehouseid,
			whooff : row.whooff,
			whoon : row.whoon
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

var release = function(){
	processType = 'release';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			customerid : row.customerid,
			customername : row.customername,
			fromtime : row.fromtime,
			holdby : row.holdby,
			holdbyName : row.holdbyName,
			holdcode : row.holdcode,
			holdcodeName : row.holdcodeName,
			holdflag : row.holdflag,
			inventoryholdid : row.inventoryholdid,
			locationid : row.locationid,
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
			lotatt12 : row.lotatt12,
			lotnum : row.lotnum,
			offfromtime : row.offfromtime,
			reason : row.reason,
			sku : row.sku,
			skudescrc : row.skudescrc,
			traceid : row.traceid,
			warehouseid : row.warehouseid,
			whooff : row.whooff,
			whoon : row.whoon
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

var commit = function(){
	var url = '';
	if (processType == 'hold') {
		url = '<c:url value="/viewInvHoldController.do?hold"/>';
	}else{
		url = '<c:url value="/viewInvHoldController.do?release"/>';
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
		fromtime : $('#fromtime').datetimebox('getValue'),
		lotatt11 : $('#lotatt11').datetimebox('getValue'),//fromtime_to
		whoon : $('#whoon').val(),
		offfromtime : $('#offfromtime').datetimebox('getValue'),
		lotatt12 : $('#lotatt12').datetimebox('getValue'),//offfromtime_to
		whooff : $('#whooff').val(),
		holdflag : $('#holdflag').combobox('getValue'),
		holdcode : $('#holdcode').combobox('getValue'),
		reason : $('#reason').val(),
		customerid : $('#customerid').val(),
		sku : $('#sku').val(),
		locationid : $('#locationid').val(),
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
							<th>冻结时间</th><td><input type='text' id='fromtime' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='lotatt11' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>冻结操作人</th><td><input type='text' id='whoon' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>释放时间</th><td><input type='text' id='offfromtime' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='lotatt12' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>释放操作人</th><td><input type='text' id='whooff' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>是否冻结</th><td><input type='text' id='holdflag' class='easyui-combobox' size='16' data-options="required:true,panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'Y', value: 'Y'}, 
																											{id: 'N', value: 'N'}
																										]"/></td>
							<th>冻结原因</th><td><input type='text' id='holdcode' class='easyui-combobox' size='16' data-options="required:true,panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'OK', value: '正常'}, 
																											{id: 'SP', value: '异常'},
																											{id: 'CS', value: '残损'}
																										]"/></td>
							<th>原因描述</th><td><input type='text' id='reason' class='easyui-textbox' size='16' data-options=''/></td>
							
						</tr>
						<tr>
							<th>客户编码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>商品编码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>库位</th><td><input type='text' id='locationid' class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='hold();' id='ezuiBtn_hold' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.hold'/></a>
					<a onclick='release();' id='ezuiBtn_release' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.release'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='viewInvLotattId' name='viewInvLotattId'/>
			<table>
				<tr>
					<th>冻结编号</th>
					<td><input type='text' name='inventoryholdid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>是否冻结</th>
					<td><input type='text' name=holdflag class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>冻结原因</th>
					<td><input type='text' name='holdcodeName' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>原因描述</th>
					<td><input type='text' name='reason' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				</tr>
				<tr>
					<th>仓库编码</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>客户编码</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>商品编码</th>
					<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>商品名称</th>
					<td><input type='text' name='skudescrc' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>库位</th>
					<td><input type='text' name='locationid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				</tr>
				<tr>
					<th>冻结时间</th>
					<td><input type='text' name='fromtime' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>冻结操作人</th>
					<td><input type='text' name='whoon' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>释放时间</th>
					<td><input type='text' name='offfromtime' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>释放操作人</th>
					<td><input type='text' name='whooff' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				</tr>
				<tr>
					<th>批号</th>
					<td><input type='text' name='lotnum' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>生产日期</th>
					<td><input type='text' name='lotatt01' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>失效日期</th>
					<td><input type='text' name='lotatt02' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>入库日期</th>
					<td><input type='text' name='lotatt03' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				</tr>
				<tr>	
					<th>库存状态</th>
					<td><input type='text' name='lotatt04' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>批属5</th>
					<td><input type='text' name='lotatt05' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>批属6</th>
					<td><input type='text' name='lotatt06' class='easyui-textbox' size='16' data-options='editable:false'/></td>
					<th>跟踪号</th>
					<td><input type='text' name='traceid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
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
	
	<!-- 客户选择弹框 -->
	<div id='ezuiCustDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
	<div class='easyui-layout' data-options='fit:true,border:false'>
	<div data-options="region:'center'">
		<div id='ezuiCustToolbar' class='datagrid-toolbar'   style="">
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
</body>
</html>
