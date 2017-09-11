package com.sishuok.dao;

import java.util.LinkedList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sishuok.entity.WeixinUserInfo;

//@Transactional

@Repository
public interface WeixinUserInfoDao extends CrudRepository<WeixinUserInfo, String> {
  public WeixinUserInfo findByOpenId(String openId);
  public WeixinUserInfo save(WeixinUserInfo weixinUserInfo);
//	public List<WeixinUserInfo> findAllList() {
//		return find("from WeixinUserInfo where delFlag=:p1 order by id", 0);
//	}
//  public List<WeixinUserInfo> findByPrizeClassAndPrizeClassNotLike(int pri);
  @Query("select e from WeixinUserInfo e order by e.prizeClass")
public LinkedList<WeixinUserInfo> findByPrizeClassAndOrderByPrizeClass();
} 