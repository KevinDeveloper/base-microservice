package com.kevin.microservicebase.userservice.service.impl;

import com.kevin.microservicebase.commonsupport.dto.RespDTO;
import com.kevin.microservicebase.commonsupport.exception.CommonException;
import com.kevin.microservicebase.commonsupport.exception.ErrorCode;
import com.kevin.microservicebase.commonsupport.util.BPwdEncoderUtil;
import com.kevin.microservicebase.userservice.entity.JWTBean;
import com.kevin.microservicebase.userservice.entity.LoginDTO;
import com.kevin.microservicebase.userservice.entity.User;
import com.kevin.microservicebase.userservice.feign.AuthServiceClient;
import com.kevin.microservicebase.userservice.jpa.UserRepository;
import com.kevin.microservicebase.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Objects;

/**
 * @ClassName: UserServiceImpl
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/22 10:03
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    //private static Logger logger = LogManager.getLogger(UserServiceImpl.class);


    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthServiceClient authServiceClient;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);

    }

    @Override
    public User getUserInfo(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public LoginDTO login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new CommonException(ErrorCode.USER_NOT_FOUND);
        }
        if (!BPwdEncoderUtil.matches(password, user.getPassword())) {
            throw new CommonException(ErrorCode.USER_PASSWORD_ERROR);
        }
        String enAuth = new String(Base64.getEncoder().encode("uaa-service:123456".getBytes()));
        enAuth = "Basic " + enAuth;
        log.info("auth = {}", enAuth);
        JWTBean jwtBean = authServiceClient.getToken(enAuth, "password", username, password);
        // 获得用户菜单
        if (Objects.isNull(jwtBean)) {
            throw new CommonException(ErrorCode.GET_TOKEN_FAIL);
        }
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUser(user);
        loginDTO.setToken(jwtBean.getAccess_token());
//        loginDTO.setToken("test_token");
        return loginDTO;
    }
}
