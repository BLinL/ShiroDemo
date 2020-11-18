package com.eg.shiro.demo.shiro.realm;

import com.eg.shiro.demo.dao.UserDao;
import com.eg.shiro.demo.pojo.UserDo;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomRealm extends AuthorizingRealm {

    private static final Logger LOG = LoggerFactory.getLogger(CustomRealm.class);

    @Autowired
    private UserDao userDao;

    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(LOG.isInfoEnabled()) {
            LOG.info("用户授权 {}",principalCollection);
        }

        String userName = (String)principalCollection.getPrimaryPrincipal();
        UserDo user = userDao.getUserByName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(user.getRoles());
        simpleAuthorizationInfo.setStringPermissions(user.getPermissions());
        return simpleAuthorizationInfo;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(LOG.isInfoEnabled()) {
            LOG.info("用户认证 {}",authenticationToken);
        }
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String userName = token.getUsername();

        //数据库查询
        UserDo user = userDao.getUserByName(userName);

        if(user == null) {
            throw new UnknownAccountException("No account found for user [" + userName + "]");
        }

        if(user.getStatus() == -1) {
            throw new LockedAccountException("此用户已被锁定");
        }

        LOG.info("user's salt is {}" , user.getSalt());
        return new SimpleAuthenticationInfo(
                userName,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),
                getName()
        );
    }
}
