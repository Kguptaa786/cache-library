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

@RestController("/leader/cache")
public class LeaderController {

    @Autowired
    @Qualifier("leaderInMemoryCacheService")
    private CacheService leaderCacheService;

    @GetMapping("test")
    public ResponseEntity testApi(){
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("put")
    public ResponseEntity putCacheKeyAndValue(@RequestParam CacheKey key, @RequestParam CacheValue value) throws BadRequestException, IOException {
        System.out.println("key : "+key + ", value : "+value);
        return ResponseEntity.ok(leaderCacheService.put(key, value));
    }

    @PostMapping("delete")
    public ResponseEntity deleteCacheKeyAndValue(@RequestParam CacheKey key) throws BadRequestException, IOException {
        return ResponseEntity.ok(leaderCacheService.delete(key));
    }
}
