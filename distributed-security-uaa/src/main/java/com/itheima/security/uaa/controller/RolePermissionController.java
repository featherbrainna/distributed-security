package com.itheima.security.uaa.controller;


import com.itheima.security.uaa.entity.RolePermissionEntity;
import com.itheima.security.uaa.service.RolePermissionService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 角色与权限对应关系
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@RestController
@RequestMapping("security/rolepermission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("security:rolepermission:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = rolePermissionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{roleId}")
    //@RequiresPermissions("security:rolepermission:info")
    public R info(@PathVariable("roleId") Long roleId){
		RolePermissionEntity rolePermission = rolePermissionService.getById(roleId);

        return R.ok().put("rolePermission", rolePermission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("security:rolepermission:save")
    public R save(@RequestBody RolePermissionEntity rolePermission){
		rolePermissionService.save(rolePermission);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("security:rolepermission:update")
    public R update(@RequestBody RolePermissionEntity rolePermission){
		rolePermissionService.updateById(rolePermission);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("security:rolepermission:delete")
    public R delete(@RequestBody Long[] roleIds){
		rolePermissionService.removeByIds(Arrays.asList(roleIds));

        return R.ok();
    }

}
