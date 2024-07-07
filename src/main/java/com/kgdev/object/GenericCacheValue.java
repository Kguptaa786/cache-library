package com.kgdev.object;

public class GenericCacheValue implements CacheValue{
    private final Object value;
    public GenericCacheValue(Object value) {
        this.value = value;
    }

    @Override
    public Object getCacheValue() {
        return this.value;
    }

}
