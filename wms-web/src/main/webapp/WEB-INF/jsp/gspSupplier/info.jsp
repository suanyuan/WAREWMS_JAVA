<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%--<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>


<script type='text/javascript'>


var  ezuiDialog1;
var enterpriseDialog;
var dialogUrl1 = "/gspEnterpriseInfoController.do?toDetail";


</script>
<%--<c:import url='/WEB-INF/jsp/include/meta.jsp' />--%>
<%--<c:import url='/WEB-INF/jsp/include/easyui.jsp' />--%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId" value="${supplierId}"/></td>
        </tr>
        <tr><th>企业</th>
            <td>
                <input type='text' data="1" id='enterpriseIdQuery1'  name="enterpriseName" data='1' class='easyui-textbox' data-options='required:true,width:200' style="width: 100px;"/>
                <input type="hidden"  id="enterpriseId" name="enterpriseId" data='1'  />
                <%--<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>--%>
                <a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewEnterpriseUrl()">查看</a>

            </td>
        </tr>
        <tr><th>代码</th><td><input type='text' data="1" id='enterpriseNo' size='20' name="enterpriseNo" class='easyui-textbox' data-options='required:true,width:200' readonly/></td></tr>
        <tr><th>简称</th><td><input type='text' data="1" id='shorthandName' size='20' name="shorthandName" class='easyui-textbox' data-options='required:true,width:200' readonly/></td></tr>
        <tr>

            <%--<th>企业类型</th>--%>
            <%--<td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
            <th>企业类型</th><td><input type="text" data="1" id="enterpriseType"  name="enterpriseType"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
                                                                                                                                    required:true,
                                                                                                                                    width:200,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'JY', value: '经营'},
																																	{id: 'SC', value: '生产'}
																																]" readonly/></td>
        </tr>


        <%--<tr>--%>
            <%--<th>企业</th>--%>
            <%--<td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>


        <tr>
            <th>合同编号</th>
            <td><input type='text' value="" data="1" id="contractNo"   name='contractNo' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" data="1" name="contractUrl" id="contractUrl" value=""/>
                <%--<input class="textbox-value" data="1" name="contractUrl" id="contractUrl" value=" value="${customer.contractUrl}"/>--%>
                <input id="contractUrlFile" name='file' value="" />
                <a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
            </td>
        </tr>
        <tr>
            <th>合同内容</th>
            <td><input type='text' value="" data="1" id="clientContent" name='clientContent' class='easyui-textbox' data-options='required:true,width:200,height:80,multiline:true'/></td>
        </tr>
        <tr>
            <th>合同开始时间</th>
            <td><input type='text' data="1"  id="clientStartDate"  name='clientStartDate' class='easyui-datebox' data-options='required:true,width:200
                                                                                                                               '/></td>
        </tr>
        <tr>
            <th>合同结束时间</th>
            <td><input type='text' data="1" id="clientEndDate"  name='clientEndDate' class='easyui-datebox' data-options='required:true,width:200
                                                                                                                                '/></td>
        </tr>
        <tr>
            <th>合同期限</th>
            <td><input type='text' data="1" id="clientTerm" name='clientTerm' class='easyui-numberbox' data-options='width:200'/></td>
        </tr>



        <tr>
            <th>是否审查</th>
            <td><input type="text" data="1" id="isCheck"  name="isCheck" value="${isCheck}"   class="easyui-combobox" size='16' data-options="panelHeight:'auto',
                                                                                                                                    required:true,
                                                                                                                                    width:200,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
        </tr>


        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="createId" name='createId' value="${createId}" class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="createDate" name='createDate' value="${createDate}" class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editId" name='editId' value="${createId}" class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="editDate" name='editDate' value="${createDate}" class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <%--<th>是否有效</th>--%>
            <th>是否有效</th> <td><input type="text" data="1" id="isUse"  name="isUse"  value="${isUse}" class="easyui-combobox" size='16' data-options="panelHeight:'auto',
                                                                                                                                    width:200,
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
<div id='ezuiDialogBtn1'>
    <%--<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'>提交企业信息</a>--%>
    <a onclick='ezuiDialogClose("#ezuiDialog1");' class='easyui-linkbutton' href='javascript:void(0);'>关闭</a>
</div>
<div id='ezuiDialog1' style='padding: 10px;'>

</div>

<div id='enterpriseDialog' style='padding: 10px;'>

</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
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
                                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                                    $(this).datebox("setValue",result.obj[""+$(this).attr("id")+""]);
                                }
                            }


                        })
                        //console.log(result.obj.enterpriseType);
                        console.log(result.obj.enterpriseId);
                        //$("#ezuiFormInfo input[id='createDate'][data='1']").textbox('setValue',result.obj.createDate);
                        $("#enterpriseId").val(result.obj.enterpriseId);
                        $("#ezuiFormInfo input[id='editDate'][data='1']").textbox('setValue',result.obj.editDate);
                        $("#ezuiFormInfo input[id='enterpriseIdQuery1'][data='1']").textbox('setValue',result.obj.enterpriseName);
                        $("#contractUrl").val(result.obj.contractUrl);
                        $("#ezuiFormInfo input[id='contractUrlFile']").textbox('setValue',result.obj.contractUrl);
                        //$("#ezuiFormInfo input[id='contractUrl']").textbox('setValue',result.obj.contractUrl);


                        //$("#ezuiFormInfo input[id='contractUrl']").val('setValue',result.obj.contractUrl);
                        //$("#ezuiFormInfo input[id='clientStartDate'][data='1']").datebox('setValue',result.obj.clientStartDate);
                        //$("#ezuiFormInfo input[id='clientEndDate'][data='1']").datebox('setValue',result.obj.clientEndDate);
                        //$("#contractUrl").val(result.obj.contractUrl);
                        //$("#contractUrl").val(result.obj.enterpriseType);
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
            href: sy.bp() + "/gspEnterpriseInfoController.do?toSearchDialog&target=gspSupplierInfo&enterpriseType=supplier&type=orther",
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {

            }
        })
    }

    function choseSelect_gspSupplierInfo(id,name) {
        //console.log(1111111)
        //console.log(name)
        var enterpriceId;
        enterpriceId = id;
        //$("input[name='enterpriseId'][data='1']").val(id);

        $("#ezuiFormInfo input[id='enterpriseId']").val(id);

        $("#enterpriseIdQuery1").textbox("setValue",name);

        $.ajax({
            url : 'gspEnterpriseInfoController.do?getInfo',
            data : {enterpriseId : enterpriceId},
            type : 'POST',
            dataType : 'JSON',
            success : function(date){
                if(date.success){
                   // console.log(3333333)
                    //console.log(date.obj.enterpriseNo+'====result.enterpriseNo====');
                    //console.log('====result.shorthandName===='+date.obj.shorthandName);

                    //$("#enterpriseNo").textbox("setValue",date.obj.enterpriseNo);
                    //$("#shorthandName").textbox("setValue",date.obj[""+$("#shorthandName").attr("id")+""]);
                    $("#ezuiFormInfo input[id='enterpriseNo'][data='1']").textbox('setValue',date.obj.enterpriseNo);
                    $("#ezuiFormInfo input[id='shorthandName'][data='1']").textbox('setValue',date.obj.shorthandName);

                    //$(this).textbox("setValue",result.obj[""+$(this).attr("id")+""]);
                    $("#ezuiFormInfo input[id='enterpriseType'][data='1']").combobox('setValue',date.obj.enterpriseType);

                }
            }
        });
        //console.log(4444444444)
        //$("input[name='enterpriseId1']").val(id);
        enterpriseDialog_gspSupplierInfo.dialog("close");
    }

    $(function () {
        $("#enterpriseIdQuery1").textbox({
            width:200,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        })
    })


    $(function () {
        $('#clientStartDate').datebox({
            onChange: function(date){
                onChangeDate();
            }
        });
        $('#clientEndDate').datebox({
            onChange: function(date){
                onChangeDate();
            }
        });

        $('#contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: true,
            onChange:function(data){

                if(data){
                    doUpload(data);
                }
            }
        });

        function doUpload(data) {
            var ajaxFile = new uploadFile({
                "url":sy.bp()+"/commonController.do?uploadFileLocal",
                "dataType":"json",
                "timeout":50000,
                "async":true,
                "data":{
                    //多文件
                    "file":{
                        //file为name字段 后台可以通过$_FILES["file"]获得
                        "file":document.getElementsByName("file")[0].files[0]//文件数组
                    }
                },
                onload:function(data){
                    $("#contractUrl").val(data.comment);
                },
                onerror:function(er){
                    // console.log(er);
                }
            });
        }
    })

    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#contractUrl").val()!=""){
                showUrl($("#contractUrl").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }



    function viewEnterpriseUrl() {
        $(function() {
            ezuiDialog1 = $('#ezuiDialog1').dialog({
                modal : true,
                title : '<spring:message code="common.dialog.title"/>',
                buttons : '',
                href:dialogUrl,
                width:1200,
                height:530,
                closable:true,
                cache: false,
                onClose : function() {
                    ezuiFormClear(ezuiForm);
                }
            }).dialog('close');
        })
        processType = 'edit';

            //var row = ezuiDatagrid.datagrid('getSelected');
        console.log($("#enterpriseId").val());
        var enterpriseId = $("#enterpriseId").val();
        if(enterpriseId!=null && enterpriseId!="" ){
            ezuiDialog1.dialog('refresh', dialogUrl1+"&id="+enterpriseId).dialog('open');
        }else{
            $.messager.show({
                msg : '请先选择企业', title : '提示'
            });
        }

    }




    function onChangeDate() {
        var startTime = $("#clientStartDate").datebox('getValue');
        var endTime =$("#clientEndDate").datebox('getValue') ;
        if(startTime!=null && endTime!=null&& endTime!=""&& startTime !=""  ) {
            var endTime = new Date(endTime);
            var startTime = new Date(startTime);
            var days = endTime.getTime() - startTime.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24));
            $("#clientTerm").numberbox('setValue',day);
        }
    }
</script>
