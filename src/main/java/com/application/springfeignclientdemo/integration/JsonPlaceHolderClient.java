package com.application.springfeignclientdemo.integration;

import com.application.springfeignclientdemo.config.ClientDecoderInterceptor;
import com.application.springfeignclientdemo.config.ClientErrorDecoder;
import com.application.springfeignclientdemo.model.Posts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "jsonPlaceHolderClient",
        url = "https://jsonplaceholder.typicode.com",
        configuration = {ClientDecoderInterceptor.class, ClientErrorDecoder.class},
        fallbackFactory = JsonPlaceHolderClientFallback.class

)
public interface JsonPlaceHolderClient {


    @GetMapping("/posts")
    List<Posts> getPosts();


    @GetMapping("/postssss/{id}")
    Posts getPostById(@PathVariable("id") Long id);
}

@Component
@Slf4j
class JsonPlaceHolderClientFallback implements FallbackFactory<JsonPlaceHolderClient> {

    @Override
    public JsonPlaceHolderClient create(Throwable cause) {
        return new JsonPlaceHolderClient() {
            @Override
            public List<Posts> getPosts() {
                log.error("Exception while calling getPosts");
                throw new RuntimeException(cause);
            }

            @Override
            public Posts getPostById(Long id) {
                log.error("Exception while calling getPostById");
                throw new RuntimeException(cause);
            }
        };
    }
}

