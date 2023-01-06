package com.example.normal;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
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


    public List<Object> findNormalList(String targetKey) {

        List<String> adsNoList = objectMapper.convertValue(redisTemplate.opsForValue().get(targetKey), AdTarget.class).getAdsNoList();

//        return adsNoList.stream().map(s -> objectMapper.convertValue(redisTemplate.opsForValue().get(s), Ad.class))
//                .collect(Collectors.toList());


        return Objects.requireNonNull(redisTemplate.opsForValue().multiGet(adsNoList));
//                .stream()
//                .map(obj -> objectMapper.convertValue(obj, Ad.class))
//                .sorted(Comparator.comparing(Ad::getWeight))
//                .collect(Collectors.toList());
    }
}
