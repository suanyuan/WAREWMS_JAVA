<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
</head>
<body>
<style>
    .easyui-textbox{
        width: 180px;
    }
</style>
<div class='easyui-layout' data-options='fit:true,border:false'>
    <%--点击增加编辑dialog--%>
    <div data-options='region:"center",border:false' style='overflow: auto;'>
        <form id='ezuiFormDetail' method='post'>
            <input type="hidden" id="operateDetail" name="operateDetail" value="${operateDetail}"/>
            <input type='hidden' id='choseScope' value="${choseScope}" name="choseScope" class="textbox-value"/>
            <input type="hidden" id="opType" name="opType" value="add"/>
            <fieldset>
                <legend>产品注册证/备案信息</legend>
                <input type='hidden' class="textbox-value" id='gspProductRegisterId' name='productRegisterId' value="${gspProductRegister.productRegisterId}"/>
                <table width="80%">
                    <tr>
                        <th>产品注册证/备案</th>
                        <td><input type='text' id1="productRegisterNo" name='productRegisterNo' class='easyui-textbox' value="${gspProductRegister.productRegisterNo}" data-options='required:true'/></td>
                        <th>审批部门</th>
                        <td><input type='text' id1="approvalDepartment" name='approvalDepartment' class='easyui-textbox' value="${gspProductRegister.approvalDepartment}" data-options=''/></td>
                    </tr>
                    <tr>
                        <th>产品名称</th>
                        <td><input type='text' id1="productNameMain" name='productNameMain' class='easyui-textbox' value="${gspProductRegister.productNameMain}" data-options='required:true'/></td>
                        <th>批准日期</th>
                        <td><input type='text' id1="approveDate" id="approveDate" name='approveDate' class='easyui-datebox' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspProductRegister.approveDate}"/>" data-options='required:true,width:185'/></td>
                    </tr>
                    <tr>
                        <th>管理分类</th>
                        <td><input type='text' id1="classifyId" id="classifyId" name='classifyId' value="${gspProductRegister.classifyId}" data-options='required:true,editable:false'/></td>

                        <%--<th>有效期至</th>--%>
                        <%--<td><input type='text' id="productExpiryDate" name='productExpiryDate' class='easyui-datebox' value="${gspProductRegister.productExpiryDate}" data-options='required:true,editable:false,width:185'/></td>--%>
                        <th>有效期至</th>
                        <td><input type='text' id1="productRegisterExpiryDate" id="productRegisterExpiryDate" name='productRegisterExpiryDate' class='easyui-datebox' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspProductRegister.productRegisterExpiryDate}"/>" data-options='required:true,width:185'/></td>
                    </tr>
                    <tr>
                        <th>注册证/备案版本</th>
                        <td><input type='text' id="productRegisterVersion" name='productRegisterVersion' class='easyui-textbox' value="" data-options='required:true,editable:false'/></td>

                        <th>注册证/备案附件</th>
                        <td>
                            <input  id="attachmentUrlFile" name="attachmentUrlFile"  data-options='' value="${gspProductRegister.attachmentUrl}"/>
                            <a id="btn" href="javascript:void(0)" onclick="viewUrl()" class="easyui-linkbutton" data-options="">查看</a>
                            <input type="hidden" class="textbox-value" name="attachmentUrl" id="attachmentUrl" value="${gspProductRegister.attachmentUrl}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>分类目录</th>
                        <td>
                            <input type='text' id1="classifyCatalog" id="classifyCatalog" name="classifyCatalog"   data-options='required:true'/>
                            <a onclick='selectProductRegisterScope()' id='ezuiDetailsBtn_scope' class='easyui-linkbutton' data-options='required:true,plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>
                        </td>
                        <th>结构及组成</th>
                        <td><input type='text' id1="structureAndComposition" name='structureAndComposition' class='easyui-textbox' value="${gspProductRegister.structureAndComposition}" data-options=''/></td>
                    </tr>
                    <tr>
                        <th>产地</th>
                        <td><input type='text' id1="productionAddress" name='productionAddress' class='easyui-textbox' value="${gspProductRegister.productionAddress}" data-options=''/></td>
                        <th>适用范围</th>
                        <td><input type='text' id1="applyScope" name='applyScope' class='easyui-textbox' value="${gspProductRegister.applyScope}" data-options='multiline:true'/></td>
                    </tr>
                    <tr>
                        <th>储存条件</th>
                        <td><input type='text' id1="storageConditions" name='storageConditions' class='easyui-textbox' value="${gspProductRegister.storageConditions}"  data-options='required:true'/></td>
                        <th>预期用途</th>
                        <td><input type='text' id1="expectUse" name='expectUse' class='easyui-textbox' value="${gspProductRegister.expectUse}" data-options='multiline:true'/></td>
                    </tr>
                    <tr>

                        <th>产品运输条件</th>
                        <td><input type='text' id1="transportConditionMain" name='transportConditionMain' class='easyui-textbox' value="${gspProductRegister.transportConditionMain}" data-options=''/></td>
                        <th>主要组成部分</th>
                        <td><input type='text' id1="mainPart" name='mainPart' class='easyui-textbox' value="${gspProductRegister.mainPart}" data-options='multiline:true'/></td>
                    </tr>
                    <tr>
                        <th>注册人名称/生产企业</th>
                        <td>
                            <input type="hidden" id="enterpriseId" name="enterpriseId" class="textbox-value" value="${gspProductRegister.enterpriseId}"/>
                            <input type='text' id='enterpriseName' />
                        </td>
                        <th>附件</th>
                        <td>
                            <input type='text' id1="productRegsiterUrl" id="productRegsiterUrl" name="productRegsiterUrl"  class='easyui-textbox' data-options='multiline:true' value="${gspProductRegister.productRegsiterUrl}"/>
                        </td>
                    </tr>
                    <tr>
                        <th>生产许可证号/备案号</th>
                        <td><input type='text' data="1" id="licenseOrRecordNol" value="${gspProductRegister.licenseOrRecordNol}"  name='licenseOrRecordNol' class='easyui-textbox' data-options='' /></td>

                        <th>其他内容</th>
                        <td><input type='text' id1="otherContent" name='otherContent' class='easyui-textbox' value="${gspProductRegister.otherContent}" data-options=''/></td>

                    </tr>
                    <tr>
                        <%--<th>注册人名称</th>--%>
                        <input type='hidden' name='productRegisterName'  value="${gspProductRegister.productRegisterName}" data-options='required:true'/>
                        <th>注册人住所</th>
                        <td><input type='text' id1="productRegisterAddress" name='productRegisterAddress' class='easyui-textbox' value="${gspProductRegister.productRegisterAddress}" data-options=''/></td>

                        <th>备注</th>
                        <td><input type='text' id1="remark" name='remark' class='easyui-textbox' value="${gspProductRegister.remark}" data-options='multiline:true'/></td>

                    </tr>
                    <tr>
                        <th>生产地址</th>
                        <td><input type='text' id1="productProductionAddress" name='productProductionAddress' class='easyui-textbox' value="${gspProductRegister.productProductionAddress}" data-options=''/></td>

                        <th>创建人</th>
                        <td><input type='text' id1="createId" name='createId' class='easyui-textbox' value="${gspProductRegister.createId}" data-options='editable:false'/></td>

                    </tr>
                    <tr>
                        <th>代理人名称</th>
                        <td><input type='text' id1="agentName" name='agentName' class='easyui-textbox' value="${gspProductRegister.agentName}" data-options=''/></td>


                        <th>创建时间</th>
                        <td><input type='text' id1="createDate" name='createDate' class='easyui-textbox' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspProductRegister.createDate}"/>" data-options='editable:false'/></td>
                    </tr>
                    <tr>
                        <th>代理人住所</th>
                        <td><input type='text' id1="agentAddress" name='agentAddress' class='easyui-textbox' value="${gspProductRegister.agentAddress}" data-options=''/></td>

                        <td colspan="1">

                        </td>
                        <td>

                            <a onclick='addDetail();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>新增</a>
                            <a onclick='submitDetail();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </form>
        <div class="easyui-tabs" style="width:100%;height:200px">
            <div title="证照历史数据" style="padding:3px">
                    <div>
                        <a onclick='registerUpdate();' id='ezuiDetailsBtn_update' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"<c:if test="${gspProductRegister.checkerId ==null }">,disabled:true</c:if>' href='javascript:void(0);'>开始换证</a>
                        <a onclick='detailsCopyRegister();' id='ezuiDetailsBtn_copy' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"<c:if test="${gspProductRegister.checkerId ==null }">,disabled:true</c:if>' href='javascript:void(0);'>信息复用</a>
                    </div>
                <table id='ezuiDetailsDatagrid'></table>
            </div>
            <div title="绑定产品列表" style="padding:0px">
                    <%--<div>--%>
                        <%--<a onclick='detailsBind();' id='ezuiDetailsBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>绑定产品</a>--%>
                        <%--<a onclick='detailsUnBind();' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>解除产品</a>--%>
                    <%--</div>--%>
                <table id='ezuiDatagridDetail' ></table>
            </div>
        </div>
    </div>
    <!--产品查询列表dialog -->
    <div id='ezuiDialogSpec' style='padding: 10px;display: none;'>
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
                            <a id="getBy" onclick='getBy();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                            <a id="choseSelect" onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <table id="dataGridProduct">

        </table>
    </div>
    <!--企业信息列表dialog -->
    <div id='ezuiDialogEnterprise' style='padding: 10px;display: none;'>
        <div id='ezuiEnterpriseToolbar' class='datagrid-toolbar' style=''>
            <fieldset>
                <legend>企业信息</legend>
                <table>
                    <tr>
                        <th>代码</th>
                        <td><input type='text' id='enterpriseNo' class='easyui-textbox' data-options='width:200'/></td>
                        <th>简称</th>
                        <td><input type='text' id='shorthandName' class='easyui-textbox' data-options='width:200'/></td>
                        <td>
                            <a id="productQuery" onclick="productQuery()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" href='javascript:void(0);'>查询</a>
                            <a id="productChose" onclick="productChose()" class="easyui-linkbutton" data-options="iconCls:'icon-search'" href='javascript:void(0);'>选择</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <table id="enterpriseGridDetail">

        </table>
    </div>
    <div id="dialogChoseScope" style='padding: 10px;'></div>

        <div id="dialogEnterprise">

        </div>

</div>
<script charset="UTF-8" type="text/javascript" src="/js/jquery/ajaxfileupload.js"></script>
<script>
    var ezuiFormDetail = $("#ezuiFormDetail");
    var ezuiDialogSpec;
    var dataGridProduct;

    var ezuiDialogEnterprise;
    var enterpriseDatagrid;
    var dialogEnterprise; //TODO 替换成企业信息查询通用
    var ezuiDatagridDetail;
    var ezuidialogChoseScope;
    var choseRowArr = new Array();
    var opType = "add";
//上传文件初始化
    $(function () {
        $('#attachmentUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required:true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });

        //绑定产品列表
        ezuiDatagridDetail = $("#ezuiDatagridDetail").datagrid({
            url : sy.bp()+'/gspProductRegisterController.do?showSpecsList',
            method:'POST',
            toolbar : '',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,

            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            queryParams:{'productRegisterId':'${gspProductRegister.productRegisterId}'},
            pagination:true,
            rownumbers:true,
            scrollbarSize:100,
            idField : 'specsId',
            columns : [[
                {field: 'specsId',title:'主键',hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '25%'},
                {field: 'specsName',title: '规格' ,width: '25%'},
                {field: 'productModel',title: '型号',width: '25%'},
                {field: 'productName',title: '产品名称',width: '25%'},
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        })


        enterpriseDatagrid = $("#enterpriseGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagrid',
            method:'POST',
            toolbar : '#ezuiEnterpriseToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                enterpriseType:'SCQY',
                type:'1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业代码',	width: '20%' },
                {field: 'shorthandName',		title: '简称',	width: '20%' },
                {field: 'enterpriseName',		title: '企业名称',	width: '20%' },
                {field: 'enterpriseType',		title: '企业类型',	width: '20%' ,formatter:entTypeFormatter},
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOperEnterprise
                }
            ]],
            onDblClickCell: function(index,field,value){
                selectEnterprise();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
                //sy.bp()+'/gspProductRegisterController.do?showSpecsList'
            }
        })
        //企业信息详情
        dialogEnterprise = $('#dialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            cache: false,
            onClose : function() {
                //  ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        $("#ezuiFormDetail input[name='classifyId']").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getCatalogClassify',
            valueField:'id',
            textField:'value',
            width:185
        });

        $("#ezuiFormDetail input[id='classifyCatalog']").textbox({
            width:186,
            multiline:true,
            height:40,
            editable:false
        });

        $('#ezuiFormDetail input[name="productRegisterVersion"]').combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getCatalogVersion',
            valueField:'id',
            textField:'value',
            onLoadSuccess:function () {
                $(this).combobox("setValue",'${gspProductRegister.productRegisterVersion}');
            }
        });

        //加载注册证历史信息
        ezuiDetailsDatagrid = $("#ezuiDetailsDatagrid").datagrid({
            url : sy.bp()+'/gspProductRegisterController.do?showHistoryDatagrid',
            method:'POST',
            toolbar : '',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,

            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            queryParams:{
                version : '${gspProductRegister.version}'
            },
            idField : 'productRegisterId',
            columns : [[
                {field: 'productRegisterId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'productRegisterNo',		title: '注册证编号/备案号',	width: '20%' },
                {field: 'productNameMain',		title: '产品名称',	width: '20%' },
                {field: 'isUse',		title: '是否有效',	width: '10%' ,formatter:isUseFormatter},
                {field: 'checkerId',		title: '审核人',	width: '10%' },
                {field: 'createDate',		title: '创建时间',	width: '20%' },
                {field: '_operate',		title: '注册证/备案附件',	width: '20%',
                    formatter: formatOper
                }
            ]],
            onDblClickCell: function(index,field,value){
                selectEnterprise();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
                //sy.bp()+'/gspProductRegisterController.do?showSpecsList'
            }
        })

        initChoseText();
    });

    function initChoseText() {
        $.ajax({
            url : sy.bp()+'/gspInstrumentCatalogController.do?searchCheckByLicenseId',
            data : {
                "licenseId":'${gspProductRegister.productRegisterId}'
            }
            ,type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                if(result && result.length>0){
                    var arr = new Array();
                    for(var i=0;i<result.length;i++){
                        arr.push(result[i].operateName);
                    }
                    $("#ezuiFormDetail input[id='classifyCatalog']").textbox("setValue",arr.join(","))
                }
            }
        });
    }
    /**
     * 经营范围选择
     */
    function selectProductRegisterScope() {

        ezuidialogChoseScope = $('#dialogChoseScope').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:800,
            height:500,
            href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=productRegister&id=${gspProductRegister.productRegisterId}',
            onClose : function() {
                ezuidialogChoseScope.dialog('clear');
            }
        });
    }
    
    function choseSelect_Catalog_productRegister(row) {
        var choseRowNameArr = new Array();
        //var oldValue = $("#ezuiFormDetail input[id='classifyCatalog']").textbox("getValue");
        if(row instanceof Array){
            for(var i=0;i<row.length;i++){
                console.log(row[i])
                choseRowArr.push(row[i].instrumentCatalogId);
                choseRowNameArr.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
            }
            $("#ezuiFormDetail input[id='classifyCatalog']").textbox("setValue",choseRowNameArr.join(","))
        }else{
            choseRowArr.push(row.instrumentCatalogId);
            $("#ezuiFormDetail input[id='classifyCatalog']").textbox("setValue","["+row.classifyId+"]"+row.instrumentCatalogName);
        }
        $("#ezuiFormDetail input[id='choseScope']").val(choseRowArr.join(","));
        ezuidialogChoseScope.dialog("close");
    }
    //上传文件
    function doUpload(data) {
        $.messager.progress({
            text : '<spring:message code="common.message.data.processing"/>', interval : 100
        });
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("attachmentUrlFile")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                $.messager.progress('close');
                console.log(data);
                if (data.success) {
                    $("#attachmentUrl").val(data.comment);
                }else {
                    showMsg("上传附件失败，请重试");
                    $("#ezuiFormDetail input[id='attachmentUrlFile']").filebox("setValue","");
                }
            },
            onerror:function(er){
                $.messager.progress('close');
                console.log(er);
                showMsg("上传附件失败，请重试");
                $("#ezuiFormDetail input[id='attachmentUrlFile']").filebox("setValue","");
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }
//清空from 点击新增触发
    function addDetail() {
        processType = 'add';
        ezuiFormClear($("#ezuiFormDetail"));
        $("#enterpriseId").val("");
        $("#gspProductRegisterId").val("");
        $("#ezuiFormDetail input[id='enterpriseName']").textbox("setValue","");
        $("#ezuiFormDetail input[id='classifyId']").combobox("clear");
        $("#ezuiFormDetail input[id='classifyCatalog']").textbox("setValue","");
        $("#ezuiFormDetail input[id='attachmentUrlFile']").filebox("setValue","");
        $("#ezuiFormDetail input[id='attachmentUrl']").val("");
        ezuiDatagridDetail.datagrid('loadData',{total:0,rows:[]});
    }
//二级daiyog提交事件
    function submitDetail() {
        // var productExpiryDate = $("#ezuiFormDetail input[id='productExpiryDate']").datebox("getValue");
        // var productRegisterExpiryDate = $("#ezuiFormDetail input[id='productRegisterExpiryDate']").datebox("getValue");
        // if(productRegisterExpiryDate<productExpiryDate){
        //     showMsg("有效期时间大于有效期至时间");
        //     return;
        // }
        var url = '';
        if (processType == 'edit') {
            url = '/gspProductRegisterController.do?edit';
        }else{
            url = '/gspProductRegisterController.do?add';
        }
        ezuiFormDetail.form('submit', {
            url : url,
            onSubmit : function(){
                if($("#enterpriseId").val() == ""){
                    return false;
                }

                if(judgeDate($("#ezuiFormDetail #approveDate").datebox("getValue"))){
                    checkResult = false;
                    showMsg("批准日期不能超过当前时间");
                    return false;
                }

                if($("#ezuiFormDetail #approveDate").datebox("getValue")>$("#ezuiFormDetail #productRegisterExpiryDate").datebox("getValue"))
                {
                    $("#productRegisterExpiryDate").focus();
                    showMsg("有效时间不能小于批准时间！");
                    return false;
                }

                /*if(!judgeDate($("#ezuiFormDetail #productRegisterExpiryDate").datebox("getValue"))){
                    checkResult = false;
                    showMsg("有效期不能小于当前时间");
                    return false;
                }*/


                if(ezuiFormDetail.form('validate')){
                    $.messager.progress({
                        text : '<spring:message code="common.message.data.processing"/>', interval : 100
                    });
                    return true;
                }else{
                    return false;
                }
            },
            success : function(data) {
                console.log(data);
                $.messager.progress('close');
                var result = $.parseJSON(data);
                if(result.success){
                    if(processType == 'add'){
                        // $("#ezuiBtn_editdiayog").linkbutton("readonly",false);
                    }
                    console.log(result.obj);
                    $("#gspProductRegisterId").val(result.obj);
                    // console.log($("#gspProductRegisterId").val());
                    // ezuiDatagridDetail.datagrid("reload");
                    ezuiDatagrid.datagrid("reload");
                    ezuiDialog.dialog('close');
                }
                showMsg(result.msg);

            },
            error:function () {
                $.messager.progress('close');
                showMsg("请求失败，请检查网络是否正常");
            }
        });
    }

    var ezuiFormClear = function(ezuiForm){
        ezuiTimespinnerClear(ezuiForm);
        ezuiFileboxClear(ezuiForm);
        ezuiNumberboxClear(ezuiForm);
        ezuiTextboxClear(ezuiForm);
        ezuiComboboxClear(ezuiForm);
        ezuiCombotreeClear(ezuiForm);
        ezuiDateboxClear(ezuiForm);
        ezuiDatetimeboxClear(ezuiForm);
        $(ezuiForm).find('input[type=file]').each(function(){
            $(this).val('');
        });
        $(ezuiForm).find('input[type=checkbox]').each(function(){
            $(this).attr('checked',false);
        });
        $(ezuiForm).find('input').each(function(){
            $(this).removeClass('tooltip-f');
            $(this).removeClass('validatebox-invalid');
        });
    };

    function detailsBind(){

        if($("#gspProductRegisterId").val()==""){
            $.messager.show({
                msg : "请先添加注册证再绑定产品", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        dataGridProduct = $("#dataGridProduct").datagrid({
            url : sy.bp()+'/gspProductRegisterSpecsController.do?showDatagrid',
            method:'POST',
            toolbar : '',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : true,
            scrollbarSize:100,
            nowrap: true,
            striped: true,
            collapsible:false,
            fit:true,
            pagination:true,
            queryParams:{"isUse":"1"},
            rownumbers:true,
            idField : 'specsId',
            columns : [[
                {field: 'ck',checkbox:true },
                {field: 'specsId',title: '主键' ,hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '25%'},
                {field: 'productName',title: '产品名称',width: '25%'},
                {field: 'specsName',title: '规格名称' ,width: '25%'},
                {field: 'productModel',title: '产品型号',width: '25%'}
            ]],
            onDblClickCell: function(index,field,value){

            },
            onDblClickRow: function(index,row){
                choseSelect(row);
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid("resize",{height:540});
                //初始化已选择
                $.ajax({
                    url : sy.bp()+'/gspProductRegisterController.do?showSpecsList',
                    data : {'productRegisterId':'${gspProductRegister.productRegisterId}'},
                    type : 'POST',
                    dataType : 'JSON',
                    success : function(result){
                        if(result){
                            console.log(result.rows);
                            var rows = result.rows;
                            for(var i=0;i<rows.length;i++){
                                dataGridProduct.datagrid("selectRecord",rows[i].specsId);
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
            },
            onLoad:function () {

            }
        });
    }

    function detailsUnBind(){
        if($("#gspProductRegisterId").val()==""){
            return;
        }
        var selectRows = ezuiDatagridDetail.datagrid("getSelections");
        if(selectRows){
            var arr = new Array();
            for(var i=0;i<selectRows.length;i++){
                arr.push(selectRows[i].specsId);
            }
            $.messager.confirm('', '确认要解除产品绑定吗', function(confirm) {
                if(confirm){
                    $.ajax({
                        url : 'gspProductRegisterController.do?unBind',
                        data : {"gspProductRegisterId":$("#gspProductRegisterId").val(),id : arr.join(',')},
                        type : 'POST',
                        dataType : 'JSON',
                        success : function(result){
                            var msg = '';
                            try {
                                msg = result.msg;
                            } catch (e) {
                                msg = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $.messager.show({
                                    msg : msg, title : '<spring:message code="common.message.prompt"/>'
                                });
                                ezuiDatagridDetail.datagrid('reload');
                            }
                        }
                    });
                }
            });
        }
    }

    function choseSelect(row) {
        var rows = row || dataGridProduct.datagrid("getChecked");
        if(rows){
            var arr = new Array();
            if(rows instanceof Array){
                for(var i=0;i<rows.length;i++){
                    arr.push(rows[i].specsId);
                }
            }else{
                arr.push(row.specsId);
            }

            $.ajax({
                url : 'gspProductRegisterController.do?addSpec',
                data : {"gspProductRegisterId":$("#gspProductRegisterId").val(),"specId" : arr.join(',')},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result){
                        $.messager.show({
                            msg : result.msg, title : '<spring:message code="common.message.prompt"/>'
                        });
                        ezuiDatagridDetail.datagrid("reload");
                        ezuiDialogSpec.dialog('close');
                    }
                }
            });
        }
    }

    $("#ezuiFormDetail #enterpriseName").textbox({
        width:185,
        required:true,
        editable:false,
        icons:[{
            iconCls:'icon-search',
            handler: function(e){
                searchEnterprise();
            }
        }],
        value:"${enterpriseName}"
    });
    
    function searchEnterprise() {
        ezuiDialogEnterprise = $('#ezuiDialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {

            },
            onOpen : function () {

            }
        })
        //ezuiDialogEnterprise.dialog("open");
    }

    function selectEnterprise() {
        var rows = enterpriseDatagrid.datagrid("getSelected") || enterpriseDatagrid.datagrid("getChecked");
        console.log(rows);
        if(rows){
            $('#licenseOrRecordNol').textbox('clear');
            console.log(rows);
            var licenseNo  = rows.licenseNo;
            var recordNo = rows.recordNo;
            var plicenseNo = rows.plicenseNo;
            var grecordNo = rows.grecordNo;
            $("#enterpriseId").val(rows.enterpriseId);
            $("#ezuiFormDetail #enterpriseName").textbox("setValue",rows.enterpriseName);
            //$("#licenseOrRecordNol").textbox("setValue",licenseNo);
            ezuiDialogEnterprise.dialog("close");
            //生产许可证号 备案号
            $("#licenseOrRecordNol").combobox({
                panelHeight: 'auto',
                valueField: 'label',
                textField: 'value',
                data: [{
                    label: licenseNo,
                    value: licenseNo
                }, {
                    label: recordNo,
                    value: recordNo
                }, {
                    label: plicenseNo,
                    value: plicenseNo
                }, {
                    label: grecordNo,
                    value: grecordNo
                }]

            })
        }
    }

    // function operateGrid(id) {
    //     //dialogEnterprise.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    // }
    function operateGrid(id) {
        dialogEnterprise.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    }

    function formatOperEnterprise(value,row,index){
        return "<a onclick=\"operateGrid('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function formatOper(value,row,index){
        return "<a onclick=\"viewUrl('"+row.attachmentUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }
//查看上传文件
    function viewUrl(url) {
        if(url != null){
            if (url) {
                showUrl(url);
            }else {
                showMsg("请上传产品注册证附件！");
            }
        }else{
            if($("#attachmentUrl").val()!=""){
                showUrl($("#attachmentUrl").val());
            }else {
                showMsg("请上传产品注册证附件！");
            }
        }
    }

    function getBy() {
        dataGridProduct.datagrid("load",
            {"productCode":$("#productCode").textbox("getValue"),
                "productName":$("#productName").textbox("getValue"),
                "isUse":"1"})
    }

    function productQuery() {
        enterpriseDatagrid.datagrid("reload",
            {"enterpriseNo":$("#ezuiEnterpriseToolbar #enterpriseNo").textbox("getValue"),
                "shorthandName":$("#ezuiEnterpriseToolbar #shorthandName").textbox("getValue"),
                "isUse":"1"})
    }

    function productChose() {
        selectEnterprise();
    }
    
    function detailsCopyRegister() {
        var row = ezuiDetailsDatagrid.datagrid("getSelected");
        initHistoryDataRegister(row);
    }

    //加载历史证照信息
    function initHistoryDataRegister(row) {
        console.log(row);
        $("#ezuiFormDetail input[type!=hidden]").each(function (index) {
            console.log($(this).attr("id1"));
            if($(this).attr("class")){
                if($(this).attr("id1")) {
                    if ($(this).attr("class").indexOf('easyui-textbox') != -1) {
                        //console.log($(this).attr("id"));
                        $(this).textbox("setValue", row["" + $(this).attr("id1") + ""]);
                    }else if ($(this).attr("class").indexOf('easyui-datebox') != -1) {
                        $(this).datebox("setValue", dateFormat2(row["" + $(this).attr("id1") + ""]));
                    }else if ($(this).attr("class").indexOf('easyui-numberbox') != -1) {
                        $(this).numberbox("setValue", row["" + $(this).attr("id1") + ""]);
                    }
                    // else if ($(this).attr("class").indexOf('easyui-combobox') != -1) {
                    //     $(this).combobox("setValue", row["" + $(this).attr("id1") + ""]);
                    // }
                }
            }
        });

        // alert(row.enterpriseId+"==="+row.enterpriseName+"======"+row.licenseOrRecordNol);
        $("#ezuiFormDetail #enterpriseId").val(row.enterpriseId);
        $("#ezuiFormDetail #enterpriseName").textbox("setValue",row.enterpriseName);

        $("#ezuiFormDetail #licenseOrRecordNol").textbox("setValue",row.licenseOrRecordNol);
        $("#ezuiFormDetail #productRegisterVersion").combobox("setValue",row.productRegisterVersion);

        //$("#ezuiFormDetail #classifyCatalog").textbox("setValue",row.classifyCatalog);
        //$("#ezuiFormDetail #enterpriseName").textbox("setValue",row.enterpriseName);
        // $("#ezuiFormDetail #licenseOrRecordNol").combobox("setValue",row.licenseOrRecordNol);
        //$("#ezuiFormDetail #choseScope").val(row.choseScope);
    }

    //换证清空当前数据
    function registerUpdate() {
        if($("#productRegisterId").val() == ""){
            return;
        }
        opType = "update";
        $("#ezuiFormDetail input[id='opType']").val("update");
        $("#ezuiFormDetail input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue","");
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue","");
                }else if($(this).attr("class").indexOf('easyui-numberbox')!=-1){
                    $(this).numberbox("setValue","");
                }
            }
        })

        // $("#ezuiFormDetail #classifyCatalog").textbox("setValue","");

        $("#ezuiFormDetail #enterpriseName").textbox("setValue","");
        $("#ezuiFormDetail input[id='attachmentUrl']").val("");
        $("#attachmentUrlFile").filebox("setValue","");
        $("#ezuiFormDetail #operateDetail").val("");
        // $("#ezuiFormDetail #choseScope").val("");
        $("#licenseOrRecordNol").textbox("setValue","");

    }
</script>
</body>
</html>