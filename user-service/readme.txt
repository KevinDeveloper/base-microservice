user-service 作为业务资源服务，对外提供api接口，需要实现以下功能。
1、作为Eureka Client, 向eureka-server 服务注册中心注册, 具体实现参考eureka-server readme.txt;
2、作为Config Client, 向config-server读取配置文件，具体实现参考config-server readme.txt;
3、作为Zipkin Client, 上传链路追踪数据到Zipkin-server , 具体实现参考zipkin-server readme.txt;
