<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
	<style>
		table th{
			text-align: right;
		}

	</style>
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
var dialogUrl = "/docAsnCertificateController.do?toInfo";
var ezuiImportDataDialog;
var ezuiImportDataForm;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docAsnCertificateController.do?showDatagrid"/>',
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
		idField : 'id',
		columns : [[
			{field: 'customerid',		title: '货主',	width: 100 },
			{field: 'sku',		title: '产品代码',	width: 100 },
			{field: 'lotatt04',		title: '生产批号',	width: 100 },
			{field: 'addtime',		title: '创建人',	width: 100 },
			{field: 'addwho',		title: '创建时间',	width: 100 },
			{field: 'edittime',		title: '编辑时间',	width: 100 },
			{field: 'editwho',		title: '编辑人',	width: 100 },
			{field: 'certificateContext',		title: '合格证照片',	width: 100,hidden:true}
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
			ajaxBtn($('#menuId').val(), '<c:url value="/docAsnCertificateController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
        href:dialogUrl,
        fit:true,
        cache: false,
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
    var row = ezuiDatagrid.datagrid('getSelected');
	processType = 'add';
	$('#docAsnCertificateId').val(0);

	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		// ezuiForm.form('load',{
		// 	customerid : row.customerid,
		// 	sku : row.sku,
		// 	lotatt04 : row.lotatt04,
		// 	addtime : row.addtime,
		// 	addwho : row.addwho,
		// 	edittime : row.edittime,
		// 	editwho : row.editwho,
		// 	certificateContext : row.certificateContext
		// });
		ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
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
					url : 'docAsnCertificateController.do?delete',
                    data : {"sku" : row.sku,"lotatt04":row.lotatt04,"customerid":row.customerid},
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


var addOrEdit = function(url,infoObj) {
    console.log(44444);
    $.ajax({
        url: url,
        data: {"docAsnCertificateForm": JSON.stringify(infoObj)}, type: 'POST', dataType: 'JSON', async: true,
        success: function (result) {
            console.log(result);
            var msg = '';
            try {
                if (result.success) {
                    msg = result.msg;
                    ezuiDatagrid.datagrid('reload');
                    ezuiDialog.dialog('close');
                } else {
                    msg = '<font color="red">' + result.msg + '</font>';
                }
            } catch (e) {
                //msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
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


var commit = function(){
    var row = ezuiDatagrid.datagrid('getSelected');
    var infoObj = new Object();
    $("#ezuiFormInfo input[class='textbox-value']").each(function (index) {
        infoObj[""+$(this).attr("name")+""] = $(this).val();
    })
    //infoObj["enterpriseId"] = $("#ezuiFormInfo input[id='enterpriseId'][data='1']").val();

    var url = '';
    if (processType == 'edit') {
		infoObj["supplierId"] = row.supplierId;
		url = sy.bp()+'/docAsnCertificateController.do?edit';
		addOrEdit(url,infoObj);
    }else{
		url = sy.bp()+'/docAsnCertificateController.do?add';
		addOrEdit(url,infoObj);
    }
};


var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		customerid : $('#customerid').val(),
		sku : $('#sku').val(),
		lotatt04 : $('#lotatt04').val(),
		addtime : $('#addtime').val(),
		addwho : $('#addwho').val(),
		edittime : $('#edittime').val(),
		editwho : $('#editwho').val(),
		certificateContext : $('#certificateContext').val()
	});
};


/* 导入start */
var commitImportData = function(obj){
    ezuiImportDataForm.form('submit', {
        url : '<c:url value="/docAsnCertificateController.do?importExcelData"/>',
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
        var formId = ajaxDownloadFile(sy.bp()+"/docAsnCertificateController.do?exportTemplate", param);
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
							<th>货主</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th><td><input type='text' id='sku' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
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

	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>

	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<%--<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>--%>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<%--<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>--%>
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



<script>

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
                    alert(data.comment);
                    $("#certificateContext").val(data.comment);
                },
                onerror:function(er){
                    // console.log(er);
                }
            });
        }
    })

    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#certificateContext").val()!=""){
                showUrl($("#certificateContext").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }
</script>
</body>
</html>
