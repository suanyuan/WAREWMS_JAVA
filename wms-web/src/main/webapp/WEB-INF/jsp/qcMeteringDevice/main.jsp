<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
var ezuiImportDataDialog;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    ezuiImportDataForm=$('#ezuiImportDataForm').form();
    ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/qcMeteringDeviceController.do?showDatagrid"/>',
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
		singleSelect:true,
		idField : 'calId',
		columns : [[
			{field: 'calId',		title: '待输入栏位0',	width: 80,hidden:true },
			{field: 'calName',		title: '名称',	width: 80 },
			{field: 'calNumber',	title: '编号',	width: 80 },
			{field: 'calTerm',		title: '校期',	width: 80 },
			{field: 'calCardUrl',	title: '证件',	width: 80 },
            {field: 'activeFlag',	title: '是否有效',	width: 80 ,formatter:function (value,rowData,rowIndex) {
                    return rowData.activeFlag=='1'? '是':'否'
                }},
            {field: 'createId',		title: '创建人',	width: 80 },
            {field: 'createDate',	title: '创建日期',	width: 80 },
            {field: 'editId',		title: '修改人',	width: 80 },
            {field: 'editDate',		title: '修改日期',	width: 80 }
		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
			event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			});
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/qcMeteringDeviceController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		width:300,
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');


    $('#file').filebox({
        prompt: '选择一个文件',//文本说明文件
        width: '170', //文本宽度
        buttonText: '上传',  //按钮说明文字
        required: true,
        onChange:function(data){
            if(data){
                doUpload(data);
            }
        }
    });

    function doUpload(data) {
        $.messager.progress({
            text : '<spring:message code="common.message.data.processing"/>', interval : 100
        });
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
                $.messager.progress('close');
                $("#calCardUrl").val(data.comment);
            },
            onerror:function(er){
                $.messager.progress('close');
                console.log(er);
            }
        });
    }
    $('#activeFlag').combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });
    $('#active').combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });

    $.ajax({
        url : sy.bp()+"/qcMeteringDeviceController.do?getQcMeteringDeviceOutTime",
        type : 'POST', dataType : 'JSON',async  :true,
        success : function(result){
            if(result.obj){
                $('#tb').datagrid({
                    data:result.obj,
                    columns:[[
                        {field: 'calName',		title: '名称',	width: 80 },
                        {field: 'calNumber',	title: '编号',	width: 80 },
                        {field: 'calTerm',		title: '校期',	width: 80 },
                        {field: 'activeFlag',	title: '是否有效',	width: 80 },
                        {field: 'createId',	title: '创建人',	width: 80 },
                        {field: 'createDate',	title: '创建时间',	width: 130 }
                    ]]
                });

                $('#show').dialog({
                    modal : true,
                    title : '计量设备校期过期提醒',
                    width:550,
                    height:350,
                    cache: false,
                    onClose : function() {

                    }
                })
            }
        }
    });



    //导入
    ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
        modal : true,
        title : '导入',
        buttons : '#ezuiImportDataDialogBtn',
        onClose : function() {
            ezuiFormClear(ezuiImportDataForm);
        }
    }).dialog('close');
});

var add = function(){
	processType = 'add';
	$('#qcMeteringDeviceId').val(0);
    // $('#file').filebox(0);
    $('#file').filebox({prompt:""});
    $('#calCardUrl').val("");
	ezuiDialog.dialog('open');
};


function viewUrl(url) {
    if(url){
        showUrl(url);
    }else{
        if($("#calCardUrl").val()!=""){
            showUrl($("#calCardUrl").val());
        }else {
            showMsg("请上传设备证件！");
        }
    }
}


var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			calId : row.calId,
			calName : row.calName,
			calNumber : row.calNumber,
			calTerm : row.calTerm,
            calCardUrl : row.calCardUrl,
			createId : row.createId,
			createDate : row.createDate,
			editId : row.editId,
			editDate : row.editDate,
			activeFlag : row.activeFlag
		});
        $('#file').filebox({prompt:row.calCardUrl,required: false});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'qcMeteringDeviceController.do?delete',
					data : {id : row.calId},
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
		url = '<c:url value="/qcMeteringDeviceController.do?edit"/>';
	}else{
		url = '<c:url value="/qcMeteringDeviceController.do?add"/>';
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
                    ezuiForm.form('clear');
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
		calId : $('#calId').val(),
		calName : $('#calName').val(),
		calNumber : $('#calNumber').val(),
		calTerm : $('#calTerm').datebox('getValue'),
		calCardUrl : $('#calCardUrl').val(),
		createId : $('#createId').val(),
		createDateStart : $('#createDateStart').datebox('getValue'),
		createDateEnd : $('#createDateEnd').datebox('getValue'),
		editId : $('#editId').val(),
		editDateStart : $('#editDateStart').datebox('getValue'),
		editDateEnd : $('#editDateEnd').datebox('getValue'),
		activeFlag : $('#activeFlag').combobox('getValue')
	});
};


/* 导入start */
var commitImportData = function(obj){
    ezuiImportDataForm.form('submit', {
        url : '<c:url value="/qcMeteringDeviceController.do?importExcelData"/>',
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
        var formId = ajaxDownloadFile(sy.bp()+"/qcMeteringDeviceController.do?exportTemplate", param);
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
					<table style="text-align: right">
						<tr>
							<th>名称</th><td><input type='text' id='calName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>编号</th><td><input type='text' id='calNumber' class='easyui-textbox' size='16' data-options=''/></td>
							<th>校期</th><td><input type='text' id='calTerm' class='easyui-datebox' size='16' data-options=''/></td>
							<%--<th>待输入名称4：</th><td><input type='text' id='calCardUrl' class='easyui-textbox' size='16' data-options=''/></td>--%>

							<th>创建日期</th><td><input type='text' id='createDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>创建人</th><td><input type='text' id='createId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>修改人</th><td><input type='text' id='editId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否有效</th><td><input type='text' id='activeFlag' class='easyui-combobox' size='16' data-options=''/></td>
							<th>修改日期</th><td><input type='text' id='editDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='editDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>

							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='calId' name='calId'/>
			<table>
				<tr>
					<th>名称</th>
					<td><input type='text' name='calName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>编号</th>
					<td><input type='text' name='calNumber' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>校期</th>
					<td><input type='text' name='calTerm' class='easyui-datebox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>证件上传</th>
					<td><input  id="file" name="file" />
						<input type="hidden"  class="textbox-value" name="calCardUrl"<%-- class='easyui-textbox' size='16' data-options='required:true'--%> id="calCardUrl" />
						<a id="btn" href="#" class="easyui-linkbutton" onclick="viewUrl()">查看</a>
					</td>
				</tr>
				<tr>
					<th>是否有效</th>
					<td><input type='text' id="active" name='activeFlag' class='easyui-combobox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
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
	<div id="show" style="display: none;">
		<table id="tb"></table>
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
</body>
</html>
