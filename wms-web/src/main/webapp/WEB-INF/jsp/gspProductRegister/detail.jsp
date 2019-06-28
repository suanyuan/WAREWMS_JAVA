<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head></head>
<body>
<style>
    .easyui-textbox{
        width: 180px;
    }
</style>
<div class='easyui-layout' data-options='fit:true,border:false'>
    <div data-options='region:"center",border:false' style='overflow: hidden;'>
        <div id='detailToolbar' class='datagrid-toolbar' style='padding: 5px;'>
            <fieldset>
                <legend>产品注册证信息</legend>
                <form id='ezuiForm' method='post'>
                    <input type='hidden' id='gspProductRegisterId' name='gspProductRegisterId' value="${gspProductRegisterId}"/>
                    <table>
                        <!--<tr>
                            <th>注册证编号</th>
                            <td><input type='text' name='productRegisterNo' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>管理分类</th>
                            <td><input type='text' name='classifyId' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>分类目录</th>
                            <td><input type='text' name='classifyCatalog' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>产品名称</th>
                            <td><input type='text' name='productNameMain' class='easyui-textbox'  data-options='required:true'/></td>
                        </tr>
                        <tr>
                            <th>注册人名称</th>
                            <td><input type='text' name='productRegisterName' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>注册人住所</th>
                            <td><input type='text' name='productRegisterAddress' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>生产地址</th>
                            <td><input type='text' name='productProductionAddress' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>代理人名称</th>
                            <td><input type='text' name='agentName' class='easyui-textbox'  data-options='required:true'/></td>
                        </tr>
                        <tr>
                            <th>产品储存条件</th>
                            <td><input type='text' name='storageConditions' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>有效期至</th>
                            <td><input type='text' name='productRegisterExpiryDate' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>有效期</th>
                            <td><input type='text' name='productExpiryDate' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>批准日期</th>
                            <td><input type='text' name='approveDate' class='easyui-textbox'  data-options='required:true'/></td>
                        </tr>
                        <tr>
                            <th>审核人</th>
                            <td><input type='text' name='checkerId' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>审核时间</th>
                            <td><input type='text' name='checkDate' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>创建人</th>
                            <td><input type='text' name='createId' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>创建时间</th>
                            <td><input type='text' name='createDate' class='easyui-textbox'  data-options='required:true'/></td>
                        </tr>
                        <tr>
                            <th>注册证版本</th>
                            <td><input type='text' name='productRegisterVersion' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>其他内容</th>
                            <td><input type='text' name='otherContent' class='easyui-textbox'  data-options='required:true,multiline:true'/></td>
                            <th>编辑人</th>
                            <td><input type='text' name='editId' class='easyui-textbox'  data-options='required:true'/></td>
                            <th>编辑时间</th>
                            <td><input type='text' name='editDate' class='easyui-textbox'  data-options='required:true'/></td>
                        </tr>

                        <tr>
                            <th>预期用途</th>
                            <td colspan="3"><input type='text' name='expectUse' class='easyui-textbox'  style="width: 440px;height: 50px;" data-options='required:true,multiline:true'/></td>
                            <th>适用范围</th>
                            <td colspan="3"><input type='text' name='applyScope' class='easyui-textbox' style="width: 440px;height: 50px;" data-options='required:true,multiline:true'/></td>
                        </tr>-->
                        <tr>
                            <th>主要组成部分</th>
                            <td colspan="3"><input type='text' name='mainPart' class='easyui-textbox' style="width: 440px;height: 50px;"  data-options='required:true,multiline:true'/></td>
                            <th>备注</th>
                            <td colspan="3"><input type='text' name='remark' class='easyui-textbox' style="width: 440px;height: 50px;" data-options='required:true,multiline:true'/></td>
                        </tr>
                        <tr>
                            <th>注册证附件</th>
                            <td style="text-align: left;" colspan="7">
                                <input type='text' name='attachmentUrl'  data-options='required:true'/>
                                <a id="btn" href="#" class="easyui-linkbutton" data-options="">查看</a>
                                <input type="hidden" class="textbox-value" name="attachmentUrl" id="attachmentUrl"/>
                                <a onclick='add();' id='ezuiBtn_add' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>新增</a>
                                <a onclick='edit();' id='ezuiBtn_edit' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'>提交</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </fieldset>
        </div>
        <table id='ezuiDatagridDetail'></table>
    </div>
</div>
<script>
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
        title: '产品信息列表',
        pageSize : 50,
        pageList : [50, 100, 200],
        fit: true,
        border: false,
        fitColumns : false,
        nowrap: true,
        striped: true,
        collapsible:false,
        pagination:true,
        rownumbers:true,
        singleSelect:true,
        idField : 'specsId',
        columns : [[
            {field: 'specsName',title: '规格名称' ,width: '25%'},
            {field: 'productCode',title: '产品代码' ,width: '25%'},
            {field: 'productName',title: '产品名称',width: '25%'},
            {field: 'productModel',title: '产品型号',width: '25%'}
        ]],
        onDblClickCell: function(index,field,value){
            edit();
        },
        onRowContextMenu : function(event, rowIndex, rowData) {

        },
        onSelect: function(rowIndex, rowData) {

        },
        onLoadSuccess:function(data){

        }
    });

</script>
</body>
</html>