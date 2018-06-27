package com.kevin.microservicebase.userservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @ClassName: GlobalMethodSecurityConfig
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/19 19:49
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig {
}
