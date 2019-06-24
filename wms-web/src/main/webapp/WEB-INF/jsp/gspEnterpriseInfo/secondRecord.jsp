<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiForm' method='post'>
    <input type='hidden' id='gspSecondRecordId' name='gspSecondRecordId'/>
    <table>
        <tr>
            <th>备案编号</th>
            <td><input type='text' name='recordNo' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' name='enterpriseId' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>企业负责人</th>
            <td><input type='text' name='headName' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营方式</th>
            <td><input type='text' name='operateMode' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营场所</th>
            <td><input type='text' name='operatePlace' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' name='bussinessScope' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>住所</th>
            <td><input type='text' name='residence' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案照片</th>
            <td><input type='text' name='recordUrl' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案批准日期</th>
            <td><input type='text' name='approveDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备案发证机关</th>
            <td><input type='text' name='registrationAuthority' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>待输入11</th>
            <td><input type='text' name='createId' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
        </tr>
    </table>
</form>