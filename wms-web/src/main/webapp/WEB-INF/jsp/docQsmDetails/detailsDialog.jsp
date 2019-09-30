<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiDetailsDialog' class='easyui-dialog' style='padding: 10px;'>
	<form id='ezuiDetailsForm' method='post'>
		<table>
			<tr>
				<th>管理任务号</th>
				<td><input type='text' name='qcudocno'  id='qcudocno' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>行号</th>
				<td><input type='text' name='qcudoclineno'  id='qcudoclineno' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>管理状态</th>
				<td><input type='text' name='qcustatus' id='qcustatus' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        readonly:true,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '任务创建'},
																																        {label: '30',
																																        value: '部分完成'},
																																       {label: '40',
																																         value: '任务完成'},
																																       {label: '90',
																																         value: '任务取消'},
																																        {label: '99',
																																         value: '任务关闭'}]"/></td>
				<th>入库单号</th>
				<td><input type='text' name='lotatt14' id='lotatt14' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>货主</th>
				<td><input type='text' name='customerid'  id='customerid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>入库日期</th>
				<td><input type='text' name='lotatt03' id='lotatt03' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>供应商</th>
				<td><input type='text' name='lotatt08' id='lotatt08' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>产品代码</th>
				<td><input type='text' name='sku' id='sku' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>产品名称</th>
				<td><input type='text' name='lotatt12' id='lotatt12' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>注册证号/备案凭证号</th>
				<td><input type='text' name='lotatt06' id='lotatt06' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>规格/型号</th>
				<td><input type='text' name='descrc' id='descrc' class='easyui-textbox' size='16' data-options="editable:false"/></td>
				<th>生产批号</th>
				<td><input type='text' name='lotatt04' id='lotatt04' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>序列号</th>
				<td><input type='text' name='lotatt05' id='lotatt05' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>灭菌批号</th>
				<td><input type='text' name='lotatt07' id='lotatt07' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>生产日期</th>
				<td><input type='text' name='lotatt01' id='lotatt01' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>有效期/失效期</th>
				<td><input type='text' name='lotatt02' id='lotatt02' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>件数</th>
				<td><input type='text' name='qty' id='qty' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>数量</th>
				<td><input type='text' name='qtyeach' id='qtyeach' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>生产企业</th>
				<td><input type='text' name='lotatt15' id='lotatt15' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>生产许可证号/备案号</th>
				<td><input type='text' name='reservedfield06' id='reservedfield06' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>质量状态</th>
				<td><input type='text' name='lotatt10' id='lotatt10' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>质量状态变更过程</th>
				<td><input type='text' name='changeProcess' id='changeProcess' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
                <th>库位</th>
				<td><input type='text' name='locationid' id='locationid' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>发现日期</th>
				<td><input type='text' name='finddate' id='finddate' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>不合格说明</th>
				<td><input type='text' name='failedDescription' id='failedDescription' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>货主处置措施</th>
				<td><input type='text' name='customeridTreatment' id='customeridTreatment' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>处置日期</th>
				<td><input type='text' name='treatmentDate' id='treatmentDate' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>备注</th>
				<td><input type='text' name='remarks' id='remarks' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>
			<tr>
				<th>记录日期</th>
				<td><input type='text' name='recordingDate' id='recordingDate' class='easyui-textbox' size='16' data-options='editable:false'/></td>
				<th>记录人</th>
				<td><input type='text' name='recordingPeople' id='recordingPeople' class='easyui-textbox' size='16' data-options='editable:false'/></td>
			</tr>

			</table>

	</form>
</div>
<div id='ezuiDetailsDialogBtn'>
	<a onclick='detailsCommitZ();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton' href='javascript:void(0);'>指定库存</a>
	<a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
