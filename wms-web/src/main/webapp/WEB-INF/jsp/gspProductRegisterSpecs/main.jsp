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
var dialogUrl = "/gspProductRegisterSpecsController.do?toAdd";
var ezuiImportDataDialog;
var ezuiImportDataForm;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspProductRegisterSpecsController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '待输入标题',
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

			{field: 'specsId',		title: '主键',	width: 25 },
            // {field: 'productRegisterId',		title: '产品注册证表主键',	width: 25 },


			{field: 'specsName',		title: '规格名称',	width: 25 },
			{field: 'productCode',		title: '代码',	width: 25 },
			{field: 'productName',		title: '商品名称',	width: 25 },
			{field: 'productRemark',		title: '商品描述',	width: 25 },
			{field: 'productModel',		title: '型号',	width: 25 },
			{field: 'productionAddress',		title: '产地',	width: 25 },
			//{field: 'barCode',		title: '商品条码',	width: 25 },
			{field: 'unit',		title: '单位',	width: 25 },
			{field: 'packingUnit',		title: '包装单位',	width: 25 },
			// {field: 'categories',		title: '分类目录',	width: 25 },
			// {field: 'conversionRate',		title: '换算率',	width: 25 },
			// {field: 'llong',		title: '长',	width: 25 },
			// {field: 'wide',		title: '宽',	width: 25 },
			// {field: 'hight',		title: '高',	width: 25 },
			// {field: 'productLine',		title: '管理分类',	width: 25 },
			// {field: 'manageCategories',		title: '管理分类',	width: 25 },
			// {field: 'packingRequire',		title: '包装要求',	width: 25 },
			// {field: 'storageCondition',		title: '存储条件',	width: 25 },
			// {field: 'transportCondition',		title: '运输条件',	width: 25 },
			{field: 'createId',		title: '创建人',	width: 25 },
			{field: 'createDate',		title: '创建时间',	width: 25 },
			{field: 'editId',		title: '编辑人',	width: 25 },
			{field: 'editDate',		title: '编辑时间',	width: 25 },
			{field: 'isUse',		title: '是否有效',	width: 25, formatter:function(value,rowData,rowIndex){
                    return rowData.isUse == '1' ? '是' : '否';
                }},
			// {field: 'alternatName1',		title: '自赋码1',	width: 25 },
			// {field: 'alternatName2',		title: '自赋码2',	width: 25 },
			// {field: 'alternatName3',		title: '自赋码3',	width: 25 },
			// {field: 'alternatName4',		title: '自赋码4',	width: 25 },
			// {field: 'alternatName5',		title: '自赋码5',	width: 25 },

            {field: 'productRegisterNo',		title: '注册证编号',	width: 25 },
            {field: 'productNameMain',		title: '产品名称',	width: 25 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspProductRegisterSpecsController.do?getBtn"/>', ezuiMenu);
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
	processType = 'add';
	$('#gspProductRegisterSpecsId').val(0);
	ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		// ezuiForm.form('load',{
		// 	specsId : row.specsId,
		// 	productRegisterId : row.productRegisterId,
		// 	specsName : row.specsName,
		// 	productCode : row.productCode,
		// 	productName : row.productName,
		// 	productRemark : row.productRemark,
		// 	productModel : row.productModel,
		// 	productionAddress : row.productionAddress,
		// 	barCode : row.barCode,
		// 	unit : row.unit,
		// 	packingUnit : row.packingUnit,
		// 	categories : row.categories,
		// 	conversionRate : row.conversionRate,
		// 	llong : row.llong,
		// 	wide : row.wide,
		// 	hight : row.hight,
		// 	productLine : row.productLine,
		// 	manageCategories : row.manageCategories,
		// 	packing_Require : row.packing_Require,
		// 	storageCondition : row.storageCondition,
		// 	transportCondition : row.transportCondition,
		// 	createId : row.createId,
		// 	createDate : row.createDate,
		// 	editId : row.editId,
		// 	editDate : row.editDate,
		// 	isUse : row.isUse,
		// 	alternatName1 : row.alternatName1,
		// 	alternatName2 : row.alternatName2,
		// 	alternatName3 : row.alternatName3,
		// 	alternatName4 : row.alternatName4,
		// 	alternatName5 : row.alternatName5
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
					url : 'gspProductRegisterSpecsController.do?delete',
					data : {id : row.specsId},
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
    console.log("qwe");
    var infoObj = new Object();
    $("#ezuiFormInfo input[class='textbox-value']").each(function (index) {
        infoObj[""+$(this).attr("name")+""] = $(this).val();
    })
	console.log(infoObj);
    var url = '';
    if (processType == 'edit') {
        var row = ezuiDatagrid.datagrid('getSelected');
        infoObj["supplierId"] = row.supplierId;
        url = sy.bp()+'/gspSupplierController.do?edit';
    }else{
        url = sy.bp()+'/gspSupplierController.do?add';
    }
    $.ajax({
        url : url,
        data : {"gspSupplierForm":JSON.stringify(infoObj)},type : 'POST', dataType : 'JSON',async  :true,
        success : function(result){
            console.log(result);
            var msg='';
            try{
                if(result.success){
                    msg = result.msg;
                    ezuiDatagrid.datagrid('reload');
                    ezuiDialog.dialog('close');
                }else{
                    msg = '<font color="red">' + result.msg + '</font>';
                }
            }catch (e) {
                //msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
            } finally {
                $.messager.show({
                    msg : msg, title : '<spring:message code="common.message.prompt"/>'
                });
                $.messager.progress('close');
            }
        }
    });

	/*var url = '';
	if (processType == 'edit') {
		url = sy.bp()+'/gspProductRegisterSpecsController.do?edit';
	}else{
		url = sy.bp()+'/gspProductRegisterSpecsController.do?add';
	}
	console.log(ezuiForm);
	ezuiForm.form('submit', {
		url : url,
		onSubmit : function(){
		    console.log("1111111111");
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
	});*/
};
var doSearch = function(){

	ezuiDatagrid.datagrid('load', {
        productNameMain : $('#productNameMain').val(),
        productRegisterNo: $('#productRegisterNo').val(),
		specsName : $('#specsName').val(),
		productCode : $('#productCode').val(),
		productName : $('#productName').val(),
		productRemark : $('#productRemark').val(),
		productModel : $('#productModel').val(),
		productionAddress : $('#productionAddress').val(),

        createDateEnd : $("#createDateEnd").datebox("getValue"),
        createDateStart : $("#createDateStart").datebox("getValue"),
        editDateStart : $("#editDateStart").datebox("getValue"),
        editDateEnd : $("#editDateEnd").datebox("getValue"),
		// unit : $('#unit').val(),
		// packingUnit : $('#packingUnit').val(),
		// categories : $('#categories').val(),
		// conversionRate : $('#conversionRate').val(),
		// llong : $('#llong').val(),
		// wide : $('#wide').val(),
		// hight : $('#hight').val(),
		// productLine : $('#productLine').val(),
		// manageCategories : $('#manageCategories').val(),
		// packing_Require : $('#packing_Require').val(),
		// storageCondition : $('#storageCondition').val(),
		// transportCondition : $('#transportCondition').val(),
		createId : $('#createId').val(),
		//createDate : $('#createDate').val(),
		editId : $('#editId').val(),
		//editDate : $('#editDate').val(),
		isUse : $('#isUse').combobox('getValue')

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
						<tr>
							<th>注册证编号</th><td><input type='text' id='productRegisterNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品名称</th><td><input type='text' id='productNameMain' class='easyui-textbox' size='16' data-options=''/></td>
							<th>规格名称</th><td><input type='text' id='specsName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>代码</th><td><input type='text' id='productCode' class='easyui-textbox' size='16' data-options=''/></td>
							<th>商品名称</th><td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
						<td>

                        </td>
					</tr>
						<tr>
							<th>商品描述</th><td><input type='text' id='productRemark' class='easyui-textbox' size='16' data-options=''/></td>
							<th>型号</th><td><input type='text' id='productModel' class='easyui-textbox' size='16' data-options=''/></td>

							<th>创建人</th><td><input type='text' id='createId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建时间起始</th><td><input type='text' id='createDateStart' class='easyui-datebox' size='16' data-options=''/></td>
                            <th>创建时间结束</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>

                        </tr>
						<tr >
                            <th>产地</th><td><input type='text' id='productionAddress' class='easyui-textbox' size='16' data-options=''/></td>
							<th>编辑人</th><td><input type='text' id='editId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>编辑时间起始</th><td><input type='text' id='editDateStart' class='easyui-datebox' size='16' data-options=''/></td>
                            <th>编辑时间结束</th><td><input type='text' id='editDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
                            <th>是否有效：</th><td><input type="text" id="isUse"  name="isUse"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>


                        </tr>
                        <td colspan="10">
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
		//////
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
