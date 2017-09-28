package com.sishuok.entity;

import com.sishuok.utils.excel.annotation.ExcelField;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 接口访问凭证
 * 
 * @author liufeng
 * @date 2013-08-08
 */
@Entity
@Table(name = "channel_class")
@Data
//@NamedQuery(name = "Task.findByTaskName", query = "select t from ChannelClass t where t.taskName = ?1")
public class ChannelClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -505947172422922178L;
	private int id;
	@ExcelField(title="渠道代码", align=2, sort=1)
	private String channelName;
	private int channelClass;

	public String getSurveyName() {
		return channelName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setSurveyName(String channelName) {
		this.channelName = channelName;
	}

	public int getSurveyClass() {
		return channelClass;
	}

	public void setSurveyClass(int channelClass) {
		this.channelClass = channelClass;
	}

	@ExcelField(title="抽奖码", align=2, sort=21)
//	@GeneratedValue(strategy = GenerationType.)
	private int code;
	private String survey1;
	private int survey2;
	private int survey3;
	private int survey4;
	private int survey5;
	private int survey6;
	private int survey7;
	private int survey8;
	private int survey9;
	private int survey10;
	private int survey11;
	private int survey12;
	private int survey13;
	private int survey14;
	private int survey15;
	private int survey16;
	private int survey17;
	private int survey18;
	private int survey19;
	private int survey20;
	// 用户的标识
	private String openId;
	@ExcelField(title="姓名", align=2, sort=2)
	private String name;
	@ExcelField(title="手机号", align=2, sort=3)
	private String phone;
	@ExcelField(title="电子邮件", align=2, sort=4)
	private String email;
}