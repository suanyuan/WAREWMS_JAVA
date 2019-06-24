<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiForm' method='post'>
    <input type='hidden' id='gspOperateLicenseId' name='gspOperateLicenseId'/>
    <table>
        <tr>
            <th>企业名称</th>
            <td><input type='text' name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true,min:0,max:100'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证编号</th>
            <td><input type='text' name='licenseNo' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营方式</th>
            <td><input type='text' name='operateMode' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业负责人</th>
            <td><input type='text' name='headName' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' name='bussinessScope' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>库房地址</th>
            <td><input type='text' name='warehouseAddress' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证有效期</th>
            <td><input type='text' name='licenseExpiryDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证照片</th>
            <td><input type='text' name='licenseUrl' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证批准日期</th>
            <td><input type='text' name='approveDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>经营/生成许可证发证机关</th>
            <td><input type='text' name='registrationAuthority' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>类型(经营或生产)</th>
            <td><input type='text' name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
    </table>
</form>