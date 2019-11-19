package com.example.mallcommon.exception;

import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

@Configuration
public class FeginErrorDecoder implements ErrorDecoder {

    private static final Logger logger = LoggerFactory.getLogger(FeginErrorDecoder.class);
    @Override
    public Exception decode(String s, Response response) {
        try {
            this.logger.error(s);
            String json = Util.toString(response.body().asReader());
            this.logger.error(json);
            Map<String, String> map = JSONObject.parseObject(json,Map.class);
            return new BaseException((String)map.get("code"), (String)map.get("tip"));
        } catch (IOException var5) {
            return new BaseException("104", "系统异常");
        }
    }


}
