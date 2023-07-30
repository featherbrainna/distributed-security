package com.itheima.security.uaa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.security.uaa.dao.UserRoleDao;
import com.itheima.security.uaa.entity.UserRoleEntity;
import com.itheima.security.uaa.service.UserRoleService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleEntity> implements UserRoleService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserRoleEntity> page = this.page(
                new Query<UserRoleEntity>().getPage(params),
                new QueryWrapper<UserRoleEntity>()
        );

        return new PageUtils(page);
    }

}