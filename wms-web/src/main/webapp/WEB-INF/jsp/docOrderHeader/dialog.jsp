<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiDialog' class='easyui-dialog' style='padding: 10px;'>
	<form id='ezuiForm' method='post' >
		<input type='hidden' id='docOrderHeaderId' name='docOrderHeaderId'/>
		<fieldset>
			<legend><spring:message code='common.button.orderQuery'/></legend>
			<table>
				<tr>
					<th>客户编号</th>
					<td><input type='text' id='customerid' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>SO编号</th>
					<td><input type='text' id='orderno' name='orderno' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>客户单号</th>
					<td><input type='text' id='soreference1' name='soreference1' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>来源单号</th>
					<td><input type='text' id='soreference2' name='soreference2' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>订单状态</th>
					<td><input type='text' id='sostatus' name='sostatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																														editable: false,
																														url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																														valueField: 'id',
																														textField: 'value'"/></td>
				</tr>
				<tr>
					<th>收货人</th>
					<td><input type='text' id='consigneename' name='consigneename' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>联系方式</th>
					<td><input type='text' id='cTel1' name='cTel1' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>释放状态</th>
					<td><input type='text' id='releasestatus' name='releasestatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																												editable: false,
																												url:'<c:url value="/docOrderHeaderController.do?getReleasestatusCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
					<th>订单类型</th>
					<td><input type='text' id='ordertype' name='ordertype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																											editable: false,
																											url:'<c:url value="/docOrderHeaderController.do?getOrderTypeCombobox"/>',
																											valueField: 'id',
																											textField: 'value'"/></td>
				</tr>
				<tr>
					<th>收货地址</th>
					<td colspan="5"><input type='text' id='cAddress1' name='cAddress1' class='easyui-textbox' size='78' data-options='required:true'/></td>
					<th>订单发运时间</th>
					<td><input type='text' id='lastshipmenttime' name='lastshipmenttime' class='easyui-textbox' size='16' data-options='editable: false'/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="5"><input type='text' id='notes' name='notes' class='easyui-textbox' size='78' style="height:30px" data-options="multiline:true,validType:['length[200]']"/></td>
				</tr>
				<tr>
					<th>订单创建时间</th>
					<td><input type='text' id='ordertime' name='ordertime' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>订单创建人</th>
					<td><input type='text' id='addwho' name='addwho' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>最后修改时间</th>
					<td><input type='text' id='edittime' name='edittime' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>最后修改人</th>
					<td><input type='text' id='editwho' name='editwho' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<td colspan="2">
						<a onclick='commit();' id='ezuiBtn_orderCommit' class='easyui-linkbutton' data-options='iconCls:"icon-save"' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<table id='ezuiDetailsDatagrid'></table>
	<form>
		<div>
			<a onclick='detailsAdd();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.skuAdd'/></a>
			<a onclick='detailsDel();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.skuDelete'/></a>
			<a onclick='detailsEdit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.skuEdit'/></a>
			<a onclick='clearDatagridSelected("#ezuiDetailsDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
		</div>
	</form>
</div>
<div id='ezuiDialogBtn'>
	<%-- <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
	<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>