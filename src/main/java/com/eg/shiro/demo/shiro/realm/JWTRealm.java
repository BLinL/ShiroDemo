package com.eg.shiro.demo.shiro.realm;

import com.eg.shiro.demo.dao.UserDao;
import com.eg.shiro.demo.pojo.UserDo;
import com.eg.shiro.demo.shiro.JWTToken;
import com.eg.shiro.demo.uitls.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JWTRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 获取授权信息
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("————权限认证————");
        String userName = JWTUtil.getUsername(principals.toString());
        UserDo user = userDao.getUserByName(userName);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(user.getRoles());
        simpleAuthorizationInfo.setStringPermissions(user.getPermissions());
        return simpleAuthorizationInfo;
    }

    /**
     * 身份认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        String token = (String) authenticationToken.getCredentials();
        String userName = JWTUtil.getUsername(token);
        if (userName == null || !JWTUtil.verify(token, userName)) {
            throw new AuthenticationException("token认证失败！");
        }
        UserDo user = userDao.getUserByName(userName);
        if(user == null) {
            throw new UnknownAccountException("No account found for user [" + userName + "]");
        }
        if(user.getStatus() == -1) {
            throw new LockedAccountException("此用户已被锁定");
        }
        return new SimpleAuthenticationInfo(token, token, "MyRealm");
    }
}
