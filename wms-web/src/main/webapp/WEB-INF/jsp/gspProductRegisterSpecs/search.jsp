<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
    <div id='enterpriseSearchGridToolbar' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>产品注册证</legend>
            <table>
                <tr>
                    <th>注册证编号</th>
                    <td><input type='text' id='productRegisterNo1' class='easyui-textbox' data-options='width:200'/></td>
                    <th>产品名称</th>
                    <td><input type='text' id='productNameMain1' class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchEnterprise();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='ezuiToolbarClear("#enterpriseSearchGridToolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>清空</a>
                        <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="enterpriseSearchGrid">

    </table>

    <%--<div id='enterpriseDialog' style='padding: 10px;'></div>--%>
<script>
    var enterpriseSearchGrid;
    $(function () {
        enterpriseSearchGrid = $("#enterpriseSearchGrid").datagrid({
            url : '/gspProductRegisterController.do?showDatagridSearch',
            method:'POST',
            toolbar : '#enterpriseSearchGridToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                // isUse : '1',
                type:'${type}',
                enterpriseType:'${enterpriseType}'
            },
            rowStyler:function(index,row){
                if(row.isUse == "0" ){
                    return 'color:red;';
                }
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'productRegisterId',
            columns : [[
                {field: 'productRegisterId',		title: '主键',	width: '20%' ,hidden:true},
                {field: 'productRegisterNo',		title: '注册证编号',	width: '33%' },
                {field: 'productNameMain',		title: '产品名称',	width: '33%' },

                {field: 'classifyId',	    	title: '管理分类',	width: '33%' },
                // {field: 'classifyCatalog',		title: '分类目录',	width: '20%' },
                // {field: 'productionAddress',		title: '产地',	width: '25%' }
                // {field: '_operate',		title: '操作',	width: '20%',
                //     formatter: formatOper
                // }
            ]],
            onDblClickRow: function(index,row){
                choseSelect(row);
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        })
    })

    <%--function operateGrid(id) {--%>
        <%--$('#enterpriseDialog').dialog({--%>
            <%--modal : true,--%>
            <%--title : '<spring:message code="common.dialog.title"/>',--%>
            <%--href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail&id="+id,--%>
            <%--width:850,--%>
            <%--height:500,--%>
            <%--cache:false,--%>
            <%--onClose : function() {--%>

            <%--}--%>
        <%--})--%>
    <%--}--%>
    var doSearchEnterprise = function(){
        console.log($('#productRegisterNo1').textbox("getValue"));
        console.log($('#productRegisterNo1').val());
        console.log($('#productRegisterNo1').textbox('getValue'));
        enterpriseSearchGrid.datagrid('load', {
            productRegisterNo : $('#productRegisterNo1').textbox('getValue'),
            productNameMain :$('#productNameMain1').textbox('getValue'),
            // isUse:'1'
        });
    };

    function formatOper(value,row,index){
        return "<a onclick=\"operateGrid('"+row.productRegisterId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }
</script>
</body>
</html>
