Hystrix 是什么？
Hystrix 作为熔断器，在使用RestTemplate 和Feign 消费服务，当服务提供者出现故障时，将导出依赖于它们的其他服务出现远程调试的线程阻塞，Htstrix 提供熔断器功能，能够阻止分布式系统中的出现的联动故意。
Hystrix 通过隔离服务的访问点阻止联动故障，并提供故障解决方案，从而提高整个分布式系统的可靠性。

Hystrix 为了解决什么问题？
某个服务的单个点的请坟故障会导致用户的请求处于阻塞状态，最终导至整个服务的线程资源消耗殆尽。
由于服务的依赖性，会导致依赖于该故障服务的其他服务也处于线程阻塞状态，最终导致这些服务的线程资源也消耗殆尽，直到不可用，从而导致了整体服务系统都不可用，即 雪崩效应。为了解决分布式系统的雪崩效应，分布式系统引进入了熔断器机制。
Hystrix就是为了防止雪崩效应而出现的熔断器模型的开源组件。
同时，利用Hystrix 可以实现服务降级的功能 ，也可以实现当因某个微小的故障如网络服务商问题，导致服务短时间内不可用的问题，而开启熔断器将具有自我修复能力。

Hystrix 的工作机制
1、当服务的某个API接口失败的次数在一定时间内小于设定的阈值时，熔断器处于关闭状态，服务正常；
2、当接口失败次数大于设定的阈值时，说明服务出现故障，打开熔断器，此时该接口的所有请求会执行快速失败，不执行业务逻辑， 执行熔断器逻辑，
3、当熔断器处于打开状态，一段时间后，会处于半打开状态，并执行一定数量的请求，剩余请求依旧执行快速失败。若执行的请求失败了，则继续打开熔断器，若成功了，则将熔断器关闭。

Hystrix 引入流程

一、在Feign h 上使用熔断器
1、由于Feign 的起步依赖已经引入了Hystrix的依赖，所以不需要重新引入Hysrtrix依赖，但若是使用RestTemplate 和Ribbon ，则需要引入Hystrix依赖
		<!--增加hystrix熔断器-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-hystrix</artifactId>
		</dependency>

2、配置Hystrix功能
#feign服务调用时开启hystrix熔断器
feign:
  hystrix:
    enabled: true

3、在FeignClient 上@FeignClient注解中加上fallback快速失败的处理类，该处理类作为Feign熔断器的逻辑处理类，必须实现@FeignClient修饰的接口。
1》、实现@FeignClient修饰的接口：
@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWTBean getToken(String authorization, String type, String username, String password) {
        System.out.println("--------opps getToken hystrix---------");
        return null;
    }
}
注意加上@Component注解，注入ioc容器。

2》、@FeignClient注解中加上fallback快速失败的处理类：
@FeignClient(value = "uaa-service", configuration = FeignConfig.class, fallback =AuthServiceHystrix.class )
public interface AuthServiceClient {
    @PostMapping(value = "/oauth/token")
    JWTBean getToken(@RequestHeader(value = "Authorization") String authorization, @RequestParam("grant_type") String type, @RequestParam("username") String username, @RequestParam("password") String password);
}

4、启动Hystrix 功能
在启动类上添加 @EnableHystrix 注解，开启Hystrix功能

6、此时已经Hystrix熔断器已经配置完毕，启动项目，当api接口不可用时，则会执行熔断器的处理类中的逻辑。






































