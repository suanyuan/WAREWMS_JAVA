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
            <th>历史文档名称</th>
            <td><input type='text' data="1" id="codenameC" name='codenameC'  class='easyui-textbox' size='16' data-options='required:true,width:200,editable:false'/></td>
        </tr>


        <tr>
            <th>历史文档</th>
            <td>
                <input type="hidden" class="textbox-value" data="1" name="codenameE" id="codenameE" value=""/>
                <%--<input class="textbox-value" data="1" name="contractUrl" id="contractUrl" value=" value="${customer.contractUrl}"/>--%>
                <input id="codenameEContextFile" name='file' value="" />
                <%--<a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>--%>
            </td>
        </tr>


    </table>
</form>
<div id='enterpriseDialog' style='padding: 10px;'>

</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var enterpriseDialog_gspSupplierInfo;
    // var ezuiDatagrid;
    var codenameC;
    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        // console.log(row.codenameC);
        // alert(row.codenameC);
        //alert(row.supplierId);
        if(row){
            console.log(row.codenameC);
            // codenameC = row.codenameC;
            // $("#ezuiFormInfo #codenameC").textbox('setValue',row.codenameC);
            // $("#ezuiFormInfo input[id='codenameC'][data='1']").textbox('setValue',codenameC);
            if(processType == 'edit'){
            $.ajax({
                url : 'historyFileController.do?getInfo',
                data : {"codeid" : 'HIS_FILE',
                    "codenameC":row.codenameC,
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
                        console.log(result.obj.codenameE);

                        //$("#ezuiFormInfo input[id='createDate'][data='1']").textbox('setValue',result.obj.createDate);
                        $("#ezuiFormInfo input[id='codenameEContextFile']").textbox('setValue',result.obj.codenameE);

                    }
                }
            });
            }
        }
    });




    $(function () {
        // if(processType == 'edit'){
        //     $('input[name=customerid]').attr("readonly","readonly");
        //     $('input[name=sku]').attr("readonly","readonly");
        //     $('input[name=lotatt04]').attr("readonly","readonly");
        // }


        $('#codenameEContextFile').filebox({
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
                        $("#codenameE").val(data.comment);

                        // $("#ezuiFormFirstRecord #recordUrl").val(data.comment);
                    }else {
                        showMsg("上传附件失败，请重试");
                        $("#codenameEContextFile").filebox("setValue","");
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
            if($("#codenameE").val()!=""){
                showUrl($("#codenameE").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }

</script>
