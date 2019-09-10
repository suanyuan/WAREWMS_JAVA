<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>


<script type='text/javascript'>


var  ezuiDialogSupplier;
var enterpriseDialog;
var dialogUrl1 = "/gspEnterpriseInfoController.do?toDetail";


</script>
<%--<c:import url='/WEB-INF/jsp/include/meta.jsp' />--%>
<%--<c:import url='/WEB-INF/jsp/include/easyui.jsp' />--%>
<form id='ezuiFormSupInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId1" value="${supplierId}"/></td>
        </tr>
        <tr><th>企业</th>
            <td>
                <input type='text' data="1" id='enterpriseIdQuery1' value="${gspSupplier.enterpriseName}"  name="enterpriseName"  class='easyui-textbox' data-options='required:true,width:200,editable:false'  style="width: 100px;"/>
                <input type="hidden"  id="enterpriseId" name="enterpriseId" value="${gspSupplier.enterpriseId}" data='1'  />
                <%--<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>--%>
                <a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewSupplierEnterpriseUrl()">查看</a>

            </td>
            <th>代码</th><td><input type='text' data="1" id='enterpriseNo' value="${gspSupplier.enterpriseNo}" size='20' name="enterpriseNo" class='easyui-textbox' data-options='required:true,width:200' readonly/></td>
        </tr>
        <tr><th>简称</th><td><input type='text' data="1" id='shorthandName' value="${gspSupplier.shorthandName}" size='20' name="shorthandName" class='easyui-textbox' data-options='required:true,width:200' readonly/></td>
            <th>企业类型</th><td><input type="text" data="1" id="enterpriseType1" value="${gspSupplier.enterpriseType}"  name="enterpriseType1"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
                                                                                                                                    required:true,
                                                                                                                                    width:200,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: 'JY', value: '经营'},
																																	{id: 'SCJY', value: '生产经营'},
																																	{id: 'KD', value: '快递'},
																																	{id: 'YL', value: '医疗机构'},
																																	{id: 'GNSC', value: '生产'}
																																]" readonly/></td>

        </tr>
        <%--<tr>--%>

            <%--&lt;%&ndash;<th>企业类型</th>&ndash;%&gt;--%>
            <%--&lt;%&ndash;<td><input type='text' data="1" id="operateType" name='operateType' class='easyui-textbox' size='16' data-options='required:true'/></td>&ndash;%&gt;--%>

        <%--</tr>--%>


        <%--<tr>--%>
            <%--<th>企业</th>--%>
            <%--<td><input type='text' data="1" id="enterpriseId" name='enterpriseId' class='easyui-textbox' size='16' data-options='required:true'/></td>--%>
        <%--</tr>--%>


        <tr>
            <th>合同编号</th>
            <td><input type='text'  data="1" id="contractNo" value="${gspSupplier.contractNo}"  name='contractNo' class='easyui-textbox' data-options='required:false,width:200'/></td>

            <th>货主</th>
            <td><input type='text' data="1" id='costomerid' name='costomerid' value="${gspSupplier.costomerid}" class='easyui-textbox' data-options='required:true,width:200'/>
            <input type="hidden" name="cli_enterpriseId" id="cli_enterpriseId" value="${clientEnterpriseId}" />
            <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewClientEnterpriseUrl()">查看</a>
            </td>

        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" data="1" name="contractUrl" id="contractUrl" value="${gspSupplier.contractUrl}"  />
                <%--<input class="textbox-value" data="1" name="contractUrl" id="contractUrl" value=" value="${customer.contractUrl}"/>--%>
                <input id="contractUrlFile" name='file'  value="${gspSupplier.contractUrl}"  />
                <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
            </td>
            <th>授权照片</th>
            <td>
                <input type="hidden" class="textbox-value" data="1" name="empowerPhoto" id="empowerPhoto" value="${gspSupplier.contractUrl}" />
                <input id="contractUrlFile1" name='file1' value="${gspSupplier.empowerPhoto}"  />
                <a  href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl1()">查看</a>
            </td>
        </tr>
        <tr>
            <th>合同内容</th>
            <td><input type='text'  data="1" value="${gspSupplier.clientContent}" id="clientContent" name='clientContent' class='easyui-textbox' data-options='required:false,width:200,height:80,multiline:true'/></td>
            <th>授权内容</th>
            <td><input type='text'  data="1" value="${gspSupplier.empowerContent}" id="empowerContent" name='empowerContent' class='easyui-textbox' data-options='required:false,width:200,height:80,multiline:true'/></td>

        </tr>
        <tr>
            <th>合同开始时间</th>
            <td><input type='text' data="1"  id="clientStartDate"  value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.clientStartDate}"/>" name='clientStartDate' class='easyui-datebox' data-options='required:false,width:200'/></td>
            <th>授权开始时间</th>
            <td><input type='text' data="1" id="empowerStartdate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.empowerStartdate}"/>" name='empowerStartdate' class='easyui-datebox' data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>合同结束时间</th>
            <td><input type='text' data="1" id="clientEndDate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.clientEndDate}"/>"  name='clientEndDate' class='easyui-datebox' data-options='required:false,width:200'/></td>
            <th>授权结束时间</th>
            <td><input type='text' data="1" id="empowerEnddate" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.empowerEnddate}"/>" name='empowerEnddate' class='easyui-datebox' data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>合同期限</th>
            <td><input type='text' data="1" id="clientTerm" value="${gspSupplier.clientTerm}" name='clientTerm' class='easyui-numberbox' data-options='width:200'/></td>
            <th>授权单位</th>
            <td><input type='text' data="1" id='empowerUnit' value="${gspSupplier.empowerUnit}" name='empowerUnit' class='easyui-textbox' data-options='required:false,width:200'/></td>

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
<%--<div id='ezuiDialogBtn1'>--%>
    <%--&lt;%&ndash;<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'>提交企业信息</a>&ndash;%&gt;--%>
    <%--<a onclick='ezuiDialogClose("#ezuiDialogSupplier");' class='easyui-linkbutton' href='javascript:void(0);'>关闭</a>--%>
<%--</div>--%>
<div id='ezuiDialogSupplier' style='padding: 10px;'>

</div>

<div id='enterpriseDialog' style='padding: 10px;'>

</div>
<div id='ezuiDialogClientDetail' style='padding: 10px;display: none'>
    <div id='clientTB' class='datagrid-toolbar' style=''>
        <fieldset>
            <legend>货主信息</legend>
            <table>
                <tr>
                    <th>客户代码：</th><td><input type='text' id='kehudaima1' class='easyui-textbox' data-options=''/></td>
                    <th>客户名称：</th><td><input type='text' id='kehumingcehng1' class='easyui-textbox' data-options=''/></td>
                </tr>
                <tr>
                    <td>
                        <a onclick='doSearchClient();' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>查询</a>
                        <a onclick='choseClientSelect()' class='easyui-linkbutton' data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>选择</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </div>
    <table id="dataGridClientDetail">

    </table>
</div>
<div id="ezuiDialogClientEnterpriseInfo">

</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var enterpriseDialog_gspSupplierInfo;
    var ezuiDialogClientDetail;
    var ezuiDialogClientEnterpriseInfo;
    $(function(){
        var row = ezuiDatagrid.datagrid('getSelected');
        // alert(row.supplierId);
        // alert(11111);
        // debugger


        if(row){

            if(processType == 'edit'){
            // $.ajax({
            //     url : 'gspSupplierController.do?getInfo',
            //     data : {"supplierId" : row.supplierId},
            //     type : 'POST',
            //     dataType : 'JSON',
            //     success : function(result){
            //         if(result.success){
            //             $("#ezuiFormInfo input[id!=''][data='1']").each(function (index) {
            //                 if($(this).attr("class")){
            //                     if($(this).attr("class").indexOf('easyui-textbox')!=-1){
            //                         $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""]);
            //                         //gspBusinessFrom[""+$(this).attr("id")+""] = $(this).textbox("getValue");
            //                     }else if($(this).attr("class").indexOf('easyui-combobox')!=-1){
            //                         $(this).combobox("setValue",result.obj[""+$(this).attr("id")+""]);
            //                     }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
            //                         $(this).datebox("setValue",result.obj[""+$(this).attr("id")+""]);
            //                     }
            //                 }
            //
            //
            //             })
            //             console.log(result.obj.enterpriseName);
            //
            //             console.log(result.obj.empowerPhoto);
            //             console.log(result.obj.contractUrl);
            //             //$("#ezuiFormInfo input[id='createDate'][data='1']").textbox('setValue',result.obj.createDate);
            //             $("#enterpriseId").val(result.obj.enterpriseId);
            //             $("#ezuiFormInfo input[id='editDate'][data='1']").textbox('setValue',result.obj.editDate);
            //
            //             // $("#ezuiFormInfo input[id='enterpriseIdQuery1'][data='1']").textbox('setValue',result.obj.enterpriseName);
            //
            //             $("#enterpriseType1").combobox('setValue',result.obj.enterpriseType);
            //             $("#empowerPhoto").val(result.obj.empowerPhoto);
            //             $("#contractUrlFile1").val('setValue',result.obj.empowerPhoto);
            //             $("#contractUrl").val(result.obj.contractUrl);
            //             // $("#ezuiFormInfo input[id='contractUrlFile']").textbox('setValue',result.obj.contractUrl);
            //             // $("#contractUrlFile").val(result.obj.contractUrl);
            //             // $("#contractUrlFile").val(result.obj.contractUrl);
            //             // $("#empowerPhoto").val(result.obj.empowerPhoto);
            //             // $("#contractUrlFile1").val(result.obj.empowerPhoto);
            //             //$("#ezuiFormInfo input[id='contractUrl']").textbox('setValue',result.obj.contractUrl);
            //
            //
            //             //$("#ezuiFormInfo input[id='contractUrl']").val('setValue',result.obj.contractUrl);
            //             //$("#ezuiFormInfo input[id='clientStartDate'][data='1']").datebox('setValue',result.obj.clientStartDate);
            //             //$("#ezuiFormInfo input[id='clientEndDate'][data='1']").datebox('setValue',result.obj.clientEndDate);
            //             //$("#contractUrl").val(result.obj.contractUrl);
            //             //$("#contractUrl").val(result.obj.enterpriseType);
            //             //$("#enterpriseIdQuery1").textbox("setValue",result.Obj.enterpriseName);
            //         }
            //     }
            // });
            }
        }

        //
        // $('#packingUnit').combobox({
        //     url:sy.bp()+'/basPackageController.do?getCombobox',
        //     valueField:'value',
        //     textField:'value'
        // });

    });
    $(function () {
        $("#ezuiFormSupInfo #enterpriseIdQuery1").textbox({
            width:200,
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        });

        $("#ezuiFormSupInfo #costomerid").textbox({
            width: 200,
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    searchclient(); //货主
                }
            }]
        });

        //货主列表
        clientDatagrid = $("#dataGridClientDetail").datagrid({
            url : sy.bp()+'/basCustomerController.do?showDatagrid',
            method:'POST',
            toolbar : '#clientTB',
            title: '',
            pageSize : 50,
            pageList : [50, 100, 200],
            border: false,
            fitColumns : false,
            nowrap: true,
            striped: true,
            queryParams:{
                activeFlag : '1',
                customerType:'OW'
            },
            fit:true,
            collapsible:false,
            pagination:true,
            rownumbers:true,
            singleSelect:true,
            idField : 'clientId',
            columns : [[
                {field: 'customerType',	title: '客户类型 ',	width: '10%',formatter:function(value,rowData,rowIndex){
                        if (rowData.customerType=='CO') {
                            return rowData.customerType='收货单位';
                        }else if (rowData.customerType=='VE'){
                            return rowData.customerType='供应商';
                        }else if (rowData.customerType=='CA'){
                            return rowData.customerType='承运商';
                        }else if (rowData.customerType=='OT'){
                            return rowData.customerType='其他';
                        }else if (rowData.customerType=='OW'){
                            return rowData.customerType='货主';
                        }else if (rowData.customerType=='PR'){
                            return rowData.customerType='生产企业';
                        }else if (rowData.customerType=='WH'){
                            return rowData.customerType='主体';
                        }
                    } },
                // {field: 'activeFlag',		title: '是否合作 ',	width: '10%',formatter:function(value,rowData,rowIndex){
                //         return rowData.activeFlag == '1' ? '是' : '否';
                //     }},
                {field: 'enterpriseId',		title: '主键',	width: '10%' ,hidden:true},
                {field: 'customerid',		title: '客户代码',	width: '10%' },
                {field: 'descrC',		title: '客户名称',	width: '30%' },
                {field: 'enterpriseNo',		title: '企业信息代码 ',	width: '10%' },
                {field: 'shorthandName',		title: '简称 ',	width:'10%' },
                {field: 'enterpriseName',		title: '企业名称 ',	width: '10%' },
                // {field: '_operate',		title: '操作',	width: '10%',
                //     formatter: formatOper
                // }
            ]],
            onDblClickCell: function(index,field,value){
                choseClientSelect();
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

    });
    //货主弹窗
    ezuiDialogClientDetail = $('#ezuiDialogClientDetail').dialog({
        modal : true,
        title : '<spring:message code="common.dialog.title"/>',
        width:850,
        height:500,
        cache: false,

        onClose : function() {
            ezuiFormClear(ezuiForm);
        }
    }).dialog('close');

    function searchclient() {
        if(ezuiDialogClientDetail){
            ezuiDialogClientDetail.dialog('open');
        }
    }
    //查询货主信息条件
    function doSearchClient() {
        console.log($('#kehumingcehng1').val());
        console.log($('#kehudaimaC').val());

        clientDatagrid.datagrid('load', {
            // enterpriseName : $('#qiyemingcheng2').val(),
            // enterpriseNo : $('#qiyexinxidaima1').val(),

            descrC : $('#kehumingcehng1').val(),
            customerid : $('#ezuiDialogClientDetail #kehudaimaC').val(),
            isUse : '1',
            customerType:'OW',
            activeFlag : '1',

        });
    }
    //选择货主
    function choseClientSelect() {
        var row = clientDatagrid.datagrid("getSelected");
        // alert(row.enterpriseId);
        if(row){
            $("#ezuiFormSupInfo #costomerid").textbox("setValue",row.customerid);
            $("#ezuiFormSupInfo #cli_enterpriseId").val(row.enterpriseId);
            ezuiDialogClientDetail.dialog('close');
        }
    }
    //委托方企业信息详情
    function viewClientEnterpriseUrl(){
        var enterpriseId = $("#ezuiFormSupInfo #cli_enterpriseId").val();
        enterpriseInfo(enterpriseId);
    }
    //供应商企业信息详情
    function viewSupplierEnterpriseUrl() {
        var enterpriseId = $("#ezuiFormSupInfo #enterpriseId").val();
        enterpriseInfo(enterpriseId);
    }

    //供应商列表
    enterpriseDialog_gspSupplierInfo = $('#enterpriseDialog').dialog({
        modal: true,
        title: '<spring:message code="common.dialog.title"/>',
        href: sy.bp() + "/gspEnterpriseInfoController.do?toSearchDialog&target=gspSupplierInfo&&type=noSupplier&enterpriseType=default",
        width: 850,
        height: 500,
        cache: false,
        onClose: function () {

        }
    }).dialog('close');

    function searchEnterprise() {
        enterpriseDialog_gspSupplierInfo = $('#enterpriseDialog').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            // href: sy.bp() + "/gspEnterpriseInfoController.do?toSearchDialog&target=gspSupplier&type=noSupplier&enterpriseType=default",
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {

            }
        })
    }
    var doSearchEnterprise = function(){
        // console.log($("#enterpriseSearchGridToolbar_gspSupplierInfo input[id='enterpriseNo']").textbox("getValue"));
        // console.log($('#shorthandName').val());
        // console.log($('#shorthandName').textbox('getValue'));
        enterpriseSearchGrid_gspSupplierInfo.datagrid('load', {
            enterpriseNo : $("#enterpriseSearchGridToolbar_gspSupplierInfo input[id='enterpriseNo']").textbox("getValue"),
            shorthandName:$("#enterpriseSearchGridToolbar_gspSupplierInfo input[id='shorthandName']").textbox("getValue"),
            type :'noSupplier',
            enterpriseType:'default',
            isUse : '1'
        });
    };

    function choseSelect_gspSupplierInfo(id,name) {
        //console.log(1111111)
        //console.log(name)
        var enterpriceId;
        enterpriceId = id;
        //$("input[name='enterpriseId'][data='1']").val(id);

        $("#ezuiFormSupInfo input[id='enterpriseId']").val(id);
        console.log($("#ezuiFormSupInfo input[id='enterpriseId']").val());
        $("#ezuiFormSupInfo #enterpriseIdQuery1").textbox("setValue",name);

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
                    $("#ezuiFormSupInfo input[id='enterpriseNo'][data='1']").textbox('setValue',date.obj.enterpriseNo);
                    $("#ezuiFormSupInfo input[id='shorthandName'][data='1']").textbox('setValue',date.obj.shorthandName);

                    //$(this).textbox("setValue",result.obj[""+$(this).attr("id")+""]);
                    $("#ezuiFormSupInfo input[id='enterpriseType1'][data='1']").combobox('setValue',date.obj.enterpriseType);

                }
            }
        });
        //console.log(4444444444)
        //$("input[name='enterpriseId1']").val(id);
        enterpriseDialog_gspSupplierInfo.dialog("close");
    }




    $(function () {
        $('#ezuiFormSupInfo #clientStartDate').datebox({
            onChange: function(date){
                onChangeDate();
            }
        });
        $('#ezuiFormSupInfo #clientEndDate').datebox({
            onChange: function(date){
                onChangeDate();
            }
        });

        $('#ezuiFormSupInfo #contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: false,
            onChange:function(data){

                if(data){
                    doUploadContractUrlFile(data);
                }
            }
        });
        $('#ezuiFormSupInfo #contractUrlFile1').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            //required: true,
            onChange:function(data){
                if(data){
                    doUploadContractUrlFile1(data);
                }
            }
        });
        function doUploadContractUrlFile(data) {
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
                    $("#ezuiFormSupInfo #contractUrl").val(data.comment);
                },
                onerror:function(er){
                    // console.log(er);
                }
            });
        };
        function doUploadContractUrlFile1(data) {
            var ajaxFile = new uploadFile({
                "url":sy.bp()+"/commonController.do?uploadFileLocal",
                "dataType":"json",
                "timeout":50000,
                "async":true,
                "data":{
                    //多文件
                    "file":{
                        //file为name字段 后台可以通过$_FILES["file"]获得
                        "file":document.getElementsByName("file1")[0].files[0]//文件数组
                    }
                },
                onload:function(data){
                    $("#ezuiFormSupInfo #empowerPhoto").val(data.comment);
                },
                onerror:function(er){
                    console.log(er);
                }
            });
        }

    });

    function viewUrl(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormSupInfo #contractUrl").val()!=""){
                showUrl($("#ezuiFormSupInfo #contractUrl").val());
            }else {
                showMsg("请上传合同附件！");
            }
        }
    }
    function viewUrl1(url) {
        if(url){
            showUrl(url);
        }else{
            if($("#ezuiFormSupInfo #empowerPhoto").val()!=""){
                showUrl($("#ezuiFormSupInfo #empowerPhoto").val());
            }else {
                showMsg("请上传授权照片！");
            }
        }
    }







    function onChangeDate() {
        var startTime = $("#ezuiFormSupInfo #clientStartDate").datebox('getValue');
        var endTime =$("#ezuiFormSupInfo #clientEndDate").datebox('getValue') ;
        if(startTime!=null && endTime!=null&& endTime!=""&& startTime !=""  ) {
            var endTime = new Date(endTime);
            var startTime = new Date(startTime);
            var days = endTime.getTime() - startTime.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24));
            $("#ezuiFormSupInfo #clientTerm").numberbox('setValue',day);
        }
    }
</script>
