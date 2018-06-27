package com.kevin.microservicebase.userservice.feign.hystrix;

import com.kevin.microservicebase.userservice.feign.AuthServiceClient;
import com.kevin.microservicebase.userservice.entity.JWTBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @ClassName: AuthServiceHystrix
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/22 09:49
 */
@Slf4j
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWTBean getToken(String authorization, String type, String username, String password) {
        log.info("--------opps getToken hystrix---------");
        return null;
    }
}
