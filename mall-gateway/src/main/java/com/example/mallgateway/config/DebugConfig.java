package com.example.mallgateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/***
 * Debug模式配置类
 */
@Configuration
public class DebugConfig {

    @Value("${spring.debugging}")
    private boolean debugging = false;

    public boolean isDebugging() {
        return debugging;
    }

    public void setDebugging(boolean debugging) {
        this.debugging = debugging;
    }


}
