package com.kevin.microservicebase.userservice.entity;

import lombok.Data;

/**
 * @ClassName: JWTBean
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/22 09:42
 */
@Data
public class JWTBean {
    String access_token;
    String token_type;
    String refresh_token;
    int expires_in;
    String scope;
    String jti;
}
