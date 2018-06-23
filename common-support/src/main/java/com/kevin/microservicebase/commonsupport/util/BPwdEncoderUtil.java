package com.kevin.microservicebase.commonsupport.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @ClassName: BPwdEncoderUtil
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/20 09:30
 */
public class BPwdEncoderUtil {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String BCryptPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}
