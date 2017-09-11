package com.sishuok.dao;

import com.sishuok.dto.Surveys;
import com.sishuok.entity.SurveyClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Transactional

@Repository
public interface SurveyClassDao extends CrudRepository<SurveyClass, Integer> {
  public SurveyClass findByEmail(String email);
//  public WeixinUserInfo save(WeixinUserInfo weixinUserInfo);
//	public List<WeixinUserInfo> findAllList() {
//		return find("from WeixinUserInfo where delFlag=:p1 order by id", 0);
//	}
//  public WeixinUserInfo findOrderByCreateDateDESC();
} 