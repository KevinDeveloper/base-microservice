package com.kevin.microsevicebase.uaaservice.jpa;


import com.kevin.microsevicebase.uaaservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: UserDao
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/15 09:14
 */
public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
