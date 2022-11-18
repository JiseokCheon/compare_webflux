package com.example.normal;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BasicService {

    private final RedisTemplate<String, Ad> redisTemplate;

    void loadData() {
        IntStream.range(0, 100000).forEach(i ->
                redisTemplate.opsForValue().set(UUID.randomUUID().toString(), Ad.builder()
                        .id(UUID.randomUUID().toString())
                        .name("광고-" + i)
                        .weight(new Random().nextLong(0, 100000))
                        .build()
                ));
    }


    public List<Ad> findNormalList() {
        Set<String> keys = Objects.requireNonNull(redisTemplate.keys("*"));
        return redisTemplate.opsForValue().multiGet(keys);
    }


}
