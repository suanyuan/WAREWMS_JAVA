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
                    idField : 'receivingId',
                    columns : [[

                        {field: 'enterpriseId',	hidden:true,width: 92 },
                        {field: 'receivingId',	title: '申请单号',width: 72 },
                       /* {field: 'receivingId',			width: 102 },*/
                        {field: 'firstState',		title: '首营状态',	width: 62 ,formatter:firstStateFormatter},
                        {field: 'enterpriseName',		title: '收货单位',	width: 102 },
                        {field: 'enterpriseNo',		title: '收货单位代码',	width: 91 },
                        {field: 'shorthandName',		title: '收货单位简称',	width: 91 },
                        {field: 'clientId',		title: '货主ID',	width: 52 },
                        {field: 'supplierId',		title: '供应商',	width: 61  },
                        {field: 'deliveryAddress',		title: '地址',	width: 92 },
                        {field: 'contacts',		title: '联系人',	width: 82 },
                        {field: 'phone',		title: '联系人电话',	width: 82 },

                        {field: 'isCheck',		title: '是否需要审核',	width: 82 ,formatter:function(value,rowData,rowIndex){
                                return rowData.isCheck == '1' ? '是' : '否';
                            }},
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
                /*ezuiDialogA = $('#ezuiDialog').dialog({
                    modal : true,
                    title : '',
                    href:dialogUrll,
                    fit:true,
                    cache:false,
                    buttons : '#ezuiDialogBtn',
                    onClose : function() {
                        ezuiFormClear(ezuiForm);
                    }
                }).dialog('close');*/


                enterDialog = $('#ezuiFormInfo').dialog({
                    modal : true,
                    title : '<spring:message code="common.dialog.title"/>',
                    buttons : '#ezuiDialogBtn',
                    onClose : function() {
                        ezuiFormClear(ezuiForm);
                    }
                }).dialog('close');


                $("#isU").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
                $("#isR").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
              /*  $("#isCooperation").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });*/
                $("#isCh").combobox({
                    url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
                    valueField:'id',
                    textField:'value'
                });
            });
var add = function(){
                processType = 'add';
                /*console.log($('#receivingId').val(0));
               ezuiDialogA.dialog('open');*/
    $('#ezuiDialog').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        href:dialogUrll,
        fit:true,
        cache:false,
        buttons : '#ezuiDialogBtn',
        onClose : function() {
            $('#ezuiFormAddress').form('clear');
        }
    });

            };
var edit = function(){

                processType = 'edit';
var row = ezuiDatagrid.datagrid('getSelected');
       	 if(row){
             $.ajax({
                 url: '/gspReceivingController.do?validateReceiv',
                 data: {receivingId: row.receivingId},
                 type: 'POST',
                 dataType: 'JSON',
                 success: function (res) {

                     if (res.firstState == '10' || res.firstState == '40') {

                         $('#ezuiDialog').dialog({
                             modal : true,
                             title : '<spring:message code="common.dialog.title"/>',

                             fit:true,
                             cache:false,
                             buttons : '#ezuiDialogBtn',
                             onClose : function() {
                                 ezuiFormClear(ezuiForm);
                             }
                         }).dialog('refresh', dialogUrll+"&enterpriseId="+res.enterpriseId+"&receivingId="+res.receivingId);
                         /*$.messager.show({
                             msg : '审核中与审核通过的申请无法编辑', title : '提示'
                         });*/
                     }else {
                         // ezuiDialogA.dialog('open').dialog('refresh', dialogUrll+"&enterpriseId="+row.enterpriseId+"&receivingId="+row.receivingId);

                         $('#ezuiDialog').dialog({
                             modal : true,
                             title : '<spring:message code="common.dialog.title"/>',

                             fit:true,
                             cache:false,
                             buttons : '#ezuiDialogBtn',
                             onClose : function() {
                                 ezuiFormClear(ezuiForm);
                             }
                         }).dialog('refresh', dialogUrll+"&enterpriseId="+row.enterpriseId+"&receivingId="+row.receivingId);

                     }

                 }





             })


        }else{
                    $.messager.show({
                        msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
                  			  });
               			 }

            };


 var newAdd = function(){
     processType='newAdd';

     var row = ezuiDatagrid.datagrid('getSelected');
     if (row) {
         $.ajax({
             url: '/gspReceivingController.do?validateReceiv',
             data: {receivingId: row.receivingId},
             type: 'POST',
             dataType: 'JSON',
			 success: function (res) {
                 if(res.firstState=="40"){
                     $('#ezuiDialog').dialog({
                         modal : true,
                         title : '<spring:message code="common.dialog.title"/>',
                         fit:true,
                         cache:false,
                         buttons : '#ezuiDialogBtn',
                         onClose : function() {
                             ezuiFormClear(ezuiForm);
                         }
                     }).dialog('refresh', dialogUrll+"&enterpriseId="+row.enterpriseId+"&receivingId="+row.receivingId);

                 }else {
                     $.messager.show({
                         msg : '该项审核未通过，请重新选择！', title : '<spring:message code="common.message.prompt"/>'
                     });
				 }


             }





		 })






     }else {
         $.messager.show({
             msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
         });
     }


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
                    enterpriseName : $('#enterpriseNa').val(),
                    shorthandName : $('#shortName').val(),
                    enterpriseNo : $('#enterpriseNumber').val(),
                    clientId : $('#clientId').val(),
                   supplierId : $('#supplierId').val(),
                    /*contacts : $('#contacts').val(),
                   phone : $('#phone').val(),
                    deliveryAddress : $('#deliveryAddress').val(),*/
                    isCheck : $('#isCh').combobox("getValue"),
                    isReturn : $('#isR').combobox("getValue"),

                   /* createId : $('#createId').val(),
                    createDate : $('#createDate').val(),
                    editId : $('#editId').val(),
                    editDate : $('#editDate').val(),*/
                    isUse : $('#isU').combobox("getValue")
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


















function xiafa() {

    //processType = 'edit';
    var rowSelect = ezuiDatagrid.datagrid('getSelected');


		console.log(rowSelect);
    if(rowSelect){

        $.ajax({
            url: '/gspReceivingController.do?validateReceiv',
            data: {receivingId: rowSelect.receivingId},
            type: 'POST',
            dataType: 'JSON',
			success:function (res) {
                if (res.firstState == '00' &&  res.isCheck=='1') {
                    $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认申请？', function(confirm) {
                        if (confirm) {
                            $.ajax({
                                url: '/gspReceivingController.do?confirmApply',
                                data: {gspReceivingFormsttr:JSON.stringify(rowSelect)},
                                type: 'POST',
                                dataType: 'JSON',
                                success: function (result) {
                                    var msg = '';
                                    try {
                                        msg = result.msg;
                                    } catch (e) {
                                        msg = '<spring:message code="common.message.data.delete.failed"/>';
                                    } finally {
                                        $.messager.show({
                                            msg: msg, title: '<spring:message code="common.message.prompt"/>'
                                        });
                                        ezuiDatagrid.datagrid('reload');
                                    }
                                }
                            });
                        }
                    })

                }else if (res.firstState=='00'&& res.isCheck=='0') {
                    $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否直接下发？', function(confirm) {
                        if (confirm) {
                            $.ajax({
                                url: '/basCustomerController.do?add',
                                data: {basCustomerFormStr: JSON.stringify(rowSelect)},
                                type: 'POST',
                                dataType: 'JSON',
                                success: function (result) {
                                    var msg = '';
                                    try {
                                        msg = result.msg;
                                    } catch (e) {
                                        msg = '<spring:message code="common.message.data.delete.failed"/>';
                                    } finally {
                                        $.messager.show({
                                            msg: msg, title: '<spring:message code="common.message.prompt"/>'
                                        });
                                        ezuiDatagrid.datagrid('reload');
                                    }
                                }
                            });
                        }
                    })

                }else {
                    $.messager.show({
                        msg : '该项已在审核中，请重新选择!', title : '提示'
                    });
				}
            }

		});



    }else{
        $.messager.show({
            msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
        });
    }


}





		</script>
		<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table style="text-align: right">
						<tr>
						<th>收货单位</th>
						<td><input type='text' id='enterpriseNa' name="enterpriseName" class='easyui-textbox' size='16' data-options=''/></td>
						<th>收货单位代码</th>
						<td><input type='text' id="enterpriseNumber"  name='enterpriseNo'  size='16' class='easyui-textbox' data-options=''/></td>
						<th>收货单位简称</th>
						<td><input type='text' id="shortName"   name='shorthandName' size='16'  class='easyui-textbox' data-options=''/></td>

						</tr>

						<tr>
							<th>货主ID</th><td><input type='text' id='clientId' name="clientId" class='easyui-textbox' size='16' data-options=''/></td>
							<th>供应商</th><td><input type='text' id='supplierId' name="supplierId" class='easyui-textbox' size='16' data-options=''/></td>

						</tr>
						<tr>
							<th>是否需要审核</th><td><input type='text' id='isCh' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否医废</th><td><input type='text' id='isR' name="isReturn" class='easyui-textbox' size='16' data-options=''/></td>

							<th>是否有效</th><td><input type='text' id='isU' name="isUse" class='easyui-textbox' size='16' data-options=''/></td>
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
					<a onclick='xiafa();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
					<a onclick='newAdd();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
					<%--<a onclick='confirmApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>确认申请</a>--%>
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
