<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormBusiness' method='post'>
    <input type='hidden' id='businessId' name='businessId' value="${gspBusinessLicense.businessId}"/>
    <input type='hidden' id='enterpriseId' name='enterpriseId' value="${gspBusinessLicense.enterpriseId}"/>
    <table>
        <tr>
            <th>证照编号</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.licenseNumber}" id="licenseNumber" name='licenseNumber' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>统一社会信用代码</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.socialCreditCode}" id="socialCreditCode" name='socialCreditCode' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>名称</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.licenseName}" id="licenseName" name='licenseName' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>类型</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.licenseType}" id="licenseType" name='licenseType' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>住所</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.residence}" id="residence" name='residence' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>法定代表人</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.juridicalPerson}" id="juridicalPerson" name='juridicalPerson' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>注册资本</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.registeredCapital}" id="registeredCapital" name='registeredCapital' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>成立日期</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.establishmentDate}" id="establishmentDate" name='establishmentDate' class='easyui-datebox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业期限起始时间</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.businessStartDate}" id="businessStartDate" name='businessStartDate' class='easyui-datebox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业期限结束时间</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.businessEndDate}" id="businessEndDate" name='businessEndDate' class='easyui-datebox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否长期</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.isLong}" id="isLong" name='isLong' class='easyui-numberbox' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.businessScope}" id="businessScope" name='businessScope' style="height: 150px;" class='easyui-textbox' data-options='required:true,multiline:true'/></td>
        </tr>
        <tr>
            <th>发证日期</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.issueDate}" id="issueDate" name='issueDate' class='easyui-datebox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>登记机关</th>
            <td><input type='text' data="1" value="${gspBusinessLicense.registrationAuthority}" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>营业执照照片</th>
            <td>
                <input id="file" name='file' value="${gspBusinessLicense.attachmentUrl}">
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">查看</a>
                <input type="hidden" class="textbox-value" name="attachmentUrl" id="attachmentUrl" value="${gspBusinessLicense.attachmentUrl}"/>
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
            buttonText: '上传',  //按钮说明文字
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
                url : 'gspEnterpriseInfoController.do?getBusness',
                data : {"enterpriseId" : row.enterpriseId},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $("#ezuiFormBusiness input[id!=''][data='1']").each(function (index) {
                            $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                        })
                        $("#attachmentUrl").val(result.obj.attachmentUrl);
                        $("#file").val(result.obj.attachmentUrl);
                    }
                }
            });
        }*/
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