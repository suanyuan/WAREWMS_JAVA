<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%--点击主页按钮弹出二级dialog--%>
<div id='ezuiDialog' style="width:700px;height:600px;">
    <div class='easyui-layout' data-options='fit:true,border:false'>
        <div data-options="region:'center'">
            <div id='ezuiToolbar' class='datagrid-toolbar' style="">
                <fieldset>
                    <table>
                        <tr>
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
                            <th>产品线</th>
                            <td>
                                <input type='text' id='productLineName' class='easyui-combobox' size='16'
                                       data-options="url:'<c:url value="/productLineController.do?getCombobox"/>',
													valueField: 'id',
													textField: 'value'"/>
                            </td>
                            </tr>
                            <tr>
                            <th>生产批号</th>
                            <td>
                                <input type='text' id='lotatt04' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                            <th>序列号</th>
                            <td>
                                <input type='text' id='lotatt05' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                            <th>库位</th>
                            <td>
                                <input type='text' id='locationid' class='easyui-textbox' size='16'
                                       data-options=''/>
                            </td>
                            </tr>
                          <tr>
                            <td colspan="2">
                                <a onclick='doxDialogSearch();' class='easyui-linkbutton'
                                   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                                <a onclick='ezuiDialogxToolbarClear();' class='easyui-linkbutton'
                                   data-options='plain:true,iconCls:"icon-remove"'
                                   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <div id='ezuiDialogBtn'></div>
            </div>
            <table id='ezuiDetailsDatagrid'></table>
        </div>
    </div>
</div>
<div id='ezuiDialogBtn'>
    <a onclick='GenerateInventoryPlanT();' class='easyui-linkbutton' href='javascript:void(0);'>生成任务</a>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>