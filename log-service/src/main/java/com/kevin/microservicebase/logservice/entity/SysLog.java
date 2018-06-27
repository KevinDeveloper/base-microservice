package com.kevin.microservicebase.logservice.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName: SysLog
 * @Description:
 * @Author: Kevin
 * @Date: 2018/6/27 15:09
 */
@Data
@Entity
@Table(name = "sys_log")
public class SysLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 用户名
     */
    @Column
    private String username;
    /**
     * 用户操作
     */
    @Column
    private String operation;
    /**
     * 请求方法
     */
    @Column
    private String method;
    /**
     * 请求参数
     */
    @Column
    private String params;
    /**
     * IP地址
     */
    @Column
    private String ip;
    /**
     * 创建时间
     */
    @Column
    private Date createDate;
}
