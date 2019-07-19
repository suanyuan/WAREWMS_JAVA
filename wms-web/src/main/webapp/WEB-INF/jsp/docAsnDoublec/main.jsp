<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;

var ezuiImportDataDialog;
var ezuiImportDataForm;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docAsnDoublecController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:false,
		idField : 'doublecno',
		columns : [[
			{field: 'doublecno',		title: '任务号',	width: 53 },
			{field: 'customerid',		title: '',	width: 53 ,hidden:true},
			{field: 'context1',		title: '产品型号',	width: 53 },
			{field: 'context2',		title: '名称',	width: 53 },
			{field: 'context3',		title: '执行标准',	width: 53 },
			{field: 'context4',		title: '备注',	width: 53 },
			{field: 'context5',		title: '待输入栏位6',	width: 53 ,hidden:true},
			{field: 'context6',		title: '待输入栏位7',	width: 53 ,hidden:true},
			{field: 'context7',		title: '待输入栏位8',	width: 53 ,hidden:true},
			{field: 'context8',		title: '待输入栏位9',	width: 53 ,hidden:true},
			{field: 'matchFlag',		title: '匹配标记',	width: 53 , formatter:function(value,rowData,rowIndex){
                    return rowData.matchFlag == '1' ? '已匹配' : '未匹配';
                }},
			{field: 'addtime',		title: '创建时间',	width: 53 },
			{field: 'addwho',		title: '创建人',	width: 53 },
			{field: 'edittime',		title: '编辑时间',	width: 53 },
			{field: 'editwho',		title: '编辑人',	width: 53 }
		]],

		onRowContextMenu : function(event, rowIndex, rowData) {
			event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			});
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/docAsnDoublecController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

    //导入
    ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
        modal : true,
        title : '导入',
        buttons : '#ezuiImportDataDialogBtn',
        onClose : function() {
            ezuiFormClear(ezuiImportDataForm);
        }
    }).dialog('close');
    /* 控件初始化end */
});
var add = function(){
	processType = 'add';
	$('#docAsnDoublecId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			doublecno : row.doublecno,
			customerid : row.customerid,
			context1 : row.context1,
			context2 : row.context2,
			context3 : row.context3,
			context4 : row.context4,
			context5 : row.context5,
			context6 : row.context6,
			context7 : row.context7,
			context8 : row.context8,
			matchFlag : row.matchFlag,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelections');
	if(row){
	    var arr = new Array();
	    for(var i=0;i<row.length;i++){
            arr.push(row[i].doublecno);
		}
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docAsnDoublecController.do?delete',
					data : {id :arr.join(",")},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '<spring:message code="common.message.data.delete.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							ezuiDatagrid.datagrid('reload');
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docAsnDoublecController.do?edit"/>';
	}else{
		url = '<c:url value="/docAsnDoublecController.do?add"/>';
	}
	ezuiForm.form('submit', {
		url : url,
		onSubmit : function(){
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
};
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		doublecno : $('#doublecno').val(),
		customerid : $('#customerid').val(),
		context1 : $('#context1').val(),
		context2 : $('#context2').val(),
		context3 : $('#context3').val(),
		context4 : $('#context4').val(),
		context5 : $('#context5').val(),
		context6 : $('#context6').val(),
		context7 : $('#context7').val(),
		context8 : $('#context8').val(),
		matchFlag : $('#matchFlag').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val()
	});
};

/* 导入start */
var commitImportData = function(obj){
    ezuiImportDataForm.form('submit', {
        url : '<c:url value="/gspProductRegisterSpecsController.do?importExcelData"/>',
        onSubmit : function(){
            if(ezuiImportDataForm.form('validate')){
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
                    msg = result.msg.replace(/ /g, '\n');
                    ezuiDatagrid.datagrid('reload');
                }else{
                    msg = result.msg.replace(/ /g, '\n');
                }
            } catch (e) {
                msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
            } finally {
                ezuiFormClear(ezuiImportDataForm);
                $('#importResult').textbox('setValue',msg);
                $.messager.progress('close');
            }
        }
    });
};

/* 下载导入模板 */
var downloadTemplate = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_downloadTemplate').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        var formId = ajaxDownloadFile(sy.bp()+"/docAsnDoublecController.do?exportTemplate", param);
        downloadCheckTimer = window.setInterval(function () {
            var list = new cookieList('downloadToken');
            if (list.items() == token){
                window.clearInterval(downloadCheckTimer);
                list.clear();
                $('#'+formId).remove();
                $('#ezuiBtn_downloadTemplate').linkbutton('enable');
                $.messager.show({
                    msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
                });
            };
        }, 1000);
    }else{
        $.messager.show({
            msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
        });
    };
};
/* 导入end */

var toImportData = function(){
    ezuiImportDataDialog.dialog('open');
};

</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>任务号</th><td><input type='text' id='doublecno' class='easyui-textbox' size='16' data-options=''/></td>

							<th>产品型号</th><td><input type='text' id='context1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>名称</th><td><input type='text' id='context2' class='easyui-textbox' size='16' data-options=''/></td>
							<th>执行标准</th><td><input type='text' id='context3' class='easyui-textbox' size='16' data-options=''/></td>
							<th>备注</th><td><input type='text' id='context4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>标记匹配</th><td><input type='text' id='matchFlag' class='easyui-textbox' size='16' data-options=''/></td>
							<%--<th>待输入名称6：</th><td><input type='text' id='context5' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称7：</th><td><input type='text' id='context6' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称8：</th><td><input type='text' id='context7' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称9：</th><td><input type='text' id='context8' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称10：</th><td><input type='text' id='matchFlag' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称11：</th><td><input type='text' id='addtime' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称12：</th><td><input type='text' id='addwho' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称13：</th><td><input type='text' id='edittime' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称14：</th><td><input type='text' id='editwho' class='easyui-textbox' size='16' data-options=''/></td>--%>
						</tr>
						<tr>
							<td colspan="12">
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>

					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<%--<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='docAsnDoublecId' name='docAsnDoublecId'/>
			<table>
				<tr>
					<th>任务号</th>
					<td><input type='text' name='doublecno' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='context1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='context2' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='context3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='context4' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='context5' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='context6' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='context7' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='context8' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='matchFlag' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>

			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

	<!-- 导入start -->
	<div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>
		<form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>
			<table>
				<tr>
					<th>档案</th>
					<td>
						<input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36" data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>
						<a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton' href='javascript:void(0);'>下载档案模版</a>
					</td>
				</tr>
				<tr>
					<th>执行结果</th>
					<td><input id='importResult' class="easyui-textbox" size='100' style="height:150px" data-options="editable:false,multiline:true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiImportDataDialogBtn'>
		<a onclick='commitImportData();' id='ezuiBtn_importDataCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiImportDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<!-- 导入end -->
</body>
</html>
