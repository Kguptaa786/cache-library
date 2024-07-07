package com.kgdev.utils;

import com.kgdev.object.*;

public class CommitLogUtil {

    private static final String DELIMITER = ";;";
    public static String createLogEntry(CommitLog commitLog){
        return commitLog.getKey().getKey() + DELIMITER + commitLog.getValue().getCacheValue() + DELIMITER + commitLog.getMethod().name() + "\n";
    }

    public static CommitLog parseLogEntry(String str){
        String[] parts = str.split(DELIMITER);
        CacheKey key = new CacheKey(parts[0]);
        CacheValue value = new GenericCacheValue(parts[1]);
        MethodEnum method = MethodEnum.valueOf(parts[2].trim());

        return CommitLog.builder()
                .key(key)
                .value(value)
                .method(method)
                .build();
    }
}
