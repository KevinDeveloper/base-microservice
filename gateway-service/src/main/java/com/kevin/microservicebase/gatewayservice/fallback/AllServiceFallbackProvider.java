package com.kevin.microservicebase.gatewayservice.fallback;

import org.springframework.cloud.netflix.zuul.filters.route.ZuulFallbackProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @ClassName: AllServiceFallbackProvider
 * @Description: 所有的服务熔断功能
 * @Author: Kevin
 * @Date: 2018/6/21 17:19
 */
public class AllServiceFallbackProvider implements ZuulFallbackProvider {
    /**
     * 用于指定熔断功能应用于哪些路由服务
     * @return
     */
    @Override
    public String getRoute() {
        //将所有路由服务都加熔断
        return "*";
        //return "user-service"; //针对某个具体的服务的熔断
    }

    /**
     * 进入熔断时执行的逻辑
     * @return
     */
    @Override
    public ClientHttpResponse fallbackResponse() {
        return new ClientHttpResponse() {
            @Override
            public HttpStatus getStatusCode() throws IOException {
                return HttpStatus.OK;
            }

            @Override
            public int getRawStatusCode() throws IOException {
                return 200;
            }

            @Override
            public String getStatusText() throws IOException {
                return "ok";
            }

            @Override
            public void close() {

            }

            @Override
            public InputStream getBody() throws IOException {
                return new ByteArrayInputStream("hopen error, i am the fallback.".getBytes());
            }

            @Override
            public HttpHeaders getHeaders() {
                HttpHeaders httpHeaders =  new HttpHeaders();
                httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                return httpHeaders;
            }
        };
    }
}
