<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring'%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type='text/javascript'>


    var ezuiDialog1;
    var enterpriseDialog;
    var dialogUrl1 = "/gspEnterpriseInfoController.do?toDetail";


</script>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspSupplierId' name='gspSupplierId'/>
    <table>
        <tr style="display: none">
            <td><input type="text" id="supplierId" value="${supplierId}"/></td>
        </tr>
        <tr>
            <th>企业</th>
            <td>
                <input type='text' data="1" value="${gspSupplier.enterpriseName}" id='enterpriseIdQuery1' name="enterpriseName" data='1'
                       class='easyui-textbox' data-options='required:true,width:200,editable:false'
                       style="width: 100px;"/>
                <input type="hidden" value="${gspSupplier.enterpriseId}" id="enterpriseId" name="enterpriseId" data='1'/>
                <%--<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>--%>
                <a id="btn" href="javascript:void(0);" class="easyui-linkbutton" data-options=""
                   onclick="viewEnterpriseUrl()">查看</a>

            </td>
            <th>代码</th>
            <td><input type='text' data="1" value="${gspSupplier.enterpriseNo}" id='enterpriseNo' size='20' name="enterpriseNo" class='easyui-textbox'
                       data-options='required:true,width:200' readonly/></td>
        </tr>
        <tr>
            <th>简称</th>
            <td><input type='text' data="1" value="${gspSupplier.shorthandName}" id='shorthandName' size='20' name="shorthandName" class='easyui-textbox'
                       data-options='required:true,width:200' readonly/></td>
            <th>企业类型</th>
            <td><input type="text" data="1" value="${gspSupplier.enterpriseType}" id="enterpriseType1" name="enterpriseType1" class="easyui-combobox"
                       size='16' data-options="panelHeight:'auto',
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
																																]"
                       readonly/></td>

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
            <td><input type='text'  data="1" value="${gspSupplier.contractNo}" id="contractNo" name='contractNo' class='easyui-textbox'
                       data-options='required:true,width:200'/></td>

            <th>货主</th>
            <td><input type='text' data="1" value="${gspSupplier.costomerid}" id='costomerid' name='costomerid' class='easyui-textbox'
                       data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>合同附件</th>
            <td>
                <input type="hidden" class="textbox-value" data="1"  name="contractUrl" id="contractUrl" value="${gspSupplier.contractUrl}"/>
                <%--<input class="textbox-value" data="1" name="contractUrl" id="contractUrl" value=" value="${customer.contractUrl}"/>--%>
                <input id="contractUrlFile" name='file' value="${gspSupplier.contractUrl}"/>
                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl()">查看</a>
            </td>
            <th>授权照片</th>
            <td>
                <input type="hidden" class="textbox-value" data="1" value="${gspSupplier.empowerPhoto}"  name="empowerPhoto" id="empowerPhoto"/>
                <input id="contractUrlFile1" name='file1' value="${gspSupplier.empowerPhoto}"/>
                <a href="javascript:void(0);" class="easyui-linkbutton" data-options="" onclick="viewUrl1()">查看</a>
            </td>
        </tr>
        <tr>
            <th>合同内容</th>
            <td><input type='text'  data="1" value="${gspSupplier.clientContent}" id="clientContent" name='clientContent' class='easyui-textbox'
                       data-options='required:true,width:200,height:80,multiline:true'/></td>
            <th>授权内容</th>
            <td><input type='text'   data="1" value="${gspSupplier.empowerContent}" id="empowerContent" name='empowerContent' class='easyui-textbox'
                       data-options='required:true,width:200,height:80,multiline:true'/></td>

        </tr>
        <tr>
            <th>合同开始时间</th>
            <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.clientStartDate}"/>"   id="clientStartDate"  name='clientStartDate' class='easyui-datebox'
                       data-options='required:true,width:200'/></td>
            <th>授权开始时间</th>
            <td><input type='text' data="1"   value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.empowerStartdate}"/>"  id="empowerStartdate" name='empowerStartdate' class='easyui-datebox'
                       data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>合同结束时间</th>
            <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.clientEndDate}"/>" id="clientEndDate" name='clientEndDate' class='easyui-datebox'
                       data-options='required:true,width:200'/></td>
            <th>授权结束时间</th>
            <td><input type='text' data="1" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${gspSupplier.empowerEnddate}"/>" id="empowerEnddate" name='empowerEnddate' class='easyui-datebox'
                       data-options='required:false,width:200'/></td>

        </tr>
        <tr>
            <th>合同期限</th>
            <td><input type='text' data="1" value="${gspSupplier.clientTerm}" id="clientTerm"  name='clientTerm' class='easyui-numberbox'
                       data-options='width:200'/></td>
            <th>授权单位</th>
            <td><input type='text' data="1" value="${gspSupplier.empowerUnit}"  id='empowerUnit' name='empowerUnit' class='easyui-textbox'
                       data-options='required:false,width:200'/></td>

        </tr>


        <tr>
            <th>是否审查</th>
            <td><input type="text" data="1" id="isCheck" name="isCheck" value="${isCheck}" class="easyui-combobox"
                       size='16' data-options="panelHeight:'auto',
                                                                                                                                    required:true,
                                                                                                                                    width:200,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/>
            </td>
        </tr>


        <tr>
            <th>创建人</th>
            <td><input type='text' data="1" id="createId" name='createId' value="${createId}" class='easyui-textbox'
                       size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>创建时间</th>
            <td><input type='text' data="1" id="createDate" name='createDate' value="${createDate}"
                       class='easyui-textbox' size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>编辑人</th>
            <td><input type='text' data="1" id="editId" name='editId' value="${createId}" class='easyui-textbox'
                       size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text' data="1" id="editDate" name='editDate' value="${createDate}" class='easyui-textbox'
                       size='16' data-options='required:true,width:200'/></td>
        </tr>
        <tr>
            <%--<th>是否有效</th>--%>
            <th>是否有效</th>
            <td><input type="text" data="1" id="isUse" name="isUse" value="${isUse}" class="easyui-combobox" size='16'
                       data-options="panelHeight:'auto',
                                                                                                                                    width:200,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/>
            </td>
        </tr>
    </table>
</form>
<%--<div id='ezuiDialogBtn1'>--%>
<%--&lt;%&ndash;<a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'>提交企业信息</a>&ndash;%&gt;--%>
<%--<a onclick='ezuiDialogClose("#ezuiDialog1");' class='easyui-linkbutton' href='javascript:void(0);'>关闭</a>--%>
<%--</div>--%>
<div id='ezuiDialog1' style='padding: 10px;'>

</div>

<div id='enterpriseDialog' style='padding: 10px;'>

</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    var enterpriseDialog_gspSupplierInfo;


    $(function () {
        $("#enterpriseIdQuery1").textbox({
            width: 200,
            icons: [{
                iconCls: 'icon-search',
                handler: function (e) {
                    searchEnterprise();
                }
            }]
        })
    })

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

    var doSearchEnterprise = function () {
        // console.log($("#enterpriseSearchGridToolbar_gspSupplierInfo input[id='enterpriseNo']").textbox("getValue"));
        // console.log($('#shorthandName').val());
        // console.log($('#shorthandName').textbox('getValue'));
        enterpriseSearchGrid_gspSupplierInfo.datagrid('load', {
            enterpriseNo: $("#enterpriseSearchGridToolbar_gspSupplierInfo input[id='enterpriseNo']").textbox("getValue"),
            shorthandName: $("#enterpriseSearchGridToolbar_gspSupplierInfo input[id='shorthandName']").textbox("getValue"),
            type: 'noSupplier',
            enterpriseType: 'default',
            isUse: '1'
        });
    };



    $(function () {
        $('#clientStartDate').datebox({
            onChange: function (date) {
                onChangeDate();
            }
        });
        $('#clientEndDate').datebox({
            onChange: function (date) {
                onChangeDate();
            }
        });

        $('#contractUrlFile').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            required: true,
            onChange: function (data) {

                if (data) {
                    doUpload(data);
                }
            }
        });
        $('#contractUrlFile1').filebox({
            prompt: '选择一个文件',//文本说明文件
            width: '200', //文本宽度
            buttonText: '浏览',  //按钮说明文字
            //required: true,
            onChange: function (data) {
                if (data) {
                    doUpload1(data);
                }
            }
        });

        function doUpload(data) {
            var ajaxFile = new uploadFile({
                "url": sy.bp() + "/commonController.do?uploadFileLocal",
                "dataType": "json",
                "timeout": 50000,
                "async": true,
                "data": {
                    //多文件
                    "file": {
                        //file为name字段 后台可以通过$_FILES["file"]获得
                        "file": document.getElementsByName("file")[0].files[0]//文件数组
                    }
                },
                onload: function (data) {
                    $("#contractUrl").val(data.comment);
                },
                onerror: function (er) {
                    // console.log(er);
                }
            });
        };

        function doUpload1(data) {
            var ajaxFile = new uploadFile({
                "url": sy.bp() + "/commonController.do?uploadFileLocal",
                "dataType": "json",
                "timeout": 50000,
                "async": true,
                "data": {
                    //多文件
                    "file": {
                        //file为name字段 后台可以通过$_FILES["file"]获得
                        "file": document.getElementsByName("file1")[0].files[0]//文件数组
                    }
                },
                onload: function (data) {
                    $("#empowerPhoto").val(data.comment);
                },
                onerror: function (er) {
                    console.log(er);
                }
            });
        }

    });

    function viewUrl(url) {
        if (url) {
            showUrl(url);
        } else {
            if ($("#contractUrl").val() != "") {
                showUrl($("#contractUrl").val());
            } else {
                showMsg("请上传合同附件！");
            }
        }
    }

    function viewUrl1(url) {
        if (url) {
            showUrl(url);
        } else {
            if ($("#empowerPhoto").val() != "") {
                showUrl($("#empowerPhoto").val());
            } else {
                showMsg("请上传授权照片！");
            }
        }
    }


    function viewEnterpriseUrl() {
        $(function () {
            ezuiDialog1 = $('#ezuiDialog1').dialog({
                modal: true,
                title: '<spring:message code="common.dialog.title"/>',
                buttons: '',
                href: dialogUrl1,
                width: 1200,
                height: 530,
                closable: true,
                cache: false,
                onClose: function () {
                    ezuiFormClear(ezuiDialog1);
                },


            }).dialog('close');
        })
        //processType = 'add';

        //var row = ezuiDatagrid.datagrid('getSelected');
        console.log($("#ezuiFormInfo input[id='enterpriseId']").val());
        var enterpriseId = $("#ezuiFormInfo input[id='enterpriseId']").val();
        if (enterpriseId == null || enterpriseId == "") {
            enterpriseId = $("#enterpriseId").val();
        }

        if (enterpriseId != null && enterpriseId != "") {
            ezuiDialog1.dialog('refresh', dialogUrl1 + "&id=" + enterpriseId).dialog('open');
        } else {
            $.messager.show({
                msg: '请先选择企业', title: '提示'
            });
        }

    }


    function onChangeDate() {
        var startTime = $("#clientStartDate").datebox('getValue');
        var endTime = $("#clientEndDate").datebox('getValue');
        if (startTime != null && endTime != null && endTime != "" && startTime != "") {
            var endTime = new Date(endTime);
            var startTime = new Date(startTime);
            var days = endTime.getTime() - startTime.getTime();
            var day = parseInt(days / (1000 * 60 * 60 * 24));
            $("#clientTerm").numberbox('setValue', day);
        }
    }
</script>
