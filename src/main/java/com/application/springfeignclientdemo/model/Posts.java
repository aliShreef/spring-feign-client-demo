package com.application.springfeignclientdemo.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Posts {

    private Long id;

    @JsonProperty("userId")
    private Long userId;

    private String title;
    private String body;

}
