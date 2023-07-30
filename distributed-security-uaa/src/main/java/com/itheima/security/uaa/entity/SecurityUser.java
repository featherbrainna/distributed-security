package com.itheima.security.uaa.entity;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 自定义的UserDetails对象
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/26 8:31
 */
@ToString
@EqualsAndHashCode
public class SecurityUser extends User {

    private UserEntity userEntity;

    public SecurityUser(UserEntity userEntity, Collection<? extends GrantedAuthority> authorities) {
        super(userEntity.getUsername(), userEntity.getPassword(), authorities);
        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
