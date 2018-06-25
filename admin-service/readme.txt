spring boot admin 用于管理和监控一个或多个Spring boot 程序。spring boot admin 分为Server和Client。
server 端通过服务发现对client端进行监控和管理。
本节分为以下部分，
一、admin-Server 端接入流程
二、admin-Client 端接入流程
三、admin-server 集成turbine
四、admin-server 添加安全登录界面

一、admin-Server 端接入流程
1、起步依赖：
		<!-- admin-server -->
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server</artifactId>
			<version>1.5.7</version>
		</dependency>
		<!-- admin-server-ui -->
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server-ui</artifactId>
			<version>1.5.7</version>
		</dependency>
		<!--增加actuator控制器-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
        <dependency>
            <groupId>org.jolokia</groupId>
            <artifactId>jolokia-core</artifactId>
        </dependency>
2、配置文件配置：
spring:
  application:
    name: admin-service
  #配置admin server
  boot:
    admin:
      routes:
        endpoints: env,metrics,dump,jolokia,info,configprops,trace,logfile,refresh,flyway,liquibase,heapdump,loggers,auditevents,hystrix.stream,activiti

logging:
  file: "logs/admin-service.log"

3、spring boot admin 支持对日志的管理，在Resources目录下建一个logback-spring.xml文件，文件内容如下：
<?xml version="1.0" encoding="UTF-8" ?>
<configuration>
    <include resource="org/springframework/boot/logging/logback/base.xml"/>
    <jmxConfigurator/>
</configuration>

4、启动adminServer
在启动类上添加 @EnableAdminServer 开启AdminServer的功能。

至此,Sping Boot Admin Server 创建完成。

二、admin-Client 端接入流程
由于admin-server 需要监控admin-client ，主要通过actuator 进行监控数据采集。本示例admin-client走服务发现，注册到服务中心，详情步骤参考eureka-server 说明。
admin-client 示例为 monitoring-service 服务

1、起步依赖：
		<!--增加actuator控制器-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
        <dependency>
            <groupId>org.jolokia</groupId>
            <artifactId>jolokia-core</artifactId>
        </dependency>

2、配置文件：
management:
  security:
    enabled: false
logging:
  file: "logs/monitoring-service.log"

至此：admin-server 和admin-client 都已经配置完毕，依次启动服务，
可访问admin-server 的主页： http://localhost:9998//admin-service ,查看admin-server 管理界面

三、admin-server 集成turbine
admin-server 中集成turbine 需要对admin-server 和admin-client 进行改造，同时需要一个turbine工程
三（1）、admin-server改造步骤
三（2）、turbine工程（monitoring-service）改造步骤
三（3）、admin-client工程（user-service）改造步骤

三（1）、admin-server改造步骤
1、起步依赖
		<!--增加hystrix熔断器-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-turbine</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
		<!--增加hystrix dashboard 监控熔断器状态-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
		</dependency>
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server-ui-turbine</artifactId>
			<version>1.5.7</version>
		</dependency>
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server-ui-hystrix</artifactId>
			<version>1.5.7</version>
		</dependency>

2、配置文件：
spring:
  application:
    name: admin-service
  #配置admin server
  boot:
    admin:
      routes:
        endpoints: env,metrics,dump,jolokia,info,configprops,trace,logfile,refresh,flyway,liquibase,heapdump,loggers,auditevents,hystrix.stream,activiti
      #admin server 集成turbine
      trubine:
        clusters: default
        location: monitoring-service

3、启动功能
在启动类上添加@EnableHystrixDashboard @EnableAdminServer 注解，开启HystrixDashBoard功能

三（2）、turbine工程（monitoring-service）改造步骤

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
		<!--增加hystrix熔断器-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>
		<!--增加hystrix dashboard 监控熔断器状态-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix-dashboard</artifactId>
		</dependency>

2、配置文件：
#配置turbine 统一监控服务的hystrix dashboard , 采用默认监控集群配置
turbine:
  aggregator:
    clusterConfig: default
  appConfig: user-service #, blog-service
  clusterNameExpression: new String("default")
  instanceUrlSuffix: /user-service/hystrix.stream # 当受监控的actutor 如user-service 设置了context-path 时，需要重新设置监控指标数据的地址

3、启动Turbine功能
在启动类上添加：@EnableTurbine注解，开启turbine功能

三（3）、admin-client工程（user-service）改造步骤
admin-client 参考user-service 中的hystrix 与hystrix dashboard 说明文档


四、admin-server 添加安全登录界面
由于admin-server 提供了服务监控和管理的功能，信息包含太多，因此很有必要给界面添加一个安全登录界面

1、起步依赖：
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server-ui-activiti</artifactId>
			<version>1.5.7</version>
		</dependency>
		<dependency>
			<groupId>de.codecentric</groupId>
			<artifactId>spring-boot-admin-server-ui-login</artifactId>
			<version>1.5.7</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>

2、配置文件：
#配置服务注册中心
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8760/eureka/
  instance:
    metadata-map:
      user.name: admin
      user.password: 123456
management:
  security:
    enabled: false #关闭actuator 模块的安全验证
  user:
    name: admin
    password: 123456

3、在程序中配置spring boot security ,增加securityconfig配置类：

1、配置登录界面为“login.html”
2、开启http基本认证，httpBasic()方法。
	/**
	 * 以下代码来自于官方文档
	 */
	// tag::configuration-spring-security[]
	@Configuration
	public static class SecurityConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// Page with login form is served as /login.html and does a POST on /login
			http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
			// The UI does a POST on /logout on logout
			http.logout().logoutUrl("/logout");
			// The ui currently doesn't support csrf
			http.csrf().disable();

			// Requests for the login page and the static assets are allowed
			http.authorizeRequests()
					.antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**")
					.permitAll();
			// ... and any other request needs to be authorized
			http.authorizeRequests().antMatchers("/**").authenticated();

			// Enable so that the clients can authenticate via HTTP basic for registering
			http.httpBasic();
		}
	}
	// end::configuration-spring-security[]
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth
				.inMemoryAuthentication()
				.withUser("admin").password("123456").roles("USER");
	}




















