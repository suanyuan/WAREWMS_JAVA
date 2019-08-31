<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>

<div id='enterpriseProduct' style='padding: 10px;'>
	<form id='dialogAddAddressForm' method='post' >

	<input  id='r'  type="hidden" name='receivingId' value="${receivingId}" class='textbox-value' />
	<input  id='rece'  type="hidden" name='receivingAddressId' value="${gspReceivingAddress.receivingAddressId}" class='textbox-value' />
	<%--<input    name='receivingId' value="${receivingId}" class='easyui-textbox' />--%>

			<table>
				<tr>
					<th>供应商</th>
					<td>
						<input type='text'  id='supplierName' class='easyui-textbox' data-options='required:true,width:200'/>
						<input type="hidden" name="supplierId" id="supplierId" />
						<input type="hidden" name="sup_enterpriseId" id="sup_enterpriseId" />
						<a href="javascript:void(0);" class="easyui-linkbutton"  data-options="" onclick="viewSupplierEnterpriseUrl()">查看</a>

					</td>
				</tr>
				<tr>
					<th>产品</th>
					<%--<td><input type='text' name="sellerName" value="${gspReceivingAddress.sellerName}"  class='easyui-textbox' size='20' data-options='required:true'/></td>--%>
					<td>
						<input type='text'  id='productNameP' class='easyui-textbox' data-options='required:true,width:200'/>
						<input type="hidden" name="specsId" id="specsId" />
					</td>
				</tr>

				<%--<tr>--%>
					<%--<th>供应商</th>--%>
					<%--<td><input type='text' name="zipcode" value="${gspReceivingAddress.zipcode}"   class='easyui-textbox' size='20' data-options='required:true'/></td>--%>
				<%--</tr>--%>

			</table>

	</form>
</div>

<%--供应商--%>
<div id='ezuiDialogSupplierDetail' style='padding: 10px;display: none' >
	<div id='TB' class='datagrid-toolbar' style=''>
		<fieldset>
			<legend>供应商信息</legend>
			<table>
				<tr>
					<th>客户代码：</th><td><input id="kehudaima" type='text'   class='easyui-textbox' data-options='width:200'/></td>
					<th>客户名称：</th><td><input id="kehumingcheng" type='text'   class='easyui-textbox' data-options='width:200'/></td>
                    <td>
                        <a onclick='doSearchSupplier();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <%--<a onclick='choseSupplierSelect();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
                    </td>
                </tr>

					<%--<th>企业信息代码：</th><td><input id="qiyexinxidaima" type='text'  class='easyui-textbox' data-options=''/></td>
                    <th>企业名称：</th><td><input id="qiyemingcheng1" type='text'  class='easyui-textbox' data-options=''/></td>--%>
					<%--<td>
                        <input type='text' style="width: 170px;"/>
                        <input type="hidden" class="easyui-textvalue" name="enterpriseId">
                        <!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
                    </td>--%>

			</table>
		</fieldset>

	</div>
	<table id="dataGridSupplierDetail">

	</table>
</div>

<!--产品查询列表dialog -->
<div id='ezuiDialogSpec_PAS' style='padding: 10px;display: none' >
	<div id='productToolbar' class='datagrid-toolbar' style=''>
		<fieldset>
			<legend>产品注册证信息</legend>
			<table>
				<tr>
					<th>产品代码</th>
					<td><input type='text' id='productCode1' class='easyui-textbox'    data-options='width:200'/></td>
					<th>产品名称</th>
					<td><input type='text' id='productName1' class='easyui-textbox'   data-options='width:200'/>
						<a onclick='searchProduct1();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
						<a onclick='choseProductSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
					</td>



					<%--<td>--%>
						<%--<a onclick='searchProduct();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>--%>
						<%--<a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>--%>
					<%--</td>--%>
				</tr>
				<tr>
					<th>规格</th>
					<td><input type='text' id='specsName' class='easyui-textbox'  data-options='width:200'/></td>

					<th>注册证号</th>
					<td><input type='text' id='registerNo' class='easyui-textbox'   data-options='width:200'/></td>

				</tr>
			</table>
		</fieldset>
	</div>
	<table id="dataGridProduct2">

	</table>
</div>


<div id='ezuiDialogSupplierEnterprise' style='padding: 10px;'>
<script>
	var ezuiDialogReceivingDetail;
	var dataGridReceivingDetail;
	var dialogReceiving;
    var ezuiDialogSupplierDetail;
	var  newreceivingId;
    var ezuiDialogSpec_PAS;
    var dataGridProduct2;
    var ezuiDialogSupplierEnterprise;
    var rows;
    var rows1;
    $(function () {

        //供应商
        supplierDatagrid = $("#dataGridSupplierDetail").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#TB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                activeFlag :'1',
                customerType:'VE'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'clientId',
            columns : [[
                {field: 'customerType',	title: '客户类型 ',	width: 100,formatter:function(value,rowData,rowIndex){
                        if (rowData.customerType=='CO') {
                            return rowData.customerType='收货单位';
                        }else if (rowData.customerType=='VE'){
                            return rowData.customerType='供应商';
                        }else if (rowData.customerType=='CA'){
                            return rowData.customerType='承运商';
                        }else if (rowData.customerType=='OT'){
                            return rowData.customerType='其他';
                        }else if (rowData.customerType=='OW'){
                            return rowData.customerType='货主';
                        }else if (rowData.customerType=='PR'){
                            return rowData.customerType='生产企业';
                        }else if (rowData.customerType=='WH'){
                            return rowData.customerType='主体';
                        }
                    } },
                {field: 'activeFlag',		title: '是否合作 ',	width: 100,formatter:function(value,rowData,rowIndex){
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }},
                {field: 'customerid',		title: '客户代码',	width: 150 },
                {field: 'descrC',		title: '客户名称',	width: 150 },
                {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 80 },
                {field: 'shorthandName',		title: '简称 ',	width: 85 },
                {field: 'enterpriseName',		title: '企业名称 ',	width: 80 }
            ]],
            onDblClickCell: function(index,field,value){
                choseSupplierSelect();
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
        //赋值放大镜
        $("#supplierName").textbox({
            <%--value:"${firstBusinessApply.supplierName}",--%>
            width:350,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchSupplier();
                }
            }]
        });
		//赋值放大镜
        $("#productNameP").textbox({
            <%--value:"${productName}",--%>
            width:350,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchProduct();
                }
            }]
        });
		//搜索打开供应商弹窗
        function searchSupplier() {
            // $('#ezuiDialogSupplierDetail').dialog('destroy');
            ezuiDialogSupplierDetail = $('#ezuiDialogSupplierDetail').dialog({
                modal : true,
                title : '<spring:message code="common.dialog.title"/>',
                width:850,
                height:500,
                cache: false,
                onClose : function() {

                    // ezuiFormClear(ezuiForm);
                    // $(this).dialog('destroy');
                }

            });
            // if(ezuiDialogSupplierDetail){
            //     ezuiDialogSupplierDetail.dialog('open');
            //     $('#ezuiDialogSupplierDetail').dialog('destroy');
            // }
        }

		//供应商弹窗

		//双击选中供应商
        function choseSupplierSelect() {
            rows1 = supplierDatagrid.datagrid("getSelected");
            console.log(rows1);
            if(rows1){
                $("#supplierId").val(rows1.customerid);
                $("#supplierName").textbox("setValue",rows1.descrC);
                $("#sup_enterpriseId").val(rows1.enterpriseId);

                ezuiDialogSupplierDetail.dialog('close');
                console.log($("#supplierId").val());
            }
        }
        // debugger
        //产品
        dataGridProduct2 = $("#dataGridProduct2").datagrid({

            url : sy.bp()+'/gspProductRegisterSpecsController.do?showDatagrid',
            method:'POST',
            toolbar : '#productToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            fit:true,
            pagination:true,
            singleSelect:false,
            queryParams:{
                isUse : '1',
                type:'CER',
                // supplierId:$("#supplierId").val(),
            },
            rownumbers:true,
            idField : 'specsId',
            columns : [[
                // {field: 'ck',checkbox:true },
                {field: 'specsId',title: '主键' ,hidden:true},
                {field: 'productCode',title: '产品代码' ,width: '15%'},
                {field: 'productName',title: '产品名称',width: '40%'},
                {field: 'specsName',title: '规格' ,width: '10%'},
                {field: 'productRegisterNo',title: '产品注册证',width: '20%'},

            ]],
            onDblClickCell: function(index,field,value){
                // choseProductSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                <%--$(this).datagrid("resize",{height:540});--%>
                <%--$.ajax({--%>
                    <%--url : '/firstBusinessApplyController.do?showSpecsDatagrid',--%>
                    <%--data : {'applyId':"<c:choose><c:when test="${firstBusinessApply.applyId == null}">'empty'</c:when><c:otherwise>'${firstBusinessApply.applyId}'</c:otherwise></c:choose>"},--%>
                    <%--type : 'POST',--%>
                    <%--dataType : 'JSON',--%>
                    <%--success : function(result){--%>
                        <%--//console.log(result);--%>
                        <%--if(result){--%>
                            <%--for(var i=0;i<result.rows.length;i++){--%>
                                <%--//console.log(result.rows[i]);--%>
                                <%--dataGridProduct.datagrid("selectRecord",result.rows[i].specsId);--%>
                            <%--}--%>
                        <%--}--%>
                    <%--}--%>
                <%--});--%>
            }
        });

		//搜索打开产品弹窗
        function searchProduct() {
            // if(ezuiDialogSpec_PAS){
            //     ezuiDialogSpec.dialog('open');
            //产品

            ezuiDialogSpec_PAS = $('#ezuiDialogSpec_PAS').dialog({
                modal : true,
                title : '<spring:message code="common.dialog.title"/>',
                width:850,
                height:500,
                cache: false,
                onClose : function() {
                    // ezuiFormClear(ezuiForm);
                    // $(this).dialog('destroy');

                }
            });
        }





        // $("#dialogAddAddressForm input[name='isDefault']").combobox({
        //     url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        //     valueField:'id',
        //     textField:'value'
        // });
        // console.log($("#hiddenreceivingId").val());
    });

    //供应商弹窗搜索功能
    function doSearchSupplier() {
        supplierDatagrid.datagrid('load', {
            // enterpriseName : $('#qiyemingcheng1').val(),
            // enterpriseNo : $('#qiyexinxidaima').val(),
            descrC : $('#kehumingcheng').val(),
            customerid : $('#kehudaima').val(),
            activeFlag : '1',
            isUse : '1',
            customerType:'VE'
        });
    }
    //查询产品
    function searchProduct1() {
        dataGridProduct2.datagrid("reload", {

            "productCode":$("#ezuiDialogSpec_PAS #productCode1").val(),
            "productName":$("#ezuiDialogSpec_PAS #productName1").val(),
            "productRegisterNo":$("#ezuiDialogSpec_PAS #registerNo").val(),
            "specsName":$("#ezuiDialogSpec_PAS #specsName").val(),

            "isUse":"1",
            // "supplierId" : $('#supplierId').val(),
            "type":"CER"
        })
    }


    //双击选中产品
    function choseProductSelect() {
        // var row = dataGridProduct2.datagrid("getSelected");



		rows = dataGridProduct2.datagrid('getSelections');
        var arrP = new Array();
        var arrPId = new Array();
        for(var i=0;i<rows.length;i++){
            arrP.push(rows[i].specsId);
            arrPId.push(rows[i].productName);
        }
        arrP.join(",");
        arrPId.join(",");

        console.log(rows);
        if(rows){
            $("#dialogAddAddressForm #specsId").val(arrP);
            $("#productNameP").textbox("setValue",arrPId);
            // $("#productLine").combobox({
            //     url:'/firstBusinessApplyController.do?getProductLineByEnterpriseId&customerId='+row.customerid,
            //     valueField:'id',
            //     textField:'value',
            //     onLoadSuccess:function () {
            //
            //     }
            // })
            ezuiDialogSpec_PAS.dialog('close');
        }
    }

    //查看供应商企业详情
    function viewSupplierEnterpriseUrl() {
        $(function() {
            ezuiDialogSupplierEnterprise = $('#ezuiDialogSupplierEnterprise').dialog({
                modal : true,
                title : '<spring:message code="common.dialog.title"/>',
                buttons : '',
                href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
                width:1200,
                height:530,
                closable:true,
                cache: false,
                onClose : function() {
                    ezuiFormClear(ezuiDialogSupplierEnterprise);
                }
            }).dialog('close');
        })
        //processType = 'edit';

        //var row = ezuiDatagrid.datagrid('getSelected');
        console.log($("#dialogAddAddressForm input[id='sup_enterpriseId']").val());
        var enterpriseId = $("#dialogAddAddressForm input[id='sup_enterpriseId']").val();
        if(enterpriseId==null || enterpriseId==""){
            // enterpriseId = $("#enterpriseId").val();
        }

        if(enterpriseId!=null && enterpriseId!="" ){
            ezuiDialogSupplierEnterprise.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail"+"&id="+enterpriseId).dialog('open');
            enterpriseId = "";
        }else{
            $.messager.show({
                msg : '请先选择企业', title : '提示'
            });
        }
	}


    //选择
    function doSubmitAddress() {
        // var rows = dataGridProduct.datagrid("getSelections");
		// rows1
		// alert(rows)

        // $("#specsId").val();
        // $("#productNameP").textbox("getValue");
        if(rows){
            for(var i=0;i<rows.length;i++){

                // alert(rows[i].productRegisterId)
                if(arr.indexOf(rows[i].specsId)==-1){
                    // $.ajax({
                    //     url : sy.bp()+"/gspProductRegisterSpecsController.do?getInfo",
                    //     data : {
                    //         "specsId":rows[i].specsId,
                    //     },
                    //     type : 'POST',
                    //     dataType : 'JSON',
                    //     async  :true,
                    //     success : function(result){
                    //     }
                    // });
                    //         console.log(result.obj.productRegisterNo)

							ezuiDatagridDetail1.datagrid("appendRow",{
								"productApplyId":"",
								"applyId":"",
								"specsId":rows[i].specsId,
								"isCheck":"",
								"isOperate":"",
								"isCold":"",
								"createId":"",
								"createDate":"",
								"editId":"",
								"editDate":"",
								"isUse":"",
								"productCode":rows[i].productCode,
								"productName":rows[i].productName,
								"specsName":rows[i].specsName,
								"productModel":rows[i].productModel,
								"supplierName" :rows1.descrC,
								"productRegisterNo":rows[i].productRegisterNo,
                                "productRegisterId":rows[i].productRegisterId,



							});
							arr.push(rows[i].specsId);
							//arr1 = rows1[i].supplierId;
							arr1.push(rows1.customerid);
						// arr1.push(rows1[i].supplierId);

                }

            }

        }
        enterpriseProduct.dialog("close");
    }
    <%--function doSubmitAddress() {--%>
        <%--var infoObj = new Object();--%>
        <%--$("#dialogAddAddressForm input[class='textbox-value']").each(function (index) {--%>
            <%--infoObj[""+$(this).attr("name")+""] = $(this).val();--%>
        <%--});--%>

	<%--var defaultAddress=$('#isDefault').combobox('getValue');--%>


        <%--var url = '';--%>
   <%--if (defaultAddress=='0'){--%>
        <%--if (processTypeAddress == 'editAddress') {--%>
            <%--url = '<c:url value="/gspReceivingAddressController.do?edit"/>';--%>
        <%--}else{--%>
            <%--url = '<c:url value="/gspReceivingAddressController.do?add"/>';--%>
        <%--}--%>

        <%--$.ajax({--%>
            <%--url : url,--%>
            <%--data : {"gspReceivingAddressFormStr":JSON.stringify(infoObj)},type : 'POST', dataType : 'JSON',async  :true,--%>
            <%--success : function(result){--%>
                <%--//console.log(result);--%>
                <%--var msg='';--%>
                <%--try{--%>
                    <%--if(result.success){--%>
                        <%--msg = result.msg;--%>
                        <%--newreceivingId=result.obj;--%>
                        <%--ezuiDetailsDatagrid.datagrid('reload');--%>
                        <%--$("#dialogAddAddress").dialog('close');--%>

                    <%--}else{--%>
                        <%--msg = '<font color="red">' + result.msg + '</font>';--%>
                    <%--}--%>
                <%--}catch (e) {--%>
                    <%--//msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';--%>
                    <%--msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;--%>
                <%--} finally {--%>
                    <%--$.messager.show({--%>
                        <%--msg : msg, title : '<spring:message code="common.message.prompt"/>'--%>
                    <%--});--%>
                    <%--$.messager.progress('close');--%>
                <%--}--%>
            <%--}--%>
        <%--});--%>

    <%--}else {--%>

       <%--if (processTypeAddress == 'editAddress') {--%>
           <%--url = '<c:url value="/gspReceivingAddressController.do?editDefault"/>';--%>
       <%--}else{--%>
           <%--url = '<c:url value="/gspReceivingAddressController.do?addDefault"/>';--%>
       <%--}--%>

       <%--$.ajax({--%>
           <%--url : url,--%>
           <%--data : {"gspReceivingAddressFormStr":JSON.stringify(infoObj)},type : 'POST', dataType : 'JSON',async  :true,--%>
           <%--success : function(result){--%>
               <%--//console.log(result);--%>
               <%--var msg='';--%>
               <%--try{--%>
                   <%--if(result.success){--%>
                       <%--msg = result.msg;--%>
                       <%--newreceivingId=result.obj;--%>
                       <%--ezuiDetailsDatagrid.datagrid('reload');--%>
                       <%--$("#dialogAddAddress").dialog('close');--%>

                   <%--}else{--%>
                       <%--msg = '<font color="red">' + result.msg + '</font>';--%>
                   <%--}--%>
               <%--}catch (e) {--%>
                   <%--//msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';--%>
                   <%--msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;--%>
               <%--} finally {--%>
                   <%--$.messager.show({--%>
                       <%--msg : msg, title : '<spring:message code="common.message.prompt"/>'--%>
                   <%--});--%>
                   <%--$.messager.progress('close');--%>
               <%--}--%>
           <%--}--%>
       <%--});--%>
   <%--}--%>

    <%--}--%>



</script>
