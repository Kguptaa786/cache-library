package com.kgdev.config;
import com.kgdev.service.CacheServiceHelper;
import com.kgdev.service.FollowerInMemoryCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
@Profile({"follower1", "follower2", "follower3"})
public class FollowerConfig {
    private final String filePath;
    public FollowerConfig(@Value("${logFilePath}") String filePath){
        this.filePath = filePath;
    }
    @Bean
    public FollowerInMemoryCacheService followerInMemoryCacheService() throws IOException, FileNotFoundException {
        return new FollowerInMemoryCacheService(filePath);
    }

    @Bean
    public CacheServiceHelper cacheServiceHelper(FollowerInMemoryCacheService follower) {
        return new CacheServiceHelper(null, follower);
    }
}

