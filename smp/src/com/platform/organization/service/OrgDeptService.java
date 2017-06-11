package com.platform.organization.service;

import java.util.List;

import com.platform.core.bo.Page;
import com.platform.organization.pojo.OrgDept;
import com.platform.organization.pojo.OrgDeptView;

public interface OrgDeptService {

	public OrgDept getOrgDept(String id);

	public OrgDept saveDept(OrgDept dept);

	public boolean delDept(String id);

	/**
	 * ���Ų�ѯ
	 * @param deptName ��������
	 * @param parentDeptId ������id
	 * @param isContainChildDept �Ƿ��ѯ�Ӳ���
	 * @return
	 */
	public List<OrgDeptView> queryDepts(String deptName,String parentDeptId,boolean isContainChildDept);
	
	/**
	 * ���ŷ�ҳ��ѯ
	 * @param deptName ��������
	 * @param parentDeptId ������id
	 * @param isContainChildDept �Ƿ��ѯ�Ӳ���
	 * @param page ��ҳ����
	 * @return
	 */
	public Page queryDepts(String deptName,String parentDeptId,boolean isContainChildDept,Page page);
	
	/**
	 * ��ȡָ�����ŵ�ֱ����˾����deptIDΪ��˾ID���򷵻ر���˾
	 * @param deptId
	 * @return
	 */
	public OrgDept getDirectCompany(String deptId);
}
