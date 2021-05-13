package com.pmcc.system.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.system.dao.SysRoleUserDao;
import com.pmcc.system.model.SysRoleMenu;
import com.pmcc.system.model.SysRoleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysRoleUserService <br>
 * @Description: TODO角色用户对照表
 * @Date: 2020/12/26 11:28 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysRoleUserService extends CommonServiceImpl<SysRoleUser, String> {

    @Autowired
    private SysRoleUserDao sysRoleUserDao;

    @Override
    protected AbstractBaseDao<SysRoleUser, String> getEntityDao() {
        // TODO Auto-generated method stub
        return sysRoleUserDao;
    }

    /**
     * 根据角色获取对应用户
     * @param roleID
     * @return
     */
    public List<SysRoleUser> getUsersByRoleID(String roleID) {
        return sysRoleUserDao.getUsersByRoleID(roleID);
    }

    /**
     * 根据用户获取角色列表
     * @param userID
     * @return
     */
    public List<SysRoleUser> getRoleByUserID(String userID) {
        return sysRoleUserDao.getRoleByUserID(userID);
    }

}
