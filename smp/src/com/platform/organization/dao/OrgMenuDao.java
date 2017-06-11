package com.platform.organization.dao;

import java.util.List;

import com.platform.core.bo.Page;
import com.platform.organization.bo.OrgMenuBo;
import com.platform.organization.pojo.OrgMenu;
import com.platform.organization.pojo.OrgMenuView;

public interface OrgMenuDao {

	/**
	 * ��ѯ�û��ɼ��˵��������û���¼��Ĳ˵�չ��
	 * @param userId
	 * @return
	 */
	public List<OrgMenuBo> getUserMenu(String userId);
	
	public OrgMenu getOrgMenu(String id);
	
	public OrgMenu saveOrgMenu(OrgMenu orgMenu);
	
	public boolean delMenu(String id);
	
	public List<OrgMenuView> queryMenus(String menuName,String url,String parentMenuId,boolean isContainChildMenu);
	
	public Page queryMenus(String menuName,String url,String parentMenuId,boolean isContainChildMenu,Page page);
}
