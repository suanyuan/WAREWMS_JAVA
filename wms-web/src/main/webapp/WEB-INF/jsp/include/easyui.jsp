<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/jquery.min.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/jquery.cookie.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/jquery.easyui.min.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/datagrid-detailview.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery-easyui/locale/easyui-lang-zh_CN.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/syUtils.js"/>"></script>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/swUtils.js"/>"></script>
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
		//修改sy.bp()+"/commonController.do?fileDownLoad&url="+url+"&fileName="+fileName为url
		window.open(url);
    }
    
    var isUseFormatter = function(value,row,index) {
	    console.log(value);
		if(value == "1"){
		    return "有效";
		}else{
            return "失效";
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
        return  year+'-'+this.add0(month)+'-'+ this.add0(day)+' '+this.add0(hour)+':'+this.add0(minute)+':'+this.add0(second)
    }

    var add0 = function(m){
        return m < 10 ? '0' + m: m
    }
</script>
