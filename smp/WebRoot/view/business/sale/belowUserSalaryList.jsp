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

</head>

<BODY>
		<!-- 查询结果 -->
		<div class="panel panel-default">
			<div class="panel-heading">
				<h3 class="panel-title">第二步：填写销售额</h3>
			</div>
			<div class="panel-body">
				<div class="form-group">
					<label class="col-sm-2 control-label">本人当月销售额</label>
					<div class="col-sm-10 form-inline">
						<div class="input-group col-sm-10">
		                	<s:textfield name="salary.amount" class="form-control"></s:textfield>
		                	<s:hidden name="salary.comulativeAmount"></s:hidden>
						</div>
					</div>
				</div>
			</div>
			<div class="panel-heading">
				<h3 class="panel-title">下级网体当月累计销售额 </h3>
			</div>
			<div class="panel-body">
				<div class="col-sm-12">
					<table class="table table-condensed">
						<thead>
							<tr>
								<th>序号</th>
								<th>用户</th>
								<th>级别</th>
								<th>当月累计销售额</th>
							</tr>
						</thead>
						<tbody>
							<s:if test="salaryList==null || salaryList.size==0">
								<tr><td colspan="4">未找到下级网体数据</td></tr>
							</s:if>
							<s:else>
								<s:iterator value="salaryList" status="st">
									<!--<s:hidden name="salaryList[#st.index].comulativeAmount" theme="simple"></s:hidden>-->
									<input type="hidden" name="salaryList[<s:property value='#st.index'/>].comulativeAmount" value="<s:property value='salaryList[#st.index].comulativeAmount'/>"/>
									<input type="hidden" name="salaryList[<s:property value='#st.index'/>].userName" value="<s:property value='salaryList[#st.index].userName'/>"/>
									<input type="hidden" name="salaryList[<s:property value='#st.index'/>].userLoginName" value="<s:property value='salaryList[#st.index].userLoginName'/>"/>
									<tr>
										<td> <s:property value='#st.index+1' /></td>
										<td>
											<s:property value='userName' />(<s:property value='userLoginName' />)
										</td>
										<td>
											<!-- 
											<s:select list="#{'体验会员':'体验会员','正式会员':'正式会员','公司员工':'公司员工','高级员工':'高级员工','业务主管':'业务主管','高级主管':'高级主管','业务经理':'业务经理','高级经理':'高级经理' }" 
											listKey="key" listValue="value" name="salaryList[#st.index].userLevel" value="salaryList[#st.index].userLevel" theme="simple"></s:select>
											 -->
											<select name="salaryList[<s:property value='#st.index'/>].userLevel">
												<option value="体验会员" <s:if test="salaryList[#st.index].userLevel=='体验会员'"> selected="selected" </s:if>>体验会员</option>
												<option value="正式会员" <s:if test="salaryList[#st.index].userLevel=='正式会员'"> selected="selected" </s:if>>正式会员</option>
												<option value="公司员工" <s:if test="salaryList[#st.index].userLevel=='公司员工'"> selected="selected" </s:if>>公司员工</option>
												<option value="高级员工" <s:if test="salaryList[#st.index].userLevel=='高级员工'"> selected="selected" </s:if>>高级员工</option>
												<option value="业务主管" <s:if test="salaryList[#st.index].userLevel=='业务主管'"> selected="selected" </s:if>>业务主管</option>
												<option value="高级主管" <s:if test="salaryList[#st.index].userLevel=='高级主管'"> selected="selected" </s:if>>高级主管</option>
												<option value="业务经理" <s:if test="salaryList[#st.index].userLevel=='业务经理'"> selected="selected" </s:if>>业务经理</option>
												<option value="高级经理" <s:if test="salaryList[#st.index].userLevel=='高级经理'"> selected="selected" </s:if>>高级经理</option>
											</select>
										</td>
										<td>
											<!-- 
											<s:textfield name="salaryList[#st.index].comulativeAmountMonth" theme="simple"></s:textfield>
											 -->
											 <input type="text" name="salaryList[<s:property value='#st.index'/>].comulativeAmountMonth" value="<s:property value='salaryList[#st.index].comulativeAmountMonth'/>"/>
										</td>
									</tr>
								</s:iterator>
							</s:else>
						</tbody>
					</table>
				</div>
				<div class="form-group">
					<div class="col-sm-12 col-sm-offset-6">
						<button type="submit" class="btn btn-primary">计算</button>
					</div>
				</div>
			</div>
		</div>
</BODY>
</html>
