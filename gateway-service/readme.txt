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
  prefix: /v1  #可选
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

4、网关代理：
通过网关地址：http://localhost:5000/v1/userapi/** 访问具体的服务接口

备注：
1、Zuul 作为Netflix 组件，可以与ribbon, eureka, hystrix等组件结合，实现了负载均衡、熔断器功能。
在默认情况下，Zuul 与ribbon结合，默认实现了负载均衡的功能。

2、通过继承 ZuulFallbackProvider 实现 getRoute() 和 ClientHttpResponse fallbackResponse()
其中 getRoute()方法用于指定熔断功能应用于哪些路由服务， fallbackResponse用于熔断逻辑.
Dalston及更低版本通过实现 ZuulFallbackProvider 接口，从而实现回退；

注意：
Edgware及更高版本通过实现 FallbackProvider 接口，从而实现回退。
在Edgware中：
FallbackProvider是ZuulFallbackProvider的子接口。
ZuulFallbackProvider已经被标注 Deprecated，很可能在未来的版本中被删除。
FallbackProvider接口比ZuulFallbackProvider多了一个 ClientHttpResponse
fallbackResponse(Throwablecause);方法，使用该方法，可获得造成回退的原因。



3、通过继承ZuulFilter 重写方法filterType(),filterOrder(), shouldFilter(), run()
filterType()：指定过滤器类型;
     * 过滤器的类型
     * PRE 在请求路由转发之前执行
     * ROUTING 将请求路由到具体服务实例
     * POST 请求被路由到具体微服务实例之后 执行
     * ERROR 其他过滤器发生错误时执行

filterOrder(): 过滤器执行顺序;
shouldFilter(): 是否执行过滤器中的run方法
run()： 过滤器选择的逻辑







































