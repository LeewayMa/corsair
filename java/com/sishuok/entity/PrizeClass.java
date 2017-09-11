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
@Table(name = "prize_class")
public class PrizeClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -505947172422913178L;
	private int id;
	// 获取到的凭证
	private String prizeName;
	// 凭证有效时间，单位：秒
	private int prizeClass;

	public String getPrizeName() {
		return prizeName;
	}

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setPrizeName(String prizeName) {
		this.prizeName = prizeName;
	}

	public int getPrizeClass() {
		return prizeClass;
	}

	public void setPrizeClass(int prizeClass) {
		this.prizeClass = prizeClass;
	}
}