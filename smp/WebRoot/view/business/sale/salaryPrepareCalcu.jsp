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

<title>工资预计算</title>

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
	<form action="business/prepareSalaryCalcu.action" method="post" class="form-horizontal" id="queryForm">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">第一步：选择用户</h3>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-2 control-label">结算月份</label>
					<div class="col-sm-3 form-inline">
						<div class="input-group col-sm-8">
		                	<s:select class="form-control" style="width: auto;" list="#{'2017':'2017','2018':'2018','2019':'2019','2020':'2020','2021':'2021','2022':'2022' }" 
		                		listKey="key" listValue="value" name="year" id="year">
		                	</s:select>
		                	<s:select class="form-control" style="width: auto;" list="#{'01':'01','02':'02','03':'03','04':'04','05':'05','06':'06','07':'07','08':'08','09':'09','10':'10','11':'11','12':'12' }" 
		                		listKey="key" listValue="value" name="month" id="month">
		                	</s:select>
						</div>
					</div>
					<label class="col-sm-2 control-label">结算用户</label>
					<div class="col-sm-5 form-inline">
						<div class="input-group col-sm-10">
		                	<s:textfield name="saleUserName" id="saleUserName" readonly="readonly" onclick="showUserTree()" class="form-control"></s:textfield>
							<s:hidden name="saleUserId" id="saleUserId"></s:hidden>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="div_userList">
			<s:if test="prepareSalary!=null">
			<s:include value="belowUserSalaryList.jsp"></s:include>
			</s:if>
		</div>
		
		<!-- 计算结果 -->
		<s:if test="prepareSalary!=null">
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">计算结果</h3>
			</div>
			<div class="panel-body">
				<div class="col-sm-12">
					<table class="table table-condensed">
						<thead>
							<tr>
								<th>工资A</th>
								<th>工资B</th>
								<th>合计</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><s:property value='%{formatDouble(prepareSalary.salaryA)}' /></td>
								<td><s:property value='%{formatDouble(prepareSalary.salaryB)}' /></td>
								<td><s:property value='%{formatDouble(prepareSalary.salaryTotal)}' /></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		</s:if>
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

function showUserTree() {
	var checkedIds = document.getElementById("saleUserId").value;
	var _url = "business/selectSaleUser.action?checkedIds=" + checkedIds;
	//var values = window.showModalDialog(_url,"dialogWidth=50px;dialogHeight=100px");
	window.open(_url,'newWin','modal=yes,width=300,height=500');
}

function showUserTreeBack(values){
	if(values!=null && values!="") {
		var aRetValue = values.split(";");
		document.getElementById("saleUserId").value = aRetValue[0];
		document.getElementById("saleUserName").value = aRetValue[1];
		//加载下级网体
		showBelowUserList(aRetValue[0]);
	}
}

//下级网体
function showBelowUserList(userId) {
	var y = document.getElementById("year").value;
	var m = document.getElementById("month").value;
	var _url = "business/showBelowUserList.action";
	var params = "id=" + userId + "&year=" + y + "&month=" + m;
	queryByAjax("div_userList", _url, "post", params);
}

</script>
</BODY>
</html>
