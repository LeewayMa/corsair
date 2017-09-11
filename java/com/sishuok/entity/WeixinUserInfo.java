package com.sishuok.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
//import javax.persistence.Id;
//import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 类名: WeixinUserInfo </br> 描述: 微信用户的基本信息 </br> 开发人员： souvc </br> 创建时间：
 * 2015-11-27 </br> 发布版本：V1.0 </br>
 */
// @EntityScan
@Entity
@Table(name = "weixin_user_info")
public class WeixinUserInfo implements Serializable {
	private int prizeClass;

	@JsonIgnore
	public int getPrizeClass() {
		return prizeClass;
	}

	public void setPrizeClass(int prizeClass) {
		this.prizeClass = prizeClass;
	}

	// 用户的标识
	private String openId;
	// 关注状态（1是关注，0是未关注），未关注时获取不到其余信息
	private int subscribe;
	// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String subscribeTime;
	// 昵称
	private String nickName;
	// 用户的性别（1是男性，2是女性，0是未知）
	private int sex;
	// 用户所在国家
	private String country;
	// 用户所在省份
	private String province;
	// 用户所在城市
	private String city;
	// 用户的语言，简体中文为zh_CN
	private String language;
	// 用户头像
	private String headImgUrl;

	@Id
	@Column(columnDefinition = "VARCHAR(32) COMMENT 'openId'")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public int getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribeTime() {
		return subscribeTime;
	}

	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public int getSex() {
		return sex;
	}

	protected Date createDate;// 创建日期

	protected Date updateDate;// 更新日期
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
}
