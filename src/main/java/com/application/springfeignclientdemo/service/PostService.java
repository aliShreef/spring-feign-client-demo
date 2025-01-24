package com.application.springfeignclientdemo.service;

import com.application.springfeignclientdemo.integration.JsonPlaceHolderClient;
import com.application.springfeignclientdemo.model.Posts;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PostService {

    private final JsonPlaceHolderClient jsonPlaceholderClient;


    public PostService(JsonPlaceHolderClient jsonPlaceholderClient) {
        this.jsonPlaceholderClient = jsonPlaceholderClient;
    }

    public ResponseEntity<List<Posts>> getAllPosts() {
        return ResponseEntity.ok(jsonPlaceholderClient.getPosts());
    }

    @CircuitBreaker(name = "jsonPlaceHolderService")
    public ResponseEntity<Posts> getPostById(Long id) {
        return ResponseEntity.ok( jsonPlaceholderClient.getPostById(id));
    }
}
