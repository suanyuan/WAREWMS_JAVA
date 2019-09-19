<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    table th{
        text-align: right;
    }
</style>
<div id='detailProdToolbar' class='datagrid-toolbar' style='padding: 0px;background-color: #ffffff;'>
    <form id='ezuiFormProd' method='post' style="padding: 0px;">
        <input type='hidden' data="1" id='operateId' name='operateId' value="${gspProdLicense.operateId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspProdLicense.enterpriseId}"/>
        <input type='hidden' id='choseScope' value="${choseScope}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table width="100%">
                <tr>
                    <th>企业名称</th>
                    <td><input type='text' value="${gspProdLicense.enterpriseName}" data="1" id="enterpriseName" name='enterpriseName' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>生产许可证编号</th>
                    <td>
                        <input type='text' value="${gspProdLicense.licenseNo}" data="1" id="licenseNo" name='licenseNo' class='easyui-textbox' data-options='required:true,width:300'/>
                    </td>
                </tr>
                <tr >
                    <th>法定代表人</th>
                    <td><input type='text' value="${gspProdLicense.juridicalPerson}" data="1" id="juridicalPerson" name='juridicalPerson' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th rowspan="2">生产场所</th>
                    <td rowspan="2"><input type='text' value="${gspProdLicense.businessResidence}" data="1" id="businessResidence" name='businessResidence' class='easyui-textbox' data-options='required:true,width:300,height:60,multiline:true'/></td>

                </tr>
                <tr>
                    <th>企业负责人</th>
                    <td><input type='text' value="${gspProdLicense.headName}" data="1" id="headName" name='headName' class='easyui-textbox' data-options='required:true,width:300'/></td>

                 </tr>
                <tr>
                    <th>住所</th>
                    <td><input type='text' value="${gspProdLicense.residence}" data="1" id="residence" name='residence' class='easyui-textbox' data-options='required:true,width:300'/></td>

                    <th rowspan="2">生产范围(已选择)</th>
                    <td rowspan="2">
                        <input type='text' data="1"  id="showChose" name='showChose' style="height:45px;" class='easyui-textbox' data-options='required:true,multiline:true,width:300,height:60,editable:false'/>
                        <a onclick='selectOperateScope1()' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>
                    </td>
                </tr>

                <tr>
                    <th>库房地址</th>
                    <td><input type='text' value="${gspProdLicense.warehouseAddress}" data="1" id="warehouseAddress" name='warehouseAddress' class='easyui-textbox' data-options='required:true,width:300'/></td>
                </tr>
                <tr>
                    <th>发证部门</th>
                    <td><input type='text' value="${gspProdLicense.registrationAuthority}" data="1" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:300'/></td>

                    <th>发证日期</th>
                    <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspProdLicense.approveDate}"/>" data="1" id="approveDate" name='approveDate' class='easyui-datebox' data-options='required:true,width:300'/></td>
                </tr>
                <tr>
                    <th>许可证照片</th>
                    <td>
                        <input id="licenseUrlFile1" name='licenseUrlFile1' value="${gspProdLicense.licenseUrl}" atth="fileUpload">
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlOperate1()">查看</a>
                        <input type="hidden" data="2" class="textbox-value" name="licenseUrl" id="licenseUrl" value="${gspProdLicense.licenseUrl}"/>
                        <!--<a onclick='businessSubmit()' id='ezuiDetailsBtn_save' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>-->
                    </td>
                    <th>有效期限</th>
                    <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspProdLicense.licenseExpiryDate}"/>" data="1" id="licenseExpiryDate" name='licenseExpiryDate' class='easyui-datebox' data-options='required:true,width:300'/></td>

                    <%--<th>经营方式</th>--%>
                    <%--<td colspan="2"><input type='text' value="${gspOperateLicense.operateMode}" data="1" id="operateMode" name='operateMode' class='easyui-textbox' data-options='required:true,width:250'/></td>--%>

                </tr>

                <tr>

                    <th>生产范围</th>
                    <td>
                        <input type='text' value="${gspProdLicense.businessScope}" id="businessScope" name='businessScope' class='easyui-textbox' style="height: 60px;" data-options='required:true,multiline:true,width:300'/>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
    <div>
        <a onclick='operateUpdate1()' id='ezuiDetailsBtn_update' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>开始换证</a>
        <a onclick='operateCopy1()' id='ezuiDetailsBtn_copy' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-back"' href='javascript:void(0);'>信息复用</a>
    </div>
    <fieldset>
        <legend>证照历史数据</legend>
        <table id='ezuiOperateDatagridDetail1' ></table>
    </fieldset>
</div>
<div id="dialogChoseScopeOperate1"></div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var ezuiOperateDatagridDetail1;
    var ezuidialogChoseScopeOperate1;
    var url;
    var opType = "add";
    $(function () {
        //控件初始化
        ezuiOperateDatagridDetail1 = $("#ezuiOperateDatagridDetail1").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?operateHistoryDatagridList',
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
            queryParams:{
                'enterpriseId':'${gspProdLicense.enterpriseId}',
                'operateType': "PROD"
            },
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'operateId',
            columns : [[
                {field: 'isUse',title: '是否有效' ,width: '5%',formatter:isUseFormatter},
                {field: 'licenseNo',title: '许可证编号' ,width: '15%'},
                {field: 'approveDate',title: '发证日期' ,width: '20%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: 'licenseExpiryDate',title: '有效期限' ,width: '20%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: '_operate',		title: '许可证照片',	width: '10%',
                    formatter: formatOperAttachmentOperate1
                },
                {field: 'createId',title: '创建人',width: '10%'},

                {field: 'createDate',title: '创建时间',width: '15%',formatter:function (value,row,index) {
                        return dateFormat(value);
                    }
                },




                {field: 'operateId',title:'主键',hidden:true},
                // {field: 'operateMode',title: '经营方式',width: '20%'},
                // {field: 'createDate',title: '创建时间',width: '20%',formatter:function (value,row,index) {
                //         return dateFormat(value);
                //     }
                // },
                // {field: '_operate',		title: '许可证照片',	width: '20%',
                //     formatter: formatOperAttachmentOperate1
                // }
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {
                //initHistoryData(rowData);
            },
            onLoadSuccess:function(data){
                //$(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:300});
                //$(this).datagrid('selectRow',0);
            }
        })
        // $("#ezuiFormInfo input[id='productNameMain']").textbox('setValue',name);
        $("#ezuiFormProd input[id='licenseUrlFile1']").filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '300', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUploadProdLisense(data);
                }
            }
        });
        initChoseText();
    })
    function initChoseText() {
        $.ajax({
            url : sy.bp()+'/gspInstrumentCatalogController.do?searchCheckByLicenseId',
            data : {
                "licenseId":'${gspProdLicense.operateId}'
            }
            ,type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                if(result && result.length>0){
                    var arr = new Array();
                    for(var i=0;i<result.length;i++){
                        arr.push(result[i].operateName);
                    }
                    $("#ezuiFormProd input[id='showChose']").textbox("setValue",arr.join(","))
                }
            }
        });
    }
    /**
     * 数据提交

    function businessSubmit() {
        if(opType == "add"){
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认要进行换证操作吗，执行该操作原数据将会至为无效！', function(confirm) {
                if (!confirm) {

                }
            })
        }

        if($("#gspEnterpriseId").val() == ""){
            showMsg("请先保存企业基础信息！");
            return;
        }
        url = sy.bp()+"/gspEnterpriseInfoController.do?addBusinessLicense";
        var gspOperateFrom = new Object();
        var gspOperateOperateType = new Array();
        var isVal = true;
        $("#ezuiFormOperate input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    if(!$(this).textbox("isValid")){
                        isVal = false;
                        return;
                    }
                    gspOperateFrom[""+$(this).attr("id")+""] = $(this).textbox("getValue");
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    if(!$(this).datebox("isValid")){
                        isVal = false;
                        return;
                    }
                    //if(judgeDate($(this).datebox("getValue"))<0){
                    //    showMsg("日期不能大于当前日期！");
                    //    $(this).focus();
                    //    isVal = false;
                    //    return;
                    //}
                    gspOperateFrom[""+$(this).attr("id")+""] = $(this).datebox("getValue");
                }
            }
        })
        if(!isVal){//普通输入判断
            return;
        }
        if(!($("#isLong").is(':checked')) && $("#businessStartDate").datebox("getValue")>$("#businessEndDate").datebox("getValue"))
        {
            $("#businessStartDate").focus();
            showMsg("营业期限起始时间不能大于结束时间！");
            return;
        }
        if($("#attachmentUrl").val() == ""){
            showMsg("请上传营业执照附件！");
            return;
        }
        if(choseRowArrOperate.length ==0){
            showMsg("请选择营业执照经营范围！");
            return;
        }
        gspOperateFrom["attachmentUrl"] = $("#attachmentUrl").val();
        for(var i=0;i<choseRowArrOperate.length;i++){
            var obj = new Object();
            obj["enterpriseId"] = "";
            obj["operateId"] = choseRowArrOperate[i];
            gspOperateOperateType.push(obj);
        }
        //console.log(JSON.stringify(gspBusinessFrom))
        //console.log(JSON.stringify(gspBusinessOperateType))

        $.ajax({
            url : url,
            data : {
                "enterpriseId":$("#gspEnterpriseId").val(),
                "businessFormStr":JSON.stringify(gspOperateFrom),
                "operateDetailStr":JSON.stringify(gspOperateOperateType),
                "gspBusinessLicenseId":$("#gspBusinessLicenseId").val(),
                "opType":opType
            }
            ,type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
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
    }*/

    /**
     * 经营范围选择
     */
    function selectOperateScope1() {
        ezuidialogChoseScopeOperate1 = $('#dialogChoseScopeOperate1').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:800,
            height:500,
            href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=prodLicense&id=${gspProdLicense.operateId}',
            onClose : function() {
                ezuidialogChoseScopeOperate1.dialog('clear');
            }
        });
    }

    function doUploadProdLisense(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("licenseUrlFile1")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                console.log("url"+data);
                $("#ezuiFormProd input[id='licenseUrl']").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }

    function choseSelect_Catalog_prodLicense(row) {
        var choseRowNameArr = new Array();
        var choseRowArrProd = new Array();
        var Ipl = new Array();
        var IIpl = new Array();
        var IIIpl = new Array();
        //var oldValue = $("#ezuiFormOperate input[id='businessScope']").textbox("getValue");
        if(row instanceof Array){
            // alert(1111);
            for(var i=0;i<row.length;i++){
                if(row[i].classifyId=="I"){
                    Ipl.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
                }else if(row[i].classifyId=="II"){
                    IIpl.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
                }else if(row[i].classifyId=="III"){
                    IIIpl.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
                }
                choseRowArrProd.push(row[i].instrumentCatalogId);
                // choseRowNameArr.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);

            }
            $("#ezuiFormProd input[id='showChose']").textbox("setValue",Ipl.concat(IIpl).concat(IIIpl).join(","))
        }else{
            // alert(2222);
            choseRowArrProd.push(row.instrumentCatalogId);
            $("#ezuiFormProd input[id='showChose']").textbox("setValue","["+row.classifyId+"]"+row.instrumentCatalogName);
        }
        $("#ezuiFormProd input[id='choseScope']").val(choseRowArrProd.join(","));
        $(ezuidialogChoseScopeOperate1).dialog("close");
    }

    function viewUrlOperate1(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormProd input[id='licenseUrl']").val()!=""){
                showUrl($("#ezuiFormProd input[id='licenseUrl']").val());
            }else {
                showMsg("请上传许可证附件！");
            }
        }
    }

    function formatOperAttachmentOperate1(value,row,index){
        return "<a onclick=\"viewUrlOperate1('"+row.licenseUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function operateCopy1() {
        var row = ezuiOperateDatagridDetail1.datagrid("getSelected");
        initHistoryData1(row);
    }

    //加载历史证照信息
    function initHistoryData1(row) {
        $("#ezuiFormProd input[type!=hidden]").each(function (index) {
            console.log($(this).attr("class"));
            if($(this).attr("class")){
                if($(this).attr("id")) {
                    if ($(this).attr("class").indexOf('easyui-textbox') != -1) {
                        $(this).textbox("setValue", row["" + $(this).attr("id") + ""]);
                    }else if ($(this).attr("class").indexOf('easyui-datebox') != -1) {
                        $(this).datebox("setValue", dateFormat2(row["" + $(this).attr("id") + ""]));
                    }else if ($(this).attr("class").indexOf('easyui-numberbox') != -1) {
                        $(this).numberbox("setValue", row["" + $(this).attr("id") + ""]);
                    }
                }
            }
        })
       //  $("#ezuiFormProd input[id='licenseUrl']").val(row['licenseUrl'])
       // $("#licenseUrlFile1").filebox("setValue",row['licenseUrl']);
    }

    //换证清空当前数据
    function operateUpdate1() {
        opType = "update";
        $("#ezuiFormProd input[id='opType']").val("update");
        $("#ezuiFormProd input[type!=hidden]").each(function (index) {
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
        $("#ezuiFormProd input[id='licenseUrl']").val("");
        $("#licenseUrlFile1").filebox("setValue","");
    }

</script>