<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<style>
    table th{
        text-align: right;
    }
</style>
<div id='detailRecordToolbar' class='datagrid-toolbar' style='padding: 0px;'>
    <form id='ezuiFormRecord' method='post' style="padding: 0px;">
        <input type='hidden' id='gspSecondRecordId' name='gspSecondRecordId' value="${gspSecondRecord.recordId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspOperateLicense.enterpriseId}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table>
                <tr>
                    <th>备案编号</th>
                    <td><input type='text' value="${gspSecondRecord.recordNo}" name='recordNo' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>企业名称</th>
                    <td><input type='text' value="" id="recordEnterprise" name='enterpriseId' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>企业负责人</th>
                    <td><input type='text' value="${gspSecondRecord.headName}" name='headName' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>经营方式</th>
                    <td><input type='text' value="${gspSecondRecord.operateMode}" name='operateMode' class='easyui-textbox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>经营场所</th>
                    <td><input type='text' value="${gspSecondRecord.operatePlace}" name='operatePlace' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>住所</th>
                    <td><input type='text' value="${gspSecondRecord.residence}" name='residence' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>备案批准日期</th>
                    <td><input type='text' value="${gspSecondRecord.approveDate}" name='approveDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
                    <th>备案发证机关</th>
                    <td><input type='text' value="${gspSecondRecord.registrationAuthority}" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>备案照片</th>
                    <td>
                        <input type="hidden" data="1" class="textbox-value" name="recordUrl" id="recordUrl" value="${gspSecondRecord.recordUrl}"/>
                        <input id="recordFile" name='recordFile' value="${gspSecondRecord.recordUrl}">
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
                    </td>
                    <th>经营范围</th>
                    <td colspan="3">
                        <input type='text' value="${gspSecondRecord.bussinessScope}" name='bussinessScope' class='easyui-textbox' style="height: 40px;" data-options='required:true,multiline:true,width:480'/>
                    </td>
                    <td style="text-align: left" colspan="2">
                        <a onclick='selectRecordScope()' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
    <div>
        <a onclick='recordUpdate()' id='ezuiDetailsBtn_update' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>开始换证</a>
        <a onclick='recordCopy()' id='ezuiDetailsBtn_copy' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-back"' href='javascript:void(0);'>信息复用</a>
    </div>
    <fieldset>
        <legend>证照历史数据</legend>
        <table id='ezuiRecordDatagridDetail' ></table>
    </fieldset>
</div>
<div id="dialogChoseScopeRecord"></div>


<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>

    var ezuiRecordDatagridDetail;
    var ezuidialogChoseScopeRecord;
    var choseRowArrRecord = new Array();
    var opType = "add";

    $(function () {
        //初始化显示企业信息
        $("#ezuiFormInfo input[id='enterpriseName']").each(function (index) {
            //$("#recordEnterprise").textbox("setValue",$(this).textbox("getValue"));
        })

        //控件初始化
        ezuiRecordDatagridDetail = $("#ezuiRecordDatagridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?recordHistoryDatagridList',
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
            queryParams:{'enterpriseId':'${gspSecondRecord.recordId}'},
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'operateId',
            columns : [[
                {field: 'operateId',title:'主键',hidden:true},
                {field: 'recordNo',title: '备案编号' ,width: '20%'},
                {field: 'operateMode',title: '经营方式',width: '20%'},
                {field: 'isUse',title: '是否有效' ,width: '20%'},
                {field: 'createDate',title: '创建时间',width: '20%',formatter:function (value,row,index) {
                        return dateFormat(value);
                    }
                },
                {field: '_operate',		title: '许可证照片',	width: '20%',
                    formatter: formatOperAttachmentRecord
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

        $('#recordFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });

    })

    function doUpload(data) {
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
                console.log(data);
                $("#recordUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }

    function formatOperAttachmentRecord(value,row,index){
        return "<a onclick=\"viewUrl('"+row.licenseUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    /**
     * 经营范围选择
     */
    function selectRecordScope() {
        ezuidialogChoseScopeRecord = $('#dialogChoseScopeRecord').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:800,
            height:500,
            href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=secondRecord&id=${gspSecondRecord.recordId}',
            onClose : function() {

            }
        });
    }

    function choseSelect_Catalog_secondRecord(row) {
        var choseRowNameArr = new Array();
        var oldValue = $("#ezuiFormRecord input[name = 'businessScope']").textbox("getValue");
        if(row instanceof Array){
            for(var i=0;i<row.length;i++){
                choseRowArrRecord.push(row[i].instrumentCatalogId);
                choseRowNameArr.push(row[i].instrumentCatalogName);
            }
            $("#ezuiFormRecord input[name = 'businessScope']").textbox("setValue",oldValue+choseRowNameArr.join(","))
        }else{
            choseRowArrRecord.push(row.instrumentCatalogId);
            $("#ezuiFormRecord input[name = 'businessScope']").textbox("setValue",oldValue+row.instrumentCatalogName);
        }
        $(ezuidialogChoseScopeRecord).dialog("close");
    }

    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#licenseUrl").val()!=""){
                showUrl($("#licenseUrl").val());
            }else {
                showMsg("请上传许可证附件！");
            }
        }
    }

    function recordCopy() {
        var row = ezuiRecordDatagridDetail.datagrid("getSelected");
        initHistoryData(row);
    }

    //加载历史证照信息
    function initHistoryData(row) {

        $("#ezuiFormBusiness input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue",row[""+$(this).attr("id")+""]);
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue",row[""+$(this).attr("id")+""]);
                }
            }
        })
        $("#recordUrl").val(row['recordUrl'])
        $("#recordFile").filebox("setValue",row['recordUrl']);
    }

    //换证清空当前数据
    function recordUpdate() {
        opType = "update";
        $("#opType").val("update");
        $("#ezuiFormBusiness input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue","");
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue","");
                }
            }
        })
        $("#recordUrl").val("")
        $("#recordFile").filebox("setValue","");
    }

</script>