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
    <input type='hidden' id='clientId' name='clientId' value="${clientId}" class="textbox-value"/>
    <input type="hidden" id="enterpriseId" name='enterpriseId' class="textbox-value"/>
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
            <td><input type='text' name='remark' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>首营状态</th>
            <td><input type='text' name='firstState' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>
        <tr>
            <th>是否审核</th>
            <td><input type='text' name='isCheck' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>
        <tr>
            <th>是否合作</th>
            <td><input type='text' name='isCooperation' class='easyui-textbox' data-options='required:true,width:200,editable:false'/></td>
        </tr>
        <tr>
            <th>类型</th>
            <td><input type='text' name='operateType' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>合同编号</th>
            <td><input type='text' name='contractNo' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" name="contractUrl" id="contractUrl"/>
                <input id="contractUrlFile" name='file'>
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">浏览</a>
            </td>
        </tr>
        <tr>
            <th>委托内容</th>
            <td><input type='text' name='clientContent' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>委托开始时间</th>
            <td><input type='text' name='clientStartDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>委托结束时间</th>
            <td><input type='text' name='clientEndDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>委托期限</th>
            <td><input type='text' name='clientTerm' class='easyui-numberbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>是否贴中文标签</th>
            <td><input type='text' name='isChineseLabel' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>创建人</th>
            <td><input type='text' name='createId' value="${createId}" class='easyui-textbox' data-options='editable:false,width:200'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' name='createDate' value="${createDate}" class='easyui-textbox' data-options='editable:false,width:200'/></td>
        </tr>
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
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var enterpriseDatagrid;
    var dataGridDetail;
    var dialogEnterprise;
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

        $('input[name="isChineseLabel"]').combobox({
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
            url = '<c:url value="/gspCustomerController.do?edit"/>';
        }else{
            url = '<c:url value="/gspCustomerController.do?add"/>';
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
</script>
</body>
</html>