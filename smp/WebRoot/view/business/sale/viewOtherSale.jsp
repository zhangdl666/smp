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

<title>销售数据录入</title>

<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<link href="css/navbar-fixed-top.css" rel="stylesheet">
<link href="css/bootstrapValidator.css" rel="stylesheet">
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
<script type="text/javascript" src="js/jquery.form.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript" src="js/common.js" charset="gbk"></script>
<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
</head>

<BODY>
<%@include file="/header_nav.jsp"%>
<div class="container" id="bodyContainer">
		
	
	<form action="business/saveOtherSale.action" method="post" class="form-horizontal" id="editForm">
		<s:if test="sale.id!=null">
			<s:hidden name="sale.id"></s:hidden>
		</s:if>
		<s:hidden name="sale.businessNumber"></s:hidden>
		<s:hidden name="sale.recordUserId"></s:hidden>
		<s:hidden name="sale.recordUserName"></s:hidden>
		<s:hidden name="sale.recordType"></s:hidden>
		<s:hidden name="sale.validstatus"></s:hidden>
		<s:hidden name="sale.recordTime">
			<s:param name="value"><s:date name="sale.recordTime" format="yyyy-MM-dd HH:mm:ss"></s:date></s:param>
		</s:hidden>
		<fieldset>
        	<legend>
        		销售数据录入
        	</legend>
			<div class="form-group">
				<label class="col-sm-2 control-label">单号</label>
				<div class="col-sm-2">
					<s:property value="sale.businessNumber"/>
				</div>
				<label class="col-sm-2 control-label">录入人</label>
				<div class="col-sm-6">
					<s:property value="sale.recordUserName"/> &nbsp;&nbsp;
					<s:date name="sale.recordTime" format="yyyy-MM-dd HH:mm:ss"></s:date>
				</div>
			</div>
			<div class="form-group">
				<label class="col-sm-2 control-label">销售人</label>
				<div class="col-sm-2">
					<s:textfield name="sale.saleUserName" id="saleUserName" readonly="readonly" onclick="showUserTree()" class="form-control"></s:textfield>
					<s:hidden name="sale.saleUserId" id="saleUserId"></s:hidden>
				</div>
				<label class="col-sm-2 control-label">销售时间</label>
				<s:hidden name="sale.saleTime" id="saleTime"></s:hidden>
				<div class="col-sm-6">
					<div class="input-group date form_date col-sm-6" data-date="" data-date-format="yyyy-MM-dd" data-link-field="saleTime" data-link-format="yyyy-mm-dd">
	                	<input class="form-control" size="16" type="text" value="<s:date name="sale.saleTime" format="yyyy-MM-dd"/>" readonly>
	                	<span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
						<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
					</div>
				</div>
			</div>
			
			<div class="form-group">
				<label class="col-sm-2 control-label">销售额</label>
				<div class="col-sm-2">
					<s:textfield name="sale.total" id="total"></s:textfield>
				</div>
			</div>
			
			<div class="form-group">
				<div class="col-sm-12 col-md-offset-5">
					<button type="submit" class="btn btn-primary" id="saveBtn">保存</button>
					<s:if test="sale.id!=null">
						<button type="button" class="btn btn-success" onclick="nextAdd()">继续添加</button>
					</s:if>
				</div>
			</div>
		</fieldset>
	</form>
</div>
<script type="text/javascript">

var mess = "${message}";
if(mess!=null && mess!="") {
	alert(mess);
}

function nextAdd(){
	window.location.href = "business/viewOtherSale.action";
}

function initValidator() {
	$('#editForm').bootstrapValidator({
      live: 'enabled',
      message: 'This value is not valid',
      feedbackIcons: {
          valid: 'glyphicon glyphicon-ok',
          invalid: 'glyphicon glyphicon-remove',
          validating: 'glyphicon glyphicon-refresh'
      },
      fields: {
          "sale.saleUserName":{
        	  validators: {
                  notEmpty: {
                      message: '销售人不能为空'
                  }
              }
          },
          "sale.saleTime": {
              validators: {
                  notEmpty: {
                      message: '销售时间不能为空'
                  }
              }
          },
          "sale.total": {
              validators: {
                  notEmpty: {
                      message: '销售额不能为空'
                  },
                  numeric:{
                	  message: '销售额必须为数值'
                  }
              }
          }
      }
  });
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

$(document).ready(initValidator());

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
	}
}

</script>
</BODY>
</html>
