package com.application.springfeignclientdemo;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class SpringFeignClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFeignClientDemoApplication.class, args);
    }


//    @Bean
//    public Logger.Level feignLoggerLevel() {
//        return Logger.Level.FULL; // Logs headers, body, and metadata
//    }

}
