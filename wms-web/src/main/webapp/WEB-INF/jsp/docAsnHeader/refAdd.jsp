<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<!-- 引用新增 -->
<div id="refAddDialog" style="display: none;text-align: center">
    <table width="100%">
        <tr>
            <td>订单
                <input type='text' id='addRefFlag' size='16' class="easyui-combobox" data-options="
																								editable: false,
																								panelHeight: 'auto',
																								width:'150',
																								valueField: 'id',
																								textField: 'value',
																								data: [{
																									id: '1',
																									value: '引用出库明细'
																								}]" />
            </td>
        </tr>
        <tr>
            <td>
                编号
                <input id="addrefInNoTo" type="text" class='easyui-textbox' size='16' data-options="panelHeight: 'auto', width:'150'"/>
            </td>
        </tr>
        <tr>
            <td>
                货主
                <input id="customerIdRef" type="text" size='16'/>
            </td>
        </tr>
        <tr>
            <td>
                供应商
                <input id="supplierIdRef" type="text" style="width: 125px;"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <a onclick='closerefAdd()' id='closeRefInrefAdd' class='easyui-linkbutton' data-options=''
                   href='javascript:void(0);'>关闭</a>
                <a onclick='refAddDetailIn()' id='doRefInrefAdd' class='easyui-linkbutton' data-options='' href='javascript:void(0);'>引用</a>
            </td>
        </tr>
    </table>
</div>


