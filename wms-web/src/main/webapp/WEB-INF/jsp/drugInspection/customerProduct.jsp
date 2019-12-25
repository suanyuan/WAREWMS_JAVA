<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
/* 初始化 */
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();


	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/drugInspectionController.do?showCustomerProductDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: false,
		striped: true,
        rowStyler:function(index,row){
            if(row.activeFlag == "0" ){
                return 'color:red;';
            }
        },
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'enterpriseName',
		queryParams: {
			customerType:'OW',
			reservedfield09:1,

		},
		columns : [[
			{field: 'enterpriseName',		title: '委托方企业名称 ',	width: 200 },
			{field: 'productName',		title: '委托方货品名称',	width: 150 },
			{field: 'specsName',		    title: '规格/型号',	width: 150 },
			// {field: 'productModel',		title: '型号',	width: 150 },
			{field: 'productRegisterNo',		        title: '产品注册证号/备案凭证号 ',	width: 200 },
            {field: 'approveDate',		title: '批准日期 ',	width: 200,formatter:dateFormat2 },
            {field: 'productRegisterExpiryDate',		title: '有效期',	width: 200 ,formatter:dateFormat2 },
			{field: 'enterpriseSc',		title: '生产企业 ',	width: 500 },
			{field: 'licenseOrRecordNo',		    title: '生产企业许可证号/备案凭证号 ',	width: 200 },
			{field: 'unit',	title: '单位 ',	width: 120 },
			{field: 'storageCondition',title: '储存条件 ',	width: 150 },
			// {field: 'recordNo',		        title: '备案凭证号 ',	width: 150 },
			// {field: 'registrationAuthorityR',title: '发证机关 ',	width: 150 },
			// {field: 'clientStartDate',		title: '委托/合同开始时间 ',	width: 130 },
			// {field: 'clientEndDate',		title: '委托/合同结束时间 ',	width: 130 },
			// {field: 'clientTerm',	        title: '委托/合同期限',	width: 100 ,formatter:day},
			// {field: 'isChineseLabel',		title: '是否贴中文标签 ',	width: 110,formatter:yesOrNoFormatter},
			// {field: 'clientContent',		title: '委托业务范围',	width: 200 },


		]],
		onDblClickCell: function(index,field,value){

		},
		onRowContextMenu : function(event, rowIndex, rowData) {
			event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			});
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/basCustomerController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});


    //委托方下拉框
    $('#enterpriseName').combobox({
        // panelHeight: 'auto',
        url:sy.bp()+'/basCustomerController.do?getCustomerNameCombobox&type=client',
        valueField:'id',
        textField:'value'
    });

});


/* 查询 */
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		enterpriseName : $('#enterpriseName').combobox('getValue'),
		productName : $('#productName').val(),
		specsName : $('#specsName').val(),
		productRegisterNo : $('#productRegisterNo').val(),
		enterpriseSc : $('#enterpriseSc').val(),
		activeFlag : $('#activeFlag').combobox('getValue'),
		reservedfield09 : '1'
	});
};

/* 导出start */
var doExport = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        param.put("enterpriseName",$('#enterpriseName').combobox('getValue'));
        param.put("productName",$('#productName').val());
        param.put("specsName",$('#specsName').val());
        param.put("productRegisterNo",$('#productRegisterNo').val());
        param.put("enterpriseSc",$('#enterpriseSc').val());
		param.put("activeFlag",$('#activeFlag').combobox('getValue'));

		//--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/drugInspectionController.do?exportCustomerProductDataToExcel", param);
        downloadCheckTimer = window.setInterval(function () {
            window.clearInterval(downloadCheckTimer);
            $('#'+formId).remove();
            $('#ezuiBtn_export').linkbutton('enable');
            $.messager.progress('close');
            $.messager.show({
                msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
            });
        }, 1000);
    }else{
        $.messager.show({
            msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
        });
    }
};
/* 导出end */


</script>
</head>
<body>


	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table style="text-align: right">
						<tr>
							<th>委托方企业名称</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>委托方货品名称</th><td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>规格</th><td><input type='text' id='specsName' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>产品注册证号/备案凭证号</th><td><input type='text' id='productRegisterNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产企业</th><td><input type='text' id='enterpriseSc' class='easyui-textbox' size='16' data-options=''/></td>
							<th >是否合作</th>
							<td>
								<select id="activeFlag" class="easyui-combobox"  style="width:135px;" data-options="panelHeight:'auto',">
									<option value=""></option>
									<option value="1">是</option>
									<option value="0">否</option>
								</select>
							</td>

							<td colspan="2">
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>

								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

							</td>
						</tr>






						</tr>
					</table>
				</fieldset>
				<div>
					<%--<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
<%--					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.update'/></a>--%>
<%--					<a onclick='goon();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.goon'/></a>--%>
<%--					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>--%>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>

</body>
</html>
