<%@ page contentType='text/html;charset=UTF-8' language='java' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%--点击主页按钮弹出二级dialog--%>
<div id='ezuiDialog'  class='easyui-dialog' style='padding: 10px;'  >
    <form id='ezuiForm' method='post' >
        <fieldset>
            <legend>生成质量状态管理主单</legend>
        <table>
            <tr>
                <th>管理单号</th>
                <td ><input type='text'  id='qcudocno' name='qcudocno' class='easyui-textbox' size='16' data-options='editable:false'/></td>

                <th>单号状态</th>
                <td ><input type='text' id='status'  name='status' class='easyui-combobox' size='16' data-options=" panelHeight: 'auto',
                                                                                                                                    readonly:true,
							                                                                                                        editable: false,
							                                                                                                        valueField: 'label',
																																	textField: 'value',
																																data: [{label: '00',
																																        value: '任务创建'},
																																        {label: '30',
																																        value: '部分完成'},
																																       {label: '40',
																																         value: '任务完成'},
																																       {label: '90',
																																         value: '任务取消'},
																																        {label: '99',
																																         value: '任务关闭'}]"/></td>
                <th>货主代码</th>
                <td ><input type='text'  id='customerid' name='customerid' class='easyui-textbox' size='16' data-options='required:true'/></td>

            </tr>
            <tr>
                 <th>备注</th>
                <td colspan="3"><input type='text'  id='notes' name='notes' class='easyui-textbox' size='16' data-options="required:false,multiline:true,width:'325',height:'40'"/></td>
                <td colspan='2'>
                    <a onclick='commitZ();' id='ezuiBtn_recommit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>
                </td>
            </tr>

        </table>
        </fieldset>
    </form>
<%--二级datafrid--%>
    <table id='ezuiDetailsDatagrid'></table>
    <form>
        <div >
            <a onclick='generatemanagement();' id='ezuiBtn_generatemanagement' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>生成质量状态管理</a>
            <a onclick='qualityStatusWork();' id='ezuiBtn_qualityStatusWork' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>质量状态管理作业</a>
            <a onclick='detailedit();' id='ezuiBtn_detailedit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message code='common.button.edit'/></a>
            <a onclick='detaildel();' id='ezuiBtn_detaildel' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message code='common.button.delete'/></a>
            <a onclick='clearDatagridSelected("#ezuiDetailsDatagrid");' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message code='common.button.cancelSelect'/></a>
        </div>
    </form>
</div>
<div id='ezuiDialogBtn'>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.close'/></a>
</div>