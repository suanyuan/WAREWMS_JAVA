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
					<th>货主</th>
					<td><input type='text' id='customerid' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>SO编号</th>
					<td><input type='text' id='orderno' name='orderno' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>客户单号</th>
					<td><input type='text' id='soreference1' name='soreference1' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>定向入库单号</th>
					<td><input type='text' id='soreference2' name='soreference2' class='easyui-textbox' size='16' data-options='required:false'/></td>
					<th>订单状态</th>
					<td><input type='text' id='sostatus' name='sostatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																														editable: false,
																														url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																														valueField: 'id',
																														textField: 'value'"/></td>
				</tr>
				<tr>
					<th>收货方</th>
					<td><input type='text' id='consigneeid' name='consigneeid' class='easyui-textbox' size='16' data-options='required:true'/></td>
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
					<th>快递公司</th>
					<td><input type='text' id='carrierId' name='carrierId' data-options=''/></td>
				</tr>
				<tr>
					<th>省</th>
					<td>
						<!--<input type='text' id='cProvince' class='easyui-textbox' size='16' data-options=''/>-->
						<input id="cProvince" class="easyui-combobox" name="cProvince"  editable="false" data-options="
								valueField: 'name',
								textField: 'name',
								width:110,
								required:true,
								url: 'gspReceivingAddressController.do?getArea&pid=0',
								onSelect: function(rec){
									$('#cCity').combobox('clear');
									$('#cAddress2').combobox('clear');
									var url= 'gspReceivingAddressController.do?getArea&pid='+rec.id;
									$('#cCity').combobox('reload',url);
							}">
					</td>
					<th>市</th>
					<td>
						<!--<input type='text' id='cCity' class='easyui-textbox' size='16' data-options=''/>-->
						<input id="cCity" class="easyui-combobox" name="cCity" editable="false" data-options="
								valueField: 'name',
								textField: 'name',
								width:110,
								required:true,
								onSelect: function(rec){
									$('#cCity').combobox('reload', url);
									$('#cAddress2').combobox('clear');
									var url = 'gspReceivingAddressController.do?getArea&pid='+rec.id;
									$('#cAddress2').combobox('reload', url);
								}">
					</td>
					<th>区</th>
					<td>
						<!--<input type='text' id='cCountry' class='easyui-textbox' size='16' data-options=''/>-->
						<input id="cAddress2" class="easyui-combobox" name="cAddress2"  editable="false" data-options="
								width:110,
								valueField:'name',
								required:true,
								textField:'name'">
					</td>
					<th>收货地址</th>
					<td colspan="3"><input type='text' id='cAddress1' name='cAddress1' class='easyui-textbox' size='45' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3"><input type='text' id='notes' name='notes' class='easyui-textbox' size='45' style="height:30px" data-options="multiline:true,validType:['length[200]']"/></td>
					<th>结算方式</th>
					<td><input type='text' id='userdefine2' name='userdefine2' data-options=''/></td>
					<th>订单发运时间</th>
					<td><input type='text' id='lastshipmenttime' name='lastshipmenttime' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>发运方式</th>
					<td><input type='text' id='userdefine1' name='userdefine1' data-options=''/></td>
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
	<div class="easyui-tabs" style="width:950px;height:200px">
		<div title="出库明细" style="padding:3px">
			<table id='ezuiDetailsDatagrid'></table>
		</div>
		<div title="分配明细" style="padding:0px">
			<table id='allocationDetailsDatagrid'></table>
		</div>
	</div>
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
<script>
	$(function () {
        $("#carrierId").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/basCarrierLicenseController.do?getCombobox',
            valueField:'id',
            textField:'value',
			width:110
        });

        $("#userdefine1").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?sendFunction',
            valueField:'id',
            textField:'value',
            width:110
        });

        $("#userdefine2").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?settlement',
            valueField:'id',
            textField:'value',
            width:110
        });
    })
</script>