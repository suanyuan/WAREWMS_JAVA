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

var ezuiImportDataDialog; //导入
var ezuiImportDataForm;   //导入form

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    ezuiImportDataForm=$('#ezuiImportDataForm').form();  //导入form

	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docPaHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
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
		singleSelect:false,
		columns : [[
			{field:'ck',checkbox:true},
			{field: 'pano',		title: '上架任务单号',	width: 150 },
			{field: 'asnno',		title: '入库单号',	width: 150},
			{field: 'pareference1',		title: '发货凭证号',	width: 150},
			{field: 'customerIdRef',		title: '货主',	    width: 131 },
			{field: 'pname',		title: '产品线',		width: 101 },
			// {field: 'patype',		title: '类型',	width: 71 },
			{field: 'pastatus',		title: '上架状态',	width: 71,formatter: pastatusFormatter},
			{field: 'addtime',		title: '创建时间',	width: 150},
			{field: 'addwho',		title: '创建人',	width: 150 },
			{field: 'edittime',		title: '编辑时间',	width: 150 },
			{field: 'editwho',		title: '编辑人',	width: 150 },
			{field: 'paPrintFlag',		title: '是否打印',	width: 150,formatter:yesOrNoFormatter},
			{field: 'warehouseid',		title: '仓库',	width: 150 }
		]],
		onDblClickCell: function(index,field,value){
			//edit();
		},
        onDblClickRow: function(index,row){
            edit(row);
        }
		// onRowContextMenu : function(event, rowIndex, rowData) {
		// 	event.preventDefault();
		// 	$(this).datagrid('unselectAll');
		// 	$(this).datagrid('selectRow', rowIndex);
		// 	ezuiMenu.menu('show', {
		// 		left : event.pageX,
		// 		top : event.pageY
		// 	});
		,onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/docPaHeaderController.do?getBtn"/>', ezuiMenu);
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

	$("#paPrintFlag").combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'

	});
	/* 控件初始化start */
	$("#customerid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	//客户选择弹框
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');

});

/* 客户选择弹框-主界面 */
var ezuiCustDataClick = function () {
	$("#ezuiCustDataDialog #customerType").combobox('setValue', 'OW').combo('readonly', true);
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue', '1').combo('readonly', true);
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
			customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
			activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
		},
		idField: 'customerid',
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
			selectCust();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	ezuiCustDataDialog.dialog('open');
};
/* 客户选择-主界面 */
var selectCust = function () {
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if (row) {
		var customerids = $("#toolbar #customerid").textbox('getValue');
		console.log(customerids);
		if (customerids != "" && customerids != undefined) {
			customerids = customerids + "," + row.customerid;
		} else {
			customerids = row.customerid;
		}
		$("#toolbar #customerid").textbox('setValue', customerids);
		ezuiCustDataDialog.dialog('close');
	}
	;
};
/* 客户选择弹框查询 */
var ezuiCustDataDialogSearch = function () {
	ezuiCustDataDialogId.datagrid('load', {
		customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	});
};
/* 客户选择弹框清空 */
var ezuiCustToolbarClear = function () {
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
var edit = function(row){
	processType = 'edit';
	var row = row;
	if (row == null) {
		row=ezuiDatagrid.datagrid('getSelected');
	}

	if(row){
		ezuiForm.form('load',{
			pano : row.pano,
			asnno : row.asnno,
			customerid : row.customerid,
			pareference1 : row.pareference1,
			pareference2 : row.pareference2,
			pareference3 : row.pareference3,
			pareference4 : row.pareference4,
			pareference5 : row.pareference5,
			patype : row.patype,
			pastatus : row.pastatus,
			pacreationtime : row.pacreationtime,
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
			paPrintFlag : row.paPrintFlag,
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
	//var row = ezuiDatagrid.datagrid('getSelected');
    var checkedItems = ezuiDatagrid.datagrid('getSelections');
    if(checkedItems && ezuiDatagrid.length>0){
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
            if(confirm){
                $.each(checkedItems, function(index, item){
                    $.ajax({
                        url : 'docPaHeaderController.do?delete',
                        data : {id : item.pano},
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
                });
            }
        });
	}

	/*if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docPaHeaderController.do?delete',
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
	}*/

};

var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docPaHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docPaHeaderController.do?add"/>';
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
		pano : $('#pano').val(),
		asnno : $('#asnno').val(),
		customerid : $('#customerid').val(),
		pareference1 : $('#pareference1').val(),
		pareference2 : $('#pareference2').val(),
		pareference3 : $('#pareference3').val(),
		pareference4 : $('#pareference4').val(),
		pareference5 : $('#pareference5').val(),
		patype : $('#patype').val(),
		pastatus : $('#pastatus').combobox('getValue'),
		pacreationtime : $('#pacreationtime').val(),
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
		paPrintFlag : $('#paPrintFlag').val(),
		warehouseid : $('#warehouseid').val(),
		skuGroup1:$('#skugroup1').combobox('getValue'),
		// 附加查询起始到结束时间
        addtimeBegin:$('#addtimeBegin').datebox("getValue"),
        addtimeEnd:$('#addtimeEnd').datebox("getValue")
	});
};

//导出上架任务明细
var doExport = function() {

    var rows = $('#ezuiDatagrid').datagrid('getChecked');
    if (rows.length > 0) {

        var pano = rows[0].pano;
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否导出上架任务清单:'+pano, function(confirm) {

            if(confirm) {

                doExportPaTask(pano);
            }
        });
    } else {
        $.messager.show({
            msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
        });
    }
}

//开始导出上架任务清单
var doExportPaTask = function(pano){

    if (navigator.cookieEnabled) {

        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        param.put("pano",pano);

        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp() + "/docPaHeaderController.do?exportDocPaDataToExcel", param);
        downloadCheckTimer = window.setTimeout(function () {
            $('#' + formId).remove();
            // $('#ezuiBtn_export').linkbutton('enable');
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

//导入上架结果明细
var doImport = function() {
    ezuiImportDataDialog.dialog('open');
};

var commitImportData = function(obj){
    ezuiImportDataForm.form('submit', {
        url : '<c:url value="/docPaHeaderController.do?importExcelData"/>',
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



//打印
var batchPrint = function(){
    var rows = $('#ezuiDatagrid').datagrid('getChecked');
    var pano="";
    if (rows.length > 0) {
        for (var i = 0; i < rows.length; i++) {
            pano += rows[i].pano+",";
        }

        /*ajaxDownloadFile(sy.bp()+"/docPaHeaderController.do?exportBatchPdf&pano="+pano);*/
        window.open(sy.bp()+"/docPaHeaderController.do?exportBatchPdf&pano="+pano);



    } else {
        $.messager.show({
            msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
        });
    }
	/*    var orderCodeList = null;
        var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
        $.each(checkedItems, function(index, item){
            if (orderCodeList != null) {
                orderCodeList = orderCodeList + ',' + item.pano;
            } else {
                orderCodeList = item.pano;
            }
        });
    if (orderCodeList != null) {
        window.open(sy.bp()+"/docPaHeaderController.do?exportBatchPdf&orderCodeList="+orderCodeList, "Report_"+orderCodeList, "scrollbars=yes,resizable=no");
    }*/

};

var parowStyle = function (index,row) {
	if(row.pastatus == "30"){
        return 'color:red;';
	}
}

function btnReset() {
    var checkedItems = ezuiDatagrid.datagrid('getSelections');
    if(checkedItems && ezuiDatagrid.length>0){
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认要回写收货数量？', function(confirm) {
            if(confirm){
                $.each(checkedItems, function(index, item){
                    if(item.userdefine5 == '1'){
                        showMsg("该任务已回写收货");
                        return false;
					}
                    $.ajax({
                        url : 'docPaHeaderController.do?resetDocPa',
                        data : {orderNo : item.pano},
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
                });
            }
        });
    }
}
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页上部查询菜单--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>上架单号：</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>
							<th>入库单号：</th><td><input type='text' id='asnno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>上架状态：</th><td><input type='text' id='pastatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																										editable: false,
																										url:'<c:url value="/commonController.do?getPaState"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
						</tr>
						<tr>
							<th>创建时间起始：</th><td><input type='text' id='addtimeBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>创建时间结束：</th><td><input type='text' id='addtimeEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<th>是否打印：</th><td><input type='text' id='paPrintFlag' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>货主</th>
							<td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品线</th>
							<td>
								<input type='text' id='skugroup1' name="skugroup1" class='easyui-combobox' size='16' data-options="panelHeight: '300px',
																															editable: false,
																															url:'<c:url value="/productLineController.do?getCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
							</td>
							<th>仓库：</th><td><input type='text' id='warehouseid' class='easyui-textbox' size='16' data-options=''/></td>
							<td colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='doExport();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>任务导出</a>
								<a onclick='doImport();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>结果导入</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='batchPrint();' id='ezuiBtn_print' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印</a>
					<a onclick='btnReset();' id='ezuiBtn_reSet' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>收货回写</a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<!--<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>-->
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>

    <%--双击弹窗dialog--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='docPaHeaderId' name='docPaHeaderId'/>
			<table>
				<tr>
					<th>上架任务单号</th>
					<td><input type='text' name='pano' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>入库单号</th>
					<td><input type='text' name='asnno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>

<%--				<tr>--%>
<%--					<th>类型</th>--%>
<%--					<td><input type='text' name='patype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',--%>
<%--																										editable: false,--%>
<%--																										url:'<c:url value="/commonController.do?getPaState"/>',--%>
<%--																										valueField: 'id',--%>
<%--																										textField: 'value'"/></td>--%>
<%--				</tr>--%>
				<tr>
					<th>上架状态</th>
					<td><input type='text' name='pastatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																										editable: false,
																										url:'<c:url value="/commonController.do?getPaState"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
				</tr>

				<tr>
					<th>创建时间</th>
					<td><input type='text' name='addtime' class='easyui-datetimebox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>创建人</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>编辑时间</th>
					<td><input type='text' name='edittime' class='easyui-datetimebox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>编辑人</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>是否打印</th>
					<td><input type='text' name='paPrintFlag' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																										editable: false,
																										url:'<c:url value="/commonController.do?getYesOrNoCombobox"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
				</tr>
				<tr>
					<th>仓库</th>
					<td><input type='text' name='warehouseid' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																											editable: false,
																											url:'<c:url value="/docPoHeaderController.do?getWarehouseCombobox"/>',
																											valueField: 'id',
																											textField: 'value'"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
<%--		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>--%>
<%--		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>--%>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

    <%--导入数据 Begin --%>
    <div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>
        <form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>
            <table>
                <tr>
                    <th>档案</th>
                    <td>
                        <input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36" data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>
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

	<c:import url='/WEB-INF/jsp/docPaHeader/custDialog.jsp'/>
</body>
</html>
