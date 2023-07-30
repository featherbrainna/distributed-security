package com.itheima.security.uaa.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itheima.security.uaa.entity.ClientEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-26 17:29:26
 */
@Mapper
public interface ClientDao extends BaseMapper<ClientEntity> {
	
}
