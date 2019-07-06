<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!DOCTYPE html>
<html>
<head>
    <c:import url='/WEB-INF/jsp/include/meta.jsp' />
    <c:import url='/WEB-INF/jsp/include/easyui.jsp' />
    <script type='text/javascript'>
        $(function () {
            $('#tap').tabs({
                fit:true
            });
        })
    </script>
</head>
<body>

<div id="tap" class="easyui-tabs">
    <div title="基本信息" style="padding:20px;" data-options="href:'/basCarrierLicenseController.do?toInfo'">
    </div>
    <div title="营业执照信息" data-options="href:'/gspEnterpriseInfoController.do?toBusinessLicense'" style="overflow:auto;padding:20px;display:none;">
        tab2
    </div>

</div>
</body>
</html>