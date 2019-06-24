<%@ page language='java' pageEncoding='UTF-8'%>
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
                <input name='attachmentUrl' class="easyui-filebox" size="80" data-options="required:true,buttonText:'选择'">
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
            </td>
        </tr>
    </table>
</form>