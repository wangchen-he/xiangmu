package com.pmcc.core.security.token;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * @ClassName: JwtAuthenticationToken <br>
 * @Description: TODO 自定义JWT身份信息 封装成security的Authentication
 * @Date: 2019/12/15 13:57 <br>
 * @Author: DarkBF <br> 登陆类型：user:用户密码登陆；phone:手机验证码登陆；qr:二维码扫码登陆
 * @Version: 1.0 <br>
 */
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

//    private static final long serialVersionUID = 3981518947978158945L;

    private Object principal;
    /**
     * 用户名
     */
    private Object credentials;
    /**
     * 密码
     */
    private String token;
    private String type;
    private String mobile;

    public JwtAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.token = token;
        this.setAuthenticated(false);
    }

    /**
     * 创建未认证的用户名密码认证对象
     */
    public JwtAuthenticationToken(Object principal, Object credentials, String token) {
//        super(Collections.emptyList());
        super((Collection) null);
        this.principal = principal;
        this.credentials = credentials;
        this.token = token;
        this.setAuthenticated(false);
    }

    /**
     * 创建未认证的对象
     *
     * @param principal   用户
     * @param credentials 加密后的密码
     * @param type        登录类型
     * @param mobile      手机号
     */
    public JwtAuthenticationToken(Object principal, Object credentials, String type, String mobile) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        this.mobile = mobile;
        this.setAuthenticated(false);
    }

    /**
     * 创建已认证的用户密码认证对象
     */
    public JwtAuthenticationToken(Object principal, Object credentials, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.token = token;
        super.setAuthenticated(true);
    }

    /**
     * 创建已认证的对象
     *
     * @param principal   用户
     * @param credentials 加密后的密码
     * @param type        登录类型
     * @param mobile      手机号
     */
    public JwtAuthenticationToken(Object principal, Object credentials, String type, String mobile, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.type = type;
        this.mobile = mobile;
        super.setAuthenticated(true);
    }


//    @Override
//    public void setDetails(Object details) {
//        super.setDetails(details);
//        this.setAuthenticated(true);
//    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }


    public String getToken() {
        return token;
    }

    public String getType() {
        return this.type;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException("Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        } else {
            super.setAuthenticated(false);
        }
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

}
