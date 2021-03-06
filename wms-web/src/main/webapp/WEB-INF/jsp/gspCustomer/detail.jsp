<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp' />
    <c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<body>
<script type='text/javascript'>


     var  ezuiDialog1;
    // var enterpriseDialog;
     var dialogUrl1 = "/gspEnterpriseInfoController.do?toDetail";


</script>
<%--增加修改弹出二级dialog--%>
<form id='ezuiFormCustomer' method='post'>
    <input type='hidden' id='clientId' name='clientId' value="${customer.clientId}" class="textbox-value"/>
    <input type="hidden" id="enterpriseId" name='enterpriseId'  value="${customer.enterpriseId}"  class="textbox-value"/>
    <table>
        <tr>
            <th>企业</th>
            <td>
                <input type='text' value="${customer.enterpriseName}" id="enterpriseName" name='enterpriseName' />
                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewEnterpriseUrl()">查看</a>
            </td>

            <th>简称</th>
            <td><input type='text' value="${customer.clientName}" id='clientName' name='clientName' class='easyui-textbox' data-options='required:true,width:200'/></td>


        </tr>

        <tr>
            <th>代码</th>
            <td><input type='text' value="${customer.clientNo}" id="clientNo" name='clientNo' class='easyui-textbox' data-options='required:true,width:200'/></td>


            <th>货主联系人</th>
            <td><input type='text' value="${customer.contacts}" id='contacts' name='contacts' class='easyui-textbox' data-options='required:false,width:200'/></td>


        </tr>

        <tr>
            <th>企业类型</th>
            <td><input type='text' id="operateTypeData" name='operateType' /></td>



            <th>电话</th>
            <td><input type='text' value="${customer.telephone}" id='telephone' name='telephone' class='easyui-textbox' data-options='required:false,width:200'/></td>


        </tr>
        <tr>
            <th>是否贴中文标签</th>
            <td><input type='text' id="isChineseLabelData" name='isChineseLabel' /></td>


            <th>身份证正面照片</th>
            <td>
                <input type="hidden" class="textbox-value" name="idCardFront" id="idCardFront"  value="${customer.idCardFront}"/>
                <input id="contractUrlFile2" name='file2' value="${customer.idCardFront}">
                <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl2()">查看</a>
            </td>
        </tr>
        <tr>
            <th>合同编号</th>
            <td><input type='text' value="${customer.contractNo}" name='contractNo' class='easyui-textbox' data-options='required:true,width:200'/></td>

            <th>身份证背面照片</th>
            <td>
                <input type="hidden" class="textbox-value" name="idCardBack" id="idCardBack"  value="${customer.idCardBack}"/>
                <input id="contractUrlFile3" name='file3' value="${customer.idCardBack}">
                <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl3()">查看</a>
            </td>
        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" name="contractUrl" id="contractUrl"  value="${customer.contractUrl}"/>
                <input id="contractUrlFile" name='file' value="${customer.contractUrl}">
                <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
            </td>

            <th>身份证编号</th>
            <td><input type='text' value="${customer.idCardNumber}" name='idCardNumber' class='easyui-textbox' data-options='required:false,width:200'/></td>

        </tr>
        <tr>

            <th>委托开始时间</th>
            <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${customer.clientStartDate}"/>" id="clientStartDateForm" name='clientStartDate' data-options='required:true,width:200'/></td>

            <th>授权照片</th>
            <td>
                <input type="hidden" class="textbox-value" name="empowerPhoto" id="empowerPhoto" value="${customer.empowerPhoto}"/>
                <input id="contractUrlFile1" name='file1' value="${customer.empowerPhoto}">
                <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl1()">查看</a>
            </td>

        </tr>

        <tr>
            <th>委托结束时间</th>
            <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${customer.clientEndDate}"/>" id="clientEndDateForm" name='clientEndDate' data-options='required:true,width:200'/></td>

            <th>授权开始时间</th>
            <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${customer.empowerStartDate}"/>" id="empowerStartDateForm" name='empowerStartDate' class='easyui-datebox' data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>委托期限</th>
            <td><input type='text' value="${customer.clientTerm}" id="clientTermForm" name='clientTerm' class='easyui-numberbox' data-options='required:true,width:200'/></td>

            <th>授权结束时间</th>
            <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${customer.empowerEndDate}"/>" id="empowerEndDateForm" name='empowerEndDate' class='easyui-datebox' data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>委托内容</th>
            <td><input type='text' value="${customer.clientContent}" name='clientContent' class='easyui-textbox' data-options='required:true,width:200,height:80,multiline:true'/></td>


        </tr>


        <tr>
            <th>备注</th>
            <td><input type='text' value="${customer.remark}" name='remark' class='easyui-textbox' data-options='width:200,height:80,multiline:true'/></td>
        </tr>
        <!--<tr>
            <th>创建人</th>
            <td><input type='text' name='createId' value="${createId}" class='easyui-textbox' data-options='editable:false,width:200'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' name='createDate' value="${createDate}" class='easyui-textbox' data-options='editable:false,width:200'/></td>
        </tr>-->
    </table>
</form>

<%--查看企业弹窗--%>
<div id='ezuiDialogDetail' style='padding: 10px;display: none'>
    <div id='detailToolbar' class='datagrid-toolbar' style='background-color: #ffffff'>
        <fieldset>
            <legend>企业信息</legend>
            <table>
                <tr>
                    <th>代码</th>
                    <td><input type='text' id='enterpriseNo' class='easyui-textbox' data-options='width:200'/></td>
                    <th>简称</th>
                    <td><input type='text' id='shorthandName' class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchEnterprise();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridDetail">

    </table>
</div>
<div id="ezuiDialog1" style='padding: 10px;'>

</div>
<div id="dialogEnterprise">

</div>
<div id='ezuiDialogCustomer' style='padding: 10px;'>

</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var enterpriseDatagrid;
    var dataGridDetail;//企业信息弹窗datagrid
    var dialogEnterprise;
    $(function () {
        $('#ezuiFormCustomer #clientStartDateForm').datebox({
            onChange: function(date){
                onChangeDate();
            }
        });

        $('#ezuiFormCustomer #clientEndDateForm').datebox({
            onChange: function(date){
                onChangeDate();
            }
        });
        //企业加放大镜
        $("#ezuiFormCustomer #enterpriseName").textbox({
            value:"${customer.clientName}",
            width:200,
            required: true,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        })

        $('#ezuiFormCustomer #contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            //required: true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });
        $('#ezuiFormCustomer #contractUrlFile1').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            //required: true,
            onChange:function(data){
                if(data){
                    doUpload1(data);
                }
            }
        });
        $('#ezuiFormCustomer #contractUrlFile2').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            //required: true,
            onChange:function(data){
                if(data){
                    doUpload2(data);
                }
            }
        });
        $('#ezuiFormCustomer #contractUrlFile3').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            //required: true,
            onChange:function(data){
                if(data){
                    doUpload3(data);
                }
            }
        });

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
                        "file":document.getElementsByName("file")[0].files[0]//文件数组
                    }
                },
                onload:function(data){
                    // $("#ezuiFormCustomer #contractUrl").val(data.comment);

                    $.messager.progress('close');
                    if (data.success) {
                        $("#ezuiFormCustomer #contractUrl").val(data.comment);
                    }else {
                        showMsg("上传附件失败，请重试");
                        $("#ezuiFormCustomer input[name='file']").filebox("setValue","");
                    }
                },
                onerror:function(er){
                    $.messager.progress('close');
                    console.log(er);
                }
            });
        }
        function doUpload1(data) {
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
                        "file":document.getElementsByName("file1")[0].files[0]//文件数组
                    }
                },
                onload:function(data1){
                    // $("#ezuiFormCustomer #empowerPhoto").val(data1.comment);
                    $.messager.progress('close');
                    if (data1.success) {
                        $("#ezuiFormCustomer #empowerPhoto").val(data1.comment);
                    }else {
                        showMsg("上传附件失败，请重试");
                        $("#ezuiFormCustomer input[id='contractUrlFile1']").filebox("setValue","")
                    }
                },
                onerror:function(er){
                    $.messager.progress('close');
                    console.log(er);
                }
            });
        }
        function doUpload2(data) {
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
                        "file":document.getElementsByName("file2")[0].files[0]//文件数组
                    }
                },
                onload:function(data2){
                    // $("#ezuiFormCustomer #idCardFront").val(data2.comment);

                    $.messager.progress('close');
                    if (data2.success) {
                        $("#ezuiFormCustomer #idCardFront").val(data2.comment);
                    }else {
                        showMsg("上传附件失败，请重试");
                        $("#ezuiFormCustomer input[name='file2']").filebox("setValue","");
                    }
                },
                onerror:function(er){
                    $.messager.progress('close');
                    console.log(er);
                }
            });
        }
        function doUpload3(data) {
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
                        "file":document.getElementsByName("file3")[0].files[0]//文件数组
                    }
                },
                onload:function(data3){

                        // $("#ezuiFormCustomer #idCardBack").val(data3.comment);

                    $.messager.progress('close');
                    if (data3.success) {
                        $("#ezuiFormCustomer #idCardBack").val(data3.comment);
                    }else {
                        showMsg("上传附件失败，请重试");
                        $("#ezuiFormCustomer input[name='file3']").filebox("setValue","");
                    }
                },
                onerror:function(er){
                    $.messager.progress('close');
                    console.log(er);
                }
            });
        }


        $('#ezuiFormCustomer input[name="firstState"]').combobox({
            url:sy.bp()+'/commonController.do?getCatalogFirstState',
            valueField:'id',
            textField:'value'
        });

        /*$('#isCheckData').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#isCheckData').combobox("setValue",'${customer.isCheck}')
            }
        });

        $('#isCooperationData').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#isCooperationData').combobox("setValue",'${customer.isCooperation}')
            }
        });*/

        $("#ezuiFormCustomer #isChineseLabelData").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value',
            width:200,
            required:true,
            onLoadSuccess:function () {
                $('#ezuiFormCustomer #isChineseLabelData').combobox("setValue",'${customer.isChineseLabel}')
            }
        });

        $('#ezuiFormCustomer #operateTypeData').combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getEntType',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#ezuiFormCustomer #operateTypeData').combobox("setValue",'${customer.operateType}')
            }
        })
        //企业信息弹窗datagrid
        enterpriseDatagrid = $("#dataGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagridSearch',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                enterpriseType:'default',
                type:'noCustomer'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: '20%' },
                {field: 'shorthandName',		title: '简称',	width: '20%' },
                {field: 'enterpriseName',		title: '企业名称',	width: '20%' },
                {field: 'enterpriseType',		title: '企业类型',	width: '20%' ,formatter:entTypeFormatter},
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOper
                }
            ]],
            onDblClickCell: function(index,field,value){
                choseSelect();
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


        dialogEnterprise = $('#dialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

    })

    function searchEnterprise() {
        dataGridDetail = $('#ezuiDialogDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                // ezuiDialogDetail.dialog("destroy");
                ezuiFormClear(ezuiForm);
            }
        })

    }

    function doSearchEnterprise() {
        //
        //console.log($('#ezuiDialogDetail #enterpriseNo').textbox("getValue"))
        enterpriseDatagrid.datagrid('load', {
            enterpriseNo : $('#ezuiDialogDetail #enterpriseNo').textbox("getValue"),
            shorthandName : $('#ezuiDialogDetail #shorthandName').textbox("getValue"),
            type:'noCustomer',
            enterpriseType:'default',
            isUse : '1'

        });
    }

    function choseSelect() {
        var row = enterpriseDatagrid.datagrid("getSelected");
        if(row){
            console.log(row.enterpriseId);
            //$("#enterpriseId").attr("value",row.enterpriseId);
            console.log($("#enterpriseId").val());
            $("#ezuiFormCustomer  input[id='enterpriseId']").val(row.enterpriseId);
            $("#ezuiFormCustomer #enterpriseName").textbox("setValue",row.enterpriseName);
            $("#ezuiFormCustomer #clientNo").textbox("setValue",row.enterpriseNo);
            $("#ezuiFormCustomer #clientName").textbox("setValue",row.shorthandName);
            $("#ezuiFormCustomer #operateTypeData").combobox("setValue",row.enterpriseType);
            dataGridDetail.dialog('close');
        }
    }

    function operateGrid(id) {
        dialogEnterprise.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    }

    function formatOper(value,row,index){
        return "<a onclick=\"operateGrid('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }
    //增加修改提交
    function doSubmit() {
        var url = '';
        if (processType == 'edit') {
            url = '<c:url value="/gspCustomerController.do?edit"/>';
        }else{
            url = '<c:url value="/gspCustomerController.do?add"/>';
        }
        $("#ezuiFormCustomer").form('submit', {
            url : url,
            onSubmit : function(){
                console.log("1");
                if($(this).form('validate')){
                    $.messager.progress({
                        text : '<spring:message code="common.message.data.processing"/>', interval : 100
                    });
                    return true;
                    console.log("1");
                }else{
                    console.log("12");

                    return false;
                }
            },
            success : function(data) {
                var msg='';
                console.log("2");
                try {
                    var result = $.parseJSON(data);
                    if(result.success){
                        console.log("3");
                        msg = result.msg;
                        ezuiDatagrid.datagrid('reload');
                        ezuiDialog.dialog('close');
                    }else{
                        msg = '<font color="red">' + result.msg + '</font>';
                        console.log("4");
                    }
                } catch (e) {
                    console.log("f");
                    msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';

                    msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
                } finally {
                    console.log("5");
                    $.messager.show({
                        msg : msg, title : '<spring:message code="common.message.prompt"/>'
                    });
                    $.messager.progress('close');
                }
            }
        });
    }

    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormCustomer #contractUrl").val()!=""){
                showUrl($("#ezuiFormCustomer #contractUrl").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }
    function viewUrl1(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormCustomer #empowerPhoto").val()!=""){
                showUrl($("#ezuiFormCustomer #empowerPhoto").val());
            }else {
                showMsg("请上传授权照片！");
            }
        }
    }
    function viewUrl2(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormCustomer #idCardFront").val()!=""){
                showUrl($("#ezuiFormCustomer #idCardFront").val());
            }else {
                showMsg("请上传身份证正面照！");
            }
        }
    }
    function viewUrl3(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormCustomer #idCardBack").val()!=""){
                showUrl($("#ezuiFormCustomer #idCardBack").val());
            }else {
                showMsg("请上传身份证背面照！");
            }
        }
    }


    var ezuiDialogCustomer;
    //委托方企业信息详情
    function viewEnterpriseUrl(){
        var enterpriseIdCustomer = $("#ezuiFormCustomer input[id='enterpriseId']").val();
        if (enterpriseIdCustomer == null || enterpriseIdCustomer == "") {
            enterpriseIdCustomer = $("#ezuiFormCustomer #enterpriseId").val();
        }
        // var enterpriseId = $("#ezuiFormSupInfo #cli_enterpriseId").val();
        //var enterpriseId = $("#ezuiFormCustomer input[id='enterpriseId']").val();
        //enterpriseInfo(enterpriseId);
        ezuiDialogCustomer = $('#ezuiDialogCustomer').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            buttons: '',
            href: "/gspEnterpriseInfoController.do?toDetail&id="+enterpriseIdCustomer,
            width: 1200,
            height: 530,
            closable: true,
            cache: false,
            onClose: function () {
               // ezuiFormClear(ezuiDialogCustomer);
                $(this).dialog("clear");
            },
        })
        //processType = 'add';
        //var row = ezuiDatagrid.datagrid('getSelected');
        if (enterpriseIdCustomer != null && enterpriseIdCustomer != "") {
            ezuiDialogCustomer.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail" + "&id=" + enterpriseIdCustomer).dialog('open');
        } else {
            $.messager.show({
                msg: '请先选择企业', title: '提示'
            });
        }
    }

    <%--function viewEnterpriseUrl() {--%>
            <%--ezuiDialog1 = $('#ezuiDialog1').dialog({--%>
                <%--modal : true,--%>
                <%--title : '<spring:message code="common.dialog.title"/>',--%>
                <%--buttons : '',--%>
                <%--href:sy.bp()+dialogUrl1,--%>
                <%--width:1200,--%>
                <%--height:530,--%>
                <%--closable:true,--%>
                <%--cache: false,--%>
                <%--onClose : function() {--%>
                    <%--ezuiFormClear(ezuiDialog1);--%>
                <%--}--%>
            <%--})--%>
        <%--//processType = 'edit';--%>

        <%--//var row = ezuiDatagrid.datagrid('getSelected');--%>
        <%--console.log($("#ezuiFormCustomer  input[id='enterpriseId']").val());--%>
        <%--var enterpriseId = $("#ezuiFormCustomer  input[id='enterpriseId']").val();--%>
        <%--if(enterpriseId==null || enterpriseId==""){--%>
            <%--enterpriseId = $("#ezuiFormCustomer #enterpriseId").val();--%>
        <%--}--%>

        <%--if(enterpriseId!=null && enterpriseId!="" ){--%>
            <%--ezuiDialog1.dialog('refresh', dialogUrl1+"&id="+enterpriseId).dialog('open');--%>
            <%--enterpriseId = "";--%>
        <%--}else{--%>
            <%--$.messager.show({--%>
                <%--msg : '请先选择企业', title : '提示'--%>
            <%--});--%>
        <%--}--%>

    <%--}--%>


    function onChangeDate() {
        var startTime = $('#ezuiFormCustomer #clientStartDateForm').eq(0).datebox('getValue');
        var endTime = $('#ezuiFormCustomer #clientEndDateForm').eq(0).datebox('getValue') ;

        if(startTime!=null && endTime!=null&& endTime!=""&& startTime !=""  ) {
            var endTime = new Date(endTime);
            var startTime = new Date(startTime);
            var days = endTime.getTime() - startTime.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24));
            $('#ezuiFormCustomer #clientTermForm').eq(0).numberbox('setValue',day);
        }
    }


</script>
</body>
</html>
