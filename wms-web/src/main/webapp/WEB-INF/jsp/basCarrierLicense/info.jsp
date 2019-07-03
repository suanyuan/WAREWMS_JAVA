<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='basCarrierLicenseId' name='basCarrierLicenseId'/>

    <table>
        <tr style="display: none">
            <td><input type="hidden" id="carrierLicenseId" value="${basCarrierLicense.carrierLicenseId}"/></td>
        </tr>
        <tr>
            <th>道路经营许可证</th>
            <td><input type='text' data="1" id="roadNumber" value="${basCarrierLicense.roadNumber}" name='roadNumber' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>道路经营许可证照片</th>
            <td>
                <input id="roadNumberUrlFile" name='file' value="${basCarrierLicense.roadNumberlicenseUrl}">
                <a id="roadNumberbtn" href="#" class="easyui-linkbutton" data-options="">查看</a>
                <input type="hidden" class="textbox-value" name="roadNumberlicenseUrl" id="roadNumberlicenseUrl" value="${BasCarrierLicense.roadNumberlicenseUrl}"/>
            </td>
        </tr>
        <tr>
            <th>许可证有效期</th>
            <td><input type='text' data="1" id="roadNumberTerm" value="${basCarrierLicense.roadNumberTerm}" name='roadNumberTerm' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>许可证签发机关</th>
            <td><input type='text' data="1" id="roadAuthorityPermit" value="${basCarrierLicense.roadAuthorityPermit}" name='roadAuthorityPermit' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>道路经营范围</th>
            <td><input type='text' data="1" id="roadBusinessScope" value="${basCarrierLicense.roadBusinessScope}" name='roadBusinessScope' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>快递经营许可证编号</th>
            <td><input type='text' data="1" id="carrierNo" value="${basCarrierLicense.carrierNo}" name='carrierNo' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>快递经营许可证照片</th>
            <td>
                <input id="licenseUrlFile" name='file' value="${basCarrierLicense.licenseUrl}">
                <a id="btn" href="#" class="easyui-linkbutton" data-options="">查看</a>
                <input type="hidden" class="textbox-value" name="licenseUrl" id="licenseUrl" value="${BasCarrierLicense.licenseUrl}"/>
            </td>
        </tr>
        <tr>
            <th>快递经营许可证发证日期</th>
            <td><input type='text' data="1" id="carrierDate"  value="${basCarrierLicense.carrierDate}"  name='carrierDate' class="easyui-datebox" size='50' data-options='required:true'/></td>
        </tr> <tr>
            <th>快递经营许可证有效期截止日期</th>
            <td><input type='text' data="1" id="carrierEndDate" value="${basCarrierLicense.carrierEndDate}" name='carrierEndDate' class="easyui-datebox" size='50' data-options='required:true'/></td>
        </tr> <tr>
            <th>快递经营范围</th>
            <td><input type='text' data="1" id="carrierBusinessScope" value="${basCarrierLicense.carrierBusinessScope}"  name='carrierBusinessScope' class="easyui-textbox" size='50' data-options='required:true'/></td>
        </tr>
        <tr>
            <th>快递经营许可证签发机关</th>
            <td><input type='text' data="1" id="carrierAuthorityPermit" value="${basCarrierLicense.carrierAuthorityPermit}" name='carrierAuthorityPermit' class='easyui-textbox' size='50'  data-options='required:true'/></td>
        </tr>

        <tr>
            <th>是否启用</th>
            <td>
                <input type='text' data="1" id="isUse" value="${basCarrierLicense.isUse}" name='isUse' class='easyui-textbox' size='50'  data-options='required:true,editable:false'/>
            </td>
        </tr>
    </table>
</form>

<script>
    $(function(){



        $('#licenseUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
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
                width: '200', //文本宽度
                buttonText: '上传',  //按钮说明文字
                required: true,
                onChange:function(data){
                    if(data){
                        doUpload(data);
                    }
                }
            });


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
                        $("#licenseUrl").val(data.comment);
                        $("#roadNumberlicenseUrl").val(data.comment);
                    },
                    onerror:function(er){
                        console.log(er);
                    }
                });
                //$('#file').filebox('clear');//上传成功后清空里面的值
            }
    })


    $('input[name="isUse"]').combobox({
        url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
        valueField:'id',
        textField:'value'
    });



</script>
