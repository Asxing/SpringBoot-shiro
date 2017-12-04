package com.holddie.boot.shiro.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Shiro配置
 * @author HoldDie
 * @version 1.0.0
 * @email holddie@163.com
 * @date 2017/12/2 9:31
 */
@Configuration
public class ShiroConfig {

    /**
     * shiro判断核心拦截器
     *
     * @author HoldDie
     * @email holddie@163.com
     * @date 2017/12/2 11:11
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFileter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shiroFilter()");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //拦截器
        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        //配置不会被拦截的链接，顺序判断
        filterChainDefinitionMap.put("/static/**","anon");
        //配置退出过滤器，其中的具体的退出代码Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout","logout");
        //过滤连定义，从上下顺序执行，一般将/**放在最为下面 --> 这是一个坑，一不小心代码就不好使了
        //authc：所有url都必须认证通过才可以访问；anon：所有url都可以匿名访问
        filterChainDefinitionMap.put("/**","authc");
        //如果不是遏制默认自动寻找web工程根下的“/login.jsp”页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/index");

        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return  shiroFilterFactoryBean;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法，这里使用MD5算法
        hashedCredentialsMatcher.setHashIterations(2);//设置散列的次数
        return hashedCredentialsMatcher;
    }

    @Bean
    public MyShiroRealm myShiroRealm(){
        MyShiroRealm myShiroRealm = new MyShiroRealm();
        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return myShiroRealm;
    }

    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    /**
     * 开启shiro aop注解支持
     * 使用代理方式，所以需要开启代码支持
     *
     * @author HoldDie
     * @email holddie@163.com
     * @date 2017/12/2 11:28
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    public SimpleMappingExceptionResolver createSimpleMappingExceptionResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties mappings = new Properties();
        mappings.setProperty("DataBaseException","databaseError"); //数据库异常处理
        mappings.setProperty("UnauthorizedException","403");
        resolver.setExceptionMappings(mappings);//默认为空
        resolver.setDefaultErrorView("error");//没有默认
        resolver.setExceptionAttribute("ex");//默认为 exception
        return resolver;
    }

}
