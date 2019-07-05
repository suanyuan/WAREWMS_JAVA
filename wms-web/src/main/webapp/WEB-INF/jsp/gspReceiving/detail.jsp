<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp' />
    <c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<body>
<form id='ezuiForm' method='post'>
    <input type='hidden' id='receivingId' name='receivingId' value="${receivingId}" class="textbox-value"/>
    <input type="hidden" id="enterpriseId" name='enterpriseId' value="${enterpriseId}" class="textbox-value"/>
    <table>

        <tr>
            <th>企业</th>
            <td>
                <input type='text' id="enterpriseName" name='enterpriseName' class='easyui-textbox' data-options='required:true,width:200'/>
                <a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>
            </td>
        </tr>
        <tr>
            <th>代码</th>
            <td><input type='text' id="clientNo" name='clientNo' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>简称</th>
            <td><input type='text' id='clientName' name='clientName' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>备注</th>
            <td><input type='text' name='remark' id="remark" class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>货主</th>
            <td><input type='text' name='clientId' id="text" class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>供应商</th>
            <td>
                <input type='text' name='supplierId' id="supplier" class='easyui-textbox' data-options='required:true,width:200'/>
                <a href="javascript:void(0)" onclick="searchSupplier()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>
            </td>
        </tr>
        <tr>
            <th>销售人</th>
            <td><input type='text' name='sellerName' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
       <%-- <tr>
            <th>首营状态</th>
            <td><input type='text' name='firstState' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>--%>
        <tr>
            <th>是否审核</th>
            <td><input type='text' name='isCheck' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>
        <tr>
            <th>是否医废</th>
            <td><input type='text' name='isReturn' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>
        <tr>
            <th>是否合作</th>
            <td><input type='text' name='isCooperation' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>
        <tr>
            <th>送货地址</th>
            <td><input type='text' name='deliveryAddress' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>联系人</th>
            <td><input type='text' name='contacts' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
       <%-- <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" name="contractUrl" id="contractUrl"/>
                <input id="contractUrlFile" name='file'>
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
            </td>
        </tr>--%>
        <tr>
            <th>联系人电话</th>
            <td><input type='text' name='phone' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>

        <%--<tr>
            <th>创建人</th>
            <td><input type='text' name='createId' value="${createId}" class='easyui-datebox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' name='createDate' value="${createDate}" class='easyui-datebox' data-options='required:true,width:200'/></td>
        </tr>--%>
    </table>
</form>
<div id='ezuiDialogDetail' style='padding: 10px;'>
    <div id='detailToolbar' class='datagrid-toolbar' style=''>
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
                        <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridDetail">

    </table>
</div>

<div id="dialogEnterprise">

</div>


<div id='ezuiDialogSupplierDetail' style='padding: 10px;'>
    <div id='TB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>供应商信息</legend>
            <table>
                <tr>
                    <th>供应商</th>
                    <td><input type='text' id='supplierId' class='easyui-textbox' data-options='width:200'/></td>
                    <th>类型</th>
                    <td><input type='text' id='operationType' class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchSupplier();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseSupplierSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridSupplierDetail">

    </table>
</div>

<div id="dialogSupplier">

</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var enterpriseDatagrid;
    var dataGridDetail;
    var supplierDatagrid;
    var ezuiDialogSupplierDetail;
    var dialogEnterprise;
    var dialogSupplier;
    $(function () {
        $('#contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });

        function doUpload(data) {
            var ajaxFile = new uploadFile({
                "url":sy.bp()+"/commonController.do?uploadFileLocal",
                "dataType":"json",
                "timeout":50000,
                "async":true,
                "data":{
                    //多文件
                    "file":{
                        //file为name字段 后台可以通过$_FILES["file"]获得
                        "file":document.getElementsByName("file")[0].files[0]//文件数组
                    }
                },
                onload:function(data){
                    $("#contractUrl").val(data.comment);
                },
                onerror:function(er){
                    console.log(er);
                }
            });
        }

        $('input[name="firstState"]').combobox({
            url:sy.bp()+'/commonController.do?getCatalogFirstState',
            valueField:'id',
            textField:'value'
        });

        $('input[name="isCheck"]').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });

        $('input[name="isCooperation"]').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });

        $('input[name="isReturn"]').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });

        enterpriseDatagrid = $("#dataGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagrid',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
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
                    formatter: formatOper
                }
            ]],
            onDblClickCell: function(index,field,value){
                choseSelect();
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

        dataGridDetail = $('#ezuiDialogDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogEnterprise = $('#dialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');



        supplierDatagrid = $("#dataGridSupplierDetail").datagrid({
            url : sy.bp()+'/gspSupplierController.do?showDatagrid',
            method:'POST',
            toolbar : '#TB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'supplierId',title: '主键',	width: 88 },
                {field: 'enterpriseId',		title: '企业流水号',	width: 88 },
                {field: 'isCheck',		title: '是否审查',	width: 88 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isCheck == '1' ? '是' : '否';
                    }},
                {field: 'operateType',		title: '类型（经营/生产）',	width: 88 },
                {field: 'createId',		title: '创建人',	width: 88 },
                {field: 'createDate',		title: '创建时间',	width: 88 },
                {field: 'editId',		title: '编辑人',	width: 88 },
                {field: 'editDate',		title: '编辑时间',	width: 88 },
                {field: 'isUse',		title: '是否有效',	width: 88 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isUse == '1' ? '是' : '否';
                    }}
            ]],
            onDblClickCell: function(index,field,value){
                choseSupplierSelect();
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

        ezuiDialogSupplierDetail = $('#ezuiDialogSupplierDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogSupplier = $('#dialogSupplier').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspSupplierController.do?toDetail",
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

    })

    function searchEnterprise() {
        if(dataGridDetail){
            dataGridDetail.dialog('open');
        }

    }


    function doSearchEnterprise() {
        enterpriseDatagrid.datagrid('load', {
            enterpriseNo : $('#enterpriseNo').val(),
            shorthandName : $('#shorthandName').val(),
            isUse : '1'
        });
    }
    
    function choseSelect() {
        var row = enterpriseDatagrid.datagrid("getSelected");
        if(row){
            $("#enterpriseId").val(row.enterpriseId);
            $("#enterpriseName").textbox("setValue",row.enterpriseName);
            $("#clientNo").textbox("setValue",row.enterpriseNo);
            $("#clientName").textbox("setValue",row.shorthandName);
            dataGridDetail.dialog('close');
        }
    }



    function operateGrid(id) {
        console.log(id);
        dialogEnterprise.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    }

    function formatOper(value,row,index){
        return "<a onclick=\"operateGrid('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }

    function doSubmit() {
        var url = '';
        if (processType == 'edit') {
            url = '<c:url value="/gspReceivingController.do?edit"/>';
        }else{
            url = '<c:url value="/gspReceivingController.do?add"/>';
        }
        $("#ezuiForm").form('submit', {
            url : url,
            onSubmit : function(){
                console.log("1");
                if(ezuiForm.form('validate')){
                    $.messager.progress({
                        text : '<spring:message code="common.message.data.processing"/>', interval : 100
                    });
                    return true;
                }else{
                    return false;
                }
            },
            success : function(data) {
                var msg='';
                try {
                    var result = $.parseJSON(data);
                    if(result.success){
                        msg = result.msg;
                        ezuiDatagrid.datagrid('reload');
                        ezuiDialog.dialog('close');
                    }else{
                        msg = '<font color="red">' + result.msg + '</font>';
                    }
                } catch (e) {
                    msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                    msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
                } finally {
                    $.messager.show({
                        msg : msg, title : '<spring:message code="common.message.prompt"/>'
                    });
                    $.messager.progress('close');
                }
            }
        });
    }


    function choseSupplierSelect() {
        var row = supplierDatagrid.datagrid("getSelected");
        if(row){
            $("#supplier").textbox("setValue",row.supplierId);
           /* $("#operateType").textbox("setValue",row.operateType);
            $("#isUse").textbox("setValue",row.isUse);
            $("#isCheck").textbox("setValue",row.isCheck);*/
            ezuiDialogSupplierDetail.dialog('close');
        }
    }
    function doSearchSupplier() {
        supplierDatagrid.datagrid('load', {
            supplierId : $('#supplierId').val(),
            operateType : $('#operateType').val(),
            isUse : '1'
        });
    }

    function searchSupplier() {
        if(ezuiDialogSupplierDetail){
            ezuiDialogSupplierDetail.dialog('open');
        }

    }
</script>
</body>
</html>
