package com.platform.business.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.platform.business.bo.SaleQueryBo;
import com.platform.business.bo.SaleStat;
import com.platform.business.pojo.Product;
import com.platform.business.pojo.Sale;
import com.platform.business.service.BusinessNumberService;
import com.platform.business.service.ProductService;
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
	
	private String treeNodeData;
	private String checkedIds;
	
	private String tableStr;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
