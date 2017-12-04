package com.holddie.boot.shiro.dao;

import com.holddie.boot.shiro.entity.UserInfo;
import org.springframework.data.repository.CrudRepository;

/**
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 10:46
 */
public interface UserInfoDao extends CrudRepository<UserInfo, Long> {

    /**
     * 通过username查找用户信息
     *
     * @author HoldDie
     * @email holddie@163.com
     * @date 2017/12/2 10:50
     * @param username 账号
     * @return UserInfo 用户信息
     */
    public UserInfo findByUsername(String username);
}
