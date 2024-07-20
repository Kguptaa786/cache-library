package com.kgdev.object;

import com.kgdev.utils.CacheUtil;
import lombok.Getter;

import java.util.Objects;
@Getter
public class CacheKey {
    private final String key;
    private CacheKey(String key) {
        this.key = key;
    }

    public static CacheKey from(String key){
        CacheUtil.validateCacheKey(key);
        return new CacheKey(key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CacheKey cacheKey = (CacheKey) o;
        return Objects.equals(key, cacheKey.key);
    }
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
