package com.sishuok.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

import net.sf.json.JSONObject;
//import javax.persistence.Entity;
//import javax.persistence.Table;

/**
 * 类名: WeixinUserInfo </br> 描述: 微信用户的基本信息 </br> 开发人员： souvc </br> 创建时间：
 * 2015-11-27 </br> 发布版本：V1.0 </br>
 */
// @EntityScan
@Entity
@Table(name = "weixin_user_list")
public class WeixinUserList implements Serializable {
	private long id;
	private int count;
	private JSONObject data;

	private List<String> openid;
	private String next_openid;

	private int total;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public long getId() {
		return id;
	}

	protected Date createDate;// 创建日期

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getCount() {
		return count;
	}

	@Transient
	public JSONObject getData() {
		return data;
	}

	public String getNext_openid() {
		return next_openid;
	}

	@Transient
	public List<String> getOpenid() {
		return openid;
	}

	public int getTotal() {
		return total;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}

	public void setOpenid(List<String> openid) {
		this.openid = openid;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
