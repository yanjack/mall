package com.example.orderservice.feign;

import com.example.user.api.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "user-service")
public interface UserFeign extends UserApi {

}
