package com.kevin.microservicebase.logservice.jpa;

import com.kevin.microservicebase.logservice.entity.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @ClassName: SysLogJpa
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/27 15:17
 */
public interface SysLogJpa extends JpaRepository<SysLog, Long> {
}
