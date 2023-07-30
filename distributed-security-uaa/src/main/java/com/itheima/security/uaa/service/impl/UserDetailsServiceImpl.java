package com.itheima.security.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.security.uaa.entity.SecurityUser;
import com.itheima.security.uaa.entity.UserEntity;
import com.itheima.security.uaa.service.UserService;
import com.itheima.security.uaa.utils.SysConstant;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义的UserDetailsService来认证查询数据库用户
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/24 9:11
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private UserService userService;

    /**
     * 根据账号查询用户
     * @param username 用户名
     * @return 查询到用户返回对象，没有查询到返回null。
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.查询数据库
        UserEntity user = userService.getOne(new QueryWrapper<UserEntity>().eq(SysConstant.USER_NAME,username));
        //2.如果查询数据为空，直接返回null，让spring security来处理抛出异常。
        if (user == null) return null;

        //3.根据用户id查询用户权限
        List<String> list = userService.queryAllPerms(user.getId());
        List<SimpleGrantedAuthority> authorities = list.stream().map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        //4.封装用户信息和权限为UserDetails返回
        return new SecurityUser(user,authorities);
    }
}
