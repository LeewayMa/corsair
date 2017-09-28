package com.sishuok.dao;

import com.sishuok.entity.ChannelClass;
import com.sishuok.entity.SurveyClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.List;

//@Transactional

@Repository
public interface ChannelClassDao extends JpaRepository<ChannelClass, Integer> {
  public List<ChannelClass> findByChannelNameAndEmail(String channelName,String email);

//  @Query("select distinct c.code from ChannelClass c where c.channel_name = :u1")

//  @Query("from ChannelClass c where c.channelName = :u1")
//  public Integer findMaxCodeByChannelName(@Param("u1") String channelName);

//  public Iterator<ChannelClass> findAllByChannelName(String channelName);


//  List<ChannelClass> findDistinctByChannelName();
//  ChannelClass findOneByChannelNameOrderByIdDesc(String name);

//    @Query(value="select t from ChannelClass t where t.channel_name = :u1")
//    ChannelClass findGroupBychannelName();
}