package com.example.mallcommon.json;


import org.apache.commons.lang.StringUtils;

//接口返回对象
public class JSONResult<T> extends Result {

    private static final long serialVersionUID = 7880907731807860636L;
    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JSONResult() {
        super.setCode("100");
        super.setStatus(1);
        super.setTip("执行成功！");
    }

    public JSONResult(String tip) {
        super.setCode("100");
        super.setStatus(1);
        super.setTip(tip);
    }



    public JSONResult(T data) {
        super.setCode("100");
        super.setTip("执行成功");
        super.setStatus(1);
        this.data = data;
    }



    public JSONResult(T data2, String tip) {
        super.setCode("100");
        if (StringUtils.isNotBlank(tip)) {
            super.setTip(tip);
        } else {
            super.setTip("执行成功");
        }

        super.setStatus(1);
        this.data = data2;
    }

    public JSONResult(String code, String msg, T data) {
        super.setCode(code);
        super.setStatus("100".equals(code) ? 1 : 0);
        super.setTip(msg == null ? "未知异常": msg);
        this.setData(data);
    }

//    public JSONResult(String code, String msg, T data) {
//        super.setCode(code);
//        super.setStatus("100".equals(code) ? 1 : 0);
//        this.setData(data);
//    }

    public static <T> JSONResult<T> newFailureResult(String code) {
        return new JSONResult(code, (String)null, (Object)null);
    }

    public static <T> JSONResult<T> newFailureResult(String code, String msg) {
        return new JSONResult(code, msg, (Object)null);
    }

    public static <T> JSONResult<T> newFailureResult(String code, T data) {
        return new JSONResult(code, (String)null, data);
    }

    public static <T> JSONResult<T> newSuccessResult() {
        return new JSONResult("100", (String)null, (Object)null);
    }

    public static <T> JSONResult<T> newSuccessResult(T result) {
        return new JSONResult("100", (String)null, result);
    }

    public static <T> JSONResult<T> newSuccessResult(T result, int totalCount) {
        return new JSONResult("100", (String)null, result);
    }
}
