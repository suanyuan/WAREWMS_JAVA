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
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/waybillStatisticsController.do?showDatagrid"/>',
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
		idField : 'id',
		columns : [[
			// {field: 'enterpriseId',		title: '待输入栏位0',	width: 36 },
			{field: 'carrierName',		title: '承运商名称',	width: 136 },
			{field: 'year',		title: '年',	width: 50 },
			{field: 'month',		title: '月',	width: 50 },
			{field: 'day',		title: '日',	width: 50 },
			{field: 'orderNum',		title: '发运单数',	width: 100 },
			{field: 'complaintNum',		title: '投诉单数',	width: 100 },
			{field: 'missingNum',		title: '丢件',	width: 100 },
			{field: 'damageNum',		title: '破损',	width: 100 },
			{field: 'lagNum',		title: '到件延迟',	width: 100 },
			{field: 'otherNum',		title: '其他',	width: 100 },
			{field: 'remark',		title: '详情说明',	width: 100 },
			{field: 'createDate',		title: '创建时间',	width: 150 },
			{field: 'createId',		title: '创建人',	width: 100 },
			{field: 'editDate',		title: '编辑时间',	width: 150 },
			{field: 'editId',		title: '编辑人',	width: 100 },
			// {field: 'userdefind1',		title: '待输入栏位16',	width: 36 },
			// {field: 'userdefind2',		title: '待输入栏位17',	width: 36 },
			// {field: 'userdefind3',		title: '待输入栏位18',	width: 36 },
			// {field: 'userdefind4',		title: '待输入栏位19',	width: 36 },
			// {field: 'userdefind5',		title: '待输入栏位20',	width: 36 }
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
			ajaxBtn($('#menuId').val(), '<c:url value="/waybillStatisticsController.do?getBtn"/>', ezuiMenu);
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




    $("#carrierNameQ").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/basCarrierLicenseController.do?getCombobox',
        valueField:'value',
        textField:'value',
        // size:'',
        width:145
    });
});
var add = function(){
	processType = 'add';
	$('#waybillStatisticsId').val(0);
	ezuiDialog.dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			waybillStatisticsId : row.id,
			enterpriseId : row.enterpriseId,
			carrierName : row.carrierName,
			year : row.year,
			month : row.month,
			day : row.day,
			orderNum : row.orderNum,
			complaintNum : row.complaintNum,
			missingNum : row.missingNum,
			damageNum : row.damageNum,
			lagNum : row.lagNum,
			otherNum : row.otherNum,
			remark : row.remark,
			createDate : row.createDate,
			createId : row.createId,
			editDate : row.editDate,
			editId : row.editId,
			userdefind1 : row.userdefind1,
			userdefind2 : row.userdefind2,
			userdefind3 : row.userdefind3,
			userdefind4 : row.userdefind4,
			userdefind5 : row.userdefind5
		});
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
					url : 'waybillStatisticsController.do?delete',
					data : {id : row.id},
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
		url = '<c:url value="/waybillStatisticsController.do?edit"/>';
	}else{
		url = '<c:url value="/waybillStatisticsController.do?add"/>';
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
		// enterpriseId : $('#enterpriseIdQ').val(),
		carrierName : $('#carrierNameQ').combobox("getValue"),
		year : $('#yearQ').val(),
		month : $('#monthQ').val(),
		// day : $('#day').val(),
		// orderNum : $('#orderNum').val(),
		// complaintNum : $('#complaintNum').val(),
		// missingNum : $('#missingNum').val(),
		// damageNum : $('#damageNum').val(),
		// lagNum : $('#lagNum').val(),
		// otherNum : $('#otherNum').val(),
		// remark : $('#remark').val(),
		// createDate : $('#createDate').val(),
		// createId : $('#createId').val(),
		// editDate : $('#editDate').val(),
		// editId : $('#editId').val(),
		// userdefind1 : $('#userdefind1').val(),
		// userdefind2 : $('#userdefind2').val(),
		// userdefind3 : $('#userdefind3').val(),
		// userdefind4 : $('#userdefind4').val(),
		// userdefind5 : $('#userdefind5').val()
	});
};



/* 导出库内货品start */
var doExport = function(){
    var row = ezuiDatagrid.datagrid('getSelected');

    if(navigator.cookieEnabled){
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        // param.put("idList", idlist1);

        param.put("carrierName", $('#carrierNameQ').combobox("getValue"));
        param.put("year", $('#yearQ').val());
        param.put("month",$('#monthQ').val());



        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/waybillStatisticsController.do?exportWaybillStatisticsDataToExcel", param);
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
							<th>承运商名称：</th><td><input type='text' id='carrierNameQ' class='easyui-textbox' size='16' data-options=''/></td>
							<th>年：</th><td><input type='text' id='yearQ' class='easyui-textbox' size='16' data-options=''/></td>
							<th>月：</th><td><input type='text' id='monthQ' class='easyui-textbox' size='16' data-options=''/></td>
							<%--<th>待输入名称5：</th><td><input type='text' id='day' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<%--<th>待输入名称6：</th><td><input type='text' id='orderNum' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                                <a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>


                            </td>
						</tr>
					</table>
				</fieldset>
				<div>
					<%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
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
			<input type='hidden' id='waybillStatisticsId' name='waybillStatisticsId'/>
			<table>
				<%--<tr>--%>
					<%--<th>承运商名称</th>--%>
					<%--<td><input type='text' name='enterpriseId' class='easyui-textbox' size='16' data-options='required:false'/></td>--%>
				<%--</tr>--%>
				<tr>
					<th>承运商名称</th>
					<td><input type='text' name='carrierName' class='easyui-textbox' size='16' data-options='required:false,editable:false'/></td>
				</tr>
				<tr>
					<th>年</th>
					<td><input type='text' name='year' class='easyui-textbox' size='16' data-options='required:false,editable:false'/></td>
				</tr>
				<tr>
					<th>月</th>
					<td><input type='text' name='month' class='easyui-textbox' size='16' data-options='required:false,editable:false'/></td>
				</tr>
				<tr>
					<th>日</th>
					<td><input type='text' name='day' class='easyui-textbox' size='16' data-options='required:false,editable:false'/></td>
				</tr>
				<tr>
					<th>发运单数</th>
					<td><input type='text' name='orderNum' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>投诉单数</th>
					<td><input type='text' name='complaintNum' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>丢件</th>
					<td><input type='text' name='missingNum' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>破损</th>
					<td><input type='text' name='damageNum' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>到件延迟</th>
					<td><input type='text' name='lagNum' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>其他</th>
					<td><input type='text' name='otherNum' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>详情说明</th>
					<td><input type='text' name='remark' class='easyui-textbox' size='16' data-options='required:false'/></td>
				</tr>
				<%--<tr>--%>
					<%--<th>待输入12</th>--%>
					<%--<td><input type='text' name='createDate' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<th>待输入13</th>--%>
					<%--<td><input type='text' name='createId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<th>待输入14</th>--%>
					<%--<td><input type='text' name='editDate' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
				<%--</tr>--%>
				<%--<tr>--%>
					<%--<th>待输入15</th>--%>
					<%--<td><input type='text' name='editId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
				<%--</tr>--%>

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
</body>
</html>
