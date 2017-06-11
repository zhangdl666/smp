package com.platform.organization.service;

import java.util.List;

import com.platform.core.bo.Page;
import com.platform.organization.bo.OrgUserBo;
import com.platform.organization.pojo.OrgUser;

public interface OrgUserService {

	public OrgUser getUser(String id);
	
	public OrgUser getUserByLoginName(String loginName);
	
	/**
	 * �û���ѯ
	 * @param userName
	 * @param loginName
	 * @param departmentId
	 * @param isContainChildDept
	 * @return
	 */
	public List<OrgUserBo> queryUsers(String userName,String loginName,String departmentId,boolean isContainChildDept);
	
	/**
	 * �û���ҳ��ѯ
	 * @param userName
	 * @param loginName
	 * @param departmentId
	 * @param isContainChildDept
	 * @return
	 */
	public Page queryUsers(String userName,String loginName,String departmentId,boolean isContainChildDept,Page page);
	
	public List<OrgUser> getUserListByRoleId(String roleId);

	public OrgUser saveUser(OrgUser user);
	
	public OrgUser saveUser(OrgUser user, String roleIds);
	
	public boolean delUser(String id);
}
