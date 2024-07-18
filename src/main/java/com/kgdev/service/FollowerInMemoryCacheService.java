package com.kgdev.service;

import com.kgdev.exception.BadRequestException;
import com.kgdev.object.CacheKey;
import com.kgdev.object.CacheValue;
import com.kgdev.object.CommitLog;
import com.kgdev.utils.CommitLogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

@Service("followerInMemoryCacheService")
public class FollowerInMemoryCacheService extends InMemoryCacheService implements Runnable{
    private  Socket leaderSocket;
    private  BufferedReader commitLogReader;

    public FollowerInMemoryCacheService(@Value("${leaderConnection.host}") String host,@Value("${leaderConnection.port}") int port){
        super();
        try {
            this.leaderSocket = new Socket(host, port);
            this.commitLogReader = new BufferedReader(new InputStreamReader(leaderSocket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        sync();
    }
    private void sync() {
        try {
            String log;
            while ((log = commitLogReader.readLine()) != null) {
                CommitLog commitLog = CommitLogUtil.parseLogEntry(log);
                apply(commitLog);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void apply(CommitLog commitLog) throws BadRequestException, IOException {
        switch (commitLog.getMethod()) {
            case WRITE:
                super.put(commitLog.getKey(), commitLog.getValue());
                break;
            case DELETE:
                super.delete(commitLog.getKey());
                break;
        }
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
