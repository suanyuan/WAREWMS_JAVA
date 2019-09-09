<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<html>
<head>
    <title>Title</title>
</head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<body>

<form id='ezuiFormDetail' method='post'>
    <fieldset>
        <legend>产品首营信息</legend>
            <input type='hidden' id='applyId' name='applyId' />
            <table>
                <tr>
                    <th>委托客户</th>
                    <td>
                        <input type='text'  id='clientName' class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="clientId" id="clientId" />
                        <input type="hidden" name="cli_enterpriseId" id="cli_enterpriseId" />
                        <a href="javascript:void(0);" class="easyui-linkbutton"  data-options="" onclick="viewClientEnterpriseUrl()">查看</a>

                    </td>
                    <%--<th>供应商</th>--%>
                    <%--<td>--%>
                        <%--<input type='text' value="${firstBusinessApply.supplierName}" id='supplierName' class='easyui-textbox' data-options='required:true,width:200'/>--%>
                        <%--<input type="hidden" name="supplierId" id="supplierId" value="${firstBusinessApply.supplierId}"/>--%>
                    <%--</td>--%>
                    <th>产品线</th>
                    <td>
                        <input id="productLine" name="productLine"  data-options='required:true'  type="text"/>
                    </td>
                    <!--<th>创建人</th>
                    <td><input type='text' name='createId' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>创建时间</th>
                    <td><input type='text' name='createDate' class='easyui-textbox' data-options='required:true,width:200'/></td>-->

                </tr>
            </table>
    </fieldset>
</form>
<div>
    <a onclick='choseProduct();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>选择产品</a>
    <a onclick='delProduct();'   class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
    <a onclick='submitApply();'  class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>提交申请</a>
    <!--<a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'>清空选择</a>-->
</div>
<table id='ezuiDatagridDetail1' ></table>

<div id='enterpriseCustomerDialog' style='padding: 10px;'>

</div>

<div id='enterpriseSupplierDialog' style='padding: 10px;'>

</div>

<%--选择供应商和产品--%>
<div id='enterpriseProduct' style='padding: 10px;'>

</div>
<div id='ezuiBtn' style="display: none">
    <a onclick='doSubmitAddress();' id='ezuiBtn_commit1' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='clearDialog();' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>


<div id='ezuiDialogClientDetail' style='padding: 10px;display: none'>
    <div id='clientTB' class='datagrid-toolbar' style=''>
        <fieldset >
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input type='text' id='kehudaimaD'  class='easyui-textbox'    data-options='width:200'/></td>
                    <th>客户名称：</th><td><input type='text' id='kehumingcehngD' class='easyui-textbox'    data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchClient()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridClientDetail">

    </table>
</div>


<div id="ProductDialog">

</div>
<div id="productRegisterDialog">

</div>
<div id="ezuiDialogClientEnterprise">

</div>

<script>
    var ezuiDatagridDetail1;
    var enterpriseCustomerDialog;
    var enterpriseSupplierDialog;
    // var ezuiDialogSpec;
    var dataGridProduct;
    var ezuiDialogClientDetail;
    var ezuiDialogSupplierDetail;
    var arr;
    var arr1;
    var enterpriseProduct;
    var ezuiDialogClientEnterprise;

    Array.prototype.indexOf = function(val) { //prototype 给数组添加属性
        for (var i = 0; i < this.length; i++) { //this是指向数组，this.length指的数组类元素的数量
            if (this[i] == val) return i; //数组中元素等于传入的参数，i是下标，如果存在，就将i返回
        }
        return -1;
    };
    Array.prototype.remove = function(val) {  //prototype 给数组添加属性
        var index = this.indexOf(val); //调用index()函数获取查找的返回值
        if (index > -1) {
            this.splice(index, 1); //利用splice()函数删除指定元素，splice() 方法用于插入、删除或替换数组的元素
        }
    };

    <%--alert(${firstBusinessApply.productline}+'111111111111');--%>
    $(function () {
        // ezuiDialogSpec.close();


        //主页面
        ezuiDatagridDetail1 = $("#ezuiDatagridDetail1").datagrid({
            url : sy.bp()+'/firstBusinessApplyController.do?showSpecsDatagrid',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            queryParams:{'applyId':<c:choose><c:when test="${firstBusinessApply.applyId == null}">'empty'</c:when><c:otherwise>'${firstBusinessApply.applyId}'</c:otherwise></c:choose>},
            pagination:true,
            rownumbers:true,
            singleSelect:false,
            idField : 'productApplyId',
            columns : [[
                {field: 'productApplyId',title:'主键',hidden:true},
                {field: 'specsId',title:'主键',hidden:true},
                {field: 'customerid',title:'主键',hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '10%'},
                {field: 'productName',title: '产品名称',width: '10%'},
                {field: 'specsName',title: '规格' ,width: '10%'},
                {field: 'productModel',title: '产品型号',width: '15%'},
                {field: 'supplierName',title: '供应商',width: '20%'},
                {field: 'productRegisterNo',title: '注册证编号',width: '20%'},
                {field: 'productRegisterId',title: '注册证主键',hidden:true},
                {field: '_operate1',		title: '查看注册证',	width: '10%',
                    formatter: formatOperProductRegeister
                },
                {field: '_operate',		title: '查看产品',	width: '10%',
                    formatter: formatOperProductAdd
                }
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:500});
            }
        });






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
                isUse : '1',
                activeFlag : '1',
                customerType:'OW'
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





        $("#clientName").textbox({
            icons:[{
                value:'${firstBusinessApply.clientName}',
                iconCls:'icon-search',
                handler: function(e){
                    //searchCustomerEnterprise();
                    searchClient();
                }
            }]
        })


        var customerid = $("#clientId").val();
        <c:choose>
            <c:when test="${firstBusinessApply.productline != null}">

                $("#productLine").combobox({
                    panelHeight: 'auto',
                    url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid,
                    valueField:'id',
                    textField:'value',
                    onLoadSuccess:function () {
                        $("#productLine").combobox("setValue",'${firstBusinessApply.productline}');
                    }
                })
            </c:when>
            <c:otherwise>
                $("#productLine").combobox({panelHeight: 'auto'
                });
            </c:otherwise>
        </c:choose>



    });


    function delProduct(){
        console.log(1111111111111111);
        var row =  ezuiDatagridDetail1.datagrid('getSelected');
        arr.remove(row.specsId);
        arr1.remove(row.customerid);
        var rowIndex = ezuiDatagridDetail1.datagrid('getRowIndex', row);
        ezuiDatagridDetail1.datagrid('deleteRow', rowIndex);

    }

    //供应商企业信息详情
    function viewClientEnterpriseUrl() {
        var enterpriseId = $("#ezuiFormDetail input[id='cli_enterpriseId']").val();
        // var enterpriseId = $("#ezuiFormSupInfo #enterpriseId").val();
        enterpriseInfo(enterpriseId);
    }

    //委托方企业信息详情
    <%--function viewClientEnterpriseUrl(){--%>
        <%--$(function() {--%>
            <%--ezuiDialogClientEnterprise = $('#ezuiDialogClientEnterprise').dialog({--%>
                <%--modal : true,--%>
                <%--title : '<spring:message code="common.dialog.title"/>',--%>
                <%--buttons : '',--%>
                <%--href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",--%>
                <%--width:1200,--%>
                <%--height:530,--%>
                <%--closable:true,--%>
                <%--cache: false,--%>
                <%--onClose : function() {--%>
                    <%--ezuiFormClear(ezuiDialogClientEnterprise);--%>
                <%--}--%>
            <%--}).dialog('close');--%>
        <%--});--%>
        <%--//processType = 'edit';--%>

        <%--//var row = ezuiDatagrid.datagrid('getSelected');--%>
        <%--console.log($("#ezuiFormDetail input[id='cli_enterpriseId']").val());--%>
        <%--if(enterpriseId==null || enterpriseId==""){--%>
            <%--// enterpriseId = $("#enterpriseId").val();--%>
        <%--}--%>

        <%--if(enterpriseId!=null && enterpriseId!="" ){--%>
            <%--ezuiDialogClientEnterprise.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail"+"&id="+enterpriseId).dialog('open');--%>
            <%--enterpriseId = "";--%>
        <%--}else{--%>
            <%--$.messager.show({--%>
                <%--msg : '请先选择企业', title : '提示'--%>
            <%--});--%>
        <%--}--%>
    <%--}--%>


    function operateGridProductAdd(id) {
        console.log("---------->"+id);
        // var type = 'product';
        // enterpriseDialog.dialog("refresh","/gspProductRegisterSpecsController.do?toAdd&specsId="+id).dialog('open');
        // enterpriseDialog.dialog('close');
        $('#ProductDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspProductRegisterSpecsController.do?toAdd&specsId="+id+"&type=product",
            width:1050,
            height:550,
            cache:false,
            onClose : function() {

            }
        })
    }
    function formatOperProductAdd(value,row,index){
        // for(var i=0;i<rows.length;i++){
        //     var s=rows[i].specsId;
        // }
        return "<a onclick=\"operateGridProductAdd('"+row.specsId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }


    function formatOperProductRegeister(value,row,index){
        if(row.productRegisterId!=null && row.productRegisterId!=""){
            return "<a onclick=\"operateGridProductRegeister('"+row.productRegisterId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
        }else{
            return "<a>无</a>";

        }
    }
    function operateGridProductRegeister(id) {
        console.log("---------->"+id);
        // processType = 'product';
        // processType = 'edit';
        // enterpriseDialog.dialog("refresh","/gspProductRegisterSpecsController.do?toAdd&specsId="+id).dialog('open');
        // enterpriseDialog.dialog('close');
        $('#productRegisterDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspProductRegisterController.do?toDetail&id="+id,
            fit:true,
            cache: false,

            onClose : function() {
                // ezuiFormClear(ezuiForm);
            },
            onLoad:function () {

            }
        });

    }

    function searchCustomerEnterprise() {
        enterpriseCustomerDialog = $('#enterpriseCustomerDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toSearchDialog&target=enterpriseCustomerApply&type=customer",
            width:850,
            height:500,
            cache:false,
            onClose : function() {

            }
        })
    }

    function choseSelect_enterpriseCustomerApply(id,name) {
        //$("input[name='enterpriseId']").val(id);
        //$("#enterpriseIdQuery").textbox("setValue",name);
        console.log(id)
        $("#clientName").textbox("setValue",name);
        $("#clientId").val(id);
        enterpriseCustomerDialog.dialog("close");

    }
    
    function searchSupplierEnterprise() {
        enterpriseSupplierDialog = $('#enterpriseSupplierDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toSearchDialog&target=enterpriseSupplierApply&type=supplier",
            width:850,
            height:500,
            cache:false,
            onClose : function() {

            }
        })
    }

    function choseSelect_enterpriseSupplierApply(id,name,target) {
        $("#supplierName").textbox("setValue",name);
        $("#supplierId").val(id);
        enterpriseSupplierDialog.dialog("close");
    }



    //选择产品
    function choseProduct() {
        // alert($('#clientName').val());
        if($('#ezuiFormDetail #clientName').val()==""){
            $.messager.show({
                msg : '请先选择货主', title : '提示'
            });
            return;
        }
        //ezuiDialogSpec.dialog('open');
        enterpriseProduct = $('#enterpriseProduct').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            left:400,
            top:200,
            width:550,
            height:170,
            href: sy.bp()+"/firstBusinessApplyController.do?toDialogProductAndSupplier",
            cache: false,
            buttons : '#ezuiBtn',
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        });

        ;

        $('#ezuiDialogSpec_PAS').dialog('destroy');	//ProductAndSupplier产品
        $('#ezuiDialogSupplierDetail').dialog('destroy');//ProductAndSupplier供应商
    }

    var  clearDialog=function () {
        $('#enterpriseProduct').dialog('close')
    }
    //查询产品
    // function searchProduct() {
    //     dataGridProduct.datagrid("load", {
    //         "productCode":$("#ezuiDialogSpec #productCode").val(),
    //         "productName":$("#ezuiDialogSpec #productName").val(),
    //         "productRegisterNo":$("#ezuiDialogSpec #registerNo").val(),
    //         "isUse":"1",
    //         "type":"CER"
    //     })
    // }
    
    function submitApply() {





        if(processType == 'edit'){
             var applyId = $("#applyId").val();
             var specsId;
             var customerid;
            // var  rowE  = ezuiDatagridDetail.datagrid('getSelected');
            $.ajax({
                url : sy.bp()+"/firstBusinessApplyController.do?getInfo",
                data : {
                    applyId : applyId ,
                },
                type : 'POST',
                dataType : 'JSON',
                async  :true,
                success : function(result){
                    // alert(result.obj.specsId+"====="+result.obj.customerid)
                    specsId = result.obj.specsId;
                    customerid = result.obj.customerid;
                    arr1.push(customerid);
                    arr.push(specsId);
                }
            });

        }


        if(processType == 'edit'){
            var rowMain = ezuiDatagrid.datagrid('getSelected');
            if(rowMain.firstState == '10' || rowMain.firstState =='40'){
                $.messager.show({
                    msg : '审核中与审核通过的申请无法修改', title : '提示'
                });
                return;
            }
        }
        if($("#clientId").val() == ""){
            $.messager.show({
                msg : "请选择委托客户", title : '<spring:message code="common.message.prompt"/>'
            });

            return;
        }
        if($("#productLine").combobox("getValue") == ""){
            $.messager.show({
                msg : "请选择产品线", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        // var row = ezuiDatagridDetail.datagrid('getSelected');

        if(arr.length==0){
            $.messager.show({
                msg : "请选择要首营的产品", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        /*if($("#supplierId").val() == ""){

            $.messager.show({
                msg : "请选择供应商", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }*/



        $.ajax({
            url : sy.bp()+"/firstBusinessApplyController.do?apply",
            data : {
                "id":"${firstBusinessApply.applyId}",
                "clientId":$("#clientId").val(),
                "supplierArr":arr1.join(","),
                "productArr":arr.join(","),

                "productLine":$("#productLine").combobox("getValue")

                },
            type : 'POST',
            dataType : 'JSON',
            async  :true,
            success : function(result){
                console.log(result);
                var msg='';
                try{
                    if(result.success){
                        msg = result.msg;
                        ezuiDatagrid1.datagrid('reload');
                        ezuiDialog.dialog('close');
                    }else{
                        msg = '<font color="red">' + result.msg + '</font>';
                    }
                }catch (e) {
                    //msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                    msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
                } finally {
                    $.messager.show({
                        msg : msg, title : '<spring:message code="common.message.prompt"/>'
                    });
                    $.messager.progress('close');
                }
            }
        });
    }
    arr = new Array();
    arr1 = new Array();
    // //选择
    // function choseSelect() {
    //     var rows = dataGridProduct.datagrid("getChecked");
    //     if(rows){
    //         for(var i=0;i<rows.length;i++){
    //             if(arr.indexOf(rows[i].specsId)==-1){
    //                 ezuiDatagridDetail.datagrid("appendRow",{
    //                     "productApplyId":"",
    //                     "applyId":"",
    //                     "specsId":rows[i].specsId,
    //                     "isCheck":"",
    //                     "isOperate":"",
    //                     "isCold":"",
    //                     "createId":"",
    //                     "createDate":"",
    //                     "editId":"",
    //                     "editDate":"",
    //                     "isUse":"",
    //                     "productCode":rows[i].productCode,
    //                     "productName":rows[i].productName,
    //                     "specsName":rows[i].specsName,
    //                     "productModel":rows[i].productModel
    //                 });
    //                 arr.push(rows[i].specsId);
    //             }
    //
    //         }
    //
    //     }
    //     enterpriseProduct.dialog("close");
    // }
    
    // function clearApply() {
    //     $("#clientName").textbox("setValue","");
    //     $("#clientId").val("");
    //
    //     $("#supplierName").textbox("setValue","");
    //     $("#supplierId").val("");
    //
    //     ezuiDatagridDetail.datagrid("reload",{"applyId":"empty"});
    //     arr.splice(0,arr.length);
    //     arr1.splice(0,arr.length);
    // }
    //货主弹窗
    // .dialog('close');
    //打开货主弹窗
    function searchClient() {
        // if(ezuiDialogClientDetail){
        //     ezuiDialogClientDetail.dialog('open');
        // }
        ezuiDialogClientDetail = $('#ezuiDialogClientDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        })
    }

    //选择货主
    function choseClientSelect() {
        var row = clientDatagrid.datagrid("getSelected");
        if(row){

            $("#clientId").val(row.customerid);
            $("#clientName").textbox("setValue",row.descrC);
            $("#cli_enterpriseId").val(row.enterpriseId);


            console.log(row.customerid);
            $("#productLine").combobox({
                panelHeight: 'auto',
                url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
                valueField:'id',
                textField:'value',
                onLoadSuccess:function () {

                }
            })
            console.log(row.customerid+'结束');
            ezuiDialogClientDetail.dialog('close');
        }
    }

    // //双击选中供应商
    // function choseSupplierSelect() {
    //     var row = supplierDatagrid.datagrid("getSelected");
    //     console.log(row);
    //     if(row){
    //         $("#supplierId").val(row.customerid);
    //         $("#supplierName").textbox("setValue",row.descrC)
    //         // $("#productLine").combobox({
    //         //     url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
    //         //     valueField:'id',
    //         //     textField:'value',
    //         //     onLoadSuccess:function () {
    //         //
    //         //     }
    //         // })
    //         ezuiDialogSupplierDetail.dialog('close');
    //     }
    // }

    // //供应商弹窗搜索功能
    // function doSearchSupplier() {
    //     supplierDatagrid.datagrid('load', {
    //         enterpriseName : $('#qiyemingcheng1').val(),
    //         enterpriseNo : $('#qiyexinxidaima').val(),
    //         descrC : $('#kehumingcheng').val(),
    //         customerid : $('#kehudaima').val(),
    //         activeFlag : '1',
    //         isUse : '1',
    //         customerType:'VE'
    //     });
    // }
    //搜索打开供应商弹窗
    // function searchSupplier() {
    //     if(ezuiDialogSupplierDetail){
    //         ezuiDialogSupplierDetail.dialog('open');
    //
    //     }
    // }

    //查询货主信息条件
    function doSearchClient() {
        // alert($('#kehumingcehngD').val()+"===="+$('#kehudaimaD').val());
        // console.log($('#kehumingcehngD').val());

        clientDatagrid.datagrid('load', {

            // enterpriseName : $('#qiyemingcheng2').val(),
            // enterpriseNo : $('#qiyexinxidaima1').val(),
            descrC : $('#kehumingcehngD').val(),
            customerid : $('#kehudaimaD').val(),
            activeFlag : '1',
            isUse : '1',
            customerType:'OW'
        });
    }



</script>
</body>
</html>
