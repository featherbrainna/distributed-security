package com.itheima.security.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.security.uaa.entity.PermissionEntity;
import com.itheima.security.uaa.utils.PageUtils;

import java.util.Map;

/**
 * 权限表
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
public interface PermissionService extends IService<PermissionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

