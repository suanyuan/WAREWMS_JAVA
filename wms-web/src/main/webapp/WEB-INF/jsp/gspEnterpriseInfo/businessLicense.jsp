<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiForm' method='post'>
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
            <td><input type='text' name='establishmentDate' class='easyui-date' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业期限起始时间</th>
            <td><input type='text' name='businessStartDate' class='easyui-date' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业期限结束时间</th>
            <td><input type='text' name='businessEndDate' class='easyui-date' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否长期</th>
            <td><input type='text' name='isLong' class='easyui-numberbox' size='100' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' name='businessScope' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>发证日期</th>
            <td><input type='text' name='issueDate' class='easyui-date' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>登记机关</th>
            <td><input type='text' name='registrationAuthority' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业执照照片</th>
            <td>
                <input id="attachmentUrl" name='attachmentUrl'>
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
            </td>
        </tr>
    </table>
</form>
<script>
    $(function () {
        $('#attachmentUrl').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: true,
            onChange:function(data){
                doUpload(data);
            }
        });

    })

    function doUpload(data) {
        console.log(data);
        $.ajaxFileUpload({
            url: '<c:url value="/common/upload_fileLocal"/>', //执行上传处理的文件地址
            secureuri: false, //是否加密，一般是false，默认值为false
            fileElementId: 'filebox_file_id_1', //这里 的filebox的id为 filebox_file_id_1而不是fileName
            type: 'post', //请求方式，如果传递额外数据，必须是post
            success: function (data) {//成功的回调函数，内部处理会加上html标签
                console.log(data);
            }
        });
        $('#attachmentUrl').filebox('clear');//上传成功后清空里面的值
    }
</script>