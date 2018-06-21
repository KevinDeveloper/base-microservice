config-server 工程统一管理服务的配置文件。config-server可以从本地仓库读取配置文件，也可以从远程git仓库读取。本示例从本地仓库读取。
spring cloud config 分为两部分，config-server 和config-client。

一、config-server 配置中心
1、起步依赖：
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-config-server</artifactId>
		</dependency>

2、 配置
spring:
  cloud:
   #配置配置服务，从本地仓库shared目录下读取配置文件
    config:
      server:
        native:
          search-locations: classpath:/shared
  profiles:
     active: native

3、启动类加 @EnableConfigServer 注解开启configserver 功能

备注：其他服务配置文件命名格式为：{applicationName}-{activeProfile}.yml。
其中application.yml 为所有服务共享的配置文件

二、config-client
1、起步依赖：
        <!--config client-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
2、设置配置中心地址及加载配置文件类型，和失败策略
spring:
  cloud:
    #设置配置中心地址
    config:
      uri: http://localhost:8900
      fail-fast: true
  #设置加载配置文件类型
  profiles:
    active: pro


备注：

一、有两种配置中心地址的方式
1、配置文件中写好
2、通过service-id从注册中心获取
spring:
  application:
    name: zipkin-service
  cloud:
    #设置配置中心地址
    config:
      #uri: http://localhost:8900 #单个地址配置
      fail-fast: true
      #从服务注册中心获取配置中心服务
      discovery:
        enabled: true
        service-id: config-server
二、config-client 启动过程如果遇到No instances found of configserver (config-server) 的提示错误，
则是 eureka-server 和config-service 启动时序有问题，config-client需要从eureka-server发现配置中心，却又需要从config-service配置中心加载出eureka-server的地址，所以出现了问题。
修改：将eureka-server 的地址配置放到bootstrap.yml里面。

















































