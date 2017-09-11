package com.sishuok.dto;

import com.sishuok.utils.excel.annotation.ExcelField;
import lombok.Data;
import org.springframework.ui.Model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

@Data
public class Channel implements Serializable, Model {
	private static final long serialVersionUID = 1L;
	@ExcelField(title="渠道代码", align=2, sort=1)
	private String channelName;
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
//
//	public int getSurvey0() {
//		return survey0;
//	}
//
//	public void setSurvey0(int survey0) {
//		this.survey0 = survey0;
//	}
//
//	public int getSurvey1() {
//		return survey1;
//	}
//
//	public void setSurvey1(int survey1) {
//		this.survey1 = survey1;
//	}
//
//	public int getSurvey2() {
//		return survey2;
//	}
//
//	public void setSurvey2(int survey2) {
//		this.survey2 = survey2;
//	}
//
//	public int getSurvey3() {
//		return survey3;
//	}
//
//	public void setSurvey3(int survey3) {
//		this.survey3 = survey3;
//	}
//
//	public int getSurvey4() {
//		return survey4;
//	}
//
//	public void setSurvey4(int survey4) {
//		this.survey4 = survey4;
//	}

	@Override
	public Model addAllAttributes(Collection<?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addAllAttributes(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addAttribute(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model addAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> asMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean containsAttribute(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Model mergeAttributes(Map<String, ?> arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
