<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormBusiness' method='post'>
    <input type='hidden' id='gspBusinessLicenseId' name='gspBusinessLicenseId'/>
    <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId'/>
    <table>
        <tr>
            <th>证照编号</th>
            <td><input type='text' name='licenseNumber' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>统一社会信用代码</th>
            <td><input type='text' name='socialCreditCode' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>名称</th>
            <td><input type='text' name='licenseName' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>类型</th>
            <td><input type='text' name='licenseType' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>住所</th>
            <td><input type='text' name='residence' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>法定代表人</th>
            <td><input type='text' name='juridicalPerson' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>注册资本</th>
            <td><input type='text' name='registeredCapital' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>成立日期</th>
            <td><input type='text' name='establishmentDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业期限起始时间</th>
            <td><input type='text' name='businessStartDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业期限结束时间</th>
            <td><input type='text' name='businessEndDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否长期</th>
            <td><input type='text' name='isLong' class='easyui-numberbox' size='100' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' name='businessScope' style="height: 150px;" class='easyui-textbox' size='100' data-options='required:true,multiline:true'/></td>
        </tr>
        <tr>
            <th>发证日期</th>
            <td><input type='text' name='issueDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>登记机关</th>
            <td><input type='text' name='registrationAuthority' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业执照照片</th>
            <td>
                <input id="file" name='file'>
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
                <input type="hidden" class="textbox-value" name="attachmentUrl" id="attachmentUrl"/>
            </td>
        </tr>
    </table>
</form>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $(function () {
        $('#file').filebox({
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
                $("#attachmentUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }
</script>