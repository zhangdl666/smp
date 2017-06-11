package com.platform.report.action;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.platform.business.action.BaseAction;
import com.platform.business.bo.SaleQueryBo;
import com.platform.business.pojo.Sale;
import com.platform.report.po.Report;
import com.platform.report.service.SaleReportService;

public class SaleReportAction extends BaseAction {

	private Date startTime;
	private Date endTime;
	private String tableStr;
	private List<Sale> saleList;
	
	private SaleReportService saleReportService;
	
	public String getTableStr() {
		return tableStr;
	}

	public void setTableStr(String tableStr) {
		this.tableStr = tableStr;
	}


	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public List<Sale> getSaleList() {
		return saleList;
	}

	public void setSaleList(List<Sale> saleList) {
		this.saleList = saleList;
	}

	public SaleReportService getSaleReportService() {
		return saleReportService;
	}

	public void setSaleReportService(SaleReportService saleReportService) {
		this.saleReportService = saleReportService;
	}
	private SaleQueryBo saleQueryBo;

	public SaleQueryBo getSaleQueryBo() {
		return saleQueryBo;
	}

	public void setSaleQueryBo(SaleQueryBo saleQueryBo) {
		this.saleQueryBo = saleQueryBo;
	}

	public String viewSaleReport() throws Exception{
		//初始化参数
		if(saleQueryBo == null) {
			saleQueryBo = new SaleQueryBo();
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			saleQueryBo.setSaleTimeFrom(cal.getTime());
		}
		
		//查询
		try {
			Report report = saleReportService.statSale(saleQueryBo);
			
			//生成table
			com.platform.report.datadef.Data2D dataSet = report.getDataSet();
			List rowKeys = dataSet.getRowKeys();
			List columnKeys = dataSet.getColumnKeys();
			if(rowKeys == null || rowKeys.size() == 0) {
				tableStr = "未查到符合条件的数据！";
			}else {
				com.platform.report.po.Table table = new com.platform.report.po.Table();
				table.setTableId("xiaoshouhuizong");
				table.setTableCssClass("table table-bordered");
				table.setTrClassOne("one");
				table.setTrClassTwo("two");
				table.setBorder("0");
				table.setCellpadding("0");
				table.setCellspacing("0");
				
				//列标题
				List<com.platform.report.po.ColumnTitle> columnTitleList = new ArrayList<com.platform.report.po.ColumnTitle>();
				for(int i=0; i<columnKeys.size(); i++) {
					String value = (String)columnKeys.get(i);
					if(value == null) {
						continue;
					}
					
					com.platform.report.po.ColumnTitle columnTitle = new com.platform.report.po.ColumnTitle();
					columnTitle.setName(value);
					columnTitle.setDisplayName(value);
					columnTitle.setViewDetail(true);
					columnTitle.setShowType(com.platform.report.po.Table.SHOW_TYPE_NUMBER);
					columnTitle.setDecimals(0);
					//columnTitle.setCssStyle("text-align:center");
					columnTitleList.add(columnTitle);
				}
				
				table.setColumnTitleList(columnTitleList);
				
				//行标题
				List<com.platform.report.po.RowTitle> rowTitleList = new ArrayList<com.platform.report.po.RowTitle>();
				
				for(int i=0; i< rowKeys.size(); i++) {
					String value = (String)(rowKeys.get(i));
					if(value == null){
						continue;
					}
					com.platform.report.po.RowTitle rowTitle = new com.platform.report.po.RowTitle();
					String s[] = value.split("-");
					rowTitle.setName(value);
					rowTitle.setDisplayName(s[1]);
					rowTitle.setParentRowName(null);
					//rowTitle.setCssStyle("text-align:center");
					rowTitle.setViewDetail(true);
					rowTitleList.add(rowTitle);
				}
				
				table.setRowTitleList(rowTitleList);
				
				//生成表格
				com.platform.report.util.ReportShowUtil util = new com.platform.report.util.ReportShowUtil();
				tableStr = util.generateTable(report, table, false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		
		return SUCCESS;
	}
	
	public String viewSaleReportDetail() throws Exception {
		getRequest().setCharacterEncoding("UTF-8");
		String rowName = getRequest().getParameter("rowName");
		rowName = new String(rowName .getBytes("iso8859-1"),"UTF-8"); 
		String columnName = getRequest().getParameter("columnName");
		columnName = new String(columnName .getBytes("iso8859-1"),"UTF-8");
		if("累计额度".equals(columnName)) {
			try {
				String userId = "";
				if(rowName!=null && !rowName.equals("")){
					String[] s = rowName.split("-");
					userId = s[0];
				}
				Report report = saleReportService.statSaleForSpecifyUser(saleQueryBo, userId);
				
				//生成table
				com.platform.report.datadef.Data2D dataSet = report.getDataSet();
				List rowKeys = dataSet.getRowKeys();
				List columnKeys = dataSet.getColumnKeys();
				if(rowKeys == null || rowKeys.size() == 0) {
					tableStr = "未查到符合条件的数据！";
				}else {
					com.platform.report.po.Table table = new com.platform.report.po.Table();
					table.setTableId("xiaoshouhuizong");
					table.setTableCssClass("table table-bordered");
					table.setTrClassOne("one");
					table.setTrClassTwo("two");
					table.setBorder("0");
					table.setCellpadding("0");
					table.setCellspacing("0");
					
					//列标题
					List<com.platform.report.po.ColumnTitle> columnTitleList = new ArrayList<com.platform.report.po.ColumnTitle>();
					for(int i=0; i<columnKeys.size(); i++) {
						String value = (String)columnKeys.get(i);
						if(value == null) {
							continue;
						}
						
						com.platform.report.po.ColumnTitle columnTitle = new com.platform.report.po.ColumnTitle();
						columnTitle.setName(value);
						columnTitle.setDisplayName(value);
						columnTitle.setViewDetail(false);
						columnTitle.setShowType(com.platform.report.po.Table.SHOW_TYPE_NUMBER);
						columnTitle.setDecimals(0);
						//columnTitle.setCssStyle("text-align:center");
						columnTitleList.add(columnTitle);
					}
					
					table.setColumnTitleList(columnTitleList);
					
					//行标题
					List<com.platform.report.po.RowTitle> rowTitleList = new ArrayList<com.platform.report.po.RowTitle>();
					
					for(int i=0; i< rowKeys.size(); i++) {
						String value = (String)(rowKeys.get(i));
						if(value == null){
							continue;
						}
						com.platform.report.po.RowTitle rowTitle = new com.platform.report.po.RowTitle();
						String s[] = value.split("-");
						rowTitle.setName(value);
						rowTitle.setDisplayName(s[1]);
						rowTitle.setParentRowName(null);
						//rowTitle.setCssStyle("text-align:center");
						rowTitle.setViewDetail(false);
						rowTitleList.add(rowTitle);
					}
					
					table.setRowTitleList(rowTitleList);
					
					//生成表格
					com.platform.report.util.ReportShowUtil util = new com.platform.report.util.ReportShowUtil();
					tableStr = util.generateTable(report, table, false);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
		}else {
			saleList = saleReportService.viewSaleDetails(saleQueryBo,rowName,columnName);
		}
		return SUCCESS;
	}
}
