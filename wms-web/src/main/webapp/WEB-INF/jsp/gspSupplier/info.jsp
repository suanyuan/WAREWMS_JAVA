<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId" value="${supplierId}"/></td>
        </tr>

        <tr><th>代码：</th><td><input type='text' data="1" id='clientNo' class='easyui-textbox' data-options=''/></td></tr>
        <tr><th>简称：</th><td><input type='text' data="1" id='clientName' class='easyui-textbox' data-options=''/></td></tr>
        <tr><th>企业：</th>
            <td>
                <input type='text' data="1" id='enterpriseIdQuery' class='easyui-textbox' data-options='' style="width: 100px;"/>
                <input type="hidden" class="easyui-textvalue" name="enterpriseId">
                <a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>
            </td>
        </tr>


        <%--<tr>--%>
            <%--<th>企业</th>--%>
            <%--<td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>






        <tr>
            <th>是否审查</th>
            <td><input type="text" data="1" id="isCheck"  name="isCheck"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
        </tr>

        <tr>

            <%--<th>企业类型</th>--%>
            <%--<td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
            <th>企业类型</th><td><input type="text" data="1" id="operateType"  name="operateType"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '经营', value: '经营'},
																																	{id: '生产', value: '生产'}
																																]"/></td>
        </tr>
        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="createId" name='createId' value="${createId}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="createDate" name='createDate' value="${createDate}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editId" name='editId' value="${createId}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="editDate" name='editDate' value="${createDate}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <%--<th>是否有效</th>--%>
            <th>是否有效</th> <td><input type="text" data="1" id="isUser"  name="isUser"  value="${isUser}" class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
        </tr>
    </table>
</form>

<script>
    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        //alert(row.supplierId);
        if(row){
            if(processType == 'edit'){
            $.ajax({
                url : 'gspSupplierController.do?getInfo',
                data : {"supplierId" : row.supplierId},
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
        }

        //
        // $('#packingUnit').combobox({
        //     url:sy.bp()+'/basPackageController.do?getCombobox',
        //     valueField:'value',
        //     textField:'value'
        // });

    })



</script>
