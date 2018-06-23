package com.kevin.microservicebase.userservice.feign;

import com.kevin.microservicebase.userservice.feign.hystrix.AuthServiceHystrix;
import com.kevin.microservicebase.userservice.config.FeignConfig;
import com.kevin.microservicebase.userservice.entity.JWTBean;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @ClassName: AuthServiceClient
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/22 09:41
 */
@FeignClient(value = "uaa-service", configuration = FeignConfig.class, fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {
    @PostMapping(value = "/oauth/token")
    JWTBean getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type, @RequestParam("username") String username, @RequestParam("password") String password);
}
