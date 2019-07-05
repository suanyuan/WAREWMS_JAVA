<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspEnterpriseInfoId' name='gspEnterpriseInfoId'/>
    <table>
        <tr style="display: none">
            <td><input type="hidden" id="enterpriseId" value="${enterpriseId}"/></td>
        </tr>
        <tr>
            <th>企业信息代码</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.enterpriseNo}" id="enterpriseNo" name='enterpriseNo' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>简称</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.shorthandName}" id="shorthandName" name='shorthandName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.enterpriseName}" id="enterpriseName" name='enterpriseName' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>企业类型</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.enterpriseType}" id="enterpriseType" name='enterpriseType' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系人</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.contacts}" id="contacts" name='contacts' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>联系电话</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.contactsPhone}" id="contactsPhone" name='contactsPhone' class="easyui-numberbox" size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>备注</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.remark}" id="remark" name='remark' class='easyui-textbox' size='50' style="height: 100px;" data-options='multiline:true'/></td>
        </tr>
    </table>
</form>
<div id='catalogToolbar' class='datagrid-toolbar' style=''>
    <fieldset>
        <legend>器械目录分类</legend>
        <table>
            <tr>
                <th>版本</th>
                <td><input type='text' id='version' class='easyui-textbox' size='16' data-options=''/></td>
                <th>编号</th>
                <td><input type='text' id='productCode' class='easyui-textbox' size='16' data-options=''/></td>
                <th>名称</th>
                <td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
                <td>
                    <a onclick='getBy();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                    <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                </td>
            </tr>
        </table>
    </fieldset>
</div>
<table id="catalogDatagrid">

</table>
<script>
    var catalogDatagrid;
    $(function () {
        catalogDatagrid = $("#catalogDatagrid").datagrid({
            url : sy.bp()+'/gspInstrumentCatalogController.do?showCatalogEnterpriseDatagrid',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            queryParams:{'enterpriseId':'${enterpriseId}'},
            pagination:true,
            rownumbers:true,
            idField : 'instrumentCatalogId',
            columns : [[
                {field: 'instrumentCatalogId',title:'主键',hidden:true},
                {field: 'instrumentCatalogNo',title: '编号'},
                {field: 'instrumentCatalogName',title: '名称'},
                {field: 'classifyId',title: '分类'},
                {field: 'version',title: '版本'},
                {field: 'createDate',title: '创建时间'}
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:520});
            }
        })
    })
</script>
