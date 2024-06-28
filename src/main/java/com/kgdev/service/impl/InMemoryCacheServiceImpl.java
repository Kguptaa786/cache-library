package com.kgdev.service.impl;

import com.kgdev.service.CacheService;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCacheServiceImpl implements CacheService {
    Map<String, Object> cahceMap;

    public InMemoryCacheServiceImpl() {
        this.cahceMap = new HashMap<>();
    }

    @Override
    public Object get(String key) {
        if(cahceMap.containsKey(key)){
            return cahceMap.get(key);
        }
        return null;
    }

    @Override
    public void put(String key, Object value) {
        cahceMap.put(key, value);
    }
}
