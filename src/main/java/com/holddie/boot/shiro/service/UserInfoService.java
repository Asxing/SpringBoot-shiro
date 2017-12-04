package com.holddie.boot.shiro.service;

import com.holddie.boot.shiro.entity.UserInfo;

/**
 * 用户信息
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 10:12
 */
public interface UserInfoService {

    /**
     * 通过username查找用户
     *
     * @author HoldDie
     * @email holddie@163.com
     * @date 2017/12/2 10:41
     * @param username 账号
     * @return UserInfo 用户信息
     */
    public UserInfo findByUsername(String username);

}
