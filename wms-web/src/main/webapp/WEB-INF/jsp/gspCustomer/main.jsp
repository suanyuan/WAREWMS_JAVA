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
    .easyui-textbox{
        width:130px;
    }
</style>
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
var enterpriseDialog;
var dialogUrl = sy.bp()+"/gspCustomerController.do?toDetail";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspCustomerController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
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
		idField : 'clientId',
		columns : [[
			{field: 'clientId',		title: '申请单号',	width: 50 },
            {field: 'firstState',		title: '首营状态',	width: 38 ,formatter:firstStateTypeFormatter},
			{field: 'clientNo',		title: '代码',	width: 38 },
			{field: 'clientName',		title: '简称',	width: 38 },
			{field: 'enterpriseId',		title: '企业id',	width: 38 ,hidden:true},
			{field: 'remark',		title: '备注',	width: 38 ,hidden:true},
			{field: 'isCheck',		title: '是否审查',	width: 38 ,formatter: yesOrNoFormatter},
			{field: 'isCooperation',		title: '是否合作',	width: 38 ,formatter: yesOrNoFormatter},
			{field: 'operateType',		title: '类型',	width: 38 ,formatter: entTypeFormatter,hidden:true},
			{field: 'contractNo',		title: '合同编号',	width: 38 ,hidden:true},
			{field: 'contractUrl',		title: '合同文件',	width: 38 ,hidden:true},
			{field: 'clientContent',		title: '委托内容',	width: 38 ,hidden:true},
			{field: 'clientStartDate',		title: '委托开始时间',	width: 38 ,hidden:true},
			{field: 'clientEndDate',		title: '委托结束时间',	width: 38 ,hidden:true},
			{field: 'clientTerm',		title: '委托期限',	width: 38 },
			{field: 'isChineseLabel',		title: '是否长期',	width: 38 ,formatter: yesOrNoFormatter},
			{field: 'createId',		title: '创建人',	width: 38 },
			{field: 'createDate',		title: '创建时间',	width: 38 ,formatter:dateFormat2},
			{field: 'isUse',		title: '是否有效',	width: 38 ,formatter: yesOrNoFormatter}
		]],
		onDblClickRow: function(index,row){
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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspCustomerController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});

});
var add = function(){
	processType = 'add';
	$('#gspCustomerId').val(0);
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href:dialogUrl,
        fit:true,
        cache:false,
        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    })
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
        //ezuiDialog.dialog('refresh', dialogUrl+"&id="+row.clientId).dialog('open');
        ezuiDialog = $('#ezuiDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            buttons : '#ezuiDialogBtn',
            href:dialogUrl+"&id="+row.clientId,
            fit:true,
            cache:false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        })
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
					url : 'gspCustomerController.do?delete',
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
    doSubmit();
};
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		clientId : $('#clientIdQuery').val(),
		clientNo : $('#clientNoQuery').val(),
		clientName : $('#clientName').val(),
		enterpriseId : $('#enterpriseId').val(),
		remark : $('#remark').val(),
		firstState : $('#firstState').val(),
		isCheck : $('#isCheck').val(),
		isCooperation : $('#isCooperation').val(),
		operateType : $('#operateType').val(),
		contractNo : $('#contractNo').val(),
		contractUrl : $('#contractUrl').val(),
		clientContent : $('#clientContent').val(),
		clientStartDate : $('#clientStartDate').val(),
		clientEndDate : $('#clientEndDate').val(),
		clientTerm : $('#clientTerm').val(),
		isChineseLabel : $('#isChineseLabel').val(),
		createId : $('#createId').val(),
		createDate : $('#createDate').val(),
		editId : $('#editId').val(),
		editDate : $('#editDate').val(),
		isUse : $('#isUse').val()
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
							<th>企业代码：</th><td><input type='text' id='clientNoQuery' class='easyui-textbox' data-options=''/></td>
							<th>企业简称：</th><td><input type='text' id='clientNameQuery' class='easyui-textbox' data-options=''/></td>
							<th>企业：</th>
                            <td>
                                <input type='text' id='enterpriseIdQuery' style="width: 100px;"/>
                                <input type="hidden" class="easyui-textvalue" name="enterpriseId">
								<!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
                            </td>
							<th>首营状态：</th><td><input type='text' id='firstState' class='easyui-textbox' data-options=''/></td>
							<th style="display: none;">是否审查：</th><td style="display: none;"><input type='text' id='isCheck' class='easyui-textbox' data-options=''/></td>
						</tr>
						<tr>
							<th>是否合作：</th><td><input type='text' id='isCooperation' class='easyui-textbox' data-options=''/></td>
							<th>类型：</th><td><input type='text' id='operateType' class='easyui-textbox' data-options=''/></td>
							<th>委托开始时间：</th><td><input type='text' id='clientStartDate' class='easyui-datebox' data-options=''/></td>
							<th>委托结束时间：</th><td><input type='text' id='clientEndDate' class='easyui-datebox' data-options=''/></td>
							<th style="display: none;">是否有效：</th><td style="display: none;"><input type='text' id='isUse' data-options=''/></td>
						</tr>
						<tr>
							<th>是否贴中文标签：</th><td><input type='text' id='isChineseLabel' class='easyui-textbox' data-options=''/></td>
							<th>创建时间：</th><td><input type='text' id='createDateStart' class='easyui-datebox' data-options=''/></td>
							<th>创建时间：</th><td><input type='text' id='createDateEnd' class='easyui-datebox' data-options=''/></td>
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
					<a onclick='doConfirm();' id='ezuiBtn_confirm' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
					<a onclick='reApply();' id='ezuiBtn_reApply' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table>
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>

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

    <div id='enterpriseDialog' style='padding: 10px;'>

    </div>
<script>
    var enterpriseDialog_gspCustomer;
    $("#isCheck").combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });

    $("#isCooperation").combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });

    $("#isChineseLabel").combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });

    $("#isUse").combobox({
        url:sy.bp()+'/commonController.do?getIsUseCombobox',
        valueField:'id',
        textField:'value'
    });

    $("#firstState").combobox({
        url:sy.bp()+'/commonController.do?getCatalogFirstState',
        valueField:'id',
        textField:'value'
    });

    $("#operateType").combobox({
        url:sy.bp()+'/commonController.do?getEntType',
        valueField:'id',
        textField:'value'
    });


    function searchMainEnterprise() {
        enterpriseDialog_gspCustomer = $('#enterpriseDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toSearchDialog&target=gspCustomer",
            width:850,
            height:500,
            cache:false,
            onClose : function() {

            }
        })
		/*
		//查询条件货主字段初始化
	$("#fmcustomerid").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
		 */
    }

    function choseSelect_gspCustomer(id,name) {
        $("input[name='enterpriseId']").val(id);
        $("#enterpriseIdQuery").textbox("setValue",name);
        enterpriseDialog_gspCustomer.dialog("close");
    }

    function doConfirm(){
        var rows = ezuiDatagrid.datagrid("getSelections");
        if(rows && rows.length>0){
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认提交审核吗', function(confirm) {
                if (confirm) {
                    var arr = new Array();
                    for(var i=0;i<rows.length;i++){
                        arr.push(rows[i].clientId);
					}
                    $.ajax({
                        url : sy.bp()+"/gspCustomerController.do?confirmSubmit",
                        data : {"id":arr.join(","),"remark":$("#remark").val()},type : 'POST', dataType : 'JSON',async  :true,
                        success : function(result){
                            var msg = result.msg;
                            showMsg(msg);
                            ezuiDatagrid.datagrid("reload");
                        }
                    });

                }
            })
        }
	}

    function reApply(){
        var rows = ezuiDatagrid.datagrid("getSelections");
        if(rows && rows.length>0){
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认要重新下发吗', function(confirm) {
                if (confirm) {
                    var arr = new Array();
                    for(var i=0;i<rows.length;i++){
                        arr.push(rows[i].clientId);
                    }
                    $.ajax({
                        url : sy.bp()+"/gspCustomerController.do?reApply",
                        data : {"id":arr.join(","),"remark":$("#remark").val()},type : 'POST', dataType : 'JSON',async  :true,
                        success : function(result){
                            var msg = result.msg;
                            showMsg(msg);
                            ezuiDatagrid.datagrid("reload");
                        }
                    });

                }
            })
        }
    }

	$(function () {
		$("#enterpriseIdQuery").textbox({
			width:135,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchMainEnterprise();
                }
            }]
        })
    })

</script>
</body>
</html>
