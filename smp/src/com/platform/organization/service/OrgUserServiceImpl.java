package com.platform.organization.service;

import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.platform.core.bo.Page;
import com.platform.organization.bo.OrgUserBo;
import com.platform.organization.bo.OrgUserRoleBo;
import com.platform.organization.dao.OrgUserDao;
import com.platform.organization.dao.OrgUserRoleDao;
import com.platform.organization.pojo.OrgUser;
import com.platform.organization.pojo.OrgUserRole;

public class OrgUserServiceImpl implements OrgUserService {
	private final Logger logger = Logger.getLogger(OrgUserServiceImpl.class);
	
	@Autowired
	private OrgUserDao orgUserDao;
	
	@Autowired
	private OrgUserRoleDao OrgUserRoleDao;
	
	public void setOrgUserDao(OrgUserDao orgUserDao) {
		this.orgUserDao = orgUserDao;
	}

	@Override
	public OrgUser getUser(String id) {
		return orgUserDao.getUser(id);
	}

	public OrgUserRoleDao getOrgUserRoleDao() {
		return OrgUserRoleDao;
	}

	public void setOrgUserRoleDao(OrgUserRoleDao orgUserRoleDao) {
		OrgUserRoleDao = orgUserRoleDao;
	}

	public OrgUserDao getOrgUserDao() {
		return orgUserDao;
	}

	@Override
	public boolean delUser(String id) {
		return orgUserDao.delUser(id);
	}

	@Override
	public OrgUser getUserByLoginName(String loginName) {
		return orgUserDao.getUserByLoginName(loginName);
	}

	@Override
	public List<OrgUser> getUserListByRoleId(String roleId) {
		return orgUserDao.getUserListByRoleId(roleId);
	}

	@Override
	public OrgUser saveUser(OrgUser user) {
		user = orgUserDao.saveUser(user);
		return user;
	}
	
	@Override
	public OrgUser saveUser(OrgUser user, String roleIds) {
		user = orgUserDao.saveUser(user);
		// �����ɫ��Ϣ
		List<OrgUserRoleBo> userRoleList = OrgUserRoleDao.getUserRoleListByUserId(user.getId());
		
		if(roleIds == null || roleIds.equals("")) {
			roleIds = "";
		}
		//����ɾ������
		String oldRoleIds = ",";
		String tempRoleIds = "," + roleIds + ",";
		if(userRoleList != null && userRoleList.size() > 0) {
			for(int i=0; i<userRoleList.size(); i++) {
				OrgUserRoleBo ur = userRoleList.get(i);
				oldRoleIds = oldRoleIds + ur.getRole().getId() + ",";
				if(tempRoleIds.indexOf("," + ur.getRole().getId() + ",") == -1){
					//�û����µĽ�ɫ�б���û�д˽�ɫ�����ɾ��
					logger.info("�û���ɫ������ɾ���û�" + user.getLoginName() + " roleID��" + ur.getRole().getId());
					OrgUserRoleDao.deleteUserRole(ur.getId());
				}
			}
		}
		
		//������Ӳ���
		String[] s = roleIds.split(",");
		
		for (int i = 0; i < s.length; i++) {
			String rid = s[i];
			if(oldRoleIds.indexOf(rid) == -1) {
				//Ϊ�û�������ɫ
				logger.info("�û���ɫ�������û�" + user.getLoginName() + " ����roleID��" + rid);
				OrgUserRole ur = new OrgUserRole();
				ur.setUserId(user.getId());
				ur.setRoleId(rid);
				ur.setCreateTime(Calendar.getInstance().getTime());
				OrgUserRoleDao.saveUserRole(ur);
			}
			
		}
		return user;
	}

	@Override
	public List<OrgUserBo> queryUsers(String userName, String loginName,
			String departmentId, boolean isContainChildDept) {
		return orgUserDao.queryUsers(userName, loginName, departmentId, isContainChildDept);
	}

	@Override
	public Page queryUsers(String userName, String loginName,
			String managerId, boolean isContainChildDept, Page page) {
		return orgUserDao.queryUsers(userName, loginName, managerId, isContainChildDept, page);
	}

}
