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
var dialogUrl = "/gspEnterpriseInfoController.do?toDetail";
$(function() {
	ezuiMenu = $('#ezuiMenu').menu();
	ezuiForm = $('#ezuiForm').form();
	ezuiDatagrid = $('#ezuiDatagrid').datagrid({
		url : '<c:url value="/gspEnterpriseInfoController.do?showDatagrid"/>',
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
		idField : 'enterpriseId',
        rowStyler: isUseRowStyler,
		columns : [[
			{field: 'enterpriseId',		title: '主键',	width: 61 ,hidden:true},
            {field: 'isUse',		title: '是否有效',	width: 61,formatter:isUseFormatter },
			{field: 'enterpriseNo',		title: '企业代码',	width: 61 },
			{field: 'shorthandName',		title: '简称',	width: 61 },
			{field: 'enterpriseName',		title: '企业名称',	width: 61 },
			{field: 'enterpriseType',		title: '企业类型',	width: 61 ,formatter: entTypeFormatter},
            {field: 'createId',		title: '创建人',	width: 61 },
            {field: 'createDate',		title: '创建时间',	width: 61 },
            {field: 'editId',		title: '修改人',	width: 61 },
            {field: 'editDate',		title: '修改时间',	width: 61 },

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
			ajaxBtn($('#menuId').val(), '<c:url value="/gspEnterpriseInfoController.do?getBtn"/>', ezuiMenu);
			//$(this).datagrid('unselectAll');
            $('#ezuiDatagrid').datagrid('clearSelections');
		}
	});
	ezuiDialog = $('#ezuiDialog').dialog({
		modal : true,
        left:0,
        top:0,
		title : '<spring:message code="common.dialog.title"/>',
		buttons : '#ezuiDialogBtn',
		href:dialogUrl,
		width:1200,
		height:530,
        closable:false,
        cache: false,
		onClose : function() {
			ezuiFormClear(ezuiForm);
		}
	}).dialog('close');

	$("#enterpriseTypeQuery").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getEntType',
        valueField:'id',
        textField:'value'
    });
//时候有效
    $("#isUse").combobox({
        panelHeight: 'auto',
        url:sy.bp()+'/commonController.do?getIsUseCombobox',
        valueField:'id',
        textField:'value'
    });

});

var add = function(){
	processType = 'add';
	$('#gspEnterpriseInfoId').val(0);
	ezuiDialog.dialog('open').dialog('refresh', dialogUrl);
	localStorage.clear();//清楚本地储存器的信息
};

var edit = function(row){
	localStorage.clear();
	processType = 'edit';
	if(!row){
        var row = ezuiDatagrid.datagrid('getSelected');
    }
	if(row){
		ezuiDialog.dialog('refresh', dialogUrl+"&id="+row.enterpriseId).dialog('open');
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
					url : 'gspEnterpriseInfoController.do?delete',
					data : {id : row.enterpriseId},
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


var commit = function(){
    var gspEnterpriceFrom = new Object();   //all
    var infoObj = new Object();				//基本信息
    var businessObj = new Object();
    var medicalObj =new Object();
    var operateobj = new Object();
    var secondRecord = new Object();
    var firstRecord = new Object();
    var prodObj = new Object();
    var url = '';
    var isVal = true;
    var isVal1 =true;
    var operateLicenseFlag=0 ;
    var secondRecordFlag=0;
    var prodLicenseFlag=0;
    var firstRecordFlag=0;
    var medicalRecordFlag=0;
    var ffff=false;
    url = sy.bp()+"/gspEnterpriseInfoController.do?add";
    //}
    var enterpriceId = "";
    var row = ezuiDatagrid.datagrid('getSelected');
    if(processType != "add"){
        if(row){
            enterpriceId = row.enterpriseId;
        }
	}

    if(enterpriceId!=null||enterpriceId!=""){
        //查询证照,手动点击初始化
        $.ajax({
            url : sy.bp()+"/gspEnterpriseInfoController.do?selectSixLicense",
            data : {"enterpriseId":enterpriceId},
            type : 'POST', dataType : 'JSON',
            async  :false,
            success : function(result) {
                // $.messager.progress('close');
                if(result.obj!=null){
                    // alert(result.obj.operateLicenseFlag+"="+result.obj.secondRecordFlag+"="
                    //     +result.obj.prodLicenseFlag+"="+result.obj.firstRecordFlag+"="+result.obj.medicalRecordFlag);
                    operateLicenseFlag = result.obj.operateLicenseFlag;
                    secondRecordFlag = result.obj.secondRecordFlag;
                    prodLicenseFlag = result.obj.prodLicenseFlag;
                    firstRecordFlag = result.obj.firstRecordFlag;
                    medicalRecordFlag = result.obj.medicalRecordFlag;
                }

            }
        })
    }






    /*if (processType == 'edit') {
        url = sy.bp()+"/gspEnterpriseInfoController.do?edit";
    }else{*/

    //判断基本信息
    isVal = checkFormData("ezuiFormInfo",infoObj);
	if(isVal == false){
        showMsg("企业基础信息未填全！");
        return;
	}

    console.log(infoObj.enterpriseType+"============="+CODE_ENT_TYP.CODE_ENT_TYP_GW);
	//判断营业执照信息
	isVal = checkFormData("ezuiFormBusiness",businessObj);
	if(infoObj.enterpriseType  != CODE_ENT_TYP.CODE_ENT_TYP_GW&& infoObj.enterpriseType  != CODE_ENT_TYP.CODE_ENT_TYP_YL && !checkObjIsEmpty(businessObj)){
        showMsg("必须填写营业执照信息！");
        return;
	}
    if(infoObj.enterpriseType != CODE_ENT_TYP.CODE_ENT_TYP_GW && isVal == false){
        showMsg("营业执照信息填写不完全！");
        return;
    }
    if(infoObj.enterpriseType != CODE_ENT_TYP.CODE_ENT_TYP_GW && infoObj.enterpriseType != CODE_ENT_TYP.CODE_ENT_TYP_YL && isVal == true){
        //判断证照时间合法性
        //营业执照
        if($("#ezuiFormBusiness #issueDate").datebox("getValue")!=""){
            if(judgeDate($("#ezuiFormBusiness #issueDate").datebox("getValue"))){
                checkResult = false;
                showMsg("营业执照发证日期不能超过当前时间");
                return;
            }
        }
        if(judgeDate($("#ezuiFormBusiness #businessStartDate").datebox("getValue"))){
            checkResult = false;
            showMsg("营业执照营业起始时间不能超过当前时间");
            return;
        }
        if(judgeDate($("#ezuiFormBusiness #establishmentDate").datebox("getValue"))){
            checkResult = false;
            showMsg("营业执照中成立日期不能超过当前时间");
            return;
        }
        if(!($("#ezuiFormBusiness #isLong").is(':checked')) && $("#ezuiFormBusiness #businessStartDate").datebox("getValue")>$("#ezuiFormBusiness #businessEndDate").datebox("getValue"))
        {
            $("#businessStartDate").focus();
            showMsg("营业执照营业期限起始时间不能大于结束时间！");
            return;
        }
	}

    //第二备案凭证
    isVal1 = checkFormData("ezuiFormRecord",secondRecord);



	//判断经营许可证
    isVal = checkFormData("ezuiFormOperate",operateobj);

    // isVal = checkFormData("ezuiFormMedical",medicalObj);
    //判断主体经营许可证
    if(infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_ZT ){
        if(!checkObjIsEmpty(operateobj) || isVal == false){
            showMsg("主体企业需要填写经营许可证信息！");
            return;
        }
        if(checkObjIsEmpty(operateobj) && isVal == false){
            showMsg("经营许可证填写不完全！");
            return;
        }
        if(judgeDate($("#ezuiFormOperate #approveDate").datebox("getValue"))){
            checkResult = false;
            showMsg("经营许可证发证日期不能超过当前时间");
            return;
        }
        if(!judgeDate($("#ezuiFormOperate #licenseExpiryDate").datebox("getValue"))){
            checkResult = false;
            showMsg("经营许可证有效期不能小于当前时间");
            return;
        }
    }else{

    }

    //企业判断
    if(infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_JY || infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_SCJY){
        if((!checkObjIsEmpty(operateobj) ||  isVal == false) &&( isVal1==false   || !checkObjIsEmpty(secondRecord))){
            showMsg("经营或者生产经营类型企业至少需要填写经营许可证或者第二类经营备案凭证其中一个！");
            return;
        }
        if(checkObjIsEmpty(operateobj) && isVal == false){
            showMsg("经营许可证填写不完全！");
            return;
        }
        if(checkObjIsEmpty(secondRecord) && isVal1 == false){
            showMsg("第二类经营备案填写不完全！");
            return;
        }

        if(!checkObjIsEmpty(secondRecord) && secondRecordFlag=="1"){
            showMsg("请检查第二类经营备案！");
            return;
        }
        if(!checkObjIsEmpty(operateobj) && operateLicenseFlag=="1"){
            showMsg("请检查经营许可证！");
            return;
        }

        if(isVal1==false   || !checkObjIsEmpty(secondRecord)){
        }else{
            if(judgeDate($("#ezuiFormRecord #approveDate").datebox("getValue"))){
                checkResult = false;
                showMsg("第二备案凭证备案日期不能超过当前时间");
                return;
            }
        }

        if(!checkObjIsEmpty(operateobj) ||  isVal == false){
        }else{
            if(judgeDate($("#ezuiFormOperate #approveDate").datebox("getValue"))){
                checkResult = false;
                showMsg("经营许可证发证日期不能超过当前时间");
                return;
            }
            if(!judgeDate($("#ezuiFormOperate #licenseExpiryDate").datebox("getValue"))){
                checkResult = false;
                showMsg("经营许可证有效期不能小于当前时间");
                return;
            }
        }

    }else{
        // if(checkObjIsEmpty(operateobj) && isVal == false){
        //     showMsg("经营许可证填写不完全！");
        //     return;
        // }
        // if(checkObjIsEmpty(secondRecord) && isVal == false){
        //     showMsg("第二类经营备案填写不完全！");
        //     return;
        // }
    }


    //第一备案凭证

    isVal1=true;
    isVal1 = checkFormData("ezuiFormFirstRecord",firstRecord);
    // if(checkObjIsEmpty(firstRecord) && isVal == false){
    //     showMsg("第一备案凭证填写不完全！");
    //     return;
    // }
    //判断生产许可证
    isVal = checkFormData("ezuiFormProd",prodObj);
    //生产企业判断
    if(infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_GNSC || infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_SCJY){
        if((!checkObjIsEmpty(prodObj) ||  isVal == false) &&( isVal1==false   || !checkObjIsEmpty(firstRecord))){
            showMsg("生产或者生产经营类型企业至少需要填写经营生产许可证或者第一类生产备案凭证其中一个！");
            return;
        }
        if(checkObjIsEmpty(prodObj) && isVal == false){
            showMsg("生产许可证填写不完全！");
            return;
        }
        if(checkObjIsEmpty(firstRecord) && isVal1 == false){
            showMsg("第一类生产备案凭证证填写不完全！");
            return;
        }

        if(!checkObjIsEmpty(firstRecord) && firstRecordFlag=="1"){
            showMsg("请检查第一类生产备案凭证！");
            return;
        }
        if(!checkObjIsEmpty(prodObj) && prodLicenseFlag=="1"){
            showMsg("请检查生产许可证！");
            return;
        }



        if(isVal1==false   || !checkObjIsEmpty(firstRecord)){
        }else{
            if(judgeDate($("#ezuiFormFirstRecord #approveDate").datebox("getValue"))){
                console.log($("#ezuiFormFirstRecord #approveDate").datebox("getValue"));
                checkResult = false;
                showMsg("第一备案凭证备案日期不能超过当前时间");
                return;
            }
        }
        if(!checkObjIsEmpty(prodObj) ||  isVal == false){
        }else{
            if(judgeDate($("#ezuiFormProd #approveDate").datebox("getValue"))){
                checkResult = false;
                showMsg("生产许可证发证日期不能超过当前时间");
                return;
            }
            if(!judgeDate($("#ezuiFormProd #licenseExpiryDate").datebox("getValue"))){
                checkResult = false;
                showMsg("生产许可证有效期不能小于当前时间");
                return;
            }
        }

    }else{

    }

    //判断医疗机构执业许可证
    isVal = checkFormData("ezuiFormMedical",medicalObj);
        if(!checkObjIsEmpty(medicalObj) && medicalRecordFlag=="1"){
        showMsg("请检查医疗机构执业许可证！");
        return;
    }
    //企业判断
    if(infoObj.enterpriseType == CODE_ENT_TYP.CODE_ENT_TYP_YL ){
        if(!checkObjIsEmpty(medicalObj) || isVal == false){
            showMsg("医疗类型企业需要填写医疗机构执业许可证！");
            return;
        }
        if(judgeDate($("#ezuiFormMedical #approveDate").datebox("getValue"))){
            checkResult = false;
            showMsg("医疗机构发证日期不能超过当前时间");
            return;
        }
    }else{
        if(checkObjIsEmpty(medicalObj) && isVal == false){
            showMsg("医疗机构执业许可证填写不完全！");
            return;
        }
    }
	// debugger

    console.log(businessObj.opType);
    console.log(operateobj.opType);
    console.log(prodObj.opType);
    console.log(medicalObj.opType);
    console.log(firstRecord.opType);
    console.log(secondRecord.opType);

    $.ajax({
        url : sy.bp()+"/gspEnterpriseInfoController.do?verify",
        data : {"enterpriseId":enterpriceId},
        type : 'POST', dataType : 'JSON',
        async  :false,
        success : function(result){
            // $.messager.progress('close');
            var msg='';
            try{
                if(result.success){
                    // alert(result+"==="+result.obj);
                    // alert(businessObj.opType+operateobj.opType+prodObj.opType+prodObj.opType+
					// 	medicalObj.opType+firstRecord.opType+secondRecord.opType);
                    // msg = result.msg;
                    // ezuiDatagrid.datagrid('reload');
                    // ezuiDialog.dialog('close');

                    if(result.obj=="1" && (businessObj.opType!="update"&&
						 						 operateobj.opType!="update"&&
													prodObj.opType!="update"&&
												 medicalObj.opType!="update"&&
												firstRecord.opType!="update"&&secondRecord.opType!="update")  ){
                        $.messager.show({
                            msg : "企业已有相关首营申请,请点击开始换证进行换证!", title : '<spring:message code="common.message.prompt"/>'
                        });
						ffff=true;

                    }

                }else{
                    msg = '<font color="red">' + '提交错误' + '</font>';
                    return ;
                }
            }catch (e) {
                msg = '<spring:message code="common.message.data.process.failed"/><br/>'+ msg;
            }
        }
    });
	if(ffff){
		return;
	}

    gspEnterpriceFrom["gspEnterpriseInfoForm"] = infoObj;


    gspEnterpriceFrom["gspBusinessLicenseForm"] = businessObj;
    gspEnterpriceFrom["gspOperateLicenseForm"] = operateobj;
    gspEnterpriceFrom["gspProdLicenseForm"] = prodObj;

    gspEnterpriceFrom["gspMedicalRecordForm"] = medicalObj;
    gspEnterpriceFrom["gspFirstRecordForm"] = firstRecord;

    gspEnterpriceFrom["gspSecondRecordForm"] = secondRecord;
	console.log(gspEnterpriceFrom);
	//提交
    // alert(111);

    submitFormData(enterpriceId,gspEnterpriceFrom,url);
};

function submitFormData(enterpriceId,gspEnterpriceFrom,url) {
    $.messager.progress({
        text : '<spring:message code="common.message.data.processing"/>', interval : 100
    });
    $.ajax({
        url : url,
        data : {"enterpriseId":enterpriceId,"gspEnterpriceFrom":JSON.stringify(gspEnterpriceFrom)},type : 'POST', dataType : 'JSON',async  :true,
		success : function(result){
            $.messager.progress('close');
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

var checkFormData = function (formId,obj) {
    var checkResult = true;
    $("#"+formId+" input[type!=hidden]").each(function (index) {
        if($(this).attr("class")){
            if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                if(!$(this).textbox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).textbox("getValue");
            }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                if(!$(this).datebox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).datebox("getValue");
            }else if($(this).attr("class").indexOf('easyui-combobox')!=-1){
                if(!$(this).combobox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).combobox("getValue");
            }else if($(this).attr("class").indexOf('easyui-numberbox')!=-1){
                if(!$(this).combobox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
                obj[""+$(this).attr("id")+""] = $(this).numberbox("getValue");
            } else if($(this).attr("atth")){
                if(!$(this).filebox("isValid")){
                    checkResult = false;
                    $(this).focus();
                    return;
                }
            }
        }
    })

	if(checkObjIsEmpty(obj) == true && checkResult == true){

	    //长期证照
        if(formId == "ezuiFormBusiness"){
            var isLong = $("#ezuiFormBusiness input[id='isLong']");
            if($(isLong).is(':checked')){
                obj["isLong"] = "1";
            }else{
                obj["isLong"] = "0";
            }
        }

        //主键
        $("#"+formId+" input[type=hidden][data=1]").each(function () {
            if($(this).val() != ""){
                obj[""+$(this).attr("id")+""] = $(this).val();
            }

        })

        //上传附件
        $("#"+formId+" input[type=hidden][data=2]").each(function () {
            if($(this).val() != ""){
                obj[""+$(this).attr("id")+""] = $(this).val();
			}
        })


        //加载经营范围
        var scopArr = $("#"+formId+" input[id='choseScope']");
        if(scopArr){
            //obj["scopArr"] = $(scopArr).val() || "";
            if($(scopArr).val()!="" && $(scopArr).val()!=undefined){
                var str = $(scopArr).val();
                obj["scopArr"] = str;
            }else{
                obj["scopArr"] = "";
            }

        }
        //操作类型是否是换证
        var opType = $("#"+formId+" input[id='opType']");
        if(opType){
            obj["opType"] = $(opType).val() || "";
        }

	}
	return checkResult;
}

var doSearch = function(){
	ezuiDatagrid.datagrid('load', {
		enterpriseNo : $('#enterpriseNo').textbox("getValue"),
		shorthandName : $('#shorthandName').textbox("getValue"),
		enterpriseName : $('#enterpriseName').textbox("getValue"),
		enterpriseType : $('#enterpriseTypeQuery').combobox("getValue"),
		createDateBegin : $('#createDateBegin').datebox("getValue"),
        createDateEnd : $('#createDateEnd').datebox("getValue"),
		isUse : $('#isUse').combobox("getValue")
	});
};
function test(){
    $.ajax({
        url : sy.bp()+"/gspEnterpriseInfoController.do?enterpriseOutTime",
        // data : {},
		type : 'POST',
		dataType : 'JSON',
		async  :true,
        success : function(result){

        }
    });
}


/* 导出start */
var doExport = function(){
    if(navigator.cookieEnabled){
        $('#ezuiBtn_export').linkbutton('disable');
        var token = new Date().getTime();
        var param = new HashMap();
        param.put("token", token);
        param.put("enterpriseNo", $('#enterpriseNo').val());
        param.put("shorthandName",$('#shorthandName').val());
        param.put("enterpriseName",$('#enterpriseName').val());
        param.put("enterpriseType",$('#enterpriseTypeQuery').combobox("getValue"));
        param.put("createDateBegin",$('#createDateBegin').datebox("getValue"));
        param.put("createDateEnd",$('#createDateEnd').datebox("getValue"));
        param.put("isUse",$('#isUse').combobox("getValue"));


        //--导出Excel
        var formId = ajaxDownloadFile(sy.bp()+"/gspEnterpriseInfoController.do?exportDataToExcel", param);
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
					<table>
						<tr>
							<th>企业代码</th><td><input type='text' id='enterpriseNo' class='easyui-textbox' size='16' data-options=''/></td>
							<th>简称</th><td><input type='text' id='shorthandName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业名称</th><td><input type='text' id='enterpriseName' class='easyui-textbox' size='16' data-options=''/></td>
							<th>企业类型</th><td><input type='text' id='enterpriseTypeQuery' class='easyui-combobox' size='16' data-options=''/></td>
						</tr>
						<tr>
							<th>创建时间</th><td><input type='text' id='createDateBegin' class='easyui-datebox' size='16' data-options=''/></td>
							<th>至</th><td><input type='text' id='createDateEnd' class='easyui-datebox' size='16' data-options=''/></td>
							<th>是否有效</th>
							<td>
								<select id="isUse" style="width:145px;" class="easyui-combobox">
								</select>
							</td>
							<td colspan="2">
								<a onclick='doSearch();' id='ezuiBtn_select' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='ezuiToolbarClear("#toolbar");' id='ezuiBtn_clear' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>

								<a onclick='doExport();' id='ezuiBtn_export' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>导出</a>

							<%--<a onclick='test();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'>测试</a>--%>
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
			<table id='ezuiDatagrid'></table>
		</div>
	</div>
	<div id='ezuiDialog' style='padding: 10px;'>

	</div>
	<div id='ezuiDialogBtn'>
		<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'>提交企业信息</a>
		<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
	</div>
	<div id='ezuiMenu' class='easyui-menu' style='width:120px;display: none;'>
		<div onclick='add();' id='menu_add' data-options='plain:true,iconCls:"icon-add"'><spring:message code='common.button.add'/></div>
		<div onclick='del();' id='menu_del' data-options='plain:true,iconCls:"icon-remove"'><spring:message code='common.button.delete'/></div>
		<div onclick='edit();' id='menu_edit' data-options='plain:true,iconCls:"icon-edit"'><spring:message code='common.button.edit'/></div>
	</div>
</body>
</html>
