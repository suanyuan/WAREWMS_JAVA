<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%--点击主页按钮弹出二级dialog--%>
<div id='ezuiDialogGuKe' style="width:700px;height:600px;">
    <div class='easyui-layout' data-options='fit:true,border:false'>
        <div data-options="region:'center'">
            <div id='ezuiToolbarGuKe' class='datagrid-toolbar' style="">
                <fieldset>
                    <table>
                        <tr>
                         <th>出库日期</th>
                           <td><input type='text' id='SoTimeStart' class='easyui-datetimebox' size='17' data-options=""/>
                           </td>
                           <th>至
                           </th>
                           <td><input type='text' id='SoTimeEnd' class='easyui-datetimebox' size='17' data-options=""/></td>
                            <td colspan="2">
                                <a onclick='doxDialogSearchGuKe();' class='easyui-linkbutton'
                                   data-options='plain:true,iconCls:"icon-search"' href='javascript:void(0);'>查詢</a>
                                <a onclick='ezuiDialogGuKeClear();' class='easyui-linkbutton'
                                   data-options='plain:true,iconCls:"icon-remove"'
                                   href='javascript:void(0);'><spring:message code='common.button.clear'/></a>
                            </td>
                        </tr>
                    </table>
                </fieldset>
                <div id='ezuiDialogGuKeBtn'></div>
            </div>
            <table id='ezuiDetailsDatagridGuKe'></table>
        </div>
    </div>
</div>
<div id='ezuiDialogGuKeBtn'>
    <a onclick='GuKeInventoryT();' class='easyui-linkbutton' href='javascript:void(0);'>生成任务</a>
    <a onclick='ezuiDialogClose("#ezuiDialogGuKe");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>