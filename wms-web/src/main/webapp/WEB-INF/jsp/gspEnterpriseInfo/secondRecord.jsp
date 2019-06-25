<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormRecord' method='post'>
    <input type='hidden' id='gspSecondRecordId' name='gspSecondRecordId'/>
    <table>
        <tr>
            <th>备案编号</th>
            <td><input type='text' name='recordNo' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' name='enterpriseId' class='easyui-numberbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业负责人</th>
            <td><input type='text' name='headName' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营方式</th>
            <td><input type='text' name='operateMode' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营场所</th>
            <td><input type='text' name='operatePlace' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>住所</th>
            <td><input type='text' name='residence' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案批准日期</th>
            <td><input type='text' name='approveDate' class='easyui-datebox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案发证机关</th>
            <td><input type='text' name='registrationAuthority' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' name='bussinessScope' class='easyui-textbox' size='100' style="height: 150px;" data-options='required:true,multiline:true'/></td>
        </tr>
        <tr>
            <th>备案照片</th>
            <td>
                <input type="hidden" class="textbox-value" name="recordUrl" id="recordUrl"/>
                <input id="recordFile" name='recordFile'>
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
                console.log(data);
                $("#recordUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }
</script>