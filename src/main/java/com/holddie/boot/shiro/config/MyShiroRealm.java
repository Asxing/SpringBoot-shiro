package com.holddie.boot.shiro.config;

import com.holddie.boot.shiro.entity.SysPermission;
import com.holddie.boot.shiro.entity.SysRole;
import com.holddie.boot.shiro.entity.UserInfo;
import com.holddie.boot.shiro.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

/**
 * 自定义实现权限
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 9:22
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserInfoService userinfoService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限配置-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        UserInfo userInfo = (UserInfo) principals.getPrimaryPrincipal();
        for (SysRole role :
                userInfo.getRoleList()) {
            authorizationInfo.addRole(role.getRole());
            for (SysPermission permission :
                    role.getPermissions()) {
                authorizationInfo.addStringPermission(permission.getPermission());
            }
        }
        return authorizationInfo;
    }

    /**
     * 用于身份验证，判断账号和密码是否正确
     *
     * @author HoldDie
     * @email holddie@163.com
     * @date 2017/12/2 11:00
     * @param token
     * @return
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("MyShiroRealm.doGetAuthenticationInfo()");
        //获取用户的输入的账号
        String username = (String) token.getPrincipal();
        System.out.println(token.getPrincipal());
        //通过username从数据库中查找user对象
        //实际项目中，可以根据实际情况做缓存，如果不做，shiro自己也是有时间间隔机制，2分钟之内不会重复执行该方法
        UserInfo userInfo = userinfoService.findByUsername(username);
        System.out.println("--->>userinfo="+userInfo);
        if (userInfo==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(userInfo.getCredentialsSalt()),
                getName());
        return authenticationInfo;
    }

}
