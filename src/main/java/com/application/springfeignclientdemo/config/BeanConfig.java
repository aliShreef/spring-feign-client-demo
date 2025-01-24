package com.application.springfeignclientdemo.config;

import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BeanConfig {

    @Bean
    public SpringDecoder defaultDecoder() {
        return new SpringDecoder(HttpMessageConverters::new);
    }

    @Bean
    public ClientErrorDecoder clientErrorDecoder(){
        return new ClientErrorDecoder();
    }

}
