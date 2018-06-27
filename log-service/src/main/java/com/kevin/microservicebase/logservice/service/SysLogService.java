package com.kevin.microservicebase.logservice.service;

import com.kevin.microservicebase.logservice.entity.SysLog;
import com.kevin.microservicebase.logservice.jpa.SysLogJpa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: SysLogService
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/27 15:18
 */
@Service
public class SysLogService {
    @Autowired
    SysLogJpa sysLogJpa;

    public void saveLogger(SysLog sysLog) {
        sysLogJpa.save(sysLog);
    }
}
