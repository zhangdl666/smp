package com.platform.organization.service;

import java.util.List;

import com.platform.core.bo.Page;
import com.platform.organization.bo.OrgRoleBo;
import com.platform.organization.pojo.OrgRole;

public interface OrgRoleService {

	public OrgRole getRole(String id);

	public OrgRoleBo getRoleBo(String id);
	
	/**
	 * ��ɫ��ѯ
	 * 
	 * @param roleName
	 *            ��ɫ����
	 * @param departmentId
	 *            ����id
	 * @param isContainParentDept
	 *            �Ƿ��ѯ�������µĽ�ɫ
	 * @param isContainChildDept
	 *            �Ƿ��ѯ�Ӳ����µĽ�ɫ
	 * @return
	 */
	public List<OrgRoleBo> queryRoles(String roleName, String departmentId,
			boolean isContainParentDept, boolean isContainChildDept);

	/**
	 * ��ɫ��ҳ��ѯ
	 * 
	 * @param roleName
	 *            ��ɫ����
	 * @param departmentId
	 *            ����id
	 * @param isContainParentDept
	 *            �Ƿ��ѯ�������µĽ�ɫ
	 * @param isContainChildDept
	 *            �Ƿ��ѯ�Ӳ����µĽ�ɫ
	 * @param page
	 *            ��ҳ
	 * @return
	 */
	public Page queryRoles(String roleName, String departmentId,
			boolean isContainParentDept, boolean isContainChildDept, Page page);

	public List<OrgRole> getRoleListByUserId(String userId);

	public OrgRole saveRole(OrgRole role);

	public boolean delRole(String id);

}
