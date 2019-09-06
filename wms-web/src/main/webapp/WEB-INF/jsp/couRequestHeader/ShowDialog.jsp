<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%--双击显示细单ialog--%>
<div id='ShowEzuiDialog' style="width:700px;height:600px;">
    <div class='easyui-layout' data-options='fit:true,border:false'>
        <div data-options="region:'center'">
            <div id='ShowEzuiToolbar' class='datagrid-toolbar' style="">
                <fieldset>
                    <table>
                        <tr>
                            <input type='hidden' id='cycleCountno' size='16' data-options=''/>
                            <th>货主代码</th>
                            <td>
                                <input type='text' id='customerid' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                            <th>产品代码</th>
                            <td>
                                <input type='text' id='sku' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                            <th>产品名称</th>
                            <td>
                                <input type='text' id='reservedfield01' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                        </tr>
                        <tr>
                            <th>产品线</th>
                            <td>
                                <input type='text' id='productLineName' class='easyui-combobox' size='16'
                                       data-options="url:'<c:url value="/productLineController.do?getCombobox"/>',
													valueField: 'id',
													textField: 'value'"/>
                            </td>

                            <th>序列号</th>
                            <td>
                                <input type='text' id='lotatt05' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                            <th>生产批号</th>
                            <td>
                                <input type='text' id='lotatt04' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                        </tr>
                        <tr>
                            <th>库位</th>
                            <td>
                                <input type='text' id='locationid' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                        <td colspan="2">
                            <a onclick='dosDialogSearch();' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                            <a onclick='ezuiDialogsToolbarClear();' class='easyui-linkbutton'
                               data-options='plain:true,iconCls:"icon-remove"'
                               href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                        </td>
                        </tr>
                    </table>
                </fieldset>
                <div id='ShowEzuiDialogBtn'></div>
            </div>
            <table id='ShowEzuiDetailsDatagrid'></table>
        </div>
    </div>
</div>
<div id='ShowEzuiDialogBtn'>
<%--    <a onclick='GenerateInventoryPlanT();' class='easyui-linkbutton' href='javascript:void(0);'>生成任务</a>--%>
    <a onclick='ezuiDialogClose("#ShowEzuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>