gateway-service 为网关工程，集成了zuul 组件。
zuul组件具有转发、过滤、鉴权的功能。
集成步骤：

1、起步依赖：
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zuul</artifactId>
		</dependency>

2、配置文件：
#配置网关
zuul:
  host:
    connect-timeout-millis: 20000 #连接超时
    socket-timeout-millis: 20000 #socket连接时间
  #api版本
  prefix: /v1
  #配置路由功能
  routes:
    user-service:
      path: /userapi/**
      serviceId: user-service
      sensitiveHeaders:
    blog-service:
      path: /blogapi/**
      serviceId: blog-service
      sensitiveHeaders: #设置敏感头信息为空

3、启动类加上 @EnableZuulProxy 注解，启动Zunl网关代理功能









































