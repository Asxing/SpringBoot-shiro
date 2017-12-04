package com.holddie.boot.shiro.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 9:57
 */
@Entity
public class UserInfo {

    @Id@GeneratedValue
    private Integer uid;

    @Column(unique = true)
    private String username;//账号

    private String password;//密码

    private String name;//名称

    private String salt;//加密密码的盐

    private byte state;//用户状态，0：创建未认证（比如没有激活或者没有输入验证码等等）--等待验证的用户，1：正常状态，2：用户被锁定

    @ManyToMany(fetch = FetchType.EAGER)//立即从数据库中进行数据加载
    @JoinTable(name = "SysUserRole",joinColumns = {@JoinColumn(name = "uid")},inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<SysRole> roleList;//一个用户具有多个角色

    /**
     * 对盐重新定义，用户名+salt，这样不易被破解
     *
     * @author HoldDie
     * @email holddie@163.com
     * @date 2017/12/2 11:37
     * @param 
     * @return 
     */
    public String getCredentialsSalt(){
        return this.username + this.salt;
    }


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    public List<SysRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysRole> roleList) {
        this.roleList = roleList;
    }
}
