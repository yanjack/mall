package com.example.userservice.aop;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
* @Description:使用AOP实现controller日志记录及请求时间记录
* @Author: yl
* @Date: 2019/11/13
*/
@Component
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    //切点
    @Pointcut("execution(public * com.example.userservice.controller..*(..)) ")
    public void log(){
    }

    @Around("log()")
    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
        //开始时间
        long start = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer(1000);
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sb.append("\r\n-----------------------").append(sdf.format(new Date()))
                .append("-------------------------------------\r\n");
        sb.append("RemoteAddr: ").append(request.getRemoteAddr()).append("\r\n");
        sb.append("Controller: ").append(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName()).append("\r\n");
        sb.append("Method    : ").append(request.getMethod()).append("\r\n");
        sb.append("URI       : ").append(request.getRequestURI()).append("\r\n");
        String contentType = request.getContentType();
        String params = getParamString(request.getParameterMap());
        if(StringUtils.isNotBlank(params)){
            logger.info("params:"+params);

        }
        sb.append("Params    : ").append(params).append("\r\n");
        String body = "";
        //添加body，当传入body为，application/json或text/plain时追加，其他Body忽略
        if (contentType != null && ("application/json;charset=UTF-8".equalsIgnoreCase(contentType.replaceAll(" ", ""))
                || "application/json".equalsIgnoreCase(contentType)
                || "text/plain;charset=UTF-8".equalsIgnoreCase(contentType.replaceAll(" ", ""))
                || "text/plain".equalsIgnoreCase(contentType))) {
            String queryString = request.getQueryString();
            Object[] args = joinPoint.getArgs();
            Object object = args[0];
            Map map = getKeyAndValue(object);
            body = JSON.toJSONString(map);
            try {
                sb.append("body      : ").append(JSON.parse(body)).append("\r\n");
            } catch (Exception e) {
                logger.error("body:"+body);
                logger.error("",e);
            }
            if(StringUtils.isNotBlank(body)){
                logger.info("body:"+body);
            }
        }
        //没办法记录body，请求内容被读取一次后就没有了。影响后续逻辑取参数
        logger.info(sb.toString());
        Object res=joinPoint.proceed();
        //结束时间
        long end = System.currentTimeMillis();
        long times = end - start;
        logger.info(request.getRequestURI()+"请求时间为："+times+"ms");

        return res;
    }
    //获取url后的参数
    private String getParamString(Map<String, String[]> map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,String[]> e:map.entrySet()){
            sb.append(e.getKey()).append("=");
            String[] value = e.getValue();
            if(value != null && value.length == 1){
                sb.append(value[0]).append("\t");
            }else{
                sb.append(Arrays.toString(value)).append("\t");
            }
        }
        return sb.toString();
    }
    //获取body参数
    public static Map<String, Object> getKeyAndValue(Object obj) {
        Map<String, Object> map = new HashMap<>();
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            Object val = new Object();
            try {
                val = f.get(obj);
                // 得到此属性的值
                map.put(f.getName(), val);// 设置键值
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        return map;
    }

}
