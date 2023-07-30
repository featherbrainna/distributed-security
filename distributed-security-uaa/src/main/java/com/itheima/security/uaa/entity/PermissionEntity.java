package com.itheima.security.uaa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.itheima.security.uaa.utils.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 权限表
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@Data
@TableName("t_permission")
public class PermissionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 权限标识符
	 */
	@NotBlank(groups = AddGroup.class)
	private String code;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
	/**
	 * 修改时间
	 */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

}
