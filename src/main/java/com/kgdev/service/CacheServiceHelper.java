package com.kgdev.service;

import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;
import com.kgdev.object.StringCacheValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class CacheServiceHelper {

    private final LeaderInMemoryCacheService leader;
    private final FollowerInMemoryCacheService follower;

    @Autowired
    public CacheServiceHelper(LeaderInMemoryCacheService leader, FollowerInMemoryCacheService follower) {
        this.leader = leader;
        this.follower = follower;
    }

    public CacheValue getCacheValueFromKey(String key) {
        if(follower == null){
            leader.get(CacheKey.from(key));
        }
        return follower.get(CacheKey.from(key));
    }

    public Boolean putCacheKeyAndValue(String key, String value) throws IOException {
        if (leader == null) {
            throw new UnsupportedOperationException("Write operations are not allowed on follower profile.");
        }
        return leader.put(CacheKey.from(key), new StringCacheValue(value));
    }

    public Boolean deleteCacheKey(String key) throws IOException {
        if (leader == null) {
            throw new UnsupportedOperationException("Delete operations are not allowed on follower profile.");
        }
        return leader.delete(CacheKey.from(key));
    }
}
