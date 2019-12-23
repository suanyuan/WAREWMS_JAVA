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
		url : '<c:url value="/drugInspectionController.do?showSearchInvLocationDatagrid"/>',
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
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		queryParams:{
			reservedfield09:1,
		},
		columns : [[
			{field: 'enterpriseName',		title: '委托方企业名称',	width: 150 },
			{field: 'lotatt03',		        title: '入库日期',	width: 100 },
			{field: 'lotatt12',		        title: '产品名称',	width: 150 },
			{field: 'descrc',		        title: '规格/型号',	width: 150 },
			{field: 'lotatt15',		        title: '生产企业',	width: 150 },
			{field: 'lotatt06',		        title: '产品注册证号/备案凭证号 ',	width: 150 },
			{field: 'lotatt04',		        title: '生产批号/序列号',	width: 100 },
			// {field: 'lotatt05',		        title: '序列号',	width: 100 },
			{field: 'lotatt01Andlotatt02',		        title: '生产日期和有效期(或者失效期)',	width: 200 },
			// {field: 'qty',                  title: '库存件数 ',	width: 100 },
			{field: 'qtyeach',		        title: '库存数量 ',	width: 100 },
			{field: 'uom',                  title: '单位 ',	width: 100 },
			{field: 'locationid',		    title: '库存地点(货架号)',	width: 130 },
			{field: 'lotatt11',		        title: '储存条件',	width: 130 },
			{field: 'lotatt10',	            title: '质量状态',	width: 100,formatter:ZL_TYPstatusFormatter},
			{field: 'notes',		        title: '备注 ',	width: 200},
			{field: 'qty1',		           title: '换算率 ',	width: 70,hidden:true},


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
			ajaxBtn($('#menuId').val(), '<c:url value="/drugInspectionController.do?getBtn"/>', ezuiMenu);
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
		enterpriseName:$('#enterpriseName').combobox('getValue'),
		lotatt12:$('#lotatt12').val(),
		descrc:$('#descrc').val(),
		lotatt15:$('#lotatt15').val(),
		lotatt04 : $('#lotatt04').val(),
		lotatt05 : $('#lotatt05').val(),
		lotatt06 : $('#lotatt06').val(),
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
		param.put("lotatt12",$('#lotatt12').val());
		param.put("descrc",$('#descrc').val());
		param.put("lotatt15",$('#lotatt15').val());
		param.put("lotatt04",$('#lotatt04').val());
		param.put("lotatt05",$('#lotatt05').val());
		param.put("lotatt06",$('#lotatt06').val());
        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/drugInspectionController.do?exportSearchInvLocationDataToExcel", param);
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
						<tr >
						<th>委托方企业名称</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='16' data-options=''/></td>
						<th>产品名称</th><td><input type='text' id='lotatt12' class='easyui-textbox' size='16' data-options=''/></td>
						<th>规格</th><td><input type='text' id='descrc' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr >
							<th>生产企业</th><td><input type='text' id='lotatt15' class='easyui-textbox' size='16' data-options=''/></td>
							<th>生产批号</th><td><input type='text' id='lotatt04' class='easyui-textbox' size='16' data-options=''/></td>
							<th>序列号</th><td><input type='text' id='lotatt05' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr >
							<th>产品注册证号/备案凭证号</th><td><input type='text' id='lotatt06' class='easyui-textbox' size='16' data-options=''/></td>
							<td colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

							</td>
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
