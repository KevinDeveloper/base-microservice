log-service 是一个日志收集服务，目前收集的是用户操作日志，并对日志作持久化，保存到mysql数据库中。

在日志服务的架构中，user-service作为日志源，通过将日志消息发送到rabbitMQ， 而log-service 通过监听RabbitMQ
进行消息获取，然后通过jpa保存到日志消息到mysql。
通过rabbitMq将日志产生与日志收集两端服务进行解耦，一方面保障了日志收集服务的稳定性，另一方面也利于扩展增加日志收集。

该文档将包含两部分，log-service日志收集服务，和user-service资源服务采集日志。

一、log-service日志收集服务

1、起步依赖：
		<!--mq-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>

2、rabbitmq配置：
spring:
  application:
    name: log-service
  #配置 rabbitmq
  rabbitmq:
      host: 172.16.20.139
      port: 5672
      username: root
      password: admin
      publisher-confirms: true
      virtual-host: /

3、监听rabbitmq日志消息
写一个Receiver监听类，用于接收rabbitmq 服务器的消息，同时将消息进行数据库持久化
@Slf4j
@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);
    @Autowired
    SysLogService sysLogService;
    public void receiveMessage(String message) {
        log.info("Received <" + message + ">");
        SysLog sysLog=  JSON.parseObject(message,SysLog.class);
        sysLogService.saveLogger(sysLog);
        latch.countDown();
    }
}

4、注册监听
@Configuration
public class RabbitConfig {
    public   final static String queueName = "spring-boot";
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(listenerAdapter);
        return container;
    }
    @Bean
    MessageListenerAdapter listenerAdapter(Receiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

}

到此，log-service 日志收集服务器已经完成，接下来需要对日志产生的服务进行日志采集。

二、资源服务器日志采集（user-service）

1、集成mq依赖：
		<!-- mq -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>

2、配置mq:
spring:
  #配置 rabbitmq
  rabbitmq:
      host: 172.16.20.139
      port: 5672
      username: root
      password: admin
      publisher-confirms: true
      virtual-host: /

3、配置mq消息
@Configuration
public class RabbitConfig {
    public   final static String queueName = "spring-boot";
    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("spring-boot-exchange");
    }
    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(queueName);
    }
}


4、添加方法级别的注解
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLogger {
    String value() default "";
}


5、设置aop 进行日志注解扫描并发送到mq
@Aspect
@Component
public class SysLoggerAspect {
    @Autowired
    private LogService loggerService;

    @Pointcut("@annotation(com.kevin.microservicebase.commonsupport.annotation.SysLogger)")
    public void loggerPointCut() {

    }

    @Before("loggerPointCut()")
    public void saveSysLog(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        SysLog sysLog = new SysLog();
        SysLogger sysLogger = method.getAnnotation(SysLogger.class);
        if(sysLogger != null){
            //注解上的描述
            sysLog.setOperation(sysLogger.value());
        }
        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLog.setMethod(className + "." + methodName + "()");
        //请求的参数
        Object[] args = joinPoint.getArgs();
        String params="";
        for(Object o:args){
            params+= JSON.toJSONString(o);
        }
        if(!StringUtils.isEmpty(params)) {
            sysLog.setParams(params);
        }
        //设置IP地址
        sysLog.setIp(HttpUtils.getIpAddress());
        //用户名
        String username = UserUtils.getCurrentPrinciple();
        if(!StringUtils.isEmpty(username)) {
            sysLog.setUsername(username);
        }
        sysLog.setCreateDate(new Date());
        //保存系统日志
        loggerService.log(sysLog);
    }

}

6、在接口方法上添加注解：@SysLogger("registry") 开启该接口的日志收集功能。

至此，日志采集点已经配置完成，先后启动日志收集服务与user-service服务，
成功启动后，通过swagger调用接口，可以从数据库中看到sys_log表中已经有操作日志记录存在 。







