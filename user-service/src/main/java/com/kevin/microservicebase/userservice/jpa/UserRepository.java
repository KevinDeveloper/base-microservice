package com.kevin.microservicebase.userservice.jpa;

import com.kevin.microservicebase.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: UserRepository
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/20 09:25
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String userName);
}
