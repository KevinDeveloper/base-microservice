package com.kevin.microservicebase.userservice.config;

import feign.Retryer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * @ClassName: FeignConfig
 * @Description:
 * @Author: Kevin
 * @Date: 2018/5/25 10:48
 */
@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer() {
        //重写feign client 请求失败重试策略，重试间隔100ms， 最大重试时间为1s, 重试次数为5
        return new Retryer.Default(100,SECONDS.toMillis(1),5);
    }
}
