<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiDetailsDialog' class='easyui-dialog' style='padding: 10px;'>
	<form id='ezuiDetailsForm' method='post'>
		<input type='hidden' id='docOrderDetailsId' name='docOrderDetailsId'/>
		<table>
			<tr>
			<th>SO编号</th>
			<td><input type='text' name='orderno'  id='orderno' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			<th>客户编码</th>
			<td><input type='text' name='customerid'  id='customerid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			<th>行号</th>
			<td><input type='text' name='orderlineno'  id='orderlineno' class='easyui-numberbox' size='16' data-options='editable:false'/></td>
			<th>状态</th>
			<td><input type='text' name='linestatus' id='linestatus' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																													editable:false,
																													url:'<c:url value="/docOrderHeaderController.do?getSostatusCombobox"/>',
																													valueField:'id',
																													textField:'value'"/></td>
			</tr>
			<tr>
			<th>产品</th>
			<td><input type='text' name='sku' id='sku' class='easyui-textbox' size='16' data-options="required:true"/></td>
			<th>中文名称</th>
			<td colspan="3"><input type='text' name=skuName id='skuName' class='easyui-textbox' size='44' data-options="editable:false"/></td>
			</tr>
			<tr>
			<th>产品条码</th>
			<td><input type='text' name='alternativesku' id='alternativesku' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			<th>英文代码</th>
			<td colspan="3"><input type='text' name='skuNameE' id='skuNameE' class='easyui-textbox' size='44' data-options='editable:false'/></td>
			</tr>
			<tr>
			<th>订货数</th>
			<td><input type='text' name='qtyordered' id='qtyordered' class='easyui-numberbox' size='16' data-options='required:true,min:1,precision:0'/></td>
			<th>分配数</th>
			<td><input type='text' name='qtyallocated' id='qtyallocated' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
			<th>拣货数</th>
			<td><input type='text' name='qtypicked' id='qtypicked' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
			<th>发货数</th>
			<td><input type='text' name='qtyshipped' id='qtyshipped' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
			</tr>
			<tr>
			<th>拣货区</th>
			<td><input type='text' name='pickzone' id='pickzone' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getPizoneTypeCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>
			<th>库位</th>
			<td><input type='text' name='location' id='location' class='easyui-textbox' size='16' /></td>
			<th>批次号</th>
			<td><input type='text' name='lotnum' id='lotnum' class='easyui-textbox' size='16' /></td>
			</tr>
			<tr>
			<th>包装代码</th>
			<td><input type='text' name='packid' id='packid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			<th>重量</th>
			<td><input type='text' name='grossweight' id='grossweight' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
			<th>体积</th>
			<td><input type='text' name='cubic' id='cubic' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
			<th>金额</th>
			<td><input type='text' name='price' id='price' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:2'/></td>
			</tr>
		</table>
	</form>
</div>