<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
    <div id='enterpriseSearchGridToolbar_${target}' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>企业信息</legend>
            <table>
                <tr>
                    <th>代码</th>
                    <td><input type='text' id='enterpriseNo' class='easyui-textbox' data-options='width:200'/></td>
                    <th>简称</th>
                    <td><input type='text' id='shorthandName' class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchEnterprise();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSelect_${target}()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="enterpriseSearchGrid_${target}">

    </table>

    <div id='enterpriseDialog_${target}' style='padding: 10px;'></div>
<script>
    var enterpriseSearchGrid_${target};
    $(function () {
        enterpriseSearchGrid_${target} = $("#enterpriseSearchGrid_${target}").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagrid',
            method:'POST',
            toolbar : '#enterpriseSearchGridToolbar_${target}',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                type:'${type}'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: '20%' },
                {field: 'shorthandName',		title: '简称',	width: '20%' },
                {field: 'enterpriseName',		title: '企业名称',	width: '20%' },
                {field: 'enterpriseType',		title: '企业类型',	width: '20%' },
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOper_${target}
                }
            ]],
            onDblClickRow: function(index,row){
                choseSelect_${target}(row.enterpriseId,row.enterpriseName);
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

    function operateGrid_${target}(id) {
        $('#enterpriseDialog_${target}').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail&id="+id,
            width:850,
            height:500,
            cache:false,
            onClose : function() {

            }
        })
        //ezuiDialog_${target}
        //enterpriseSearchDetal_${target}.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    }

    function formatOper_${target}(value,row,index){
        return "<a onclick=\"operateGrid_${target}('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }
</script>
</body>
</html>
