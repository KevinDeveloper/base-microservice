package com.kevin.microservicebase.logservice.rabbitmq;

import lombok.Data;

/**
 * @ClassName: User
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/27 15:15
 */
@Data
public class User {
    private String username;
    private String password;
}
