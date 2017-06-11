<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<link rel="icon" href="image/favicon.ico">

<title>用户管理</title>
<%@include file="/cssAndJs.jsp"%>
<link href="css/dashboard.css" rel="stylesheet">
<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="js/ztree.js"></script>
<script type="text/javascript" src="js/common.js" charset="gbk"></script>
<SCRIPT type="text/javascript">
	
	var setting = {
		data : {
			simpleData : {
				enable : true
			}
		},
		key: {
			title:"name"
		},
		callback: {
			onClick: showDeptUserList
		}
	};
	var zTree;
	var treeNodes;
	var treeName="tree";
	$(function() {
		$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			dataType : "json",
			url : "organization/getUserTree.action?",//请求的action路径   
			error : function() {//请求失败处理函数   
				alert('请求失败');
			},
			success : function(data) { //请求成功后处理函数。     
				treeNodes = data; //把后台封装好的简单Json格式赋给treeNodes   
			}
		});
		$.fn.zTree.init($("#tree"), setting, treeNodes);
		key = $("#key");
		key.bind("focus", focusKey)
		.bind("blur", blurKey);
		$("#search").bind("click",searchNode);
	});
	
	function showDeptUserList(event, treeId, treeNode, clickFlag) {
		//document.getElementById("deptId").value = treeNode.id;
		//document.getElementById("currentPage").value = 1;
		var _url = "organization/userQuery.action";
		//var params = $("#queryForm").serialize();
		var params = "managerId=" + treeNode.id + "&page.currentPage=1";
		queryByAjax("userManagerContainer", _url, "post", params);
	}
//-->
</SCRIPT>
</head>

<BODY>
	<%@include file="/header_nav.jsp"%>
	<div class="row">
		<div class="col-sm-3 col-md-2 sidebar">
			<div class="form-group">
				<input type="text" id="key" value="" name="key" onkeydown="if(event.keyCode==13) searchNode();" class="empty"  style="width: 165px;"/><input name="" type="button" value="搜索"  id="search" style="margin-right: 0px;" class="tankuan_button_queding"  />
			</div>
			<div class="zTreeDemoBackground left">
				<ul id="tree" class="ztree"></ul>
			</div>
		</div>
		<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main"
			id="userManagerContainer">
			请先选择左侧的组织结构树，以便进行用户信息维护！
		</div>
	</div>
	<%@include file="/footer.jsp"%>
</BODY>
</html>
