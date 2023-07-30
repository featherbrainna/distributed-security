package com.itheima.security.uaa.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

import lombok.Data;

/**
 * 
 * 
 * @author wzy
 * @email 578996401@qq.com
 * @date 2023-07-26 17:29:26
 */
@Data
@TableName("oauth2_registered_client")
public class ClientEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private String id;
	/**
	 * 
	 */
	private String clientId;
	/**
	 * 
	 */
	private Instant clientIdIssuedAt;
	/**
	 * 
	 */
	private String clientSecret;
	/**
	 * 
	 */
	private Instant clientSecretExpiresAt;
	/**
	 * 
	 */
	private String clientName;

	private String scopes;

}
