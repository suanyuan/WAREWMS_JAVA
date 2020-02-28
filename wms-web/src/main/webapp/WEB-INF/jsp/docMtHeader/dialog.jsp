<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%--点击主页按钮弹出二级dialog--%>
<div id='ezuiDialog' style="width:700px;height:600px;">
    <div class='easyui-layout' data-options='fit:true,border:false'>
        <div data-options="region:'center'">
            <div id='ezuiToolbar' class='datagrid-toolbar' style="">
                <fieldset>
                    <legend>查询</legend>
                    <table>
                        <tr>
                            <th>开始时间</th>
                            <td>
                                <input type='text' id='fromdate' class='easyui-datebox' size='16'
                                       data-options=''/>
                            </td>
                            <th>结束时间</th>
                            <td>
                                <input type='text' id='todate' class='easyui-datebox' size='16'
                                       data-options=''/>
                            </td>
                            <th>质量状态</th><td><input type='text' id='lotatt10' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																																	editable: false,
																																	url:'<c:url value="/commonController.do?qcState"/>',
																																	valueField: 'id',
																																     textField: 'value'"/></td>
                         </tr>
                        <tr>
                            <th>货主</th>
                            <td><input type='text' id='customerid' class='easyui-textbox' size='16' data-options=''/></td>
                            <th>库位</th>
                            <td><input type='text' id='locationid' class='easyui-textbox' size='16' data-options=''/></td>
                            <td colspan="2">
                                <a onclick='doDialogSearch();' class='easyui-linkbutton'
                                   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                                <a onclick='ezuiToolbarClear("#ezuiToolbar");' class='easyui-linkbutton'
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
    <a onclick='generationPlanT();' class='easyui-linkbutton' href='javascript:void(0);'>生成</a>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>