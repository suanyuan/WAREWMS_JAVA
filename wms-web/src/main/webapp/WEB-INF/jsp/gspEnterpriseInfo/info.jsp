<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspEnterpriseInfoId' name='gspEnterpriseInfoId'/>
    <table>
        <tr style="display: none">
            <td><input data="1" type="hidden" id="enterpriseId" value="${enterpriseId}"/></td>
        </tr>
        <tr>
            <th>企业代码</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.enterpriseNo}" id="enterpriseNo" name='enterpriseNo' class='easyui-textbox'  data-options='required:true,width:200'/></td>
            <%--<td><input onkeyup="value=value.replace(/[\d]/g,'') " onbeforepaste="clipboardData.setData('text',clipboardData.getData('text').replace(/[\d]/g,''))" type='text' data="1" value="${gspEnterpriseInfo.enterpriseNo}" id="enterpriseNo" name='enterpriseNo' class='easyui-textbox' size='50' data-options='required:true'><td>--%>
            <%--<td><input id="enterpriseNo" class="easyui-validatebox" data-options="required:true,validType:'account[5,20]'" /></td>--%>
        </tr>
        <tr>
            <th>简称</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.shorthandName}" id="shorthandName" name='shorthandName' class='easyui-textbox'  data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>企业名称</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.enterpriseName}" id="enterpriseName" name='enterpriseName' class='easyui-textbox'  data-options='required:true,width:200<c:if test="${not empty enterpriseId}">,editable:false</c:if>'/></td>
        </tr>
        <tr>
            <th>企业类型</th>
            <td>
                <input type='text' data="1" value="" id="enterpriseType" name='enterpriseType' class='easyui-combobox'    data-options='required:true,editable:false,width:200'  />
            </td>
        </tr>
        <tr>
            <th>联系人</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.contacts}" id="contacts" name='contacts' class='easyui-textbox'  data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>联系电话</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.contactsPhone}" id="contactsPhone" name='contactsPhone' class="easyui-numberbox"   data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>备注</th>
            <td><input type='text' data="1" value="${gspEnterpriseInfo.remark}" id="remark" name='remark' class='easyui-textbox'   style="height: 100px;" data-options='multiline:true,width:200'/></td>
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
                $("#ezuiFormInfo #enterpriseType").combobox("select",enterpriseType);
            },onChange:function (newValue,oldValue) {
                if(newValue == CODE_ENT_TYP.CODE_ENT_TYP_GNSC || newValue == CODE_ENT_TYP.CODE_ENT_TYP_JY || newValue == CODE_ENT_TYP.CODE_ENT_TYP_SCJY || newValue == CODE_ENT_TYP.CODE_ENT_TYP_GW ||newValue == CODE_ENT_TYP.CODE_ENT_TYP_YL){
                    $("#ezuiFormInfo input[id='contacts']").textbox({"required":false});
                    $("#ezuiFormInfo input[id='contactsPhone']").textbox({"required":false})
                }else{
                    $("#ezuiFormInfo input[id='contacts']").textbox({"required":true});
                    $("#ezuiFormInfo input[id='contactsPhone']").textbox({"required":true})
                }
            }
        });



    })

    $(function () {
        //设置text需要验证
        $('#enterpriseNo').validatebox();
    })
    var fg=true;
    $.extend($.fn.validatebox.defaults.rules, {
        //验证(只能包括 _ 数字 字母)
        account: {//param的值为[]中值
            validator: function (value, param) {
                if (!/^[\w]+$/.test(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                    //$('#enterpriseNo').val('111111');
                    fg=false;
                    return false;
                } else {
                    if (value.length < param[0] || value.length > param[1]) {
                        $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在' + param[0] + '至' + param[1] + '范围';
                        fg=false;
                        return false;
                    } else {
                        fg=true;
                        return true;
                    }
                }
            }, message: ''
        }
    })
</script>
