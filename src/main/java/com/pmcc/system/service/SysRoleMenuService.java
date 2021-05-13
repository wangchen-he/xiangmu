package com.pmcc.system.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.system.dao.SysRoleMenuDao;
import com.pmcc.system.model.SysRoleMenu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: SysRoleMenuService <br>
 * @Description: TODOdf
 * @Date: 2020/12/26 11:27 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysRoleMenuService extends CommonServiceImpl<SysRoleMenu, String> {

    @Autowired
    private SysRoleMenuDao sysRoleMenuDao;

    @Override
    protected AbstractBaseDao<SysRoleMenu, String> getEntityDao() {
        // TODO Auto-generated method stub
        return sysRoleMenuDao;
    }

    /**
     * 根据角色获取对应菜单
     * @param roleID
     * @return
     */
    public List<SysRoleMenu> getMenusByRoleID(String roleID) {
        return sysRoleMenuDao.getMenusByRoleID(roleID);
    }
}
