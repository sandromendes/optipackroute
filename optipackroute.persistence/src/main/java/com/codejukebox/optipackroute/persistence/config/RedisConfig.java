package com.codejukebox.optipackroute.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class RedisConfig {

    @Bean
    ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
 
        objectMapper.registerModule(new JavaTimeModule()); 
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS); 
        return objectMapper;
    }
	
    @Bean
    RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory, 
    		ObjectMapper objectMapper) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        
        // Use StringRedisSerializer for the keys
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // Use GenericJackson2JsonRedisSerializer for the values
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        // Set same serializer for hash keys and values
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));

        redisTemplate.setEnableTransactionSupport(true); // Enable transaction support if needed

        return redisTemplate;
    }
}
