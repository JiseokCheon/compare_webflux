package com.example.webflux;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
public class WebfluxController {

//    @Autowired
    private ReactiveRedisOperations<String, Object> reactiveRedisOperations;

    @PostMapping("/data-target2")
    public Mono<Boolean> dataTarget(@RequestBody AdTarget adTarget) {
        return reactiveRedisOperations.opsForValue().set("target:1:list", List.of(adTarget));
    }

//    @GetMapping(value = "/redis")
//    public Mono<List<Person>> findPersonListByRedis(){
//
//        return personRedisTemplate.opsForValue().get("friend:1")
//                .switchIfEmpty(Mono.just(Collections.emptyList()));
//    }

}
