<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
    <style>
        table th{
            text-align: right;
        }
    </style>
<script type='text/javascript'>
var processType;
var orderList = null;
var selectedOrders = [];
var ezuiMenu;
var ezuiDetailsMenu;
var ezuiForm;
var ezuiDetailsForm;
var ezuiDialog;
var ezuiDetailsDialog;
var ezuiDatagrid;
var ezuiDetailsDatagrid;
var ezuiCustDataDialog;
var ezuiCustDataDialogId;
var ezuiSkuDataDialog;
var ezuiSkuDataDialogId;
var ezuiLocDataDialog;
var ezuiLocDataDialogId;
var ezuiImportDataDialog;
var ezuiImportDataForm;
var ezuiOperateResultDataDialog;
var ezuiOperateResultDataForm;
var allocationDetailsDatagrid;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiDetailsMenu = $('#ezuiDetailsMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDetailsForm = $('#ezuiDetailsForm').form();
	ezuiImportDataForm = $('#ezuiImportDataForm').form();
	ezuiOperateResultDataForm = $('#ezuiOperateResultDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docOrderHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '订单列表',
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
		singleSelect:false,
		checkOnSelect:true,
		selectOnCheck: true,
		idField : 'orderno',
		rowStyler:docOrderRowStyler,
		queryParams:{
			ordertime : $('#ordertime').datetimebox('getValue'),
			ordertimeTo : $('#ordertimeTo').datetimebox('getValue'),
			sostatusCheck : $('#sostatusCheck').is(':checked') == true ? "" : "N"
		},
		columns : [[
		            {field: 'chk',                  checkbox:true,      width: 6},
					{field: 'customerid',			title: '客户编码',		width: 100 },
                    {field: 'orderStatusName',			title: '订单状态',		width: 100 },
					{field: 'orderno',				title: 'SO编号',		width: 100 },
					{field: 'soreference1',			title: '客户单号',	width: 120 },
					{field: 'soreference2',			title: '定向入库单号',	width: 120 },
					{field: 'ordertime',			title: '创建时间',		width: 150 },
					{field: 'orderTypeName',		title: '订单类型',		width: 100 },
					{field: 'consigneename',		title: '收货人',		width: 100 },
                    {field: 'cProvince',			title: '省',		width: 100 },
                    {field: 'cCity',			title: '市',		width: 100 },
                    {field: 'cAddress2',			title: '区',		width: 100 },
					{field: 'cAddress1',			title: '收货地址',		width: 250 },
					{field: 'cTel1',				title: '联系方式',		width: 100 },
					{field: 'releasestatusName',	title: '释放状态',		width: 100 }
		]],
		onDblClickCell: function(index,field,value){
			//edit();
		},
        onDblClickRow(index,row){
            edit(row);
        },
		/* 鼠标右键 */
		onRowContextMenu : function(event, rowIndex, rowData) {
			/* event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			}); */
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/docOrderHeaderController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
			$(this).datagrid('clearChecked');
		}
	});
	
	//订单明细列表
	ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
		url : '<c:url value="/docOrderDetailsController.do?showDatagrid"/>',
		method : 'POST',
		toolbar : '#detailToolbar',
		idField : 'orderlineno',
		title : '',
		border : false,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : false,
		rownumbers : true,
		singleSelect : true,
		columns : [[
			{field: 'orderlineno',		title: '行号',		width: 130 },
			{field: 'sku',				title: '商品编码',		width: 130 },
			{field: 'skuName',			title: '商品名称',		width: 130 },
			{field: 'linestatusName',	title: '状态',		width: 130 },
			{field: 'qtyordered',		title: '订货数',		width: 130 },
			{field: 'qtyallocated',		title: '分配数',		width: 130 },
			{field: 'qtypicked',		title: '拣货数',		width: 130 },
			{field: 'qtyshipped',		title: '发货数',		width: 130 },
			{field: 'alternativesku',	title: '商品条码',		width: 130 },
            {field: 'lotatt01',	title: '生产日期',		width: 130 },
            {field: 'lotatt02',	title: '效期',		width: 130 },
            {field: 'lotatt03',	title: '入库日期',		width: 130 },
            {field: 'lotatt04',	title: '生产批号',		width: 130 },
            {field: 'lotatt05',	title: '序列号',		width: 130 },
            {field: 'lotatt06',	title: '产品注册证',		width: 130 },
            {field: 'lotatt07',	title: '灭菌批号',		width: 130 },
            {field: 'lotatt08',	title: '供应商',		width: 130 },
            {field: 'lotatt09',	title: '样品属性',		width: 130 },
            {field: 'lotatt10',	title: '质量状态',		width: 130 },
            {field: 'lotatt11',	title: '存储条件',		width: 130 },
            {field: 'lotatt12',	title: '产品名称',		width: 130 },
            {field: 'lotatt13',	title: '双证',		width: 130 },
            {field: 'lotatt14',	title: '入库单号',		width: 130 },
            {field: 'lotatt15',	title: '生产厂商名称',		width: 130 }
            /*{field: 'lotatt16',	title: '自定义批属1',		width: 130 },
            {field: 'lotatt17',	title: '自定义批属2',		width: 130 },
            {field: 'lotatt18',	title: '自定义批属3',		width: 130 }*/
		]],
		onDblClickCell: function(index,field,value){
			detailsEdit();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
			$(this).datagrid("resize",{height:200});
		}
	});

	/* 分配明细列表*/
    allocationDetailsDatagrid = $('#allocationDetailsDatagrid').datagrid({
        url : '<c:url value="/docOrderHeaderController.do?showAllocation"/>',
        method : 'POST',
        toolbar : '',
        idField : 'orderlineno',
        title : '',
        border : false,
        fitColumns : false,
        nowrap : false,
        striped : true,
        collapsible : false,
        rownumbers : true,
        singleSelect : true,
        columns : [[
            {field: 'location',	title: '库位',		width: 120 },
            {field: 'sku',				title: '商品编码',		width: 130 },
            {field: 'skuName',			title: '商品名称',		width: 130 },
            {field: 'qty',		title: '分配数',		width: 80 },
            {field: 'qtyEach',		title: '分配件数',		width: 80 },
            {field: 'qtypickedEach',		title: '拣货数',		width: 80 },
            {field: 'qtyshippedEach',		title: '发货数',		width: 80 },
            {field: 'status',		title: '状态',		width: 80 ,formatter:sostatusFormatter},
            {field: 'pickName',	title: '包装',		width: 80 }
        ]],
        onDblClickCell: function(index,field,value){
            detailsEdit();
        },
        onRowContextMenu : function(event, rowIndex, rowData) {
        },
        onLoadSuccess:function(data){
            $(this).datagrid('unselectAll');
            $(this).datagrid("resize",{height:200});
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

	$('#ordertime').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });

	$('#ordertimeTo').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	
	/* 产品编码限定大写字母 */
	$("#ezuiSkuDataDialog #sku").textbox('textbox').css('text-transform','uppercase');
	$("#ezuiDetailsForm #sku").textbox('textbox').css('text-transform','uppercase');
	
	//订单信息弹框
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		left:0,
	    top:0,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
	
	//订单明细弹框
	ezuiDetailsDialog = $('#ezuiDetailsDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDetailsDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiDetailsForm);
		}
	}).dialog('close');
	
	//客户选择弹框
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');

	//商品选择弹框
	ezuiSkuDataDialog = $('#ezuiSkuDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
		},
		onClose : function() {
		}
	}).dialog('close');

	//库位选择弹框
	ezuiLocDataDialog = $('#ezuiLocDataDialog').dialog({
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
	
	//批量操作返回结果
	ezuiOperateResultDataDialog = $('#ezuiOperateResultDataDialog').dialog({
		modal : true,
		title : '批量操作',
		buttons : '#ezuiOperateResultDataDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiOperateResultDataForm);
		}
	}).dialog('close');
	/* 控件初始化end */
});

/* 查询条件清空按钮 */
var ezuiToolbarClear = function(){
	$("#orderno").textbox('clear');
	$("#customerid").textbox('clear');
	$("#soreference1").textbox('clear');
	$("#sostatus").combobox('clear');
	$("#sostatusTo").combobox('clear');
	$("#ordertype").combobox('clear');
	$("#releasestatus").combobox('clear');
	$("#ordertime").datetimebox({
		value:ordertimeDate(new Date())
	});
	$("#ordertimeTo").datetimebox({
		value:ordertimeDateTo(new Date())
	});
	$("#sostatusCheck").attr("checked",false);
};

/* 获取起始日期 */
var ordertimeDate = function(date){
	var day = date.getDate() - 7 > 9 ? date.getDate() - 7 : "0" + date.getDate() - 7;
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 00:00';
};
/* 获取结束日期 */
var ordertimeDateTo = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 23:59';
};

/* 新增按钮 */
var add = function(){
	processType = 'add';
	$('#docOrderHeaderId').val(0);
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
	$('#ezuiForm #soreference1').textbox({editable:true});
	$('#ezuiForm #soreference2').textbox({editable:true});
	$('#ezuiForm #consigneename').textbox({editable:true});
	$('#ezuiForm #cTel1').textbox({editable:true});
	$('#ezuiForm #cAddress1').textbox({editable:true});
	$('#ezuiForm #notes').textbox({editable:true});
	$("#ezuiForm #sostatus").combobox('setValue','00').combo('readonly', true);
	$("#ezuiForm #releasestatus").combobox('setValue','Y').combo('readonly', false);
	$("#ezuiForm #ordertype").combobox('setValue','SO').combo('readonly', false);
	$("#ezuiForm #ezuiBtn_orderCommit").linkbutton('enable');
	ezuiDetailsDatagrid.datagrid('load',{orderno:'-1'});
	ezuiDialog.dialog('open');
};

/* 编辑按钮 */
var edit = function(srow){
	processType = 'edit';
	$('#docOrderHeaderId').val(0);
	var row = srow || ezuiDatagrid.datagrid('getSelected');
	console.log(srow);
	if(row){
		ezuiForm.form('load',{
			customerid : row.customerid,
			orderno : row.orderno,
			soreference1 : row.soreference1,
			soreference2 : row.soreference2,
			sostatus : row.sostatus,
			consigneename: row.consigneename,
			cAddress1 : row.cAddress1,
			cTel1 : row.cTel1,
			releasestatus : row.releasestatus,
			ordertype : row.ordertype,
			ordertime : row.ordertime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			lastshipmenttime : row.lastshipmenttime,
			notes : row.notes,
            carrierid:row.carrierid,
            userdefine1:row.userdefine1,
            userdefine2:row.userdefine2,
            consigneeid:row.consigneeid

		});
		if (row.sostatus == '90' || row.sostatus == '99') {
			$("#ezuiForm #customerid").textbox({
				editable:false,
				icons:[]
			});
			$("#ezuiForm #sostatus").combo('readonly', true);
			$('#ezuiForm #soreference1').textbox({editable:false});
			$('#ezuiForm #soreference2').textbox({editable:false});
			$('#ezuiForm #consigneename').textbox({editable:false});
			$('#ezuiForm #cTel1').textbox({editable:false});
			$('#ezuiForm #cAddress1').textbox({editable:false});
			$('#ezuiForm #notes').textbox({editable:false});
			$("#ezuiForm #sostatus").combo('readonly', true);
			$("#ezuiForm #releasestatus").combo('readonly', true);
			$("#ezuiForm #ordertype").combo('readonly', true);
			$("#ezuiForm #ezuiBtn_orderCommit").linkbutton('disable');
		} else {
			if (row.addwho == 'EDI') {
				$('#ezuiForm #soreference1').textbox({editable:false});
				$('#ezuiForm #soreference2').textbox({editable:false});
			} else {
				$('#ezuiForm #soreference1').textbox({editable:true});
				$('#ezuiForm #soreference2').textbox({editable:true});
			}
			$("#ezuiForm #customerid").textbox({
				editable:false,
				icons:[]
			});
			$("#ezuiForm #sostatus").combo('readonly', true);
			$('#ezuiForm #consigneename').textbox({editable:true});
			$('#ezuiForm #cTel1').textbox({editable:true});
			$('#ezuiForm #cAddress1').textbox({editable:true});
			$('#ezuiForm #notes').textbox({editable:true});
			$("#ezuiForm #sostatus").combo('readonly', true);
			$("#ezuiForm #releasestatus").combo('readonly', false);
			$("#ezuiForm #ordertype").combo('readonly', false);
			$("#ezuiForm #ezuiBtn_orderCommit").linkbutton('enable');
		}
		ezuiDetailsDatagrid.datagrid('load',{orderno:row.orderno});
        allocationDetailsDatagrid.datagrid('load',{ordero:row.orderno})
		$('#ezuiDetailsDatagrid').parent().parent().parent().show();
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	};
};

/* 删除按钮 */
var del = function(){
    var operateResult = '';
    var checkedItems = ezuiDatagrid.datagrid('getSelections');
    $.each(checkedItems, function(index, item){
        console.log(item);
        $.ajax({
            async: false,
            url : 'docOrderHeaderController.do?delete',
            data : {orderno : item.orderno},
            type : 'POST',
            dataType : 'JSON',
            success : function(result){
                var msg = '';
                try {
                    msg = result.msg;
                    if (msg == '000') {
                        operateResult = operateResult + "订单编号：" + item.orderno + ",";
                        operateResult = operateResult + "处理成功" + "\n";
                    } else {
                        operateResult = operateResult + "订单编号：" + item.orderno + ",";
                        operateResult = operateResult + "处理时错误：" + msg + "\n";
                    };
                } catch (e) {
                    msg = '<spring:message code="common.message.data.delete.failed"/>';
                };
            }
        });
    })
    if (operateResult != '') {
        $('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
        $('#ezuiOperateResultDataDialog').panel({title: "批量操作：分配"});
        ezuiOperateResultDataDialog.dialog('open');
        ezuiDatagrid.datagrid('reload');
    };

	/*var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		if (row.sostatus != '00') {
			$.messager.alert('提示','此状态下不能删除订单!','info');
			return;
		} else if (row.addwho == 'EDI') {
			$.messager.alert('提示','EDI订单,不能删除!','info');
			return;
		} else {
			$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
				if(confirm){
					$.ajax({
						url : 'docOrderHeaderController.do?delete',
						data : {orderno : row.orderno},
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
		}
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	};*/
};

/* 分配按钮 */
var allocation = function(){
	var operateResult = '';
	var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
	$.each(checkedItems, function(index, item){
		if (item.sostatus >= '50') {
			operateResult = operateResult + "订单编号：" + item.orderno + ",";
			operateResult = operateResult + "处理时错误：订单此状态不能操作分配" + "\n";
		} else {
			$.ajax({
				async: false,
				url : 'docOrderHeaderController.do?allocation',
				data : {orderNo : item.orderno},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
						msg = result.msg;
						if (msg == '000') {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理成功" + "\n";
						} else {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理时错误：" + msg + "\n";
						};
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					};
				}
			});
		};
	});
	if (operateResult != '') {
		$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
		$('#ezuiOperateResultDataDialog').panel({title: "批量操作：分配"});
		ezuiOperateResultDataDialog.dialog('open');
		ezuiDatagrid.datagrid('reload');
	};
};

/* 取消分配按钮 */
var deAllocation = function(){
	var operateResult = '';
	var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
	$.each(checkedItems, function(index, item){
		if (item.sostatus >= '50') {
			operateResult = operateResult + "订单编号：" + item.orderno + ",";
			operateResult = operateResult + "处理时错误：订单此状态不能取消分配" + "\n";
		} else {
			$.ajax({
				async: false,
				url : 'docOrderHeaderController.do?deAllocation',
				data : {orderNo : item.orderno},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
						msg = result.msg;
						if (msg == '000') {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理成功" + "\n";
						} else {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理时错误：" + msg + "\n";
						};
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					};
				}
			});
		};
	});
	if (operateResult != '') {
		$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
		$('#ezuiOperateResultDataDialog').panel({title: "批量操作：取消分配"});
		ezuiOperateResultDataDialog.dialog('open');
		ezuiDatagrid.datagrid('reload');
	};
};

/* 拣货按钮 */
var picking = function(){
	var operateResult = '';
	var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
	$.each(checkedItems, function(index, item){
		if (item.sostatus == '00' || item.sostatus > '60') {
			operateResult = operateResult + "订单编号：" + item.orderno + ",";
			operateResult = operateResult + "处理时错误：订单此状态不能操作拣货" + "\n";
		} else {
			$.ajax({
				async: false,
				url : 'docOrderHeaderController.do?picking',
				data : {orderNo : item.orderno},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
					    console.log(result);
						msg = result.msg;
						if (msg == '000') {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理成功" + "\n";
						} else {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理时错误：" + msg + "\n";
						};
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					};
				}
			});
		};
	});
	if (operateResult != '') {
		$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
		$('#ezuiOperateResultDataDialog').panel({title: "批量操作：拣货"});
		ezuiOperateResultDataDialog.dialog('open');
		ezuiDatagrid.datagrid('reload');
	};
};

/* 取消拣货按钮 */
var unPicking = function(){
	var operateResult = '';
	var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
	$.each(checkedItems, function(index, item){
		if (item.sostatus <= '40' || item.sostatus > '60') {
			operateResult = operateResult + "订单编号：" + item.orderno + ",";
			operateResult = operateResult + "处理时错误：订单此状态不能取消拣货" + "\n";
		} else {
			$.ajax({
				async: false,
				url : 'docOrderHeaderController.do?unPicking',
				data : {orderNo : item.orderno},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
						msg = result.msg;
						if (msg == '000') {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理成功" + "\n";
						} else {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理时错误：" + msg + "\n";
						};
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					};
				}
			});
		};
	});
	if (operateResult != '') {
		$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
		$('#ezuiOperateResultDataDialog').panel({title: "批量操作：取消拣货"});
		ezuiOperateResultDataDialog.dialog('open');
		ezuiDatagrid.datagrid('reload');
	};
};

/* 取消装箱按钮 */
var unPacking = function(){
	$.messager.confirm('提示', '此操作将会删除所有的装箱明细记录，是否继续?', function(r){
		if (r){
			var operateResult = '';
			var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
			$.each(checkedItems, function(index, item){
				if (item.sostatus != '62' && item.sostatus != '63') {
					operateResult = operateResult + "订单编号：" + item.orderno + ",";
					operateResult = operateResult + "处理时错误：订单此状态不能取消装箱" + "\n";
				} else {
					$.ajax({
						async: false,
						url : 'docOrderHeaderController.do?unPacking',
						data : {orderno : item.orderno},
						type : 'POST',
						dataType : 'JSON',
						success : function(result){
							var msg = '';
							try {
								msg = result.msg;
								if (msg == '000') {
									operateResult = operateResult + "订单编号：" + item.orderno + ",";
									operateResult = operateResult + "处理成功" + "\n";
								} else {
									operateResult = operateResult + "订单编号：" + item.orderno + ",";
									operateResult = operateResult + "处理时错误：" + msg + "\n";
								};
							} catch (e) {
								msg = '<spring:message code="common.message.data.delete.failed"/>';
							};
						}
					});
				};
			});
			if (operateResult != '') {
				$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
				$('#ezuiOperateResultDataDialog').panel({title: "批量操作：取消装箱"});
				ezuiOperateResultDataDialog.dialog('open');
				ezuiDatagrid.datagrid('reload');
			};
		} else {
			return;
		}
	});
};

/* 订单发货按钮 */
var shipment = function(){
	var operateResult = '';
	var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
	$.each(checkedItems, function(index, item){
		if (item.sostatus <= '40' || item.sostatus >= '90') {
			operateResult = operateResult + "订单编号：" + item.orderno + ",";
			operateResult = operateResult + "处理时错误：订单此状态不能操作发货" + "\n";
		} else {
			$.ajax({
				async: false,
				url : 'docOrderHeaderController.do?shipment',
				data : {orderno : item.orderno},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					var msg = '';
					try {
						msg = result.msg;
						if (msg == '000') {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理成功" + "\n";
						} else {
							operateResult = operateResult + "订单编号：" + item.orderno + ",";
							operateResult = operateResult + "处理时错误：" + msg + "\n";
						};
					} catch (e) {
						msg = '<spring:message code="common.message.data.delete.failed"/>';
					};
				}
			});
		};
	});
	if (operateResult != '') {
		$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
		$('#ezuiOperateResultDataDialog').panel({title: "批量操作：发货"});
		ezuiOperateResultDataDialog.dialog('open');
		ezuiDatagrid.datagrid('reload');
	};
};

/* 订单取消按钮 */
var cancel = function(){
	$.messager.confirm('提示', '确认取消勾选订单?', function(r){
		if (r){
			var operateResult = '';
			var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
			$.each(checkedItems, function(index, item){
				if (item.sostatus == '62' || item.sostatus == '63' || item.sostatus == '70' || item.sostatus == '80' || item.sostatus == '90' || item.sostatus == '99') {
					operateResult = operateResult + "订单编号：" + item.orderno + ",";
					operateResult = operateResult + "处理时错误：订单此状态不能操作取消" + "\n";
				} else {
					$.ajax({
						async: false,
						url : 'docOrderHeaderController.do?cancel',
						data : {orderno : item.orderno},
						type : 'POST',
						dataType : 'JSON',
						success : function(result){
							var msg = '';
							try {
								msg = result.msg;
								if (msg == '000') {
									operateResult = operateResult + "订单编号：" + item.orderno + ",";
									operateResult = operateResult + "处理成功" + "\n";
								} else {
									operateResult = operateResult + "订单编号：" + item.orderno + ",";
									operateResult = operateResult + "处理时错误：" + msg + "\n";
								};
							} catch (e) {
								msg = '<spring:message code="common.message.data.delete.failed"/>';
							};
						}
					});
				};
			});
			if (operateResult != '') {
				$('#ezuiOperateResultDataForm #operateResult').textbox('setValue',operateResult);
				$('#ezuiOperateResultDataDialog').panel({title: "批量操作：发货"});
				ezuiOperateResultDataDialog.dialog('open');
				ezuiDatagrid.datagrid('reload');
			}
		} else {
			return;
		}
	});
};

/* 批量打印拣货单按钮 */
var printPacking = function(){
	orderList = null;
	var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
	$.each(checkedItems, function(index, item){
		if (orderList == null) {
			orderList = item.orderno;
		} else {
			orderList = orderList + ',' + item.orderno;
		}
	});
	if (orderList == null) {
		return;
	}
	console.log(orderList);
	window.open(sy.bp()+"/docOrderHeaderController.do?exportPackingPdf&orderCodeList="+orderList, "Report_"+orderList, "scrollbars=yes,resizable=no");
};


var printAccompanying = function () {
    orderList = null;
    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
    $.each(checkedItems, function(index, item){
        if (orderList == null) {
            orderList = item.orderno;
        } else {
            orderList = orderList + ',' + item.orderno;
        }
    });
    if (orderList == null) {
        return;
    }
    console.log(orderList);
    window.open(sy.bp()+"/docOrderHeaderController.do?exportAccompanyingPdf&orderCodeList="+orderList, "Report_"+orderList, "scrollbars=yes,resizable=no");
}

/* 提交按钮 */
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docOrderHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docOrderHeaderController.do?add"/>';
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
			};
		},
		success : function(data) {
			var msg='';
			try {
				var result = $.parseJSON(data);
				if(result.success){
					if (processType == 'edit') {
						msg = result.msg;
						ezuiDatagrid.datagrid('reload');
					} else {
						msg = result.msg;
						$("#ezuiForm #customerid").textbox({
							editable:false,
							icons:[]
						});
						$('#ezuiForm #orderno').textbox('setValue',result.obj.orderno);
						ezuiDatagrid.datagrid('reload');
						ezuiDetailsDatagrid.datagrid('load',{orderno:result.obj.orderno});
					};
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				};
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			};
		}
	});
};

/* 查询按钮 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		orderno : $('#orderno').val(),
		customerid : $('#customerid').val(),
		soreference1 : $('#soreference1').val(),
		sostatus : $('#sostatus').combobox('getValue'),
		sostatusTo : $('#sostatusTo').combobox('getValue'),
		ordertime : $('#ordertime').datetimebox('getValue'),
		ordertimeTo : $('#ordertimeTo').datetimebox('getValue'),
		ordertype : $('#ordertype').combobox('getValue'),
		releasestatus : $('#releasestatus').combobox('getValue'),
		sostatusCheck : $('#sostatusCheck').is(':checked') == true ? "" : "N"
	});
};

/* 导出start */
var doExport = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		param.put("customerid", $('#customerid').val());
		param.put("orderno", $('#orderno').val());
		param.put("soreference1", $('#soreference1').val());
		param.put("sostatus", $('#sostatus').combobox('getValue'));
		param.put("sostatusTo", $('#sostatusTo').combobox('getValue'));
		param.put("ordertime", $('#ordertime').datetimebox('getValue'));
		param.put("ordertimeTo", $('#ordertimeTo').datetimebox('getValue'));
		param.put("ordertype", $('#ordertype').combobox('getValue'));
		param.put("releasestatus", $('#releasestatus').combobox('getValue'));
		//--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/docOrderHeaderController.do?exportOrderDataToExcel", param);
		downloadCheckTimer = window.setInterval(function () {
			window.clearInterval(downloadCheckTimer);
			$('#'+formId).remove();
			$('#ezuiBtn_export').linkbutton('enable');
			$.messager.progress('close');
			$.messager.show({
				msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
			});
		}, 1000);
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	};
};
/* 导出end */

/* 导入start */
var commitImportData = function(obj){
	ezuiImportDataForm.form('submit', {
		url : '<c:url value="/docOrderHeaderController.do?importExcelData"/>',
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
		var formId = ajaxDownloadFile(sy.bp()+"/docOrderHeaderController.do?exportTemplate", param);
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
			};
		}, 1000);
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	};
};
/* 导入end */

/* 明细新增按钮 */
var detailsAdd = function(){
	if ($('#ezuiForm #orderno').val() == '') {
		return;
	} else {
		if ($('#ezuiForm #sostatus').combobox('getValue') > '20') {
			$.messager.alert('提示','此状态下不能新增明细!','info');
			return;
		} else if ($('#ezuiForm #addwho').val() == 'EDI') {
			$.messager.alert('提示','EDI订单,不能新增明细!','info');
			return;
		} else {
			processType = 'add';
			$('#docOrderDetailsId').val(0);
			$("#ezuiDetailsForm #sku").textbox({
				editable:false,
				icons:[{
					iconCls:'icon-search',
					handler: function(e){
						$("#ezuiSkuDataDialog #sku").textbox('clear');
						ezuiSkuDataClick();
						ezuiSkuDataSearch();
					}
				}]
			});
			$('#ezuiDetailsForm #customerid').textbox('setValue',$('#ezuiForm #customerid').val());
			$('#ezuiDetailsForm #orderno').textbox('setValue',$('#ezuiForm #orderno').val());
			$("#ezuiDetailsForm #orderlineno").numberbox('setValue', 0);
			$("#ezuiDetailsForm #linestatus").combobox('setValue','00').combo('readonly', true);
			$("#ezuiDetailsForm #qtyallocated").numberbox('setValue', 0);
			$("#ezuiDetailsForm #qtypicked").numberbox('setValue', 0);
			$("#ezuiDetailsForm #qtyshipped").numberbox('setValue', 0);
			$('#ezuiDetailsForm #qtyordered').numberbox({editable:true});
			$('#ezuiDetailsForm #grossweight').numberbox({editable:true});
			$('#ezuiDetailsForm #cubic').numberbox({editable:true});
			$('#ezuiDetailsForm #price').numberbox({editable:true});
			$('#ezuiDetailsForm #pickzone').combo('readonly', false);
			$("#ezuiDetailsForm #location").textbox({
				editable:false,
				icons:[{
					iconCls:'icon-search',
					handler: function(e){
						$("#ezuiLocDataDialog #locationid").textbox('clear');
						ezuiLocDataClick();
						ezuiLocDataSearch();
					}
				}]
			});
			$('#ezuiDetailsForm #lotnum').textbox({editable:true});
			$('#ezuiBtn_detailsCommit').linkbutton('enable');
			/* 获取产品信息 */
			$("input",$("#ezuiDetailsForm #qtyordered").next("span")).blur(function(){
				if ($("#ezuiDetailsForm #qtyordered").val() != '') {
					if ($("#ezuiDetailsForm #sku").val() == '') {
						$("#ezuiDetailsForm #qtyordered").numberbox('clear');
					} else {
						$.ajax({
							url : 'basSkuController.do?getSkuInfo',
							data : {customerid : $("#ezuiDetailsForm #customerid").val(),sku : $("#ezuiDetailsForm #sku").val(), qty : $("#ezuiDetailsForm #qtyordered").numberbox('getValue')},
							type : 'POST',
							dataType : 'JSON',
							success : function(result){
								try {
									$("#ezuiDetailsForm #grossweight").numberbox('setValue',result.grossweight);
									$("#ezuiDetailsForm #cubic").numberbox('setValue',result.cube);
									$("#ezuiDetailsForm #price").numberbox('setValue',result.price);
								} catch (e) {
									return;
								};
							}
						});
					};
				};
			});
			ezuiDetailsDialog.dialog('open');
		};
	};
};

/* 明细编辑按钮 */
var detailsEdit = function(){
	if ($('#ezuiForm #orderno').val() == '') {
		return;
	} else {
		processType = 'edit';
		$('#docOrderDetailsId').val(0);
		$("#ezuiDetailsForm #location").textbox({
			editable:false,
			icons:[{
				iconCls:'icon-search',
				handler: function(e){
					$("#ezuiLocDataDialog #locationid").textbox('clear');
					ezuiLocDataClick();
					ezuiLocDataSearch();
				}
			}]
		});
		$("#ezuiDetailsForm #linestatus").combo('readonly', true);
		var row = ezuiDetailsDatagrid.datagrid('getSelected');
		if(row){
			ezuiDetailsForm.form('load',{
				customerid : row.customerid,
				orderno : row.orderno,
				orderlineno : row.orderlineno,
				linestatus : row.linestatus,
				sku : row.sku,
				skuName : row.skuName,
				skuNameE : row.skuNameE,
				alternativesku : row.alternativesku,
				packid : row.packid,
	            qtyordered : row.qtyordered,
	            qtyallocated : row.qtyallocated,
	            qtypicked : row.qtypicked,
	            qtyshipped : row.qtyshipped,
	            cubic : row.cubic,
	            grossweight : row.grossweight,
	            price : row.price,
	            pickzone : row.pickzone,
	            location : row.location,
	            lotnum : row.lotnum,
                lotatt01: row.lotatt01,
                lotatt02: row.lotatt02,
                lotatt03: row.lotatt03,
                lotatt04: row.lotatt04,
                lotatt05: row.lotatt05,
                lotatt06: row.lotatt06,
                lotatt07: row.lotatt07,
                lotatt08: row.lotatt08,
                lotatt09: row.lotatt09,
                lotatt10: row.lotatt10,
                lotatt11: row.lotatt11,
                lotatt12: row.lotatt12,
                lotatt13: row.lotatt13,
                lotatt14: row.lotatt14,
                lotatt15: row.lotatt15,
                lotatt16: row.lotatt16,
                lotatt17: row.lotatt17,
                lotatt18: row.lotatt18,
                supplierIdChose:row.lotatt08


			});
			if (row.linestatus == '00') {
				$("#ezuiDetailsForm #sku").textbox({
					editable:false,
					icons:[{
						iconCls:'icon-search',
						handler: function(e){
							$("#ezuiSkuDataDialog #sku").textbox('clear');
							ezuiSkuDataClick();
							ezuiSkuDataSearch();
						}
					}]
				});
				$('#ezuiDetailsForm #qtyordered').numberbox({editable:true});
				$('#ezuiDetailsForm #grossweight').numberbox({editable:true});
				$('#ezuiDetailsForm #cubic').numberbox({editable:true});
				$('#ezuiDetailsForm #price').numberbox({editable:true});
				$('#ezuiDetailsForm #pickzone').combo('readonly', false);
				$("#ezuiDetailsForm #location").textbox({
					editable:false,
					icons:[{
						iconCls:'icon-search',
						handler: function(e){
							$("#ezuiLocDataDialog #locationid").textbox('clear');
							ezuiLocDataClick();
							ezuiLocDataSearch();
						}
					}]
				});
				$('#ezuiDetailsForm #lotnum').textbox({editable:true});
				$('#ezuiBtn_detailsCommit').linkbutton('enable');
				/* 获取产品信息 */
				$("input",$("#ezuiDetailsForm #qtyordered").next("span")).blur(function(){
					if ($("#ezuiDetailsForm #qtyordered").val() != '') {
						if ($("#ezuiDetailsForm #sku").val() == '') {
							$("#ezuiDetailsForm #qtyordered").numberbox('clear');
						} else {
							$.ajax({
								url : 'basSkuController.do?getSkuInfo',
								data : {customerid : $("#ezuiDetailsForm #customerid").val(),sku : $("#ezuiDetailsForm #sku").val(), qty : $("#ezuiDetailsForm #qtyordered").numberbox('getValue')},
								type : 'POST',
								dataType : 'JSON',
								success : function(result){
									try {
										$("#ezuiDetailsForm #grossweight").numberbox('setValue',result.grossweight);
										$("#ezuiDetailsForm #cubic").numberbox('setValue',result.cube);
										$("#ezuiDetailsForm #price").numberbox('setValue',result.price);
									} catch (e) {
										return;
									};
								}
							});
						};
					};
				});
			} else if (row.linestatus == '90' || row.linestatus == '99') {
				$("#ezuiDetailsForm #sku").textbox({
					editable:false,
					icons:[]
				});
				$('#ezuiDetailsForm #qtyordered').numberbox({editable:false});
				$('#ezuiDetailsForm #grossweight').numberbox({editable:false});
				$('#ezuiDetailsForm #cubic').numberbox({editable:false});
				$('#ezuiDetailsForm #price').numberbox({editable:false});
				$('#ezuiDetailsForm #pickzone').combo('readonly', true);
				$("#ezuiDetailsForm #location").textbox({
					editable:false,
					icons:[]
				});
				$('#ezuiDetailsForm #lotnum').textbox({editable:false});
				$('#ezuiBtn_detailsCommit').linkbutton('disable');
			} else {
				$("#ezuiDetailsForm #sku").textbox({
					editable:false,
					icons:[]
				});
				$('#ezuiDetailsForm #qtyordered').numberbox({editable:false});
				$('#ezuiDetailsForm #grossweight').numberbox({editable:false});
				$('#ezuiDetailsForm #cubic').numberbox({editable:false});
				$('#ezuiDetailsForm #price').numberbox({editable:false});
				$('#ezuiDetailsForm #pickzone').combo('readonly', false);
				$("#ezuiDetailsForm #location").textbox({
					editable:false,
					icons:[{
						iconCls:'icon-search',
						handler: function(e){
							$("#ezuiLocDataDialog #locationid").textbox('clear');
							ezuiLocDataClick();
							ezuiLocDataSearch();
						}
					}]
				});
				$('#ezuiDetailsForm #lotnum').textbox({editable:true});
				$('#ezuiBtn_detailsCommit').linkbutton('enable');
			}
			ezuiDetailsDialog.dialog('open');
		}else{
			$.messager.show({
				msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
			});
		}
	};
};

/* 明细删除按钮 */
var detailsDel = function(){
	if ($('#ezuiForm #orderno').val() == '') {
		return;
	} else {
		var row = ezuiDetailsDatagrid.datagrid('getSelected');
		if(row){
			if (row.linestatus != '00') {
				$.messager.alert('提示','此状态下不能删除明细!','info');
				return;
			} else if (row.addwho == 'EDI') {
				$.messager.alert('提示','EDI订单,不能删除明细!','info');
				return;
			} else {
				$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
					if(confirm){
						$.ajax({
							url : 'docOrderDetailsController.do?delete',
							data : {orderno : row.orderno, orderlineno : row.orderlineno},
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
									ezuiDetailsDatagrid.datagrid('reload');
								};
							}
						});
					};
				});
			};
		}else{
			$.messager.show({
				msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
			});
		}
	};
};

/* 明细提交按钮 */
var detailsCommit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docOrderDetailsController.do?edit"/>';
	}else{
		url = '<c:url value="/docOrderDetailsController.do?add"/>';
	}
	ezuiDetailsForm.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiDetailsForm.form('validate')){
				$.messager.progress({
					text : '<spring:message code="common.message.data.processing"/>', interval : 100
				});
				return true;
			}else{
				return false;
			};
		},
		success : function(data) {
			var msg='';
			try {
				var result = $.parseJSON(data);
				if(result.success){
					if (processType == 'edit') {
						msg = result.msg;
						ezuiDetailsDatagrid.datagrid('reload');
					} else {
						msg = result.msg;
						ezuiDetailsDatagrid.datagrid('reload');
					}
					ezuiDetailsDialog.dialog('close');
				}else{
					msg = '<font color="red">' + result.msg + '</font>';
				};
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				$.messager.show({
					msg : msg, title : '<spring:message code="common.message.prompt"/>'
				});
				$.messager.progress('close');
			};
		}
	});
};


/* 客户选择弹框查询 */
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	});
};
/* 客户选择弹框清空 */
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};

/* 客户选择弹框-主界面 */
var ezuiCustDataClick = function(){
    $("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combo('readonly', true);
    $("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
	url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
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
	queryParams:{
		customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	},
	idField : 'customerid',
	columns : [[
				{field: 'customerid',	title: '客户代码',	width: 15},
				{field: 'descrC',		title: '中文名称',	width: 50},
				{field: 'descrE',		title: '英文名称',	width: 50},
				{field: 'customerTypeName',	title: '类型',	width: 15},
				{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
					return rowData.activeFlag == '1' ? '是' : '否';
	            }}
			]],
	onDblClickCell: function(index,field,value){
		selectCust();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});

	ezuiCustDataDialog.dialog('open');
};
/* 客户选择-主界面 */
var selectCust = function(){
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	};
};

/* 客户选择弹框-订单信息界面 */
var ezuiCustDataDialogClick = function(){
    $("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combo('readonly', true);
    $("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
	url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
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
	queryParams:{
		customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	},
	idField : 'customerid',
	columns : [[
				{field: 'customerid',		title: '客户代码',	width: 15},
				{field: 'descrC',			title: '中文名称',	width: 50},
				{field: 'descrE',			title: '英文名称',	width: 50},
				{field: 'customerTypeName',	title: '类型',	width: 15},
				{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
					return rowData.activeFlag == '1' ? '是' : '否';
	            }}
			]],
	onDblClickCell: function(index,field,value){
		selectDialogCust();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});

	ezuiCustDataDialog.dialog('open');
};
/* 客户选择-订单信息界面 */
var selectDialogCust = function(){
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDialog #customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	};
};


/* 商品选择弹框查询 */
var ezuiSkuDataSearch = function(){
	ezuiSkuDataDialogId.datagrid('load', {
		customerid : $("#ezuiSkuDataDialog #customerid").textbox("getValue"),
		sku : $("#ezuiSkuDataDialog #sku").textbox("getValue"),
		activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
	});
};
/* 商品选择弹框清空 */
var ezuiSkuToolbarClear = function(){
	$("#ezuiSkuDataDialog #sku").textbox('clear');
};
/* 商品选择弹框 */
var ezuiSkuDataClick = function(){
	ezuiSkuDataDialogId = $('#ezuiSkuDataDialogId').datagrid({
		url:'<c:url value="/basSkuController.do?showDatagrid"/>',
		method:'POST',
		toolbar:'#ezuiSkuToolbar',
		title:'商品档案',
		pageSize:50,
		pageList:[50, 100, 200],
		fit:true,
		border:false,
		fitColumns:false,
		nowrap:false,
		striped:true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		queryParams:{
			customerid : $("#ezuiDetailsForm #customerid").textbox("getValue"),
			activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
		},
		idField : 'sku',
		columns : [[
					{field: 'customerid',	title: '客户代码',	width: 80},
					{field: 'sku',			title: '商品编码',	width: 120},
					{field: 'descrC',		title: '中文名称',	width: 160},
					{field: 'descrE',		title: '英文名称',	width: 160},
					{field: 'activeFlag',	title: '激活',	width: 40, formatter:function(value,rowData,rowIndex){
						return rowData.activeFlag == '1' ? '是' : '否';
		            }},
					{field: 'alternateSku1',title: '商品条码',	width: 120},
					{field: 'packid',		title: '包装代码',	width: 80},
					{field: 'qty',			title: '库存数',	width: 60},
					{field: 'qtyallocated',	title: '分配数',	width: 60},
					{field: 'qtyonhold',	title: '冻结数',	width: 60}
				]],
		onDblClickCell: function(index,field,value){
			selectSku();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiSkuDataDialog #customerid").textbox('setValue',$("#ezuiDetailsForm #customerid").textbox("getValue")).textbox('readonly', true);
	$("#ezuiSkuDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiSkuDataDialog.dialog('open');
};
/* 商品选择 */
var selectSku = function(){
	var row = ezuiSkuDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDetailsForm #sku").textbox('setValue',row.sku);
		$("#ezuiDetailsForm #skuName").textbox('setValue',row.descrC);
		$("#ezuiDetailsForm #skuNameE").textbox('setValue',row.descrE);
		$("#ezuiDetailsForm #alternativesku").textbox('setValue',row.alternateSku1);
		$("#ezuiDetailsForm #packid").textbox('setValue',row.packid);
		$("#ezuiDetailsForm #qtyordered").numberbox('clear');
		$("#ezuiDetailsForm #grossweight").numberbox('clear');
		$("#ezuiDetailsForm #cubic").numberbox('clear');
		$("#ezuiDetailsForm #price").numberbox('clear');

		$("#ezuiDetailsForm #lotatt04").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/docOrderHeaderController.do?getLotAttBySkuCustomerId&sku='+row.sku+"&customerId="+$("#ezuiDetailsForm #customerid").textbox("getValue"),
            valueField:'id',
            textField:'value',
            width:110,
            required:true,
            onSelect:function (record) {
               /* console.log(record);
                $("#ezuiDetailsForm #lotatt09").combobox('setValue',record.option.lotatt09);
                $("#ezuiDetailsForm #lotatt10").textbox('setValue',record.option.lotatt10);
                $("#ezuiDetailsForm #lotatt08").textbox('setValue',record.option.lotatt15);
                $("#ezuiDetailsForm #lotatt14").textbox('setValue',record.option.lotatt14);*/
            }
		});
		ezuiSkuDataDialog.dialog('close');
	};
};

/* 库位选择弹框查询 */
var ezuiLocDataSearch = function(){
	ezuiLocDataDialogId.datagrid('load', {
		locationid : $("#ezuiLocDataDialog #locationid").textbox("getValue")
	});
};
/* 库位选择弹框清空 */
var ezuiLocToolbarClear = function(){
	$("#ezuiLocDataDialog #locationid").textbox('clear');
	$("#ezuiLocDataDialog #locationcategory").combobox('clear');
};
/* 库位选择弹框 */
var ezuiLocDataClick = function(){
	ezuiLocDataDialogId = $('#ezuiLocDataDialogId').datagrid({
		url:'<c:url value="/basLocationController.do?showDatagrid"/>',
		method:'POST',
		toolbar:'#ezuiLocToolbar',
		title:'库位选择',
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
		idField : 'locationid',
		columns : [[
					{field: 'locationid',				title: '库位',	width: 80},
					{field: 'locationusageName',		title: '库位使用',	width: 100},
					{field: 'locationcategoryName', 	title: '库位类型',	width: 100},
					{field: 'locationattributeName',	title: '库位属性',	width: 100},
					{field: 'putawayzoneName',			title: '上架区',	width: 100},
					{field: 'pickzoneName',				title: '拣货区',	width: 100}
				]],
		onDblClickCell: function(index,field,value){
			selectLocation();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	ezuiLocDataDialog.dialog('open');
};
/* 库位选择 */
var selectLocation = function(){
	var row = ezuiLocDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDetailsForm #location").textbox('setValue',row.locationid);
		ezuiLocDataDialog.dialog('close');
	};
};

var docOrderRowStyler = function (index,row) {
    if(row.sostatus == "30"){
        return 'color:red;';
    }
}

var refOutDialog;
//初始化
$(function () {
    /**
     * 引用出库
     */
    refOutDialog = $('#refOutDialog').dialog({
        modal : true,
        title : '引用出库',
        buttons : '',
		width:250,
		height:100,
        onOpen : function() {
        },
        onClose : function() {

        }
    }).dialog('close');

	$("#refOutNo").combobox({
        panelHeight: 'auto',
        editable: false,
        url:'/docOrderHeaderController.do?getRefOutOrder',
        valueField: 'id',
        textField: 'value',
		width:150
	})
})

function showRefOut() {
    var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
    if(checkedItems.length>1 || checkedItems.length == 0){
        showMsg("请选择一张单据进行引用");
        return;
	}
    refOutDialog.dialog("open");
    $("#refOutNo").combobox("clear");
}

function closeRefOut() {
    refOutDialog.dialog("close");
}

function doRefOut() {
    var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
    $.ajax({
        url : 'docOrderHeaderController.do?doRefOut',
        data : {orderno : checkedItems[0].orderno,refOrderno:$("#refOutNo").combobox("getValue")},
        type : 'POST',
        dataType : 'JSON',
        success : function(result){
            try {

               showMsg(result.msg)
                refOutDialog.dialog("close");
            } catch (e) {
                return;
            };
        }
    });

}

</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
    <input type="hidden" id="pdlist" name="pdlist" value="">
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>SO编号</th><td><input type='text' id='orderno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>货主</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>客户单号</th><td><input type='text' id='soreference1' class='easyui-textbox' size='16' data-options=''/></td>
                            <th>定向入库单号</th><td><input type='text' id='soreference2' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>省</th>
							<td>
								<!--<input type='text' id='cProvince' class='easyui-textbox' size='16' data-options=''/>-->
								<input id="cc1" class="easyui-combobox" name="cProvince"  editable="false" data-options="
								valueField: 'name',
								textField: 'name',
								width:110,
								url: 'gspReceivingAddressController.do?getArea&pid=0',
								onSelect: function(rec){
									$('#cc2').combobox('clear');
									$('#cc3').combobox('clear');
									var url= 'gspReceivingAddressController.do?getArea&pid='+rec.id;
									$('#cc2').combobox('reload',url);
							}">
							</td>
							<th>市</th>
							<td>
								<!--<input type='text' id='cCity' class='easyui-textbox' size='16' data-options=''/>-->
								<input id="cc2" class="easyui-combobox" name="cCity" editable="false" data-options="
								valueField: 'name',
								textField: 'name',
								width:110,
								onSelect: function(rec){
									$('#cc2').combobox('reload', url);
									$('#cc3').combobox('clear');
									var url = 'gspReceivingAddressController.do?getArea&pid='+rec.id;
									$('#cc3').combobox('reload', url);
								}">
							</td>
							<th>区</th>
							<td>
								<!--<input type='text' id='cCountry' class='easyui-textbox' size='16' data-options=''/>-->
								<input id="cc3" class="easyui-combobox" name="district"  editable="false" data-options="
								width:110,
								valueField:'name',
								textField:'name'">
							</td>
							<th>地址</th><td><input type='text' id='cAddress1' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>收货人</th><td><input type='text' id='cContact' class='easyui-textbox' size='16' data-options=''/></td>
							<th>收货电话</th><td><input type='text' id='cTel1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>承运人</th><td><input type='text' id='carrierContact' class='easyui-textbox' size='16' data-options=''/></td>
							<th>快递单号</th><td><input type='text' id='cAddress4' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>订单状态</th><td><input type='text' id='sostatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							
							<th>至</th><td><input type='text' id='sostatusTo' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							<th>订单类型</th><td><input type='text' id='ordertype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																editable: false,
																																url:'<c:url value="/docOrderHeaderController.do?getOrderTypeCombobox"/>',
																																valueField: 'id',
																																textField: 'value'"/></td>
                            <th>释放状态</th><td><input type='text' id='releasestatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docOrderHeaderController.do?getReleasestatusCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/></td>
						</tr>
						<tr>
							<th>订单创建时间</th><td><input type='text' id='ordertime' class='easyui-datetimebox' size='16' data-options="editable:false,
																																	required:true,
																																	showSeconds:false,
																																	value:ordertimeDate(new Date())"/></td>
							<th>至</th><td><input type='text' id='ordertimeTo' class='easyui-datetimebox' size='16' data-options="editable:false,
																																required:true,
																																showSeconds:false,
																																value:ordertimeDateTo(new Date())"/></td>
							<th colspan="2" style="text-align: left"><input id="sostatusCheck" type="checkbox" onclick=""><label for="sostatusCheck">显示关闭/取消订单</label></th>
							<td colspan="2">
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
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
					<a onclick='allocation();' id='ezuiBtn_allocation' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message code='common.button.allocation'/></a>
					<a onclick='deAllocation();' id='ezuiBtn_cancelAllocation' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelAllocation'/></a>
 					<a onclick='picking();' id='ezuiBtn_picking' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message code='common.button.picking'/></a>
 					<a onclick='unPicking();' id='ezuiBtn_cancelPicking' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelPicking'/></a>
                    <a onclick='shipment();' id='ezuiBtn_shipment' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message code='common.button.shipment'/></a>
                   <!-- <a onclick='unPacking();' id='ezuiBtn_cancelPacking' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'>关闭订单（D）</a> -->
					<a onclick='cancel();' id='ezuiBtn_cancel' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelOrder'/></a>
                    <a onclick='showRefOut()' id='ezuiBtn_ref' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>引用出库</a>

                    <a onclick='printPacking();' id='ezuiBtn_PrintPacking' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印拣货单</a>
                    <a onclick='printAccompanying();' id='ezuiBtn_PrintAccompanying' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印随货清单</a>
					<a onclick='javascript:void(0);' id='ezuiBtn_PrintExpress' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印快递单</a>
                    <!--<a onclick='print();' id='ezuiBtn_print' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>生成波次（D）</a>-->
				</div>
			</div>
			<table id='ezuiDatagrid'></table>
		</div>
	</div>
	<div id='ezuiDialogBtn'>
		<%-- <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiDetailsDialogBtn'>
		<a onclick='detailsCommit();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
	<div id='ezuiDetailsMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='detailsAdd();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.skuAdd'/></div>
		<div onclick='detailsDel();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.skuDelete'/></div>
		<div onclick='detailsEdit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.skuEdit'/></div>
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
	
	<!-- 批量操作返回start -->
	<div id='ezuiOperateResultDataDialog' class='easyui-dialog' style='padding: 10px;'>
		<form id='ezuiOperateResultDataForm' method='post' enctype='multipart/form-data'>
			<table>	
				<tr>
					<td><input id='operateResult' class="easyui-textbox" size='100' style="height:150px" data-options="editable:false,multiline:true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiOperateResultDataDialogBtn'>
		<a onclick='ezuiDialogClose("#ezuiOperateResultDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>

	<!--引用出库 -->
	<div id="refOutDialog" style="display: none;text-align: center">
		<table width="100%">
			<tr>
				<td>SO编号</td>
				<td>
					<input id="refOutNo" type="text"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a onclick='closeRefOut()' id='closeRefOut' class='easyui-linkbutton' data-options='' href='javascript:void(0);'>关闭</a>
					<a onclick='doRefOut()' id='doRefOut' class='easyui-linkbutton' data-options='' href='javascript:void(0);'>引用</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 批量操作返回end -->
	
	<c:import url='/WEB-INF/jsp/docOrderHeader/dialog.jsp' />
	<c:import url='/WEB-INF/jsp/docOrderHeader/custDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docOrderHeader/detailsDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docOrderHeader/skuDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docOrderHeader/locDialog.jsp' />
</body>
</html>
