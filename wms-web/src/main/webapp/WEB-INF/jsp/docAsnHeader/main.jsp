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
var ezuiDetailsMenu;
var ezuiForm;
var ezuiDetailsForm;
var ezuiDialog;
var ezuiDetailsDialog;
var ezuiDatagrid;
var ezuiDetailsDatagrid;
var ezuiCustDataDialog;
var ezuiCustDataDialogId;
var ezuiSkuDataDialog;
var ezuiSkuDataDialogId;
var ezuiLocDataDialog;
var ezuiLocDataDialogId;
var ezuiImportDataDialog;
var ezuiImportDataForm;
var productDialog_docAsnHeader;
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiDetailsMenu = $('#ezuiDetailsMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDetailsForm = $('#ezuiDetailsForm').form();
	ezuiImportDataForm=$('#ezuiImportDataForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docAsnHeaderController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '入库单管理',
		pageSize : 50,
		pageList : [50, 100, 200],
		fit: true,
		border: false,
		fitColumns : false,
		nowrap: true,
		striped: true,
		collapsible:false,
		pagination:true,
		rownumbers:true,
		singleSelect:false,
		idField : 'asnno',
		queryParams: {
			asnstatusCheck: $('#asnstatusCheck').is(':checked') == true ? "Y" : "N"
		},
		rowStyler:asnRowStyle,
		columns : [[
            {field: 'ck',checkbox:true },
			{field: 'customerid',		title: '客户编码',	width: 71 },
			{field: 'asnno',		title: '入库单编号',	width: 101 },
			{field: 'asntypeName',		title: '入库类型',	width: 71 },
			{field: 'asnstatusName',		title: '入库状态',	width: 71 },
			{field: 'asnreference1',		title: '客户订单号',	width: 131 },
			{field: 'expectedarrivetime1',		title: '预期到货时间',	width: 137 ,formatter:dateFormat2},
			{field: 'asnreference2',		title: '客户采购单号',	width: 101 },
            {field: 'coldTag',		title: '冷链标记',	width: 71 },
			//{field: 'asnreference3',		title: '参考编号3',	width: 50 },
			//{field: 'asnreference4',		title: '参考编号4',	width: 50 },
		   //{field: 'asnreference5',		title: '参考编号5',	width: 50 },
			{field: 'addtime',		title: '创建时间',	width: 137 },
			{field: 'addwho',		title: '创建人',	width: 71 },
			{field: 'edittime',		title: '最后编辑时间',	width: 137 },
			{field: 'editwho',		title: '编辑人',	width: 71 },
			{field: 'releasestatusName',		title: '释放状态',	width: 71 },
			{field: 'warehouseid',		title: '仓库编码',	width: 71 },
			{field: 'notes',		title: '备注',	width: 250 },
			// {field: 'asnstatus',		title: '入库状态',	width: 71 },

		]],
		onDblClickCell: function(index,field,value){
			//edit();
		},
        onDblClickRow: function(index,row){
            edit(row);
			qlDetails(row);
        },
		onRowContextMenu : function(event, rowIndex, rowData) {
// 			event.preventDefault();
// 			$(this).datagrid('unselectAll');
// 			$(this).datagrid('selectRow', rowIndex);
// 			ezuiMenu.menu('show', {
// 				left : event.pageX,
// 				top : event.pageY
// 			});
		},
        onCheckAll:function(rows){
		  if(rows){
		      for(var i=0;i<rows.length;i++){
                  afterCheckButtion(rows[i]);
			  }
		  }
		},
        onCheck:function(index,row){
            console.log("onCheck");
            if (index - 1){
                afterCheckButtion(row);
            };
		},
		onSelect: function(rowIndex, rowData) {
		    console.log("onSelect");
			//if (rowIndex - 1){
                afterCheckButtion(rowData);
			//};
		},
		onLoadSuccess:function(data){
			ajaxBtn($('#menuId').val(), '<c:url value="/docAsnHeaderController.do?getBtn"/>', ezuiMenu);
			$(this).datagrid('unselectAll');
			$(this).datagrid('clearChecked');
		}
	});
	
	//订单明细列表（数据窗口）
	ezuiDetailsDatagrid = $('#ezuiDetailsDatagrid').datagrid({
		url : '<c:url value="/docAsnDetailController.do?showDatagrid"/>',
		method : 'POST',
		toolbar : '#detailToolbar',
		idField : 'asnlineno',
		title : '入库单明细',
		pageSize : 200,
		pageList : [50, 100, 200],
		border : false,
		fitColumns : false,
		nowrap : false,
		striped : true,
		collapsible : false,
		pagination:true,
		rownumbers : true,
		singleSelect : true,
		checkOnSelect:true,
		selectOnCheck: false,
		columns : [[
			{field: 'chk',              checkbox:true,      width: 6},
			{field: 'asnlineno',		title: '行号',		width: 40 },
			{field: 'sku',				title: '产品代码',		width: 100 },
			{field: 'skudescrc',			title: '产品名称',		width: 200 },
			{field: 'alternativesku',	title: '产品条码',		width: 70 },
            {field: 'coldName',	title: '冷链标记',		width: 70 },
			{field: 'linestatusName',	title: '行状态',		width: 70 },
			{field: 'expectedqty',		title: '预期到货数',		width: 70 },
			{field: 'receivedqty',		title: '实际到货数',		width: 70 },
			{field: 'receivinglocation',		title: '收货库位',		width: 107 },
			{field: 'totalgrossweight',		title: '重量',		width: 50 },
			{field: 'totalcubic',		title: '体积',		width: 50 },
			{field: 'totalprice',		title: '单价',		width: 50 },
			{field: 'lotatt01',		title: '生产日期',		width: 155 },
			{field: 'lotatt02',		title: '效期',		width: 155 },
			{field: 'lotatt03',		title: '入库日期',		width: 155 },
			{field: 'lotatt04',		title: '生产批号',		width: 70 },
			{field: 'lotatt05',		title: '序列号',		width: 70 },
			{field: 'lotatt06',		title: '产品注册证',		width: 80 },
			{field: 'lotatt07',	title: '灭菌批号',		width: 80 },
            {field: 'cusdescr',	title: '供应商',		width: 80 },
            {field: 'lotatt09',	title: '样品属性',		width: 80 },
            {field: 'lotatt10',	title: '质量状态',		width: 80 },
            {field: 'lotatt11',	title: '存储条件',		width: 150 },
            {field: 'lotatt12',	title: '产品名称',		width: 80 },
            {field: 'lotatt13',	title: '双证',		width: 80 },
            {field: 'lotatt14',	title: '入库单号',		width: 80 }
		]],
		onDblClickCell: function(index,field,value){
			detailsEdit();
		},
		onRowContextMenu : function(event, rowIndex, rowData) {

		},
		onSelect: function(rowIndex, rowData) {
			if (rowIndex - 1){
				if(rowData.linestatus == '00'){
					$('#ezuiDetailsBtn_edit').linkbutton('enable');
					$('#ezuiDetailsBtn_del').linkbutton('enable');
					$('#ezuiDetailsBtn_receive').linkbutton('enable');
                    $('#ezuiDetailsBtn_add').linkbutton('enable');
                    $("#ezuiBtn_merge").linkbutton('disable');
				}else{
				    if(rowData.linestatus == '40'){//完全收货状态可以进行上架任务、
						$("#ezuiBtn_merge").linkbutton('enable');
					}else{
                        $("#ezuiBtn_merge").linkbutton('disable');
					}
					$('#ezuiDetailsBtn_edit').linkbutton('disable');
					$('#ezuiDetailsBtn_del').linkbutton('disable');
					$('#ezuiDetailsBtn_receive').linkbutton('disable');
                    $('#ezuiDetailsBtn_add').linkbutton('disable');
                    $("#ezuiBtn_merge").linkbutton('disable');

				};
			};
		},
		onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
			$(this).datagrid("resize",{height:200});
		}
	});
    /* 收货明细列表*/
    asnDetailsDatagrid = $('#asnDetailsDatagrid').datagrid({
        url : '<c:url value="/docAsnHeaderController.do?showAsnDetal"/>',
        method : 'POST',
        toolbar : '',
        idField : 'docNo',
        title : '',
        border : false,
        fitColumns : false,
        nowrap : false,
        striped : true,
        collapsible : false,
        rownumbers : true,
        singleSelect : true,
        columns : [[
            //{field: 'docNo',	title: '产品代码',		width: 120 },
            {field: 'docLineNo',				title: '行号',		width: 130 },
            {field: 'fmSku',			title: '产品名称',		width: 130 },
            {field: 'lotatt12',		title: '注册证号/备案凭证号',		width: 350 },
            {field: 'lotatt06',		title: '产品注册证',		width: 200 },
            {field: 'descC',		title: '规格/型号',		width: 130 },
            {field: 'lotatt04',		title: '生产批号',		width: 130 },
            {field: 'lotatt05',		title: '序列号',		width: 130 },
            {field: 'lotatt07',	title: '灭菌批号',		width: 130 },
            {field: 'lotatt01',	title: '生产日期',		width: 130 },
            {field: 'lotatt02',	title: '效期',		width: 130 },
            {field: 'fmqty',	title: '数量',		width: 130 },
            {field: 'fmqty_each',	title: '件数',		width: 130 },
            {field: 'fmLocation',	title: '库位',		width: 130 },
            {field: 'addTime',	title: '制单日期',		width: 200 },
            {field: 'addWho',	title: '制单人',		width: 130 }
        ]],
        onDblClickCell: function(index,field,value){
            detailsEdit();
        },
        onRowContextMenu : function(event, rowIndex, rowData) {
        },
        onLoadSuccess:function(data){

        }
    });
	
	/* 控件初始化start */
	$("#customerid").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick('OW');
				ezuiCustDataDialogSearch();
			}
		}]
	});
	
	//时间控件初始化
	$('#addtime').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$('#edisendtime5').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$('#expectedarrivetime1').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$('#expectedarrivetime2').datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	
	$("#ezuiSkuDataDialog #sku").textbox('textbox').css('text-transform','uppercase');
	$("#ezuiDetailsForm #sku").textbox('textbox').css('text-transform','uppercase');
	
	$("input",$("#ezuiDetailsForm #expectedqty").next("span")).blur(function(){
		if ($("#ezuiDetailsForm #expectedqty").val() != '') {
			if ($("#ezuiDetailsForm #sku").val() == '') {
				$("#ezuiDetailsForm #expectedqty").numberbox('clear');
				//alert("没有输入产品");
				showMsg("没有输入产品")
			} else {
				$.ajax({
					url : 'basSkuController.do?getSkuInfo',
					data : {customerid : $("#ezuiDetailsForm #customerid").val(),sku : $("#ezuiDetailsForm #sku").val(), qty : $("#ezuiDetailsForm #expectedqty").numberbox('getValue')},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						try {
							$("#ezuiDetailsForm #totalgrossweight").numberbox('setValue',result.totalgrossweight);
							$("#ezuiDetailsForm #totalcubic").numberbox('setValue',result.totalcube);
							$("#ezuiDetailsForm #totalprice").numberbox('setValue',result.totalprice);
						} catch (e) {
							return;
						}
					}
				});
			}
		}
	});
	
	//订单信息弹框
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		left:0,
	    top:0,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			detailObjs = [];
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

	//订单明细弹框
	ezuiDetailsDialog = $('#ezuiDetailsDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDetailsDialogBtn',
		onClose : function() {
			detailObjs = [];
			ezuiFormClear(ezuiDetailsForm);
		}
	}).dialog('close');
	
	//客户选择弹框
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	
	//商品选择弹框
	ezuiSkuDataDialog = $('#ezuiSkuDataDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '',
		onOpen : function() {
			
		},
		onClose : function() {
			
		}
	}).dialog('close');
	
	//库位选择弹框
	ezuiLocDataDialog = $('#ezuiLocDataDialog').dialog({
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
    $("#productId").textbox({
        icons:[{
            iconCls:'icon-search',
            handler: function(e){
                productDialog_docAsnHeader = $('#ezuiProductSearchDialog').dialog({
                    modal : true,
                    title : '查询',
                    href:sy.bp()+"/basSkuController.do?toSearchDialog&target=docAsnHeader",
                    width:850,
                    height:500,
                    cache:false,
                    onClose : function() {

                    }
                })
            }
        }]
    });

    $("#supplierId").textbox({
        width:110,
        icons:[{
            iconCls:'icon-search',
            handler: function(e){
                $("#ezuiCustDataDialog #customerid").textbox('clear');
                ezuiCustDataClick('VE');
                ezuiCustDataDialogSearch();
            }
        }]
    });

//仓库下拉框
    $("#warehouseId").combobox({
        panelHeight: 'auto',
        editable: false,
        url:'<c:url value="/docPoHeaderController.do?getWarehouseCombobox"/>',
        valueField: 'id',
        textField: 'value'
    });
//单选框改变事件
	$("#asnstatusCheck").change(function() {
		doSearch();
	});

});


/* 查询条件清空按钮 */
var ezuiToolbarClear = function(){
	$('#customerid').textbox('clear');
	$('#asnno').textbox('clear');
	$('#releasestatus').combobox('clear');
	$('#asnstatus').combobox('clear');
	$('#userdefinea').combobox('clear');
	$('#asntype').combobox('clear');
	$('#asnreference1').textbox('clear');
	$('#asnreference2').textbox('clear');
	$('#asnreference3').textbox('clear');
	$('#expectedarrivetime1').datetimebox('clear');
	$('#expectedarrivetime2').datetimebox('clear');
	$('#addtime').datetimebox('clear');
	$('#edisendtime5').datetimebox('clear');
	$('#addwho').textbox('clear');
	$("#asnstatusCheck").attr("checked",false);
    $('#addwho').textbox('clear');
    $('#customerid').textbox('clear');
    $('#productId').textbox('clear');
    $('#supplierId').textbox('clear');
};

/* 新增按钮 */
var add = function(){
	processType = 'add';
	$('#docAsnHeaderId').val(0);
	//时间控件初始化
	$("#ezuiForm #addtime").datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
        	var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
        	return date <= validateDate;
        }
    });
	$("#ezuiForm #expectedarrivetime1").datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
        	var validateDate = new Date(now.getFullYear()+1, now.getMonth(), now.getDate());
        	return date <= validateDate;
        }
    });
	$("#ezuiForm #addtime").textbox({
		editable:false,readonly:true
	});
	
	$('#ezuiDetailsDatagrid').datagrid('load',{asnno:'-1'});
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
	$("#ezuiForm #asnno1").textbox({
		editable:false
	});
	$("#ezuiForm #asnstatus").combobox('setValue','00').combo('readonly', true);
    $("#ezuiForm #releasestatus").combobox('setValue','Y');
	$('#ezuiBtn_renew').linkbutton('disable');
	$('#ezuiBtn_recommit').linkbutton('enable');
	ezuiDialog.dialog('open');
};

/*  查询是否有明细 */
var qlDetails = function(row){
	$.ajax({
		url : 'docAsnHeaderController.do?qlDetails',
		data : {generalNo: row.asnno},
		type : 'POST',
		dataType : 'JSON',
		success : function(result){
			console.log(result)
			try {
				if(result.success){
					$('#ezuiBtn_copyDetail').linkbutton('disable');
				}else{
					$('#ezuiBtn_copyDetail').linkbutton('enable');
				}
			} catch (e) {
				return;
			};
		}
	});
}


/* 编辑按钮 */
var edit = function(row){
	processType = 'edit';
	$('#docAsnHeaderId').val(0);
	//时间控件初始化
	$("#ezuiForm #addtime").datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$("#ezuiForm #expectedarrivetime1").datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$("#ezuiForm #addtime").textbox({
		editable:false,readonly:true
	});
	
	$("#ezuiForm #customerid").textbox({
		editable:false,
		icons:[]
	});
	$("#ezuiForm #asnno1").textbox({
		editable:false
	});
	$("#ezuiForm #asnstatus").combo('readonly', true);
	var row = row;//ezuiDatagrid.datagrid('getSelected');
	if(row){
        //初始化根据货主初始化供应商
        $('#ezuiForm #supplierid').combobox({
            url:'commonController.do?getSupplier',
            valueField:'id',
            textField:'value',
            queryParams:{customerId:row.customerid},
			onLoadSuccess:function () {
				$(this).combobox("setValue",row.supplierid)
            }
		});

		$('#ezuiForm').form({
		    url: '<c:url value="/docAsnHeaderController.do?showAsnHeader"/>',
		    queryParams: {asnno : row.asnno},
		    onSubmit: function(){
		    	/* 进行表单验证 */
				/* 如果返回false阻止提交 */
		    },
		    success:function(data){
		    	var result = $.parseJSON(data);
		    	$('#ezuiForm #customerid').textbox('setValue',result.customerid);
		    	$('#ezuiForm #asnno1').textbox('setValue',result.asnno);
		    	$('#ezuiForm #asnreference1').textbox('setValue',result.asnreference1);
		    	$('#ezuiForm #asnreference2').textbox('setValue',result.asnreference2);
		    	$('#ezuiForm #asnreference3').textbox('setValue',result.asnreference3);
		    	$('#ezuiForm #asnreference4').textbox('setValue',result.asnreference4);
		    	$('#ezuiForm #asnreference5').textbox('setValue',result.asnreference5);
                $('#ezuiForm #userdefine1').textbox('setValue',result.userdefine1);
                $('#ezuiForm #userdefine2').textbox('setValue',result.userdefine2);
                $('#ezuiForm #userdefine3').textbox('setValue',result.userdefine3);
                $('#ezuiForm #userdefine4').textbox('setValue',result.userdefine4);
                $('#ezuiForm #userdefine5').textbox('setValue',result.userdefine5);
		    	$('#ezuiForm #asnstatus').combobox('setValue',result.asnstatus);
		    	$('#ezuiForm #releasestatus').combobox('setValue',result.releasestatus);
		    	$('#ezuiForm #asntype').combobox('setValue',result.asntype);
		    	$('#ezuiForm #addtime').datetimebox('setValue',result.addtime);//？？？
		    	$('#ezuiForm #expectedarrivetime1').datebox('setValue',result.expectedarrivetime1);
		    	$('#ezuiForm #notes').textbox('setValue',result.notes);
		    }
		});
		/* 提交表单 */
		$('#ezuiForm').submit();
		ezuiDetailsDatagrid.datagrid('load',{asnno:row.asnno});
        asnDetailsDatagrid.datagrid('load',{ordero:row.asnno});
		$('#ezuiDetailsDatagrid').parent().parent().parent().show();
		$('#ezuiBtn_renew').linkbutton('disable');
		$('#ezuiBtn_recommit').linkbutton('enable');
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 取消按钮 */
var cancel = function(){
	var row = ezuiDatagrid.datagrid('getSelections');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确认取消？', function(confirm) {
			if(confirm){
                var arr = new Array();
                for(var i=0;i<row.length;i++){
                    arr.push(row[i].asnno);
                }
                // console.log(arr);
				$.ajax({
					url : 'docAsnHeaderController.do?cancel',
					data : {'asnnos' : arr.join(",")},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
                            msg = result.msg;
						} catch (e) {
							msg = '取消异常';
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

var mergeOrder = function () {
    var row = ezuiDatagrid.datagrid('getSelections');
    if(row) {
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确认合并生成上架清单？', function(confirm) {
            if (confirm) {
                var arr = new Array();
                for(var i=0;i<row.length;i++){
                    arr.push(row[i].asnno);
                }
                $.ajax({
                    url : sy.bp()+"/docAsnHeaderController.do?addDocPa",
                    data : {"asnNos":arr.join(",")},type : 'POST', dataType : 'JSON',async  :true,
                        success : function(result){
                            console.log(result);
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
        })
    }

}

var mergeReceiving = function () {
    var row = ezuiDatagrid.datagrid('getSelections');
    // console.log(row);
    if(row) {
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确认收货？', function(confirm) {
            if (confirm) {
                var arr = new Array();
                for(var i=0;i<row.length;i++){
                    arr.push(row[i].asnno);
                }
                $.ajax({
                    url : sy.bp()+"/docAsnHeaderController.do?confirmReveiving",
                    data : {"asnNos":arr.join(",")},type : 'POST', dataType : 'JSON',async  :true,
                    success : function(result){
                        console.log(result);
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
        })
    }

}

/* 关闭按钮 */

var closeCheck = function(){
    var row = ezuiDatagrid.datagrid('getSelections');
    if(row){
        var asnnos = new Array();
        for(var i=0;i<row.length;i++){
            asnnos.push(row[i].asnno);
        }
        $.ajax({
            url : 'docAsnHeaderController.do?closeCheck',
            data : {'asnnos' : asnnos.join(",")},
            type : 'GET',
            dataType : 'JSON',
            success : function(result){
                var msg = '';
                try {
                    msg = result.msg;
                } catch (e) {
                    msg = '订单状态检查异常';
                    result.success = false;
                } finally {

                    if (result.success) {

                        var showMsg = msg + '是否确认关单？';
                        $.messager.confirm('<spring:message code="common.message.confirm"/>', showMsg, function(confirm) {
                            if (confirm) {

                                close1();
                            }
                        });
                    } else {

                        $.messager.show({
                            msg : msg, title : '<spring:message code="common.message.prompt"/>'
                        });
                    }
                }
            }
        });
    }else{
        $.messager.show({
            msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
        });
    }
};

var close1 = function(){
	var row = ezuiDatagrid.datagrid('getSelections');
	if(row){
        var arr = new Array();
        for(var i=0;i<row.length;i++){
            arr.push(row[i].asnno);
        }
        $.ajax({
            url : 'docAsnHeaderController.do?close',
            data : {'asnnos' : arr.join(",")},
            type : 'POST',
            dataType : 'JSON',
            success : function(result){
                var msg = '';
                try {
                    msg = result.msg;
                } catch (e) {
                    msg = '关单异常';
                } finally {
                    $.messager.show({
                        msg : msg, title : '<spring:message code="common.message.prompt"/>'
                    });
                    ezuiDatagrid.datagrid('reload');
                }
            }
        });
    }else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		customerid : $('#customerid').val(),
		asnno : $('#asnno').val(),
		releasestatus : $('#releasestatus').combobox('getValue'),
		asnstatus : $('#asnstatus').combobox('getValue'),
		userdefinea : $('#userdefinea').combobox('getValue'),
		asntype : $('#asntype').combobox('getValue'),
		asnreference1 : $('#asnreference1').val(),
		asnreference2 : $('#asnreference2').val(),
		asnreference3 : $('#asnreference3').val(),
		expectedarrivetime1 : $('#expectedarrivetime1').datetimebox('getValue'),
		expectedarrivetime2 : $('#expectedarrivetime2').datetimebox('getValue'),
		addtime : $('#addtime').datetimebox('getValue'),
		edisendtime5 : $('#edisendtime5').datetimebox('getValue'),
		addwho : $('#addwho').val(),
		asnstatusCheck : $('#asnstatusCheck').is(':checked') == true ? "Y" : "N"
	});
};

/* 导入start */
var commitImportData = function(obj){
    ezuiImportDataForm.form('submit', {
        url : '<c:url value="/docAsnHeaderController.do?importExcelData"/>',
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
		var formId = ajaxDownloadFile(sy.bp()+"/docAsnHeaderController.do?exportTemplate", param);
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

/* 表头新增按钮 */
var renew = function(){
	processType = 'add';
	$('#docAsnHeaderId').val(0);
	$('#ezuiForm').form('clear');
	//时间控件初始化
	$("#ezuiForm #addtime").datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$("#ezuiForm #expectedarrivetime1").datetimebox('calendar').calendar({
        validator: function(date){
        	var now = new Date();
			var validateDate = new Date(now.getFullYear(), now.getMonth(), now.getDate());
            return date <= validateDate;
        }
    });
	$("#ezuiForm #addtime").textbox({
		editable:false,readonly:true
	});
	
	$('#ezuiDetailsDatagrid').datagrid('load',{asnno:'-1'});
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
	$("#ezuiForm #asnno1").textbox({
		editable:false
	});
	$("#ezuiForm #asnstatus").combobox('setValue','00').combo('readonly', true);
	$('#ezuiBtn_renew').linkbutton('disable');
	$('#ezuiBtn_recommit').linkbutton('enable');
};

/* 表头提交按钮 */
var commit = function(){
	var asnstatus = $("#ezuiDialog #asnstatus").combobox('getValue');
	if (asnstatus == '00' || asnstatus == '30' || asnstatus == '40'){
		var url = '';
		if (processType == 'edit') {
			url = '<c:url value="/docAsnHeaderController.do?edit"/>';
		}else{
			url = '<c:url value="/docAsnHeaderController.do?add"/>';
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
						if (processType == 'edit') {
							
						} else {
							$("#ezuiForm #customerid").textbox({
								editable:false,
								icons:[]
							});
							$('#ezuiForm #asnno1').textbox('setValue',result.msg);
						}
						ezuiDatagrid.datagrid('reload');
						msg = '<font color="red">' + '提交成功' + '</font>';
						$('#ezuiBtn_renew').linkbutton('enable');
                        $('#ezuiDetailsBtn_add').linkbutton('enable');
						$('#ezuiBtn_recommit').linkbutton('disable');
						$('#ezuiBtn_copyDetail').linkbutton('enable');
						/* ezuiDialog.dialog('close'); */
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
	}else{
		$.messager.show({
			msg : '订单已经关闭或取消，不能提交', title : '<spring:message code="common.message.prompt"/>'
		});
	};
};

var nowDate = function(date){
	var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"	+ (date.getMonth() + 1);
	return date.getFullYear() + '-' + month + '-' + day;
};

/* 明细新增按钮 */
var detailsAdd = function(){
	if ($('#ezuiForm #asnno1').val() == '') {
		return;
	} else {
		processType = 'add';
		$('#docAsnDetailsId').val(0);
		$("#ezuiDetailsForm #sku").textbox({
			editable:false,
			icons:[{
				iconCls:'icon-search',
				handler: function(e){
					$("#ezuiSkuDataDialog #sku").textbox('clear');
					ezuiSkuDataClick();
					ezuiSkuDataSearch();
				}
			}]
		});
		$("#ezuiDetailsForm #location").textbox({
			editable:false,
			icons:[{
				iconCls:'icon-search',
				handler: function(e){
					$("#ezuiLocDataDialog #locationid").textbox('clear');
					ezuiLocDataClick();
					ezuiLocDataSearch();
				}
			}]
		});
		$('#ezuiDetailsForm #customerid').textbox('setValue',$('#ezuiForm #customerid').val());//？？？
		$('#ezuiDetailsForm #asnno').textbox('setValue',$('#ezuiForm #asnno1').val());
		$("#ezuiDetailsForm #asnlineno").numberbox('setValue', 0);
		$("#ezuiDetailsForm #linestatus").combobox('setValue','00').combo('readonly', true);
		$("#ezuiDetailsForm #expectedqty").numberbox('setValue', 1);
		$("#ezuiDetailsForm #receivedqty").numberbox('setValue', 0);
		
		$("#ezuiDetailsForm #totalgrossweight").numberbox("setValue",'0.000');
		$("#ezuiDetailsForm #totalcubic").numberbox("setValue",'0.000');
		$("#ezuiDetailsForm #totalprice").numberbox("setValue",'0.00');
		//$("#ezuiDetailsForm #lotatt04").combobox("setValue",'HG');
		//$("#ezuiDetailsForm #lotatt03").datebox("setValue",nowDate(new Date()));
		ezuiDetailsDialog.dialog('open');
	};
};

/* 明细编辑按钮 */
var detailsEdit = function(){
	processType = 'edit';
	$('#docAsnDetailsId').val(0);
	$("#ezuiDetailsForm #sku").textbox({
		editable:false,
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiSkuDataDialog #sku").textbox('clear');
				ezuiSkuDataClick();
				ezuiSkuDataSearch();
			}
		}]
	});
	$("#ezuiDetailsForm #location").textbox({
		editable:false,
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiLocDataDialog #locationid").textbox('clear');
				ezuiLocDataClick();
				ezuiLocDataSearch();
			}
		}]
	});
	$("#ezuiDetailsForm #linestatus").combo('readonly', true);
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	console.log(row);
	if(row){
		ezuiDetailsForm.form('load',{
			asnno : row.asnno,
			customerid : row.customerid,
			asnlineno : row.asnlineno,
			linestatus : row.linestatus,
			sku : row.sku,
			skudescrc : row.skudescrc,
			alternativesku : row.alternativesku,
			packid : row.packid,
			expectedqty : row.expectedqty,
			receivedqty : row.receivedqty,
			receivinglocation : row.receivinglocation,
			totalgrossweight : row.totalgrossweight,
			totalcubic : row.totalcubic,
            grossweight : row.grossweight,
            totalprice : row.totalprice,
            lotatt01 : row.lotatt01,
            lotatt02 : row.lotatt02,
            lotatt03 : row.lotatt03,
            lotatt04 : row.lotatt04,
            lotatt05 : row.lotatt05,
            lotatt06 : row.lotatt06,
            lotatt07 : row.lotatt07,
            lotatt08 : row.lotatt08,
            lotatt09 : row.lotatt09,
            lotatt10 : row.lotatt10,
            lotatt11 : row.lotatt11,
            lotatt12 : row.lotatt12,
            lotatt13 : row.lotatt13,
            lotatt14 : row.lotatt14
		});
        //$("#ezuiDetailsForm #customerid").textbox("setValue",row.customerid)
		if (row.linestatus == '00') {
			$('#ezuiBtn_detailsCommit').linkbutton("enable");
		} else {
			$('#ezuiBtn_detailsCommit').linkbutton("disable");
		}
		ezuiDetailsDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 明细删除按钮 */
var detailsDel = function(){
	var row = ezuiDetailsDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docAsnDetailController.do?delete',
					data : {asnno : row.asnno, asnlineno : row.asnlineno},
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
							ezuiDetailsDatagrid.datagrid('reload');
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

/* 确认收货按钮 */
var detailsReceive = function(){
	var checkedItems = $('#ezuiDetailsDatagrid').datagrid('getChecked');
	var rowcount = checkedItems.length;
// 	alert(rowcount);
	if(rowcount > 0) {  
		$.each(checkedItems ,function(index, item){
			if (item.linestatus != '00') {
				showMsg("非创建状态不能确认收货！")
			} else {
				$.ajax({
					async: false,//异步执行，保证每一条记录异步执行。
					url : 'docAsnDetailController.do?receive',
					data : {asnno : item.asnno, asnlineno : item.asnlineno},
					type : 'POST',
					dataType : 'JSON',
					success : function(result){
						var msg = '';
						try {
							msg = result.msg;
						} catch (e) {
							msg = '收货异常';
						} finally {
							ezuiDetailsDatagrid.datagrid('reload');
							ezuiDatagrid.datagrid('reload');
							$.messager.show({
								msg : msg, title : '<spring:message code="common.message.prompt"/>'
							});
						}
					}
				});
			}
		});
		ezuiDetailsDatagrid.datagrid('reload');
		ezuiDatagrid.datagrid('reload');
    } else {
    	$.messager.show({msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'});
    }
};



/* 明细提交按钮 */
var detailsCommit = function(){
    console.log("------------");
	var asnstatus = $("#ezuiDetailsDialog #linestatus").combobox('getValue');
	if (asnstatus == '00' || asnstatus == '30' || asnstatus == '40'){
		var url = '';
		if (processType == 'edit') {
			url = '<c:url value="/docAsnDetailController.do?edit"/>';
		}else{
			url = '<c:url value="/docAsnDetailController.do?add"/>';
		}
		ezuiDetailsForm.form('submit', {
			url : url,
			onSubmit : function(){
				if(ezuiDetailsForm.form('validate')){
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
						if (processType == 'edit') {
							ezuiDetailsDatagrid.datagrid('reload',{"asnno":$("#asnno1").textbox("getValue")});
						} else {
							ezuiDetailsDatagrid.datagrid('reload',{"asnno":$("#asnno1").textbox("getValue")});
						}
						ezuiDetailsDialog.dialog('close');
						msg = '<font color="red">' + "提交成功" + '</font>';
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
	}else{
		$.messager.show({
			msg : '订单已经关闭或取消，不能提交', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

/* 客户选择弹框查询 */
var ezuiCustDataDialogSearch = function(){
	ezuiCustDataDialogId.datagrid('load', {
		customerid : $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	});
};

/* 客户选择弹框清空 */
var ezuiCustToolbarClear = function(){
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};

/* 客户选择弹框-主界面 */
var ezuiCustDataClick = function(customerType){
    $("#ezuiCustDataDialog #customerType").combobox('setValue',customerType).combo('readonly', true);
    $("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
	url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
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
	queryParams:{
		customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	},
	idField : 'customerid',
	columns : [[
				{field: 'customerid',	title: '客户代码',	width: 50},
				{field: 'descrC',		title: '中文名称',	width: 50},
				{field: 'descrE',		title: '英文名称',	width: 25},
				{field: 'customerTypeName',	title: '类型',	width: 15},
				{field: 'activeFlag',	title: '激活',	width: 15, formatter:function(value,rowData,rowIndex){
					return rowData.activeFlag == '1' ? '是' : '否';
	            }}
			]],
	onDblClickCell: function(index,field,value){
		selectCust();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});

	ezuiCustDataDialog.dialog('open');
};

/* 客户选择-主界面 */
var selectCust = function(){
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		if(row.customerType == "OW"){
            $("#customerid").textbox('setValue',row.customerid);
		}else if(row.customerType == "VE"){
            $("#supplierId").textbox('setValue',row.customerid);
		}
		ezuiCustDataDialog.dialog('close');
	}
};

/* 客户选择弹框-订单信息界面 */
var ezuiCustDataDialogClick = function(){
    $("#ezuiCustDataDialog #customerType").combobox('setValue','OW').combo('readonly', true);
    $("#ezuiCustDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
	url : '<c:url value="/basCustomerController.do?showDatagrid"/>',
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
	queryParams:{
		customerType : $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag : $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	},
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
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});

	ezuiCustDataDialog.dialog('open');
};
/* 客户选择-订单信息界面 */
var selectDialogCust = function(){
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDialog #customerid").textbox('setValue',row.customerid);
        //初始化供应商
        $('#ezuiForm #supplierid').combobox({
            url:'commonController.do?getSupplier',
            valueField:'id',
            textField:'value',
			width:106,
            required:true,
            editable:false,
            queryParams:{customerId:row.customerid},
            onLoadSuccess:function () {
                $(this).combobox("setValue",row.supplierid)
            }
        });
		ezuiCustDataDialog.dialog('close');
	}
};

/* 商品选择弹框查询 */
var ezuiSkuDataSearch = function(){
	ezuiSkuDataDialogId.datagrid('load', {
		customerid : $("#ezuiSkuDataDialog #customerid").textbox("getValue"),
		sku : $("#ezuiSkuDataDialog #sku").textbox("getValue"),
		activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
	});
};
/* 商品选择弹框清空 */
var ezuiSkuToolbarClear = function(){
	$("#ezuiSkuDataDialog #sku").textbox('clear');
};

/* 商品选择弹框 */
var ezuiSkuDataClick = function(){
    $("#ezuiSkuDataDialog #customerid").textbox('setValue',$("#ezuiDetailsForm #customerid").textbox("getValue")).textbox('readonly', true);
    $("#ezuiSkuDataDialog #activeFlag").combobox('setValue','1').combo('readonly', true);
	ezuiSkuDataDialogId = $('#ezuiSkuDataDialogId').datagrid({
	url:'<c:url value="/basSkuController.do?showDatagrid"/>',
	method:'POST',
	toolbar:'#ezuiSkuToolbar',
	title:'产品档案',
	pageSize:50,
	pageList:[50, 100, 200],
	fit:true,
	border:false,
	fitColumns:false,
	nowrap:false,
	striped:true,
	collapsible:false,
	pagination:true,
	rownumbers:true,
	singleSelect:true,
	queryParams:{
		customerid : $("#ezuiDetailsForm #customerid").textbox("getValue"),
		activeFlag : $("#ezuiSkuDataDialog #activeFlag").combobox('getValue')
	},
	idField : 'sku',
	columns : [[
				{field: 'customerid',	title: '客户代码',	width: 80},
				{field: 'sku',			title: '产品代码',	width: 120},
				{field: 'descrC',		title: '中文名称',	width: 160},
				{field: 'descrE',		title: '英文名称',	width: 160},
				{field: 'activeFlag',	title: '激活',	width: 40, formatter:function(value,rowData,rowIndex){
					return rowData.activeFlag == '1' ? '是' : '否';
	            }},
				{field: 'alternateSku1',title: '产品条码',	width: 120},
				{field: 'packid',		title: '包装代码',	width: 80},
				{field: 'qty',			title: '库存数',	width: 60},
				{field: 'qtyallocated',	title: '分配数',	width: 60},
				{field: 'qtyonhold',	title: '冻结数',	width: 60}
			]],
	onDblClickCell: function(index,field,value){
		selectSku();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});

	ezuiSkuDataDialog.dialog('open');
};
/* 商品选择 */
var selectSku = function(){
	var row = ezuiSkuDataDialogId.datagrid('getSelected');
	if(row){
	    //TODO 去除产品档案信息会写界面
		$("#ezuiDetailsForm #sku").textbox('setValue',row.sku);
		$("#ezuiDetailsForm #skuName").textbox('setValue',row.descrC);
		$("#ezuiDetailsForm #skudescrc").textbox('setValue',row.descrE);
		$("#ezuiDetailsForm #alternativesku").textbox('setValue',row.alternateSku1);
		$("#ezuiDetailsForm #packid").textbox('setValue',row.packid);
		$("#ezuiDetailsForm #expectedqty").numberbox('clear');
		$("#ezuiDetailsForm #totalgrossweight").numberbox('clear');
		$("#ezuiDetailsForm #totalcubic").numberbox('clear');
		$("#ezuiDetailsForm #totalprice").numberbox('clear');
		ezuiSkuDataDialog.dialog('close');
	}
};

/* 库位选择弹框查询 */
var ezuiLocDataSearch = function(){
	ezuiLocDataDialogId.datagrid('load', {
		locationid : $("#ezuiLocDataDialog #locationid").textbox("getValue")
	});
};
/* 库位选择弹框清空 */
var ezuiLocToolbarClear = function(){
	$("#ezuiLocDataDialog #locationid").textbox('clear');
	$("#ezuiLocDataDialog #locationcategory").combobox('clear');
};
/* 库位选择弹框 */
var ezuiLocDataClick = function(){
	ezuiLocDataDialogId = $('#ezuiLocDataDialogId').datagrid({
	url:'<c:url value="/basLocationController.do?showDatagrid"/>',
	method:'POST',
	toolbar:'#ezuiLocToolbar',
	title:'库位选择',
	pageSize:50,
	pageList:[50, 100, 200],
	fit:true,
	border:false,
	fitColumns:true,
	nowrap:false,
	striped:true,
	collapsible:false,
	pagination:true,
	rownumbers:true,
	singleSelect:true,
	idField : 'locationid',
	columns : [[
				{field: 'locationid',				title: '库位',	width: 80},
				{field: 'locationusageName',		title: '库位使用',	width: 100},
				{field: 'locationcategoryName', 	title: '库位类型',	width: 100},
				{field: 'locationattributeName',	title: '库位属性',	width: 100},
				{field: 'putawayzoneName',			title: '上架区',	width: 100},
				{field: 'pickzoneName',				title: '拣货区',	width: 100}
			]],
	onDblClickCell: function(index,field,value){
		selectLocation();
	},
	onRowContextMenu : function(event, rowIndex, rowData) {
		},onLoadSuccess:function(data){
			$(this).datagrid('unselectAll');
		}
	});
	ezuiLocDataDialog.dialog('open');
};
/* 库位选择 */
var selectLocation = function(){
	var row = ezuiLocDataDialogId.datagrid('getSelected');
	if(row){
		$("#ezuiDetailsForm #location").textbox('setValue',row.locationid);
		ezuiLocDataDialog.dialog('close');
	};
};

function choseSelect_product_docAsnHeader(row){
    var sku;
    if(row){
        sku = row;
    }else{
        row = $("#productSearchGrid_docAsnHeader").datagrid("getSelections");
        if(row){
            sku = row[0]
        }
	}
	if(sku){
		$("#productId").textbox("setValue",sku.sku);
	}
    productDialog_docAsnHeader.dialog("close");
}

function afterCheckButtion(rowData) {
    console.log(rowData);
    if (rowData.asnstatus == '99' || rowData.asnstatus == '90') {
        $('#ezuiBtn_close').linkbutton('disable');
        $('#ezuiBtn_cancel').linkbutton('disable');
        $('#ezuiDetailsBtn_add').linkbutton('disable');
        $('#ezuiDetailsBtn_edit').linkbutton('disable');
        $('#ezuiDetailsBtn_del').linkbutton('disable');
        $('#ezuiDetailsBtn_receive').linkbutton('disable');
    }else if(rowData.asnstatus == '40' || rowData.asnstatus == '30' || rowData.asnstatus == '70'){
        $('#ezuiBtn_close').linkbutton('enable');
        $('#ezuiBtn_cancel').linkbutton('disable');
        $('#ezuiDetailsBtn_add').linkbutton('disable');
        $('#ezuiDetailsBtn_edit').linkbutton('disable');
        $('#ezuiDetailsBtn_del').linkbutton('disable');
        $('#ezuiDetailsBtn_receive').linkbutton('disable');
        if(rowData.asnstatus == '40'){
            $("#ezuiBtn_merge").linkbutton('enable');
        }else {
            $("#ezuiBtn_merge").linkbutton('disable');
        }
    }else if(rowData.asnstatus == '00'){
        $('#ezuiBtn_close').linkbutton('disable');
        $('#ezuiBtn_cancel').linkbutton('enable');
        $('#ezuiDetailsBtn_add').linkbutton('enable');
        $('#ezuiDetailsBtn_edit').linkbutton('enable');
        $('#ezuiDetailsBtn_del').linkbutton('enable');
        $('#ezuiDetailsBtn_receive').linkbutton('enable');
        $("#ezuiBtn_merge").linkbutton('disable');
        $("#ezuiBtn_receiving").linkbutton('enable');
    }else{

    };
    if(rowData.asntypeName == '定向订单'){
        $("#ezuiBtn_merge").linkbutton('disable');
	}
}

/**
 * 引用入库
 */
var refInDialog;
//初始化
$(function () {
    /**
     * 引用出库
     */
    refInDialog = $('#refInDialog').dialog({
        modal : true,
        title : '引用入库',
        buttons : '',
        width:250,
        height:100,
        onOpen : function() {
        },
        onClose : function() {

        }
    }).dialog('close');

    $("#refInNo").combobox({
        panelHeight: 'auto',
        editable: false,
        url:'/docOrderHeaderController.do?getRefOutOrder',
        valueField: 'id',
        textField: 'value',
        width:150
    })
})

var reuseDialog;
//初始化
$(function () {
	 $('#ezuiBtn_copyDetail').linkbutton('disable');
	reuseDialog = $('#reuseDialog').dialog({
		modal: true,
		title: '明细复用',
		buttons: '',
		width: 250,
		height: 150,
		onOpen: function () {
		},
		onClose: function () {

		}
	}).dialog('close');



})
//给明细复用入库编号文本框
function  copyDetail() {
	$('#copyFlag').combobox({
		onChange:function(newValue,oldValue){

			$('#refInNoTo').textbox('clear');
			if(newValue == 1){
				$('#refInNoTo').textbox({
					multiline:false,
					prompt:'请输入SO编号'
				});
			}else if(newValue == -1){
				$('#refInNoTo').textbox({
					multiline:false,
					prompt:'请输入ASN编号'
				});
			}else{
				$('#refInNoTo').textbox({
					multiline:false,
					prompt:'请选择订单...'
				});
			}
		}
	});
	reuseDialog.dialog("open");
	$('#copyFlag').combobox('clear');
}
//明细——复用按钮
function reuseDetailIn() {
	var copyFlag = $('#copyFlag').combobox('getValue');
	var customeridff =  $("#ezuiDialog #customerid").textbox('getValue');//获取的货主代码
	var asnno1 =  $("#ezuiDialog #asnno1").textbox('getValue');
    $.ajax({
        url : 'docAsnHeaderController.do?addDoDetailReuse',
        data : {generalNo:$("#refInNoTo").val(),detailAssno :asnno1 , customerid :customeridff , copyFlag : copyFlag},
        type : 'POST',
        dataType : 'JSON',
        success : function(result){
            try {
                if(result.success){
					$('#ezuiBtn_copyDetail').linkbutton('disable');
					$('#ezuiDetailsDatagrid').datagrid('load',{asnno:asnno1});
					reuseDialog.dialog("close");
					$('#refInNoTo').textbox('clear')
                }
                showMsg(result.msg)
            } catch (e) {
                return;
            };
        }
    });
}
function closeReuse() {
	reuseDialog.dialog("close");
	$('#refInNoTo').textbox('clear');
	$('#copyFlag').combobox('clear');
}


function showRefIn() {
    var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
    if(checkedItems.length>1 || checkedItems.length == 0){
        showMsg("请选择一张单据进行引用");
        return;
    }
    refInDialog.dialog("open");
    $("#refInNo").combobox("clear");
}

function closeRefIn() {
    refInDialog.dialog("close");
}

function doRefIn() {
    var checkedItems = $('#ezuiDatagrid').datagrid('getChecked');
    $.ajax({
        url : 'docAsnHeaderController.do?doRefIn',
        data : {orderno : checkedItems[0].asnno,refOrderno:$("#refInNo").combobox("getValue")},
        type : 'POST',
        dataType : 'JSON',
        success : function(result){
            try {
                if(result.success){
                    refInDialog.dialog("close");
                }
                showMsg(result.msg)
            } catch (e) {
                return;
            };
        }
    });

}

function asnRowStyle(index,row) {
    if(row.asnstatus == "70"){
        return 'color:green;';
    }
}

var refAddDialog;
//初始化
$(function () {
    //$('#ezuiBtn_copyDetail').linkbutton('disable');
    refAddDialog = $('#refAddDialog').dialog({
        modal: true,
        title: '引用新增',
        buttons: '',
        width: 250,
        height: 150,
        onOpen: function () {
        },
        onClose: function () {

        }
    }).dialog('close');



})
//给明细复用入库编号文本框
function refAdd() {
    $('#addRefFlag').combobox({
        onChange:function(newValue,oldValue){

            $('#addrefInNoTo').textbox('clear');
            if(newValue == 1){
                $('#addrefInNoTo').textbox({
                    multiline:false,
                    prompt:'请输入SO编号'
                });
            }else if(newValue == -1){
                $('#addrefInNoTo').textbox({
                    multiline:false,
                    prompt:'请输入ASN编号'
                });
            }else{
                $('#addrefInNoTo').textbox({
                    multiline:false,
                    prompt:'请选择订单...'
                });
            }
        }
    });
    refAddDialog.dialog("open");
    $('#addRefFlag').combobox('setValue','1');
}
//引用新增
function refAddDetailIn() {
    var addRefFlag = $('#addRefFlag').combobox('getValue');
    var customeridff =  $("#ezuiDialog #customerid").textbox('getValue');//获取的货主代码
    var asnno1 =  $("#ezuiDialog #asnno1").textbox('getValue');
    $.ajax({
        url : 'docAsnHeaderController.do?quoteDocOrder',
        data : {orderno:$('#addrefInNoTo').textbox("getValue")},
        type : 'POST',
        dataType : 'JSON',
        success : function(result){
            try {
                if(result.success){
                    // $('#ezuiBtn_copyDetail').linkbutton('disable');
                    $('#ezuiDetailsDatagrid').ezuiDatagrid('load');
                    refAddDialog.dialog("close");
                    $('#addrefInNoTo').textbox('clear')
                }
                showMsg(result.msg)
            } catch (e) {
                return;
            };
        }
    });
}
function closerefAdd() {
    refAddDialog.dialog("close");
    $('#addrefInNoTo').textbox('clear');
    $('#addRefFlag').combobox('clear');
}

function deleteMain() {
    var row = ezuiDatagrid.datagrid('getSelections');
    if(row){
        $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确认删除？', function(confirm) {
            if (confirm) {
                var arr = new Array();
                for (var i = 0; i < row.length; i++) {
                    arr.push(row[i].asnno);

                    $.ajax({
                        url : 'docAsnHeaderController.do?delete',
                        data : {'asnnos' : arr.join(",")},
                        type : 'POST',
                        dataType : 'JSON',
                        success : function(result){
                            var msg = '';
                            try {
                                msg = result.msg;
                            } catch (e) {
                                msg = '删除订单异常';
                            } finally {
                                $.messager.show({
                                    msg : msg, title : '<spring:message code="common.message.prompt"/>'
                                });
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            }
        })
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
							<th>货主代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
							<th>入库单编号</th><td><input type='text' id='asnno' class='easyui-textbox' size='16' data-options=''/></td>
							<th>释放状态</th><td><input type='text' id='releasestatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docAsnHeaderController.do?getReleasestatusCombobox"/>',
																																	valueField: 'id',
																																textField: 'value'"/></td>
						</tr>
						<tr>
							<th>单据状态</th><td><input type='text' id='asnstatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docAsnHeaderController.do?getAsnstatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							<th>至</th><td><input type='text' id='userdefinea' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docAsnHeaderController.do?getAsnstatusCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>
							<th>入库类型</th><td><input type='text' id='asntype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																															editable: false,
																															url:'<c:url value="/docAsnHeaderController.do?getAsnTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/></td>

						</tr>
						<tr>
							<th>客户订单号</th><td><input type='text' id='asnreference1' class='easyui-textbox' size='16' data-options=''/></td>
							<th>参考编号2</th><td><input type='text' id='asnreference2' class='easyui-textbox' size='16' data-options=''/></td>
							<!--<th>参考编号3</th><td><input type='text' id='asnreference3' class='easyui-textbox' size='16' data-options=''/></td>-->
							<th>供应商</th><td><input type="text" id="supplierId"/></td>
							<%--仓库在js中设置--%>
							<th style="text-align: right">仓库</th><td><input type="text" id="warehouseId"/></td>
						</tr>
						<tr>
							<th>预计入库时间</th><td><input type='text' id='expectedarrivetime1' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='expectedarrivetime2' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>备注</th><td><input type='text' id='notes' class='easyui-textbox' size='16' data-options=''/></td>
							<th style="text-align: right">产品代码</th>	<td><input type="text" id="productId"/></td>
						</tr>
						<tr>
							<th>创建时间</th><td><input type='text' id='addtime' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='edisendtime5' class='easyui-datetimebox' size='16' data-options=''/></td>
							<th>创建人</th><td><input type='text' id='addwho' class='easyui-textbox' size='16' data-options=''/></td>
							<th colspan="2">
								<input id="asnstatusCheck" type="checkbox" onclick="" checked="checked"><label for="asnstatusCheck">显示关闭/取消</label>
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								<a onclick='toImportData();' id='ezuiBtn_import' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>导入</a>
							</th>

						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>
					<a onclick='refAdd();' id='ezuiBtn_refAdd' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>引用新增</a>
					<%--<a onclick='deleteMain();' id='ezuiBtn_delete' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>删除订单</a>--%>
					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
					<a onclick='showRefIn()' id='ezuiBtn_ref' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>引用入库</a>
					<a onclick='mergeOrder();' id='ezuiBtn_merge' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>生成上架任务清单</a>
					<a onclick='mergeReceiving();' id='ezuiBtn_receiving' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-ok"' href='javascript:void(0);'>确认收货</a>
                    <a onclick='cancel();' id='ezuiBtn_cancel' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>取消订单</a>
                    <a onclick='closeCheck();' id='ezuiBtn_close' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>关闭订单</a>
				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
	<div id='ezuiDialogBtn'>
<%-- 		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiDetailsDialogBtn'>
		<a onclick='detailsCommit();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
	<div id='ezuiDetailsMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='detailsAdd();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.skuAdd'/></div>
		<div onclick='detailsDel();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.skuDelete'/></div>
		<div onclick='detailsEdit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.skuEdit'/></div>
	</div>
	
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

	<!--引用出库 -->
	<div id="refInDialog" style="display: none;text-align: center">
		<table width="100%">
			<tr>
				<td>SO编号</td>
				<td>
					<input id="refInNo" type="text"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a onclick='closeRefIn()' id='closeRefIn' class='easyui-linkbutton' data-options='' href='javascript:void(0);'>关闭</a>
					<a onclick='doRefIn()' id='doRefIn' class='easyui-linkbutton' data-options='' href='javascript:void(0);'>引用</a>
				</td>
			</tr>
		</table>
	</div>
	<!-- 明细复用 -->
	<div id="reuseDialog" style="display: none;text-align: center">
		<table width="100%">
			<tr>
				<td>订单
				<input type='text' id='copyFlag' size='16' class="easyui-combobox" data-options="
																								editable: false,
																								panelHeight: 'auto',
																								width:'150',
																								valueField: 'id',
																								textField: 'value',
																								data: [{
																									id: '1',
																									value: '复用出库明细'
																								},{
																									id: '-1',
																									value: '复用入库明细'
																								}]" />
				</td>
			</tr>
			<tr>
				<td>
					编号
					<input id="refInNoTo" type="text" class='easyui-textbox' size='16' data-options="panelHeight: 'auto', width:'150'"/>
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a onclick='closeReuse()' id='closeRefInReuse' class='easyui-linkbutton' data-options=''
					   href='javascript:void(0);'>关闭</a>
					<a onclick='reuseDetailIn()' id='doRefInReuse' class='easyui-linkbutton' data-options='' href='javascript:void(0);'>复用</a>
				</td>
			</tr>
		</table>
	</div>

	<!-- 导入end -->
	<c:import url='/WEB-INF/jsp/docAsnHeader/dialog.jsp' />
	<c:import url='/WEB-INF/jsp/docAsnHeader/custDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docAsnHeader/detailsDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docAsnHeader/skuDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docAsnHeader/locDialog.jsp' />
	<c:import url='/WEB-INF/jsp/docAsnHeader/refAdd.jsp' />


	<!--产品查询 -->
	<div id="ezuiProductSearchDialog">

	</div>
</body>
</html>
