<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
</head>
<body>
<style>
    .easyui-textbox{
        width: 180px;
    }
</style>
<div class='easyui-layout' data-options='fit:true,border:false'>
    <div data-options='region:"center",border:false' style='overflow: hidden;'>
            <div id='detailToolbar' class='datagrid-toolbar' style=''>
                <form id='ezuiFormDetail' method='post'>
                <fieldset>
                    <legend>产品注册证信息</legend>
                        <input type='hidden' id='gspProductRegisterId' name='gspProductRegisterId' value="${gspProductRegisterId}"/>
                        <table>
                            <tr>
                                <th>注册证编号</th>
                                <td><input type='text' name='productRegisterNo' class='easyui-textbox' value="${gspProductRegister.productRegisterNo}" data-options='required:true'/></td>
                                <th>管理分类</th>
                                <td><input type='text' name='classifyId' class='easyui-textbox' value="${gspProductRegister.classifyId}" data-options='required:true'/></td>
                                <th>分类目录</th>
                                <td><input type='text' name='classifyCatalog' class='easyui-textbox' value="${gspProductRegister.classifyCatalog}" data-options='required:true'/></td>
                                <th>产品名称</th>
                                <td><input type='text' name='productNameMain' class='easyui-textbox' value="${gspProductRegister.productNameMain}" data-options='required:true'/></td>
                            </tr>
                            <tr>
                                <th>注册人名称</th>
                                <td><input type='text' name='productRegisterName' class='easyui-textbox' value="${gspProductRegister.productRegisterName}" data-options='required:true'/></td>
                                <th>注册人住所</th>
                                <td><input type='text' name='productRegisterAddress' class='easyui-textbox' value="${gspProductRegister.productRegisterAddress}" data-options='required:true'/></td>
                                <th>生产地址</th>
                                <td><input type='text' name='productProductionAddress' class='easyui-textbox' value="${gspProductRegister.productProductionAddress}" data-options='required:true'/></td>
                                <th>代理人名称</th>
                                <td><input type='text' name='agentName' class='easyui-textbox' value="${gspProductRegister.agentName}" data-options='required:true'/></td>
                            </tr>
                            <tr>
                                <th>产品储存条件</th>
                                <td><input type='text' name='storageConditions' class='easyui-textbox' value="${gspProductRegister.storageConditions}"  data-options='required:true'/></td>
                                <th>有效期至</th>
                                <td><input type='text' name='productRegisterExpiryDate' class='easyui-textbox' value="${gspProductRegister.productRegisterExpiryDate}" data-options='required:true'/></td>
                                <th>有效期</th>
                                <td><input type='text' name='productExpiryDate' class='easyui-textbox' value="${gspProductRegister.productExpiryDate}" data-options='required:true'/></td>
                                <th>批准日期</th>
                                <td><input type='text' name='approveDate' class='easyui-textbox' value="${gspProductRegister.approveDate}" data-options='required:true'/></td>
                            </tr>
                            <tr>
                                <th>审核人</th>
                                <td><input type='text' name='checkerId' class='easyui-textbox' value="${gspProductRegister.checkerId}" data-options='required:true'/></td>
                                <th>审核时间</th>
                                <td><input type='text' name='checkDate' class='easyui-textbox' value="${gspProductRegister.checkDate}" data-options='required:true'/></td>
                                <th>创建人</th>
                                <td><input type='text' name='createId' class='easyui-textbox' value="${gspProductRegister.createId}" data-options='required:true'/></td>
                                <th>创建时间</th>
                                <td><input type='text' name='createDate' class='easyui-textbox' value="${gspProductRegister.createDate}" data-options='required:true'/></td>
                            </tr>
                            <tr>
                                <th>注册证版本</th>
                                <td><input type='text' name='productRegisterVersion' class='easyui-textbox' value="${gspProductRegister.productRegisterVersion}" data-options='required:true'/></td>
                                <th>其他内容</th>
                                <td><input type='text' name='otherContent' class='easyui-textbox' value="${gspProductRegister.otherContent}" data-options='required:true'/></td>
                                <th>编辑人</th>
                                <td><input type='text' name='editId' class='easyui-textbox' value="${gspProductRegister.editId}" data-options='required:true'/></td>
                                <th>编辑时间</th>
                                <td><input type='text' name='editDate' class='easyui-textbox' value="${gspProductRegister.editDate}" data-options='required:true'/></td>
                            </tr>

                            <tr>
                                <th>预期用途</th>
                                <td colspan="3"><input type='text' name='expectUse' class='easyui-textbox' value="${gspProductRegister.expectUse}" style="width: 440px;height: 50px;" data-options='required:true,multiline:true'/></td>
                                <th>适用范围</th>
                                <td colspan="3"><input type='text' name='applyScope' class='easyui-textbox' value="${gspProductRegister.applyScope}" style="width: 440px;height: 50px;" data-options='required:true,multiline:true'/></td>
                            </tr>
                            <tr>
                                <th>主要组成部分</th>
                                <td colspan="3"><input type='text' name='mainPart' class='easyui-textbox' value="${gspProductRegister.mainPart}" style="width: 440px;height: 50px;"  data-options='required:true,multiline:true'/></td>
                                <th>备注</th>
                                <td colspan="3"><input type='text' name='remark' class='easyui-textbox' value="${gspProductRegister.remark}" style="width: 440px;height: 50px;" data-options='required:true,multiline:true'/></td>
                            </tr>
                            <tr>
                                <th>注册证附件</th>
                                <td style="text-align: left;" colspan="7">
                                    <input type='text' id="attachmentUrlFile" name='attachmentUrl'  data-options='required:true'/>
                                    <a id="btn" href="#" class="easyui-linkbutton" data-options="">查看</a>
                                    <input type="hidden" class="textbox-value" name="attachmentUrl" id="attachmentUrl"/>
                                    <a onclick='addDetail();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>新增</a>
                                    <a onclick='submitDetail();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>
                                </td>
                            </tr>
                        </table>
                </fieldset>
                </form>
            </div>
            <table id='ezuiDatagridDetail' ></table>
            <form>
                <div>
                    <a onclick='detailsBind();' id='ezuiDetailsBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>绑定产品</a>
                    <a onclick='detailsUnBind();' id='ezuiDetailsBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>解除产品</a>
                </div>
            </form>
    </div>
    <div id='ezuiDialogSpec' style='padding: 10px;'>
        <div id='productToolbar' class='datagrid-toolbar' style=''>
            <fieldset>
                <legend>产品注册证信息</legend>
                <table>
                    <tr>
                        <th>产品代码</th>
                        <td><input type='text' id='productCode' class='easyui-textbox' size='16' data-options=''/></td>
                        <th>产品名称</th>
                        <td><input type='text' id='productName' class='easyui-textbox' size='16' data-options=''/></td>
                        <td>
                            <a onclick='getBy();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                            <a onclick='choseSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <table id="dataGridProduct">

        </table>
    </div>
</div>
<script>
    var ezuiFormDetail = $("#ezuiFormDetail");
    var ezuiDialogSpec;
    var dataGridProduct;
    $('#attachmentUrlFile').filebox({
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
            },
            onerror:function(er){
                console.log(er);
            }
        });
        //$('#file').filebox('clear');//上传成功后清空里面的值
    }

    $("#ezuiDatagridDetail").datagrid({
        url : sy.bp()+'/gspProductRegisterController.do?showSpecsList',
        method:'POST',
        toolbar : '#detailToolbar',
        title: '',
        pageSize : 50,
        pageList : [50, 100, 200],
        border: false,
        fitColumns : false,
        nowrap: true,
        striped: true,
        collapsible:false,
        pagination:true,
        rownumbers:true,
        idField : 'specsId',
        columns : [[
            {field: 'productCode',title: '产品代码' ,width: '25%'},
            {field: 'productName',title: '产品名称',width: '25%'},
            {field: 'specsName',title: '规格名称' ,width: '25%'},
            {field: 'productModel',title: '产品型号',width: '25%'}
        ]],
        onDblClickCell: function(index,field,value){

        },
        onRowContextMenu : function(event, rowIndex, rowData) {

        },
        onSelect: function(rowIndex, rowData) {

        },
        onLoadSuccess:function(data){
            $(this).datagrid('unselectAll');
            $(this).datagrid("resize",{height:540});
        }
    })
    
    function addDetail() {
        ezuiFormClear($("#ezuiFormDetail"))
    }
    
    function submitDetail() {
        var url = '';
        if (processType == 'edit') {
            url = '/gspProductRegisterController.do?edit';
        }else{
            url = '/gspProductRegisterController.do?add';
        }
        ezuiFormDetail.form('submit', {
            url : url,
            onSubmit : function(){
                if(ezuiFormDetail.form('validate')){
                    $.messager.progress({
                        text : '<spring:message code="common.message.data.processing"/>', interval : 100
                    });
                    return true;
                }else{
                    return false;
                }
            },
            success : function(data) {
                console.log(data);

            }
        });
    }

    var ezuiFormClear = function(ezuiForm){
        ezuiTimespinnerClear(ezuiForm);
        ezuiFileboxClear(ezuiForm);
        ezuiNumberboxClear(ezuiForm);
        ezuiTextboxClear(ezuiForm);
        ezuiComboboxClear(ezuiForm);
        ezuiCombotreeClear(ezuiForm);
        ezuiDateboxClear(ezuiForm);
        ezuiDatetimeboxClear(ezuiForm);
        $(ezuiForm).find('input[type=file]').each(function(){
            $(this).val('');
        });
        $(ezuiForm).find('input[type=checkbox]').each(function(){
            $(this).attr('checked',false);
        });
        $(ezuiForm).find('input').each(function(){
            $(this).removeClass('tooltip-f');
            $(this).removeClass('validatebox-invalid');
        });
    };

    function detailsBind(){
        ezuiDialogSpec.dialog('open');
    }

    function detailsUnBind(){

    }

    $(function () {
        dataGridProduct = $("#dataGridProduct").datagrid({
            url : sy.bp()+'/gspProductRegisterSpecsController.do?showDatagrid',
            method:'POST',
            toolbar : '#productToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            collapsible:false,
            fit:true,
            pagination:true,
            rownumbers:true,
            idField : 'specsId',
            columns : [[
                {field: 'ck',checkbox:true },
                {field: 'specsId',title: '主键' ,hidden:true},
                {field: 'specsName',title: '规格名称' ,width: '25%'},
                {field: 'productCode',title: '产品代码' ,width: '25%'},
                {field: 'productName',title: '产品名称',width: '25%'},
                {field: 'productModel',title: '产品型号',width: '25%'}
            ]],
            onDblClickCell: function(index,field,value){

            },
            onRowContextMenu : function(event, rowIndex, rowData) {

            },
            onSelect: function(rowIndex, rowData) {

            },
            onLoadSuccess:function(data){
                $(this).datagrid("resize",{height:540});
            }
        })

        ezuiDialogSpec = $('#ezuiDialogSpec').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            onClose : function() {
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');
    })
    
    function choseSelect() {
        var rows = dataGridProduct.datagrid("getChecked");
        console.log(rows);
        if(rows){
            var arr = new Array();
            for(var i=0;i<rows.length;i++){
                arr.push(rows[i].specsId);
            }
            $.ajax({
                url : 'gspProductRegisterController.do?addSpec',
                data : {"gspProductRegisterId":$("#gspProductRegisterId").val(),"specId" : arr.join(',')},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        $.messager.show({
                            msg : result.msg, title : '<spring:message code="common.message.prompt"/>'
                        });
                    }
                }
            });
        }
    }
</script>
</body>
</html>