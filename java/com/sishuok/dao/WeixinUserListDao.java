package com.sishuok.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sishuok.entity.WeixinUserInfo;
import com.sishuok.entity.WeixinUserList;

//@Transactional

@Repository
public interface WeixinUserListDao extends CrudRepository<WeixinUserList, Long> {
//  public WeixinUserList getWeixinUserList();
  public WeixinUserList save(WeixinUserList weixinUserList);
//  public String findStringAll("select next_openid from weixin_user_list where id=(SELECT max(id) from weixin_user_list)");
//	public List<WeixinUserInfo> getMax() {
//		return find("from WeixinUserInfo where delFlag=:p1 order by id", 0);
//	}
} 