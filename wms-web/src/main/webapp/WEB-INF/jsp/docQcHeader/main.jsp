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
var ezuiMenu;                 //右键菜单
var ezuiForm;                 //一级dialog form
var ezuiDialog;               //一级dialog
var ezuiAcceptanceForm;       //验收作业dialog form
var ezuiAcceptanceDialog;     //验收作业dialog
var ezuiDatagrid;              //主页datagrid
var ezuiAccDataDialog;         //验收单号选择框
var ezuiAccDataDialogId;       //验收单号选择框
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
	ezuiAcceptanceForm = $('#ezuiAcceptanceForm').form();   //验收作业dialog form
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docQcDetailsController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '待输入标题',
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
		singleSelect:false,
		columns : [[
			{field:'ck',checkbox:true},
			// {field: 'qcno',		title: '验收单号',	width: 124 },
			// {field: 'pano',		title: '上架单号',	width: 124 },
            {field: 'qclineno',		title: '验收行号',	width: 80 },
			{field: 'linestatus',		title: '行状态',	width: 80,formatter:AcceptancestatusFormatter},
			{field: 'qcdescr',		title: '验收说明',	width: 160},
			{field: 'userdefine1',		title: '库位',	width: 110 },
			{field: 'customerid',		title: '货主代码',	width: 134 },
			{field: 'sku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12',		title: '产品名称',	width: 200 },
			{field: 'qcqtyExpected',		title: '待验件数',	width: 100 },
			{field: 'qcqtyCompleted',		title: '已验件数',	width: 100},
			{field: 'lotatt10',		title: '质量状态',	width: 80,formatter:ZL_TYPstatusFormatter },
			{field: 'lotatt04',		title: '生产批号',	width: 110 },
			{field: 'lotatt05',		title: '序列号',	width: 110 },
			{field: 'lotatt07',		title: '灭菌批号',	width: 110 },
			{field: 'lotatt15',		title: '生产企业',	width: 250 },
			{field: 'lotatt01',		title: '生产日期',	width: 100 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 100 },
			{field: 'lotatt06',		title: '注册证号',	width: 200 },
			{field: 'lotatt11',		title: '存储条件',	width: 100 },
			{field: 'lotnum',		title: '批次号',	width: 134 },
			{field: 'addtime',		title: '创建时间',	width: 134 },
			{field: 'addwho',		title: '创建人',	width: 100 },
			{field: 'edittime',		title: '编辑时间',	width: 134 },
			{field: 'editwho',		title: '编辑人',	width: 100 }
		]],
		onDblClickCell: function(index,field,value){

		},
	     onLoadSuccess:function(data){
			<%--ajaxBtn($('#menuId').val(), '<c:url value="/docQcHeaderController.do?getBtn"/>', ezuiMenu);--%>
			$(this).datagrid('unselectAll');
		}
	});
//验收单号放大镜初始化
	$("#qcno").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiAccDataDialog #pano").textbox('clear');
				$("#ezuiAccDataDialog #qcno").textbox('clear');
				ezuiAccDataClick();
				ezuiAccDataDialogSearch();
			}
		}]
	});
//一级dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
//验收作业dialog
	ezuiAcceptanceDialog = $('#ezuiAcceptanceDialog').dialog({
		modal : true,
		width:260,
		height:180,
		title : '验收作业',
		buttons : '#ezuiAcceptanceDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiAcceptanceForm);
		}
	}).dialog('close');
//验收单号选择弹框
	ezuiAccDataDialog = $('#ezuiAccDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
});
//增加
var add = function(){
	processType = 'add';
	$('#docQcHeaderId').val(0);
	ezuiDialog.dialog('open');
};
//修改
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			qcno : row.qcno,
			pano : row.pano,
			customerid : row.customerid,
			qcreference1 : row.qcreference1,
			qcreference2 : row.qcreference2,
			qcreference3 : row.qcreference3,
			qcreference4 : row.qcreference4,
			qcreference5 : row.qcreference5,
			qctype : row.qctype,
			qcstatus : row.qcstatus,
			qccreationtime : row.qccreationtime,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			notes : row.notes,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			qcPrintFlag : row.qcPrintFlag,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//删除
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docQcHeaderController.do?delete',
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
//一级dialog提交
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docQcHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docQcHeaderController.do?add"/>';
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
//验收作业提交
var commitAcceptance = function(type){
	var typeC=type; //是否合格
	url = '<c:url value="/docQcDetailsController.do?submitDocQcList"/>';
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	var isCan=true;//是否可以验收
	for (var i = 0; i < rows.length; i++) {
		 var linestatus=rows[i].linestatus;
		 //判断细单验收状态
		 if(linestatus!='00'){
			 msg="验收行号:"+rows[i].qclineno+" ,已验收不可重复验收!"
			isCan=false;
		 	break;
		 }
		data=new Object();
		data.qcno=rows[i].qcno;              //验收单号
		data.qclineno=rows[i].qclineno;      //验收行号
		data.lotatt02=rows[i].userdefine2;  //效期
		data.lotatt04=rows[i].userdefine3;  //生产批号
		data.lotnum=rows[i].lotnum;
		data.allqcflag=0;
		data.lotatt10=typeC;                //合格 不合格
		//判断验收的行数
		if(rows.length==1){
			var qcqtyNum=$('#ezuiAcceptanceForm #qcqty').textbox('getValue');
		    data.qcqty=qcqtyNum;
		}else{
			var qcqtyNumAll=rows[i].qcqtyExpected;
			data.qcqty=qcqtyNumAll;
		}
		data.qcdescr=$('#ezuiAcceptanceForm #qcdescr').combobox('getText');  //验收描述
		forms.push(data);

	}
    if(!isCan){
		$.messager.show({
			msg : msg, title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	if(ezuiAcceptanceForm.form('validate')) {
		$.messager.progress({
			text: '<spring:message code="common.message.data.processing"/>', interval: 100
		});

			$.ajax({
				url:url,
				data:"forms="+JSON.stringify(forms),
				dataType: 'json',
				error: function () {

				},
				success: function (result) {
					try{
						if(result.success){
							msg=result.msg;
						 	ezuiDatagrid.datagrid('reload');
							ezuiAcceptanceDialog.dialog('close');
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');
						 }else{
							msg=result.msg;
							ezuiDatagrid.datagrid('reload');
							ezuiAcceptanceDialog.dialog('close');
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							$.messager.progress('close');

						}
					}catch (e) {
						$.messager.show({
							msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
					}
				}
			});
	}else{
		msg = '<font color="red">' +'请输入完整!'+ '</font>';
		$.messager.show({
			msg : msg, title : '<spring:message code="common.message.prompt"/>'
		});
		$.messager.progress('close');

	}

};
//验收作业
var acceptanceWork = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var num=rows.length;
    if(num==1){
		 $("#ezuiAcceptanceForm #qcqtyExpected").textbox('enable');
		 $("#ezuiAcceptanceForm #qcqty").textbox('enable');
		ezuiAcceptanceForm.form('load',{
			qcqtyExpected:rows[0].qcqtyExpected

		});
		ezuiAcceptanceDialog.dialog('open');
	}else if(num>1){
		 $("#ezuiAcceptanceForm #qcqtyExpected").textbox('disable');
           $("#ezuiAcceptanceForm #qcqty").textbox('disable');
		$("#ezuiAcceptanceForm #qcqtyExpected").textbox('setValue','所有');
		$("#ezuiAcceptanceForm #qcqty").textbox('setValue','所有');
		ezuiAcceptanceDialog.dialog('open');
	}
    else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//主页查询
var doSearch = function() {
	if ($('#qcno').val() == null || $('#qcno').val() == "") {
		ezuiDatagrid.datagrid('load', {
			qcno: $('#qcno').val(),                            //验收单号
			userdefine3: $('#userdefine3').val(),              //生产批号
			userdefine4: $('#userdefine4').val(),              //序列号
			linestatus: $('#linestatus').combobox('getValue')  //验收状态
		});
		$.messager.show({
			msg: '请先选择验收单号!', title: '<spring:message code="common.message.prompt"/>'
		});
	} else {
		ezuiDatagrid.datagrid('load', {
			qcno: $('#qcno').val(),                            //验收单号
			userdefine3: $('#userdefine3').val(),              //生产批号
			userdefine4: $('#userdefine4').val(),              //序列号
			linestatus: $('#linestatus').combobox('getValue')  //验收状态
		});

	}
}

/* 库位选择弹框查询 */
var ezuiAccDataDialogSearch = function () {
	ezuiAccDataDialogId.datagrid('load', {
		pano: $("#ezuiAccDataDialog #pano").textbox("getValue"),
		qcno: $("#ezuiAccDataDialog #qcno").textbox("getValue")
	});
};
/* 库位选择弹框清空 */
var ezuiAccToolbarClear = function () {
	$("#ezuiAccDataDialog #pano").textbox('clear');
	$("#ezuiAccDataDialog #qcno").textbox('clear');
};
/* 库位选择弹框 */
var ezuiAccDataClick = function () {
	ezuiAccDataDialogId = $('#ezuiAccDataDialogId').datagrid({
		url: '<c:url value="/docQcHeaderController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiAccToolbar',
		title: '库位选择',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		columns: [[
			{field: 'pano', title: '上架单号', width: 80},
			{field: 'qcno', title: '验收单号', width: 100},
			{field: 'qcstatus', title: '验收状态', width: 100,formatter:AcceptancestatusFormatter}
		]],
		onDblClickCell: function (index, field, value) {
			selectAcceptance();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	ezuiAccDataDialog.dialog('open');
};
/* 库位选择 */
var selectAcceptance = function () {
	var row = ezuiAccDataDialogId.datagrid('getSelected');
	if (row) {
		// $("#toolbar #pano").textbox('setValue', row.pano);
		$("#toolbar #qcno").textbox('setValue', row.qcno);
		doSearch();
		ezuiAccDataDialog.dialog('close');
	}
	;
};
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页datagrid工具栏--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
<%--							<th>上架单号</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<th>验收单号</th><td><input type='text' id='qcno' class='easyui-textbox' size='16' data-options='editable: false'/></td>
							<th>验收状态</th><td><input type='text' id='linestatus' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '未验收'},
																																       {label: '40',
																																         value: '已验收'},
																																       {label: '',
																																         value: '全部'}]"/></td>

						</tr>
						<tr>
							<th>序列号</th><td><input type='text' id='userdefine4' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产批号</th><td><input type='text' id='userdefine3' class='easyui-textbox' size='16' data-options=''/></td>
							<td colspan="2">
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='acceptanceWork()' id='ezuiBtn_check' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>验收作业</a>
<%--					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
<%--					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
<%--					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--一级dialog--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='docQcHeaderId' name='docQcHeaderId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='qcno' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入1</th>
					<td><input type='text' name='pano' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入2</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入3</th>
					<td><input type='text' name='qcreference1' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入4</th>
					<td><input type='text' name='qcreference2' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入5</th>
					<td><input type='text' name='qcreference3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='qcreference4' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入7</th>
					<td><input type='text' name='qcreference5' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入8</th>
					<td><input type='text' name='qctype' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入9</th>
					<td><input type='text' name='qcstatus' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入10</th>
					<td><input type='text' name='qccreationtime' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入11</th>
					<td><input type='text' name='userdefine1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='userdefine2' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入13</th>
					<td><input type='text' name='userdefine3' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入14</th>
					<td><input type='text' name='userdefine4' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入15</th>
					<td><input type='text' name='userdefine5' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入16</th>
					<td><input type='text' name='notes' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>创建时间</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>创建人</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>编辑时间</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>编辑人</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入21</th>
					<td><input type='text' name='qcPrintFlag' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入22</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

<%--验收单号弹窗--%>
	<div id='ezuiAccDataDialog'  style="width:900px;height:480px;padding:10px 20px"   >
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiAccToolbar' class='datagrid-toolbar'   style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>上架单号</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>
								<th>验收单号</th><td><input type='text' id='qcno' class='easyui-textbox' size='16' data-options=''/></td>
								<td>
									<a onclick='ezuiAccDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectAcceptance();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiAccToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
					<div id='ezuiAccDialogBtn'> </div>
				</div>
				<table id='ezuiAccDataDialogId' ></table>
			</div>
		</div>
	</div>
	<div id='ezuiAccDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiAccDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--验收作业点击弹窗--%>
	<div id='ezuiAcceptanceDialog' style='padding: 10px;'>
		<form id='ezuiAcceptanceForm' method='post'>
			<table>

				<tr>
					<th>待验件数</th><td><input type='text' id='qcqtyExpected' name="qcqtyExpected" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

				</tr>
				<tr>
					<th>验收件数</th><td><input type='text' id='qcqty' name="qcqty" class='easyui-textbox' size='20' data-options="required:true"/></td>

				</tr>
				<tr>
					<th>验收说明</th>
					<td><input type='text' id='qcdescr' name="qcdescr" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',

							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '1',
																																        value: '未见异常，检查验收合格'},
																																       {label: '2',
																																         value: '近效期，包装外观未见异常'},
																																       {label: '3',
																																         value: '退货经检查验收，包装外观符合要求，可入库'},
																																       {label: '4',
																																         value: '包装损坏，产品经检验后合格，可入库'},
																																       {label: '5',
																																         value: '包装破损，不合格'},
																																       {label: '6',
																																         value: '无中文标签，不合格'},
																																       {label: '7',
																																         value: '无合格证明文件，不合格'},
																																       {label: '8',
																																         value: '产品经检验后判定不合格'},

																																         ]"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiAcceptanceDialogBtn'>
		<a onclick='commitAcceptance("HG");' class='easyui-linkbutton' href='javascript:void(0);'>合格</a>
		<a onclick='commitAcceptance("BHG");' class='easyui-linkbutton' href='javascript:void(0);'>不合格</a>
	</div>
</body>
</html>
