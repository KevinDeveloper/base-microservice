package com.kevin.microservicebase.gatewayservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName: CustomRouteFilter
 * @Description: 自定义过滤器,在请求路由转发之前对请求进行过滤处理，可以进行安全验证
 * @Author: Kevin
 * @Date: 2018/6/21 17:19
 */
@Component
public class CustomRouteFilter extends ZuulFilter {
    /**
     * 过滤器的类型
     * PRE 在请求路由转发之前执行
     * ROUTING 将请求路由到具体服务实例
     * POST 请求被路由到具体微服务实例之后 执行
     * ERROR 其他过滤器发生错误时执行
    @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 用于指定过滤器执行的顺序，值越大，越往后执行
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否执行run方法
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();

        return null;
    }
}
