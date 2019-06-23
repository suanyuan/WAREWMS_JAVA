<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/jsp/include/meta.jsp" /> 
<c:import url="/WEB-INF/jsp/include/easyui.jsp" /> 

<script type="text/javascript">
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiTreegrid;
$(function() {
	$(document).ajaxComplete(function() {
		parent.menu.tree('reload');
	});
	
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiTreegrid = $('#ezuiTreegrid').treegrid({
		url : '<c:url value="/userController.do?showTreegrid"/>',
		toolbar:"#toolbar",
		title:"<spring:message code='menu.datagrid.title'/>",
		pagination:false,
		fit:true,
		border:false,
		fitColumns:true,
    	nowrap:true,  
    	striped:true, 
		rownumbers:true, 
		collapsible:false,
		idField : 'id',
		treeField : 'userName',
		columns : [[
			{field: 'userName',		title: "<spring:message code='adminUser.datagrid.userName'/>",	width: 120 },
			{field: 'gender',		title: "<spring:message code='adminUser.datagrid.gender'/>",	width: 40 ,
				formatter:function(value,rowData,rowIndex){
					if(value == "M"){
						return "<spring:message code='adminUser.datagrid.gender.m'/>";
					}else if(value == "F"){
						return "<spring:message code='adminUser.datagrid.gender.f'/>";
					}else{
						return "";
					}
                }
			},
			{field: 'enable',		title: "<spring:message code='adminUser.datagrid.enable'/>",	width: 60 ,
				formatter:function(value,rowData,rowIndex){
					if(value == 1){
						return "<spring:message code='adminUser.datagrid.enable.y'/>";
					}else if(value == 0){
						return "<spring:message code='adminUser.datagrid.enable.n'/>";
					}else{
						return "";
					}
                }
			},
			{field: 'birthday',		title: "<spring:message code='adminUser.datagrid.birthday'/>",	width: 100 },
			{field: 'country',	title: "<spring:message code='adminUser.datagrid.country'/>",		width: 80 ,
				formatter:function(value,rowData,rowIndex){
					if(value){
						return value.countryName;
					}else{
						return "";
					}
                }
			},
			{field: 'email',		title: "<spring:message code='adminUser.datagrid.email'/>",			width: 120 },
			{field: 'createTime',	title: "<spring:message code='adminUser.datagrid.createTime'/>",	width: 120 },
			{field: 'lastLoginTime',title: "<spring:message code='adminUser.datagrid.lastLoginTime'/>",	width: 120 },
			{field:	'roleSet',		title: "<spring:message code='adminUser.datagrid.roleList'/>",		width:70,
				formatter:function(value,rowData,rowIndex){
					var result = "";
					if(value){
						for(var i = 0; i < value.length; i++){
							result = result + value[i].roleName;
							if(i != (value.length-1)){
								result = result + "<br/>";
							}
	                    }
					}
                    return result;
                }
			},
			{field:	'warehouseSet',		title: "<spring:message code='adminUser.datagrid.warehouseList'/>",		width:70,
				formatter:function(value,rowData,rowIndex){
					var result = "";
					if(value){
						for(var i = 0; i < value.length; i++){
							result = result + value[i].warehouseName;
							if(i != (value.length-1)){
								result = result + "<br/>";
							}
	                    }
					}
                    return result;
                }
			},
			{field:	'customerSet',		title: "<spring:message code='adminUser.datagrid.customerList'/>",		width:70,
				formatter:function(value,rowData,rowIndex){
					var result = "";
					if(value){
						for(var i = 0; i < value.length; i++){
							result = result + value[i].customerName;
							if(i != (value.length-1)){
								result = result + "<br/>";
							}
	                    }
					}
                    return result;
                }
			}
	    ]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		onContextMenu : function(e, row) {
			 e.preventDefault();  
			 $(this).treegrid('unselectAll');
			 $(this).treegrid('select', row.id);
			 ezuiMenu.menu('show', {  
                left: e.pageX,  
                top: e.pageY  
            });
		},
		onLoadSuccess:function(data){ 
			ajaxBtn($('#menuId').val(), '<c:url value="/userController.do?getBtn"/>', ezuiMenu);
			ezuiTreegrid.treegrid('expandAll');
		}
	});

	$('#warehouseCombobox').combobox({
		onChange: function (newVal,oldVal) {
			if (newVal != '') {
				$("#defaultWarehouseCombobox").combobox({
					url:'userController.do?getDefaultCombobox&userId='+$('#userId').val()+'&warehouse='+newVal,
					valueField: 'id',
					textField: 'value'
				});
			} else {
				$("#defaultWarehouseCombobox").combobox('clear');
			}
		}
    });

	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : "<spring:message code='adminUser.easyuiDialog.title'/>",
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		},
		onOpen : function(){
			$('#userId').textbox('readonly', processType == 'edit');
		}
	}).dialog('close');
});

var add = function(){
	processType = 'add';
	ezuiDialog.dialog('open');
};

var edit = function(){
	processType = 'edit';
	var row = ezuiTreegrid.treegrid('getSelected');
	if(row){
		if(row.userType == 0){
			$('#enableCombobox').combobox('disable');
		    $('#genderCombobox').combobox('disable');
		    $('#countryCombobox').combobox('disable');
		    $('#roleCombobox').combobox('disable');
		    $('#warehouseCombobox').combobox('disable');
		    $('#customerCombobox').combobox('disable');
			$('#birthday').datebox('disable');
			$('#email').textbox('disable');
			ezuiForm.form('load',{
				userId : row.id,
				userName : row.userName,
				userType : row.userType
			});
		}else{
			$('#enableCombobox').combobox('enable');
			$('#genderCombobox').combobox('enable');
			$('#countryCombobox').combobox('enable');
			$('#roleCombobox').combobox('enable');
			$('#warehouseCombobox').combobox('enable');
		    $('#customerCombobox').combobox('enable');
			$('#birthday').datebox('enable');
			$('#email').textbox('enable');
			ezuiForm.form('load',{
				userId : row.id,
				userName : row.userName,
				userType : row.userType,
				gender : row.gender,
				enable : row.enable,
				birthday : row.birthday,
				countryId : row.country.id,
				email : row.email
			});
			var role = row.roleSet;
			if(role){
				var roleArray = [];
				for(var i = 0; i < role.length; i++){
					roleArray[i] = role[i].id;
			    }
				$('#roleCombobox').combobox('setValues',roleArray);
			}
			var warehouse = row.warehouseSet;
			if(warehouse){
				var warehouseArray = [];
				for(var i = 0; i < warehouse.length; i++){
					warehouseArray[i] = warehouse[i].id;
			    }
				$('#warehouseCombobox').combobox('setValues',warehouseArray);
			}
			var customer = row.customerSet;
			if(customer){
				var customerArray = [];
				for(var i = 0; i < customer.length; i++){
					customerArray[i] = customer[i].id;
			    }
				$('#customerCombobox').combobox('setValues',customerArray);
			}
		}
		$('#parentNodeId').combotree('setValue', row.parent ? row.parent.parentNodeId : "");
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : "<spring:message code='common.message.selectRecord'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};

var del = function(){
	var row = ezuiTreegrid.treegrid('getSelected');
	if(row){
		if(row.userType == 0){
			$.messager.confirm("<spring:message code='common.message.confirm'/>", "<spring:message code='common.message.confirm.delete'/>", function(confirm) {
				if(confirm){
					$.ajax({
						url : '<c:url value="userController.do?delete"/>',
						data : {id : row.id},
						type : 'POST', 
						dataType : 'JSON',
						success : function(result){
							var msg = "";
							try {
								msg = result.msg;
							} catch (e) {
								msg = "<spring:message code='common.message.data.delete.failed'/>";
							} finally {
								$.messager.show({
									msg : msg, title : "<spring:message code='common.message.prompt'/>"
								});
								ezuiTreegrid.treegrid('reload');
							}
						}
					});
				}
			});
		}else{
			$.messager.show({
				msg : "無法刪除使用者！", title : "<spring:message code='common.message.prompt'/>"
			});
		}
	}else{
		$.messager.show({
			msg : "<spring:message code='common.message.selectRecord'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};

var commit = function(){
	var url = "";
	if (processType == "edit") {
		url = '<c:url value="/userController.do?edit"/>';
	}else{
		url = '<c:url value="/userController.do?add"/>';
	}

	ezuiForm.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiForm.form('validate')){
				$.messager.progress({
					text : "<spring:message code='common.message.data.processing'/>", interval : 100
				});
				return true;
			}else{
				return false;
			}
		},
		success : function(data) {
			try {
				var result = $.parseJSON(data);
				if(result.success){
					if($('#userId').val() == "${sessionScope.userInfo.id}"){
						parent.$('#userInfo').text("<spring:message code='adminUser.text.hello'/>["+$('#userName').val()+"]");
					}
					ezuiTreegrid.treegrid('reload');
					ezuiDialog.dialog('close');
				}
				$.messager.show({
					msg : result.msg, title : "<spring:message code='common.message.prompt'/>"
				});
			} catch (e) {
				$.messager.show({
					msg : "<spring:message code='common.message.data.process.failed'/><br/><br/>"+ data, title : "<spring:message code='common.message.prompt'/>"
				});
			} finally {
				$.messager.progress('close');
			}
		}
	});
};

var clearParentName = function(){
	$('#parentNodeId').combotree('clear');
};
</script>

</head>
<body>
	<input type="hidden" id="menuId" name="menuId" value="${menuId}"/>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options='region:"center",border:false' style="overflow: hidden;">
			<div id="toolbar" class="datagrid-toolbar" style="padding: 5px;">
				<div>
					<a onclick="add();" 	id="ezuiBtn_add"	class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-add"'		href="javascript:void(0);"><spring:message code="common.button.add"/></a>
					<a onclick="edit();" 	id="ezuiBtn_edit"	class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-edit"'		href="javascript:void(0);"><spring:message code="common.button.edit"/></a>
					<a onclick="clearTreegridSelected('#ezuiTreegrid');" class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-undo"'	href="javascript:void(0);"><spring:message code="common.button.cancelSelect"/></a>
				</div>
			</div>
			
			<table id="ezuiTreegrid"></table> 
		</div>
	</div>
	
	<div id="ezuiDialog" style="padding: 10px;">
		<form id="ezuiForm" method="post">
			<table>
				<tr>
					<th>類型</th>
					<td>
						<input type="text" id="userTypeCombobox" name="userType" class="easyui-combobox" size='16' data-options="	panelHeight:'auto',
																																	editable:false,
																																	required:true,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																		{id: '0', value: '部門'},
																																		{id: '1', value: '員工'}
																																	],
																																	onSelect : function(record){
																																		if(record.id == 0){
																																		    $('#enableCombobox').combobox('disable');
																																		    $('#genderCombobox').combobox('disable');
																																		    $('#countryCombobox').combobox('disable');
																																		    $('#roleCombobox').combobox('disable');
																																		    $('#warehouseCombobox').combobox('disable');
																																		    $('#customerCombobox').combobox('disable');
																																			$('#birthday').datebox('disable');
																																			$('#email').textbox('disable');
																																		}else{
																																			$('#enableCombobox').combobox('enable');
																																			$('#genderCombobox').combobox('enable');
																																			$('#countryCombobox').combobox('enable');
																																			$('#roleCombobox').combobox('enable');
																																		    $('#warehouseCombobox').combobox('enable');
																																		    $('#customerCombobox').combobox('enable');
																																			$('#birthday').datebox('enable');
																																			$('#email').textbox('enable');
																																		}
																																	}"/> 
				 	</td>
					<th><spring:message code='adminUser.datagrid.id'/></th>
					<td><input type="text" id="userId" name="userId" class="easyui-textbox" size='16' data-options='required:true'/></td>
					<th><spring:message code='adminUser.datagrid.userName'/></th>
					<td><input type="text" id="userName" name="userName" class="easyui-textbox" size='16' data-options='required:true'/></td>
					<th><spring:message code='adminUser.datagrid.country'/></th>
					<td>
						<input type="text" id="countryCombobox" name="countryId" class="easyui-combobox" size='16' data-options="	required:true,
																																	url:'<c:url value="/countryController.do?getCountryCombobox"/>',
																																	valueField:'id',
																																	textField:'value'
																																 "/>  
					</td>
				</tr>
				<tr>
					<th><spring:message code='adminUser.datagrid.gender'/></th>
					<td>
						<input type="text" id="genderCombobox" name="gender" class="easyui-combobox" size='16' data-options="	panelHeight:'auto',
																																editable:false,
																																required:true,
																																valueField: 'id',
																																textField: 'value',
																																data: [
																																	{id: 'M', value: '<spring:message code="adminUser.datagrid.gender.m"/>'},
																																	{id: 'F', value: '<spring:message code="adminUser.datagrid.gender.f"/>'}
																																]"/>  
					</td>
					<th><spring:message code='adminUser.datagrid.birthday'/></th>
					<td><input type="text" id="birthday" name="birthday" class="easyui-datebox" size='16' data-options='required:true' size="15"/></td>
					<th><spring:message code='adminUser.datagrid.email'/></th>
					<td colspan="3"><input type="text" id="email" name="email" class="easyui-textbox" size='42' data-options='required:true,validType:"email"'/></td>
				</tr>
				<tr>
					<th><spring:message code='adminUser.datagrid.enable'/></th>
					<td>
						<input type="text" id="enableCombobox" class="easyui-combobox" name="enable" size='16' data-options="	panelHeight:'auto',
																																editable:false,
																																required:true,
																																valueField: 'id',
																																textField: 'value',
																																data: [
																																	{id: '0', value: '<spring:message code='adminUser.datagrid.enable.n'/>'},
																																	{id: '1', value: '<spring:message code='adminUser.datagrid.enable.y'/>'}
																																]"/>  
					</td>
					<th><spring:message code="menu.datagrid.parent"/></th>
					<td colspan="3">
						<input type="text" id="parentNodeId" name="parentNodeId" class="easyui-combotree" size='16' data-options=" 	url : '<c:url value="/userController.do?getTree"/>',
																																animate : true,
																																lines : true,
																																onLoadSuccess : function(node, data) {
																																	var t = $(this);
																																	if (data) {
																																		$(data).each(function(index, d) {
																																			if (this.state == 'closed') {
																																				t.tree('expandAll');
																																			}
																																		});
																																	}
																																}">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-remove"' onclick="clearParentName();">
							<spring:message code="common.button.clear"/>
						</a>
					</td>
				</tr>
				<tr>
					<th><spring:message code='adminUser.datagrid.roleList'/></th>
					<td colspan="7">
						<input type="text" id="roleCombobox" name="role" class="easyui-combobox" size='96' data-options="	required:true,
																															panelHeight:'auto',
																															multiple:'multiple',
																															editable:false,
																															url:'<c:url value="/roleController.do?getCombobox"/>',
																															valueField:'id',  
																															textField:'value'" />  
					</td>
				</tr>
				<tr>
					<th><spring:message code='adminUser.datagrid.warehouseList'/></th>
					<td colspan="5">
						<input type="text" id="warehouseCombobox" name="warehouse" class="easyui-combobox" size='70' data-options="	required:true,
																															panelHeight:'auto',
																															multiple:'multiple',
																															editable:false,
																															url:'<c:url value="/userController.do?getCombobox"/>',
																															valueField:'id',  
																															textField:'value'" />  
					</td>
					<th><spring:message code='adminUser.datagrid.defaultWwarehouse'/></th>
					<td>
						<input type="text" id="defaultWarehouseCombobox" name="defaultWarehouse" class="easyui-combobox" size='16' data-options="	required:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField:'id',  
																															textField:'value'" />  
					</td>
				</tr>
				<tr>
					<th><spring:message code="adminUser.datagrid.customerList"/></th>
					<td colspan="5">
						<input type="text" id="customerCombobox" name="customer" class="easyui-combobox" size='70' style="height:60px" data-options=" panelHeight:'auto',
																																					multiple:'multiple',
																																					multiline:true,
																																					editable:false,
																																				    url:'<c:url value="/userController.do?getCustomerCombobox"/>',
																																				    valueField:'id',  
																																				    textField:'value'" />  
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div id="ezuiDialogBtn">
		<a onclick="commit();" id="ezuiBtn_commit" class="easyui-linkbutton" href="javascript:void(0);"><spring:message code="common.button.commit"/></a>
		<a onclick="ezuiDialogClose('#ezuiDialog');" class="easyui-linkbutton" href="javascript:void(0);"><spring:message code="common.button.close"/></a>
	</div>
	
	<div id="ezuiMenu" class="easyui-menu" style="width:120px;display: none;">
		<div onclick="add();" id="menu_add"		data-options='plain:true,iconCls:"icon-add"'><spring:message code="common.button.add"/></div>
		<div onclick="edit();"id="menu_edit" 	data-options='plain:true,iconCls:"icon-edit"'><spring:message code="common.button.edit"/></div>
	</div> 
</body>
</html>