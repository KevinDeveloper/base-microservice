package com.kevin.microservicebase.gatewayservice.fallback;


import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.http.client.ClientHttpResponse;

/**
 * @ClassName: NewFallbackProvider
 * @Description: 新版本的熔断器
 * @Author: Kevin
 * @Date: 2018/6/21 17:41
 */
public class NewFallbackProvider implements FallbackProvider {


    @Override
    public String getRoute() {
        //将所有路由服务都加熔断
        return "*";
        //return "user-service"; //针对某个具体的服务的熔断
    }
    @Override
    public ClientHttpResponse fallbackResponse(Throwable cause) {
        return null;
    }
    @Override
    public ClientHttpResponse fallbackResponse() {
        return null;
    }
}
