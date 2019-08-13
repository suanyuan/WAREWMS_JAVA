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
        var ezuiDatagridAll;

        var ezuiCustDataDialog;        //货主编码
        var ezuiCustDataDialogId;      //货主编码
        var ezuiLocDataDialog;         //库位选择框
        var ezuiLocDataDialogId;       //库位选择框
        var ezuiSkuDataDialog;         //产品名称选择框
        var ezuiSkuDataDialogId;       //产品名称选择框
        var productDialog_viewInvLocation;//主页产品代码选择框

        $(function () {
            ezuiMenu = $('#ezuiMenu').menu();
            ezuiForm = $('#ezuiForm').form();
            ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                url: '<c:url value="/viewInvLocationController.do?showDatagrid"/>',
                method: 'POST',
                toolbar: '#toolbar',
                title: '库存余量_按商品/库位',
                pageSize:5,
                pageList: [5, 100, 200],
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
                    {field: 'fmlocation', title: '库位', width: 100},
                    {field: 'customerid', title: '货主', width: 71},
                    {field: 'fmsku', title: '产品代码', width: 100},
                    {field: 'lotatt12', title: '产品名称', width: 330},
                    {field: 'fmqty', title: '库存件数', width: 100},
                    {field: 'fmqtyEach', title: '库存数量', width: 100},

                    {field: 'qtyallocated', title: '分配件数', width: 100},
                    {field: 'qtyallocatedEach', title: '分配数量', width: 100},
                    {field: 'qtyavailed', title: '可用件数', width: 100},
                    {field: 'qtyholded', title: '冻结件数', width: 100},
                    {field: 'qtyholdedEach', title: '冻结数量', width: 100},
                    {field: 'defaultreceivinguom', title: '单位', width: 100},
                    {field: 'lotatt06', title: '注册证号/备案凭证号', width: 150},//加个字段
                    {field: 'skudescre', title: '规格型号', width: 103},
                    {field: 'lotatt04', title: '生产批号', width: 95},
                    {field: 'lotatt05', title: '序列号', width: 110},
                    {field: 'lotatt07', title: '灭菌批号', width: 120},
                    {field: 'lotatt03', title: '入库日期', width: 91},
                    {field: 'lotatt01', title: '生产日期', width: 112},
                    {field: 'lotatt02', title: '有效期/失效期', width: 113},
                    {field: 'lotatt08', title: '供应商', width: 200},
                    {field: 'lotatt09', title: '样品属性', width: 200},
                    {field: 'lotatt14', title: '入库单号', width: 91},


                    {field: 'lotatt11', title: '存储条件', width: 150},
                    {field: 'enterpriseName', title: '生产厂家', width: 100},

                    {field: 'lotatt10', title: '质量状态', width: 100, formatter: ZL_TYPstatusFormatter},

                    {field: 'name', title: '产品线', width: 100},
                    // {field: 'lotatt10',		title: '备注',	width: 71 },
                ]],onDblClickCell: function(index,field,value){
                    edit();
                },
                onLoadSuccess: function (index,field,value) {

                    $('#ezuiDatagrid').datagrid('reloadFooter',[
                        {name:'合计:',fmqty: fmqtySum(),fmqtyEach: fmqtyEachSum(),qtyallocated:qtyallocatedSum(),
                       qtyallocatedEach:qtyallocatedEachSum(),qtyavailed:qtyavailedSum(),qtyholded:qtyholdedSum(),
                       qtyholdedEach:qtyholdedEachSum()},
                        {name:'合计:',fmqty: fmqtySumAll(),fmqtyEach: fmqtyEachSumAll(),qtyallocated:qtyallocatedSumAll(),
                       qtyallocatedEach:qtyallocatedEachSumAll(),qtyavailed:qtyavailedSumAll(),qtyholded:qtyholdedSumAll(),
                       qtyholdedEach:qtyholdedEachSumAll()},


                    ]);
                },
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
            ezuiDatagridAll = $('#ezuiDatagridAll').datagrid({
                url: '<c:url value="/viewInvLocationController.do?showDatagrid"/>'});
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
                    customerid: row.customerid,
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
                    lotatt12: row.lotatt12,
                    lotatt06: row.lotatt06,
                    sku : row.fmsku,
                    qtyallocated: row.qtyallocated,
                    qtyholded: row.qtyholded,
                    fmuomName: row.fmuomName,
                    defaultreceivinguom: row.defaultreceivinguom,
                    iPa: row.iPa,
                    iMv: row.iMv,
                    oMv: row.oMv,
                    iRp: row.iRp,
                    oRp: row.oRp,
                    totalcubic: row.totalcubic,
                    totalgrossweight: row.totalgrossweight,
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
                param.put("name", $('#name').val());//产品线
                param.put("lotatt04", $('#lotatt04').val());//批号
                param.put("lotatt05", $('#lotatt05').val());//序列号
                param.put("lotatt02Start",$('#lotatt02Start').datebox('getValue'));//时间查询
                param.put("lotatt02End",$('#lotatt02End').datebox('getValue'));//时间查询

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
            ezuiDatagrid.datagrid('load', {
                fmcustomerid: $('#fmcustomerid').val(),//货主编码
                fmlocation: $('#fmlocation').val(),  //库位
                fmsku: $('#fmsku').val(),           //产品代码
                lotatt12: $('#skudescrc').val(),  //产品名称
                name: $('#name').val(),          //产品线
                lotatt04: $('#lotatt04').val(),//批号
                lotatt05: $('#lotatt05').val(),//序列号
                lotatt02Start: $('#lotatt02Start').datebox('getValue'),//时间查询
                lotatt02End: $('#lotatt02End').datebox('getValue'),//时间查询
            });
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
                title: '商品档案',
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
                // queryParams:{
                // 	customerid : $("#ezuiDetailsForm #customerid").textbox("getValue"),
                // 	activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
                // },
                idField: 'sku',
                columns: [[
                    {field: 'customerid', title: '客户代码', width: 80},
                    {field: 'sku', title: '商品编码', width: 120},
                    {field: 'descrC', title: '中文名称', width: 160},
                    {field: 'descrE', title: '英文名称', width: 160},
                    {
                        field: 'activeFlag', title: '激活', width: 40, formatter: function (value, rowData, rowIndex) {
                            return rowData.activeFlag == '1' ? '是' : '否';
                        }
                    },
                    {field: 'alternateSku1', title: '商品条码', width: 120},
                    {field: 'packid', title: '包装代码', width: 80},
                    {field: 'qty', title: '库存数', width: 60},
                    {field: 'qtyallocated', title: '分配数', width: 60},
                    {field: 'qtyonhold', title: '冻结数', width: 60}
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
        };
        // 产品名称选择-主页
        var selectSku = function () {
            var row = ezuiSkuDataDialogId.datagrid('getSelected');
            if (row) {
                $("#skudescrc").textbox('setValue', row.descrC);
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
        // 统计合计
        var fmqtySum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['fmqty']; //获取指定列
            }
            return total;
        }
        var fmqtyEachSum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['fmqtyEach']; //获取指定列
            }
            return total;
        }

        var qtyallocatedSum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyallocated']; //获取指定列
            }
            return total;
        }
        var qtyallocatedEachSum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyallocatedEach']; //获取指定列
            }
            return total;
        }
        var qtyavailedSum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyavailed']; //获取指定列
            }
            return total;
        }
        var qtyholdedSum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyholded']; //获取指定列
            }
            return total;
        }
        var qtyholdedEachSum = function(){
            var rows = $('#ezuiDatagrid').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyholdedEach']; //获取指定列
            }
            return total;
        }
        // 统计总计
        var fmqtySumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['fmqty']; //获取指定列
            }
            return total;
        }
        var fmqtyEachSumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['fmqtyEach']; //获取指定列
            }
            return total;
        }

        var qtyallocatedSumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyallocated']; //获取指定列
            }
            return total;
        }
        var qtyallocatedEachSumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyallocatedEach']; //获取指定列
            }
            return total;
        }
        var qtyavailedSumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyavailed']; //获取指定列
            }
            return total;
        }
        var qtyholdedSumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyholded']; //获取指定列
            }
            return total;
        }
        var qtyholdedEachSumAll = function(){
            var rows = $('#ezuiDatagridAll').datagrid('getRows')//获取当前页的数据行
            var total = 0;
            for (var i = 0; i < rows.length; i++) {
                total += rows[i]['qtyholdedEach']; //获取指定列
            }
            return total;
        }

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
                        <th>货主编码</th>
                        <td><input type='text' id='fmcustomerid' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>产品代码</th>
                        <td><input type='text' id='fmsku' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>库位</th>
                        <td><input type='text' id='fmlocation' class='easyui-textbox' size='16' data-options=''/></td>
                    </tr>
                    <tr>
                        <th>产品名称</th>
                        <td><input type='text' id='skudescrc' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>产品线</th>
                        <td><input type='text' id='name' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>批号</th>
                        <td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>

                    </tr>
                    <tr>
                        <th>序列号</th>
                        <td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>效期</th>
                        <td><input type='text' id='lotatt02Start' class='easyui-datebox' size='16' data-options=''/>
                        </td>
                        <th>至
                        </th>
                        <td><input type='text' id='lotatt02End' class='easyui-datebox' size='16' data-options=''/></td>

                        <td>
                            <a onclick='doSearch();' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                            <a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton'
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
                <a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton'
                   data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                        code='common.button.cancelSelect'/></a>
            </div>
        </div>
        <table id='ezuiDatagrid'></table>
    </div>
</div>
<div id='ezuiDialog' style='padding: 10px;'>
    <form id='ezuiForm' method='post'>
        <input type='hidden' id='viewInvLocationId' name='viewInvLocationId'/>
        🍉麻瓜 2019/8/12 16:44:34
        <table >
            <tr>
                <th>货主</th>
                <td><input type='text' name='customerid' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>入库单号</th>
                <td><input type='text' name='lotatt14' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>入库日期</th>
                <td><input type='text' name='lotatt03' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>产品代码</th>
                <td><input type='text' name='sku' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>产品名称</th>
                <td><input type='text' name='lotatt12' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>注册证号/备案凭证号</th>
                <td><input type='text' name='lotatt06' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>规格型号</th>
                <td><input type='text' name='qtyallocated' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>冻结数量</th>
                <td><input type='text' name='qtyholded' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>单位</th>
                <td><input type='text' name='fmuomName' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>待上架数量</th>
                <td><input type='text' name='iPa' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>待移入数量</th>
                <td><input type='text' name='iMv' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>待移出数量</th>
                <td><input type='text' name='oMv' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>补货待上架</th>
                <td><input type='text' name='iRp' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>补货待下架</th>
                <td><input type='text' name='oRp' class='easyui-textbox' size='50' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>体积</th>
                <td><input type='text' name='totalcubic' class='easyui-textbox' size='50' data-options='required:true'/></td>
                <th>毛重</th>
                <td><input type='text' name='totalgrossweight' class='easyui-textbox' size='50' data-options='required:true'/></td>
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
<%--查询所有数据datagrid--%>
<div id="ezuiDatagridAll" hidden="true"></div>
</body>
</html>
