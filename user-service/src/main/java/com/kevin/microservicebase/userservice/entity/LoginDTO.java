package com.kevin.microservicebase.userservice.entity;

import lombok.Data;

/**
 * @ClassName: LoginDTO
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/22 10:14
 */
@Data
public class LoginDTO {
    private User user;
    private String token;

}
