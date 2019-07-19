document.onkeydown=function(ev) {
	var oEvent=ev||event;
	if(oEvent.keyCode==27){
		$('.easyui-dialog').each(function(i){
			$(this).dialog('close');
		});
	}
}

var cookieList = function(cookieName) {
	var cookie = $.cookie(cookieName);
	var items = cookie ? cookie.split(/,/) : new Array();
	return {
		"contains" : function(val){
			for(var i = 0 ; i < items.length ; i++){
				if(val == items[i]){
					return true;
				}
			}
			return false;
		},
	    "add": function(val) {//Add to the items.
	        items.push(val);
	        $.cookie(cookieName, items.join(','));
	    },
	    "remove": function (val) {
	        /** indexOf not support in IE, and I add the below code **/
	        if (!Array.prototype.indexOf) {
	            Array.prototype.indexOf = function(obj, start) {
	                for (var i = (start || 0), j = this.length; i < j; i++) {
	                    if (this[i] === obj) { return i; }
	                }
	                return -1;
	            };
	        }
	        var indx = items.indexOf(val);
	        if(indx!=-1) items.splice(indx, 1);
	        $.cookie(cookieName, items.join(','));
	    },
	    "clear": function() {//clear the cookie.
	        items = null;
	        $.cookie(cookieName, null);
	    },
	    "items": function() {//Get all the items.
	        return items;
	    }
	};
};
var ezuiDialogClose = function(dialogId){
    $.messager.confirm('<spring:message code="common.message.confirm"/>', '是否确认关闭？', function(confirm) {
        if (confirm) {
            $(dialogId).dialog('close');
        }
    })

};
var ezuiCombotreeReload = function(ezuiForm){
	$(ezuiForm).find('.easyui-combotree').each(function(){
		$(this).combotree('reload');
	});
};
var ezuiComboboxReload = function(ezuiForm){
	$(ezuiForm).find('.easyui-combobox').each(function(){
		$(this).combobox('reload');
	});
};
var ezuiCombotreeClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-combotree').each(function(){
		$(this).combotree('clear');
	});
};
var ezuiTimespinnerClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-timespinner').each(function(){
		$(this).timespinner('clear');
	});
};
var ezuiFileboxClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-filebox').each(function(){
		$(this).filebox('clear');
	});
};
var ezuiComboboxClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-combobox').each(function(){
		$(this).combobox('clear');
	});
};
var ezuiTextboxClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-textbox').each(function(){
		$(this).textbox('clear');
	});
};
var ezuiNumberboxClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-numberbox').each(function(){
		$(this).numberbox('clear');
	});
};
var ezuiToolbarClear = function(toolbarId, ezuiDatagrId){
	ezuiFormClear($(toolbarId));
	if(ezuiDatagrId){
		$(ezuiDatagrId).datagrid('load', {});
	}
};
var ezuiDateboxClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-datebox').each(function(){
		$(this).datebox('clear');
		$(this).datebox('calendar').calendar({
            validator: function(date){
                return true;
            }
        });
	});
};
var ezuiDatetimeboxClear = function(ezuiForm){
	$(ezuiForm).find('.easyui-datetimebox').each(function(){
		$(this).datetimebox('clear');
		$(this).datetimebox('calendar').calendar({
            validator: function(date){
                return true;
            }
        });
	});
};
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
    $(ezuiForm).find('input[name=enterpriseId]').each(function(){
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
var clearDatagridSelected = function(datagridId){
	$(datagridId).datagrid('unselectAll');
};
var clearTreegridSelected = function(treegridId){
	$(treegridId).treegrid('unselectAll');
};
var ajaxBtn = function(menuId, url, datagridMenu){
	var btnArray;
	$.ajax({
		url : 'btnController.do?findAll',
		type : 'POST', dataType : 'JSON',async  :false,
		success : function(result){
			btnArray = result.obj.split(",");
		}
	});

	$.ajax({
		url : url,
		data : {id : menuId},type : 'POST', dataType : 'JSON',async  :false,
		success : function(result){
			var flag = "";
			for(var i = 0 ; i < btnArray.length ; i++){
				flag = result.obj.indexOf(btnArray[i]) == -1 ? 'disable' : 'enable';
				if($('#ezuiBtn_'+btnArray[i]).length > 0){
					$('#ezuiBtn_'+btnArray[i]).linkbutton(result.obj.indexOf(btnArray[i]) == -1 ? "disable" : "enable");
				}
				if($('#menu_'+btnArray[i]).length > 0){
					$(datagridMenu).menu(flag+'Item', $('#menu_'+btnArray[i])[0]);
				}
				if($('#ezuiDatagrid').length > 0){
					if(flag == 'disable'){
						$('#ezuiDatagrid').datagrid('hideColumn', btnArray[i]);
					}
				}
			}
		}
	});
};

var getObjArrayValues = function(objArray){
	var valueArray = [];
	$(objArray).each(function(){
		valueArray.push($(this).val());
	});
	return valueArray.join(',');
}

/**
 * ajax download
 * @param url 路径
 * @param paramMap 参数HashMap
 * @returns {String} formId
 */
var ajaxDownloadFile = function(url,paramMap){
	var token = new Date().getTime();
	var formId = 'ajaxDownload_'+token;
	
	var form = $('<form style="display:none" target="" method="post">'); 
	form.attr('id',formId);   
	form.attr('action',url);
	if(paramMap != null && paramMap.size() > 0){
		var keys = paramMap.keys();
		for(var i = 0 ; i < keys.length ; i++){ 
			input0 = $('<input type="hidden">'); 
			input0.attr('name',keys[i]); 
			input0.attr('value',paramMap.get(keys[i])); 
			form.append(input0);
		} 
	}
	$('body').append(form);
	form.submit(); 
	return formId;
};

function HashMap(){
	var size = 0;/** Map 大小 * */
	var entry = new Object();/** 对象 * */
	this.put = function(key, value){/** 存 * */
		if (!this.containsKey(key)){
			size++;
		}
		entry[key] = value;
	};
	this.get = function(key){/** 取 * */
		if (this.containsKey(key)){
			return entry[key];
		}else{
			return null;
		}
	};
	this.remove = function(key){/** 删除 * */
		if (delete entry[key]){
			size--;
		}
	};
	this.containsKey = function(key){/** 是否包含 Key * */
		return (key in entry);
	};
	this.containsValue = function(value){/** 是否包含 Value * */
		for (var prop in entry){
			if (entry[prop] == value){
				return true;
			}
		}
		return false;
	};
	this.values = function(){/** 所有 Value * */
		var values = [];
		for (var prop in entry){
			values.push(entry[prop]);
		}
		return values;
	};
	this.keys = function(){/** 所有 Key * */
		var keys = [];
		for (var prop in entry){
			keys.push(prop);
		}
		return keys;
	};
	this.size = function(){/** Map Size * */
		return size;
	};
}

$.fn.fieldToUpperCase = function(){// 欄位英文變大寫
	$(this).blur(function(){
		$(this).val($(this).val().toUpperCase());
	});
};

$.fn.datebox.defaults.formatter = function(date) {
	var y = date.getFullYear();
	var m = date.getMonth() + 1;
	var d = date.getDate();
	return  y + '-' + (m < 10 ? '0' + m : m) + '-' + (d < 10 ? '0' + d : d);
	};

$.fn.datebox.defaults.parser = function(s) {
	if (s) {
		var a = s.split('-');
		var y = new Number(a[0]);
		var m = new Number(a[1]);
		var d = new Number(a[2]);
		var dd = new Date(y, m-1, d);
		return dd;
	} else {
		return new Date();
	}
};