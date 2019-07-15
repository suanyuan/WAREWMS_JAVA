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
    <div title="基本信息" style="display:none;overflow:auto;padding:10px;" data-options="href:'gspEnterpriseInfoController.do?toInfo&id=${enterpriseId}'">
    </div>
    <div title="营业执照信息" data-options="href:'gspEnterpriseInfoController.do?toBusinessLicense&id=${enterpriseId}'" style="overflow:auto;padding:10px;display:none;">
        tab2
    </div>
    <div title="经营/生产许可证信息" data-options="href:'gspEnterpriseInfoController.do?toOperateLicense&id=${enterpriseId}'" style="display:none;overflow:auto;padding:10px;">
        tab3
    </div>
    <div title="备案凭证" data-options="href:'gspEnterpriseInfoController.do?toSecondRecord&id=${enterpriseId}'" style="display:none;overflow:auto;padding:10px;">
        tab3
    </div>
</div>
</body>
</html>