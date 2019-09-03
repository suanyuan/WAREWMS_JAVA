<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp' />
    <c:import url='/WEB-INF/jsp/include/easyui.jsp' />

</head>
<body>
<script type='text/javascript'>
    $(function () {
        $('#tap').tabs({
            fit:true
        });
        // $('#divMain').css("visibility", "hidden");
        // alert($('#customerType').val());
        // //
        // select();
    })
    // function select(){
    //     if($('#customerType').val()=="收货单位"){
    //         $('#divMain').css("visibility", "visible");
    //     }else {
    //
    //     }
    // }
</script>
<%--<input type="hidden" id="customerType" value="${customerType}" >--%>
<div id="tap" class="easyui-tabs">
    <div title="基本信息" style="display:none;overflow:auto;padding:10px;" data-options="href:'gspEnterpriseInfoController.do?toInfo&id=${enterpriseId}'">
    </div>
    <div title="营业执照信息" data-options="href:'gspEnterpriseInfoController.do?toBusinessLicense&id=${enterpriseId}'" style="overflow:auto;padding:10px;display:none;">
    </div>
    <div title="生产许可证信息" data-options="href:'gspEnterpriseInfoController.do?toProdLicense&id=${enterpriseId}'" style="display:none;overflow:auto;padding:10px;">
    </div>
    <div title="第一类生产备案凭证" data-options="href:'gspEnterpriseInfoController.do?toFirstRecord&id=${enterpriseId}'" style="display:none;overflow:auto;padding:10px;">
    </div>
    <div title="经营许可证信息" data-options="href:'gspEnterpriseInfoController.do?toOperateLicense&id=${enterpriseId}'" style="display:none;overflow:auto;padding:10px;">
    </div>
    <div title="第二类经营备案凭证" data-options="href:'gspEnterpriseInfoController.do?toSecondRecord&id=${enterpriseId}'" style="display:none;overflow:auto;padding:10px;">
    </div>
    <div title="医疗机构执业许可证信息" data-options="href:'gspEnterpriseInfoController.do?toMedicalLicense&id=${enterpriseId}'" style="overflow:auto;padding:10px;display:none;">
    </div>

    <div title="收货地址信息" data-options="href:'/basCustomerController.do?toReceivingAddress&enterpriseId=${enterpriseId}'"   style="overflow:auto;padding:10px;display:none;">

    </div>


</div>
</body>



</html>