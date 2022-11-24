package com.example.webflux;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@AllArgsConstructor
public class BasicRouter {

    private final BasicService basicService;

    @Bean
    RouterFunction<ServerResponse> routerList() {
        return route()
                .GET("/reactive-list", serverRequest ->
                        ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(basicService.findReactorList(serverRequest.queryParam("key").get()), List.class)
                )
                .GET("/data", serverRequest -> {
                    basicService.loadData();
                    return ServerResponse.ok()
                            .body(BodyInserters.fromValue("Load Data Completed"));
                })
                .POST("/data-target", serverRequest ->
                        serverRequest.bodyToMono(AdTarget.class)
                                .doOnNext(basicService::loadTargetKey)
                                .log()
                                .then(ServerResponse.ok()
                                        .body(BodyInserters.fromValue("Load Targeting Data Completed")))
                )
                .build();
    }

}
