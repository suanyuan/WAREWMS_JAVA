<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<script type="text/javascript" charset="UTF-8">
$(function(){
	if('${sessionScope.userInfo}'){
		getMenu();
	}
});

</script>
<div class="easyui-layout" data-options='fit:true'>
	<div data-options='region:"north",border:false' style="overflow: hidden;">
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-redo"' 	onclick="menu.tree('expandAll');">展開</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-undo"' 	onclick="menu.tree('collapseAll');">收合</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options='plain:true,iconCls:"icon-reload"' onclick="menu.tree('reload');">重整</a>
		<hr style="border-color: #fff;" />
	</div>
	<div data-options='region:"center",border:false'>
		<ul id="menu" style="margin-top: 5px;"></ul>
	</div>
</div>