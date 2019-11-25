<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiCustomerDataDialog'  style="width:700px;height:480px;padding:10px 20px"   >
<div class='easyui-layout' data-options='fit:true,border:false'>
<div data-options="region:'center'">
	<table id='ezuiCustomerDataDialogId' ></table>
</div>
</div>·
</div>
<div id='ezuiReceDialogBtn'>
	<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
	<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>