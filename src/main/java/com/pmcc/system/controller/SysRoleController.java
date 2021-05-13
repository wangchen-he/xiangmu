package com.pmcc.system.controller;

import com.alibaba.fastjson.JSON;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.entity.RoleMenus;
import com.pmcc.system.model.SysMenu;
import com.pmcc.system.model.SysRole;
import com.pmcc.system.model.SysRoleMenu;
import com.pmcc.system.model.SysRoleUser;
import com.pmcc.system.service.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: SysRoleController <br>
 * @Description: TODO 角色管理
 * @Date: 2020/12/26 11:41 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysRoleMenuService sysRoleMenuService;

    @Resource
    private SysRoleUserService sysRoleUserService;

    @Resource
    private SysMenuService sysMenuService;

    @Resource
    private SysUserService sysUserService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 获取角色相关信息
     * @RequestParam("roleID") String roleID
     * @return
     */
    @GetMapping("get-role")
    public Object getTree(){
        //获取权限列表
        List<SysRole> roleList = sysRoleService.getRole();
//        //获取机构人员列表-标记相应用户
//        String userStr = sysUserService.getUserTreeStr();
        return roleList;
    }

    /**
     * 新增
     * @param map
     * @return
     */
    @PostMapping("/add-role")
    public ApiResult add(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if(map.size() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        String roleName = "",roleMemo ="";
        int roleSort = 0;
        roleName = map.get("roleName");
        roleMemo = map.get("roleMemo");
        roleSort = Integer.parseInt(map.get("roleSort"));
        SysRole role = new SysRole();
        //判断同一级下 名称  链接不重复
        ApiResult repeat = sysRoleService.checkRepeat(roleName);
        if(repeat != null){
            return repeat;
        }

        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        role.setName(roleName);
        role.setMemo(roleMemo);
        role.setStatus(CommonConstant.STATUS_NORMAL);
        role.setSort(roleSort);
        role.setOperator(jwtUserService.onLineUser().getId());
        role.setOperateTime(timenow);           //创建时间
        sysRoleService.save(role);
        return ApiResult.success();
    }

    /**
     * 修改角色
     * @param map
     * @return
     */
    @PutMapping("/edit-role")
    public ApiResult edit(@RequestBody LinkedHashMap<String, String> map) {
        //判断map有数据
        if(map.size() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        SysRole role = sysRoleService.findById(map.get("roleId"));
        if(role != null){
            Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            role.setName(map.get("roleName"));
            role.setMemo(map.get("roleMemo"));
            role.setSort(Integer.parseInt(map.get("roleSort")));
            role.setStatus(CommonConstant.STATUS_NORMAL);
            role.setOperator(jwtUserService.onLineUser().getId());
            role.setOperateTime(timenow);           //创建时间
            sysRoleService.update(role);
        }
        return ApiResult.success();
    }

    /**
     * 删除角色
     * @param roleID
     * @return
     */
    @PostMapping("del-role")
    public ApiResult delete(@RequestBody String roleID) {
        //判断map有数据
        if(roleID.length() == 0){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        SysRole role = sysRoleService.findById(roleID);
        if(role != null){
            //删除角色菜单映射表
            List<SysRoleMenu> sysRoleMenu = sysRoleMenuService.getMenusByRoleID(roleID);
            if(sysRoleMenu.size()>0){
                for (SysRoleMenu item:sysRoleMenu) {
                    sysRoleMenuService.delete(item);
                }
            }
            //删除角色用户表
            List<SysRoleUser> sysRoleUser = sysRoleUserService.getUsersByRoleID(roleID);
            if(sysRoleUser.size()>0){
                for (SysRoleUser item:sysRoleUser) {
                    sysRoleUserService.delete(item);
                }
            }
            //删除角色
            sysRoleService.delete(role);
        }
        return ApiResult.success();
    }

    /**
     * 角色对应菜单更新保存
     * @param roleMenus
     * @return
     */
    @PostMapping("edit-role-menu")
    public ApiResult editRoleMenu(@RequestBody RoleMenus roleMenus){
        //判断有数据
        if(roleMenus.getRole().equals("")){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        //获取配置角色
        SysRole role = sysRoleService.findById(roleMenus.getRole());
        String[] menus = roleMenus.getMenus();

        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //根据角色获取对应菜单
        List<SysRoleMenu> menuList = sysRoleMenuService.getMenusByRoleID(role.getId());
        //删除角色对应所有菜单
        for (SysRoleMenu menu:menuList) {
            sysRoleMenuService.delete(menu);
        }
        //重新写入角色对应菜单
       for(int i=0; i<menus.length; i++){
           String menuID = menus[i];
            SysMenu sysmenu = sysMenuService.findById(menuID);
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRole(role);
            sysRoleMenu.setMenu(sysmenu);
            sysRoleMenu.setOperator(jwtUserService.onLineUser());
            sysRoleMenu.setOperateTime(timenow);           //创建时间
            sysRoleMenuService.save(sysRoleMenu);
        }
        return ApiResult.success();
    }
}
