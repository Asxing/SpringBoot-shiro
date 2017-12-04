package com.holddie.boot.shiro.web;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录验证
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 10:14
 */
@Controller
public class HomeController {

    @RequestMapping({"/","/index"})
    public String index(){
        return "/index";
    }

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) {
        System.out.println("HomeController.login()");
        //登录失败从request中获取shiro处理的异常信息
        //shiroLoginFailure: 就是shiro异常类的全名类
        String exception = (String) request.getAttribute("shiroLoginFaliure");

        System.out.println("exception="+exception);
        String msg = "";
        if (exception != null){
            if (UnknownAccountException.class.getName().equals(exception)){
                System.out.println("UnknowAccoutException --> 账号不存在：");
                msg = "UnknowAccoutException --> 账号不存在";
            }else if(IncorrectCredentialsException.class.getName().equals(exception)){
                System.out.println("IncorrectCredentialsException --> 密码不正确：");
                msg = "IncorrectCredentialsException --> 密码不正确";
            }else if ("kaptchaValidateFailed".equals(exception)){
                System.out.println("kaptchaValidateFailed --> 验证码错误");
                msg = "kaptchaValidateFailed --> 验证码错误";
            }else{
                msg = "else >> " + exception;
                System.out.println("else --> "+ exception);
            }
        }
        map.put("msg",msg);
        return "/login";
    }

    @RequestMapping("/403")
    public String unanthorizedRole(){
        System.out.println("-------没有权限--------");
        return "403";
    }
}
