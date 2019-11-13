package com.example.mallgateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.mallgateway.config.DebugConfig;
import com.example.mallgateway.config.JSONResult;
import com.example.mallgateway.config.URLWhritList;
import com.example.mallgateway.database.GatewayInterfaceBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;


/**
* @Description:白名单 、token校验
* @Author: yl
* @Date: 2019/11/13
*/
@Component
public class RequestWhritAndTokenFilter implements GlobalFilter, Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestWhritAndTokenFilter.class);

    @Resource
    DebugConfig debugConfig;

    @Resource
    URLWhritList urlWhritList;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        HttpHeaders httpRequestHeaders = serverHttpRequest.getHeaders();

        ServerHttpResponse serverHttpResponse = exchange.getResponse();
        HttpHeaders httpResponseHeaders = serverHttpResponse.getHeaders();

        String path = serverHttpRequest.getPath().toString();
        String key = path+ serverHttpRequest.getMethodValue().toUpperCase();
        GatewayInterfaceBean interfaceBean = URLWhritList.getInterface(key);

        serverHttpResponse.setStatusCode(HttpStatus.OK);
        //取访问IP, 必须在nginx中做IP转发
        String ip = httpRequestHeaders.get("X-Real-IP")==null?"127.0.0.1":httpRequestHeaders.get("X-Real-IP").get(0);
        //不在白名单内
        if(null == interfaceBean){
            //不合法(响应未登录的异常)
            //设置headers
            httpResponseHeaders.add("Content-Type", "application/json; charset=UTF-8");
            httpResponseHeaders.add("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
            httpResponseHeaders.add("X-Real-IP",ip);
            //设置body
            JSONResult result = new JSONResult();
            result.setData("URL地址不存在或不允许访问:" + path);
            result.setTip("访问失败");
            result.setCode("403");
            result.setStatus(0);
            String warningStr = JSONObject.toJSONString(result);
            LOGGER.error(warningStr);

            //如果开启DEBUG模式,把不存在的URL写入白名单,请求正常访问
            if(debugConfig.isDebugging()) {
                urlWhritList.insertInterface("", path, serverHttpRequest.getMethodValue());
                urlWhritList.reload();
                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    if(serverHttpResponse.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        serverHttpResponse.setStatusCode(HttpStatus.OK);
                    }
                }));

            }
            //如果URL在白名单中不存在,则返回403
            DataBuffer bodyDataBuffer = serverHttpResponse.bufferFactory().wrap(warningStr.getBytes());
            serverHttpResponse.setStatusCode(HttpStatus.OK);
            return serverHttpResponse.writeWith(Mono.just(bodyDataBuffer));
        }
        //在白名单中校验token

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
