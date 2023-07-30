package com.itheima.security.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.security.uaa.entity.PermissionEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@Mapper
public interface PermissionDao extends BaseMapper<PermissionEntity> {
	
}
