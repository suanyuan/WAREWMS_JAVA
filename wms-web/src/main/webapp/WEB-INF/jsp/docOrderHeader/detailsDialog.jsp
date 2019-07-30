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
																													url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																													valueField:'id',
																													textField:'value'"/></td>
			</tr>
			<tr>
			<th>产品</th>
			<td><input type='text' name='sku' id='sku' class='easyui-textbox' size='16' data-options="required:true"/></td>
			<th>中文名称</th>
			<td><input type='text' name=skuName id='skuName' class='easyui-textbox' size='16' data-options="editable:false"/></td>
				<th>产品条码</th>
				<td><input type='text' name='alternativesku' id='alternativesku' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>规格型号</th>
				<td><input type='text' name='skuNameE' id='skuNameE' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
			<th>订货数</th>
			<td><input type='text' name='qtyordered' id='qtyordered' class='easyui-numberbox' size='16' data-options='required:true,min:1,precision:0'/></td>
			<th>库位</th>
			<td><input type='text' name='location' id='location' class='easyui-textbox' size='16' /></td>
			<th>包装代码</th>
			<td><input type='text' name='packid' id='packid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			<th>序列号</th>
			<td><input type='text' name='lotatt05' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
			</tr>
			<tr>
				<th>生产日期</th>
				<td><input type='text' name='lotatt01' id='lotatt01' class='easyui-datebox' size='16' data-options=''/></td>
				<th>效期</th>
				<td><input type='text' name='lotatt02' id='lotatt02' class='easyui-textbox' size='16' data-options=''/></td>
				<th>入库日期</th>
				<td><input type='text' name='lotatt03' id='lotatt03' class='easyui-datebox' size='16' data-options=''/></td>
				<th>生产批号(S)</th>
				<td><input type='text' name='lotatt04' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
			</tr>
			<tr>
				<th>样品属性(S)</th>
				<td><input type='text' name='lotatt09' id='lotatt09' class='easyui-textbox' size='16' data-options=''/></td>
				<th>质量状态</th>
				<td><input type='text' name='lotatt10' id='lotatt10' class='easyui-textbox' size='16' data-options=''/></td>
				<th>供应商(S)</th>
				<td><input type='text' name='lotatt08' id='lotatt08' class='easyui-textbox' size='16' data-options=''/></td>
				<th>入库单号</th>
				<td><input type='text' name='lotatt14' id='lotatt14' class='easyui-textbox' size='16' data-options=''/></td>
			</tr>
			<!--
			<th>[+件数]分配数C</th>
			<td><input type='text' name='qtyallocated' id='qtyallocated' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
			<th>拣货数C</th>
			<td><input type='text' name='qtypicked' id='qtypicked' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
			<th>发货数C</th>
			<td><input type='text' name='qtyshipped' id='qtyshipped' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
			<th>重量C</th>
			<td><input type='text' name='grossweight' id='grossweight' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
			<th>体积C</th>
			<td><input type='text' name='cubic' id='cubic' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
			<th>金额C</th>
			<td><input type='text' name='price' id='price' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:2'/></td>
			<th>拣货区C</th>
			<td><input type='text' name='pickzone' id='pickzone' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																											    editable:false,
																												url:'<c:url value="/basLocationController.do?getPizoneTypeCombobox"/>',
																												valueField: 'id',
			<th>批次号C</th>
			<td><input type='text' name='lotnum' id='lotnum' class='easyui-textbox' size='16' /></td>																									textField: 'value'"/></td>

			<th>产品注册证C</th>
				<td><input type='text' name='lotatt06' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>
				<th>灭菌批号C</th>
				<td><input type='text' name='lotatt07' id='lotatt07' class='easyui-textbox' size='16' data-options=''/></td>
			<th>存储条件C</th>
				<td><input type='text' name='lotatt11' id='lotatt11' class='easyui-textbox' size='16' data-options=''/></td>
				<th>产品名称C</th>
				<td><input type='text' name='lotatt12' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
				<th>生产厂商名称C</th>
				<td><input type='text' name='lotatt15' id='lotatt15' class='easyui-textbox' size='16' data-options=''/></td>
				<th>自定义批属1C</th>
				<td><input type='text' name='lotatt16' id='lotatt16' class='easyui-textbox' size='16' data-options=''/></td>
				<th>自定义批属2C</th>
				<td><input type='text' name='lotatt17' id='lotatt17' class='easyui-textbox' size='16' data-options=''/></td>
				<th>自定义批属3C</th>
				<td><input type='text' name='lotatt18' id='lotatt18' class='easyui-textbox' size='16' data-options=''/></td>
				<th>双证C</th>
				<td><input type='text' name='lotatt13' id='lotatt13' class='easyui-textbox' size='16' data-options=''/></td>
			-->
		</table>
	</form>
</div>