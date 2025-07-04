package com.project.revision.Service.Impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class RedisServiceImpl {
    private final StringRedisTemplate stringRedisTemplate;

    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }
    public void save(String key, String value, long seconds) {
        stringRedisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    public String get(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }


    public void delete(String key) {
        stringRedisTemplate.delete(key);
    }
}
