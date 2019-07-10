<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>

<div id='dialogAddAddress' style='padding: 10px;'>
	<form id='dialogAddAddressForm' method='post' >

	<input type='hidden' id='r'  name='receivingId' class='easyui-textvalue' value="${receivingId}"/>

			<table>
				<tr>
					<th>销售人</th>
					<td><input type='text' name="sellerName"  class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>国家</th>
					<td><input type='text'   name="country"  class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>省</th>
					<td>
						<input id="cc1" class="easyui-combobox" name="province" editable="false" data-options="
            	required:true,
                valueField: 'id',
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
            valueField: 'id',
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
						<input id="cc3" class="easyui-combobox" name="city" editable="false" data-options="
            required:true,
            valueField:'id',
            textField:'name'">
					</td>
				</tr>
				<%--<tr>
					<th>省</th>
					<td>
						<input  id="provincesssss"  name="province" class="easyui-combobox"  data-options='required:true' />
					</td>

				</tr>
				<tr>
					<th>市</th>
					<td>
						<input  id="citys"  name="city" class="easyui-combobox"  data-options='required:true' />
					</td>
				</tr>
				<tr>
					<th>区/县</th>
					<td>
						<input  id="area"  name="areas" class="easyui-combobox"  data-options='required:true' />
					</td>
				</tr>--%>

				<%--<tr>
				<th>省</th>
				<td>
					<select id="provincesssss" style="width: 140px" name="province"   data-options='required:true' onchange="changeCity()">
						<option value="34">--请选择省--</option>
					</select>
				</td>

			</tr>
				<tr>
					<th>市</th>
					<td>
						<select id="city" style="width: 140px"  name="city"   onchange="changeDistrict()">
							<option value="3401">--请选择市--</option>
						</select>
					</td>
				</tr>
				<tr>
					<th>区/县</th>
					<td>
						<select style="width: 140px" id="areas"  name="district"   data-options='required:true' onchange="changeDhiddenValue()">
							<option value="341225">--请选择区/县--</option>
						</select>
					</td>
				</tr>
				<input type="hidden" name="phidden" id="phidden">
				<input type="hidden" name="chidden" id="chidden">
				<input type="hidden" name="dhidden" id="dhidden">
--%>

				<%--<tr>
					<th>省</th>
					<td>
						<input id="pcc1" class="easyui-combobox" >
					</td>
				</tr>
				<tr>
					<th>市</th>
					<td>
						<input id="pcc2" class="easyui-combobox" >
					</td>
				</tr>
				<tr>
					<th>区/县</th>
					<td>
						<input id="pcc3" class="easyui-combobox" >
					</td>
				</tr>--%>

				<tr>
					<th>地址</th>
					<td><input type='text' name="deliveryAddress"  class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>邮编</th>
					<td><input type='text' name="zipcode"   class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人</th>
					<td><input type='text' name="contacts"  class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>联系人电话</th>
					<td><input type='text' name="phone"  class='easyui-textbox' size='16' data-options='required:true'/></td>
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
   /* $(function () {
        $('#pcc1').combobox({
            url:'/gspReceivingAddressController.do?getArea&pid=0',
            valueField:'id',
            textField:'name',
            /!*onSelect: function(rec){
                $('#pcc2').combobox('clear');
                $('#pcc3').combobox('clear');
                var url= '/gspReceivingAddressController.do?getArea&pid='+rec.id;
                $('#pcc2').combobox('reload',url);
            }*!/
        });
        /!* $('#pcc2').combobox({

             valueField:'id',
             textField:'name',
             onSelect: function(rec){
                 $('#pcc2').combobox('reload', url);
                 $('#pcc3').combobox('clear');
                 var url = '/gspReceivingAddressController.do?getArea&pid='+rec.id;
                 $('#pcc3').combobox('reload', url);
             }
         });
         $('#pcc3').combobox({
             //url:'gspReceivingAddressController.do?getArea&pid=0',
             valueField:'id',
             textField:'name'
         });*!/
    })*/



        /*$(function(){
           /!* $.ajax({
                url:'/gspReceivingAddressController.do?getArea',
                data:{pid:0},
                type:'POST',
				sync:false,
                dataType:'json',
                success:function(data){
					console.log(data[0]);
                    for(var i=0;i<data.length;i++){
                        var $option = $("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                        $("#dialogAddAddressForm select[name='province']").append($option);
                    }
                }
            })*!/
        });*/

 /*  var changeCity=    function (){
            //当省的内容发生变化的时候，响应的改变省的隐藏域的值
         //   $("#phidden").val($("#provincesssss option:selected").html());
            //页面加载完成，将省的信息加载完成
            //下拉列表框标签对象的val()方法就是选中的option标签的value的属性值
         //  var pida =  $("#provincesssss").val();
       var pron=  document.getElementById("provincesssss");
       var index =  pron.selectedIndex;

       var pid =  pron.options[index].value;
       var pid1 =  pron.options[index].text;
          //  var pid =  $(this).val();
            console.log(pid);
            console.log(pid1);
            $.ajax({
                url:"gspReceivingAddressController.do?getArea",
                data:{pid:pid},
				sync:false,
                dataType:"json",
                success:function(data){
                   //清空城市下拉列表框的内容
                    $("#city").html("<option value=''>-- 请选择市 --</option>");
                    $("#areas").html("<option value=''>-- 请选择区/县 --</option>");
                    //遍历json，json数组中每一个json，都对应一个省的信息，都应该在省的下拉列表框下面添加一个<option>
                    for(var i=0;i<data.length;i++){
                        var $option = $("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                        $("#dialogAddAddressForm select[name='city']").append($option);
                    }
                }
            });
        };

        function changeDistrict(){
            //当城市的内容发生变化的时候，相应的改变城市的隐藏域的值
            $("#chidden").val($("#city option:selected").html());
            //页面加载完成，将省的信息加载完成
            //下拉列表框标签对象的val()方法就是选中的option标签的value的属性值
            var pid = $("#dialogAddAddressForm select[name='city']").val();
            $.ajax({
                url:"gspReceivingAddressController.do?getArea",
                data:{"pid":pid},
                dataType:"json",
                success:function(data){
                    //清空城市下拉列表框的内容
                    $("#dialogAddAddressForm select[name='areas']").html("<option value=''>-- 请选择区/县 --</option>");
                    for(var i=0;i<data.length;i++){
                        var $option = $("<option value='"+data[i].id+"'>"+data[i].name+"</option>");
                        $("#dialogAddAddressForm select[name='areas']").append($option);
                    }
                }
            });
        }

        function changeDhiddenValue(){
            //当城市的内容发生变化的时候，相应的改变城市的隐藏域的值
            $("#dhidden").val($("#dialogAddAddressForm select[name='areas'] option:selected").html());
        }*/






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
