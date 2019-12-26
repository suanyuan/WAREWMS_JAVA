<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<!DOCTYPE html>
<html>
<head>
    <style>
        table th{
            text-align: right;
        }
    </style>
    <c:import url='/WEB-INF/jsp/include/meta.jsp'/>
    <c:import url='/WEB-INF/jsp/include/easyui.jsp'/>
    <script type='text/javascript'>
        var processType;
        var ezuiMenu;
        var ezuiForm;
        var ezuiDialog;
        var ezuiDatagrid;
        var ezuiDatagridAll;    //隐藏的datagrid  用于总计的数据获取

        var ezuiCustDataDialog;        //货主编码
        var ezuiCustDataDialogId;      //货主编码
        var ezuiLocDataDialog;         //库位选择框
        var ezuiLocDataDialogId;       //库位选择框
        var ezuiSkuDataDialog;         //产品名称选择框
        var ezuiSkuDataDialogId;       //产品名称选择框
        var productDialog_viewInvLocation;//主页产品代码选择框

        var getTotalInit; //初始化合计
        var getTotalAllInit; //初始化总计

        $(function () {
            ezuiMenu = $('#ezuiMenu').menu();
            ezuiForm = $('#ezuiForm').form();
            //主页datagrid
            ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                url: '<c:url value="/viewInvLocationController.do?showDatagrid"/>',
                method: 'POST',
                toolbar: '#toolbar',
                title: '库存余量_按产品/库位',
                pageSize:50,
                pageList: [50, 100, 200],
                fit: true,
                border: false,
                fitColumns: false,
                nowrap: true,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                showFooter:true,
                idField: 'customerid',
                columns: [[
                    {field: 'fmcustomerid', title: '货主', width: 100},
                    {field: 'lotatt14', title: '入库单号', width:150},
                    {field: 'fmlocation', title: '库位', width: 100},
                    {field: 'fmsku', title: '产品代码', width: 100},
                    {field: 'lotatt04', title: '生产批号', width: 95},
                    {field: 'lotatt05', title: '序列号', width: 110},
                    {field: 'fmqty', title: '库存件数', width: 100},
                    {field: 'fmqtyEach', title: '库存数量', width: 100},
                    {field: 'qtyallocated', title: '分配件数', width: 100},
                    {field: 'qtyallocatedEach', title: '分配数量', width: 100},
                    {field: 'qtyavailed', title: '可用件数', width: 100},
                    {field: 'qtyavailedEach', title: '可用数量', width: 100},
                    {field: 'lotatt01', title: '生产日期', width: 112},
                    {field: 'lotatt02', title: '有效期/失效期', width: 113},
                    {field: 'lotatt03', title: '入库日期', width: 91},
                    {field: 'lotatt12', title: '产品名称', width: 250},
                    {field: 'skudescre', title: '规格型号', width: 150},
                    {field: 'name', title: '产品线', width: 100},
                    {field: 'lotatt06', title: '注册证号', width: 170},//加个字段
                    {field: 'qtyholded', title: '冻结件数', width: 100,hidden:true},
                    {field: 'qtyholdedEach', title: '冻结数量', width: 100,hidden:true},
                    {field: 'defaultreceivinguom', title: '单位', width: 100,formatter:unitFormatter},
                    {field: 'reservedfield02', title: '产品描述', width: 200},
                    {field: 'lotatt09', title: '样品属性', width: 100,formatter:YP_TYPstatusFormatter},
                    {field: 'lotatt10', title: '质量状态', width: 100, formatter: ZL_TYPstatusFormatter},
                    {field: 'lotatt11', title: '存储条件', width: 150},
                    // {field: 'lotatt07', title: '灭菌批号', width: 120},
                    // {field: 'lotatt08', title: '供应商', width: 120},
                    {field: 'lotatt13', title: '双证', width: 100,formatter:Asn_DoublecstatusFormatter},
                    // {field: 'enterpriseName', title: '生产厂家', width: 100},
                    // {field: 'lotatt10',		title: '备注',	width: 71 },
                ]]
                ,onDblClickCell: function(index,field,value){
                    edit();
                },
                onLoadSuccess: function (data) {
                    ajaxBtn($('#menuId').val(), '<c:url value="/viewInvLocationController.do?getBtn"/>', ezuiMenu);
                    getTotalInit=getTotal();
                    ezuiDatagrid.datagrid('reloadFooter',[
                        {lotatt05:'合计:',fmqty:getTotalInit.fmqty,fmqtyEach:getTotalInit.fmqtyEach,qtyallocated:getTotalInit.qtyallocated,
                            qtyallocatedEach:getTotalInit.qtyallocatedEach,qtyavailed:getTotalInit.qtyavailed,qtyavailedEach:getTotalInit.qtyavailedEach,qtyholded:getTotalInit.qtyholded,
                            qtyholdedEach:getTotalInit.qtyholdedEach},
                        {lotatt05:'总计:',fmqty:data.rows[0].fmqtySum,fmqtyEach:data.rows[0].fmqtyEachSum,
                            qtyallocated:data.rows[0].qtyallocatedSum,qtyallocatedEach:data.rows[0].qtyallocatedEachSum,
                            qtyavailed:data.rows[0].qtyavailedSum,qtyavailedEach:data.rows[0].qtyavailedEachSum,
                            qtyholded:0, qtyholdedEach:0}
                    ]);
                }
                // onDblClickCell: function(index,field,value){
                // 	edit();
                // },
// 		onRowContextMenu : function(event, rowIndex, rowData) {
// 			event.preventDefault();
// 			$(this).datagrid('unselectAll');
// 			$(this).datagrid('selectRow', rowIndex);
// 			ezuiMenu.menu('show', {
// 				left : event.pageX,
// 				top : event.pageY
// 			});
// 		},onLoadSuccess:function(data){
// 			ajaxBtn($('#menuId').val(), '<c:url value="/viewInvLocationController.do?getBtn"/>', ezuiMenu);
// 			$(this).datagrid('unselectAll');
// 		}
            });


            //产品代码控件初始化 载入公用弹窗页面
            $("#fmsku").textbox({
                icons: [{
                    iconCls: 'icon-search',
                    handler: function (e) {
                        productDialog_viewInvLocation = $('#ezuiSkuSearchDialog').dialog({
                            modal: true,
                            title: '查询',
                            href: sy.bp() + "/basSkuController.do?toSearchDialog&target=viewInvLocation",
                            width: 850,
                            height: 500,
                            cache: false,
                            onClose: function () {

                            }
                        })
                    }
                }]
            });
            //库位
            $("#fmlocation").textbox({
                icons: [{
                    iconCls: 'icon-search',
                    handler: function (e) {
                        $("#ezuiLocDataDialog #locationid").textbox('clear');
                        ezuiLocDataClick();
                        ezuiLocDataSearch();
                    }
                }]
            });
            //主页产品名称查询
            $("#skudescrc").textbox({
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
            //查询条件货主字段初始化
            $("#fmcustomerid").textbox({
                icons: [{
                    iconCls: 'icon-search',
                    handler: function (e) {
                        $("#ezuiCustDataDialog #customerid").textbox('clear');
                        ezuiCustDataClick();
                        ezuiCustDataDialogSearch();
                    }
                }]
            });

            //货主带出产品线
            $("#toolbar #fmcustomerid").textbox({
                onChange:function(){
                    var customerid = $("#toolbar #fmcustomerid").textbox('getValue');
                    if(customerid !=null && ($.trim(customerid).length>0)){
                        $("#toolbar #name").combobox({
                            panelHeight: 'auto',
                            url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid,
                            valueField:'id',
                            textField:'value',
                            onLoadSuccess:function () {
                            }
                        });
                    }else{
                        $("#toolbar #name").combobox({
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
            ezuiDialog = $('#ezuiDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiForm);
                }
            }).dialog('close');
        });

        var edit = function () {
            processType = 'edit';
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                ezuiForm.form('load', {
                    fmlocation: row.fmlocation,
                    customerid: row.customerid,
                    fmsku: row.fmsku,
                    fmqty: row.fmqty,
                    fmqtyEach: row.fmqtyEach,
                    qtyallocated: row.qtyallocated,
                    qtyallocatedEach: row.qtyallocatedEach,
                    qtyavailed: row.qtyavailed,
                    qtyholded: row.qtyholded,
                    qtyholdedEach: row.qtyholdedEach,
                    defaultreceivinguom: row.defaultreceivinguom,
                    skudescre: row.skudescre,
                    lotatt14: row.lotatt14,
                    lotatt03: row.lotatt03,
                    lotatt01: row.lotatt01,
                    lotatt02: row.lotatt02,
                    lotatt04: row.lotatt04,
                    lotatt05: row.lotatt05,
                    lotatt10: row.lotatt10,
                    lotatt07: row.lotatt07,
                    lotatt11: row.lotatt11,
                    lotatt08: row.lotatt08,
                    lotatt09: row.lotatt09,
                    lotatt12: row.lotatt12,
                    lotatt06: row.lotatt06,
                    name: row.name,
                    enterpriseName: row.enterpriseName,
                    warehouseid: row.warehouseid
                });
                ezuiDialog.dialog('open');
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };


        //货主查询弹框弹出start=========================
        var ezuiCustDataClick = function () {
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
                    selectCust();
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
        var selectCust = function () {
            processType = 'selectCust';
            var row = ezuiCustDataDialogId.datagrid('getSelected');
            if (row) {
                $("#fmcustomerid").textbox('setValue', row.customerid);
                ezuiCustDataDialog.dialog('close');
            }
        };
        //货主查询弹框清空按钮
        var ezuiCustToolbarClear = function () {
            $("#ezuiCustDataDialog #customerid").textbox('clear');
        };
        //货主查询弹框弹出end==========================

        /* 导出start */
        var doExport = function () {
            if (navigator.cookieEnabled) {
                $('#ezuiBtn_export').linkbutton('disable');
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("token", token);

                param.put("fmcustomerid", $('#fmcustomerid').val());//货主编码
                param.put("fmlocation", $('#fmlocation').val());//库位
                param.put("fmsku", $('#fmsku').val());//产品代码
                param.put("lotatt12", $('#skudescrc').val());//产品名称
                param.put("name", $('#name').combobox('getText'));//产品线
                param.put("lotatt04", $('#lotatt04').val());//批号
                param.put("lotatt05", $('#lotatt05').val());//序列号
                param.put("lotatt14", $('#lotatt14').val());//入库单号
                param.put("lotatt02Start",$('#lotatt02Start').datebox('getValue'));//时间查询
                param.put("lotatt02End",$('#lotatt02End').datebox('getValue'));//时间查询
                param.put("lotatt03Start",$('#lotatt03Start').datebox('getValue'));//入库单号时间查询
                param.put("lotatt03End",$('#lotatt03End').datebox('getValue'));//入库单号时间查询
                param.put("lotatt10",$('#lotatt10').combobox('getValue'));//质量状态

                //--导出Excel
                var formId = ajaxDownloadFile(sy.bp() + "/viewInvLocationController.do?exportViewInvLocationDataToExcel", param);
                downloadCheckTimer = window.setInterval(function () {
                    window.clearInterval(downloadCheckTimer);
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
        /* 导出end */

        var doSearch = function () {
            //隐藏datagridAll加载  主页datagrid查询在datagridAll初始化中
            ezuiDatagrid.datagrid('load', {
                fmcustomerid: $('#fmcustomerid').val(),//货主编码
                fmlocation: $('#fmlocation').val(),  //库位
                fmsku: $('#fmsku').val(),           //产品代码
                lotatt12: $('#skudescrc').val(),  //产品名称
                name: $('#name').combobox('getText'),          //产品线
                lotatt04: $('#lotatt04').val(),//批号
                lotatt05: $('#lotatt05').val(),//序列号
                lotatt14: $('#lotatt14').val(),//入库单号
                lotatt02Start: $('#lotatt02Start').datebox('getValue'),//时间查询
                lotatt02End: $('#lotatt02End').datebox('getValue'),//时间查询
                lotatt03Start: $('#lotatt03Start').datebox('getValue'),//入库日期时间查询
                lotatt03End: $('#lotatt03End').datebox('getValue'),//入库日期时时间查询
                lotatt10: $('#lotatt10').combobox('getValue')
            });
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
                }, onLoadSuccess: function (data) {
                    $(this).datagrid('unselectAll');
                }
            });
            ezuiLocDataDialog.dialog('open');
        };
        /* 库位选择 */
        var selectLocation = function () {
            var row = ezuiLocDataDialogId.datagrid('getSelected');
            if (row) {
                $("#fmlocation").textbox('setValue', row.locationid);
                ezuiLocDataDialog.dialog('close');
            }
            ;
        };


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
                $("#skudescrc").textbox('setValue', row.reservedfield01);
                ezuiSkuDataDialog.dialog('close');
            }
        };

        // 主页产品代码框选择
        function choseSelect_product_viewInvLocation(row) {
            var fmsku;
            if (row) {
                fmsku = row;
            } else {
                row = $("#productSearchGrid_viewInvLocation").datagrid("getSelections");
                if (row) {
                    fmsku = row[0]
                }
            }
            if (fmsku) {
                $("#fmsku").textbox("setValue", fmsku.sku);
            }
            productDialog_viewInvLocation.dialog("close");
        }
        // 统计合计 start
        var getTotal = function(){
           var sums=new Object();
             var rows = ezuiDatagrid.datagrid('getRows');//获取当前页的数据行
            sums.fmqty = 0;
            for (var i = 0; i < rows.length; i++) {
                sums.fmqty  += rows[i]['fmqty']; //获取指定列
            }
            sums.fmqtyEach = 0;
            for (var i = 0; i < rows.length; i++) {
             sums.fmqtyEach += rows[i]['fmqtyEach']; //获取指定列
            }
            sums.qtyallocated= 0;
            for (var i = 0; i < rows.length; i++) {
                sums.qtyallocated+= rows[i]['qtyallocated']; //获取指定列
            }
            sums.qtyallocatedEach= 0;
            for (var i = 0; i < rows.length; i++) {
                sums.qtyallocatedEach+= rows[i]['qtyallocatedEach']; //获取指定列
            }
            sums.qtyholded = 0;
            for (var i = 0; i < rows.length; i++) {
                sums.qtyholded += rows[i]['qtyholded']; //获取指定列
            }
            sums.qtyholdedEach = 0;
            for (var i = 0; i < rows.length; i++) {
                sums.qtyholdedEach+= rows[i]['qtyholdedEach']; //获取指定列
            }
            sums.qtyavailed = 0;
            for (var i = 0; i < rows.length; i++) {
                sums.qtyavailed += rows[i]['qtyavailed']; //获取指定列
            }
            sums.qtyavailedEach = 0;
            for (var i = 0; i < rows.length; i++) {
                sums.qtyavailedEach += rows[i]['qtyavailedEach']; //获取指定列
            }
            return sums;
        }

        // 统计合计 end
        // 统计总计 start
        // var getTotalAll = function(){
        //    var sums=new Object();
        //      var rows = ezuiDatagridAll.datagrid('getRows');//获取当前页的数据行
        //     sums.fmqty = 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.fmqty  += rows[i]['fmqty']; //获取指定列
        //     }
        //     sums.fmqtyEach = 0;
        //     for (var i = 0; i < rows.length; i++) {
        //      sums.fmqtyEach += rows[i]['fmqtyEach']; //获取指定列
        //     }
        //     sums.qtyallocated= 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.qtyallocated+= rows[i]['qtyallocated']; //获取指定列
        //     }
        //     sums.qtyallocatedEach= 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.qtyallocatedEach+= rows[i]['qtyallocatedEach']; //获取指定列
        //     }
        //     sums.qtyholded = 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.qtyholded += rows[i]['qtyholded']; //获取指定列
        //     }
        //     sums.qtyholdedEach = 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.qtyholdedEach+= rows[i]['qtyholdedEach']; //获取指定列
        //     }
        //     sums.qtyavailed = 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.qtyavailed += rows[i]['qtyavailed']; //获取指定列
        //     }
        //     sums.qtyavailedEach = 0;
        //     for (var i = 0; i < rows.length; i++) {
        //         sums.qtyavailedEach += rows[i]['qtyavailedEach']; //获取指定列
        //     }
        //     return sums;
        // }

        // 统计总计 end

    </script>
</head>
<body>

<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<!--查询数据datagrid-->
<div class='easyui-layout' data-options='fit:true,border:false'>
    <div data-options='region:"center",border:false' style='overflow: hidden;'>
        <div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
            <fieldset>
                <legend><spring:message code='common.button.query'/></legend>
                <table>
                    <tr>
                        <th>货主编码</th>
                        <td><input type='text' id='fmcustomerid' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>库位</th>
                        <td><input type='text' id='fmlocation' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>产品代码</th>
                        <td><input type='text' id='fmsku' class='easyui-textbox' size='16' data-options=''/></td>

                    </tr>
                    <tr>
                        <th>产品线</th>
                        <td><input type='text' id='name' class='easyui-combobox' size='16' data-options="
																										url:'<c:url value="/productLineController.do?getCombobox"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
                        <th>产品名称</th>
                        <td><input type='text' id='skudescrc' class='easyui-textbox' size='16' data-options=''/></td>

                        <th>入库单号</th>
                        <td><input type='text' id='lotatt14' class='easyui-textbox' size='16' data-options=''/></td>


                    </tr>
                    <tr>
                        <th>序列号</th>
                        <td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>入库日期</th>
                        <td><input type='text' id='lotatt03Start' class='easyui-datebox' size='16' data-options=''/>
                        </td>
                        <th>至
                        </th>
                        <td><input type='text' id='lotatt03End' class='easyui-datebox' size='16' data-options=''/></td>
                    </tr>
                    <tr>
                        <th>生产批号</th>
                        <td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>效期</th>
                        <td><input type='text' id='lotatt02Start' class='easyui-datebox' size='16' data-options=''/>
                        </td>
                        <th>至
                        </th>
                        <td><input type='text' id='lotatt02End' class='easyui-datebox' size='16' data-options=''/></td>
                    </tr>
                    <tr>
                        <th>质量状态</th><td><input type='text' id='lotatt10' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?qcState"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
                        <td colspan="2">
                            <a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                            <a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-remove"'
                               href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                            <a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
            <div>
                <%-- 					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a> --%>
                <%-- 					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a> --%>
                <a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查看</a>
                <a onclick='clearDatagridSelected("#ezuiDatagrid");' id='ezuiBtn_cancelsel' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                        code='common.button.cancelSelect'/></a>
            </div>
        </div>
        <table id='ezuiDatagrid'></table>
    </div>
</div>

<!--查询所有数据datagrid隐藏-->
<table id='ezuiDatagridAll' hidden="true"></table>

<div id='ezuiDialog' style='padding: 10px;'>
    <form id='ezuiForm' method='post'>
        <input type='hidden' id='viewInvLocationId' name='viewInvLocationId'/>
        <table >
            <tr>
                <th>库位</th>
                <td><input type='text' name='fmlocation' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>货主</th>
                <td><input type='text' name='customerid' class='easyui-textbox' size='50' data-options='required:true'/></td>

            </tr>
            <tr>
                <th>产品代码</th>
                <td><input type='text' name='fmsku' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>产品名称</th>
                <td><input type='text' name='lotatt12' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>库存件数</th>
                <td><input type='text' name='fmqty' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>库存数量</th>
                <td><input type='text' name='fmqtyEach' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>分配件数</th>
                <td><input type='text' name='qtyallocated' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>分配数量</th>
                <td><input type='text' name='qtyallocatedEach' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>冻结件数</th>
                <td><input type='text' name='qtyholded' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>冻结数量</th>
                <td><input type='text' name='qtyholdedEach' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>可用件数</th>
                <td><input type='text' name='qtyavailed' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>单位</th>
                <td><input type='text' name='defaultreceivinguom' class='easyui-combobox' readonly="readonly" size='50' data-options="panelHeight: 'auto',
																																editable: false,
																																url:'<c:url value="/commonController.do?getUOM"/>',
																																valueField: 'id',
																																textField: 'value'"/></td>
            </tr>
            <tr>
                <th>注册证号/备案凭证号</th>
                <td><input type='text' name='lotatt06' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>规格型号</th>
                <td><input type='text' name='skudescre' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>生产批号</th>
                <td><input type='text' name='lotatt04' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>序列号</th>
                <td><input type='text' name='lotatt05' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>灭菌批号</th>
                <td><input type='text' name='lotatt07' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>入库日期</th>
                <td><input type='text' name='lotatt03' class='easyui-datebox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>生产日期</th>
                <td><input type='text' name='lotatt01' class='easyui-datebox' size='50' data-options='required:true'/></td>
                <th>有效期/失效期</th>
                <td><input type='text' name='lotatt02' class='easyui-datebox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>供应商</th>
                <td><input type='text' name='lotatt08' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>样品属性</th>
                <td><input type='text' name='lotatt09' class='easyui-combobox' size='50' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?sampleAttr"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
            </tr>
            <tr>
                <th>入库单号</th>
                <td><input type='text' name='lotatt14' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>存储条件</th>
                <td><input type='text' name='lotatt11' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>生产企业</th>
                <td><input type='text' name='enterpriseName' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>质量状态</th>
                <td><input type='text' name='lotatt10' class='easyui-combobox' size='50' data-options="panelHeight: 'auto',
																										editable: false,
																										url:'<c:url value="/commonController.do?qcState"/>',
																										valueField: 'id',
																										textField: 'value'"/></td>
            </tr>
            <tr>
                <th>产品线</th>
                <td><input type='text' name='name' class='easyui-textbox' size='50' data-options='required:true'/></td>

            </tr>
        </table>
    </form>
</div>
<div id='ezuiDialogBtn'>
    <%-- 		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.close'/></a>
</div>
<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
    <div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message
            code='common.button.add'/></div>
    <div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message
            code='common.button.delete'/></div>
    <div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message
            code='common.button.edit'/></div>
</div>


<!-- 客户选择弹框 -->
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
<%--导入页面--%>
<c:import url='/WEB-INF/jsp/viewInvLocation/locDialog.jsp'/>
<c:import url='/WEB-INF/jsp/viewInvLocation/skuDialog.jsp'/>

<!--产品代码查询弹窗 -->
<div id="ezuiSkuSearchDialog">

</div>

</body>
</html>
