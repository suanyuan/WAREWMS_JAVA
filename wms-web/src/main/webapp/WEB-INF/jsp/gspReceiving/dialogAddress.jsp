<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>

<div id='dialogAddAddress' style='padding: 10px;'>
	<form id='dialogAddAddressForm' method='post' >

	<input  id='r'  type="hidden" name='receivingId' value="${receivingId}" class='textbox-value' />
	<input  id='rece'  type="hidden" name='receivingAddressId' value="${gspReceivingAddress.receivingAddressId}" class='textbox-value' />
	<%--<input    name='receivingId' value="${receivingId}" class='easyui-textbox' />--%>

			<table>
				<%--<tr>
				<th>收货单位</th>
				<td>
					<input type='text' name='clientId' id="receivingI"  size="16" class='easyui-textbox' data-options='required:true'/>
					<a href="javascript:void(0)" onclick="searchReceiving()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"/>
				</td>
				</tr>--%>
				<tr>
					<th>销售人</th>
					<td><input type='text' name="sellerName" value="${gspReceivingAddress.sellerName}"  class='easyui-textbox' size='20' data-options='required:false'/></td>
				</tr>
				<tr>
					<th>国家</th>
					<td><input type='text'   name="country" value="${gspReceivingAddress.country}"    class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>省</th>
					<td>
						<input id="cc1" class="easyui-combobox" name="province" value="${gspReceivingAddress.province}"  editable="false" data-options="
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
					<input id="cc2" class="easyui-combobox" name="city" value="${gspReceivingAddress.city}" editable="false" data-options="
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
						<input id="cc3" class="easyui-combobox" name="district" value="${gspReceivingAddress.district}" editable="false" data-options="
            required:true,
            valueField:'name',
            textField:'name'">
					</td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input type='text' name="deliveryAddress"  value="${gspReceivingAddress.deliveryAddress}"   class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>邮编</th>
					<td><input type='text' name="zipcode" value="${gspReceivingAddress.zipcode}"   class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input type='text' name="contacts"  value="${gspReceivingAddress.contacts}"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td><input type='text' name="phone"  value="${gspReceivingAddress.phone}"  class='easyui-textbox' size='20' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>是否默认</th>
					<td><input type='text' id='isDefault' name="isDefault" value="${gspReceivingAddress.isDefault}" class='easyui-combobox' size='20' data-options='required:true'/></td>
				</tr>
			</table>

	</form>
</div>




<script>
	var ezuiDialogReceivingDetail;
	var dataGridReceivingDetail;
	var dialogReceiving;

	var  newreceivingId;
    $(function () {
        $("#dialogAddAddressForm input[name='isDefault']").combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'

        });
        console.log($("#hiddenreceivingId").val());
		if($("#dialogAddAddressForm input[name='country']").val()==null ||$("#dialogAddAddressForm input[name='country']").val()==undefined ||$("#dialogAddAddressForm input[name='country']").val()==""){
            $("#dialogAddAddressForm input[name='country']").val("中国");
		}

    });


    function addOredit(infoObj,url){
        $.ajax({
            url : url,
            data : {"gspReceivingAddressFormStr":JSON.stringify(infoObj)},
				type : 'POST', dataType : 'JSON',async  :true,
            success : function(result){
                //console.log(result);
                var msg='';
                try{
                    if(result.success){
                        msg = result.msg;
                        newreceivingId=result.obj;
                        ezuiDetailsDatagrid.datagrid('reload');
                        $("#dialogAddAddress").dialog('close');

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


    function doSubmitAddress() {
        var infoObj = new Object();
        $("#dialogAddAddressForm input[class='textbox-value']").each(function (index) {
            infoObj[""+$(this).attr("name")+""] = $(this).val();
        });

		var defaultAddress=$('#isDefault').combobox('getValue');


        var url = '';
	   if (defaultAddress=='0'){
			if (processTypeAddress == 'editAddress') {

				url = '<c:url value="/gspReceivingAddressController.do?edit"/>';
			}else{
				url = '<c:url value="/gspReceivingAddressController.do?add"/>';
			}

           addOredit(infoObj,url);

		}else {

		   if (processTypeAddress == 'editAddress') {
			   url = '<c:url value="/gspReceivingAddressController.do?editDefault"/>';
		   }else{
			   url = '<c:url value="/gspReceivingAddressController.do?addDefault"/>';
		   }
           addOredit(infoObj,url);

	   }

    }



</script>
