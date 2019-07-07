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
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
    var ezuiDialog;
    function showCheck() {
        ezuiDialog = $('#ezuiDialog').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:300,
            height:300,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
                $("#remark").textbox("setValue","");
            }
        });
    }

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
			{field: 'reviewTypeId',		title: '申请单据类型',	width: 57 },
			{field: 'applyContent',		title: '内容',	width: 57 },
			{field: 'applyState',		title: '状态',	width: 57 ,
				formatter:function (value,row) {
					switch (value) {
						case "00":return "新建";
                        case "10":return "审核中";
                        case "20":return "质量部审核";
                        case "30":return "负责人审核";
                        case "40":return "已通过";
                        case "50":return "未通过";
                        case "60":return "已停止";
                        case "90":return "已报废";
                    }
                }
			},
			{field: 'checkIdQc',		title: '质量部审核人',	width: 57 },
			{field: 'checkDateQc',		title: '审核时间',	width: 57 },
			{field: 'checkRemarkQc',		title: '备注',	width: 57 },
			{field: 'checkIdHead',		title: '负责人审核',	width: 57 },
			{field: 'checkDateHead',		title: '负责人审核时间',	width: 57 },
			{field: 'checkRemarkHead',		title: '负责人审核说明',	width: 57 },
			{field: 'createDate',		title: '创建时间',	width: 57 },
			{field: 'editId',		title: '编辑人',	width: 57 },
			{field: 'editDate',		title: '编辑时间',	width: 57 }
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
});

var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'firstReviewLogController.do?delete',
					data : {id : row.id},
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
var edit = function(row){
	if(row){
        /*$('#ezuiDialog').dialog({
            modal : true,
            title : '审核信息',
            buttons : '#ezuiDialogBtn',
			urlL:'',
            onClose : function() {

            }
        })*/
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
							<th>类型：</th><td><input type='text' id='reviewTypeId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>状态：</th><td><input type='text' id='applyState' class='easyui-textbox' size='16' data-options=''/></td>
							<th>创建起始时间：</th><td><input type='text' id='createDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>创建结束时间：</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<td>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查询</a>
								<a onclick='showCheck()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-check"' href='javascript:void(0);'>审核</a>
								<a onclick='doReturn()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>驳回</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
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
				<td><input type='text' id='remark' class='easyui-textbox' data-options='required:true,multiline:true,height:100,width:200'/></td>
			</tr>
			<tr>
				<td colspan="2" style="text-align: center">
					<a onclick='doCheck()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>审核</a>
				</td>
			</tr>
		</table>
	</div>
	<div id="showDialog"></div>
</body>
</html>
