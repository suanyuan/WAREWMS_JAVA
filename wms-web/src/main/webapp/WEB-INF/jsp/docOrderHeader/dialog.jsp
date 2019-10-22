<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiDialog' class='easyui-dialog' style='padding: 10px;'>
	<form id='ezuiForm' method='post' >
		<input type='hidden' id='docOrderHeaderId' name='docOrderHeaderId'/>
		<fieldset>
			<legend><spring:message code='common.button.orderQuery'/></legend>
			<table>
				<tr>
					<th>货主</th>
					<td><input type='text' id='customerid' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>SO编号</th>
					<td><input type='text' id='orderno' name='orderno' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>客户单号</th>
					<td><input type='text' id='soreference1' name='soreference1' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>定向入库单号</th>
					<td><input type='text' id='soreference2' name='soreference2' class='easyui-textbox' size='20' data-options='required:false'/></td>

				</tr>
				<tr>
					<th>订单状态</th>
					<td><input type='text' id='sostatus' name='sostatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																														editable: false,
																														url:'<c:url value="/docOrderHeaderController.do?getOrderStatusCombobox"/>',
																														valueField: 'id',
																														textField: 'value'"/></td>
					<th>释放状态</th>
					<td><input type='text' id='releasestatus' name='releasestatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																												editable: false,
																												url:'<c:url value="/docOrderHeaderController.do?getReleasestatusCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/></td>

					<th>订单类型</th>
					<td><input type='text' id='ordertype' name='ordertype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																											editable: false,
																											url:'<c:url value="/docOrderHeaderController.do?getOrderTypeCombobox"/>',
																											valueField: 'id',
																											textField: 'value',
																											onSelect:function(value){
																												choseOrderTypeAfter(value);
																											}
																											"/></td>
					<th>结算方式</th>
					<td><input type='text' id='stop' name='stop' data-options=''/></td>

				</tr>
				<tr>
					<th>收货单位</th>
					<td><input type='text' id='consigneeid' name='consigneeid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>联系人</th>
					<td><input type='text' id='cContact' name='cContact' class='easyui-textbox' size='16' data-options='editable: true'/></td>
					<th>联系方式</th>
					<td><input type='text' id='cTel1' name='cTel1' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>备注</th>
					<td colspan="3"><input type='text' id='notes' name='notes' class='easyui-textbox' size='20' style="height:30px" data-options="multiline:true,validType:['length[200]']"/></td>


				</tr>
				<tr>
					<th>收货地址</th>
					<td colspan="3"><input type='text' id='cAddress1' name='cAddress1' class='easyui-textbox' size='45' data-options='required:true'/></td>
					<th>快递公司</th>
					<td><input type='text' id='carrierid' name='carrierid'  data-options=''/></td>
					<%--<th>订单发运时间</th>--%>
					<%--<td><input type='text' id='lastshipmenttime' name='lastshipmenttime' class='easyui-textbox' size='16' data-options='editable: false'/></td>--%>
					<th>发运方式</th>
					<td><input type='text' id='route' name='route' data-options=''/></td>
				</tr>
				<tr>
					<th>温度记录表</th>
					<td colspan="2">
						<input type='text' id='coldTag' name="coldTag"  size='16' data-options=''/>
						<input type="hidden" id='door' name="door" />
						<a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlColdTag()">查看</a>
					</td>
					<%--<th>订单创建时间</th>--%>
					<%--<td><input type='text' id='ordertime' name='ordertime' class='easyui-textbox' size='16' data-options='editable: false'/></td>--%>

					<!--<th>最后修改时间</th>
					<td><input type='text' id='edittime' name='edittime' class='easyui-textbox' size='16' data-options='editable: false'/></td>
					<th>最后修改人</th>
					<td><input type='text' id='editwho' name='editwho' class='easyui-textbox' size='16' data-options='editable: false'/></td>-->
					<td colspan="2">
						<a onclick='commit();' id='ezuiBtn_orderCommit' class='easyui-linkbutton' data-options='iconCls:"icon-save"' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
						<a onclick='copyDetailGo();' id='ezuiBtn_copyDetailGo' class='easyui-linkbutton'
						   data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>明细复用</a>
					</td>
				</tr>
			</table>
		</fieldset>
	</form>
	<div class="easyui-tabs" style="width:950px;height:200px">
		<div title="出库明细" style="padding:3px">
			<table id='ezuiDetailsDatagrid'></table>
		</div>
		<div title="分配明细" style="padding:0px">
			<table id='allocationDetailsDatagrid'></table>
		</div>
	</div>
	<form>
		<div>
			<a onclick='detailsAdd();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.skuAdd'/></a>
			<a onclick='detailsDel();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.skuDelete'/></a>
			<a onclick='detailsEdit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.skuEdit'/></a>
			<a onclick='clearDatagridSelected("#ezuiDetailsDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
		</div>
	</form>
</div>
<div id='ezuiDialogBtn'>
	<%-- <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
	<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $("#coldTag").filebox({
        prompt: '选择一个文件',//文本说明文件
        width:150,
        buttonText: '上传',  //按钮说明文字
        onChange:function(data){
            if(data){
                doUploadColdTag(data);
            }
        }
    });

    function doUploadColdTag(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("coldTag")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                //$("#userdefine4").filebox("setValue",data.comment);
                //$("#ezuiForm input[name='userdefine4']").eq(0).val(data.comment);
                document.getElementsByName("door")[0].value = data.comment;
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }

    function viewUrlColdTag(url) {
        if(url){
            showUrl(url);
        }
    }
</script>
<script>
	$(function () {
        // row =  ezuiDatagrid.datagrid('getSelection');
        // alert(row.door);

        $("#carrierid").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/basCarrierLicenseController.do?getCombobox',
            valueField:'id',
            textField:'value',
			// size:'',
			width:145
        });

        $("#route").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?sendFunction',
            valueField:'id',
            textField:'value',
            width:172
        });

        $("#stop").combobox({
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?settlement',
            valueField:'id',
            textField:'value',
            width:172
        });

        // $('#coldTag').val(row.door);
        // var a = $("#door").val();
        // $("#codeTag").textbox("setValue",a);
    })
</script>