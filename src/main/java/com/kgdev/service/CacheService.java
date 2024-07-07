package com.kgdev.service;

import com.kgdev.exception.BadRequestException;
import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;

import java.io.IOException;

public interface CacheService {
    CacheValue get(CacheKey key) throws BadRequestException;
    boolean put(CacheKey key, CacheValue value) throws BadRequestException, IOException;
    boolean delete(CacheKey key) throws BadRequestException, IOException;
}
