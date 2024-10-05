package com.codejukebox.optipackroute.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.codejukebox.optipackroute.core.algorithms.binpacking.anxietatem.AnxietatemAlgorithm;
import com.codejukebox.optipackroute.persistence.repository.RedisRepositoryImpl;
import com.codejukebox.optipackroute.persistence.repository.interfaces.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan(basePackages = "com.codejukebox.optipackroute")
public class CoreConfig {

    @Bean
    RedisRepository redisRepository(RedisTemplate<String, Object> redisTemplate, 
    		ObjectMapper objectMapper) {
        return new RedisRepositoryImpl(redisTemplate, objectMapper);
    }
	
    @Bean
    AnxietatemAlgorithm anxietatemAlgorithm(RedisRepository redisRepository, 
    		ObjectMapper objectMapper) {
        return new AnxietatemAlgorithm(redisRepository, objectMapper);
    }
}
