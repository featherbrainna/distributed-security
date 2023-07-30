package com.itheima.security.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.security.uaa.dao.PermissionDao;
import com.itheima.security.uaa.entity.PermissionEntity;
import com.itheima.security.uaa.service.PermissionService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionEntity> implements PermissionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PermissionEntity> page = this.page(
                new Query<PermissionEntity>().getPage(params),
                new QueryWrapper<PermissionEntity>()
        );

        return new PageUtils(page);
    }

}