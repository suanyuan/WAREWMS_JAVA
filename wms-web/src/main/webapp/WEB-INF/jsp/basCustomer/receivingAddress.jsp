<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<form id='ezuiFormAddress' method='post'>
    <input type='hidden' id='receivingAddressId' name='receivingAddressId'/>
    <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId'/>
    <table>
        <tr>
            <th>收货单位</th>
            <td><input type='text' data="1" id="receivingId" name='receivingId' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>销售人</th>
            <td><input type='text' data="1" id="sellerName" name='sellerName' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>国家</th>
            <td><input type='text' data="1" id="country" name='country' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>省</th>
            <td><input type='text' data="1" id="province" name='province' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>市</th>
            <td><input type='text' data="1" id="city" name='city' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>区</th>
            <td><input type='text' data="1" id="district" name='district' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>地址</th>
            <td><input type='text' data="1" id="deliveryAddress" name='deliveryAddress' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>邮编</th>
            <td><input type='text' data="1" id="zipcode" name='zipcode' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系人</th>
            <td><input type='text' data="1" id="contacts" name='contacts' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系人电话</th>
            <td><input type='text' data="1" id="phone" name='phone' class='easyui-textbox' size='100' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否默认</th>
            <td><input type='text' data="1" id="isDefault" name='isDefault' class='easyui-numberbox' size='100' data-options='required:true,min:0,max:100'/></td>
        </tr>
    </table>
</form>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $(function () {
        var row = ezuiDatagrid.datagrid('getSelected');
        if (row){
       $.ajax({
           url: "/basCustomerController.do?getReceivingAddress",
           data:{enterpriseId : row.enterpriseId, receivingAddressId : row.receivingAddressId},
           type:'post',
           dataType: "JSON",
           success:function (result) {
               if(result.success){
                   $("#ezuiFormAddress input[id!=''][data='1']").each(function (index) {
                       $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                   })
               }
           }
       })
        }
    });

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