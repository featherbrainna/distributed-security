package com.itheima.security.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.security.uaa.dao.RoleDao;
import com.itheima.security.uaa.entity.RoleEntity;
import com.itheima.security.uaa.service.RoleService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleEntity> implements RoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<RoleEntity> page = this.page(
                new Query<RoleEntity>().getPage(params),
                new QueryWrapper<RoleEntity>()
        );

        return new PageUtils(page);
    }

}