package com.kgdev.service;

import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class CacheStorage {
    private final Map<CacheKey, CacheValue> cacheMap;
    public CacheStorage(){
        cacheMap = new HashMap<>();
    }

}
