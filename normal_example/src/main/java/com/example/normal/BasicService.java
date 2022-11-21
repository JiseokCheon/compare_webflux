package com.example.normal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class BasicService {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    void loadData() {
        IntStream.range(0, 10000).forEach(i ->
                redisTemplate.opsForValue().set(UUID.randomUUID().toString(), Ad.builder()
                        .id(UUID.randomUUID().toString())
                        .weight(new Random().nextLong(0, 10000))
                        .build()
                ));
    }


    public List<Ad> findNormalList(String targetKey) {
        return Objects.requireNonNull(redisTemplate.opsForValue().multiGet(objectMapper.convertValue(redisTemplate.opsForValue().get(targetKey), AdTarget.class).adsNoList))
                .stream()
                .map(obj -> objectMapper.convertValue(obj, Ad.class))
                .collect(Collectors.toList());
    }
}
