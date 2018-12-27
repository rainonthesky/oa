package com.oa.sys.util;

import com.oa.sys.entity.User;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取当前用户信息的工具类
 */

public class UserUtils {
    public static Long getCurrentUserId(){
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
       if (request.getSession().getAttribute("user")!=null){
           User user=(User)request.getSession().getAttribute("user");
           return user.getUserId();
       }else{
           return null;
       }
    }


    public static String getCurrentUserName(){
        HttpServletRequest request=((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        if (request.getSession().getAttribute("user")!=null){
            User user=(User)request.getSession().getAttribute("user");
            return user.getUserName();
        }else{
            return null;
        }
    }
}
