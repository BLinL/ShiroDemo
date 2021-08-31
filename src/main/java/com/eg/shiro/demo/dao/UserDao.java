package com.eg.shiro.demo.dao;

import com.eg.shiro.demo.pojo.UserDo;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class UserDao {

    private static final HashMap<String, UserDo> userCollection = new HashMap<>();

    //模拟用户
    static {
        UserDo.Builder userVoBuilder = new UserDo.Builder();
        //user1
        UserDo userDo = userVoBuilder.accountName("admin")
                .password("64ea22d8255ff30ae673597551f7ee124d675fe0c892921da9004d15c4595e1b")
//                .status(-1)admin
                .salt("admin.salt")//盐
                .build();
        userDo.setRoles(new HashSet<>(Collections.singletonList("admin")));//角色
        userDo.setPermissions(new HashSet<>(Arrays.asList("user:add","user:view")));//权限

        //user2
        UserDo userDo1 = userVoBuilder.accountName("test")
                .password("9a984e219b07f9b645ef35f4de938b4741abe2e0b4adc88b40e9367170c91cc8")
                .salt("test")
                .build();
        userDo1.setRoles(new HashSet<>(Collections.singletonList("test")));
        userDo1.setPermissions(new HashSet<>(Collections.singletonList("user:view")));

        userCollection.put("admin", userDo);
        userCollection.put("test", userDo1);
    }

    public UserDo getUserByName(String userName) {
        Assert.notNull(userName,"用户名为null");
        return userCollection.get(userName);
    }

    public List<UserDo> getAllUser() {
        return userCollection.values().stream().collect(Collectors.toList());
    }


    public void addUser(UserDo userDo){
        this.userCollection.put(userDo.getAccountName(), userDo);
    }
}
