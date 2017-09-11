package com.sishuok.service;

import com.sishuok.entity.AccessToken;

public interface AccessTokenService {

    /**
     * 新增一个用户
     * @param name
     * @param age
     */
    void create(String token, Integer expiresIn);


    /**
     * 删除所有用户
     */
    void deleteAllAccessTokens();
    String get();

}