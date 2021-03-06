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
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
        rowStyler:function(index,row){
            if(row.isUse == "0" ){
                return 'color:red;';
            }
        },
		rownumbers:true,
		singleSelect:true,
		idField : 'specsId',
		columns : [[

			{field: 'specsId',		title: '主键',	width: 25 ,hidden:true},
            {field: 'isUse',		title: '是否有效',	width: 71, formatter:isUseFormatter},
			{field: 'productCode',		title: '产品代码',	width: 180 },
            {field: 'productRegisterNo',		title: '注册证编号',	width: 200 },

            {field: 'productName',		title: '产品名称',	width: 200 },
			{field: 'productRemark',		title: '产品描述',	width:350 },
            {field: 'specsName',		title: '规格',	width: 150 },
            {field: 'productModel',		title: '型号',	width: 100 },
            {field: 'enterpriseName',		title: '生产企业',	width: 100 },
            {field: 'packingUnit',		title: '包装规格',	width: 80 },
            {field: 'storageCondition',		title: '储存条件',	width: 100 },
            {field: 'unit',		title: '单位',	width: 50  },

            {field: 'maintenanceCycle',		title: '养护周期',	width: 50 ,formatter:day },
            {field: 'isDoublec',		title: '双证',	width: 50 ,formatter:yesOrNoFormatter },

			// {field: 'productionAddress',		title: '产地',	width: 150 },
			//{field: 'barCode',		title: '商品条码',	width: 25 },
            // {field: 'attacheCardCategory',		title: '附卡类别',	width: 100 },
			// {field: 'categories',		title: '分类目录',	width: 25 },
			// {field: 'conversionRate',		title: '换算率',	width: 25 },
			// {field: 'llong',		title: '长',	width: 25 },
			// {field: 'wide',		title: '宽',	width: 25 },
			// {field: 'hight',		title: '高',	width: 25 },
			// {field: 'productLine',		title: '管理分类',	width: 25 },
			// {field: 'manageCategories',		title: '管理分类',	width: 25 },
			// {field: 'packingRequire',		title: '包装要求',	width: 25 },
			// {field: 'transportCondition',		title: '运输条件',	width: 25 },





            // {field: 'isDoublec',		title: '双证',	width: 90, formatter:yesOrNoFormatter},
            {field: 'isCertificate',		title: '产品合格证',	width: 90, formatter:yesOrNoFormatter},


			{field: 'createId',		title: '创建人',	width: 71 },
			{field: 'createDate',		title: '创建时间',	width: 160 },
			{field: 'editId',		title: '编辑人',	width: 71 },
			{field: 'editDate',		title: '编辑时间',	width: 160 },

			// {field: 'alternatName1',		title: '自赋码1',	width: 25 },
			// {field: 'alternatName2',		title: '自赋码2',	width: 25 },
			// {field: 'alternatName3',		title: '自赋码3',	width: 25 },
			// {field: 'alternatName4',		title: '自赋码4',	width: 25 },
			// {field: 'alternatName5',		title: '自赋码5',	width: 25 },
            // {field: 'productNameMain',		title: '产品名称',	width: 200 }
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
			<%--ajaxBtn($('#menuId').val(), '<c:url value="/gspProductRegisterSpecsController.do?getBtn"/>', ezuiMenu);--%>
			$(this).datagrid('unselectAll');
		}
	});

    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        left:0,
        top:0,
        width:1050,
        height:550,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href:dialogUrl,
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
	//
    <%--enterpriseDialog = $('#enterpriseDialog').dialog({--%>
        <%--modal: true,--%>
        <%--title: '<spring:message code="common.dialog.title"/>',--%>
        <%--href: sy.bp() + "/gspProductRegisterSpecsController.do?toSearchDialog",--%>
        <%--width: 850,--%>
        <%--height: 500,--%>
        <%--cache: false,--%>
        <%--onClose: function () {--%>

        <%--}--%>
    <%--})--%>
	// loadfield();
});



var add = function(){
	processType = 'add';
	$('#gspProductRegisterSpecsId').val(0);
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        left:0,
        top:0,
        width:1050,
        height:550,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href:dialogUrl,
        cache: false,
        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    });
    $('#enterpriseDialog').dialog('destroy');

	// ezuiDialog.dialog('open');
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
        ezuiDialog = $('#ezuiDialog').dialog({
            modal : true,
            left:0,
            top:0,
            width:1050,
            height:550,
            title : '<spring:message code="common.dialog.title"/>',
            buttons : '#ezuiDialogBtn',
            href:dialogUrl,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        });

		// ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
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



var addOrEdit = function(url,infoObj) {
    $.ajax({
        url : url,
        data : {"gspProductRegisterSpecsForm":JSON.stringify(infoObj)},type : 'POST', dataType : 'JSON',async  :true,
        success : function(result){
            $.messager.progress('close');
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
}



var commit = function(){

    $.messager.progress({
        text : '<spring:message code="common.message.data.processing"/>', interval : 100
    });

    console.log("qwe");
    var infoObj = new Object();
    $("#ezuiFormInfoProduct input[class='textbox-value']").each(function (index) {
        infoObj[""+$(this).attr("name")+""] = $(this).val();
    })

	if(infoObj["medicalDeviceMark"] == "1"){
        if(infoObj["productRegisterId"] ==null || infoObj["productRegisterId"] =="" ){
            $.messager.show({
                msg : '请添加产品注册证', title : '提示'
            });
            return;
        }
	}


    console.log(infoObj);


    var url = '';
		if (processType == 'edit') {
			var row = ezuiDatagrid.datagrid('getSelected');
			infoObj["specsId"] = row.specsId;
			url = sy.bp()+'/gspProductRegisterSpecsController.do?edit';
			addOrEdit(url,infoObj);
		}else{
			// $.ajax({
			// 	url : '/gspProductRegisterSpecsController.do?getInfoByProductCode',
			// 	data : {productCode: infoObj["productCode"] },
			// 	type : 'POST',
			// 	dataType : 'JSON',
			// 	async  :true,
			// 	success : function(result){
			// 		//alert(result+"====="+result.obj.isUse);
			// 			//alert(111111);
			// 			$.messager.show({
			// 				msg : '已有该产品信息并且有效 无法重复添加', title : '提示'
			// 			});
			// 	},
			// 	error : function() {
			// 		//alert(33333333);
					url = sy.bp()+'/gspProductRegisterSpecsController.do?add';
					addOrEdit(url,infoObj);
			// 	}
			// });

		}


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
        // productNameMain : $('#productNameMain').val(),
        productRegisterNo: $('#productRegisterNoQuery').val(),
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
		enterpriseName : $('#enterpriseNameQ').val(),
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

/* 下载导入模板 */
var downloadTemplate = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_downloadTemplate').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        var formId = ajaxDownloadFile(sy.bp()+"/gspProductRegisterSpecsController.do?exportTemplate", param);
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


/* 导出start */
var doExport = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        param.put("productRegisterNo", $('#productRegisterNoQuery').val());
        param.put("specsName",$('#specsName').val());
        param.put("productCode",$('#productCode').val());
        param.put("productName",$('#productName').val());
        param.put("productRemark", $('#productRemark').val());

        param.put("enterpriseName", $('#enterpriseNameQ').val());


        param.put("productModel", $('#productModel').val());
        param.put("productionAddress", $('#productionAddress').val());
        param.put("createDateEnd", $("#createDateEnd").datebox("getValue"));
        param.put("createDateStart",$("#createDateStart").datebox("getValue"));
        param.put("editDateStart", $("#editDateStart").datebox("getValue"));
        param.put("editDateEnd", $("#editDateEnd").datebox("getValue"));
        param.put("createId", $('#createId').val());
        param.put("editId",$('#editId').val());
        param.put("isUse", $('#isUse').combobox('getValue'));
        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/gspProductRegisterSpecsController.do?exportDataToExcel", param);
        downloadCheckTimer = window.setInterval(function () {
            window.clearInterval(downloadCheckTimer);
            $('#'+formId).remove();
            $('#ezuiBtn_export').linkbutton('enable');
            $.messager.progress('close');
            $.messager.show({
                msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
            });
        }, 1000);
    }else{
        $.messager.show({
            msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
        });
    }
};
/* 导出end */


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
							<th>注册证编号</th><td><input type='text' id='productRegisterNoQuery' class='easyui-textbox' size='16' data-options=''/></td>
						   <th>产品名称</th><td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>规格</th><td><input type='text' id='specsName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th><td><input type='text' id='productCode' class='easyui-textbox' size='16' data-options=''/></td>
						   <th>生产企业</th><td><input type='text' id='enterpriseNameQ' class='easyui-textbox' size='16' data-options=''/></td>

						<td>

                        </td>
					</tr>
						<tr>
							<th>产品描述</th><td><input type='text' id='productRemark' class='easyui-textbox' size='16' data-options=''/></td>
							<th>型号</th><td><input type='text' id='productModel' class='easyui-textbox' size='16' data-options=''/></td>

							<th>创建人</th><td><input type='text' id='createId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建时间</th><td><input type='text' id='createDateStart' class='easyui-datebox' size='16' data-options=''/></td>
                            <th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>

                        </tr>
						<tr >
                            <th>产地</th><td><input type="text" id="productionAddress"  name="productionAddress"  class="easyui-textbox" size='16' data-options=''/></td>
                            <%--<input type='text' id='productionAddress' class='easyui-textbox' size='16' data-options=''/></td>--%>
                            <th>是否有效</th><td><input type="text" id="isUse"  name="isUse"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
							<th>编辑人</th><td><input type='text' id='editId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>编辑时间</th><td><input type='text' id='editDateStart' class='easyui-datebox' size='16' data-options=''/></td>
                            <th>至</th><td><input type='text' id='editDateEnd' class='easyui-datebox' size='16' data-options=''/></td>



                        </tr>
                        <td colspan="10">
							<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
							<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

						</td>
                    </tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
                    <a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>
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
