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

<title>请选择</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="css/zTreeStyle/zTreeStyle.css" type="text/css">
<script src="js/jquery.min.js"></script>
<script src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="js/ztree.js"></script>
<script type="text/javascript" src="js/common.js"></script>
<SCRIPT type="text/javascript">
	var mess = "${message}";
	if(mess!=null && mess!="") {
		if(mess=="success"){
			window.close();
		}else {
			alert(mess);
		}
	}
	
	var setting = {
		check : {
			enable : true,
			chkStyle: "radio",
			radioType:"all"
		},
		key: {
			title:"name"
		},
		data : {
			simpleData : {
				enable : true
			}
		},
	};
	var zTree;
	var treeNodes =  ${treeNodeData};
	
	var treeName="tree";
	if(mess==""){
		$(document).ready(function(){
			$.fn.zTree.init($("#tree"), setting, treeNodes);
			key = $("#key");
			key.bind("focus", focusKey)
			.bind("blur", blurKey);
			$("#search").bind("click",searchNode);
		});
	}
	function cancel(){
		window.returnValue = null;
		window.close();
	}
	
	function confirm(){
		var treeObj=$.fn.zTree.getZTreeObj("tree");
        nodes=treeObj.getCheckedNodes(true);
        if(nodes.length==0) {
        	alert("未选择任何值！");
        	return;
        }
        var names = "";
        var ids = "";
        for(var i=0;i<nodes.length;i++){
        	//只取叶子节点
	        names = names + nodes[i].name + ",";
       		ids = ids + nodes[i].id + ",";
        }
        if(ids.length > 1) {
        	ids = ids.substr(0,ids.length - 1);
        	names = names.substr(0,names.length - 1);
        }
        window.returnValue = ids + ";" + names;
        window.close();
	}
	
//-->
</SCRIPT>
</head>

<BODY>
	<div class="form-group">
		<input type="text" id="key" value="" name="key" onkeydown="if(event.keyCode==13) searchNode();" class="empty"  style="width: 165px;"/><input name="" type="button" value="搜索"  id="search" style="margin-right: 0px;" class="tankuan_button_queding"  />
	</div>
	<div class="form-group">
		<button type="button" class="btn btn-xs btn-default" onclick="cancel()">取消</button>
		<button type="button" class="btn btn-xs btn-default" onclick="confirm()">确定</button>
	</div>
	<div class="form-group">
		<ul id="tree" class="ztree"></ul>
	</div>
	<div class="form-group">
		<button type="button" class="btn btn-xs btn-default" onclick="cancel()">取消</button>
		<button type="button" class="btn btn-xs btn-default" onclick="confirm()">确定</button>
	</div>
</BODY>
</html>
