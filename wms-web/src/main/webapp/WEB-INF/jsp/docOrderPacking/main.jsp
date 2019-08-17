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
var commitType;
var ezuiMenu;
var ezuiForm;
var ezuiSinglePackingCancelForm;
var ezuiOrderPackingCancelForm;
var ezuiDialog;
var ezuiDatagrid;
var ezuiPackingDialog;
var ezuiSinglePackingCancelDialog;
var ezuiOrderPackingCancelDialog;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiSinglePackingCancelForm = $('#ezuiSinglePackingCancelForm').form();
	ezuiOrderPackingCancelForm = $('#ezuiOrderPackingCancelForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docOrderPackingController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '装箱明细',
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
		checkOnSelect:false,
		selectOnCheck: false,
		idField : 'cartonNo',
		queryParams:{
			orderNo : '0'
		},
		columns : [[
           			 {field: 'cartonNo',				title: '装箱序号',		width: 20 	},
		            {field: 'traceId',				title: '箱号',		width: 20 	},
					{field: 'sku',					title: '产品编码',		width: 20	},
					{field: 'skuName',				title: '产品名称',		width: 40	},
					{field: 'scanQty',				title: '装箱件数',		width: 20 	}
		]],
		onDblClickCell: function(index,field,value){
		},
		/* 鼠标右键 */
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/docOrderPackingController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('scrollTo',0);
			$(this).datagrid('unselectAll');
            var grid = ezuiDatagrid.datagrid("getData");
            if(grid.rows.length>0){
                $('#ezuiBtn_packing').linkbutton('enable');
                //复核完成按钮
                $('#ezuiBtn_packingCommit').linkbutton('enable');
            }
		}
	});
	
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
		},
		onClose : function() {
		}
	}).dialog('close');
	
	ezuiPackingDialog = $('#ezuiPackingDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiPackingDialogBtn',
		onOpen : function() {
		},
		onClose : function() {
		}
	}).dialog('close');
	
	ezuiSinglePackingCancelDialog = $('#ezuiSinglePackingCancelDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiSinglePackingCancelDialogBtn',
		onOpen : function() {
		},
		onClose : function() {
		}
	}).dialog('close');
	
	ezuiOrderPackingCancelDialog = $('#ezuiOrderPackingCancelDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiOrderPackingCancelDialogBtn',
		onOpen : function() {
		},
		onClose : function() {
		}
	}).dialog('close');
	//控件初始化
	ezuiClear();
});
/* 获取新的箱号 */
var generateCartonNo = function(){
	$.ajax({
		async : false,
		url : 'docOrderPackingController.do?generateCartonNo',
		data : {orderNo : $('#orderNo').val()},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				if (result.success) {
					$('#cartonNo').numberbox('setValue',result.obj.cartonNo);
					$('#ezuiBtn_packing').linkbutton('enable');
					$('#ezuiBtn_packingCommit').linkbutton('enable');
					$('#orderNo').textbox('readonly',true);
					$('#allocationQty').numberbox('clear');
					$('#scanQty').numberbox('clear');
					$('#skuName').textbox('clear');
					/* 焦点跳转 */
					skuCodeInit();
				} else {
					msg = '<font color="red">' + result.msg + '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
				}
			} catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			};
		}
	});
};
/* 订单状态判断 */
var orderStatusCheck = function(){
	if ($('#orderNo').val() == '') {
		return;
	};
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?orderStatusCheck',
		data : {orderNo : $('#orderNo').val()},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				if (result.success) {
					ezuiDatagrid.datagrid('load', {
						orderNo : $('#orderNo').val()
					});
					/* 生成新的箱号 */
					//generateCartonNo();
					//跳转sku焦点
                    skuCodeInit();
				} else {
					ezuiClear();
					msg = '<font color="red">' + result.msg + '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			}
		}
	});
};
/* 条码扫描判断 */
var skuScanCheck = function(){
	if ($('#skuCode').val() == '') {
		return;
	};
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?skuScanCheck',
		data : {orderNo : $('#orderNo').val(), skuCode : $('#skuCode').val()},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				if (result.success) {
                    $('#cartonNo').numberbox('setValue',result.obj.cartonNum);
                    ezuiDatagrid.datagrid('reload');
                    skuCodeInit();
                    $('#ezuiBtn_packing').linkbutton('enable');
                    //复核完成按钮
                    $('#ezuiBtn_packingCommit').linkbutton('enable');
				} else {
					skuCodeInit();
					msg = '<font color="red">' + result.msg + '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			};
		}
	});
};
/* 条码扫描 */
var skuScan = function(){
	if ($('#skuCode').val() == '') {
		return;
	};
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?skuScan',
		data : {orderNo : $('#orderNo').val(), cartonNo : $('#cartonNo').numberbox('getValue'), skuCode : $('#skuCode').val(), qty : 1},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				if (result.success) {
					/* 显示SKU扫描信息 */
					$('#skuName').textbox('setValue',result.obj.skuName);
					$('#allocationQty').numberbox('setValue',result.obj.allocationQty);
					$('#scanQty').numberbox('setValue',result.obj.scanQty);
					skuCodeInit();
				} else {
					skuCodeInit();
					msg = '<font color="red">' + result.msg + '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			};
		}
	});
};
/* 条码批量扫描 */
var skuBatchScan = function(){
	if ($('#batchQty').val() == '') {
		return;
	};
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?skuScan',
		data : {orderNo : $('#orderNo').val(), cartonNo : $('#cartonNo').numberbox('getValue'), skuCode : $('#skuCode').val(), qty : $('#batchQty').numberbox('getValue')},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			var msg = '';
			try {
				if (result.success) {
					/* 显示SKU扫描信息 */
					$('#skuName').textbox('setValue',result.obj.skuName);
					$('#allocationQty').numberbox('setValue',result.obj.allocationQty);
					$('#scanQty').numberbox('setValue',result.obj.scanQty);
					$("#batchQty").numberbox('clear').numberbox('readonly',true);
					skuCodeInit();
				} else {
					$("#batchQty").numberbox('clear').numberbox('readonly',true);
					skuCodeInit();
					msg = '<font color="red">' + result.msg + '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
				};
			} catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			};
		}
	});
};
/* 装箱完成 */
var packing = function(){
	commitType = 'packingCommit';
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?getPackingInfo',
		data : { orderNo : $('#orderNo').val(), cartonNo : $('#cartonNo').numberbox('getValue') },
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			try {
				if (result.success) {
					msg = result.msg;
					$('#grossWeight').numberbox('setValue', result.obj.grossWeight);
					$('#cube').numberbox('setValue', result.obj.cube);
				}
			} catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			};
		}
	});
	$('#ezuiPackingDialog').panel({title: "确认是否完成装箱操作？"});
	ezuiPackingDialog.dialog('open');
};
/* 复核完成 */
var packingCommit = function(){
	commitType = 'orderCommit';
	$.ajax({
		async: false,
		url : 'docOrderPackingController.do?getOrderPackingInfo',
		data : { orderNo : $('#orderNo').val() },
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			try {
				/*if (result.success) {
					msg = result.msg;
					//$('#grossWeight').numberbox('setValue', result.obj.grossWeight);
					//$('#cube').numberbox('setValue', result.obj.cube);
				}*/
				var resultMsg = result.msg+"<br/>";
				for(var i = 0;i<result.obj.length;i++){
				    resultMsg += result.obj[i].batchNum+":"+result.obj[i].differentQty;
				}
                $.messager.confirm('提示', resultMsg+'<br/>是否确认复合完成？', function(r){
                    if(r){
                        commit();
                    }
                });

            } catch (e) {
				msg = '<spring:message code="common.message.data.process.failed"/>';
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
			};
		}
	});
	/*$('#ezuiPackingDialog').panel({title: "确认是否完成复核操作？"});
	ezuiPackingDialog.dialog('open');*/

};
/* 提交 */
var commit = function(){
		if(commitType == 'packingCommit' && !ezuiForm.form('validate')){
			return;
		}
		var url = '';
		if (commitType == 'packingCommit') {
			url = '<c:url value="/docOrderPackingController.do?packingCommit"/>';
		} else {
			url = '<c:url value="/docOrderPackingController.do?orderCommit"/>';
		}
		var selectRow = ezuiDatagrid.datagrid("getSelected");
		console.log(selectRow);
		$.ajax({
			async: false,
			url : url,
			data : {orderNo : $('#orderNo').val()},
			type : 'POST',
			dataType : 'JSON',
			success : function(result){
				try {
					if (result.success) {
						msg = result.msg;
						ezuiPackingDialog.dialog('close');
						ezuiFormClear(ezuiForm);
						if (commitType == 'packingCommit') {
							ezuiDatagrid.datagrid('reload');
							generateCartonNo();
						} else {
							//扫描条码不可输入
							$("#skuCode").textbox('readonly',true);
							//批量装箱数量不可输入
							$('#batchQty').numberbox('readonly',true);
							//清空显示数据
							$('#cartonNo').numberbox('clear');
							$('#skuName').textbox('clear');
							$('#allocationQty').numberbox('clear');
							$('#scanQty').numberbox('clear');
							//装箱完成按钮
							$('#ezuiBtn_packing').linkbutton('disable');
							//复核完成按钮
							$('#ezuiBtn_packingCommit').linkbutton('disable');
							//复核完成允许打印装箱标签
							$('#ezuiBtn_printPackingLabel').linkbutton('enable');
							//复核完成允许打印装箱清单
							$('#ezuiBtn_printPackingList').linkbutton('enable');
						}
						$.messager.show({
							msg : msg, title : '<spring:message code="common.message.prompt"/>'
						});
					} else {
						msg = '<font color="red">' + result.msg + '</font>';
						$.messager.alert('提示',msg);
					}
				} catch (e) {
					msg = '<spring:message code="common.message.data.process.failed"/>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
				};
			}
		});
};
/* 取消复核（按箱） */
var singlePackingCancel = function(){
	ezuiSinglePackingCancelDialog.dialog('open');
};
/* 取消复核（整单） */
var orderPackingCancel = function(){
	ezuiOrderPackingCancelDialog.dialog('open');
};
/* 取消复核提交（按箱） */
var singlePackingCancelCommit = function(){
	if(ezuiSinglePackingCancelForm.form('validate')){
		$.messager.confirm('提示', '是否确认取消当前订单箱号的装箱记录？', function(r){
			if (r){
				$.ajax({
					async: false,
					url : 'docOrderPackingController.do?singlePackingCancel',
					data : {orderNo : $('#ezuiSinglePackingCancelForm #orderNo').val(), cartonNo : $('#ezuiSinglePackingCancelForm #cartonNo').val()},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						try {
							if (result.success) {
								msg = result.msg;
							} else {
								msg = '<font color="red">' + result.msg + '</font>';
							}
							ezuiSinglePackingCancelDialog.dialog('close');
							ezuiFormClear(ezuiSinglePackingCancelForm);
							ezuiClear();
						} catch (e) {
							msg = '<spring:message code="common.message.data.process.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
						};
					}
				});
			};
		});
	}
};
/* 取消复核提交（整单） */
var orderPackingCancelCommit = function(){
	if(ezuiOrderPackingCancelForm.form('validate')){
		$.messager.confirm('提示', '是否确认取消当前订单的复核记录？', function(r){
			if (r){
				$.ajax({
					async: false,
					url : 'docOrderPackingController.do?orderPackingCancel',
					data : {orderNo : $('#ezuiOrderPackingCancelForm #orderNo').val()},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						try {
							if (result.success) {
								msg = result.msg;
							} else {
								msg = '<font color="red">' + result.msg + '</font>';
							}
							ezuiOrderPackingCancelDialog.dialog('close');
							ezuiFormClear(ezuiOrderPackingCancelForm);
							ezuiClear();
						} catch (e) {
							msg = '<spring:message code="common.message.data.process.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
						};
					}
				});
			};
		});
	};
};
/* 条码扫描框初始化 */
var skuCodeInit = function(){
	$('#skuCode').textbox('readonly',false);
	$('#skuCode').textbox('clear').textbox('textbox').focus();
	/* $('#skuCode').textbox('textbox').keydown(function(e){
        if (e.keyCode == 13) {
        	skuScanCheck();
        }
    }); */
};
/* 清空 */
var ezuiClear = function(){
	processType = 'init';
	//出库单号可输入
	$('#orderNo').textbox('clear').textbox('readonly',false).textbox('textbox').focus();
	//出库单号回车事件
	/* $('#orderNo').textbox('textbox').keydown(function (e) {
        if (e.keyCode == 13) {
        	orderStatusCheck();
        }
    }); */
	//扫描条码不可输入
	$('#skuCode').textbox('readonly',true);
	//批量装箱数量不可输入
	$('#batchQty').numberbox('readonly',true);
	//清空显示数据
	$('#cartonNo').numberbox('clear');
	$('#skuName').textbox('clear');
	$('#allocationQty').numberbox('clear');
	$('#scanQty').numberbox('clear');
	//装箱完成按钮
	$('#ezuiBtn_packing').linkbutton('disable');
	//复核完成按钮
	$('#ezuiBtn_packingCommit').linkbutton('disable');
	//打印装箱标签
	$('#ezuiBtn_printPackingLabel').linkbutton('disable');
	//打印装箱清单
	$('#ezuiBtn_printPackingList').linkbutton('disable');
	//默认单件装箱
	$('input:radio[name="ScanType"][value="single"]').prop('checked', true);
	//清空数据列表
	$('#ezuiDatagrid').datagrid('loadData',{total:0,rows:[]});
};
/* 打印装箱标签按钮 */
var printPackingLabel = function(){
	if ($('#orderNo').val() == '') {
		return;
	}
	var orderNo = $('#orderNo').val();
	window.open(sy.bp()+"/docOrderPackingController.do?exportPackingLabelPdf&orderNo="+orderNo, "Report_"+orderNo, "scrollbars=yes,resizable=no");
};
/* 打印装箱清单按钮 */
var printPackingList = function(){
	if ($('#orderNo').val() == '') {
		return;
	}
	var orderNo = $('#orderNo').val();
	window.open(sy.bp()+"/docOrderPackingController.do?exportPackingListPdf&orderNo="+orderNo, "Report_"+orderNo, "scrollbars=yes,resizable=no");
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
							<th>出库单号</th><td><input type='text' id='orderNo' class='easyui-textbox' size='16' data-options="onChange:function(newValue,oldValue){
																																orderStatusCheck();
																													 	  	}" /></td>
							<th>箱号</th><td><input type='text' id='cartonNo' class='easyui-numberbox' size='16' data-options='readonly:true,min:1,precision:0' /></td>
							<th>扫描条码</th><td><input type='text' id='skuCode' name='skuCode' class='easyui-textbox' size='16' data-options="onChange:function(newValue,oldValue){
																																				skuScanCheck();
																																	 	  	}" /></td>
							<th colspan="2"><input name="ScanType" type="radio" class="easyui-validatebox" checked="checked" value="single"><label>单件装箱（逐个扫描SKU装箱）</label></th>
							<th><input name="ScanType" type="radio" class="easyui-validatebox" value="batch"><label>批量装箱</label></th>
							<td><input type='text' id='batchQty' class='easyui-numberbox' size='16' data-options="onChange:function(newValue,oldValue){
																													skuBatchScan();
																												},
																												min:1,
																												precision:0" /></td>
						</tr>
						<tr>
							<th>中文名称</th><td colspan="3"><input type='text' id='skuName' class='easyui-textbox' size='41' data-options='editable:false' /></td>
							<th>分配数量</th><td><input type='text' id='allocationQty' class='easyui-numberbox' size='16' data-options='readonly:true' /></td>
							<th>装箱数量</th><td><input type='text' id='scanQty' class='easyui-numberbox' size='16' data-options='readonly:true' /></td>
							<td colspan="2"><a onclick='ezuiClear();' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='iconCls:"icon-remove"' style="width:90px" href='javascript:void(0);'>数据清空</a></td>
						</tr>
					</table>
				</fieldset>
					<table>
						<tr>
							<td colspan="6">
								<a onclick='packing();' id='ezuiBtn_packing' class='easyui-linkbutton' data-options='iconCls:"icon-save"' style="width:90px" href='javascript:void(0);'>装箱完成</a>
								<a onclick='packingCommit();' id='ezuiBtn_packingCommit' class='easyui-linkbutton' data-options='iconCls:"icon-save"' style="width:90px" href='javascript:void(0);'>复核完成</a>
								<a onclick='singlePackingCancel();' id='ezuiBtn_singlePackingCancel' class='easyui-linkbutton' data-options='iconCls:"icon-remove"' style="width:90px" href='javascript:void(0);'>取消装箱</a>
								<!-- <a onclick='orderPackingCancel();' id='ezuiBtn_orderPackingCancel' class='easyui-linkbutton' data-options='iconCls:"icon-remove"' style="width:90px" href='javascript:void(0);'>取消复核</a> -->
								<!--<a onclick='printPackingLabel();' id='ezuiBtn_printPackingLabel' class='easyui-linkbutton' data-options='iconCls:"icon-print"' style="width:120px" href='javascript:void(0);'>打印装箱标签</a>
								<a onclick='printPackingList();' id='ezuiBtn_printPackingList' class='easyui-linkbutton' data-options='iconCls:"icon-print"' style="width:120px" href='javascript:void(0);'>打印装箱清单</a>-->
							</td>
						</tr>
					</table>
			</div>
			<table id='ezuiDatagrid'></table>
		</div>
	</div>
	<div id='ezuiPackingDialog' style="width:270px;height:180px;padding:10px 20px" >
		<form id='ezuiForm' method='post'>
			<div class='easyui-layout' data-options='border:false'>
				<table>
					<tr>
						<th>箱型</th>
						<td><input type='text' name='cartontype'  id='cartontype' class='easyui-textbox' data-options='required:true,' style='width:180px;height:32px;'/></td>
					</tr>
					<!--<tr>
						<th>体积（立方）</th>
						<td><input type='text' name='cube' id="cube" class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3' style='width:120px;height:32px;'/></td>
					</tr>-->
				</table>
			</div>
		</form>
	</div>
	<div id='ezuiPackingDialogBtn'>
		<a onclick='commit();' id='ezuiPackingBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiPackingDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiSinglePackingCancelDialog' style="width:270px;height:150px;padding:10px 20px" >
		<form id='ezuiSinglePackingCancelForm' method='post'>
			<div class='easyui-layout' data-options='border:false'>
				<table>
					<tr>
						<th>出库单号</th>
						<td><input type='text' name='orderNo'  id='orderNo' class='easyui-textbox' size='16' data-options='required:true' /></td>
					</tr>
					<tr>
						<th>装箱序号</th>
						<td><input type='text' name='cartonNo'  id='cartonNo' class='easyui-numberbox' size='16' data-options='required:true'/></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div id='ezuiSinglePackingCancelDialogBtn'>
		<a onclick='singlePackingCancelCommit();' id='ezuiPackingBtn_cancelCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiSinglePackingCancelDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiOrderPackingCancelDialog' style="width:270px;height:150px;padding:10px 20px" >
		<form id='ezuiOrderPackingCancelForm' method='post'>
			<div class='easyui-layout' data-options='border:false'>
				<table>
					<tr>
						<th>出库单号</th>
						<td><input type='text' name='orderNo'  id='orderNo' class='easyui-textbox' size='16' data-options='required:true' /></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div id='ezuiOrderPackingCancelDialogBtn'>
		<a onclick='orderPackingCancelCommit();' id='ezuiPackingBtn_cancelCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiOrderPackingCancelDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
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
