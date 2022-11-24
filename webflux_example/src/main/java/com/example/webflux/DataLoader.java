package com.example.webflux;



import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader {

    private static Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final ReactiveRedisOperations<String, Object> personOps;


    @PostConstruct
    public void loadData() {
        personOps.opsForValue().set("friend:1", List.of(new AdTarget()))
                .subscribe(result -> logger.info("Redis Data load end! result:" + result));

    }
}
