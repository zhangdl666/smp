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

<title>工资结算</title>

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
	<form action="business/salaryQuery.action" method="post" class="form-horizontal" id="queryForm">
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
					<label class="col-sm-2 control-label">结算月份</label>
					<s:hidden name="saleQueryBo.saleTimeFrom" id="saleTimeFrom"></s:hidden>
					<div class="col-sm-10 form-inline">
						<div class="input-group col-sm-10">
		                	<s:select class="form-control" style="width: auto;" list="#{'2017':'2017','2018':'2018','2019':'2019','2020':'2020','2021':'2021','2022':'2022' }" 
		                		listKey="key" listValue="value" name="year" onchange="refreshResult()">
		                	</s:select>
		                	<s:select class="form-control" style="width: auto;" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12' }" 
		                		listKey="key" listValue="value" name="month" onchange="refreshResult()">
		                	</s:select>
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
				<h3 class="panel-title">工资单 &nbsp;&nbsp;&nbsp;&nbsp;
					<s:if test="salaryList!=null && salaryList.size>0">
						<button type="button" onclick="salaryReCalcu()" class="btn btn-primary">重新结算</button>
					</s:if>
				</h3>
			</div>
			<div class="panel-body">
				<div class="col-sm-12">
					<table class="table table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>月份</th>
								<th>用户</th>
								<th>级别</th>
								<th>工资A</th>
								<th>工资B</th>
								<th>工资C</th>
								<th>工资D</th>
								<th>工资合计</th>
							</tr>
						</thead>
						<tbody>
							<s:if test="salaryList==null || salaryList.size==0">
								<tr><td colspan="7"><a href="javascript:salaryCalcu();">该月工资还未结算，点击此处开始结算！</a></td></tr>
							</s:if>
							<s:else>
								<s:iterator value="salaryList" status="st">
									<tr>
										<td><s:property value='#st.index+1' /></td>
										<td><s:property value="yearMonth"/> </td>
										<td><s:property value='userName' />(<s:property value='userLoginName' />)</td>
										<td><s:property value='userLevel' /></td>
										<td><s:property value='salaryA' /></td>
										<td><s:property value='salaryB' /></td>
										<td><s:property value='salaryC' /></td>
										<td><s:property value='salaryD' /></td>
										<td><s:property value='salaryTotal' /></td>
									</tr>
								</s:iterator>
							</s:else>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</form>
</div>
<script type="text/javascript">
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

function salaryCalcu() {
	var _url = "business/salaryCalcu.action";
	document.getElementById("queryForm").action = _url;
	$("#queryForm").submit();
}

function salaryReCalcu() {
	var r = confirm("确认重新结算吗");
	if(r==false) {
		return;
	}
	var _url = "business/salaryReCalcu.action";
	document.getElementById("queryForm").action = _url;
	$("#queryForm").submit();
}

function refreshResult(){
	$("#queryForm").submit();
}
</script>
</BODY>
</html>
