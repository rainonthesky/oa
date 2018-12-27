package com.oa.sys.intercepter;

import com.oa.sys.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 类描述：用户登录认证的拦截器
 * 创建人：陈亚平
 * 创建时间：2018年1月16日
 */

public class LoginInterceptor implements HandlerInterceptor {
    //执行 handler之前 运行这个方法里面的代码
    //用户登录验证
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        //得到请求的url
        String url = request.getRequestURI();
        //将不需要验证的地址(公开地址)滤掉
        if(url.indexOf("login")>=0||url.indexOf("gotoLogin")>=0){
            return true;
        }
        //如果不是公开地址,进行session判断,如果session有记录,则返回true
        //如果session里面没有值,则转发到登陆页面重新输入用户名和密码进行验证
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null){
            return true;
        }else{
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
        return false;

    }
    //在执行handler ,返回modelAndView之前 运行这个方法里面的代码
    //向页面提供一些公用的数据或者视图信息,
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                            Object handler, ModelAndView modelAndView)throws Exception{

    }

    //执行handler之后 运行这个方法来面的代码
    //系统统一的异常处理,方法执行时间 :afterCompletion-preHandler
    //系统日志记录
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception exception)
            throws Exception {

    }
}
