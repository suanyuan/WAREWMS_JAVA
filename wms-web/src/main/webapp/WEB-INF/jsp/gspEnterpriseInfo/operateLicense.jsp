<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormOperate' method='post'>
    <input type='hidden' id='gspOperateLicenseId' name='gspOperateLicenseId'/>
    <table>
        <tr>
            <th>企业名称</th>
            <td><input type='text' name='enterpriseId' class='easyui-textbox' size='100' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证编号</th>
            <td><input type='text' name='licenseNo' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营方式</th>
            <td><input type='text' name='operateMode' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业负责人</th>
            <td><input type='text' name='headName' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' name='bussinessScope' class='easyui-textbox' style="height: 150px;" size='100' data-options='required:true,multiline:true'/></td>
        </tr>
        <tr>
            <th>库房地址</th>
            <td><input type='text' name='warehouseAddress' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证有效期</th>
            <td><input type='text' name='licenseExpiryDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证批准日期</th>
            <td><input type='text' name='approveDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证发证机关</th>
            <td><input type='text' name='registrationAuthority' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>类型(经营或生产)</th>
            <td><input type='text' name='operateType' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证照片</th>
            <td>
                <input id="licenseUrl" name='licenseUrl'>
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
            </td>
        </tr>
    </table>
</form>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $(function () {
        $('#licenseUrl').filebox({
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
        /*console.log(data);
        $.ajaxFileUpload({
            url: sy.bp()+"/commonController.do?uploadFileLocal", //执行上传处理的文件地址
            secureuri: false, //是否加密，一般是false，默认值为false
            fileElementId: 'filebox_file_id_1', //这里 的filebox的id为 filebox_file_id_1而不是fileName
            type: 'json', //请求方式，如果传递额外数据，必须是post
            success: function (data) {//成功的回调函数，内部处理会加上html标签
                console.log(data);
            }
        });*/
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