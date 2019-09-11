<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    table th{
        text-align: right;
    }
</style>
<div id='detailOperateToolbar' class='datagrid-toolbar' style='padding: 0px;background-color: #ffffff;'>
    <form id='ezuiFormOperate' method='post' style="padding: 0px;">
        <input type='hidden' data="1" id='operateId' name='operateId' value="${gspOperateLicense.operateId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspOperateLicense.enterpriseId}"/>
        <input type='hidden' id='choseScope' value="${choseScope}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table width="100%">
                <tr>
                    <th>企业名称</th>
                    <td><input type='text' value="${gspOperateLicense.enterpriseName}" data="1" id="enterpriseName" name='enterpriseName' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>经营许可证编号</th>
                    <td>
                        <input type='text' value="${gspOperateLicense.licenseNo}" data="1" id="licenseNo" name='licenseNo' class='easyui-textbox' data-options='required:true,width:300'/>
                    </td>

                </tr>
                <tr>
                    <%--<th>经营方式</th>--%>
                    <%--<td><input type='text' value="${gspOperateLicense.operateMode}" data="1" id="operateMode" name='operateMode' class='easyui-textbox' data-options='required:true,width:300'/></td>--%>
                    <th>经营方式</th>
                    <td><input type="text" data="1" id="operateMode" value="${gspOperateLicense.operateMode}" name="operateMode"  class='easyui-combobox'  data-options="panelHeight:'auto',
                                                                                                                                    required:true,
                                                                                                                                    width:300,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '批发', value: '批发'},
																																	{id: '零售', value: '零售'},
																																	{id: '批零兼营', value: '批零兼营'},
																																	{id: '/', value: '/'},
																																    ]" /></td>
                    <th>法定代表人</th>
                    <td><input type='text' value="${gspOperateLicense.juridicalPerson}" data="1" id="juridicalPerson" name='juridicalPerson' class='easyui-textbox' data-options='required:true,width:300'/></td>


                  </tr>
                <tr>
                    <th>住所</th>
                    <td><input type='text' value="${gspOperateLicense.residence}" data="1" id="residence" name='residence' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>企业负责人</th>
                    <td><input type='text' value="${gspOperateLicense.headName}" data="1" id="headName" name='headName' class='easyui-textbox' data-options='required:true,width:300'/></td>


                </tr>
                <tr>
                    <th>经营场所</th>
                    <td><input type='text' value="${gspOperateLicense.businessResidence}" data="1" id="businessResidence" name='businessResidence' class='easyui-textbox' data-options='required:true,width:300'/></td>

                    <th>发证部门</th>
                    <td><input type='text' value="${gspOperateLicense.registrationAuthority}" data="1" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:300'/></td>


                </tr>
                <tr>
                    <th>库房地址</th>
                    <td><input type='text' value="${gspOperateLicense.warehouseAddress}" data="1" id="warehouseAddress" name='warehouseAddress' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>发证日期</th>
                    <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspOperateLicense.approveDate}"/>" data="1" id="approveDate" name='approveDate' class='easyui-datebox' data-options='required:true,width:300'/></td>



                </tr>
                <tr>
                    <th>许可证照片</th>
                    <td>
                        <input id="licenseUrlFile" name='licenseUrlFile' value="${gspOperateLicense.licenseUrl}" atth="fileUpload" data-options='required:true,width:300'>
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlOperate()">查看</a>
                        <input type="hidden" data="2" class="textbox-value" name="licenseUrl" id="licenseUrl" value="${gspOperateLicense.licenseUrl}"/>
                        <!--<a onclick='businessSubmit()' id='ezuiDetailsBtn_save' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>-->
                    </td>
                    <th>有效期限</th>
                    <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspOperateLicense.licenseExpiryDate}"/>" data="1" id="licenseExpiryDate" name='licenseExpiryDate' class='easyui-datebox' data-options='required:true,width:300'/></td>

                </tr>
                <tr>
                    <th>经营范围</th>
                    <td >
                        <input type='text' data="1" id="showChose" name='showChose' style="height:45px;" class='easyui-textbox' data-options='required:true,multiline:true,width:300,editable:false'/>
                        <a onclick='selectOperateScope()' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>
                    </td>

                    <th>经营范围</th>
                    <td >
                        <input type='text' value="${gspOperateLicense.businessScope}" id="businessScope" name='businessScope' class='easyui-textbox' style="height: 60px;" data-options='required:true,multiline:true,width:300'/>
                    </td>

                </tr>


            </table>
        </fieldset>
    </form>
    <div>
        <a onclick='operateUpdate()' id='ezuiDetailsBtn_update' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>开始换证</a>
        <a onclick='operateCopy()' id='ezuiDetailsBtn_copy' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-back"' href='javascript:void(0);'>信息复用</a>
    </div>
    <fieldset>
        <legend>证照历史数据</legend>
        <table id='ezuiOperateDatagridDetail' ></table>
    </fieldset>
</div>
<div id="dialogChoseScopeOperate"></div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var ezuiOperateDatagridDetail;
    var ezuidialogChoseScopeOperate;
    var url;
    var opType = "add";
    $(function () {
        //控件初始化
        ezuiOperateDatagridDetail = $("#ezuiOperateDatagridDetail").datagrid({
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
                'enterpriseId':'${gspOperateLicense.enterpriseId}',
                'operateType': "OPERATE"
            },
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'operateId',
            columns : [[
                {field: 'isUse',title: '是否有效' ,width: '5%',formatter:isUseFormatter},
                {field: 'licenseNo',title: '许可证编号' ,width: '15%'},
                {field: 'approveDate',title:'发证日期', width: '15%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: 'licenseExpiryDate',title:'有效期限', width: '15%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: '_operate',		title: '许可证照片',	width: '10%',
                    formatter: formatOperAttachmentOperate
                },
                {field: 'createId',title: '创建人',width: '10%'},

                {field: 'createDate',title: '创建时间',width: '15%',formatter:function (value,row,index) {
                        return dateFormat(value);
                    }
                },

                {field: 'operateId',title:'主键',hidden:true},

                // {field: 'operateMode',title: '经营方式',width: '20%'},



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

        $('#licenseUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '300', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUploadOperateLisense(data);
                }
            }
        });
        initChoseText();
    })

    function initChoseText() {
        $.ajax({
            url : sy.bp()+'/gspInstrumentCatalogController.do?searchCheckByLicenseId',
            data : {
                "licenseId":'${gspOperateLicense.operateId}'
            }
            ,type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                if(result && result.length>0){
                    var arr = new Array();
                    for(var i=0;i<result.length;i++){
                        arr.push(result[i].operateName);

                    }
                    $("#ezuiFormOperate input[id='showChose']").textbox("setValue",arr.join(","))
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
    function selectOperateScope() {
        ezuidialogChoseScopeOperate = $('#dialogChoseScopeOperate').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:800,
            height:500,
            href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=operateLicense&id=${gspOperateLicense.operateId}',
            onClose : function() {
                ezuidialogChoseScopeOperate.dialog('clear');
            }
        });
    }

    function doUploadOperateLisense(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("licenseUrlFile")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                console.log("url"+data);
                $("#ezuiFormOperate input[id='licenseUrl']").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }

    function choseSelect_Catalog_operateLicense(row) {
        console.log(row);
        var choseRowNameArr = new Array();
        var choseRowArrOperate = new Array();
        //var oldValue = $("#ezuiFormOperate input[id='businessScope']").textbox("getValue");
        if(row instanceof Array){
            for(var i=0;i<row.length;i++){
                choseRowArrOperate.push(row[i].instrumentCatalogId);
                choseRowNameArr.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
            }
            $("#ezuiFormOperate input[id='showChose']").textbox("setValue",choseRowNameArr.join(","))
        }else{
            choseRowArrOperate.push(row.instrumentCatalogId);
            $("#ezuiFormOperate input[id='showChose']").textbox("setValue","["+row.classifyId+"]"+row.instrumentCatalogName);
        }
        $("#ezuiFormOperate input[id='choseScope']").val(choseRowArrOperate.join(","));
        $(ezuidialogChoseScopeOperate).dialog("close");
    }

    function viewUrlOperate(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormOperate input[id='licenseUrl']").val()!=""){
                showUrl($("#ezuiFormOperate input[id='licenseUrl']").val());
            }else {
                showMsg("请上传许可证附件！");
            }
        }
    }

    function formatOperAttachmentOperate(value,row,index){
        return "<a onclick=\"viewUrlOperate('"+row.licenseUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function operateCopy() {
        var row = ezuiOperateDatagridDetail.datagrid("getSelected");
        initHistoryData(row);
    }

    //加载历史证照信息
    function initHistoryData(row) {
        $("#ezuiFormOperate input[type!=hidden]").each(function (index) {
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
       //  $("#ezuiFormOperate input[id='licenseUrl']").val(row['licenseUrl'])
       // $("#licenseUrlFile").filebox("setValue",row['licenseUrl']);
    }

    //换证清空当前数据
    function operateUpdate() {
        // opType = "update";
        // $("#ezuiFormOperate input[id='opType']").val("update");
        $("#ezuiFormOperate input[type!=hidden]").each(function (index) {
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
        $("#ezuiFormOperate input[id='licenseUrl']").val("");
        $("#licenseUrlFile").filebox("setValue","");
    }

</script>