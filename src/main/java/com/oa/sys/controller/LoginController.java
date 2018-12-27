package com.oa.sys.controller;
import com.oa.sys.entity.Menu;
import com.oa.sys.entity.User;
import com.oa.sys.service.IMenuService;
import com.oa.sys.service.IUserService;
import com.oa.sys.util.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class LoginController {
    private  static Logger logger =Logger.getLogger(LoginController.class);
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;

    @RequestMapping("/gotoLogin")
    public String gotoLogin(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(@RequestParam(value = "loginName",required = true)String loginName,
                        String password, Model model,HttpSession session) {
        if (StringUtils.isNotEmpty(loginName) && StringUtils.isNotEmpty(password)) {
            User user = userService.loginUser(loginName, password);
            if (user != null) {
                logger.info("登录成功");
                session.setAttribute("user",user);
                return "redirect:/main";
            } else {

                model.addAttribute("loginFlag", "登录失败，请输入正确的密码和用户名");
                return "forward:/WEB-INF/pages/login.jsp";
            }
        } else {
            model.addAttribute("loginFlag", "登录失败，请输入正确的用户名和密码");
            return "forword:/WEB-INF/pages/login.jsp";
        }

    }

    @RequestMapping("/main")
    public String main(Model model){
        Long userId=UserUtils.getCurrentUserId();
        List<Menu>menuList=menuService.getMenuListByUserId(userId);
        model.addAttribute("menuList",menuList);
        String userName= UserUtils.getCurrentUserName();
        logger.info(userName);
        model.addAttribute("userName",userName);
        return "main/main";
    }
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "login";

    }

}
