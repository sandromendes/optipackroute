package com.codejukebox.optipackroute.persistence.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.codejukebox.optipackroute.persistence.repository.interfaces.RedisRepository;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void saveArrayList(String key, List<? extends Object> data) {
        redisTemplate.opsForList().rightPushAll(key, data);
        int expireMinutes = 10;
        redisTemplate.expire(key, expireMinutes, TimeUnit.MINUTES);
    }

    @Override
    public void addToList(String key, Object item) {
        redisTemplate.opsForList().rightPush(key, item);
    }

    @Override
    public List<Object> getArrayList(String key) {
        return redisTemplate.opsForList().range(key, 0, -1);
    }

    @Override
    public void deleteArrayList(String key) {
        redisTemplate.delete(key);
    }
    
    public void removeItemFromList(String key, Object item) {
        redisTemplate.opsForList().remove(key, 1, item);
    }
}
