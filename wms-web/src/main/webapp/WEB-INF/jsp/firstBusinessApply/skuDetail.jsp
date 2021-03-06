<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<html>
<head>
    <title>Title</title>
</head>
<c:import url='/WEB-INF/jsp/include/meta.jsp'/>
<c:import url='/WEB-INF/jsp/include/easyui.jsp'/>
<body>

<form id='ezuiFormDetail1' method='post'>
    <fieldset>
        <legend>产品首营信息</legend>
        <input type='hidden' id='applyId1' name='applyId1' value="${firstBusinessApply1.applyId}"/>
        <table>
            <tr>
                <th>委托客户</th>
                <td>
                    <input type='text' value="${firstBusinessApply1.clientName}" id='clientName1' class='easyui-textbox'
                           data-options='required:true,width:200'/>
                    <input type="hidden" name="clientId1" id="clientId1" value="${firstBusinessApply1.clientId}"/>
                    <input type="hidden" name="cliedit_enterpriseId" id="cliedit_enterpriseId"
                           value="${firstBusinessApply1.clientEnterpeiseId}"/>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options=""
                       onclick="viewEditClientEnterpriseUrl()">查看</a>

                </td>
                <th>供应商</th>
                <td>
                    <input type='text' value="${firstBusinessApply1.supplierName}" id='supplierName1'
                           class='easyui-textbox' data-options='required:true,width:200'/>
                    <input type="hidden" name="supplierId1" id="supplierId1" value="${firstBusinessApply1.supplierId}"/>
                    <input type="hidden" name="supedit_enterpriseId" id="supedit_enterpriseId"
                           value="${firstBusinessApply1.supplierEnterpeiseId}"/>
                    <a href="javascript:void(0);" class="easyui-linkbutton" data-options=""
                       onclick="viewEditSupplierEnterpriseUrl()">查看</a>

                </td>
                <th>产品线</th>
                <td>
                    <input id="productLine1" name="productLine1" data-options='required:true' type="text"/>
                </td>

                <th>产品</th>
                <td>
                    <input type='text' id='productName2' value="${firstBusinessApply1.productName}"
                           class='easyui-textbox' data-options='required:true,width:200'/>
                    <input type="hidden" name="specsId1" id="specsId1" value="${firstBusinessApply1.specsId}"/>
                </td>


            </tr>
        </table>
    </fieldset>
</form>
<div>

</div>
<table id='ezuiDatagridDetail2'></table>

<div id='ezuiBtn' style="display: none">

</div>

<div id='ezuiDialogClientDetail1' style='padding: 10px;display: none'>
    <div id='client1TB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th>
                    <td><input type='text' id='kehudaimaE' class='easyui-textbox' data-options='width:200'/></td>
                    <th>客户名称：</th>
                    <td><input type='text' id='kehumingcehngE' class='easyui-textbox' data-options='width:200'/></td>

                    <td>
                        <a onclick='doSearchClient1();' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseClientSelect1()' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridClientDetail1">

    </table>
</div>
<%--供应商--%>
<div id='ezuiDialogSupplierDetail1' style='padding: 10px;display: none'>
    <div id='TB1' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>供应商信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th>
                    <td><input id="kehudaimaS" type='text' class='easyui-textbox' data-options='width:200'/></td>
                    <th>客户名称：</th>
                    <td><input id="kehumingchengS" type='text' class='easyui-textbox' data-options='width:200'/></td>

                    <td>
                        <a onclick='doSearchSupplier1();' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSupplierSelect1()' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>

            </table>
        </fieldset>

    </div>
    <table id="dataGridSupplierDetail1">

    </table>
</div>

<!--产品查询列表dialog -->
<div id='ezuiDialogSpec1' style='padding: 10px;display: none'>
    <div id='productToolbar1' class='datagrid-toolbar' style='display: none'>
        <fieldset>
            <legend>产品注册证信息</legend>
            <table>
                <tr>
                    <th>产品代码</th>
                    <td><input type='text' id='productCodeE' class='easyui-textbox' data-options='width:200'/></td>
                    <th>产品名称</th>
                    <td><input type='text' id='productNameE' class='easyui-textbox' data-options='width:200'/>
                        <a onclick='searchProductE();' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseProductSelectE()' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>

                </tr>
                <tr>
                    <th>规格</th>
                    <td><input type='text' id='specsNameE' class='easyui-textbox' data-options='width:200'/></td>

                    <th>注册证号</th>
                    <td><input type='text' id='registerNoE' class='easyui-textbox' data-options='width:200'/></td>

                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridProduct1">

    </table>
</div>

<div id="enterpriseDialog1">

</div>
<div id="productRegisterDialog1">

</div>


<div id="ezuiDialogEditClientEnterprise">

</div>
<div id="ezuiDialogEditSupplierEnterprise">

</div>
<script>
    var ezuiDatagridDetail2; //主页
    var enterpriseCustomerDialog;
    var enterpriseSupplierDialog;
    var productDialog;
    // var ezuiDialogSpec;
    var ezuiDialogSpec1;

    var dataGridProduct1;
    var clientDatagrid1;
    var supplierDatagrid1;
    var ezuiDialogClientDetail1;
    var ezuiDialogSupplierDetail1;
    var ezuiDialogProductDetail;
    var arr;
    var arr1;

    var ezuiDialogEditSupplierEnterprise;
    var ezuiDialogEditClientEnterprise;
    var enterpriseDialog1;
    var productRegisterDialog1;

    $(function () {


        //主页面
        ezuiDatagridDetail2 = $("#ezuiDatagridDetail2").datagrid({
            url: sy.bp() + '/firstBusinessApplyController.do?showSpecsDatagrid',
            method: 'POST',
            toolbar: '#detailToolbar',
            title: '',
            pageSize: 50,
            pageList: [50, 100, 200],
            border: false,
            fitColumns: false,
            nowrap: true,
            striped: true,
            collapsible: false,
            queryParams: {
                'applyId': <c:choose><c:when test="${firstBusinessApply1.applyId == null}">'empty'
                </c:when><c:otherwise>'${firstBusinessApply1.applyId}'</c:otherwise></c:choose>
            },
            pagination: true,
            rownumbers: true,
            idField: 'productApplyId',
            columns: [[
                {field: 'productApplyId', title: '主键', hidden: true},
                {field: 'specsId', title: '主键', hidden: true},
                {field: 'customerid', title: '主键', hidden: true},
                {field: 'productCode', title: '产品代码', width: '10%'},
                {field: 'productName', title: '产品名称', width: '10%'},
                {field: 'specsName', title: '规格', width: '10%'},
                {field: 'productModel', title: '产品型号', width: '15%'},
                {field: 'supplierName', title: '供应商', width: '20%'},
                {field: 'productRegisterNo', title: '注册证编号', width: '20%'},
                {field: 'productRegisterId', title: '注册证主键', hidden: true},
                {
                    field: '_operate22', title: '查看注册证', width: '10%',
                    formatter: formatOper2
                },
                {
                    field: '_operate2', title: '查看产品', width: '10%',
                    formatter: formatOper
                }


            ]],
            onDblClickCell: function (index, field, value) {

            },
            onRowContextMenu: function (event, rowIndex, rowData) {

            },
            onSelect: function (rowIndex, rowData) {

            },
            onLoadSuccess: function (data) {
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize", {height: 500});
            }
        });


        //货主弹窗列表
        clientDatagrid1 = $("#dataGridClientDetail1").datagrid({
            url: sy.bp() + '/basCustomerController.do?showDatagrid',
            method: 'POST',
            toolbar: '#client1TB',
            title: '',
            pageSize: 50,
            pageList: [50, 100, 200],
            border: false,
            fitColumns: false,
            nowrap: true,
            striped: true,
            queryParams: {
                isUse: '1',
                activeFlag: '1',
                customerType: 'OW'
            },
            fit: true,
            collapsible: false,
            pagination: true,
            rownumbers: true,
            singleSelect: true,
            idField: 'clientId',
            columns: [[
                {
                    field: 'customerType', title: '客户类型 ', width: 80, formatter: function (value, rowData, rowIndex) {
                        if (rowData.customerType == 'CO') {
                            return rowData.customerType = '收货单位';
                        } else if (rowData.customerType == 'VE') {
                            return rowData.customerType = '供应商';
                        } else if (rowData.customerType == 'CA') {
                            return rowData.customerType = '承运商';
                        } else if (rowData.customerType == 'OT') {
                            return rowData.customerType = '其他';
                        } else if (rowData.customerType == 'OW') {
                            return rowData.customerType = '货主';
                        } else if (rowData.customerType == 'PR') {
                            return rowData.customerType = '生产企业';
                        } else if (rowData.customerType == 'WH') {
                            return rowData.customerType = '主体';
                        }
                    }
                },
                {
                    field: 'activeFlag', title: '是否合作 ', width: 80, formatter: function (value, rowData, rowIndex) {
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }
                },
                {field: 'customerid', title: '客户代码', width: 80},
                {field: 'descrC', title: '客户名称', width: 80},
                {field: 'enterpriseNo', title: '企业信息代码 ', width: 80},
                {field: 'shorthandName', title: '简称 ', width: 85},
                {field: 'enterpriseName', title: '企业名称 ', width: 80},
                {field: 'contacts', title: '联系人 ', width: 85},
                {field: 'contactsPhone', title: '联系人电话 ', width: 80},
                {field: 'remark', title: '备注 ', width: 85},
            ]],
            onDblClickCell: function (index, field, value) {
                choseClientSelect1();
            },
            onRowContextMenu: function (event, rowIndex, rowData) {

            },
            onSelect: function (rowIndex, rowData) {

            },
            onLoadSuccess: function (data) {
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize", {height: 540});
            }
        });


        //供应商
        supplierDatagrid1 = $("#dataGridSupplierDetail1").datagrid({
            url: sy.bp() + '/basCustomerController.do?showDatagrid',
            method: 'POST',
            toolbar: '#TB1',
            title: '',
            pageSize: 50,
            pageList: [50, 100, 200],
            border: false,
            fitColumns: false,
            nowrap: true,
            striped: true,
            queryParams: {
                isUse: '1',
                customerType: 'VE',
                activeFlag: '1',
            },
            fit: true,
            collapsible: false,
            pagination: true,
            rownumbers: true,
            singleSelect: true,
            idField: 'clientId',
            columns: [[
                {
                    field: 'customerType', title: '客户类型 ', width: 100, formatter: function (value, rowData, rowIndex) {
                        if (rowData.customerType == 'CO') {
                            return rowData.customerType = '收货单位';
                        } else if (rowData.customerType == 'VE') {
                            return rowData.customerType = '供应商';
                        } else if (rowData.customerType == 'CA') {
                            return rowData.customerType = '承运商';
                        } else if (rowData.customerType == 'OT') {
                            return rowData.customerType = '其他';
                        } else if (rowData.customerType == 'OW') {
                            return rowData.customerType = '货主';
                        } else if (rowData.customerType == 'PR') {
                            return rowData.customerType = '生产企业';
                        } else if (rowData.customerType == 'WH') {
                            return rowData.customerType = '主体';
                        }
                    }
                },
                {
                    field: 'activeFlag', title: '是否合作 ', width: 100, formatter: function (value, rowData, rowIndex) {
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }
                },
                {field: 'customerid', title: '客户代码', width: 150},
                {field: 'descrC', title: '客户名称', width: 150},
                {field: 'enterpriseNo', title: '企业信息代码 ', width: 80},
                {field: 'shorthandName', title: '简称 ', width: 85},
                {field: 'enterpriseName', title: '企业名称 ', width: 80}
            ]],
            onDblClickCell: function (index, field, value) {
                choseSupplierSelect1();
            },
            onRowContextMenu: function (event, rowIndex, rowData) {

            },
            onSelect: function (rowIndex, rowData) {

            },
            onLoadSuccess: function (data) {
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize", {height: 540});
            }
        });


        $("#clientName1").textbox({
            icons: [{
                value: '${firstBusinessApply1.clientName}',
                iconCls: 'icon-search',
                handler: function (e) {
                    //searchCustomerEnterprise();
                    searchClient1();
                }
            }]
        });

        $("#supplierName1").textbox({
            value: "${firstBusinessApply1.supplierName}",
            width: 150,
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    searchSupplier1();
                }
            }]
        });

        $("#productName2").textbox({
            value: "${firstBusinessApply1.productName}",
            width: 150,
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    searchProductdialog();
                }
            }]
        });


        //产品
        dataGridProduct1 = $("#dataGridProduct1").datagrid({
            url: sy.bp() + '/gspProductRegisterSpecsController.do?showDatagrid',
            method: 'POST',
            toolbar: '#productToolbar1',
            title: '',
            pageSize: 50,
            pageList: [50, 100, 200],
            border: false,
            fitColumns: false,
            nowrap: true,
            striped: true,
            collapsible: false,
            fit: true,
            pagination: true,
            singleSelect: true,
            queryParams: {
                isUse: '1',
                type: 'CER',
                // supplierId:$("#supplierId").val(),
            },
            rownumbers: true,
            idField: 'specsId',
            columns: [[
                // {field: 'ck',checkbox:true },
                {field: 'specsId', title: '主键', hidden: true},
                {field: 'productCode', title: '产品代码', width: '15%'},
                {field: 'productName', title: '产品名称', width: '40%'},
                {field: 'specsName', title: '规格', width: '10%'},
                {field: 'productRegisterNo', title: '产品注册证', width: '20%'},
                {
                    field: '_operate', title: '查看产品', width: '10%',
                    formatter: formatOper
                }


            ]],
            onDblClickCell: function (index, field, value) {
                choseProductSelectE();
            },
            onRowContextMenu: function (event, rowIndex, rowData) {

            },
            onSelect: function (rowIndex, rowData) {

            },
            onLoadSuccess: function (data) {
                <%--$(this).datagrid("resize",{height:540});--%>
                <%--$.ajax({--%>
                <%--url : '/firstBusinessApplyController.do?showSpecsDatagrid',--%>
                <%--data : {'applyId':"<c:choose><c:when test="${firstBusinessApply.applyId == null}">'empty'</c:when><c:otherwise>'${firstBusinessApply.applyId}'</c:otherwise></c:choose>"},--%>
                <%--type : 'POST',--%>
                <%--dataType : 'JSON',--%>
                <%--success : function(result){--%>
                <%--//console.log(result);--%>
                <%--if(result){--%>
                <%--for(var i=0;i<result.rows.length;i++){--%>
                <%--//console.log(result.rows[i]);--%>
                <%--dataGridProduct.datagrid("selectRecord",result.rows[i].specsId);--%>
                <%--}--%>
                <%--}--%>
                <%--}--%>
                <%--});--%>
            }
        });


        //搜索打开产品弹窗
        function searchProductdialog() {
            // ezuiDialogSpec1.dialog('open')
            ezuiDialogSpec1 = $('#ezuiDialogSpec1').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                width: 850,
                height: 500,
                cache: false,
                onClose: function () {
                    // ezuiFormClear(ezuiForm);
                    // $(this).dialog('destroy');
                    // enterpriseDialog1
                    // ezuiFormClear(ezuiDialogEditClientEnterprise);
                }
            });
        }


        //产品线1
        var customerid1 = $("#clientId1").val();
        <c:choose>
        <c:when test="${firstBusinessApply1.productline != null}">

        $("#productLine1").combobox({
            panelHeight: 'auto',
            url: '/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId=' + customerid1,
            valueField: 'id',
            textField: 'value',
            onLoadSuccess: function () {
                $("#productLine1").combobox("setValue", '${firstBusinessApply1.productline}');
            }
        })
        </c:when>
        <c:otherwise>
        $("#productLine1").combobox({
            panelHeight: 'auto'
        });
        </c:otherwise>
        </c:choose>
    });


    //查看委托方企业详情
    function viewEditClientEnterpriseUrl() {
        console.log($("#ezuiFormDetail1 input[id='cliedit_enterpriseId']").val());
        var enterpriseId = $("#ezuiFormDetail1 input[id='cliedit_enterpriseId']").val();
        if (enterpriseId == null || enterpriseId == "") {
             //enterpriseId = $("#enterpriseId").val();
        }

        ezuiDialogEditClientEnterprise = $('#ezuiDialogEditClientEnterprise').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            buttons: '',
            href: sy.bp() + "/gspEnterpriseInfoController.do?toDetail&id="+enterpriseId,
            width: 1200,
            height: 530,
            closable: true,
            cache: false,
            onClose: function () {
                ezuiFormClear(ezuiDialogEditClientEnterprise);
                $(this).dialog("clear");
            }
        })


        if (enterpriseId != null && enterpriseId != "") {
            ezuiDialogEditClientEnterprise.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail" + "&id=" + enterpriseId).dialog('open');
            enterpriseId = "";
        } else {
            $.messager.show({
                msg: '请先选择企业', title: '提示'
            });
        }
    }


    //查看供应商企业详情
    function viewEditSupplierEnterpriseUrl() {

        var enterpriseId = $("#ezuiFormDetail1 input[id='supedit_enterpriseId']").val();
        if (enterpriseId == null || enterpriseId == "") {
            // enterpriseId = $("#enterpriseId").val();
        }
        ezuiDialogEditSupplierEnterprise = $('#ezuiDialogEditSupplierEnterprise').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            buttons: '',
            href: sy.bp() + "/gspEnterpriseInfoController.do?toDetail&id="+enterpriseId,
            width: 1200,
            height: 530,
            closable: true,
            cache: false,
            onClose: function () {
                ezuiFormClear(ezuiDialogEditSupplierEnterprise);
                $(this).dialog("clear");
            }
        })
        //processType = 'edit';

        //var row = ezuiDatagrid.datagrid('getSelected');
        console.log($("#ezuiFormDetail1 input[id='cliedit_enterpriseId']").val());
        if (enterpriseId != null && enterpriseId != "") {
            ezuiDialogEditSupplierEnterprise.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail" + "&id=" + enterpriseId).dialog('open');
            enterpriseId = "";
        } else {
            $.messager.show({
                msg: '请先选择企业', title: '提示'
            });
        }
    }


    function operateGrid1(id) {
        // processType = 'product';
        enterpriseDialog1 =  $('#enterpriseDialog1').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            href: sy.bp() + "/gspProductRegisterSpecsController.do?toAdd&specsId=" + id + "&type=product",
            width: 1050,
            height: 550,
            cache: false,
            onClose: function () {
                enterpriseDialog1.dialog('clear');
                // ezuiFormClear(enterpriseDialog1);
            }
        })
    }

    function formatOper(value, row, index) {
        // $('#enterpriseDialog').dialog('destroy');
        return "<a onclick=\"operateGrid1('" + row.specsId + "')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function formatOper2(value, row, index) {
        // $('#enterpriseDialog').dialog('destroy');


        if (row.productRegisterId != null && row.productRegisterId != "") {
            return "<a onclick=\"operateGridProductRegister2('" + row.productRegisterId + "')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
        } else {
            return "<a>无</a>";

        }
    }

    function operateGridProductRegister2(id) {
        console.log("---------->" + id);
        productRegisterDialog1 = $('#productRegisterDialog1').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            href: sy.bp() + "/gspProductRegisterController.do?toDetail&id=" + id,
            fit: true,
            cache: false,

            onClose: function () {
                // ezuiFormClear(ezuiForm);
                // ezuiFormClear(productRegisterDialog1);
                productRegisterDialog1.dialog('clear');
            },
            onLoad: function () {

            }
        });

    }


    function searchCustomerEnterprise() {
        enterpriseCustomerDialog = $('#enterpriseCustomerDialog').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            href: sy.bp() + "/gspEnterpriseInfoController.do?toSearchDialog&target=enterpriseCustomerApply&type=customer",
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {

            }
        })
    }

    function choseSelect_enterpriseCustomerApply(id, name) {
        //$("input[name='enterpriseId']").val(id);
        //$("#enterpriseIdQuery").textbox("setValue",name);
        console.log(id)
        $("#clientName").textbox("setValue", name);          //
        $("#clientId").val(id);//是enterpriseId不是customerId
        $("#cliedit_enterpriseId").val(id);

        enterpriseCustomerDialog.dialog("close");

    }

    function searchSupplierEnterprise() {
        enterpriseSupplierDialog = $('#enterpriseSupplierDialog').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            href: sy.bp() + "/gspEnterpriseInfoController.do?toSearchDialog&target=enterpriseSupplierApply&type=supplier",
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {

            }
        })
    }

    function choseSelect_enterpriseSupplierApply(id, name, target) {
        $("#supplierName").textbox("setValue", name);
        $("#supplierId").val(id);
        enterpriseSupplierDialog.dialog("close");
    }



    //查询产品
    function searchProductE() {
        dataGridProduct1.datagrid("load", {
            "productCode": $("#ezuiDialogSpec1 #productCodeE").val(),
            "productName": $("#ezuiDialogSpec1 #productNameE").val(),
            "productRegisterNo": $("#ezuiDialogSpec1 #registerNoE").val(),
            "specsName": $("#ezuiDialogSpec1 #specsNameE").val(),
            "isUse": "1",
            "type": "CER"
        })
    }

    //双击选中产品
    function choseProductSelectE() {
        var row = dataGridProduct1.datagrid("getSelected");
        console.log(row);
        if (row) {
            $("#specsId1").val(row.specsId);
            $("#productName2").textbox("setValue", row.productName);

            ezuiDialogSpec1.dialog('close');
        }
    }


    function clearApply() {
        $("#clientName").textbox("setValue", "");
        $("#clientId").val("");

        $("#supplierName").textbox("setValue", "");
        $("#supplierId").val("");

        ezuiDatagridDetail2.datagrid("reload", {"applyId": "empty"});
        arr.splice(0, arr.length);
        arr1.splice(0, arr.length);
    }


    //选择货主
    function choseClientSelect1() {
        var row = clientDatagrid1.datagrid("getSelected");
        if (row) {
            $("#clientId1").val(row.customerid);
            $("#clientName1").textbox("setValue", row.descrC);
            $("#cliedit_enterpriseId").val(row.enterpriseId);

            console.log(row.customerid);
            $("#productLine1").combobox({
                panelHeight: 'auto',
                url: '/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId=' + row.customerid,
                valueField: 'id',
                textField: 'value',
                onLoadSuccess: function () {

                }
            })
            console.log(row.customerid + '结束');
            ezuiDialogClientDetail1.dialog('close');
        }
    }

    //双击选中供应商
    function choseSupplierSelect1() {
        var row = supplierDatagrid1.datagrid("getSelected");
        console.log(row);
        if (row) {
            $("#supplierId1").val(row.customerid);
            $("#supplierName1").textbox("setValue", row.descrC);
            $("#ezuiFormDetail1 input[id='supedit_enterpriseId']").val(row.enterpriseId);

            ezuiDialogSupplierDetail1.dialog('close');
        }
    }

    //供应商弹窗搜索功能
    function doSearchSupplier1() {
        supplierDatagrid1.datagrid('load', {

            descrC: $('#kehumingchengS').val(),
            customerid: $('#kehudaimaS').val(),
            activeFlag: '1',
            isUse: '1',
            customerType: 'VE'
        });
    }

    //打开货主弹窗
    function searchClient1() {

        //货主弹窗
        ezuiDialogClientDetail1 = $('#ezuiDialogClientDetail1').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {
                ezuiFormClear(ezuiForm);
            }
        });
    }

    //搜索打开供应商弹窗
    function searchSupplier1() {

        //供应商弹窗
        ezuiDialogSupplierDetail1 = $('#ezuiDialogSupplierDetail1').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {
                ezuiFormClear(ezuiForm);
            }
        });
    }

    //searchProduct1
    //查询货主信息条件
    function doSearchClient1() {
        clientDatagrid1.datagrid('load', {
            // enterpriseName : $('#qiyemingcheng2').val(),
            // enterpriseNo : $('#qiyexinxidaima1').val(),
            descrC: $('#kehumingcehngE').val(),
            customerid: $('#kehudaimaE').val(),
            activeFlag: '1',
            isUse: '1',
            customerType: 'OW'
        });
    }


</script>
</body>
</html>
