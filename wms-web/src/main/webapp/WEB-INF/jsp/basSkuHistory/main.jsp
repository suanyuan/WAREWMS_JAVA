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
var ezuiCustDataDialog;
var ezuiCustDataDialogId;
var ezuiImportDataDialog;
var ezuiImportDataForm;

$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/basSkuController.do?showHistoryDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '产品档案',
		pageSize : 50,
		pageList : [50, 3, 200],
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
		idField : 'customerid',
		columns : [[
			/* {field: 'instockDetail',		title: '打印操作',	width: 7,formatter:function(value,rowData,rowIndex){
				return "<a id='ezuiBtn_instockDetailTable'"+rowIndex+" onclick=\"instockDetail('"+rowData.basSkuPK.customerid+"','"+rowData.basSkuPK.sku+"')\" href='javascript:void(0);'>打印</a>";
			}}, */
            {field: 'activeFlag',		title: '激活',	width: 50, formatter:function(value,rowData,rowIndex){
                    return rowData.activeFlag == '1' ? '是' : '否';
                }},
            {field: 'firstop',		title: '首营状态',	width: 100 ,formatter:firstStateTypeFormatter},

            {field: 'customerid',		title: '货主代码',	width: 100},
            {field: 'clientName',		title: '货主名称',	width: 100},
            {field: 'reservedfield09',		title: '医疗器械标志',	width: 100,formatter:yesOrNoFormatter},
            {field: 'sku',		title: '产品代码',	width: 150 },
            {field: 'reservedfield03',		title: '注册证号',	width: 180 },


            {field: 'reservedfield04',		title: '管理分类',	width: 180 },
            {field: 'reservedfield05',		title: '分类目录',	width: 180 },
            {field: 'reservedfield01',		title: '产品名称',	width: 200 },
            {field: 'reservedfield02',		title: '产品描述',	width: 300 },



            {field: 'descrC',		title: '规格',	width: 150 },
			{field: 'descrE',		title: '型号',	width: 150 },
            {field: 'reservedfield14',		title: '生产企业',	width: 150 },


            // {field: 'packid',		title: '包装规格代码',	width: 100,hidden:true },
            {field: 'descr',		title: '包装规格',	width: 100},

            {field: 'skuGroup4',		title: '储存条件',	width: 100},
            {field: 'unit',		title: '单位',	width: 100},
            {field: 'reservedfield10',		title: '养护周期（天）',	width: 100,formatter:day},
            {field: 'skuGroup7',		title: '双证',	width: 100,formatter:yesOrNoFormatter},
            {field: 'skuGroup8',		title: '质量合格证',	width: 100,formatter:yesOrNoFormatter},

            {field: 'productLineName',		title: '产品线',	width: 100 },
            // {field: 'skuGroup2',		title: '附卡类别',	width: 100 },

            {field: 'skuGroup6Name',		title: '默认供应商',	width: 250 },
            // {field: 'supplierNum',		title: '产品供应商数量',	width: 250 },
            // {field: 'supplierNames',		title: '所有产品供应商',	width: 250 },

            {field: 'addtime',		title: '创建时间',	width: 100},
            {field: 'addwho',		title: '创建人',	width: 100},
            {field: 'edittime',		title: '编辑时间',	width: 100 },
            {field: 'editwho',		title: '编辑人',	width: 100 },

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
			});
		},
		onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/basSkuController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
		}
	});

	/* 控件初始化start */
	$("#customerid").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	/* easyUI textbox keyup事件用法 */
	/* $("input",$("#sku").next("span")).keyup(function(){
		$("#sku").textbox('setValue',this.value.toUpperCase());
	}); */
	/* 产品编码限定大写字母 */
	// $("#sku").textbox('textbox').css('text-transform','uppercase');

	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onOpen : function() {
			
		},
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
	
	//资料弹框
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	
	//导入
	ezuiImportDataDialog = $('#ezuiImportDataDialog').dialog({
		modal : true,
		title : '导入',
		buttons : '#ezuiImportDataDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiImportDataForm);
		}
	}).dialog('close');
	/* 控件初始化end */



    <%--$("#skuGroup1").combobox({--%>
        <%--panelHeight: 'auto',--%>
        <%--url:'/productLineController.do?getCombobox',--%>
        <%--valueField:'id',--%>
        <%--textField:'value',--%>
        <%--onLoadSuccess:function () {--%>
            <%--&lt;%&ndash;$("#productLine").combobox("setValue",'${firstBusinessApply1.productline}');&ndash;%&gt;--%>
        <%--}--%>
    <%--})--%>
    $("#sku_group1").combobox({
        // panelHeight: 'auto',
        url:'/productLineController.do?getCombobox',
        valueField:'value',
        textField:'value',
        onLoadSuccess:function () {
            <%--$("#productLine").combobox("setValue",'${firstBusinessApply1.productline}');--%>
        }
    })

    $("#skuGroup1").combobox({
        // panelHeight: 'auto',
        url:'/productLineController.do?getCombobox',
        valueField:'id',
        textField:'value',
        onLoadSuccess:function () {
            <%--$("#productLine").combobox("setValue",'${firstBusinessApply1.productline}');--%>
        }
    })

    $("#packid").combobox({
        panelHeight: 'auto',
        url:'/basPackageController.do?getCombobox',
        valueField:'id',
        textField:'value',
        onLoadSuccess:function () {
            <%--$("#productLine").combobox("setValue",'${firstBusinessApply1.productline}');--%>
        }
    })

    $("#defaultreceivinguom").combobox({
        panelHeight: 'auto',
        url:'/commonController.do?getUOM',
        valueField:'id',
        textField:'value'
    });

});

/* 自动转大写 */
var toUpperFunction = function(){
	/* var x=document.getElementById("fname");
	x.value=x.value.toUpperCase(); */
	// $("#sku").textbox('setValue',$("#sku").val().toUpperCase());
};

/* 新增 */
var add = function(){
	processType = 'add';
	$('#basSkuId').val(0);
	$("#ezuiForm #customerid").textbox({
		editable:false,
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataDialogClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
	$("#ezuiForm #sku").textbox({
		editable:true});
		// .textbox('textbox').css('text-transform','uppercase');
	$("#ezuiForm #activeFlag").combobox('setValue','1').combobox('setText','是');
	$("#ezuiForm #packid").textbox({
		editable:false
	}).textbox('setValue','STANDARD');
	ezuiDialog.dialog('open');
};


/* 编辑 */
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$("#ezuiForm #customerid").textbox({
			editable:false,
			icons:[]
		});
		$("#ezuiForm #sku").textbox({
			editable:false
		});
		$("#ezuiForm #packid").textbox({
			editable:false
		});
		ezuiForm.form('load',{
			customerid : row.customerid/* row.basSkuPK.customerid */,
            skuGroup1:row.skuGroup1,
            skuGroup2:row.skuGroup2,
            skuGroup3:row.skuGroup3,
            skuGroup4:row.skuGroup4,
            skuGroup5:row.skuGroup5,
            skuGroup6:row.skuGroup6,
			//附加供应商名称
            skuGroup6Name:row.skuGroup6Name,
            skuGroup7:row.skuGroup7,
            skuGroup8:row.skuGroup8,
            skuGroup9:row.skuGroup9,
			sku : row.sku,
			descrC : row.descrC,
			descrE : row.descrE,
			packid : row.packid,

            defaultreceivinguom:row.defaultreceivinguom,
			activeFlag: row.activeFlag,
  			alternateSku1 : row.alternateSku1,
  			alternateSku2 : row.alternateSku2,
  			alternateSku3 : row.alternateSku3,
            alternateSku4 : row.alternateSku4,
            alternateSku5 : row.alternateSku5,
            clientName : row.clientName, //货主名称
            skulength: row.skulength,
            skuwidth:row.skuwidth,
            skuhigh : row.skuhigh,
            reservedfield01 : row.reservedfield01,
  			reservedfield02 : row.reservedfield02,
  			reservedfield03 : row.reservedfield03,
            reservedfield04 : row.reservedfield04,
            reservedfield05 : row.reservedfield05,
            reservedfield06 : row.reservedfield06,
            reservedfield07 : row.reservedfield07,
            reservedfield08 : row.reservedfield08,
            reservedfield09 : row.reservedfield09,
            reservedfield10 : row.reservedfield10,
            reservedfield11 : row.reservedfield11,
            reservedfield12 : row.reservedfield12,
			reservedfield13 : row.reservedfield13,
			reservedfield14 : row.reservedfield14,
  			grossweight : row.grossweight,
  			cube : row.cube,
  			price : row.price,
            editwho : row.editwho,
            edittime : row.edittime,
			firstop : row.firstop,
            addtime : row.addtime,
            addwho:  row.addwho
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 删除 */
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'basSkuController.do?delete',
					data : {customerid : row.customerid,sku : row.sku},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							if(result.success){
								msg = result.msg;
							}else{
								$.messager.alert('操作提示', result.msg);
								msg = '<font color="red">' + result.msg + '</font>';
							}
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

/* 提交 */
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/basSkuController.do?edit"/>';
	}else{
		url = '<c:url value="/basSkuController.do?add"/>';
	}
	ezuiForm.form('submit', {
		url : url,
		onSubmit : function(){
			if(ezuiForm.form('validate')){
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
					ezuiDialog.dialog('close');
				}else{
					$.messager.alert('操作提示', result.msg);
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
};

/* 停止激活 */
var editActiveFlag = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		//处于激活状态无法继续激活 反之处于停止激活 无法停止激活   报废的产品无法参与激活
		if(row.firstop == '90'){
			$.messager.show({
				msg : '该产品已是报废状态，请重新选择。', title : '<spring:message code="common.message.prompt"/>'
			});
		}else {
			$.ajax({
				url : 'basSkuController.do?selectBasSku',
				data : {customerid : row.customerid, sku : row.sku},
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					 if(result.activeFlag=="1"){
						$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.updateSkuActiveFlag"/>', function(confirm) {
							if(confirm){
								$.ajax({
									url : 'basSkuController.do?editActiveFlag',
									data : {customerid : row.customerid, sku : row.sku,activeFlag: row.activeFlag},
									type : 'POST',
									dataType : 'JSON',
									success : function(result){
										var msg = '';
										try {
											if(result.success){
												msg = result.msg;
											}else{
												$.messager.alert('操作提示', result.msg);
												msg = '<font color="red">' + result.msg + '</font>';
											}
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
							msg : '该产品状态为未激活，请重新选择。', title : '<spring:message code="common.message.prompt"/>'
						});
					}
				}
			});

		}
	}
}

/* 继续激活 */
var editYesActiveFlag = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		//处于激活状态无法继续激活 反之处于停止激活 无法停止激活   报废的产品无法参与激活
		if(row.firstop == '90'){
			$.messager.show({
				msg : '该产品已是报废状态，请重新选择。', title : '<spring:message code="common.message.prompt"/>'
			});
		}else {
			$.ajax({
				url : 'basSkuController.do?selectBasSku',
				data : {customerid : row.customerid, sku : row.sku },
				type : 'POST',
				dataType : 'JSON',
				success : function(result){
					if(result.activeFlag!="1"){
						$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.SkuActiveFlag"/>', function(confirm) {
							if(confirm){
								$.ajax({
									url : 'basSkuController.do?editActiveFlag',
									data : {customerid : row.customerid, sku : row.sku,activeFlag: row.activeFlag, reservedfield03 : row.reservedfield03},
									type : 'POST',
									dataType : 'JSON',
									success : function(result){
										var msg = '';
										try {
											if(result.success){
												msg = result.msg;
											}else{
												$.messager.alert('操作提示',  '<spring:message code="common.message.data.process.failed"/>');
												msg = '<font color="red">' + result.msg + '</font>';
											}
										} catch (e) {
											msg = '<spring:message code="common.message.data.process.failed"/>';
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
							msg : '该产品状态为已激活，请重新选择。', title : '<spring:message code="common.message.prompt"/>'
						});
					}
				}
			});
		}
	}
}

/* 查询 */
var doSearch = function(){
    //alert( $("#addTimeStart").datebox("getValue")+"===========");
	ezuiDatagrid.datagrid('load', {
		activeFlag : $('#activeFlagQ').combobox('getValue'),
		customerid : $("#customerid").val(),
		sku : $("#sku").val(),
        addTimeEnd : $("#addTimeEnd").datebox("getValue"),
        addTimeStart : $("#addTimeStart").datebox("getValue"),
        edittimeStart : $("#edittimeStart").datebox("getValue"),
        edittimeEnd : $("#edittimeEnd").datebox("getValue"),
        addwho : $("#addwho").val(),
        descrC : $("#descrC").val(),
        descrE : $("#descrE").val(),
        //edittime : $("#edittime").val(),
        editwho : $("#editwho").val(),
        firstop : $("#firstop").combobox('getValue'),
        packid : $("#packidQ").val(),
        reservedfield01 : $("#reservedfield01").val(),
        reservedfield02 : $("#reservedfield02").val(),
        reservedfield03 : $("#reservedfield03").val(),

        reservedfield14 : $("#reservedfield14").val(),

        skuGroup1 : $("#sku_group1").combobox('getValue'),
        skuGroup2 : $("#sku_group2").val(),
        skuGroup6 : $("#skuGroup6Q").val(),

        isUse : $('#isUse').val()
	});
};

/* 打印 */
var instockDetail=function(customerid,sku){
	window.open(sy.bp()+"/basSkuController.do?exportPdf&customerid="+customerid+"&sku="+sku);
};

/* 货主弹框查询 */
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		/* customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue') */
	});
};

/* 货主弹框清空 */
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};

/* 货主弹框-1 */
var ezuiCustDataClick = function(){
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=1&customerType=OW"/>',
		method:'POST',
		toolbar : '#ezuiCustToolbar',
		title: '客户档案',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: false,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'customerid',
		columns : [[
					{field: 'customerid',	title: '客户代码',	width: 15},
					{field: 'descrC',		title: '中文名称',	width: 50},
					{field: 'descrE',		title: '英文名称',	width: 50},
					{field: 'customerTypeName',	title: '类型',	width: 15},
					{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
						return rowData.activeFlag == '1' ? '是' : '否';
		            }}
				]],
		onDblClickCell: function(index,field,value){
			selectCust();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
/* 货主选择-1 */
var selectCust = function(){
	processType = 'selectCust';
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};

/* 货主弹框-2 */
var ezuiCustDataDialogClick = function(){
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url : '<c:url value="/basCustomerController.do?showDatagrid&activeFlag=1&customerType=OW"/>',
		method:'POST',
		toolbar : '#ezuiCustToolbar',
		title: '客户档案',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : true,
		nowrap: false,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:true,
		idField : 'customerid',
		columns : [[
					{field: 'customerid',		title: '客户代码',	width: 15},
					{field: 'descrC',			title: '中文名称',	width: 50},
					{field: 'descrE',			title: '英文名称',	width: 50},
					{field: 'customerTypeName',	title: '类型',	width: 15},
					{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
						return rowData.activeFlag == '1' ? '是' : '否';
		            }}
				]],
		onDblClickCell: function(index,field,value){
			selectDialogCust();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combobox('setText','货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combobox('setText','是');
	ezuiCustDataDialog.dialog('open');
};
/* 货主选择-2 */
var selectDialogCust = function(){
	/* processType = 'selectCust'; */
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDialog #customerid").textbox('setValue',row.customerid);
		ezuiCustDataDialog.dialog('close');
	}
};

/* 导出start */
var doExport = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_export').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		param.put("customerid", $('#customerid').val());
		param.put("sku", $('#sku').val());
		param.put("activeFlag", $('#activeFlagQ').combobox('getValue'));
        param.put("addTimeEnd", $('#addTimeEnd').datebox("getValue"));
        param.put("addTimeStart", $('#addTimeStart').datebox("getValue"));
        param.put("edittimeStart", $('#edittimeStart').datebox("getValue"));
        param.put("edittimeEnd", $('#edittimeEnd').datebox("getValue"));
        param.put("addwho", $('#addwho').val());
        param.put("descrC", $('#descrC').val());
        param.put("descrE", $('#descrE').val());
        param.put("editwho", $('#editwho').val());
        param.put("firstop", $('#firstop').combobox("getValue"));
        param.put("packid", $('#packidQ').val());
        param.put("reservedfield01", $('#reservedfield01').val());
        param.put("reservedfield02", $('#reservedfield02').val());
        param.put("reservedfield03", $('#reservedfield03').val());
        param.put("reservedfield14", $('#reservedfield14').val());
        param.put("skuGroup1", $('#sku_group1').combobox('getValue'));
        param.put("skuGroup2", $('#sku_group2').val());
        param.put("skuGroup6", $('#sku_group6Q').val());
        param.put("isUse", $('#isUse').val());


        //--导出Excel
		var formId = ajaxDownloadFile(sy.bp()+"/basSkuController.do?exportSkuHistoryDataToExcel", param);
		downloadCheckTimer = window.setInterval(function () {
			window.clearInterval(downloadCheckTimer);
			$('#'+formId).remove();
			$('#ezuiBtn_export').linkbutton('enable');
			$.messager.progress('close');
			$.messager.show({
				msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
			});
		}, 1000);
		//--导出csv
		/* var formId = ajaxDownloadFile(sy.bp()+"/basSkuController.do?exportSkuData", param);
		downloadCheckTimer = window.setInterval(function () {
			var list = new cookieList('exportToken');
			if (list.items() == token){
				window.clearInterval(downloadCheckTimer);
				list.clear();
				$('#'+formId).remove();
				$('#ezuiBtn_export').linkbutton('enable');
				$.messager.progress('close');
				$.messager.show({
					msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
				});
			}
		}, 1000); */
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};
/* 导出end */




/* 导入start */
var commitImportData = function(obj){
	ezuiImportDataForm.form('submit', {
		url : '<c:url value="/basSkuController.do?importExcelData"/>',
		onSubmit : function(){
			if(ezuiImportDataForm.form('validate')){
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
					msg = result.msg.replace(/ /g, '\n');
					ezuiDatagrid.datagrid('reload');
				}else{
					msg = result.msg.replace(/ /g, '\n');
				}
			} catch (e) {
				msg = '<font color="red">' + JSON.stringify(data).split('description')[1].split('</u>')[0].split('<u>')[1] + '</font>';
				msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
			} finally {
				ezuiFormClear(ezuiImportDataForm);
				$('#importResult').textbox('setValue',msg);
				$.messager.progress('close');
			}
		}
	});
};
var toImportData = function(){
	ezuiImportDataDialog.dialog('open');
};
/* 下载导入模板 */
var downloadTemplate = function(){
	if(navigator.cookieEnabled){
		$('#ezuiBtn_downloadTemplate').linkbutton('disable');
		var token = new Date().getTime();
		var param = new HashMap();
		param.put("token", token);
		var formId = ajaxDownloadFile(sy.bp()+"/basSkuController.do?exportTemplate", param);
		downloadCheckTimer = window.setInterval(function () {
			var list = new cookieList('downloadToken');
			if (list.items() == token){
				window.clearInterval(downloadCheckTimer);
				list.clear();
				$('#'+formId).remove();
				$('#ezuiBtn_downloadTemplate').linkbutton('enable');
				$.messager.show({
					msg : "<spring:message code='common.message.export.success'/>", title : "<spring:message code='common.message.prompt'/>"
				});
			}
		}, 1000);
	}else{
		$.messager.show({
			msg : "<spring:message code='common.navigator.cookieEnabled.false'/>", title : "<spring:message code='common.message.prompt'/>"
		});
	}
};
/* 导入end */







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
							<tr>
							<th>货主</th>
							<td><input type='text' id='customerid' name="customerid" class='easyui-textbox' size='16' data-options=''/></td>
							<th>产品代码</th>
							<td><input type='text' id='sku'  name="sku" class='easyui-textbox' size='16' /></td>
							<th>创建时间</th>
							<td><input type='text' id='addTimeStart' name="addTimeStart" class='easyui-datebox' size='16' data-options=''/></td>
                            <th>至</th>
                            <td><input type='text' id='addTimeEnd' name="addTimeEnd" class='easyui-datebox' size='16' data-options=''/></td>

                            <th>创建人</th>
							<td><input type='text' id='addwho'  name="addwho" class='easyui-textbox' size='16' /></td>
                            </tr>

						    <tr>
							<th>规格</th>
							<td><input type='text' id='descrC' name="descrC" class='easyui-textbox' size='16' data-options=''/></td>
							<th>型号</th>
							<td><input type='text' id='descrE'  name="descrE" class='easyui-textbox' size='16' /></td>
							<th>编辑时间</th>
							<td><input type='text' id='edittimeStart' name="edittimeStart" class='easyui-datebox' size='16' data-options=''/></td>
                            <th>至</th>
                            <td><input type='text' id='edittimeEnd' name="edittimeEnd" class='easyui-datebox' size='16' data-options=''/></td>
                            <th>编辑人</th>
							<td><input type='text' id='editwho'  name="editwho" class='easyui-textbox' size='16' /></td>
                            </tr>


                            <tr>
                            <th>首营状态</th>
                            <td><input type='text' id='firstop' name="firstop" class='easyui-combobox' size='16' data-options="
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '00', value: '新建'},
																																{id: '10', value: '审核中'},
																																{id: '40', value: '审核通过'},
																																{id: '50', value: '未通过'},
																																{id: '60', value: '已停止'},
																																{id: '90', value: '已报废'}
																															]"/></td>
							<th>包装规格</th>
							<td><input type='text' id='packidQ'  name="packid" class='easyui-textbox' size='16' /></td>
							<th>产品名称</th>
							<td><input type='text' id='reservedfield01'  name="reservedfield01" class='easyui-textbox' size='16' /></td>
							<th>产品描述</th>
							<td><input type='text' id='reservedfield02' name="reservedfield02" class='easyui-textbox' size='16' data-options=''/></td>
                            <th>注册证号</th>
                            <td><input type='text' id='reservedfield03'  name="reservedfield03" class='easyui-textbox' size='16' /></td>

                            </tr>


                            <tr>
                            <th>产品线</th>
							<td><input type='text' id='sku_group1' name="sku_group1" class='easyui-textbox' size='16' data-options='editable:false'/>

							</td>
							<th>附卡类别</th>
							<td><input type='text' id='sku_group2'  name="sku_group2" class='easyui-textbox' size='16' /></td>
							<th>是否激活</th><td>
							<input type="text" id="activeFlagQ"  name="activeFlag"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
							<th>默认供应商</th>
							<td><input type='text' id='skuGroup6Q'  name="skuGroup6Q" class='easyui-textbox' size='16' /></td>
							<th>生产企业</th>
							<td><input type='text' id='reservedfield14'  name="reservedfield14" class='easyui-textbox' size='16' /></td>


						</tr>

                        <td colspan="10">
                            <a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                            <a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                            <a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>
                            <%--<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>--%>
                        </td>

                        </tr>

					</table>
				</fieldset>
				<div>
					<%--<a onclick='editActiveFlag();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>停止激活</a>--%>
					<%--<a onclick='editYesActiveFlag();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>继续激活</a>--%>

					<%--<a onclick='edit();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>详情</a>--%>
					<%--&lt;%&ndash;<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>&ndash;%&gt;--%>
					<%--&lt;%&ndash;<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>&ndash;%&gt;--%>
					<%--<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>--%>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialog' class='easyui-dialog'  style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='basSkuId' name='basSkuId'/>
			<table>
				<tr>
					<th>首营状态</th>
					<td><input type='text' name='firstop' id="firstop" class='easyui-combobox' size='16' data-options="required:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '00', value: '新建'},
																																{id: '10', value: '审核中'},
																																{id: '40', value: '审核通过'},
																																{id: '50', value: '未通过'},
																																{id: '60', value: '已停止'},
																																{id: '90', value: '已报废'}
																															]" readonly/></td>
					<th>是否激活</th>
					<td><input type='text' name='activeFlag' id="activeFlag" class='easyui-combobox' size='16' data-options="required:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
																															]" readonly/></td>
				</tr>


				<tr>
					<th>货主</th>
					<td><input type='text' name='customerid'  id='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>默认供应商</th>
					<td><input type='hidden' name='skuGroup6'   />
						<input type='text' name='skuGroup6Name' class='easyui-textbox' size='16' /></td>


				</tr>

				<tr>
					<th>货主名称</th>
					<td><input type='text' name='clientName' class='easyui-textbox' size='16' /></td>


					<th>产品线</th>
					<td><input type='text' name='skuGroup1' id='skuGroup1'  data-options='required:true,width:150,editable:false'  readonly/></td>

				</tr>


				<tr>
					<th>注册证号</th>
					<td><input type='text' name='reservedfield03' class='easyui-textbox' size='16' /></td>

					<th>产品名称</th>
				    <td><input type='text' name='reservedfield01' class='easyui-textbox' size='16' data-options='required:true'/></td>

				</tr>


				<tr>
					<th>产品代码</th>
					<td><input type='text' name='sku' id="sku" class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>产品描述</th>
					<td><input type='text' name='reservedfield02' class='easyui-textbox' size='16' /></td>

				</tr>

				<tr>
					<th>规格</th>
					<td ><input type='text' name='descrC' class='easyui-textbox' size='16' data-options="required:true"/></td>

					<th>单位</th>
					<td>
						<input type='text' name='defaultreceivinguom' id="defaultreceivinguom" class='easyui-textbox' data-options='required:true,width:150' readonly/>
					</td>

				</tr>

				<tr>
					<th>型号</th>
					<td ><input type='text' name='descrE' class='easyui-textbox' size='16' data-options="required:true"/></td>

					<th>包装单位</th>
					<td><input type='text' name='reservedfield13'  id='reservedfield13' class='easyui-textbox' size='16' data-options='required:false'/></td>

				</tr>

				<tr>
					<th>包装规格</th>
					<td><input type='text' name='packid'  id='packid' class='easyui-textbox' size='16' data-options='required:true,width:150'/></td>

					<th>包装要求</th>
					<td><input type='text' name='skuGroup3' class='easyui-textbox' size='16' /></td>

				</tr>

				<tr>
					<th>管理分类</th>
					<td><input type='text' name='reservedfield04' class='easyui-textbox' size='16' /></td>

					<th>长</th>
					<td><input type='text' name='skulength' class='easyui-textbox' size='13' />(m)</td>

				</tr>

				<tr>
					<th>分类目录</th>
					<td><input type='text' name='reservedfield05' class='easyui-textbox' size='16' /></td>

					<th>宽</th>
					<td><input type='text' name='skuwidth' class='easyui-textbox' size='13' />(m)</td>
				</tr>


				<tr>
					<th>运输条件</th>
					<td><input type='text' name='skuGroup5' class='easyui-textbox' size='16' /></td>

					<th>高</th>
					<td><input type='text' name='skuhigh' class='easyui-textbox' size='13' />(m)</td>
				</tr>



				<tr>
					<th>储存条件</th>
					<td><input type='text' name='skuGroup4' class='easyui-textbox' size='16' /></td>


					<th>重量</th>
					<td><input type='text' name='reservedfield11' class='easyui-textbox' size='13' />(kg)</td>

				</tr>


				<tr>
					<th>生产企业</th>
					<td><input type='text' name='reservedfield14' class='easyui-textbox' size='16' /></td>

					<th>商品条码</th>
					<td><input type='text' name='reservedfield12' class='easyui-textbox' size='16' /></td>

				</tr>

				<tr>
					<th>生产许可证号/备案号</th>
					<td><input type='text' name='reservedfield06' class='easyui-textbox' size='16' /></td>

					<th>自赋码1</th>
					<td><input type='text' name='alternateSku1' class='easyui-textbox' size='16' /></td>

				</tr>
				<tr>
					<th>产地</th>
					<td><input type='text' name='skuGroup9' class='easyui-textbox' size='16' /></td>

					<th>自赋码2</th>
					<td><input type='text' name='alternateSku2' class='easyui-textbox' size='16' /></td>


				<%--<td><input type='text' name='skuGroup8' class='easyui-textbox' size='16' /></td>--%>
					<%--<th>宽</th>--%>
					<%--<td><input type='text' name='skuwidth' class='easyui-textbox' size='16' /></td>--%>
				</tr>
				<tr>

					<th>双证</th>
					<%--<td><input type='text' name='skuGroup7' class='easyui-textbox' size='16' /></td>--%>
					<td><input type='text' name='skuGroup7' id="skuGroup7" class='easyui-combobox' size='16' data-options="required:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
																															]" readonly/></td>



					<th>自赋码3</th>
					<td><input type='text' name='alternateSku3' class='easyui-textbox' size='16' /></td>



				</tr>
				<tr>
					<th>产品合格证</th>
					<td><input type='text' name='skuGroup8' id="skuGroup8" class='easyui-combobox' size='16' data-options="required:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
																															]" readonly/></td>


					<th>自赋码4</th>
					<td><input type='text' name='alternateSku4' class='easyui-textbox' size='16' /></td>


				</tr>

				<tr>
					<th>冷链标志</th>
					<td><input type='text' name='reservedfield07' id="reservedfield07" class='easyui-combobox' size='16' data-options="required:false,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'LD', value: '冷冻'},
																																{id: 'LC', value: '冷藏'},
																																{id: 'FLL', value: '非冷链'}
																															]" readonly/></td>




					<th>自赋码5</th>
					<td><input type='text' name='alternateSku5' class='easyui-textbox' size='16' /></td>
				</tr>

				<tr>
					<th>灭菌标志</th>
					<td><input type='text' name='reservedfield08' id="reservedfield08" class='easyui-combobox' size='16' data-options="required:false,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
																															]" readonly/></td>


					<th>创建人</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>

				</tr>
				<tr>
					<th>医疗器械标志</th>
					<td><input type='text' name='reservedfield09' id="reservedfield09" class='easyui-combobox' size='16' data-options="required:false,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
																															]" readonly/></td>
					<th>创建时间</th>
					<td><input type='text' name='addtime' id='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>

				</tr>
				<tr>
					<th>养护周期(天)</th>
					<td><input type='text' name='reservedfield10' class='easyui-textbox' size='16' value="tian" /></td>


					<th>编辑人</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' /></td>


				</tr>
				<tr>
					<th>附卡类别</th>
					<td><input type='text' name='skuGroup2' class='easyui-textbox' size='16' /></td>


					<th>编辑时间</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' /></td>
				</tr>
				<!-- <tr>
				<th>时间控件</th><td><input type='text' name='edittime' class='easyui-datebox' data-options='required:true,editable:false'/></td>
				<td><a href="#" id="ezuiBtn_Wms" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick='ezuiCustDataDialogClick();'>按钮控件</a></td>
				</tr> -->
			</table>
		</form>
	</div>
	<%--<div id='ezuiDialogBtn'>--%>
		<%--<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>--%>
		<%--<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>--%>
	<%--</div>--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='' data-options='plain:true,iconCls:"icon-add"'>详情<spring:message code='common.button.add'/></div>
		<%--<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>--%>
		<%--<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>--%>
	</div>
	
	<!-- 客户选择弹框 -->
	<div id='ezuiCustDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
	<div class='easyui-layout' data-options='fit:true,border:false'>
	<div data-options="region:'center'">
		<div id='ezuiCustToolbar' class='datagrid-toolbar'   style=''>
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th><td>
								<input type='text' id='customerid' name="customerid" class='easyui-textbox'  size='12' data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th><td>
								<input type='text' id='customerType' name="customerType" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
								<th>激活：</th><td>
								<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox'  size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: '1', value: '是'},
																																{id: '0', value: '否'}
																															]"/></td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
	<div id='ezuiCustDialogBtn'> </div>
	</div>
		<table id='ezuiCustDataDialogId' ></table> 
	</div>
	</div>
	</div>
	<%--<div id='ezuiCustDialogBtn'>--%>
		<%--<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>--%>
		<%--<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>--%>
	<%--</div>--%>
	
	
	<!-- 导入start -->
	<div id='ezuiImportDataDialog' class='easyui-dialog' style='padding: 10px;'>
		<form id='ezuiImportDataForm' method='post' enctype='multipart/form-data'>
			<table>	
				<tr>
					<th>档案</th>
					<td>
						<input type="text" id="uploadData" name="uploadData" class="easyui-filebox" size="36" data-options="buttonText:'选择',validType:['filenameExtension[\'xls\']']"/>
						<a onclick='downloadTemplate();' id='ezuiBtn_downloadTemplate' class='easyui-linkbutton' href='javascript:void(0);'>下载档案模版</a>
					</td>
				</tr>
				<tr>
					<th>执行结果</th>
					<td><input id='importResult' class="easyui-textbox" size='100' style="height:150px" data-options="editable:false,multiline:true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiImportDataDialogBtn'>
		<a onclick='commitImportData();' id='ezuiBtn_importDataCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiImportDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<!-- 导入end -->
	
</body>



</html>
