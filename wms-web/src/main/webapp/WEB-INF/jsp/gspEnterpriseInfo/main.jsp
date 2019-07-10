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
var dialogUrl = "/gspEnterpriseInfoController.do?toDetail";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspEnterpriseInfoController.do?showDatagrid"/>',
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
        rowStyler: isUseRowStyler,
		columns : [[
			{field: 'enterpriseId',		title: '主键',	width: 61 ,hidden:true},
			{field: 'enterpriseNo',		title: '企业信息代码',	width: 61 },
			{field: 'shorthandName',		title: '简称',	width: 61 },
			{field: 'enterpriseName',		title: '企业名称',	width: 61 },
			{field: 'enterpriseType',		title: '企业类型',	width: 61 ,formatter: entTypeFormatter},
            {field: 'createId',		title: '录入人',	width: 61 },
            {field: 'createDate',		title: '录入时间',	width: 61 },
            {field: 'editId',		title: '修改人',	width: 61 },
            {field: 'editDate',		title: '修改时间',	width: 61 },
            {field: 'isUse',		title: '是否有效',	width: 61,formatter:isUseFormatter }
		]],
        onDblClickRow: function(index,row){
			edit(row);
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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspEnterpriseInfoController.do?getBtn"/>', ezuiMenu);
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

	$("#enterpriseTypeQuery").combobox({
        url:sy.bp()+'/commonController.do?getEntType',
        valueField:'id',
        textField:'value'
    });

    $("#isUse").combobox({
        url:sy.bp()+'/commonController.do?getIsUseCombobox',
        valueField:'id',
        textField:'value'
    });

});

var add = function(){
	processType = 'add';
	$('#gspEnterpriseInfoId').val(0);
	ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
};

var edit = function(row){
	processType = 'edit';
	if(!row){
        var row = ezuiDatagrid.datagrid('getSelected');
    }
	if(row){
		ezuiDialog.dialog('refresh', dialogUrl+"&id="+row.enterpriseId).dialog('open');
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
					url : 'gspEnterpriseInfoController.do?delete',
					data : {id : row.enterpriseId},
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
    var gspEnterpriceFrom = new Object();
    var infoObj = new Object();
    var businessObj = new Object();
    var operateobj = new Object();
    var secondRecord = new Object();
    var url = '';
    var isVal = true;


    if (processType == 'edit') {
        url = sy.bp()+"/gspEnterpriseInfoController.do?edit";
    }else{
        url = sy.bp()+"/gspEnterpriseInfoController.do?add";
    }
    var enterpriceId = "";
    var row = ezuiDatagrid.datagrid('getSelected');
    if(row){
        enterpriceId = row.enterpriceId;
    }
    //判断基本信息
    isVal = checkFormData("ezuiFormInfo",infoObj);
	if(isVal == false){
        showMsg("企业基础信息未填全！");
        return;
	}

	//判断营业执照信息
	isVal = checkFormData("ezuiFormBusiness",businessObj);
	if(infoObj.enterpriseType != CODE_ENT_TYP.CODE_ENT_TYP_GWSC && !checkObjIsEmpty(businessObj)){
        showMsg("必须填写营业执照信息！");
        return;
	}
    if(infoObj.enterpriseType != CODE_ENT_TYP.CODE_ENT_TYP_GWSC && isVal == false){
        showMsg("营业执照信息填写不完全！");
        return;
    }
    console.log("bus---"+businessObj);

	//判断经营许可证
    isVal = checkFormData("ezuiFormOperate",operateobj);
	if(infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_GNSC && !checkObjIsEmpty(operateobj)){
        showMsg("生产类型企业需要填写经营生产许可证！");
        return;
	}
    if(infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_GNSC && isVal == false){
        showMsg("经营生产许可证填写不完全！");
        return;
    }

	//第二备案凭证
    checkFormData("ezuiFormRecord",secondRecord);

    gspEnterpriceFrom["gspEnterpriseInfoForm"] = infoObj;
    gspEnterpriceFrom["gspBusinessLicenseForm"] = businessObj;
    gspEnterpriceFrom["gspOperateLicenseForm"] = operateobj;
    gspEnterpriceFrom["gspSecondRecordForm"] = secondRecord;
	console.log(gspEnterpriceFrom);
	//提交
    submitFormData(enterpriceId,gspEnterpriceFrom,url);
};

function submitFormData(enterpriceId,gspEnterpriceFrom,url) {
    $.ajax({
        url : url,
        data : {"enterpriseId":enterpriceId,"gspEnterpriceFrom":JSON.stringify(gspEnterpriceFrom)},type : 'POST', dataType : 'JSON',async  :true,
        success : function(result){
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

var checkFormData = function (formId,obj) {
    var checkResult = true;
    $("#"+formId+" input[type!=hidden]").each(function (index) {
        if($(this).attr("class")){
            if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                if(!$(this).textbox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).textbox("getValue");
            }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                if(!$(this).datebox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).datebox("getValue");
            }else if($(this).attr("class").indexOf('easyui-combobox')!=-1){
                if(!$(this).combobox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).combobox("getValue");
            }else if($(this).attr("class").indexOf('easyui-numberbox')!=-1){
                if(!$(this).combobox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).numberbox("getValue");
            }
        }
    })
	if(checkObjIsEmpty(obj) == true && checkResult == true){
        //加载经营范围
        var scopArr = $("#"+formId+" input[id='choseScope']");
        if(scopArr){
            //obj["scopArr"] = $(scopArr).val() || "";
            if($(scopArr).val()!="" && $(scopArr).val()!=undefined){
                var str = $(scopArr).val();
                obj["scopArr"] = str;
            }else{
                obj["scopArr"] = "";
            }

        }
        //操作类型是否是换证
        var opType = $("#"+formId+" input[id='opType']");
        if(opType){
            obj["opType"] = $(opType).val() || "";
        }
	}
	return checkResult;
}

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		enterpriseNo : $('#enterpriseNo').textbox("getValue"),
		shorthandName : $('#shorthandName').textbox("getValue"),
		enterpriseName : $('#enterpriseName').textbox("getValue"),
		enterpriseType : $('#enterpriseTypeQuery').combobox("getValue"),
		createDateBegin : $('#createDateBegin').datebox("getValue"),
        createDateEnd : $('#createDateEnd').datebox("getValue"),
		isUse : $('#isUse').combobox("getValue")
	});
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
							<th>企业信用代码</th><td><input type='text' id='enterpriseNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>简称</th><td><input type='text' id='shorthandName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业名称</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业类型</th><td><input type='text' id='enterpriseTypeQuery' class='easyui-combobox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
						<tr>
							<th>创建时间</th><td><input type='text' id='createDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<th>是否启用</th>
							<td>
								<select id="isUse" style="width:100px;">
								</select>
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
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'>提交企业信息</a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
