<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp'/>
    <c:import url='/WEB-INF/jsp/include/easyui.jsp'/>
    <style>
        table th {
            text-align: right;
        }

    </style>
    <script type='text/javascript'>
        var processType;
        var ezuiMenu;
        var ezuiForm;
        var ezuiDialog;
        var ezuiDatagrid;
        var ezuiImportDataDialog;
        var ezuiImportDataForm;
        $(function () {
            ezuiMenu = $('#ezuiMenu').menu();
            ezuiForm = $('#ezuiForm').form();
            ezuiImportDataForm = $('#ezuiImportDataForm').form();
            ezuiDatagrid = $('#ezuiDatagrid').datagrid({
                url: '<c:url value="/remindController.do?showDatagrid1"/>',
                method: 'POST',
                toolbar: '#toolbar',
                title: '',
                pageSize: 50,
                pageList: [10, 50, 100],
                fit: true,
                border: false,
                fitColumns: true,
                nowrap: true,
                striped: true,
                collapsible: false,
                pagination: true,
                rownumbers: true,
                singleSelect: true,
                idField: 'id',
                columns: [[
                    // {field: 'sku',		title: '序号',	width: 100 },
                    {field: 'codenameC', title: '提醒模块', width: 300},
                    {field: 'codenameE', title: '提醒内容', width: 400},
                    {field: 'showSequence', title: '个数', width: 100}
                ]],
                // onDblClickCell: function(index,field,value){
                // 	edit();
                // },
                onRowContextMenu: function (event, rowIndex, rowData) {
                    event.preventDefault();
                    $(this).datagrid('unselectAll');
                    $(this).datagrid('selectRow', rowIndex);
                    ezuiMenu.menu('show', {
                        left: event.pageX,
                        top: event.pageY
                    });
                }, onLoadSuccess: function (data) {
                    ajaxBtn($('#menuId').val(), '<c:url value="/basGtnController.do?getBtn"/>', ezuiMenu);
                    $(this).datagrid('unselectAll');
                }
            });
            ezuiDialog = $('#ezuiDialog').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '#ezuiDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiForm);
                }
            }).dialog('close');

            //导入
            ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
                modal: true,
                title: '导入',
                buttons: '#ezuiImportDataDialogBtn',
                onClose: function () {
                    ezuiFormClear(ezuiImportDataForm);
                }
            }).dialog('close');
            /* 控件初始化end */
        });
        var add = function () {
            processType = 'add';
            $('#basGtnId').val(0);
            ezuiDialog.dialog('open');
        };
        var edit = function () {
            processType = 'edit';
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                ezuiForm.form('load', {
                    sku: row.sku,
                    gtncode: row.gtncode
                });
                ezuiDialog.dialog('open');
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
        var del = function () {
            var row = ezuiDatagrid.datagrid('getSelected');
            if (row) {
                $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function (confirm) {
                    if (confirm) {
                        $.ajax({
                            url: 'basGtnController.do?delete',
                            data: {id: row.sku},
                            type: 'POST',
                            dataType: 'JSON',
                            success: function (result) {
                                var msg = '';
                                try {
                                    msg = result.msg;
                                } catch (e) {
                                    msg = '<spring:message code="common.message.data.delete.failed"/>';
                                } finally {
                                    $.messager.show({
                                        msg: msg, title: '<spring:message code="common.message.prompt"/>'
                                    });
                                    ezuiDatagrid.datagrid('reload');
                                }
                            }
                        });
                    }
                });
            } else {
                $.messager.show({
                    msg: '<spring:message code="common.message.selectRecord"/>',
                    title: '<spring:message code="common.message.prompt"/>'
                });
            }
        };
        var commit = function () {
            var url = '';
            if (processType == 'edit') {
                url = '<c:url value="/basGtnController.do?edit"/>';
            } else {
                url = '<c:url value="/basGtnController.do?add"/>';
            }
            ezuiForm.form('submit', {
                url: url,
                onSubmit: function () {
                    if (ezuiForm.form('validate')) {
                        $.messager.progress({
                            text: '<spring:message code="common.message.data.processing"/>', interval: 100
                        });
                        return true;
                    } else {
                        return false;
                    }
                },
                success: function (data) {
                    var msg = '';
                    try {
                        var result = $.parseJSON(data);
                        if (result.success) {
                            msg = result.msg;
                            ezuiDatagrid.datagrid('reload');
                            ezuiDialog.dialog('close');
                        } else {
                            msg = '<font color="red">' + result.msg + '</font>';
                        }
                    } catch (e) {
                        msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                        msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
                    } finally {
                        $.messager.show({
                            msg: msg, title: '<spring:message code="common.message.prompt"/>'
                        });
                        $.messager.progress('close');
                    }
                }
            });
        };
        var doSearch = function () {
            ezuiDatagrid.datagrid('load', {
                sku: $('#sku').val(),
                gtncode: $('#gtncode').val()
            });
        };

        /* 导入start */
        var commitImportData1 = function (obj) {
            ezuiImportDataForm.form('submit', {
                url: '<c:url value="/basGtnController.do?importExcelData"/>',
                onSubmit: function () {
                    if (ezuiImportDataForm.form('validate')) {
                        $.messager.progress({
                            text: '<spring:message code="common.message.data.processing"/>', interval: 100
                        });
                        return true;
                    } else {
                        return false;
                    }
                },
                success: function (data) {
                    var msg = '';
                    try {
                        var result = $.parseJSON(data);
                        if (result.success) {
                            msg = result.msg.replace(/ /g, '\n');
                            ezuiDatagrid.datagrid('reload');
                        } else {
                            msg = result.msg.replace(/ /g, '\n');
                        }
                    } catch (e) {
                        msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                        msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
                    } finally {
                        ezuiFormClear(ezuiImportDataForm);
                        $('#importResult').textbox('setValue', msg);
                        $.messager.progress('close');
                    }
                }
            });
        };

        /* 下载导入模板 */
        var downloadTemplate = function () {
            if (navigator.cookieEnabled) {
                $('#ezuiBtn_downloadTemplate').linkbutton('disable');
                var token = new Date().getTime();
                var param = new HashMap();
                param.put("token", token);
                var formId = ajaxDownloadFile(sy.bp() + "/basGtnController.do?exportTemplate", param);
                downloadCheckTimer = window.setInterval(function () {
                    var list = new cookieList('downloadToken');
                    if (list.items() == token) {
                        window.clearInterval(downloadCheckTimer);
                        list.clear();
                        $('#' + formId).remove();
                        $('#ezuiBtn_downloadTemplate').linkbutton('enable');
                        $.messager.show({
                            msg: "<spring:message code='common.message.export.success'/>",
                            title: "<spring:message code='common.message.prompt'/>"
                        });
                    }
                    ;
                }, 1000);
            } else {
                $.messager.show({
                    msg: "<spring:message code='common.navigator.cookieEnabled.false'/>",
                    title: "<spring:message code='common.message.prompt'/>"
                });
            }
            ;
        };
        /* 导入end */

        var toImportData = function () {
            ezuiImportDataDialog.dialog('open');
        };

    </script>
</head>
<body>
<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<div class='easyui-layout' data-options='fit:true,border:false'>
    <div data-options='region:"center",border:false' style='overflow: hidden;'>
        <div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
            <%--<fieldset>--%>
            <%--<legend><spring:message code='common.button.query'/></legend>--%>
            <%--<table>--%>
            <%--<tr>--%>
            <%--<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>--%>
            <%--<th>GTIN码</th><td><input type='text' id='gtncode' class='easyui-textbox' size='16' data-options=''/></td>--%>
            <%--<td>--%>
            <%--<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>--%>
            <%--<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>--%>
            <%--<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>--%>

            <%--</td>--%>
            <%--</tr>--%>
            <%--</table>--%>
            <%--</fieldset>--%>
            <%--<div>--%>
            <%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
            <%--<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
            <%--&lt;%&ndash;<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>&ndash;%&gt;--%>
            <%--<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>--%>
            <%--</div>--%>
        </div>
        <table id='ezuiDatagrid'></table>
    </div>
</div>
<div id='ezuiDialog' style='padding: 10px;'>
    <form id='ezuiForm' method='post'>
        <input type='hidden' id='basGtnId' name='basGtnId'/>
        <table>
            <tr>
                <th>产品代码</th>
                <td><input type='text' name='sku' class='easyui-textbox' size='16' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>GTIN码</th>
                <td><input type='text' name='gtncode' class='easyui-textbox' size='16' data-options='required:true'/>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id='ezuiDialogBtn'>
    <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.close'/></a>
</div>
<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
    <div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message
            code='common.button.add'/></div>
    <div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message
            code='common.button.delete'/></div>
    <div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message
            code='common.button.edit'/></div>

</div>

<!-- 导入start -->
<div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>
        <table>
            <tr>
                <th>档案</th>
                <td>
                    <input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36"
                           data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>
                    <a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton'
                       href='javascript:void(0);'>下载档案模版</a>
                </td>
            </tr>
            <tr>
                <th>执行结果</th>
                <td><input id='importResult' class="easyui-textbox" size='100' style="height:150px"
                           data-options="editable:false,multiline:true"/></td>
            </tr>
        </table>
    </form>
</div>
<div id='ezuiImportDataDialogBtn'>
    <a onclick='commitImportData1();' id='ezuiBtn_importDataCommit' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiImportDataDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<!-- 导入end -->
</body>
</html>
