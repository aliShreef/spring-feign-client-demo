package com.application.springfeignclientdemo.config;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.support.SpringDecoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
public class ClientDecoderInterceptor implements Decoder {

    private final SpringDecoder defaultDecoder;

    public ClientDecoderInterceptor(SpringDecoder  defaultDecoder) {
        this.defaultDecoder = defaultDecoder;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
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
        String responseAsString = convertStreamToString(response.body().asInputStream());
        if(response.body() != null){
            log.info("Body:{}", responseAsString);
        }
        log.info("<------------");
        Response updatedResponse = response.toBuilder()
                .body(responseAsString, StandardCharsets.UTF_8)
                .build();
        return defaultDecoder.decode(updatedResponse,type);
    }

    private String convertStreamToString(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            return "Error reading response body";
        }
    }
}
