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
        var ezuiMenu;
        var ezuiForm;
        var ezuiDialog;
        var ezuiDatagrid;
        var ezuiImportDataDialog;
        var ezuiImportDataForm;
        var enterpriseDatagrid;
        var ezuiDialogEnterprise;
        var idlist1;
        var htAndSqDatagrid;
        var ezuiDialogHtAndSq;
        var ezuiDialogBasCarrier;
        var basCarrierDatagrid;
        var qcMeteringDeviceDatagrid;
        var ezuiDialogQcMeteringDevice;
        var basSkuLeakDatagrid;
        var ezuiDialogBasSkuLeak;
        var MtDatagrid;
        var ezuiDialogMt;
        var invlotlocDatagrid;
        var ezuiDialogInvlotloc;
        var gspProductRegisterDatagrid;
        var ezuiDialogProductRegister;
        $(function () {
            ezuiMenu = $('#ezuiMenu').menu();
            ezuiForm = $('#ezuiForm').form();
            ezuiImportDataForm = $('#ezuiImportDataForm').form();
            ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                url: '<c:url value="/remindController.do?showDatagrid1"/>',
                method: 'POST',
                toolbar: '#toolbar',
                title: '',
                pageSize: 50,
                pageList: [10, 50, 100],
                fit: true,
                border: false,
                fitColumns: true,
                nowrap: false,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                idField: 'id',
                columns: [[
                    // {field: 'sku',		title: '序号',	width: 100 },
                    {field: 'codenameC', title: '提醒模块', width: 500},
                    {field: 'codenameE', title: '提醒内容', width:600},
                    {field: 'showSequence', title: '个数', width: 200},
                    // {field: 'udf1', title: '企业idJson', width: 1000,hidden:false},

                ]],
                onDblClickCell: function(index,field,value){
                	edit();
                },
                onRowContextMenu: function (event, rowIndex, rowData) {
                    event.preventDefault();
                    $(this).datagrid('unselectAll');
                    $(this).datagrid('selectRow', rowIndex);
                    ezuiMenu.menu('show', {
                        left: event.pageX,
                        top: event.pageY
                    });
                }, onLoadSuccess: function (data) {
                    ajaxBtn($('#menuId').val(), '<c:url value="/basGtnController.do?getBtn"/>', ezuiMenu);
                    $(this).datagrid('unselectAll');
                }
            });
            ezuiDialog = $('#ezuiDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiForm);
                }
            }).dialog('close');
            var row = ezuiDatagrid.datagrid('getSelected');

            if(row!=null){
                idlist1 = row.udf1;
            }else{
                idlist1='';
            }
            //企业
            enterpriseDatagrid = $("#dataGridEnterprise").datagrid({
                url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagrid',
                method:'POST',
                toolbar : '',
                title: '',
                pageSize : 50,
                pageList : [50, 100, 200],
                border: false,
                fitColumns : false,
                nowrap: true,
                striped: true,
                queryParams:{
                    idList : idlist1,
                    // activeFlag : '1',
                    // customerType:'OW'
                },
                fit:true,
                collapsible:false,
                pagination:true,
                rownumbers:true,
                singleSelect:true,
                idField : 'enterpriseId',
                columns : [[
                    {field: 'enterpriseId',		title: '主键',	width:160 ,hidden:true},
                    {field: 'isUse',		title: '是否有效',	width: 160,formatter:isUseFormatter },
                    {field: 'enterpriseNo',		title: '企业代码',	width: 160 },
                    {field: 'shorthandName',		title: '简称',	width: 160},
                    {field: 'enterpriseName',		title: '企业名称',	width: 160 },
                    {field: 'enterpriseType',		title: '企业类型',	width: 160 ,formatter: entTypeFormatter},

                ]],
                // onDblClickCell: function(index,field,value){
                //     choseClientSelect();
                // },
                onRowContextMenu : function(event, rowIndex, rowData) {

                },
                onSelect: function(rowIndex, rowData) {

                },
                onLoadSuccess:function(data){
                    $(this).datagrid('unselectAll');
                    $(this).datagrid("resize",{height:540});
                }
            });





            //导入
            ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
                modal: true,
                title: '导入',
                buttons: '#ezuiImportDataDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiImportDataForm);
                }
            }).dialog('close');
            /* 控件初始化end */
        });
        var add = function () {
            processType = 'add';
            $('#basGtnId').val(0);
            ezuiDialog.dialog('open');
        };


        var jiekou = function () {
            $.ajax({
                url: 'remindController.do?remind',
                // data: {id: row.sku},
                type: 'POST',
                dataType: 'JSON',
                success: function (result) {

                }
            });
            ezuiDatagrid.datagrid('reload');
        };

        var edit = function () {

            var row = ezuiDatagrid.datagrid('getSelected');

            if(row!=null){
                idlist1 = row.udf1;
            }else{
                idlist1='';
            }
            if(row.showSequence==0){
                $.messager.show({
                    msg: '无详情列表',
                    title: '<spring:message code="common.message.prompt"/>'
                });
                return;
            }
            processType = 'edit';
            // var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                console.log(row.code);

                if(row.code==11||row.code==12||row.code==13||row.code==14){
                    //库内货品近效期
                    invlotlocDatagrid = $('#dataGridInvlotloc').datagrid({
                        // url : sy.bp()+"/commonController.do?queryMtList",
                        url : '<c:url value="/remindController.do?showInvLotLocDatagrid"/>',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        fit: false,
                        border: false,
                        fitColumns : false,
                        nowrap: true,
                        striped: true,
                        collapsible:true,
                        height:400,
                        queryParams:{
                            idList : idlist1,
                        },
                        pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        columns : [[
                            {field: 'enterpriseName',		title: '货主',	width: 220 },

                            {field: 'locationid',		title: '货位',	width: 220 },
                            {field: 'productName',		title: '产品名称',	width: 220 },

                            {field: 'sku',		title: '产品代码',	width: 200 },
                            {field: 'specsName',		title: '规格',	width: 200 },
                            {field: 'lotatt02',		title: '效期',	width: 200 },
                            {field: 'lotatt01',		title: '生产日期',	width: 200 },
                            {field: 'lotatt04',		title: '批号',	width: 200 },
                            {field: 'lotatt05',		title: '序列号',	width: 200 },
                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     choseClientSelect();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });

                    ezuiDialogInvlotloc = $('#ezuiDialogInvlotloc').dialog({
                        modal : true,
                        fit:true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width: 1000,
                        height:650,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })


                }else if(row.code==15||row.code==16||row.code==17||row.code==18||row.code==19||row.code==20
                    ||row.code==21||row.code==22){
                    // 营业，经营，生产，医疗

                    ezuiDialogEnterprise = $('#ezuiDialogEnterprise').dialog({
                        modal : true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:600,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })
                    enterpriseDatagrid.datagrid('load', {
                        idList : idlist1,
                    });
                }else if(row.code==23||row.code==24||row.code==25||row.code==26 ){
                    //授权，合同
                    htAndSqDatagrid = $("#dataGridHtAndSq").datagrid({
                        url : sy.bp()+'/basCustomerController.do?showDatagrid',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        border: false,
                        fitColumns : false,
                        nowrap: true,
                        striped: true,
                        queryParams:{
                            idList : idlist1,

                        },
                        fit:true,
                        collapsible:false,
                        pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        idField : 'firstop',
                        columns : [[
                            {field: 'activeFlag',		title: '是否合作 ',	width: 71,formatter:function(value,rowData,rowIndex){
                                    return rowData.activeFlag == '1' ? '是' : '否';
                                }},
                            {field: 'billclass',		title: '首营状态 ',	width: 71,formatter:firstStateFormatter
                            },
                            {field: 'customerType',		title: '客户类型 ',	width: 80,formatter:function(value,rowData,rowIndex){
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

                            {field: 'customerid',		title: '客户代码',	width: 100 },
                            {field: 'descrC',		title: '客户名称',	width: 250 },
                            {field: 'enterpriseNo',		title: '企业代码 ',	width: 100 },
                            {field: 'shorthandName',		title: '简称 ',	width: 100 },
                            // {field: 'enterpriseName',		title: '企业名称 ',	width: 250 },
                            {field: 'allClient',		title: '供应商对应货主 ',	width: 250 },
                            {field: 'receivingAllClient',		title: '收货单位对应货主 ',	width: 250 },


                            {field: 'contacts',		title: '联系人 ',	width: 80 },
                            {field: 'contactsPhone',		title: '联系人电话 ',	width: 100 },
                            {field: 'supContractNo',		title: '合同编号 ',	width: 120 },
                            /* {field: 'operateType',		title: '类型 ',	width: 12, formatter:function(value,rowData,rowIndex){
                                     return rowData.operateType;
                                 }},*/
                            {field: 'contractUrl',		title: '合同文件 ',	width: 80,formatter:showUrlFile},
                            {field: 'clientContent',		title: '委托/合同内容',	width: 120 },
                            {field: 'clientStartDate',		title: '委托/合同开始时间 ',	width: 130 ,formatter:dateFormat2},
                            {field: 'clientEndDate',		title: '委托/合同结束时间 ',	width: 130 ,formatter:dateFormat2},
                            {field: 'clientTerm',		title: '委托/合同期限',	width: 130 ,formatter:day},
                            /*{field: 'overreceiving',		title: '允许超收',	width: 12, formatter:function(value,rowData,rowIndex){
                                return rowData.overreceiving == 'Y' ? '是' : '否';
                            }},*/
                            {field: 'isChineseLabel',		title: '是否贴中文标签 ',	width: 110,formatter:yesOrNoFormatter},

                            {field: 'notes',		title: '备注 ',	width: 200 },

                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     choseClientSelect();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });

                    ezuiDialogHtAndSq = $('#ezuiDialogHtAndSq').dialog({
                        modal : true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:600,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })
                }else if(row.code==27||row.code==28||row.code==29||row.code==30||row.code==31||row.code==32){
                    //承运商

                    basCarrierDatagrid = $('#dataGridBasCarrier').datagrid({
                        url : '<c:url value="/basCarrierLicenseController.do?showDatagrid"/>',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        fit: true,
                        border: false,
                        fitColumns : false,
                        nowrap: true,
                        striped: true,
                        collapsible:false,
                        queryParams:{
                            idList : idlist1,
                        },
                        pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        idField : 'carrierLicenseId',
                        columns : [[
                            {field: 'carrierLicenseId',		title: '承运商名称',	width: 120,hidden:true },
                            {field: 'enterpriseName',		title: '承运商名称',	width: 120 },
                            {field: 'roadNumber',		title: '道路运营许可证编号',	width: 140 },
                            {field: 'roadNumberTerm',		title: '证件有效期',	width: 120 },
                            {field: 'roadAuthorityPermit',		title: '核发机关',	width: 120 },
                            {field: 'roadBusinessScope',		title: '经营范围',	width: 120 },
                            {field: 'carrierNo',		title: '快递经营许可证编号',	width: 120 },
                            {field: 'carrierDate',		title: '发证日期',	width: 125 },
                            {field: 'carrierAuthorityPermit',		title: '发证机关',	width: 125 },
                            {field: 'carrierEndDate',		title: '有效期至',	width: 125 },
                            {field: 'carrierBusinessScope',		title: '业务范围',	width: 120 },
                            {field: 'contractNo',		title: '合同编号',	width: 38 ,hidden:true},
                            {field: 'contractUrl',		title: '合同文件',	width: 38 ,hidden:true},
                            {field: 'clientContent',		title: '合同内容',	width: 38 ,hidden:true},
                            {field: 'clientStartDate',		title: '合同开始时间',	width: 38 ,hidden:true},
                            {field: 'clientEndDate',		title: '合同结束时间',	width: 38 ,hidden:true},
                            {field: 'clientTerm',		title: '合同期限',	width: 38,hidden:true },
                            {field: 'createId',		title: '录入人',	width: 120 },
                            {field: 'createDate',	title: '录入时间',	width: 125 },
                            {field: 'editId',		title: '修改人',	width: 120 },
                            {field: 'editDate',		title: '修改时间',	width: 125 },
                            {field: 'activeFlag',	title: '是否合作',	width: 120 ,formatter:function(value,rowData,rowIndex){
                                    return rowData.activeFlag == '1' ? '是' : '否';
                                } }
                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     choseClientSelect();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });

                    ezuiDialogBasCarrier = $('#ezuiDialogBasCarrier').dialog({
                        modal : true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:600,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })

                }else if(row.code==33){
                    //库内货品 养护提醒
                    MtDatagrid = $('#dataGridMt').datagrid({
                        // url : sy.bp()+"/commonController.do?queryMtList",
                        url : '<c:url value="/commonController.do?queryMtList"/>',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        fit: true,
                        border: false,
                        fitColumns : false,
                        nowrap: true,
                        striped: true,
                        collapsible:false,
                        // queryParams:{
                        //     idList : idlist1,
                        // },
                        // pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        columns : [[
                            {field: 'sku',		title: '产品代码',	width: 200 },
                            {field: 'locationid',		title: '库位',	width: 220 },
                            {field: 'customerid',		title: '客户代码',	width: 220 },
                            {field: 'qty',		title: '库存',	width: 140 }
                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     choseClientSelect();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });

                    ezuiDialogMt = $('#ezuiDialogMt').dialog({
                        modal : true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:600,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })


                }else if(row.code==34||row.code==35){
                    //计量设备
                    qcMeteringDeviceDatagrid = $('#dataGridQcMeteringDevice').datagrid({
                        url : '<c:url value="/qcMeteringDeviceController.do?showDatagrid"/>',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        fit: true,
                        border: false,
                        fitColumns : true,
                        nowrap: true,
                        striped: true,
                        queryParams:{
                            idList : idlist1,
                        },
                        collapsible:false,
                        pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        idField : 'calId',
                        columns : [[
                            {field: 'calId',		title: '待输入栏位0',	width: 80,hidden:true },
                            {field: 'calName',		title: '名称',	width: 80 },
                            {field: 'calNumber',	title: '编号',	width: 80 },
                            {field: 'calTerm',		title: '校期',	width: 80 },
                            {field: 'calCardUrl',	title: '证件',	width: 80 },
                            {field: 'activeFlag',	title: '是否有效',	width: 80 ,formatter:function (value,rowData,rowIndex) {
                                    return rowData.activeFlag=='1'? '是':'否'
                                }},
                            {field: 'createId',		title: '创建人',	width: 80 },
                            {field: 'createDate',	title: '创建日期',	width: 80 },
                            {field: 'editId',		title: '修改人',	width: 80 },
                            {field: 'editDate',		title: '修改日期',	width: 80 }
                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     edit();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });


                    ezuiDialogQcMeteringDevice = $('#ezuiDialogQcMeteringDevice').dialog({
                        modal : true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:600,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })

                }else if(row.code==36){
                    //未备案商品
                    basSkuLeakDatagrid = $('#dataGridBasSkuLeak').datagrid({
                        url : '<c:url value="/basSkuLeakController.do?showDatagrid"/>',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        fit: false,
                        border: false,
                        fitColumns : false,
                        nowrap: true,
                        striped: true,
                        // queryParams:{
                        //     idList : idlist1,
                        // },
                        collapsible:false,
                        pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        idField : 'customerid',
                        columns : [[
                            {field: 'customerid',		title: '货主',	width: 142 },
                            {field: 'sku',		title: '产品代码',	width: 142 },
                            {field: 'standard',		title: '规格',	width: 142 },
                            {field: 'lotatt06',		title: '注册证',	width: 142 },
                            {field: 'lotatt11',		title: '储存条件',	width: 142 },
                            {field: 'conversionRatio',		title: '换算率',	width: 142 },
                            {field: 'unit',		title: '单位',	width: 142 },
                            {field: 'productline',		title: '产品线',	width: 142 },
                            {field: 'supplier',		title: '供应商',	width: 142 },

                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     edit();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });


                    ezuiDialogBasSkuLeak = $('#ezuiDialogBasSkuLeak').dialog({
                        modal : true,
                        fit:true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:660,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })
                }else if(row.code==37||row.code==38){
                    //注册证
                    gspProductRegisterDatagrid = $('#dataGridProductRegister').datagrid({
                        url : '<c:url value="/gspProductRegisterController.do?showDatagrid"/>',
                        method:'POST',
                        toolbar : '',
                        title: '',
                        pageSize : 50,
                        pageList : [50, 100, 200],
                        fit: true,
                        border: false,
                        fitColumns : false,
                        nowrap: true,
                        striped: true,
                        queryParams:{
                            idList : idlist1,
                        },
                        collapsible:false,
                        pagination:true,
                        rownumbers:true,
                        singleSelect:true,
                        idField : 'customerid',
                        columns : [[
                            {field: 'productRegisterId',		title: '主键',	width: 100 ,hidden:true},
                            {field: 'isUse',		title: '是否有效',	width: 100 ,formatter:isUseFormatter},
                            {field: 'productRegisterNo',		title: '注册证编号/备案号',	width: 150 },
                            {field: 'attachmentUrl',		title: '注册证编号/备案附件',	width: 150,formatter:showProductRegisteUrlFile },

                            {field: 'classifyId',		title: '管理分类',	width: 60 },
                            {field: 'classifyCatalog',		title: '分类目录',	width: 280 },
                            {field: 'productNameMain',		title: '产品名称',	width: 170 },
                            {field: 'approveDate',		title: '批准日期',	width: 150,formatter:function (value,row,index) {
                                    return dateFormat2(value);
                                } },
                            {field: 'productRegisterExpiryDate',		title: '有效期至',	width: 150 ,formatter:function (value,row,index) {
                                    return dateFormat2(value);
                                }},

                            {field: 'productRegisterVersionName',		title: '注册证/备案版本',	width: 100 },
                            {field: 'checkerId',		title: '审核人',	width: 100 },
                            {field: 'checkDate',		title: '审核时间',	width: 150 },
                            {field: 'createId',		title: '创建人',	width: 100 },
                            {field: 'createDate',		title: '创建时间',	width: 150 },

                        ]],
                        // onDblClickCell: function(index,field,value){
                        //     edit();
                        // },
                        onRowContextMenu : function(event, rowIndex, rowData) {

                        },
                        onSelect: function(rowIndex, rowData) {

                        },
                        onLoadSuccess:function(data){
                            $(this).datagrid('unselectAll');
                            $(this).datagrid("resize",{height:540});
                        }
                    });


                    ezuiDialogProductRegister = $('#ezuiDialogProductRegister').dialog({
                        modal : true,
                        title : '<spring:message code="common.dialog.title"/>',
                        width:850,
                        height:600,
                        cache: false,
                        onClose : function() {
                            ezuiFormClear(ezuiForm);
                        }
                    })
                }



                // ezuiDialog.dialog('open');






            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
        //细单












        var del = function () {
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function (confirm) {
                    if (confirm) {
                        $.ajax({
                            url: 'basGtnController.do?delete',
                            data: {id: row.sku},
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
                });
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
        var commit = function () {
            var url = '';
            if (processType == 'edit') {
                url = '<c:url value="/basGtnController.do?edit"/>';
            } else {
                url = '<c:url value="/basGtnController.do?add"/>';
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
                            msg = result.msg;
                            ezuiDatagrid.datagrid('reload');
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
        };
        var doSearch = function () {
            ezuiDatagrid.datagrid('load', {
                sku: $('#sku').val(),
                gtncode: $('#gtncode').val()
            });
        };

        /* 导入start */
        var commitImportData1 = function (obj) {
            ezuiImportDataForm.form('submit', {
                url: '<c:url value="/basGtnController.do?importExcelData"/>',
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

        /* 下载导入模板 */
        var downloadTemplate = function () {
            if (navigator.cookieEnabled) {
                $('#ezuiBtn_downloadTemplate').linkbutton('disable');
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("token", token);
                var formId = ajaxDownloadFile(sy.bp() + "/basGtnController.do?exportTemplate", param);
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

        var toImportData = function () {
            ezuiImportDataDialog.dialog('open');
        };



        //查询库内货品信息
        function doSearchClient() {
            var row = ezuiDatagrid.datagrid('getSelected');
            if(row!=null){
                idlist1 = row.udf1;
            }else{
                idlist1='';
            }
            invlotlocDatagrid.datagrid('load', {
                idList:idlist1,
                enterpriseName : $('#enterpriseNameQ').val(),
                locationid : $('#locationidQ').val(),
                productName : $('#productNameQ').val(),
                sku : $('#skuQ').val(),
                specsName : $('#specsNameQ').val(),
                lotatt02:$('#lotatt02Q').val(),
                lotatt01 : $('#lotatt01Q').val(),
                lotatt04 : $('#lotatt04Q').val(),
                lotatt05 : $('#lotatt05Q').val(),
            });
        }
        //查询未备案产品信息
        function doSearchBasSkuLeak() {
            // var row = ezuiDatagrid.datagrid('getSelected');
            // if(row!=null){
            //     idlist1 = row.udf1;
            // }else{
            //     idlist1='';
            // }
            basSkuLeakDatagrid.datagrid('load', {
                // idList:idlist1,
                customerid : $('#customerid').val(),
                sku : $('#sku').val(),
                standard : $('#standard').val(),
                lotatt06 : $('#lotatt06').val(),
                productline : $('#productline').val(),
                supplier:$('#supplier').val(),

            });
        }

        /* 导出库内货品start */
        var doExport = function(){
            var row = ezuiDatagrid.datagrid('getSelected');
            if(row!=null){
                idlist1 = row.udf1;
            }else{
                idlist1='';
            }
            if(navigator.cookieEnabled){
                $('#ezuiBtn_export').linkbutton('disable');
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("token", token);
                param.put("idList", idlist1);

                param.put("enterpriseName", $('#enterpriseNameQ').val());
                param.put("locationid", $('#locationidQ').val());
                param.put("productName",$('#productNameQ').val());
                param.put("sku", $('#skuQ').val());
                param.put("specsName", $('#specsNameQ').val());
                param.put("lotatt02", $('#lotatt02Q').val());
                param.put("lotatt01", $('#lotatt01Q').val());
                param.put("lotatt04", $('#lotatt04Q').val());
                param.put("lotatt05", $('#lotatt05Q').val());


                //--导出Excel
                var formId = ajaxDownloadFile(sy.bp()+"/remindController.do?exportInvLotAttDataToExcel", param);
                downloadCheckTimer = window.setInterval(function () {
                    window.clearInterval(downloadCheckTimer);
                    $('#'+formId).remove();
                    $('#ezuiBtn_export').linkbutton('enable');
                    $.messager.progress('close');
                    $.messager.show({
                        msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
                    });
                }, 1000);
            }else{
                $.messager.show({
                    msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
                });
            }
        };
        /* 导出end */




        /* 导出未备案产品start */
        var doBasSkuLeakExport = function(){
            var row = ezuiDatagrid.datagrid('getSelected');
            if(row!=null){
                idlist1 = row.udf1;
            }else{
                idlist1='';
            }
            if(navigator.cookieEnabled){
                $('#ezuiBtn_export').linkbutton('disable');
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("token", token);
                // param.put("idList", idlist1);

                param.put("customerid", $('#customerid').val());
                param.put("sku", $('#sku').val());
                param.put("standard",$('#standard').val());
                param.put("lotatt06", $('#lotatt06').val());
                param.put("specsName", $('#specsName').val());
                param.put("productline", $('#productline').val());
                param.put("supplier", $('#supplier').val());



                //--导出Excel
                var formId = ajaxDownloadFile(sy.bp()+"/basSkuLeakController.do?exportSkuDataToExcel", param);
                downloadCheckTimer = window.setInterval(function () {
                    window.clearInterval(downloadCheckTimer);
                    $('#'+formId).remove();
                    $('#ezuiBtn_export').linkbutton('enable');
                    $.messager.progress('close');
                    $.messager.show({
                        msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
                    });
                }, 1000);
            }else{
                $.messager.show({
                    msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
                });
            }
        };
        /* 导出end */

</script>
</head>
<body>
<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<div class='easyui-layout' data-options='fit:true,border:false'>
    <div data-options='region:"center",border:false' style='overflow: hidden;'>
        <div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
            <%--<fieldset>--%>
            <%--<legend><spring:message code='common.button.query'/></legend>--%>
            <%--<table>--%>
            <%--<tr>--%>
            <%--<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>--%>
            <%--<th>GTIN码</th><td><input type='text' id='gtncode' class='easyui-textbox' size='16' data-options=''/></td>--%>
            <%--<td>--%>
            <%--<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>--%>
            <%--<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>--%>
            <%--<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>--%>

            <%--</td>--%>
            <%--</tr>--%>
            <%--</table>--%>
            <%--</fieldset>--%>
            <div>

            <%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
            <%--<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
            <%--<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
            <%--<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>--%>
            <%--<a onclick='jiekou();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>接口</a>--%>

            </div>
        </div>
        <table id='ezuiDatagrid'></table>
    </div>
</div>
<div id='ezuiDialog' style='padding: 10px;'>
    <form id='ezuiForm' method='post'>
        <input type='hidden' id='basGtnId' name='basGtnId'/>
        <%--<table>--%>
            <%--<tr>--%>
                <%--<th>产品代码</th>--%>
                <%--<td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>GTIN码</th>--%>
                <%--<td><input type='text' name='gtncode' class='easyui-textbox' size='16' data-options='required:true'/>--%>
                <%--</td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    </form>
</div>
<div id='ezuiDialogBtn'>
    <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.commit'/></a>
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

<!-- 导入start -->
<%--<div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>--%>
    <%--<form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>--%>
        <%--<table>--%>
            <%--<tr>--%>
                <%--<th>档案</th>--%>
                <%--<td>--%>
                    <%--<input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36"--%>
                           <%--data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>--%>
                    <%--<a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton'--%>
                       <%--href='javascript:void(0);'>下载档案模版</a>--%>
                <%--</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<th>执行结果</th>--%>
                <%--<td><input id='importResult' class="easyui-textbox" size='100' style="height:150px"--%>
                           <%--data-options="editable:false,multiline:true"/></td>--%>
            <%--</tr>--%>
        <%--</table>--%>
    <%--</form>--%>
<%--</div>--%>
<%--<div id='ezuiImportDataDialogBtn'>--%>
    <%--<a onclick='commitImportData1();' id='ezuiBtn_importDataCommit' class='easyui-linkbutton'--%>
       <%--href='javascript:void(0);'><spring:message code='common.button.commit'/></a>--%>
    <%--<a onclick='ezuiDialogClose("#ezuiImportDataDialog");' class='easyui-linkbutton'--%>
       <%--href='javascript:void(0);'><spring:message code='common.button.close'/></a>--%>
<%--</div>--%>
<!-- 导入end -->


<div id='ezuiDialogEnterprise' style='padding: 10px;display: none'>
    <div id='clientTB' class='datagrid-toolbar' style=''>
        <%--<fieldset >--%>
            <%--<legend>企业信息</legend>--%>
            <%--<table>--%>
                <%--<tr>--%>
                    <%--<th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>--%>
                    <%--<th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>--%>
                    <%--<td>--%>
                        <%--<a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
                        <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</fieldset>--%>
    </div>
    <table id="dataGridEnterprise">

    </table>
</div>

<div id='ezuiDialogHtAndSq' style='padding: 10px;display: none'>
    <div id='clientTB2' class='datagrid-toolbar' style=''>
        <%--<fieldset >--%>
        <%--<legend>企业信息</legend>--%>
        <%--<table>--%>
        <%--<tr>--%>
        <%--<th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<td>--%>
        <%--<a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
        <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</table>--%>
        <%--</fieldset>--%>
    </div>
    <table id="dataGridHtAndSq">

    </table>
</div>

<div id='ezuiDialogBasCarrier' style='padding: 10px;display: none'>
    <div id='clientTB3' class='datagrid-toolbar' style=''>
        <%--<fieldset >--%>
        <%--<legend>企业信息</legend>--%>
        <%--<table>--%>
        <%--<tr>--%>
        <%--<th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<td>--%>
        <%--<a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
        <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</table>--%>
        <%--</fieldset>--%>
    </div>
    <table id="dataGridBasCarrier">

    </table>
</div>
<div id='ezuiDialogQcMeteringDevice' style='padding: 10px;display: none'>
    <div id='clientTB4' class='datagrid-toolbar' style=''>
        <%--<fieldset >--%>
        <%--<legend>企业信息</legend>--%>
        <%--<table>--%>
        <%--<tr>--%>
        <%--<th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<td>--%>
        <%--<a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
        <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</table>--%>
        <%--</fieldset>--%>
    </div>
    <table id="dataGridQcMeteringDevice">

    </table>
</div>

<div id='ezuiDialogBasSkuLeak' style='padding: 10px;display: none'>
    <div id='clientTB5' class='datagrid-toolbar' style=''>
        <fieldset >
        <legend>未备案产品</legend>
        <table>
        <tr>
            <th>货主：</th><td><input type='text' id='customerid'  class='easyui-textbox'    data-options='width:200'/></td>
            <th>产品代码：</th><td><input type='text' id='sku' class='easyui-textbox'    data-options='width:200'/></td>
            <th>规格：</th><td><input type='text' id='standard' class='easyui-textbox'    data-options='width:200'/></td>
            <th>注册证：</th><td><input type='text' id='lotatt06' class='easyui-textbox'    data-options='width:200'/></td>
        </tr>
        <tr>
            <th>产品线：</th><td><input type='text' id='productline' class='easyui-textbox'    data-options='width:200'/></td>
            <th>供应商：</th><td><input type='text' id='supplier' class='easyui-textbox'    data-options='width:200'/></td>


            <td colspan="2">
            <a onclick='doSearchBasSkuLeak()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
            <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
            <a onclick='ezuiToolbarClear("#clientTB5");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
            <a onclick='doBasSkuLeakExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

        </td>
        </tr>
        </table>
        </fieldset>
    </div>
    <table id="dataGridBasSkuLeak">

    </table>
</div>

<div id='ezuiDialogMt' style='padding: 10px;display: none'>
    <div id='clientTB6' class='datagrid-toolbar' style=''>
        <%--<fieldset >--%>
        <%--<legend>企业信息</legend>--%>
        <%--<table>--%>
        <%--<tr>--%>
        <%--<th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<td>--%>
        <%--<a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
        <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</table>--%>
        <%--</fieldset>--%>
    </div>
    <table id="dataGridMt">

    </table>
</div>

<div id='ezuiDialogInvlotloc' style='padding: 10px;display: none'>
    <div id='clientTB7' class='datagrid-toolbar' style=''>
        <fieldset >
        <legend>库内货品</legend>
        <table>
            <tr>
                <th>货主：</th><td><input type='text' id='enterpriseNameQ'  class='easyui-textbox'    data-options='width:150'/></td>
                <th>货位：</th><td><input type='text' id='locationidQ' class='easyui-textbox'    data-options='width:150'/></td>
                <th>产品名称：</th><td><input type='text' id='productNameQ' class='easyui-textbox'    data-options='width:150'/></td>
                <th>产品代码：</th><td><input type='text' id='skuQ' class='easyui-textbox'    data-options='width:150'/></td>
                <th>规格：</th><td><input type='text' id='specsNameQ' class='easyui-textbox'    data-options='width:150'/></td>

            </tr>
            <tr>
                <th>效期：</th><td><input type='text' id='lotatt02Q'  class='easyui-textbox'    data-options='width:150'/></td>
                <th>生产日期：</th><td><input type='text' id='lotatt01Q' class='easyui-textbox'    data-options='width:150'/></td>
                <th>批号：</th><td><input type='text' id='lotatt04Q' class='easyui-textbox'    data-options='width:150'/></td>
                <th>序列号：</th><td><input type='text' id='lotatt05Q' class='easyui-textbox'    data-options='width:150'/></td>


                <td colspan="2">
                <a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                    <a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

                    <a onclick='ezuiToolbarClear("#clientTB7");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>

                </td>
            </tr>
        </table>
        </fieldset>
    </div>
    <table id="dataGridInvlotloc">

    </table>
</div>



<div id='ezuiDialogProductRegister' style='padding: 10px;display: none'>
    <div id='clientTB8' class='datagrid-toolbar' style=''>
        <%--<fieldset >--%>
        <%--<legend>企业信息</legend>--%>
        <%--<table>--%>
        <%--<tr>--%>
        <%--<th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>--%>
        <%--<td>--%>
        <%--<a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
        <%--<a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</table>--%>
        <%--</fieldset>--%>
    </div>
    <table id="dataGridProductRegister">

    </table>
</div>




</body>
</html>
