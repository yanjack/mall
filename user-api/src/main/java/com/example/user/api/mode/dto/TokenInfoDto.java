package com.example.user.api.mode.dto;

import com.example.user.api.mode.UserBean;

public class TokenInfoDto extends UserBean {
    //令牌
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
