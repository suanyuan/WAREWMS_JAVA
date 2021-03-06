<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp'/>
    <c:import url='/WEB-INF/jsp/include/easyui.jsp'/>
    <style>
        table th {
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
        var ezuiDetailsDatagridAll;
        var ezuiDetailsDatagrid;
        var ezuiCustDataDialog;
        var ezuiCustDataDialogId;
        var ezuiReceDataDialog;
        var ezuiReceDataDialogId;
        var ezuiSkuDataDialog;
        var ezuiSkuDataDialogId;
        var ezuiLocDataDialog;
        var ezuiLocDataDialogId;
        var ezuiImportDataDialog;
        var ezuiImportDataForm;
        var ezuiOutToExcelDataDialog;
        var ezuiOutToExcel1Form;
        var ezuiPackDataDialog;
        var ezuiPackForm;
        var ezuiOperateResultDataDialog;
        var ezuiOperateResultDataForm;
        var allocationDetailsDatagrid;
        var ezuiCustomerDataDialogId;
        var ezuiCustomerDataDialog;

        var courierComplaintForm;       //快递投诉dialog form
        var courierComplaintDialog;     //快递投诉dialog
        var returnSfodd;
        var returnSfoddForm;
        var writeBackExpressDiv;
        var writeBackExpressForm;
        $(function () {
            ezuiMenu = $('#ezuiMenu').menu();
            ezuiDetailsMenu = $('#ezuiDetailsMenu').menu();
            ezuiForm = $('#ezuiForm').form();
            ezuiDetailsForm = $('#ezuiDetailsForm').form();
            ezuiImportDataForm = $('#ezuiImportDataForm').form();
            ezuiOperateResultDataForm = $('#ezuiOperateResultDataForm').form();
            courierComplaintForm = $('#courierComplaintForm').form();
            ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                url: '<c:url value="/docOrderHeaderController.do?showDatagrid"/>',
                method: 'POST',
                toolbar: '#toolbar',
                title: '订单列表',
                pageSize: 50,
                pageList: [50, 100, 200],
                fit: true,
                border: false,
                fitColumns: false,
                nowrap: false,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: false,
                checkOnSelect: true,
                selectOnCheck: true,
                idField: 'orderno',
                rowStyler: docOrderRowStyler,
                queryParams: {
                    ordertime: $('#ordertime').datetimebox('getValue'),
                    ordertimeTo: $('#ordertimeTo').datetimebox('getValue'),
                    sostatusCheck: $('#sostatusCheck').is(':checked') == true ? "Y" : "N"
                },
                columns: [[
                    {field: 'chk', checkbox: true, width: 6},
                    {field: 'customerid', title: '客户编码', width: 85},
                    {field: 'orderno', title: 'SO编号', width: 120},
                    {field: 'orderTypeName', title: '订单类型', width: 80},
                    {field: 'orderStatusName', title: '订单状态', width: 80},
                    {field: 'soreference1', title: '客户单号', width: 100},
                    {field: 'cAddress4', title: '快递单号', width: 120},
                    {field: 'notes', title: '备注', width: 100},
                    {field: 'lineName', title: '产品线', width: 120},
                    {field: 'edisendflag', title: '回传标识', width: 70, formatter: edisendflag},
                    {field: 'consigneeid', title: '收货单位', width: 200},
                    {field: 'cAddress1', title: '收货地址', width: 250},
                    {field: 'cTel1', title: '联系方式', width: 100},
                    {field: 'addtime', title: '创建时间', width: 145},
                    {field: 'addwho', title: '创建人', width: 70},
                    {field: 'edittime', title: '编辑时间', width: 145},
                    {field: 'editwho', title: '编辑人', width: 70},
                    {field: 'soreference2', title: '定向入库单号', width: 120},
                    {field: 'courierComplaint', title: '快递投诉内容', width: 150},
                    {field: 'udfprintflag2', title: '拣货单打印次数', width: 120, align: 'center'},
                    {field: 'udfprintflag3', title: '随货单打印次数', width: 120, align: 'center'}
                ]],
                onDblClickRow: function (index, row) {
                    edit(row);
                    qlOrderDetails(row);

                },
                onLoadSuccess: function (data) {
                    ajaxBtn($('#menuId').val(), '<c:url value="/docOrderHeaderController.do?getBtn"/>', ezuiMenu);
                    $(this).datagrid('unselectAll');
                    $(this).datagrid('clearChecked');
                    $(this).datagrid('clearSelections');
                }, onClickRow: function (index, row) {

                    row = ezuiDatagrid.datagrid("getSelections");
                    var a = row.length;
                    // alert(a);
                    $('#nummm').html(a);
                }
            });

            /* 订单明细列表*/
            ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
                url: '<c:url value="/docOrderDetailsController.do?showDatagrid"/>',
                method: 'POST',
                toolbar: '#detailToolbar',
                idField: 'orderlineno',
                title: '',
                pageSize: 50,
                pageList: [50, 100, 200],
                border: false,
                fitColumns: false,
                nowrap: false,
                striped: true,
                showFooter: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: true,
                selectOnCheck: false,
                columns: [[
                    {field: 'orderlineno', title: '行号', width: 35},
                    {field: 'linestatus', title: '状态', width: 60, formatter: sostatusFormatter},
                    {field: 'sku', title: '商品编码', width: 80},
                    {field: 'lotatt04', title: '生产批号', width: 80},
                    {field: 'lotatt05', title: '序列号', width: 80},
                    {field: 'lotatt01', title: '生产日期', width: 85},
                    {field: 'lotatt02', title: '效期', width: 85},
                    // {field: 'skuName',			title: '产品名称',		width: 130 },
                    {field: 'qtyorderedEach', title: '订货数量', width: 60},
                    {field: 'qtyordered', title: '订货件数', width: 60},
                    {field: 'qtyallocatedEach', title: '分配数量', width: 60},
                    {field: 'qtyallocated', title: '分配件数', width: 60},
                    {field: 'qtypickedEach', title: '拣货数量', width: 60},
                    {field: 'qtypicked', title: '拣货件数', width: 60},
                    {field: 'qtyshippedEach', title: '发货数量', width: 60},
                    {field: 'qtyshipped', title: '发货件数', width: 60},
                    // {field: 'alternativesku',	title: '商品条码',		width: 130 },
                    // {field: 'lotatt03',			title: '入库日期',		width: 130 },
                    {field: 'lotatt06', title: '产品注册证', width: 80},
                    {field: 'lotatt07', title: '灭菌批号', width: 60},
                    {field: 'lotatt08', title: '供应商', width: 60},
                    {field: 'lotatt09', title: '样品属性', width: 80, formatter: YP_TYPstatusFormatter},
                    {field: 'lotatt10', title: '质量状态', width: 80, formatter: ZL_TYPstatusFormatter},
                    {field: 'lotatt11', title: '存储条件', width: 60},
                    {field: 'descrc', title: '规格型号', width: 80},
                    // {field: 'lotatt13',			title: '双证',			width: 100 },
                    {field: 'lotatt14', title: '入库单号', width: 80},
                    // {field: 'lotatt15',			title: '生产厂商名称',	width: 130 },
                    {field: 'dEdi04', title: '样品/投诉单号', width: 100},
                    {field: 'qty1', title: '换算率', width: 100}
                    /*{field: 'lotatt16',	title: '自定义批属1',		width: 130 },
                {field: 'lotatt17',	title: '自定义批属2',		width: 130 },
                {field: 'lotatt18',	title: '自定义批属3',		width: 130 }*/
                ]],
                onDblClickCell: function (index, field, value) {
                    detailsEdit();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                },
                onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                    $(this).datagrid("resize", {height: 300});
                    $('#ezuiDetailsDatagrid').datagrid('reloadFooter', [
                        {
                            skuName: '总合计:',
                            qtyorderedEach: data.rows[0].qtyorderedEachSum,
                            qtyordered: data.rows[0].qtyorderedSum,
                            qtyallocatedEach: data.rows[0].qtyallocatedEachSum,
                            qtyallocated: data.rows[0].qtyallocatedSum,
                            qtypickedEach: data.rows[0].qtypickedEachSum,
                            qtypicked: data.rows[0].qtypickedSum,
                            qtyshippedEach: data.rows[0].qtyshippedEachSum,
                            qtyshipped: data.rows[0].qtyshippedSum
                        }]);
                }
            });
            /* 分配明细列表*/
            allocationDetailsDatagrid = $('#allocationDetailsDatagrid').datagrid({
                url: '<c:url value="/docOrderHeaderController.do?showAllocation"/>',
                method: 'POST',
                toolbar: '',
                idField: 'orderlineno',
                title: '',
                pageSize: 50,
                pageList: [50, 100, 200],
                border: false,
                fitColumns: true,
                nowrap: false,
                striped: true,
                showFooter: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: true,
                selectOnCheck: false,
                columns: [[
                    {field: 'location', title: '库位', width: 150},
                    {field: 'sku', title: '商品编码', width: 150},
                    {field: 'skuName', title: '商品名称', width: 150},
                    {field: 'qty', title: '分配数', width: 100},
                    {field: 'qtyEach', title: '分配件数', width: 100},
                    {field: 'qtypickedEach', title: '拣货数', width: 100},
                    {field: 'qtyshippedEach', title: '发货数', width: 100},
                    {field: 'status', title: '状态', width: 150, formatter: sostatusFormatter},
                    {field: 'pickName', title: '包装', width: 100}
                ]],
                onDblClickCell: function (index, field, value) {
                    detailsEdit();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                },
                onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                    $(this).datagrid("resize", {height: 300});
                    $('#allocationDetailsDatagrid').datagrid('reloadFooter', [
                        {
                            skuName: '总合计:',
                            qty: data.rows[0].qtysum,
                            qtyEach: data.rows[0].qtyEachsum,
                            qtypickedEach: data.rows[0].qtypickedEachsum,
                            qtyshippedEach: data.rows[0].qtyshippedEachsum
                        }]);
                }
            });


            // 统计总计 end
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

            $('#ordertime').datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });

            $('#ordertimeTo').datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            /* 产品编码限定大写字母 */
            $("#ezuiSkuDataDialog #sku").textbox('textbox').css('text-transform', 'uppercase');
            $("#ezuiDetailsForm #sku").textbox('textbox').css('text-transform', 'uppercase');

            //订单信息弹框
            ezuiDialog = $('#ezuiDialog').dialog({
                modal: true,
                left: 0,
                top: 0,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiForm);
                },
                onOpen: function () {
                }
            }).dialog('close');

            //订单明细弹框
            ezuiDetailsDialog = $('#ezuiDetailsDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDetailsDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiDetailsForm);
                }
            }).dialog('close');

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

            //收货单位选择弹框
            ezuiReceDataDialog = $('#ezuiReceDataDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '',
                onOpen: function () {

                },
                onClose: function () {

                }
            }).dialog('close');
            ezuiCustomerDataDialog = $('#ezuiCustomerDataDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '',
                onOpen: function () {

                },
                onClose: function () {

                }
            }).dialog('close');
            //商品选择弹框
            ezuiSkuDataDialog = $('#ezuiSkuDataDialog').dialog({
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

            //导入
            ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
                modal: true,
                title: '导入',
                buttons: '#ezuiImportDataDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiImportDataForm);
                }
            }).dialog('close');
            //导出
            ezuiOutToExcelDataDialog = $('#ezuiOutToExcelDataDialog').dialog({
                modal: true,
                width: 270,
                height: 150,
                title: '选择导出内容',
                buttons: '#ezuiOutToExcel1Btn',
                onClose: function () {
                    ezuiFormClear(ezuiOutToExcel1Form);
                }
            }).dialog('close');
            //拣货单
            ezuiPackDataDialog = $('#ezuiPackDataDialog').dialog({
                modal: true,
                width: 270,
                height: 150,
                title: '选择导出内容',
                buttons: '#ezuiPackABtn',
                onClose: function () {
                    ezuiFormClear(ezuiPackForm);
                }
            }).dialog('close');
            //批量操作返回结果
            ezuiOperateResultDataDialog = $('#ezuiOperateResultDataDialog').dialog({
                modal: true,
                title: '批量操作',
                buttons: '#ezuiOperateResultDataDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiOperateResultDataForm);
                }
            }).dialog('close');
            /* 控件初始化end */
            //单选框改变事件
            $("#sostatusCheck").change(function () {
                doSearch();
            });


            var customerid1;
            //产品线1
            $("#toolbar #customerid").textbox({
                onChange: function () {
                    customerid1 = $("#toolbar #customerid").textbox('getValue');
                    console.log(customerid1);
                    if (customerid1 != null && ($.trim(customerid1).length > 0)) {
                        $("#toolbar #productLineOrder").combobox({
                            panelHeight: 'auto',
                            url: '/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId=' + customerid1,
                            valueField: 'id',
                            textField: 'value',
                            onLoadSuccess: function () {
                            }
                        });
                    } else {
                        $("#toolbar #productLineOrder").combobox({
                            panelHeight: 'auto',
                            url: "/productLineController.do?getCombobox",
                            valueField: 'id',
                            textField: 'value',
                            onLoadSuccess: function () {
                            }
                        });
                    }
                }
            });

//快递投诉dialog
            courierComplaintDialog = $('#courierComplaintDialog').dialog({
                modal: true,
                width: 270,
                height: 250,
                title: '录入投诉内容',
                buttons: '#courierComplaintDialogBtn',
                onClose: function () {
                    ezuiFormClear(courierComplaintForm);
                }
            }).dialog('close');

            returnSfodd = $('#returnSfodd').dialog({
                modal: true,
                width: 200,
                height: 130,
                title: '订单下单是否需要签回单号',
                buttons: '#returnSfoddDialogBtn',
                onClose: function () {
                    ezuiFormClear(returnSfoddForm);
                }
            }).dialog('close');

            writeBackExpressDiv = $('#writeBackExpressDiv').dialog({
                modal: true,
                width: 270,
                height: 210,
                title: '回写快递单号/签回单号',
                buttons: '#writeBackExpressBtn',
                onClose: function () {
                    ezuiFormClear(writeBackExpressForm);
                }
            }).dialog('close');

            $("input").each(function () {
                // alert($(this));

                $(this).click(function () {
                    jishu();
                    // 全选中
                });
            })
            $('div').on('click', 'tbody>tr', function () {
                jishu();

            })
        });

        function jishu() {
            row = ezuiDatagrid.datagrid("getSelections");
            var a = row.length;
            // alert(a);
            $('#nummm').html(a);
        }

        /* 查询条件清空按钮 */
        var ezuiToolbarClear = function () {
            $("#orderno").textbox('clear');
            $("#customerid").textbox('clear');
            $("#soreference1").textbox('clear');
            $("#sostatus").combobox('clear');
            $("#sostatusTo").combobox('clear');
            $("#ordertype").combobox('clear');
            $("#releasestatus").combobox('clear');
            $("#edisendflag").combobox('clear');
            $('#soreference2').textbox('clear');
            $("#cAddress1").textbox('clear');
            $("#consigneeid").textbox('clear');
            $("#cAddress4Q").textbox('clear');
            $("#cAddress4").textbox('clear');
            $("#carrierContact").textbox('clear');
            $("#ordertime").datetimebox('clear');
            $("#ordertimeTo").datetimebox('clear');
            $("#edittime").datetimebox('clear');
            $("#edittimeTo").datetimebox('clear');
            $("#toolbar #productLineOrder").combobox('clear');  //清空option选项
        };

        /* 获取起始日期 */
        var ordertimeDate = function (date) {
            var day = date.getDate() - 7 > 9 ? date.getDate() - 7 : "0" + date.getDate() - 7;
            var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
            return date.getFullYear() + '-' + month + '-' + day + ' 00:00';
        };
        /* 获取结束日期 */
        var ordertimeDateTo = function (date) {
            var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
            var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
            return date.getFullYear() + '-' + month + '-' + day + ' 23:59';
        };

        /* 新增按钮 */
        var add = function () {
            processType = 'add';
            $('#docOrderHeaderId').val(0);
            $("#ezuiForm #customerid").textbox({
                editable: false,
                icons: [{
                    iconCls: 'icon-search',
                    handler: function (e) {
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataDialogClick();
                        ezuiCustDataDialogSearch();
                    }
                }]
            });

            $("#ezuiForm #consigneeid").textbox({
                editable: true,
                icons: [{
                    iconCls: 'icon-search',
                    handler: function (e) {
                        $("#ezuiReceDataDialog #consigneeid").textbox('clear');
                        ezuiReceDataDialogClick();
                        ezuiReceDataDialogSearch();
                    }
                }]
            });

            $('#ezuiForm #soreference1').textbox({editable: true});
            $('#ezuiForm #soreference2').textbox({editable: true});
            $('#ezuiForm #consigneename').textbox({editable: true});
            $('#ezuiForm #cTel1').textbox({editable: true});
            $('#ezuiForm #cAddress1').textbox({editable: true});
            $('#ezuiForm #notes').textbox({editable: true});
            $("#ezuiForm #sostatus").combobox('setValue', '00').combo('readonly', true);
            $("#ezuiForm #releasestatus").combobox('setValue', 'Y').combo('readonly', false);
            $("#ezuiForm #ordertype").combobox('setValue', 'SO').combo('readonly', false);
            $("#ezuiForm #ezuiBtn_orderCommit").linkbutton('enable');
            ezuiDetailsDatagrid.datagrid('load', {orderno: '-1'});
            ezuiDialog.dialog('open');
        };

        /*  查询是否有明细 */
        var qlOrderDetails = function (row) {
            $.ajax({
                url: 'docOrderHeaderController.do?qlOrderDetails',
                data: {orderno: row.orderno},
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {
                    console.log(result)
                    try {
                        if (result.success) {
                            $('#ezuiBtn_copyDetailGo').linkbutton('disable');
                        } else {
                            $('#ezuiBtn_copyDetailGo').linkbutton('enable');
                        }
                    } catch (e) {
                        return;
                    }
                    ;
                }
            });
        }

        /* 编辑按钮 */
        var edit = function (srow) {
            processType = 'edit';
            $('#docOrderHeaderId').val(0);
            var row = srow || ezuiDatagrid.datagrid('getSelected');
            console.log(srow);
            if (row) {
                ezuiForm.form('load', {
                    customerid: row.customerid,
                    orderno: row.orderno,
                    soreference1: row.soreference1,
                    soreference2: row.soreference2,
                    sostatus: row.sostatus,
                    consigneename: row.consigneename,
                    cAddress1: row.cAddress1,
                    cTel1: row.cTel1,
                    releasestatus: row.releasestatus,
                    ordertype: row.ordertype,
                    ordertime: row.ordertime,
                    addwho: row.addwho,
                    edittime: row.edittime,
                    editwho: row.editwho,
                    lastshipmenttime: row.lastshipmenttime,
                    notes: row.notes,
                    carrierid: row.carrierid,
                    userdefine1: row.userdefine1,
                    userdefine2: row.userdefine2,
                    userdefine3: row.userdefine3,
                    consigneeid: row.consigneeid,
                    cProvince: row.cProvince,
                    cCity: row.cCity,
                    cAddress2: row.cAddress2,
                    cContact: row.cContact,
                    stop: row.stop,
                    door: row.door,
                    // coldTag:row.door,
                    route: row.route,
                    // carrierid:row.carrierid,


                });
                if (row.sostatus == '90' || row.sostatus == '99') {
                    $("#ezuiForm #customerid").textbox({
                        editable: false,
                        icons: []
                    });
                    $("#ezuiForm #sostatus").combo('readonly', true);
                    $('#ezuiForm #soreference1').textbox({editable: false});
                    $('#ezuiForm #soreference2').textbox({editable: false});
                    $('#ezuiForm #consigneename').textbox({editable: false});
                    $('#ezuiForm #cTel1').textbox({editable: false});
                    $('#ezuiForm #cAddress1').textbox({editable: false});
                    $('#ezuiForm #notes').textbox({editable: false});
                    $("#ezuiForm #sostatus").combo('readonly', true);
                    $("#ezuiForm #releasestatus").combo('readonly', true);
                    $("#ezuiForm #ordertype").combo('readonly', true);
                    $("#ezuiForm #ezuiBtn_orderCommit").linkbutton('disable');
                } else {
                    if (row.addwho == 'EDI') {
                        $('#ezuiForm #soreference1').textbox({editable: false});
                        $('#ezuiForm #soreference2').textbox({editable: false});
                    } else {
                        $('#ezuiForm #soreference1').textbox({editable: true});
                        $('#ezuiForm #soreference2').textbox({editable: true});
                    }
                    $("#ezuiForm #customerid").textbox({
                        editable: false,
                        icons: []
                    });
                    $("#ezuiForm #sostatus").combo('readonly', true);
                    $('#ezuiForm #consigneename').textbox({editable: true});
                    $('#ezuiForm #cTel1').textbox({editable: true});
                    $('#ezuiForm #cAddress1').textbox({editable: true});
                    $('#ezuiForm #notes').textbox({editable: true});
                    $("#ezuiForm #sostatus").combo('readonly', true);
                    $("#ezuiForm #releasestatus").combo('readonly', false);
                    $("#ezuiForm #ordertype").combo('readonly', false);
                    $("#ezuiForm #ezuiBtn_orderCommit").linkbutton('enable');
                }
                $("#ezuiForm #consigneeid").textbox({
                    editable: true,
                    icons: [{
                        iconCls: 'icon-search',
                        handler: function (e) {
                            $("#ezuiReceDataDialog #consigneeid").textbox('clear');
                            ezuiReceDataDialogClick();
                            ezuiReceDataDialogSearch();
                        }
                    }]
                });
                ezuiDetailsDatagrid.datagrid('load', {orderno: row.orderno});
                allocationDetailsDatagrid.datagrid('load', {ordero: row.orderno});
                $('#ezuiDetailsDatagrid').parent().parent().parent().show();
                // $('#coldTag').filebox("setValue",row.door);
                ezuiDialog.dialog('open');
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
            ;
        };

        var clearDatagridSelection = function () {

            ezuiDatagrid.datagrid('clearSelections');
            jishu();
        }

        /* 删除按钮 */
        var del = function () {

            var operateResult = '';
            var checkedItems = ezuiDatagrid.datagrid('getSelections');
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function (confirm) {
                if (confirm) {

                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var ordernos = '';
                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });
                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?delete',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：删除订单"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }

                        }
                    });
                }
            });
        };

        var chooseExpressType = function () {

            returnSfodd = $('#returnSfodd').dialog({
                modal: true,
                width: 200,
                height: 130,
                title: '是否需要签回单号',
                buttons: [{
                    id: 'returnSfoddBtn',
                    text: '提交',
                    handler: function () {
                        returnSfodd.dialog('close');
                        allocation();
                    }
                }],
                onClose: function () {
                    ezuiFormClear(returnSfoddForm);
                }
            }).dialog('open');
        };


        /* 分配按钮 */
        var allocation = function () {

            $.messager.progress({
                text: '<spring:message code="common.message.data.processing"/>', interval: 100
            });

            var returnSfOrder = $('#returnSfoddid').combobox('getValue');
            var operateResult = '';
            var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
            var ordernos = '';
            $.each(checkedItems, function (index, item) {
                ordernos += item.orderno + ',';
            });

            $.ajax({
                async: true,
                url: 'docOrderHeaderController.do?allocation',
                data: {orderno: ordernos, returnSfOrder: returnSfOrder},
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {

                    $.messager.progress('close');
                    try {
                        operateResult = result.msg;
                        clearDatagridSelection();
                    } catch (e) {
                        operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                    } finally {
                        $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                        $('#ezuiOperateResultDataDialog').panel({title: "批量操作：分配订单"});
                        ezuiOperateResultDataDialog.dialog('open');
                        ezuiDatagrid.datagrid('reload');
                    }
                }
            });
        };

        /* 取消分配按钮 */
        var deAllocation = function () {

            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否继续进行取消分配操作', function (confirm) {
                if (confirm) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var operateResult = '';
                    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
                    var ordernos = '';

                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });

                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?deAllocation',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：取消分配"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        };

        /*确认拣货*/
        var picked = function () {
          $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否继续进行确认拣货操作', function (confirm) {
           if (confirm) {
            $.messager.progress({
                text: '<spring:message code="common.message.data.processing"/>', interval: 100
            });
            var operateResult = '';
            var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
            var ordernos = '';
            $.each(checkedItems, function (index, item) {
                ordernos += item.orderno + ',';
            });

            $.ajax({
                async: true,
                url: 'docOrderHeaderController.do?picked',
                data: {ordernos: ordernos},
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {

                    $.messager.progress('close');
                    try {
                        operateResult = result.msg;
                        clearDatagridSelection();
                    } catch (e) {
                        operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                    } finally {
                        $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                        $('#ezuiOperateResultDataDialog').panel({title: "批量操作：确认拣货"});
                        ezuiOperateResultDataDialog.dialog('open');
                        ezuiDatagrid.datagrid('reload');
                    }
                }
            });
           }
          });
        };
        /* 取消拣货按钮 */
        var cancelPicked = function () {

            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否继续进行取消拣货操作', function (confirm) {
                if (confirm) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var operateResult = '';
                    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
                    var ordernos = '';

                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });

                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?cancelPicked',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：取消拣货"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        };

        /* 复核按钮 */
        var packing = function () {

            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否继续进行复核操作', function (confirm) {
                if (confirm) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var operateResult = '';
                    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
                    var ordernos = '';

                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });

                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?packing',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：复核"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        };

        /* 取消复核按钮 */
        var unPacking = function () {

            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否继续进行取消复核操作', function (confirm) {
                if (confirm) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var operateResult = '';
                    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
                    var ordernos = '';

                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });

                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?unPacking',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：取消复核"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        };

        /* 订单发货按钮 */
        var shipment = function () {

            $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否继续进行订单发运操作', function (confirm) {
                if (confirm) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var operateResult = '';
                    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
                    var ordernos = '';

                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });

                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?shipment',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：订单发运"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        };

        /* 订单作废按钮 */
        var cancel = function () {
            $.messager.confirm('提示', '确认作废勾选订单?', function (r) {
                if (r) {
                    $.messager.progress({
                        text: '<spring:message code="common.message.data.processing"/>', interval: 100
                    });

                    var operateResult = '';
                    var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
                    var ordernos = '';

                    $.each(checkedItems, function (index, item) {
                        ordernos += item.orderno + ',';
                    });

                    $.ajax({
                        async: true,
                        url: 'docOrderHeaderController.do?cancel',
                        data: {ordernos: ordernos},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                            $.messager.progress('close');
                            try {
                                operateResult = result.msg;
                                clearDatagridSelection();
                            } catch (e) {
                                operateResult = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $('#ezuiOperateResultDataForm #operateResult').textbox('setValue', operateResult);
                                $('#ezuiOperateResultDataDialog').panel({title: "批量操作：订单作废"});
                                ezuiOperateResultDataDialog.dialog('open');
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        };

        /* 批量打印拣货单按钮 */
        var printPacking = function () {
            var rows = $('#ezuiDatagrid').datagrid('getSelections');
            var orderno = "";
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    orderno += rows[i].orderno + ",";
                }
                /*ajaxDownloadFile(sy.bp()+"/docPaHeaderController.do?exportBatchPdf&pano="+pano);*/
                window.open(sy.bp() + "/docOrderHeaderController.do?exportPackingPdf&orderno=" + orderno);
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
        /* 打印随货清单 */
        var printAccompanying = function () {
            var rows = $('#ezuiDatagrid').datagrid('getSelections');
            var orderno = "";
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    orderno += rows[i].orderno + ",";
                    if (i > 0) {
                        if (rows[0].customerid != rows[i].customerid) {
                            showMsg("同时只支持同一客户编码(类型)的随货单打印......");
                            return;
                        }
                    }
                }
                /*ajaxDownloadFile(sy.bp()+"/docPaHeaderController.do?exportBatchPdf&pano="+pano);*/
                window.open(sy.bp() + "/docOrderHeaderController.do?exportAccompanyingPdf&orderno=" + orderno);
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }

        }

        var printExpress = function () {
            orderList = null;
            var falgNo = 1;
            var falgTwo = 1;
            var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
            console.log(checkedItems)
            for (var i = 0; i < checkedItems.length; i++) {
                if (orderList == null) {
                    orderList = checkedItems[i].orderno
                } else {
                    orderList = orderList + ',' + checkedItems[i].orderno;

                }
                if (checkedItems[i].carriercountry == "1") {
                    return showMsg("选中的订单存在已回写-快递单号-无法打印快递单.......");
                }
                if (checkedItems[i].cAddress4 == null) {
                    return showMsg("选中的订单未在系统内下单，无法打印快递单.......");
                }
                if (i > 0) {
                    if (checkedItems[0].cAddress3 != null) {
                        falgNo = 1;
                    } else {
                        falgNo = 0;
                    }
                    if (checkedItems[i].cAddress3 != null) {
                        falgTwo = 1;
                    } else {
                        falgTwo = 0;
                    }
                    if (falgNo != falgTwo) {
                        return showMsg("打印电子面单请区分-有无签回单-进行连打确认!");
                    }
                }
            }
            if (orderList == null) {
                return;
            }
            window.open(sy.bp() + "/docOrderHeaderController.do?printExpress&orderCodeList=" + orderList);
        }

        /* 提交按钮 */
        var commit = function () {
            var url = '';
            if (processType == 'edit') {
                url = '<c:url value="/docOrderHeaderController.do?edit"/>';
            } else {
                url = '<c:url value="/docOrderHeaderController.do?add"/>';
            }
            ezuiForm.form('submit', {
                url: url,
                onSubmit: function () {
                    if ($("#ezuiForm #ordertype").combobox("getValue") == "DX" && $("#ezuiForm #soreference2").textbox("getValue") == "") {
                        showMsg("定向订单需要填写定向入库单号");
                        return false;
                    }
                    if (ezuiForm.form('validate')) {
                        $.messager.progress({
                            text: '<spring:message code="common.message.data.processing"/>', interval: 100
                        });
                        return true;
                    } else {
                        return false;
                    }
                    ;
                },
                success: function (data) {
                    var msg = '';
                    try {
                        var result = $.parseJSON(data);
                        if (result.success) {
                            if (processType == 'edit') {
                                msg = result.msg;
                                ezuiDatagrid.datagrid('reload');
                            } else {
                                msg = result.msg;
                                $("#ezuiForm #customerid").textbox({
                                    editable: false,
                                    icons: []
                                });
                                $('#ezuiForm #orderno').textbox('setValue', result.obj.orderno);
                                ezuiDatagrid.datagrid('reload');
                                $('#ezuiBtn_copyDetailGo').linkbutton('enable');
                                ezuiDetailsDatagrid.datagrid('load', {orderno: result.obj.orderno});
                            }
                            ;
                        } else {
                            msg = '<font color="red">' + result.msg + '</font>';
                        }
                        ;
                    } catch (e) {
                        msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                        msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
                    } finally {
                        $.messager.show({
                            msg: msg, title: '<spring:message code="common.message.prompt"/>'
                        });
                        $.messager.progress('close');
                    }
                    ;
                }
            });
        };

        /* 查询按钮 */
        var doSearch = function () {
            ezuiDatagrid.datagrid('unselectAll');
            ezuiDatagrid.datagrid('load', {
                orderno: $('#orderno').val(),
                customerid: $('#customerid').val(),
                soreference1: $('#soreference1').val(),
                soreference2: $('#soreference2').val(),
                //收货单位
                consigneeid: $('#consigneeid').val(),
                productLineId: $("#toolbar #productLineOrder").combobox('getValue'),
                edisendflag: $("#toolbar #edisendflag").combobox('getValue'), //回传标识
                //收货电话
                //  cTel1 : $('#cTel1').val(),
                //订单状态
                cAddress1: $('#cAddress1').val(),
                sostatus: $('#sostatus').combobox('getValue'),
                sostatusTo: $('#sostatusTo').combobox('getValue'),
                ordertime: $('#ordertime').datetimebox('getValue'),
                ordertimeTo: $('#ordertimeTo').datetimebox('getValue'),
                edittime: $('#edittime').datetimebox('getValue'),
                edittimeTo: $('#edittimeTo').datetimebox('getValue'),
                ordertype: $('#ordertype').combobox('getValue'),
                releasestatus: $('#releasestatus').combobox('getValue'),
                sostatusCheck: $('#sostatusCheck').is(':checked') == true ? "Y" : "N",
                cAddress4: $('#cAddress4Q').val(),


                carriercontact: $('#carrierContact').val(),
                /*cProvince : $("#cc1").textbox("getValue"),
        cCity : $("#cc2").textbox("getValue"),
        cAddress2 : $("#cc3").textbox("getValue")*/
            });


        };

        /* 导出start */
        var doExport = function () {
            var rows = $('#ezuiDatagrid').datagrid('getSelections');
            var outtype = $("#ezuiOutToExcel1Form #outtype").combobox('getValue');

            if(outtype == 1){
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("orderno",$('#orderno').val());
                param.put("customerid",$('#customerid').val());
                param.put("soreference1",$('#soreference1').val());
                param.put("soreference2",$('#soreference2').val());
                param.put("consigneeid",$('#consigneeid').val());
                param.put("cAddress1",$('#cAddress1').val());
                param.put("cAddress4",$('#cAddress4Q').val());
                param.put("sostatusCheck",$('#sostatusCheck').is(':checked') == true ? "Y" : "N");
                param.put("carrierContact",$('#carrierContact').val());
                param.put("releasestatus",$('#releasestatus').combobox('getValue'));
                param.put("sostatusTo",$('#sostatusTo').combobox('getValue'));
                param.put("ordertime",$('#ordertime').datebox('getValue'));//时间查询
                param.put("ordertimeTo",$('#ordertimeTo').datebox('getValue'));//入库单号时间查询
                param.put("edittime",$('#edittime').datebox('getValue'));//入库单号时间查询
                param.put("edittimeTo",$('#edittimeTo').combobox('getValue'));//质量状态
                param.put("productLineId",$("#toolbar #productLineOrder").combobox('getValue'));
                param.put("edisendflag",$("#toolbar #edisendflag").combobox('getValue'));
                param.put("sostatus",$('#sostatus').combobox('getValue'));
                param.put("ordertype",$('#ordertype').combobox('getValue'));
                param.put("outtype",outtype);
                param.put("token", token);
                ajaxDownloadFile(sy.bp()+ "/docOrderHeaderController.do?exportOrderNoToExcel1",param);
                ezuiOutToExcelDataDialog.dialog('close');
            }else{
                if(rows.length>0) {
                    if(rows.length>1){
                        $.messager.show({
                            msg : '导出明细仅支持单条出库单导出！', title : '<spring:message code="common.message.prompt"/>'
                        });
                    }else{
                        var orderno=rows[0].orderno;
                        var token = new Date().getTime();
                        var param = new HashMap();
                        param.put("orderno",orderno);
                        param.put("outtype",outtype);
                        param.put("token", token);
                        ajaxDownloadFile(sy.bp()+ "/docOrderHeaderController.do?exportOrderNoToExcel1",param);
                        ezuiOutToExcelDataDialog.dialog('close');
                    }

                }else{
                    $.messager.show({
                        msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
                    });
                }
            }
        };
        /* 导出end */
        /* 拣货start */
        var doPack = function () {
            var rows = $('#ezuiDatagrid').datagrid('getSelections');
            var outtype = $("#ezuiPackForm #pack").combobox('getValue');
            var orderno = "";
            if (rows.length > 0) {
                if(outtype == 1){
                    for (var i = 0; i < rows.length; i++) {
                        orderno += rows[i].orderno + ",";
                    }
                    /*ajaxDownloadFile(sy.bp()+"/docPaHeaderController.do?exportBatchPdf&pano="+pano);*/
                    window.open(sy.bp() + "/docOrderHeaderController.do?exportPackingPdfNewReport&orderno=" + orderno);
                }else{
                    printPacking();
                }
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
                ezuiPackDataDialog.dialog('close');
            }
        };
        /* 拣货end */
        /* 导出随货start */
        var doExportGoodsList = function () {
            var rows = $('#ezuiDatagrid').datagrid('getSelections');
            var orderno = "";
            if (rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    orderno += rows[i].orderno + ",";
                    if (i > 0) {
                        if (rows[0].customerid != rows[i].customerid) {
                            showMsg("同时只支持同一客户编码(类型)的随货单导出......");
                            return;
                        }
                    }
                }
                if (navigator.cookieEnabled) {
                    $('#ezuiBtn_export_goods_list').linkbutton('disable');
                    //--导出Excel
                    // window.open(sy.bp() + "/docOrderHeaderController.do?exportOrderNoToExcel&orderno="+order);
                    var token = new Date().getTime();
                    var param = new HashMap();
                    param.put("token", token);
                    var formId = ajaxDownloadFile(sy.bp() + "/docOrderHeaderController.do?exportOrderNoToExcel&orderno=" + orderno);
                    downloadCheckTimer = window.setInterval(function () {
                        window.clearInterval(downloadCheckTimer);
                        // $('#' + formId).remove();
                        $('#ezuiBtn_export_goods_list').linkbutton('enable');
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
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }

        }
        /* 导出随货end */

        /* 导入start */
        var commitImportData = function (obj) {
            ezuiImportDataForm.form('submit', {
                url: '<c:url value="/docOrderHeaderController.do?importExcelData"/>',
                onSubmit: function () {
                    if (ezuiImportDataForm.form('validate')) {
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
                            msg = result.msg.replace(/ /g, '\n');
                            ezuiDatagrid.datagrid('reload');
                        } else {
                            msg = result.msg.replace(/ /g, '\n');
                        }
                    } catch (e) {
                        msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                        msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
                    } finally {
                        ezuiFormClear(ezuiImportDataForm);
                        $('#importResult').textbox('setValue', msg);
                        $.messager.progress('close');
                    }
                }
            });
        };
        var toImportData = function () {
            ezuiImportDataDialog.dialog('open');
        };
        var toOutData = function () {
            $('#outtype').combobox('select', '1');
            ezuiOutToExcelDataDialog.dialog('open');
        };
        var toPackData = function () {
            $('#pack').combobox('select', '1');
            ezuiPackDataDialog.dialog('open');
        };
        /* 下载导入模板 */
        var downloadTemplate = function () {
            if (navigator.cookieEnabled) {
                $('#ezuiBtn_downloadTemplate').linkbutton('disable');
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("token", token);
                var formId = ajaxDownloadFile(sy.bp() + "/docOrderHeaderController.do?exportTemplate", param);
                downloadCheckTimer = window.setInterval(function () {
                    var list = new cookieList('downloadToken');
                    if (list.items() == token) {
                        window.clearInterval(downloadCheckTimer);
                        list.clear();
                        $('#' + formId).remove();
                        $('#ezuiBtn_downloadTemplate').linkbutton('enable');
                        $.messager.show({
                            msg: "<spring:message code='common.message.export.success'/>",
                            title: "<spring:message code='common.message.prompt'/>"
                        });
                    }
                    ;
                }, 1000);
            } else {
                $.messager.show({
                    msg: "<spring:message code='common.navigator.cookieEnabled.false'/>",
                    title: "<spring:message code='common.message.prompt'/>"
                });
            }
            ;
        };
        /* 导入end */


        /*导出序列号记录 start*/
        var doExportOrderNo = function () {
            if (navigator.cookieEnabled) {
                var rowp = $('#ezuiDatagrid').datagrid('getSelected');
                var rowlength = $('#ezuiDatagrid').datagrid('getChecked');
                if (rowlength.length == 1) {
                    var orderFlag = new Date().getTime();
                    var setting = new HashMap();
                    setting.put("orderFlag", orderFlag);
                    setting.put("orderno", rowp.orderno);
                    //判断orderno有无
                    $.ajax({
                        url: 'docOrderHeaderController.do?isexportOrderNo',
                        data: {orderno: rowp.orderno},
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {
                            var msg = '';
                            try {
                                if (!result.success) {
                                    msg = result.msg;
                                    $.messager.show({
                                        msg: msg, title: '<spring:message code="common.message.prompt"/>'
                                    });

                                } else {
                                    //--导出Excel
                                    var formIdb = ajaxDownloadFile(sy.bp() + "/docOrderHeaderController.do?exportbasSerialNumToExcel", setting);
                                    downloadCheckTimer = window.setInterval(function () {
                                        window.clearInterval(downloadCheckTimer);
                                        $('#' + formIdb).remove();
                                        $.messager.progress('close');
                                        $.messager.show({
                                            msg: "<spring:message code='common.message.export.success'/>",
                                            title: "<spring:message code='common.message.prompt'/>"
                                        });
                                    }, 1000);

                                }
                            } catch (e) {
                                $.messager.show({
                                    msg: "数据出错!", title: "<spring:message code='common.message.prompt'/>"
                                });
                            }
                        }
                    });

                } else {
                    $.messager.show({
                        msg: "<spring:message code='common.message.selectRecord'/>",
                        title: "<spring:message code='common.message.prompt'/>"
                    });
                }
            } else {
                $.messager.show({
                    msg: "<spring:message code='common.navigator.cookieEnabled.false'/>",
                    title: "<spring:message code='common.message.prompt'/>"
                });
            }
            ;
        }
        /*导出序列号记录 end*/

        /* 明细新增按钮 */
        var detailsAdd = function () {
            if ($('#ezuiForm #orderno').val() == '') {
                return;
            } else {
                if ($('#ezuiForm #sostatus').combobox('getValue') > '20') {
                    $.messager.alert('提示', '此状态下不能新增明细!', 'info');
                    return;
                } else if ($('#ezuiForm #addwho').val() == 'EDI') {
                    $.messager.alert('提示', 'EDI订单,不能新增明细!', 'info');
                    return;
                } else {
                    processType = 'add';
                    $('#docOrderDetailsId').val(0);
                    $("#ezuiDetailsForm #sku").textbox({
                        editable: false,
                        icons: [{
                            iconCls: 'icon-search',
                            handler: function (e) {
                                $("#ezuiSkuDataDialog #sku").textbox('clear');
                                ezuiSkuDataClick();
                                ezuiSkuDataSearch();
                            }
                        }]
                    });
                    $('#ezuiDetailsForm #customerid').textbox('setValue', $('#ezuiForm #customerid').val());
                    $('#ezuiDetailsForm #orderno').textbox('setValue', $('#ezuiForm #orderno').val());
                    $("#ezuiDetailsForm #orderlineno").numberbox('setValue', 0);
                    $("#ezuiDetailsForm #linestatus").combobox('setValue', '00').combo('readonly', true);
                    $("#ezuiDetailsForm #qtyallocated").numberbox('setValue', 0);
                    $("#ezuiDetailsForm #qtypicked").numberbox('setValue', 0);
                    $("#ezuiDetailsForm #qtyshipped").numberbox('setValue', 0);
                    // $('#ezuiDetailsForm #qtyordered').numberbox({editable:true});
                    // $('#ezuiDetailsForm #qtyorderedEach').numberbox({editable:true});
                    $('#ezuiDetailsForm #grossweight').numberbox({editable: true});
                    $('#ezuiDetailsForm #cubic').numberbox({editable: true});
                    $('#ezuiDetailsForm #price').numberbox({editable: true});
                    $('#ezuiDetailsForm #pickzone').combo('readonly', false);
                    $("#ezuiDetailsForm #location").textbox({
                        editable: false,
                        icons: [{
                            iconCls: 'icon-search',
                            handler: function (e) {
                                $("#ezuiLocDataDialog #locationid").textbox('clear');
                                ezuiLocDataClick();
                                ezuiLocDataSearch();
                            }
                        }]
                    });
                    $('#ezuiDetailsForm #lotnum').textbox({editable: true});
                    $('#ezuiBtn_detailsCommit').linkbutton('enable');
                    /* 获取产品信息 */
                    $("input", $("#ezuiDetailsForm #qtyordered").next("span")).blur(function () {
                        if ($("#ezuiDetailsForm #qtyordered").val() != '') {
                            if ($("#ezuiDetailsForm #sku").val() == '') {
                                $("#ezuiDetailsForm #qtyordered").numberbox('clear');
                            } else {
                                $.ajax({
                                    url: 'basSkuController.do?getSkuInfo',
                                    data: {
                                        customerid: $("#ezuiDetailsForm #customerid").val(),
                                        sku: $("#ezuiDetailsForm #sku").val(),
                                        qty: $("#ezuiDetailsForm #qtyordered").numberbox('getValue')
                                    },
                                    type: 'POST',
                                    dataType: 'JSON',
                                    success: function (result) {
                                        try {
                                            $("#ezuiDetailsForm #grossweight").numberbox('setValue', result.grossweight);
                                            $("#ezuiDetailsForm #cubic").numberbox('setValue', result.cube);
                                            $("#ezuiDetailsForm #price").numberbox('setValue', result.price);
                                        } catch (e) {
                                            return;
                                        }
                                        ;
                                    }
                                });
                            }
                            ;
                        }
                        ;
                    });

                    choseOrderTypeAdd($("#ezuiForm #ordertype").combobox("getValue"));
                    ezuiDetailsDialog.dialog('open');
                }
                ;
            }
            ;
        };

        /* 明细编辑按钮 */
        var detailsEdit = function () {
            if ($('#ezuiForm #orderno').val() == '') {
                return;
            } else {
                processType = 'edit';
                $('#docOrderDetailsId').val(0);
                $("#ezuiDetailsForm #location").textbox({
                    editable: false,
                    icons: [{
                        iconCls: 'icon-search',
                        handler: function (e) {
                            $("#ezuiLocDataDialog #locationid").textbox('clear');
                            ezuiLocDataClick();
                            ezuiLocDataSearch();
                        }
                    }]
                });
                $("#ezuiDetailsForm #linestatus").combo('readonly', true);
                var row = ezuiDetailsDatagrid.datagrid('getSelected');
                if (row) {
                    $("#ezuiDetailsForm #qty1").val(row.qty1);
                    ezuiDetailsForm.form('load', {
                        customerid: row.customerid,
                        orderno: row.orderno,
                        orderlineno: row.orderlineno,
                        linestatus: row.linestatus,
                        sku: row.sku,
                        skuName: row.skuName,
                        skuNameE: row.descrc,
                        alternativesku: row.alternativesku,
                        packid: row.packid,
                        qtyordered: row.qtyordered,
                        qtyorderedEach: row.qtyorderedEach,
                        qtyallocated: row.qtyallocated,
                        qtypicked: row.qtypicked,
                        qtyshipped: row.qtyshipped,
                        cubic: row.cubic,
                        grossweight: row.grossweight,
                        price: row.price,
                        pickzone: row.pickzone,
                        location: row.location,
                        lotnum: row.lotnum,
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
                        supplierIdChose: row.lotatt08


                    });
                    if (row.linestatus == '00') {
                        $("#ezuiDetailsForm #sku").textbox({
                            editable: false,
                            icons: [{
                                iconCls: 'icon-search',
                                handler: function (e) {
                                    $("#ezuiSkuDataDialog #sku").textbox('clear');
                                    ezuiSkuDataClick();
                                    ezuiSkuDataSearch();
                                }
                            }]
                        });
                        // $('#ezuiDetailsForm #qtyordered').numberbox({editable:true});
                        // $('#ezuiDetailsForm #qtyorderedEach').numberbox({editable:true});
                        $('#ezuiDetailsForm #grossweight').numberbox({editable: true});
                        $('#ezuiDetailsForm #cubic').numberbox({editable: true});
                        $('#ezuiDetailsForm #price').numberbox({editable: true});
                        $('#ezuiDetailsForm #pickzone').combo('readonly', false);
                        $("#ezuiDetailsForm #location").textbox({
                            editable: false,
                            icons: [{
                                iconCls: 'icon-search',
                                handler: function (e) {
                                    $("#ezuiLocDataDialog #locationid").textbox('clear');
                                    ezuiLocDataClick();
                                    ezuiLocDataSearch();
                                }
                            }]
                        });
                        $('#ezuiDetailsForm #lotnum').textbox({editable: true});
                        $('#ezuiBtn_detailsCommit').linkbutton('enable');
                        /* 获取产品信息 */
                        $("input", $("#ezuiDetailsForm #qtyordered").next("span")).blur(function () {
                            if ($("#ezuiDetailsForm #qtyordered").val() != '') {
                                if ($("#ezuiDetailsForm #sku").val() == '') {
                                    $("#ezuiDetailsForm #qtyordered").numberbox('clear');
                                } else {
                                    $.ajax({
                                        url: 'basSkuController.do?getSkuInfo',
                                        data: {
                                            customerid: $("#ezuiDetailsForm #customerid").val(),
                                            sku: $("#ezuiDetailsForm #sku").val(),
                                            qty: $("#ezuiDetailsForm #qtyordered").numberbox('getValue')
                                        },
                                        type: 'POST',
                                        dataType: 'JSON',
                                        success: function (result) {
                                            try {
                                                $("#ezuiDetailsForm #grossweight").numberbox('setValue', result.grossweight);
                                                $("#ezuiDetailsForm #cubic").numberbox('setValue', result.cube);
                                                $("#ezuiDetailsForm #price").numberbox('setValue', result.price);
                                            } catch (e) {
                                                return;
                                            }
                                            ;
                                        }
                                    });
                                }
                                ;
                            }
                            ;
                        });
                    } else if (row.linestatus == '90' || row.linestatus == '99') {
                        $("#ezuiDetailsForm #sku").textbox({
                            editable: false,
                            icons: []
                        });
                        // $('#ezuiDetailsForm #qtyordered').numberbox({editable:false});
                        // $('#ezuiDetailsForm #qtyorderedEach').numberbox({editable:false});
                        $('#ezuiDetailsForm #grossweight').numberbox({editable: false});
                        $('#ezuiDetailsForm #cubic').numberbox({editable: false});
                        $('#ezuiDetailsForm #price').numberbox({editable: false});
                        $('#ezuiDetailsForm #pickzone').combo('readonly', true);
                        $("#ezuiDetailsForm #location").textbox({
                            editable: false,
                            icons: []
                        });
                        $('#ezuiDetailsForm #lotnum').textbox({editable: false});
                        $('#ezuiBtn_detailsCommit').linkbutton('disable');
                    } else {
                        $("#ezuiDetailsForm #sku").textbox({
                            editable: false,
                            icons: []
                        });
                        // $('#ezuiDetailsForm #qtyordered').numberbox({editable:false});
                        // $('#ezuiDetailsForm #qtyorderedEach').numberbox({editable:false});
                        $('#ezuiDetailsForm #grossweight').numberbox({editable: false});
                        $('#ezuiDetailsForm #cubic').numberbox({editable: false});
                        $('#ezuiDetailsForm #price').numberbox({editable: false});
                        $('#ezuiDetailsForm #pickzone').combo('readonly', false);
                        $("#ezuiDetailsForm #location").textbox({
                            editable: false,
                            icons: [{
                                iconCls: 'icon-search',
                                handler: function (e) {
                                    $("#ezuiLocDataDialog #locationid").textbox('clear');
                                    ezuiLocDataClick();
                                    ezuiLocDataSearch();
                                }
                            }]
                        });
                        $('#ezuiDetailsForm #lotnum').textbox({editable: true});
                        $('#ezuiBtn_detailsCommit').linkbutton('enable');
                    }
                    ezuiDetailsDialog.dialog('open');
                } else {
                    $.messager.show({
                        msg: '<spring:message code="common.message.selectRecord"/>',
                        title: '<spring:message code="common.message.prompt"/>'
                    });
                }
            }
            ;
        };

        /* 明细删除按钮 */
        var detailsDel = function () {
            if ($('#ezuiForm #orderno').val() == '') {
                return;
            } else {
                var row = ezuiDetailsDatagrid.datagrid('getSelected');
                if (row) {
                    if (row.linestatus != '00') {
                        $.messager.alert('提示', '此状态下不能删除明细!', 'info');
                        return;
                    } else if (row.addwho == 'EDI') {
                        $.messager.alert('提示', 'EDI订单,不能删除明细!', 'info');
                        return;
                    } else {
                        $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function (confirm) {
                            if (confirm) {
                                $.ajax({
                                    url: 'docOrderDetailsController.do?delete',
                                    data: {orderNo: row.orderno, orderLineNo: row.orderlineno},
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
                                            ezuiDetailsDatagrid.datagrid('reload');
                                        }
                                        ;
                                    }
                                });
                            }
                            ;
                        });
                    }
                    ;
                } else {
                    $.messager.show({
                        msg: '<spring:message code="common.message.selectRecord"/>',
                        title: '<spring:message code="common.message.prompt"/>'
                    });
                }
            }
            ;
        };

        /* 明细提交按钮 */
        var detailsCommit = function () {
            var url = '';
            if (processType == 'edit') {
                url = '<c:url value="/docOrderDetailsController.do?edit"/>';
            } else {
                url = '<c:url value="/docOrderDetailsController.do?add"/>';
            }
            // if($("#ezuiDetailsForm #lotatt04").textbox("getValue") == "" && $("#ezuiDetailsForm #lotatt05").textbox("getValue") == ""){
            //     showMsg("序列号和生产批号必须有一个");
            //     return;
            // }
            ezuiDetailsForm.form('submit', {
                url: url,
                onSubmit: function () {
                    if (ezuiDetailsForm.form('validate')) {
                        $.messager.progress({
                            text: '<spring:message code="common.message.data.processing"/>', interval: 100
                        });
                        return true;
                    } else {
                        return false;
                    }
                    ;
                },
                success: function (data) {
                    var msg = '';
                    try {
                        var result = $.parseJSON(data);
                        if (result.success) {
                            if (processType == 'edit') {
                                msg = result.msg;
                                ezuiDetailsDatagrid.datagrid('reload');
                            } else {
                                msg = result.msg;
                                ezuiDetailsDatagrid.datagrid('reload');
                            }
                            ezuiDetailsDialog.dialog('close');
                        } else {
                            msg = '<font color="red">' + result.msg + '</font>';
                        }
                        ;
                    } catch (e) {
                        msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                        msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
                    } finally {
                        $.messager.show({
                            msg: msg, title: '<spring:message code="common.message.prompt"/>'
                        });
                        $.messager.progress('close');
                    }
                    ;
                }
            });
        };


        /* 客户选择弹框查询 */
        var ezuiCustDataDialogSearch = function () {
            ezuiCustDataDialogId.datagrid('load', {
                customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
                customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
                activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
            });
        };

        /* 收货单位选择弹框查询 */
        var ezuiReceDataDialogSearch = function () {
            ezuiReceDataDialogId.datagrid('load', {
                customerid: $("#ezuiReceDataDialog #customerid").textbox("getValue"),
                customerType: $("#ezuiReceDataDialog #customerType").combobox('getValue'),
                activeFlag: $("#ezuiReceDataDialog #activeFlag").combobox('getValue')
            });
        };
        /* 客户选择弹框清空 */
        var ezuiCustToolbarClear = function () {
            $("#ezuiCustDataDialog #customerid").textbox('clear');
        };

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

        /* 客户选择弹框-订单信息界面 */
        var ezuiCustDataDialogClick = function () {
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
                    selectDialogCust();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                }, onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });

            ezuiCustDataDialog.dialog('open');
        };
        /* 客户选择-订单信息界面 */
        var selectDialogCust = function () {
            var row = ezuiCustDataDialogId.datagrid('getSelected');
            if (row) {
                $("#ezuiDialog #customerid").textbox('setValue', row.customerid);
                ezuiCustDataDialog.dialog('close');
            }
            ;
        };


        /*收货单位选择*/
        /* 客户选择弹框-订单信息界面 */
        var ezuiReceDataDialogClick = function () {
            $("#ezuiReceDataDialog #customerType").combobox('setValue', 'CO').combo('readonly', true);
            $("#ezuiReceDataDialog #activeFlag").combobox('setValue', '1').combo('readonly', true);
            ezuiReceDataDialogId = $('#ezuiReceDataDialogId').datagrid({
                url: '<c:url value="/basCustomerController.do?showDatagrid"/>',
                method: 'POST',
                toolbar: '#ezuiReceToolbar',
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
                    customerType: $("#ezuiReceDataDialog #customerType").combobox('getValue'),
                    activeFlag: $("#ezuiReceDataDialog #activeFlag").combobox('getValue')
                },
                idField: 'customerid',
                columns: [[
                    {field: 'customerid', title: '客户代码', width: 35},
                    {field: 'descrC', title: '中文名称', width: 50},
                    {field: 'descrE', title: '英文名称', width: 50},
                    {field: 'customerTypeName', title: '类型', width: 20},
                    {
                        field: 'activeFlag', title: '激活', width: 15, formatter: function (value, rowData, rowIndex) {
                            return rowData.activeFlag == '1' ? '是' : '否';
                        }
                    }
                ]],
                onDblClickCell: function (index, field, value) {
                    aaa();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                }, onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });

            ezuiReceDataDialog.dialog('open');
        };

        var aaa = function () {
            var rows = ezuiReceDataDialogId.datagrid('getSelected');
            ezuiCustomerDataDialogId = $('#ezuiCustomerDataDialogId').datagrid({
                url: '<c:url value="/gspReceivingAddressController.do?gspReceivingInfo"/>',
                method: 'POST',
                title: '收货单位详情',
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
                idField: 'customerid',
                queryParams: {
                    customerid: rows.customerid
                },
                columns: [[
                    {field: 'contacts', title: '联系人', width: 20},
                    {field: 'phone', title: '联系电话', width: 20},
                    {field: 'deliveryAddress', title: '收货地址', width: 80}
                ]],
                onDblClickCell: function (index, field, value) {
                }, onDblClickRow: function (index, row) {
                    $("#ezuiDialog #cContact").textbox('setValue', row.contacts);
                    $("#ezuiDialog #cTel1").textbox('setValue', row.phone);
                    $("#ezuiDialog #cAddress1").textbox('setValue', row.deliveryAddress);
                    $("#ezuiDialog #consigneeid").textbox('setValue', rows.descrC);
                    ezuiCustomerDataDialog.dialog('close');
                    ezuiReceDataDialog.dialog('close');
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                }, onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });
            ezuiCustomerDataDialog.dialog('open');
        };
        /* 商品选择弹框查询 */
        var ezuiSkuDataSearch = function () {
            ezuiSkuDataDialogId.datagrid('load', {
                customerid: $("#ezuiSkuDataDialog #customerid").textbox("getValue"),
                sku: $("#ezuiSkuDataDialog #sku").textbox("getValue"),
                activeFlag: $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
            });
        };
        /* 商品选择弹框清空 */
        var ezuiSkuToolbarClear = function () {
            $("#ezuiSkuDataDialog #sku").textbox('clear');
        };
        /* 商品选择弹框 */
        var ezuiSkuDataClick = function () {
            ezuiSkuDataDialogId = $('#ezuiSkuDataDialogId').datagrid({
                url: '<c:url value="/basSkuController.do?showDatagridByInvLot"/>',
                method: 'POST',
                toolbar: '#ezuiSkuToolbar',
                title: '产品档案',
                pageSize: 50,
                pageList: [50, 100, 200],
                fit: true,
                border: false,
                fitColumns: false,
                nowrap: false,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                queryParams: {
                    customerid: $("#ezuiDetailsForm #customerid").textbox("getValue"),
                    activeFlag: $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
                },
                idField: 'sku',
                columns: [[
                    {field: 'customerid', title: '客户代码', width: 80},
                    {field: 'sku', title: '产品代码', width: 120},
                    {field: 'reservedfield01', title: '中文名称', width: 160},
                    {field: 'descrE', title: '英文名称', width: 160},
                    {
                        field: 'activeFlag', title: '激活', width: 40, formatter: function (value, rowData, rowIndex) {
                            return rowData.activeFlag == '1' ? '是' : '否';
                        }
                    },
                    {field: 'alternateSku1', title: '产品条码', width: 120},
                    {field: 'qty', title: '库存数', width: 60},
                    {field: 'qtyallocated', title: '分配数', width: 60},
                    {field: 'qtyonhold', title: '冻结数', width: 60},
                    {field: 'packid', title: '包装代码', width: 80},
                    {field: 'qty1', title: '换算率', width: 80}

                ]],
                onDblClickCell: function (index, field, value) {
                    selectSku();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                },
                onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });
            $("#ezuiSkuDataDialog #customerid").textbox('setValue', $("#ezuiDetailsForm #customerid").textbox("getValue")).textbox('readonly', true);
            $("#ezuiSkuDataDialog #activeFlag").combobox('setValue', '1').combo('readonly', true);
            ezuiSkuDataDialog.dialog('open');
        };
        /* 商品选择 */
        var selectSku = function () {
            var row = ezuiSkuDataDialogId.datagrid('getSelected');
            if (row) {
                $("#ezuiDetailsForm #sku").textbox('setValue', row.sku);
                $("#ezuiDetailsForm #skuName").textbox('setValue', row.reservedfield01);
                $("#ezuiDetailsForm #skuNameE").textbox('setValue', row.descrE);
                $("#ezuiDetailsForm #alternativesku").textbox('setValue', row.alternateSku1);
                $("#ezuiDetailsForm #packid").textbox('setValue', row.packid);
                $("#ezuiDetailsForm #qty1").val(row.qty1);
                $("#ezuiDetailsForm #qtyordered").numberbox('clear');
                $("#ezuiDetailsForm #grossweight").numberbox('clear');
                $("#ezuiDetailsForm #cubic").numberbox('clear');
                $("#ezuiDetailsForm #price").numberbox('clear');
                //选择产品生产批号下拉
                $("#ezuiDetailsForm #lotatt04").combobox({
                    panelHeight: 'auto',
                    url: sy.bp() + '/docOrderHeaderController.do?getLotAttBySkuCustomerId&sku=' + row.sku + "&customerId=" + $("#ezuiDetailsForm #customerid").textbox("getValue"),
                    valueField: 'id',
                    textField: 'value',
                    width: 135,
                    onSelect: function (record) {
                        /* console.log(record);
                $("#ezuiDetailsForm #lotatt09").combobox('setValue',record.option.lotatt09);
                $("#ezuiDetailsForm #lotatt10").textbox('setValue',record.option.lotatt10);
                $("#ezuiDetailsForm #lotatt08").textbox('setValue',record.option.lotatt15);
                $("#ezuiDetailsForm #lotatt14").textbox('setValue',record.option.lotatt14);*/
                    }
                });
                //选择产品序列号下拉
                $("#ezuiDetailsForm #lotatt05").combobox({
                    panelHeight: 'auto',
                    url: sy.bp() + '/docOrderHeaderController.do?getLotAtt05BySkuCustomerId&sku=' + row.sku + "&customerId=" + $("#ezuiDetailsForm #customerid").textbox("getValue"),
                    valueField: 'id',
                    textField: 'value',
                    width: 135,
                    onSelect: function (record) {
                        /* console.log(record);
                 $("#ezuiDetailsForm #lotatt09").combobox('setValue',record.option.lotatt09);
                 $("#ezuiDetailsForm #lotatt10").textbox('setValue',record.option.lotatt10);
                 $("#ezuiDetailsForm #lotatt08").textbox('setValue',record.option.lotatt15);
                 $("#ezuiDetailsForm #lotatt14").textbox('setValue',record.option.lotatt14);*/
                    }
                });
                ezuiSkuDataDialog.dialog('close');
            }
            ;
        };

        /* 库位选择弹框查询 */
        var ezuiLocDataSearch = function () {
            ezuiLocDataDialogId.datagrid('load', {
                locationid: $("#ezuiLocDataDialog #locationid").textbox("getValue")
            });
        };
        /* 库位选择弹框清空 */
        var ezuiLocToolbarClear = function () {
            $("#ezuiLocDataDialog #locationid").textbox('clear');
            $("#ezuiLocDataDialog #locationcategory").combobox('clear');
        };
        /* 库位选择弹框 */
        var ezuiLocDataClick = function () {
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
                    selectLocation();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                },
                onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });
            ezuiLocDataDialog.dialog('open');
        };
        /* 库位选择 */
        var selectLocation = function () {
            var row = ezuiLocDataDialogId.datagrid('getSelected');
            if (row) {
                $("#ezuiDetailsForm #location").textbox('setValue', row.locationid);
                ezuiLocDataDialog.dialog('close');
            }
            ;
        };

        var docOrderRowStyler = function (index, row) {
            if (row.sostatus == "30") {
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
                modal: true,
                title: '引用出库',
                buttons: '',
                width: 250,
                height: 100,
                onOpen: function () {
                },
                onClose: function () {

                }
            }).dialog('close');

            $("#refOutNo").combobox({
                panelHeight: 'auto',
                editable: false,
                url: '/docOrderHeaderController.do?getRefOutOrder',
                valueField: 'id',
                textField: 'value',
                width: 150
            })
        })

        function showRefOut() {
            var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
            if (checkedItems.length > 1 || checkedItems.length == 0) {
                showMsg("请选择一张单据进行引用");
                return;
            }
            refOutDialog.dialog("open");
            $("#refOutNo").combobox("clear");
        }

        function closeRefOut() {
            refOutDialog.dialog("close");
        }

        //明细复用
        var reuseDialogGo;
        //初始化
        $(function () {
            $('#ezuiBtn_copyDetailGo').linkbutton('disable');
            reuseDialogGo = $('#reuseDialogGo').dialog({
                modal: true,
                title: '明细复用',
                buttons: '',
                width: 350,
                height: 150,
                onOpen: function () {
                },
                onClose: function () {

                }
            }).dialog('close');


        })

        //给明细复用入库编号文本框
        function copyDetailGo() {
            $('#copyFlagGo').combobox({
                onChange: function (newValue, oldValue) {

                    $('#refInNoGo').textbox('clear');
                    if (newValue == 1) {
                        $('#refInNoGo').textbox({
                            multiline: false,
                            prompt: '请输入SO编号'
                        });
                    } else if (newValue == -1) {
                        $('#refInNoGo').textbox({
                            multiline: false,
                            prompt: '请输入ASN编号'
                        });
                    } else {
                        $('#refInNoGo').textbox({
                            multiline: false,
                            prompt: '请选择订单...'
                        });
                    }
                }
            });
            reuseDialogGo.dialog("open");
            $('#copyFlagGo').combobox('clear');
        }


        function copyDodetails() {
            var copyFlagGo = $('#copyFlagGo').combobox('getValue');//标识出入库标识
            var newOrderno = $("#ezuiDialog  #orderno").textbox('getValue');//新增SO编号
            var customerid = $("#ezuiDialog  #customerid").textbox('getValue');//货主代码
            var soreference2 = $("#ezuiDialog  #soreference2").textbox('getValue');//定向入库单号
            $.ajax({
                url: 'docOrderHeaderController.do?copyDetailGo',
                data: {
                    generalNO: $("#refInNoGo").val(),
                    detailOrderno: newOrderno,
                    customerid: customerid,
                    soreference2: soreference2,
                    copyFlag: copyFlagGo
                },
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {
                    try {
                        if (result.success) {
                            $('#ezuiBtn_copyDetailGo').linkbutton('disable');
                            $('#s').datagrid('load', {orderno: newOrderno});
                            reuseDialogGo.dialog("close");
                            $('#refInNoGo').textbox('clear');
                        }
                        showMsg(result.msg)
                    } catch (e) {
                        return;
                    }
                    ;
                }
            });
        }

        function closeReuseGo() {
            reuseDialogGo.dialog("close");
            $('#refInNoGo').textbox('clear');
            $('#copyFlagGo').combobox('clear');

        }


        function doRefOut() {
            var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
            $.ajax({
                url: 'docOrderHeaderController.do?doRefOut',
                data: {orderno: checkedItems[0].orderno, refOrderno: $("#refOutNo").combobox("getValue")},
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {
                    try {

                        showMsg(result.msg)
                        refOutDialog.dialog("close");
                    } catch (e) {
                        return;
                    }
                    ;
                }
            });
        }

        /*
打印质量合格证
 */
        function printH() {
            orderList = null;
            var checkedItems = $('#ezuiDatagrid').datagrid('getSelections');
            $.each(checkedItems, function (index, item) {
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
            window.open(sy.bp() + "/docOrderHeaderController.do?printH&orderCodeList=" + orderList, "Report_" + orderList, "scrollbars=yes,resizable=no");
        }

        function rDouble() {
            var checkedItems = ezuiDatagrid.datagrid('getSelections');
            $.each(checkedItems, function (index, item) {
                $.ajax({
                    url: 'docOrderHeaderController.do?reqDouble',
                    data: {orderno: item.orderno},
                    type: 'POST',
                    dataType: 'JSON',
                    success: function (result) {
                        try {
                            showMsg(result.msg)
                            refOutDialog.dialog("close");
                        } catch (e) {
                            return;
                        }
                        ;
                    }
                });
            })
        }

        function choseOrderTypeAfter(value) {
            if (value && value.id == "YF") {
                $("#ezuiDetailsForm #lotatt10").combobox("setValue", "BHG").combobox("readonly", true);
            } else {
                $("#ezuiDetailsForm #lotatt10").combobox("setValue", "HG").combobox("readonly", true);
            }
        }

        function choseOrderTypeAdd(value) {
            if (value && value == "YF") {
                $("#ezuiDetailsForm #lotatt10").combobox("setValue", "BHG").combobox("readonly", true);
            } else {
                $("#ezuiDetailsForm #lotatt10").combobox("setValue", "HG").combobox("readonly", true);
            }
        }

        //快递投诉
        var courierComplaint = function () {
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                $("#courierComplaintForm #orderno").textbox('setValue', row.orderno);
                $("#courierComplaintForm #courierComplaint").textbox('setValue', row.courierComplaint);
                courierComplaintDialog.dialog('open');
            } else {
                $.messager.show({
                    msg: '请选择一笔资料', title: '<spring:message code="common.message.prompt"/>'
                });
            }

        }

        //提交快递投诉
        var commitcourierComplaint = function () {
            url = '<c:url value="/docOrderHeaderController.do?courierComplaint"/>';
            var row = ezuiDatagrid.datagrid('getSelected');
            var msg = '';
            if (courierComplaintForm.form('validate')) {
                var data = new Object();
                data.orderno = row.orderno;
                data.courierComplaintU = $("#courierComplaintForm #courierComplaint").val();

                $.messager.progress({
                    text: '<spring:message code="common.message.data.processing"/>', interval: 100
                });
                $.ajax({
                    url: url,
                    data: data,
                    dataType: 'json',
                    error: function (a, b, c) {
                        //alert(a + b + c);
                    },
                    success: function (result) {
                        try {
                            if (result.success) {
                                msg = result.msg;
                            } else {
                                msg = result.msg;

                            }
                        } catch (e) {

                            msg: '数据错误!';

                        } finally {
                            $.messager.show({
                                msg: msg, title: '<spring:message code="common.message.prompt"/>'
                            });
                            $.messager.progress('close');
                            courierComplaintDialog.dialog('close');
                            ezuiDatagrid.datagrid('reload');
                        }
                    }
                });
            } else {
                $.messager.show({
                    msg: '请填写完整!', title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };

        //回写快递单号/签回单号
        var writeBackExpress = function () {
            var row = ezuiDatagrid.datagrid('getSelected');
            // if (row.cAddress4 != null) {
            $("#writeBackExpressForm #orderno").textbox('setValue', row.orderno);
            $("#writeBackExpressForm #caddress4").textbox('setValue', row.cAddress4);
            $("#writeBackExpressForm #caddress3").textbox('setValue', row.cAddress3);
            writeBackExpressDiv.dialog('open');
            <%--}else{--%>
            <%--    $.messager.show({--%>
            <%--        msg: '请选择一笔成功发运的信息', title: '<spring:message code="common.message.prompt"/>'--%>
            <%--    });--%>
            <%--}--%>
        }
        //提交回写快递单号/签回单号
        var writeBackExpressBtnCommit = function () {
            url = '<c:url value="/docOrderHeaderController.do?writeBackExpressBtnCommit"/>';
            var row = ezuiDatagrid.datagrid('getSelected');
            var msg = '';
            if (row) {
                var data = new Object();
                data.orderno = $("#writeBackExpressForm #orderno").val();
                data.cAddress3 = $("#writeBackExpressForm #caddress3").val();
                data.cAddress4 = $("#writeBackExpressForm #caddress4").val();
                $.messager.progress({
                    text: '<spring:message code="common.message.data.processing"/>', interval: 100
                });
                $.ajax({
                    url: url,
                    data: data,
                    dataType: 'json',
                    success: function (result) {
                        try {
                            if (result.success) {
                                msg = result.msg;
                            } else {
                                msg = result.msg;

                            }
                        } catch (e) {

                            msg: '数据错误!';

                        } finally {
                            $.messager.show({
                                msg: msg, title: '<spring:message code="common.message.prompt"/>'
                            });
                            $.messager.progress('close');
                            writeBackExpressDiv.dialog('close');
                            ezuiDatagrid.datagrid('reload');
                        }
                    }
                });
            }
        };

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
                        <th>SO编号</th>
                        <td><input type='text' id='orderno' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>货主</th>
                        <td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>客户单号</th>
                        <td><input type='text' id='soreference1' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>定向入库单号</th>
                        <td><input type='text' id='soreference2' class='easyui-textbox' size='16' data-options=''/></td>
                    </tr>
                    <tr>
                        <th>收货单位</th>
                        <td><input type='text' id='consigneeid' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>快递单号</th>
                        <td><input type='text' id='cAddress4Q' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>产品线</th>
                        <td>
                            <input id="productLineOrder" name="productLineOrder" size='16' type="text"
                                   class='easyui-combobox' data-options="panelHeight: '300px',
																															editable: false,
																															url:'<c:url value="/productLineController.do?getCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
                        </td>
                        <th>回传标识</th>
                        <td>
                            <input type='text' id='edisendflag' name="edisendflag" class='easyui-combobox' size='16'
                                   data-options="panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '已回传'},
																																{id: '0', value: '未回传'}
																															]"/>
                        </td>

                    </tr>
                    <tr>
                        <th>订单状态</th>
                        <td><input type='text' id='sostatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
                        </td>

                        <th>至</th>
                        <td><input type='text' id='sostatusTo' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
                        </td>
                        <th>订单类型</th>
                        <td><input type='text' id='ordertype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																editable: false,
																																url:'<c:url value="/docOrderHeaderController.do?getOrderTypeCombobox"/>',
																																valueField: 'id',
																																textField: 'value'
																																"/>
                        </td>
                        <th>释放状态</th>
                        <td><input type='text' id='releasestatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docOrderHeaderController.do?getReleasestatusCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/>
                        </td>
                    </tr>
                    <tr>
                        <th>订单创建时间</th>
                        <td><input type='text' id='ordertime' class='easyui-datetimebox' size='16' data-options=""/>
                        </td>
                        <th>至</th>
                        <td><input type='text' id='ordertimeTo' class='easyui-datetimebox' size='16' data-options=""/>
                        </td>
                        <th>订单编辑时间</th>
                        <td><input type='text' id='edittime' class='easyui-datetimebox' size='16' data-options=""/></td>
                        <th>至</th>
                        <td><input type='text' id='edittimeTo' class='easyui-datetimebox' size='16' data-options=""/>
                        </td>
                        <th style="text-align: left"><input id="sostatusCheck" type="checkbox" onclick=""
                                                            checked="checked"><label
                                for="sostatusCheck">显示完成/取消订单</label></th>

                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">
                            <a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                            <a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-remove"'
                               href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                            <a onclick='toOutData();' id='ezuiBtn_export' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
                            <a onclick='doExportGoodsList();' id='ezuiBtn_export_goods_list' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出随货</a>
                            <a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>
                            <a onclick='doExportOrderNo();' id='ezuiBtn_exportOerderNo' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出序列号记录</a>

                        </td>
                    </tr>
                </table>
            </fieldset>
            <div>
                <a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message
                        code='common.button.add'/></a>
                <a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message
                        code='common.button.edit'/></a>
                <a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message
                        code='common.button.delete'/></a>
                <%--
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
--%>
                <a onclick='chooseExpressType();' id='ezuiBtn_allocation' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message
                        code='common.button.allocation'/></a>
                <a onclick='deAllocation();' id='ezuiBtn_cancelAllocation' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                        code='common.button.cancelAllocation'/></a>
                <a onclick='picked();' id='ezuiBtn_picked' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>确认拣货</a>
                <a onclick='cancelPicked();' id='ezuiBtn_cancelPicked' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'>取消拣货</a>
                <a onclick='packing();' id='ezuiBtn_packing' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message
                        code='common.button.packing'/></a>
                <a onclick='unPacking();' id='ezuiBtn_cancelPacking' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                        code='common.button.cancelPacking'/></a>
                <a onclick='shipment();' id='ezuiBtn_shipment' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message
                        code='common.button.shipment'/></a>
                <a onclick='cancel();' id='ezuiBtn_cancel' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                        code='common.button.rubBish'/></a>
                <%--                    <a onclick='showRefOut()' id='ezuiBtn_ref' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>引用出库</a>--%>

                <%--					<a onclick='rDouble()' id='ezuiBtn_double' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>匹配双证</a>--%>
                <!--<a onclick='print();' id='ezuiBtn_print' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>生成波次（D）</a>-->
                <div style="float: right">
                    <a class='easyui-linkbutton' data-options='plain:true'>已选择</a><a id="nummm"
                                                                                     class='easyui-linkbutton'
                                                                                     data-options='plain:true'>0</a><a
                        class='easyui-linkbutton' data-options='plain:true'>条</a>
                </div>
            </div>
            <div>
                <a onclick='toPackData();' id='ezuiBtn_PrintPacking' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印拣货单</a>
                <a onclick='printAccompanying();' id='ezuiBtn_PrintAccompanying' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印随货清单</a>
                <a onclick='printExpress();' id='ezuiBtn_PrintExpress' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印快递单</a>
                <a onclick='printH()' id='ezuiBtn_h' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>打印质量合格证</a>
                <a onclick='courierComplaint();' id='ezuiBtn_courierComplaint' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>快递投诉</a>
                <a onclick='writeBackExpress();' id='ezuiBtn_WriteBackExpress' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>回写快递单号</a>

            </div>

        </div>
        <table id='ezuiDatagrid'></table>
    </div>
</div>
<div id='ezuiDialogBtn'>
    <%-- <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.close'/></a>
</div>
<div id='ezuiDetailsDialogBtn'>
    <a onclick='detailsCommit();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
    <div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message
            code='common.button.add'/></div>
    <div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message
            code='common.button.delete'/></div>
    <div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message
            code='common.button.edit'/></div>
</div>
<div id='ezuiDetailsMenu' class='easyui-menu' style='width:120px;display: none;'>
    <div onclick='detailsAdd();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message
            code='common.button.skuAdd'/></div>
    <div onclick='detailsDel();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message
            code='common.button.skuDelete'/></div>
    <div onclick='detailsEdit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message
            code='common.button.skuEdit'/></div>
</div>

<!-- 导入start -->
<div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>
        <table>
            <tr>
                <th>档案</th>
                <td>
                    <input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36"
                           data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>
                    <a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton'
                       href='javascript:void(0);'>下载档案模版</a>
                </td>
            </tr>
            <tr>
                <th>执行结果</th>
                <td><input id='importResult' class="easyui-textbox" size='100' style="height:150px"
                           data-options="editable:false,multiline:true"/></td>
            </tr>
        </table>
    </form>
</div>
<div id='ezuiImportDataDialogBtn'>
    <a onclick='commitImportData();' id='ezuiBtn_importDataCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiImportDataDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<!-- 导入end -->
<!-- 导出start -->
<div id='ezuiOutToExcelDataDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiOutToExcel1Form' method='post' enctype='multipart/form-data'>
        <table>
            <tr>
                <th>选择导出内容</th>
                <td><input id='outtype' class="easyui-combobox" size='100' style="height:30px" data-options="
																								editable: false,
																								panelHeight: 'auto',
																								width:'150',
																								valueField: 'id',
																								textField: 'value',
																								data: [{
																									id: '1',
																									value: '导出头档'
																								},{
																									id: '-1',
																									value: '导出明细'
																								}]"/></td>
            </tr>
        </table>
    </form>
</div>

<div id='ezuiOutToExcel1Btn'>
    <a onclick='doExport();' id='ezuiBtn_outDataCommit' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiOutToExcelDataDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<!-- 导出end -->
<!-- 拣货单start -->
<div id='ezuiPackDataDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiPackForm' method='post' enctype='multipart/form-data'>
        <table>
            <tr>
                <th>拣货单类型</th>
                <td><input id='pack' class="easyui-combobox" size='100' style="height:30px" data-options="
																								editable: false,
																								panelHeight: 'auto',
																								width:'150',
																								valueField: 'id',
																								textField: 'value',
																								data: [{
																									id: '1',
																									value: '拣货标签'
																								},{
																									id: '-1',
																									value: '拣货单'
																								}]"/></td>
            </tr>
        </table>
    </form>
</div>

<div id='ezuiPackABtn'>
    <a onclick='doPack();' id='ezuiBtn_PackCommit' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiPackDataDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<!-- 拣货单end -->
<!-- 批量操作返回start -->
<div id='ezuiOperateResultDataDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiOperateResultDataForm' method='post' enctype='multipart/form-data'>
        <table>
            <tr>
                <td><input id='operateResult' class="easyui-textbox" size='100' style="height:150px"
                           data-options="editable:false,multiline:true"/></td>
            </tr>
        </table>
    </form>
</div>
<div id='ezuiOperateResultDataDialogBtn'>
    <a onclick='ezuiDialogClose("#ezuiOperateResultDataDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
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
                <a onclick='closeRefOut()' id='closeRefOut' class='easyui-linkbutton' data-options=''
                   href='javascript:void(0);'>关闭</a>
                <a onclick='doRefOut()' id='doRefOut' class='easyui-linkbutton' data-options=''
                   href='javascript:void(0);'>引用</a>
            </td>
        </tr>
    </table>
</div>
<!-- 明细复用 -->
<div id="reuseDialogGo" style="display: none;text-align: center">
    <table width="100%">
        <tr>
            <td>订单
                <input onChange="locationCut()" type='text' id='copyFlagGo' size='16' class="easyui-combobox"
                       data-options="
																								editable: false,
																								panelHeight: 'auto',
																								width:'150',
																								valueField: 'id',
																								textField: 'value',
																								data: [{
																									id: '1',
																									value: '复用出库明细'
																								},{
																									id: '-1',
																									value: '复用入库明细'
																								}]"/>
            </td>
        </tr>
        <tr>
            <td>
                编号
                <input id="refInNoGo" type="text" class='easyui-textbox' size='16'
                       data-options="panelHeight: 'auto', width:'150'"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <a onclick='closeReuseGo();' id='closeRefInReuse' class='easyui-linkbutton' data-options=''
                   href='javascript:void(0);'>关闭</a>
                <a onclick='copyDodetails();' id='doRefInReuse' class='easyui-linkbutton' data-options=''
                   href='javascript:void(0);'>复用</a>
            </td>
        </tr>
    </table>
</div>

<!-- 批量操作返回end -->

<%--快递投诉单点击弹窗--%>
<div id='courierComplaintDialog' style='padding: 10px;'>
    <form id='courierComplaintForm' method='post'>
        <table>

            <tr>
                <th>SO编号</th>
                <td><input type='text' id='orderno' name="orderno" class='easyui-textbox' size='20'
                           data-options="readonly:true"/></td>

            </tr>
            <tr>
                <th>投诉内容</th>
                <td><input type='text' id='courierComplaint' name="courierComplaint" class='easyui-textbox' size='20'
                           data-options="width:160,height:120,required:true,multiline:true"/></td>

            </tr>


        </table>
    </form>
</div>
<div id='courierComplaintDialogBtn'>
    <a onclick='commitcourierComplaint();' class='easyui-linkbutton' href='javascript:void(0);'>提交</a>
</div>


<!-- 是否需要签回单 -->
<div id='returnSfodd' style='padding: 10px;'>
    <form id='returnSfoddForm' method='post'>
        <table>
            <select id="returnSfoddid" class="easyui-combobox" name="returnSfoddid" style="width:170px;">
                <option value="1">是</option>
                <option value="0">否</option>
            </select>

        </table>
    </form>
</div>

<div id='returnSfoddDialogBtn'>
    <a id="returnSfoddBtn" class='easyui-linkbutton' href='javascript:void(0);'>提交</a>
</div>
<!-- 回写快递单号/签回单号 -->
<div id='writeBackExpressDiv' style='padding: 10px;'>
    <form id='writeBackExpressForm' method='post'>
        <table>
            <tr>
                <th>SO编号</th>
                <td><input type='text' id='orderno' name="orderno" class='easyui-textbox' size='20'
                           data-options="readonly:true"/></td>
            </tr>
            <tr>
                <th>快递单号</th>
                <td><input type='text' id='caddress4' name="caddress4" class='easyui-textbox' size='20'
                           data-options="required:true"/></td>

            </tr>
            <tr>
                <th>签回单号</th>
                <td><input type='text' id='caddress3' name="caddress3" class='easyui-textbox' size='20'/></td>
            </tr>
        </table>
    </form>
</div>

<div id='writeBackExpressBtn'>
    <a onclick='writeBackExpressBtnCommit()' class='easyui-linkbutton' href='javascript:void(0);'>提交</a>
</div>


<c:import url='/WEB-INF/jsp/docOrderHeader/dialog.jsp'/>
<c:import url='/WEB-INF/jsp/docOrderHeader/custDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docOrderHeader/receDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docOrderHeader/detailsDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docOrderHeader/skuDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docOrderHeader/locDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docOrderHeader/customerDialog.jsp'/>
</body>
</html>
