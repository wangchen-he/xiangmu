package com.pmcc.system.service;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.system.dao.SysRoleDao;
import com.pmcc.system.model.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ClassName: SysRoleService <br>
 * @Description: TODO角色表
 * @Date: 2020/12/26 11:24 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysRoleService extends CommonServiceImpl<SysRole, String> {

    @Autowired
    private SysRoleDao sysRoleDao;

    @Override
    protected AbstractBaseDao<SysRole, String> getEntityDao() {
        // TODO Auto-generated method stub
        return sysRoleDao;
    }

    /**
     * 根据角色列表
     */
    public List<SysRole> getRole(){
        return sysRoleDao.getRole();
    }

    /**
     * 判断名称不重复
     *
     * @param roleName  名称
     */
    public ApiResult checkRepeat(String roleName) {
        List<SysRole> list = sysRoleDao.getRoleByName(roleName);
        if(list.size()>0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG + "角色不允许重名！");
        }
        return null;
    }
}
