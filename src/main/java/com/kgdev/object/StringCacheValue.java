package com.kgdev.object;

public class StringCacheValue implements CacheValue{
    private final String value;
    public StringCacheValue(String value) {
        this.value = value;
    }
    @Override
    public Object getCacheValue() {
        return this.value;
    }

}
