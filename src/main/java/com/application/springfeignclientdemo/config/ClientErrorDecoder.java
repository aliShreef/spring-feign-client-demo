package com.application.springfeignclientdemo.config;

import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ClientErrorDecoder implements ErrorDecoder {


    @Override
    public Exception decode(String methodKey, Response response) {
        log.info("-----> Request");
        log.info("URL:{}",response.request().url());
        log.info("Header:{}",response.request().headers().toString());
        log.info("Body:{}",response.request().body() != null ? new String(response.request().body(), StandardCharsets.UTF_8): null);
        log.info("Time:{}", LocalDateTime.now());
        log.info("----->");

        log.info("<------ Response");
        log.info("Header:{}",response.headers().toString());
        log.info("Status:{}",response.status());
        log.info("Time:{}", LocalDateTime.now());
        String responseAsString = null;
        try {
            responseAsString = convertStreamToString(response.body().asInputStream());
        } catch (IOException e) {
            log.error("Error While reading error response:{}",response.body());
        }
        if(response.body() != null){
            log.info("Body:{}", responseAsString);
        }
        log.info("<------------");

        return FeignException.errorStatus(methodKey,response);
    }

    private String convertStreamToString(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Error reading response body";
        }
    }
}
