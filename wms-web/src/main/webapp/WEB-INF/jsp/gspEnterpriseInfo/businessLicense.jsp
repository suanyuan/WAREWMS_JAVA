<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    table th{
        text-align: right;
    }
</style>
<div id='detailBusinessToolbar' class='datagrid-toolbar' style='padding: 0px;background-color: #ffffff;'>
    <form id='ezuiFormBusiness' method='post' style="padding: 0px;">
        <input type='hidden' data="1" id='businessId' name='businessId' value="${gspBusinessLicense.businessId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspBusinessLicense.enterpriseId}"/>
        <input type='hidden' id='choseScope' value="${choseScope}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table>
                <tr>
                    <th>证照编号</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.licenseNumber}" id="licenseNumber" name='licenseNumber' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>统一社会信用代码</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.socialCreditCode}" id="socialCreditCode" name='socialCreditCode' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>名称</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.licenseName}" id="licenseName" name='licenseName' class='easyui-textbox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>类型</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.licenseType}" id="licenseType" name='licenseType' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>法定代表人</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.juridicalPerson}" id="juridicalPerson" name='juridicalPerson' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>注册资本</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.registeredCapital}" id="registeredCapital" name='registeredCapital' class='easyui-textbox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>住所</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.residence}" id="residence" name='residence' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>登记机关</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.registrationAuthority}" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>成立日期</th>
                    <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.establishmentDate}"/>" id="establishmentDate" name='establishmentDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>发证日期</th>
                    <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.issueDate}"/>" id="issueDate" name='issueDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
                    <th>营业期限时间</th>
                    <td colspan="3">
                        <input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.businessStartDate}"/>" id="businessStartDate" name='businessStartDate' class='easyui-datebox' data-options='required:true,width:200'/>
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.businessEndDate}"/>" id="businessEndDate" name='businessEndDate' class='easyui-datebox' data-options='required:true,width:180<c:if test="${gspBusinessLicense.isLong == '1'}">,disabled:true</c:if>'/>
                        <input id="isLong" <c:if test="${gspBusinessLicense.isLong == '1'}">checked</c:if> type="checkbox" class="checkbox"><label for="isLong">长期/无固定时间</label>
                    </td>
                </tr>
                <tr>
                    <th>营业执照照片</th>
                    <td>
                        <input id="file" name='file' value="${gspBusinessLicense.attachmentUrl}" atth="fileUpload">
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlBusiness()">查看</a>
                        <input type="hidden" data="2" class="textbox-value" name="attachmentUrl" id="attachmentUrl" value="${gspBusinessLicense.attachmentUrl}"/>
                        <!--<a onclick='businessSubmit()' id='ezuiDetailsBtn_save' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>-->
                    </td>
                    <th>经营范围</th>
                    <td colspan="5">
                        <input type='text' data="1" value="${gspBusinessLicense.businessScope}" id="businessScope" name='businessScope' style="height:45px;" class='easyui-textbox' data-options='required:true,multiline:true,width:400'/>
                        <!--<a onclick='selectBusinessScope()' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>-->
                    </td>
                </tr>

            </table>
        </fieldset>
    </form>
    <div>
        <a onclick='businessUpdate()' id='ezuiDetailsBtn_update' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>开始换证</a>
        <a onclick='businessCopy()' id='ezuiDetailsBtn_copy' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-back"' href='javascript:void(0);'>信息复用</a>
    </div>
    <fieldset>
        <legend>证照历史数据</legend>
        <table id='ezuiBussinessDatagridDetail' ></table>
    </fieldset>
</div>
<div id="dialogChoseScope"></div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var ezuiBussinessDatagridDetail;
    var ezuidialogChoseScope;
    var url;
    var opType = "add";
    $(function () {
        //控件初始化
        ezuiBussinessDatagridDetail = $("#ezuiBussinessDatagridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?businessHistoryDatagridList',
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
            queryParams:{'enterpriseId':'${gspBusinessLicense.enterpriseId}'},
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'businessId',
            columns : [[
                {field: 'businessId',title:'主键',hidden:true},
                {field: 'licenseNumber',title: '统一社会信用代码' ,width: '20%'},
                {field: 'socialCreditCode',title: '证照编号',width: '20%'},
                {field: 'isUse',title: '是否有效' ,width: '20%',formatter:isUseFormatter},
                {field: 'createDate',title: '创建时间',width: '20%',formatter:function (value,row,index) {
                        return dateFormat(value);
                    }
                },
                {field: '_operate',		title: '营业执照照片',	width: '20%',
                    formatter: formatOperAttachment
                }
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

        $('#file').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUploadbusinessLicense(data);
                }
            }
        });
        
        $("#isLong").click(function () {
            if($(this).is(':checked')){
                //$("#businessStartDate").textbox("clear")
                $("#businessEndDate").textbox("clear")
                //$("#businessStartDate").textbox("disable")
                $("#businessEndDate").textbox("disable")
            }else{
                $("#businessStartDate").textbox("enable")
                $("#businessEndDate").textbox("enable")
            }
        })
    })

    /**
     * 数据提交
     */
    function businessSubmit() {
        /*if(opType == "add"){
            $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认要进行换证操作吗，执行该操作原数据将会至为无效！', function(confirm) {
                if (!confirm) {

                }
            })
        }*/
        if($("#gspEnterpriseId").val() == ""){
            showMsg("请先保存企业基础信息！");
            return;
        }
        url = sy.bp()+"/gspEnterpriseInfoController.do?addBusinessLicense";
        var gspBusinessFrom = new Object();
        var gspBusinessOperateType = new Array();
        var isVal = true;
        $("#ezuiFormBusiness input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    if(!$(this).textbox("isValid")){
                        isVal = false;
                        return;
                    }
                    gspBusinessFrom[""+$(this).attr("id")+""] = $(this).textbox("getValue");
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
                    gspBusinessFrom[""+$(this).attr("id")+""] = $(this).datebox("getValue");
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
        if(choseRowArr.length ==0){
            showMsg("请选择营业执照经营范围！");
            return;
        }
        gspBusinessFrom["attachmentUrl"] = $("#attachmentUrl").val();
        for(var i=0;i<choseRowArr.length;i++){
            var obj = new Object();
            obj["enterpriseId"] = "";
            obj["operateId"] = choseRowArr[i];
            gspBusinessOperateType.push(obj);
        }
        //console.log(JSON.stringify(gspBusinessFrom))
        //console.log(JSON.stringify(gspBusinessOperateType))

        $.ajax({
            url : url,
            data : {
                "enterpriseId":$("#gspEnterpriseId").val(),
                "businessFormStr":JSON.stringify(gspBusinessFrom),
                "operateDetailStr":JSON.stringify(gspBusinessOperateType),
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
    }

    /**
     * 经营范围选择
     */
    function selectBusinessScope() {
        ezuidialogChoseScope = $('#dialogChoseScope').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:800,
            height:500,
            href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=businessLicense&id=${gspBusinessLicense.businessId}',
            onClose : function() {

            }
        });
    }

    function doUploadbusinessLicense(data) {
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
                $("#attachmentUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }
    
    function choseSelect_Catalog_businessLicense(row) {
        var choseRowNameArr = new Array();
        var choseRowArr = new Array();
        //var oldValue = $("#businessScope").textbox("getValue");
        if(row instanceof Array){
            for(var i=0;i<row.length;i++){
                choseRowArr.push(row[i].instrumentCatalogId);
                choseRowNameArr.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
            }
            $("#businessScope").textbox("setValue",choseRowNameArr.join(","))
        }else{
            choseRowArr.push(row.instrumentCatalogId);
           $("#businessScope").textbox("setValue","["+row.classifyId+"]"+row.instrumentCatalogName);
        }
        $("#ezuiFormBusiness input[id='choseScope']").val(choseRowArr.join(","));
        $(ezuidialogChoseScope).dialog("close");
    }
    
    function viewUrlBusiness(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#attachmentUrl").val()!=""){
                showUrl($("#attachmentUrl").val());
            }else {
                showMsg("请上传营业执照附件！");
            }
        }
    }
    
    function formatOperAttachment(value,row,index){
        return "<a onclick=\"viewUrlBusiness('"+row.attachmentUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function businessCopy() {
        var row = ezuiBussinessDatagridDetail.datagrid("getSelected");
        initHistoryData(row);
    }

    //加载历史证照信息
    function initHistoryData(row) {
        $("#ezuiFormBusiness input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue",row[""+$(this).attr("id")+""]);
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue",dateFormat2(row[""+$(this).attr("id")+""]));
                }
            }
        })
        if(row.isLong == "1"){//长期营业执照
            $("#ezuiFormBusiness input[id='isLong']").attr("checked","checked");
            $("#ezuiFormBusiness input[id='businessStartDate']").datebox("disable");
            $("#ezuiFormBusiness input[id='businessEndDate']").datebox("disable");
        }
    }

    //换证清空当前数据
    function businessUpdate() {
        opType = "update";
        console.log($("#ezuiFormBusiness #ezuiDetailsBtn_copy"));
        $("#ezuiFormBusiness #opType").val("update");
        $("#ezuiFormBusiness input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue","");
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue","");
                }
            }
        })
        $("#attachmentUrl").val("")
        $("#file").filebox("setValue","");
        //$("#ezuiFormBusiness #ezuiDetailsBtn_copy").linkbutton("enable");
        $("#ezuiFormBusiness input[id='isLong']").removeAttr("checked");
        $("#ezuiFormBusiness input[id='businessStartDate']").datebox("enable");
        $("#ezuiFormBusiness input[id='businessEndDate']").datebox("enable");

    }

</script>