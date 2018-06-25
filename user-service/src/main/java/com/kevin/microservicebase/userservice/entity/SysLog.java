package com.kevin.microservicebase.userservice.entity;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: SysLog
 * @Description: 操作日志
 * @Author: Kevin
 * @Date: 2018/6/22 10:15
 */
@Data
public class SysLog {
    private Long id;
    //用户名
    private String username;
    //用户操作
    private String operation;
    //请求方法
    private String method;
    //请求参数
    private String params;
    //IP地址
    private String ip;
    //创建时间
    private Date createDate;
}
