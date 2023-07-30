package com.itheima.security.uaa.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.itheima.security.uaa.utils.group.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-25 12:02:25
 */
@Data
@TableName("t_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 用户名
	 */
	@NotBlank(groups = AddGroup.class,message = "添加用户用户名不能为空")
	private String username;
	/**
	 * 用户密码
	 */
	@NotBlank(groups = AddGroup.class,message = "添加用户用户密码不能为空")
	private String password;
	/**
	 * 用户姓名
	 */
	@NotBlank(groups = AddGroup.class,message = "添加用户用户姓名不能为空")
	private String fullname;
	/**
	 * 手机号
	 */
	private String mobile;
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
