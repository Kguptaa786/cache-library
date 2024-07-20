package com.kgdev.controller;

import com.kgdev.exception.BadRequestException;
import com.kgdev.service.CacheServiceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/v1/cache")
public class CacheController {

    @Autowired
    private CacheServiceHelper cacheServiceHelper;

    @GetMapping
    public ResponseEntity getCacheValueFromKey(@RequestParam String key){
        return ResponseEntity.ok(cacheServiceHelper.getCacheValueFromKey(key));
    }
    @PostMapping
    public ResponseEntity putCacheKeyAndValue(@RequestParam String key, @RequestParam String value) throws IOException {
        return ResponseEntity.ok(cacheServiceHelper.putCacheKeyAndValue(key, value));
    }

    @DeleteMapping
    public ResponseEntity deleteCacheKeyAndValue(@RequestParam String key) throws IOException {
        return ResponseEntity.ok(cacheServiceHelper.deleteCacheKey(key));
    }
}
