package com.itheima.security.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.security.uaa.dao.RolePermissionDao;
import com.itheima.security.uaa.entity.RolePermissionEntity;
import com.itheima.security.uaa.service.RolePermissionService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionDao, RolePermissionEntity> implements RolePermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RolePermissionEntity> page = this.page(
                new Query<RolePermissionEntity>().getPage(params),
                new QueryWrapper<RolePermissionEntity>()
        );

        return new PageUtils(page);
    }

}