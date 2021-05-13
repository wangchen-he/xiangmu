package com.pmcc.system.entity;

/**
 * @ClassName: RoleMenus <br>
 * @Description: TODO 和前端角色设置进行数据传输
 * @Date: 2021/1/25 10:32 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class RoleUser {
    private String user;
    private String[] roles;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }
}
