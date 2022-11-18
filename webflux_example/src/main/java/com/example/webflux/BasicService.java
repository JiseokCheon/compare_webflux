package com.example.webflux;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class BasicService {

    private final ReactiveRedisConnectionFactory factory;

    private final ReactiveRedisOperations<String, Ad> reactiveRedisOperations;

    private static final AtomicInteger count = new AtomicInteger(0);

    void loadData() {
        List<Ad> data = new ArrayList<>();
        IntStream.range(0, 100000).forEach(i -> data.add(Ad.builder()
                .id(UUID.randomUUID().toString())
                .name("광고-" + i)
                .weight(new Random().nextLong(0, 100000))
                .build()));

        Flux<Ad> stringFlux = Flux.fromIterable(data);

        factory.getReactiveConnection()
                .serverCommands()
                .flushAll()
                .thenMany(stringFlux.flatMap(ad -> reactiveRedisOperations.opsForValue().set(String.valueOf(count.getAndAdd(1)), ad)))
                .subscribe();
    }

    Flux<Ad> findReactorList() {
        Flux<String> keys = reactiveRedisOperations.keys("*");

        keys.doOnNext(key -> System.out.println(key))
                .subscribe();

        Flux.just(1, 2, 3)
                .doOnNext(i -> System.out.println("호출: " + i))
                .subscribe(i -> System.out.println("출력 결과: " + i));

        Mono<List<String>> listMono = keys.collectSortedList();


//        Disposable subscribe = keys.subscribe(key -> reactiveRedisOperations.opsForValue().get(keys));

        return reactiveRedisOperations.keys("*")
                .flatMap(key -> reactiveRedisOperations.opsForValue().get(key));
//                .take(3);
//                .takeUntil();

    }
}
