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
var ezuiMenu;        //右键菜单

var ezuiForm;        //生成盘点任务dialog form
var ezuiDialog;      //生成盘点任务dialog
var ezuiDetailsDatagrid;//生成盘点任务datagrid

var ezuiDialogGuKe;      //生成盘点任务dialog 骨科
var ezuiDetailsDatagridGuKe;      //生成盘点任务dialog 骨科


var ShowEzuiDialog;//按钮查看盘点任务datagrid
var ShowEzuiDetailsDatagrid;//按钮查看盘点任务datagrid

var ezuiDatagrid;    //主页datagird

var closeDialog;       //关闭计划dialog form
var closeDialogForm;     //关闭计划dialog

var ezuiImportDataDialog; //导入
var ezuiImportDataForm;   //导入form


var ezuiCustDataDialog;        //货主
var ezuiCustDataDialogId;      //货主
var ezuiSkuDataDialog;         //产品名称选择框
var ezuiSkuDataDialogId;       //产品名称选择框
var productDialog_couRequestHeader;//产品代码选择框
var productDialog_couRequestHeaderS;//show产品代码选择框
var ezuiLocDataDialog;         //show库位选择框
var ezuiLocDataDialogId;       //show库位选择框
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
	ezuiImportDataForm=$('#ezuiImportDataForm').form();  //导入form
	closeDialogForm = $('#closeDialogForm').form();   //关闭计划dialog form
//主页Datagrid
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/couRequestHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '盘点任务',
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
		singleSelect:false,
		columns : [[
			{field:'ck',checkbox:true},
			{field: 'cycleCountno',		title: '盘点单号',	width: 61 },
			{field: 'status',		title: '状态',	width: 61,formatter:Cou_RequeststatusFormatter },
			{field: 'fuzzyc',		title: '条件',	width: 61 },
			{field: 'addtime',		title: '创建时间',	width: 61 },
			{field: 'addwho',		title: '创建人',	width: 61 },
			{field: 'starttime',		title: '开始时间',	width: 61 },
			{field: 'endtime',		title: '结束时间',	width: 61 },
			{field: 'notes',		title: '备注',	width: 61 },
			// {field: 'userdefine1',		title: '待输入栏位8',	width: 61 },
			// {field: 'userdefine2',		title: '待输入栏位9',	width: 61 },
			// {field: 'userdefine3',		title: '待输入栏位10',	width: 61 },
			// {field: 'userdefine4',		title: '待输入栏位11',	width: 61 },
			// {field: 'userdefine5',		title: '待输入栏位12',	width: 61 }
		]],
		onDblClickCell: function(index,field,value){
			ajaxBtn($('#menuId').val(), '<c:url value="/couRequestHeaderController.do?getBtn"/>', ezuiMenu);
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
			$(this).datagrid('unselectAll');
		}
	});
//按钮查看盘点任务datagrid初始化
	ShowEzuiDetailsDatagrid = $('#ShowEzuiDetailsDatagrid').datagrid({
		url: '<c:url value="/couRequestDetailsController.do?getcouRequestInfoBycycleCountno"/>',
		method: 'POST',
		toolbar: '#ShowEzuiToolbar',
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		rownumbers: true,
		singleSelect:true,
		columns: [[
			// {field: 'cycleCountlineno', title: '行号', width: 71},
			{field: 'customerid', title: '货主', width: 71},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'reservedfield01', title: '产品名称', width: 170},
			{field: 'locationid', title: '库位', width: 100},
			{field: 'qtyInv', title: '库存件数', width: 70},
			{field: 'qtyInvEach', title: '库存数量', width: 70},
			{field: 'qtyAct', title: '实际盘点件数', width: 80},
			{field: 'userdefined1', title: '盘点差异', width: 80},
			{field: 'lotatt04', title: '生产批号', width: 100},
			{field: 'lotatt05', title: '序列号', width: 100},
			{field: 'productLineName', title: '产品线', width: 100},
			{field: 'userdefined2', title: '备注', width: 100},
			{field: 'addtime', title: '创建时间', width: 150},
			{field: 'addwho', title: '创建人', width: 70},
			{field: 'edittime', title: '盘点日期', width: 100},
			{field: 'editwho', title: '盘点人', width: 70},
			{field: 'userdefined3', title: '复核人', width: 70},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
		},
		onClose:function(){
		}
	});
//生成盘点任务dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		top:0,
		left:200,
		title : '生成任务',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiToolbar);
		}
	}).dialog('close');
//生成盘点任务dialog初始化 骨科
	ezuiDialogGuKe = $('#ezuiDialogGuKe').dialog({
		modal : true,
		top:0,
		left:200,
		title : '生成任务(骨科)',
		buttons : '#ezuiDialogGuKeBtn',
		onClose : function() {
			ezuiFormClear(ezuiToolbarGuKe);
		}
	}).dialog('close');
//按钮查看盘点任务dialog初始化
	ShowEzuiDialog = $('#ShowEzuiDialog').dialog({
		modal : true,
		top:0,
		left:200,
		buttons : '#ShowEzuiDialogBtn',
		onClose : function() {
		}
	}).dialog('close');
//关闭计划dialog
	closeDialog = $('#closeDialog').dialog({
		modal : true,
		width:300,
		height:200,
		title : '关闭计划',
		buttons : '#closeDialogBtn',
		onClose : function() {
			ezuiFormClear(closeDialogForm);
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
//产品名称选择弹框
	ezuiSkuDataDialog = $('#ezuiSkuDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//货主查询弹框初始化
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//库位选择弹框
	ezuiLocDataDialog = $('#ezuiLocDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//产品代码控件初始化 载入公用弹窗页面
	$("#ezuiToolbar #sku").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				productDialog_couRequestHeader = $('#ezuiSkuSearchDialog').dialog({
					modal: true,
					title: '查询',
					href: sy.bp() + "/basSkuController.do?toSearchDialog&target=couRequestHeader",
					width: 850,
					height: 500,
					cache: false,
					onClose: function () {

					}
				})
			}
		}]
	});
//查询条件货主字段初始化
	$("#ezuiToolbar #customerid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
//生成盘点任务库位弹窗
    $("#ezuiToolbar #locationid").textbox({
        icons: [{
            iconCls: 'icon-search',
            handler: function (e) {
                $("#ezuiLocDataDialog #locationid").textbox('clear');
                ezuiLocDataClick();
                ezuiLocDataSearch();
            }
        }]
    });

//show产品代码控件初始化 载入公用弹窗页面
	$("#ShowEzuiToolbar #sku").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				productDialog_couRequestHeaderS = $('#ezuiSkuSearchDialogS').dialog({
					modal: true,
					title: '查询',
					href: sy.bp() + "/basSkuController.do?toSearchDialog&target=couRequestHeaderS",
					width: 850,
					height: 500,
					cache: false,
					onClose: function () {

					}
				})
			}
		}]
	});
//show查询条件货主字段初始化
	$("#ShowEzuiToolbar #customerid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick("S");
				ezuiCustDataDialogSearch();
			}
		}]
	});
//show库位
	$("#ShowEzuiToolbar #locationid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiLocDataDialog #locationid").textbox('clear');
				ezuiLocDataClick("S");
				ezuiLocDataSearch();
			}
		}]
	});
//show产品名称查询
	$("#ShowEzuiToolbar #reservedfield01").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiSkuDataDialog #sku").textbox('clear');
				ezuiSkuDataClick();
				ezuiSkuDataSearch();
			}
		}]
	});

//货主带出产品线
	$("#ezuiToolbar #customerid").textbox({
		onChange:function(){
			var customerid = $("#ezuiToolbar #customerid").textbox('getValue');
			if(customerid !=null && ($.trim(customerid).length>0)){
				$("#ezuiToolbar #productLineName").combobox({
					panelHeight: 'auto',
					url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid,
					valueField:'id',
					textField:'value',
					onLoadSuccess:function () {
					}
				});
			}else{
				$("#ezuiToolbar #productLineName").combobox({
					panelHeight: 'auto',
					url:"/productLineController.do?getCombobox",
					valueField:'id',
					textField:'value',
					onLoadSuccess:function () {
					}
				});
			}
		}
	});
//货主带出产品线
	$("#ShowEzuiToolbar #customerid").textbox({
		onChange:function(){
			var customerid = $("#ShowEzuiToolbar #customerid").textbox('getValue');
			if(customerid !=null && ($.trim(customerid).length>0)){
				$("#ShowEzuiToolbar #productLineName").combobox({
					panelHeight: 'auto',
					url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid,
					valueField:'id',
					textField:'value',
					onLoadSuccess:function () {
					}
				});
			}else{
				$("#ShowEzuiToolbar #productLineName").combobox({
					panelHeight: 'auto',
					url:"/productLineController.do?getCombobox",
					valueField:'id',
					textField:'value',
					onLoadSuccess:function () {
					}
				});
			}
		}
	});
//骨科盘点时间初始化
    $('#SoTimeStart').datetimebox('calendar').calendar({
        validator: function(date){
            var now = new Date();
            var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
//骨科盘点时间初始化
    $('#SoTimeEnd').datetimebox('calendar').calendar({
        validator: function(date){
            var now = new Date();
            var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
});
//增加
var add = function(){
	processType = 'add';
	$('#docMtHeaderId').val(0);
	ezuiDialog.dialog('open');
};
//编辑
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			mtno : row.mtno,
			mtstatus : row.mtstatus,
			mttype : row.mttype,
			fromdate : row.fromdate,
			todate : row.todate,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			remark : row.remark,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//删除任务单
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	<%--if(row.mtstatus!="00"){--%>
	<%--	$.messager.show({--%>
	<%--		msg : "只有养护状态为任务创建的状态才能删除!", title : '<spring:message code="common.message.prompt"/>'--%>
	<%--	});--%>
	<%--	return;--%>
	<%--}--%>
	if(row){
		var cycleCountno=row.cycleCountno;
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'couRequestHeaderController.do?delete',
					data : {cycleCountno : cycleCountno},
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
//取消任务单
var cancel = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		var cycleCountno=row.cycleCountno;
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确定取消当前任务单!', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'couRequestHeaderController.do?cancel',
					data : {cycleCountno : cycleCountno},
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
//生成任务
var GenerateInventoryPlan = function(){
	//生成盘点任务datagrid初始化
	ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
		url: '<c:url value="/couRequestHeaderController.do?getcouRequestInfo"/>',
		method: 'POST',
		toolbar: '#ezuiToolbar',
		pageSize : 2000,
		pageList : [2000, 3000, 5000],
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		rownumbers: true,
		singleSelect: false,
		pagination:true,
		columns: [[
			{field:'ck',checkbox:true},
			{field: 'customerid', title: '货主', width: 71},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'locationid', title: '库位', width: 100},
			{field: 'qty', title: '盘点件数', width: 100},
			{field: 'lotatt04', title: '生产批号', width: 100},
			{field: 'lotatt05', title: '序列号', width: 100},
			{field: 'productLineName', title: '产品线', width: 100},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		},onClose:function(){
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialog.dialog('open');
};
//生成任务 骨科盘点
var GuKeInventory = function(){
	//时间初始化
	$("#SoTimeStart").datetimebox('setValue',ordertimeDate(new Date()));
	$("#SoTimeEnd").datetimebox('setValue',ordertimeDateTo(new Date()));
	//生成盘点任务datagrid初始化
	ezuiDetailsDatagridGuKe = $('#ezuiDetailsDatagridGuKe').datagrid({
		url: '<c:url value="/couRequestHeaderController.do?getcouRequestInfoGuKe"/>',
		method: 'POST',
		toolbar: '#ezuiToolbarGuKe',
		pageSize : 2000,
		pageList : [2000, 3000, 5000],
		fit: true,
		border: false,
		fitColumns: false,
		nowrap: false,
		striped: true,
		collapsible: false,
		rownumbers: true,
		singleSelect: false,
		pagination:true,
		queryParams:{
			SoTimeStart:$("#SoTimeStart").datetimebox('getValue'),
			SoTimeEnd:$("#SoTimeEnd").datetimebox('getValue')
         },
		columns: [[
			{field: 'customerid', title: '货主', width: 71},
			{field: 'sku', title: '产品代码', width: 150},
			{field: 'locationid', title: '库位', width: 100},
			{field: 'qty', title: '盘点件数', width: 100},
			{field: 'lotatt04', title: '生产批号', width: 100},
			{field: 'lotatt05', title: '序列号', width: 100},
			{field: 'productLineName', title: '产品线', width: 100},
		]],
		onDblClickCell: function (index, field, value) {

		},
		onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		},onClose:function(){
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialogGuKe.dialog('open');
};
//生成任务 选择条数之后
var GenerateInventoryPlanT = function(){
	url = '<c:url value="/couRequestHeaderController.do?ToGenerateInventoryPlan"/>';
	var rows = ezuiDetailsDatagrid.datagrid('getChecked');
	var rowsL=rows.length;
	var forms=[];
	var data=null;
	var msg='';
	for (var i = 0; i < rows.length; i++) {
		data=new Object();
		data.customerid=rows[i].customerid;
		data.sku=rows[i].sku;
		data.locationid=rows[i].locationid;
		data.lotatt04=rows[i].lotatt04;
		data.lotatt05=rows[i].lotatt05;
		forms.push(data);

	}
	if(rowsL>0) {
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});

		$.ajax({
			url: url,
			data:"forms="+JSON.stringify(forms),
			dataType: 'json',
			error: function (a,b,c) {
				//alert(a+b+c);
			},
			success: function (result) {
				try{
					if(result.success){
						msg=result.msg;
						ezuiDatagrid.datagrid('reload');
						$.messager.show({
							msg:msg, title : '<spring:message code="common.message.prompt"/>'
						});
						ezuiDialog.dialog('close');
						$.messager.progress('close');
					}else{
						msg=result.msg;
						ezuiDatagrid.datagrid('reload');
						$.messager.show({
							msg :msg, title : '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
					}
				}catch (e) {
					$.messager.show({
						msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
				}
			}
		});

	}else{
		msg = '<font color="red">' +'请选择资料!'+ '</font>';
		$.messager.show({
			msg : msg, title : '<spring:message code="common.message.prompt"/>'
		});
		$.messager.progress('close');

	}
};
//生成任务 骨科
var GuKeInventoryT = function(){
	url = '<c:url value="/couRequestHeaderController.do?ToGenerateInventoryPlanGuKe"/>';
	var data=[];
	var msg='';
	data.SoTimeStart=$("#SoTimeStart").datetimebox('getValue')+"";
	data.SoTimeEnd=$("#SoTimeEnd").datetimebox('getValue')+"";

	$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});

		$.ajax({
			url: url,
			data:{SoTimeStart:data.SoTimeStart,SoTimeEnd:data.SoTimeEnd},
			dataType:"json",
			error: function (a,b,c) {
				//alert(a+b+c);
			},
			success: function (result) {
				try{
					if(result.success){
						msg=result.msg;
					}else{
						msg=result.msg;

					}
				}catch (e) {
					$.messager.show({
						msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
				}finally {
					ezuiDatagrid.datagrid('reload');
					$.messager.show({
						msg :msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');
				}
			}
		});

};
//查看盘点信息
var ShowGenerateInventory = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		 var cycleCountno=row.cycleCountno;
		 $("#ShowEzuiToolbar #cycleCountno").val(cycleCountno);
		ShowEzuiDetailsDatagrid.datagrid('load',{
			cycleCountno:cycleCountno
		});
		ShowEzuiDialog.dialog('setTitle',"盘点单号:"+cycleCountno);
		ShowEzuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//关闭任务单
var closeC = function() {

	var row = ezuiDatagrid.datagrid('getSelected');
	if (row) {
		closeDialog.dialog('open');
	}else{
		$.messager.show({
			msg: '请选择一笔资料', title: '<spring:message code="common.message.prompt"/>'
		});
	}


};
//提交关闭任务单
var commitCloseDialog = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if (row) {
    var data=new Object();
		data.cycleCountno=row.cycleCountno;
		data.notes=$("#closeDialogForm #notes").val();
		$.ajax({
			url : 'couRequestHeaderController.do?close',
			data :data,
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
					closeDialog.dialog('close');
					ezuiDatagrid.datagrid('reload');
				}
			}
		});
	} else{
			$.messager.show({
				msg: '请选择一笔资料', title: '<spring:message code="common.message.prompt"/>'
			});
		}
};
//一级dialog提交
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/couRequestHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/couRequestHeaderController.do?add"/>';
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
//主页datagrid查询
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		cycleCountno : $('#cycleCountno').val(),
		status : $('#status').combobox('getValue'),

	});
};
//生成盘点任务datagrid查询
var doxDialogSearch = function(){
	ezuiDetailsDatagrid.datagrid('load', {
		customerid : $('#ezuiToolbar #customerid').textbox('getValue'),
		sku : $('#ezuiToolbar #sku').textbox('getValue'),
		lotatt04 : $('#ezuiToolbar #lotatt04').textbox('getValue'),
		lotatt05 : $('#ezuiToolbar #lotatt05').textbox('getValue'),
		productLineName : $('#ezuiToolbar #productLineName').combobox('getText'),
        locationid : $('#ezuiToolbar #locationid').textbox('getValue'),
	});
};
//生成盘点任务datagrid查询 骨科
var doxDialogSearchGuKe = function(){
	ezuiDetailsDatagridGuKe.datagrid('load', {
		SoTimeStart:$("#SoTimeStart").datetimebox('getValue'),
		SoTimeEnd:$("#SoTimeEnd").datetimebox('getValue')
	});
};
//按钮盘点任务datagrid查询
var dosDialogSearch = function(){
	ShowEzuiDetailsDatagrid.datagrid('load', {
		cycleCountno : $("#ShowEzuiToolbar #cycleCountno").val(),
		customerid : $('#ShowEzuiToolbar #customerid').textbox('getValue'),
		sku : $('#ShowEzuiToolbar #sku').textbox('getValue'),
		lotatt04 : $('#ShowEzuiToolbar #lotatt04').textbox('getValue'),
		lotatt05 : $('#ShowEzuiToolbar #lotatt05').textbox('getValue'),
		productLineName : $('#ShowEzuiToolbar #productLineName').combobox('getText'),
		reservedfield01 : $('#ShowEzuiToolbar #reservedfield01').textbox('getText'),
		locationid : $('#ShowEzuiToolbar #locationid').textbox('getText'),
	});
};

//按钮盘点任务datagrid清除
var ezuiDialogsToolbarClear= function(){
	$("#ShowEzuiToolbar #customerid").textbox('clear');
	$("#ShowEzuiToolbar #sku").textbox('clear');
	$("#ShowEzuiToolbar #lotatt04").textbox('clear');
	$("#ShowEzuiToolbar #lotatt05").textbox('clear');
	$("#ShowEzuiToolbar #productLineName").combobox('clear');
	$("#ShowEzuiToolbar #reservedfield01").textbox('clear');
	$("#ShowEzuiToolbar #locationid").textbox('clear');
};
//生成盘点任务datagrid清除 骨科
var ezuiDialogGuKeClear= function(){
	$("#SoTimeStart").datetimebox('clear');
	$("#SoTimeEnd").datetimebox('clear');
};
/* 导出start */
var doExport = function(cycleCountno){
	if (navigator.cookieEnabled) {
				$('#ezuiBtn_export').linkbutton('disable');
				var token = new Date().getTime();
				var param = new HashMap();
				param.put("token", token);
				param.put("cycleCountno",cycleCountno);

				//--导出Excel
				var formId = ajaxDownloadFile(sy.bp() + "/couRequestHeaderController.do?exportCouRequestDataToExcel", param);
				downloadCheckTimer = window.setTimeout(function () {
					$('#' + formId).remove();
					$('#ezuiBtn_export').linkbutton('enable');
					$.messager.progress('close');
					$.messager.show({
						msg: "<spring:message code='common.message.export.success'/>",
						title: "<spring:message code='common.message.prompt'/>"
					});
				}, 1000);
		} else {
			$.messager.show({
				msg: "<spring:message code='common.navigator.cookieEnabled.false'/>",
				title: "<spring:message code='common.message.prompt'/>"
			});
		}

};
//多条导出
var doExportM=function () {
	var rows = ezuiDatagrid.datagrid('getChecked');
	if(rows.length>0) {
        //循环导出excel
        for (let i = 0; i < rows.length;i++) {
            var cycleCountno=rows[i].cycleCountno;
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否导出盘点单:'+cycleCountno, function(confirm) {
                if(confirm) {
                    cycleCountno=rows[i].cycleCountno;
                    doExport(cycleCountno);

                }
            });
        }
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
}
/* 导出end */
//导入
var toImportData = function(){
	ezuiImportDataDialog.dialog('open');
};
/* 导入start */
var commitImportData = function(obj){
	ezuiImportDataForm.form('submit', {
		url : '<c:url value="/couRequestHeaderController.do?importExcelData"/>',
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
					ezuiDatagrid.datagrid('reload');
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


//货主查询弹框弹出start=========================
var ezuiCustDataClick = function (type) {
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url: '<c:url value="/basCustomerController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiCustToolbar',
		title: '客户档案',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		queryParams: {
			activeFlag: '1',
			customerType: 'OW'
		},
		idField: 'id',
		columns: [[
			{field: 'customerid', title: '客户代码', width: 15},
			{field: 'descrC', title: '中文名称', width: 50},
			{field: 'descrE', title: '英文名称', width: 50},
			{field: 'customerTypeName', title: '类型', width: 15},
			{
				field: 'activeFlag', title: '激活', width: 15, formatter: function (value, rowData, rowIndex) {
					return rowData.activeFlag == '1' ? '是' : '否';
				}
			}
		]],
		onDblClickCell: function (index, field, value) {
			selectCust(type);
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue', 'OW').combobox('setText', '货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue', '1').combobox('setText', '是');
	ezuiCustDataDialog.dialog('open');
};

//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function () {
	ezuiCustDataDialogId.datagrid('load', {
		customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	});
};
//货主查询弹框选择按钮
var selectCust = function (type) {
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	var type=type;
	if (row) {
		if(type=="S"){
			$("#ShowEzuiToolbar #customerid").textbox('setValue', row.customerid);

		}else {
			$("#ezuiToolbar #customerid").textbox('setValue', row.customerid);
		}
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function () {
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================

// 产品名称选择弹框-
var ezuiSkuDataClick = function () {
	// $("#ezuiSkuDataDialog #customerid").textbox('setValue',$("#ezuiDetailsForm #customerid").textbox("getValue")).textbox('readonly', true);
	// $("#ezuiSkuDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiSkuDataDialogId = $('#ezuiSkuDataDialogId').datagrid({
		url: '<c:url value="/basSkuController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiSkuToolbar',
		title: '产品档案',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		// queryParams:{
		// 	customerid : $("#ezuiDetailsForm #customerid").textbox("getValue"),
		// 	activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
		// },
		idField: 'sku',
		columns: [[
			{field: 'customerid', title: '客户代码', width: 80},
			{field: 'sku', title: '产品代码', width: 160},
			{field: 'reservedfield01', title: '产品名称', width: 160},
			{field: 'descrE', title: '英文名称', width: 160},
			{
				field: 'activeFlag', title: '激活', width: 40, formatter: function (value, rowData, rowIndex) {
					return rowData.activeFlag == '1' ? '是' : '否';
				}
			},
			// {field: 'alternateSku1', title: '商品条码', width: 120},
			// {field: 'packid', title: '包装代码', width: 80},
			// {field: 'qty', title: '库存数', width: 60},
			// {field: 'qtyallocated', title: '分配数', width: 60},
			// {field: 'qtyonhold', title: '冻结数', width: 60}
		]],
		onDblClickCell: function (index, field, value) {
			selectSku();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});

	ezuiSkuDataDialog.dialog('open');
};
// 产品名称选择弹框查询-
var ezuiSkuDataSearch = function () {
	ezuiSkuDataDialogId.datagrid('load', {
		customerid: $("#ezuiSkuDataDialog #customerid").textbox("getValue"),
		sku: $("#ezuiSkuDataDialog #sku").textbox("getValue"),
		activeFlag: $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
	});
};
// 产品名称选择弹框清空
var ezuiSkuToolbarClear = function () {
	$("#ezuiSkuDataDialog #sku").textbox('clear');
	$("#ezuiSkuDataDialog #customerid").textbox('clear');
	$("#ezuiSkuDataDialog #activeFlag").combobox('clear');
};
// 产品名称选择-主页
var selectSku = function () {
	var row = ezuiSkuDataDialogId.datagrid('getSelected');
	if (row) {
		$("#ShowEzuiToolbar #reservedfield01").textbox('setValue', row.reservedfield01);
		ezuiSkuDataDialog.dialog('close');
	}
};

/* 库位选择弹框查询 */
var ezuiLocDataSearch = function () {
	ezuiLocDataDialogId.datagrid('load', {
		locationid: $("#ezuiLocDataDialog #locationid").textbox("getValue"),
		locationcategory: $("#ezuiLocDataDialog #locationcategory").combobox("getValue"),

	});
};
/* 库位选择弹框清空 */
var ezuiLocToolbarClear = function () {
	$("#ezuiLocDataDialog #locationid").textbox('clear');
	$("#ezuiLocDataDialog #locationcategory").combobox('clear');
};
/* 库位选择弹框 */
var ezuiLocDataClick = function (type) {
	ezuiLocDataDialogId = $('#ezuiLocDataDialogId').datagrid({
		url: '<c:url value="/basLocationController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiLocToolbar',
		title: '库位选择',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		idField: 'locationid',
		columns: [[
			{field: 'locationid', title: '库位', width: 80},
			{field: 'locationusageName', title: '库位使用', width: 100},
			{field: 'locationcategoryName', title: '库位类型', width: 100},
			{field: 'locationattributeName', title: '库位属性', width: 100},
			{field: 'putawayzoneName', title: '上架区', width: 100},
			{field: 'pickzoneName', title: '拣货区', width: 100}
		]],
		onDblClickCell: function (index, field, value) {
			selectLocation(type);
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	ezuiLocDataDialog.dialog('open');
};
/* 库位选择 */
var selectLocation = function (type) {
	var row = ezuiLocDataDialogId.datagrid('getSelected');
	if (row) {
        if(type=="S"){
            $("#ShowEzuiToolbar #locationid").textbox('setValue', row.locationid);

        }else {
            $("#ezuiToolbar #locationid").textbox('setValue', row.locationid);
        }
		ezuiLocDataDialog.dialog('close');
	}
	;
};


// 产品代码框选择
function choseSelect_product_couRequestHeader(row) {
	var sku;
	if (row) {
		sku = row;
	} else {
		row = $("#productSearchGrid_couRequestHeader").datagrid("getSelections");
		if (row) {
			sku = row[0]
		}
	}
	if (sku) {
		$("#ezuiToolbar #sku").textbox("setValue", sku.sku);
	}
	productDialog_couRequestHeader.dialog("close");
}
// show产品代码框选择
function choseSelect_product_couRequestHeaderS(row) {
	var sku;
	if (row) {
		sku = row;
	} else {
		row = $("#productSearchGrid_couRequestHeaderS").datagrid("getSelections");
		if (row) {
			sku = row[0]
		}
	}
	if (sku) {
		$("#ShowEzuiToolbar #sku").textbox("setValue", sku.sku);
	}
	productDialog_couRequestHeaderS.dialog("close");
}



/* 获取起始日期 */
var ordertimeDate = function (date) {
	var day = date.getDate() - 1> 9 ? date.getDate() - 1: "0" + date.getDate() - 1;
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 00:00:00';
};
/* 获取结束日期 */
var ordertimeDateTo = function (date) {
	var day = date.getDate() -1> 9 ? date.getDate() -1: "0" + date.getDate()-1;
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day + ' 23:59:59';
};
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页datagrid工具栏--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>盘点单号</th><td><input type='text' id='cycleCountno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>盘点状态</th><td><input type='text' id='status' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?getCouRequestStatus"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>
							<td>
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查看</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExportM();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导出</a>
								<a onclick='toImportData();' id='ezuiBtn_export1' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>

							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='ShowGenerateInventory();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查看</a>

					<a onclick='GenerateInventoryPlan();' id='ezuiBtn_plan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成盘点任务</a>
					<a onclick='GuKeInventory();' id='ezuiBtn_inventory' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>骨科盘点</a>
					<a onclick='closeC();' id='ezuiBtn_closeplan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>关闭任务单</a>
					<a onclick='cancel();' id='ezuiBtn_cancelplan' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>取消任务单</a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>删除任务单</a>
<%--					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--关闭计划单点击弹窗--%>
	<div id='closeDialog' style='padding: 10px;'>
		<form id='closeDialogForm' method='post'>
			<table>

				<tr>
					<th>备注</th><td><input type='text' id='notes' name="notes" class='easyui-textbox' size='20' data-options="multiline:true,width:200,height:80"/></td>

				</tr>
			</table>
		</form>
	</div>
	<div id='closeDialogBtn'>
		<a onclick='commitCloseDialog();' class='easyui-linkbutton' href='javascript:void(0);'>确认</a>
	</div>
<%--右键菜单--%>
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
<%--						<a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton' href='javascript:void(0);'>下载档案模版</a>--%>
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
<!-- 货主选择弹框 -->
	<div id='ezuiCustDataDialog' style="width:700px;height:480px;padding:10px 20px">
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiCustToolbar' class='datagrid-toolbar' style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th>
								<td>
									<input type='text' id='customerid' name="customerid" class='easyui-textbox' size='12'
										   data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th>
								<td>
									<input type='text' id='customerType' name="customerType" class='easyui-combobox'
										   size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
								</td>
								<th>激活：</th>
								<td>
									<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox' size='8'
										   data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'},
																																{id: 'N', value: '否'}
																															]"/>
								</td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-remove"'
									   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
					<div id='ezuiCustDialogBtn'></div>
				</div>
				<table id='ezuiCustDataDialogId'></table>
			</div>
		</div>
	</div>
	<div id='ezuiCustDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.close'/></a>
	</div>
<!--产品代码查询弹窗 -->
	<div id="ezuiSkuSearchDialog">

	</div>
<!--产品代码查询弹窗 -->
	<div id="ezuiSkuSearchDialogS">

	</div>

	<c:import url='/WEB-INF/jsp/couRequestHeader/dialog.jsp'/>
	<c:import url='/WEB-INF/jsp/couRequestHeader/dialogGuKe.jsp'/>
	<c:import url='/WEB-INF/jsp/couRequestHeader/ShowDialog.jsp'/>
	<c:import url='/WEB-INF/jsp/couRequestHeader/skuDialog.jsp'/>
	<c:import url='/WEB-INF/jsp/couRequestHeader/locDialog.jsp'/>
</body>
</html>
