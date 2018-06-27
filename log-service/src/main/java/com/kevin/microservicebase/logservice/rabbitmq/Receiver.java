package com.kevin.microservicebase.logservice.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.kevin.microservicebase.logservice.entity.SysLog;
import com.kevin.microservicebase.logservice.service.SysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: Receiver
 * @Description: 用于接收rabbitmq 服务器的消息，同时将消息进行数据库持久化
 * @Author: Kevin
 * @Date: 2018/6/27 15:16
 */
@Slf4j
@Component
public class Receiver {
    /**
     * 信号量，阻塞其他线程
     */
    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    SysLogService sysLogService;
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        SysLog sysLog=  JSON.parseObject(message,SysLog.class);
        sysLogService.saveLogger(sysLog);
        latch.countDown();
    }
}
