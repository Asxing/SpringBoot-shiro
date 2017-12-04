package com.holddie.boot.shiro.service.impl;

import com.holddie.boot.shiro.dao.UserInfoDao;
import com.holddie.boot.shiro.entity.UserInfo;
import com.holddie.boot.shiro.service.UserInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户信息实现类
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 10:42
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Resource
    private UserInfoDao userInfoDao;

    @Override
    public UserInfo findByUsername(String username) {
        System.out.println("UserInfoServiceImpl.findByUsername()");
        return userInfoDao.findByUsername(username);
    }
}
