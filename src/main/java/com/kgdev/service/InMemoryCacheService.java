package com.kgdev.service;

import com.kgdev.exception.BadRequestException;
import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;

import java.io.IOException;

public abstract class InMemoryCacheService implements CacheService {
    private final CacheStorage cacheStorage;

    public InMemoryCacheService() {
        this.cacheStorage = new CacheStorage();
    }

    @Override
    public CacheValue get(CacheKey key) {
        return cacheStorage.getCacheMap().getOrDefault(key, null);
    }

    @Override
    public boolean put(CacheKey key, CacheValue value) throws BadRequestException, IOException {
        cacheStorage.getCacheMap().put(key, value);
        return true;
    }

    @Override
    public boolean delete(CacheKey key) throws BadRequestException, IOException {
        return cacheStorage.getCacheMap().remove(key) != null;
    }
}
