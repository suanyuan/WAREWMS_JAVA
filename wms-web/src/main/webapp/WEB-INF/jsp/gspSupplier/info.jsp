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
        <tr><th>代码</th><td><input type='text' data="1" id='enterpriseNo' size='16' name="enterpriseNo" class='easyui-textbox' data-options='' readonly/></td></tr>
        <tr><th>简称</th><td><input type='text' data="1" id='shorthandName' size='16' name="shorthandName" class='easyui-textbox' data-options='' readonly/></td></tr>
        <tr>

            <%--<th>企业类型</th>--%>
            <%--<td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
            <th>企业类型</th><td><input type="text" data="1" id="enterpriseType"  name="enterpriseType"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'JY', value: '经营'},
																																	{id: 'SC', value: '生产'}
																																]"/></td>
        </tr>


        <%--<tr>--%>
            <%--<th>企业</th>--%>
            <%--<td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>


        <tr>
            <th>合同编号</th>
            <td><input type='text' value="${customer.contractNo}" name='contractNo' class='easyui-textbox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" name="contractUrl" id="contractUrl" value=" value="${customer.contractUrl}"/>
                <input id="contractUrlFile" name='file' value="${customer.contractUrl}">
                <a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
            </td>
        </tr>
        <tr>
            <th>委托内容</th>
            <td><input type='text' value="${customer.clientContent}" name='clientContent' class='easyui-textbox' data-options='required:true,width:200,height:80,multiline:true'/></td>
        </tr>
        <tr>
            <th>委托开始时间</th>
            <td><input type='text'  name='clientStartDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>委托结束时间</th>
            <td><input type='text'  name='clientEndDate' class='easyui-datebox' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>委托期限</th>
            <td><input type='text' value="${customer.clientTerm}" name='clientTerm' class='easyui-numberbox' data-options='required:true,width:200'/></td>
        </tr>



        <tr>
            <th>是否审查</th>
            <td><input type="text" data="1" id="isCheck"  name="isCheck" value="${isCheck}"   class="easyui-combobox" size='16' data-options="panelHeight:'auto',
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
                        console.log(result.obj.enterpriseType);
                        console.log(result.obj.operateType);
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
            width:135,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        })
    })

    // $(function () {
    //
    //
    //         if(processType == 'add'){
    //            // alert(1111);
    //             $("#ezuiFormInfo input[id='isCheck'][data='1']").combobox('setValue','1');
    //
    //            // $("#ezuiFormInfo input[id='isCheck'][data='1']").textbox('setValue',"1");
    //             //$("#ezuiFormInfo input[id='isCheck'][data='1']").combobox('setValue',"1");
    //
    //         }
    //
    //
    //
    // })
    $(function () {
        $("#enterpriseName").textbox({
            value:"${customer.clientName}",
            width:200,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        })

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
                    console.log(er);
                }
            });
        }

        $('input[name="firstState"]').combobox({
            url:sy.bp()+'/commonController.do?getCatalogFirstState',
            valueField:'id',
            textField:'value'
        });

        /*$('#isCheckData').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#isCheckData').combobox("setValue",'${customer.isCheck}')
            }
        });

        $('#isCooperationData').combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#isCooperationData').combobox("setValue",'${customer.isCooperation}')
            }
        });*/

        $("#isChineseLabelData").combobox({
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#isChineseLabelData').combobox("setValue",'${customer.isChineseLabel}')
            }
        });

        $('#operateTypeData').combobox({
            url:sy.bp()+'/commonController.do?getEntType',
            valueField:'id',
            textField:'value',
            width:200,
            onLoadSuccess:function () {
                $('#operateTypeData').combobox("setValue",'${customer.operateType}')
            }
        })

        enterpriseDatagrid = $("#dataGridDetail").datagrid({
            url : sy.bp()+'/gspEnterpriseInfoController.do?showDatagridSearch',
            method:'POST',
            toolbar : '#detailToolbar',
            title: '',
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
                {field: 'enterpriseId',		title: '主键',	width: 0 ,hidden:true},
                {field: 'enterpriseNo',		title: '企业信息代码',	width: '20%' },
                {field: 'shorthandName',		title: '简称',	width: '20%' },
                {field: 'enterpriseName',		title: '企业名称',	width: '20%' },
                {field: 'enterpriseType',		title: '企业类型',	width: '20%' ,formatter:entTypeFormatter},
                {field: '_operate',		title: '操作',	width: '20%',
                    formatter: formatOper
                }
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
        })

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
                ezuiFormClear(ezuiForm);
            }
        }).dialog('close');

    })


</script>
