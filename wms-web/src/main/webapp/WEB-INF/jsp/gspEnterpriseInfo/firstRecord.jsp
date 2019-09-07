<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    table th{
        text-align: right;
    }
</style>
<div id='detailFirstRecordToolbar' class='datagrid-toolbar' style='padding: 0px;background-color: #ffffff;'>
    <form id='ezuiFormFirstRecord' method='post' style="padding: 0px;">
        <input type='hidden' data="1" id='recordId' name='recordId' value="${gspFirstRecord.recordId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspFirstRecord.enterpriseId}"/>
        <input type='hidden' id='choseScope' value="${choseScope}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table>
                <tr>
                    <th>企业名称</th>
                    <td><input type='text' value="${gspFirstRecord.enterpriseName}" id="enterpriseName" name='enterpriseName' data-options='required:true,width:300' class='easyui-textbox'/></td>
                    <th>备案编号</th>
                    <td><input type='text' value="${gspFirstRecord.recordNo}" id="recordNo" name='recordNo' class='easyui-textbox' data-options='required:true,width:300'/></td>

                </tr>
                <tr>
                    <th>住所</th>
                    <td><input type='text' value="${gspFirstRecord.residence}" id="residence" name='residence' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>生产场所</th>
                    <td><input type='text' value="${gspFirstRecord.reservedfield1}" id="reservedfield1" name='reservedfield1' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <%--<th>经营方式</th>--%>
                    <%--<td><input type='text' value="${gspFirstRecord.operateMode}" id="operateMode" name='operateMode' class='easyui-textbox' data-options='required:true,width:200'/></td>--%>
                </tr>
                <tr>
                    <th>法定代表人</th>
                    <td><input type='text' data="1" value="${gspFirstRecord.reservedfield2}" id="reservedfield2" name='reservedfield2' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>企业负责人</th>
                    <td><input type='text' value="${gspFirstRecord.headName}" id="headName" name='headName' class='easyui-textbox' data-options='required:true,width:300'/></td>
                </tr>
                <tr>
                    <th>备案部门</th>
                    <td><input type='text' value="${gspFirstRecord.registrationAuthority}" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:300'/></td>
                    <th>备案日期</th>
                    <td><input type='text' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspFirstRecord.approveDate}"/>" id="approveDate" name='approveDate' class='easyui-datebox' data-options='required:true,width:300'/></td>
                </tr>
                <tr>
                    <%--<th>经营范围</th>--%>
                    <%--<td colspan="2">--%>
                        <%--<input type='text' value="${gspFirstRecord.businessScope}" id="businessScope" name='businessScope' class='easyui-textbox' style="height: 60px;" data-options='required:true,multiline:true,width:350'/>--%>
                    <%--</td>--%>
                    <th>生产范围(已选择)</th>
                    <td colspan="1">
                        <input type='text' id="bussinessScope" class='easyui-textbox' style="height: 60px;" data-options='required:true,multiline:true,width:300,editable:false'/>
                        <a onclick='selectRecordScope1()' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>
                    </td>

                    <th>备案照片</th>
                    <td>
                        <input type="hidden" data="2" class="textbox-value" name="recordUrl" id="recordUrl" value="${gspFirstRecord.recordUrl}"/>
                        <input id="firstRecordFile" name='firstRecordFile' value="${gspFirstRecord.recordUrl}" atth="fileUpload" data-options='required:true,width:300' >
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlSecond()">查看</a>
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
        <table id='ezuiFirstRecordDatagridDetail' ></table>
    </fieldset>
</div>
<div id="dialogChoseFirstRecord"></div>


<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>

    var ezuiFirstRecordDatagridDetail;
    var ezuidialogChoseScopeRecord1;
    var opType = "add";

    $(function () {
        //初始化显示企业信息
        /*if($("#ezuiFormInfo input[id='enterpriseName']")){
            $("#recordEnterprise").textbox({
                value:$("#ezuiFormInfo input[id='enterpriseName']").textbox("getValue")
            });
        }*/

        //控件初始化
        ezuiFirstRecordDatagridDetail = $("#ezuiFirstRecordDatagridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?firstRecordHistoryDatagridList',
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
            queryParams:{'enterpriseId':'${gspFirstRecord.enterpriseId}'},
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'operateId',
            columns : [[
                {field: 'isUse',title: '是否有效' ,width: '5%',formatter:isUseFormatter},
                {field: 'recordNo',title: '备案号' ,width: '15%'},
                {field: 'approveDate',title:'备案日期', width: '15%',formatter:function (value,row,index) {
                        return dateFormat2(value);
                    }},
                {field: '_operate',		title: '备案照片',	width: '20%',
                    formatter: formatOperAttachmentRecord
                },
                {field: 'createId',title: '创建人',width: '10%'},
                {field: 'createDate',title: '创建时间',width: '20%',formatter:function (value,row,index) {
                        return dateFormat(value);
                    }
                },
                {field: 'operateId',title:'主键',hidden:true},
                // {field: 'operateMode',title: '经营方式',width: '20%'},
                // {field: 'createDate',title: '创建时间',width: '20%',formatter:function (value,row,index) {
                //         return dateFormat(value);
                //     }
                // },

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

        $('#firstRecordFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '300', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUploadSecondRecord(data);
                }
            }
        });

        initChoseText();

    })

    function initChoseText() {
        $.ajax({
            url : sy.bp()+'/gspInstrumentCatalogController.do?searchCheckByLicenseId',
            data : {
                "licenseId":'${gspFirstRecord.recordId}'
            }
            ,type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                if(result && result.length>0){
                    var arr = new Array();
                    for(var i=0;i<result.length;i++){
                        arr.push(result[i].operateName);
                    }
                    $("#ezuiFormFirstRecord input[id='bussinessScope']").textbox("setValue",arr.join(","))
                }
            }
        });
    }

    function doUploadSecondRecord(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("firstRecordFile")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                $("#ezuiFormFirstRecord #recordUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }

    function formatOperAttachmentRecord(value,row,index){
        return "<a onclick=\"viewUrlSecond('"+row.recordUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    /**
     * 经营范围选择
     */
    function selectRecordScope1() {
        ezuidialogChoseScopeRecord1 = $('#dialogChoseFirstRecord').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:800,
            height:500,
            href:sy.bp()+'/gspInstrumentCatalogController.do?toSearch&target=firstRecord&id=${gspFirstRecord.recordId}',
            onClose : function() {
                ezuidialogChoseScopeRecord1.dialog('clear');
            }
        });
    }

    function choseSelect_Catalog_firstRecord(row) {
        var choseRowNameArr = new Array();
        var choseRowArrRecord = new Array();
        //var oldValue = $("#ezuiFormRecord input[id='showChose']").textbox("getValue");
        if(row instanceof Array){
            for(var i=0;i<row.length;i++){
                choseRowArrRecord.push(row[i].instrumentCatalogId);
                choseRowNameArr.push("["+row[i].classifyId+"]"+row[i].instrumentCatalogName);
            }
            $("#ezuiFormFirstRecord input[id='bussinessScope']").textbox("setValue",choseRowNameArr.join(","))
        }else{
            choseRowArrRecord.push(row.instrumentCatalogId);
            $("#ezuiFormFirstRecord input[id='bussinessScope']").textbox("setValue","["+row.classifyId+"]"+row.instrumentCatalogName);
        }
        $("#ezuiFormFirstRecord input[id='choseScope']").val(choseRowArrRecord.join(","));
        $(ezuidialogChoseScopeRecord1).dialog("close");
    }

    function viewUrlSecond(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormFirstRecord #recordUrl").val()!=""){
                showUrl($("#ezuiFormFirstRecord #recordUrl").val());
            }else {
                showMsg("请上传许可证附件！");
            }
        }
    }

    function recordCopy() {
        var row = ezuiFirstRecordDatagridDetail.datagrid("getSelected");
        initHistoryData(row);
    }

    //加载历史证照信息
    function initHistoryData(row) {
        $("#ezuiFormFirstRecord input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue",row[""+$(this).attr("id")+""]);
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue",dateFormat2(row[""+$(this).attr("id")+""]));
                }
            }
        })
        //$("#recordUrl").val(row['recordUrl'])
        //$("#recordFile").filebox("setValue",row['recordUrl']);

        //加载已选择的范围
        initChoseText();

        if($("#ezuiFormInfo input[id='enterpriseName']")){

            $("#firstRecordEnterprise").textbox({
                value:$("#ezuiFormInfo input[id='enterpriseName']").textbox("getValue")
            });
        }
    }

    //换证清空当前数据
    function recordUpdate() {
        // opType = "update";
        // $("#ezuiFormFirstRecord #opType").val("update");
        $("#ezuiFormFirstRecord input[type!=hidden]").each(function (index) {
            if($(this).attr("class")){
                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                    $(this).textbox("setValue","");
                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                    $(this).datebox("setValue","");
                }
            }
        })
        $("#ezuiFormFirstRecord input[id='bussinessScope']").val("")
        $("#ezuiFormFirstRecord #recordUrl").val("")
        $("#firstRecordFile").filebox("setValue","");
    }

</script>