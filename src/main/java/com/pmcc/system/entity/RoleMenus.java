package com.pmcc.system.entity;

/**
 * @ClassName: RoleMenus <br>
 * @Description: TODO 和前端角色设置进行数据传输
 * @Date: 2021/1/25 10:32 <br>
 * @Author: DarkBF <br>
 * @Version: 1.0 <br>
 */
public class RoleMenus {
    private String role;
    private String[] menus;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String[] getMenus() {
        return menus;
    }

    public void setMenus(String[] menus) {
        this.menus = menus;
    }
}
