package com.yj.oa.framework.shiro.service;

import com.yj.oa.project.po.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Component;

/**
 * 登录密码方法
 * 
 *
 */
@Component
public class SysPasswordService
{


    public boolean matches(User user, String newPassword)
    {
        return user.getPwd().equals(encryptPassword(user.getLoginName(), newPassword, user.getSalt()));
    }

    public String encryptPassword(String username, String password, String salt)
    {
        return new Md5Hash(username + password + salt).toHex();
    }

}
