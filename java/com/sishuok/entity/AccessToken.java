package com.sishuok.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 接口访问凭证
 * 
 * @author liufeng
 * @date 2013-08-08
 */
@Entity
@Table(name = "access_token")
public class AccessToken implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -505947172422913178L;
	private int id;
	// 获取到的凭证
	private String token;
	// 凭证有效时间，单位：秒
	private int expiresIn;

	public String getToken() {
		return token;
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setToken(String token) {
		this.token = token;
	}

	public int getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}