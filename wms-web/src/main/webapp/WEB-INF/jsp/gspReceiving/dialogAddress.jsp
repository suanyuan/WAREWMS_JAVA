<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>

<div id='dialogAddAddress' style='padding: 10px;'>
	<form id='dialogAddAddressForm' method='post' >
	<input type='hidden' id='r'   name='receivingId' class='textbox-value' />

			<table>
				<tr>
				<th>收货单位</th>
				<td>
					<input type='text' name='clientId' id="receivingI"  size="16" class='easyui-textbox' data-options='required:true'/>
					<a href="javascript:void(0)" onclick="searchReceiving()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"/>
				</td>
				</tr>
				<tr>
					<th>销售人</th>
					<td><input type='text' name="sellerName"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>国家</th>
					<td><input type='text'   name="country"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>省</th>
					<td>
						<input id="cc1" class="easyui-combobox" name="province" editable="false" data-options="
            	required:true,
                valueField: 'name',
                textField: 'name',
            	url: 'gspReceivingAddressController.do?getArea&pid=0',
            	onSelect: function(rec){
					$('#cc2').combobox('clear');
               		$('#cc3').combobox('clear');
                	var url= 'gspReceivingAddressController.do?getArea&pid='+rec.id;
                	$('#cc2').combobox('reload',url);
            }">
					</td>
				</tr>
				<tr>
					<th>市</th>
					<td>
					<input id="cc2" class="easyui-combobox" name="city" editable="false" data-options="
            required:true,
            valueField: 'name',
            textField: 'name',
            onSelect: function(rec){
            	$('#cc2').combobox('reload', url);
                $('#cc3').combobox('clear');
                var url = 'gspReceivingAddressController.do?getArea&pid='+rec.id;
                $('#cc3').combobox('reload', url);
            }">
					</td>
				</tr>
				<tr>
					<th>区/县</th>
					<td>
						<input id="cc3" class="easyui-combobox" name="district" editable="false" data-options="
            required:true,
            valueField:'name',
            textField:'name'">
					</td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input type='text' name="deliveryAddress"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>邮编</th>
					<td><input type='text' name="zipcode"   class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input type='text' name="contacts"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td><input type='text' name="phone"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>是否默认</th>
					<td><input type='text' id='isDefault' name="isDefault" class='easyui-combobox' size='20' data-options='required:true'/></td>
				</tr>
			</table>

	</form>
</div>
<div>
	<td>
		<a onclick='doSubmitAddress();'  class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose(dialogAddAddress);' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</td>
</div>


























<div id='ezuiDialogReceivingDetail' style='padding: 10px;'>
	<div id='TBReceiving' class='datagrid-toolbar' style=''>
		<fieldset>
			<legend>收货单位信息</legend>
			<table>
				<tr>
					<th>收货单位</th>
					<td><input type='text' id='receivId' class='easyui-textbox' data-options='width:200'/></td>
					<th>是否需要审核</th>
					<td><input type='text' id='check' class='easyui-textbox' data-options='width:200'/></td>
					<td>
						<a onclick='doSearchReceiving();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
						<a onclick='choseReceivingSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
					</td>
				</tr>
			</table>
		</fieldset>
	</div>
	<table id="dataGridReceivingDetail">

	</table>
</div>

<div id="dialogReceiving">

</div>
<script>
	var ezuiDialogReceivingDetail;
	var dataGridReceivingDetail;
	var dialogReceiving;
    $(function () {
        $("#dialogAddAddressForm input[name='isDefault']").combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });



        dataGridReceivingDetail = $("#dataGridReceivingDetail").datagrid({
            url : '<c:url value="/gspReceivingController.do?showDatagrid"/>',
            method:'POST',
            toolbar : '#TBReceiving',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'receivingId',
            columns : [[
                {field: 'receivingId',	hidden:true,		width: 72 },
                {field: 'clientId',		title: '货主',	width: 52 },
                {field: 'supplierId',		title: '供应商',	width: 52 },

                {field: 'enterpriseNo',		title: '企业信息代码',	width: 81 },
                {field: 'shorthandName',		title: '简称',	width: 41 },
                {field: 'enterpriseName',		title: '企业名称',	width: 61  },


                {field: 'deliveryAddress',		title: '地址',	width: 52 },
                {field: 'isCheck',		title: '是否需要审核',	width: 82 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isCheck == '1' ? '是' : '否';
                    }},
                {field: 'firstState',		title: '审核状态',	width: 62 ,formatter:firstStateFormatter},
                {field: 'isReturn',		title: '是否医废',	width: 62 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isReturn == '1' ? '是' : '否';
                    }},
                {field: 'isUse',		title: '是否有效',	width: 62 ,formatter:function(value,rowData,rowIndex){
                        return rowData.isUse == '1' ? '是' : '否';
                    }},
            ]],
            onDblClickCell: function(index,field,value){
                choseReceivingSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        });


        ezuiDialogReceivingDetail = $('#ezuiDialogReceivingDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
               // ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogReceiving = $('#dialogReceiving').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspReceivingController.do?toDetail",
            cache: false,
            onClose : function() {
                // ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

    });







    function searchReceiving() {
        if(ezuiDialogReceivingDetail){

        ezuiDialogReceivingDetail.dialog('open')
        }

    }

    function doSearchReceiving() {
        dataGridReceivingDetail.datagrid('load', {
            receivingId : $('#receivId').val(),
            isCheck : $('#check').val(),
        });
    }

    function choseReceivingSelect() {
        var row = dataGridReceivingDetail.datagrid("getSelected");
        if(row){
            $("#receivingI").textbox("setValue",row.clientId);
         //   $("#r").textbox("setValue",row.receivingId);
            $("#r").val(row.receivingId);
            ezuiDialogReceivingDetail.dialog('close');
        }
    }

    function doSubmitAddress() {
        var infoObj = new Object();
        $("#dialogAddAddressForm input[class='textbox-value']").each(function (index) {
            infoObj[""+$(this).attr("name")+""] = $(this).val();
        });

        var url = '';
        if (processType == 'edit') {
            url = '<c:url value="/gspReceivingAddressController.do?edit"/>';
        }else{
            url = '<c:url value="/gspReceivingAddressController.do?add"/>';
        }

        $.ajax({
            url : url,
            data : {"gspReceivingAddressFormStr":JSON.stringify(infoObj)},type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                console.log(result);
                var msg='';
                try{
                    if(result.success){
                        msg = result.msg;
                        ezuiDetailsDatagrid.datagrid('reload');
                        dialogAddAddress.dialog('close');
                    }else{
                        msg = '<font color="red">' + result.msg + '</font>';
                    }
                }catch (e) {
                    //msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                    msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
                } finally {
                    $.messager.show({
                        msg : msg, title : '<spring:message code="common.message.prompt"/>'
                    });
                    $.messager.progress('close');
                }
            }
        });



    }



</script>
