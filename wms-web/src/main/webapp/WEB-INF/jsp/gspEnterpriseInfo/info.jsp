<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspEnterpriseInfoId' name='gspEnterpriseInfoId'/>
    <table>
        <tr style="display: none">
            <td><input data="1" type="hidden" id="enterpriseId" value="${enterpriseId}"/></td>
        </tr>
        <tr>
            <th>企业代码</th>
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
            <td>
                <input type='text' data="1" value="" id="enterpriseType" name='enterpriseType' class='easyui-combobox' size='50' />
            </td>
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
<!--<table id="catalogDatagrid">

</table>-->
<script>
    /*var catalogDatagrid;
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
            width:400,
            idField : 'instrumentCatalogId',
            columns : [[
                {field: 'instrumentCatalogId',title:'主键',hidden:true},
                {field: 'instrumentCatalogNo',title: '编号',width: '20%'},
                {field: 'instrumentCatalogName',title: '名称',width: '20%'},
                {field: 'classifyId',title: '分类',width: '20%'},
                {field: 'version',title: '版本',width: '20%'},
                {field: 'createDate',title: '创建时间',width: '20%'}
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
            }
        })
    })*/

    //控件初始化data-options="required:true,valueField:'id',textField:'value',url:'commonController.do?getEntType'"
    /**/

    $(function () {
        var enterpriseType = '${gspEnterpriseInfo.enterpriseType}';
        $('#enterpriseType').combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getEntType',
            valueField:'id',
            textField:'value',
            required:true,
            onLoadSuccess:function(){
                $("#enterpriseType").combobox("select",enterpriseType);
            },onChange:function (newValue,oldValue) {
                if(newValue == CODE_ENT_TYP.CODE_ENT_TYP_GNSC || newValue == CODE_ENT_TYP.CODE_ENT_TYP_JY || newValue == CODE_ENT_TYP.CODE_ENT_TYP_SCJY || newValue == CODE_ENT_TYP.CODE_ENT_TYP_GW){
                    $("#ezuiFormInfo input[id='contacts']").textbox({"required":false});
                    $("#ezuiFormInfo input[id='contactsPhone']").textbox({"required":false})
                }else{
                    $("#ezuiFormInfo input[id='contacts']").textbox({"required":true});
                    $("#ezuiFormInfo input[id='contactsPhone']").textbox({"required":true})
                }
            }
        });

    })
</script>
