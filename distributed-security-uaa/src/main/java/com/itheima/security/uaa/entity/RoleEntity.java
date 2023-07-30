package com.itheima.security.uaa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.itheima.security.uaa.utils.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 角色表
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 19:20:32
 */
@Data
@TableName("t_role")
public class RoleEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 角色名称
	 */
	@NotBlank(groups = AddGroup.class,message = "添加角色时角色名不能为空")
	private String roleName;
	/**
	 * 角色描述
	 */
	private String description;
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
	/**
	 * 状态
	 */
	private String status;

}
