<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form id='ezuiFormDetail' method='post'>
    <fieldset>
        <legend>产品首营信息</legend>
            <input type='hidden' id='applyId' name='applyId' value="${firstBusinessApply.applyId}"/>
            <table>
                <tr>
                    <th>委托客户</th>
                    <td>
                        <input type='text' value="${firstBusinessApply.clientName}" id='clientName' class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="clientId" id="clientId" value="${firstBusinessApply.clientId}"/>
                    </td>
                    <th>产品线</th>
                    <td>
                        <input id="productLine" name="productLine" type="text"/>
                    </td>
                    <th>供应客户</th>
                    <td>
                        <input type='text' value="${firstBusinessApply.supplierName}" id='supplierName' class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="supplierId" id="supplierId" value="${firstBusinessApply.supplierId}"/>
                    </td>
                    <td>

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
    <a onclick='submitApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>提交申请</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'>清空选择</a>
</div>
<table id='ezuiDatagridDetail' ></table>

<div id='enterpriseCustomerDialog' style='padding: 10px;'>

</div>

<div id='enterpriseSupplierDialog' style='padding: 10px;'>

</div>

<!--产品查询列表dialog -->
<div id='ezuiDialogSpec' style='padding: 10px;'>
    <div id='productToolbar' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>产品注册证信息</legend>
            <table>
                <tr>
                    <th>产品代码</th>
                    <td><input type='text' id='productCode' class='easyui-textbox' size='16' data-options=''/></td>
                    <th>产品名称</th>
                    <td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
                    <td>
                        <a onclick='searchProduct();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridProduct">

    </table>
</div>

<script>
    var ezuiDatagridDetail;
    var enterpriseCustomerDialog;
    var enterpriseSupplierDialog;
    var ezuiDialogSpec;
    var dataGridProduct;
    var arr = new Array();

    $(function () {
        ezuiDatagridDetail = $("#ezuiDatagridDetail").datagrid({
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
            idField : 'productApplyId',
            columns : [[
                {field: 'productApplyId',title:'主键',hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '25%'},
                {field: 'productName',title: '产品名称',width: '25%'},
                {field: 'specsName',title: '规格名称' ,width: '25%'},
                {field: 'productModel',title: '产品型号',width: '25%'}
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
        })

        dataGridProduct = $("#dataGridProduct").datagrid({
            url : sy.bp()+'/gspProductRegisterSpecsController.do?showDatagrid',
            method:'POST',
            toolbar : '#productToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            fit:true,
            pagination:true,
            queryParams:{

            },
            rownumbers:true,
            idField : 'specsId',
            columns : [[
                {field: 'ck',checkbox:true },
                {field: 'specsId',title: '主键' ,hidden:true},
                {field: 'specsName',title: '规格名称' ,width: '25%'},
                {field: 'productCode',title: '产品代码' ,width: '25%'},
                {field: 'productName',title: '产品名称',width: '25%'},
                {field: 'productModel',title: '产品型号',width: '25%'}
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid("resize",{height:540});
                $.ajax({
                    url : '/firstBusinessApplyController.do?showSpecsDatagrid',
                    data : {'applyId':"<c:choose><c:when test="${firstBusinessApply.applyId == null}">'empty'</c:when><c:otherwise>'${firstBusinessApply.applyId}'</c:otherwise></c:choose>"},
                    type : 'POST',
                    dataType : 'JSON',
                    success : function(result){
                        //console.log(result);
                        if(result){
                            for(var i=0;i<result.rows.length;i++){
                                //console.log(result.rows[i]);
                                dataGridProduct.datagrid("selectRecord",result.rows[i].specsId);
                            }
                        }
                    }
                });
            }
        })


        ezuiDialogSpec = $('#ezuiDialogSpec').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        $("#clientName").textbox({
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchCustomerEnterprise();
                }
            }]
        })

        $("#supplierName").textbox({
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchSupplierEnterprise();
                }
            }]
        })

        <c:choose>
            <c:when test="${firstBusinessApply.productLine != null}">
                $("#productLine").combobox({
                    url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId',
                    valueField:'id',
                    textField:'text',
                    onLoadSuccess:function () {
                        $("#productLine").combobox("setValue",'${firstBusinessApply.productLine}');
                    }
                })
            </c:when>
            <c:otherwise>
                $("#productLine").combobox();
            </c:otherwise>
        </c:choose>
    })

    
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

        $("#productLine").combobox({
            url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&enterpriseId='+id,
            valueField:'id',
            textField:'value',
            onLoadSuccess:function () {

            }
        })
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
    
    function choseProduct() {
        ezuiDialogSpec.dialog('open');
    }
    
    function searchProduct() {
        
    }
    
    function submitApply() {
        if(arr.length==0){
            $.messager.show({
                msg : "请选择要首营的产品", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        if($("#clientId").val() == ""){
            $.messager.show({
                msg : "请选择委托客户", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        if($("#supplierId").val() == ""){
            $.messager.show({
                msg : "请选择供应商", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        $.ajax({
            url : sy.bp()+"/firstBusinessApplyController.do?apply",
            data : {"id":"${firstBusinessApply.applyId}","clientId":$("#clientId").val(),"supplierId":$("#supplierId").val(),"productArr":arr.join(",")},type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                console.log(result);
                var msg='';
                try{
                    if(result.success){
                        msg = result.msg;
                        ezuiDatagrid.datagrid('reload');
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
    
    function choseSelect() {
        var rows = dataGridProduct.datagrid("getChecked");
        if(rows){
            for(var i=0;i<rows.length;i++){
                arr.push(rows[i].specsId);
                ezuiDatagridDetail.datagrid("appendRow",{
                    "productApplyId":"",
                    "applyId":"",
                    "specsId":rows[i].specsId,
                    "isCheck":"",
                    "isOperate":"",
                    "isCold":"",
                    "createId":"",
                    "createDate":"",
                    "editId":"",
                    "editDate":"",
                    "isUse":"",
                    "productCode":rows[i].productCode,
                    "productName":rows[i].productName,
                    "specsName":rows[i].specsName,
                    "productModel":rows[i].productModel
                });
            }

        }
        ezuiDialogSpec.dialog("close");
    }
    
    function clearApply() {
        $("#clientName").textbox("setValue","");
        $("#clientId").val("");

        $("#supplierName").textbox("setValue","");
        $("#supplierId").val("");

        ezuiDatagridDetail.datagrid("reload",{"applyId":"empty"});
        arr.splice(0,arr.length);
    }

</script>
</body>
</html>
