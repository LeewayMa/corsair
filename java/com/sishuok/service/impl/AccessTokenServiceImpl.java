package com.sishuok.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.sishuok.entity.AccessToken;
import com.sishuok.service.AccessTokenService;

@Service
public class AccessTokenServiceImpl implements AccessTokenService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void create(String token, Integer expiresIn) {
        jdbcTemplate.update("insert into access_token(id,token, expires_In) values(?,?, ?)", 1,token, expiresIn);
    }


    @Override
    public void deleteAllAccessTokens() {
        jdbcTemplate.update("delete from access_token");
    }
    @Override
    public String get() {
//    	SqlRowSet set =	jdbcTemplate.queryForObject("select * from ACCESS_TOKEN where id = 1",AccessToken.class);
    	
        return 	jdbcTemplate.queryForObject("select token from access_token where id = 1",String.class);
    }
}