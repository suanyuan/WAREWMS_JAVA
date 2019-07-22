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
                    <th>供应商</th>
                    <td>
                        <input type='text' value="${firstBusinessApply.supplierName}" id='supplierName' class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="supplierId" id="supplierId" value="${firstBusinessApply.supplierId}"/>
                    </td>
                    <th>产品线</th>
                    <td>
                        <input id="productLine" name="productLine" type="text"/>
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
    <!--<a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'>清空选择</a>-->
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
                    <td><input type='text' id='productCode'  size='16' data-options=''/></td>
                    <th>产品名称</th>
                    <td><input type='text' id='productName'  size='16' data-options=''/></td>
                    <th>注册证号</th>
                    <td><input type='text' id='registerNo'  size='16' data-options=''/></td>
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

<div id='ezuiDialogClientDetail' style='padding: 10px;'>
    <div id='clientTB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input type='text' id='kehudaima1' class='easyui-textbox' data-options=''/></td>
                    <th>客户名称：</th><td><input type='text' id='kehumingcehng1' class='easyui-textbox' data-options=''/></td>
                </tr>
                <tr>
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

<div id='ezuiDialogSupplierDetail' style='padding: 10px;'>
    <div id='TB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input id="kehudaima" type='text'  class='easyui-textbox' data-options=''/></td>
                    <th>客户名称：</th><td><input id="kehumingcheng" type='text'  class='easyui-textbox' data-options=''/></td>
                </tr>
                <tr>
                    <%--<th>企业信息代码：</th><td><input id="qiyexinxidaima" type='text'  class='easyui-textbox' data-options=''/></td>
                    <th>企业名称：</th><td><input id="qiyemingcheng1" type='text'  class='easyui-textbox' data-options=''/></td>--%>
                    <%--<td>
                        <input type='text' style="width: 170px;"/>
                        <input type="hidden" class="easyui-textvalue" name="enterpriseId">
                        <!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
                    </td>--%>
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

<script>
    var ezuiDatagridDetail;
    var enterpriseCustomerDialog;
    var enterpriseSupplierDialog;
    var ezuiDialogSpec;
    var dataGridProduct;
    var arr = new Array();
    var ezuiDialogClientDetail;
    var ezuiDialogSupplierDetail;

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

        //供应商
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
                isUse : '1',
                customerType:'VE'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'clientId',
            columns : [[
                {field: 'customerType',	title: '客户类型 ',	width: 100,formatter:function(value,rowData,rowIndex){
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
                {field: 'activeFlag',		title: '是否合作 ',	width: 100,formatter:function(value,rowData,rowIndex){
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }},
                {field: 'customerid',		title: '客户代码',	width: 150 },
                {field: 'descrC',		title: '客户名称',	width: 150 },
                {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 80 },
                {field: 'shorthandName',		title: '简称 ',	width: 85 },
                {field: 'enterpriseName',		title: '企业名称 ',	width: 80 }
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

        $("#clientName").textbox({
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    //searchCustomerEnterprise();
                    searchClient();
                }
            }]
        })

        $("#supplierName").textbox({
            value:"${gspReceiving.supplierId}",
            width:150,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchSupplier();
                }
            }]
        });

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
        dataGridProduct.datagrid("load",{"productRegisterNo":$("#registerNo").val()})
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
            data : {"id":"${firstBusinessApply.applyId}","clientId":$("#clientId").val(),"supplierId":$("#supplierId").val(),"productArr":arr.join(","),"productLine":$("#productLine").combobox("getValue")},type : 'POST', dataType : 'JSON',async  :true,
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

    //打开货主弹窗
    function searchClient() {
        if(ezuiDialogClientDetail){
            ezuiDialogClientDetail.dialog('open');
        }

    }

    //选择货主
    function choseClientSelect() {
        var row = clientDatagrid.datagrid("getSelected");
        if(row){

            $("#clientId").val(row.customerid);
            $("#clientName").textbox("setValue",row.descrC)

            ezuiDialogClientDetail.dialog('close');
        }
    }

    //双击选中供应商
    function choseSupplierSelect() {
        var row = supplierDatagrid.datagrid("getSelected");
        console.log(row);
        if(row){
            $("#supplierId").val(row.customerid);
            $("#supplierName").textbox("setValue",row.descrC)
            $("#productLine").combobox({
                url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
                valueField:'id',
                textField:'value',
                onLoadSuccess:function () {

                }
            })
            ezuiDialogSupplierDetail.dialog('close');
        }
    }

    //供应商弹窗搜索功能
    function doSearchSupplier() {
        supplierDatagrid.datagrid('load', {
            enterpriseName : $('#qiyemingcheng1').val(),
            enterpriseNo : $('#qiyexinxidaima').val(),
            descrC : $('#kehumingcheng').val(),
            customerid : $('#kehudaima').val(),
            isUse : '1',
            customerType:'VE'
        });
    }
    //搜索打开供应商弹窗
    function searchSupplier() {
        if(ezuiDialogSupplierDetail){
            ezuiDialogSupplierDetail.dialog('open');
        }

    }

    //查询货主信息条件
    function doSearchClient() {
        clientDatagrid.datagrid('load', {
            enterpriseName : $('#qiyemingcheng2').val(),
            enterpriseNo : $('#qiyexinxidaima1').val(),
            descrC : $('#kehumingcehng1').val(),
            customerid : $('#kehudaima1').val(),
            isUse : '1',
            customerType:'OW'
        });
    }



</script>
</body>
</html>
