package com.example.mallcommon.json;


import java.io.Serializable;

public class Result implements Serializable {
    private static final long serialVersionUID = 6288374846131788743L;
    private String tip;
    private int status;
    private String code;

    public String getTip() {
        return this.tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Result() {
    }
}
