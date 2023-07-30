package com.itheima.security.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.security.uaa.entity.RolePermissionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色与权限对应关系
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@Mapper
public interface RolePermissionDao extends BaseMapper<RolePermissionEntity> {
	
}
