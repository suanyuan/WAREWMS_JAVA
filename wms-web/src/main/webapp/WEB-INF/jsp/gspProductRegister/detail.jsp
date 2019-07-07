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
                        <input type='hidden' id='gspProductRegisterId' name='gspProductRegisterId' value="${gspProductRegister.productRegisterId}"/>
                        <table width="100%">
                            <tr>
                                <th>注册证编号</th>
                                <td><input type='text' name='productRegisterNo' class='easyui-textbox' value="${gspProductRegister.productRegisterNo}" data-options='required:true'/></td>
                                <th>所属企业</th>
                                <td>
                                    <input type="hidden" id="enterpriseId" name="enterpriseId" class="textbox-value" value="${gspProductRegister.enterpriseId}"/>
                                    <input type='text' id='enterpriseName' value="" data-options='required:true,width:150,editable:false'/>
                                    <a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>
                                </td>
                                <th>管理分类</th>
                                <td><input type='text' name='classifyId' class='easyui-textbox' value="${gspProductRegister.classifyId}" data-options='required:true'/></td>
                                <th>分类目录</th>
                                <td><input type='text' name='classifyCatalog' class='easyui-textbox' value="${gspProductRegister.classifyCatalog}" data-options='required:true'/></td>
                            </tr>
                            <tr>
                                <th>产品名称</th>
                                <td><input type='text' name='productNameMain' class='easyui-textbox' value="${gspProductRegister.productNameMain}" data-options='required:true'/></td>
                                <th>注册人名称</th>
                                <td><input type='text' name='productRegisterName' class='easyui-textbox' value="${gspProductRegister.productRegisterName}" data-options='required:true'/></td>
                                <th>注册人住所</th>
                                <td><input type='text' name='productRegisterAddress' class='easyui-textbox' value="${gspProductRegister.productRegisterAddress}" data-options='required:true'/></td>
                                <th>生产地址</th>
                                <td><input type='text' name='productProductionAddress' class='easyui-textbox' value="${gspProductRegister.productProductionAddress}" data-options='required:true'/></td>
                            </tr>
                            <tr>
                                <th>代理人名称</th>
                                <td><input type='text' name='agentName' class='easyui-textbox' value="${gspProductRegister.agentName}" data-options='required:true'/></td>
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
                                <th>产品储存条件</th>
                                <td><input type='text' name='storageConditions' class='easyui-textbox' value="${gspProductRegister.storageConditions}"  data-options='required:true'/></td>
                                <th>其他内容</th>
                                <td><input type='text' name='otherContent' class='easyui-textbox' value="${gspProductRegister.otherContent}" data-options='required:true'/></td>
                                <th>编辑人</th>
                                <td><input type='text' name='editId' class='easyui-textbox' value="${gspProductRegister.editId}" data-options='required:true'/></td>
                                <th>编辑时间</th>
                                <td><input type='text' name='editDate' class='easyui-textbox' value="${gspProductRegister.editDate}" data-options='required:true'/></td>
                            </tr>

                            <tr>
                                <th>预期用途</th>
                                <td colspan="3"><input type='text' name='expectUse' class='easyui-textbox' value="${gspProductRegister.expectUse}" style="width: 100%;height: 50px;" data-options='required:true,multiline:true'/></td>
                                <th>适用范围</th>
                                <td colspan="3"><input type='text' name='applyScope' class='easyui-textbox' value="${gspProductRegister.applyScope}" style="width: 100%;height: 50px;" data-options='required:true,multiline:true'/></td>
                            </tr>
                            <tr>
                                <th>主要组成部分</th>
                                <td colspan="3"><input type='text' name='mainPart' class='easyui-textbox' value="${gspProductRegister.mainPart}" style="width: 100%;height: 50px;"  data-options='required:true,multiline:true'/></td>
                                <th>备注</th>
                                <td colspan="3"><input type='text' name='remark' class='easyui-textbox' value="${gspProductRegister.remark}" style="width: 100%;height: 50px;" data-options='required:true,multiline:true'/></td>
                            </tr>
                            <tr>
                                <th>注册证版本</th>
                                <td><input type='text' name='productRegisterVersion' class='easyui-textbox' value="${gspProductRegister.productRegisterVersion}" data-options='required:true'/></td>
                                <th>注册证附件</th>
                                <td style="text-align: left;" colspan="5">
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
    <!--产品查询列表dialog -->
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
    <!--企业信息列表dialog -->
    <div id='ezuiDialogEnterprise' style='padding: 10px;'>
        <div id='ezuiEnterpriseToolbar' class='datagrid-toolbar' style=''>
            <fieldset>
                <legend>企业信息</legend>
                <table>
                    <tr>
                        <th>代码</th>
                        <td><input type='text' id='enterpriseNo' class='easyui-textbox' data-options='width:200'/></td>
                        <th>简称</th>
                        <td><input type='text' id='shorthandName' class='easyui-textbox' data-options='width:200'/></td>
                        <td>
                            <a onclick='doSearchEnterprise();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                            <a onclick='selectEnterprise()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <table id="enterpriseGridDetail">

        </table>
    </div>
    <!--企业信息详情dialog
    <div id="dialogEnterprise">

    </div>-->
</div>
<script>
    var ezuiFormDetail = $("#ezuiFormDetail");
    var ezuiDialogSpec;
    var dataGridProduct;
    var ezuiDialogEnterprise;
    var enterpriseDatagrid;
    var dialogEnterprise; //TODO 替换成企业信息查询通用
    var ezuiDatagridDetail;

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
        if($("#gspProductRegisterId").val()==""){
            $.messager.show({
                msg : "请先添加注册证再绑定产品", title : '<spring:message code="common.message.prompt"/>'
            });
            return;
        }
        ezuiDialogSpec.dialog('open');
    }

    function detailsUnBind(){
        if($("#gspProductRegisterId").val()==""){
            return;
        }
        var selectRows = ezuiDatagridDetail.datagrid("getSelections");
        if(selectRows){
            var arr = selectRows.join(',');
            $.messager.confirm('', '确认要解除产品绑定吗', function(confirm) {
                if(confirm){
                    $.ajax({
                        url : 'gspProductRegisterController.do?unBind',
                        data : {id : arr},
                        type : 'POST',
                        dataType : 'JSON',
                        success : function(result){
                            var msg = '';
                            try {
                                msg = result.msg;
                            } catch (e) {
                                msg = '<spring:message code="common.message.data.delete.failed"/>';
                            } finally {
                                $.messager.show({
                                    msg : msg, title : '<spring:message code="common.message.prompt"/>'
                                });
                                ezuiDatagrid.datagrid('reload');
                            }
                        }
                    });
                }
            });
        }
    }

    $(function () {
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

        ezuiDatagridDetail = $("#ezuiDatagridDetail").datagrid({
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
            queryParams:{'productRegisterId':'${gspProductRegister.productRegisterId}'},
            pagination:true,
            rownumbers:true,
            idField : 'specsId',
            columns : [[
                {field: 'specsId',title:'主键',hidden:true},
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
                $(this).datagrid("resize",{height:520});
            }
        })

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

        enterpriseDatagrid = $("#enterpriseGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagrid',
            method:'POST',
            toolbar : '#ezuiEnterpriseToolbar',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                isUse : '1'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'enterpriseId',
            columns : [[
                {field: 'enterpriseId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: '20%' },
                {field: 'shorthandName',		title: '简称',	width: '20%' },
                {field: 'enterpriseName',		title: '企业名称',	width: '20%' },
                {field: 'enterpriseType',		title: '企业类型',	width: '20%' },
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOper
                }
            ]],
            onDblClickCell: function(index,field,value){
                selectEnterprise();
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

        ezuiDialogEnterprise = $('#ezuiDialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            width:850,
            height:500,
            cache: false,
            cache: false,
            onClose : function() {
                
            }
        }).dialog('close');

        /*dialogEnterprise = $('#dialogEnterprise').dialog({
            modal : true,
            title : '<spring:message code="common.dialog.title"/>',
            fit:true,
            href:sy.bp()+"/gspEnterpriseInfoController.do?toDetail",
            cache: false,
            onClose : function() {

            }
        }).dialog('close');*/
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
                    if(result){
                        $.messager.show({
                            msg : result.msg, title : '<spring:message code="common.message.prompt"/>'
                        });
                        if(result.success){
                            ezuiDialogEnterprise.dialog("close");
                        }
                    }
                }
            });
        }
    }

    $("#enterpriseName").textbox({
        width:110,
        icons:[{
            iconCls:'icon-search',
            handler: function(e){
                //$("#ezuiCustDataDialog #customerid").textbox('clear');
                //ezuiCustDataClick();
                //ezuiCustDataDialogSearch();
            }
        }]
    });
    
    function searchEnterprise() {
        ezuiDialogEnterprise.dialog("open");
    }

    function selectEnterprise() {
        var rows = enterpriseDatagrid.datagrid("getSelected");
        console.log(rows)
        if(rows){
            //TODO 查询企业信息反填
            $("#enterpriseId").val(rows.enterpriseId);
            $("#enterpriseName").val(rows.enterpriseName);
            ezuiDialogEnterprise.dialog("close");
        }
    }

    function operateGrid(id) {
        //dialogEnterprise.dialog("refresh","/gspEnterpriseInfoController.do?toDetail&id="+id).dialog('open');
    }

    function formatOper(value,row,index){
        return "<a onclick=\"operateGrid('"+row.enterpriseId+"')\" class='easyui-linkbutton' data-options='plain:true,iconCls:\"icon-search\"' href='javascript:void(0);'>查看</a>";
    }
</script>
</body>
</html>