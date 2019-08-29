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

<form id='ezuiFormDetail1' method='post'>
    <fieldset>
        <legend>产品首营信息</legend>
            <input type='hidden' id='applyId1' name='applyId1' value="${firstBusinessApply1.applyId}"/>
            <table>
                <tr>
                    <th>委托客户</th>
                    <td>
                        <input type='text' value="${firstBusinessApply1.clientName}" id='clientName1' class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="clientId1" id="clientId1" value="${firstBusinessApply1.clientId}"/>
                    </td>
                    <th>供应商</th>
                    <td>
                        <input type='text' value="${firstBusinessApply1.supplierName}" id='supplierName1' class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="supplierId1" id="supplierId1" value="${firstBusinessApply1.supplierId}"/>
                    </td>
                    <th>产品线</th>
                    <td>
                        <input id="productLine1" name="productLine1"  data-options='required:true'  type="text"/>
                    </td>

                    <th>产品</th>
                    <td>
                        <input type='text'  id='productName2' value="${firstBusinessApply1.productName}" class='easyui-textbox' data-options='required:true,width:200'/>
                        <input type="hidden" name="specsId1" id="specsId1" value="${firstBusinessApply1.specsId}" />
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
    <%--<a onclick='choseProduct();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>选择产品</a>--%>
    <%--<a onclick='delProduct();'   class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
    <a onclick='submitApply1();'  class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>提交申请</a>
    <!--<a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>提交审核</a>
    <a onclick='clearApply();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'>清空选择</a>-->
</div>
<table id='ezuiDatagridDetail1' ></table>

<%--<div id='enterpriseCustomerDialog' style='padding: 10px;'>--%>

<%--</div>--%>

<%--<div id='enterpriseSupplierDialog' style='padding: 10px;'>--%>

<%--</div>--%>

<%--&lt;%&ndash;选择供应商和产品&ndash;%&gt;--%>
<%--<div id='enterpriseProduct' style='padding: 10px;'>--%>

<%--</div>--%>
<div id='ezuiBtn' style="display: none">
    <a onclick='doSubmitAddress();' id='ezuiBtn_commit1' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='clearDialog();' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<%--选择供应商和产品--%>
<%--<div id='enterpriseProduct' style='padding: 10px;'>--%>
    <%--<form>--%>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<th>产品基础信息</th>--%>
            <%--<td>--%>
                <%--<input type='text'  id='product' class='easyui-textbox' data-options='required:true,width:200'/>--%>
                <%--<input type="hidden" name="specId" id="specId1" />--%>
            <%--</td>--%>
            <%--<a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>

        <%--</tr>--%>

        <%--<tr>--%>
            <%--<th>供应商</th>--%>
            <%--<td>--%>
                <%--<input type='text'  id='supplierName1' class='easyui-textbox' data-options='required:true,width:200'/>--%>
                <%--<input type="hidden" name="supplierId1" id="supplierId1" />--%>
            <%--</td>--%>

        <%--</tr>--%>
    <%--</table>--%>
    <%--</form>--%>
<%--</div>--%>
<!--产品查询列表dialog -->
<%--<div id='ezuiDialogSpec' style='padding: 10px;'>--%>
    <%--<div id='productToolbar' class='datagrid-toolbar' style=''>--%>
        <%--<fieldset>--%>
            <%--<legend>产品注册证信息</legend>--%>
            <%--<table>--%>
                <%--<tr>--%>
                    <%--<th>产品代码</th>--%>
                    <%--<td><input type='text' id='productCode'  size='16' data-options=''/></td>--%>
                    <%--<th>产品名称</th>--%>
                    <%--<td><input type='text' id='productName'  size='16' data-options=''/></td>--%>
                    <%--<th>注册证号</th>--%>
                    <%--<td><input type='text' id='registerNo'  size='16' data-options=''/></td>--%>
                    <%--<td>--%>
                        <%--<a onclick='searchProduct();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
                        <%--<a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</fieldset>--%>
    <%--</div>--%>
    <%--<table id="dataGridProduct">--%>

    <%--</table>--%>
<%--</div>--%>
<%--货主--%>
<div id='ezuiDialogClientDetail1' style='padding: 10px;display: none'>
    <div id='client1TB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input type='text' id='kehudaimaE' class='easyui-textbox' data-options='width:200'/></td>
                    <th>客户名称：</th><td><input type='text' id='kehumingcehngE' class='easyui-textbox' data-options='width:200'/></td>

                    <td>
                        <a onclick='doSearchClient1();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseClientSelect1()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridClientDetail1">

    </table>
</div>
<%--供应商--%>
<div id='ezuiDialogSupplierDetail1' style='padding: 10px;display: none' >
    <div id='TB1' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>供应商信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input id="kehudaimaS" type='text'   class='easyui-textbox' data-options='width:200'/></td>
                    <th>客户名称：</th><td><input id="kehumingchengS" type='text'   class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchSupplier1();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSupplierSelect1()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>

            </table>
        </fieldset>

    </div>
    <table id="dataGridSupplierDetail1">

    </table>
</div>

<!--产品查询列表dialog -->
<div id='ezuiDialogSpec1' style='padding: 10px;display: none' >
    <div id='productToolbar1' class='datagrid-toolbar' style='display: none'>
        <fieldset>
            <legend>产品注册证信息</legend>
            <table>
                <tr>
                    <th>产品代码</th>
                    <td><input type='text' id='productCodeE' class='easyui-textbox'  data-options='width:200'/></td>
                    <th>产品名称</th>
                    <td><input type='text' id='productNameE'  class='easyui-textbox' data-options='width:200'/>
                        <a onclick='searchProductE();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseProductSelectE()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                    <%--<td>--%>
                    <%--<a onclick='searchProduct();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
                    <%--<a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                    <%--</td>--%>
                </tr>
                <tr>
                    <th>规格</th>
                    <td><input type='text' id='specsNameE' class='easyui-textbox'  data-options='width:200'/></td>

                    <th>注册证号</th>
                    <td><input type='text' id='registerNoE'  class='easyui-textbox' data-options='width:200'/></td>

                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridProduct1">

    </table>
</div>
<%--<div id='ezuiDialogSupplierDetail' style='padding: 10px;'>--%>
    <%--<div id='TB' class='datagrid-toolbar' style=''>--%>
        <%--<fieldset>--%>
            <%--<legend>货主信息</legend>--%>
            <%--<table>--%>
                <%--<tr>--%>
                    <%--<th>客户代码：</th><td><input id="kehudaima" type='text'  class='easyui-textbox' data-options=''/></td>--%>
                    <%--<th>客户名称：</th><td><input id="kehumingcheng" type='text'  class='easyui-textbox' data-options=''/></td>--%>
                <%--</tr>--%>
                <%--<tr>--%>
                    <%--&lt;%&ndash;<th>企业信息代码：</th><td><input id="qiyexinxidaima" type='text'  class='easyui-textbox' data-options=''/></td>--%>
                    <%--<th>企业名称：</th><td><input id="qiyemingcheng1" type='text'  class='easyui-textbox' data-options=''/></td>&ndash;%&gt;--%>
                    <%--&lt;%&ndash;<td>--%>
                        <%--<input type='text' style="width: 170px;"/>--%>
                        <%--<input type="hidden" class="easyui-textvalue" name="enterpriseId">--%>
                        <%--<!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->--%>
                    <%--</td>&ndash;%&gt;--%>
                    <%--<td>--%>
                        <%--<a onclick='doSearchSupplier();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
                        <%--<a onclick='choseSupplierSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
            <%--</table>--%>
        <%--</fieldset>--%>
        <%--&lt;%&ndash; <fieldset>--%>
             <%--<legend>供应商信息</legend>--%>
             <%--<table>--%>
                 <%--<tr>--%>
                     <%--<th>供应商</th>--%>
                     <%--<td><input type='text' id='supplierId' class='easyui-textbox' data-options='width:200'/></td>--%>
                     <%--<th>类型</th>--%>
                     <%--<td><input type='text' id='operationType' class='easyui-textbox' data-options='width:200'/></td>--%>
                     <%--<td>--%>
                         <%--<a onclick='doSearchSupplier();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
                         <%--<a onclick='choseSupplierSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                     <%--</td>--%>
                 <%--</tr>--%>
             <%--</table>--%>
         <%--</fieldset>&ndash;%&gt;--%>
    <%--</div>--%>
    <%--<table id="dataGridSupplierDetail">--%>

    <%--</table>--%>
<%--</div>--%>
<div id="enterpriseDialog">

</div>
<script>
    var ezuiDatagridDetail1; //主页
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
    // var enterpriseProduct;


    // Array.prototype.indexOf = function(val) { //prototype 给数组添加属性
    //     for (var i = 0; i < this.length; i++) { //this是指向数组，this.length指的数组类元素的数量
    //         if (this[i] == val) return i; //数组中元素等于传入的参数，i是下标，如果存在，就将i返回
    //     }
    //     return -1;
    // };
    // Array.prototype.remove = function(val) {  //prototype 给数组添加属性
    //     var index = this.indexOf(val); //调用index()函数获取查找的返回值
    //     if (index > -1) {
    //         this.splice(index, 1); //利用splice()函数删除指定元素，splice() 方法用于插入、删除或替换数组的元素
    //     }
    // };

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
            queryParams:{'applyId':<c:choose><c:when test="${firstBusinessApply1.applyId == null}">'empty'</c:when><c:otherwise>'${firstBusinessApply1.applyId}'</c:otherwise></c:choose>},
            pagination:true,
            rownumbers:true,
            idField : 'productApplyId',
            columns : [[
                {field: 'productApplyId',title:'主键',hidden:true},
                {field: 'specsId',title:'主键',hidden:true},
                {field: 'customerid',title:'主键',hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '15%'},
                {field: 'productName',title: '产品名称',width: '20%'},
                {field: 'specsName',title: '规格名称' ,width: '15%'},
                {field: 'productModel',title: '产品型号',width: '15%'},
                {field: 'supplierName',title: '供应商',width: '20%'},
                {field: 'productRegisterNo',title: '注册证编号',width: '20%'},
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



        <%--//产品--%>
        <%--dataGridProduct = $("#dataGridProduct").datagrid({--%>
            <%--url : sy.bp()+'/gspProductRegisterSpecsController.do?showDatagrid',--%>
            <%--method:'POST',--%>
            <%--toolbar : '#productToolbar',--%>
            <%--title: '',--%>
            <%--pageSize : 50,--%>
            <%--pageList : [50, 100, 200],--%>
            <%--border: false,--%>
            <%--fitColumns : false,--%>
            <%--nowrap: true,--%>
            <%--striped: true,--%>
            <%--collapsible:false,--%>
            <%--fit:true,--%>
            <%--pagination:true,--%>
            <%--queryParams:{--%>
                <%--isUse : '1',--%>
                <%--type:'CER'--%>
            <%--},--%>
            <%--rownumbers:true,--%>
            <%--idField : 'specsId',--%>
            <%--columns : [[--%>
                <%--{field: 'ck',checkbox:true },--%>
                <%--{field: 'specsId',title: '主键' ,hidden:true},--%>
                <%--{field: 'productCode',title: '产品代码' ,width: '15%'},--%>
                <%--{field: 'productName',title: '产品名称',width: '40%'},--%>
                <%--{field: 'specsName',title: '规格名称' ,width: '10%'},--%>
                <%--{field: 'productRegisterNo',title: '产品注册证',width: '20%'},--%>
                <%--{field: '_operate',		title: '操作',	width: '10%',--%>
                    <%--formatter: formatOper--%>
                <%--}--%>
            <%--]],--%>
            <%--onDblClickCell: function(index,field,value){--%>

            <%--},--%>
            <%--onRowContextMenu : function(event, rowIndex, rowData) {--%>

            <%--},--%>
            <%--onSelect: function(rowIndex, rowData) {--%>

            <%--},--%>
            <%--onLoadSuccess:function(data){--%>
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
            <%--}--%>
        <%--});--%>
        <%--//注册证--%>
        <%--ezuiDialogSpec = $('#ezuiDialogSpec').dialog({--%>
            <%--modal : true,--%>
            <%--title : '<spring:message code="common.dialog.title"/>',--%>
            <%--width:850,--%>
            <%--height:500,--%>
            <%--cache: false,--%>
            <%--onClose : function() {--%>

            <%--}--%>
        <%--}).dialog('close');--%>

        <%--dialogEnterprise = $('#dialogEnterprise').dialog({--%>
            <%--modal : true,--%>
            <%--title : '<spring:message code="common.dialog.title"/>',--%>
            <%--fit:true,--%>
            <%--href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",--%>
            <%--cache: false,--%>
            <%--onClose : function() {--%>
                <%--//  ezuiFormClear(ezuiForm);--%>
            <%--}--%>
        <%--}).dialog('close');--%>
        <%--enterpriseProduct = $('#enterpriseProduct').dialog({--%>
            <%--modal : true,--%>
            <%--title : '<spring:message code="common.dialog.title"/>',--%>
            <%--width:850,--%>
            <%--height:500,--%>
            <%--cache: false,--%>
            <%--onClose : function() {--%>
                <%--ezuiFormClear(ezuiForm);--%>
            <%--}--%>
        <%--}).dialog('close');--%>







        //货主弹窗列表
        clientDatagrid1 = $("#dataGridClientDetail1").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#client1TB',
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
                choseClientSelect1();
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





        //供应商
        supplierDatagrid1 = $("#dataGridSupplierDetail1").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#TB1',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                customerType:'VE',
                activeFlag : '1',
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
                choseSupplierSelect1();
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




        $("#clientName1").textbox({
            icons:[{
                value:'${firstBusinessApply1.clientName}',
                iconCls:'icon-search',
                handler: function(e){
                    //searchCustomerEnterprise();
                    searchClient1();
                }
            }]
        });

        $("#supplierName1").textbox({
            value:"${firstBusinessApply1.supplierName}",
            width:150,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchSupplier1();
                }
            }]
        });

        $("#productName2").textbox({
            value:"${firstBusinessApply1.productName}",
            width:150,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchProductdialog();
                }
            }]
        });


        //产品
        dataGridProduct1 = $("#dataGridProduct1").datagrid({
            url : sy.bp()+'/gspProductRegisterSpecsController.do?showDatagrid',
            method:'POST',
            toolbar : '#productToolbar1',
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
            singleSelect:true,
            queryParams:{
                isUse : '1',
                type:'CER',
                // supplierId:$("#supplierId").val(),
            },
            rownumbers:true,
            idField : 'specsId',
            columns : [[
                // {field: 'ck',checkbox:true },
                {field: 'specsId',title: '主键' ,hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '15%'},
                {field: 'productName',title: '产品名称',width: '40%'},
                {field: 'specsName',title: '规格' ,width: '10%'},
                {field: 'productRegisterNo',title: '产品注册证',width: '20%'},
                {field: '_operate',		title: '操作',	width: '10%',
                    formatter: formatOper
                }
            ]],
            onDblClickCell: function(index,field,value){
                choseProductSelectE();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
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
                modal : true,
                title : '<spring:message code="common.dialog.title"/>',
                width:850,
                height:500,
                cache: false,
                onClose : function() {
                    // ezuiFormClear(ezuiForm);
                    // $(this).dialog('destroy');
                }
            });
        }

        // console.log(customerid);
        // $("#productLine").combobox({
        //     panelHeight: 'auto',
        //     url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid,
        //     valueField:'id',
        //     textField:'value',
        //     onLoadSuccess:function () {
        //
        //     }
        // });


        //产品线1
        var customerid1 = $("#clientId1").val();
        <c:choose>
            <c:when test="${firstBusinessApply1.productline != null}">

                $("#productLine1").combobox({
                    panelHeight: 'auto',
                    url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+customerid1,
                    valueField:'id',
                    textField:'value',
                    onLoadSuccess:function () {
                        $("#productLine1").combobox("setValue",'${firstBusinessApply1.productline}');
                    }
                })
            </c:when>
            <c:otherwise>
                $("#productLine1").combobox({panelHeight: 'auto'
                });
            </c:otherwise>
        </c:choose>
    });


    // function delProduct(){
    //     console.log(1111111111111111);
    //
    //     var row =  ezuiDatagridDetail.datagrid('getSelected');
    //
    //
    //     arr.remove(row.specsId);
    //     arr1.remove(row.customerid);
    //     var rowIndex = ezuiDatagridDetail.datagrid('getRowIndex', row);
    //     ezuiDatagridDetail.datagrid('deleteRow', rowIndex);
    //
    //
    //         // row.specsId = '';
    //         // row.productCode ='';
    //         // row.productName ='';
    //         // row.specsName ='';
    //         // row.productModel ='';
    //         // row.descrC ='';
    //         // row.supplierId ='';
    //
    // }





    function operateGrid1(id) {
        processType = 'edit';
        $('#enterpriseDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspProductRegisterSpecsController.do?toAdd&specsId="+id,
            width:1050,
            height:550,
            cache:false,
            onClose : function() {

            }
        })
    }
    function formatOper(value,row,index){
        return "<a onclick=\"operateGrid1('"+row.specsId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
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



    <%--//选择产品--%>
    <%--function choseProduct() {--%>
        <%--//ezuiDialogSpec.dialog('open');--%>
        <%--enterpriseProduct = $('#enterpriseProduct').dialog({--%>
            <%--modal : true,--%>
            <%--title : '<spring:message code="common.dialog.title"/>',--%>
            <%--left:400,--%>
            <%--top:200,--%>
            <%--width:500,--%>
            <%--height:170,--%>
            <%--href: sy.bp()+"/firstBusinessApplyController.do?toDialogProductAndSupplier",--%>
            <%--cache: false,--%>
            <%--buttons : '#ezuiBtn',--%>
            <%--onClose : function() {--%>
                <%--ezuiFormClear(ezuiForm);--%>
            <%--}--%>
        <%--})--%>

        <%--//$('#ezuiDialogSupplierDetail').dialog('destroy');--%>
        <%--//$('#dataGridProduct').dialog('destroy');--%>
    <%--}--%>

    // var  clearDialog=function () {
    //     $('#enterpriseProduct').dialog('close')
    // }
    //查询产品
    function searchProductE() {
        dataGridProduct1.datagrid("load", {
            "productCode":$("#ezuiDialogSpec1 #productCodeE").val(),
            "productName":$("#ezuiDialogSpec1 #productNameE").val(),
            "productRegisterNo":$("#ezuiDialogSpec1 #registerNoE").val(),
            "specsName":$("#ezuiDialogSpec1 #specsNameE").val(),
            "isUse":"1",
            "type":"CER"
        })
    }
    //双击选中产品
    function choseProductSelectE() {
        var row = dataGridProduct1.datagrid("getSelected");
        console.log(row);
        if(row){
            $("#specsId1").val(row.specsId);
            $("#productName2").textbox("setValue",row.productName);
            // $("#productLine").combobox({
            //     url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
            //     valueField:'id',
            //     textField:'value',
            //     onLoadSuccess:function () {
            //
            //     }
            // })
            ezuiDialogSpec1.dialog('close');
        }
    }
    function submitApply1() {
        if(processType == 'edit'){
            var rowMain = ezuiDatagrid.datagrid('getSelected');
            if(rowMain.firstState == '10' || rowMain.firstState =='40'){
                $.messager.show({
                    msg : '审核中与审核通过的申请无法修改', title : '提示'
                });
                return;
            }
        }
        if($("#clientId1").val() == ""){
            $.messager.show({
                msg : "请选择委托客户", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        if($("#productLine1").combobox("getValue") == ""){
            $.messager.show({
                msg : "请选择产品线", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        // var row = ezuiDatagridDetail.datagrid('getSelected');

        if($("#specsId1").val()==""){
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
                "id":"${firstBusinessApply1.applyId}",
                "clientId":$("#clientId1").val(),
                "supplierArr":$("#supplierId1").val(),
                "productArr":$("#specsId1").val(),
                "productLine":$("#productLine1").combobox("getValue")

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

    
    function clearApply() {
        $("#clientName").textbox("setValue","");
        $("#clientId").val("");

        $("#supplierName").textbox("setValue","");
        $("#supplierId").val("");

        ezuiDatagridDetail.datagrid("reload",{"applyId":"empty"});
        arr.splice(0,arr.length);
        arr1.splice(0,arr.length);
    }



    //选择货主
    function choseClientSelect1() {
        var row = clientDatagrid1.datagrid("getSelected");
        if(row){
            $("#clientId1").val(row.customerid);
            $("#clientName1").textbox("setValue",row.descrC)
            console.log(row.customerid);
            $("#productLine1").combobox({
                panelHeight: 'auto',
                url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
                valueField:'id',
                textField:'value',
                onLoadSuccess:function () {

                }
            })
            console.log(row.customerid+'结束');
            ezuiDialogClientDetail1.dialog('close');
        }
    }

    //双击选中供应商
    function choseSupplierSelect1() {
        var row = supplierDatagrid1.datagrid("getSelected");
        console.log(row);
        if(row){
            $("#supplierId1").val(row.customerid);
            $("#supplierName1").textbox("setValue",row.descrC)
            // $("#productLine").combobox({
            //     url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
            //     valueField:'id',
            //     textField:'value',
            //     onLoadSuccess:function () {
            //
            //     }
            // })
            ezuiDialogSupplierDetail1.dialog('close');
        }
    }

    //供应商弹窗搜索功能
    function doSearchSupplier1() {
        supplierDatagrid1.datagrid('load', {
            // enterpriseName : $('#qiyemingcheng1').val(),
            // enterpriseNo : $('#qiyexinxidaima').val(),
            descrC : $('#kehumingchengS').val(),
            customerid : $('#kehudaimaS').val(),
            activeFlag : '1',
            isUse : '1',
            customerType:'VE'
        });
    }
    //打开货主弹窗
    function searchClient1() {
        // if(ezuiDialogClientDetail1){
        //     ezuiDialogClientDetail1.dialog('open');
        // }
        //货主弹窗
        ezuiDialogClientDetail1 = $('#ezuiDialogClientDetail1').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        });
    }

    //搜索打开供应商弹窗
    function searchSupplier1() {
        // if(ezuiDialogSupplierDetail1){
        //     ezuiDialogSupplierDetail1.dialog('open');
        //
        // }
        //供应商弹窗
        ezuiDialogSupplierDetail1 = $('#ezuiDialogSupplierDetail1').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        });
    }

    // //搜索打开产品弹窗
    // function searchProduct1() {
    //     if(ezuiDialogProductDetail){
    //         ezuiDialogProductDetail.dialog('open');
    //
    //     }
    // }
    //searchProduct1
    //查询货主信息条件
    function doSearchClient1() {
        clientDatagrid1.datagrid('load', {
            // enterpriseName : $('#qiyemingcheng2').val(),
            // enterpriseNo : $('#qiyexinxidaima1').val(),
            descrC : $('#kehumingcehngE').val(),
            customerid : $('#kehudaimaE').val(),
            activeFlag : '1',
            isUse : '1',
            customerType:'OW'
        });
    }



</script>
</body>
</html>