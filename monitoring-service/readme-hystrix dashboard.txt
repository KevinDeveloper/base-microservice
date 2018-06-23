在分布式微服务架构中，防止雪崩效应、服务降级、自我修复而引入了熔断器模型。熔断器提高了系统的可用性的健壮性。
而Hystrix Dashboard 是监控Hystrix熔断器状况的一个组件。
将在项目引入Hystrix的基础上，引入Hsytrix Dashboard监控熔断器的状态。

Hystrix Dashboard引入流程

1、起步依赖：
		<!--增加hystrix熔断器-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
		<!--增加actuator控制器-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<!--增加hystrix dashboard 监控熔断器状态-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
		</dependency>

2、启动配置
在启动类上添加@EnableHystrixDashboard注解，开启Hystrix Dashboard的功能。

3、查看监控状况
启动服务，访问
http://localhost:8080/user-service/hystrix.stream 数据指标
http://localhost:8080/user-service/hystrix 监控界面，
依次输入 http://localhost:8080/user-service/hystrix.stream， 2000， * , 点击monitor 进入数据指标页面

4、备注：
当没有熔断发生时，界面会显示一个loading....,此时如果调用远程api接口，发生熔断了，则会显示出数据。







