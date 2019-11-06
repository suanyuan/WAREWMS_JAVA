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
var ezuiMenu;                 //右键菜单
var ezuiForm;                 //一级dialog form
var ezuiDialog;               //一级dialog

var ezuiAcceptanceForm;       //验收作业dialog form
var ezuiAcceptanceDialog;     //验收作业dialog
var ezuiAcceptanceFormS;       //验收作业dialog form 单条验收
var ezuiAcceptanceDialogS;     //验收作业dialog  单条验收

var ezuiDatagrid;              //主页datagrid
var ezuiAccDataDialog;         //验收单号选择框
var ezuiAccDataDialogId;       //验收单号选择框

var ezuiCustDataDialog;        //货主
var ezuiCustDataDialogId;      //货主
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();   //右键菜单
	ezuiForm = $('#ezuiForm').form();   //一级dialog form
	ezuiAcceptanceForm = $('#ezuiAcceptanceForm').form();   //验收作业dialog form
	ezuiAcceptanceFormS = $('#ezuiAcceptanceFormS').form();   //验收作业dialog form 单条验收
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/docQcDetailsController.do?showDatagrid"/>',
		method:'POST',
		toolbar : '#toolbar',
		title: '验收作业',
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
		columns : [[
			{field:'ck',checkbox:true},
			// {field: 'qcno',		title: '验收单号',	width: 124 },
			// {field: 'pano',		title: '上架单号',	width: 124 },
            // {field: 'qclineno',		title: '验收行号',	width: 80 },
			{field: 'linestatus',		title: '行状态',	width: 80,formatter:AcceptancestatusFormatter},
			{field: 'lotatt10',		title: '质量状态',	width: 80,formatter:ZL_TYPstatusFormatter },
			{field: 'userdefine1',		title: '库位',	width: 110 },



			{field: 'customerid',		title: '货主代码',	width: 134 },
			{field: 'shippershortname',		title: '货主简称',	width: 134 },
			{field: 'lotatt08',		title: '供应商',	width: 134 },
			{field: 'sku',		title: '产品代码',	width: 150 },
			{field: 'lotatt12',		title: '产品名称',	width: 200 },
			{field: 'lotatt06',		title: '产品注册证号/备案号',	width: 200 },
			{field: 'descrc',		title: '规格/型号',	width: 100 },
			{field: 'lotatt04',		title: '生产批号',	width: 110 },
			{field: 'lotatt07',		title: '灭菌批号',	width: 110 },
			{field: 'lotatt05',		title: '序列号',	width: 110 },
			{field: 'lotatt01',		title: '生产日期',	width: 100 },
			{field: 'lotatt02',		title: '有效期/失效期',	width: 100 },
			{field: 'lotatt11',		title: '存储条件',	width: 100 },
			{field: 'lotatt15',		title: '生产企业',	width: 200 },
			{field: 'reservedfield06',		title: '生产许可证号/备案号',	width: 150 },
			{field: 'qcqtyExpected',		title: '待验收件数',	width: 100 },
			{field: 'qcqtyCompleted',		title: '已验收件数',	width: 100},
			{field: 'qcqtyExpectedEach',		title: '待验收数量',	width: 100 },
			{field: 'qcqtyCompletedEach',		title: '已验收数量',	width: 100},
			{field: 'qcdescr',		title: '验收说明',	width: 160},
			{field: 'editwho',		title: '验收人',	width: 100 },
			{field: 'edittime',		title: '验收时间',	width: 134 },
			// {field: 'lotnum',		title: '批次号',	width: 134 },
			{field: 'addtime',		title: '创建时间',	width: 134 },
			{field: 'addwho',		title: '创建人',	width: 100 },
			{field: 'qty1',		title: '换算率',	width: 100},
			{field: 'reservedfield09',		title: '医疗',	width: 100,hidden:true},
			{field: 'reservedfield14',		title: '生产企业（默认）',	width: 100,hidden:true}
		]],
		onDblClickCell: function(index,field,value){

		},
	     onLoadSuccess:function(data){
			<%--ajaxBtn($('#menuId').val(), '<c:url value="/docQcHeaderController.do?getBtn"/>', ezuiMenu);--%>
			$(this).datagrid('unselectAll');
		}
	});
//验收单号放大镜初始化
	$("#qcno").textbox({
		icons:[{
			iconCls:'icon-search',
			handler: function(e){
				$("#ezuiAccDataDialog #pano").textbox('clear');
				$("#ezuiAccDataDialog #qcno").textbox('clear');
				ezuiAccDataClick();
				ezuiAccDataDialogSearch();
			}
		}]
	});
//查询条件货主字段初始化
	$("#toolbar #customerid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick();
				ezuiCustDataDialogSearch();
			}
		}]
	});
//查询条件货主字段初始化
	$("#ezuiAccToolbar #customerid").textbox({
		icons: [{
			iconCls: 'icon-search',
			handler: function (e) {
				$("#ezuiCustDataDialog #customerid").textbox('clear');
				ezuiCustDataClick("S");
				ezuiCustDataDialogSearch();
			}
		}]
	});
//一级dialog初始化
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');
//验收作业dialog
	ezuiAcceptanceDialog = $('#ezuiAcceptanceDialog').dialog({
		modal : true,
		width:260,
		height:240,
		title : '验收作业',
		buttons : '#ezuiAcceptanceDialogBtn',
		onClose : function() {
			ezuiFormClear(ezuiAcceptanceForm);
		}
	}).dialog('close');
//验收作业dialog 单条验收
	ezuiAcceptanceDialogS = $('#ezuiAcceptanceDialogS').dialog({
		modal : true,
		width:570,
		height:280,
		title : '验收作业',
		buttons : '#ezuiAcceptanceDialogSBtn',
		onClose : function() {
			ezuiFormClear(ezuiAcceptanceFormS);
		}
	}).dialog('close');
//验收单号选择弹框
	ezuiAccDataDialog = $('#ezuiAccDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//货主查询弹框初始化
	ezuiCustDataDialog = $('#ezuiCustDataDialog').dialog({
		modal: true,
		title: '<spring:message code="common.dialog.title"/>',
		buttons: '',
		onOpen: function () {

		},
		onClose: function () {

		}
	}).dialog('close');
//单条验收的时候 通过件数算数量
	$("input",$("#ezuiAcceptanceFormS #qcqty").next("span")).keyup(function(event){
		var v = $('#ezuiAcceptanceFormS #qcqty').next().children().val();
		var qty1=$("#ezuiAcceptanceFormS #qty1").val();
		$("#ezuiAcceptanceFormS #qcqtyEach").textbox('setValue',v*qty1);
	});
//单条验收的时候 通过件数算件数
	$("input",$("#ezuiAcceptanceFormS #qcqtyEach").next("span")).keyup(function(event){
		var v = $('#ezuiAcceptanceFormS #qcqtyEach').next().children().val();
		var qty1=$("#ezuiAcceptanceFormS #qty1").val();
		$("#ezuiAcceptanceFormS #qcqty").textbox('setValue',v/qty1);
	});

});
//增加
var add = function(){
	processType = 'add';
	$('#docQcHeaderId').val(0);
	ezuiDialog.dialog('open');
};
//修改
var edit = function(){
	processType = 'edit';
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		ezuiForm.form('load',{
			qcno : row.qcno,
			pano : row.pano,
			customerid : row.customerid,
			qcreference1 : row.qcreference1,
			qcreference2 : row.qcreference2,
			qcreference3 : row.qcreference3,
			qcreference4 : row.qcreference4,
			qcreference5 : row.qcreference5,
			qctype : row.qctype,
			qcstatus : row.qcstatus,
			qccreationtime : row.qccreationtime,
			userdefine1 : row.userdefine1,
			userdefine2 : row.userdefine2,
			userdefine3 : row.userdefine3,
			userdefine4 : row.userdefine4,
			userdefine5 : row.userdefine5,
			notes : row.notes,
			addtime : row.addtime,
			addwho : row.addwho,
			edittime : row.edittime,
			editwho : row.editwho,
			qcPrintFlag : row.qcPrintFlag,
			warehouseid : row.warehouseid
		});
		ezuiDialog.dialog('open');
	}else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};
//删除
var del = function(){
	var row = ezuiDatagrid.datagrid('getSelected');
	if(row){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '<spring:message code="common.message.confirm.delete"/>', function(confirm) {
			if(confirm){
				$.ajax({
					url : 'docQcHeaderController.do?delete',
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
//一级dialog提交
var commit = function(){
	var url = '';
	if (processType == 'edit') {
		url = '<c:url value="/docQcHeaderController.do?edit"/>';
	}else{
		url = '<c:url value="/docQcHeaderController.do?add"/>';
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
//验收作业提交
var commitAcceptance = function(type){
	var typeC=type; //是否合格
	url = '<c:url value="/docQcDetailsController.do?submitDocQcList"/>';
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	var isCan=true;//是否可以验收
	for (var i = 0; i < rows.length; i++) {
		 var linestatus=rows[i].linestatus;
		 //判断细单验收状态
		 if(linestatus!='00'){
			 msg="列表存在已验收产品!"
			isCan=false;
		 	break;
		 }
		data=new Object();
		data.qcno=rows[i].qcno;              //验收单号
		data.qclineno=rows[i].qclineno;      //验收行号
		data.lotatt02=rows[i].lotatt02;  //效期
		data.lotatt04=rows[i].lotatt04;  //生产批号
		data.lotnum=rows[i].lotnum;
		data.allqcflag=0;
		data.lotatt10=typeC;                //合格 待处理
		//判断验收的行数
		if(rows.length==1){
			var qcqtyNum=$('#ezuiAcceptanceForm #qcqty').textbox('getValue');
		    data.qcqty=qcqtyNum;
		}else{
			var qcqtyNumAll=rows[i].qcqtyExpected;
			data.qcqty=qcqtyNumAll;
		}
		data.qcdescr=$('#ezuiAcceptanceForm #qcdescr').combobox('getText');  //验收描述
		forms.push(data);

	}
    if(!isCan){
		$.messager.show({
			msg : msg, title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	//判断待处理需要弹窗 合格则是继续做下去
	if(typeC=="DCL"){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '验收记录为待处理,是否继续操作!', function(confirm) {
			if(confirm){
				if(ezuiAcceptanceForm.form('validate')) {
					$.messager.progress({
						text: '<spring:message code="common.message.data.processing"/>', interval: 100
					});

					$.ajax({
						url:url,
						data:"forms="+JSON.stringify(forms),
						dataType: 'json',
						error: function () {

						},
						success: function (result) {
							try{

									msg=result.msg;

							}catch (e) {
								$.messager.show({
									msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
								});
							}finally {
								ezuiDatagrid.datagrid('reload');
								ezuiAcceptanceDialog.dialog('close');
								$.messager.show({
									msg : msg, title : '<spring:message code="common.message.prompt"/>'
								});
								$.messager.progress('close');
							}
						}
					});
				}else{
					msg = '<font color="red">' +'请输入完整!'+ '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');

				}
			}else{
				return;
			}
		});

	}else{
		if(ezuiAcceptanceForm.form('validate')) {
			$.messager.progress({
				text: '<spring:message code="common.message.data.processing"/>', interval: 100
			});

			$.ajax({
				url:url,
				data:"forms="+JSON.stringify(forms),
				dataType: 'json',
				error: function () {

				},
				success: function (result) {
					try{
						msg=result.msg;

					}catch (e) {
						$.messager.show({
							msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
						});

					}finally {
						ezuiDatagrid.datagrid('reload');
						ezuiAcceptanceDialog.dialog('close');
						$.messager.show({
							msg : msg, title : '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
					}
				}
			});
		}else{
			msg = '<font color="red">' +'请输入完整!'+ '</font>';
			$.messager.show({
				msg : msg, title : '<spring:message code="common.message.prompt"/>'
			});
			$.messager.progress('close');

		}

	}


};
function escEncode(strSendToServer){
	strSendToServer = strSendToServer.replace(/\%/g,'%25').replace(/\&/g,'%26').replace(/\#/g,'%23');
	strSendToServer = strSendToServer.replace(/\+/g,'%2B').replace(/\//g,'%2F').replace(/\\/g,'%5C');
	strSendToServer = strSendToServer.replace(/\=/g,'%3D').replace(/\?/g,'%3F').replace(/\ /g,'%20');
	strSendToServer = strSendToServer.replace(/\./g,'%2E').replace(/\:/g,'%3A');
	return strSendToServer;
}
//验收作业提交 单条验收
var commitAcceptanceS = function(type){
	var typeC=type; //是否合格
	url = '<c:url value="/docQcDetailsController.do?submitDocQcList"/>';
	var rows = ezuiDatagrid.datagrid('getChecked');
	var forms=[];
	var data=null;
	var msg='';
	var isCan=true;//是否可以验收
	for (var i = 0; i < rows.length; i++) {
		var linestatus=rows[i].linestatus;
		//判断细单验收状态
		if(linestatus!='00'){
			msg="列表存在已验收产品!"
			isCan=false;
			break;
		}
		data=new Object();
		data.qcno=rows[i].qcno;              //验收单号
		data.qclineno=rows[i].qclineno;      //验收行号
		data.lotnum=rows[i].lotnum;
		data.allqcflag=0;
		data.lotatt10=typeC;                //合格 待处理
		data.qcqty=$('#ezuiAcceptanceFormS #qcqty').textbox('getValue');     //验收件数
		data.qcdescr=$('#ezuiAcceptanceFormS #qcdescr').combobox('getText');  //验收描述
		data.lotatt01=$('#ezuiAcceptanceFormS #lotatt01').datebox('getValue');
		data.lotatt02=$('#ezuiAcceptanceFormS #lotatt02').datebox('getValue');
		data.lotatt04=$('#ezuiAcceptanceFormS #lotatt04').textbox('getValue');
		data.lotatt05=$('#ezuiAcceptanceFormS #lotatt05').textbox('getValue');
		data.lotatt06=$('#ezuiAcceptanceFormS #lotatt06').combobox('getValue');
		data.lotatt11=escEncode($('#ezuiAcceptanceFormS #lotatt11').textbox('getValue'));
		data.lotatt15=$('#ezuiAcceptanceFormS #lotatt15').textbox('getValue');
		forms.push(data);

	}
	if(!isCan){
		$.messager.show({
			msg : msg, title : '<spring:message code="common.message.prompt"/>'
		});
		return;
	}
	//判断待处理需要弹窗 合格则是继续做下去
	if(typeC=="DCL"){
		$.messager.confirm('<spring:message code="common.message.confirm"/>', '验收记录为待处理,是否继续操作!', function(confirm) {
			if(confirm){
				if(ezuiAcceptanceFormS.form('validate')) {
					$.messager.progress({
						text: '<spring:message code="common.message.data.processing"/>', interval: 100
					});

					$.ajax({
						url:url,
						data:"forms="+JSON.stringify(forms),
						dataType: 'json',
						error: function () {

						},
						success: function (result) {
							try{

								msg=result.msg;

							}catch (e) {
								$.messager.show({
									msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
								});
							}finally {
								ezuiDatagrid.datagrid('reload');
								ezuiAcceptanceDialogS.dialog('close');
								$.messager.show({
									msg : msg, title : '<spring:message code="common.message.prompt"/>'
								});
								$.messager.progress('close');
							}
						}
					});
				}else{
					msg = '<font color="red">' +'请输入完整!'+ '</font>';
					$.messager.show({
						msg : msg, title : '<spring:message code="common.message.prompt"/>'
					});
					$.messager.progress('close');

				}
			}else{
				return;
			}
		});

	}else{
		if(ezuiAcceptanceFormS.form('validate')) {
			$.messager.progress({
				text: '<spring:message code="common.message.data.processing"/>', interval: 100
			});

			$.ajax({
				url:url,
				data:"forms="+JSON.stringify(forms),
				dataType: 'json',
				error: function () {

				},
				success: function (result) {
					try{
						msg=result.msg;

					}catch (e) {
						$.messager.show({
							msg :'数据错误!', title : '<spring:message code="common.message.prompt"/>'
						});

					}finally {
						ezuiDatagrid.datagrid('reload');
						ezuiAcceptanceDialogS.dialog('close');
						$.messager.show({
							msg : msg, title : '<spring:message code="common.message.prompt"/>'
						});
						$.messager.progress('close');
					}
				}
			});
		}else{
			msg = '<font color="red">' +'请输入完整!'+ '</font>';
			$.messager.show({
				msg : msg, title : '<spring:message code="common.message.prompt"/>'
			});
			$.messager.progress('close');

		}

	}


};
//验收作业
var acceptanceWork = function(){
	var rows = ezuiDatagrid.datagrid('getChecked');
	var num=rows.length;
    if(num==1){
		$("#ezuiAcceptanceFormS #qcqtyExpected").textbox('enable');
		$("#ezuiAcceptanceFormS #qcqty").textbox('enable');
		$("#ezuiAcceptanceFormS #qcqtyExpectedEach").textbox('enable');
		$("#ezuiAcceptanceFormS #qcqtyEach").textbox('enable');
		//注册证combobox
		var lotatt06=rows[0].lotatt06;
		getRgisterListBylotatt06(lotatt06);
		//判断是否医疗
		var num=rows[0].reservedfield09;
		console.log(num);
		if(num.toString()=="1"){
			$("#ezuiAcceptanceFormS #lotatt15").textbox({
				required:true
			});
		}else{
			$("#ezuiAcceptanceFormS #lotatt15").textbox({
				required:false
			});
		}
		ezuiAcceptanceFormS.form('load',{
			sku:rows[0].sku,
			lotatt11:rows[0].lotatt11,
			lotatt04:rows[0].lotatt04,
			lotatt05:rows[0].lotatt05,
			lotatt01:rows[0].lotatt01,
			lotatt02:rows[0].lotatt02,
			lotatt06:rows[0].lotatt06,
			lotatt15:rows[0].reservedfield14,
			reservedfield06:rows[0].reservedfield06,
			qcqtyExpected:rows[0].qcqtyExpected,
			qcqtyExpectedEach:rows[0].qcqtyExpectedEach,
			qty1:rows[0].qty1

		});
		ezuiAcceptanceDialogS.dialog('open');
	}else if(num>1){

         var sums=new Object();
		sums.qcqtyExpected =0;
		sums.qcqtyCompleted =0;
		sums.qcqtyExpectedEach =0;
		sums.qcqtyCompletedEach =0;
		for (var i = 0; i < rows.length; i++) {
			sums.qcqtyExpected  += rows[i]['qcqtyExpected'];
			sums.qcqtyExpectedEach  += rows[i]['qcqtyExpectedEach'];
		}
		$("#ezuiAcceptanceForm #qcqtyExpected").textbox('setValue',sums.qcqtyExpected );
		$("#ezuiAcceptanceForm #qcqty").textbox('setValue',sums.qcqtyExpected );
		$("#ezuiAcceptanceForm #qcqtyExpectedEach").textbox('setValue',sums.qcqtyExpectedEach);
		$("#ezuiAcceptanceForm #qcqtyEach").textbox('setValue',sums.qcqtyExpectedEach);

		$("#ezuiAcceptanceForm #qcqtyExpected").textbox('disable');
		$("#ezuiAcceptanceForm #qcqty").textbox('disable');
		$("#ezuiAcceptanceForm #qcqtyExpectedEach").textbox('disable');
		$("#ezuiAcceptanceForm #qcqtyEach").textbox('disable');
		ezuiAcceptanceDialog.dialog('open');
	}
    else{
		$.messager.show({
			msg : '<spring:message code="common.message.selectRecord"/>', title : '<spring:message code="common.message.prompt"/>'
		});
	}
};

//打印验收
var printQcHeader = function(){

	var qcno = $('#qcno').val();//验收单号
	var linestatus = $('#linestatus').combobox('getValue');//验收状态
	var lotatt10 = $('#lotatt10').combobox('getValue');  //z质量状态
	//如果当前页面没有行数那么就不给它做
	console.log($('#ezuiDatagrid').datagrid('getRows'));
	var rowsSize = $('#ezuiDatagrid').datagrid('getRows');
	if(rowsSize.length > 0){
		window.open(sy.bp()+"/docQcHeaderController.do?printQcHeader&qcno="+qcno+"&linestatus="+linestatus+"&lotatt10="+lotatt10);
	}else {
		$.messager.show({
			msg: "<spring:message code='common.message.export.failed'/>",
			title: "<spring:message code='common.message.prompt'/>"
		});
	}

}



//主页查询
var doSearch = function() {
	if ($('#qcno').val() == null || $('#qcno').val() == "") {
		ezuiDatagrid.datagrid('load', {
			qcno: $('#qcno').val(),                            //验收单号
			linestatus: $('#linestatus').combobox('getValue'), //验收状态
			lotatt10: $('#lotatt10').combobox('getValue'),  //z质量状态
			// customerid: $('#customerid').val(),              //货主代码
			// shippershortname: $('#shippershortname').val(),   //货主简称
			sku:$('#skuQ').val(),
            descrc:$('#descrcQ').val(),
            lotatt04:$('#lotatt04Q').val(),
            lotatt05:$('#lotatt05Q').val(),



        });
		$.messager.show({
			msg: '请先选择验收单号!', title: '<spring:message code="common.message.prompt"/>'
		});
	} else {
		ezuiDatagrid.datagrid('load', {
			qcno: $('#qcno').val(),                            //验收单号
			linestatus: $('#linestatus').combobox('getValue'), //验收状态
			lotatt10: $('#lotatt10').combobox('getValue'),  //z质量状态
			// customerid: $('#customerid').val(),              //货主代码
			// shippershortname: $('#shippershortname').val(),   //货主简称
            sku:$('#skuQ').val(),
            descrc:$('#descrcQ').val(),
            lotatt04:$('#lotatt04Q').val(),
            lotatt05:$('#lotatt05Q').val(),
		});

	}
}
//主页清空查询
var ezuiToolbarClear=function () {
	ezuiComboboxClear("#toolbar");
    $("#skuQ").textbox('clear');
    $('#descrcQ').textbox('clear');
    $('#lotatt04Q').textbox('clear');
    $('#lotatt05Q').textbox('clear');
}

/* 单号选择弹框查询 */
var ezuiAccDataDialogSearch = function () {
	ezuiAccDataDialogId.datagrid('load', {
		pano: $("#ezuiAccDataDialog #pano").textbox("getValue"),
		qcno: $("#ezuiAccDataDialog #qcno").textbox("getValue"),
		customerid: $("#ezuiAccDataDialog #customerid").textbox("getValue"),
        asnreference1: $("#ezuiAccDataDialog #asnreference1").textbox("getValue"),

	});
};
/* 单号选择弹框清空 */
var ezuiAccToolbarClear = function () {
	$("#ezuiAccDataDialog #pano").textbox('clear');
	$("#ezuiAccDataDialog #qcno").textbox('clear');
    $("#ezuiAccDataDialog #customerid").textbox('clear');
    $("#ezuiAccDataDialog #asnreference1").textbox('clear');
    // $("#ezuiAccDataDialog #skuQ").textbox('clear');
	// $('#descrcQ').textbox('clear');
	// $('#lotatt04Q').textbox('clear');
	// $('#lotatt05Q').textbox('clear');
};
/* 单号选择弹框 */
var ezuiAccDataClick = function () {
	ezuiAccDataDialogId = $('#ezuiAccDataDialogId').datagrid({
		url: '<c:url value="/docQcHeaderController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiAccToolbar',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		columns: [[
			{field: 'pano', title: '上架单号', width: 80},
			{field: 'qcno', title: '验收单号', width: 100},
            {field: 'asnreference1',		title: '客户订单号',	width: 134 },
			{field: 'qcstatus', title: '验收状态', width: 100,formatter:AcceptancestatusFormatter},

			{field: 'customerid', title: '货主代码', width: 100}
		]],
		onDblClickCell: function (index, field, value) {
			selectAcceptance();
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	ezuiAccDataDialog.dialog('open');
};
/* 单号选择 */
var selectAcceptance = function () {
	var row = ezuiAccDataDialogId.datagrid('getSelected');
	if (row) {
		// $("#toolbar #pano").textbox('setValue', row.pano);
		$("#toolbar #qcno").textbox('setValue', row.qcno);
		doSearch();
		ezuiAccDataDialog.dialog('close');
	}
	;
};

//货主查询弹框弹出start=========================
var ezuiCustDataClick = function (type) {
	ezuiCustDataDialogId = $('#ezuiCustDataDialogId').datagrid({
		url: '<c:url value="/basCustomerController.do?showDatagrid"/>',
		method: 'POST',
		toolbar: '#ezuiCustToolbar',
		title: '客户档案',
		pageSize: 50,
		pageList: [50, 100, 200],
		fit: true,
		border: false,
		fitColumns: true,
		nowrap: false,
		striped: true,
		collapsible: false,
		pagination: true,
		rownumbers: true,
		singleSelect: true,
		queryParams: {
			activeFlag: '1',
			customerType: 'OW'
		},
		idField: 'id',
		columns: [[
			{field: 'customerid', title: '客户代码', width: 15},
			{field: 'descrC', title: '中文名称', width: 50},
			{field: 'descrE', title: '英文名称', width: 50},
			{field: 'customerTypeName', title: '类型', width: 15},
			{
				field: 'activeFlag', title: '激活', width: 15, formatter: function (value, rowData, rowIndex) {
					return rowData.activeFlag == '1' ? '是' : '否';
				}
			}
		]],
		onDblClickCell: function (index, field, value) {
			selectCust(type);
		},
		onRowContextMenu: function (event, rowIndex, rowData) {
		}, onLoadSuccess: function (data) {
			$(this).datagrid('unselectAll');
		}
	});
	$("#ezuiCustDataDialog #customerType").combobox('setValue', 'OW').combobox('setText', '货主');
	$("#ezuiCustDataDialog #activeFlag").combobox('setValue', '1').combobox('setText', '是');
	ezuiCustDataDialog.dialog('open');
};

//货主查询弹框查询按钮
var ezuiCustDataDialogSearch = function () {
	ezuiCustDataDialogId.datagrid('load', {
		customerid: $("#ezuiCustDataDialog #customerid").textbox("getValue"),
		customerType: $("#ezuiCustDataDialog #customerType").combobox('getValue'),
		activeFlag: $("#ezuiCustDataDialog #activeFlag").combobox('getValue')
	});
};
//货主查询弹框选择按钮
var selectCust = function (type) {
	var row = ezuiCustDataDialogId.datagrid('getSelected');
	var type=type;
	if (row) {
		if(type=="S"){
			$("#ezuiAccToolbar #customerid").textbox('setValue', row.customerid);

		}else {
			$("#toolbar #customerid").textbox('setValue', row.customerid);
		}
		ezuiCustDataDialog.dialog('close');
	}
};
//货主查询弹框清空按钮
var ezuiCustToolbarClear = function () {
	$("#ezuiCustDataDialog #customerid").textbox('clear');
};
//货主查询弹框弹出end==========================


//单个验收作业 注册证combobox
var  getRgisterListBylotatt06= function (lotatt06) {

	$('#ezuiAcceptanceFormS #lotatt06').combobox({
		url:'/docQcDetailsController.do?getRgisterListBylotatt06',
		valueField:'productRegisterNo',
		textField:'productRegisterNo',
		panelHeight: 'auto',
		queryParams: {
			lotatt06: lotatt06
		},
		onSelect:function () {
			var v=$(this).combobox("getValue");
			var opts = $(this).combobox("getData");
			console.log(opts)
			var result=null;
			for(var i=0;i<opts.length;i++){
				if(opts[i].productRegisterNo==v){
					result=opts[i];
					break;
				}
			}
			console.log(result)
			if(result!=null){
				console.log(result.enterpriseInfo.enterpriseName)

				$("#ezuiAcceptanceFormS #lotatt15").textbox('setValue',result.enterpriseInfo.enterpriseName);
				$("#ezuiAcceptanceFormS #lotatt11").textbox('setValue',result.storageConditions);
				$("#ezuiAcceptanceFormS #reservedfield06").textbox('setValue',result.licenseOrRecordNol);
			}
		}
	});
};
</script>
</head>
<body>
	<input type='hidden' id='menuId' name='menuId' value='${menuId}'/>
<%--主页datagrid工具栏--%>
	<div class='easyui-layout' data-options='fit:true,border:false'>
		<div data-options='region:"center",border:false' style='overflow: hidden;'>
			<div id='toolbar' class='datagrid-toolbar' style='padding: 5px;'>
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
<%--							<th>上架单号</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>--%>
							<th>验收单号</th><td><input type='text' id='qcno' class='easyui-textbox' size='16' data-options='editable: false'/></td>
							<th>验收状态</th><td><input type='text' id='linestatus' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '未验收'},
																																       {label: '40',
																																         value: '已验收'},
																																       {label: '',
																																         value: '全部'}]"/></td>
							<th>质量状态</th><td><input type='text' id='lotatt10' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?qcState"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
							<th>产品代码</th><td><input type='text' id='skuQ' class='easyui-textbox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>规格</th><td><input type='text' id='descrcQ' class='easyui-textbox' size='16' data-options=''/></td>
							<th>批号</th><td><input type='text' id='lotatt04Q' class='easyui-textbox' size='16' data-options=''/></td>
							<th>序列号</th><td><input type='text' id='lotatt05Q' class='easyui-textbox' size='16' data-options=''/></td>

							<td colspan="2">
								<a onclick='doSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
				<div>
					<a onclick='acceptanceWork()' id='ezuiBtn_check' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>验收作业</a>
<%--					<a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.add'/></a>--%>
<%--					<a onclick='del();' id='ezuiBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>--%>
<%--					<a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>--%>
					<a onclick='clearDatagridSelected("#ezuiDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
					<a onclick='printQcHeader()' id='ezuiBtn_printQcSeacrch' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-print"' href='javascript:void(0);'>验收任务清单</a>

				</div>
			</div>
			<table id='ezuiDatagrid'></table> 
		</div>
	</div>
<%--一级dialog--%>
	<div id='ezuiDialog' style='padding: 10px;'>
		<form id='ezuiForm' method='post'>
			<input type='hidden' id='docQcHeaderId' name='docQcHeaderId'/>
			<table>
				<tr>
					<th>待输入0</th>
					<td><input type='text' name='qcno' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入1</th>
					<td><input type='text' name='pano' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入2</th>
					<td><input type='text' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入3</th>
					<td><input type='text' name='qcreference1' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入4</th>
					<td><input type='text' name='qcreference2' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入5</th>
					<td><input type='text' name='qcreference3' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入6</th>
					<td><input type='text' name='qcreference4' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入7</th>
					<td><input type='text' name='qcreference5' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入8</th>
					<td><input type='text' name='qctype' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入9</th>
					<td><input type='text' name='qcstatus' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入10</th>
					<td><input type='text' name='qccreationtime' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入11</th>
					<td><input type='text' name='userdefine1' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>待输入12</th>
					<td><input type='text' name='userdefine2' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入13</th>
					<td><input type='text' name='userdefine3' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入14</th>
					<td><input type='text' name='userdefine4' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入15</th>
					<td><input type='text' name='userdefine5' class='easyui-textbox' size='16' data-options='required:true'/></td>

					<th>待输入16</th>
					<td><input type='text' name='notes' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>创建时间</th>
					<td><input type='text' name='addtime' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
				<tr>
					<th>创建人</th>
					<td><input type='text' name='addwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>编辑时间</th>
					<td><input type='text' name='edittime' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>编辑人</th>
					<td><input type='text' name='editwho' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入21</th>
					<td><input type='text' name='qcPrintFlag' class='easyui-textbox' size='16' data-options='required:true'/></td>
					<th>待输入22</th>
					<td><input type='text' name='warehouseid' class='easyui-textbox' size='16' data-options='required:true'/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--右键菜单--%>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>

<%--验收单号弹窗--%>
	<div id='ezuiAccDataDialog'  style="width:900px;height:480px;padding:10px 20px"   >
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiAccToolbar' class='datagrid-toolbar'   style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>上架单号</th><td><input type='text' id='pano' class='easyui-textbox' size='16' data-options=''/></td>
								<th>验收单号</th><td><input type='text' id='qcno' class='easyui-textbox' size='16' data-options=''/></td>
								<td>
									<a onclick='ezuiAccDataDialogSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectAcceptance();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiAccToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
							<tr>

								<th>客户订单号</th><td><input type='text' id='asnreference1' class='easyui-textbox' size='16' data-options=''/></td>
								<th>货主代码</th><td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>


							</tr>
						</table>
					</fieldset>
					<div id='ezuiAccDialogBtn'> </div>
				</div>
				<table id='ezuiAccDataDialogId' ></table>
			</div>
		</div>
	</div>
	<div id='ezuiAccDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiAccDataDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
<%--验收作业点击弹窗--%>
	<div id='ezuiAcceptanceDialog' style='padding: 10px;'>
		<form id='ezuiAcceptanceForm' method='post'>
			<table>
				<input type='hidden' id='qty1' name="qty1" />
				<tr>
					<th>待验件数</th><td><input type='text' id='qcqtyExpected' name="qcqtyExpected" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

				</tr>
				<tr>
					<th>验收件数</th><td><input type='text' id='qcqty' name="qcqty" class='easyui-textbox' size='20' data-options="required:true" /></td>

				</tr>
				<tr>
					<th>待验数量</th><td><input type='text' id='qcqtyExpectedEach' name="qcqtyExpectedEach" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>

				</tr>
				<tr>
					<th>验收数量</th><td><input type='text' id='qcqtyEach' name="qcqtyEach" class='easyui-textbox' size='20' data-options="required:true"/></td>

				</tr>
				<tr>
					<th>验收说明</th>
					<td><input type='text' id='qcdescr' name="qcdescr" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',

							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '1',
																																        value: '未见异常，检查验收合格'},
																																       {label: '2',
																																         value: '近效期，包装外观未见异常'},
																																       {label: '3',
																																         value: '退货经检查验收，包装外观符合要求，可入库'},
																																       {label: '4',
																																         value: '包装损坏，产品经检验后合格，可入库'},
																																       {label: '5',
																																         value: '包装破损，不合格'},
																																       {label: '6',
																																         value: '无中文标签，不合格'},
																																       {label: '7',
																																         value: '无合格证明文件，不合格'},
																																       {label: '8',
																																         value: '产品经检验后判定不合格'},

																																         ]"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiAcceptanceDialogBtn'>
		<a onclick='commitAcceptance("HG");' class='easyui-linkbutton' href='javascript:void(0);'>合格</a>
		<a onclick='commitAcceptance("DCL");' class='easyui-linkbutton' href='javascript:void(0);'>待处理</a>
	</div>
<%--验收作业点击弹窗 处理单条验收--%>
	<div id='ezuiAcceptanceDialogS' style='padding: 10px;'>
		<form id='ezuiAcceptanceFormS' method='post'>
			<table>
				<input type='hidden' id='qty1' name="qty1" />
				<tr>
				   <th>产品代码</th><td><input type='text' id='sku' name="sku" class='easyui-textbox' size='20' data-options="readonly:true"/></td>
				   <th>存储条件</th><td><input type='text' id='lotatt11' name="lotatt11" class='easyui-textbox' size='20' data-options="readonly:true"/></td>
				</tr>
				<tr>
				   <th>批号</th><td><input type='text' id='lotatt04' name="lotatt04" class='easyui-textbox' size='20' data-options="readonly:true"/></td>
				   <th>序列号</th><td><input type='text' id='lotatt05' name="lotatt05" class='easyui-textbox' size='20' data-options="readonly:true"/></td>
				</tr>
				<tr>
				   <th>生产日期</th><td><input type='text' id='lotatt01' name="lotatt01" class='easyui-datebox' size='20' data-options="required:true,editable:false"/></td>
				   <th>有效期/失效期</th><td><input type='text' id='lotatt02' name="lotatt02" class='easyui-datebox' size='20' data-options="required:true,editable:false"/></td>
				</tr>
				<tr>
				   <th>产品注册证号/备案号</th><td><input type='text' id='lotatt06' name="lotatt06" class='easyui-combobox' size='20' data-options="editable:false"/></td>
				   <th>生产企业</th><td><input type='text' id='lotatt15' name="lotatt15" class='easyui-textbox' size='20' data-options=""/></td>
				</tr>
				<tr>
				   <th>生产许可证号/备案号</th><td><input type='text' id='reservedfield06' name="reservedfield06" class='easyui-textbox' size='20' data-options="readonly:true"/></td>
				   <th>验收说明</th>
				   <td><input type='text' id='qcdescr' name="qcdescr" class='easyui-combobox' size='20' data-options="required:true,panelHeight: 'auto',

							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '1',
																																        value: '未见异常，检查验收合格'},
																																       {label: '2',
																																         value: '近效期，包装外观未见异常'},
																																       {label: '3',
																																         value: '退货经检查验收，包装外观符合要求，可入库'},
																																       {label: '4',
																																         value: '包装损坏，产品经检验后合格，可入库'},
																																       {label: '5',
																																         value: '包装破损，不合格'},
																																       {label: '6',
																																         value: '无中文标签，不合格'},
																																       {label: '7',
																																         value: '无合格证明文件，不合格'},
																																       {label: '8',
																																         value: '产品经检验后判定不合格'},

																																         ]"/></td>
				</tr>
				<tr>
					<th>待验件数</th><td><input type='text' id='qcqtyExpected' name="qcqtyExpected" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>
					<th>验收件数</th><td><input type='text' id='qcqty' name="qcqty" class='easyui-textbox' size='20' data-options="required:true" /></td>
				</tr>
				<tr>
					<th>待验数量</th><td><input type='text' id='qcqtyExpectedEach' name="qcqtyExpectedEach" class='easyui-textbox' size='20' data-options="required:true,readonly:true"/></td>
					<th>验收数量</th><td><input type='text' id='qcqtyEach' name="qcqtyEach" class='easyui-textbox' size='20' data-options="required:true"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div id='ezuiAcceptanceDialogSBtn'>
		<a onclick='commitAcceptanceS("HG");' class='easyui-linkbutton' href='javascript:void(0);'>合格</a>
		<a onclick='commitAcceptanceS("DCL");' class='easyui-linkbutton' href='javascript:void(0);'>待处理</a>
	</div>



<!-- 货主选择弹框 -->
	<div id='ezuiCustDataDialog' style="width:700px;height:480px;padding:10px 20px">
		<div class='easyui-layout' data-options='fit:true,border:false'>
			<div data-options="region:'center'">
				<div id='ezuiCustToolbar' class='datagrid-toolbar' style="">
					<fieldset>
						<legend><spring:message code='common.button.query'/></legend>
						<table>
							<tr>
								<th>客户：</th>
								<td>
									<input type='text' id='customerid' name="customerid" class='easyui-textbox' size='12'
										   data-options='prompt:"请输入客户代码"'/></td>
								<th>类型：</th>
								<td>
									<input type='text' id='customerType' name="customerType" class='easyui-combobox'
										   size='8' data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															url:'<c:url value="/basCustomerController.do?getCustomerTypeCombobox"/>',
																															valueField: 'id',
																															textField: 'value'"/>
								</td>
								<th>激活：</th>
								<td>
									<input type='text' id='activeFlag' name="activeFlag" class='easyui-combobox' size='8'
										   data-options="disabled:true,
																															panelHeight:'auto',
																															editable:false,
																															valueField: 'id',
																															textField: 'value',
																															data: [
																																{id: 'Y', value: '是'},
																																{id: 'N', value: '否'}
																															]"/>
								</td>
								<td>
									<a onclick='ezuiCustDataDialogSearch();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
									<a onclick='selectCust();' id='ezuiBtn_edit' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
									<a onclick='ezuiCustToolbarClear();' class='easyui-linkbutton'
									   data-options='plain:true,iconCls:"icon-remove"'
									   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
								</td>
							</tr>
						</table>
					</fieldset>
					<div id='ezuiCustDialogBtn'></div>
				</div>
				<table id='ezuiCustDataDialogId'></table>
			</div>
		</div>
	</div>
	<div id='ezuiCustDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.commit'/></a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
				code='common.button.close'/></a>
	</div>
</body>
</html>
