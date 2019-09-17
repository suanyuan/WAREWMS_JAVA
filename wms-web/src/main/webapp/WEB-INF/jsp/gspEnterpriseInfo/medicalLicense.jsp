<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    table th{
        text-align: right;
    }
</style>
<div id='detailMedicalToolbar' class='datagrid-toolbar' style='padding: 0px;background-color: #ffffff;'>
    <form id='ezuiFormMedical' method='post' style="padding: 0px;">
        <input type='hidden' data="1" id='medicalId' name='medicalId' value="${gspMedicalLicense.medicalId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspMedicalLicense.enterpriseId}"/>
        <input type='hidden' id='enterpriseId' value="add"/>

        <input type='hidden' id='choseScope' value="${choseScope}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table>
                <tr>
                    <th>医疗机构名称</th>
                    <td><input type='text' data="1" value="${gspMedicalLicense.medicalName}" id="medicalName" name='medicalName' class='easyui-textbox' data-options='required:true,width:300'/></td>

                    <th>医疗机构登记号</th>
                    <td><input type='text' data="1" value="${gspMedicalLicense.medicalRegisterNo}" id="medicalRegisterNo" name='medicalRegisterNo' class='easyui-textbox' data-options='required:true,width:300'/></td>

                </tr>
                <tr>
                    <th>地址</th>
                    <td><input type='text' data="1" value="${gspMedicalLicense.medicalAddress}" id="medicalAddress" name='medicalAddress' class='easyui-textbox' data-options='required:true,width:300'/></td>

                    <th>法定代表人</th>
                    <td><input type='text' data="1" value="${gspMedicalLicense.juridicalPerson}" id="juridicalPerson" name='juridicalPerson' class='easyui-textbox' data-options='required:true,width:300'/></td>

                </tr>
                <tr>
                    <th>发证机关</th>
                    <td><input type='text' data="1" value="${gspMedicalLicense.registrationAuthority}" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>主要负责人</th>
                    <td><input type='text' data="1" value="${gspMedicalLicense.headName}" id="headName" name='headName' class='easyui-textbox' data-options='required:true,width:300'/></td>


                </tr>
                <tr>
                    <th>发证日期</th>
                    <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspMedicalLicense.approveDate}"/>" id="approveDate" name='approveDate' class='easyui-datebox' data-options='required:true,width:300'/></td>
                    <th>许可证照片</th>
                    <td>
                        <input id="medicalFile" name='medicalFile' value="${gspMedicalLicense.recordUrl}" atth="fileUpload" data-options="width:300" />
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlBusiness()">查看</a>
                        <input type="hidden" data="2" class="textbox-value" name="recordUrl" id="recordUrl" value="${gspMedicalLicense.recordUrl}"/>
                        <!--<a onclick='businessSubmit()' id='ezuiDetailsBtn_save' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>-->
                    </td>

                </tr>
                <tr>
                    <th>有效期限</th>
                    <td>
                        <input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspMedicalLicense.licenseExpiryDateBegin}"/>" id="licenseExpiryDateBegin" name='licenseExpiryDateBegin' class='easyui-datebox' data-options='required:true,width:300<c:if test="${gspMedicalLicense.isLong == '1'}">,disabled:true</c:if>'/>
                    </td>
                    <th>至</th>
                    <td>
                        <input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspMedicalLicense.licenseExpiryDateEnd}"/>" id="licenseExpiryDateEnd" name='licenseExpiryDateEnd' class='easyui-datebox' data-options='required:true,width:300<c:if test="${gspMedicalLicense.isLong == '1'}">,disabled:true</c:if>'/>
                        <%--<input id="isLong" <c:if test="${gspMedicalLicense.isLong == '1'}">checked</c:if> type="checkbox" class="checkbox"><label for="isLong">长期/无固定时间</label>--%>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
    <div>
        <a onclick='medecalUpdate()' id='ezuiDetailsBtn_update' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>开始换证</a>
        <a onclick='medecalCopy()' id='ezuiDetailsBtn_copy' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-back",disabled:true' href='javascript:void(0);'>信息复用</a>
    </div>
    <fieldset>
        <legend>证照历史数据</legend>
        <table id='ezuiMedicalDatagridDetail' ></table>
    </fieldset>
</div>
<div id="dialogChoseScope"></div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var ezuiMedicalDatagridDetail;
    var ezuidialogChoseScope;
    var url;
    var opType = "add";
    $(function () {
        //控件初始化
        ezuiMedicalDatagridDetail = $("#ezuiMedicalDatagridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?medicalHistoryDatagridList',
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
            queryParams:{'enterpriseId':'${gspMedicalLicense.enterpriseId}'},
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'medicalId',
            columns : [[
                {field: 'isUse',title: '是否有效' ,width: '5%',formatter:isUseFormatter},
                {field: 'medicalRegisterNo',title: '医疗机构登记号' ,width: '20%'},
                {field: 'approveDate',title:'发证日期',width: '10%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: 'licenseExpiryDateBegin',title:'有效期限起始',width: '10%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: 'licenseExpiryDateEnd',title:'有效期限结束',width: '10%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: '_operate',		title: '许可证照照片',	width: '10%',
                    formatter: formatOperAttachment
                },
                {field: 'createId',title: '创建人',width: '10%'},
                {field: 'createDate',title: '创建时间',width: '20%',formatter:function (value,row,index) {
                        return dateFormat(value);
                    }
                },


                {field: 'businessId',title:'主键',hidden:true},




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

        $('#medicalFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '300', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUploadMedcalLicense(data);
                }
            }
        });
        
        $("#ezuiFormMedical #isLong").click(function () {
            if($(this).is(':checked')){
                $("#licenseExpiryDateBegin").textbox("clear")
                $("#licenseExpiryDateEnd").textbox("clear")
                $("#licenseExpiryDateBegin").textbox("disable")
                $("#licenseExpiryDateEnd").textbox("disable")
            }else{
                $("#licenseExpiryDateBegin").textbox("enable")
                $("#licenseExpiryDateEnd").textbox("enable")
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
        $("#ezuiFormMedical input[type!=hidden]").each(function (index) {
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
        if($("#ezuiFormMedical input[id='recordUrl']").val() == ""){
            showMsg("请上传营业执照附件！");
            return;
        }
        if(choseRowArr.length ==0){
            showMsg("请选择营业执照经营范围！");
            return;
        }
        gspBusinessFrom["recordUrl"] = $("#ezuiFormMedical input[id='recordUrl']").val();
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
                "enterpriseId":$("#ezuiFormMedical #gspEnterpriseId").val(),
                "businessFormStr":JSON.stringify(gspBusinessFrom),
                "operateDetailStr":JSON.stringify(gspBusinessOperateType),
                "gspBusinessLicenseId":$("#ezuiFormMedical #gspBusinessLicenseId").val(),
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
    <%--function selectBusinessScope() {--%>
        <%--ezuidialogChoseScope = $('#dialogChoseScope').dialog({--%>
            <%--modal : true,--%>
            <%--title : '<spring:message code="common.dialog.title"/>',--%>
            <%--width:800,--%>
            <%--height:500,--%>
            <%--href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=businessLicense&id=${gspMedicalLicense.businessId}',--%>
            <%--onClose : function() {--%>

            <%--}--%>
        <%--});--%>
    <%--}--%>

    function doUploadMedcalLicense(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("medicalFile")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                $("#ezuiFormMedical input[id='recordUrl']").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }
    
    // function choseSelect_Catalog_businessLicense(row) {
    //     var choseRowNameArr = new Array();
    //     var choseRowArr = new Array();
    //     //var oldValue = $("#businessScope").textbox("getValue");
    //     if(row instanceof Array){
    //         for(var i=0;i<row.length;i++){
    //             choseRowArr.push(row[i].instrumentCatalogId);
    //             choseRowNameArr.push(row[i].instrumentCatalogName);
    //         }
    //         $("#businessScope").textbox("setValue",choseRowNameArr.join(","))
    //     }else{
    //         choseRowArr.push(row.instrumentCatalogId);
    //        $("#businessScope").textbox("setValue",row.instrumentCatalogName);
    //     }
    //     $("#ezuiFormMedical input[id='choseScope']").val(choseRowArr.join(","));
    //     $(ezuidialogChoseScope).dialog("close");
    // }
    
    function viewUrlMedical(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormMedical input[id='recordUrl']").val()!=""){
                showUrl($("#ezuiFormMedical input[id='recordUrl']").val());
            }else {
                showMsg("请上传营业执照附件！");
            }
        }
    }
    
    function formatOperAttachment(value,row,index){
        return "<a onclick=\"viewUrlMedical('"+row.recordUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function medecalCopy() {
        var row = ezuiMedicalDatagridDetail.datagrid("getSelected");
        initHistoryData(row);
    }

    //加载历史证照信息
    function initHistoryData(row) {
        $("#ezuiFormMedical input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue",row[""+$(this).attr("id")+""]);
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue",dateFormat2(row[""+$(this).attr("id")+""]));
                }
            }
        })
        if(row.isLong == "1"){//长期营业执照
            $("#ezuiFormMedical input[id='isLong']").attr("checked","checked");
            $("#ezuiFormMedical input[id='businessStartDate']").datebox("disable");
            $("#ezuiFormMedical input[id='businessEndDate']").datebox("disable");
        }
    }

    //换证清空当前数据
    function medecalUpdate() {
        opType = "update";
        $("#ezuiFormMedical #opType").val("update");
        $("#ezuiFormMedical input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue","");
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue","");
                }
            }
        })
        $("#ezuiFormMedical input[id='recordUrl']").val("");
        $("#ezuiFormMedical input[id='medicalFile']").filebox("setValue","");
        $("#ezuiFormMedical #ezuiDetailsBtn_copy").linkbutton("enable");
        $("#ezuiFormMedical input[id='isLong']").removeAttr("checked");
        $("#ezuiFormMedical input[id='businessStartDate']").datebox("enable");
        $("#ezuiFormMedical input[id='businessEndDate']").datebox("enable");

    }

</script>