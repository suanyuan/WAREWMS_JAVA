<%@ page language='java' pageEncoding='UTF-8' %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>
<%@ taglib uri='http://www.springframework.org/tags' prefix='spring' %>
<style>
    table th {
        text-align: right;
    }
</style>
<div id='ezuiDialog' class='easyui-dialog' style='padding: 10px;'>
    <form id='ezuiForm' method='post'>
        <input type='hidden' id='docAsnHeaderId' name='docAsnHeaderId'/>
        <fieldset>
            <legend>入库单头</legend>
            <table>
                <tr>
                    <th>货主代码</th>
                    <td><input type='text' id='customerid' name='customerid' class='easyui-textbox' size='16'
                               data-options='required:true'/></td>
                    <th>入库单编号</th>
                    <td><input type='text' id='asnno1' name='asnno1' class='easyui-textbox' size='16'
                               data-options='required:false'/></td>
                    <th>客户单号</th>
                    <td><input type='text' id='asnreference1' name='asnreference1' class='easyui-textbox' size='16'
                               data-options='required:false'/></td>
                    <th>参考编号2</th>
                    <td><input type='text' id='asnreference2' name='asnreference2' class='easyui-textbox' size='16'
                               data-options='required:false'/></td>
                </tr>
                <tr>
                    <!----><th>供应商</th>
                    <td><input type='text' id='supplierid' name='supplierid' class='easyui-combobox' size='16'
                               data-options="panelHeight: 'auto',
																														editable: false,
																														url:'<c:url value="commonController.do?getSupplier"/>',
																														valueField: 'id',
																														textField: 'value'"/>
                    </td>

                    <th>入库单状态</th>
                    <td><input type='text' id='asnstatus' name='asnstatus' class='easyui-combobox' size='16'
                               data-options="panelHeight: 'auto',
																														editable: false,
																														url:'<c:url value="/docAsnHeaderController.do?getAsnstatusCombobox"/>',
																														valueField: 'id',
																														textField: 'value'"/>
                    </td>
                    <th>释放状态</th>
                    <td><input type='text' id='releasestatus' name='releasestatus' class='easyui-combobox' size='16'
                               data-options="panelHeight: 'auto',
																												editable: false,
																												url:'<c:url value="/docAsnHeaderController.do?getReleasestatusCombobox"/>',
																												valueField: 'id',
																												textField: 'value'"/>
                    </td>
                    <th>入库单类型</th>
                    <td><input type='text' id='asntype' name='asntype' class='easyui-combobox' size='16' data-options="panelHeight: 'auto',
																											editable: false,
																											required:true,
																											url:'<c:url value="/docAsnHeaderController.do?getAsnTypeCombobox"/>',
																											valueField: 'id',
																											textField: 'value'"/>
                    </td>
                </tr>
                <tr>
                    <th>预计收货时间</th>
                    <td><input type='text' id='expectedarrivetime1' name='expectedarrivetime1' class='easyui-datebox'
                               size='16' data-options='required:true'/></td>
                    <th>订单创建时间</th>
                    <td><input type='text' id='addtime' name='addtime' class='easyui-datetimebox' size='16'
                               data-options='editable: false'/></td>
                </tr>
                <tr>
                    <th>备注</th>
                    <td colspan="7"><input type='text' id='notes' name='notes' class='easyui-textbox' size='90'
                                           data-options='multiline:true,height:30'/></td>
                </tr>
                <tr>
                    <th>随车温度</th>
                    <td><input type='text' id='userdefine1' name='userdefine1' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <th>上架单号</th>
                    <td><input type='text' id='userdefine2' name='userdefine2' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <th>验收单号</th>
                    <td><input type='text' id='userdefine3' name='userdefine3' class='easyui-textbox' size='16'
                               data-options=''/></td>
                </tr>
                <tr>
                    <th>温度记录表</th>
                    <td colspan="2">
						<input type='text' id='coldTag' name="coldTag" size='16' data-options=''/>
						<input type="hidden" name="userdefine4"/>
						<a id="btn" href="javascript:void(0)" class="easyui-linkbutton" data-options="" onclick="viewUrlColdTag()">查看</a>
					</td>
                    <th>用户自定义5</th>
                    <td><input type='text' id='userdefine5' name='userdefine5' class='easyui-textbox' size='16'
                               data-options=''/></td>
                    <td colspan="2">
                        <a onclick='renew();' id='ezuiBtn_renew' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>新增</a>
                        <a onclick='commit();' id='ezuiBtn_recommit' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-save"' href='javascript:void(0);'><spring:message
                                code='common.button.commit'/></a>
                        <a onclick='copyDetail();' id='ezuiBtn_copyDetail' class='easyui-linkbutton'
                           data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'>明细复用</a>
                    </td>
                </tr>
            </table>
        </fieldset>
    </form>
    <div class="easyui-tabs" style="width:950px;height:200px">
        <div title="入库明细" style="padding:3px">
            <table id='ezuiDetailsDatagrid'></table>
        </div>
        <div title="收货明细" style="padding:0px">
            <table id='asnDetailsDatagrid'></table>
        </div>
    </div>
    <form>
        <div>
            <a onclick='detailsAdd();' id='ezuiDetailsBtn_add' class='easyui-linkbutton'
               data-options='plain:true,iconCls:"icon-add"' href='javascript:void(0);'><spring:message
                    code='common.button.skuAdd'/></a>
            <a onclick='detailsEdit();' id='ezuiDetailsBtn_edit' class='easyui-linkbutton'
               data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'><spring:message
                    code='common.button.skuEdit'/></a>
            <a onclick='detailsDel();' id='ezuiDetailsBtn_del' class='easyui-linkbutton'
               data-options='plain:true,iconCls:"icon-remove"' href='javascript:void(0);'><spring:message
                    code='common.button.skuDelete'/></a>
            <a onclick='detailsReceive();' id='ezuiDetailsBtn_receive' class='easyui-linkbutton'
               data-options='plain:true,iconCls:"icon-edit"' href='javascript:void(0);'>确认收货</a>
            <a onclick='clearDatagridSelected("#ezuiDetailsDatagrid");' class='easyui-linkbutton'
               data-options='plain:true,iconCls:"icon-undo"' href='javascript:void(0);'><spring:message
                    code='common.button.cancelSelect'/></a>
        </div>
    </form>
</div>
<div id='ezuiDialogBtn'>
    <%-- <a onclick='commit();' id='ezuiBtn_commit' class='easyui-linkbutton' href='javascript:void(0);'><spring:message code='common.button.commit'/></a> --%>
    <a onclick='ezuiDialogClose("#ezuiDialog");' class='easyui-linkbutton' href='javascript:void(0);'><spring:message
            code='common.button.close'/></a>
</div>
<script charset="UTF-8" type="text/javascript" src="<c:url value="/js/jquery/ajaxfileupload.js"/>"></script>
<script>
    $('#coldTag').filebox({
        prompt: '选择一个文件',//文本说明文件
		width:150,
        buttonText: '上传',  //按钮说明文字
        onChange:function(data){
            if(data){
                doUploadColdTag(data);
            }
        }
    });

    function doUploadColdTag(data) {
        var ajaxFile = new uploadFile({
            "url":sy.bp()+"/commonController.do?uploadFileLocal",
            "dataType":"json",
            "timeout":50000,
            "async":true,
            "data":{
                //多文件
                "file":{
                    //file为name字段 后台可以通过$_FILES["file"]获得
                    "file":document.getElementsByName("coldTag")[0].files[0]//文件数组
                }
            },
            onload:function(data){
                //$("#userdefine4").filebox("setValue",data.comment);
                //$("#ezuiForm input[name='userdefine4']").eq(0).val(data.comment);
                document.getElementsByName("userdefine4")[0].value = data.comment;
            },
            onerror:function(er){
                console.log(er);
            }
        });
    }

    function viewUrlColdTag(url) {
        if(url){
            showUrl(url);
        }
    }
</script>