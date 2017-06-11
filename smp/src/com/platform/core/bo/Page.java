package com.platform.core.bo;

import java.util.List;

/**
 * ��ҳpage
 * @author zdl
 *
 */
public class Page {

	/**
	 * �ܼ�¼��
	 */
	private int totalRowSize;
	
	/**
	 * ��ǰҳ
	 */
	private int currentPage;
	
	/**
	 * ÿҳ��¼��
	 */
	private int pageSize;

	/**
	 * ��ѯ���
	 */
	private List result;

	/**
     * �õ���ҳ��
     * @return ��ҳ��
     */
    public int getTotalPage(){
        int totalPage = (totalRowSize % pageSize == 0)? (totalRowSize / pageSize): (totalRowSize / pageSize) + 1;
        return totalPage;
    }
    
    /**
     * �õ���ǰ��ʼ��¼��
     * @param pageSize ÿҳ��¼��
     * @param currentPage ��ǰҳ
     * @return
     */
    public int getCurrentPageOffset()
    {
        int offset = pageSize * (currentPage - 1);
        return offset;
    }
    
    
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRowSize() {
		return totalRowSize;
	}

	public void setTotalRowSize(int totalRowSize) {
		this.totalRowSize = totalRowSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public List getResult() {
		return result;
	}

	public void setResult(List result) {
		this.result = result;
	}
	
	public static Page getDefaultPage() {
		Page page = new Page();
		page.setCurrentPage(1);
		page.setPageSize(10);
		return page;
	}
}
