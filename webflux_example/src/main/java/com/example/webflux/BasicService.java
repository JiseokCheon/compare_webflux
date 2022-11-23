package com.example.webflux;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BasicService {

    private final ReactiveRedisConnectionFactory factory;

    private final ReactiveRedisOperations<String, Object> reactiveRedisOperations;

    private static final AtomicInteger count = new AtomicInteger(0);

    private ObjectMapper objectMapper;

    void loadData() {
        List<Ad> data = new ArrayList<>();
        IntStream.range(0, 10000).forEach(i -> data.add(Ad.builder()
                .id(String.valueOf(i))
                .weight(new Random().nextLong(0, 10000))
                .build()));

        Flux<Ad> stringFlux = Flux.fromIterable(data);

        factory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(stringFlux.flatMap(ad -> reactiveRedisOperations.opsForValue().set(String.valueOf(count.getAndAdd(1)), ad)))
                .subscribe();
    }

    void loadTargetKey(AdTarget adTarget) {
        reactiveRedisOperations.opsForSet().add(adTarget.getKey(), adTarget)
                .subscribe();
    }

    Mono<Object> findReactorList(String targetKey) {
        return reactiveRedisOperations.opsForValue().get(targetKey)
                .flatMap(obj -> reactiveRedisOperations.opsForValue().multiGet(objectMapper.convertValue(obj, AdTarget.class).getAdsNoList()));

//                .flatMap(obj -> reactiveRedisOperations.opsForValue().multiGet(objectMapper.convertValue(obj, AdTarget.class).getAdsNoList()));
    }

}

