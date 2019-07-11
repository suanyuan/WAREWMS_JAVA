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
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    var ezuiDialog;

	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/firstReviewLogController.do?showDatagrid"/>',
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
		idField : 'id',
		columns : [[
			{field: 'reviewId',		title: '主键',	width: 57 ,hidden:true},
			{field: 'reviewTypeId',		title: '申请单编号',	width: 57 },
			{field: 'applyContent',		title: '内容',	width: 57 },
			{field: 'applyState',		title: '状态',	width: 57 ,
				formatter:checkStateTypeFormatter
			},
			{field: 'checkIdQc',		title: '质量部审核人',	width: 57 },
			{field: 'checkDateQc',		title: '审核时间',	width: 57 },
			{field: 'checkRemarkQc',		title: '备注',	width: 57 },
			{field: 'checkIdHead',		title: '负责人审核',	width: 57 },
			{field: 'checkDateHead',		title: '负责人审核时间',	width: 57 },
			{field: 'checkRemarkHead',		title: '负责人审核说明',	width: 57 },
			{field: 'createDate',		title: '创建时间',	width: 57 ,formatter:dateFormat2}
		]],
        onDblClickRow: function(index,row){
			edit(row);
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

		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

	$("#applyState").combobox({
        url:sy.bp()+'/commonController.do?checkState',
        valueField:'id',
        textField:'value'
    });
});

var edit = function(row){
	if(row){

	}
};
var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		reviewTypeId : $('#reviewTypeId').val(),
		applyState : $('#applyState').val(),
		createDateBegin : $('#createDateBegin').val(),
        createDateEnd : $('#createDateEnd').val()
	});
};

function showCheck() {
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        width:300,
        height:300,
        cache: false,
		buttons:"#ezuiDialogBtn",
        onClose : function() {
            ezuiFormClear(ezuiForm);
            $("#remark").textbox("setValue","");
        }
    });
}

//审核
function doCheck() {
    $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认要进行审核吗', function(confirm) {
        if (confirm) {
            var row = ezuiDatagrid.datagrid("getSelections");
            var arr = new Array();
            for(var i=0;i<row.length;i++){
                arr.push(row[i].reviewId);
            }
            if(row){
                $.ajax({
                    url : sy.bp()+"/firstReviewLogController.do?check",
                    data : {"id":arr.join(","),"remark":$("#remark").val()},type : 'POST', dataType : 'JSON',async  :true,
                    success : function(result){
                        var msg='';
                        try{
                            if(result.success){
                                msg = result.msg;
                                ezuiDatagrid.datagrid('reload');
                                ezuiDialog.dialog('close');
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

        }
    })

}

function returnCheck() {
	
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
							<!-- <th>类型</th><td><input type='text' id='reviewTypeId' class='easyui-textbox' size='16' data-options=''/></td>-->
							<th>状态</th><td><input type='text' id='applyState' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建时间</th><td><input type='text' id='createDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查询</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='showCheck()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>审核</a>
							</td>
						</tr>
					</table>
				</fieldset>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>
		<table>
			<tr>
				<th>备注</th>
				<td><input type='text' id='remark' class='easyui-textbox' data-options='multiline:true,height:180,width:230'/></td>
			</tr>
		</table>
	</div>
	<div id="showDialog"></div>

	<div id='ezuiDialogBtn'>
		<a onclick='doCheck();' class='easyui-linkbutton' href='javascript:void(0);'>通过</a>
		<a onclick='returnCheck();' class='easyui-linkbutton' href='javascript:void(0);'>驳回</a>
	</div>
</body>
</html>
