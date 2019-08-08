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
				<th>生产批号</th>
				<td><input type='text' name='lotatt04' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
			</tr>
			<tr>
				<th>样品属性</th>
				<td><input type='text' name='lotatt09' id='lotatt09'/></td>
				<th>质量状态</th>
				<td>
                    <!--<input type='text' name='lotatt10' id='lotatt10'/>-->
                    <select name='lotatt10' id='lotatt10' class="easyui-combobox" data-options='width:110'>
                        <option value="HG">合格</option>
                        <option value="BHG">不合格</option>
                    </select>
                </td>
				<th>供应商</th>
				<td>
                    <input type='text' id='supplierIdChose' class='easyui-textbox'/>
                    <input type='hidden' name='lotatt08' id='lotatt08' />
                </td>
				<th>入库单号</th>
				<td><input type='text' name='lotatt14' id='lotatt14' class='easyui-textbox' size='16' data-options=''/></td>
			</tr>
		</table>

		<div style="display: none;">
			<table>
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
																												textField: 'value'"/></td>

				<th>批次号C</th>
				<td><input type='text' name='lotnum' id='lotnum' class='easyui-textbox' size='16' /></td>
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
			</table>


		</div>
	</form>
</div>
<script>
    $(function () {
        $("#lotatt09").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?sampleAttr',
            valueField:'id',
            textField:'value',
            width:110
        });

        /*$("#lotatt10").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?qcState',
            valueField:'id',
            textField:'value',
            width:110,
            required:true
        });*/
        /* 控件初始化start */
        $("#ezuiDetailsForm #supplierIdChose").textbox({
            width:110,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    ezuiSupplierDataClick();
                    ezuiCustDataDialogSearch();
                }
            }]
        });

        /* 客户选择弹框-主界面 */
        var ezuiSupplierDataClick = function(){
            $("#ezuiCustDataDialog #customerType").combobox('setValue','VE').combo('readonly', true);
            $("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
            ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
                url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
                method:'POST',
                toolbar : '#ezuiCustToolbar',
                title: '客户档案',
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
                queryParams:{
                    customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
                    activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
                },
                idField : 'customerid',
                columns : [[
                    {field: 'customerid',	title: '客户代码',	width: 15},
                    {field: 'descrC',		title: '中文名称',	width: 50},
                    {field: 'descrE',		title: '英文名称',	width: 50},
                    {field: 'customerTypeName',	title: '类型',	width: 15},
                    {field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
                            return rowData.activeFlag == '1' ? '是' : '否';
                        }}
                ]],
                onDblClickCell: function(index,field,value){
                    selectSupplierCust();
                },
                onRowContextMenu : function(event, rowIndex, rowData) {
                },onLoadSuccess:function(data){
                    $(this).datagrid('unselectAll');
                }
            });

            ezuiCustDataDialog.dialog('open');
        };

        /* 客户选择-主界面 */
        var selectSupplierCust = function(){
            var row = ezuiCustDataDialogId.datagrid('getSelected');
            if(row){
                $("#ezuiDetailsForm #supplierIdChose").textbox('setValue',row.customerid);
                $("#ezuiDetailsForm #lotatt08").val(row.customerid);
                ezuiCustDataDialog.dialog('close');
            };
        };


    })
</script>