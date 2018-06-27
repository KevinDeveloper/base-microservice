本项目是本人通过看书学习《深入理解Spring Cloud与微服务构建》后，根据知识内容，搭建的一个完整的微服务系统
采用了Maven 多Module工程的结构，提供了一整套微服务治理的功能。
本项目不作任何商业用途, 仅作为个人学习总结记录，或个人开发后期参考，或项目扩展而使用。
本项目将持续完善....

一、结构介绍：

多个Module工程，分别是：
配置中心config-server、
注册中心eureka-server、
授权中心Uaa服务uaa-service、
Turbine聚合监控服务monitoring-service、
链路追踪服务zipkin-service、
聚合监控服务admin-service、
路由网关服务gateway-service、
日志服务log-servic、
基本工具包common-support

sql文件夹存入数据库使用到的sql脚本。
各个服务各有其功能，相互协作、构成一套完整的微服务系统。

二、使用到的技术栈：
Eureka  服务注册和发现
Spring Cloud Config 分布式服务配置中心
Spring Cloud OAuth2 包括SpringOAuth2 和SpringBootSecurit，结合JWT 为微服务提供一整套的安全解决方案
Feign   声明式服务调用，用于消费服务
Ribbon 负载均衡
Hystrix 熔断器
Hystrix Dashboard 熔断器仪盘盘，用户监控熔断器的状况
Turbine 聚合多个Hystrix Dashboard
Spring Cloud Sleuth 集成Zipkin, 用于服务链路追踪
Spring Boot Admin 聚合监控服务的状况
Zuul    服务网关，用于服务智能路由、负载均衡
Spring Data JPA 数据库采用MySQL, 实体对象持久化采用JPA
Swagger2 ApI接口文档组件
EESTful API 接口风格采用RESTful风格
RabbitMQ    消息服务器，用于发送日志消息。


三、项目详情说明
1、需要注册Eureka 的服务有：
eureka-server port:8760
基础服务有：
网关服务：gateway-service -> port:5000
日志服务：log-service
鉴权和认证服务：uaa-service
调用链服务：zipkin-service
配置中心：config-server -> port:8900

业务服务
user-service
blog-service

2、需要配置Zipkin 的服务有
业务服务
user-service
blog-service


































