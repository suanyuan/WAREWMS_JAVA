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
	// function showUrl1(url,fileName) {
	// 	if(!fileName){
	// 		fileName="";
	// 	}
	// 	var newwin=ajaxDownloadFile(sy.bp()+"/fileUpload/"+url);
	// 	// newwin.document.title=fileName;
    // }


    function showUrl(url,fileName) {
        if(!fileName){
            fileName="";
        }
        var newwin=window.open(sy.bp()+"/fileUpload/"+url);
        newwin.document.title=fileName;
    }

    function fileDownLoad(url,fileName) {
        if(!fileName){
            fileName="";
        }
        var newwin=ajaxDownloadFile(sy.bp()+"/commonController.do?fileDownLoad&url="+url);
        // $.ajax({
        //     url : sy.bp()+"/commonController.do?fileDownLoad&url="+url,
        //     // data : {"url":url},
        //     type : 'POST',
        //     dataType : 'JSON',
        //     success : function(result){
        //     }
        // });

        // newwin.document.title=fileName;
    }

    //客户档案显示文件链接
	function showUrlFile(value,row,index){
		  var supContractNo=row.supContractNo;
		  if (supContractNo) {
			  return "<a onclick=\"showUrl('"+value+"','"+supContractNo+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
		  }
		  return '';
	}

    //注册证/备案附件  显示文件链接
    function showProductRegisteUrlFile(value,row,index){
        var attachmentUrl=row.attachmentUrl;
        if(attachmentUrl!=null && attachmentUrl!=""){
            return "<a onclick=\"showUrl('"+value+"','"+attachmentUrl+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
        }else{
            return "<a>无</a>";
		}
    }
    //质量合格证照片  显示文件链接
    function showcertificateContextFile(value,row,index){
        var certificateContext=row.certificateContext;
        if(certificateContext!=null && certificateContext!=""){
            return "<a onclick=\"showUrl('"+value+"','"+certificateContext+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
        }else{
            return "<a>无</a>";
        }
    }
    //历史文档  下载
    function showHistoryContextFile(value,row,index){
        var codenameE=row.codenameE;
        if(codenameE!=null && codenameE!=""){
            return "<a onclick=\"fileDownLoad('"+codenameE+"','"+codenameE+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>下载</a>";
        }else{
            return "<a>无</a>";
        }
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
	    if(dateStr=="" || dateStr==null){
            return false;
		}
        var mydate = new Date();
        var str = "" + mydate.getFullYear() + "-";
        str += (mydate.getMonth()+1) + "-";
        str += mydate.getDate();
        var tady=new Date(str.replace("-", "/").replace("-", "/"));
        var t2 = new Date(dateStr.replace("-", "/").replace("-", "/"));
		if(t2<=tady){
		    return false;
		}else{
		    return true;
		}
		/*if(new Date()>=new Date(dateStr)){
            return false;
		}else{
            return true;
		}*/
    }

    var judgeDate2 = function (dateStrBeg,dateStrEnd) {
        if(new Date(dateStrBeg)>=new Date(dateStrEnd)){
            return false;
        }else{
            return true;
        }
    }

    var dateFormat = function(timestamp){
        if(!timestamp || timestamp == "" ||timestamp==null ){
            return "";
        }
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
        if(!timestamp || timestamp == "" ||timestamp==null ){
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

    var day = function(value,row,index) {

	    if(value!=null && value!=""){
            return value+"天";
		}

    }
    var month = function(value,row,index) {

        if(value!=null && value!=""){
            return value+"月";
        }

    }
    var yesOrNoFormatter = function(value,row,index) {
        if(value == "1"){
            return "是";
        }else if(value == "0"){
            return "否";
        }else{
            return "";
        }
    }

	var unitFormatter = function(value,row,index) {
		value+="";
		switch (value) {
			case "BA" : return "包";
			case "CS" : return "箱";
			case "EA" : return "件";
			case "GE" : return "个";
			case "GN" : return "根";
			case "HE" : return "盒";
			case "HE" : return "内箱";
			case "HE" : return "其它";
			case "PL" : return "板";
		}
	}

    var QualifiedOrFailed = function(value,row,index) {
		value+="";
		switch (value) {
			case "1" : return "合格";
			case "0" : return "不合格";
			case "2" : return "未检查";
			case "" : return "";
		}
    }
   var AccordOrNoAccord = function(value,row,index) {
		value+="";
		switch (value) {
			case "1" : return "符合";
			case "0" : return "不符合";
			case "2" : return "未检查";
			case "" : return "";
		}
    }

    var entTypeFormatter = function(value,row,index) {
        switch (value) {
			case CODE_ENT_TYP.CODE_ENT_TYP_JY : return "经营";
            case CODE_ENT_TYP.CODE_ENT_TYP_GNSC : return "生产";
            case CODE_ENT_TYP.CODE_ENT_TYP_GWSC : return "国外生产";
            case CODE_ENT_TYP.CODE_ENT_TYP_KD : return "快递";
            case CODE_ENT_TYP.CODE_ENT_TYP_YL : return "医疗机构";
			case CODE_ENT_TYP.CODE_ENT_TYP_ZT : return "主体";
			case CODE_ENT_TYP.CODE_ENT_TYP_SCJY : return "生产经营";
            case CODE_ENT_TYP.CODE_ENT_TYP_GW : return "国外企业";
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
		console.log(value);
        switch (value) {
			case "00" : return "创建订单";
			case "30" : return "部分分配";
            case "40" : return "分配完成";
			case "50" : return "部分拣货";
            case "60" : return "拣货完成";
            case "62" : return "部分装箱";
			case "63" : return "完全装箱";
			case "70" : return "部分发运";
            case "80" : return "完全发运";
            case "90" : return "订单取消";
			case "98" : return "等待释放";
			case "99" : return "订单完成";
        }
    }
//上架状态
	var pastatusFormatter = function (value,row,index) {
		switch (value) {
			case "00" : return "订单创建";
			case "30" : return "部分上架";
			case "40" : return "完全上架";
			case "90" : return "上架取消";
			case "99" : return "上架关闭";
		}
	}
//质量状态
	var ZL_TYPstatusFormatter = function (value,row,index) {
		switch (value) {
			case "BHG" : return "不合格";
			case "DCL" : return "待处理";
			case "DJ" : return "待检";
			case "HG" : return "合格";
			case "HG>BHG":return "合格>不合格";
			case "BHG>HG":return "不合格>合格";
			case "DCL>HG":return "待处理>合格";
			case "DCL>BHG":return "待处理>不合格";
		}
	}
//冻结状态
	var holdStatusFormatter = function (value,row,index) {
		switch (value+"") {
			case "99" : return "库存冻结";
			case "0" : return "库存解冻";
			default: return "库存解冻";
		}
	}
//样品属性
	var YP_TYPstatusFormatter = function (value,row,index) {
		switch (value) {
			case "TS" : return "投诉";
			case "YP" : return "样品";
			case "ZC" : return "正常";
		}
	}
//验收状态
	var AcceptancestatusFormatter = function (value,row,index) {
		switch (value) {
			case "00" : return "未验收";
			case "30" : return "部分验收";
			case "40" : return "已验收";
		}
	}
//养护状态
	var MT_STSstatusFormatter = function (value,row,index) {
		switch (value) {
			case "00" : return "任务创建";
			case "30" : return "部分养护";
			case "40" : return "完全养护";
			case "90" : return "养护取消";
			case "99" : return "养护关闭";
		}
	}
//养护类型
	var MT_TYPstatusFormatter = function (value,row,index) {
		switch (value) {
			case "PC" : return "指定养护";
			case "SC" : return "计划养护";
		}
	}
//双证匹配类型
	var Asn_DoublecstatusFormatter = function (value,row,index) {
		value+="";
    	switch (value) {
			case "1" : return "已匹配";
			case "" : return "";
		}
	}
//盘点状态
	var Cou_RequeststatusFormatter = function (value,row,index) {
		value+="";
    	switch (value) {
			case "00" : return "任务创建";
			case "30" : return "部分盘点";
			case "40" : return "完全盘点";
			case "90" : return "盘点取消";
			case "99" : return "盘点关闭";
		}
	}
//质量状态更新
	var qcustatusFormatter = function (value,row,index) {
		value+="";
    	switch (value) {
			case "00" : return "任务创建";
			case "30" : return "部分完成";
			case "40" : return "任务完成";
			case "90" : return "任务取消";
			case "99" : return "任务关闭";
		}
	}
//发运订单-回传标识
    var edisendflag = function(value,row,index) {
		value+="";
		switch (value) {
			case "1" : return "已回传";
			case "0" : return "未回传";
			default: return "";
		}
	}
</script>
