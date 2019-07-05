<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId" value="${supplierId}"/></td>
        </tr>

        <tr>
            <th>企业流水号</th>
            <td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否审查</th>
            <td><input type='text' data="1" id="isCheck" name='isCheck' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>类型（经营/生产）</th>
            <td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="createId" name='createId' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="createDate" name='createDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editId" name='editId' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="editDate" name='editDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否有效</th>
            <td><input type='text' data="1" id="isUse" name='isUse' class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
    </table>
</form>

<script>
    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        //alert(row.supplierId);
        if(row){
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

        //
        // $('#packingUnit').combobox({
        //     url:sy.bp()+'/basPackageController.do?getCombobox',
        //     valueField:'value',
        //     textField:'value'
        // });

    })



</script>
