uaa-service 是一个集成了OAuth2 和JWT 的授权和认证中心。
关于OAuth2 详情资料请学习：
http://www.ruanyifeng.com/blog/2014/05/oauth_2_0.html
http://www.zhenchao.org/2017/03/04/oauth-v2-principle/
http://www.zhenchao.org/2017/03/11/oauth-v2-token/

接入流程：
1、起步依赖：
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-oauth2</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>

2、配置文件：
spring:
  #配置数据库
  datasource:
    driver-class-name: com.mysql.jdbc.Driver
    url:  jdbc:mysql://localhost:3306/spring_security?useUnicode=true&characterEncoding=utf8&characterSetResults=utf8
    username: root
    password: root
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

management:
  security:
    enabled: false

3、配置spring security
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserServiceDetail userServiceDetail;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().exceptionHandling()
                .authenticationEntryPoint((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/**").authenticated()
                .and()
                .httpBasic();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userServiceDetail).passwordEncoder(new BCryptPasswordEncoder());
    }
}

4、定义userserviceDetail 、userdao 、jpa 连接数据库，根据用户名查询用户

5、配置Authorization Server
@Configuration
@EnableAuthorizationServer
public class OAuth2Config extends AuthorizationServerConfigurerAdapter {
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient("uaa-service")
                .secret("123456")
                .scopes("service")
                .autoApprove(true)
                .authorizedGrantTypes( "authorization_code", "implicit","refresh_token", "password","client_credentials")
                //24小时过期
                .accessTokenValiditySeconds(24*3600);
    }
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer()).authenticationManager(authenticationManager);
    }
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtTokenEnhancer());
    }
    @Bean
    protected JwtAccessTokenConverter jwtTokenEnhancer() {
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("Kevin-jwt.jks"), "Kevin123".toCharArray());
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("Kevin-jwt"));
        return converter;
    }
}

6、生成jks
该流程详情查看readme-jwt-jks.txt说明文档。

7、
通过访问：http:localhost:9999/uaa/oauth/token 获取token

Base64=uaa-service:123456 通过base64加密得到
header: 'Authorization':'Basic Base64'
username: kk
password:123456

                .withClient("uaa-service")
                .secret("123456")
                .scopes("service")
                .autoApprove(true)
                .authorizedGrantTypes( "authorization_code", "implicit","refresh_token", "password","client_credentials")


8、jks 文件乱码
maven 编译项目时，可能会将jks文件编译，导致jks乱码而不可用。通过pom文件添加：
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-resources-plugin</artifactId>
                <configuration>
                    <nonFilteredFileExtensions>
                        <nonFilteredFileExtension>cert</nonFilteredFileExtension>
                        <nonFilteredFileExtension>jks</nonFilteredFileExtension>
                    </nonFilteredFileExtensions>
                </configuration>
            </plugin>




















