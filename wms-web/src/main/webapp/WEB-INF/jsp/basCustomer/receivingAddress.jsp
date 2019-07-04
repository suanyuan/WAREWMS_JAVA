<%@ page language='java' pageEncoding='UTF-8'%>
<!DOCTYPE html>
<body>

<script>
    var ezuiDatagri;
    $(function() {



    ezuiDatagri = $('#ezuiData').datagrid({
		url : 'gspReceivingAddressController.do?showDatagrid',
		method:'POST',
        toolbar : '#toolb',
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
        idField : 'receivingAddressId',
		columns : [[
			{field: 'receivingAddressId',	hidden:true,	title: '待输入栏位0',	width: 50 },
			{field: 'receivingId',		hidden:true,  title: '待输入栏位1',	width: 50 },
			{field: 'sellerName',		title: '销售人',	width: 50 },
			{field: 'country',		title: '国家',	width: 50 },
			{field: 'province',		title: '省',	width: 50 },
            {field: 'city',		title: '市',	width: 50 },
            {field: 'district',		title: '区',	width: 50 },
            {field: 'deliveryAddress',		title: '送货地址',	width: 50 },
            {field: 'zipcode',		title: '邮编',	width: 50 },
            {field: 'contacts',		title: '联系人',	width: 50 },
            {field: 'phone',		title: '联系人电话',	width: 50 },
            {field: 'isDefault',		title: '是否默认',	width: 50 },
            {field: 'createId',		title: '创建人',	width: 50 },
            {field: 'createDate',		title: '创建日期',	width: 50 },
            {field: 'editId',		title: '修改人',	width: 50 },
            {field: 'editDate',		title: '修改日期',	width: 50 }
		]],
		onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
            ezuiDatagri.datagrid("resize",{height:500});
        }
	});

});


var doSearch = function(){
    ezuiDatagri.datagrid('load', {
		receivingAddressId : $('#receivingAddressId').val(),
		receivingId : $('#receivingId').val(),
		sellerName : $('#sellerName').val(),
		country : $('#country').val(),
		province : $('#province').val(),
		city : $('#city').val(),
		district : $('#district').val(),
		deliveryAddress : $('#deliveryAddress').val(),
		zipcode : $('#zipcode').val(),
		contacts : $('#contacts').val(),
		phone : $('#phone').val(),
		isDefault : $('#isDefault').val(),
		createId : $('#createId').val(),
		createDate : $('#createDate').val(),
		editId : $('#editId').val(),
		editDate : $('#editDate').val()
	});
};
</script>


	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div id='toolb' class='datagrid-toolbar' style='padding: 5px;'>
		<fieldset>
			<legend><spring:message code='common.button.query'/></legend>
			<table>
				<tr>
					<th>销售人：</th><td><input type='text' id='sellerName' class='easyui-textbox' size='16' data-options=''/></td>
					<th>国家：</th><td><input type='text' id='country' class='easyui-textbox' size='16' data-options=''/></td>
					<th>省：</th><td><input type='text' id='province' class='easyui-textbox' size='16' data-options=''/></td>
					<%--<th>待输入名称5：</th><td><input type='text' id='city' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称6：</th><td><input type='text' id='district' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称7：</th><td><input type='text' id='deliveryAddress' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称8：</th><td><input type='text' id='zipcode' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称9：</th><td><input type='text' id='contacts' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称10：</th><td><input type='text' id='phone' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称11：</th><td><input type='text' id='isDefault' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称12：</th><td><input type='text' id='createId' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称13：</th><td><input type='text' id='createDate' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称14：</th><td><input type='text' id='editId' class='easyui-textbox' size='16' data-options=''/></td>
					<th>待输入名称15：</th><td><input type='text' id='editDate' class='easyui-textbox' size='16' data-options=''/></td>--%>
					<td>
						<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
						<a onclick='ezuiToolbarClear("#toolb");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/>取消</a>
					</td>
				</tr>
			</table>
		</fieldset>
		<%--<div>
			<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
			<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
			<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
			<a onclick='clearDatagridSelected("#ezuiDatagri");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
		</div>--%>
	</div>
				<table id='ezuiData'></table>
</body>

