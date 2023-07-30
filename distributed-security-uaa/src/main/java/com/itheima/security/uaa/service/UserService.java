package com.itheima.security.uaa.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.security.uaa.entity.UserEntity;
import com.itheima.security.uaa.utils.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 12:02:25
 */
public interface UserService extends IService<UserEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<String> queryAllPerms(Long id);
}

