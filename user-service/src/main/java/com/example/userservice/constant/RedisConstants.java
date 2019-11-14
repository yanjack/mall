package com.example.userservice.constant;

public class RedisConstants {

    public static final String ROOT = "mall:";

    public static final String TOKEN =ROOT +"user:token";

    public static final String TOKEN_LINK =ROOT +"user:token:link";
    //秒 一周时间
    public static final long TOKEN_EXPIRE = 60*60*24*7;

}
