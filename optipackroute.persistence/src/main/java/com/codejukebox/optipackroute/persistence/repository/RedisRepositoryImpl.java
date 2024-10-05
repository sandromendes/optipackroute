package com.codejukebox.optipackroute.persistence.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.codejukebox.optipackroute.persistence.repository.interfaces.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

@Repository
public class RedisRepositoryImpl implements RedisRepository {

    private RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    
    public RedisRepositoryImpl(RedisTemplate<String, Object> redisTemplate, 
    		ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }
    
    @Override
    public <T> void saveArrayList(String key, List<T> list) {
        try {
            for (T item : list) {
                String serializedItem = objectMapper.writeValueAsString(item);
                redisTemplate.opsForList().rightPush(key, serializedItem);
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addToList(String key, Object item) {
        redisTemplate.opsForList().rightPush(key, item);
    }

    @Override
    public <T> List<T> retrieveArrayList(String key, Class<T> clazz) {
        List<Object> rawData = redisTemplate.opsForList().range(key, 0, -1);
        List<T> resultList = new ArrayList<>();

        for (Object item : rawData) {
            try {
                if (item instanceof LinkedHashMap) {
                    String json = objectMapper.writeValueAsString(item);
                    T deserializedItem = objectMapper.readValue(json, clazz);
                    resultList.add(deserializedItem);
                } else if (item instanceof String) {
                    T deserializedItem = objectMapper.readValue((String) item, clazz);
                    resultList.add(deserializedItem);
                } else {
                    System.out.println("No identified item: " + item.getClass());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }

    @Override
    public void deleteArrayList(String key) {
        redisTemplate.delete(key);
    }
    
    public void removeItemFromList(String key, Object item) {
        redisTemplate.opsForList().remove(key, 1, item);
    }
    
    public static <T> List<T> jsonArrayToList(String json, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType = 
          objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return objectMapper.readValue(json, listType);
    }
}
