package com.example.mallcommon.exception;

import com.example.mallcommon.json.JSONResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletResponse;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = BaseException.class)
    public JSONResult<Object> errorHandler (BaseException exception, HttpServletResponse response) {
        exception.printStackTrace();
//        response.addHeader("Content-Type", "application/json; charset=UTF-8");
//        response.addHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        JSONResult result = new JSONResult();
        result.setTip(exception.getDesc());
        result.setStatus(0);
        result.setCode(exception.getCode());
        result.setData(exception.getStackMessage());
        response.setStatus(500);

        return result;
    }
}
