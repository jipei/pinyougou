package com.pinyougou.shop.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * 动态认证授权类；可以根据用户输入的用户名进行认证和查询其角色权限
 */
public class UserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //获取角色权限集合
        List<GrantedAuthority> authorities = new ArrayList<>();
        //设置一个角色
        authorities.add(new SimpleGrantedAuthority("ROLE_SELLER"));

        //只要密码是123456的就可以登录进入系统
        //会将user中的123456与用户在登录页面输入的密码进行对比；如果是123456则通过
        return new User(username, "123456", authorities);
    }
}
