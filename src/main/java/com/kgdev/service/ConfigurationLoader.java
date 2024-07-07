package com.kgdev.service;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class ConfigurationLoader {
    public static Configuration loadConfiguration(String filePath) throws IOException {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return yaml.loadAs(inputStream, Configuration.class);
        }
    }
}

