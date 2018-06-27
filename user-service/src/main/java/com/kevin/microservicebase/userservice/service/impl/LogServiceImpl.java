package com.kevin.microservicebase.userservice.service.impl;

import com.alibaba.fastjson.JSON;
import com.kevin.microservicebase.userservice.config.RabbitConfig;
import com.kevin.microservicebase.userservice.entity.SysLog;
import com.kevin.microservicebase.userservice.service.LogService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName: LogServiceImpl
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/27 16:12
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    @Override
    public void log(SysLog sysLog) {
        rabbitTemplate.convertAndSend(RabbitConfig.queueName, JSON.toJSONString(sysLog));
    }
}
