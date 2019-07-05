<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
<div id='productSearchGridToolbar_${target}' class='datagrid-toolbar' style=''>
    <fieldset>
        <legend>产品信息</legend>
        <table>
            <tr>
                <th>代码</th>
                <td><input type='text' id='product_code_${target}' class='easyui-textbox' data-options='width:200'/></td>
                <th>名称</th>
                <td><input type='text' id='product_name_${target}' class='easyui-textbox' data-options='width:200'/></td>
                <td>
                    <a onclick='doSearch_product_${target}();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                    <a onclick='choseSelect_product_${target}()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                </td>
            </tr>
        </table>
    </fieldset>
</div>
<table id="productSearchGrid_${target}">

</table>

<div id='productSearchGrid_${target}' style='padding: 10px;'></div>
<script>
    var productSearchGrid_${target};
    $(function () {
        productSearchGrid_${target} = $("#productSearchGrid_${target}").datagrid({
            url : sy.bp()+'/basSkuController.do?showSearchDatagrid',
            method:'POST',
            toolbar : '#productSearchGridToolbar_${target}',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'sku',
            columns : [[
                {field: 'sku',		title: '代码',	width: '20%' },
                {field: 'descrC',		title: '名称',	width: '20%' },
                {field: 'descrE',		title: '型号规格',	width: '20%' },
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOperProductSearch_${target}
                }
            ]],
            onDblClickCell:function () {

            },
            onDblClickRow: function(index,row){
                choseSelect_product_${target}(row);
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

    function operateGrid_product_${target}(id) {
        /*$('#productSearchGrid_${target}').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail&id="+id,
            width:850,
            height:500,
            cache:false,
            onClose : function() {

            }
        })
       earchDetal_${target}.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');*/
    }

    function formatOperProductSearch_${target}(value,row,index){
        return "<a onclick=\"operateGrid_product_${target}('"+row.sku+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }
</script>
</body>
</html>
