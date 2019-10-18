<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<div id='ezuiDetailsDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiDetailsForm' method='post'>
        <input type='hidden' id='docAsnDetailsId' name='docAsnDetailsId'/>
        <table>
            <tr>
                <th>入库单编号</th>
                <td><input type='text' name='asnno' id='asnno' class='easyui-textbox' size='16'
                           data-options='editable:false'/></td>
                <th>客户编码</th>
                <td><input type='text' name='customerid' id='customerid' class='easyui-textbox' size='16'
                           data-options='editable:false'/></td>
                <th>状态</th>
                <td><input type='text' name='linestatus' id='linestatus' class='easyui-combobox' size='16'
                           data-options="panelHeight:'auto',
																														editable:false,
																														url:'<c:url value="/docAsnHeaderController.do?getAsnstatusCombobox"/>',
																														valueField:'id',
																														textField:'value'"/>
                </td>
            </tr>
            <tr>
                <th>产品</th>
                <td><input type='text' name='sku' id='sku' class='easyui-textbox' size='16'
                           data-options="required:true"/></td>
                <th>产品名称</th>
                <td colspan="3"><input type='text' name='skudescrc' id='skudescrc' class='easyui-textbox' size='44'
                                       data-options="editable:false"/></td>
            </tr>
            <tr>
                <th>预计到货数</th>
                <td><input type='text' name='expectedqty' id='expectedqty' class='easyui-numberbox' size='16'
                           data-options='required:true,min:1,precision:0'/></td>
                <th>实际到货数</th>
                <td><input type='text' name='receivedqty' id='receivedqty' class='easyui-numberbox' size='16'
                           data-options='editable:false,min:0,precision:0'/></td>
                <th>收货库位</th>
                <td><input type='text' name='receivinglocation' id='location' class='easyui-textbox' size='16'/></td>
            </tr>
            <tr>
                <th>生产日期</th>
                <td><input type='text' name='lotatt01' id='lotatt01' class='easyui-datebox' size='16'
                           data-options='required:true,editable:true'/></td>
                <th>效期</th>
                <td><input type='text' name='lotatt02' id='lotatt02' class='easyui-datebox' size='16'
                           data-options='required:true,editable:true'/></td>
                <th>产品属性</th>
                <td><input type='text' name='lotatt09' id='lotatt09' size='16' data-options=''/></td>
            </tr>
            <tr>
                <th>生产批号</th>
                <td><input type='text' name='lotatt04' id='lotatt04' class='easyui-textbox' size='16' data-options=''/>
                </td>
                <th>序列号</th>
                <td><input type='text' name='lotatt05' id='lotatt05' class='easyui-textbox' size='16' data-options=''/>
                </td>
                <th>存储条件</th>
                <td><input type='text' name='lotatt11' id='lotatt11' class='easyui-textbox' size='16' data-options=''/>
                </td>
            </tr>
        </table>
        <div style="display: none;">
            <table>
                <tr>
                    <th>产品条码</th>
                    <td><input type='text' name='alternativesku' id='alternativesku' class='easyui-textbox' size='16'
                               data-options='editable:false'/></td>
                    <th>包装代码</th>
                    <td><input type='text' name='packid' id='packid' class='easyui-textbox' size='16'
                               data-options='editable:false'/></td>
                    <th>行号</th>
                    <td><input type='text' name='asnlineno' id='asnlineno' class='easyui-numberbox' size='16'
                               data-options='editable:false'/></td>
                    <th>重量</th>
                    <td><input type='text' name='totalgrossweight' id='totalgrossweight' class='easyui-numberbox'
                               size='16' data-options='min:0,precision:3'/></td>
                    <th>体积</th>
                    <td><input type='text' name='totalcubic' id='totalcubic' class='easyui-numberbox' size='16'
                               data-options='min:0,precision:3'/></td>
                    <th>单价</th>
                    <td><input type='text' name='totalprice' id='totalprice' class='easyui-numberbox' size='16'
                               data-options='min:0,precision:2'/></td>
                </tr>
                <tr>
                    <th>入库日期</th>
                    <td><input type='text' name='lotatt03' id='lotatt03' class='easyui-datebox' size='16'
                               data-options='required:false,editable:true'/></td>
                    <th>入库单号</th>
                    <td><input type='text' name='lotatt14' id='lotatt14' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <th>双证</th>
                    <td><input type='text' name='lotatt13' id='lotatt13' class='easyui-textbox' size='16'
                               data-options=''/></td>
                </tr>
                <tr>
                    <th>产品注册证</th>
                    <td><input type='text' name='lotatt06' id='lotatt06' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <th>灭菌批号</th>
                    <td><input type='text' name='lotatt07' id='lotatt07' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <th>供应商</th>
                    <td><input type='text' name='lotatt08' id='lotatt08' class='easyui-textbox' size='16'
                               data-options=''/></td>
                </tr>
                <tr>
                    <th>质量状态</th>
                    <td><input type='text' name='lotatt10' id='lotatt10' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <th>产品名称</th>
                    <td><input type='text' name='lotatt12' id='lotatt12' class='easyui-textbox' size='16'
                               data-options=''/></td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div id='ezuiDetailsDialogBtn'>
    <a onclick='detailsCommit();' id='ezuiBtn_detailsCommit' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.commit'/></a>
    <a onclick='ezuiDialogClose("#ezuiDetailsDialog");' class='easyui-linkbutton'
       href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>
<script>
    $(function () {
        $("#lotatt09").combobox({
            panelHeight: 'auto',
            url: sy.bp() + '/commonController.do?sampleAttr',
            valueField: 'id',
            textField: 'value',
            width: 110,
            required: true
        });

    })
</script>