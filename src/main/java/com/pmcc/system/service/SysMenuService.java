package com.pmcc.system.service;

import com.alibaba.fastjson.JSONObject;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.entity.TreeTableEntity;
import com.pmcc.system.dao.SysMenuDao;
import com.pmcc.system.dao.SysRoleMenuDao;
import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysMenu;
import com.pmcc.system.model.SysRoleMenu;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: SysMenuService <br>
 * @Description: TODO菜单
 * @Date: 2020/4/29 9:26 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@Service
@Transactional
public class SysMenuService extends CommonServiceImpl<SysMenu, String> {

    @Autowired
    private SysMenuDao sysMenuDao;
    @Autowired
    SysUserDao sysUserDao;  //注入用户查询操作类
    @Autowired
    private SysRoleMenuDao sysRoleMenuDao; //注入菜单关系映射表

    @Override
    protected AbstractBaseDao<SysMenu, String> getEntityDao() {
        return sysMenuDao;
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
//        sysMenuDao.saveRoot(text, sort, status, operator, remarks);
//    }

    /**
     * 查询所有菜单
     *
     * @return
     */
    public List<SysMenu> getMenuTree() {
        List<SysMenu> list = sysMenuDao.findAll(SysMenu.class);
        return list;
    }

    @Transactional(readOnly = true)
    public List<TreeTableEntity> getTree(String prID) {
        List<TreeTableEntity> list = new ArrayList<>();
        String node = "-1";
        if (prID.length() > 0) {
            node = prID;
        }
        //加载根节点(根节点父ID为-1,没有对应值，用sql查询)
        List<SysMenu> rootList = sysMenuDao.getMenuByPID(node);
        if (rootList.size() > 0) {
            list.addAll(allTree(rootList)); //加载所有机构树
        }
        return list;
    }

    /**
     * 根据菜单关系列表 循环判断加载子菜单
     *
     * @param mlist
     * @return
     */
    private List<TreeTableEntity> allTree(List<SysMenu> mlist) {
        List<TreeTableEntity> list = new ArrayList<>();
        //循环机构关系列表
        for (SysMenu item : mlist) {
            TreeTableEntity entity = new TreeTableEntity();
            entity.setKey(item.getId());
            entity.setText(item.getText());         //菜单名称
            entity.setLink(item.getLink());         //链接
            entity.setType(item.getType());         //类型
            entity.setIcon(item.getIcon());         //图标
            entity.setSort(item.getSort());         //排序
            entity.setStatus(item.getStatus());     //保存状态
            entity.setDisabled(item.getDisabled()); //使用状态
            //判断子集 (因为有假删除，这里需要另外查询)
            List<SysMenu> seList = sysMenuDao.getMenuByPID(item.getId());
            if (seList.size() > 0) {
                //有子  无限向下查询
                entity.setChildren(allTree(seList));
            } else {
                //无子
                entity.setLeaf(true);
            }
            list.add(entity);
        }
        return list;
    }

    /**
     * 根据映射表父ID 获取子集
     *
     * @param parentID
     * @return
     */
//    public List<SysMenu> getchildrens(String parentID) {
//        List<SysMenu> depList = new ArrayList<>();
//        List<SysMenu> reList = sysMenuDao.getMenuByPID(parentID);
//        if (reList.size() > 0) {
//            for (SysMenu item : reList) {
//                SysMenu sysDep = item;
//                depList.add(sysDep);
//            }
//        }
//        return depList;
//    }

    /**
     * 判断同一级下 名称  链接不重复
     *
     * @param menuID   菜单ID
     * @param parentID 父ID
     * @param menuText 名称
     * @param menuLink 链接
     */
    public ApiResult checkRepeat(String menuID, String parentID, String menuText, String menuLink) {
        List<SysMenu> list = sysMenuDao.getMenuByPID(parentID); //父机构下所有子集
        if (list.size() > 0) {
            for (SysMenu item : list) {
                //修改数据时过滤自身  根据ID判断  不同ID不可同名  同链接
                if (!item.getId().equals(menuID)) {
                    //判断名称
                    if (menuText.equals(item.getText())) {
                        return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG + "同一级别下不允许重名！");
                    }
                    //判断链接
                    if (menuLink.equals(item.getLink()) && !"".equals(menuLink)) {
                        return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG + "同一级别下不允许跳转链接相同！");
                    }
                }
            }
        }
        return null;
    }


    /**
     * 根据角色组成系统菜单
     *
     * @return
     */
    public List<Map<String, Object>> systemMenu(List<String> userMenu) {
        String prID = CommonConstant.SYS_MENU;   //数据库目录初始化根节点 ID
        //根据用户查询权限后返回所有菜单
        List<SysMenu> rootList = sysMenuDao.getSysMenu(prID);
        List<Map<String, Object>> mapMenu = new ArrayList<>();
        if (rootList.size() > 0) {
            for (SysMenu menu : rootList) {
                if(userMenu.stream().anyMatch(item->item.equals(menu.getId()))){
                    Map<String, Object> map = userMenuInfo(menu, userMenu);
                    mapMenu.add(map);
                }
            }
        }
        return mapMenu;
    }

    /**
     * 用户菜单详情
     * @param menu
     * @return
     */
    public Map<String, Object> userMenuInfo(SysMenu menu, List<String> userMenu) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", menu.getId());  //菜单ID
        map.put("text", menu.getText());  //菜单名称
        if (menu.getIcon().length() > 0) {
            map.put("icon", "anticon-" + menu.getIcon());  //icon
        }
        if (menu.getType().equals("0")) {  //判断是否是分组
            map.put("group", true);
        } else {
            if (!"".equals(menu.getLink())) {     //只有当链接值存在时才添加link属性
                map.put("link", menu.getLink());
            }
        }
        List<Map<String, Object>> mapMenu = new ArrayList<>();
        //判断子集是否存在
        if (menu.getChildrens().size() > 0) {
            List<SysMenu> childList = menu.getChildrens();
            for (SysMenu child : childList) {
                if (userMenu.stream().anyMatch(item -> item.equals(child.getId()))) {
                    //只有状态正常  未禁用的菜单才可显示 过滤掉删除  、禁用 状态的菜单
                    if (child.getStatus() == 0 && child.getDisabled() == 0) {
                        Map<String, Object> childMeu = userMenuInfo(child, userMenu);
                        mapMenu.add(childMeu);
                    }
                }
            }
            map.put("children", mapMenu);
        }else{
            //无子
            map.put("isLeaf", true);
        }
        return map;
    }

    /**
     * 获取全部菜单组成菜单树
     *
     * @return
     */
    public List<Map<String, Object>> getAllMenuTree(boolean expand) {
        String prID = CommonConstant.SYS_MENU;   //数据库目录初始化根节点 ID
        //根据用户查询权限后返回菜单列表
        List<SysMenu> rootList = sysMenuDao.getSysMenu(prID);

        List<Map<String, Object>> mapMenu = new ArrayList<>();
        if (rootList.size() > 0) {
            for (SysMenu menu : rootList) {
                Map<String, Object> map = allMenuInfo(menu, expand);
                mapMenu.add(map);
            }
        }
        return mapMenu;
    }
    /**
     * 返回菜单对象参数内容
     *      * 只有状态正常  未禁用的菜单才可显示
     *      * status =0   disabled =0
     * @param menu  菜单对象
     * @param expand   是否展开，系统菜单不展开  角色权限分配树展开
     * @return
     */
    public Map<String, Object> allMenuInfo(SysMenu menu, boolean expand) {
        Map<String, Object> map = new HashMap<>();
        map.put("key", menu.getId());  //菜单ID
        map.put("text", menu.getText());  //菜单名称
        if (menu.getIcon().length() > 0) {
            map.put("icon", "anticon-" + menu.getIcon());  //icon
        }
        if (menu.getType().equals("0")) {  //判断是否是分组
            map.put("group", true);
        } else {
            if (!"".equals(menu.getLink())) {     //只有当链接值存在时才添加link属性
                map.put("link", menu.getLink());
            }
        }
        List<Map<String, Object>> mapMenu = new ArrayList<>();
        //判断子集是否存在
        if (menu.getChildrens().size() > 0) {
            List<SysMenu> childList = menu.getChildrens();
            for (SysMenu child : childList) {
                //只有状态正常  未禁用的菜单才可显示 过滤掉删除  、禁用 状态的菜单
                if (child.getStatus() == 0 && child.getDisabled() == 0) {
                    Map<String, Object> childMeu = allMenuInfo(child, expand);
                    mapMenu.add(childMeu);
                }
            }
            map.put("children", mapMenu);
            //但不是系统菜单时 展开机构树
            if(expand){
                map.put("expanded", true);
            }
        }else{
            //无子
            map.put("isLeaf", true);
        }
        return map;
    }

}
