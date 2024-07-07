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

@RestController("/cache")
public class CacheController {

    @Autowired
    @Qualifier("LeaderInMemoryCacheService")
    private CacheService leaderCacheService;

    @Autowired
    @Qualifier
    private CacheService followerCacheService;


    @GetMapping(value = "/get")
    public ResponseEntity getCacheKey(@RequestParam CacheKey key) throws BadRequestException {

        CacheValue cacheValue = followerCacheService.get(key);
        return ResponseEntity.ok(cacheValue);
    }

    @PostMapping(value = "/put")
    public ResponseEntity putCacheKeyAndValue(@RequestParam CacheKey key, @RequestParam CacheValue value) throws BadRequestException, IOException {
        return ResponseEntity.ok(leaderCacheService.put(key, value));
    }

    @PostMapping(value = "delete")
    public ResponseEntity deleteCacheKeyAndValue(@RequestParam CacheKey key) throws BadRequestException, IOException {
        return ResponseEntity.ok(leaderCacheService.delete(key));
    }
}
