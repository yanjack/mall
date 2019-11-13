package com.example.mallgateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
/**
* @Description:请求时长
* @Author: yl
* @Date: 2019/11/13
*/
@Component
public class RequestLogFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLogFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getAttributes().put("start",System.currentTimeMillis());
//        return chain.filter(exchange).then(Mono.fromRunnable(new Runnable() {
//            @Override
//            public void run() {
//                Long start = exchange.getAttribute("start");
////                if (start != null) {
////                    Long executeTime = (System.currentTimeMillis() - start);
////                    LOGGER.info(exchange.getRequest().getURI().getRawPath() + " : " + executeTime + "ms");
////                }
//            }
//        }));
        return chain.filter(exchange).then(Mono.fromRunnable(()-> {
            Long start = exchange.getAttribute("start");
            if (start != null) {
                Long executeTime = (System.currentTimeMillis() - start);
                LOGGER.info(exchange.getRequest().getURI().getRawPath() + " : " + executeTime + "ms");
            }
        }));
    }
    @Override
    public int getOrder() {
        return -1;
    }
}
