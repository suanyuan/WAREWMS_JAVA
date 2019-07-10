<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
</head>
<body>
<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<div class='easyui-layout' data-options='fit:true,border:false'>
	<div data-options='region:"center",border:false' style='overflow: hidden;'>
		<script type='text/javascript'>
            var processType;
            var ezuiMenu;
            var ezuiForm;
            var enterDialog;
            var ezuiDialogA;
            var ezuiDatagrid;
            var ezuiFormInfo;
            var dialogUrll = sy.bp()+"/gspReceivingController.do?toDetail";
            $(function() {
                ezuiMenu = $('#ezuiMenu').menu();
                ezuiForm = $('#ezuiForm').form();
                ezuiFormInfo = $('#ezuiFormInfo').form();

                ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                    url : '<c:url value="/gspReceivingController.do?showDatagrid"/>',
                    method:'POST',
                    toolbar : '#toolbar',
                    title: '待输入标题',
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
                    idField : 'receivingId',
                    columns : [[


                        {field: 'receivingId',	hidden:true,		width: 72 },
                        {field: 'enterpriseId',	hidden:true,		width: 72 },
                        {field: 'clientId',		title: '货主',	width: 52 },
                        {field: 'supplierId',		title: '供应商',	width: 52 },

                        {field: 'enterpriseNo',		title: '企业信息代码',	width: 81 },
                        {field: 'shorthandName',		title: '简称',	width: 41 },
                        {field: 'enterpriseName',		title: '企业名称',	width: 61  },


                        {field: 'deliveryAddress',		title: '地址',	width: 52 },
                        {field: 'isCheck',		title: '是否需要审核',	width: 82 ,formatter:function(value,rowData,rowIndex){
                                return rowData.isCheck == '1' ? '是' : '否';
                            }},
                        {field: 'firstState',		title: '审核状态',	width: 62 },
                        {field: 'isReturn',		title: '是否医废',	width: 62 ,formatter:function(value,rowData,rowIndex){
                                return rowData.isReturn == '1' ? '是' : '否';
                            }},
                        {field: 'isUse',		title: '是否有效',	width: 62 ,formatter:function(value,rowData,rowIndex){
                                return rowData.isUse == '1' ? '是' : '否';
                            }},
                        {field: 'createId',		title: '创建人',	width: 52 },
                        {field: 'createDate',		title: '创建日期',	width: 102 },
                        {field: 'editId',		title: '修改人',	width: 52 },
                        {field: 'editDate',		title: '修改日期',	width: 102 },


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
                        ajaxBtn($('#menuId').val(), '<c:url value="/gspReceivingController.do?getBtn"/>', ezuiMenu);
                        $(this).datagrid('unselectAll');

                    }
                });
                ezuiDialogA = $('#ezuiDialog').dialog({
                    modal : true,
                    title : '<spring:message code="common.dialog.title"/>',
                    href:dialogUrll,
                    fit:true,
                    cache:false,
                    buttons : '#ezuiDialogBtn',
                    onClose : function() {
                        ezuiFormClear(ezuiForm);
                    }
                }).dialog('close');


                enterDialog = $('#ezuiFormInfo').dialog({
                    modal : true,
                    title : '<spring:message code="common.dialog.title"/>',
                    buttons : '#ezuiDialogBtn',
                    onClose : function() {
                       /* ezuiFormClear(ezuiForm);*/
                    }
                }).dialog('close');


                $("#isUse").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
                $("#isReturn").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
                $("#isCooperation").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
                $("#isCheck").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
            });
            var add = function(){
                processType = 'add';
                $('#receivingId').val(0);
               ezuiDialogA.dialog('open');

            };
  var edit = function(){
                processType = 'edit';
                var row = ezuiDatagrid.datagrid('getSelected');

        if(row){
           // ezuiDialogA.dialog('open');
                    ezuiDialogA.dialog('open').dialog('refresh', dialogUrll+"&enterpriseId="+row.enterpriseId+"&receivingId="+row.receivingId);
                }else{
                    $.messager.show({
                        msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
                    });
                }
            };


            var newAdd = function(){
                edit();
                /*var row = ezuiDatagrid.datagrid('getSelected');
                console.log(row);
                if(row){

                    ezuiFormAddress.form('load',{

                        receivingId : row.receivingId,
                        enterpriseId : row.enterpriseId,
                        enterpriseNo : row.enterpriseNo,
                        enterpriseName : row.enterpriseName,
                        customerid : row.customerid,
                        supplierId : row.supplierId,
                        sellerName : row.sellerName,
                        isCheck : row.isCheck,
                        isReturn : row.isReturn,
                        deliveryAddress : row.deliveryAddress,
                        contacts : row.contacts,
                        phone : row.phone,

                });
                ezuiDialog.dialog('open');
            }else{

            }*/
            };
            var del = function(){
                var row = ezuiDatagrid.datagrid('getSelected');
                if(row){
                    $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
                        if(confirm){
                            $.ajax({
                                url : 'gspReceivingController.do?delete',
                                data : {id : row.receivingId},
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
                dooSubmit();
            };
 var doSearch = function(){
                ezuiDatagrid.datagrid('load', {
                    receivingId : $('#receivingId').val(),
                    enterpriseId : $('#enterpriseId').val(),
                    clientId : $('#clientId').val(),
                    supplierId : $('#supplierId').val(),
                    deliveryAddress : $('#deliveryAddress').val(),
                    isCheck : $('#isCheck').combobox("getValue"),
                    isReturn : $('#isReturn').combobox("getValue"),

                    createId : $('#createId').val(),
                    createDate : $('#createDate').val(),
                    editId : $('#editId').val(),
                    editDate : $('#editDate').val(),
                    isUse : $('#isUse').combobox("getValue")
                });
            };

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
            }


		</script>
		<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>货主</th><td><input type='text' id='clientId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>供应商</th><td><input type='text' id='supplierId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否需要审核</th><td><input type='text' id='isCheck' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否医废</th><td><input type='text' id='isReturn' name="isReturn" class='easyui-textbox' size='16' data-options=''/></td>

							<th>是否有效</th><td><input type='text' id='isUse' name="isUse" class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<%--<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.update'/></a>--%>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
					<a onclick='newAdd();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>

	</div>


	<form id='enterpriseDialog' method='post'>

	</form>




	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
