<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormOperate' method='post'>
    <input type='hidden' id='gspOperateLicenseId' name='gspOperateLicenseId'/>
    <table>
        <tr>
            <th>企业名称</th>
            <td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='100' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证编号</th>
            <td><input type='text' data="1" id="licenseNo" name='licenseNo' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营方式</th>
            <td><input type='text' data="1" id="operateMode" name='operateMode' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业负责人</th>
            <td><input type='text' data="1" id="headName" name='headName' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' data="1" id="bussinessScope" name='bussinessScope' class='easyui-textbox' style="height: 150px;" size='100' data-options='required:true,multiline:true'/></td>
        </tr>
        <tr>
            <th>库房地址</th>
            <td><input type='text' data="1" id="warehouseAddress" name='warehouseAddress' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证有效期</th>
            <td><input type='text' data="1" id="licenseExpiryDate" name='licenseExpiryDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证批准日期</th>
            <td><input type='text' data="1" id="approveDate" name='approveDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证发证机关</th>
            <td><input type='text' data="1" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>类型(经营或生产)</th>
            <td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证照片</th>
            <td>
                <input id="licenseUrlFile" name='licenseUrlFile'>
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">查看</a>
                <input type="hidden" class="textbox-value" name="licenseUrl" id="licenseUrl"/>
            </td>
        </tr>
    </table>
</form>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $(function () {
        $('#licenseUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });

        var row = ezuiDatagrid.datagrid('getSelected');
        if(row){
            $.ajax({
                url : 'gspEnterpriseInfoController.do?getOperate',
                data : {"enterpriseId" : row.enterpriseId},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $("#ezuiFormOperate input[id!=''][data='1']").each(function (index) {
                            $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                        })
                        $("#attachmentUrl").val(result.obj.attachmentUrl);
                        $("#licenseUrlFile").val(result.obj.attachmentUrl);
                    }
                }
            });
        }

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
                console.log(data)
                $("#licenseUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }
</script>