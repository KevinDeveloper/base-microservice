Eureka 服务发现使用说明
Eureka 分为Eureka Server 和 Eureka Client

文章将分两部分进行讲解
一、eureka-server 服务注册中心
二、eureka-client 服务实例实现


一、eureka-service 服务注册中心
1、起步依赖：
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka-server</artifactId>
        </dependency>

2、设置配置文件，设置端口，并不自动注册，
server:
  port: 8760
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/

3、开启服务注册功能
在启动类上加上 @EnableEurekaServer 注解，开启EurekaServer的功能。
访问http://localhost:8760/ 可查看当前所有注册的服务

二、eureka-client 服务实例发现
1、起步依赖
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>

2、设置配置文件，设置端口和服务注册中心地址
eureka:
  client:
    service-url:
      defaultZone: http://localhost:8760/eureka/

server:
  port: 8762
spring:
  application:
    name: eureka-client

3、开启服务发现功能
在启动类上加上 @EnableEurekaClient 注解，开启EurekaClient的功能。








































































