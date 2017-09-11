package weixin;

import java.io.Serializable;
import java.util.List;

//import javax.persistence.Entity;
//import javax.persistence.Table;


import net.sf.json.JSONObject;

/**
* 类名: WeixinUserInfo </br>
* 描述: 微信用户的基本信息 </br>
* 开发人员： souvc </br>
* 创建时间： 2015-11-27 </br>
* 发布版本：V1.0 </br>
 */
//@EntityScan
//@Entity
//@Table(name = "weixin_user_list")
public class WeixinUserList implements Serializable{
  private int count;
  private JSONObject data;
  
  private List<String> openid;
  private String next_openid;
  
  public  List<String> getOpenid() {
	return openid;
}
public void setOpenid( List<String> openid) {
	this.openid = openid;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public JSONObject getData() {
	return data;
}
public void setData(JSONObject data) {
	this.data = data;
}
public String getNext_openid() {
	return next_openid;
}
public void setNext_openid(String next_openid) {
	this.next_openid = next_openid;
}
public int getTotal() {
	return total;
}
public void setTotal(int total) {
	this.total = total;
}
private int total;
}
