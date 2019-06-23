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
var ezuiDataDialogId;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	
	
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
		},
		onClose : function() {
		}
	}).dialog('close');

	$('#batchQty').textbox('disable');
	ordernoInit();
	$("#skucode").textbox({editable:false});
	$('#ezuiBtn_packing').linkbutton('disable');
	$('#ezuiBtn_packingCommit').linkbutton('disable');
	$('#ezuiBtn_addBoxno').linkbutton('disable');
});
/* 生成新箱号 */
var addBoxno = function(){
	if ($('#orderno').val() == '') {
		$.messager.alert('提示','生成箱号时,订单号不能为空!','info');
		return;
	};
	$.messager.confirm('提示', '是否开始新的装箱？', function(r){
		if (r){
			$.ajax({
				async: false,
				url : 'docOrderPackingController.do?addBoxno',
				data : {orderno : $('#orderno').val()},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
						msg = result.msg;
						if (msg != '000') {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							return;
						} else {
							$('#traceid').textbox('setValue',result.obj.traceid);
							/* 焦点跳转 */
							skucodeInit();
						}
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					}
				}
			});
		} else {
			/* 焦点跳转 */
			skucodeInit();
		};
	});
};
/* 订单状态判断 */
var orderStatusCheck = function(){
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?orderStatusCheck',
		data : {orderno : $('#orderno').val()},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				msg = result.msg;
				if (msg != '000') {
					ordernoInit();
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					return;
				} else {
					processType = 'scan';
					$("#orderno").textbox({editable:false});
		   			$('#ezuiBtn_packing').linkbutton('enable');
		   			$('#ezuiBtn_packingCommit').linkbutton('enable');
		   			$('#ezuiBtn_addBoxno').linkbutton('enable');
					/* 生成新的箱号 */
					addBoxno();
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.delete.failed"/>';
			}
		}
	});
};
/* 条码扫描判断 */
var skuScanCheck = function(){
	if ($('#traceid').val() == '') {
		$.messager.alert('提示','扫描条码时,箱号不能为空!','info');
		return;
	};
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?skuScanCheck',
		data : {orderno : $('#orderno').val(), traceid : $('#traceid').val(), sku : $('#skucode').val()},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				msg = result.msg;
				if (msg != '000') {
					skucodeInit();
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					return;
				} else {
					/* 显示SKU扫描信息 */
					$('#sku').textbox('setValue',result.obj.sku);
					$('#skuName').textbox('setValue',result.obj.skuName);
					$('#allocationQty').textbox('setValue',result.obj.allocationQty);
					$('#qty').textbox('setValue',result.obj.qty);
					/* 判断是否批量确认 */
					if ($('#batchCheck').is(':checked')) {
						$("#skucode").textbox({editable:false});
						$("#batchQty").numberbox('clear');
						$("#batchQty").numberbox({editable:true});
						$("#batchQty").numberbox().next('span').find('input').focus();
						$('#batchQty').numberbox('textbox').keydown(function (e) {
					        if (e.keyCode == 13) {
					        	skuBatchScan();
					        }
					    });
					} else {
			        	skuScan();
					}
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.delete.failed"/>';
			};
		}
	});
};
/* 条码扫描 */
var skuScan = function(){
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?skuScan',
		data : {orderno : $('#orderno').val(), traceid : $('#traceid').val(), sku : $('#sku').val(), qty : 1},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				msg = result.msg;
				if (msg != '000') {
					skucodeInit();
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					return;
				} else {
					/* 显示SKU扫描信息 */
					$('#sku').textbox('setValue',result.obj.sku);
					$('#skuName').textbox('setValue',result.obj.skuName);
					$('#allocationQty').textbox('setValue',result.obj.allocationQty);
					$('#qty').textbox('setValue',result.obj.qty);
					/* 判断单件装箱 */
					if ($('#singlePacking').is(':checked')) {
						$("#traceid").textbox('clear');
						$.messager.confirm('提示', '是否开始新的装箱？', function(r){
							if (r){
								addBoxno();
							} else {
								skucodeInit();
							}
						});
					} else {
						skucodeInit();
					}
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.delete.failed"/>';
			};
		}
	});
};
/* 条码批量扫描 */
var skuBatchScan = function(){
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?skuScan',
		data : {orderno : $('#orderno').val(), traceid : $('#traceid').val(), sku : $('#sku').val(), qty : $('#batchQty').numberbox('getValue')},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				msg = result.msg;
				if (msg != '000') {
					$("#batchQty").numberbox('clear');
					$("#batchQty").numberbox().next('span').find('input').focus();
					$('#batchQty').numberbox('textbox').keydown(function (e) {
				        if (e.keyCode == 13) {
				        	skuBatchScan();
				        }
				    });
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					return;
				} else {
					/* 显示SKU扫描信息 */
					$('#sku').textbox('setValue',result.obj.sku);
					$('#skuName').textbox('setValue',result.obj.skuName);
					$('#allocationQty').textbox('setValue',result.obj.allocationQty);
					$('#qty').textbox('setValue',result.obj.qty);
					skucodeInit();
					$("#batchQty").numberbox('clear');
					$("#batchQty").numberbox({editable:false});
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.delete.failed"/>';
			};
		}
	});
};
/* 装箱完成 */
var packing = function(){
	$.messager.confirm('提示', '是否开始新的装箱？', function(r){
		if (r){
			addBoxno();
		} else {
			return;
		}
	});
};
/* 订单提交 */
var packingCommit = function(){
	$.messager.confirm('提示', '是否确认提交订单？', function(r){
		if (r){
			$.ajax({
				async: false,
				url : 'docOrderPackingController.do?commit',
				data : {orderno : $('#orderno').val()},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
						msg = result.msg;
						if (msg != '000') {
							$.messager.alert('提示', msg);
							return;
						} else {
							$.messager.alert('提示','订单装箱提交成功!');
							processType = '';
							ordernoInit();
							$("#skucode").textbox({editable:false});
							$('#traceid').textbox('clear');
							$('#sku').textbox('clear');
							$('#skuName').textbox('clear');
							$('#allocationQty').textbox('clear');
							$('#qty').textbox('clear');
							$('#ezuiBtn_packing').linkbutton('disable');
							$('#ezuiBtn_packingCommit').linkbutton('disable');
							$('#ezuiBtn_addBoxno').linkbutton('disable');
						};
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					};
				}
			});
		} else {
			return;
		}
	});
};
/* 打开装箱明细弹框 */
var queryPackingList = function(){
	ezuiDataDialogId = $('#ezuiDataDialogId').datagrid({
		url:'<c:url value="/docOrderPackingController.do?showDatagrid"/>',
		method:'POST',
		toolbar:'#ezuiPackingToolbar',
		title:'装箱明细',
		pageSize:50,
		pageList:[50, 100, 200],
		fit:true,
		border:false,
		fitColumns:true,
		nowrap:false,
		striped:true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'traceid',
		queryParams:{
			orderno : $('#orderno').val()
		},
		columns : [[
		            {field: 'traceid',				title: '箱号',		width: 20 },
					{field: 'sku',					title: '产品编码',		width: 20 },
					{field: 'skuName',				title: '产品名称',		width: 40 },
					{field: 'qty',					title: '装箱数量',		width: 8,	align: 'right' },
					{field: 'allocationdetailsid',	title: '分配ID',		width: 12 },
					{field: 'addwho',				title: '操作员工',		width: 16 }
		]],
		onDblClickCell: function(index,field,value){
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	$('#ezuiDialog #orderno').textbox('setValue',$('#orderno').val());
	$('#ezuiDialog #traceid').textbox('clear');
	ezuiDialog.dialog('open');
};
/* 勾选批量装箱 */
var batchCheck = function(){
	if ($('#batchCheck').is(':checked')) {
		$("#singlePacking").attr("checked",false);
		$('#batchQty').numberbox('enable');
		if (processType == 'scan') {
			skucodeInit();
		}
	} else {
		$('#batchQty').numberbox('disable');
		$('#batchQty').numberbox('setValue','');
		if (processType == 'scan') {
			skucodeInit();
		}
	};
};
/* 勾选单件装箱 */
var singlePacking = function(){
	if ($('#singlePacking').is(':checked')) {
		$("#batchCheck").attr("checked",false);
		$('#batchQty').numberbox('disable');
		$('#batchQty').numberbox('setValue','');
		if (processType == 'scan') {
			skucodeInit();
		}
	} else {
		if (processType == 'scan') {
			skucodeInit();
		}
	};
};
/* 订单扫描框初始化 */
var ordernoInit = function(){
	$("#orderno").textbox('clear');
	$("#orderno").textbox().next('span').find('input').focus();
	$('#orderno').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	orderStatusCheck();
        }
    });
};
/* 条码扫描框初始化 */
var skucodeInit = function(){
	$("#skucode").textbox('clear');
	$("#skucode").textbox({editable:true});
	$("#skucode").textbox().next('span').find('input').focus();
	$('#skucode').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	skuScanCheck();
        }
    });
};
/* 装箱明细弹框清空 */
var ezuiPackingToolbarClear = function(){
	$('#ezuiDialog #orderno').textbox('clear');
	$('#ezuiDialog #traceid').textbox('clear');
};
/* 装箱明细弹框查询 */
var doSearch = function(){
	if ($('#ezuiDialog #orderno').val() == '') {
		$.messager.alert('提示','查询条件"订单号"不能为空！','info');
		return;
	};
	ezuiDataDialogId.datagrid('load', {
		orderno : $('#ezuiDialog #orderno').val(),
		traceid : $('#ezuiDialog #traceid').val()
	});
};
/* 装箱明细单件删除 */
var packingSingleDelete = function(){
	var row = ezuiDataDialogId.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docOrderPackingController.do?singleDelete',
					data : {traceid : row.traceid, sku : row.sku, allocationdetailsid : row.allocationdetailsid},
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
							ezuiDataDialogId.datagrid('reload');
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
/* 装箱明细整行删除 */
var packingSkuDelete = function(){
	var row = ezuiDataDialogId.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docOrderPackingController.do?skuDelete',
					data : {traceid : row.traceid, sku : row.sku, allocationdetailsid : row.allocationdetailsid},
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
							ezuiDataDialogId.datagrid('reload');
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
/* 打印装箱标签按钮 */
var printPackingLabel = function(){
	doSearch();
	var orderno = $('#ezuiDialog #orderno').val();
	var traceid = $('#ezuiDialog #traceid').val();
	window.open(sy.bp()+"/docOrderPackingController.do?exportPackingLabelPdf&orderno="+orderno+"&traceid="+traceid, "Report_"+orderno, "scrollbars=yes,resizable=no");
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
					<table style="border-collapse:separate; border-spacing:30px;">
						<tr>
							<th>订单号：</th><td><input type='text' id='orderno' class='easyui-textbox' size='16' data-options='' style="width:200px;height:32px;"/></td>
							<th>箱号：</th><td><input type='text' id='traceid' class='easyui-textbox' size='16' data-options='editable:false' style="width:200px;height:32px;"/></td>
							<th>扫描条码：</th><td><input type='text' id='skucode' name='skucode' class='easyui-textbox' size='16' data-options='' style="width:200px;height:32px;"/></td>
						</tr>
						<tr>
							<th>产品编码：</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options='editable:false' style="width:200px;height:32px;"/></td>
							<th>分配数量：</th><td><input type='text' id='allocationQty' class='easyui-textbox' size='16' data-options='editable:false' style="width:200px;height:32px;"/></td>
							<th>已扫数量：</th><td><input type='text' id='qty' class='easyui-textbox' size='16' data-options='editable:false' style="width:200px;height:32px;"/></td>
						</tr>
						<tr>
							<th>中文名称：</th><td colspan="3"><input type='text' id='skuName' class='easyui-textbox' size='48' data-options='editable:false' style="width:540px;height:32px;"/></td>
						</tr>
						<tr>
							<th><input id="batchCheck" type="checkbox" onclick="batchCheck();"><label for="batchCheck">批量装箱</label></th>
							<td><input type='text' id='batchQty' class='easyui-numberbox' size='16' data-options='editable:false,min:1,precision:0' style="width:200px;height:32px;"/></td>
							<th><input id="singlePacking" type="checkbox" onclick="singlePacking();"><label for="singlePacking">单件装箱</label></th>
						</tr>
						<tr>
							<td colspan="5">
								<a onclick='packing();' id='ezuiBtn_packing' class='easyui-linkbutton' data-options='iconCls:"icon-search"' style="width:90px" href='javascript:void(0);'>装箱完成</a>
								<a onclick='packingCommit();' id='ezuiBtn_packingCommit' class='easyui-linkbutton' data-options='iconCls:"icon-save"' style="width:90px" href='javascript:void(0);'>订单提交</a>
								<a onclick='addBoxno();' id='ezuiBtn_addBoxno' class='easyui-linkbutton' data-options='iconCls:"icon-add"' style="width:90px" href='javascript:void(0);'>新箱号</a>
								<a onclick='queryPackingList();' class='easyui-linkbutton' data-options='iconCls:"icon-search"' style="width:90px" href='javascript:void(0);'>装箱明细</a>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
		</div>
	</div>
	<div id='ezuiDialog' style="width:900px;height:480px;padding:10px 20px" >
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiPackingToolbar' class='datagrid-toolbar' style="">
					<fieldset>
						<legend><spring:message code='common.button.packingQuery'/></legend>
						<table>
							<tr>
							<th>订单号：</th>
							<td><input type='text' name='orderno'  id='orderno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>箱号：</th>
							<td><input type='text' name='traceid' id="traceid" class='easyui-textbox' size='16' data-options=''/></td>
							<td><a onclick='doSearch();' class='easyui-linkbutton' data-options='iconCls:"icon-search"' href='javascript:void(0);'>查詢</a></td>
							<td><a onclick='ezuiToolbarClear("#packingToolbar");' class='easyui-linkbutton' data-options='iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a></td>
							<td><a onclick='printPackingLabel();' class='easyui-linkbutton' data-options='iconCls:"icon-print"' href='javascript:void(0);'>装箱标签</a></td>
							<td><a onclick='printPackingList();' class='easyui-linkbutton' data-options='iconCls:"icon-print"' href='javascript:void(0);'>装箱单</a></td>
						</table>
					</fieldset>
					<div>
						<a onclick='packingSingleDelete();' id='ezuiBtn_singleDelete' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>删除单件</a>
						<a onclick='packingSkuDelete();' id='ezuiBtn_skuDelete' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>删除整行</a>
						<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
					</div>
				</div>
				<table id='ezuiDataDialogId'></table>
			</div>
		</div>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
