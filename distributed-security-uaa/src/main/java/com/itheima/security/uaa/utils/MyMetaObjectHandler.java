package com.itheima.security.uaa.utils;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 自动填充数据库字段实现处理器
 * @author 王忠义
 * @version 1.0
 * @date 2023/7/25 19:12
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        //通用自动填充处理
        setFieldValByName("createTime", LocalDateTime.now(),metaObject);
        setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", LocalDateTime.now(),metaObject);
    }
}
