package com.holddie.boot.shiro.web;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户信息
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 10:34
 */
@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @RequestMapping("/userList")
    @RequiresPermissions("userInfo:view")//权限管理
    public String userInfo(){
        return "userInfo";
    }

    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理
    public String userInfoAdd(){
        return "userInfoAdd";
    }

    @RequestMapping("/userDel")
    @RequiresPermissions("/userInfo:del")//权限管理
    public String userDel(){
        return "userInfoDel";
    }
}
