<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
	<style>
		table th{
			text-align: right;
		}

	</style>
<script type='text/javascript'>
var processType;
var ezuiMenu;
var ezuiForm;
var ezuiDialog;
var ezuiDatagrid;
var enterpriseDialog;
var dialogUrl = "/gspSupplierController.do?toAdd";
var ezuiDialogEditZONGEnterprise;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspSupplierController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: true,
		striped: true,
        rowStyler:function(index,row){
            if(row.isUse == "0" ){
                return 'color:red;';
            }
        },
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'supplierId',
		columns : [[
            {field: 'isUse',		title: '是否有效',	width: 88 ,formatter:function(value,rowData,rowIndex){
                    return rowData.isUse == '1' ? '是' : '否';
                }},
            {field: 'isCheck',		title: '是否审查',	width: 88 ,formatter:function(value,rowData,rowIndex){
                    return rowData.isCheck == '1' ? '是' : '否';
                }},
            {field: 'firstState',		title: '首营状态',	width: 88 ,formatter:firstStateFormatter},



            {field: 'supplierId',		title: '申请单号',	width: 130},
			{field: 'enterpriseId',		title: '企业流水号',	width: 88 ,hidden:true},

			{field: 'operateType',		title: '企业类型',	width: 88 ,hidden:true},

            {field: 'enterpriseNo',		title: '企业代码',	width: 100	 },
            {field: 'shorthandName',		title: '简称',	width: 100 },
            {field: 'enterpriseName',		title: '企业名称',	width: 150 },
            {field: 'costomerid',		title: '货主代码',	width: 150 },
            {field: 'customerName',		title: '货主名称',	width: 150 },

            {field: 'clientTerm',		title: '合同期限',	width: 200 },
            {field: 'enterpriseType',		title: '企业类型',	width: 88,formatter:entTypeFormatter },
            {field: 'createId',		title: '创建人',	width: 88 },
            {field: 'createDate',		title: '创建时间',	width: 200 },
            {field: 'editId',		title: '编辑人',	width: 88 },
            {field: 'editDate',		title: '编辑时间',	width: 160 },


		]],
		onDblClickCell: function(index,field,value){
			edit();
		},
		<%--onRowContextMenu : function(event, rowIndex, rowData) {--%>
			<%--event.preventDefault();--%>
			<%--$(this).datagrid('unselectAll');--%>
			<%--$(this).datagrid('selectRow', rowIndex);--%>
			<%--ezuiMenu.menu('show', {--%>
				<%--left : event.pageX,--%>
				<%--top : event.pageY--%>
			<%--});--%>
		// },
		onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/gspSupplierController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        width:900,
        height:600,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href:dialogUrl,
        cache: false,
        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    }).dialog('close');



    $("#firstStateQuery").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getCatalogFirstState',
        valueField:'id',
        textField:'value'
    });

});
var add = function(){
	processType = 'add';
	$('#gspSupplierId').val(0);
    $('#enterpriseId').val('');
    // $('#contractUrlFile').val('');
    // $('#contractUrlFile1').val('');
    // $('#ezuiDialog').dialog('destroy');
    ezuiDialog = $('#ezuiDialog').dialog({
        modal : true,
        width:900,
        height:600,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '#ezuiDialogBtn',
        href:dialogUrl,
        cache: false,
        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    });

	// ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
    $('#enterpriseDialog').dialog('destroy');

};
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	// alert(row.supplierId);
    if(row){
        // ezuiForm.form('load',{
        //     // supplierId : row.supplierId,
        // 	// enterpriseId : row.enterpriseId,
        // 	// isCheck : row.isCheck,
        // 	// operateType : row.operateType,
        // 	// createId : row.createId,
        // 	// createDate : row.createDate,
        // 	// editId : row.editId,
        // 	// editDate : row.editDate,
        // 	// isUse : row.isUse
        // });
        ezuiDialog = $('#ezuiDialog').dialog({
            modal : true,
            width:900,
            height:600,
            title : '<spring:message code="common.dialog.title"/>',
            buttons : '#ezuiDialogBtn',
            href:dialogUrl+"&id="+row.supplierId,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        });
        // ezuiDialog.dialog('open').dialog('refresh', dialogUrl+"&id="+row.supplierId);
        // $('#ezuiDialogClientDetail').dialog('destroy');
        $('#ezuiDialogClientEnterpriseInfo').dialog('destroy');
    }else{
        $.messager.show({
            msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
        });
    }
};

var check = function () {
    var row = ezuiDatagrid.datagrid('getSelected');
    var infoObj = new Object();
    infoObj["supplierId"] = row.supplierId;
    infoObj["firstState"] = "10";

    //alert(row.supplierId+"================="+row.firstState);
    if(row.firstState!="00" || row.isUse != '1'){
        $.messager.show({
            msg : '只有有效的新建状态申请可以开始审核', title : '提示'
        });
    }else {
        if (row) {
            $.messager.confirm('提示', '是否开始审核', function (confirm) {
                if (confirm) {
                    //修改首营状态为 审核中 10
                    $.ajax({
                        url: 'gspSupplierController.do?commit',
                        data: {"gspSupplierForm": JSON.stringify(infoObj)},//审核中 10
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {
                            ezuiDatagrid.datagrid('reload');
                        }
                    });
					//修改审核状态为 质量部审核  20
                    $.ajax({
                        url: 'firstReviewLogController.do?updateByReviewTypeId',
                        data: {
                            reviewTypeId: row.supplierId,
                            applyState:"20",
                            checkIdQc:"",
                            checkDateQc:null,
                            checkRemarkQc:"",
                            checkIdHead:"",
                            checkDateHead:null,
                            checkRemarkHead:""
                        },//质量部审核  20
                        type: 'POST',
                        dataType: 'JSON',
                        success: function (result) {

                        }
                    });

                }
            });


        }
    };

    };


var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');


        if(row){

            if(row.firstState=="10" || row.firstState=="40"){
                $.messager.progress('close');
                $.messager.show({
                    msg : '审核中与审核通过的申请无法删除', title : '提示'
                });
            }else {
                $.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function (confirm) {
                    if (confirm) {
                        $.ajax({
                            url: 'gspSupplierController.do?delete',
                            data: {id: row.supplierId},
                            type: 'POST',
                            dataType: 'JSON',
                            success: function (result) {
                                $.ajax({
                                    url: 'basCustomerController.do?delete',
                                    data: {enterpriseId: row.enterpriseId, customerType: "VE"},
                                    type: 'POST',
                                    dataType: 'JSON',
                                    success: function (date) {

                                    }
                                });


                                var msg = '';
                                try {
                                    msg = result.msg;
                                } catch (e) {
                                    msg = '<spring:message code="common.message.data.delete.failed"/>';
                                } finally {
                                    $.messager.show({
                                        msg: msg, title: '<spring:message code="common.message.prompt"/>'
                                    });
                                    ezuiDatagrid.datagrid('reload');
                                }
                            }
                        });
                    }
                });
            }
        }else{
            $.messager.show({
                msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
            });
        }




};

var addOrEdit = function(url,infoObj) {
    console.log(44444);
    $.ajax({
        url: url,
        data: {"gspSupplierForm": JSON.stringify(infoObj)}, type: 'POST', dataType: 'JSON', async: true,
        success: function (result) {
            $.messager.progress('close');
            console.log(result);
            var msg = '';
            try {
                if (result.success) {
                    msg = result.msg;
                    ezuiDatagrid.datagrid('reload');
                    ezuiDialog.dialog('close');
                } else {
                    msg = '<font color="red">' + result.msg + '</font>';
                }
            } catch (e) {
                //msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
                msg = '<spring:message code="common.message.data.process.failed"/><br/>' + msg;
            } finally {
                $.messager.show({
                    msg: msg, title: '<spring:message code="common.message.prompt"/>'
                });
                $.messager.progress('close');
            }
        }
    });
}


var commit = function(){
    $.messager.progress({
        text : '<spring:message code="common.message.data.processing"/>', interval : 100
    });


    var row = ezuiDatagrid.datagrid('getSelected');
    var infoObj = new Object();
    $("#ezuiFormSupInfo input[class='textbox-value']").each(function (index) {
        infoObj[""+$(this).attr("name")+""] = $(this).val();
    })
    infoObj["enterpriseId"] = $("#ezuiFormSupInfo input[id='enterpriseId'][data='1']").val();
    infoObj["costomerid"] = $("#ezuiFormSupInfo input[id='costomerid'][data='1']").val();

    //alert(infoObj["enterpriseId"]);
    //alert(infoObj["enterpriseName"]);
	//console.log(infoObj+"infoObj====="+infoObj.isCheck);
	var url = '';
	if (processType == 'edit') {
		// if(infoObj.firstState == "40" || infoObj.firstState == "10" ){
		//
		// }

        if(row.firstState=="40" || row.firstState=="10"){
            $.messager.progress('close');
            $.messager.show({
                msg : '审核中与审核通过的申请无法编辑', title : '提示'
            });
        }else {
            //var row = ezuiDatagrid.datagrid('getSelected');
            infoObj["supplierId"] = row.supplierId;
            url = sy.bp()+'/gspSupplierController.do?edit';
            addOrEdit(url,infoObj);
        }
	}else{
	    if(infoObj.isCheck==0 && infoObj.isCheck!=null&&infoObj.isCheck!=""){
            //alert(infoObj.isCheck);
            console.log(11111);
            $.messager.confirm('提示', '该申请无需审核 是否直接下发？', function(confirm) {
                if(confirm){
                    url = sy.bp()+'/gspSupplierController.do?add';

                    addOrEdit(url,infoObj);

                    $.ajax({
                        url: sy.bp()+'/basCustomerController.do?supplierAdd',
                        data: {"gspSupplierForm": JSON.stringify(infoObj),},
						type: 'POST',
						dataType: 'JSON',
						async: true,
                        success: function (result) {
                            if (result.success) {
                                msg = result.msg;
                                $.messager.show({
                                    msg: '下发成功', title: '<spring:message code="common.message.prompt"/>'
                                });
                            } else {
                                msg = '<font color="red">下发失败</font>';
                            }
                        }
                    });
                }
            });
		}else if(infoObj.isCheck==1){
            console.log(222222);
            url = sy.bp()+'/gspSupplierController.do?add';
            addOrEdit(url,infoObj);
		}else if(infoObj.isCheck==""){
            console.log(333333);
            $.messager.show({
                msg : '请选择是否需要审核', title : '提示'
            });
        }else{
            console.log(55555);
        }
	}



};

function reApply(){
    var row = ezuiDatagrid.datagrid('getSelected');
    if(row.firstState!="40" && row.firstState!="90") {
        $.messager.show({
            msg: '只有审核通过和已报废的能发起新申请', title: '提示'
        });
        return;
    }
    var rows = ezuiDatagrid.datagrid("getSelections");
    if(rows && rows.length>0){
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '确认要发起新申请吗', function(confirm) {
            if (confirm) {
                var arr = new Array();
                for(var i=0;i<rows.length;i++){
                    arr.push(rows[i].supplierId);
                }
                $.ajax({
                    url : sy.bp()+"/gspSupplierController.do?reApply",
                    data : {"id":arr.join(","),"remark":$("#remark").val()},type : 'POST', dataType : 'JSON',async  :true,
                    success : function(result){
                        var msg = result.msg;
                        showMsg(msg);
                        ezuiDatagrid.datagrid("reload");
                    }
                });

            }
        })
    }
}

var doSearch = function(){
   //alert( $('#operateType').combobox("getValue"));
    //alert( $('#enterpriseIdQuery').val());
	ezuiDatagrid.datagrid('load', {
		 supplierId : $('#supplierId').val(),
		enterpriseId : $('#enterpriseId').val(),
        enterpriseIdQuery :$('#enterpriseIdQuery').val(),
        operateType : $('#operateType').combobox("getValue"),
		// createId : $('#createId').val(),
        createDateStart : $('#createDateStart').datebox("getValue"),
        createDateEnd : $('#createDateEnd').datebox("getValue"),
        editDateStart : $('#editDateStart').datebox("getValue"),
        isCheck : $('#isCheck').combobox('getValue'),
        isUse : $('#isUse').combobox('getValue'),
        editDateEnd : $('#editDateEnd').datebox("getValue"),
        //productionAddress : $('#productionAddress').combobox("getValue"),
        shorthandName : $('#shorthandNameQuery').val(),
		// editId : $('#editId').val(),
		//editDate : $('#editDate').val(),
        enterpriseNo: $('#enterpriseNoQuery').val(),
        customerid: $('#clientQuery').val(),
        firstState :$('#firstStateQuery').combobox('getValue'),
        descrC:$('#customernameQuery').val(),

	});
};




//企业信息详情（所有页面都用这个打开企业信息）
function enterpriseInfo(enterpriseId){

    ezuiDialogEditZONGEnterprise = $('#ezuiDialogEditZONGEnterprise').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        buttons : '',
        href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail&id="+enterpriseId,
        width:1200,
        height:530,
        closable:true,
        cache: false,
        onClose : function() {
            $(this).dialog("clear");
        }
    });

    if(enterpriseId!=null && enterpriseId!="" ){
        ezuiDialogEditZONGEnterprise.dialog('refresh', "/gspEnterpriseInfoController.do?toDetail&id="+enterpriseId).dialog('open');
        enterpriseId = "";
    }else{
        $.messager.show({
            msg : '请先选择企业', title : '提示'
        });
    }
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
							<%--<th>企业流水号</th><td><input type='text' id='enterpriseId' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<th>申请单号</th><td><input type='text' id='supplierId' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业代码</th><td><input type='text' id='enterpriseNoQuery' class='easyui-textbox' size='16' data-options=''/></td>
							<th>简称</th><td><input type='text' id='shorthandNameQuery' class='easyui-textbox' size='16' data-options=''/></td>
							<%--<th>企业</th><td><input type='text' id='enterpriseId' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<th>企业</th>
							<td>
								<input type='text' id='enterpriseIdQuery' class='easyui-textbox' data-options='' style="width: 100px;"/>
								<input type="hidden" id="enterpriseId" name="enterpriseId">
								<!--<a href="javascript:void(0)" onclick='searchMainEnterprise()' class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
							</td>
								<th>企业类型</th><td><input type="text" id="operateType"  name="operateType"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'JY', value: '经营'},
																																	{id: 'SC', value: '生产'}
																																]"/></td>
							<th>创建时间</th><td><input type='text' id='createDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>

							<%--<td><input type='text' id='operateType' class='easyui-textbox' size='16' data-options=''/></td>--%>
						</tr>
						<tr>
							<th>首营状态</th><td><input type='text' id='firstStateQuery' class="easyui-combobox"  size='16' data-options=''/></td>

                            <th>货主代码</th><td><input type='text' id='clientQuery' class='easyui-textbox' size='16' data-options=''/></td>

							<th>货主名称</th><td><input type='text' id='customernameQuery' class='easyui-textbox' size='16' data-options=''/></td>
                            <th>是否审查</th><td><input type='text' id='isCheck' class='easyui-textbox' size='16' data-options=''/></td>
							<%--<th>是否审查</th><td><input type="text" id="ischeck"  name="ischeck"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',--%>
																																	<%--editable:false,--%>
																																	<%--valueField: 'id',--%>
																																	<%--textField: 'value',--%>
																																	<%--data: [--%>
																																	<%--{id: '1', value: '是'},--%>
																																	<%--{id: '0', value: '否'}]"/></td>--%>

							<%--<th>是否有效</th><td><input type="text" id="isUse"  name="isUse"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',--%>
																																	<%--editable:false,--%>
																																	<%--valueField: 'id',--%>
																																	<%--textField: 'value',--%>
																																	<%--data: [--%>
																																	<%--{id: '1', value: '是'},--%>
																																	<%--{id: '0', value: '否'}--%>
																																<%--]"/></td>--%>
							<th>是否有效</th><td><input type='text' id='isUse' class='easyui-textbox' data-options='' size='16'/></td>
							<th>编辑时间</th><td><input type='text' id='editDateStart' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='editDateEnd' class='easyui-datebox' size='16' data-options=''/></td>

								<%--<th>是否有效：</th><td>--%>
								<%--<select id="isUse" name="isUse" class="easyui-combobox"  style="width:150px;">--%>
									<%--<option value=""></option>--%>
									<%--<option value="1">是</option>--%>
									<%--<option value="0">否</option>--%>
								<%--</select></td>--%>

						</tr>
						<tr >
							<td colspan="10">
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

					<a onclick='check();' id='ezuiBtn_check' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>提交审核</a>
					<a onclick='reApply();' id='ezuiBtn_reApply' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-redo"' href='javascript:void(0);'>发起新申请</a>

					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>

	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
	<div id='enterpriseDialog' style='padding: 10px;'>

	</div>




	<div id='ezuiDialogDetail' style='padding: 10px;'>
		<div id='enterpriseSearchGridToolbar_gspSupplier' class='datagrid-toolbar' style=''>
			<fieldset>
				<legend>企业信息</legend>
				<table>
					<tr>
						<th>代码</th>
						<td><input type='text' id='enterpriseNo1' class='easyui-textbox' data-options='width:200'/></td>
						<th>简称</th>
						<td><input type='text' id='shorthandName1' class='easyui-textbox' data-options='width:200'/></td>
						<td>
							<a onclick='doSearchEnterprise1();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
							<a onclick='choseSelect_gspSupplier(id,name)' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<table id="enterpriseSearchGrid_gspSupplier">

		</table>
	</div>


	<div id='enterpriseDialog_gspSupplier' style='padding: 10px;'></div>
	<div id='ezuiDialogEditZONGEnterprise' style='padding: 10px;'></div>


<script>
    var enterpriseDialog1_gspSupplier;
    var ezuiDialogDetail;
	var enterpriseSearchGrid_gspSupplier;
    $(function () {


        enterpriseSearchGrid_gspSupplier = $("#enterpriseSearchGrid_gspSupplier").datagrid({
            url : '/gspEnterpriseInfoController.do?showDatagridSearch',
            method:'POST',
            toolbar : '#enterpriseSearchGridToolbar_gspSupplier',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                enterpriseType:'default',
                <%--enterpriseType:'${enterpriseType}'--%>
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: '20%' },
                {field: 'shorthandName',		title: '简称',	width: '20%' },
                {field: 'enterpriseName',		title: '企业名称',	width: '20%' },
                {field: 'enterpriseType',		title: '企业类型',	width: '20%' ,formatter:entTypeFormatter},
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOper_gspSupplier
                }
            ]],
            onDblClickRow: function(index,row){
                choseSelect_gspSupplier(row.enterpriseId,row.enterpriseName);
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }


        })

        ezuiDialogDetail = $('#ezuiDialogDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');



    });

    $("#enterpriseIdQuery").textbox({
        width:146,
        icons:[{
            iconCls:'icon-search',
            handler: function(e){
                searchMainEnterprise();
            }
        }]
    });

    function searchMainEnterprise() {
        if(ezuiDialogDetail){
            ezuiDialogDetail.dialog('open');
        }

    }

    function doSearchEnterprise1(){
        //console.log("-----");
        //console.log($("#enterpriseSearchGridToolbar_gspSupplier input[id='enterpriseNo1']").textbox("getValue"));
        //console.log($('#shorthandName1').val());
        // console.log($('#shorthandName1').textbox('getValue'));
        enterpriseSearchGrid_gspSupplier.datagrid('load', {
            enterpriseNo : $("#enterpriseSearchGridToolbar_gspSupplier input[id='enterpriseNo1']").textbox("getValue"),
            shorthandName:$("#enterpriseSearchGridToolbar_gspSupplier input[id='shorthandName1']").textbox("getValue"),
            // type :'noSupplier',
            enterpriseType:'default',
            isUse : '1'
        });
    };


    $("#isCheck").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });



    $("#isUse").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getIsUseCombobox',
        valueField:'id',
        textField:'value'
    });



    function operateGrid_gspSupplier(id) {
        $('#enterpriseDialog_gspSupplier').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail&id="+id,
            width:850,
            height:500,
            cache:false,
            onClose : function() {

            }
        })
    }

    function formatOper_gspSupplier(value,row,index){
        return "<a onclick=\"operateGrid_gspSupplier('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }




    function choseSelect_gspSupplier(id,name) {
        var row =enterpriseSearchGrid_gspSupplier.datagrid("getSelected");
        console.log(id)
        console.log(name)
        if(id=="" || name==""){
            id = row.enterpriseId;
            name = row.enterpriseName;
        }
        $("input[name='enterpriseId']").val(id);
        $("#enterpriseIdQuery").textbox("setValue",name);
        ezuiDialogDetail.dialog("close");
    }


</script>


</body>
</html>
