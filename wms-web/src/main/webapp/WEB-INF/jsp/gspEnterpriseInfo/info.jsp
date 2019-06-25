<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspEnterpriseInfoId' name='gspEnterpriseInfoId'/>
    <table>
        <tr style="display: none">
            <td><input type="hidden" id="enterpriseId" value="${enterpriseId}"/></td>
        </tr>
        <tr>
            <th>企业信息代码</th>
            <td><input type='text' name='enterpriseNo' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>简称</th>
            <td><input type='text' name='shorthandName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' name='enterpriseName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业类型</th>
            <td><input type='text' name='enterpriseType' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系人</th>
            <td><input type='text' name='contacts' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系电话</th>
            <td><input type='text' name='contactsPhone' class="easyui-numberbox" size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备注</th>
            <td><input type='text' name='remark' class='easyui-textbox' size='50' style="height: 150px;" data-options='multiline:true'/></td>
        </tr>
    </table>
</form>
