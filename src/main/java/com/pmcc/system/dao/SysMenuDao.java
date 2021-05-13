package com.pmcc.system.dao;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.system.model.SysMenu;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * @ClassName: SysMenyDao <br>
 * @Description: TODO导航
 * @Date: 2020/4/29 9:25 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Repository
public class SysMenuDao extends AbstractBaseDao<SysMenu, String> {

    /**
     * 获取目录根节点
     */
    public List<SysMenu> getMenuByPID(String node){
        Query query = entityManager
                .createNativeQuery("select * from sys_menu where parent_id=?1 and status!=?2 order by sort", SysMenu.class)
                .setParameter(1, node)
                .setParameter(2, CommonConstant.DEL_FLAG);  //2删除状态
        return query.getResultList();
    }

    /**
     * 获取系统菜单
     * 正常未禁用的菜单
     */
    public List<SysMenu> getSysMenu(String node){
        Query query = entityManager
                .createNativeQuery("select * from sys_menu where parent_id=?1 and status=?2 and disabled=?3 order by sort", SysMenu.class)
                .setParameter(1, node)
                .setParameter(2, CommonConstant.STATUS_NORMAL)  // 0 正常状态
                .setParameter(3, CommonConstant.STATUS_NORMAL);  //0 正常

        return query.getResultList();
    }

    /**
     * 保存根方法
     * @param text      名称
     * @param sort      排序
     * @param status    状态
     * @param operator  操作员
     * @param remarks   备注
     */
//    public void saveRoot(String text, String sort, String status, String operator, String remarks){

//        Query query = entityManager
//                .createNativeQuery("INSERT INTO sys_menu () VALUES(?1,?2,?3,?4,?5)", SysMenu.class)
//                .setParameter(1, node)
//                .setParameter(2, CommonConstant.DEL_FLAG);  //2删除状态
//    }
}
