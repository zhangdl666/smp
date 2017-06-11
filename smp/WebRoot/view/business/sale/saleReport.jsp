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

<title>销售额汇总</title>

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
<script type="text/javascript" src="js/common.js"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>

<BODY>
<%@include file="/header_nav.jsp"%>
<div class="container" id="bodyContainer">
	<form action="business/saleReport.action" method="post" class="form-horizontal" id="queryForm">
		<s:hidden name="rowName" id="rowName"></s:hidden>
		<s:hidden name="columnName" id="columnName"></s:hidden>
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">查询条件</h3>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-2 control-label">销售时间</label>
					<s:hidden name="saleQueryBo.saleTimeFrom" id="saleTimeFrom"></s:hidden>
					<s:hidden name="saleQueryBo.saleTimeEnd" id="saleTimeEnd"></s:hidden>
					<div class="col-sm-10 form-inline" >
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
						<button type="submit" class="btn btn-primary" >查询</button>
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
					<s:property value="tableStr" escape="false"/>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
//查看清单
function viewDetail(row,column) {
	document.getElementById("rowName").value = row;
	document.getElementById("columnName").value = column;
	var formvar=$('#queryForm').serialize();
	window.open("business/saleReportDetail.action?"+formvar);
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
</script>
</BODY>
</html>
