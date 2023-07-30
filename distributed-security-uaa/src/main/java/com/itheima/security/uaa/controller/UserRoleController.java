package com.itheima.security.uaa.controller;


import com.itheima.security.uaa.entity.UserRoleEntity;
import com.itheima.security.uaa.service.UserRoleService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 用户与角色关系
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@RestController
@RequestMapping("security/userrole")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("security:userrole:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userRoleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{userId}")
    //@RequiresPermissions("security:userrole:info")
    public R info(@PathVariable("userId") Long userId){
		UserRoleEntity userRole = userRoleService.getById(userId);

        return R.ok().put("userRole", userRole);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("security:userrole:save")
    public R save(@RequestBody UserRoleEntity userRole){
		userRoleService.save(userRole);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("security:userrole:update")
    public R update(@RequestBody UserRoleEntity userRole){
		userRoleService.updateById(userRole);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("security:userrole:delete")
    public R delete(@RequestBody Long[] userIds){
		userRoleService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
