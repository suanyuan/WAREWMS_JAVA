<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<div id='ezuiLocDataDialog'  style="width:900px;height:480px;padding:10px 20px"   >
<div class='easyui-layout' data-options='fit:true,border:false'>
<div data-options="region:'center'">
	<div id='ezuiLocToolbar' class='datagrid-toolbar'   style="">
				<fieldset>
					<legend><spring:message code='common.button.query'/></legend>
					<table>
						<tr>
							<th>库位：</th><td>
							<input type='text' id='locationid' name="locationid" class='easyui-textbox'  size='12' data-options='prompt:"请输入客户代码"'/></td>
							<th>库位类型：</th><td>
							<input type='text' id='locationcategory' name="locationcategory" class='easyui-combobox' size='12' data-options="panelHeight:'auto',
																																		    editable:false,
																																			url:'<c:url value="/basLocationController.do?getCatTypeCombobox"/>',
																																			valueField: 'id',
																																			textField: 'value'"/></td>
							<td>
								<a onclick='ezuiLocDataSearch();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
								<a onclick='selectLocation();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>选择</a>
								<a onclick='ezuiLocToolbarClear();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
							</td>
						</tr>
					</table>
				</fieldset>
<div id='ezuiLocDialogBtn'> </div>
</div>
	<table id='ezuiLocDataDialogId' ></table> 
</div>
</div>
</div>
<div id='ezuiLocDialogBtn'>
	<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
	<a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>