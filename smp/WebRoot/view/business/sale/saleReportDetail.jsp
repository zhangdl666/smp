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

<title></title>
<!-- Bootstrap core CSS -->
<link href="css/bootstrap.min.css" rel="stylesheet">

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<link href="css/ie10-viewport-bug-workaround.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="css/sticky-footer-navbar.css" rel="stylesheet">

<link href="css/dashboard.css" rel="stylesheet">

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
</head>

<BODY>
	<div class="col-sm-12">
		<s:if test="tableStr!=null">
			<s:property value="tableStr" escape="false"/>
		</s:if>
		<s:else>
		<table class="table table-striped">
			<thead>
				<tr>
					<th>序号</th>
					<th>单号</th>
					<th>销售时间</th>
					<th>销售人</th>
					<th>销售额</th>
				</tr>
			</thead>
			<tbody>
				<s:if test="saleList==null || saleList.size==0">
					<tr>
						<td colspan="5">未找到任何数据！</td>
					</tr>
				</s:if>
				<s:else>
					<s:iterator value="saleList" status="st">
						<tr>
							<td><s:property value='#st.index+1' />
							</td>
							<td><s:property value='businessNumber' />
							</td>
							<td><s:date name="saleTime"
									format="yyyy-MM-dd"></s:date>
							</td>
							<td> 
								<s:property value='saleUserName' />
							</td>
							<td>
								<s:property value='total' />
							</td>
							
						</tr>
					</s:iterator>
				</s:else>
			</tbody>
		</table>
		</s:else>
	</div>
<script type="text/javascript">

</script>
</BODY>
</html>
