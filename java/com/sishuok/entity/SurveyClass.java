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
@Table(name = "survey_class")
@Data
public class SurveyClass implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -505947172422922178L;
	private int id;
	private String surveyName;
	private int surveyClass;

	public String getSurveyName() {
		return surveyName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setSurveyName(String surveyName) {
		this.surveyName = surveyName;
	}

	public int getSurveyClass() {
		return surveyClass;
	}

	public void setSurveyClass(int surveyClass) {
		this.surveyClass = surveyClass;
	}

	private int survey1;
	private String survey2;
	private String survey3;
	private int survey4;
	private int survey5;
	private int survey6;
	private int survey7;
	private int survey8;
	private int survey9;
	private int survey10;
	// 用户的标识
	private String openId;
	@ExcelField(title="姓名", align=2, sort=2)
	private String name;
	@ExcelField(title="手机号", align=2, sort=3)
	private String phone;
	@ExcelField(title="电子邮件", align=2, sort=4)
	private String email;
}