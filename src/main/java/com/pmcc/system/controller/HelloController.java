package com.pmcc.system.controller;

import com.pmcc.system.model.SysUser;
import com.pmcc.system.service.SysUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName: HelloController <br>
 * @Description: TODOtest
 * GET http://localhost:8080/api/users （查询用户）
 * POST http://localhost:8080/api/users （新增用户）
 * PUT http://localhost:8080/api/users （更新用户）
 * DELETE http://localhost:8080/api/users （删除用户）
 * @Date: 2019/12/7 12:02 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
@RestController
@RequestMapping("/login2")
public class HelloController {

    @Resource
    private SysUserService sysUserService;

    @RequestMapping("/getAll")
    public String findDep(){
        List<SysUser> deps = sysUserService.findAll();
        return String.valueOf(deps.toString());
    }

    @RequestMapping("/save")
    public String save(){
        SysUser user = new SysUser();
        user.setUserCName("测试保存");
        user.setUserEName("test");
        user.setCode("003");
        SysUser str = sysUserService.save(user);
        return str.getId();
    }
}
