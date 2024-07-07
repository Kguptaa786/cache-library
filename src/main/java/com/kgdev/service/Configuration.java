package com.kgdev.service;

import lombok.Data;

@Data
public class Configuration {
    private String mode;
    private Connection connection;
    private LeaderConnection leaderConnection;
    private String logFilePath;

    @Data
    public static class Connection {
        private String host;
        private int port;

    }

    @Data
    public static class LeaderConnection {
        private String host;
        private int port;

    }
}

