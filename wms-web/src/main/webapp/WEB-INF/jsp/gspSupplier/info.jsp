<%@ page language='java' pageEncoding='UTF-8'%>

<c:import url='/WEB-INF/jsp/include/meta.jsp' />
<c:import url='/WEB-INF/jsp/include/easyui.jsp' />
<script type='text/javascript'>



var enterpriseDialog;
</script>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId" value="${supplierId}"/></td>
        </tr>
        <tr><th>企业</th>
            <td>
                <input type='text' data="1" id='enterpriseIdQuery1' size='16' name="enterpriseName" data='1' class='easyui-textbox' data-options='' style="width: 100px;"/>
                <input type="hidden"  id="enterpriseId" name="enterpriseId" data='1'  />
                <%--<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>--%>
            </td>
        </tr>
        <tr><th>代码</th><td><input type='text' data="1" id='enterpriseNo' size='16' name="enterpriseNo" class='easyui-textbox' data-options=''/></td></tr>
        <tr><th>简称</th><td><input type='text' data="1" id='shorthandName' size='16' name="shorthandName" class='easyui-textbox' data-options=''/></td></tr>



        <%--<tr>--%>
            <%--<th>企业</th>--%>
            <%--<td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>






        <tr>
            <th>是否审查</th>
            <td><input type="text" data="1" id="isCheck"  name="isCheck"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
        </tr>

        <tr>

            <%--<th>企业类型</th>--%>
            <%--<td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
            <th>企业类型</th><td><input type="text" data="1" id="operateType"  name="operateType"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '经营', value: '经营'},
																																	{id: '生产', value: '生产'}
																																]"/></td>
        </tr>
        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="createId" name='createId' value="${createId}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="createDate" name='createDate' value="${createDate}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editId" name='editId' value="${createId}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="editDate" name='editDate' value="${createDate}" class='easyui-textbox' size='16' data-options='required:true'/></td>
        </tr>
        <tr>
            <%--<th>是否有效</th>--%>
            <th>是否有效</th> <td><input type="text" data="1" id="isUse"  name="isUse"  value="${isUse}" class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
        </tr>
    </table>
</form>
<div id='enterpriseDialog' style='padding: 10px;'>

</div>
<script>
    var enterpriseDialog_gspSupplierInfo;

    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        //alert(row.supplierId);
        if(row){
            if(processType == 'edit'){
            $.ajax({
                url : 'gspSupplierController.do?getInfo',
                data : {"supplierId" : row.supplierId},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $("#ezuiFormInfo input[id!=''][data='1']").each(function (index) {
                            if($(this).attr("class")){
                                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                                    $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""]);
                                    //gspBusinessFrom[""+$(this).attr("id")+""] = $(this).textbox("getValue");
                                }else if($(this).attr("class").indexOf('easyui-combobox')!=-1){
                                    $(this).combobox("setValue",result.obj[""+$(this).attr("id")+""]);
                                }else if($(this).attr("class").indexOf('easyui-combobox')!=-1){
                                    $(this).datebox("setValue",result.obj[""+$(this).attr("id")+""]);
                                }
                            }


                        })
                        $("#ezuiFormInfo input[id='createDate'][data='1']").textbox('setValue',result.obj.createDate);
                        $("#ezuiFormInfo input[id='editDate'][data='1']").textbox('setValue',result.obj.editDate);
                        $("#ezuiFormInfo input[id='enterpriseIdQuery1'][data='1']").textbox('setValue',result.obj.enterpriseName);
                        //$("#enterpriseIdQuery1").textbox("setValue",result.Obj.enterpriseName);
                    }
                }
            });
            }
        }

        //
        // $('#packingUnit').combobox({
        //     url:sy.bp()+'/basPackageController.do?getCombobox',
        //     valueField:'value',
        //     textField:'value'
        // });

    })

    function searchEnterprise() {
        enterpriseDialog_gspSupplierInfo = $('#enterpriseDialog').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            href: sy.bp() + "/gspEnterpriseInfoController.do?toSearchDialog&target=gspSupplierInfo&enterpriseType=supplier",
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {

            }
        })
    }

    function choseSelect_gspSupplierInfo(id,name) {
        //console.log(id)
        //console.log(name)
        var enterpriceId;
        enterpriceId = id;
        //$("input[name='enterpriseId'][data='1']").val(id);
        $("#ezuiFormInfo input[id='enterpriseId']").textbox('setValue',id);
        $("#enterpriseIdQuery1").textbox("setValue",name);
        $.ajax({
            url : 'gspEnterpriseInfoController.do?getInfo',
            data : {enterpriseId : enterpriceId},
            type : 'POST',
            dataType : 'JSON',
            success : function(date){
                if(date.success){

                    //console.log(date.obj.enterpriseNo+'====result.enterpriseNo====');
                    //console.log('====result.shorthandName===='+date.obj.shorthandName);

                    //$("#enterpriseNo").textbox("setValue",date.obj.enterpriseNo);
                    //$("#shorthandName").textbox("setValue",date.obj[""+$("#shorthandName").attr("id")+""]);
                    $("#ezuiFormInfo input[id='enterpriseNo'][data='1']").textbox('setValue',date.obj.enterpriseNo);
                    $("#ezuiFormInfo input[id='shorthandName'][data='1']").textbox('setValue',date.obj.shorthandName);
                    //$(this).textbox("setValue",result.obj[""+$(this).attr("id")+""]);
                }
            }
        });

        //$("input[name='enterpriseId1']").val(id);
        enterpriseDialog_gspSupplierInfo.dialog("close");
    }

    $(function () {
        $("#enterpriseIdQuery1").textbox({
            width:135,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchMainEnterprise();
                }
            }]
        })
    })




</script>
