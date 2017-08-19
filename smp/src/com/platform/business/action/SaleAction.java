package com.platform.business.action;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.pojo.Product;
import com.platform.business.pojo.Salary;
import com.platform.business.pojo.Sale;
import com.platform.business.service.BusinessNumberService;
import com.platform.business.service.ProductService;
import com.platform.business.service.SalaryService;
import com.platform.business.service.SaleService;
import com.platform.core.bo.Page;
import com.platform.organization.bo.OrgUserBo;
import com.platform.organization.bo.TextToPinyin;
import com.platform.organization.bo.TreeNode;
import com.platform.organization.pojo.OrgUser;
import com.platform.organization.service.OrgUserService;

public class SaleAction extends BaseAction {
	private final Logger logger = Logger.getLogger(SaleAction.class);
	
	private String message;
	private String id;
	private Sale sale;
	private SaleQueryBo saleQueryBo;
	private List<Sale> saleList;
	private List<Product> productList;
	private SaleService saleService;
	private BusinessNumberService businessNumberService;
	private ProductService productService;
	private OrgUserService orgUserService;
	private SalaryService salaryService;
	private String year;
	private String month;
	
	private List<Salary> salaryList;
	private String treeNodeData;
	private String checkedIds;
	private Salary salary;
	private Salary prepareSalary;
	
	private String tableStr;
	
	private String name;
	private String saleUserName;
	private String saleUserId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSaleUserName() {
		return saleUserName;
	}

	public void setSaleUserName(String saleUserName) {
		this.saleUserName = saleUserName;
	}

	public String getSaleUserId() {
		return saleUserId;
	}

	public void setSaleUserId(String saleUserId) {
		this.saleUserId = saleUserId;
	}

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	public Salary getPrepareSalary() {
		return prepareSalary;
	}

	public void setPrepareSalary(Salary prepareSalary) {
		this.prepareSalary = prepareSalary;
	}

	public String getTableStr() {
		return tableStr;
	}

	public void setTableStr(String tableStr) {
		this.tableStr = tableStr;
	}

	public OrgUserService getOrgUserService() {
		return orgUserService;
	}

	public void setOrgUserService(OrgUserService orgUserService) {
		this.orgUserService = orgUserService;
	}

	public String getTreeNodeData() {
		return treeNodeData;
	}

	public void setTreeNodeData(String treeNodeData) {
		this.treeNodeData = treeNodeData;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public SalaryService getSalaryService() {
		return salaryService;
	}

	public void setSalaryService(SalaryService salaryService) {
		this.salaryService = salaryService;
	}

	public List<Salary> getSalaryList() {
		return salaryList;
	}

	public void setSalaryList(List<Salary> salaryList) {
		this.salaryList = salaryList;
	}

	public String getCheckedIds() {
		return checkedIds;
	}

	public void setCheckedIds(String checkedIds) {
		this.checkedIds = checkedIds;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public BusinessNumberService getBusinessNumberService() {
		return businessNumberService;
	}

	public void setBusinessNumberService(BusinessNumberService businessNumberService) {
		this.businessNumberService = businessNumberService;
	}

	private Page page;	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public SaleQueryBo getSaleQueryBo() {
		return saleQueryBo;
	}

	public void setSaleQueryBo(SaleQueryBo saleQueryBo) {
		this.saleQueryBo = saleQueryBo;
	}

	public List<Sale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<Sale> saleList) {
		this.saleList = saleList;
	}

	public SaleService getSaleService() {
		return saleService;
	}

	public void setSaleService(SaleService saleService) {
		this.saleService = saleService;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	/**
	 * 我的数据录入
	 * @return
	 */
	public String viewMySale(){
		
		OrgUser loginUser = getLoginUser();
		if(id == null || id.equals("")) {
			sale = new Sale();
			String businessNumber = businessNumberService.getNumber("S");
			sale.setBusinessNumber(businessNumber);
			sale.setRecordType(Sale.RECORD_TYPE_TOTAL);
			sale.setRecordTime(Calendar.getInstance().getTime());
			sale.setRecordUserId(loginUser.getId());
			sale.setRecordUserName(loginUser.getUserName());
			sale.setSaleTime(Calendar.getInstance().getTime());
			sale.setSaleUserId(loginUser.getId());
			sale.setSaleUserName(loginUser.getUserName());
			sale.setValidstatus("1");
		}else {
			sale = saleService.getSale(id);
			if(sale.getProductId()!=null){
				Product p = productService.getProduct(sale.getProductId());
				name = p.getName();
			}
		}
		
		return SUCCESS;
	}
	
	// 选择销售人
	public String selectSaleUser() {
		List<OrgUserBo> users = orgUserService.queryUsers(null, null,
				null, true);

		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		treeNodeData = "";

		// 先放入部门节点
		List<String> checkIdList = new ArrayList<String>();

		// 再放入用户节点
		if (checkedIds != null && !"".equals(checkedIds)) {
			String[] s = checkedIds.split(",");
			Collections.addAll(checkIdList, s);
		}
		if (users != null && users.size() > 0) {
			for (int i = 0; i < users.size(); i++) {
				OrgUserBo user = users.get(i);
				TreeNode tn = new TreeNode();
				tn.setId(user.getId());
				tn.setName(user.getUserName() + "（" + user.getLoginName() + "）");
				tn.setpId(user.getManagerId());
				tn.setPinyin(TextToPinyin.getPingYin(user.getUserName() + "（" + user.getLoginName() + "）"));
				if (checkIdList.contains(user.getId())) {
					tn.setChecked(true);
				}
				treeNodes.add(tn);
			}
			JSONArray jsonArray = JSONArray.fromObject(treeNodes);
			treeNodeData = jsonArray.toString();
		}
		return SUCCESS;
	}
	
	/**
	 * 他人数据录入
	 * @return
	 */
	public String viewOtherSale(){
		OrgUser loginUser = getLoginUser();
		if(id == null || id.equals("")) {
			sale = new Sale();
			String businessNumber = businessNumberService.getNumber("S");
			sale.setBusinessNumber(businessNumber);
			sale.setRecordType(Sale.RECORD_TYPE_TOTAL);
			sale.setRecordTime(Calendar.getInstance().getTime());
			sale.setRecordUserId(loginUser.getId());
			sale.setRecordUserName(loginUser.getUserName());
			sale.setSaleTime(Calendar.getInstance().getTime());
			sale.setValidstatus("1");
		}else {
			sale = saleService.getSale(id);
			Product p = productService.getProduct(sale.getProductId());
			name = p.getName();
		}
		return SUCCESS;
	}
	
	public String saveMySale() {
		sale = saleService.saveSale(sale);
		Product p = productService.getProduct(sale.getProductId());
		name = p.getName();
		message = "保存成功";
		return SUCCESS;
	}
	
	//选择产品
	public String selectProduct() {
		if(page == null) {
			page = Page.getDefaultPage();
		}
		page = productService.queryProduct(name, page);
		productList = page.getResult();
		return SUCCESS;
	}
	
	public String saveOtherSale() {
		sale = saleService.saveSale(sale);
		productList = productService.queryProduct(null);
		message = "保存成功";
		return SUCCESS;
	}
	
	public String viewQuerySalePage(){
		if(page == null) {
			page = Page.getDefaultPage();
		}
		
		//设置默认查询条件
		if(saleQueryBo == null) {
			saleQueryBo = new SaleQueryBo();
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.MONTH, 0);
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			saleQueryBo.setSaleTimeFrom(calendar.getTime());
		}
		
		page = saleService.querySale(saleQueryBo, page);
		saleList = page.getResult();
		return SUCCESS;
	}
	
	public String deleteSaleById(){
		String id = this.getRequest().getParameter("saleId");
		saleService.deleteSale(id);
		page = saleService.querySale(saleQueryBo, page);
		saleList = page.getResult();
		return SUCCESS;
	}
	
	public String querySale(){
		if(page == null) {
			page = Page.getDefaultPage();
		}
		
		page = saleService.querySale(saleQueryBo, page);
		saleList = page.getResult();
		return SUCCESS;
	}
	
	//销售额汇总
	public String saleReport() throws Exception{
		//初始化参数
		if(saleQueryBo == null) {
			saleQueryBo = new SaleQueryBo();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			saleQueryBo.setSaleTimeFrom(cal.getTime());
		}
		
		//查询
		
		
		return SUCCESS;
	}
	
	public String viewSignsReportDetail() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("year", getRequest().getParameter("year"));
		params.put("month", getRequest().getParameter("month"));
		params.put("rowName", getRequest().getParameter("rowName"));
		params.put("columnName", getRequest().getParameter("columnName"));
//		signList = workHireReportService.viewWorkHireDetails(params);
		return SUCCESS;
	}
	
	public String viewSalaryCalcuPage() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		Date date = calendar.getTime();
		//初始化为上月
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String yearMonth = sdf.format(date);
		salaryList = salaryService.querySalary(yearMonth);
		year = yearMonth.substring(0, 4);
		month = yearMonth.substring(4);
		return SUCCESS;
	}
	
	public String salaryCalcu(){
		salaryService.calcuSalary(year + month);
		salaryList = salaryService.querySalary(year + month);
		return SUCCESS;
	}
	
	public String salaryReCalcu(){
		salaryService.deleteSalaryByYearMonth(year + month);
		salaryService.calcuSalary(year + month);
		salaryList = salaryService.querySalary(year + month);
		return SUCCESS;
	}
	
	public String salaryQuery(){
		salaryList = salaryService.querySalary(year + month);
		return SUCCESS;
	}
	
	//跳转到工资预计算页面
	public String viewPrepareSalaryCalcuPage(){
		Date date = Calendar.getInstance().getTime();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf2 = new SimpleDateFormat("MM");
		year = sdf1.format(date);
		month = sdf2.format(date);
		return SUCCESS;
	}
	
	//工资预计算
	public String prepareSalaryCalcu(){
		//第一步  计算用户级别及返利标准
		double comulativeAmount = (salary.getComulativeAmount()==null?0:salary.getComulativeAmount()) + (salary.getAmount()==null?0:salary.getAmount());
		int c = 0;//下级累计销售额大于2.8万网体个数
		String userLevel = "";
		if(salaryList!=null && salaryList.size()>0) {
			for(int i=0;i<salaryList.size();i++){
				Salary s = salaryList.get(i);
				if((s.getComulativeAmountMonth()==null?0:s.getComulativeAmountMonth() + s.getComulativeAmount()) > 28000){
					c = c + 1;
				}
				comulativeAmount = comulativeAmount + s.getComulativeAmount();
			}
		}
		
		if(c>=6){
			userLevel = "高级经理";
		}else if(c>=3){
			userLevel = "业务经理";
		}else if(c>=2){
			userLevel = "高级主管";
		}else if(c>=1){
			userLevel = "业务主管";
		}else if(comulativeAmount >= 10000){
			userLevel = "高级员工";
		}else if(comulativeAmount >= 3000){
			userLevel = "公司员工";
		}else if(comulativeAmount >= 500){
			userLevel = "正式会员";
		}else{
			userLevel = "体验会员";
		}
		
		//计算工资A
		double salaryA = 0;
		double salaryB = 0;
		if(salaryList==null || salaryList.size()==0){//无网体
			if(userLevel.equals("") || userLevel.equals("") || userLevel.equals("")) {
				salaryA = salary.getAmount() * 0.24;
			}else {
				salaryA = salary.getAmount() * getRebate(userLevel);
			}
		}else {
			salaryA = salary.getAmount() * getRebate(userLevel);
		}
		
		//计算工资B
		if(isHaveSalaryB(userLevel, salary.getAmount())){
			if(salaryList!=null && salaryList.size()>0){
				double rebate = getRebate(userLevel);
				for(int j=0; j<salaryList.size(); j++) {
					Salary s = salaryList.get(j);
					salaryB = salaryB + (rebate - getRebate(s.getUserLevel())) * (s.getComulativeAmountMonth()==null?0:s.getComulativeAmountMonth());
				}
			}
		}
		
		//工资合计
		prepareSalary = new Salary();
		prepareSalary.setSalaryA(salaryA);
		prepareSalary.setSalaryB(salaryB);
		prepareSalary.setSalaryTotal(salaryA + salaryB);
		return SUCCESS;
	}
	
	private double getRebate(String userLevel) {
		if(userLevel.equals("体验会员")){
			return 0.1;
		}else if(userLevel.equals("正式会员")){
			return 0.15;
		}else if(userLevel.equals("公司员工")){
			return 0.18;
		}else if(userLevel.equals("高级员工")){
			return 0.21;
		}else if(userLevel.equals("业务主管")){
			return 0.24;
		}else if(userLevel.equals("高级主管")){
			return 0.26;
		}else if(userLevel.equals("业务经理")){
			return 0.28;
		}else if(userLevel.equals("高级经理")){
			return 0.30;
		}
		return 0;
	}
	
	private boolean isHaveSalaryB(String userLevel,double amount){
		if(userLevel.equals("正式会员") && amount >= 10){
			return true;
		}else if(userLevel.equals("公司员工") && amount >= 10){
			return true;
		}else if(userLevel.equals("高级员工") && amount >= 100){
			return true;
		}else if(userLevel.equals("业务主管") && amount >= 100){
			return true;
		}else if(userLevel.equals("高级主管") && amount >= 200){
			return true;
		}else if(userLevel.equals("业务经理") && amount >= 300){
			return true;
		}else if(userLevel.equals("高级经理") && amount >= 500){
			return true;
		}
		return false;
	}
	
	//查询其下级网体上月工资数据
	public String showBelowUserList(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		try {
			date = sdf.parse(year + month);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.MONTH, -1);
		String yearMonth = sdf.format(cal.getTime());
		salary = salaryService.getSalary(yearMonth, id);
		salaryList = salaryService.queryBelowUserSalary(yearMonth, id);
		return SUCCESS;
	}
	
	 public String formatDouble(double s){
	      DecimalFormat fmt = new DecimalFormat("0.00");
	      return fmt.format(s);
	  }
	
}
