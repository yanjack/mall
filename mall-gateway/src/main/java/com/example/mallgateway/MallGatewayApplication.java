package com.example.mallgateway;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan(value = "com.example.mallgateway.database.dao")
@ComponentScan(value = "com.example.*")
public class MallGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MallGatewayApplication.class, args);
	}

}
