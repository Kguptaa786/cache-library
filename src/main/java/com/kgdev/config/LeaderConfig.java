package com.kgdev.config;

import com.kgdev.service.CacheServiceHelper;
import com.kgdev.service.LeaderInMemoryCacheService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Configuration
@Profile("leader")
public class LeaderConfig {

    private final String filePath;
    public LeaderConfig(@Value("${logFilePath}") String filePath){
        this.filePath = filePath;
    }

    @Bean
    public LeaderInMemoryCacheService leaderInMemoryCacheService() throws IOException {
        return new LeaderInMemoryCacheService(filePath);
    }

    @Bean
    public CacheServiceHelper cacheServiceHelper(LeaderInMemoryCacheService leader) {
        return new CacheServiceHelper(leader, null);
    }
}

