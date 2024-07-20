package com.kgdev.service;

import com.kgdev.constant.BaseConstant;
import com.kgdev.controller.CacheController;
import com.kgdev.exception.BadRequestException;
import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;
import com.kgdev.object.CommitLog;
import com.kgdev.utils.CommitLogUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@Service("followerInMemoryCacheService")
@Profile("follower")
public class FollowerInMemoryCacheService extends InMemoryCacheService {
    private final RandomAccessFile randomAccessFile;
    private final String filePath;
    private long lastReadPosition;
    private final Map<CacheKey, CacheValue> cacheMap;

    public FollowerInMemoryCacheService(@Value("${logFilePath}") String filePath) throws FileNotFoundException {
        super();
        this.filePath = filePath;
        this.lastReadPosition = 0;
        this.cacheMap = new HashMap<>();
        this.randomAccessFile = new RandomAccessFile(filePath, "r");
    }

    @Scheduled(fixedDelay = 5000)
    public void sync(){
        try {
            randomAccessFile.seek(lastReadPosition);
            String log;
            while((log = randomAccessFile.readLine()) != null){
                apply(CommitLogUtil.parseLogEntry(log));
            }
            lastReadPosition = randomAccessFile.getFilePointer();
        } catch (IOException | BadRequestException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void init(){
        System.out.println("Before post construct map size: "+cacheMap.size());
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            Deque<String> deque = new ArrayDeque<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if (deque.size() == BaseConstant.MAX_LIMIT_TO_INITIALISE) {
                    deque.removeFirst();
                }
                deque.addLast(line);
            }

            for (String log : deque) {
                apply(CommitLogUtil.parseLogEntry(log));
            }
            System.out.println("After post construct map size: "+cacheMap.size());
        } catch (IOException | BadRequestException e) {
            throw new RuntimeException(e);
        }
    }

    private void apply(CommitLog commitLog) throws BadRequestException, IOException {
        switch (commitLog.getMethod()) {
            case WRITE:
                cacheMap.put(commitLog.getKey(), commitLog.getValue());
                break;
            case DELETE:
                cacheMap.remove(commitLog.getKey());
                break;
        }
    }

    @Override
    public CacheValue get(CacheKey key){
        return cacheMap.getOrDefault(key, null);
    }
    @Override
    public boolean put(CacheKey key, CacheValue value) throws BadRequestException {
        throw new BadRequestException("Write is not allowed on read replicas. Please write via leader.");
    }

    @Override
    public boolean delete(CacheKey key) throws BadRequestException {
        throw new BadRequestException("Write is not allowed on read replicas. Please write via leader.");
    }
}
