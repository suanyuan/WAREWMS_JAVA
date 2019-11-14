<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%--<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>--%>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>


<script type='text/javascript'>



var enterpriseDialog;
</script>
<%--<c:import url='/WEB-INF/jsp/include/meta.jsp' />--%>
<%--<c:import url='/WEB-INF/jsp/include/easyui.jsp' />--%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId" value="${supplierId}"/></td>
        </tr>



        <%--<tr>--%>
            <%--<th>企业</th>--%>
            <%--<td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>
        <tr>
            <th>货主</th>
            <td><input type='text' data="1" id="customerid" name='customerid'  class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>产品代码</th>
            <td><input type='text' data="1" id="sku" name='sku'  class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>规格</th>
            <td><input type='text' data="1" id="specsName" name='specsName'  class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>生产批号</th>
            <td><input type='text' data="1" id="lotatt04" name='lotatt04'  class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>


        <tr>
            <th>合格证照片</th>
            <td>
                <input type="hidden" class="textbox-value" data="1" name="certificateContext" id="certificateContext" value=""/>
                <%--<input class="textbox-value" data="1" name="contractUrl" id="contractUrl" value=" value="${customer.contractUrl}"/>--%>
                <input id="certificateContextFile" name='file' value="" />
                <a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
            </td>
        </tr>

        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="addwho" name='addwho' value="${editwho}" class='easyui-textbox' size='16' data-options='required:true,width:200' readonly/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="addtime" name='addtime' value="${edittime}" class='easyui-textbox' size='16' data-options='required:true,width:200' readonly/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editwho" name='editwho' value="${editwho}" class='easyui-textbox' size='16' data-options='required:true,width:200' readonly/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="edittime" name='edittime' value="${edittime}" class='easyui-textbox' size='16' data-options='required:true,width:200' readonly/></td>
        </tr>

    </table>
</form>
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
                url : 'docAsnCertificateController.do?getInfo',
                data : {"specsName" : row.specsName,
                    "lotatt04":row.lotatt04,
                    "customerid":row.customerid,
                },
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
                        console.log(result.obj.certificateContext);

                        //$("#ezuiFormInfo input[id='createDate'][data='1']").textbox('setValue',result.obj.createDate);
                        $("#ezuiFormInfo input[id='editDate'][data='1']").textbox('setValue',result.obj.editDate);
                        // $("#ezuiFormInfo input[id='enterpriseIdQuery1'][data='1']").textbox('setValue',result.obj.enterpriseName);
                        $("#certificateContext").val(result.obj.certificateContext);
                        $("#ezuiFormInfo input[id='certificateContextFile']").textbox('setValue',result.obj.certificateContext);
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


//查询条件货主字段初始化
        $("#ezuiFormInfo #customerid").textbox({
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    $("#ezuiCustDataDialog #customerid").textbox('clear');
                    ezuiCustDataClickC();
                    ezuiCustDataDialogSearchC();
                }
            }]
        });
    });




    $(function () {
        // if(processType == 'edit'){
        //     $('input[name=customerid]').attr("readonly","readonly");
        //     $('input[name=sku]').attr("readonly","readonly");
        //     $('input[name=lotatt04]').attr("readonly","readonly");
        // }


        $('#certificateContextFile').filebox({
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
            $.messager.progress({
                text : '<spring:message code="common.message.data.processing"/>', interval : 100
            });
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
                    $.messager.progress('close');

                    //alert(data.comment);
                    if (data.success) {
                        $("#certificateContext").val(data.comment);

                        // $("#ezuiFormFirstRecord #recordUrl").val(data.comment);
                    }else {
                        showMsg("上传附件失败，请重试");
                        $("#certificateContextFile").filebox("setValue","");
                    }
                },
                onerror:function(er){
                    $.messager.progress('close');

                    // console.log(er);
                }
            });
        }
    })

    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#certificateContext").val()!=""){
                showUrl($("#certificateContext").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }

</script>
