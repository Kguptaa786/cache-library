package com.kgdev.service;

import com.kgdev.exception.BadRequestException;
import com.kgdev.object.*;
import com.kgdev.utils.CommitLogUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
@Service("leaderInMemoryCacheService")
public class LeaderInMemoryCacheService extends InMemoryCacheService{

    private final ServerSocket serverSocket;
    private final List<Socket> followerSockets = new ArrayList<>();
    private final FileWriter commitLogWriter;
    public LeaderInMemoryCacheService(@Value("${connection.port}") int port, @Value("${logFilePath}") String filePath) throws IOException {
        super();
        this.serverSocket = new ServerSocket(port);
        this.commitLogWriter = new FileWriter(filePath, true);
        new Thread(this::acceptFollowerConnections).start();
    }

    private void acceptFollowerConnections() {
        while (true) {
            try {
                Socket followerSocket = serverSocket.accept();
                System.out.println(followerSocket);
                followerSockets.add(followerSocket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean put(CacheKey key, CacheValue value) throws IOException, BadRequestException {
        try {
            CommitLog commitLog = CommitLog.builder().key(key).value(value).method(MethodEnum.WRITE).build();
            commitLogWriter.append(CommitLogUtil.createLogEntry(commitLog));
            commitLogWriter.flush();
            for (Socket followerSocket : followerSockets) {
                followerSocket.getOutputStream().write(CommitLogUtil.createLogEntry(commitLog).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.put(key, value);
    }

    @Override
    public boolean delete(CacheKey key) throws IOException, BadRequestException {
        try {
            CommitLog commitLog = CommitLog.builder().key(key).method(MethodEnum.DELETE).build();
            commitLogWriter.append(CommitLogUtil.createLogEntry(commitLog));
            commitLogWriter.flush();
            for (Socket followerSocket : followerSockets) {
                followerSocket.getOutputStream().write(CommitLogUtil.createLogEntry(commitLog).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return super.delete(key);
    }
}
