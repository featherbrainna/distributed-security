package com.itheima.security.uaa.controller;

import com.itheima.security.uaa.entity.PermissionEntity;
import com.itheima.security.uaa.service.PermissionService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.R;
import com.itheima.security.uaa.utils.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 权限表
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@RestController
@RequestMapping("security/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("security:permission:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = permissionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("security:permission:info")
    public R info(@PathVariable("id") Long id){
		PermissionEntity permission = permissionService.getById(id);

        return R.ok().put("permission", permission);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("security:permission:save")
    public R save(@Validated(AddGroup.class) @RequestBody PermissionEntity permission){
		permissionService.save(permission);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("security:permission:update")
    public R update(@RequestBody PermissionEntity permission){
		permissionService.updateById(permission);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("security:permission:delete")
    public R delete(@RequestBody Long[] ids){
		permissionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
