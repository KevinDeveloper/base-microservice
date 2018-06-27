user-service 需要接入uaa-service 授权认证中心，对所有接口进行授权认证功能。
接入流程：

1、起步依赖：
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-oauth2</artifactId>
		</dependency>

2、配置文件：
server:
  port: 8080
  context-path: /user-service
spring:
  application:
    name: user-service
  cloud:
    #设置配置中心地址
    config:
      #uri: http://localhost:8900 #单个地址配置
      fail-fast: true
      #从服务注册中心获取配置中心服务
      discovery:
        enabled: true
        service-id: config-server
  #设置加载配置文件类型
  profiles:
    active: pro
  #配置Zipkin 服务调用链
  zipkin:
    base-url: http://zipkin-service/ #http://localhost:9400
    enabled: true
    locator:
      discovery:
        enabled: true
#配置服务注册中心
eureka:
  client:
    service-url:
      defaultZone: http://172.16.20.138:8760/eureka/

#feign服务调用时开启hystrix熔断器
feign:
  hystrix:
    enabled: true


3、配置Resource Server
首先配置一个jwtConfig 用于jwt转换器，设置公钥。
@Configuration
public class JwtConfig {
    @Autowired
    JwtAccessTokenConverter jwtAccessTokenConverter ;
    @Bean
    @Qualifier("tokenStore")
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        ClassPathResource resource = new ClassPathResource("public.cert");
        String publicKey;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }
}

说明：
1、public.cert为公钥，存放在resource目录下，生成见uaa-service

4、配置Resource Server
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().regexMatchers(".*swagger.*",".*v2.*",".*webjars.*","/user/login.*","/user/registry.*","/user/test.*").permitAll()
                .antMatchers("/**").authenticated();
    }
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenStore(tokenStore);
    }
}

5、配置spring security
开启方法级别的安全验证，在方法执行前
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class GlobalMethodSecurityConfig {
}


6、编写用户接口，说明请看user-controller


至此，user-service接入授权中心uaa-servic已经完成。
开启服务，访问swagger
通过注册、登录接口，查看是否获取到token














