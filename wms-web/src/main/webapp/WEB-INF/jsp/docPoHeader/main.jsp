<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp'/>
    <c:import url='/WEB-INF/jsp/include/easyui.jsp'/>
    <script type='text/javascript'>
        var processType;
        var ezuiMenu;                 //右键菜单
        var ezuiDetailsMenu;          //二级datagrid右键菜单
        var ezuiForm;                 //二级dialog from表单
        var ezuiDetailsForm;          //三级dialog from表单
        var ezuiDialog;               //二级dialog
        var ezuiDetailsDialog;        //三级dialog
        var ezuiDatagrid;             //主页datagrid
        var ezuiDetailsDatagrid;      //二级datagrid
        var ezuiCustDataDialog;       //货主编码选择弹框页面
        var ezuiCustDataDialogId;     //货主编码选择弹框
        var ezuiSkuDataDialog;        //三级diaoyog产品选择框
        var ezuiSkuDataDialogId;      //三级diaoyog产品选择框
        var productDialog_docPoHeader;//主页产品选择框
        $(function () {
            ezuiMenu = $('#ezuiMenu').menu();              //右键菜单
            ezuiDetailsMenu = $('#ezuiDetailsMenu').menu();//二级右键菜单
            ezuiForm = $('#ezuiForm').form();              //二级dialog from表单
            ezuiDetailsForm = $('#ezuiDetailsForm').form();//三级dialog from表单
            ezuiImportDataForm = $('#ezuiImportDataForm').form();
//主页datagrid初始化
            ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                url: '<c:url value="/docPoHeaderController.do?showDatagrid"/>',//访问docPoHeaderController.do?showDatagrid
                method: 'POST',
                toolbar: '#toolbar',
                title: '采购订单',
                pageSize: 50,
                pageList: [50, 100, 200],
                fit: true,
                border: false,
                fitColumns: true,
                nowrap: true,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: false,
                singleSelect: true,
                idField: 'pono',
                //分页显示部分
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'pono', title: '采购单号', width: 20},
                    {field: 'potypeName', title: '采购类型', width: 14},
                    {field: 'postatusName', title: '采购状态', width: 20},
                    {field: 'customerid', title: '货主代码', width: 20},
                    {field: 'poreference1', title: '客户单号', width: 20},
                    // {field: 'expectedarrivetime2',		title: '预期到货时间TO',	width: 14 },
                    {field: 'supplierName', title: '供应商名称', width: 30},
                    {field: 'supplierid', title: '供应商',hidden:true, width: 30},
                    // {field: 'supplierCity',		title: '城市',	width: 14 },
                    // {field: 'supplierProvince',		title: '省',	width: 14 },
                    // {field: 'supplierCountry',		title: '国家',	width: 14 },
                    // {field: 'supplierZip',		title: '邮编',	width: 14 },
                    // {field: 'edisendflag',		title: 'EDI发送标签',	width: 14 },
                    {field: 'supplierContact', title: '供应商联系人', width: 20},
                    // {field: 'supplierMail',		title: '供应商电子邮件',	width: 14 },
                    // {field: 'supplierFax',		title: '供应商传真',	width: 14 },
                    // {field: 'supplierTel1',		title: '供应商电话1',	width: 14 },
                    // {field: 'supplierTel2',		title: '供应商电话2',	width: 14 },
                    // {field: 'userdefine1',		title: '用户自定义1',	width: 14 },
                    // {field: 'userdefine2',		title: '用户自定义2',	width: 14 },
                    // {field: 'userdefine3',		title: '用户自定义3',	width: 14 },
                    // {field: 'userdefine4',		title: '用户自定义4',	width: 14 },
                    // {field: 'userdefine5',		title: '用户自定义5',	width: 14 },
                    // {field: 'addtime',		title: '创建时间',	width: 20 },
                    {field: 'expectedarrivetime1', title: '预期到货时间', width: 30},
                    {field: 'pocreationtime', title: '创建时间', width: 25},
                    {field: 'addwho', title: '创建人', width: 14},
                    {field: 'edittime', title: '最后编辑时间', width: 30},
                    {field: 'editwho', title: '编辑人', width: 14},
                    // {field: 'edisendtime',		title: '待输入栏位38',	width: 14 },
                    // {field: 'hedi01',		title: '待输入栏位39',	width: 14 },
                    // {field: 'hedi02',		title: '待输入栏位40',	width: 14 },
                    // {field: 'hedi03',		title: '待输入栏位41',	width: 14 },
                    // {field: 'hedi04',		title: '待输入栏位42',	width: 14 },
                    // {field: 'hedi05',		title: '待输入栏位43',	width: 14 },
                    // {field: 'hedi06',		title: '待输入栏位44',	width: 14 },
                    // {field: 'hedi07',		title: '待输入栏位45',	width: 14 },
                    // {field: 'hedi08',		title: '待输入栏位46',	width: 14 },
                    // {field: 'hedi09',		title: '待输入栏位47',	width: 14 },
                    // {field: 'hedi10',		title: '待输入栏位48',	width: 14 },
                    // {field: 'notes', title: '备注', width: 75},
                    {field: 'warehouseid', title: '仓库编码', width: 20},
                    // {field: 'createsource',		title: '待输入栏位50',	width: 14 },
                    // {field: 'releasestatus',		title: '待输入栏位51',	width: 14 },
                    // {field: 'userdefinea',		title: '待输入栏位52',	width: 14 },
                    // {field: 'userdefineb',		title: '待输入栏位53',	width: 14 }
                ]],
                onDblClickCell: function (index, field, value) {
                    edit();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                    event.preventDefault();
                    $(this).datagrid('unselectAll');
                     $(this).datagrid('selectRow', rowIndex);
                    ezuiMenu.menu('show', {
                    	left : event.pageX,
                    	top : event.pageY
                    });
                }, onCheckAll: function (rows) {
                    if (rows) {
                        for (var i = 0; i < rows.length; i++) {
                            afterCheckButtion(rows[i]);
                        }
                    }
                },
                onCheck: function (index, row) {
                    if (index - 1) {
                        afterCheckButtion(row);
                    }
                    ;
                },
                onSelect: function (rowIndex, rowData) {
                    if (rowIndex - 1) {
                        afterCheckButtion(rowData);
                    }
                    ;
                },
                onLoadSuccess: function (data) {
                    ajaxBtn($('#menuId').val(), '<c:url value="/docPoHeaderController.do?getBtn"/>', ezuiMenu);
                    $(this).datagrid('unselectAll');
                    $(this).datagrid('clearChecked');
                }
            });
//二级页面订单明细列表datagird（编辑数据窗口）
            ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
                url: '<c:url value="/docPoDetailsController.do?showDatagrid"/>',
                method: 'POST',
                toolbar: '#detailToolbar',
                idField: 'polineno',
                title: '入库单明细',
                pageSize: 50,
                pageList: [50, 100, 200],
                border: false,
                fitColumns: false,
                nowrap: false,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                checkOnSelect: true,
                selectOnCheck: true,
                columns: [[
                    {field: 'chk', checkbox: true, width: 6},
                    {field: 'polineno', title: '订单行号', width: 100},
                    {field: 'polinestatusName', title: '行状态', width: 70},
                    {field: 'customerid', title: '货主', width: 70},
                    {field: 'sku', title: '产品代码', width: 107},
                    {field: 'skudescrc', title: '产品名称', width: 107},
                    {field: 'uom', title: '单位', width: 155},
                    {field: 'packid', title: '包装代码', width: 100},
                    {field: 'orderedqtyEach', title: '订单数量', width: 60},
                    {field: 'totalcubic', title: '总体积', width: 70},
                    {field: 'totalgrossweight', title: '总重量', width: 70},
                    {field: 'totalnetweight', title: '总净重量', width: 70},
                    {field: 'totalgrossweight', title: '重量', width: 70},
                    {field: 'addtime', title: '创建时间', width: 145},
                    {field: 'addwho', title: '创建人', width: 70},
                    {field: 'edittime', title: '最后编辑时间', width: 145},
                    {field: 'editwho', title: '编辑人', width: 70},
                    {field: 'notes', title: '备注', width: 155},
                ]],
                onDblClickCell: function (index, field, value) {
                     detailsEdit();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                },
                onSelect: function (rowIndex, rowData) {
                    if (rowIndex - 1) {
                        if (rowData.polinestatus == '00') {
                            $('#ezuiDetailsBtn_edit').linkbutton('enable');
                            $('#ezuiDetailsBtn_del').linkbutton('enable');
                            $('#ezuiDetailsBtn_receive').linkbutton('enable');
                        } else {
                            $('#ezuiDetailsBtn_edit').linkbutton('disable');
                            $('#ezuiDetailsBtn_del').linkbutton('disable');
                            $('#ezuiDetailsBtn_receive').linkbutton('disable');
                        }
                        ;
                    }
                    ;
                },
                onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                    $(this).datagrid("resize", {height: 200});
                }
            });
//查询控件初始化
            $("#customerid").textbox({
                icons:[{
                    iconCls:'icon-search',
                    handler: function(e){
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataClick('OW');
                        ezuiCustDataDialogSearch();
                    }
                }]
            });
//查询控件初始化
            $("#ezuiForm #customerid").textbox({
                // editable:false,
                icons:[{
                    iconCls:'icon-search',
                    handler: function(e){
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataDialogClick('OW');
                        ezuiCustDataDialogSearch();
                    }
                }]
            });
//查询控件初始化
            $("#supplieridName").textbox({
                icons:[{
                    iconCls:'icon-search',
                    handler: function(e){
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataClick('VE');
                        ezuiCustDataDialogSearch();
                    }
                }]
            });
//查询控件初始化
            $("#ezuiForm #supplierName").textbox({
                icons:[{
                    iconCls:'icon-search',
                    handler: function(e){
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataDialogClick('VE');
                        ezuiCustDataDialogSearch();
                    }
                }]
            });
//查询控件初始化
            $("#ezuiDetailsForm #customerid").textbox({
                icons:[{
                    iconCls:'icon-search',
                    handler: function(e){
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataDetilDialogClick('OW');
                        ezuiCustDataDialogSearch();
                    }
                }]
            });
//查询控件初始化 载入公用弹窗页面
            $("#sku").textbox({
                icons:[{
                    iconCls:'icon-search',
                    handler: function(e){
                        productDialog_docPoHeader = $('#ezuiSkuSearchDialog').dialog({
                            modal : true,
                            title : '查询',
                            href:sy.bp()+"/basSkuController.do?toSearchDialog&target=docPoHeader",
                            width:850,
                            height:500,
                            cache:false,
                            onClose : function() {

                            }
                        })
                    }
                }]
            });
//时间控件初始化
            $('#addtime').datetimebox('calendar').calendar({
                validator: function(date){
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $('#pocreationtime').datetimebox('calendar').calendar({
                validator: function(date){
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $('#expectedarrivetime1').datetimebox('calendar').calendar({
                validator: function(date){
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $('#expectedarrivetime2').datetimebox('calendar').calendar({
                validator: function(date){
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
//二级dialog
            ezuiDialog = $('#ezuiDialog').dialog({
                modal: true,
                left:0,
                top:0,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiForm);
                }
            }).dialog('close');
//三级dialog
            ezuiDetailsDialog = $('#ezuiDetailsDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDetailsDialogBtn',
                onClose: function () {
                    detailObjs = [];
                    ezuiFormClear(ezuiDetailsForm);
                }
            }).dialog('close');
//货主代码选择弹框
            ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
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
                modal : true,
                title : '<spring:message code="common.dialog.title"/>',
                buttons : '',
                onOpen : function() {

                },
                onClose : function() {

                }
            }).dialog('close');
        });
//主页新增按钮
        var add = function () {
            processType = 'add';
            $('#docPoHeaderId').val(0);
//时间控件初始化
            $("#ezuiForm #pocreationtime").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
//到货时间控件初始化
            $("#ezuiForm #expectedarrivetime1").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear() + 1, now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiForm #pocreationtime").textbox({
                editable: false, readonly: true
            });
//初始化ezuiDetailsDatagrid
            $('#ezuiDetailsDatagrid').datagrid('load', {pono: '-1'});
            $("#ezuiForm #pono").textbox({
                editable:false
            });

            // $("#ezuiForm #asnno1").textbox({
            //     editable:false
            // });
            $("#ezuiForm #postatus").combobox('setValue', '00').combo('readonly', true);
            $("#ezuiForm #potype").combobox('setValue', 'CM');
            //
            $('#ezuiBtn_renew').linkbutton('disable');
            $('#ezuiBtn_recommit').linkbutton('enable');
            ezuiDialog.dialog('open');
        };
//主页编辑按钮
        var edit = function () {
            processType = 'edit';
            //时间控件初始化
            $("#ezuiForm #pocreationtime").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiForm #expectedarrivetime1").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiForm #pocreationtime").textbox({
                editable: false, readonly: true
            });
            $("#ezuiForm #expectedarrivetime1").textbox({
                editable: false, readonly: false
            });
            $("#ezuiForm #pono").textbox({
                editable: false
            });
            $("#ezuiForm #postatus").combobox('readonly', true);
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                ezuiForm.form('load', {
                    pono: row.pono,
                    potype: row.potype,
                    postatus: row.postatus,
                    supplierid: row.supplierid,
                    supplierName: row.supplierName,
                    supplierContact: row.supplierContact,
                    notes: row.notes,
                    customerid: row.customerid,
                    pocreationtime: row.pocreationtime,
                    addwho: row.addwho,
                    edittime: row.edittime,
                    editwho: row.editwho,
                    warehouseid: row.warehouseid,
                    expectedarrivetime1: row.expectedarrivetime1,
                });
                ezuiDetailsDatagrid.datagrid('load', {pono: row.pono});
                $('#ezuiDetailsDatagrid').parent().parent().parent().show();
                $('#ezuiBtn_renew').linkbutton('disable');
                $('#ezuiBtn_recommit').linkbutton('enable');
                ezuiDialog.dialog('open');
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                })
            }
        };
//主页取消按钮
        var cancel = function () {
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确认取消？', function (confirm) {
                    if (confirm) {
                        $.ajax({
                            url: 'docPoHeaderController.do?cancel',
                            data: {id: row.pono},
                            type: 'POST',
                            dataType: 'JSON',
                            success: function (result) {
                                var msg = '';
                                try {
                                    msg = result.msg;
                                } catch (e) {
                                    msg = '取消异常';
                                } finally {
                                    $.messager.show({
                                        msg: msg, title: '<spring:message code="common.message.prompt"/>'
                                    });
                                    ezuiDatagrid.datagrid('reload');
                                }
                            }
                        });
                    }
                });
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
//明细新增按钮
        var detailsAdd = function () {
            if ($('#ezuiForm #pono').val() == '') {
                return;
            } else {
                processType = 'add';
                $('#docPoDetailsId').val(0);
                $("#ezuiDetailsForm #skudescrc").textbox({
                    // editable: false,
                    icons: [{
                        iconCls: 'icon-search',
                        handler: function (e) {
                            $("#ezuiSkuDataDialog #sku").textbox('clear');
                              ezuiSkuDataClick();
                              ezuiSkuDataSearch();
                        }
                    }]
                });
                // $("#ezuiDetailsForm #location").textbox({
                //     editable:false,
                //     icons:[{
                //         iconCls:'icon-search',
                //         handler: function(e){
                //             $("#ezuiLocDataDialog #locationid").textbox('clear');
                //             ezuiLocDataClick();
                //             ezuiLocDataSearch();
                //         }
                //     }]
                // });
                $('#ezuiDetailsForm #customerid').textbox('setValue', $('#ezuiForm #customerid').val());//？？？
                $('#ezuiDetailsForm #pono').textbox('setValue', $('#ezuiForm #pono').val());
                $("#ezuiDetailsForm #polinestatus").combobox('setValue','00').combo('readonly', true);

                $("#ezuiDetailsForm #totalgrossweight").numberbox("setValue", '0.000');
                $("#ezuiDetailsForm #totalcubic").numberbox("setValue", '0.000');
                $("#ezuiDetailsForm #totalnetweight").numberbox("setValue", '0.000');
                ezuiDetailsDialog.dialog('open');
            }
            ;
        };
//明细编辑按钮
        var detailsEdit = function () {
            processType = 'edit';
            //时间控件初始化
            $("#ezuiDetailsForm #addtime").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiDetailsForm #edittime").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiDetailsForm #addtime").textbox({
                editable: false, readonly: true
            });
            $("#ezuiDetailsForm #edittime").textbox({
                editable: false, readonly: true
            });
            $('#docPoDetailsId').val(0);
            $("#ezuiDetailsForm #skudescrc").textbox({
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
            // $("#ezuiDetailsForm #location").textbox({
            //     editable:false,
            //     icons:[{
            //         iconCls:'icon-search',
            //         handler: function(e){
            //             $("#ezuiLocDataDialog #locationid").textbox('clear');
            //             ezuiLocDataClick();
            //             ezuiLocDataSearch();
            //         }
            //     }]
            // });
            $("#ezuiDetailsForm #polinestatus").combo('readonly', true);
            var row = ezuiDetailsDatagrid.datagrid('getSelected');
            //取打开弹窗单号和点击datagrid单号对比
            var  newpono=$("#ezuiForm #pono").val();
            if (row.pono==newpono) {
                ezuiDetailsForm.form('load', {
                    pono: row.pono,
                    customerid: row.customerid,
                    polineno: row.polineno,
                    polinestatus: row.polinestatus,
                    orderedqty: row.orderedqty,
                    sku: row.sku,
                    skudescrc:row.skudescrc,
                    uom: row.uom,
                    packid: row.packid,
                    totalcubic: row.totalcubic,
                    totalnetweight: row.totalnetweight,
                    totalgrossweight: row.totalgrossweight,
                    addtime:row.addtime,
                    addwho:row.addwho,
                    edittime:row.edittime,
                    editwho:row.editwho,
                    notes:row.notes


                });
                if (row.polinestatus == '00') {
                    $('#ezuiBtn_detailsCommit').linkbutton("enable");
                } else {
                    $('#ezuiBtn_detailsCommit').linkbutton("disable");
                }
                ezuiDetailsDialog.dialog('open');
            } else {
                $.messager.show({
                    msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
                });
            }
        };

//明细删除按钮
        var detailsDel = function () {
            var row = ezuiDetailsDatagrid.datagrid('getSelected');
            if (row) {
                $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function (confirm) {
                    if (confirm) {
                        $.ajax({
                            url: 'docPoDetailsController.do?delete',
                            data: {pono: row.pono, polineno: row.polineno},
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
                            }
                        });
                    }
                });
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
// 二级ezuiDialog新增按钮
        var renew = function () {
            processType = 'add';
            $('#docPoHeaderId').val(0);
            $('#ezuiForm').form('clear');
            //时间控件初始化
            $("#ezuiForm #pocreationtime").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiForm #expectedarrivetime1").datetimebox('calendar').calendar({
                validator: function (date) {
                    var now = new Date();
                    var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
                    return date <= validateDate;
                }
            });
            $("#ezuiForm #pocreationtime").textbox({
                editable: false, readonly: true
            });

            $('#ezuiDetailsDatagrid').datagrid('load', {pono: '-1'});
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
            // $("#ezuiForm #pono").textbox({
            //     editable:false
            // });
            $("#ezuiForm #postatus").combobox('setValue', '00').combo('readonly', true);
            $("#ezuiForm #potype").combobox('setValue', 'CM');
            $('#ezuiBtn_renew').linkbutton('disable');
            $('#ezuiBtn_recommit').linkbutton('enable');
        };
// 二级ezuiDialog提交
        var commit = function () {
            var postatus = $("#ezuiDialog #postatus").combobox('getValue');
            if (postatus == '00' || postatus == '30' || postatus == '40') {
                var url = '';
                if (processType == 'edit') {
                    url = '<c:url value="/docPoHeaderController.do?edit"/>';
                } else {
                    url = '<c:url value="/docPoHeaderController.do?add"/>';
                }
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
                                if (processType == 'edit') {

                                } else {
                                    $("#ezuiForm #customerid").textbox({
                                         editable:false,
                                        icons:[]
                                     });
                                    $('#ezuiForm #pono').textbox('setValue',result.msg);
                                }
                                ezuiDatagrid.datagrid('reload');
                                msg = '<font color="red">' + '提交成功' + '</font>';
                                $('#ezuiBtn_renew').linkbutton('enable');
                                $('#ezuiBtn_recommit').linkbutton('disable');
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
            } else {
                $.messager.show({
                    msg: '订单已经关闭或取消，不能提交', title: '<spring:message code="common.message.prompt"/>'
                });
            }
            ;

        };
// 明细提交按钮
        var detailsCommit = function () {
            var postatus = $("#ezuiDetailsDialog #polinestatus").combobox('getValue');
            if (postatus == '00' || postatus == '30' || postatus == '40') {
                var url = '';
                if (processType == 'edit') {
                    url = '<c:url value="/docPoDetailsController.do?edit"/>';
                } else {
                    url = '<c:url value="/docPoDetailsController.do?add"/>';
                }
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
                    },
                    success: function (data) {
                        var msg = '';
                        try {
                            var result = $.parseJSON(data);
                            if (result.success) {
                                if (processType == 'edit') {
                                    ezuiDetailsDatagrid.datagrid('reload',{pono: $('#ezuiForm #pono').val()});
                                } else {
                                    ezuiDetailsDatagrid.datagrid('reload',{pono: $('#ezuiForm #pono').val()});
                                }
                                ezuiDetailsDialog.dialog('close');
                                msg = '<font color="red">' + "提交成功" + '</font>';
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
            } else {
                $.messager.show({
                    msg: '订单已经关闭或取消，不能提交', title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
//主页datagrid-toolbar条件查询
        var doSearch = function () {
            ezuiDatagrid.datagrid('load', {
                pono: $('#pono').val(),
                potype: $('#potype').combobox("getValue"),
                postatus: $('#postatus').combobox("getValue"),
                pocreationtime: $('#pocreationtime').datebox("getValue"),
                expectedarrivetime1: $('#expectedarrivetime1').datebox("getValue"),
                expectedarrivetime2: $('#expectedarrivetime2').datebox("getValue"),
                poreference1: $('#poreference1').val(),
                poreference2: $('#poreference2').val(),
                poreference3: $('#poreference3').val(),
                poreference4: $('#poreference4').val(),
                poreference5: $('#poreference5').val(),
                supplierid: $('#supplierid').val(),
                supplierName: $('#supplierName').val(),
                supplierAddress1: $('#supplierAddress1').val(),
                supplierAddress2: $('#supplierAddress2').val(),
                supplierAddress3: $('#supplierAddress3').val(),
                supplierAddress4: $('#supplierAddress4').val(),
                supplierCity: $('#supplierCity').val(),
                supplierProvince: $('#supplierProvince').val(),
                supplierCountry: $('#supplierCountry').val(),
                supplierZip: $('#supplierZip').val(),
                customerid: $('#customerid').val(),
                edisendflag: $('#edisendflag').val(),
                supplierContact: $('#supplierContact').val(),
                supplierMail: $('#supplierMail').val(),
                supplierFax: $('#supplierFax').val(),
                supplierTel1: $('#supplierTel1').val(),
                supplierTel2: $('#supplierTel2').val(),
                userdefine1: $('#userdefine1').val(),
                userdefine2: $('#userdefine2').val(),
                userdefine3: $('#userdefine3').val(),
                userdefine4: $('#userdefine4').val(),
                userdefine5: $('#userdefine5').val(),
                notes: $('#notes').val(),
                addtime: $('#addtime').datebox("getValue"),
                addwho: $('#addwho').val(),
                edittime: $('#edittime').val(),
                editwho: $('#editwho').val(),
                edisendtime: $('#edisendtime').val(),
                hedi01: $('#hedi01').val(),
                hedi02: $('#hedi02').val(),
                hedi03: $('#hedi03').val(),
                hedi04: $('#hedi04').val(),
                hedi05: $('#hedi05').val(),
                hedi06: $('#hedi06').val(),
                hedi07: $('#hedi07').val(),
                hedi08: $('#hedi08').val(),
                hedi09: $('#hedi09').val(),
                hedi10: $('#hedi10').val(),
                warehouseid: $('#warehouseid').combobox("getValue"),
                createsource: $('#createsource').val(),
                releasestatus: $('#releasestatus').val(),
                userdefinea: $('#userdefinea').val(),
                userdefineb: $('#userdefineb').val(),
                sku:$('#sku').val()
            });

        };
// 货主代码选择弹框清空
        var ezuiCustToolbarClear = function () {
            $("#ezuiCustDataDialog #customerid").textbox('clear');
        };
// 货主代码选择弹框-主界面
        var ezuiCustDataClick = function (customerType)     {
            $("#ezuiCustDataDialog #customerType").combobox('setValue',customerType).combo('readonly',true);
            $("#ezuiCustDataDialog #activeFlag").combobox('setValue', '1').combo('readonly',true);
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
                    {field: 'customerid', title: '客户代码', width: 50},
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
// 货主代码选择弹框-二级diayog
        var ezuiCustDataDialogClick = function (customerType) {
            $("#ezuiCustDataDialog #customerType").combobox('setValue', customerType).combo('readonly', true);
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
                    {field: 'customerid', title: '客户代码', width: 50},
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
// 货主代码选择弹框-三级diayog
        var ezuiCustDataDetilDialogClick = function (customerType) {
            $("#ezuiCustDataDialog #customerType").combobox('setValue', customerType).combo('readonly', true);
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
                    {field: 'customerid', title: '客户代码', width: 50},
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
                    selectDetilDialogCust();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                }, onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });
            ezuiCustDataDialog.dialog('open');
        };
// 货主编代码择弹框查询
        var ezuiCustDataDialogSearch = function () {
            ezuiCustDataDialogId.datagrid('load', {
                customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
                customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
                activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
            });
        };
// 货主代码选择-主界面
        var selectCust = function () {
            var row = ezuiCustDataDialogId.datagrid('getSelected');
            if(row.customerType == "OW"){
                $("#customerid").textbox('setValue',row.customerid);
            }else if(row.customerType == "VE"){
                $("#supplierid").val(row.customerid);
                $("#supplieridName").textbox('setValue',row.descrC);

            }
            ezuiCustDataDialog.dialog('close');
        };
// 货主代码选择-详细页面之后关闭
        var selectDialogCust = function () {
            var row = ezuiCustDataDialogId.datagrid('getSelected');
            if(row.customerType == "OW"){
                    $("#ezuiDialog #customerid").textbox('setValue',row.customerid);
                }else if(row.customerType == "VE"){
                    $("#ezuiDialog #supplierid").val(row.customerid);
                    $("#ezuiDialog #supplierName").textbox('setValue',row.descrC);
                }
                ezuiCustDataDialog.dialog('close');

        };
// 货主代码选择-三级diayog页面之后关闭
        var selectDetilDialogCust= function () {
            var row = ezuiCustDataDialogId.datagrid('getSelected');
            if (row) {
                $("#ezuiDetailsForm #customerid").textbox('setValue', row.customerid);

        }
            ezuiCustDataDialog.dialog('close');

        };

// 商品选择弹框-三级diayog
        var ezuiSkuDataClick = function(){
            $("#ezuiSkuDataDialog #customerid").textbox('setValue',$("#ezuiDetailsForm #customerid").textbox("getValue")).textbox('readonly', true);
            $("#ezuiSkuDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
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
                    {field: 'sku',			title: '产品代码',	width: 120},
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
                },onLoadSuccess:function(data){
                    $(this).datagrid('unselectAll');
                }
            });

            ezuiSkuDataDialog.dialog('open');
        };
// 商品选择弹框查询-三级diayog
        var ezuiSkuDataSearch = function(){
            ezuiSkuDataDialogId.datagrid('load', {
                customerid : $("#ezuiSkuDataDialog #customerid").textbox("getValue"),
                sku : $("#ezuiSkuDataDialog #sku").textbox("getValue"),
                activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
            });
        };
// 商品选择弹框清空
        var ezuiSkuToolbarClear = function(){
            $("#ezuiSkuDataDialog #sku").textbox('clear');
        };
// 商品选择-主页
        var selectSku = function(){
            var row = ezuiSkuDataDialogId.datagrid('getSelected');
            if(row){
                $("#ezuiDetailsForm #sku").val(row.sku);
                $("#ezuiDetailsForm #skudescrc").textbox('setValue',row.descrC);
                $("#ezuiDetailsForm #packid").textbox('setValue',row.packid);
                $("#ezuiDetailsForm #expectedqty").numberbox('clear');
                $("#ezuiDetailsForm #totalgrossweight").numberbox('clear');
                $("#ezuiDetailsForm #totalcubic").numberbox('clear');
                $("#ezuiDetailsForm #totalprice").numberbox('clear');
                ezuiSkuDataDialog.dialog('close');
            }
        };
// 主页产品框选择
        function choseSelect_product_docPoHeader(row){
            var sku;
            if(row){
                sku = row;
            }else{
                row = $("#productSearchGrid_docPoHeader").datagrid("getSelections");
                if(row){
                    sku = row[0]
                }
            }
            if(sku){
                $("#sku").textbox("setValue",sku.sku);
            }
            productDialog_docPoHeader.dialog("close");
        }
// 一级datagrid单击事件
        function afterCheckButtion(rowData) {
            if (rowData.postatus == '99' || rowData.postatus == '90') {
                $('#ezuiBtn_close').linkbutton('disable');
                $('#ezuiBtn_cancel').linkbutton('disable');
                $('#ezuiDetailsBtn_add').linkbutton('disable');
                $('#ezuiDetailsBtn_edit').linkbutton('disable');
                $('#ezuiDetailsBtn_del').linkbutton('disable');
                $('#ezuiDetailsBtn_receive').linkbutton('disable');
            } else if (rowData.postatus == '40' || rowData.postatus == '30') {
                $('#ezuiBtn_close').linkbutton('enable');
                $('#ezuiBtn_cancel').linkbutton('disable');
                $('#ezuiDetailsBtn_add').linkbutton('enable');
                $('#ezuiDetailsBtn_edit').linkbutton('disable');
                $('#ezuiDetailsBtn_del').linkbutton('disable');
                $('#ezuiDetailsBtn_receive').linkbutton('disable');
            } else if (rowData.postatus == '00') {
                $('#ezuiBtn_close').linkbutton('disable');
                $('#ezuiBtn_cancel').linkbutton('enable');
                $('#ezuiDetailsBtn_add').linkbutton('enable');
                $('#ezuiDetailsBtn_edit').linkbutton('enable');
                $('#ezuiDetailsBtn_del').linkbutton('enable');
                $('#ezuiDetailsBtn_receive').linkbutton('enable');
            } else {

            }
        }
    </script>
</head>
<body>
<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<div class='easyui-layout' data-options='fit:true,border:false'>
<%--主页datagrid-toolbar--%>
    <div data-options='region:"center",border:false' style='overflow: hidden;'>

        <div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
            <fieldset>
                <legend><spring:message code='common.button.query'/></legend>
                <table>
                    <tr>
                        <th>采购单号</th>
                        <td><input type='text' id='pono' class='easyui-textbox' size='16' data-options=""/></td>
                        <th>采购类型</th>
                        <td><input id='potype' name="potype" class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docPoHeaderController.do?getPotypeCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"
                        /></td>

                        <th>采购状态</th>
                        <td><input type='text' id='postatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docPoHeaderController.do?getPostatusCombobox"/>',
																																	valueField: 'id',
																																     textField: 'value'"/>
                        </td>

                    </tr>
                    <tr>

                        <th>预计到货时间</th>
                        <td><input type='text' id='expectedarrivetime1' class='easyui-datetimebox' size='16'
                                   data-options=''/></td>
                        <th>至</th>
                        <td><input type='text' id='expectedarrivetime2' class='easyui-datetimebox' size='16'
                                   data-options=''/></td>


                        <th>供应商</th>
                        <td><input type='hidden' id='supplierid'  name='supplierid' data-options=''/>
                           <input type='text' id='supplieridName' class='easyui-textbox' size='16' data-options=''/></td>

                    </tr>
                    <tr>


                        <th>创建时间</th>
                        <td><input type='text' id='pocreationtime' class='easyui-datetimebox' size='16'
                                   data-options=''/></td>
                        <th>至</th>
                        <td><input type='text' id='addtime' class='easyui-datetimebox' size='16'
                                   data-options=''/></td>
                        <th>货主代码</th>
                        <td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>


                    </tr>
                    <tr>
                        <th>仓库</th>
                        <td><input type='text' id='warehouseid' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docPoHeaderController.do?getWarehouseCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/>
                        </td>
                        <th>产品代码</th>
                        <td><input type='text' id='sku'  name='sku' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>客户单号</th>
                        <td><input type='text' id='poreference1'  name='sku' class='easyui-textbox' size='16' data-options=''/></td>
                        <td>
                            <%--一级datagrid查询--%>
                            <a onclick='doSearch();' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                            <a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-remove"'
                               href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div>
                <a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message
                        code='common.button.add'/></a>
                <a onclick='cancel();' id='ezuiBtn_cancel' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>取消</a>
                <a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message
                        code='common.button.edit'/></a>
                <a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                        code='common.button.cancelSelect'/></a>
            </div>
        </div>
        <%--主页采购订单datagrid--%>
        <table id='ezuiDatagrid'></table>
    </div>
</div>
<div id='ezuiDialogBtn'>
    <%--<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message--%>
            <%--code='common.button.commit'/></a>--%>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.close'/></a>
</div>
<%--右键菜单--%>
<div id='ezuiMenu' class='easyui-menu' style='width:75px; display:none;'>
    <div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message
            code='common.button.add'/></div>
    <div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message
            code='common.button.edit'/></div>
</div>
<%--二级右键菜单--%>
<div id='ezuiDetailsMenu' class='easyui-menu' style='width:120px;display: none;'>
<div onclick='detailsAdd();' id='menu_add2' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.skuAdd'/></div>
<div onclick='detailsDel();' id='menu_del2' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.skuDelete'/></div>
<div onclick='detailsEdit();' id='menu_edit2' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.skuEdit'/></div>
</div>
<c:import url='/WEB-INF/jsp/docPoHeader/dialog.jsp'/>
<c:import url='/WEB-INF/jsp/docPoHeader/custDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docPoHeader/detailsDialog.jsp'/>
<c:import url='/WEB-INF/jsp/docPoHeader/skuDialog.jsp' />
<!--产品查询 -->
<div id="ezuiSkuSearchDialog">

</div>
</body>
</html>
