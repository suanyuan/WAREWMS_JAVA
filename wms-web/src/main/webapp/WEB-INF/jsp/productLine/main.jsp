<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>

var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
var url="/productLineController.do?toAddProduct";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	//ezuiForm = $('#ezuiForm').form();

	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '/productLineController.do?showDatagrid',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField: 'productLineId',
        rowStyler: function (index, row) {
            if(row.isUse == "0"){  return 'color:red;';}
        },
		columns : [[
            {field: 'isUse',		title: '是否有效',	width: 88 ,formatter:function(value,rowData,rowIndex){
                    return rowData.isUse == '1' ? '是' : '否';
                }},
            {field: 'serialFlag',		title: '出库记录序列号',	width: 88 ,formatter:function(value,rowData,rowIndex){
                    return rowData.serialFlag == '1' ? '是' : '否';
                }},
			{field: 'productLineId',		title: '产品线名称',	width: 88,hidden:true },
			{field: 'name',		title: '产品线名称',	width: 88 },
			{field: 'descrC',		title: '货主',	width: 88 },
			//{field: 'name',		title: '货主',	width: 88,hidden:true },
            {field: 'riskAssessment',		title: '风险评估',	width: 88 },

            {field: 'expression',		title: '说明',	width: 88 },

			{field: 'createId',		title: '创建人',	width: 88 },
			{field: 'createDate',		title: '创建日期',	width: 88 },
			{field: 'editId',		title: '修改人',	width: 88 },
			{field: 'editDate',		title: '修改日期',	width: 88 },


		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
			event.preventDefault();
			$(this).datagrid('unselectAll');
			$(this).datagrid('selectRow', rowIndex);
			ezuiMenu.menu('show', {
				left : event.pageX,
				top : event.pageY
			})
		},onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/productLineController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});

});
var add = function(){
	processType = 'add';
	$('#productLineId').val(0);
    $('#ezuiDialog').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        href:url,
		height:250,
		width:300,
        buttons : '#ezuiDialogBtn',
        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    }).dialog('open');
};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
        $('#ezuiForm').form('load',{
			//productLineId : row.productLineId,
            enterpriseName : row.enterpriseName,
            descrC : row.descrC,
            expression : row.expression,
			customerid:row.customerid
		});
        //$("#ezuiForm input[name='enterpriseName']").textbox('setValue', row.enterpriseName);
        $('#ezuiDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:url+'&productLineId='+row.productLineId,
            height:250,
            width:300,
            buttons : '#ezuiDialogBtn',
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('open');

	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'productLineController.do?delete',
					data : {productLineId : row.productLineId},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '<spring:message code="common.message.data.delete.failed"/>';
						} finally {
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
							ezuiDatagrid.datagrid('reload');
						}
					}
				});
			}
		});
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};



var doSearch = function(){
	ezuiDatagrid.datagrid('load',{
        descrC : $('#descr').val(),
		name : $('#name').val(),
		expression : $("#expression").val(),
        riskAssessment: $("#riskAssessment").combobox("getValue")
	});
};

var commit1 = function(){
    var url = '';
    if (processType == 'edit') {
        url = '/productLineController.do?edit';
    }else{
        url = '/productLineController.do?add';
    }
    $('#ezuiForm').form('submit', {
        url : url,
        onSubmit : function(){
            if($('#ezuiForm').form('validate')){
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
                    ezuiDatagrid.datagrid('reload');
                    $('#ezuiDialog').dialog('close');
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
    });

}
</script>

</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>

							<th>产品线名称：</th><td><input type='text' id='name' class='easyui-textbox' size='8' /></td>
							<th>货主：</th><td><input type='text' id='descr' class='easyui-textbox' size='8' /></td>
							<th>说明：</th><td><input type='text' id='expression' class='easyui-textbox' size='8' /></td>

							<th>风险评估：</th><td><input type="text" id="riskAssessment"  name="isUse"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '已通过', value: '已通过'},
																																	{id: '未通过', value: '未通过'},
																																	{id: '未评估', value: '未评估'},
																																	{id: '无需评估', value: '无需评估'}
																																]"/></td>
							<td>
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'>

			</table>
		</div>
	</div>
	<div id="ezuiDialog">


	</div>





	<div id='ezuiDialogBtn'>
		<a onclick='commit1();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<%--<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>--%>


</body>
</html>
