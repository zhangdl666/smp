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

<title>销售查询</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/navbar-fixed-top.css" rel="stylesheet">
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/sticky-footer-navbar.css" rel="stylesheet">
<link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet">
<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/common.js" charset="gbk"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>

<BODY>
<%@include file="/header_nav.jsp"%>
<div class="container" id="bodyContainer">
	<form action="business/querySale.action" method="post" class="form-horizontal" id="queryForm">
		<s:hidden name="page.currentPage" id="currentPage"></s:hidden>
		<s:hidden name="page.totalRowSize" id="totalRowSize"></s:hidden>
		<s:hidden name="page.totalPage" id="totalPage"></s:hidden>
		<s:hidden name="saleId" id="saleId"></s:hidden>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">查询条件</h3>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-2 control-label">销售员</label>
					<div class="col-sm-10">
						<s:textfield name="saleQueryBo.saleUserName" class="form-control"></s:textfield>
					</div>
				</div>
				<div class="form-group">
					<label class="col-sm-2 control-label">销售时间</label>
					<s:hidden name="saleQueryBo.saleTimeFrom" id="saleTimeFrom"></s:hidden>
					<s:hidden name="saleQueryBo.saleTimeEnd" id="saleTimeEnd"></s:hidden>
					<div class="col-sm-10 form-inline">
						<div class="input-group date form_date col-sm-5" data-date="" data-date-format="yyyy-MM-dd" data-link-field="saleTimeFrom" data-link-format="yyyy-mm-dd">
		                	<input class="form-control" size="16" type="text" value="<s:date name="saleQueryBo.saleTimeFrom" format="yyyy-MM-dd"/>" readonly>
		                	<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
						-
						<div class="input-group date form_date col-sm-5" data-date="" data-date-format="yyyy-MM-dd" data-link-field="saleTimeEnd" data-link-format="yyyy-mm-dd">
		                	<input class="form-control" size="16" type="text" value='<s:date name="saleQueryBo.saleTimeEnd" format="yyyy-MM-dd"/>' readonly>
		                	<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
							<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
						</div>
					</div>
				</div>
				<div class="form-group">
					<div class="col-sm-12 col-sm-offset-6">
						<button type="submit" class="btn btn-primary">查询</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 查询结果 -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">查询结果</h3>
			</div>
			<div class="panel-body">
				<div class="col-sm-12">
					<table class="table table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>单号</th>
								<th>销售员</th>
								<th>销售时间</th>
								<th>销售额</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
							<s:if test="saleList==null || saleList.size==0">
								<tr><td colspan="6">未找到任何数据！</td></tr>
							</s:if>
							<s:else>
								<s:iterator value="saleList" status="st">
									<tr>
										<td><s:property value='#st.index+1' /></td>
										<td><a href="javascript:viewSale('<s:property value='id' />')"><s:property value="businessNumber"/> <s:property value='title' /></a></td>
										<td><s:property value='saleUserName' /></td>
										<td><s:date name="saleTime" format="yyyy-MM-dd"></s:date></td>
										<td><s:property value='total' /></td>
										<td>
											<s:if test="recordUserId==#session.loginUser.id">
												<a href="javascript:deleteSale('<s:property value='id' />')">删除</a>
											</s:if>
										</td>
									</tr>
								</s:iterator>
							</s:else>
						</tbody>
					</table>
				</div>
				<div align="center">
					共<s:property value='page.totalRowSize' />条记录，每页
					<s:select list="#{10:10,20:20,50:50,100:100 }" listKey="key" listValue="value" name="page.pageSize" id="pageSize" onchange="pageSizeChange()"></s:select>条
					<s:property value='page.currentPage' />/<s:property value='page.totalPage' />
					<s:if test="page.currentPage>1">
						<a href="javascript:movePage('first')">首页</a>
						<a href="javascript:movePage('up')">上一页</a>
					</s:if>
					<s:if test="page.currentPage<page.totalPage">
						<a href="javascript:movePage('down')">下一页</a>
						<a href="javascript:movePage('last')">末页</a>
					</s:if>
					&nbsp;&nbsp;&nbsp;跳转到<s:textfield name="forwardPage" id="forwardPage" size="3" onkeyup="this.value=this.value.replace(/[^\d]/g,'') "></s:textfield>页
					<button type="button" class="btn btn-default" onclick="jumpPage()">确定</button>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
function viewSale(id){
	var _url = "business/viewMySale.action?id=" + id;
	window.open(_url);
}
$('.form_date').datetimepicker({
    language:  'zh-CN',
    weekStart: 1,
    todayBtn:  1,
	autoclose: 1,
	todayHighlight: 1,
	startView: 2,
	minView: 2,
	forceParse: 0
});

function movePage(pageNo) {
	var pg = document.getElementById("currentPage");
	if(pageNo=="first"){
		pg.value = 1;
	}else if(pageNo=="last"){
		var tp = document.getElementById("totalPage").value;
		pg.value = parseInt(tp);
	}else if(pageNo=="up"){
		pg.value = parseInt(pg.value) - 1;
	}else if(pageNo=="down"){
		pg.value = parseInt(pg.value) + 1;
	}
	$("#queryForm").submit();
}

function jumpPage(){
	var pg = document.getElementById("currentPage");
	var totalPage = document.getElementById("totalPage");
	var forwardPage = document.getElementById("forwardPage");
	var forwardPageValue = parseInt(forwardPage.value);
	var totalPageValue = parseInt(totalPage.value);
	if(forwardPageValue>totalPageValue){
		forwardPageValue = totalPageValue;
		forwardPage.value = totalPageValue;
	}
	pg.value = forwardPageValue;
	$("#queryForm").submit();
}

function pageSizeChange(){
	var pg = document.getElementById("currentPage");
	pg.value = 1;
	$("#queryForm").submit();
}

function deleteSale(id) {
	var r = confirm("确认删除吗");
	if(r==false) {
		return;
	}
	var _url = "business/deleteSaleById.action";
	document.getElementById("saleId").value = id;
	document.getElementById("queryForm").action = _url;
	$("#queryForm").submit();
}
</script>
</BODY>
</html>
