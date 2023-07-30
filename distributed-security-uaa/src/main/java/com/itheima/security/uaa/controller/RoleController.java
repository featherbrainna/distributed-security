package com.itheima.security.uaa.controller;


import com.itheima.security.uaa.entity.RoleEntity;
import com.itheima.security.uaa.service.RoleService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.R;
import com.itheima.security.uaa.utils.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 角色表
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@RestController
@RequestMapping("security/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("security:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = roleService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("security:role:info")
    public R info(@PathVariable("id") Long id){
		RoleEntity role = roleService.getById(id);

        return R.ok().put("role", role);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("security:role:save")
    public R save(@Validated(AddGroup.class) @RequestBody RoleEntity role){
		roleService.save(role);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("security:role:update")
    public R update(@RequestBody RoleEntity role){
		roleService.updateById(role);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("security:role:delete")
    public R delete(@RequestBody Long[] ids){
		roleService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
