package com.sishuok.dao;

import com.sishuok.entity.ChannelClass;
import com.sishuok.entity.SurveyClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Iterator;

//@Transactional

@Repository
public interface ChannelClassDao extends CrudRepository<ChannelClass, Integer> {
  public ChannelClass findByEmail(String email);
//  public Integer findMaxCodeByChannelName(String channelName);

//  public Iterator<ChannelClass> findAllByChannelName(String channelName);

}