feign 是一个远程调度消费其他服务的声明式调用框架，相当于一上httpclient客户端，通过从eureka-server 服务注册中心发现服务，远程调用其他服务，所以需要项目提前注册到服务中心，并知道要调用用的服务serviceId 和接口。
feign 默认引入 了Ribbon 和Hystrix 依赖，可实现负载均衡和熔断器的功能。

下面简单讲解集成步骤：
1、起步依赖：
		<!--feign feign-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-feign</artifactId>
		</dependency>

2、配置文件，配置服务注册中心eureka-server 地址, 详情参考eureka-server 中的eureka-client说明步骤

3、开启feign功能
在启动类上加上@EnableFeignClients 注解，开启FeignClient功能。

通过以上步骤，程序已具备了feign声明式远程调用 的的功能。接下来介绍feign的配置类

4、配置feign client 远程调用失败策略
创建配置类，实现如下：
@Configuration
public class FeignConfig {
    @Bean
    public Retryer feignRetryer() {
        //重写feign client 请求失败重试策略，重试间隔100ms， 最大重试时间为1s, 重试次数为5
        return new Retryer.Default(100,SECONDS.toMillis(1),5);
    }
}

接下来将说明如何调用其他服务。
5、声明远程接口，并配置feignconfig失败策略，和hystrix熔断器（参考hystrix熔断器部分）
@FeignClient(value = "uaa-service", configuration = FeignConfig.class, fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {
    @PostMapping(value = "/oauth/token")
    JWTBean getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type, @RequestParam("username") String username, @RequestParam("password") String password);
}
注意：
a、需要提前获取服务名和接口名
b、feign 默认会使用post方式请求，可以通过@pathVariable 和@RequestParm方式传递参数

6、feign client调用 :
通过
@Autowired
FeignClient feignClient; 引入client Bean,然后调用远程方法。














