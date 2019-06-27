<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspEnterpriseInfoId' name='gspEnterpriseInfoId'/>
    <table>
        <tr style="display: none">
            <td><input type="hidden" id="enterpriseId" value="${enterpriseId}"/></td>
        </tr>
        <tr>
            <th>企业信息代码</th>
            <td><input type='text' data="1" id="enterpriseNo" name='enterpriseNo' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>简称</th>
            <td><input type='text' data="1" id="shorthandName" name='shorthandName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' data="1" id="enterpriseName" name='enterpriseName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业类型</th>
            <td><input type='text' data="1" id="enterpriseType" name='enterpriseType' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系人</th>
            <td><input type='text' data="1" id="contacts" name='contacts' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系电话</th>
            <td><input type='text' data="1" id="contactsPhone" name='contactsPhone' class="easyui-numberbox" size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备注</th>
            <td><input type='text' data="1" id="remark" name='remark' class='easyui-textbox' size='50' style="height: 150px;" data-options='multiline:true'/></td>
        </tr>
    </table>
</form>

<script>
    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        if(row){
            $.ajax({
                url : 'gspEnterpriseInfoController.do?getInfo',
                data : {"enterpriseId" : row.enterpriseId},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $("#ezuiFormInfo input[id!=''][data='1']").each(function (index) {
                            $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                        })
                    }
                }
            });
        }

    })
</script>
