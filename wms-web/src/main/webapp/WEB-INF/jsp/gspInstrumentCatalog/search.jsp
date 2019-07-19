
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div id='catalogToolbar_${target}' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>器械目录分类</legend>
            <table>
                <tr>
                    <th>编号</th>
                    <td><input type='text' id='productCode_${target}' class='easyui-textbox'  data-options='width:132'/></td>
                    <th>名称</th>
                    <td><input type='text' id='productName_${target}' class='easyui-textbox'  data-options='width:132'/></td>
                </tr>
                <tr>
                    <th>版本</th>
                    <td><input type='text' id='version_${target}' data-options=''/></td>
                    <th>分类</th>
                    <td><input type='text' id='classify_${target}'  data-options=''/></td>
                    <td>
                        <a onclick='searchCataLog_${target}()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查询</a>
                        <a onclick='removeCataLog_${target}()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>清除</a>
                        <a onclick='choseSelect_Catalog_${target}(ezuiCatalogDatagridDetail_${target}.datagrid("getSelections"))' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="catalogDatagrid_${target}">

    </table>
<script>
    var ezuiCatalogDatagridDetail_${target};
    $(function () {
        ezuiCatalogDatagridDetail_${target} = $("#catalogDatagrid_${target}").datagrid({
            url : sy.bp()+'/gspInstrumentCatalogController.do?showCatalogEnterpriseDatagrid',
            method:'POST',
            toolbar : '',
            title: '',
            pageSize : 1000,
            pageList : [1000, 2000, 3000],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            queryParams:{'enterpriseId':'${id}'},
            pagination:true,
            rownumbers:true,
            idField : 'instrumentCatalogId',
            columns : [[
                {field: 'ck',checkbox:true },
                {field: 'instrumentCatalogId',		title: '主键',hidden:true },
                {field: 'instrumentCatalogName',		title: '名称',width:'25%'},
                {field: 'instrumentCatalogRemark',		title: '描述',width:'25%'	 },
                {field: 'classifyId',		title: '分类',width:'25%'},
                {field: 'version',		title: '版本',width:'25%'},
            ]],
            onDblClickRow: function(index,row){
                choseSelect_Catalog_${target}(row);
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:400});
                //TODO 选中已选择行
                initChecked($(this));
            }
        })

        $("#version_${target}").combobox({
            url:sy.bp()+'/commonController.do?getCatalogVersion',
            valueField:'id',
            textField:'value'
        })

        $("#classify_${target}").combobox({
            url:sy.bp()+'/commonController.do?getCatalogClassify',
            valueField:'id',
            textField:'value'
        })

    })
    
    function searchCataLog_${target}() {
        ezuiCatalogDatagridDetail_${target}.datagrid("reload",
            {"version":$("#version_${target}").combobox("getValue"),
             "instrumentCatalogNo":$("#productCode_${target}").textbox("getValue"),
             "instrumentCatalogName":$("#productName_${target}").textbox("getValue"),
             "enterpriseId":"${id}",
             "classifyId":$("#classify_${target}").combobox("getValue")
            }
            )
    }
    
    function removeCataLog_${target}() {
        $("#version_${target}").combobox("clear")
        $("#classify_${target}").combobox("clear")
        $("#productCode_${target}").textbox("clear")
        $("#productName_${target}").textbox("clear")
    }
    
    function initChecked(data) {
        $.ajax({
            url : sy.bp()+'/gspInstrumentCatalogController.do?searchCheckByLicenseId',
            data : {
                "licenseId":'${id}'
            }
            ,type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                if(result.length>0){
                    for(var i=0;i<result.length;i++){
                        data.datagrid("selectRecord",result[i].operateId);
                    }
                }
            }
        });
    }

</script>
</body>
</html>
