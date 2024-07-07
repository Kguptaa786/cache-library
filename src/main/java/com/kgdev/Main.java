package com.kgdev;

import com.kgdev.service.Configuration;
import com.kgdev.service.ConfigurationLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.File;
import java.io.IOException;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Configuration file path is required as an argument.");
        }

        String configFilePath = args[0];
        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            throw new IllegalArgumentException("Configuration file not found: " + configFilePath);
        }

        SpringApplication.run(Main.class, args);
    }

    @Bean
    public Configuration configuration() throws IOException {
        String configFilePath = System.getProperty("config.file");
        File configFile = new File(configFilePath);

        if (!configFile.exists()) {
            throw new IllegalArgumentException("Configuration file not found: " + configFilePath);
        }

        return ConfigurationLoader.loadConfiguration(configFilePath);
    }
}
