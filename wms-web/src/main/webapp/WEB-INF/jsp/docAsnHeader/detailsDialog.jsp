<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiDetailsDialog' class='easyui-dialog' style='padding: 10px;'>
	<form id='ezuiDetailsForm' method='post'>
		<input type='hidden' id='docAsnDetailsId' name='docAsnDetailsId'/>
		<table>
			<tr>
				<th>入库单编号</th>
				<td><input type='text' name='asnno'  id='asnno' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>客户编码</th>
				<td><input type='text' name='customerid'  id='customerid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>行号</th>
				<td><input type='text' name='asnlineno'  id='asnlineno' class='easyui-numberbox' size='16' data-options='editable:false'/></td>
				<th>状态</th>
				<td><input type='text' name='linestatus' id='linestatus' class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																														editable:false,
																														url:'<c:url value="/docAsnHeaderController.do?getAsnstatusCombobox"/>',
																														valueField:'id',
																														textField:'value'"/></td>
			</tr>
			<tr>	
				<th>产品</th>
				<td><input type='text' name='sku' id='sku' class='easyui-textbox' size='16' data-options="required:true"/></td>
				<th>中文名称</th>
				<td colspan="3"><input type='text' name='skudescrc' id='skudescrc' class='easyui-textbox' size='44' data-options="editable:false"/></td>
				<th>产品条码</th>
				<td><input type='text' name='alternativesku' id='alternativesku' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>包装代码</th>
				<td><input type='text' name='packid' id='packid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>预计到货数</th>
				<td><input type='text' name='expectedqty' id='expectedqty' class='easyui-numberbox' size='16' data-options='required:true,min:1,precision:0'/></td>
				<th>实际到货数</th>
				<td><input type='text' name='receivedqty' id='receivedqty' class='easyui-numberbox' size='16' data-options='editable:false,min:0,precision:0'/></td>
				<th>收货库位</th>
				<td><input type='text' name='receivinglocation' id='location' class='easyui-textbox' size='16' /></td>
			</tr>
			<tr>
				<th>重量</th>
				<td><input type='text' name='totalgrossweight' id='totalgrossweight' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
				<th>体积</th>
				<td><input type='text' name='totalcubic' id='totalcubic' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:3'/></td>
				<th>单价</th>
				<td><input type='text' name='totalprice' id='totalprice' class='easyui-numberbox' size='16' data-options='required:true,min:0,precision:2'/></td>
			</tr>
			<tr>
				<th>生产日期</th><td><input type='text' name='lotatt01' id='lotatt01' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
				<th>失效日期</th><td><input type='text' name='lotatt02' id='lotatt02' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
				<th>入库日期</th><td><input type='text' name='lotatt03' id='lotatt03' class='easyui-datebox' size='16' data-options='required:false,editable:true'/></td>
			</tr>
			<tr>	
				<th>库存状态</th><td><input type='text' name='lotatt04' id='lotatt04' class='easyui-combobox' size='16' data-options="required:false,panelHeight:'auto',
																													editable:true,
																													valueField: 'id',
																													textField: 'value',
																													data: [
																													{id: 'HG', value: '合格'}, 
																													{id: 'CC', value: '残次'}, 
																												]"/></td>
				<th>批属5</th><td><input type='text' name='lotatt05' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
				<th>批属6</th><td><input type='text' name='lotatt06' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>
			</tr>
		</table>
	</form>
</div>
<div id='ezuiDetailsDialogBtn'>
	<a onclick='detailsCommit();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
	<a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>