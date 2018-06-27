package com.kevin.microservicebase.userservice.service;

import com.kevin.microservicebase.commonsupport.dto.RespDTO;
import com.kevin.microservicebase.userservice.entity.LoginDTO;
import com.kevin.microservicebase.userservice.entity.User;

/**
 * @ClassName: UserService
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/22 10:03
 */
public interface UserService {
    /**
     * 创建用户
     * @param user
     * @return
     */
    User createUser(User user);

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    User getUserInfo(String username);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    LoginDTO login(String username , String password);
}
