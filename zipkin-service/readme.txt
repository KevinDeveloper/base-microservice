Zipkin 服务调用关系。
Spring Cloud Sleuth 集成Zipkin, Zipkin 分为Zipkin Server 和Zipkin Client
文章将分为两部分讲解
一、Zipkin Server
二、Zipkin Client

一、Zipkin Server
1、起步依赖：
		<dependency>
			<groupId>io.zipkin.java</groupId>
			<artifactId>zipkin-server</artifactId>
		</dependency>
		<dependency>
			<groupId>io.zipkin.java</groupId>
			<artifactId>zipkin-autoconfigure-ui</artifactId>
		</dependency>

2、开启Zipkvin Server 功能
启动类加上 @EnableZipkinServer 注解，开启Zipkin Server 功能

二、Zipkin Client
1、起步依赖：
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-zipkin</artifactId>
		</dependency>

2、配置地址
spring:
  zipkin:
    base-url: http://localhost:9400


























































