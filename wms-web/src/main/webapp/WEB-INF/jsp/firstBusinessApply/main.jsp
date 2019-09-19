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
var ezuiDatagrid1;
var ezuiDialogEditZONGEnterprise;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid1 = $('#ezuiDatagrid1').datagrid({
		url : '<c:url value="/firstBusinessApplyController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '查询',
		pageSize : 100,
		pageList : [100, 50, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
        rowStyler:function(index,row){
            if(row.isUse == "0" ){
                return 'color:red;';
            }
        },
		rownumbers:true,
		singleSelect:false,
		idField : 'applyId',
		columns : [[
            {field: 'ck',checkbox:true },
            {field: 'isUse',		title: '是否有效',	width: 100 ,formatter:isUseFormatter},
            {field: 'firstState',		title: '首营状态',	width: 100 ,formatter: firstStateFormatter},

            {field: 'applyId',		title: '申请单号',	width: 180},
			// {field: 'clientId',		title: '委托客户',	width: 100 },
            {field: 'clientName',		title: '委托客户名称',	width: 180 },

            // {field: 'supplierId',		title: '供应商',	width: 150 },
            {field: 'supplierName',		title: '供应商名称',	width: 250 },

            {field: 'productCode',		title: '产品代码',	width: 180 },
            {field: 'productName',		title: '产品名称',	width: 200 },
            {field: 'productRegisterNo',		title: '注册证号',	width: 180 },

            {field: 'createId',		title: '创建人',	width: 100 },
			{field: 'createDate',		title: '创建时间',	width: 100 },
		]],
		onDblClickCell: function(index,field,value){
			// edit();// TODO 不能修改需要提示
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
			// event.preventDefault();
			// $(this).datagrid('unselectAll');
			// $(this).datagrid('selectRow', rowIndex);
			// ezuiMenu.menu('show', {
			// 	left : event.pageX,
			// 	top : event.pageY
			// });
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/firstBusinessApplyController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		},
        onSelect: function(rowIndex, rowData) {
            if (rowIndex - 1){
                console.log(rowData);
                if(rowData.firstState == FIRSTSTATE.FIRSTSTATE_00){
                    $('#ezuiBtn_confirm').linkbutton('enable');
                    $('#ezuiBtn_reApply').linkbutton('disable');
                }else if(rowData.firstState == FIRSTSTATE.FIRSTSTATE_90 || rowData.firstState == FIRSTSTATE.FIRSTSTATE_40){
                    $('#ezuiBtn_reApply').linkbutton('enable');
                    $('#ezuiBtn_confirm').linkbutton('disable');
                }else {
                    $('#ezuiBtn_reApply').linkbutton('disable');
                    $('#ezuiBtn_confirm').linkbutton('disable');
                }
            };
        }
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		top:0,
		left:0,
		width:1050,
		height:600,
		title : '<spring:message code="common.dialog.title"/>',
		fit:false,
		href:sy.bp()+"/firstBusinessApplyController.do?toDetail",
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

    $('#isUse').combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getIsUseCombobox',
        valueField:'id',
        textField:'value'
    });
    $("#firstStateQ").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogFirstState',
        valueField:'id',
        textField:'value'
    });
});
var add = function(){
	processType = 'add';
	$('#applyId').val(0);
	ezuiDialog.dialog('open').dialog('refresh',"/firstBusinessApplyController.do?toDetail&id=");


    $('#enterpriseProduct').dialog('destroy');//ProductAndSupplier主页
    $('#ezuiDialogSpec_PAS').dialog('destroy');	//ProductAndSupplier产品
    $('#ezuiDialogSupplierDetail').dialog('destroy');//ProductAndSupplier供应商
    $('#ezuiDatagridDetail1').dialog('destroy');    //detail主页
    // $('#ezuiDialogClientDetail').dialog('destroy'); //detail货主
    $('#ezuiDialogSpec1').dialog('destroy');	//edit 查看产品
    $('#ProductDialog').dialog('destroy');//detail查看产品
    $('#productRegisterDialog').dialog('destroy');//detail查看注册证
    $('#enterpriseDialog1').dialog('destroy');//edit查看产品

    // $('#ezuiDialogEditSupplierEnterprise').dialog('destroy');//edit供应商企业信息详情
    $('#ezuiDialogClientDetail1').dialog('destroy');//edit货主
    $('#productRegisterDialog1').dialog('destroy');	//edit 查看注册证
    // $('#ProductDialog').dialog('destroy');//detail查看产品
    // $('#productRegisterDialog').dialog('destroy');//detail查看注册证
    // $('#enterpriseDialog1').dialog('destroy');//edit查看产品
    // $('#productRegisterDialog1').dialog('destroy');//edit查看注册证
    //
    //
    //
    // $('#ezuiDialogClientDetail1').dialog('destroy');//edit货主
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid1.datagrid('getSelected');
	if(row){
		ezuiDialog.dialog('open').dialog('refresh',"/firstBusinessApplyController.do?toEdit&id="+row.applyId);
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
    $('#ezuiDatagridDetail1').dialog('destroy');    //detail主页
    $('#ProductDialog').dialog('destroy');//detail查看产品
    $('#productRegisterDialog').dialog('destroy');//detail查看注册证
    $('#ezuiDialogClientDetail1').dialog('destroy');//edit货主
    // $('#ezuiDialogEditClientEnterprise').dialog('destroy');//edit货主企业信息详情
    // $('#ezuiDialogEditSupplierEnterprise').dialog('destroy');//edit供应商企业信息详情


    $('#enterpriseDialog1').dialog('destroy');//edit查看产品
    $('#ezuiDialogSpec_PAS').dialog('destroy');	//ProductAndSupplier产品
    $('#ezuiDialogSpec1').dialog('destroy');	//edit 查看产品
    $('#productRegisterDialog1').dialog('destroy');	//edit 查看注册证
    $('#enterpriseProduct').dialog('destroy');//ProductAndSupplier主页
    $('#ezuiDialogSupplierDetail').dialog('destroy');//ProductAndSupplier供应商


    // $('#ezuiDialogClientDetail').dialog('destroy'); //detail货主
    // $('#ezuiDialogSupplierDetail1').dialog('destroy');//edit货主
    // $('#ezuiDialogSpec1').dialog('destroy');//edit货主
    // $('#enterpriseDialog1').dialog('destroy');//edit查看产品
    // $('#productRegisterDialog1').dialog('destroy');//edit查看注册证
    // $('#ProductDialog').dialog('destroy');//detail查看产品
    // $('#productRegisterDialog').dialog('destroy');//detail查看注册证
    // $('#productRegisterDialog1').dialog('destroy');
    // $('#clientDatagrid1').dialog('destroy');
    // $('#enterpriseDialog').dialog('destroy');
    // $('#ezuiDialogClientDetail1').dialog('destroy');
};
var del = function(){
	// var row = ezuiDatagrid1.datagrid('getSelected');

    var row = ezuiDatagrid1.datagrid('getSelections');
    var arrDel = new Array();
    var arrFirstState =  new Array();
    var flag = true;
    for(var i=0;i<row.length;i++){
        arrDel.push(row[i].applyId);
        if(row[i].firstState=='10' || row[i].firstState =='40' ){
            flag =false;
		}
    }
    // rows = dataGridProduct.datagrid('getSelections');


    if(!flag){
        $.messager.show({
            msg : '选中的申请中存在审核中与审核通过的申请无法删除', title : '提示'
        });
	}else{

		if(row){
			$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
				if(confirm){
					$.ajax({
						url : 'firstBusinessApplyController.do?delete',
						data : {id : arrDel.join(",")},
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
                                ezuiDatagrid1.datagrid('reload');
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
    }
};
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/firstBusinessApplyController.do?edit"/>';
	}else{
		url = '<c:url value="/firstBusinessApplyController.do?add"/>';
	}


    if(row.firstState == '10' || row.firstState =='40'){
        $.messager.show({
            msg : '审核中与审核通过的申请无法编辑', title : '提示'
        });
    }else{
        ezuiForm.form('submit', {
            url: url,
            onSubmit: function () {
                if (ezuiForm.form('validate')) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });
                    return true;
                } else {
                    return false;
                }
            },
            success: function (data) {
                var msg = '';
                try {
                    var result = $.parseJSON(data);
                    if (result.success) {
                        msg = result.msg;
                        ezuiDatagrid1.datagrid('reload');
                        ezuiDialog.dialog('close');
                    } else {
                        msg = '<font color="red">' + result.msg + '</font>';
                    }
                } catch (e) {
                    msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                    msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
                } finally {
                    $.messager.show({
                        msg: msg, title: '<spring:message code="common.message.prompt"/>'
                    });
                    $.messager.progress('close');
                }
            }
		});
	}
};


//主页查询
var doSearch = function(){
    ezuiDatagrid1.datagrid('load', {
        productCode : $('#productCode').val(),
        productName:$('#productName').val(),
		clientId : $('#clientIdQuery').val(),
		supplierId : $('#supplierIdQuery').val(),
        createDateStart : $('#createDateStart').datebox('getValue'),
        createDateEnd : $('#createDateEnd').datebox('getValue'),
		isUse : $('#isUse').combobox('getValue'),
        productRegisterNo :  $('#productRegisterNoQ').val(),
        applyId :  $('#applyIdQ').val(),
        firstState:  $('#firstStateQ').combobox('getValue'),
	});
};



//提交审核
var doConfirm = function () {
    // var row = ezuiDatagrid1.datagrid('getSelected');


    var row = ezuiDatagrid1.datagrid('getSelections');
    var arrDel = new Array();
    var arrFirstState =  new Array();
    var flag = true;
    for(var i=0;i<row.length;i++){
        arrDel.push(row[i].applyId);
        if(row[i].firstState=='10' || row[i].firstState =='40' ){
            flag =false;
        }
    }
	if(!flag){
        $.messager.show({
            msg : '审核中与审核通过的申请无法提交', title : '提示'
        });
        return;
	}
    if(row){
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认提交审核吗', function(confirm) {
            if(confirm){
                $.ajax({
                    url : 'firstBusinessApplyController.do?confirmApply',
                    data : {id : arrDel.join(",")},
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
                            ezuiDatagrid1.datagrid('reload');
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
}
//发起新申请
var reApply = function () {
    var row = ezuiDatagrid1.datagrid('getSelections');
    var arrDel = new Array();
    var arrFirstState =  new Array();
    var flag = true;
    for(var i=0;i<row.length;i++){
        arrDel.push(row[i].applyId);
        if(row[i].firstState!='40'){
            flag =false;
        }
    }
    if(!flag){
        $.messager.show({
            msg : '只有审核通过的申请才能发起新申请', title : '提示'
        });
        return;
    }
    var row = ezuiDatagrid1.datagrid('getSelected');
    if(row){
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认发起新申请吗', function(confirm) {
            if(confirm){
                $.ajax({
                    url : 'firstBusinessApplyController.do?reApply',
                    data : {id : arrDel.join(",")},
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
                            ezuiDatagrid1.datagrid('reload');
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
}




//企业信息详情（所有页面都用这个打开企业信息）
function enterpriseInfo(enterpriseId){
    $(function() {
        ezuiDialogEditZONGEnterprise = $('#ezuiDialogEditZONGEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            buttons : '',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            width:1200,
            height:530,
            closable:true,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiDialogEditZONGEnterprise);
            }
        }).dialog('close');
    })
    if(enterpriseId!=null && enterpriseId!="" ){
        ezuiDialogEditZONGEnterprise.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail"+"&id="+enterpriseId).dialog('open');
        enterpriseId = "";
    }else{
        $.messager.show({
            msg : '请先选择企业', title : '提示'
        });
    }
}
</script>
<style>
	table th{
		text-align: right;
	}
</style>
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
							<th>产品代码</th><td><input type='text' id='productCode' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品名称</th><td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>委托客户</th><td><input type='text' id='clientIdQuery' class='easyui-textbox' size='16' data-options=''/></td>
							<th>供应商</th><td><input type='text' id='supplierIdQuery' class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr>
							<th>创建时间</th><td><input type='text' id='createDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<th>是否启用</th><td><input type='text' id='isUse' class='easyui-textbox' size='16' data-options=''/></td>
							<th>注册证号</th><td><input type='text' id='productRegisterNoQ' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>

						<tr>
							<th>申请单号</th><td><input type='text' id='applyIdQ' class='easyui-textbox' size='16' data-options=''/></td>
							<th>首营状态</th><td><input type='text' id='firstStateQ' class='easyui-textbox' size='16' data-options=''/></td>
							<td colspan="4">
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
					<a onclick='doConfirm();' id='ezuiBtn_confirm' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
					<a onclick='reApply();' id='ezuiBtn_reApply' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid1");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid1'></table>
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>

	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

	<div id='ezuiDialogEditZONGEnterprise' style='padding: 10px;'>

	</div>

</body>
</html>
