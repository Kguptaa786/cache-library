package com.kgdev.controller;

import com.kgdev.exception.BadRequestException;
import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;
import com.kgdev.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController("/follower/cache")
public class FollowerController {

    @Autowired
    @Qualifier("followerInMemoryCacheService")
    private CacheService followerCacheService;
    @GetMapping("get")
    public ResponseEntity getCacheKey(@RequestParam CacheKey key) throws BadRequestException {

        CacheValue cacheValue = followerCacheService.get(key);
        return ResponseEntity.ok(cacheValue);
    }
}
