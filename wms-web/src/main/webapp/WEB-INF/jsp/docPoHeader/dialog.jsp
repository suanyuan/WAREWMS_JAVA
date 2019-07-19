<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%--点击主页按钮弹出二级dialog--%>
<div id='ezuiDialog'  class='easyui-dialog' style='padding: 10px;'  >
    <form id='ezuiForm' method='post' >
        <input type='hidden' id='docPoHeaderId' name='docPoHeaderId'/>
        <fieldset>
            <legend>采购单头</legend>
        <table>
            <tr>
                <th>采购单号</th>
                <td ><input type='text'  id='pono' name='pono' class='easyui-textbox' size='16' data-options='required:false'/></td>

                <th>订单类型</th>
                <td ><input type='text' id='potype'  name='potype' class='easyui-combobox' size='16' data-options="panelHeight:
                                                                                                                                 'auto',
                                                                                                                                  editable: false,
																																	url:'<c:url value="/docPoHeaderController.do?getPotypeCombobox"/>',
																																	valueField: 'id',
																																    textField: 'value'"/></td>

                <th>订单状态</th>
                <td ><input type='text'  id='postatus' name='postatus' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
                                                                                                                                  editable: false,
																																	url:'<c:url value="/docPoHeaderController.do?getPostatusCombobox"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
            </tr>
            <tr>


                <th>预计到货时间</th>
                <td ><input type='text' id='expectedarrivetime1' name='expectedarrivetime1' class='easyui-datetimebox' size='16' data-options=''/></td>
                <th>供应商</th>
                <td ><input type='text' id='supplierName'  name='supplierName' class='easyui-textbox' size='16' data-options='required:false'/></td>
                <th>供应商联系人</th>
                <td ><input type='text'  id='supplierContact'  name='supplierContact' class='easyui-textbox' size='16' data-options='required:false'/></td>

            </tr>

            <tr>
                <th>货主代码</th>
                <td ><input type='text'  id='customerid' name='customerid' class='easyui-textbox' size='16' data-options='required:false'/></td>
                 <th>创建时间</th>
                <td ><input type='text' id='pocreationtime' name='pocreationtime' class='easyui-datetimebox' size='16'  data-options='required:false'/></td>
                <th>仓库</th>
                <td ><input type='text'  id='warehouseid' name='warehouseid' class='easyui-combobox' size='16' data-options='required:false'/></td>
            </tr>
            <tr>
                 <th>备注</th>
                <td colspan="3"><input type='text'  id='notes' name='notes' class='easyui-textbox' size='16' data-options="required:false,multiline:true,width:'300',height:'50'"/></td>
                <td colspan='2'>
                    <a onclick='renew();' id='ezuiBtn_renew' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>新增</a>
                    <a onclick='commit();' id='ezuiBtn_recommit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
                </td>
            </tr>

        </table>
        </fieldset>
    </form>
<%--二级datafrid--%>
    <table id='ezuiDetailsDatagrid'></table>
    <form>
        <div >
            <a onclick='detailsAdd();' id='ezuiDetailsBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message code='common.button.skuAdd'/></a>
            <a onclick='detailsEdit();' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.skuEdit'/></a>
            <a onclick='detailsDel();' id='ezuiDetailsBtn_del' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.skuDelete'/></a>
            <%--<a onclick='detailsReceive();' id='ezuiDetailsBtn_receive' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>确认收货</a>--%>
            <a onclick='clearDatagridSelected("#ezuiDetailsDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
        </div>
    </form>
</div>
<div id='ezuiDialogBtn'>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>