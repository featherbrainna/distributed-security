package com.itheima.security.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.security.uaa.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 12:02:25
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    /**
     * 根据用户id查询用户权限
     * @param id
     * @return
     */
    List<String> queryAllPerms(Long id);
}
