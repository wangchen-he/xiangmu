package com.pmcc.onlineexam.service;

import com.pmcc.core.common.dao.AbstractBaseDao;
import com.pmcc.core.common.service.CommonServiceImpl;
import com.pmcc.onlineexam.dao.ExamAuditDao;
import com.pmcc.onlineexam.model.ExamAudit;
import com.pmcc.system.dao.SysUserDao;
import com.pmcc.system.model.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Service
public class ExamAuditService  extends CommonServiceImpl<ExamAudit,String> {
    @Autowired
    ExamAuditDao examAuditDao;
    @Autowired
    SysUserDao sysUserDao;
    @Override
    protected AbstractBaseDao<ExamAudit, String> getEntityDao() {
        return examAuditDao;
    }

    public void userauditpass(String data){
        examAuditDao.userauditpass(data);
    }

    public void userdictno(String data){
        examAuditDao.userdictno(data);
    }

    public SysUser getUsername(){
        SysUser user= null;
        //当前认证通过的用户身份
        Authentication authenticator = SecurityContextHolder.getContext().getAuthentication();
        //用户信息
        Object principal = authenticator.getPrincipal();
        if(principal instanceof org.springframework.security.core.userdetails.UserDetails){
            UserDetails userDetails = (UserDetails) principal;
            user = sysUserDao.getUserByName(userDetails.getUsername());
        }
        return user;
    }
}
