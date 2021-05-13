package com.pmcc.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.pmcc.core.api.ApiResult;
import com.pmcc.core.constant.CommonConstant;
import com.pmcc.core.constant.ErrorCode;
import com.pmcc.core.security.service.JwtUserService;
import com.pmcc.system.model.SysDep;
import com.pmcc.system.model.SysUser;
import com.pmcc.system.service.SysDepService;
import com.pmcc.system.service.SysUserService;
import com.pmcc.system.common.CommonCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @ClassName: SysUserController <br>
 * @Description: TODO 用户操作
 * @Date: 2019/12/15 11:57 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 * 请求方法参考 ：https://blog.csdn.net/weixin_38004638/article/details/99655322
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private SysDepService sysDepService;

    @Resource
    private JwtUserService jwtUserService;

    /**
     * 添加用户
     *
     * @param map
     * @return
     */
    @PostMapping("/add-user")
    public ApiResult add(@RequestBody Map<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        //校验密码
        if (!map.get("password").equals(map.get("confirm"))) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "密码与确认密码信息不一致！");
        }
        //校验用户（用户名EName不可重复）
        List<SysUser> validList = sysUserService.getUserByEName(map.get("userEName"));
        if (validList.size() > 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "用户名已存在，请重新录入！");
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String user = jwtUserService.onLineUser().getUserCName();
        SysDep sysDep;
        //获取机构列表，判断列表是否存在
        List<SysDep> depList = sysDepService.getDepByID(map.get("deptID"));
        if (depList.size() == 1) {
            sysDep = depList.get(0);
        } else {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "机构信息不存在，请重新录入！");
        }

        SysUser sysUser = new SysUser();

        sysUser.setUserEName(map.get("userEName"));
        sysUser.setUserCName(map.get("userCName"));
        //TODO 密码保存应加密  加密后参看 LoginAuthenticationFilter
        sysUser.setPassword(map.get("password"));
        sysUser.setPwdChange(0);    //密码保存次数
        sysUser.setCode(map.get("code"));
//        sysUser.setIcon("icon");
        sysUser.setSort(Integer.parseInt(map.get("sort")));
//        sysUser.setMobile("mobile");
//        sysUser.setEmail("email");
        if (map.get("status").equals("0")) {
            sysUser.setStatus(CommonConstant.STATUS_NORMAL);
        } else {
            sysUser.setStatus(CommonConstant.STATUS_DISABLE);
        }
        sysUser.setCreator(user);
        sysUser.setCreateTime(timenow);
        sysUser.setOperator(user);
        sysUser.setOperateTime(timenow);
        sysUser.setMemo(map.get("memo"));
        sysUser.setDeptID(sysDep.getId());
        sysUser.setDeptCName(sysDep.getDepCName());
        sysUser.setDeptEName(sysDep.getDepEName());

        sysUserService.save(sysUser);

        return ApiResult.success();
    }

    /**
     * 查询用户
     *
     * @param deptID
     * @return
     */
    @GetMapping("/get-user")
    public List<SysUser> getUser(@RequestParam("dept") String deptID) {
        List<SysUser> list = new ArrayList<>();
        list = sysUserService.getUserByDep(deptID);
        return list;
    }

    @GetMapping("/getById")
    public SysUser getById(@RequestParam("oid") String oid) {
        SysUser sysUser = new SysUser();
        sysUser = sysUserService.findById(oid);
        return sysUser;
    }

    @GetMapping("/getByDid")
    public List<SysUser> getByDid(@RequestParam("deptID") String deptID) {
        List<SysUser> list = new ArrayList<>();
        list = sysUserService.getUserByDep(deptID);
        return list;
    }

    /**
     * 验证账号是否重复
     */
    @PostMapping("/user-validation")
    public boolean validationAccount(@RequestBody String userEname) {
        List<SysUser> sysUser = sysUserService.getUserByEName(userEname);
        if (sysUser.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 禁用\启用
     *
     * @return
     */
    @PutMapping("/user-enable")
    public ApiResult enableUser(@RequestBody String userID) {
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String user = jwtUserService.onLineUser().getUserCName();
        SysUser sysUser = sysUserService.findById(userID);
        if (sysUser.getStatus() == CommonConstant.STATUS_NORMAL) {
            sysUser.setStatus(CommonConstant.STATUS_DISABLE);
        } else {
            sysUser.setStatus(CommonConstant.STATUS_NORMAL);
        }
        sysUser.setOperator(user);
        sysUser.setOperateTime(timenow);
        sysUserService.update(sysUser);
        return ApiResult.success();
    }

    /**
     * 删除用户  假删除
     *
     * @param userID
     * @return
     */
    @PostMapping("/delete-user")
    public ApiResult deleteUser(@RequestBody String userID) {
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String user = jwtUserService.onLineUser().getUserCName();
        SysUser sysUser = sysUserService.findById(userID);
        sysUser.setStatus(CommonConstant.DEL_FLAG); //删除标记
        sysUser.setOperator(user);
        sysUser.setOperateTime(timenow);
        sysUserService.update(sysUser);
        return ApiResult.success();
    }

    /**
     * 修改密码
     *
     * @param map
     * @return
     */
    @PostMapping("/update-password")
    public ApiResult updatePassword(@RequestBody Map<String, String> map) {
        //判断map有数据
        if (map.size() == 0) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, ErrorCode.DATA_WRITE_FAIL_MSG);
        }
        String nowPass = map.get("nowPass");
        String password = map.get("password");
        String confirm = map.get("confirm");
        //校验新密码
        if (!password.equals(confirm)) {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "密码与确认密码信息不一致！");
        }
        Timestamp timenow = Timestamp.valueOf(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        //校验老密码是否正确
        SysUser sysUser = jwtUserService.onLineUser();
        if (nowPass.equals(sysUser.getPassword())) {
            sysUser.setPassword(password);  //修改密码
            sysUser.setPwdChange(sysUser.getPwdChange() + 1);    //密码保存次数
            sysUser.setOperator(sysUser.getId());
            sysUser.setOperateTime(timenow);
            sysUserService.update(sysUser);
            return ApiResult.success();
        } else {
            return ApiResult.fail(ErrorCode.DATA_WRITE_FAIL_CODE, "原密码不正确，请重新输入！");
        }

    }

    @GetMapping("system-online-user")
    public Map<String, Object> getOnlineUser() {
        //用户信息
        SysUser user = jwtUserService.onLineUser();
        Map<String, Object> map = new HashMap<>();
        map.put("userid", user.getId());
        map.put("name", user.getUserCName());
        map.put("deptID", user.getDeptID());
        map.put("deptCName", user.getDeptCName());
        map.put("avatar", "./assets/tmp/img/avatar.jpg");
        map.put("email", user.getDeptCName());

        return map;
    }
}
