<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='dialogAddAddress' style='padding: 10px;'>
	<input type='hidden' id='receivingId' name='receivingId'/>
	<form id='dialogAddAddressForm' method='post' >


			<table>
				<tr>
					<th>销售人</th>
					<td><input type='text' name="sellerName" id='sellerName' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>国家</th>
					<td><input type='text'   name="country" id='country' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>省</th>
					<td><select  name="province" id='province'>
						<option value="selected">请选择省份</option>

						</select>
					</td>
				</tr>
				<tr>
					<th>市</th>
					<td><input type='text' name="city" id='city' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>区</th>
					<td><input type='text' name="district" id='district' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>地址</th>
					<td><input type='text' name="deliveryAddress" id='deliveryAddress' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>邮编</th>
					<td><input type='text' name="zipcode"  id='zipcode' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input type='text' name="contacts" id='contacts' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td><input type='text' name="phone"  id='phone' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>是否默认</th>
					<td><input type='text' id='isDefault' name="isDefault" class='easyui-textbox' size='16' data-options='required:true'/></td>
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
<script>
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


        /*$("#dialogAddAddressForm").form('submit', {
            url : url,
            onSubmit : function(){
                console.log("1");
                if(dialogAddAddressForm.form('validate')){
                    $.messager.progress({
                        text : '<spring:message code="common.message.data.processing"/>', interval : 100
                    });
                    return true;
                }else{
                    return false;
                }
            },
            success : function(data) {
                var msg='';
                try {
                    var result = $.parseJSON(data);
                    if(result.success){
                        msg = result.msg;
                        ezuiDetailsDatagrid.datagrid('reload');
                        dialogAddAddress.dialog('close');
                    }else{
                        msg = '<font color="red">' + result.msg + '</font>';
                    }
                } catch (e) {
                    msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                    msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
                } finally {
                    $.messager.show({
                        msg : msg, title : '<spring:message code="common.message.prompt"/>'
                    });
                    $.messager.progress('close');
                }
            }
        });*/
    }


   /* var provinceId = $('#provinceId').combobox({
        url: '/Sys/SSQManage/GetProvince',
        editable: false,
        valueField: 'ProvinceID',
        textField: 'ProName',
        onSelect: function (record) {
            //刷新数据，重新读取省份下的城市，并清空当前输入的值
            cityId.combobox({
                disabled: false,
                url: '/sys/SSQManage/GetCityByProID?ProID=' + record.ProvinceID,
                valueField: 'CityID',
                textField: 'CityName'
            }).combobox('clear');
            //清空区
            areaId.combobox({}).combobox('clear');
        }
    });
    var cityId = $('#cityId').combobox({
        disabled: true,
        valueField: 'CityID', //值字段
        textField: 'CityName', //显示的字段
        url: '/sys/SSQManage/GetCityByProID?ProID=' + $('.Select-Province').combobox('getValue'),
        onSelect: function (record) {
            //刷新数据，重新读取市下的区，并清空当前输入的值
            areaId.combobox({
                disabled: false,
                url: '/sys/SSQManage/GetAreaByCityID?CityID=' + record.CityID,
                valueField: 'AreaID',
                textField: 'AreaName'
            }).combobox('clear');
        }
    });

    var areaId = $('#areaId').combobox({
        disabled: true,
        valueField: 'AreaID', //值字段
        textField: 'AreaName', //显示的字段
        url: '/sys/SSQManage/GetAreaByCityID?CityID=' + $('#cityId').combobox('getValue')
    });*/
</script>