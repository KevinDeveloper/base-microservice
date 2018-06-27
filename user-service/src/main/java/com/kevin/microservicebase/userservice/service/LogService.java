package com.kevin.microservicebase.userservice.service;

import com.kevin.microservicebase.userservice.entity.SysLog;

/**
 * @ClassName: LogService
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/27 16:11
 */
public interface LogService {
    void log(SysLog sysLog);
}
