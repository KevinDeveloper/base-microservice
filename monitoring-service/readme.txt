monitoring-service 是一个集成了turbine的聚合监控服务。
在user-service 服务中，使用了Hystrix Dashboard 组件监控服务的熔断器状况，每个服务都有一个Hystrix Dashboard主页，微服务，当服务数量多时，监控不方便。
Turbine 是Netflix开源的一个组件，用于聚合多个Hystrix Dashboard ,将多个Hystrix Dashboard 组件的数据放在一个页面上展示，进行集中监控。

turbine 接入流程

1、起步依赖：
		<!--引入turbine 聚合多个hystrix dashboard ，统一监控-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-turbine</artifactId>
		</dependency>
		<!--增加actuator控制器-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>

2、配置文件：
#配置turbine 统一监控服务的hystrix dashboard , 采用默认监控集群配置
turbine:
  aggregator:
    clusterConfig: default
  appConfig: user-service #, blog-service
  clusterNameExpression: new String("default")
  instanceUrlSuffix: /user-service/hystrix.stream
  # 当受监控的actutor 如user-service 设置了context-path 时，需要重新设置监控指标数据的地址。
  #turbine 默认没有context-path地址。

此处需要注意：
1、当受监控的actutor 如user-service 设置了context-path 时，需要重新设置监控指标数据的地址。而turbine 默认没有context-path地址。
2、受监控的服务需要集成了hystrix dashboard ；
3、受监控的服务需要走服务发现，写上 appConfig: user-service #, blog-service

3、启动turbine
在启动类上加上@EnableTurbine注解，开启turbine聚合监控的功能。

到此处，monitoring-service已经能聚合监控了，
打开user-service 的hystrix dashboard 监控地址：http://localhost:8080/user-service/hystrix
依次输入：http://locahost:8700/monitoring-service/turbine.stream ,监控时间隔1000ms, title 点击monitor进行监控展示。

4、集成hystrix dashboard，独立出监控界面。
然而这种方法有依赖于业务服务，需要user-service 的hystrix dashboard地址http://localhost:8080/user-service/hystrix
不利于监控服务独立。为了方便将监控服务独立出来，monitoring-service很有必要自己集成hystrix dashbord组件，能独立监控。
所以在monitoring-service服务中，又加上集成hystrix dashboard 。
集成后拥有自己的：http://localhost:8700/monitoring-service/hystrix,
依次输入：http://locahost:8700/monitoring-service/turbine.stream ,监控时间隔1000ms, title 点击monitor进行监控展示。

集成hystrix dashboard流程详见 readme-hystrix dashboard.txt文档，同user-serivce中的文档。





























