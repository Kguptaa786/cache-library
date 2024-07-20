package com.kgdev.service;

import com.kgdev.object.*;
import com.kgdev.utils.CommitLogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service("leaderInMemoryCacheService")
@Profile("leader")
public class LeaderInMemoryCacheService extends InMemoryCacheService{

    private final FileWriter commitLogWriter;
    private final Map<CacheKey, CacheValue> cacheMap;
    public LeaderInMemoryCacheService(@Value("${logFilePath}") String filePath) throws IOException {
        super();
        this.commitLogWriter = new FileWriter(filePath, true);
        this.cacheMap = new HashMap<>();
    }

    @Override
    public CacheValue get(CacheKey key) {
        return cacheMap.getOrDefault(key, null);
    }

    @Override
    public boolean put(CacheKey key, CacheValue value) {
        try {
            CommitLog commitLog = CommitLog.builder().key(key).value(value).method(MethodEnum.WRITE).build();
            commitLogWriter.append(CommitLogUtil.createLogEntry(commitLog));
            commitLogWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cacheMap.put(key, value);
        return true;
    }

    @Override
    public boolean delete(CacheKey key) {
        if(!cacheMap.containsKey(key)){
            return false;
        }
        try {
            CommitLog commitLog = CommitLog.builder().key(key).method(MethodEnum.DELETE).build();
            commitLogWriter.append(CommitLogUtil.createLogEntry(commitLog));
            commitLogWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        cacheMap.remove(key);
        return true;
    }
}
