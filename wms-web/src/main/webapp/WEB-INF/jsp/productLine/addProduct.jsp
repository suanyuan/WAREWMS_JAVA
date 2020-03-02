<%@ page language='java' pageEncoding='UTF-8'%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style>
    table th{
        text-align: right;
    }
</style>
<%--<div id='detailBusinessToolbar' class='datagrid-toolbar' style='padding: 0px;background-color: #ffffff;'>
    <form id='ezuiFormBusiness' method='post' style="padding: 0px;">
        <input type='hidden' data="1" id='businessId' name='businessId' value="${gspBusinessLicense.businessId}"/>
        <input type='hidden' id='gspEnterpriseId' name='gspEnterpriseId' value="${gspBusinessLicense.enterpriseId}"/>
        <input type='hidden' id='choseScope' value="${choseScope}"/>
        <input type='hidden' id='opType' value="add"/>
        <fieldset>
            <legend>明细</legend>
            <table>
                <tr>
                    <th>证照编号</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.licenseNumber}" id="licenseNumber" name='licenseNumber' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>统一社会信用代码</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.socialCreditCode}" id="socialCreditCode" name='socialCreditCode' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>名称</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.licenseName}" id="licenseName" name='licenseName' class='easyui-textbox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>类型</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.licenseType}" id="licenseType" name='licenseType' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>法定代表人</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.juridicalPerson}" id="juridicalPerson" name='juridicalPerson' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>注册资本</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.registeredCapital}" id="registeredCapital" name='registeredCapital' class='easyui-textbox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>住所</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.residence}" id="residence" name='residence' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>登记机关</th>
                    <td><input type='text' data="1" value="${gspBusinessLicense.registrationAuthority}" id="registrationAuthority" name='registrationAuthority' class='easyui-textbox' data-options='required:true,width:200'/></td>
                    <th>成立日期</th>
                    <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.establishmentDate}"/>" id="establishmentDate" name='establishmentDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
                </tr>
                <tr>
                    <th>发证日期</th>
                    <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.issueDate}"/>" id="issueDate" name='issueDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
                    <th>营业期限时间</th>
                    <td colspan="3">
                        <input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.businessStartDate}"/>" id="businessStartDate" name='businessStartDate' class='easyui-datebox' data-options='required:true,width:200<c:if test="${gspBusinessLicense.isLong == '1'}">,disabled:true</c:if>'/>
                        &nbsp;&nbsp;至&nbsp;&nbsp;
                        <input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspBusinessLicense.businessEndDate}"/>" id="businessEndDate" name='businessEndDate' class='easyui-datebox' data-options='required:true,width:180<c:if test="${gspBusinessLicense.isLong == '1'}">,disabled:true</c:if>'/>
                        <input id="isLong" <c:if test="${gspBusinessLicense.isLong == '1'}">checked</c:if> type="checkbox" class="checkbox"><label for="isLong">长期/无固定时间</label>
                    </td>
                </tr>
                <tr>
                    <th>营业执照照片</th>
                    <td>
                        <input id="file" name='file' value="${gspBusinessLicense.attachmentUrl}">
                        <a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
                        <input type="hidden" data="2" class="textbox-value" name="attachmentUrl" id="attachmentUrl" value="${gspBusinessLicense.attachmentUrl}"/>
                        <!--<a onclick='businessSubmit()' id='ezuiDetailsBtn_save' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>-->
                    </td>
                    <th>经营范围</th>
                    <td colspan="5">
                        <input type='text' data="1" value="${gspBusinessLicense.businessScope}" id="businessScope" name='businessScope' style="height:45px;" class='easyui-textbox' data-options='required:true,multiline:true,width:400'/>
                        <!--<a onclick='selectBusinessScope()' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>经营范围选择</a>-->
                    </td>
                </tr>

            </table>
        </fieldset>
    </form>

</div>--%>
<div id='ezuiDialog' style='padding: 10px;'>
    <form id='ezuiForm' method='post'>
        <input type='hidden' id='productLineId' name='productLineId' value="${productLine.productLineId}"/>
        <input type='hidden' id='customerid' name='customerid' value="${productLine.customerid}"/>
        <table>
            <tr>
                <th>产品线名称</th>
                <td><input type='text' id="enterpriseN"  name='name' value="${productLine.name}" class='easyui-textbox' size='16' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>货主</th>
                <td><input type='text' id="descrC" name='descrC' value="${descrC}" class='easyui-textbox' size='16' data-options='required:true'/></td>
            </tr>
            <tr>
                <th>说明</th>
                <td><input type='text' name='expression' value="${productLine.expression}" class='easyui-textbox' size='16' data-options=''/></td>
            </tr>
            <tr>

            <th>出库记录序列号</th>
                <td><input type='text'  name ='serialFlag'  id='serialFlag' value="${productLine.serialFlag}" class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																										                                    editable: false,
									    																	                                    url:'<c:url value="/commonController.do?getYesOrNoCombobox"/>',
																									                                 	    valueField: 'id',
																										                                    textField: 'value',required:true"/></td>
            </tr>
            <th>风险评估</th>

            <td><input type='text'  name ='riskAssessment'  id='riskAssessment' value="${productLine.riskAssessment}" class='easyui-combobox' size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '已通过', value: '已通过'},
																																	{id: '未通过', value: '未通过'},
																																	{id: '未评估', value: '未评估'},
																																	{id: '无需评估', value: '无需评估'}
																																]"/></td>
            </tr>
        </table>
    </form>
</div>

<div id='ezuiDialogClientDetail' style='padding: 10px;'>
    <div id='clientTB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input type='text' id='kehudaima1' class='easyui-textbox' data-options=''/></td>
                    <th>客户名称：</th><td><input type='text' id='kehumingcehng1' class='easyui-textbox' data-options=''/></td>
                </tr>
                <tr>
                    <%-- <th>企业信息代码：</th><td><input type='text' id='qiyexinxidaima1' class='easyui-textbox' data-options=''/></td>
                     <th>企业名称：</th><td><input type='text' id='qiyemingcheng2' class='easyui-textbox' data-options=''/></td>--%>
                    <%-- <td>
                         <input type='text' id='enterpriseIdQuery' style="width: 170px;"/>
                         <input type="hidden" class="easyui-textvalue" name="enterpriseId">
                         <!--<a href="javascript:void(0)" onclick="searchMainEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>-->
                     </td>
                     <th>首营状态：</th><td><input type='text' id='firstState' class='easyui-textbox' data-options=''/></td>--%>
                    <td>
                        <a onclick='doSearchClient();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridClientDetail">

    </table>
</div>

<div id="dialogClient"></div>

<script>
    var ezuiDialogClientDetail;
    $(function () {


        $("#descrC").textbox({

            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchClient();
                }
            }]
        });







        //货主弹窗列表
        clientDatagrid = $("#dataGridClientDetail").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#clientTB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                activeFlag : '1',
                customerType:'OW'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'clientId',
            columns : [[
                {field: 'customerType',	title: '客户类型 ',	width: 80,formatter:function(value,rowData,rowIndex){
                        if (rowData.customerType=='CO') {
                            return rowData.customerType='收货单位';
                        }else if (rowData.customerType=='VE'){
                            return rowData.customerType='供应商';
                        }else if (rowData.customerType=='CA'){
                            return rowData.customerType='承运商';
                        }else if (rowData.customerType=='OT'){
                            return rowData.customerType='其他';
                        }else if (rowData.customerType=='OW'){
                            return rowData.customerType='货主';
                        }else if (rowData.customerType=='PR'){
                            return rowData.customerType='生产企业';
                        }else if (rowData.customerType=='WH'){
                            return rowData.customerType='主体';
                        }
                    } },
                {field: 'activeFlag',		title: '是否合作 ',	width: 80,formatter:function(value,rowData,rowIndex){
                        return rowData.activeFlag == '1' ? '是' : '否';
                    }},
                {field: 'customerid',		title: '客户代码',	width: 80 },
                {field: 'descrC',		title: '客户名称',	width: 80 },
                {field: 'enterpriseNo',		title: '企业信息代码 ',	width: 80 },
                {field: 'shorthandName',		title: '简称 ',	width: 85 },
                {field: 'enterpriseName',		title: '企业名称 ',	width: 80 },
                {field: 'contacts',		title: '联系人 ',	width: 85 },
                {field: 'contactsPhone',		title: '联系人电话 ',	width: 80 },
                {field: 'remark',		title: '备注 ',	width: 85 },
            ]],
            onDblClickCell: function(index,field,value){
                choseClientSelect();
            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid('unselectAll');
                $(this).datagrid("resize",{height:540});
            }
        });
        //货主弹窗
        ezuiDialogClientDetail = $('#ezuiDialogClientDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,

            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogClient = $('#dialogClient').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,

            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

    });


    //打开货主弹窗
    function searchClient() {
        if(ezuiDialogClientDetail){
            ezuiDialogClientDetail.dialog('open');
        }

    }
    //查询货主信息条件
    function doSearchClient() {
        clientDatagrid.datagrid('load', {
            /* enterpriseName : $('#qiyemingcheng2').val(),
             enterpriseNo : $('#qiyexinxidaima1').val(),*/
            descrC : $('#kehumingcehng1').val(),
            customerid : $('#kehudaima1').val(),
            isUse : '1',
            customerType:'OW'
        });
    }
    //选择货主
    function choseClientSelect() {
        var row = clientDatagrid.datagrid("getSelected");

        if(row){

            $("#descrC").textbox("setValue",row.descrC);
            $("#customerid").val(row.customerid);

            ezuiDialogClientDetail.dialog('close');
        }
    }

</script>