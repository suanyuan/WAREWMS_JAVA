<%@ page language='java' pageEncoding='UTF-8'%>
<form id='ezuiFormInfo' method='post'>
    <input type='hidden' id='gspProductRegisterSpecsId' name='gspProductRegisterSpecsId'/>
    <table>
        <tr style="display: none">
            <td><input type="hidden"  name='specsId'  id="specsId" value="${specsId}" /></td>
            <td><input type="hidden"  name='type'  id="type" value="${type}" /></td>


        </tr>
        <tr style="display: none">
            <th>产品注册证表主键</th>
            <td><input type='hidden' data="1" id="productRegisterId" name='productRegisterId' class='easyui-textbox' size='50' data-options='required:true'/></td>
        </tr>

        <tr>
            <%--<th>注册证编号</th>--%>
            <%--<td>--%>
                <%--<input type='text' data="1" id='enterpriseIdQuery1'  name="enterpriseName" data='1' class='easyui-textbox' data-options='required:true,width:200' style="width: 100px;"/>--%>
                <%--<input type="hidden"  id="enterpriseId" name="enterpriseId" data='1'  />--%>
                <%--&lt;%&ndash;<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>&ndash;%&gt;--%>
            <%--</td>--%>
                <th>医疗器械标志</th>
                <td><input type='text' data="1"  id="medicalDeviceMark" name='medicalDeviceMark' class='easyui-textbox' size='50' data-options='required:true' /></td>




            <th>产品名称</th>
            <td><input type='text' data="1" id="productNameMain" name='productNameMain' class='easyui-textbox' size='50' data-options='required:true' /></td>

        </tr>

        <tr>
            <th>注册证编号</th>
            <td>
                <input type='text' data="1" id='productRegisterNo'  name="productRegisterNo" class='easyui-textbox' size='50'  data-options='required:true,editable:false'/>
                <%--<a href="javascript:void(0)" onclick="searchEnterprise()" class="easyui-linkbutton" data-options="iconCls:'icon-search'"></a>--%>
            </td>
            <th>产品描述</th>
            <td><input type='text' data="1" id="productRemark" name='productRemark' class='easyui-textbox' size='50' data-options=''/></td>

        </tr>
        <tr>

            <th>产品代码</th>
            <td><input type='text' data="1" id="productCode" name='productCode' class='easyui-textbox' size='50' data-options='required:true'/></td>


            <th>单位</th>
            <td><input type='text' data="1" id="unit" name='unit' class='easyui-textbox' size='50' data-options='required:true'/></td>
            <%--<th>商品名称</th>--%>
            <%--<td><input type='text' data="1" id="productName" name='productName' class='easyui-textbox' size='50' data-options='required:true'/></td>--%>

        </tr>
        <tr>
            <th>规格</th>
            <td><input type='text' data="1" id="specsName" name='specsName' class='easyui-textbox' size='50' data-options='required:true'/></td>

            <th>包装单位</th>
            <td><input type='text' data="1" id="packagingUnit" name='packagingUnit' class='easyui-textbox' size='50' data-options=''/></td>

            <%--<td><select data="1" id="productionAddress" name="productionAddress" class="easyui-combobox"  style="width:385px;">--%>
            <%--<option value=""></option>--%>
            <%--<option >国内</option>--%>
            <%--<option >国外</option>--%>
            <%--</select></td>--%>
            <%--<td><input type='text' data="1" id="productionAddress" name='productionAddress' class='easyui-textbox' size='50' data-options='required:true'/></td>--%>
        </tr>
        <tr>
            <th>型号</th>
            <td><input type='text' data="1" id="productModel" name='productModel' class='easyui-textbox' size='50' data-options='required:true'/></td>


            <th>包装要求</th>
            <td><input type='text' data="1" id="packingRequire" name='packingRequire' class='easyui-textbox' size='50' data-options=''/></td>
        </tr>
        <tr>

            <th>包装规格</th>
            <td><input type='text' data="1" id="packingUnit" name='packingUnit' class='easyui-textbox' size='50' data-options='required:true'/></td>

            <th>长</th>
            <td><input type='text' data="1" id="llong" name='llong' class='easyui-textbox' size='47' data-options=''/>(m)</td>
        </tr>

        <tr>
            <th>运输条件</th>
            <td><input type='text' data="1" id="transportCondition" name='transportCondition' class='easyui-textbox' size='50' data-options='required:false'/></td>


            <th>宽</th>
            <td><input type='text' data="1" id="wide" name='wide' class='easyui-textbox' size='47' data-options=''/>(m)</td>

        </tr>

        <tr>
            <th>储存条件</th>
            <td><input type='text' data="1" id="storageCondition" name='storageCondition' class='easyui-textbox' size='50' data-options='required:true'/></td>

            <th>高</th>
            <td><input type='text' data="1" id="hight"  name='hight' class='easyui-textbox' size='47' data-options=''/>(m)</td>

          </tr>
       <tr>
           <th>生产企业</th>
           <td><input type='hidden' data="1" id="enterpriseId" name='enterpriseId'  size='50' data-options=''/>
               <input type='text' data="1" id="enterpriseName" name='enterpriseName' class='easyui-textbox' size='50' data-options=''/></td>


           <th>重量</th>
           <td><input type='text' data="1" id="wight"  name='wight' class='easyui-textbox' size='47' data-options=''/>(kg)</td>
       </tr>
        <tr>
            <th>生产许可证号/备案号</th>
            <td><input type='text' data="1" id="licenseOrRecordNo"  name='licenseOrRecordNo' class='easyui-textbox' size='50' data-options="editable:false,panelHeight: 'auto'"/></td>



            <th>商品条码</th>
            <td><input type='text' data="1" id="barCode" name='barCode' class='easyui-textbox' size='50' data-options=''/></td>


        </tr>
        <tr>
            <th>产地</th>
            <td><input type="text" data="1" id="productionAddress"  name="productionAddress"  class="easyui-textbox" size='50' data-options='editable:false' /></td>


            <th>自赋码1</th>
            <td><input type='text' data="1" id="alternatName1" name='alternatName1' class='easyui-textbox' size='50' data-options=''/></td>
            <%--<th>分类目录</th>--%>
            <%--<td><input type='text' data="1" id="categories" name='categories' class='easyui-textbox' size='50' data-options='required:true'/></td>--%>


        </tr>

        <tr>
            <th>双证</th><td><input type="text" data="1" id="isDoublec"    name="isDoublec"  class="easyui-combobox" size='50' data-options="panelHeight:'auto',
                                                                                                                                    required:true,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>

            <th>自赋码2</th>
            <td><input type='text' data="1" id="alternatName2" name='alternatName2' class='easyui-textbox' size='50' data-options=''/></td>

        </tr>
        <tr>
            <th>产品合格证</th><td><input type="text" data="1" id="isCertificate"   name="isCertificate"  class="easyui-combobox" size='50' data-options="panelHeight:'auto',
                                                                                                                                    required:true,
																																	editable:false,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>
            <th>自赋码3</th>
            <td><input type='text' data="1" id="alternatName3" name='alternatName3' class='easyui-textbox' size='50' data-options=''/></td>

        </tr>
        <tr>
            <th>附卡类别</th>
            <td><input type='text' data="1"  id="attacheCardCategory" name='attacheCardCategory' class='easyui-textbox' size='50' data-options='' /></td>


            <th>自赋码4</th>
            <td><input type='text' data="1" id="alternatName4" name='alternatName4' class='easyui-textbox' size='50' data-options=''/></td>
            <%--<th>管理分类</th>--%>
            <%--<td><input type='text' data="1" id="manageCategories" name='manageCategories' class='easyui-textbox' size='50' data-options='required:true'/></td>--%>

        </tr>
        <tr>
            <th>冷链标志</th>
            <td><input type='text' data="1"  id="coldHainMark" name='coldHainMark' class='easyui-textbox' size='50' data-options='required:false' /></td>

            <th>自赋码5</th>
            <td><input type='text' data="1" id="alternatName5" name='alternatName5' class='easyui-textbox' size='50' data-options=''/></td>

        </tr>
        <tr>
            <th>灭菌标志</th>
            <td><input type='text' data="1"  id="sterilizationMarkers" name='sterilizationMarkers' class='easyui-textbox' size='50' data-options='' /></td>



            <th>创建人</th>
            <td contenteditable="false"><input type='text' data="1" value="${createId}" id="createId" name='createId' class='easyui-textbox' size='50' data-options='' readonly/></td>

        </tr>
        <tr>
            <th>养护周期(天)</th>
            <td><input type='text' data="1"  id="maintenanceCycle" name='maintenanceCycle' class='easyui-numberbox' size='50' data-options='required:true' /></td>
            <th>创建时间</th>
            <td><input type='text' data="1" value="${createDate}" id="createDate" name='createDate' class='easyui-textbox' size='50' data-options='' readonly/></td>

        </tr>
        <tr>


            <th>编辑人</th>
            <td><input type='text' data="1" value="${createId}" id="editId" name='editId' class='easyui-textbox' size='50' data-options='' readonly/></td>
            <th>是否有效：</th><td><input type="text" data="1" id="isUse"  value="${isUse}"  name="isUse"  class="easyui-combobox" size='16' data-options="panelHeight:'auto',
																																	editable:false,
																																     readonly:true,
																																	valueField: 'id',
																																	textField: 'value',
																																	data: [
																																	{id: '1', value: '是'},
																																	{id: '0', value: '否'}
																																]"/></td>

          </tr>
        <tr>
            <th>编辑时间</th>
            <td><input type='text'  value="${createDate}" id="editDate" name='editDate' class='easyui-textbox' size='50' data-options='' readonly/></td>



        </tr>
    </table>
</form>
<div id='enterpriseDialog' style='padding: 10px;'>

</div>
<script>
    var enterpriseDialog;
    var id;
    var row;
    var type;
    $(function(){
        $('#medicalDeviceMark').combobox({
            onChange: function(){
                changeRequired();
            }
        });
        $('#coldHainMark').combobox({
                onChange: function(){
                changeColdHainMark();
            }
        });
        //主页编辑
        // debugger
        //
        // if(processType == 'product'){
        //     // $('#enterpriseDialog').dialog('destroy');
        //     var row = ezuiDatagridDetail.datagrid('getSelected');
        // }
        //alert(row.specsId);
        // if(row!=null ){
        //
        // }
// alert(processType);
//         debugger
//         alert($("#ezuiFormInfo #specsId").val());

        type = $("#ezuiFormInfo #type").val();
        if(""!=type){
            processType =type;
        }
        if(processType == 'product'){
            id = $("#ezuiFormInfo #specsId").val();
        }else if(processType == 'edit'){
            row = ezuiDatagrid.datagrid('getSelected');
            id = row.specsId;
        }


        // if(row==null && id==""){
        //
        // }else if(row!=null){
        //
        // }


        // changeRequired();
        if( id!="" || row !=null){
            if(processType == 'edit' || processType == 'product' ){
            $.ajax({
                url : 'gspProductRegisterSpecsController.do?getInfo',
                data : {"specsId" : id},
                type : 'POST',
                dataType : 'JSON',
                success : function(result){
                    if(result.success){
                        changeRequired();
                        changeColdHainMark();
                        // coldfield();
                        $("#ezuiFormInfo input[id!=''][data='1']").each(function (index) {
                            if($(this).attr("class")){
                                if($(this).attr("class").indexOf('easyui-textbox')!=-1){
                                    $(this).textbox("setValue",result.obj[""+$(this).attr("id")+""]);
                                    //gspBusinessFrom[""+$(this).attr("id")+""] = $(this).textbox("getValue");
                                }else if($(this).attr("class").indexOf('easyui-combobox')!=-1){
                                    $(this).combobox("setValue",result.obj[""+$(this).attr("id")+""]);
                                }else if($(this).attr("class").indexOf('easyui-datebox')!=-1){
                                    $(this).datebox("setValue",result.obj[""+$(this).attr("id")+""]);
                                }else if($(this).attr("class").indexOf('easyui-numberbox')!=-1){
                                    $(this).numberbox("setValue",result.obj[""+$(this).attr("id")+""]);
                                }
                            }


                        })

                    }

                }

            });

            }
        }

//包装规格下拉框
        $('#packingUnit').combobox({
            panelHeight: 'auto',
            editable:false,
            url:sy.bp()+'/basPackageController.do?getCombobox',
            valueField:'value',
            textField:'value'
        });
//单位下拉框
        $('#unit').combobox({
            panelHeight: 'auto',
            editable:false,
            url:sy.bp()+'/commonController.do?getUOM',
            valueField:'id',
            textField:'value'
        });
//冷链标志下拉框
        $('#coldHainMark').combobox({
            editable:false,
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getColdHainMark',
            valueField:'id',
            textField:'value'
        });
//灭菌标志下拉框
//         $('#sterilizationMarkers').combobox({
//             panelHeight: 'auto',
//             url:sy.bp()+'/commonController.do?getUOM',
//             valueField:'id',
//             textField:'value'
//         });
//医疗器械标志下拉框
        $('#medicalDeviceMark').combobox({
            editable:false,
            panelHeight: 'auto',
            url:sy.bp()+'/commonController.do?getYesOrNoCombobox',
            valueField:'id',
            textField:'value'
        });


        // $('#productionAddress').combobox({
        //     url:sy.bp()+'/basPackageController.do?getCombobox',
        //     valueField:'value',
        //     textField:'value'
        // });
        // changeRequired();

    });

    function coldfield() {
        console.log($('#ezuiFormInfo #coldHainMark').combobox('getValue'));
        console.log($('#ezuiFormInfo #coldHainMark').val());
        var cold = $('#ezuiFormInfo #coldHainMark').val();
        if(cold=="" || cold==null){
            $('#ezuiFormInfo #coldHainMark').combobox("setValue","FLL");
        }
    }

    function changeRequired(){
        if($('#medicalDeviceMark').combobox('getValue') == '0'){
            $('#productRegisterNo').textbox({required:false});
            $('#specsName').textbox({required:false});
            $('#productModel').textbox({required:false});
            $('#packingUnit').combobox({required:false});
            $('#storageCondition').textbox({required:false});
            $('#isDoublec').combobox({required:false});
            $('#isCertificate').combobox({required:false});
            $('#unit').combobox({required:false});
            $('#maintenanceCycle').numberbox({required:false});
            $('#licenseOrRecordNo').textbox({editable:true});
            $('#ezuiFormInfo #productionAddress').textbox({editable:true});




        }else if($('#medicalDeviceMark').combobox('getValue') == '1'){
            $('#ezuiFormInfo #productRegisterNo').textbox({required:true});
            $('#ezuiFormInfo #productName').textbox({required:true});
            $('#ezuiFormInfo #productModel').textbox({required:true});
            $('#ezuiFormInfo #packingUnit').combobox({required:true});
            $('#storageCondition').textbox({required:true});
            $('#isDoublec').combobox({required:true});
            $('#isCertificate').combobox({required:true});
            $('#unit').combobox({required:true});
            $('#maintenanceCycle').numberbox({required:true});
            $('#licenseOrRecordNo').textbox({editable:false});

            $('#ezuiFormInfo #productionAddress').textbox({editable:false});
        }
    }
    function changeColdHainMark(){
        //非冷链： FLL  冷藏： LC  冷冻：LD

        // console.log($('#ezuiFormInfo #coldHainMark').combobox('getValue'));
        if($('#ezuiFormInfo #coldHainMark').combobox('getValue') == 'FLL' || $('#ezuiFormInfo #coldHainMark').combobox('getValue') == ''){
            $('#transportCondition').textbox({required:false});
        }else {
            $('#transportCondition').textbox({required:true});

        }
    }



    function searchEnterprise() {
        enterpriseDialog = $('#enterpriseDialog').dialog({
            modal: true,
            title: '<spring:message code="common.dialog.title"/>',
            href: sy.bp() + "/gspProductRegisterSpecsController.do?toSearchDialog",
            width: 850,
            height: 500,
            cache: false,
            onClose: function () {
                // $(this).close(enterpriseDialog);
                enterpriseDialog.dialog('clear');
            },

        })
    }
    //点击注册证编号之后datagrid放大镜事件
    function choseSelect(row) {
        if(!row){
            row = enterpriseSearchGrid.datagrid("getSelected");
        }
        //id,no,name,address,enterpriseId,enterpriseName,licenseNo,recordNo,storageConditions
        //(row.productRegisterId,row.productRegisterNo,row.productNameMain,row.productionAddress,row.enterpriseId,row.enterpriseName,row.licenseNo,row.recordNo,row.storageConditions);
        console.log(row);
        var id = row.productRegisterId;
        var no = row.productRegisterNo;
        var name = row.productNameMain;
        var address = row.productionAddress;
        var enterpriseId = row.enterpriseId;
        var enterpriseName = row.enterpriseName;
        var licenseNo = row.licenseNo;
        var recordNo = row.recordNo;
        var storageConditions = row.storageConditions;
        //console.log(1111111)
        //console.log(name)
        var enterpriceId;
        enterpriceId = id;
        //$("input[name='enterpriseId'][data='1']").val(id);
        console.log(licenseNo+'======='+recordNo+'==='+enterpriseId);

        $("#ezuiFormInfo input[id='productNameMain']").textbox('setValue',name);
        $("#ezuiFormInfo input[id='productionAddress']").textbox('setValue',address);
        $("#productRegisterNo").textbox("setValue",no);
        $("#productRegisterId").textbox("setValue",id);
        $("#enterpriseName").textbox("setValue",enterpriseName);
        //储存条件
        $("#storageCondition").textbox("setValue",storageConditions);
        $("#enterpriseId").val(enterpriseId);
        $("#ezuiFormInfo #licenseOrRecordNo").textbox("setValue",licenseNo);








       /* $.ajax({
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
        console.log(4444444444)
        $("input[name='enterpriseId1']").val(id);*/
        enterpriseDialog.dialog("close");
    }

    $(function () {
        $("#productRegisterNo").textbox({
            icons:[{
                iconCls:'icon-search',
                handler: function(e){
                    searchEnterprise();
                }
            }]
        })
    })





</script>
