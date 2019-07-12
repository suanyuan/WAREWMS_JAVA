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

/* 初始化 */
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: false,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'customerid',
		columns : [[
			{field: 'customerid',		title: '客户代码',	width: 20 },
            {field: 'descrC',		title: '客户名称',	width: 20 },
            {field: 'customerType',		title: '客户类型 ',	width: 20,formatter:function(value,rowData,rowIndex){
                    if (rowData.customerType=='CO') {
                        return rowData.customerType='收货单位';
					}else if (rowData.customerType=='VE'){
                        return rowData.customerType='供应商';
					}else if (rowData.customerType=='CA'){
                        return rowData.customerType='承运商';
					}else if (rowData.customerType=='OT'){
                        return rowData.customerType='其他';
					}else if (rowData.customerType=='OW'){
                        return rowData.customerType='货主';
					}else if (rowData.customerType=='PR'){
                        return rowData.customerType='生产企业';
					}else if (rowData.customerType=='WH'){
                        return rowData.customerType='主体';
					}
                } },
            {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 30 },
            {field: 'shorthandName',		title: '简称 ',	width: 15 },
            {field: 'enterpriseName',		title: '企业名称 ',	width: 20 },
            {field: 'contacts',		title: '联系人 ',	width: 15 },
            {field: 'contactsPhone',		title: '联系人电话 ',	width: 30 },
            {field: 'remark',		title: '备注 ',	width: 15 },
           /* {field: 'operateType',		title: '类型 ',	width: 12, formatter:function(value,rowData,rowIndex){
                    return rowData.operateType;
                }},*/
			{field: 'supContractNo',		title: '合同编号 ',	width: 20 },
			{field: 'contractUrl',		title: '合同文件 ',	width: 20 },
			{field: 'clientContent',		title: '委托内容 ',	width: 20 },
			{field: 'clientStartDate',		title: '委托开始时间 ',	width: 30 },
			{field: 'clientEndDate',		title: '委托结束时间 ',	width: 30 },
			/*{field: 'overreceiving',		title: '允许超收',	width: 12, formatter:function(value,rowData,rowIndex){
				return rowData.overreceiving == 'Y' ? '是' : '否';
            }},*/
			{field: 'clientTerm',		title: '委托期限',	width: 20 },
            {field: 'isChineseLabel',		title: '是否贴中文标签 ',	width: 40},
            {field: 'activeFlag',		title: '是否合作 ',	width: 20,formatter:function(value,rowData,rowIndex){
                    return rowData.activeFlag == '1' ? '是' : '否';
                }}
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
			ajaxBtn($('#menuId').val(), '<c:url value="/basCustomerController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
	
	/*$("#customerid").textbox('textbox').css('text-transform','uppercase');*/

    ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href: '/basCustomerController.do?toDetail',
		fit:true,
		cache:false,
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
});

/* 新增 */
var add = function(){
	processType = 'add';
	$('#basCustomerId').val(0);
	/*$("#ezuiForm #customerid").textbox({
		editable:true
	}).textbox('textbox').css('text-transform','uppercase');*/
	/*$("#ezuiForm #customerType").combo('readonly', false);*/
	/*$("#ezuiForm #activeFlag").combobox('setValue','Y').combobox('setText','是');*/
	/*$("#ezuiForm #operateType").combobox('setValue','N').combobox('setText','否');*/
	/*$("#ezuiForm #overrcvpercentage").numberbox('setValue','0');*/
	ezuiDialog.dialog('open');
};

/* 编辑 */
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$("#ezuiForm #customerid").textbox({
			editable:false
		});
		$("#ezuiForm #customerType").combo('readonly', true);
		$("#ezuiForm #operateType").combo('readonly', true);
		ezuiForm.form('load',{
			customerid : row.customerid,
			customerType : row.customerType,
			descrC : row.descrC,
            enterpriseNo:row.enterpriseNo,
            shorthandName:row.shorthandName,
            enterpriseName:row.enterpriseName,
            enterpriseType:row.enterpriseType,
            contacts:row.contacts,
            contactsPhone:row.contactsPhone,
            remark:row.remark,
            operateType:row.operateType,
            supContractNo:row.supContractNo,
            contractUrl:row.contractUrl,
            clientContent:row.clientContent,
            clientStartDate:row.clientStartDate,
            clientEndDate:row.clientEndDate,
            clientTerm:row.clientTerm,
            isChineseLabel:row.isChineseLabel,
			activeFlag : row.activeFlag,
			receivingAddressId: row.receivingAddressId

			/*address1 : row.address1,
			contact1 : row.contact1,
			contact1Tel1 : row.contact1Tel1,
			overreceiving : row.overreceiving,
			overrcvpercentage : row.overrcvpercentage*/
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var exchange=function (type) {
	if (type == "收货单位") {
	    return type='CO';
	}else if (type=='供应商'){
        return type='VE';
    }else if (type=='承运商'){
        return type='CA';
    }else if (type=='其他'){
        return type='OT';
    }else if (type=='货主'){
        return type='OW';
    }else if (type=='生产企业'){
        return type='PR';
    }else if (type=='主体'){
        return type='WH';
    }
};

/* 删除 */
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
	    var customerType = exchange(row.customerType);
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.update"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'basCustomerController.do?delete',
					data : {enterpriseId : row.enterpriseId, customerType : customerType},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							if(result.success){
								msg = result.msg;
							}else{
								$.messager.alert('操作提示', result.msg);
								msg = '<font color="red">' + result.msg + '</font>';
							}
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

/* 继续合作 */
var goon = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row.activeFlag!="1"){
        var type = exchange(row.customerType);
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.goon"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'basCustomerController.do?goon',
					data : {enterpriseId : row.enterpriseId, customerType : type},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							if(result.success){
								msg = result.msg;
							}else{
								$.messager.alert('操作提示', result.msg);
								msg = '<font color="red">' + result.msg + '</font>';
							}
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
			msg : '该客户已是合作客户，请重新选择。', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 提交 */
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/basCustomerController.do?edit"/>';
	}else{
		url = '<c:url value="/basCustomerController.do?add"/>';
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
					$.messager.alert('操作提示', result.msg);
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

/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		customerid : $('#customerid').val(),
        enterpriseNo : $('#enterpriseNo').val(),
        shorthandName : $('#shorthandName').val(),
        enterpriseName : $('#enterpriseName').val(),
        clientStartDate : $('#clientStartDate').val(),
        clientEndDate : $('#clientEndDate').val(),
        supContractNo : $('#supContractNo').val(),

		customerType : $('#customerType').combobox('getValue'),
        operateType : $('#operateType').combobox('getValue'),
		activeFlag : $('#activeFlag').combobox('getValue'),
        isChineseLabel : $('#isChineseLabel').combobox('getValue'),
        descrC : $('#descrC').val()

	});
};

</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: scroll;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table >
						<tr align="left">
							<th>产品代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options='' align="left"/></td>
							<th>客户名称</th><td><input type='text' id='descrC' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业信息代码</th><td><input type='text' id='enterpriseNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>简称</th><td><input type='text' id='shorthandName' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr align="left">
							<th>企业名称</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>合同编号</th><td><input type='text' id='supContractNo' class='easyui-textbox' size='16' data-options=''/></td>

							<th>委托开始时间</th><td><input type='text' id='clientStartDate' class='easyui-datebox' size='16' data-options=''/></td>
							<th>委托结束时间</th><td><input type='text' id='clientEndDate' class='easyui-datebox' size='16' data-options=''/></td>

						</tr>
							<th align="left">客户类型</th><td><input type='text' id='customerType' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/></td>

							<th align="left">企业类型</th><td><input type='text' id='operateType' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	url:'<c:url value="/basCustomerController.do?getOperateTypeCombobox"/>',
																																	valueField: 'id',
																																	textField: 'value'"/></td>

						<th align="left">是否贴中文标签</th>

						<td>
							<select id="isChineseLabel" name="isChineseLabel" class="easyui-combobox"  style="width:145px;">
								<option value=""></option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>

							<%--<th>是否贴中文标签：</th><td><input type='text' id="isChineseLabel"  name="activeFlag"  class="easyui-combobox" size='4' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'}, 
																																	{id: 'N', value: '否'}
																																	]"/></td>--%>
							<%--<th>是否激活：</th><td><input type='text' id="activeFlag"  name="activeFlag"  class="easyui-combobox" size='4' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'},
																																	{id: 'N', value: '否'}
																																	]"/></td>--%>
						<th align="left">是否激活</th>
						<td>
							<select id="activeFlag" class="easyui-combobox"  style="width:145px;">
								<option value=""></option>
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</td>
						<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.update'/></a>
					<a onclick='goon();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.goon'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<%--<form id='ezuiForm' method='post'>
			<input type='hidden' id='basCustomerId' name='basCustomerId'/>

		&lt;%&ndash;	<table>
				<tr>
					<th>客户ID</th>
					<td><input type='text' name='customerId' id='customerid' class='easyui-textbox' size='8' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>客户名称</th>
					<td><input type='text' name='descrC' id='descrC' class='easyui-textbox' size='8' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>企业信息代码</th>
					<td colspan="3"><input type='text' name='enterpriseNo' id='enterpriseNo' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>简称</th>
					<td colspan="3"><input type='text' name='shorthandName' id='shortHandName' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>企业名称</th>
					<td colspan="3"><input type='text' name='enterpriseName' id='enterpriseName' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>企业类型</th>
					<td colspan="3"><input type='text' name='enterpriseType' id='enterpriseType' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td colspan="3"><input type='text' name='contacts' id='contacts' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td colspan="3"><input type='text' name='contactsPhone' id='contactsPhone' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><input type='text' name='remark' id='remark' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>类型</th>
					<td colspan="3"><input type='text' name='operateType' id='operateType' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>

				<tr>
					<th>合同编号</th>
					<td colspan="3"><input type='text' name='supContractNo' id='supContractNo' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>合同文件</th>
					<td><input type='text' name='contractUrl' id='contractUrl' class='easyui-textbox' size='8' data-options='required:false'/>
					</td>
				</tr>
				&lt;%&ndash;<tr>
					<th>客户类型</th>
					<td><input type='text' name='customerType' id='customerType' class='easyui-combobox' size='16' data-options="required:true,
																																panelHeight:'auto',
																																editable:false,
																																url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																																valueField: 'id',
																																textField: 'value'"/>
					</td>
				</tr>&ndash;%&gt;
				<tr>
					<th>委托内容</th>
					<td colspan="3"><input type='text' name='clientContent' id='clientContent' class='easyui-textbox' size='23' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>委托开始时间</th>
					<td colspan="3"><input type='text' name='clientStartDate' id='clientStartDate' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>委托结束时间</th>
					<td colspan="3"><input type='text' name='clientEndDate' id='clientEndDate' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>委托期限</th>
					<td colspan="3"><input type='text' name='clientTerm' id='clientTerm' class='easyui-textbox' size='23' data-options='required:true'/></td>
				</tr>


				<tr>
					<th>是否贴中文标签</th>
					<td>
						<input type='text' name='isChineseLabel' id='isChineseLabel' class='easyui-combobox' size='8' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'Y', value: '是'}, 
																																	{id: 'N', value: '否'}
																																	]"/>
					</td>


					<th>是否激活</th>
					<td>
						<input type='text' name='activeFlag' id='activeFlag' class='easyui-combobox' size='8' data-options="panelHeight:'auto',
																											editable:false,
																											valueField: 'id',
																											textField: 'value',
																											data: [
																											{id: 'Y', value: '是'},
																											{id: 'N', value: '否'}
																											]"/>
					</td>
				</tr>			
			</table>&ndash;%&gt;
		</form>--%>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<%--<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
	</div>--%>
</body>
</html>
