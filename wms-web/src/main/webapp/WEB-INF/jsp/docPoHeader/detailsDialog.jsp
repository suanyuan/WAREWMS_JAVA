<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<div id='ezuiDetailsDialog' class='easyui-dialog' title="详细信息" style='padding: 10px;'>
    <form id='ezuiDetailsForm' method='post'>
        <input type='hidden' id='docPoDetailsId' name='docPoDetailsId'/>
        <table>
            <tr>
                <th>采购单号</th>
                <td><input type='text' name='pono' id='pono' class='easyui-textbox' size='16'
                           data-options='editable:true'/></td>
                <th>订单行号</th>
                <td><input type='text' name='polineno' id='polineno' class='easyui-textbox' size='16'
                           data-options='editable:false'/></td>
                <th>行状态</th>
                <td><input type='text' name='polinestatus' id='polinestatus' class='easyui-combobox' size='16'
                           data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/docPoHeaderController.do?getPostatusCombobox"/>',
																																	valueField: 'id',
																																     textField: 'value'"/>
                </td>

            </tr>
            <tr>
                <th>产品</th>
                <td><input type='text' name='sku' id='sku' class='easyui-textbox' size='16'
                           data-options='required:false'/></td>
                <th>货主</th>
                <td><input type='text' name='customerid' id='customerid' class='easyui-textbox' size='16'
                           data-options=''/></td>

                <th>单位</th>
                <td><input type='text' name='uom' id='uom' class='easyui-textbox' size='16'
                           data-options='editable:true'/></td>
                <th>包装代码</th>
                <td><input type='text' name='packid' id='packid' class='easyui-textbox' size='16'
                           data-options='editable:true'/></td>
            </tr>
            <tr>
                <th>订单数量</th>
                <td><input type='text' name='orderedqty' id='orderedqty' class='easyui-numberbox' size=16
                           data-options='editable:true'/></td>
                <th>体积</th>
                <td><input type='text' name='totalcubic' id='totalcubic' class='easyui-numberbox' size='16'
                           data-options='required:true,min:0,precision:3'/></td>
                <th>净重量</th>
                <td><input type='text' name='totalnetweight' id='totalnetweight' class='easyui-numberbox' size='16'
                           data-options='required:true,min:0,precision:3'/></td>
                <th>重量</th>
                <td><input type='text' name='totalgrossweight' id='totalgrossweight' class='easyui-numberbox' size='16'
                           data-options='required:true,min:0,precision:2'/></td>
            </tr>
            <tr>
                <th>创建时间</th>
                <td><input type='text' name='addtime' id='addtime' class='easyui-datebox' size='16'
                           data-options='required:false,editable:false,readonly:true'/></td>
                <th>创建人</th>
                <td><input type='text' name='addwho' id='addwho' class='easyui-textbox' size='16'
                           data-options='required:false,editable:false,readonly:true'/></td>
                <th>最后编辑时间</th>
                <td><input type='text' name='edittime' id='edittime' class='easyui-datebox' size='16'
                           data-options='required:false,editable:false,readonly:true'/></td>
                <th>最后编辑人</th>
                <td><input type='text' name='editwho' id='editwho' class='easyui-textbox' size='16' data-options='required:false,editable:false,readonly:true'/>
                </td>
            </tr>
            <tr>
                <th>备注</th>
                <td colspan="4"><input type='text' name='notes' id='notes' class='easyui-textbox' size='16'
                           data-options="required:false,multiline:true,width:'300',height:'50'"/></td>
            </tr>
        </table>
    </form>
</div>
<div id='ezuiDetailsDialogBtn'>
    <a onclick='detailsCommit();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>