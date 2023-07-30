package com.itheima.security.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.security.uaa.entity.RolePermissionEntity;
import com.itheima.security.uaa.utils.PageUtils;

import java.util.Map;

/**
 * 角色与权限对应关系
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
public interface RolePermissionService extends IService<RolePermissionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

