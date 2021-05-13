package com.pmcc.system.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.entity.TreeTableEntity;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.model.*;
import com.pmcc.system.service.SysMenuRelationService;
import com.pmcc.system.service.SysMenuService;
import com.pmcc.system.service.SysRoleMenuService;
import com.pmcc.system.service.SysRoleUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: SysMenuController <br>
 * @Description: TODO菜单管理  分配给权限组
 *               0 分组  1 模块  2功能
 * @Date: 2020/5/7 8:10 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysMenuRelationService sysMenuRelationService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 获取菜单列表管理菜单
     * @param map
     * @return
     */
    @GetMapping("get-menu")
    public List<TreeTableEntity> getMenu(@RequestParam LinkedHashMap<String, String> map){

        String prID = CommonConstant.SYS_MENU;
        List<TreeTableEntity> list = new ArrayList<>();
        list = sysMenuService.getTree(prID);
        return list;
    }

    /**
     * 新增
     * @param map
     * @return
     */
    @PostMapping("/add-menu")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if(map.size() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        String menuParentID;
        SysMenu pMenu;
        //没有录入父级ID时默认为根ID
        menuParentID = map.get("parent").equals("")? CommonConstant.SYS_MENU :  map.get("parent");
        SysMenu parentMenu = sysMenuService.findById(menuParentID);
        SysMenu newMenu = new SysMenu();
        //判断同一级下 名称  链接不重复
        ApiResult repeat = sysMenuService.checkRepeat(map.get("key"),menuParentID, map.get("text"),map.get("link"));
        if(repeat != null){
            return repeat;
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        newMenu.setText(map.get("text"));
        newMenu.setLink(map.get("link"));
        newMenu.setParent(parentMenu);
        newMenu.setIcon(map.get("icon"));
        newMenu.setType(map.get("type"));
        newMenu.setStatus(CommonConstant.STATUS_NORMAL);  // 1正常
        newMenu.setDisabled(Integer.valueOf(map.get("disabled")));   //1 禁用  0 正常
        newMenu.setSort(Integer.valueOf(map.get("sort")));
        newMenu.setOperator(jwtUserService.onLineUser().getId());
        newMenu.setOperateTime(timenow);           //创建时间
        sysMenuService.save(newMenu);
        return ApiResult.success();
    }

    /**
     * 修改菜单
     * @param map
     * @return
     */
    @PutMapping("/edit-menu")
    public ApiResult edit(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if(map.size() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        SysMenu sysMenu = sysMenuService.findById(map.get("key"));

        String menuParentID;
        //没有录入父级ID时默认为根ID
        menuParentID = map.get("parent").equals("")? CommonConstant.SYS_MENU :  map.get("parent");
        SysMenu parentMenu = sysMenuService.findById(menuParentID);

        //判断同一级下 名称  链接不重复
        ApiResult repeat = sysMenuService.checkRepeat(map.get("key"),menuParentID, map.get("text"),map.get("link"));
        if(repeat != null){
            return repeat;
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        sysMenu.setText(map.get("text"));
        sysMenu.setLink(map.get("link"));
        sysMenu.setParent(parentMenu);
        sysMenu.setIcon(map.get("icon"));
        sysMenu.setType(map.get("type"));
        sysMenu.setDisabled(Integer.valueOf(map.get("disabled")));  //1 禁用  0 正常
        sysMenu.setSort(Integer.valueOf(map.get("sort")));
        sysMenu.setOperator(jwtUserService.onLineUser().getId());
        sysMenu.setOperateTime(timenow);           //操作时间
        sysMenuService.update(sysMenu);
        return ApiResult.success();
    }

    @PostMapping("/del-menu")
    public ApiResult deleteMenu(@RequestBody String id){
        if(id.length() > 0){
            SysMenu menu = sysMenuService.findById(id);
            menu.setStatus(CommonConstant.DEL_FLAG); //删除
            sysMenuService.update(menu);
            return ApiResult.success();
        }else {
            return ApiResult.fail("删除失败！");
        }
    }

    /**
     * 根据登录角色获取系统菜单
     * @return
     */
    @GetMapping("system-menu")
    public List<Map<String, Object>> systemMenu(){
        //当前登录用户
        SysUser onlineUser = jwtUserService.onLineUser();
        List<Map<String, Object>> menuMap = new ArrayList<>();
        //预留管理员有最高权限
        if(onlineUser.getId().equals(CommonConstant.SYS_USER)){
            //显示全部菜单
            menuMap= sysMenuService.getAllMenuTree(false);
        }else{
            //用户对应系统菜单
            List<String> userMenu = getUserMenu(onlineUser.getId());
            //显示分配的角色菜单
            menuMap= sysMenuService.systemMenu(userMenu);
        }
        return menuMap;
    }

    /**
     * 获取全部菜单
     * @return
     */
    @GetMapping("get-all-menu-tree")
    public List<Map<String, Object>> getAllMenu(){
        List<Map<String, Object>> menu= sysMenuService.getAllMenuTree(true);
        return menu;
    }

    /**
     * 根据角色获取菜单映射关系
     * @param roleID
     * @return
     */
    @GetMapping("get-role-menu")
    public List<String> roleMenu(@RequestParam("roleID") String roleID){
        List<SysRoleMenu> menuList= sysRoleMenuService.getMenusByRoleID(roleID);
        List<String> menus = new ArrayList<>();
        for (SysRoleMenu item:menuList) {
            SysRoleMenu sysRoleMenu = item;
            menus.add(item.getMenu().getId());
        }
        return menus;
    }

    /**
     * 根据用户获取关联所有角色下菜单映射
     */
    @GetMapping("get-user-menu")
    public List<String> getUserMenu(@RequestParam("user") String userID){
        List<String> menus = new ArrayList<>();
        //获取该用户下所有角色
        List<SysRoleUser> sysRoleUserList = sysRoleUserService.getRoleByUserID(userID);
        for (SysRoleUser roleUser:sysRoleUserList) {
            //获取角色
            SysRole sysRole = roleUser.getRole();
            //根据角色获取所有菜单
            List<SysRoleMenu> sysRoleMenuList= sysRoleMenuService.getMenusByRoleID(sysRole.getId());
            for (SysRoleMenu roleMenu:sysRoleMenuList) {
                //获取菜单ID
                String menuID = roleMenu.getMenu().getId();
                //当列表中不存在时才添加到列表中
                if(!menus.stream().anyMatch(item -> item.equals(menuID))){
                    menus.add(menuID);
                }
            }
        }
        return menus;
    }
}
