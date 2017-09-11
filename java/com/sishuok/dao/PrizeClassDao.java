package com.sishuok.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sishuok.entity.PrizeClass;

//@Transactional

@Repository
public interface PrizeClassDao extends CrudRepository<PrizeClass, Integer> {
//  public WeixinUserInfo findByOpenId(String openId);
//  public WeixinUserInfo save(WeixinUserInfo weixinUserInfo);
//	public List<WeixinUserInfo> findAllList() {
//		return find("from WeixinUserInfo where delFlag=:p1 order by id", 0);
//	}
//  public WeixinUserInfo findOrderByCreateDateDESC();
} 