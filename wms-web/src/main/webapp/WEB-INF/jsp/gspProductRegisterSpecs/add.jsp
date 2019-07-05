<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspProductRegisterSpecsId' name='gspProductRegisterSpecsId'/>
    <table>
        <tr style="display: none">
            <td><input type="hidden" id="specsId" value="${specsId}"/></td>
        </tr>
        <tr>
            <th>产品注册证表主键</th>
            <td><input type='text' data="1" id="productRegisterId" name='productRegisterId' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>规格名称</th>
            <td><input type='text' data="1" id="specsName" name='specsName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>代码</th>
            <td><input type='text' data="1" id="productCode" name='productCode' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>商品名称</th>
            <td><input type='text' data="1" id="productName" name='productName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>商品描述</th>
            <td><input type='text' data="1" id="productRemark" name='productRemark' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>型号</th>
            <td><input type='text' data="1" id="productModel" name='productModel' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>产地</th>
            <td><input type='text' data="1" id="productionAddress" name='productionAddress' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>商品条码</th>
            <td><input type='text' data="1" id="barCode" name='barCode' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>单位</th>
            <td><input type='text' data="1" id="unit" name='unit' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>包装单位</th>
            <td><input type='text' data="1" id="packingUnit" name='packingUnit' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>分类目录</th>
            <td><input type='text' data="1" id="categories" name='categories' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <%--<tr>--%>
        <%--<th>待输入12</th>--%>
        <%--<td><input type='text' name='conversionRate' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>
        <tr>
            <th>长</th>
            <td><input type='text' data="1" id="llong" name='llong' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>宽</th>
            <td><input type='text' data="1" id="wide" name='wide' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>高</th>
            <td><input type='text' data="1" id="hight"  name='hight' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <%--<tr>--%>
        <%--<th>待输入16</th>--%>
        <%--<td><input type='text' name='productLine' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>
        <tr>
            <th>管理分类</th>
            <td><input type='text' data="1" id="manageCategories" name='manageCategories' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>包装要求</th>
            <td><input type='text' data="1" id="packingRequire" name='packingRequire' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>存储条件</th>
            <td><input type='text' data="1" id="storageCondition" name='storageCondition' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>运输条件</th>
            <td><input type='text' data="1" id="transportCondition" name='transportCondition' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="createId" name='createId' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="createDate" name='createDate' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editId" name='editId' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="editDate" name='editDate' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否有效</th>
            <td><input type='text' data="1" id="isUse" name='isUse' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>自赋码1</th>
            <td><input type='text' data="1" id="alternatName1" name='alternatName1' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>自赋码2</th>
            <td><input type='text' data="1" id="alternatName2" name='alternatName2' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>自赋码3</th>
            <td><input type='text' data="1" id="alternatName3" name='alternatName3' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>自赋码4</th>
            <td><input type='text' data="1" id="alternatName4" name='alternatName4' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>自赋码5</th>
            <td><input type='text' data="1" id="alternatName5" name='alternatName5' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
    </table>
</form>

<script>
    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        if(row){
            $.ajax({
                url : 'gspProductRegisterSpecsController.do?getInfo',
                data : {"specsId" : row.specsId},
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


        $('#packingUnit').combobox({
            url:sy.bp()+'/basPackageController.do?getCombobox',
            valueField:'value',
            textField:'value'
        });

    })



</script>
