<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='enterpriseD' name='enterpriseId' class='textbox-value' value="${enterpriseInFo.enterpriseId}"/>

    <table>
        <tr style="display: none">
            <td><input type="hidden" id="carrierLicenseId" name="carrierLicenseId" value="${basCarrierLicense.carrierLicenseId}"  class="textbox-value"/></td>
        </tr>
        <tr>
            <th style="color: #0E2D5F">承运商</th>
            <td><input type='text' data="1" id="enterpriseId"   name='enterpriseName' value="${enterpriseInFo.enterpriseName}" class='easyui-textbox'  data-options='required:true'/></td>
        </tr>
        <tr>
            <th style="color: #0E2D5F">道路经营许可证</th>
            <td><input type='text' data="1" id="roadNumber" value="${basCarrierLicense.roadNumber}" name='roadNumber' class='easyui-textbox'  data-options='required:true'/></td>
        </tr>
        <tr>
            <th style="color: #0E2D5F">许可证照片</th>
            <td>
                <input id="roadNumberUrlFile" name='afile' value="${basCarrierLicense.roadNumberUrl}"/>
                <a id="roadNumberbtn" href="#" class="easyui-linkbutton" data-options="" onclick="viewUrlRoad()">查看</a>
                <input type="hidden" class="textbox-value" name="roadNumberUrl" id="roadNumberUrl" value="${basCarrierLicense.roadNumberUrl}"/>
            </td>
        </tr>
        <tr>
            <th style="color: #0E2D5F">有效期</th>
            <td><input type='text' data="1" id="roadNumberTerm" value="${basCarrierLicense.roadNumberTerm}" name='roadNumberTerm' class='easyui-textbox'  data-options='required:true'/></td>
        </tr>
        <tr>
            <th style="color: #0E2D5F">签发机关</th>
            <td><input type='text' data="1" id="roadAuthorityPermit" value="${basCarrierLicense.roadAuthorityPermit}" name='roadAuthorityPermit' class='easyui-textbox'  data-options='required:true'/></td>
        </tr>
        <tr>
            <th  style="color: #0E2D5F">经营范围</th>
            <td><input type='text' data="1" id="roadBusinessScope" value="${basCarrierLicense.roadBusinessScope}" name='roadBusinessScope' class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>快递经营许可证编号</th>
            <td><input type='text' data="1" id="carrierNo" value="${basCarrierLicense.carrierNo}" name='carrierNo' class='easyui-textbox'  data-options=''/></td>
        </tr>
        <tr>
            <th>经营许可证照片</th>
            <td>
                <input type="hidden" class="textbox-value" name="licenseUrl"   id="licenseUrl" value="${basCarrierLicense.licenseUrl}"/>
                <input id="licenseUrlFile" name='bfile'  value="${basCarrierLicense.licenseUrl}">
                <a id="btn1" href="#" class="easyui-linkbutton" data-options="" onclick="viewUrlCarrier()">查看</a>
            </td>
        </tr>
        <tr>
            <th>许可证发证日期</th>
            <td><input type='text'  <%--value="${basCarrierLicense.carrierDate}"--%>value="<fmt:formatDate pattern="yyyy-MM-dd" value="${basCarrierLicense.carrierDate}"/>" class="easyui-datebox"  data="1" id="carrierDate" name='carrierDate'   data-options='required:false'/></td>
        </tr>
        <tr>
            <th>有效期至</th>
            <td><input type='text' data="1" id="carrierEndDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${basCarrierLicense.carrierEndDate}"/>" name='carrierEndDate' class="easyui-datebox" data-options='required:false'/></td>
        </tr>
        <tr>
            <th>经营范围</th>
            <td><input type='text' data="1" id="carrierBusinessScope" value="${basCarrierLicense.carrierBusinessScope}"  name='carrierBusinessScope' class="easyui-textbox" data-options='required:false'/></td>
        </tr>
        <tr>
            <th>签发机关</th>
            <td><input type='text' data="1" id="carrierAuthorityPermit" value="${basCarrierLicense.carrierAuthorityPermit}" name='carrierAuthorityPermit' class='easyui-textbox'   data-options='required:false'/></td>
        </tr>
        <tr>
            <th>合同编号</th>
            <td><input type='text' name='contractNo' class='easyui-textbox' value="${basCarrierLicense.contractNo}" data-options='required:true'/></td>
        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input  type="hidden" name="contractUrl" id="contractUrl"  value="${basCarrierLicense.contractUrl}" data-options='' class="textbox-value"/>
                <input  id="contractUrlFile" name='file' value="${basCarrierLicense.contractUrl}" >
                <a  id="btn" href="#" class="easyui-linkbutton" onclick="viewUrl()" >查看</a>
            </td>
        </tr>

        <%--<tr>
            <th>经营许可证照片</th>
            <td>
                <input id="licenseUrlFile" name='file'  &lt;%&ndash;value="${basCarrierLicense.licenseUrl}"&ndash;%&gt;>
                <a id="btn1" href="#" class="easyui-linkbutton" data-options="">查看</a>
                <input type="hidden" class="textbox-value" name="licenseUrl"   id="licenseUrl" value="${BasCarrierLicense.licenseUrl}"/>
            </td>
        </tr>--%>
        <tr>
            <th>合同内容</th>
            <td><input type='text' name='clientContent' value="${basCarrierLicense.clientContent}" class='easyui-textbox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>合同开始时间</th>
            <td><input type='text' id="clientStartDate" name='clientStartDate' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${basCarrierLicense.clientStartDate}"/>" class='easyui-datebox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>合同结束时间</th>
            <td><input type='text' id="clientEndDate" name='clientEndDate' value="<fmt:formatDate pattern="yyyy-MM-dd" value="${basCarrierLicense.clientEndDate}"/>"  class='easyui-datebox' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>合同期限</th>
            <td><input type='text' name='clientTerm'  class='easyui-numberbox' value="${basCarrierLicense.clientTerm}" data-options='required:true'/></td>
        </tr>
        <tr>
            <th>是否启用</th>
            <td>
                <input type='text' data="1" id="isUse" value="${basCarrierLicense.activeFlag}" name='activeFlag' class='easyui-textbox'   data-options='required:true,editable:false'/>
            </td>
        </tr>
    </table>
</form>
<div>
    <div id="dialogEnterprise">

    </div>



    <div id='ezuiDialogDetail' style='padding: 10px;'>

        <div id='detailToolbar' class='datagrid-toolbar' style=''>
            <fieldset>
                <legend>企业信息</legend>
                <table>
                    <tr>
                        <th>代码</th>
                        <td><input type='text' id='enterprise' class='easyui-textbox' data-options='width:200'/></td>
                        <th>简称</th>
                        <td><input type='text' id='shorthandName' class='easyui-textbox' data-options='width:200'/></td>
                        <td>
                            <a onclick='doSearchEnterprise();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                            <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <table id="dataGridDetail">

        </table>
    </div>
</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $(function(){

        $("#enterpriseId").textbox({
            value:"${enterpriseInFo.enterpriseName}",
            width:170,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        });


        $('#licenseUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '170', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: false,
            onChange:function(data){
                if(data){
                    doUploadCarrier(data);
                }
            }
        });

        $('#contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '170', //文本宽度
            buttonText: '上传',  //按钮说明文字
            required: true,
            onChange:function(data){
                if(data){
                    doUpload(data);
                }
            }
        });


            $('#roadNumberUrlFile').filebox({
                prompt: '选择一个文件',//文本说明文件
                width: '170', //文本宽度
                buttonText: '上传',  //按钮说明文字
                required: true,
                onChange:function(data){
                    if(data){
                        doUploadRoad(data);
                    }
                }
            });

        enterpriseDatagrid = $("#dataGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagridSearch',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '<spring:message code="common.dialog.title"/>',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1',
                enterpriseType:'default'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 81 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: 91 },
                {field: 'shorthandName',		title: '简称',	width: 91 },
                {field: 'enterpriseName',		title: '企业名称',	width: 91 },
                {field: 'enterpriseType',		title: '企业类型',	width: 91 ,formatter: entTypeFormatter},
                {field: 'createId',		title: '录入人',	width:81 },
                {field: 'createDate',		title: '录入时间',	width: 81 },
                {field: 'editId',		title: '修改人',	width: 81 },
                {field: 'editDate',		title: '修改时间',	width: 81 },
                {field: 'isUse',		title: '是否有效',	width: 91,formatter:isUseFormatter }
            ]],
            onDblClickCell: function(index,field,value){
                choseSelect();
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


        dataGridDetail = $('#ezuiDialogDetail').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        dialogEnterprise = $('#dialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            cache: false,
            onClose : function() {
                //  ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

        /*var row = ezuiDatagrid.datagrid('getSelected');
        if(row){
            $.ajax({
                url : 'basCarrierLicenseController.do?getInfo',
                data : {"enterpriseId" : row.enterpriseId},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $("#ezuiFormInfo input[id!=''][data='1']").each(function (index) {
                            $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                        });

                        $("#attachmentUrl").val(result.obj.attachmentUrl);
                        $("#licenseUrlFile").val(result.obj.attachmentUrl);
                    }
                }
            });
        }*/

            /* var row = ezuiDatagrid.datagrid('getSelected');
             if(row){
                 $.ajax({
                     url : 'gspEnterpriseInfoController.do?getOperate',
                     data : {"enterpriseId" : row.enterpriseId},
                     type : 'POST',
                     dataType : 'JSON',
                     success : function(result){
                         if(result.success){
                             $("#ezuiFormOperate input[id!=''][data='1']").each(function (index) {
                                 $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""])
                             })
                             $("#attachmentUrl").val(result.obj.attachmentUrl);
                             $("#licenseUrlFile").val(result.obj.attachmentUrl);
                         }
                     }
                 });
             }

         })*/
        $('input[name="activeFlag"]').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });


    });


    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#contractUrl").val()!=""){
                showUrl($("#contractUrlFile").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }

    function viewUrlRoad(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#roadNumberUrl").val()!=""){
                showUrl($("#roadNumberUrlFile").val());
            }else {
                showMsg("请上传道路经营许可证！");
            }
        }
    }
    function viewUrlCarrier(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#licenseUrl").val()!=""){
                showUrl($("#licenseUrlFile").val());
            }else {
                showMsg("请上传快递经营许可证！");
            }
        }
    }
//上传合同附件
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
                console.log(data)
                $("#contractUrl").val(data.comment);
             //   $("#roadNumberlicenseUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }
//上传道路经营许可证照片
    function doUploadRoad(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("afile")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                console.log(data)
                $("#roadNumberUrl").val(data.comment);
             //   $("#roadNumberlicenseUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }

 //上传快递经营照片
    function doUploadCarrier(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("licenseUrlFile")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                console.log(data)
                $("#licenseUrl").val(data.comment);
             //   $("#roadNumberlicenseUrl").val(data.comment);
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }



    function searchEnterprise() {
        if(dataGridDetail){
            dataGridDetail.dialog('open');
        }

    }


    function doSearchEnterprise() {
        enterpriseDatagrid.datagrid('load', {
            enterpriseNo : $('#enterprise').val(),
            shorthandName : $('#shorthandName').val(),
            isUse : '1'
        });
    }

    function choseSelect() {
        var row = enterpriseDatagrid.datagrid("getSelected");
        if(row){
            $("#enterpriseId").textbox("setValue",row.enterpriseName);
          //  $("#enterpriseD").textbox("setValue",row.enterpriseId);
           $("#enterpriseD").val(row.enterpriseId);
            dataGridDetail.dialog('close');
        }
    }






</script>
