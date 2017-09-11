package com.sishuok.dao;

import com.sishuok.entity.ChannelClass;
import com.sishuok.entity.SurveyClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//@Transactional

@Repository
public interface ChannelClassDao extends CrudRepository<ChannelClass, Integer> {
  public ChannelClass findByEmail(String email);
}