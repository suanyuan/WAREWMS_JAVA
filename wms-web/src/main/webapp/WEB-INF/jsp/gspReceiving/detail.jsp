<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>


</head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp' />
    <c:import url='/WEB-INF/jsp/include/easyui.jsp' />


<body>


<div id='ezuiDialog' style='padding: 10px;'>
<form id='ezuiFormAddress' method='post'>
    <input type='hidden' id='hiddenreceivingId' name='receivingId' value="${receivingId}" class="textbox-value"/>
    <input type="hidden" id="hiddencustomerType" name='customerType'  value="CO" class="textbox-value"/>
    <input type='hidden' id='enterpriseId' name='enterpriseId' value="${enterpriseId}"  class='textbox-value'/>
    <input type='hidden' id='first' name='firstState' value="${gspReceiving.firstState}"  class='textbox-value'/>
    <fieldset>

        <table style="text-align: right">

         <tr>
            <th>收货单位</th>
            <td>
                <input type='text' id="enterpriseN"  name='enterpriseName'   size='16' class='easyui-textbox' data-options='required:true'/>
                <%--<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>--%>
            </td>
             <th>收货单位代码</th>
             <td><input type='text' id="enterpriseNo" style="width: 150px" name='enterpriseNo' value='${gspEnterpriseInfo.enterpriseNo}'   size='16' class='easyui-textbox' data-options='required:true'/></td>
             <th>收货单位简称</th>
             <td><input type='text' id="s" style="width: 150px"  name='shorthandName' size='16' value='${gspEnterpriseInfo.shorthandName}' class='easyui-textbox' data-options='required:true'/></td>
       </tr>
       <tr>
         <th>供应商</th>
          <td>
                <input type='text' name='supplierId' id="supplier"  size="16" class='easyui-textbox'  data-options='required:true'/>
                <%--<a href="javascript:void(0)" onclick="searchSupplier()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"/>--%>
          </td>
           <th>货主</th>
           <td><input type='text' name='clientId' id="client" style="width: 150px"  size='16' value='${gspReceiving.clientId}' class='easyui-textbox' data-options='required:true'/></td>
       </tr>
            <tr>
                <th>是否医废</th>
                <td><input type='text' name='isReturn' id="return" style="width: 150px" class='easyui-combobox' value="${gspReceiving.isReturn}" size="16" data-options='required:true,editable:false'/></td>
                <th>是否有效</th>
                <td><input type='text' name='isUse'  style="width: 150px" id="use" class='easyui-combobox' value="${gspReceiving.isUse}"  size="16" data-options='required:true,editable:false'/></td>


           <%-- <th>地址</th>
            <td><input type='text' name='deliveryAddress' class='easyui-textbox ' value="${gspReceivingAddress.deliveryAddress}" size="16" data-options='required:true'/></td>--%>

               <th>是否需要审核</th>
               <td><input type='text' name='isCheck'  style="width: 150px" id="isChec"  value="${gspReceiving.isCheck}" class='easyui-combobox' size="16" data-options='required:true,editable:false'/></td>
            <th style="text-align: right">
                <a onclick='dooSubmit();' id='ezuiBtn_commit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
                <a onclick='ezuiToolbarClear("#ezuiFormAddress");' id="ezuiBtn_clear" class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
            </th>
        </tr>
    </table>
    </fieldset>
</form>


     <table id='ezuiDetailsDatagrid'></table>
    <div>
        <a onclick='AddAddress();' id='ezuiDetailsBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.skuAdd'/></a>
        <a onclick='AddressEdit();' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.skuEdit'/></a>
        <a onclick='AddressDel();' id='ezuiDetailsBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.skuDelete'/></a>
        <a onclick='clearDatagridSelected("#ezuiDetailsDatagrid");' id="clearBtn"  class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
    </div>

    <div id="dialogAddAddress" style='padding: 10px;'>


    </div>


    <div id="dialogEnterprise">

    </div>



    <div id='ezuiDialogDetail' style='padding: 10px;'>

        <div id='detailToolbar' class='datagrid-toolbar' style=''>
            <fieldset>
                <legend>企业信息</legend>
                <table>
                    <tr>
                        <th>代码</th>
                        <td><input type='text' id='enterprise' class='easyui-textbox' data-options='width:200'/></td>
                        <th>简称</th>
                        <td><input type='text' id='shorthandName' class='easyui-textbox' data-options='width:200'/></td>
                        <td>
                            <a onclick='doSearchEnterprise();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                            <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <table id="dataGridDetail">

        </table>
    </div>
</div>






<div id='ezuiDialogSupplierDetail' style='padding: 10px;'>
    <div id='TB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>代码：</th><td><input type='text'  class='easyui-textbox' data-options=''/></td>
                    <th>简称：</th><td><input type='text'  class='easyui-textbox' data-options=''/></td>
                </tr>
                <tr>
                    <th>企业：</th>
                    <td>
                        <input type='text' style="width: 170px;"/>
                        <input type="hidden" class="easyui-textvalue" name="enterpriseId">
                        <!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
                    </td>
                    <th>首营状态：</th><td><input type='text' class='easyui-textbox' data-options=''/></td>
                    <td>
                        <a onclick='doSearchSupplier();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSupplierSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
       <%-- <fieldset>
            <legend>供应商信息</legend>
            <table>
                <tr>
                    <th>供应商</th>
                    <td><input type='text' id='supplierId' class='easyui-textbox' data-options='width:200'/></td>
                    <th>类型</th>
                    <td><input type='text' id='operationType' class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchSupplier();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSupplierSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>--%>
    </div>
    <table id="dataGridSupplierDetail">

    </table>
</div>

<div id="dialogSupplier">
</div>

<div id='ezuiDialogClientDetail' style='padding: 10px;'>
    <div id='clientTB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>代码：</th><td><input type='text' id='clientNoQuery' class='easyui-textbox' data-options=''/></td>
                    <th>简称：</th><td><input type='text' id='clientNameQuery' class='easyui-textbox' data-options=''/></td>
                </tr>
                <tr>
                    <th>企业：</th>
                    <td>
                        <input type='text' id='enterpriseIdQuery' style="width: 170px;"/>
                        <input type="hidden" class="easyui-textvalue" name="enterpriseId">
                        <!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
                    </td>
                    <th>首营状态：</th><td><input type='text' id='firstState' class='easyui-textbox' data-options=''/></td>
                    <td>
                        <a onclick='doSearchClient();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridClientDetail">

    </table>
</div>

<div id="dialogClient">
</div>



<div id='ezuiBtn' style="display: none">
    <a onclick='doSubmitAddress();' id='ezuiBtn_commit1' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose(dialogAddAddress);' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<script>
    var dialogClient;
    var ezuiDialogClientDetail;
    var clientDatagrid;

    var processType;
    var dialogAddAddress;
    var enterpriseDatagrid;
    var dataGridDetail;
    var dialogSupplier;
    var supplierDatagrid;
    var ezuiDialogSupplierDetail;

    var dialogEnterprise;

    var  dialogAddAddressForm;
    var ezuiFormAddress;



    $(function () {
        ezuiFormAddress=$('#ezuiFormAddress').form();
        dialogAddAddressForm=$('#dialogAddAddressForm').form();
            binaji();
        $("#enterpriseN").textbox({
            value:"${gspEnterpriseInfo.enterpriseName}",
            width:150,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        });
        $("#client").textbox({
            value:"${gspReceiving.clientId}",
            width:150,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchClient();
                }
            }]
        });

        $("#supplier").textbox({
            value:"${gspReceiving.supplierId}",
            width:150,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchSupplier();
                }
            }]
        });

        $('#contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });

        $("#ezuiFormAddress input[name='isCheck']").combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });
        $("#ezuiFormAddress input[name='isReturn']").combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });

        $("#ezuiFormAddress input[name='isUse']").combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });

        //收货地址列表（数据窗口）

        ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({

            url : '<c:url value="/gspReceivingAddressController.do?showDatagrid&receivingId=${receivingId}"/>',
            method : 'POST',
            toolbar : '',
            idField : 'receivingId',
            title : '收货地址列表',
            pageSize : 200,
            pageList : [50, 100, 200],
            border : false,
            fitColumns : false,
            nowrap : false,
            striped : true,
            collapsible : false,
            pagination:true,
            rownumbers : true,
            singleSelect : true,
            checkOnSelect:true,
            selectOnCheck: false,
            columns : [[
                {field: 'receivingAddressId',	hidden:true,	title: '待输入栏位0',	width: 10 },
                {field: 'receivingId',		hidden:true,  title: '待输入栏位1',	width: 90 },
                {field: 'sellerName',		title: '销售人',	width:60  },
                {field: 'country',		title: '国家',	width: 50 },

                {field: 'province',		title: '省',	width: 50 },
                {field: 'city',		title: '市',	width:50 },
                {field: 'district',		title: '区',	width:50 },

                {field: 'deliveryAddress',		title: '地址',	width:90 },
                {field: 'zipcode',		title: '邮编',	width: 90 },
                {field: 'contacts',		title: '联系人',	width: 90},
                {field: 'phone',		title: '联系人电话',	width: 90 },
                {field: 'isDefault',		title: '是否默认',	width: 90 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isDefault == '1' ? '是' : '否';
                    }},
                {field: 'createId',		title: '创建人',	width: 90},
                {field: 'createDate',		title: '创建日期',	width: 90 },
                {field: 'editId',		title: '修改人',	width: 90},
                {field: 'editDate',		title: '修改日期',	width: 100 }
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {
            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:300});


                if (processType == 'add') {
                    $("isChec").combobox('setValue','1')

                }
            }
        });

        enterpriseDatagrid = $("#dataGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagrid',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '<spring:message code="common.dialog.title"/>',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 81 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: 91 },
                {field: 'shorthandName',		title: '简称',	width: 91 },
                {field: 'enterpriseName',		title: '企业名称',	width: 91 },
                {field: 'enterpriseType',		title: '企业类型',	width: 91 ,formatter: entTypeFormatter},
                {field: 'createId',		title: '录入人',	width:81 },
                {field: 'createDate',		title: '录入时间',	width: 81 },
                {field: 'editId',		title: '修改人',	width: 81 },
                {field: 'editDate',		title: '修改时间',	width: 81 },
                {field: 'isUse',		title: '是否有效',	width: 91,formatter:isUseFormatter }
            ]],
            onDblClickCell: function(index,field,value){
                choseSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});



            }
        });


        dataGridDetail = $('#ezuiDialogDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
               ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogEnterprise = $('#dialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            cache: false,
            onClose : function() {
             //  ezuiFormClear(ezuiForm);
            }
        }).dialog('close');


//供应商初始化
       /* supplierDatagrid = $("#dataGridSupplierDetail").datagrid({
            url : sy.bp()+'/gspSupplierController.do?showDatagrid',
            method:'POST',
            toolbar : '#TB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'supplierId',		title: '主键',	width: 88,hidden:true },
                {field: 'enterpriseId',		title: '企业流水号',	width: 88 ,hidden:true},

                {field: 'operateType',		title: '企业类型',	width: 88 ,hidden:true},

                {field: 'firstState',		title: '首营状态',	width: 88 ,formatter:firstStateFormatter

                },
                {field: 'enterpriseNo',		title: '企业信息代码',	width: 88 },
                {field: 'shorthandName',		title: '简称',	width: 88 },
                {field: 'enterpriseName',		title: '企业名称',	width: 88 },
                {field: 'enterpriseType',		title: '企业类型',	width: 88 },
                {field: 'createId',		title: '创建人',	width: 88 },
                {field: 'createDate',		title: '创建时间',	width: 88 },
                {field: 'editId',		title: '编辑人',	width: 88 },
                {field: 'editDate',		title: '编辑时间',	width: 88 },
                {field: 'isCheck',		title: '是否审查',	width: 88 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isCheck == '1' ? '是' : '否';
                    }},
                {field: 'isUse',		title: '是否有效',	width: 88 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isUse == '1' ? '是' : '否';
                    }}
            ]],
            onDblClickCell: function(index,field,value){
                choseSupplierSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        }); */
        supplierDatagrid = $("#dataGridSupplierDetail").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#TB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'clientId',
            columns : [[
                {field: 'customerType',	title: '客户类型 ',	width: 80,formatter:function(value,rowData,rowIndex){
                        if (rowData.customerType=='CO') {
                            return rowData.customerType='收货单位';
                        }else if (rowData.customerType=='VE'){
                            return rowData.customerType='供应商';
                        }else if (rowData.customerType=='CA'){
                            return rowData.customerType='承运商';
                        }else if (rowData.customerType=='OT'){
                            return rowData.customerType='其他';
                        }else if (rowData.customerType=='OW'){
                            return rowData.customerType='货主';
                        }else if (rowData.customerType=='PR'){
                            return rowData.customerType='生产企业';
                        }else if (rowData.customerType=='WH'){
                            return rowData.customerType='主体';
                        }
                    } },
                {field: 'activeFlag',		title: '是否合作 ',	width: 80,formatter:function(value,rowData,rowIndex){
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }},
                {field: 'customerid',		title: '客户代码',	width: 80 },
                {field: 'descrC',		title: '客户名称',	width: 80 },
                {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 80 },
                {field: 'shorthandName',		title: '简称 ',	width: 85 },
                {field: 'enterpriseName',		title: '企业名称 ',	width: 80 },
                {field: 'contacts',		title: '联系人 ',	width: 85 },
                {field: 'contactsPhone',		title: '联系人电话 ',	width: 80 },
                {field: 'remark',		title: '备注 ',	width: 85 },
            ]],
            onDblClickCell: function(index,field,value){
                choseSupplierSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        });
//供应商弹窗
        ezuiDialogSupplierDetail = $('#ezuiDialogSupplierDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogSupplier = $('#dialogSupplier').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

//货主弹窗列表
        clientDatagrid = $("#dataGridClientDetail").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#clientTB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'clientId',
            columns : [[
                {field: 'customerType',	title: '客户类型 ',	width: 80,formatter:function(value,rowData,rowIndex){
                        if (rowData.customerType=='CO') {
                            return rowData.customerType='收货单位';
                        }else if (rowData.customerType=='VE'){
                            return rowData.customerType='供应商';
                        }else if (rowData.customerType=='CA'){
                            return rowData.customerType='承运商';
                        }else if (rowData.customerType=='OT'){
                            return rowData.customerType='其他';
                        }else if (rowData.customerType=='OW'){
                            return rowData.customerType='货主';
                        }else if (rowData.customerType=='PR'){
                            return rowData.customerType='生产企业';
                        }else if (rowData.customerType=='WH'){
                            return rowData.customerType='主体';
                        }
                    } },
                {field: 'activeFlag',		title: '是否合作 ',	width: 80,formatter:function(value,rowData,rowIndex){
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }},
                {field: 'customerid',		title: '客户代码',	width: 80 },
                {field: 'descrC',		title: '客户名称',	width: 80 },
                {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 80 },
                {field: 'shorthandName',		title: '简称 ',	width: 85 },
                {field: 'enterpriseName',		title: '企业名称 ',	width: 80 },
                {field: 'contacts',		title: '联系人 ',	width: 85 },
                {field: 'contactsPhone',		title: '联系人电话 ',	width: 80 },
                {field: 'remark',		title: '备注 ',	width: 85 },
            ]],
            onDblClickCell: function(index,field,value){
                choseClientSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        });
//货主弹窗
        ezuiDialogClientDetail = $('#ezuiDialogClientDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,

            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogClient = $('#dialogClient').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,

            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');




    });















    function searchEnterprise() {
        if(dataGridDetail){
            dataGridDetail.dialog('open');
        }

    }


    function doSearchEnterprise() {
        enterpriseDatagrid.datagrid('load', {
            enterpriseNo : $('#enterpriseNo').val(),
            shorthandName : $('#shorthandName').val(),
            isUse : '1'
        });
    }

    function choseSelect() {
        var row = enterpriseDatagrid.datagrid("getSelected");
        if(row){
            $("#enterpriseN").textbox("setValue",row.enterpriseName);
            $("#enterpriseNo").textbox("setValue",row.enterpriseNo);
            $("#s").textbox("setValue",row.shorthandName);
          // $("#enterpriseId").textbox("setValue",row.enterpriseId);
           $("#enterpriseId").val(row.enterpriseId);
         //   dialogEnterprise.dialog('close');
         // $("#ezuiFormAddress input[name='enterpriseId']").

            dataGridDetail.dialog('close');
        }
    }


    function searchClient() {
        if(ezuiDialogClientDetail){
            ezuiDialogClientDetail.dialog('open');
        }

    }

    function doSearchClient() {
        enterpriseDatagrid.datagrid('load', {
            enterpriseNo : $('#enterpriseNo').val(),
            shorthandName : $('#shorthandName').val(),
            isUse : '1'
        });
    }

    function choseClientSelect() {
        var row = clientDatagrid.datagrid("getSelected");
        if(row){
            $("#client").textbox("setValue",row.descrC);

            ezuiDialogClientDetail.dialog('close');
        }
    }



    function operateGrid(id) {
        dialogEnterprise.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    }

    function formatOper(value,row,index){
        return "<a onclick=\"operateGrid('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }


    function dooSubmit() {
        /*$("#ezuiFormAddress input[id='isCheck']")*/
        var a = $('#isChec').combobox('getValue');
        if (a=='1') {


            var   url = '<c:url value="/gspReceivingController.do?add"/>';

            $("#ezuiFormAddress").form('submit', {
                url : url+"&newreceivingId="+newreceivingId,
                onSubmit : function(){
                    console.log("1");
                    if(ezuiFormAddress.form('validate')){
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
                            $('#ezuiDialog').dialog('close');
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
                        $('#ezuiDialog').dialog('close')
                    }

                }
            });
        }else {
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否直接下发？',function (confirm) {
                if (confirm) {
                    $("#ezuiFormAddress").form('submit', {
                        url : '/basCustomerController.do?submit&newreceivingId='+newreceivingId,
                        onSubmit : function(){
                            if(ezuiFormAddress.form('validate')){
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
                                    $('#ezuiDialog').dialog('close');
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
                                $('#ezuiDialog').dialog('close')
                            }

                        }
                    });
                }
            });



        }



    }
//双击选中
    function choseSupplierSelect() {
        var row = supplierDatagrid.datagrid("getSelected");
        if(row){
            $("#supplier").textbox("setValue",row.descrC);

           // ezuiDialogClientDetail.dialog('close');
           // $("#supplier").textbox("setValue",row.supplierId);
            /* $("#operateType").textbox("setValue",row.operateType);
             $("#isUse").textbox("setValue",row.isUse);
             $("#isCheck").textbox("setValue",row.isCheck);*/
            ezuiDialogSupplierDetail.dialog('close');
        }
    }

    //弹窗搜索功能
    function doSearchSupplier() {
        supplierDatagrid.datagrid('load', {
            supplierId : $('#supplierId').val(),
            operateType : $('#operateType').val(),
            isUse : '1'
        });
    }
//搜索打开供应商弹窗
    function searchSupplier() {
        if(ezuiDialogSupplierDetail){
            ezuiDialogSupplierDetail.dialog('open');
        }

    }
//增加地址
    var  AddAddress=function () {
        processType = 'add';
        dialogAddAddress = $('#dialogAddAddress').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:320,
            height:390,
            href: sy.bp()+"/gspReceivingController.do?toDialogAddress"+"&receivingId="+$("#hiddenreceivingId").val(),
            buttons : '#ezuiBtn',
            onClose : function() {
                 ezuiFormClear(ezuiForm);
            }
        })

    };
    //地址的删除
    var AddressDel = function(){
        var row = ezuiDetailsDatagrid.datagrid('getSelected');
        if(row){
            console.log(row.receivingAddressId);
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
                if(confirm){
                    $.ajax({
                        url : 'gspReceivingAddressController.do?delete',
                        data : {receivingAddressId : row.receivingAddressId},
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
//地址的编辑
    var AddressEdit = function(){
        processType = 'edit';
        var row = ezuiDetailsDatagrid.datagrid('getSelected');
        //alert(row.supplierId);
        if(row){
            // ezuiForm.form('load',{
            //     // supplierId : row.supplierId,
            // 	// enterpriseId : row.enterpriseId,
            // 	// isCheck : row.isCheck,
            // 	// operateType : row.operateType,
            // 	// createId : row.createId,
            // 	// createDate : row.createDate,
            // 	// editId : row.editId,
            // 	// editDate : row.editDate,
            // 	// isUse : row.isUse
            // });
            dialogAddAddress.dialog('open');
        }else{
            $.messager.show({
                msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
            });
        }
    };

//控制是否编辑逻辑功能
function binaji() {



    if (processType != "newAdd"){


    var a = $("#first").val();

if (a=='40'||a =='10'){
    $('#enterpriseN').css('display', 'none');
    $('#enterpriseN').attr("readonly",true);
    $('#enterpriseNo').css('display', 'none');
    $('#enterpriseNo').attr("readonly",true);
    $('#use').css('display', 'none');
    $('#use').attr("readonly",true);
    $('#s').css('display', 'none');
    $('#s').attr("readonly",true);
    $('#return').css('display', 'none');
    $('#return').attr("readonly",true);
    $('#isChec').css('display', 'none');
    $('#isChec').attr("readonly",true);
    $('#client').css('display', 'none');
    $('#client').attr("readonly",true);
    $('#supplier').css('display', 'none');
    $('#supplier').attr("readonly",true);
    $('#ezuiDetailsBtn_add').css('display', 'none');
    $('#ezuiDetailsBtn_add').attr("readonly",true);
    $('#ezuiDetailsBtn_edit').css('display', 'none');
    $('#ezuiDetailsBtn_edit').attr("readonly",true);
    $('#ezuiDetailsBtn_del').css('display', 'none');
    $('#ezuiDetailsBtn_del').attr("readonly",true);
    $('#clearBtn').css('display', 'none');
    $('#clearBtn').attr("readonly",true);
    $('#ezuiBtn_commit').css('display', 'none');
    $('#ezuiBtn_commit').attr("readonly",true);
    $('#ezuiBtn_clear').css('display', 'none');
    $('#ezuiBtn_clear').attr("readonly",true);


}

}

}

</script>
</body>
</html>
