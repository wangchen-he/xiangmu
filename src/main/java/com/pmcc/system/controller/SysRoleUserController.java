package com.pmcc.system.controller;

import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.entity.RoleUser;
import com.pmcc.system.model.SysRole;
import com.pmcc.system.model.SysRoleUser;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.service.SysRoleService;
import com.pmcc.system.service.SysRoleUserService;
import com.pmcc.system.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: SysRoleUserController <br>
 * @Description: TODO角色-用户对照
 * @Date: 2020/12/27 17:27 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@RestController
@RequestMapping("/sys/role-user")
public class SysRoleUserController {

    @Resource
    private SysRoleUserService sysRoleUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUserService sysUserService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 给用户分配角色
     * @param roleUser
     * @return
     */
    @PostMapping("/operate-role")
    public ApiResult operateRole(@RequestBody RoleUser roleUser){
        //判断map有数据
        if(roleUser.getUser().equals("")){
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //拿到用户和分配角色
        SysUser sysUser = sysUserService.findById(roleUser.getUser());
        String[] roles = roleUser.getRoles();
        //获取该用户下所有角色
        List<SysRoleUser> sysRoleUserList = sysRoleUserService.getRoleByUserID(sysUser.getId());
        //删除所有角色
        for (SysRoleUser item : sysRoleUserList) {
            sysRoleUserService.delete(item);
        }
        //循环添加新的角色
        for (int i = 0; i < roles.length; i++) {
            //获取到角色实例
            SysRole role = sysRoleService.findById(roles[i]);
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setRole(role);                                //角色
            sysRoleUser.setUser(sysUser);                             //用户
            sysRoleUser.setOperator(jwtUserService.onLineUser());     //创建人
            sysRoleUser.setOperateTime(timenow);                      //创建时间
            sysRoleUserService.save(sysRoleUser);

        }
        return ApiResult.success();
    }

    /**
     * 根据用户查询相应角色信息
     * @return
     */
    @GetMapping("get-user-role")
    public List<SysRole> getUserRole(@RequestParam("user") String userID){
        //获取该用户下所有角色
        List<SysRoleUser> sysRoleUserList = sysRoleUserService.getRoleByUserID(userID);
        List<SysRole> list = new ArrayList<>();
        for (SysRoleUser item:sysRoleUserList) {
            list.add(item.getRole());
        }
        return list;
    }
}
