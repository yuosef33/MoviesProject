package com.project.revision.Service.Impl;


import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl {
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisTemplate<String,Map<String ,String>> redisTemplate;
    public RedisServiceImpl(StringRedisTemplate stringRedisTemplate, RedisTemplate<String,Map<String ,String>> redisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
        this.redisTemplate = redisTemplate;
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

    public void saveChat(String userEmail,String question, String answer) {
        String key="chat:" + userEmail;
        Map<String,String> QA=new HashMap<>();
        QA.put("question",question);
        QA.put("answer",answer);
        redisTemplate.opsForList().rightPush(key,QA);
        redisTemplate.expire(key, Duration.ofHours(5));
    }
    public void updateTime(String key,long seconds){
        stringRedisTemplate.expire(key,Duration.ofSeconds(seconds));
    }

    public List<Map<String,String>> getChatHistory(String userEmail) {
        return redisTemplate.opsForList().range("chat:" +userEmail, 0, -1);
    }

    public void clearChat(String userEmail) {
        redisTemplate.delete("chat:" + userEmail);
    }
}
