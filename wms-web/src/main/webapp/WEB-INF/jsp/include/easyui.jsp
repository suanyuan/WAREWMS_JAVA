<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/jquery.cookie.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/jquery.easyui.min.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/datagrid-detailview.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/syUtils.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/swUtils.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/constant.js"/>"></script>
<c:set var="themeValue">
	<c:out value="${cookie.easyuiThemeName.value}" default="default"/>
</c:set>
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/js/jquery-easyui/themes/bootstrap/easyui.css"/>' id="easyuiTheme"/>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/js/jquery-easyui/themes/icon.css"/>"/>
<link rel="stylesheet" type="text/css" media="screen" href='<c:url value="/js/lightbox/css/lightbox.min.css"/>'>
<link rel="stylesheet" type="text/css" media="screen" href="<c:url value="/css/swCss.css"/>"/>
<script>
	function showUrl(url,fileName) {
	    if(!fileName){
	        fileName = "";
		}
		window.open(sy.bp()+"/fileUpload/"+url);
    }

    var firstStateFormatter = function(value,row,index) {
        if(value == "00"){
            return "新建";
        }else if(value == "10"){
            return "审核中";
        }else if(value == "90"){
            return "已报废";
        }else if(value == "60"){
            return "已停止";
        }else if(value == "40"){
            return "审核通过";
        }
    }

    var isUseRowStyler = function(index,row) {
		if(row.isUse == "0"){
            return 'color:red;';
		}
    }

    var showMsg = function(msg,title) {
        $.messager.show({
            msg : msg, title : '<spring:message code="common.message.prompt"/>'
        });
    }

    var getDateStr = function (date) {
        var month = date.getMonth() + 1;
        var strDate = date.getFullYear() + '-' + month + '-' + date.getDate();
        return strDate;
    }

    var judgeDate = function (dateStr) {
        return new Date().getTime()-new Date(dateStr).getTime();
    }

    var dateFormat = function(timestamp){
        var time = new Date(timestamp);    //先将时间戳转为Date对象，然后才能使用Date的方法
        var year = time.getFullYear(),
            month = time.getMonth() + 1 ,  //月份是从0开始的
            day = time.getDate(),
            hour = time.getHours(),
            minute = time.getMinutes(),
            second = time.getSeconds()
        return  year+'-'+ add0(month)+'-'+ add0(day)+' '+add0(hour)+':'+add0(minute)+':'+add0(second)
    }

    var dateFormat2 = function(timestamp){
        if(!timestamp || timestamp == ""){
            return "";
        }
        var time = new Date(timestamp);    //先将时间戳转为Date对象，然后才能使用Date的方法
        var year = time.getFullYear(),
            month = time.getMonth() + 1 ,  //月份是从0开始的
            day = time.getDate(),
            hour = time.getHours(),
            minute = time.getMinutes(),
            second = time.getSeconds()
        return  year+'-'+ add0(month)+'-'+add0(day);
    }

    var add0 = function(m){
        return m < 10 ? '0' + m: m
    }

	var checkObjIsEmpty = function(obj){
        var hasProp = false;
        for (var prop in obj){
            hasProp = true;
            break;
        }
        return hasProp;
    }

    var isUseFormatter = function(value,row,index) {
        if(value == "1"){
            return "有效";
        }else{
            return "失效";
        }
    }

    var yesOrNoFormatter = function(value,row,index) {
        if(value == "1"){
            return "是";
        }else{
            return "否";
        }
    }

    var entTypeFormatter = function(value,row,index) {
        switch (value) {
			case CODE_ENT_TYP.CODE_ENT_TYP_JY : return "经营";
            case CODE_ENT_TYP.CODE_ENT_TYP_GNSC : return "生产";
            case CODE_ENT_TYP.CODE_ENT_TYP_GWSC : return "国外生产";
            case CODE_ENT_TYP.CODE_ENT_TYP_KD : return "快递";
            case CODE_ENT_TYP.CODE_ENT_TYP_YL : return "医疗单位";
			case CODE_ENT_TYP.CODE_ENT_TYP_ZT : return "主体";
			case CODE_ENT_TYP.CODE_ENT_TYP_SCJY : return "生产经营";
        }
    }

    var outTimeFormatter = function(value,row,index) {
        if(value == 0 || value<0){
            return value+"天后过期";
        }else{
            return "已过期";
        }
    }

    var firstStateTypeFormatter = function(value,row,index) {
        switch (value) {
            case FIRSTSTATE.FIRSTSTATE_00 : return "新建";
            case FIRSTSTATE.FIRSTSTATE_10 : return "审核中";
            case FIRSTSTATE.FIRSTSTATE_40 : return "审核通过";
            case FIRSTSTATE.FIRSTSTATE_50 : return "未通过";
            case FIRSTSTATE.FIRSTSTATE_60 : return "已停止";
            case FIRSTSTATE.FIRSTSTATE_90 : return "已报废";
        }
    }

    var checkStateTypeFormatter = function(value,row,index) {
        switch (value) {
            case CHECKSTATE.CHECKSTATE_00 : return "新建";
            case CHECKSTATE.CHECKSTATE_20 : return "质量部审核";
            case CHECKSTATE.CHECKSTATE_30 : return "负责人审核";
            case CHECKSTATE.CHECKSTATE_40 : return "已通过";
            case CHECKSTATE.CHECKSTATE_50 : return "未通过";
        }
    };

    var applyTypeFormatter = function(value,row,index) {
        if(row.reviewTypeId.indexOf("CUS")!=-1){
            return "委托客户";
		}else if(row.reviewTypeId.indexOf("SUP")!=-1){
            return "供应商";
		}else if(row.reviewTypeId.indexOf("PRO")!=-1){
            return "产品";
        }else if(row.reviewTypeId.indexOf("REC")!=-1){
            return "收货单位";
        }
    }

    var sostatusFormatter = function (value,row,index) {
        if(row.sostatus.indexOf("CUS")!=-1){
            return "委托客户";
        }else if(row.sostatus.indexOf("SUP")!=-1){
            return "供应商";
        }else if(row.sostatus.indexOf("PRO")!=-1){
            return "产品";
        }else if(row.sostatus.indexOf("REC")!=-1){
            return "收货单位";
        }
    }

</script>
