<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiReceDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
<div class='easyui-layout' data-options='fit:true,border:false'>
<div data-options="region:'center'">
	<div id='ezuiReceToolbar' class='datagrid-toolbar'   style="">
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>客户：</th><td>
							<input type='text' id='customerid' name="customerid" class='easyui-textbox'  size='12' data-options='prompt:"请输入客户代码"'/></td>
							<th>类型：</th><td>
							<input type='text' id='customerType' name="customerType" class='easyui-combobox'  size='8' data-options="panelHeight:'auto',
																														editable:false,
																														url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																														valueField: 'id',
																														textField: 'value'"/></td>
							<th>激活：</th><td>
							<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox'  size='8' data-options="panelHeight:'auto',
																														editable:false,
																														valueField: 'id',
																														textField: 'value',
																														data: [
																															{id: '1', value: '是'},
																															{id: '0', value: '否'}
																														]"/></td>
							<td>
								<a onclick='ezuiReceDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='selectRece();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
								<a onclick='ezuiReceToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
</div>
	<table id='ezuiReceDataDialogId' ></table>
</div>
</div>
</div>
<div id='ezuiReceDialogBtn'>
	<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
	<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>