package com.eg.shiro.demo.shiro.config;


import com.eg.shiro.demo.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //自定义Realm
    @Bean
    public CustomRealm realm() {
        //凭证比较器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //此处应和新增用户时的加密方式一致
        credentialsMatcher.setHashAlgorithmName("SHA-256");//加密算法
        credentialsMatcher.setStoredCredentialsHexEncoded(true);//表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
        credentialsMatcher.setHashIterations(2);
        CustomRealm realm = new CustomRealm();
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }

    //SecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(@Autowired CustomRealm realm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(realm);
        return manager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Autowired SecurityManager securityManager){
        ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
        sffb.setSecurityManager(securityManager);
        sffb.setLoginUrl("/api/user/login");
//        sffb.setSuccessUrl("/index");
//        sffb.setUnauthorizedUrl("/error");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/api/user/login","anon");
        filterMap.put("/error","anon");
        filterMap.put("/**","authc");
        sffb.setFilterChainDefinitionMap(filterMap);
        return sffb;
    }
}
