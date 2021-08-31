package com.eg.shiro.demo.shiro.config;


import com.eg.shiro.demo.shiro.JWTFilter;
import com.eg.shiro.demo.shiro.realm.CustomRealm;
import com.eg.shiro.demo.shiro.realm.JWTRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.util.Assert;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //自定义Realm
//    @Bean
//    public CustomRealm realm() {
//        //凭证比较器
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //此处应和新增用户时的加密方式一致
//        credentialsMatcher.setHashAlgorithmName("SHA-256");//加密算法
//        credentialsMatcher.setStoredCredentialsHexEncoded(true);//表示是否存储散列后的密码为16进制，需要和生成密码时的一样，默认是base64；
//        credentialsMatcher.setHashIterations(2);
//        CustomRealm realm = new CustomRealm();
//        realm.setCredentialsMatcher(credentialsMatcher);
//        return realm;
//    }

    //SecurityManager
    @Bean
    public DefaultWebSecurityManager securityManager(@Autowired  JWTRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);//设置自定义realm

        //关闭shiro自带的session，详情见文档
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);
        return securityManager;
    }

    /**
     * 配置Shiro生命周期处理器
     * @return
     */
    @ConditionalOnMissingBean(name = "lifecycleBeanPostProcessor")
    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();

        HashMap<String, Filter> filter = new HashMap<>();
        JWTFilter jwtFilter = new JWTFilter();
        filter.put("jwt", jwtFilter);
        sffb.setFilters(filter);

        sffb.setSecurityManager(securityManager);
//        sffb.setLoginUrl("/index/login.html");//进入登录页面
        sffb.setUnauthorizedUrl("/error");

        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/api/user/login", "anon");
        filterMap.put("/error", "anon");
        filterMap.put("/**", "jwt");
        sffb.setFilterChainDefinitionMap(filterMap);
        return sffb;
    }


}
