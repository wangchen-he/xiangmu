package com.pmcc.system.service;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.system.dao.SysMenuRelationDao;
import com.pmcc.system.model.SysMenu;
import com.pmcc.system.model.SysMenuRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: SysMenuRelationService <br>
 * @Description: TODO 导航关系
 * @Date: 2020/10/15 15:21 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysMenuRelationService extends CommonServiceImpl<SysMenuRelation,String> {

    @Autowired
    private SysMenuRelationDao sysMenuRelationDao;

    @Override
    protected AbstractBaseDao<SysMenuRelation, String> getEntityDao() {
        return sysMenuRelationDao;
    }


    //根据映射菜单ID值获取对象
    public SysMenuRelation getByMenuID(String id) {
        return sysMenuRelationDao.getByMenuID(id);
    }

    /**
     * 根据映射表父ID 获取子集
     *
     * @param parentID
     * @return
     */
    public List<SysMenu> getchildrens(String parentID) {
        List<SysMenu> depList = new ArrayList<>();
        List<SysMenuRelation> reList = (List<SysMenuRelation>) findById(parentID);

        if (reList.size() > 0) {
            for (SysMenuRelation item : reList) {
                SysMenu sysDep = item.getMenuID();
                depList.add(sysDep);
            }
        }
        return depList;
    }
    /**
     * 判断同一级下 名称  链接不重复
     *
     * @param parentID
     * @param menuLink
     */
    public ApiResult checkRepeat(String parentID, String menuLink) {
        List<SysMenu> list = getchildrens(parentID); //父机构下所有子集
        if (list.size() > 0) {
            for (SysMenu item : list) {
                //判断名称
                if (parentID.equals(item.getText())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG + "同一级别下不允许重名！");
                }
                //判断链接
                if (menuLink.equals(item.getLink())) {
                    return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG + "同一级别下不允许跳转链接相同！");
                }
            }
        }
        return null;
    }
}
