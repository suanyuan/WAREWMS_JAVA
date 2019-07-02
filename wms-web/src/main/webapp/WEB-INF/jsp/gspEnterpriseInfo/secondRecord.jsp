<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormRecord' method='post'>
    <input type='hidden' id='gspSecondRecordId' name='gspSecondRecordId' value="${gspSecondRecord.recordId}"/>
    <table>
        <tr>
            <th>备案编号</th>
            <td><input type='text' value="${gspSecondRecord.recordNo}" name='recordNo' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' value="${gspSecondRecord.enterpriseId}" name='enterpriseId' class='easyui-numberbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业负责人</th>
            <td><input type='text' value="${gspSecondRecord.headName}" name='headName' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营方式</th>
            <td><input type='text' value="${gspSecondRecord.operateMode}" name='operateMode' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营场所</th>
            <td><input type='text' value="${gspSecondRecord.operatePlace}" name='operatePlace' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>住所</th>
            <td><input type='text' value="${gspSecondRecord.residence}" name='residence' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案批准日期</th>
            <td><input type='text' value="${gspSecondRecord.approveDate}" name='approveDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案发证机关</th>
            <td><input type='text' value="${gspSecondRecord.registrationAuthority}" name='registrationAuthority' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' value="${gspSecondRecord.bussinessScope}" name='bussinessScope' class='easyui-textbox' size='100' style="height: 150px;" data-options='required:true,multiline:true'/></td>
        </tr>
        <tr>
            <th>备案照片</th>
            <td>
                <input type="hidden" class="textbox-value" name="recordUrl" id="recordUrl" value="${gspSecondRecord.attachmentUrl}"/>
                <input id="recordFile" name='recordFile' value="${gspSecondRecord.attachmentUrl}">
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
            </td>
        </tr>
    </table>
</form>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $(function () {
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

        /*var row = ezuiDatagrid.datagrid('getSelected');
        if(row){
            $.ajax({
                url : 'gspEnterpriseInfoController.do?getSecondRecord',
                data : {"enterpriseId" : row.enterpriseId},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $("#ezuiFormRecord input[id!=''][data='1']").each(function (index) {
                            $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                        })
                        $("#recordUrl").val(result.obj.attachmentUrl);
                        $("#recordFile").val(result.obj.attachmentUrl);
                    }
                }
            });
        }*/

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
</script>