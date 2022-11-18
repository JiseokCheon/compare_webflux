package com.example.webflux;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.List;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class BasicRouter {

    private final BasicService basicService;

    @Bean
    RouterFunction<ServerResponse> empRouterList() {
        return route()
                .GET("/reactive-list", serverRequest ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(basicService.findReactorList(), List.class))

//                .GET("/normal-list", serverRequest ->
//                        ServerResponse.ok()
//                                .contentType(MediaType.TEXT_EVENT_STREAM)
//                                .body(basicService.findNormalList(), String.class))

                .GET("/data", serverRequest -> {
                    basicService.loadData();
                    return ServerResponse.ok()
                            .body(BodyInserters.fromValue("Load Data Completed"));
                })
                .build();
    }

}
