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
var  dialogUrl="/basCarrierLicenseController.do?toDetail";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/basCarrierLicenseController.do?showDatagrid"/>',
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
		rownumbers:true,
		singleSelect:true,
		idField : 'carrierLicenseId',
		columns : [[
            {field: 'carrierLicenseId',		title: '承运商名称',	width: 120,hidden:true },
			{field: 'enterpriseName',		title: '承运商名称',	width: 120 },
			{field: 'roadNumber',		title: '道路运营许可证编号',	width: 140 },
			{field: 'roadNumberTerm',		title: '证件有效期',	width: 120 },
			{field: 'roadAuthorityPermit',		title: '核发机关',	width: 120 },
			{field: 'roadBusinessScope',		title: '经营范围',	width: 120 },
			{field: 'carrierNo',		title: '快递经营许可证编号',	width: 120 },
			{field: 'carrierDate',		title: '发证日期',	width: 125 },
            {field: 'carrierAuthorityPermit',		title: '发证机关',	width: 125 },
            {field: 'carrierEndDate',		title: '有效期至',	width: 125 },
			{field: 'carrierBusinessScope',		title: '业务范围',	width: 120 },
            {field: 'contractNo',		title: '合同编号',	width: 38 ,hidden:true},
            {field: 'contractUrl',		title: '合同文件',	width: 38 ,hidden:true},
            {field: 'clientContent',		title: '合同内容',	width: 38 ,hidden:true},
            {field: 'clientStartDate',		title: '合同开始时间',	width: 38 ,hidden:true},
            {field: 'clientEndDate',		title: '合同结束时间',	width: 38 ,hidden:true},
            {field: 'clientTerm',		title: '合同期限',	width: 38,hidden:true },
			{field: 'createId',		title: '录入人',	width: 120 },
			{field: 'createDate',	title: '录入时间',	width: 125 },
			{field: 'editId',		title: '修改人',	width: 120 },
			{field: 'editDate',		title: '修改时间',	width: 125 },
			{field: 'activeFlag',	title: '是否合作',	width: 120 ,formatter:function(value,rowData,rowIndex){
                    return rowData.activeFlag == '1' ? '是' : '否';
                } }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/basCarrierLicenseController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});


	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
        fit:true,
        cache: false,
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

    $("#activeFlag").combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });

});
var add = function(){
	processType = 'add';
	$('#basCarrierLicenseId').val(0);
	ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		/*ezuiForm.form('load',{
			carrierLicenseId : row.carrierLicenseId,
            enterpriseId : row.enterpriseId,
			roadNumber : row.roadNumber,
			roadNumberTerm : row.roadNumberTerm,
			roadAuthorityPermit : row.roadAuthorityPermit,
			roadBusinessScope : row.roadBusinessScope,
			carrierNo : row.carrierNo,
			carrierDate : row.carrierDate,
			carrierEndDate : row.carrierEndDate,
			carrierAuthorityPermit : row.carrierAuthorityPermit,
			carrierBusinessScope : row.carrierBusinessScope,
			createId : row.createId,
			createDate : row.createDate,
			editId : row.editId,
			editDate : row.editDate,
			activeFlag : row.activeFlag
		});*/
	/*	ezuiDialog.dialog('open');*/

        $('#ezuiDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            buttons : '#ezuiDialogBtn',
            fit:true,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('refresh', dialogUrl+"&enterpriseId="+row.enterpriseId+"&activeFlag="+row.activeFlag);
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
					url : 'basCarrierLicenseController.do?delete',
					data : {id : row.carrierLicenseId},
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
    var a =0;
    var b=0;
    var gspEnterpriceFrom = new Object();
    var infoObj = new Object();
    var businessObj = new Object();
   // var clientObj = new Object();ezuiDatagrid.datagrid('getSelected');
	// var row = ezuiBussinessDatagridDetail.datagrid('getSelected');
    $("#ezuiFormInfo input[class='textbox-value'][type='hidden']").each(function (index) {

        infoObj[""+$(this).attr("name")+""] = $(this).val();

    });
    $("#ezuiFormBusiness input[class='textbox-value'][type!='file']").each(function (index) {
        businessObj[""+$(this).attr("name")+""] = $(this).val();
        // var a =0;
        // var b=0;
        if($(this).val()!=""){
			a++;
		}
		b++;
    });

	//快递许可证
	if($("#carrierDate").datebox("getValue")!="" || $("#carrierEndDate").datebox("getValue")!=""){
		if(judgeDate($("#carrierDate").datebox("getValue"))){
			checkResult = false;
			showMsg("快递许可证发证日期不能超过当前时间");
			return;
		}

		if(!judgeDate($("#carrierEndDate").datebox("getValue"))){
			checkResult = false;
			showMsg("快递许可证发证日期有效期不能小于当前时间");
			return;
		}
	}
	console.log("判断合同结束时间");
	console.log($("#clientEndDate").datebox("getValue"));
	console.log(judgeDate2($("#clientEndDate").datebox("getValue"),$("#clientStartDate").datebox("getValue")));
	//合同
	if(judgeDate2($("#clientEndDate").datebox("getValue"),$("#clientStartDate").datebox("getValue"))){
		checkResult = false;
		showMsg("合同结束时间不能小于开始时间");
		return;
	}

	if(b>a){
		//部分填了

        $.messager.show({
            msg : "营业执照填写不完全", title : '<spring:message code="common.message.prompt"/>'
        });
        // alert("部分填了");
        return;
	}

	if(a>0){
        if(judgeDate($("#ezuiFormBusiness #establishmentDate").datebox("getValue"))){
            checkResult = false;
            showMsg("营业许可证成立日期不能超过当前时间");
            return;
        }
        if(judgeDate($("#ezuiFormBusiness #issueDate").datebox("getValue"))){
            checkResult = false;
            showMsg("营业许可证发证日期不能超过当前时间");
            return;
        }

        if(judgeDate($("#ezuiFormBusiness #businessStartDate").datebox("getValue"))){
            checkResult = false;
            showMsg("营业执照营业起始时间不能超过当前时间");
            return;
        }

        if(!judgeDate($("#ezuiFormBusiness #businessEndDate").datebox("getValue"))){
            checkResult = false;
            showMsg("营业执照营业结束时间不能小于当前时间");
            return;
        }
	}



    businessObj["businessId"] =$("#ezuiFormBusiness input[id='businessId']").val();
   /* $("#ezuiFormClient input[class='textbox-value'][type!='file']").each(function (index) {
        clientObj[""+$(this).attr("name")+""] = $(this).val();
    });*/

    gspEnterpriceFrom["BasCarrierLicenseForm"] = infoObj;
    gspEnterpriceFrom["gspBusinessLicenseForm"] = businessObj;
   // gspEnterpriceFrom["clientForm"] = clientObj;

    var url = '';
    if (processType == 'edit') {
        url = sy.bp()+"/basCarrierLicenseController.do?edit";
    }else{
        url = sy.bp()+"/basCarrierLicenseController.do?add";
    }
    /*var enterpriceId = "";
    var row = ezuiDatagrid.datagrid('getSelected');
    if(row){
        enterpriceId = row.enterpriseId;
    }*/
    //验证字段
    if($("#ezuiFormInfo").form('validate')){
        $.messager.progress({
            text : '<spring:message code="common.message.data.processing"/>', interval : 100
        });
        $.ajax({
            url : url,
            data : {"basCarrierLicenseFormstr":JSON.stringify(gspEnterpriceFrom)},type : 'POST', dataType : 'JSON',async  :true,
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
    }else{
        return false;
    }

};

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		carrierLicenseId : $('#carrierLicenseId').val(),
		enterpriseId : $('#enterpriseId').val(),
		roadNumber : $('#roadNumber').val(),
		roadNumberTerm : $('#roadNumberTerm').val(),
		roadAuthorityPermit : $('#roadAuthorityPermit').val(),
		roadBusinessScope : $('#roadBusinessScope').val(),
		carrierNo : $('#carrierNo').val(),
		carrierDate : $('#carrierDate').val(),
		carrierEndDate : $('#carrierEndDate').val(),
		clientTerm : $('#clientTerm').val(),
		carrierAuthorityPermit : $('#carrierAuthorityPermit').val(),
		carrierBusinessScope : $('#carrierBusinessScope').val(),
		createId : $('#createId').val(),
        createDateBegin : $('#createDateBegin').datebox("getValue"),
		createDateEnd : $('#createDateEnd').datebox("getValue"),
		editId : $('#editId').val(),
        editDateBegin : $('#editDateBegin').datebox("getValue"),
		editDateEnd : $('#editDateEnd').datebox("getValue"),
		activeFlag : $('#activeFlag').combobox("getValue"),
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
					<table style="text-align: right">
						<tr>

							<th>道路运营许可证编号</th><td><input type='text' id='roadNumber' class='easyui-textbox' size='16' data-options=''/></td>
							<th>是否合作</th>
							<td>
								<input id="activeFlag" class="easyui-combobox" size="16" >
							</td>

							<%--<th>道路运输经营许可证证件有效期：</th><td><input type='text' id='roadNumberTerm' class='easyui-textbox' size='4' data-options=''/></td>
							<th>道路运输经营许可证核发机关：</th><td><input type='text' id='roadAuthorityPermit' class='easyui-textbox' size='4' data-options=''/></td>
							<th>道路运输经营许可证经营范围：</th><td><input type='text' id='roadBusinessScope' class='easyui-textbox' size='4' data-options=''/></td>
							<th>快递业务经营许可证发证日期：</th><td><input type='text' id='carrierDate' class='easyui-textbox' size='4' data-options=''/></td>
							<th>快递业务经营许可证有效期至：</th><td><input type='text' id='carrierEndDate' class='easyui-textbox' size='4' data-options=''/></td>
							<th>快递业务经营许可证发证机关：</th><td><input type='text' id='carrierAuthorityPermit' class='easyui-textbox' size='4' data-options=''/></td>
							<th>快递业务经营许可证业务范围：</th><td><input type='text' id='carrierBusinessScope' class='easyui-textbox' size='4' data-options=''/></td>--%>
							<th>修改时间</th><td><input type='text' id='editDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='editDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>快递经营许可证编号</th><td><input type='text' id='carrierNo' class='easyui-textbox' size='16' data-options=''/></td>
							<%--<th>修改人</th><td><input type='text' id='editId' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>录入人</th><td><input type='text' id='createId' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<th>录入时间</th><td><input type='text' id='createDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
						<%--<th>是否有效：</th><td><input type='text' id='activeFlag' class='easyui-textbox' size='4' data-options=''/></td>--%>

							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<%--<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.update'/></a>--%>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<%--<form id='ezuiForm' method='post'>
			<input type='hidden' id='basCarrierLicenseId' name='basCarrierLicenseId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='carrierLicenseId' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入1</th>
					<td><input type='text' name='roadNumber' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入2</th>
					<td><input type='text' name='roadNumberTerm' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入3</th>
					<td><input type='text' name='roadAuthorityPermit' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入4</th>
					<td><input type='text' name='roadBusinessScope' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入5</th>
					<td><input type='text' name='carrierNo' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='carrierDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入7</th>
					<td><input type='text' name='carrierEndDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入8</th>
					<td><input type='text' name='clientTerm' class='easyui-numberbox' size='16' data-options='required:true,min:0,max:100'/></td>
				</tr>
				<tr>
					<th>待输入9</th>
					<td><input type='text' name='carrierAuthorityPermit' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入10</th>
					<td><input type='text' name='carrierBusinessScope' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入11</th>
					<td><input type='text' name='createId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='createDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入13</th>
					<td><input type='text' name='editId' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入14</th>
					<td><input type='text' name='editDate' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入15</th>
					<td><input type='text' name='activeFlag' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>--%>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.update'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
