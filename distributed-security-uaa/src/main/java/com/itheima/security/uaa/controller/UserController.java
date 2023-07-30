package com.itheima.security.uaa.controller;


import com.itheima.security.uaa.entity.UserEntity;
import com.itheima.security.uaa.service.UserService;
import com.itheima.security.uaa.utils.PageUtils;
import com.itheima.security.uaa.utils.R;
import com.itheima.security.uaa.utils.group.AddGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 12:02:25
 */
@RestController
@RequestMapping(path = "/user",produces = "application/json;charset=utf-8")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("security:user:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("security:user:info")
    public R info(@PathVariable("id") Long id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping(value = "/save",consumes = "application/json;charset=utf-8")
    //@RequiresPermissions("security:user:save")
    public R save(@Validated(AddGroup.class) @RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/update",consumes = "application/json;charset=utf-8")
    //@RequiresPermissions("security:user:update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping(value = "/delete",consumes = "application/json;charset=utf-8")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    //@RequiresPermissions("security:user:delete")
    public R delete(@RequestBody Long[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
